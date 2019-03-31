package digitalcreative.web.id.wbmobile_user.view.fragment;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.model.MateriKursus;
import digitalcreative.web.id.wbmobile_user.model.Modul;
import digitalcreative.web.id.wbmobile_user.view.adapter.RecyclerViewAdapter_Modul;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModulFragment extends Fragment {
    Spinner spinnerModul;
    DatabaseReference dbModul;
    ArrayList<String> list = new ArrayList<>();
    List<Modul> listDetailPaket;
    ArrayList<List> listJudul;
    ArrayList<List> multiList;
    RecyclerView rv_list_modul;

    public ModulFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modul, container, false);
        init(view);
        connectToFirebase();
        initActionSpinner();
        selectedItemSpinner();
        return view;
    }

    public void init(View view){
        spinnerModul = view.findViewById(R.id.spinner_paket);
        rv_list_modul = view.findViewById(R.id.rv_list_modul);
        LinearLayoutManager MyLinearLayoutManager = new LinearLayoutManager(getActivity());
        rv_list_modul.setLayoutManager(MyLinearLayoutManager);
    }

    private void connectToFirebase(){
        dbModul = FirebaseDatabase.getInstance().getReference().child("materi_kursus");
    }

    public void initActionSpinner(){

        dbModul.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String paket = "";
                multiList = new ArrayList<>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    listDetailPaket = new ArrayList<>();
                    paket = dataSnapshot1.child("judul").getValue().toString();
                    list.add(paket);
                    listJudul = new ArrayList<>();
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.child("modul").getChildren()){
                        String nama_modul = dataSnapshot2.child("nama_modul").getValue().toString();
                        String status = dataSnapshot2.child("status").getValue().toString();
                        String tema_materi = dataSnapshot2.child("tema_materi").getValue().toString();
                        String url_modul = dataSnapshot2.child("url_modul").getValue().toString();
                        listDetailPaket.add(new Modul(nama_modul, status, tema_materi, url_modul));
                    }
                    listJudul.add(listDetailPaket);
                    multiList.add(listJudul);
                }

                System.out.println(multiList);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spinnerModul.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void selectedItemSpinner(){
        spinnerModul.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),
                         "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                setRecycleView(multiList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setRecycleView(List listmodul){
        for(int i=0; i<listmodul.size(); i++){
            RecyclerViewAdapter_Modul recycler = new RecyclerViewAdapter_Modul((List<Modul>) listmodul.get(i), getActivity());
            rv_list_modul.setLayoutManager(new LinearLayoutManager(getActivity()));
            rv_list_modul.setItemAnimator( new DefaultItemAnimator());
            rv_list_modul.setAdapter(recycler);
        }

    }

}
