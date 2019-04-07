package digitalcreative.web.id.wbmobile_user.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.model.Modul;

public class SplashScreen extends AppCompatActivity {
    DatabaseReference mDatabaseKursusNobel, mDatabaseKursus, mDatabaseBatch, mDatabaseProfile;
    ArrayList<List> homeList = new ArrayList<>();
    ArrayList<List> listJudul, listJudulModul;
    ArrayList<String> batch = new ArrayList<>();
    ArrayList<String> judul = new ArrayList<>();
    ArrayList<List> detail  = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();
    ArrayList<List> listDetailPaket;
    List<List> multiList;
    ArrayList<String> listProfile = new ArrayList<>();
    String user, cekPaket;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    TextView tv_connection;
    ImageView iv_logo, iv_reload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        ProgressDialog progressDialog = ProgressDialog.show(SplashScreen.this, "", "Please Wait", true, false);
//        receiveID();
//        connectToFirebase();
//        initListJudul();
//        initActionHome();
//        initActionBatch();
//        initActionSpinner();
//        initActionSpinnerModul();
//        initShowProfile();
//        progressDialog.dismiss();
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
                initListJudul();
                initActionHome();
                initActionBatch();
                initActionSpinner();
                initActionSpinnerModul();
                initShowProfile();
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
        saveString(user, "ID_User");
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
    }

    private void initActionHome(){
        mDatabaseKursusNobel.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String paket = "", batch = "", tanggal = "", judul="";
                List<String> temp = new ArrayList<>();

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    batch = dataSnapshot1.getKey();
                    for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                        paket = dataSnapshot2.getKey();
                        tanggal = dataSnapshot2.child("informasi_dasar").child("tanggal_batch").getValue().toString();

                        for(int i=0; i<listJudul.size(); i++){
                            if(listJudul.get(i).get(0).toString().equals(paket)){
                                judul = listJudul.get(i).get(1).toString();
                            }
                        }

                        temp.add(judul);
                        temp.add(batch);
                        temp.add(tanggal);
                        homeList.add(temp);
                        cekPaket(paket);
                    }

                }
                saveArrayList(homeList, "List_Home");
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
                saveList(listJudul, "List_Judul_Real");
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
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    batch.add(dataSnapshot1.getKey());
                }
                saveArrayListString(batch, "List_Batch");
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
                saveArrayListString(judul, "List_Judul");
                saveArrayList(detail, "List_Detail");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    public void initActionSpinnerModul(){
//        mDatabaseKursus.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String paket = "";
//                multiList = new ArrayList<>();
//                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                    listDetailPaket = new ArrayList<>();
//                    paket = dataSnapshot1.child("judul").getValue().toString();
//                    list.add(paket);
//                    listJudulModul = new ArrayList<>();
//                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.child("modul").getChildren()){
//                        String nama_modul = dataSnapshot2.child("nama_modul").getValue().toString();
//                        String status = dataSnapshot2.child("status").getValue().toString();
//                        String tema_materi = dataSnapshot2.child("tema_materi").getValue().toString();
//                        String url_modul = dataSnapshot2.child("url_modul").getValue().toString();
//                        listDetailPaket.add(new Modul( nama_modul, status, tema_materi, url_modul));
//                    }
//                    listJudulModul.add(listDetailPaket);
//                    multiList.add(listJudulModul);
//                }
//                saveList(multiList, "List_Multi_Modul");
//                saveList(listJudulModul, "List_Judul_Modul");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void initActionSpinnerModul(){
        mDatabaseKursus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

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
                saveList(listJudulModul, "List_Judul_Modul");
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
                    String key = dataSnapshot1.getKey().toString();
                    String value = dataSnapshot.child(key).getValue().toString();
                    listProfile.add(value);
                }
                saveArrayListString(listProfile, "List_Profile");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void goToBaseActivity(){
        Intent intent = new Intent(SplashScreen.this, BaseActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void saveArrayListString(ArrayList<String> list, String key){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public void saveArrayList(ArrayList<List> list, String key){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public void saveList(List<List> list, String key){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public void saveString(String str, String key){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        editor.putString(key, str);
        editor.apply();     // This line is IMPORTANT !!!
    }

}

