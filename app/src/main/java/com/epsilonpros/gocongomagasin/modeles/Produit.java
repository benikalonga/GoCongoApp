package com.epsilonpros.gocongomagasin.modeles;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Amunaso Tyty on 26/10/2018.
 */

public class Produit extends ModelAbstract<Produit> {

    //clefs
    public static String ID_PRODUIT = "idproduit",ID = _ID, NOM = "nom_produit", PRIX = "prix", QUANTITE = "quantite_produit";

    private String idProduit, nom, prix, quantite;
    private int id;

    public Produit() {
    }
    public Produit(String nom, String prix, String quantite) {
        setNom(nom);
        setPrix(prix);
        setQuantite(quantite);
    }

    @Override
    public Produit fromCursor(Cursor cursor) {
        try {
            Produit produit = new Produit();

            produit.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            produit.setIdProduit(cursor.getString(cursor.getColumnIndex(ID_PRODUIT)));
            produit.setNom(cursor.getString(cursor.getColumnIndex(NOM)));
            produit.setPrix(cursor.getString(cursor.getColumnIndex(PRIX)));
            produit.setQuantite(cursor.getString(cursor.getColumnIndex(QUANTITE)));

            return produit;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Produit fromJSON(JSONObject jsonObject){
        try {
            Produit produit = new Produit();

            produit.setIdProduit(jsonObject.getString(ID_PRODUIT));
            produit.setNom(jsonObject.getString(NOM));
            produit.setPrix(jsonObject.getString(PRIX));
            produit.setQuantite(jsonObject.getString(QUANTITE));

            return produit;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    @Override
    public ArrayList<String[]> getKeysType() {
        ArrayList<String[]> keysType = new ArrayList<>();

        keysType.add(STRING_TO_TAB(_ID, TYPE_INTEGER+CLEF_PRIMAIRE+AUTOINCREMENT));
        keysType.add(STRING_TO_TAB(NOM, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(PRIX, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(QUANTITE, TYPE_TEXT));

        return keysType;
    }

    @Override
    public String TABLE_NAME() {
        return Produit.class.getSimpleName();
    }

    @Override
    public String BDD_NAME() {
        return Produit.class.getSimpleName();
    }

    @Override
    public ContentValues getContentValues() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOM, getNom() );
        contentValues.put(PRIX, getPrix() );
        contentValues.put(QUANTITE, getQuantite() );

        return contentValues;
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{_ID,NOM, PRIX, QUANTITE};
    }
}
