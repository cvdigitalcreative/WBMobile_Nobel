package digitalcreative.web.id.wbmobile_user.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.view.adapter.RecyclerView_Adapter;
import digitalcreative.web.id.wbmobile_user.view.model.MateriKursus;
/**
 * A simple {@link Fragment} subclass.
 */
public class PaketFragment extends Fragment {

    DatabaseReference mDatabase, mDatabaseSpinner, mDatabasePaket;
    RecyclerView recyclerView;
    ArrayList<String> batch = new ArrayList<>();
    RecyclerView_Adapter adapter;
    final List<MateriKursus> list = new ArrayList<>();
    List<String> judul = new ArrayList<>();
    Spinner spinner;
    TextView tvDeskripsi, tvLama, tvDurasi, tvHarga;
    String deskripsi, lama_pertemuan, durasi_pertemuan, harga;

    public PaketFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paket, container, false);

        init(view);
        initActionBatch();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new RecyclerView_Adapter(batch);

        initActionSpinner();
        selectedItemSpinner();

        return view;
    }

    public void init(View view){
        recyclerView = view.findViewById(R.id.rv_batch);
        spinner = view.findViewById(R.id.spinner_paket);
        tvDeskripsi = view.findViewById(R.id.deskripsi_paket);
        tvDurasi = view.findViewById(R.id.durasi_pertemuan);
        tvLama = view.findViewById(R.id.lama_pertemuan);
        tvHarga = view.findViewById(R.id.harga_paket);
    }

    public void initActionBatch(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("batch");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    batch.add("Batch "+dataSnapshot1.getKey());
                }
                recyclerView.setAdapter(adapter);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, batch);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void initActionSpinner(){
        mDatabaseSpinner = FirebaseDatabase.getInstance().getReference().child("materi_kursus");
        mDatabaseSpinner.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String paket = "";
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    paket = dataSnapshot1.child("judul").getValue().toString();
//                    deskripsi = dataSnapshot1.child("deskripsi").getValue().toString();
//                    lama_pertemuan = dataSnapshot1.child("lama_pertemuan").getValue().toString();
//                    durasi_pertemuan = dataSnapshot1.child("durasi_pertemuan").getValue().toString();
//                    harga = dataSnapshot1.child("harga").getValue().toString();

//                    mk.setDeskripsi(deskripsi);
//                    mk.setDurasi(durasi_pertemuan);
//                    mk.setHarga(harga);
//                    mk.setLama_pertemuan(lama_pertemuan);

                    judul.add(paket);
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, judul);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spinner.setAdapter(dataAdapter);

                MateriKursus model =  new MateriKursus();

//                System.out.println("ini adalah = "+list);

//                tvDeskripsi.setText(model.getDeskripsi());
//                tvDurasi.setText( +" jam");
//                tvLama.setText(lama + " hari");
//                tvHarga.setText("Rp "+ harga + ",-");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void selectedItemSpinner(){
        //final MateriKursus mkursus = new MateriKursus();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(parent.getContext(),
//                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
//                        Toast.LENGTH_SHORT).show();
//                mkursus.setNamaPaketReal(parent.getItemAtPosition(position).toString());

                String namaPaketReal = parent.getItemAtPosition(position).toString();
                System.out.println("test = "+namaPaketReal);
                mDatabasePaket = mDatabaseSpinner.child(namaPaketReal);
                mDatabasePaket.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            String deskripsi = dataSnapshot1.child("deskripsi").getValue().toString();
                            String durasi = dataSnapshot1.child("durasi_pertemuan").getValue().toString();
                            String harga = dataSnapshot1.child("harga").getValue().toString();
                            String lama = dataSnapshot1.child("lama_pertemuan").getValue().toString();

                            System.out.println("string = "+deskripsi );
                            tvDeskripsi.setText(deskripsi);
                            tvDurasi.setText( durasi +" jam");
                            tvLama.setText(lama + " hari");
                            tvHarga.setText("Rp "+ harga + ",-");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }




}
