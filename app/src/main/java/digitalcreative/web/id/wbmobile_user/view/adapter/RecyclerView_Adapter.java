package digitalcreative.web.id.wbmobile_user.view.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import digitalcreative.web.id.wbmobile_user.R;

public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.ViewHolder> {
    List<String> mData;
    private int pos;
    View view;
    List<LinearLayout>itemViewList = new ArrayList<>();
    public RecyclerView_Adapter(ArrayList<String> batch) {
        mData = batch;
    }

    @NonNull
    @Override
    public RecyclerView_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_batch, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView_Adapter.ViewHolder holder, final int i) {
        String batch = mData.get(i);
        if (!itemViewList.contains(holder.linearLayoutCardview)) {
            itemViewList.add(holder.linearLayoutCardview);
        }
        holder.tv_batch.setText(batch);
        holder.linearLayoutCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  for (LinearLayout linearLayout : itemViewList){
                      linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                  }
                  holder.linearLayoutCardview.setBackgroundColor(Color.parseColor("#1abc9c"));
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_batch, tv_id_batch;
        LinearLayout linearLayoutCardview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_batch = itemView.findViewById(R.id.batch2);
            tv_id_batch = itemView.findViewById(R.id.idbatch);
            linearLayoutCardview = itemView.findViewById(R.id.linearList);
        }
    }
}
