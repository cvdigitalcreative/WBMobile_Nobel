package digitalcreative.web.id.wbmobile_user.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.view.model.MateriKursus;
import digitalcreative.web.id.wbmobile_user.view.model.Modul;

/**
 * Created by User on 07/03/2019.
 */

public class RecyclerViewAdapter_Modul extends RecyclerView.Adapter<RecyclerViewAdapter_Modul.ViewHolder> {
    List<Modul> mData;
    private RecyclerView_Adapter.ViewHolder holder;
    private int i;

    public RecyclerViewAdapter_Modul(List<Modul> mData){
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_Modul.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_modul, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_Modul.ViewHolder viewHolder, int i) {
        Modul mdl = mData.get(i);
        viewHolder.tv_namaModul.setText(mdl.getNama_modul());
        viewHolder.tv_temaMateri.setText(mdl.getTema_materi());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_namaModul, tv_temaMateri;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_namaModul = itemView.findViewById(R.id.nama_modul);
            tv_temaMateri = itemView.findViewById(R.id.tema_materi);
        }
    }
}
