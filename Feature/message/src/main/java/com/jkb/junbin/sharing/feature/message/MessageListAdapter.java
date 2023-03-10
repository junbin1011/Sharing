package com.jkb.junbin.sharing.feature.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jkb.junbin.sharing.feature.message.util.DateUtil;
import com.jkb.junbin.sharing.function.shell.interfaces.IFileStatistics;


import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {
    public List<Message> infoList = new ArrayList<>();
    public Context context;
    public LayoutInflater inflater;
    @Autowired
    public IFileStatistics iFileStatistics;

    public MessageListAdapter(List<Message> list, Context context) {
        this.context = context;
        this.infoList = list;
        inflater = LayoutInflater.from(context);
        ARouter.getInstance().inject(this);
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
        if(iFileStatistics != null) {
            holder.tvCount.setText("文件浏览量：" + iFileStatistics.getDownloadCount(infoList.get(position).id+""));
        }
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvSize;
        public TextView tvFileName;
        public TextView tvCount;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_content);
            tvSize = itemView.findViewById(R.id.tv_date);
            tvFileName = itemView.findViewById(R.id.tv_filename);
            tvCount = itemView.findViewById(R.id.tv_count);
        }
    }

}
