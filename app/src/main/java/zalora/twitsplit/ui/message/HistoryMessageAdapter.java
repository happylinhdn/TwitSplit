package zalora.twitsplit.ui.message;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zalora.twitsplit.R;
import zalora.twitsplit.data.room.entity.MessageModel;

public class HistoryMessageAdapter extends RecyclerView.Adapter<HistoryMessageAdapter.MyViewHolder> {
    private List<MessageModel> listData;
    private Context context;

    public HistoryMessageAdapter(Context context) {
        this.context = context;
        this.listData = new ArrayList<>();
    }

    public void initData(List<MessageModel> listData) {
        this.listData.clear();
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        MessageModel item = listData.get(position);
        holder.bindView(item);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtMessage;
        TextView txtTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtMessage = itemView.findViewById(R.id.txt_message);
            txtTime = itemView.findViewById(R.id.tv_time);
        }

        void bindView(MessageModel model) {
            txtMessage.setText(model.getMessage());
            if (DateUtils.isToday(model.getCreated())) {
                txtTime.setText(model.getShortStringCreated());
            } else {
                txtTime.setText(model.getLongStringCreated());
            }
        }
    }
}
