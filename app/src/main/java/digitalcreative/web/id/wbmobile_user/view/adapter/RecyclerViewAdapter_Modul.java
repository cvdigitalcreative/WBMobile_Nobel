package digitalcreative.web.id.wbmobile_user.view.adapter;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.model.MateriKursus;
import digitalcreative.web.id.wbmobile_user.model.Modul;
import digitalcreative.web.id.wbmobile_user.view.activity.BaseActivity;
import digitalcreative.web.id.wbmobile_user.view.fragment.ModulFragment;

/**
 * Created by User on 07/03/2019.
 */

public class RecyclerViewAdapter_Modul extends RecyclerView.Adapter<RecyclerViewAdapter_Modul.MyHolder>{
    List<Modul> mData;
    Context context;
    private static final int PERMISSION_STORAGE_CODE = 1000;
    String url;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;

    public RecyclerViewAdapter_Modul(List<Modul> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_Modul.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_modul, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder,int position) {
        final Modul mdl = mData.get(position);
        holder.tv_namaModul.setText(mdl.getNama_modul());
        holder.tv_temaMateri.setText(mdl.getTema_materi());
        holder.iv_download_modul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = mdl.getUrl_modul();
                tryDownloading(url, mdl.getNama_modul());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void tryDownloading(String url, final String nama_file){
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReferenceFromUrl(url);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downloadFile(context, nama_file, Environment.DIRECTORY_DOWNLOADS, url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void downloadFile(Context context, String file_name, String destinantion_directory, String url){
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinantion_directory, file_name);
        downloadManager.enqueue(request);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_namaModul, tv_temaMateri;
        ImageView iv_download_modul;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_namaModul = itemView.findViewById(R.id.nama_modul);
            tv_temaMateri = itemView.findViewById(R.id.tema_materi);
            iv_download_modul = itemView.findViewById(R.id.iv_download_modul);
        }
    }
}
