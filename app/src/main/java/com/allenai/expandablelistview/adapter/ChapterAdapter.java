package com.allenai.expandablelistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.allenai.expandablelistview.R;
import com.allenai.expandablelistview.entiy.Chapter;

import java.util.List;

public class ChapterAdapter extends BaseExpandableListAdapter {
    private Context        mContext;
    private List<Chapter>  mDatas;
    private LayoutInflater mInflater;

    public ChapterAdapter(Context context, List<Chapter> datas) {
        mContext = context;
        mDatas = datas;
        mInflater = LayoutInflater.from(mContext);
    }

    public ChapterAdapter() {
        super();
    }

    @Override
    public int getGroupCount() {
        return mDatas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDatas.get(groupPosition).getChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDatas.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDatas.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition ;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ParentViewHolder vh;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_parent_chapter, parent, false);
            vh = new ParentViewHolder();
            vh.mTextView = convertView.findViewById(R.id.id_tv_parent);
            vh.mImageView = convertView.findViewById(R.id.id_indicator_group);
            convertView.setTag(vh);
        } else {
            vh = (ParentViewHolder) convertView.getTag();
        }


        vh.mTextView.setText(mDatas.get(groupPosition).getName());
        vh.mImageView.setSelected(isExpanded);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder vh;
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.item_child_chapter, parent,false);
            vh = new ChildViewHolder();
            vh.mTextView = convertView.findViewById(R.id.id_tv_child);
            convertView.setTag(vh);

        }else {
            vh= (ChildViewHolder) convertView.getTag();
        }

        vh.mTextView.setText(mDatas.get(groupPosition).getChildren().get(childPosition).getName());

        return convertView;
    }
    //控制child item 不可点击
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public static class ParentViewHolder {
        TextView  mTextView;
        ImageView mImageView;
    }

    public static class ChildViewHolder {
        TextView mTextView;
    }


}
