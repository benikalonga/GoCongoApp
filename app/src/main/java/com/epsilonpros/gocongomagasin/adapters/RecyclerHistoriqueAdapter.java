package com.epsilonpros.gocongomagasin.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.epsilonpros.gocongomagasin.R;
import com.epsilonpros.gocongomagasin.activities.MainActivity;
import com.epsilonpros.gocongomagasin.modeles.Historique;
import com.epsilonpros.gocongomagasin.utils.Utils;
import com.epsilonpros.gocongomagasin.views.TextViewTeach;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerHistoriqueAdapter extends RecyclerView.Adapter<RecyclerHistoriqueAdapter.ItemViewHolder> {

    ArrayList<Historique> historiques;
    MainActivity mainActivity;

    public RecyclerHistoriqueAdapter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public RecyclerHistoriqueAdapter(MainActivity mainActivity, ArrayList<Historique> historiques){
        this.historiques = historiques;
        this.mainActivity = mainActivity;
    }

    public void setPresences(ArrayList<Historique> historiques) {
        this.historiques = historiques;
        notifyDataSetChanged();
    }

    public ArrayList<Historique> getPresences() {
        return historiques;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ViewGroup itemLayout = (ViewGroup) LayoutInflater.from(mainActivity).inflate(R.layout.historique_item, viewGroup, false);
        return new ItemViewHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {

        if (i == 0){
            itemViewHolder.linearLayoutInfo.setVisibility(View.VISIBLE);
            itemViewHolder.linearLayoutContent.setVisibility(View.INVISIBLE);

            itemViewHolder.tvInfo.setText(historiques.size() + " évènements");
            itemViewHolder.tvInfo.setSelected(true);
        }else {
            Historique produit = historiques.get(i);

            boolean b = i % 3 != 0 ? true : false;

            itemViewHolder.ivLogo.setImageResource(b? R.drawable.ic_check_commande : R.drawable.ic_echec);
            itemViewHolder.ivLogo.setFillColorResource(R.color.grey_100);
            itemViewHolder.tvNom.setText(b? "Commande livrée": "Commande annulée");
            itemViewHolder.tvPrix.setText(Utils.getActualFrenchFormat());
            itemViewHolder.tvQuantite.setText(b? "Twiga 144":"Twiga Riz 5");

        }

    }

    @Override
    public int getItemCount() {
        return historiques != null? historiques.size() : 0;
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
