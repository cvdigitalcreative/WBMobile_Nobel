package digitalcreative.web.id.wbmobile_user.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import digitalcreative.web.id.wbmobile_user.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BantuanFragment extends Fragment {
    Button btnReport, btnInfo, btnAccount;

    public BantuanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bantuan, container, false);
        init(view);

        setBtnReport(btnReport);
        setBtnInfo(btnInfo);
        setBtnAccount(btnAccount);

        return view;
    }

    public void init(View view){
        btnReport = view.findViewById(R.id.btn_report);
        btnInfo = view.findViewById(R.id.btn_info);
        btnAccount = view.findViewById(R.id.btn_account);
    }

    private void setBtnReport(Button btnReport){
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();

                ReportingFragment reportingFragment = new ReportingFragment();
                fragmentTransaction.replace(R.id.container_fragment, reportingFragment);

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void setBtnInfo(Button btnInfo){
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();

                InformationFragment informationFragment = new InformationFragment();
                fragmentTransaction.replace(R.id.container_fragment, informationFragment);

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void setBtnAccount(Button btnAccount){
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();

                AccountFragment accountFragment = new AccountFragment();
                fragmentTransaction.replace(R.id.container_fragment, accountFragment);

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }



}
