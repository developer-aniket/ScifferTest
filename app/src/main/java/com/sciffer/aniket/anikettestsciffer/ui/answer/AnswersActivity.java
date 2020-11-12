package com.sciffer.aniket.anikettestsciffer.ui.answer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sciffer.aniket.anikettestsciffer.R;
import com.sciffer.aniket.anikettestsciffer.data.model.AnswerModel;
import com.sciffer.aniket.anikettestsciffer.ui.OnClickListenerRecyclerView;
import com.sciffer.aniket.anikettestsciffer.ui.main.AnswersAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnswersActivity extends AppCompatActivity implements OnClickListenerRecyclerView {
    private static final String TAG = AnswersActivity.class.getSimpleName();
    RecyclerView rv_list;
    TextView tv_not_found;

    private ArrayList<AnswerModel> mAnswerList = new ArrayList<>();
    private AnswersAdapter mAnswersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        allowBack();
        bindViews();
        initRecyclerView();
        getValues();
    }

    public void allowBack() {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void bindViews() {
        tv_not_found = findViewById(R.id.tv_not_found);
        rv_list = findViewById(R.id.rv_list);
    }

    private void initRecyclerView() {
        mAnswerList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list.setLayoutManager(layoutManager);
        mAnswersAdapter = new AnswersAdapter(mAnswerList, this);
        rv_list.setAdapter(mAnswersAdapter);
    }

    private void getValues() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("AnswerModelList")){
                mAnswerList = intent.getParcelableArrayListExtra("AnswerModelList");
                mAnswersAdapter.refreshList(mAnswerList);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickRecyclerView(View view, int position, int childPosition) {

    }

    @Override
    public void onClickRecyclerView(View view, int position) {

    }
}