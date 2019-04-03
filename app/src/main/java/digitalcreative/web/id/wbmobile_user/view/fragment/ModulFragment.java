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
import digitalcreative.web.id.wbmobile_user.model.DataSplashScreen;
import digitalcreative.web.id.wbmobile_user.model.MateriKursus;
import digitalcreative.web.id.wbmobile_user.model.Modul;
import digitalcreative.web.id.wbmobile_user.view.adapter.RecyclerViewAdapter_Modul;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModulFragment extends Fragment {
    Spinner spinnerModul;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<List> listJudulModul;
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
        initData();
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

    private void initData(){
        DataSplashScreen data = new DataSplashScreen(getActivity());
        list = data.getArrayListString("List_Judul");
        listJudulModul = data.getArrayList("List_Judul_Modul");
        System.out.println(listJudulModul);
    }

    private void initActionSpinner(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerModul.setAdapter(dataAdapter);
    }

    public void selectedItemSpinner(){
        spinnerModul.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setRecycleView(listJudulModul.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setRecycleView(List<List> listmodul){
        RecyclerViewAdapter_Modul recycler = new RecyclerViewAdapter_Modul(listmodul, getActivity());
        rv_list_modul.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_list_modul.setItemAnimator( new DefaultItemAnimator());
        rv_list_modul.setAdapter(recycler);

    }

}
