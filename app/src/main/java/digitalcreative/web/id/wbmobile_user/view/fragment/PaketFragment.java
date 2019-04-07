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
    ArrayList<List> detail  = new ArrayList<>();
    Spinner spinner;
    TextView tvDeskripsi, tvLama, tvDurasi, tvHarga;
    Button btnPesan;
    ArrayList<String> listProfile = new ArrayList<>();

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
        initActionBatch();
        initActionSpinner();
        selectedItemSpinner();
        prosesPesan();
        return view;
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

    private void prosesPesan(){
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToOrderDialog();
            }
        });
    }

    private void goToOrderDialog() {
        String nama = listProfile.get(2);
        String harga = tvHarga.getText().toString();
        String deskripsi = tvDeskripsi.getText().toString();
        String nama_paket = spinner.getSelectedItem().toString();
        String batch = "1";
        System.out.println(nama + " " + harga + " " + deskripsi + " " + nama_paket);

        saveString(nama, "Paket_Nama");
        saveString(harga, "Paket_Harga");
        saveString(deskripsi, "Paket_Deskripsi");
        saveString(nama_paket, "Paket_Nama_Paket");
        saveString(batch, "Paket_Batch");

        FragmentManager manager = getFragmentManager();
        FormOrderDialog dialogOrder = new FormOrderDialog();
//        Bundle bundle = new Bundle();
//        bundle.putString(nama, "Paket_Nama");
//        bundle.putString(harga, "Paket_Harga");
//        bundle.putString(deskripsi, "Paket_Deskripsi");
//        bundle.putString(nama_paket, "Paket_Nama_Paket");
//        bundle.putString(batch, "Paket_Batch");
//        dialogOrder.setArguments(bundle);
        dialogOrder.show(manager, dialogOrder.getTag());
    }

    private void saveString(String str, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, str);
        editor.apply();     // This line is IMPORTANT !!!
    }


}
