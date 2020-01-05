package com.example.gotoesig;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.gotoesig.data.Trajet;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gotoesig.data.TrajetContract;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.Map;

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
    private EditText mPosition;
    private EditText mDestination;
    private EditText mDateJour;
    private EditText mDateMois;
    private EditText mDateAnnee;
    private EditText mDateHeure;
    private EditText mDateMin;
    private TextView mRetardTolere;
    private EditText mPlaceDispo;

    private DatabaseReference mDatabase;

    private int mTransport = Trajet.TRANSPORT_VOITURE;

    private Uri mCurrentTraget;

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
        Button valid = (Button) rootView.findViewById(R.id.trajet_valid);

        mTransportSpinner = (Spinner) rootView.findViewById(R.id.spinner_transport);
        mPosition = (EditText) rootView.findViewById(R.id.edit_position);
        mDestination = (EditText) rootView.findViewById(R.id.edit_destination);
        mDateJour = (EditText) rootView.findViewById(R.id.date_jour);
        mDateMois = (EditText) rootView.findViewById(R.id.date_mois);
        mDateAnnee = (EditText) rootView.findViewById(R.id.date_annee);
        mDateHeure = (EditText) rootView.findViewById(R.id.date_heure);
        mDateMin = (EditText) rootView.findViewById(R.id.date_minute);
        mRetardTolere = (TextView) rootView.findViewById(R.id.edit_retard_tolere);
        mPlaceDispo = (EditText) rootView.findViewById(R.id.edit_nbr_place_dispo);

        setupSpinner(rootView);

        decrement.setOnClickListener(mButtonDecrement);
        increment.setOnClickListener(mButtonIncrement);
        valid.setOnClickListener(mButtonValid);

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
                        mTransport = Trajet.TRANSPORT_VOITURE;
                    } else if (selection.equals(getString(R.string.velo))) {
                        mTransport = Trajet.TRANSPORT_VELO;
                    } else if (selection.equals(getString(R.string.metro))) {
                        mTransport = Trajet.TRANSPORT_METRO;
                    } else {
                        mTransport = Trajet.TRANSPORT_PIEDS;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTransport = Trajet.TRANSPORT_VOITURE;
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


    public boolean controlDate (int jour, int mois, int annee, int heure, int min) {
        if (jour < 1 || jour > 31) {
            return false;
        } else if (mois < 1 || mois > 12) {
            return false;
        } else if (annee < 1900 || annee > 2020) {
            return false;
        } else if (heure < 0 || heure > 24) {
            return false;
        } else if (min < 0 || min > 59) {
            return false;
        } else {
            return true;
        }
    }

    private View.OnClickListener mButtonValid = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int transport = mTransport;
            String positionString = mPosition.getText().toString().trim();
            String destinationString = mDestination.getText().toString().trim();
            String jourString = mDateJour.getText().toString().trim();
            String moisString = mDateMois.getText().toString().trim();
            String anneeString = mDateAnnee.getText().toString().trim();
            String heureString = mDateHeure.getText().toString().trim();
            String minString = mDateMin.getText().toString().trim();
            String retardTolere = mRetardTolere.getText().toString().trim();
            String placeDispo = mPlaceDispo.getText().toString().trim();

            if (TextUtils.isEmpty(positionString) && TextUtils.isEmpty(destinationString) && TextUtils.isEmpty(placeDispo)) {
                String text = "Veuillez remplir les champs svp";
                int duration = Toast.LENGTH_SHORT;
                Context context = getView().getContext();

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;

            } else if (TextUtils.isEmpty(jourString) && TextUtils.isEmpty(moisString) && TextUtils.isEmpty(anneeString) && TextUtils.isEmpty(heureString) && TextUtils.isEmpty(minString)) {
                String text = "Veuillez remplir la date svp";
                int duration = Toast.LENGTH_SHORT;
                Context context = getView().getContext();

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
            }

            int jour = Integer.parseInt(jourString);
            int mois = Integer.parseInt(moisString);
            int annee = Integer.parseInt(anneeString);
            int heure = Integer.parseInt(heureString);
            int min = Integer.parseInt(minString);

            int retardInteger = Integer.parseInt(retardTolere);
            int placeInteger = Integer.parseInt(placeDispo);

            if (!controlDate(jour, mois, annee, heure, min)) {
                String text = "Attention a la date svp";
                int duration = Toast.LENGTH_SHORT;
                Context context = getView().getContext();

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                return;
            }

            String date = jourString + '/' + moisString + '/' + anneeString + ' ' + heureString + ':' + minString;

            Log.w("Minute : ", minString);

            int id = jour + mois + annee + min + heure;

            writeNewTrajet(transport, positionString, destinationString, date, retardInteger, placeInteger);

        }
    };


    private void writeNewTrajet(int transport, String depart, String destination, String date, int retardInteger, int placeInteger) {
        mDatabase = FirebaseDatabase.getInstance().getReference("trajet");
        String key = mDatabase.push().getKey();


        Trajet trajet = new Trajet(transport, depart,destination, date, retardInteger, placeInteger);
        Map<String, Object> trajetValues = trajet.toMap();

        mDatabase.child(key).setValue(trajetValues);
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

    public static AjouterFragment newInstance(){
        return (new AjouterFragment());
    }
}
