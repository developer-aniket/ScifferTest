package com.sciffer.aniket.anikettestsciffer.ui.main;

import android.net.Uri;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sciffer.aniket.anikettestsciffer.R;
import com.sciffer.aniket.anikettestsciffer.data.model.QuestionResponse;
import com.sciffer.aniket.anikettestsciffer.data.model.ResponseModel;
import com.sciffer.aniket.anikettestsciffer.ui.OnClickListenerRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> implements OnClickListenerRecyclerView {
    private static final String TAG = QuestionsAdapter.class.getSimpleName();
    private ArrayList<QuestionResponse> mQuestionsList;
    private OnClickListenerRecyclerView mOnClickListenerRecyclerView;
    private Map<Integer, Integer> mLastCheckedItem = new HashMap<>();
    private Map<Integer, String> mEnteredText = new HashMap<>();
    private Map<Integer, Uri> mSelectedImages = new HashMap<>();

    public QuestionsAdapter(ArrayList<QuestionResponse> responseArrayList, OnClickListenerRecyclerView onClickListenerRecyclerView) {
        this.mQuestionsList = responseArrayList;
        this.mOnClickListenerRecyclerView = onClickListenerRecyclerView;
    }

    public void refreshList(ArrayList<QuestionResponse> doctorArray) {
        this.mQuestionsList = doctorArray;
        notifyDataSetChanged();
    }

    public void setLastCheckedItem(Map<Integer, Integer> lastCheckedItem) {
        this.mLastCheckedItem = lastCheckedItem;
    }

    public Map<Integer, String> getEnteredText() {
        return mEnteredText;
    }

    public void setSelectedImages(Map<Integer, Uri> selectedImages) {
        this.mSelectedImages = selectedImages;
        notifyDataSetChanged();
    }

    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questions, parent, false);
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final QuestionsViewHolder holder, final int position) {
        QuestionResponse questionResponse = mQuestionsList.get(position);

//        String path = questionResponse.getDoct_photo();
//        Picasso.get().load(ConstValue.BASE_URL + "/uploads/profile/" + path).into(holder.iv_doctor_profile_pic);
//
        String title = questionResponse.getQuesContent();
        boolean isCompulsory = questionResponse.isCompulsory();
        if (isCompulsory) {
            title = title + "*";
        }

        String type = questionResponse.getResponseType();
        holder.tv_title.setText(title);

        List<ResponseModel> responseModelList = questionResponse.getResponses();
        if (QuestionType.DRPDWN.name().equalsIgnoreCase(type)) {
            // Spinner click listener
            holder.spnr_options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int itemPosition, long id) {
                    mOnClickListenerRecyclerView.onClickRecyclerView(view, position, itemPosition);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // Spinner Drop down elements
            List<String> options = new ArrayList<>();
            options.add("Select");
            int size = responseModelList.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    options.add(responseModelList.get(i).getResponseText());
                }
            }

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(holder.spnr_options.getContext(),
                    R.layout.item_dropdown_text, R.id.tv_spinner_title, options);

            // attaching data adapter to spinner
            holder.spnr_options.setAdapter(dataAdapter);
            int lastCheckedItem = 0;
            if (mLastCheckedItem.containsKey(position)) {
                lastCheckedItem = mLastCheckedItem.get(position);
            }
            holder.spnr_options.setSelection(lastCheckedItem);

            showHideViews(holder, View.VISIBLE, View.GONE, View.GONE, View.GONE);
        } else if (QuestionType.BUTTON.name().equalsIgnoreCase(type)) {
            initRecyclerView(holder.itemView, holder.rv_buttons, position, responseModelList);

            showHideViews(holder, View.GONE, View.VISIBLE, View.GONE, View.GONE);
        } else if (QuestionType.TXTBOX.name().equalsIgnoreCase(type)) {

            String hint = responseModelList.get(0).getResponseText();
            holder.et_txtbox.setTag(position);
            holder.et_txtbox.setHint(hint);

            String inputType = responseModelList.get(0).getInputType();
            if (inputType.equalsIgnoreCase("TXT")) {
                holder.et_txtbox.setInputType(InputType.TYPE_CLASS_TEXT);
            } else if (inputType.equalsIgnoreCase("DAT")) {
                holder.et_txtbox.setInputType(InputType.TYPE_CLASS_DATETIME);
            }
            if (mEnteredText.containsKey(position)) {
                holder.et_txtbox.setText(mEnteredText.get(position));
            }

            showHideViews(holder, View.GONE, View.GONE, View.VISIBLE, View.GONE);
        } else if (QuestionType.IMAGE.name().equalsIgnoreCase(type)) {

            showHideViews(holder, View.GONE, View.GONE, View.GONE, View.VISIBLE);

            if (mSelectedImages.containsKey(position)) {
                holder.iv_upload_image.setImageURI(mSelectedImages.get(position));
            }

            holder.iv_upload_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnClickListenerRecyclerView.onClickRecyclerView(view, position);
                }
            });
        }

    }

    private void showHideViews(QuestionsViewHolder holder, int spnrVisibility, int buttonsVisibility, int txtBoxVisibility, int imageViewVisibility) {
        holder.spnr_options.setVisibility(spnrVisibility);
        holder.rv_buttons.setVisibility(buttonsVisibility);
        holder.et_txtbox.setVisibility(txtBoxVisibility);
        holder.iv_upload_image.setVisibility(imageViewVisibility);
    }

    private void initRecyclerView(View itemView, RecyclerView rv_buttons, int position, List<ResponseModel> responses) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(itemView.getContext(), 2);
        rv_buttons.setLayoutManager(gridLayoutManager);
        ButtonsAdapter buttonsAdapter = new ButtonsAdapter((ArrayList<ResponseModel>) responses, position, this);
        rv_buttons.setAdapter(buttonsAdapter);
    }

    @Override
    public int getItemCount() {
        return mQuestionsList.size();
    }

    @Override
    public void onClickRecyclerView(View view, int position, int childPosition) {
        if (view.getId() == R.id.btn_title) {
            Log.e(TAG, "onClickRecyclerView Button: " + position);
            mOnClickListenerRecyclerView.onClickRecyclerView(view, position, childPosition);
        }
    }

    @Override
    public void onClickRecyclerView(View view, int position) {
        if (view.getId() == R.id.btn_title) {
            Log.e(TAG, "onClickRecyclerView Button: " + position);
        }
    }

    class QuestionsViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        EditText et_txtbox;
        Spinner spnr_options;
        ImageView iv_upload_image;
        RecyclerView rv_buttons;

        QuestionsViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            et_txtbox = itemView.findViewById(R.id.et_txtbox);
            spnr_options = itemView.findViewById(R.id.spnr_options);
            iv_upload_image = itemView.findViewById(R.id.iv_upload_image);
            rv_buttons = itemView.findViewById(R.id.rv_buttons);

            MyTextWatcher textWatcher = new MyTextWatcher(et_txtbox);
            et_txtbox.addTextChangedListener(textWatcher);
        }
    }

    class MyTextWatcher implements TextWatcher {
        private EditText editText;

        public MyTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int position = (int) editText.getTag();
            // Do whatever you want with position
            Log.e(TAG, "onTextChanged: " + position);
        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString().trim();
            int position = (int) editText.getTag();
            Log.e(TAG, "afterTextChanged: " + position + "***" + text);
            mEnteredText.put(position, text);
        }
    }
}
