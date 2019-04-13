package digitalcreative.web.id.wbmobile_user.view.activity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.model.DataSplashScreen;
import digitalcreative.web.id.wbmobile_user.model.Modul;

public class SplashScreen extends AppCompatActivity {
    DatabaseReference mDatabaseKursusNobel, mDatabaseKursus, mDatabaseBatch, mDatabaseProfile, mDatabaseImage;
    ArrayList<List> listJudul, listJudulModul, listBatch, homeList, listKonfirmasi;
    ArrayList<String> batch, judul, list;
    ArrayList<String> imagePromo = new ArrayList<>();
    ArrayList<List> detail;
    ArrayList<List> listDetailPaket;
    ArrayList<String> listProfile = new ArrayList<>();
    String user, cekPaket;
    TextView tv_connection;
    ImageView iv_logo, iv_reload;
    DataSplashScreen dataSplashScreen = new DataSplashScreen(this);
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
        clickReload();
        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();
    }

    private class LogoLauncher extends Thread{
        public void run(){
            try{
                receiveID();
                connectToFirebase();
                imagePromo();
                initListJudul();
                initActionHome();
                initActionBatch();
                initActionSpinner();
                initActionSpinnerModul();
                initShowProfile();
                cekKonfirmasi();
          } catch (Exception e){
                Toast.makeText(SplashScreen.this, "Tidak dapat mengakses data", Toast.LENGTH_LONG).show();
            }

            try{
                sleep(5000);
            }
            catch (Exception e){
                Toast.makeText(SplashScreen.this, "Tidak dapat mengakses data", Toast.LENGTH_LONG).show();
            }
            cekPaket(cekPaket);
        }

    }

    private void init(){
        tv_connection = findViewById(R.id.tv_text);
        iv_logo = findViewById(R.id.iv_splashscreen);
        iv_reload = findViewById(R.id.iv_reload);
    }

    private void receiveID(){
        user = "8I0l8Hb9JuO8Mc9h0JQlX1t9TVN2";
        dataSplashScreen.saveString(user, "ID_User");
    }

    private void cekPaket(String cekPaket){
        if(cekPaket == null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    iv_logo.setVisibility(View.GONE);
                    tv_connection.setVisibility(View.VISIBLE);
                    iv_reload.setVisibility(View.VISIBLE);
                }
            });
        }
        else
            goToBaseActivity();
    }

    private void clickReload(){
        iv_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                LogoLauncher logoLauncher = new LogoLauncher();
                logoLauncher.start();
            }
        });
    }

    private void connectToFirebase(){
        mDatabaseKursusNobel = FirebaseDatabase.getInstance().getReference().child("nobel").child(user).child("kursus");
        mDatabaseKursus = FirebaseDatabase.getInstance().getReference().child("materi_kursus");
        mDatabaseBatch = FirebaseDatabase.getInstance().getReference().child("batch");
        mDatabaseProfile = FirebaseDatabase.getInstance().getReference().child("nobel").child(user).child("profile_nobel");
        mDatabaseImage = FirebaseDatabase.getInstance().getReference().child("promo");
    }

    private void initActionHome(){
        mDatabaseKursusNobel.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String paket = "", batch = "", tanggal = "", judul="", status="";
                homeList = new ArrayList<>();

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    batch = dataSnapshot1.getKey();
                    for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                        List<String> temp = new ArrayList<>();
                        paket = dataSnapshot2.getKey();
                        tanggal = dataSnapshot2.child("informasi_dasar").child("tanggal_batch").getValue().toString();
                        status = dataSnapshot2.child("informasi_dasar").child("status").getValue().toString();
                        for(int i=0; i<listJudul.size(); i++){
                            if(listJudul.get(i).get(0).toString().equals(paket)){
                                judul = listJudul.get(i).get(1).toString();
                            }
                        }
                        temp.add(judul);
                        temp.add(batch);
                        temp.add(tanggal);
                        temp.add(status);
                        homeList.add(temp);
                        cekPaket(paket);
                    }
