package com.example.gotoesig;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EvaluerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EvaluerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EvaluerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView photo1;
    private ImageView photo2;
    private ImageView photo3;
    private OnFragmentInteractionListener mListener;

    public EvaluerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EvaluerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EvaluerFragment newInstance(String param1, String param2) {
        EvaluerFragment fragment = new EvaluerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_evaluer, container, false);
        photo1 = RootView.findViewById(R.id.roundedimage1);
        photo2 = RootView.findViewById(R.id.roundedimage2);
        photo3 = RootView.findViewById(R.id.roundedimage3);
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/gotoesig-64e20.appspot.com/o/ImageFolder%2Fimageimage%3A964013?alt=media&token=1fd0f1fa-9da1-47fe-8f12-0f80c10d80e6")
                .apply(RequestOptions.circleCropTransform())
                .into(photo1);
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/gotoesig-64e20.appspot.com/o/ImageFolder%2Fimageimage%3A964037?alt=media&token=371119d7-84ba-43b3-8d58-3981755ac8c7")
                .apply(RequestOptions.circleCropTransform())
                .into(photo2);
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/gotoesig-64e20.appspot.com/o/ImageFolder%2Fimageimage%3A964033?alt=media&token=7a65b253-11dc-44e8-808a-e4e8d1f416a5")
                .apply(RequestOptions.circleCropTransform())
                .into(photo3);
        // Inflate the layout for this fragment
        return RootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    public static EvaluerFragment newInstance(){
        return (new EvaluerFragment());
    }
}
