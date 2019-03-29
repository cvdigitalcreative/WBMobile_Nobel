package digitalcreative.web.id.wbmobile_user.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.model.Laporan;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportingFragment extends Fragment {
    EditText et_problem, et_desc;
    Button btn_report;
    String uid;
    DatabaseReference mDatabase;
    ImageView iv_back;

    public ReportingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reporting, container, false);
        init(view);
        init_data();
        sendReport();
        back();
        return view;
    }

    private void init(View view){
        btn_report = view.findViewById(R.id.btn_lapor);
        et_problem = view.findViewById(R.id.et_problem);
        et_desc = view.findViewById(R.id.et_description);
        iv_back = view.findViewById(R.id.iv_back);
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

    private void init_data(){
        uid = "8I0l8Hb9JuO8Mc9h0JQlX1t9TVN2";
        mDatabase = FirebaseDatabase.getInstance().getReference().child("pengaduan");
    }

    private String stringDate(Date today){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(today);
        return date;
    }

    private void refresh(){
        et_problem.setText("");
        et_desc.setText("");
    }

    private void sendReport(){
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String problem = et_problem.getText().toString();
                String desc = et_desc.getText().toString();
                Date date = new Date();
                String tgl = stringDate(date);
                if(problem.isEmpty() || desc.isEmpty()){
                    Toast.makeText(getActivity(),"Data belum lengkap",Toast.LENGTH_SHORT).show();
                }
                else{
                    dataReport(uid, tgl, problem, desc);
                }
            }
        });
    }

    private void dataReport(String uid, String tgl, String problem, String desc){
        Laporan lpr = new Laporan();
        lpr.setMasalah(problem);
        lpr.setDeskripsi(desc);
        mDatabase.child(tgl).child(uid).setValue(lpr);
        Toast.makeText(getActivity(), "Laporan telah terkirim ", Toast.LENGTH_SHORT).show();
        refresh();
    }

}