//                    System.out.println(homeList);
                }
                dataSplashScreen.saveArrayList(homeList, "List_Home");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initListJudul(){
        mDatabaseKursus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listJudul = new ArrayList<>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ArrayList<String> temp = new ArrayList<>();
                    String key = dataSnapshot1.getKey();
                    String judul = dataSnapshot1.child("judul").getValue().toString();
                    temp.add(key);
                    temp.add(judul);
                    listJudul.add(temp);
                };
                dataSplashScreen.saveArrayList(listJudul, "List_Judul_Real");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void initActionBatch(){
        mDatabaseBatch.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listBatch = new ArrayList<>();
                batch = new ArrayList<>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ArrayList<String> temp = new ArrayList<>();
                    String no_batch = dataSnapshot1.getKey();
                    String tgl = dataSnapshot1.child("tanggal").getValue().toString();
                    batch.add(no_batch);
                    temp.add(no_batch);
                    temp.add(tgl);
                    listBatch.add(temp);
                }
                dataSplashScreen.saveArrayListString(batch, "List_Batch");
                dataSplashScreen.saveArrayList(listBatch, "List_Batch_Lengkap");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void initActionSpinner(){
        mDatabaseKursus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String paket = "";
                judul = new ArrayList<>();
                detail = new ArrayList<>();

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    List<String> temp = new ArrayList<>();
                    paket = dataSnapshot1.child("judul").getValue().toString();
                    temp.add(dataSnapshot1.child("deskripsi").getValue().toString());
                    temp.add(dataSnapshot1.child("durasi_pertemuan").getValue().toString());
                    temp.add(dataSnapshot1.child("harga").getValue().toString());
                    temp.add(dataSnapshot1.child("lama_pertemuan").getValue().toString());
                    judul.add(paket);
                    detail.add(temp);
                }
                dataSplashScreen.saveArrayListString(judul, "List_Judul");
                dataSplashScreen.saveArrayList(detail, "List_Detail");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void initActionSpinnerModul(){
        mDatabaseKursus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                listJudulModul = new ArrayList<>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    listDetailPaket = new ArrayList<>();
                    String paket = dataSnapshot1.child("judul").getValue().toString();
                    list.add(paket);
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.child("modul").getChildren()){
                        ArrayList<String> temp = new ArrayList<>();
                        String nama_modul = dataSnapshot2.child("nama_modul").getValue().toString();
                        String status = dataSnapshot2.child("status").getValue().toString();
                        String tema_materi = dataSnapshot2.child("tema_materi").getValue().toString();
                        String url_modul = dataSnapshot2.child("url_modul").getValue().toString();
                        temp.add(nama_modul);
                        temp.add(status);
                        temp.add(tema_materi);
                        temp.add(url_modul);
                        listDetailPaket.add(temp);
                    }
                    listJudulModul.add(listDetailPaket);

                }
                dataSplashScreen.saveArrayList(listJudulModul, "List_Judul_Modul");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initShowProfile(){
        mDatabaseProfile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String key = dataSnapshot1.getKey();
                    String value = dataSnapshot.child(key).getValue().toString();
                    listProfile.add(value);
                }
                dataSplashScreen.saveArrayListString(listProfile, "List_Profile");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void cekKonfirmasi(){
        mDatabaseKursusNobel.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key_batch = "", key_paket = "", konfirmasi = "";
                listKonfirmasi = new ArrayList<>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    key_batch = dataSnapshot1.getKey();
                    for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                        ArrayList<String> temp = new ArrayList<>();
                        key_paket = dataSnapshot2.getKey();
                        konfirmasi = dataSnapshot2.child("informasi_dasar").child("konfirmasi").getValue().toString();

                        temp.add(key_batch);
                        temp.add(key_paket);
                        temp.add(konfirmasi);
                        listKonfirmasi.add(temp);
                    }
                }
                dataSplashScreen.saveArrayList(listKonfirmasi, "List_Konfirmasi");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void imagePromo(){
        mDatabaseImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String url_foto1 = dataSnapshot.child("url_foto1").getValue().toString();
                String url_foto2 = dataSnapshot.child("url_foto2").getValue().toString();
                String url_foto3 = dataSnapshot.child("url_foto3").getValue().toString();

                tryDownloading(url_foto1, "Foto1.jpg");
                tryDownloading(url_foto2, "Foto2.jpg");
                tryDownloading(url_foto3, "Foto3.jpg");

                dataSplashScreen.saveArrayListString(imagePromo, "Image_Promo");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void tryDownloading(String url, final String nama_file){
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReferenceFromUrl(url);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                createDirectoryAndSaveFile(nama_file, url);
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
        request.setDestinationInExternalFilesDir(context, destinantion_directory, file_name);
        System.out.println(destinantion_directory);
        downloadManager.enqueue(request);
    }

    private void createDirectoryAndSaveFile(String file_name, String url) {

        File direct = new File("/Download");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        File file = new File(new File("/Download"), file_name);
        if (file.exists()) {
            file.delete();
        }

        imagePromo.add(file.toString());

        DownloadManager downloadManager = (DownloadManager) SplashScreen.this.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDestinationInExternalFilesDir(SplashScreen.this, file.toString(), file_name);
        downloadManager.enqueue(request);

//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public void goToBaseActivity(){
        Intent intent = new Intent(SplashScreen.this, BaseActivity.class);
        startActivity(intent);
        this.finish();
    }
}

