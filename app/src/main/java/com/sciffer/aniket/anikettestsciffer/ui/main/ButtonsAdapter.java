package com.sciffer.aniket.anikettestsciffer.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sciffer.aniket.anikettestsciffer.R;
import com.sciffer.aniket.anikettestsciffer.data.model.ResponseModel;
import com.sciffer.aniket.anikettestsciffer.ui.OnClickListenerRecyclerView;

import java.util.ArrayList;

public class ButtonsAdapter extends RecyclerView.Adapter<ButtonsAdapter.ButtonsViewHolder> {
    private static final String TAG = ButtonsAdapter.class.getSimpleName();
    private ArrayList<ResponseModel> responseModels;
    private int mParentPosition = -1;
    private OnClickListenerRecyclerView mOnClickListenerRecyclerView;

    public ButtonsAdapter(ArrayList<ResponseModel> responseArrayList, int position, OnClickListenerRecyclerView onClickListenerRecyclerView) {
        this.responseModels = responseArrayList;
        this.mParentPosition = position;
        this.mOnClickListenerRecyclerView = onClickListenerRecyclerView;
    }

    public void refreshList(ArrayList<ResponseModel> doctorArray) {
        this.responseModels = doctorArray;
        notifyDataSetChanged();
    }

    @Override
    public ButtonsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buttons, parent, false);
        return new ButtonsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ButtonsViewHolder holder, final int position) {
        ResponseModel questionResponse = responseModels.get(position);

        String title = questionResponse.getResponseText();
        holder.btn_title.setText(title);

        holder.btn_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickListenerRecyclerView.onClickRecyclerView(view, mParentPosition, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return responseModels.size();
    }

    static class ButtonsViewHolder extends RecyclerView.ViewHolder {
        TextView btn_title;

        ButtonsViewHolder(View itemView) {
            super(itemView);
            btn_title = itemView.findViewById(R.id.btn_title);
        }
    }

}
