package com.example.gotoesig.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Trajet {
    private int mId;
    private int mTransport;
    private String mDepart;
    private String mDestination;
    private String mDate;
    private int mRetardTolere;
    private int mNbPlaceDispo;


    public final static String COLUMN_TRAJET_TRANSPORT = "transport";
    public final static String COLUMN_TRAJET_DEPART = "depart";
    public final static String COLUMN_TRAJET_DESTINATION = "destination";
    public final static String COLUMN_TRAJET_DATE = "date";
    public final static String COLUMN_TRAJET_RETARD_TOLERE = "retard_tolere";
    public final static String COLUMN_TRAJET_PLACE_DISPO = "place_dispo";

    public static final int TRANSPORT_VOITURE = 0;
    public static final int TRANSPORT_VELO = 1;
    public static final int TRANSPORT_METRO = 2;
    public static final int TRANSPORT_PIEDS = 3;


    public Trajet(int transport, String depart, String destination, String date, int retardTolere, int nbPlaceDispo) {

        this.mTransport = transport;
        this.mDepart = depart;
        this.mDestination = destination;
        this.mDate = date;
        this.mRetardTolere = retardTolere;
        this.mNbPlaceDispo = nbPlaceDispo;
    }

    public int getTransport() { return mTransport; }

    public String getDepart () { return mDepart; }

    public String getDestination () { return mDestination; }

    public String getDate () { return mDate; }

    public int getRetardTolere () { return mRetardTolere; }

    public int getNbPlaceDispo () { return mNbPlaceDispo; }


    public static boolean isValidTransport(int transport){
        if (transport == TRANSPORT_VOITURE || transport == TRANSPORT_VELO || transport == TRANSPORT_METRO || transport == TRANSPORT_PIEDS) {
            return true;
        }
        return false;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put(COLUMN_TRAJET_TRANSPORT, getTransport());
        result.put(COLUMN_TRAJET_DEPART, getDepart());
        result.put(COLUMN_TRAJET_DESTINATION, getDestination());
        result.put(COLUMN_TRAJET_DATE, getDate());
        result.put(COLUMN_TRAJET_RETARD_TOLERE, getRetardTolere());
        result.put(COLUMN_TRAJET_PLACE_DISPO, getNbPlaceDispo());

        return result;
    }
}
