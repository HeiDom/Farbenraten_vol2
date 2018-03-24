package de.dome.farbenerraten.Util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import de.dome.farbenerraten.R;

/**
 * Created by Dominik on 04.09.2017.
 */
public class ColorUtil {

    private Context mContext;

    private final static int count = 9;

    /**
     * Stellt eine Instanz der Klasse ColorUtil bereit
     * @param context
     */
    public ColorUtil(Context context){
        mContext = context;
    }

    /**
     * Gibt eine Farbe anhand des übergeben Indexes zurück
     * @param index über den die Farbe ausgewählt wird
     * @return gibt den Farbe zurück
     */
    public int getColor(int index){
        switch (index){
            case 0: return ContextCompat.getColor(mContext, R.color.red);
            case 1: return ContextCompat.getColor(mContext, R.color.blue);
            case 2: return ContextCompat.getColor(mContext, R.color.pink);
            case 3: return ContextCompat.getColor(mContext, R.color.lila);
            case 4: return ContextCompat.getColor(mContext, R.color.grey);
            case 5: return ContextCompat.getColor(mContext, R.color.green);
            case 6: return ContextCompat.getColor(mContext, R.color.brown);
            case 7: return ContextCompat.getColor(mContext, R.color.black);
            case 8: return ContextCompat.getColor(mContext, R.color.yellow);
            default: return ContextCompat.getColor(mContext, R.color.orange);
        }
    }

    /**
     * Gibt einen Farbnamen anhand des übergeben Indexes zurück
     * @param index über den die Farbe ausgewählt wird
     * @return gibt den Farbanem zurück
     */
    public String getColorName(int index){
        switch (index){
            case 0: return mContext.getResources().getString(R.string.red);
            case 1: return mContext.getResources().getString(R.string.blue);
            case 2: return mContext.getResources().getString(R.string.pink);
            case 3: return mContext.getResources().getString(R.string.lila);
            case 4: return mContext.getResources().getString(R.string.grey);
            case 5: return mContext.getResources().getString(R.string.green);
            case 6: return mContext.getResources().getString(R.string.brown);
            case 7: return mContext.getResources().getString(R.string.black);
            case 8: return mContext.getResources().getString(R.string.yellow);
            default: return mContext.getResources().getString(R.string.orange);
        }
    }

    public static int count(){
        return count;
    }
}
