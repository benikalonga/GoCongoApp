package com.epsilonpros.gocongomagasin.modeles;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Amunaso Tyty on 26/10/2018.
 */

public class Livreur extends ModelAbstract<Livreur> {

    //clefs
    public static String NUMERO_MAT = "NumeroMat",NOM = "Nom", POSTNOM = "Postnom", PRENOM = "Prenom", SEX = "Sex",
                         IMAGE = "image", NUMERO_CARD = "NumeroCard";

    private String numeroMat, nom , postnom , prenom , sex , image , numeroCard ;
    private int id;

    public Livreur() {
    }

    @Override
    public Livreur fromCursor(Cursor cursor) {
        try {
            Livreur livreur = new Livreur();

            livreur.setNumeroMat(cursor.getString(cursor.getColumnIndex(NUMERO_MAT)));
            livreur.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
            livreur.setNom(cursor.getString(cursor.getColumnIndex(NOM)));
            livreur.setPostnom(cursor.getString(cursor.getColumnIndex(POSTNOM)));
            livreur.setPrenom(cursor.getString(cursor.getColumnIndex(PRENOM)));
            livreur.setSex(cursor.getString(cursor.getColumnIndex(SEX)));
            livreur.setImage(cursor.getString(cursor.getColumnIndex(IMAGE)));
            livreur.setNumeroCard(cursor.getString(cursor.getColumnIndex(NUMERO_CARD)));

            return livreur;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Livreur fromJSON(JSONObject jsonObject){
        try {
            Livreur livreur = new Livreur();

            livreur.setNumeroMat(jsonObject.getString(NUMERO_MAT));
            livreur.setNom(jsonObject.getString(NOM));
            livreur.setPostnom(jsonObject.getString(POSTNOM));
            livreur.setPrenom(jsonObject.getString(PRENOM));
            livreur.setSex(jsonObject.getString(SEX));
            livreur.setImage(jsonObject.getString(IMAGE));
            livreur.setNumeroCard(jsonObject.getString(NUMERO_CARD));

            return livreur;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNumeroCard() {
        return numeroCard;
    }

    public void setNumeroCard(String numeroCard) {
        this.numeroCard = numeroCard;
    }

    @Override
    public ArrayList<String[]> getKeysType() {
        ArrayList<String[]> keysType = new ArrayList<>();

        keysType.add(STRING_TO_TAB(_ID, TYPE_INTEGER+CLEF_PRIMAIRE+AUTOINCREMENT));
        keysType.add(STRING_TO_TAB(NUMERO_MAT, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(NOM, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(POSTNOM, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(PRENOM, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(SEX, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(IMAGE, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(NUMERO_CARD, TYPE_TEXT));

        return keysType;
    }

    @Override
    public String TABLE_NAME() {
        return Livreur.class.getSimpleName();
    }

    @Override
    public String BDD_NAME() {
        return Livreur.class.getSimpleName();
    }

    @Override
    public ContentValues getContentValues() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(NUMERO_MAT, getNumeroMat() );
        contentValues.put(NOM, getNom() );
        contentValues.put(POSTNOM, getPostnom() );
        contentValues.put(PRENOM, getPrenom() );
        contentValues.put(SEX, getSex() );
        contentValues.put(IMAGE, getImage() );
        contentValues.put(NUMERO_CARD, getNumeroCard() );

        return contentValues;
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{_ID,NUMERO_MAT,NOM, POSTNOM,PRENOM, SEX, IMAGE, NUMERO_CARD};
    }
}
