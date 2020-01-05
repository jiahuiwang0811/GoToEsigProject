package com.example.gotoesig.data;

import java.util.Date;

public class Trajet {
    private int mIdTransport;
    private String mDepart;
    private Date mDate;
    private int mRetardTolere;
    private int mNbPlaceDispo;
    private int mNbPlaceReserve;
    private String mCreateurTrajet;

    public Trajet(int idTransport, String depart, Date date, int retardTolere, int nbPlaceDispo, int nbPlaceReserve, String createurTrajet) {
        this.mIdTransport = idTransport;
        this.mDepart = depart;
        this.mDate = date;
        this.mRetardTolere = retardTolere;
        this.mNbPlaceDispo = nbPlaceDispo;
        this.mNbPlaceReserve = nbPlaceReserve;
        this.mCreateurTrajet = createurTrajet;
    }

    public int getIdTransport () { return mIdTransport; }

    public String getDepart () { return mDepart; }

    public Date getDate () { return mDate; }

    public int getRetartTolere () { return mRetardTolere; }

    public int getNbPlaceDispo () { return mNbPlaceDispo; }

    public int getNbPlaceReserve () { return mNbPlaceReserve; }

    public String getCreateurTrajet () { return mCreateurTrajet; }
}
