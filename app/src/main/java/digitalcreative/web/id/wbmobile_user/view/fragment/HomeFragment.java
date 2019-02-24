package digitalcreative.web.id.wbmobile_user.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.synnapps.carouselview.CarouselView;

import digitalcreative.web.id.wbmobile_user.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    CarouselView carouselView;
    TextView tv_paket, tv_batch, tv_tanggal;
    DatabaseReference mDatabase;

    int[] sampleImages = {R.drawable.google, R.drawable.digital_creative, R.drawable.azura_design_new_website1};

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);

        return view;
    }

    private void init(View view){
        tv_paket = view.findViewById(R.id.tv_paket);
        tv_batch = view.findViewById(R.id.tv_batch);
        tv_tanggal = view.findViewById(R.id.tv_tanggal);
    }

    private void receiveID(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("nobel").child("8I0l8Hb9JuO8Mc9h0JQlX1t9TVN2");
    }

    private void initAction(){
        
    }


}
