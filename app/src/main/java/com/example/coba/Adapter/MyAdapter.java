package com.example.coba.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.coba.ChatFragment;
import com.example.coba.Models.TitleChild;
import com.example.coba.Models.TitleParent;
import com.example.coba.R;
import com.example.coba.ViewHolders.TitleChildViewHolder;
import com.example.coba.ViewHolders.TitleParentViewHolder;

import java.util.ArrayList;
import java.util.List;

//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.TitleChildViewHolder>{
//
//    public List<TitleChild> list;
//
//
//
//    @NonNull
//    @Override
//    public TitleChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new TitleChildViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_child, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TitleChildViewHolder holder, int position) {
//        TitleChild title = list.get(position);
//        holder.text_date.setText(title.itemDate);
//        holder.text_time.setText(title.itemTime);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    class TitleChildViewHolder extends RecyclerView.ViewHolder {
//        TextView text_date, text_time;
//
//        public TitleChildViewHolder (View view){
//            super(view);
//            text_date = (TextView) view.findViewById(R.id.text_date);
//            text_time = (TextView) view.findViewById(R.id.text_time);
//        }
//    }
//
//}
public class MyAdapter extends ExpandableRecyclerAdapter<TitleParentViewHolder, TitleChildViewHolder> {

    LayoutInflater inflater;
    public MyAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
    }

//    public MyAdapter(List<Object> context, Context parentItemList) {
//        super(context, parentItemList);
//        inflater = LayoutInflater.from(context);
//    }

    @Override
    public TitleParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.list_parent, viewGroup, false);
        return new TitleParentViewHolder(view);
    }

    @Override
    public TitleChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.layout_child, viewGroup, false);
        return new TitleChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(TitleParentViewHolder titleParentViewHolder, int i, Object o) {
        TitleParent title = (TitleParent)o;
        titleParentViewHolder._textView1.setText(title.getTitle());
    }

    @Override
    public void onBindChildViewHolder(TitleChildViewHolder titleChildViewHolder, int i, Object o) {
        TitleChild title = (TitleChild)o;
        titleChildViewHolder.option1.setText(title.getOption1());
        titleChildViewHolder.option2.setText(title.getOption2());
    }
}
