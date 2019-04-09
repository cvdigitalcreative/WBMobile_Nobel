package digitalcreative.web.id.wbmobile_user.view.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import digitalcreative.web.id.wbmobile_user.view.fragment.PaketFragment;

public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.ViewHolder> {
    List<String> mData;
    String batch;
    SharedPreferences.Editor sharedbatch;
    View view;
    Context context;
    List<LinearLayout>itemViewList = new ArrayList<>();

    public RecyclerView_Adapter(ArrayList<String> batch, Context context) {
        mData = batch;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_batch, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView_Adapter.ViewHolder holder, final int i) {
        final String inibatch = mData.get(i);
        if (!itemViewList.contains(holder.linearLayoutCardview)) {
            itemViewList.add(holder.linearLayoutCardview);
        }
        holder.tv_batch.setText(inibatch);
        markFirstBacth(holder);

        holder.linearLayoutCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (LinearLayout linearLayout : itemViewList){
                      linearLayout.setBackgroundResource(R.drawable.rect1);
                }
                holder.linearLayoutCardview.setBackgroundResource(R.drawable.rect5);
                batch = inibatch;
            }
        });
    }

    private void markFirstBacth(ViewHolder holder){
        for (int j = 0; j<itemViewList.size(); j++){
            if (j == 0) {
                holder.linearLayoutCardview.setBackgroundResource(R.drawable.rect5);
            } else {
                holder.linearLayoutCardview.setBackgroundResource(R.drawable.rect1);
            }
        }
    }

    public String getBatch(){
        return this.batch;
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

    private void getbatch(String getbatch){
        sharedbatch = context.getSharedPreferences("GET_BATCH", Context.MODE_PRIVATE).edit();
        sharedbatch.remove("getBatch").commit();
        sharedbatch.putString(getbatch, "getBatch");
        sharedbatch.apply();
    }
}
