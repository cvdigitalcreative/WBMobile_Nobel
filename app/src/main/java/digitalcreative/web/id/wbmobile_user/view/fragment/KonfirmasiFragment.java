package digitalcreative.web.id.wbmobile_user.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.model.DataSplashScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class KonfirmasiFragment extends Fragment {
    ImageView iv_upload, iv_back;
    EditText et_deskripsi;
    Button btn_finish;
    String uid;
    DatabaseReference mDatabase;

    public KonfirmasiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_konfirmasi, container, false);
        init(view);
        init_data();
        connectToFirebase();
        return view;
    }

    private void init(View view){
        iv_back = view.findViewById(R.id.iv_back);
        iv_upload = view.findViewById(R.id.iv_upload);
        et_deskripsi = view.findViewById(R.id.et_deskripsi);
        btn_finish = view.findViewById(R.id.btn_upload_foto);
    }

    private void init_data(){
        DataSplashScreen data = new DataSplashScreen(getActivity());
        uid = data.getString("ID_User");
    }

    private void connectToFirebase(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("nobel").child(uid).child("kursus");
    }

    private void uploadImage(){
        iv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new 
            }
        });
    }

    private void sendPhoto(){
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deskripsi = et_deskripsi.getText().toString();
                String url = "http://";

            }
        });
    }

}
