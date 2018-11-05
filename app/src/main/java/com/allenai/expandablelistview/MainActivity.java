package com.allenai.expandablelistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.allenai.expandablelistview.adapter.ChapterAdapter;
import com.allenai.expandablelistview.biz.ChapterBiz;
import com.allenai.expandablelistview.entiy.Chapter;
import com.allenai.expandablelistview.entiy.ChapterLab;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private              ExpandableListView mExpandableListView;
    private static final String             TAG = "Allen MainActivity";
    private              ChapterAdapter     mAdapter;
    private List<Chapter> mDatas = new ArrayList<>();
    private Button mBtnRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvent();

       // mDatas= ChapterLab.generateDatas();//generate data in lab

        mAdapter =new ChapterAdapter(this,mDatas);

        mExpandableListView.setAdapter(mAdapter);

        loadDatas(true);

    }
    private ChapterBiz mChapterBiz = new ChapterBiz();

    private void loadDatas(boolean useCashe) {
        mChapterBiz.loadDatas(this, new ChapterBiz.CallBack() {
            @Override
            public void loadSuccess(List<Chapter> chapters) {

               mDatas.clear();
               mDatas.addAll(chapters);
               mAdapter.notifyDataSetChanged();
               Log.e(TAG, "loadSuccess: ");

            }

            @Override
            public void loadFailed(Exception ex) {
                ex.printStackTrace();
                Log.e(TAG, "loadFailed: ex="+ex.getMessage());

            }
        },useCashe);


    }

    private void initEvent() {

        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatas(false);
            }
        });

        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                Log.d(TAG, "onGroupClick: groupPosition = "+groupPosition);
                return false;
            }
        });
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Log.d(TAG, "onChildClick: groupPosition = "+groupPosition+", childPosition = "+childPosition+
                        ", id = "+id);
                return false;
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            // 收回
            @Override
            public void onGroupCollapse(int groupPosition) {
                Log.d(TAG, "onGroupCollapse groupPosition = " + groupPosition);

            }
        });

        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            // 展开
            @Override
            public void onGroupExpand(int groupPosition) {
                Log.d(TAG, "onGroupExpand groupPosition = " + groupPosition);

            }
        });

        mExpandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick position = " + position);

            }
        });



    }

    private void initViews() {
        mExpandableListView =findViewById(R.id.id_expandable_listview);
        mBtnRefresh = findViewById(R.id.id_btn_refresh);

    }
}
