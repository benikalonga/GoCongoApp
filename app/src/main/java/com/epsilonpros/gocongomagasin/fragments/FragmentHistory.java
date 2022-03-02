package com.epsilonpros.gocongomagasin.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.epsilonpros.gocongomagasin.R;
import com.epsilonpros.gocongomagasin.activities.MainActivity;
import com.epsilonpros.gocongomagasin.adapters.RecyclerHistoriqueAdapter;
import com.epsilonpros.gocongomagasin.adapters.RecyclerProduitAdapter;
import com.epsilonpros.gocongomagasin.modeles.Historique;
import com.epsilonpros.gocongomagasin.modeles.Produit;
import com.epsilonpros.gocongomagasin.utils.Constants;
import com.epsilonpros.gocongomagasin.utils.Engine;
import com.epsilonpros.gocongomagasin.views.FancyButtonBK;
import com.epsilonpros.gocongomagasin.views.RecyclerViewBK;
import com.epsilonpros.gocongomagasin.views.TextViewTeach;

import net.qiujuer.genius.ui.widget.Loading;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by fj on 25/02/17.
 */

public class FragmentHistory extends Fragment implements FullScreenDialogContent, View.OnClickListener {

    private FullScreenDialogController dialogController;

    FancyButtonBK fcyActualiser;
    Loading loading;
    RecyclerViewBK recyclerStock;
    TextViewTeach tvEmpty;
    FrameLayout emptyView;

    RecyclerHistoriqueAdapter historiqueAdapter;
    ArrayList<Historique> historiqueArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fcyActualiser = view.findViewById(R.id.fcyActualiser);
        loading = view.findViewById(R.id.loading);
        recyclerStock = view.findViewById(R.id.recylerStock);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        emptyView = view.findViewById(R.id.emptyView);

        fcyActualiser.setEnabled(false);
        historiqueArrayList = new ArrayList<>();

        initCallbacks();
        initRecycler();
        initHistory();
    }
    private void initCallbacks(){

        fcyActualiser.setOnClickListener(this);
        

    }
    private void initRecycler(){
        recyclerStock.setEmptyView(emptyView);
        recyclerStock.setItemAnimator(new DefaultItemAnimator());
        recyclerStock.addItemDecoration(new RecyclerViewBK.ItemOffsetDecoration(getActivity(), R.dimen.len_4));
        historiqueAdapter = new RecyclerHistoriqueAdapter((MainActivity) getActivity(), historiqueArrayList);
        recyclerStock.setAdapter(historiqueAdapter);
        recyclerStock.setLayoutManager(new GridLayoutManager(getActivity(),1));

    }
    private void initHistory(){
        fcyActualiser.setEnabled(false);
        loading.start();
        loading.setVisibility(View.VISIBLE);

        tvEmpty.setText("Chargement...");

        Engine.getResponse(getContext(), Constants.URL_ENTREES, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, null);
        Engine.getResponse(getContext(), Constants.URL_ENTREES, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        },null);

        tvEmpty.setText("Votre historique est vide");
        fcyActualiser.setEnabled(true);
        loading.setVisibility(View.INVISIBLE);
        loading.stop();

        historiqueAdapter.notifyDataSetChanged();
    }
    @Override
    public void onDialogCreated(final FullScreenDialogController dialogController) {
        this.dialogController = dialogController;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dialogController.setConfirmButtonEnabled(false);
    }

    @Override
    public boolean onConfirmClick(FullScreenDialogController dialog) {
        dialog.confirm(null);
        return true;
    }

    @Override
    public boolean onDiscardClick(FullScreenDialogController dialog) {
        dialog.discard();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fcyActualiser){
            actualise();
        }

    }
    private void actualise(){
        initHistory();
    }

}