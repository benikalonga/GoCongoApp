package com.epsilonpros.gocongomagasin.modeles;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Amunaso Tyty on 26/10/2018.
 */

public class Magasinier extends ModelAbstract<Magasinier> {
    //clefs
    public static String ID_MAGASINIER = "idmagasinier", MOTDEPASS = "motdepass", NOM = "nom", POSTNOM = "postnom", PRENOM = "prenom",
                         NUMERO_MAT = "numero_matricule", SEXE = "sexe", PHOTO = "photo"; 

    private String idMagasinier, motdepass, nom , postnom , prenom , numeroMat, sexe, photo; 
    private int id;

    public Magasinier() {
    }

    @Override
    public Magasinier fromCursor(Cursor cursor) {
        try {
            Magasinier magasinier = new Magasinier();

            magasinier.setNumeroMat(cursor.getString(cursor.getColumnIndex(NUMERO_MAT)));
            magasinier.setMotdepass(cursor.getString(cursor.getColumnIndex(MOTDEPASS)));
            magasinier.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
            magasinier.setIdMagasinier(cursor.getString(cursor.getColumnIndex(ID_MAGASINIER)));
            magasinier.setNom(cursor.getString(cursor.getColumnIndex(NOM)));
            magasinier.setPostnom(cursor.getString(cursor.getColumnIndex(POSTNOM)));
            magasinier.setPrenom(cursor.getString(cursor.getColumnIndex(PRENOM)));
            magasinier.setSexe(cursor.getString(cursor.getColumnIndex(SEXE)));
            magasinier.setPhoto(cursor.getString(cursor.getColumnIndex(PHOTO)));

            return magasinier;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Magasinier fromJSON(JSONObject jsonObject){
        try {
            Magasinier magasinier = new Magasinier();

            magasinier.setIdMagasinier(jsonObject.getString(ID_MAGASINIER));
            magasinier.setMotdepass(jsonObject.getString(MOTDEPASS));
            magasinier.setNumeroMat(jsonObject.getString(NUMERO_MAT));
            magasinier.setNom(jsonObject.getString(NOM));
            magasinier.setPostnom(jsonObject.getString(POSTNOM));
            magasinier.setPrenom(jsonObject.getString(PRENOM));
            magasinier.setSexe(jsonObject.getString(SEXE));
            magasinier.setPhoto(jsonObject.getString(PHOTO));

            return magasinier;
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

    public String getNumeroMat() {
        return numeroMat;
    }

    public void setNumeroMat(String numeroMat) {
        this.numeroMat = numeroMat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPostnom() {
        return postnom;
    }

    public void setPostnom(String postnom) {
        this.postnom = postnom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getIdMagasinier() {
        return idMagasinier;
    }

    public void setIdMagasinier(String idMagasinier) {
        this.idMagasinier = idMagasinier;
    }

    public String getMotdepass() {
        return motdepass;
    }

    public void setMotdepass(String motdepass) {
        this.motdepass = motdepass;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public ArrayList<String[]> getKeysType() {
        ArrayList<String[]> keysType = new ArrayList<>();

        keysType.add(STRING_TO_TAB(_ID, TYPE_INTEGER+CLEF_PRIMAIRE+AUTOINCREMENT));
        keysType.add(STRING_TO_TAB(NUMERO_MAT, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(ID_MAGASINIER, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(NOM, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(POSTNOM, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(PRENOM, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(SEXE, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(MOTDEPASS, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(PHOTO, TYPE_TEXT));

        return keysType;
    }

    @Override
    public String TABLE_NAME() {
        return Magasinier.class.getSimpleName();
    }

    @Override
    public String BDD_NAME() {
        return Magasinier.class.getSimpleName();
    }

    @Override
    public ContentValues getContentValues() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_MAGASINIER, getIdMagasinier() );
        contentValues.put(MOTDEPASS, getMotdepass() );
        contentValues.put(NOM, getNom() );
        contentValues.put(POSTNOM, getPostnom() );
        contentValues.put(PRENOM, getPrenom() );
        contentValues.put(NUMERO_MAT, getNumeroMat() );
        contentValues.put(SEXE, getSexe() );
        contentValues.put(PHOTO, getPhoto() );

        return contentValues;
    }

    @Override
    public String[] getColumnNames() {

        return new String[]{_ID,ID_MAGASINIER,MOTDEPASS, NOM, POSTNOM,PRENOM,NUMERO_MAT, SEXE, PHOTO};
    }
}
