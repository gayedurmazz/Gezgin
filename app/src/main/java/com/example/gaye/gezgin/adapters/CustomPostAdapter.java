package com.example.gaye.gezgin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaye.gezgin.R;
import com.example.gaye.gezgin.models.PostModel;

import java.util.List;

public class CustomPostAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    List<PostModel> postModelList;

    public CustomPostAdapter(LayoutInflater layoutInflater, List<PostModel> postModelList) {

        this.layoutInflater = layoutInflater;
        this.postModelList = postModelList;

    }

    @Override
    public int getCount() {
        return postModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return postModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View postView = layoutInflater.inflate(R.layout.post_list, null);
        ImageView ivPost = (ImageView) postView.findViewById(R.id.iv_post);
        TextView tvTitle = (TextView) postView.findViewById(R.id.tv_title);
        TextView tvDescription = (TextView) postView.findViewById(R.id.tv_description);

        PostModel postModel = postModelList.get(position);
        ivPost.setImageResource(postModel.getPostPicture());
        tvTitle.setText(postModel.getPostName());
        tvDescription.setText(postModel.getPostDescription());

        return postView;
    }
}
