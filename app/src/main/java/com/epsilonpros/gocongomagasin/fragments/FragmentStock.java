package com.epsilonpros.gocongomagasin.fragments;

import android.Manifest;
import android.net.Uri;
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
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.epsilonpros.gocongomagasin.R;
import com.epsilonpros.gocongomagasin.activities.MainActivity;
import com.epsilonpros.gocongomagasin.adapters.RecyclerProduitAdapter;
import com.epsilonpros.gocongomagasin.interfaces.GeneralInterface;
import com.epsilonpros.gocongomagasin.modeles.Produit;
import com.epsilonpros.gocongomagasin.utils.Constants;
import com.epsilonpros.gocongomagasin.utils.Log;
import com.epsilonpros.gocongomagasin.utils.Utils;
import com.epsilonpros.gocongomagasin.views.FancyButtonBK;
import com.epsilonpros.gocongomagasin.views.FancyLayoutBK;
import com.epsilonpros.gocongomagasin.views.RecyclerViewBK;
import com.epsilonpros.gocongomagasin.views.TextViewTeach;
import com.journeyapps.barcodescanner.Util;

import net.qiujuer.genius.ui.widget.Loading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by fj on 25/02/17.
 */

public class FragmentStock extends Fragment implements FullScreenDialogContent, View.OnClickListener {

    private FullScreenDialogController dialogController;

    FancyButtonBK fcyContacterAdmin, fcyActualiser;
    Loading loadingStock;
    RecyclerViewBK recyclerStock;
    TextViewTeach tvEmpty, tvInfo;
    FrameLayout emptyView;
    FrameLayout linearInfo;
    FancyLayoutBK fcyLayoutActualiser;

    RecyclerProduitAdapter produitAdapter;
    ArrayList<Produit> produitArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_stock, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fcyActualiser = view.findViewById(R.id.fcyActualiser);
        fcyContacterAdmin = view.findViewById(R.id.fcyContacterAdmin);
        fcyLayoutActualiser = view.findViewById(R.id.fcyLayoutActualiser);
        loadingStock = view.findViewById(R.id.loadingStock);
        recyclerStock = view.findViewById(R.id.recylerStock);
        linearInfo = view.findViewById(R.id.contentInfo);
        emptyView = view.findViewById(R.id.emptyView);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        tvInfo = view.findViewById(R.id.tvInfo);

        fcyActualiser.setEnabled(false);

        produitArrayList = new ArrayList<>();

        initCallbacks();
        initRecycler();
        initStock();
    }
    private void initCallbacks(){

        fcyActualiser.setOnClickListener(this);
        fcyContacterAdmin.setOnClickListener(this);
        fcyLayoutActualiser.setOnClickListener(this);

    }
    private void initRecycler(){
        recyclerStock.setEmptyView(emptyView);
        recyclerStock.setItemAnimator(new DefaultItemAnimator());
        recyclerStock.addItemDecoration(new RecyclerViewBK.ItemOffsetDecoration(getActivity(), R.dimen.len_4));
        produitAdapter = new RecyclerProduitAdapter((MainActivity) getActivity(), produitArrayList);
        recyclerStock.setAdapter(produitAdapter);
        recyclerStock.setLayoutManager(new GridLayoutManager(getActivity(),1));

    }
    private void initStock(){
        fcyActualiser.setEnabled(false);
        loadingStock.start();
        loadingStock.setVisibility(View.VISIBLE);

        tvEmpty.setText("Mise à jour du stock...");

        new CountDownTimer(TimeUnit.SECONDS.toMillis(3), TimeUnit.SECONDS.toMillis(1)){
            @Override
            public void onFinish() {
                new Thread(()->{
                    Utils.sendOnServer(FragmentStock.this.getContext(), Request.Method.GET, Constants.URL_PRODUITS, FragmentStock.class.getSimpleName(), null, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            fcyActualiser.setEnabled(true);
                            loadingStock.setVisibility(View.INVISIBLE);
                            loadingStock.stop();
                            loadFromStock(response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            tvEmpty.setText("Aucun produit en stock");
                            fcyActualiser.setEnabled(true);
                            loadingStock.setVisibility(View.INVISIBLE);
                            loadingStock.stop();
                        }
                    });
                }).start();
            }

            @Override
            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }
    private void loadFromStock(String response){

        Utils.decodeJsonElement(response, (GeneralInterface<JSONObject>) t -> {
            getActivity().runOnUiThread(()-> {
                linearInfo.setVisibility(View.VISIBLE);
                tvInfo.setText(t.length + " produits");
                tvInfo.setSelected(true);
            });
            for (JSONObject object : t)
                produitArrayList.add(Produit.fromJSON(object));
        });
        getActivity().runOnUiThread(()->{
            linearInfo.setVisibility(View.VISIBLE);
            produitAdapter.notifyDataSetChanged();
        });
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
        else if (v.getId() == R.id.fcyContacterAdmin){
            contactAdmin();
        }
        else if (v.getId() == R.id.fcyLayoutActualiser){
            actualise();
        }
    }
    private void actualise(){
        initStock();
    }
    private void contactAdmin(){
        if (PermissionsManager.hasPermission(this.getContext(), Manifest.permission.CALL_PHONE))
            Utils.openUri(this.getContext(), Uri.parse("tel:"+"0999349596"));
        else {
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, new PermissionsResultAction() {
                @Override
                public void onGranted() {
                    Utils.openUri(getActivity(), Uri.parse("tel:"+"0999349596"));
                }

                @Override
                public void onDenied(String permission) {

                }
            });
        }
    }
}