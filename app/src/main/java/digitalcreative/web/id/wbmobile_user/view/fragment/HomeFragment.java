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

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.view.kelas.MateriKursus;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    CarouselView carouselView;
    TextView tv_paket, tv_batch, tv_tanggal;
    DatabaseReference mDatabase;


    int[] sampleImages = {R.drawable.google, R.drawable.digital_creative, R.drawable.azura_design_new_website1};

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
        initAction();

        return view;
    }

    private void init(View view){
        tv_paket = view.findViewById(R.id.tv_paket);
        tv_batch = view.findViewById(R.id.tv_batch);
        tv_tanggal = view.findViewById(R.id.tv_tanggal);
    }

    private void receiveID(){
        String user = "8I0l8Hb9JuO8Mc9h0JQlX1t9TVN2";
        mDatabase = FirebaseDatabase.getInstance().getReference().child("nobel").child(user).child("kursus");
    }

    private void initAction(){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String paket = null, batch = null, tanggal = null;
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    paket = dataSnapshot1.getKey();
                    batch = dataSnapshot1.child("informasi_dasar").child("batch").getValue().toString();
                    tanggal = dataSnapshot1.child("informasi_dasar").child("tanggal_batch").getValue().toString();
                }
//                batch = dataSnapshot.child(paket).child("informasi_dasar").child("batch").getValue().toString();
//                tanggal = dataSnapshot.child(paket).child("informasi_dasar").child("tanggal_batch").getValue().toString();
                MateriKursus mk = new MateriKursus();
                mk.setNamaPaket(paket);
                tv_paket.setText(mk.getNamaPaket());
                tv_batch.setText("Batch "+batch);
                tv_tanggal.setText(tanggal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
