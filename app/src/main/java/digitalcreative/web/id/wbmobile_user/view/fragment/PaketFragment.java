package digitalcreative.web.id.wbmobile_user.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import digitalcreative.web.id.wbmobile_user.R;

public class PaketFragment extends Fragment {

    ListView listview;
    String batch[] = {
            "Batch 1", "Batch 2", "Batch 3", "Batch 4"
    };

    public PaketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paket, container, false);

        listview = view.findViewById(R.id.listview);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_batch,
                R.id.batch2,
                batch
        );

        // set data
        listview.setAdapter(adapter);

        return view;
    }


}
