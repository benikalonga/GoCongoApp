package com.epsilonpros.gocongomagasin.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.epsilonpros.gocongomagasin.R;
import com.epsilonpros.gocongomagasin.activities.MainActivity;
import com.epsilonpros.gocongomagasin.modeles.Commande;
import com.epsilonpros.gocongomagasin.modeles.Commande;
import com.epsilonpros.gocongomagasin.utils.Utils;
import com.epsilonpros.gocongomagasin.views.TextViewTeach;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerCommandeAdapter extends RecyclerView.Adapter<RecyclerCommandeAdapter.ItemViewHolder> {

    ArrayList<Commande> commandes;
    MainActivity mainActivity;

    public RecyclerCommandeAdapter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public RecyclerCommandeAdapter(MainActivity mainActivity, ArrayList<Commande> commandes){
        this.commandes = commandes;
        this.mainActivity = mainActivity;
    }

    public void setPresences(ArrayList<Commande> commandes) {
        this.commandes = commandes;
        notifyDataSetChanged();
    }

    public ArrayList<Commande> getPresences() {
        return commandes;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ViewGroup itemLayout = (ViewGroup) LayoutInflater.from(mainActivity).inflate(R.layout.commande_item, viewGroup, false);
        return new ItemViewHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {

        if (i == 0){
            itemViewHolder.linearLayoutInfo.setVisibility(View.VISIBLE);
            itemViewHolder.linearLayoutContent.setVisibility(View.INVISIBLE);

            itemViewHolder.tvInfo.setText(commandes.size() + " commandes");
            itemViewHolder.tvInfo.setSelected(true);
        }else {
            Commande commande = commandes.get(i);

            boolean b = i % 3 != 0 ? true : false;


        }

    }

    @Override
    public int getItemCount() {
        return commandes != null? commandes.size() : 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout linearLayoutInfo, linearLayoutContent;
        public CircleImageView ivLogo;
        public TextViewTeach tvInfo;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayoutContent = itemView.findViewById(R.id.contentProduit);
            linearLayoutInfo = itemView.findViewById(R.id.contentInfo);

            ivLogo = itemView.findViewById(R.id.ivLogo);
            tvInfo = itemView.findViewById(R.id.tvInfo);

        }
    }

}
