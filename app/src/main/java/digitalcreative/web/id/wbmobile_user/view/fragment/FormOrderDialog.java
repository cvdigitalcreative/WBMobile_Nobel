package digitalcreative.web.id.wbmobile_user.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import digitalcreative.web.id.wbmobile_user.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FormOrderDialog extends AppCompatDialogFragment implements View.OnClickListener {
    private Button btnCancel, btnYes;

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
            btnYesClick(view);
        }
    }

    private void btnYesClick(View view) {

    }

    public void init(View view){
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnYes = view.findViewById(R.id.btn_yes);
    }
}
