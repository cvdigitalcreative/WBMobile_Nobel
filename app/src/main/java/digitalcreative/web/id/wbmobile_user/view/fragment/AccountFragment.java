package digitalcreative.web.id.wbmobile_user.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.model.DataSplashScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    TextView tv_nama, tv_alamat, tv_nomor, tv_email;
    ImageView iv_back;
    String id_user;
    ArrayList<String> listProfile = new ArrayList<>();

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        init(view);
        init_data();
        initShowProfile();
        back();
        return view;
    }

    private void init(View view){
        tv_nama = view.findViewById(R.id.nama_nobel);
        tv_alamat = view.findViewById(R.id.alamat_nobel);
        tv_nomor = view.findViewById(R.id.no_nobel);
        tv_email = view.findViewById(R.id.email);
        iv_back = view.findViewById(R.id.iv_back);
    }

    private void init_data(){
        DataSplashScreen data = new DataSplashScreen(getActivity());
        id_user = data.getString("ID_User");
        listProfile = data.getArrayListString("List_Profile");
    }

    private void back(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                BantuanFragment bantuanFragment = new BantuanFragment();
                fragmentTransaction.replace(R.id.container_fragment, bantuanFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void initShowProfile(){
        tv_alamat.setText(listProfile.get(0).toString());
        tv_email.setText(listProfile.get(1).toString());
        tv_nama.setText(listProfile.get(2).toString());
        tv_nomor.setText(listProfile.get(3).toString());
    }

}
