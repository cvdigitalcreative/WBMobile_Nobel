package digitalcreative.web.id.wbmobile_user.view.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.model.DataSplashScreen;
import digitalcreative.web.id.wbmobile_user.model.Pemesanan;
import digitalcreative.web.id.wbmobile_user.view.activity.BaseActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class FormOrderDialog extends AppCompatDialogFragment {
    private Button btnCancel, btnYes;
    private TextView tv_nama, tv_paket, tv_batch, tv_deskripsi, tv_harga;
    private String nama, paket, batch, deskripsi, harga, ID_User, paket_real, tanggal_batch;
    private DatabaseReference dbKursusNobel, mDatabaseKursusNobel;
    private ArrayList<List> listJudul, listBatchLengkap, listKonfirmasi;
    private DataSplashScreen dataSplashScreen;

    public FormOrderDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_form_order, container, false);
        getDialog().setCanceledOnTouchOutside(false);
        dataSplashScreen = new DataSplashScreen(getActivity());
        initData();
        connectToFirebase();
        init(view);
        initActionForm();
        setCancel();
        setYes();

        return view;
    }

    private void init(View view){
        tv_nama = view.findViewById(R.id.tv_nama);
        tv_paket = view.findViewById(R.id.tv_paket);
        tv_batch = view.findViewById(R.id.tv_batch);
        tv_deskripsi = view.findViewById(R.id.tv_deskripsi);
        tv_harga = view.findViewById(R.id.tv_harga_paket);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnYes = view.findViewById(R.id.btn_yes);
    }

    private void initData(){
        nama = dataSplashScreen.getString("Paket_Nama");
        paket = dataSplashScreen.getString("Paket_Nama_Paket");
        batch = dataSplashScreen.getString("Paket_Batch");
        deskripsi = dataSplashScreen.getString("Paket_Deskripsi");
        harga = dataSplashScreen.getString("Paket_Harga");
        ID_User = dataSplashScreen.getString("ID_User");

        listJudul = dataSplashScreen.getArrayList("List_Judul_Real");
        for(int i=0; i<listJudul.size(); i++){
            if(listJudul.get(i).get(1).toString().equals(paket))
                paket_real = listJudul.get(i).get(0).toString();
        }

        listBatchLengkap = dataSplashScreen.getArrayList("List_Batch_Lengkap");
        for(int i=0; i<listBatchLengkap.size(); i++){
            if(listBatchLengkap.get(i).get(0).equals(batch))
                tanggal_batch = listBatchLengkap.get(i).get(1).toString();
        }
    }

    private void connectToFirebase(){
        dbKursusNobel = FirebaseDatabase.getInstance().getReference().child("nobel").child(ID_User).child("kursus")
        .child(batch).child(paket_real).child("informasi_dasar");
        mDatabaseKursusNobel = FirebaseDatabase.getInstance().getReference().child("nobel").child(ID_User).child("kursus");
    }

    private void initActionForm(){
        tv_nama.setText(nama);
        tv_paket.setText(paket);
        tv_batch.setText(batch);
        tv_deskripsi.setText(deskripsi);
        tv_harga.setText(harga);
    }

    private void setCancel(){
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    private void setYes(){
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Loading");
                progressDialog.show();
                sendData();
                cekKonfirmasi();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        System.out.println("masuk");
                        Toast.makeText(getActivity(), "Pemesanan berhasil. Silahkan Konfirmasi !", Toast.LENGTH_LONG).show();
                        getDialog().dismiss();
                        progressDialog.dismiss();
                        goToBaseActivity();
                    }
                }, 2000);
            }
        });
    }

    private void sendData(){
        Pemesanan pemesanan = new Pemesanan();
        pemesanan.setHarga(harga);
        pemesanan.setKonfirmasi("Belum");
        pemesanan.setTanggal_batch(tanggal_batch);
        pemesanan.setStatus("Belum Berlangsung");
        dbKursusNobel.setValue(pemesanan);
    }

    private void goToBaseActivity(){
        Intent intent = new Intent(getActivity(), BaseActivity.class);
        startActivity(intent);
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
}
