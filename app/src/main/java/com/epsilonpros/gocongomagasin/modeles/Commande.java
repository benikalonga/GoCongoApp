package com.epsilonpros.gocongomagasin.modeles;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Amunaso Tyty on 26/10/2018.
 */

public class Commande extends ModelAbstract<Commande> {

    //clefs
    public static String ID = _ID, LIVREUR, PRODUITS;

    private Livreur livreur;
    private Produit produit;
    private int id;

    private String livreurString, produitString;

    public Commande() {
    }

    @Override
    public Commande fromCursor(Cursor cursor) {
        try {
            Commande commande = new Commande();

            commande.setId(cursor.getInt(cursor.getColumnIndex(ID)));

            return commande;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Commande fromJSON(JSONObject jsonObject){
        try {
            Commande commande = new Commande();

            commande.setId(jsonObject.getInt(ID));

            return commande;
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
        keysType.add(STRING_TO_TAB(ID, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(LIVREUR, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(PRODUITS, TYPE_TEXT));

        return keysType;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public String getLivreurString() {
        return livreurString;
    }

    public void setLivreurString(String livreurString) {
        this.livreurString = livreurString;
    }

    public String getProduitString() {
        return produitString;
    }

    public void setProduitString(String produitString) {
        this.produitString = produitString;
    }

    @Override
    public String TABLE_NAME() {
        return Commande.class.getSimpleName();
    }

    @Override
    public String BDD_NAME() {
        return Commande.class.getSimpleName();
    }

    @Override
    public ContentValues getContentValues() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(LIVREUR, getLivreurString() );
        contentValues.put(PRODUITS, getProduitString() );

        return contentValues;
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{_ID,LIVREUR, PRODUITS};
    }
}
