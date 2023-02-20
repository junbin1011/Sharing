package com.jkb.junbin.sharing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jkb.junbin.sharing.model.Message;
import com.jkb.junbin.sharing.util.DateUtil;
import com.sharing.R;



import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {
    public List<Message> infoList = new ArrayList<>();
    public Context context;
    public LayoutInflater inflater;

    public MessageListAdapter(List<Message> list, Context context) {
        this.context = context;
        this.infoList = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.message_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvName.setText(infoList.get(position).content);
        holder.tvFileName.setText(infoList.get(position).fileName);
        holder.tvSize.setText(DateUtil.getDateToString(infoList.get(position).date));
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvSize;
        public TextView tvFileName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_content);
            tvSize = itemView.findViewById(R.id.tv_date);
            tvFileName = itemView.findViewById(R.id.tv_filename);
        }
    }

}
