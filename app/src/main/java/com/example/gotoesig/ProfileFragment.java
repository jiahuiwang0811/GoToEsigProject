package com.example.gotoesig;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import static androidx.core.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;

//import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView TVusername;
    private TextView TVemail;
    private ImageView photoProfil;
    private Button BtnPhoto;
    private OnFragmentInteractionListener mListener;
    private StorageReference Folder;
    //Uri file = Uri.fromFile(new File("gs://gotoesig-64e20.appspot.com/IMG_8080.JPG"));
    //StorageReference riversRef = mStorageRef.child("images/IMG_8080.jpg");
    //File localFile;
    private static final int ImageBack = 1;
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Folder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_profile, container, false);
        TVusername = RootView.findViewById(R.id.profile_username);
        TVemail = RootView.findViewById(R.id.profile_email);
        photoProfil = RootView.findViewById(R.id.imageViewPdp);
        BtnPhoto = RootView.findViewById(R.id.profile_btn);
        BtnPhoto.setOnClickListener(this);
        updateUIWhenCreating();
        // Inflate the layout for this fragment
        return RootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onClick (View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, ImageBack);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("request Code : " + requestCode);
        System.out.println("result Code : " + resultCode);
        System.out.println("data : " + data);
        //if(resultCode == ImageBack ){
            Log.d("profil", "image Back");
          //  if(resultCode == RESULT_OK){
                Log.d("profil", "Result ok");
                Uri ImageData = data.getData();
                final StorageReference Imagename = Folder.child("image"+ImageData.getLastPathSegment());
                Imagename.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("profil", "image uploaded");
                        Toast.makeText(getActivity(), "Photo uploaded", Toast.LENGTH_SHORT).show();
                    }
                });
            //}
        //}
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static ProfileFragment newInstance(){
        return (new ProfileFragment());
    }

    // --------------------
    // UTILS
    // --------------------

    @Nullable
    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    protected Boolean isCurrentUserLogged(){ return (this.getCurrentUser() != null); }

    // --------------------
    // UI
    // --------------------

    // 1 - Update UI when activity is creating
    private void updateUIWhenCreating(){

        if (this.getCurrentUser() != null){

            //Get picture URL from Firebase
            if (this.getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this)
                        .load(this.getCurrentUser().getPhotoUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(photoProfil);
            } else{
                Glide.with(this)
                        .load("https://firebasestorage.googleapis.com/v0/b/gotoesig-64e20.appspot.com/o/ImageFolder%2Fimageimage%3A964013?alt=media&token=1fd0f1fa-9da1-47fe-8f12-0f80c10d80e6")
                        .apply(RequestOptions.circleCropTransform())
                        .into(photoProfil);

                Log.d("profil", "non photo de profil" );
            }

            //Get email & username from Firebase
            String email = TextUtils.isEmpty(this.getCurrentUser().getEmail()) ? getString(R.string.info_no_email_found) : this.getCurrentUser().getEmail();
            String username = TextUtils.isEmpty(this.getCurrentUser().getDisplayName()) ? getString(R.string.info_no_username_found) : this.getCurrentUser().getDisplayName();

            //Update views with data
            Log.d("profil", email);
            Log.d("profil", username);

            TVusername.setText(username);
            TVemail.setText(email);
        }else {
            Log.d("profil", "non connecte" );
        }
    }

}
