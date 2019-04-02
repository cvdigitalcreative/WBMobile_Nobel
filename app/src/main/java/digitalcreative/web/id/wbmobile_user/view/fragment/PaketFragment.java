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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.view.adapter.RecyclerView_Adapter;
import digitalcreative.web.id.wbmobile_user.model.MateriKursus;
/**
 * A simple {@link Fragment} subclass.
 */
public class PaketFragment extends Fragment {

    DatabaseReference mDatabase, mDatabaseSpinner, mDatabasePaket;
    RecyclerView recyclerView;
    ArrayList<String> batch = new ArrayList<>();
    RecyclerView_Adapter adapter;
    List<String> judul = new ArrayList<>();
    List<List> detail  = new ArrayList<>();
    Spinner spinner;
    TextView tvDeskripsi, tvLama, tvDurasi, tvHarga;
    String deskripsi, lama_pertemuan, durasi_pertemuan, harga;
    Button btnPesan;

    public PaketFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paket, container, false);

        init(view);
//        initActionBatch();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new RecyclerView_Adapter(batch);

//        initActionSpinner();
        selectedItemSpinner();

        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoOrderDialog();

            }
        });

        return view;
    }

    public void init(View view){
        recyclerView = view.findViewById(R.id.rv_batch);
        spinner = view.findViewById(R.id.spinner_paket);
        tvDeskripsi = view.findViewById(R.id.deskripsi_paket);
        tvDurasi = view.findViewById(R.id.durasi_pertemuan);
        tvLama = view.findViewById(R.id.lama_pertemuan);
        tvHarga = view.findViewById(R.id.harga_paket);
        btnPesan = view.findViewById(R.id.btn_pesan);
    }

//    public void initActionBatch(){
//        mDatabase = FirebaseDatabase.getInstance()
//                .getReference().child("batch");
//        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                    batch.add(dataSnapshot1.getKey());
//                }
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    public void initActionSpinner(){
//        mDatabaseSpinner = FirebaseDatabase.getInstance().getReference().child("materi_kursus");
//        mDatabaseSpinner.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String paket = "";
//                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                    List<String> temp = new ArrayList<>();
//                        paket = dataSnapshot1.child("judul").getValue().toString();
//                        temp.add(dataSnapshot1.child("deskripsi").getValue().toString());
//                        temp.add(dataSnapshot1.child("durasi_pertemuan").getValue().toString());
//                        temp.add(dataSnapshot1.child("harga").getValue().toString());
//                        temp.add(dataSnapshot1.child("lama_pertemuan").getValue().toString());
//                    judul.add(paket);
//                    detail.add(temp);
//                }
//
//                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, judul);
//                dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//                spinner.setAdapter(dataAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void selectedItemSpinner(){
        System.out.println(detail);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvDeskripsi.setText(detail.get(position).get(0).toString());
                tvDurasi.setText(detail.get(position).get(1).toString() +" jam");
                tvHarga.setText("Rp " +detail.get(position).get(2).toString() +" ,-");
                tvLama.setText(detail.get(position).get(3).toString() +" pertemuan");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void gotoOrderDialog() {
        FragmentManager manager = getFragmentManager();
        FormOrderDialog dialogOrder = new FormOrderDialog();
//        Bundle bundle = new Bundle();
//        dialogAdd.setArguments(bundle);
        dialogOrder.show(manager, dialogOrder.getTag());
    }


}
