package com.epsilonpros.gocongomagasin.modeles;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Amunaso Tyty on 26/10/2018.
 */

public class Historique extends ModelAbstract<Historique> {

    private int id;

    public Historique() {
    }

    @Override
    public Historique fromCursor(Cursor cursor) {
        try {
            Historique historique = new Historique();

            return historique;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Historique fromJSON(JSONObject jsonObject){
        try {
            Historique historique = new Historique();


            return historique;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public ArrayList<String[]> getKeysType() {
        ArrayList<String[]> keysType = new ArrayList<>();

        keysType.add(STRING_TO_TAB(_ID, TYPE_INTEGER+CLEF_PRIMAIRE+AUTOINCREMENT));

        
        return keysType;
    }

    @Override
    public String TABLE_NAME() {
        return Historique.class.getSimpleName();
    }

    @Override
    public String BDD_NAME() {
        return Historique.class.getSimpleName();
    }

    @Override
    public ContentValues getContentValues() {

        ContentValues contentValues = new ContentValues();

    
        return contentValues;
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{_ID};
    }
}
