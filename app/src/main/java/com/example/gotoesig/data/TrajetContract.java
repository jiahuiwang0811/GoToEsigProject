package com.example.gotoesig.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.Date;

public class TrajetContract {
    private TrajetContract() {}

    /**
     * Content Authority
     */
    public static final String CONTENT_AUTHORITY = "com.example.gotoesig";
    /**
     * Base Content URI
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /**
     * Path Table name
     */
    public static final String PATH_TRAJETS = "trajets";

    public static final class TrajetEntry implements BaseColumns {
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAJETS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAJETS;


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TRAJETS);

        public final static String TABLE_NAME = "trajets";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_TRAJET_TRANSPORT = "transport";

        public final static String COLUMN_TRAJET_DEPART = "depart";

        public final static String COLUMN_TRAJET_DATE = "date";

        public final static String COLUMN_TRAJET_RETARD_TOLERE = "retard_tolere";

        public final static String COLUMN_TRAJET_PLACE_DISPO = "place_dispo";

        public final static String COLUMN_TRAJET_PLACE_RESERVE = "place_reserve";

        public final static String COLUMN_TRAJET_CREATEUR_TRAJET = "createur_trajet";


        public static final int TRANSPORT_VOITURE = 0;

        public static final int TRANSPORT_VELO = 1;

        public static final int TRANSPORT_METRO = 2;

        public static final int TRANSPORT_PIEDS = 3;

        public static boolean isValidTransport(int transport){
            if (transport == TRANSPORT_VOITURE || transport == TRANSPORT_VELO || transport == TRANSPORT_METRO || transport == TRANSPORT_PIEDS) {
                return true;
            }
            return false;
        }

    }

}
