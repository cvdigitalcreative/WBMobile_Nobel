package digitalcreative.web.id.wbmobile_user.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import digitalcreative.web.id.wbmobile_user.R;

public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.ViewHolder> {
    List<String> mData;

    public RecyclerView_Adapter(ArrayList<String> batch) {
        mData = batch;
    }

    @NonNull
    @Override
    public RecyclerView_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_batch, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView_Adapter.ViewHolder holder, int i) {
        String batch = mData.get(i);
        holder.tv_batch.setText(batch);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_batch;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_batch = itemView.findViewById(R.id.batch2);
        }
    }
}
