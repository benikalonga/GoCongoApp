package com.epsilonpros.gocongomagasin.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.epsilonpros.gocongomagasin.R;
import com.epsilonpros.gocongomagasin.activities.MainActivity;
import com.epsilonpros.gocongomagasin.modeles.Produit;
import com.epsilonpros.gocongomagasin.utils.Log;
import com.epsilonpros.gocongomagasin.views.TextViewTeach;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerProduitAdapter extends RecyclerView.Adapter<RecyclerProduitAdapter.ItemViewHolder> {

    ArrayList<Produit> produits;
    MainActivity mainActivity;

    public RecyclerProduitAdapter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public RecyclerProduitAdapter(MainActivity mainActivity, ArrayList<Produit> produits){
        this.produits = produits;
        this.mainActivity = mainActivity;
    }

    public void setPresences(ArrayList<Produit> produits) {
        this.produits = produits;
        notifyDataSetChanged();
    }

    public ArrayList<Produit> getPresences() {
        return produits;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ViewGroup itemLayout = (ViewGroup) LayoutInflater.from(mainActivity).inflate(R.layout.produit_item, viewGroup, false);
        return new ItemViewHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {


            Produit produit = produits.get(i);

            itemViewHolder.tvNom.setText(produit.getNom());
            itemViewHolder.tvPrix.setText(produit.getPrix());
            itemViewHolder.tvQuantite.setText(produit.getQuantite());

    }

    @Override
    public int getItemCount() {
        return produits != null? produits.size() : 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout linearLayoutInfo, linearLayoutContent;
        public CircleImageView ivLogo;
        public TextViewTeach tvNom, tvQuantite, tvPrix, tvInfo;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayoutContent = itemView.findViewById(R.id.contentProduit);
            linearLayoutInfo = itemView.findViewById(R.id.contentInfo);

            ivLogo = itemView.findViewById(R.id.ivLogo);
            tvNom = itemView.findViewById(R.id.tvNom);
            tvInfo = itemView.findViewById(R.id.tvInfo);
            tvQuantite = itemView.findViewById(R.id.tvQuantite);
            tvPrix = itemView.findViewById(R.id.tvPrix);

        }
    }

}
