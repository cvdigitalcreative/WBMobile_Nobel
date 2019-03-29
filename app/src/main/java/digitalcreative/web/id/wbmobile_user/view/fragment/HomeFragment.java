package digitalcreative.web.id.wbmobile_user.view.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.synnapps.carouselview.CarouselView;

import java.util.ArrayList;
import java.util.List;

import digitalcreative.web.id.wbmobile_user.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    TextView tv_paket, tv_batch, tv_tanggal;
    DatabaseReference mDatabase, mDatabaseKursus;
    ArrayList<List> list = new ArrayList<>();
    String user;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        receiveID();
        connectToFirebase();
        initAction();
        return view;
    }

    private void init(View view){
        tv_paket = view.findViewById(R.id.tv_paket);
        tv_batch = view.findViewById(R.id.tv_batch);
        tv_tanggal = view.findViewById(R.id.tv_tanggal);
    }

    private void receiveID(){
        user = "8I0l8Hb9JuO8Mc9h0JQlX1t9TVN2";
    }

    private void connectToFirebase(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("nobel").child(user).child("kursus");
        mDatabaseKursus = FirebaseDatabase.getInstance().getReference().child("materi_kursus");
    }

    private void initAction(){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String paket = "", batch = "", tanggal = "";
                List<String> temp = new ArrayList<>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    paket = dataSnapshot1.getKey();
                    batch = dataSnapshot1.child("informasi_dasar").child("batch").getValue().toString();
                    tanggal = dataSnapshot1.child("informasi_dasar").child("tanggal_batch").getValue().toString();
                    temp.add(paket);
                    temp.add(batch);
                    temp.add(tanggal);
                    list.add(temp);
                }

                initActionReal(list.get(0).get(0).toString());
                tv_batch.setText("Batch "+list.get(0).get(1).toString());
                tv_tanggal.setText(list.get(0).get(2).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initActionReal(final String judul){
        mDatabaseKursus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String judulReal = dataSnapshot.child(judul).child("judul").getValue().toString();
                tv_paket.setText(judulReal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}