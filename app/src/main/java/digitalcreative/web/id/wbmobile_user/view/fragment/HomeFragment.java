package digitalcreative.web.id.wbmobile_user.view.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.model.DataSplashScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    TextView tv_paket, tv_batch, tv_tanggal;
    ArrayList<List> homelist = new ArrayList<>();
    ArrayList<List> listKonfirmasi;
    LinearLayout ll_konfirmasi;
    String no_batch, nama_paket;
    DatabaseReference mDatabaseKursusNobel;
    String ID_User;
    Button btnKonfirmasi;
    CarouselView carouselView;
    private int[] imagePromo = new int[] {R.drawable.digital_creative, R.drawable.google};
    private String[] titleImagePromo = new String[] {"dc", "google"};

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        initData();
        setCarouselView(carouselView);
        connectToFirebase();
        initAction();
        cekKonfirmasiPembayaran();
        setBtnKonfirmasi(btnKonfirmasi);
        return view;
    }

    private void setBtnKonfirmasi(Button btnKonfirmasi) {
        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPaymentConfirmation();
            }
        });
    }

    private void goToPaymentConfirmation() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        KonfirmasiFragment konfirmasiFragment = new KonfirmasiFragment();
        fragmentTransaction.replace(R.id.container_fragment, konfirmasiFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void init(View view){
        carouselView = view.findViewById(R.id.carousel);
        tv_paket = view.findViewById(R.id.tv_paket);
        tv_batch = view.findViewById(R.id.tv_batch);
        tv_tanggal = view.findViewById(R.id.tv_tanggal);
        ll_konfirmasi = view.findViewById(R.id.ll_konfirmasi);
        btnKonfirmasi = view.findViewById(R.id.btn_konfirmasi);
    }

    private void initData(){
        DataSplashScreen data = new DataSplashScreen(getActivity());
        homelist = data.getArrayList("List_Home");
        ID_User = data.getString("ID_User");
    }

    private void initAction(){
        String nama_paket = "", no_batch = "", tanggal_batch = "";
        for (int i=0; i<homelist.size(); i++){
            if(homelist.get(i).get(3).equals("Sedang Berlangsung")){
                nama_paket = homelist.get(i).get(0).toString();
                no_batch = homelist.get(i).get(1).toString();
                tanggal_batch = homelist.get(i).get(2).toString();
            }
        }

        tv_paket.setText(nama_paket);
        tv_batch.setText("Batch "+ no_batch);
        tv_tanggal.setText(tanggal_batch);
    }

    private void setCarouselView(CarouselView carouselView){
        carouselView.setPageCount(imagePromo.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(imagePromo[position]);
            }
        });
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
    }

    private void connectToFirebase(){
        mDatabaseKursusNobel = FirebaseDatabase.getInstance().getReference().child("nobel").child(ID_User).child("kursus");
    }

    private void cekKonfirmasi(){
        for (int i=0; i<listKonfirmasi.size(); i++){
            if(listKonfirmasi.get(i).get(2).equals("Belum")){
                no_batch = listKonfirmasi.get(i).get(0).toString();
                nama_paket = listKonfirmasi.get(i).get(1).toString();
            }
        }
        System.out.println(listKonfirmasi);
        tampilLinearLayout();
    }

    private void tampilLinearLayout(){
        if(no_batch != null && nama_paket != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ll_konfirmasi.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void cekKonfirmasiPembayaran(){
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
                cekKonfirmasi();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}