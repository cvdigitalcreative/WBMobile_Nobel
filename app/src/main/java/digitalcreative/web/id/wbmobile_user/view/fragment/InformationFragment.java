package digitalcreative.web.id.wbmobile_user.view.fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import digitalcreative.web.id.wbmobile_user.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends Fragment {
    ImageButton instagramBtn, fbBtn, waBtn, webBtn;
    ImageView iv_back;

    public InformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        init(view);
        setInstagramBtn(instagramBtn);
        setFbBtn(fbBtn);
        setWaBtn(waBtn);
        setWebBtn(webBtn);
        back();
        return view;
    }

    private void init (View view) {
        iv_back = view.findViewById(R.id.iv_back);
        instagramBtn = view.findViewById(R.id.insta_btn);
        fbBtn = view.findViewById(R.id.fb_btn);
        waBtn = view.findViewById(R.id.wa_btn);
        webBtn = view.findViewById(R.id.web_btn);
    }

    private void setInstagramBtn(ImageButton instagramBtn) {
        instagramBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoInstagram();
            }
        });
    }

    private void setFbBtn(ImageButton fbBtn) {
        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFacebook();
            }
        });
    }

    private void setWaBtn(ImageButton waBtn) {
        waBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoWhatsapp();
            }
        });
    }

    private void setWebBtn(ImageButton webBtn) {
        webBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoWebpage();
            }
        });
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

    private void gotoInstagram() {

        Uri uri = Uri.parse("http://instagram.com/_u/digital_creative");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/digital_creative")));
        }

    }

    private void gotoFacebook() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/Digital-Creative-1215092261929408"));
            startActivity(intent);
        } catch(Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/Digital-Creative-1215092261929408")));
        }
    }

    private void gotoWhatsapp() {
        String contact = "+62 8117199210"; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            getActivity().getPackageManager().getPackageInfo("com.whatsapp", 0);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(getActivity(), "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void gotoWebpage() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://warungbelajar.co.id/"));
            startActivity(intent);
        } catch(Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://warungbelajar.co.id/")));
        }
    }


}
