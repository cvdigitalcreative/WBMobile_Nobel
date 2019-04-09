package digitalcreative.web.id.wbmobile_user.view.fragment;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.model.DataSplashScreen;
import digitalcreative.web.id.wbmobile_user.model.Pemesanan;
import digitalcreative.web.id.wbmobile_user.view.activity.BaseActivity;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class KonfirmasiFragment extends Fragment {
    ImageView iv_upload, iv_back;
    EditText et_deskripsi;
    Button btn_finish;
    String uid, no_batch, nama_paket, nominal_uang;
    ArrayList<String> list_pembayaran;
    DatabaseReference mDatabase;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    StorageReference storageReference;

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
        cekDataKonfirmasi();
        back();
        connectToFirebase();
        uploadImage();
        sendData();
        return view;
    }

    private void init(View view){
        iv_back = view.findViewById(R.id.iv_back);
        iv_upload = view.findViewById(R.id.iv_upload);
        et_deskripsi = view.findViewById(R.id.et_deskripsi);
        btn_finish = view.findViewById(R.id.btn_upload_foto);
    }

    private void back(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.container_fragment, homeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void init_data(){
        DataSplashScreen data = new DataSplashScreen(getActivity());
        uid = data.getString("ID_User");
        list_pembayaran = data.getArrayListString("Pembayaran_Belum");
    }

    private void connectToFirebase(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("nobel").child(uid).child("kursus").child(no_batch).child(nama_paket).child("informasi_dasar");
        storageReference = FirebaseStorage.getInstance().getReference().child("foto_bukti_pembayaran").child(uid);
    }

    private void sendImage(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading");
        progressDialog.show();
        final String id_image = UUID.randomUUID().toString();
        storageReference.child(id_image).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mDatabase.child("konfirmasi").setValue("Sudah");
                mDatabase.child("harga_dibayar").setValue(nominal_uang);
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Konfirmasi pembayaran berhasil !", Toast.LENGTH_SHORT).show();
                goToHome();

                storageReference.child(id_image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        String usableUrl = downloadUrl.toString();
                        mDatabase.child("foto_bukti_pembayaran").setValue(usableUrl);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Gagal Upload !", Toast.LENGTH_SHORT).show();
                goToHome();
            }
        });
    }

    private void cekDataKonfirmasi(){
        no_batch = list_pembayaran.get(0);
        nama_paket = list_pembayaran.get(1);
    }

    private void uploadImage(){
        iv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    private void sendData(){
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nominal_uang = et_deskripsi.getText().toString();
                if(nominal_uang.isEmpty() || filePath == null){
                    Toast.makeText(getActivity(), "Data belum lengkap !", Toast.LENGTH_SHORT).show();
                }
                else{
                    sendImage();
                }
            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver contentResolver = getContext().getContentResolver();

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath);
                iv_upload.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void goToHome(){
        Intent intent = new Intent(getActivity(), BaseActivity.class);
        startActivity(intent);
    }

}
