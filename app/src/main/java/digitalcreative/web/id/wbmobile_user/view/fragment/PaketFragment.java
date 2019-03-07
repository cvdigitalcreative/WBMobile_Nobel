package digitalcreative.web.id.wbmobile_user.view.fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.view.adapter.RecyclerView_Adapter;
import digitalcreative.web.id.wbmobile_user.view.kelas.MateriKursus;

public class PaketFragment extends Fragment {

    DatabaseReference mDatabase;
    RecyclerView recyclerView;
    ArrayList<String> batch = new ArrayList<>();
    RecyclerView_Adapter adapter;
    final List<String> list = new ArrayList<>();
    Spinner spinner;

    public PaketFragment() {
        // Required empty public constructor
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
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public List<String> initActionSpinner(){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("materi_kursus");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String paket = "";
                MateriKursus mk = new MateriKursus();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    paket = dataSnapshot1.getKey().toString();
                    mk.setNamaPaket(paket);
                    list.add(mk.getNamaPaket());
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        System.out.println("loaddatabase " +list);
        return list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paket, container, false);

        initActionBatch();
        final List<String> list = initActionSpinner();
        recyclerView = view.findViewById(R.id.recyclerView);
        spinner = view.findViewById(R.id.spinner_paket);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new RecyclerView_Adapter(batch);
        System.out.println("lala "+list);

        try {

        } catch (Exception e){

        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                        spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }




}
