package digitalcreative.web.id.wbmobile_user.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.view.adapter.RecyclerView_Adapter;

public class PaketFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> batch = new ArrayList<>();

    public PaketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paket, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);

        //Set RecyclerView
        batch.add("Batch 1");
        batch.add("Batch 2");
        batch.add("Batch 3");

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        RecyclerView_Adapter adapter = new RecyclerView_Adapter(batch);
        recyclerView.setAdapter(adapter);

        return view;
    }


}
