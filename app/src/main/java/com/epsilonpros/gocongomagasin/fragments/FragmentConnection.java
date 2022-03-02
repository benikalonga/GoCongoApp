package com.epsilonpros.gocongomagasin.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.FadeOutAnimation;
import com.easyandroidanimations.library.ScaleInAnimation;
import com.epsilonpros.gocongomagasin.R;
import com.epsilonpros.gocongomagasin.modeles.Magasinier;
import com.epsilonpros.gocongomagasin.utils.Constants;
import com.epsilonpros.gocongomagasin.utils.Engine;
import com.epsilonpros.gocongomagasin.utils.Log;
import com.epsilonpros.gocongomagasin.views.FancyButtonBK;

import net.qiujuer.genius.ui.widget.EditText;
import net.qiujuer.genius.ui.widget.Loading;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.epsilonpros.gocongomagasin.utils.Plus.hasEmptyText;

public class FragmentConnection extends Fragment implements FullScreenDialogContent, View.OnClickListener {

    private FullScreenDialogController dialogController;

    FancyButtonBK fcyAnnuler, fcySeConnecter, fcyVideId, fcyVideMdp;
    EditText editTextId, editTextMdp;

    Loading loadingConnection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_connection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fcyAnnuler = view.findViewById(R.id.fcyAnnuler);
        fcySeConnecter = view.findViewById(R.id.fcySeConnecter);
        fcyVideId = view.findViewById(R.id.fcyVideId);
        fcyVideMdp = view.findViewById(R.id.fcyVideMdp);
        editTextId = view.findViewById(R.id.editId);
        editTextMdp = view.findViewById(R.id.editMdp);
        loadingConnection = view.findViewById(R.id.loadingConnect);

        fcyAnnuler.setEnabled(false);
        fcySeConnecter.setEnabled(false);

