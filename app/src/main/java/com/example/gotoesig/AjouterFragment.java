package com.example.gotoesig;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.example.gotoesig.data.TrajetContract.TrajetEntry;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gotoesig.data.TrajetContract;

import androidx.fragment.app.Fragment;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AjouterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AjouterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AjouterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int retard = 0;
    private Spinner mTransportSpinner;

    private int mTransport = TrajetEntry.TRANSPORT_VOITURE;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AjouterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AjouterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AjouterFragment newInstance(String param1, String param2) {
        AjouterFragment fragment = new AjouterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ajouter, container, false);
        Button decrement = (Button) rootView.findViewById(R.id.retard_decrement);
        Button increment = (Button) rootView.findViewById(R.id.retard_increment);

        mTransportSpinner = (Spinner) rootView.findViewById(R.id.spinner_transport);
        setupSpinner(rootView);
        decrement.setOnClickListener(mButtonDecrement);
        increment.setOnClickListener(mButtonIncrement);

        return rootView;
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

    public void setupSpinner(View view) {
        ArrayAdapter transportSpinnerAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.array_method_transport, android.R.layout.simple_spinner_item);

        transportSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mTransportSpinner.setAdapter(transportSpinnerAdapter);

        mTransportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.voiture))) {
                        mTransport = TrajetEntry.TRANSPORT_VOITURE;
                    } else if (selection.equals(getString(R.string.velo))) {
                        mTransport = TrajetEntry.TRANSPORT_VELO;
                    } else if (selection.equals(getString(R.string.metro))) {
                        mTransport = TrajetEntry.TRANSPORT_METRO;
                    } else {
                        mTransport = TrajetEntry.TRANSPORT_PIEDS;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTransport = TrajetEntry.TRANSPORT_VOITURE;
            }
        });
    }

    public void display (int number) {
        TextView retard_tolere = (TextView) getView().findViewById(R.id.edit_retard_tolere);
        retard_tolere.setText("" + number);
    }


    private View.OnClickListener mButtonDecrement = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = "Le retard toléré doit être plus grand ou égal à 0";
            int duration = Toast.LENGTH_SHORT;
            Context context = getView().getContext();
            if (retard == 0) {
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                display(retard);
                return;
            } else {
                retard--;
                display(retard);
            }
        }
    };

    private View.OnClickListener mButtonIncrement = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            retard++;
            display(retard);
        }
    };



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

    public static AjouterFragment newInstance(){
        return (new AjouterFragment());
    }
}
