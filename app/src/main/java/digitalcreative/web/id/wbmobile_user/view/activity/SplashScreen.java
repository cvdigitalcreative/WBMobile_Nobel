package digitalcreative.web.id.wbmobile_user.view.activity;

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
    String user;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        receiveID();
        connectToFirebase();
        initListJudul();
        initActionHome();
        initActionBatch();
        initActionSpinner();
        initActionSpinnerModul();
        initShowProfile();
        goToBaseActivity();
    }

    private void receiveID(){
        user = "8I0l8Hb9JuO8Mc9h0JQlX1t9TVN2";
        saveString(user, "ID_User");
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
                    paket = dataSnapshot1.getKey();
                    batch = dataSnapshot1.child("informasi_dasar").child("batch").getValue().toString();
                    tanggal = dataSnapshot1.child("informasi_dasar").child("tanggal_batch").getValue().toString();
                    for(int i=0; i<listJudul.size(); i++){
                        if(listJudul.get(i).get(0).toString().equals(paket)){
                            judul = listJudul.get(i).get(1).toString();
                        }
                    }
                    temp.add(judul);
                    temp.add(batch);
                    temp.add(tanggal);
                    homeList.add(temp);
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
                String paket = "";

                listJudulModul = new ArrayList<>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    listDetailPaket = new ArrayList<>();
                    paket = dataSnapshot1.child("judul").getValue().toString();
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

    public void goToBaseActivity(){
        Intent intent = new Intent(SplashScreen.this, BaseActivity.class);
        startActivity(intent);
        this.finish();
    }

}

