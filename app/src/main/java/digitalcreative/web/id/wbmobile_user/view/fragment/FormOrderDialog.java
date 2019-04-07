package digitalcreative.web.id.wbmobile_user.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.model.DataSplashScreen;


/**
 * A simple {@link Fragment} subclass.
 */
public class FormOrderDialog extends AppCompatDialogFragment implements View.OnClickListener {
    private Button btnCancel, btnYes;
    private TextView tv_nama, tv_paket, tv_batch, tv_deskripsi, tv_harga;
    private String nama, paket, batch, deskripsi, harga;

    public FormOrderDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_form_order, container, false);
        getDialog().setCanceledOnTouchOutside(false);
        init(view);
        initData();
        initActionForm();

        btnCancel.setOnClickListener(this);
        btnYes.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == btnCancel){
            getDialog().dismiss();
        }
        if(view == btnYes){
            btnYesClick();
        }
    }

    private void btnYesClick() {

    }

    private void init(View view){
        tv_nama = view.findViewById(R.id.tv_nama);
        tv_paket = view.findViewById(R.id.tv_paket);
        tv_batch = view.findViewById(R.id.tv_batch);
        tv_deskripsi = view.findViewById(R.id.tv_deskripsi);
        tv_harga = view.findViewById(R.id.tv_harga_paket);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnYes = view.findViewById(R.id.btn_yes);
    }

    private void initData(){
        DataSplashScreen dataSplashScreen = new DataSplashScreen(getActivity());
        nama = dataSplashScreen.getString("Paket_Nama");
        paket = dataSplashScreen.getString("Paket_Nama_Paket");
        batch = dataSplashScreen.getString("Paket_Batch");
        deskripsi = dataSplashScreen.getString("Paket_Deskripsi");
        harga = dataSplashScreen.getString("Paket_Harga");

        System.out.println(nama + " " + harga + " " + deskripsi + " " + paket + " " + batch);
    }

    private void initActionForm(){
        tv_nama.setText(nama);
        tv_paket.setText(paket);
        tv_batch.setText(batch);
        tv_deskripsi.setText(deskripsi);
        tv_harga.setText(harga);
    }

    private void sendData(){

    }
}
