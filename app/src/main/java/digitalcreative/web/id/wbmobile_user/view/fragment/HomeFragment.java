package digitalcreative.web.id.wbmobile_user.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.synnapps.carouselview.CarouselView;

import digitalcreative.web.id.wbmobile_user.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.google, R.drawable.digital_creative, R.drawable.azura_design_new_website1};

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);


    }



}
