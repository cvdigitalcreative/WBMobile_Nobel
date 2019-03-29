package digitalcreative.web.id.wbmobile_user.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.model.MateriKursus;
import digitalcreative.web.id.wbmobile_user.model.Modul;

/**
 * Created by User on 07/03/2019.
 */

public class RecyclerViewAdapter_Modul extends RecyclerView.Adapter<RecyclerViewAdapter_Modul.MyHolder> {
    List<Modul> mData;
    private RecyclerView_Adapter.ViewHolder holder;

    public RecyclerViewAdapter_Modul(List<Modul> mData){
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_Modul.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_modul, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Modul mdl = mData.get(position);
        holder.tv_namaModul.setText(mdl.getNama_modul());
        holder.tv_temaMateri.setText(mdl.getTema_materi());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_namaModul, tv_temaMateri;
        ImageView iv_download;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_namaModul = itemView.findViewById(R.id.nama_modul);
            tv_temaMateri = itemView.findViewById(R.id.tema_materi);
            iv_download = itemView.findViewById(R.id.iv_download);
        }
    }
}