        initCallbacks();
    }
    private void initCallbacks(){
        fcySeConnecter.setOnClickListener(this);
        fcyAnnuler.setOnClickListener(this);
        fcyVideId.setOnClickListener(this);
        fcyVideMdp.setOnClickListener(this);

        editTextMdp.addTextChangedListener(new TextWatch(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);

                if (hasEmptyText(editTextId) && hasEmptyText(editTextMdp)){
                    fcySeConnecter.setEnabled(false);
                    fcyAnnuler.setEnabled(false);
                }
                else {
                    fcySeConnecter.setEnabled(true);
                    fcyAnnuler.setEnabled(true);
                }

                //si mdp vide
                if (TextUtils.isEmpty(s)){

                    fcyVideMdp.setEnabled(false);
                    if (fcyVideMdp.getVisibility() == View.VISIBLE)
                        new FadeOutAnimation(fcyVideMdp)
                                .setDuration(Animation.DURATION_SHORT)
                                .animate();
                }
                else {

                    fcyVideMdp.setEnabled(true);
                    if (fcyVideMdp.getVisibility() != View.VISIBLE)
                        new ScaleInAnimation(fcyVideMdp)
                            .setInterpolator(new AnticipateOvershootInterpolator())
                            .setDuration(Animation.DURATION_SHORT)
                            .animate();
                }
            }
        });

        editTextId.addTextChangedListener(new TextWatch(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);

                if (hasEmptyText(editTextId) && hasEmptyText(editTextMdp)){
                    fcySeConnecter.setEnabled(false);
                    fcyAnnuler.setEnabled(false);
                }
                else {
                    fcySeConnecter.setEnabled(true);
                    fcyAnnuler.setEnabled(true);
                }

                if (TextUtils.isEmpty(s)){
                    fcyVideId.setEnabled(false);
                    if (fcyVideId.getVisibility() == View.VISIBLE)
                        new FadeOutAnimation(fcyVideId)
                            .setDuration(Animation.DURATION_SHORT)
                            .animate();
                }
                else {
                    fcyVideId.setEnabled(true);
                    if (fcyVideId.getVisibility() != View.VISIBLE)
                        new ScaleInAnimation(fcyVideId)
                            .setInterpolator(new AnticipateOvershootInterpolator())
                            .setDuration(Animation.DURATION_SHORT)
                            .animate();
                }
            }
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
        if (v.getId() == R.id.fcyAnnuler){
            annule();
        }
        else if (v.getId() == R.id.fcySeConnecter){
            connecte();
        }
        else if (v.getId() == R.id.fcyVideId){
            editTextId.setText("");
        }
        else if (v.getId() == R.id.fcyVideMdp){
            editTextMdp.setText("");
        }
    }
    private void annule(){
        if (!TextUtils.isEmpty(editTextId.getText()) || !TextUtils.isEmpty(editTextMdp.getText())){
            new AlertDialog.Builder(getContext())
                    .setTitle("Annuler la connexion")
                    .setMessage("Voulez-vous vraiment annuler la connexion?\nAucun service ne sera disponible")
                    .setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialogController.discard();
                        }
                    })
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Nothing to do
                        }
                    }).show();
        }
        else if (loadingConnection.isRunning()){
            loadingConnection.stop();
            new FadeOutAnimation(loadingConnection)
                    .setDuration(Animation.DURATION_DEFAULT)
                    .animate();
        }
        else {
            dialogController.discard();
        }
    }
    private void connecte(){
        String id = editTextId.getText().toString();
        String mdp = editTextMdp.getText().toString();

        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(mdp)){
            Snackbar.make(fcySeConnecter,"Veuilez entrer des informations correctes",Snackbar.LENGTH_LONG).show();
        }
        else {
            loadingConnection.start();

            new ScaleInAnimation(loadingConnection)
                    .setInterpolator(new AnticipateOvershootInterpolator())
                    .setDuration(Animation.DURATION_SHORT)
                    .setListener(new AnimationListener() {
                        @Override
                        public void onAnimationEnd(Animation animation) {

                            ArrayList<String[]> params = new ArrayList<>();
                            params.add(new String[]{"connect"});
                            params.add(new String[]{Magasinier.NUMERO_MAT, id});
                            params.add(new String[]{Magasinier.MOTDEPASS, mdp});

                            Engine.getResponse(FragmentConnection.this.getContext(), Constants.URL_MAGASINIERS, params, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        Magasinier magasinier = Magasinier.fromJSON(new JSONObject(response));

                                        Bundle bundle = new Bundle();
                                        bundle.putString(FullScreenDialogFragment.class.getSimpleName(), FragmentConnection.class.getSimpleName());
                                        bundle.putString(Magasinier.NOM, magasinier.getNom());
                                        bundle.putString(Magasinier.POSTNOM, magasinier.getPostnom());
                                        bundle.putString(Magasinier.PRENOM, magasinier.getPrenom());
                                        bundle.putString(Magasinier.PHOTO, magasinier.getPhoto());
                                        bundle.putString(Magasinier.MOTDEPASS, magasinier.getMotdepass());
                                        bundle.putString(Magasinier.ID_MAGASINIER, magasinier.getIdMagasinier());
                                        bundle.putString(Magasinier.SEXE, magasinier.getSexe());
                                        bundle.putString(Magasinier.NUMERO_MAT, magasinier.getNumeroMat());

                                        dialogController.confirm(bundle);

                                    } catch (Exception e) {
                                        e.printStackTrace();

                                        if (!Boolean.getBoolean(response)){
                                            Snackbar.make(fcySeConnecter, "Les infos entrées ne sont pas reconnues", Snackbar.LENGTH_LONG).show();
                                        }
                                        else {
                                            Snackbar.make(fcySeConnecter, "Une erreur est survenue", Snackbar.LENGTH_LONG).show();
                                        }
                                    }

                                    loadingConnection.stop();
                                    new FadeOutAnimation(loadingConnection)
                                            .animate();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.i(error.getMessage());

                                    loadingConnection.stop();
                                    new FadeOutAnimation(loadingConnection)
                                            .animate();

                                    Snackbar.make(fcySeConnecter, "Echec de connexion", Snackbar.LENGTH_LONG).show();
                                }
                            });
                        }
                    })
                    .animate();
        }
    }

    class TextWatch implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}