package digitalcreative.web.id.wbmobile_user.view.activity;

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
    List<String> judul = new ArrayList<>();
    List<List> detail  = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();
    List<Modul> listDetailPaket;
    ArrayList<List> multiList;
    ArrayList<String> listProfile = new ArrayList<>();
    String user;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        receiveID();
        connectToFirebase();
        initListJudul();
        initAction();
        initActionBatch();
        initActionSpinner();
        initActionSpinnerModul();

    }

    private void receiveID(){
        user = "8I0l8Hb9JuO8Mc9h0JQlX1t9TVN2";
    }

    private void connectToFirebase(){
        mDatabaseKursusNobel = FirebaseDatabase.getInstance().getReference().child("nobel").child(user).child("kursus");
        mDatabaseKursus = FirebaseDatabase.getInstance().getReference().child("materi_kursus");
        mDatabaseBatch = FirebaseDatabase.getInstance().getReference().child("batch");
        mDatabaseProfile = FirebaseDatabase.getInstance().getReference().child("nobel").child(user).child("profile_nobel");
    }

    private void initAction(){
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
                System.out.println(homeList);
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
                }
                System.out.println(listJudul);
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
                System.out.println(batch);
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
                System.out.println(judul);
                System.out.println(detail);
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
                String paket = "";
                multiList = new ArrayList<>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    listDetailPaket = new ArrayList<>();
                    paket = dataSnapshot1.child("judul").getValue().toString();
                    list.add(paket);
                    listJudulModul = new ArrayList<>();
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.child("modul").getChildren()){
                        String nama_modul = dataSnapshot2.child("nama_modul").getValue().toString();
                        String status = dataSnapshot2.child("status").getValue().toString();
                        String tema_materi = dataSnapshot2.child("tema_materi").getValue().toString();
                        String url_modul = dataSnapshot2.child("url_modul").getValue().toString();
                        listDetailPaket.add(new Modul(nama_modul, status, tema_materi, url_modul));
                    }
                    listJudulModul.add(listDetailPaket);
                    multiList.add(listJudul);
                }
                System.out.println(multiList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showProfile(){
        mDatabaseProfile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String key = dataSnapshot1.getKey().toString();
                    String value = dataSnapshot.child(key).getValue().toString();
                    listProfile.add(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

}

