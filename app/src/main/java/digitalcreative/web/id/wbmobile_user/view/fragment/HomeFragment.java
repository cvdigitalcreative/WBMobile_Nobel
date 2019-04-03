package digitalcreative.web.id.wbmobile_user.view.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.synnapps.carouselview.CarouselView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.model.DataSplashScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    TextView tv_paket, tv_batch, tv_tanggal;
    ArrayList<List> homelist = new ArrayList<>();
    SharedPreferences prefs;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        initData();
        initAction();
        return view;
    }

    private void init(View view){
        tv_paket = view.findViewById(R.id.tv_paket);
        tv_batch = view.findViewById(R.id.tv_batch);
        tv_tanggal = view.findViewById(R.id.tv_tanggal);
    }

    private void initData(){
        DataSplashScreen data = new DataSplashScreen(getActivity());
        homelist = data.getArrayList("List_Home");
        System.out.println(homelist);
    }

    private void initAction(){
        tv_paket.setText(homelist.get(0).get(0).toString());
        tv_batch.setText("Batch "+homelist.get(0).get(1).toString());
        tv_tanggal.setText(homelist.get(0).get(2).toString());
    }
}