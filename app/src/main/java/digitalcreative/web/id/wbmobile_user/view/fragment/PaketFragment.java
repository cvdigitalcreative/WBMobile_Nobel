package digitalcreative.web.id.wbmobile_user.view.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.model.DataSplashScreen;
import digitalcreative.web.id.wbmobile_user.view.adapter.RecyclerView_Adapter;
import digitalcreative.web.id.wbmobile_user.model.MateriKursus;
/**
 * A simple {@link Fragment} subclass.
 */
public class PaketFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> batch = new ArrayList<>();
    RecyclerView_Adapter adapter;
    ArrayList<String> judul = new ArrayList<>();
    ArrayList<List> detail, listJudul;
    ArrayList<List> listKonfirmasi = new ArrayList<>();
    Spinner spinner;
    TextView tvDeskripsi, tvLama, tvDurasi, tvHarga;
    Button btnPesan;
    ArrayList<String> listProfile = new ArrayList<>();
    String nama, harga, nama_paket, no_batch, deskripsi, ID_User;
    DatabaseReference mDatabaseKursusNobel;

    public PaketFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paket, container, false);
        init(view);
        initData();
        connectToFirebase();
        cekKonfirmasiPembayaran();
        initActionBatch();
        initActionSpinner();
        selectedItemSpinner();
        prosesPesan();
        return view;
    }

    private void connectToFirebase(){
        mDatabaseKursusNobel = FirebaseDatabase.getInstance().getReference().child("nobel").child(ID_User).child("kursus");
    }

    private void init(View view){
        recyclerView = view.findViewById(R.id.rv_batch);
        spinner = view.findViewById(R.id.spinner_paket);
        tvDeskripsi = view.findViewById(R.id.deskripsi_paket);
        tvDurasi = view.findViewById(R.id.durasi_pertemuan);
        tvLama = view.findViewById(R.id.lama_pertemuan);
        tvHarga = view.findViewById(R.id.harga_paket);
        btnPesan = view.findViewById(R.id.btn_pesan);
    }

    private void initData(){
        DataSplashScreen data = new DataSplashScreen(getActivity());
        judul = data.getArrayListString("List_Judul");
        detail = data.getArrayList("List_Detail");
        batch = data.getArrayListString("List_Batch");
        listProfile = data.getArrayListString("List_Profile");
//        listKonfirmasi = data.getArrayList("List_Konfirmasi");
        listJudul = data.getArrayList("List_Judul_Real");
        ID_User = data.getString("ID_User");
    }

    private void initActionBatch(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new RecyclerView_Adapter(batch);
        recyclerView.setAdapter(adapter);
    }

    private void initActionSpinner(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, judul);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(dataAdapter);
    }

    private void selectedItemSpinner(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvDeskripsi.setText(detail.get(position).get(0).toString());
                tvDurasi.setText(detail.get(position).get(1).toString() +" jam");
                tvHarga.setText(detail.get(position).get(2).toString());
                tvLama.setText(detail.get(position).get(3).toString() +" pertemuan");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void cekBundle(){
        Bundle b = this.getArguments();
        if(b != null){
            no_batch = b.getString("Batch_Terpilih");
        }
        System.out.println(no_batch);
    }

    private void prosesPesan(){
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag = cekKonfirmasiPesanan();
                if (flag == 1)
                    Toast.makeText(getActivity(), "Konfirmasi Terlebih Dahulu", Toast.LENGTH_LONG).show();
                else{
                    int pesan = cekDetailPesanan();
                    if(pesan == 0)
                        Toast.makeText(getActivity(), "Paket ini Telah Diambil", Toast.LENGTH_LONG).show();
                    else{
                        goToOrderDialog();
                    }
                }
            }
        });
    }

    private void getDetailPesanan(){
        nama = listProfile.get(2);
        harga = tvHarga.getText().toString();
        deskripsi = tvDeskripsi.getText().toString();
        nama_paket = spinner.getSelectedItem().toString();
        no_batch = "1";

//        RecyclerView_Adapter adapter = new RecyclerView_Adapter();
//        no_batch = adapter.getBatch();
//        System.out.println(no_batch);
    }

    private int cekDetailPesanan(){
        getDetailPesanan();

        String paket_real = "";
        String konfirmasi = "Sudah";
        int pesan = 1;
        for(int i=0; i<listJudul.size(); i++){
            if(listJudul.get(i).get(1).equals(nama_paket))
                paket_real = listJudul.get(i).get(0).toString();
        }
        System.out.println(paket_real);
        System.out.println(listKonfirmasi);
        for(int i=0; i<listKonfirmasi.size(); i++){
            if(no_batch.equals(listKonfirmasi.get(i).get(0).toString()) && paket_real.equals(listKonfirmasi.get(i).get(1).toString()) && konfirmasi.equals(listKonfirmasi.get(i).get(2).toString())){
                pesan = 0;
            }
        }
        return pesan;
    }

    private void goToOrderDialog() {
        saveString(nama, "Paket_Nama");
        saveString(harga, "Paket_Harga");
        saveString(deskripsi, "Paket_Deskripsi");
        saveString(nama_paket, "Paket_Nama_Paket");
        saveString(no_batch, "Paket_Batch");

        FragmentManager manager = getFragmentManager();
        FormOrderDialog dialogOrder = new FormOrderDialog();
        dialogOrder.show(manager, dialogOrder.getTag());
    }

    private int cekKonfirmasiPesanan(){
        int flag = 0;
        for(int i=0; i<listKonfirmasi.size(); i++){
            if(listKonfirmasi.get(i).get(2).equals("Belum"))
                flag = 1;
        }
        return flag;
    }

    private void cekKonfirmasiPembayaran(){
        mDatabaseKursusNobel.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key_batch = "", key_paket = "", konfirmasi = "";
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveString(String str, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, str);
        editor.apply();     // This line is IMPORTANT !!!
    }


}
