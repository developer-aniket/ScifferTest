package com.sciffer.aniket.anikettestsciffer.ui.main;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sciffer.aniket.anikettestsciffer.R;
import com.sciffer.aniket.anikettestsciffer.data.model.AnswerModel;
import com.sciffer.aniket.anikettestsciffer.data.model.QuestionResponse;
import com.sciffer.aniket.anikettestsciffer.data.model.ResponseModel;
import com.sciffer.aniket.anikettestsciffer.ui.OnClickListenerRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.AnswersViewHolder> implements OnClickListenerRecyclerView {
    private static final String TAG = AnswersAdapter.class.getSimpleName();
    private ArrayList<AnswerModel> answerModels;
    private OnClickListenerRecyclerView mOnClickListenerRecyclerView;

    public AnswersAdapter(ArrayList<AnswerModel> answerModels, OnClickListenerRecyclerView onClickListenerRecyclerView) {
        this.answerModels = answerModels;
        this.mOnClickListenerRecyclerView = onClickListenerRecyclerView;
    }

    public void refreshList(ArrayList<AnswerModel> answerModels) {
        this.answerModels = answerModels;
        notifyDataSetChanged();
    }

    @Override
    public AnswersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answers, parent, false);
        return new AnswersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnswersViewHolder holder, final int position) {
        AnswerModel answerModel = answerModels.get(position);

        String title = answerModel.getQuestion();
        String answer = answerModel.getAnswer();

        holder.tv_title.setText(title);
        holder.tv_answer.setText(answer);

    }

    @Override
    public int getItemCount() {
        return answerModels.size();
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
        static class AnswersViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_answer;

            AnswersViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_answer = itemView.findViewById(R.id.tv_answer);
        }
    }

}
