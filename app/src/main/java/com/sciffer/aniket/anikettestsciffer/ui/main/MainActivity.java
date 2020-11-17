package com.sciffer.aniket.anikettestsciffer.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sciffer.aniket.anikettestsciffer.R;
import com.sciffer.aniket.anikettestsciffer.data.model.AnswerModel;
import com.sciffer.aniket.anikettestsciffer.data.model.QuestionResponse;
import com.sciffer.aniket.anikettestsciffer.data.model.ResponseModel;
import com.sciffer.aniket.anikettestsciffer.ui.OnClickListenerRecyclerView;
import com.sciffer.aniket.anikettestsciffer.ui.answer.AnswersActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnClickListenerRecyclerView, View.OnClickListener {

    private static final int REQUEST_CAMERA = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView rv_list;
    TextView tv_not_found;
    Button btn_submit;

    private ArrayList<QuestionResponse> mQuestionList = new ArrayList<>();
    private QuestionsAdapter mQuestionsAdapter;

    private Map<Integer, Integer> mLastCheckedItem = new HashMap<>();

    private Map<Integer, String> mEnteredText = new HashMap<>();

    private Map<Integer, Uri> mSelectedImages = new HashMap<>();
    private int mSelectedImagePosition = -1;

    private ArrayList<Integer> mUnansweredMandatoryQuestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        initRecyclerView();
        getValues();
    }

    private void bindViews() {
        tv_not_found = findViewById(R.id.tv_not_found);
        rv_list = findViewById(R.id.rv_list);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mQuestionList = new ArrayList<>();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list.setLayoutManager(layoutManager);
        mQuestionsAdapter = new QuestionsAdapter(mQuestionList, this);
        rv_list.setAdapter(mQuestionsAdapter);
    }

    private void getValues() {
        try {
            String response = loadJSONFromAsset();
            JSONArray data = new JSONArray(response);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<QuestionResponse>>() {
            }.getType();
            mQuestionList = gson.fromJson(data.toString(), listType);
            mQuestionsAdapter.refreshList(mQuestionList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onClickRecyclerView(View view, int position, int childPosition) {
        if (view.getId() == R.id.ll_spinner) {
            mLastCheckedItem.put(position, childPosition);
            mQuestionsAdapter.setLastCheckedItem(mLastCheckedItem);
        } else if (view.getId() == R.id.btn_title) {
            mLastCheckedItem.put(position, childPosition);
            mQuestionsAdapter.setLastCheckedItem(mLastCheckedItem);
        }

    }

    @Override
    public void onClickRecyclerView(View view, int position) {
        switch (view.getId()) {
            case R.id.iv_upload_image:
                mSelectedImagePosition = position;
                cameraIntent();
                break;
        }
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");

        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        Uri tempUri = getImageUri(getApplicationContext(), photo);

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        File destination = new File(getRealPathFromURI(tempUri));

        String path = destination.getAbsolutePath();
        Log.e(TAG, "onCaptureImageResult: " + path);
        String fileName = destination.getName();
//        if (fileName != null && !fileName.isEmpty()) {
//            tvAttachmentName.setText(fileName);
//        } else {
//            tvAttachmentName.setText("" + photo);
//        }
//        ivRemoveAttachment.setVisibility(View.VISIBLE);
//        bitmap = thumbnail;
//        imageView.setImageBitmap(thumbnail);
        mSelectedImages.put(mSelectedImagePosition, tempUri);
        mQuestionsAdapter.setSelectedImages(mSelectedImages);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            StringBuilder selectedItems = new StringBuilder();
            StringBuilder enteredText = new StringBuilder();
            StringBuilder selectedImages = new StringBuilder();
            mUnansweredMandatoryQuestions = new ArrayList<>();
            mEnteredText = mQuestionsAdapter.getEnteredText();
            ArrayList<AnswerModel> answerModelArrayList = new ArrayList<>();
            int size = mQuestionList.size();
            for (int i = 0; i < size; i++) {
                AnswerModel answerModel = new AnswerModel();
                QuestionResponse questionResponse = mQuestionList.get(i);
                boolean isAnswered = false;
                if (mLastCheckedItem.containsKey(i)) {
                    isAnswered = true;
                    selectedItems.append("Selected Items Position");
                    selectedItems.append(mLastCheckedItem.get(i));
                    selectedItems.append("\n");
                    // Add answer
                    List<ResponseModel> responses = questionResponse.getResponses();
                    String type = questionResponse.getResponseType();
                    String answer = "";
                    if (QuestionType.DRPDWN.name().equalsIgnoreCase(type)) {
                        if (mLastCheckedItem == null || mLastCheckedItem.get(i) == 0) {
                            isAnswered = false;
                        } else {
                            answer = responses.get(mLastCheckedItem.get(i) - 1).getResponseText();
                        }
                    } else {
                        answer = responses.get(mLastCheckedItem.get(i)).getResponseText();
                    }
                    answerModel.setAnswer(answer);
                }

                if (mEnteredText.containsKey(i)) {
                    isAnswered = true;
                    enteredText.append(mEnteredText.get(i));
                    enteredText.append("\n");
                    // Add answer
                    String answer = mEnteredText.get(i);
                    answerModel.setAnswer(answer);
                }

                if (mSelectedImages.containsKey(i)) {
                    isAnswered = true;
                    selectedImages.append(mSelectedImages.get(i));
                    selectedImages.append("\n");
                    // Add answer
                    String answer = String.valueOf(mSelectedImages.get(i));
                    answerModel.setAnswer(answer);
                }

                answerModel.setQuestion(questionResponse.getQuesContent());
                if (questionResponse.isCompulsory() && !isAnswered) {
                    mUnansweredMandatoryQuestions.add(i);
                }
                answerModelArrayList.add(answerModel);
            }

            int remainingQuestions = mUnansweredMandatoryQuestions.size();
            if (remainingQuestions > 0) {
                Toast.makeText(this, "Please answer all the mandatory questions marked with *", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Answers submitted successfully", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(this, AnswersActivity.class).putExtra("AnswerModelList", answerModelArrayList));
            }

        }
    }
}