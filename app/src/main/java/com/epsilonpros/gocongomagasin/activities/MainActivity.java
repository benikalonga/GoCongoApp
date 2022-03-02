package com.epsilonpros.gocongomagasin.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.anthonycr.grant.PermissionsManager;
import com.apg.mobile.roundtextview.RoundTextView;
import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.FadeInAnimation;
import com.easyandroidanimations.library.FadeOutAnimation;
import com.easyandroidanimations.library.ScaleInAnimation;
import com.easyandroidanimations.library.ScaleOutAnimation;
import com.epsilonpros.gocongomagasin.R;
import com.epsilonpros.gocongomagasin.fragments.FragmentAbout;
import com.epsilonpros.gocongomagasin.fragments.FragmentCommande;
import com.epsilonpros.gocongomagasin.fragments.FragmentCommandeDetail;
import com.epsilonpros.gocongomagasin.fragments.FragmentConnection;
import com.epsilonpros.gocongomagasin.fragments.FragmentHistory;
import com.epsilonpros.gocongomagasin.fragments.FragmentListeCommande;
import com.epsilonpros.gocongomagasin.fragments.FragmentParametres;
import com.epsilonpros.gocongomagasin.fragments.FragmentRetour;
import com.epsilonpros.gocongomagasin.fragments.FragmentStock;
import com.epsilonpros.gocongomagasin.fragments.FullScreenDialogFragment;
import com.epsilonpros.gocongomagasin.modeles.Magasinier;
import com.epsilonpros.gocongomagasin.utils.Constants;
import com.epsilonpros.gocongomagasin.utils.Engine;
import com.epsilonpros.gocongomagasin.utils.Keys;
import com.epsilonpros.gocongomagasin.utils.Log;
import com.epsilonpros.gocongomagasin.utils.Plus;
import com.epsilonpros.gocongomagasin.utils.Utils;
import com.epsilonpros.gocongomagasin.views.FancyLayoutBK;
import com.epsilonpros.gocongomagasin.views.TextViewTeach;

import net.qiujuer.genius.ui.widget.Loading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
                                                                FullScreenDialogFragment.OnConfirmListener,
                                                                FullScreenDialogFragment.OnDiscardListener{

    Toolbar toolbar;

    FancyLayoutBK fcyConnect, fcyStock, fcyHistory, fcyListeCommande, fcyRetours;
    ImageView ivConnect;
    TextViewTeach tvConnect;
    Loading loadingConnect;

    FullScreenDialogFragment dialogFragment, dialogFragmentCommande;
    RoundTextView statutConnexion;

    BroadcastReceiver receiverConnexion;
    Snackbar snackbar;

    AlertDialog alertCommande;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initPermissions();
        initViews();
        initCallbacks();
        initProfils();

        if (savedInstanceState != null) {
            dialogFragment = (FullScreenDialogFragment) getSupportFragmentManager().findFragmentByTag(FullScreenDialogFragment.class.getSimpleName());
            if (dialogFragment != null) {
                dialogFragment.setOnConfirmListener(this);
                dialogFragment.setOnDiscardListener(this);
            }
        }

    }
    private void initPermissions(){
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this,null);
    }
    private void initViews(){
        fcyConnect = findViewById(R.id.fcyConnection);
        fcyStock = findViewById(R.id.fcyStock);
        fcyHistory = findViewById(R.id.fcyHistory);
        fcyListeCommande = findViewById(R.id.fcyCommandes);
        fcyRetours = findViewById(R.id.fcyRetours);

        statutConnexion = findViewById(R.id.statutConnexion);

        ivConnect = findViewById(R.id.ivConnect);
        tvConnect = findViewById(R.id.tvConnect);
        loadingConnect = findViewById(R.id.loadingConnect);
    }
    private void initCallbacks(){
        fcyConnect.setOnClickListener(this);
        fcyStock.setOnClickListener(this);
        fcyHistory.setOnClickListener(this);
        fcyListeCommande.setOnClickListener(this);
        fcyRetours.setOnClickListener(this);
    }
    private void initProfils(){
        String id = Plus.getStringPref(this,Magasinier.ID_MAGASINIER);

        //si existe
        if (!Plus.isEmptyText(id)) {
            enableAll();
        }else {
            disableAll();
        }
    }
    @Override
    public void onConfirm(@Nullable Bundle result) {
        dialogFragment.dismiss();

        if (result != null){

            String fragment = result.getString(FullScreenDialogFragment.class.getSimpleName());

            //on vien de Connexion
            if (fragment!= null && fragment.equals(FragmentConnection.class.getSimpleName())){
                Plus.sharedPreferencePut(this, Magasinier.NOM, result.getString(Magasinier.NOM), Keys.TYPE_STRING);
                Plus.sharedPreferencePut(this, Magasinier.POSTNOM, result.getString(Magasinier.POSTNOM), Keys.TYPE_STRING);
                Plus.sharedPreferencePut(this, Magasinier.PRENOM, result.getString(Magasinier.PRENOM), Keys.TYPE_STRING);
                Plus.sharedPreferencePut(this, Magasinier.MOTDEPASS, result.getString(Magasinier.MOTDEPASS), Keys.TYPE_STRING);
                Plus.sharedPreferencePut(this, Magasinier.PHOTO, result.getString(Magasinier.PHOTO), Keys.TYPE_STRING);
                Plus.sharedPreferencePut(this, Magasinier.SEXE, result.getString(Magasinier.SEXE), Keys.TYPE_STRING);
                Plus.sharedPreferencePut(this, Magasinier.ID_MAGASINIER, result.getString(Magasinier.ID_MAGASINIER), Keys.TYPE_STRING);
                Plus.sharedPreferencePut(this, Magasinier.NUMERO_MAT, result.getString(Magasinier.NUMERO_MAT), Keys.TYPE_STRING);
            }

            enableAll();

        }
    }
    private void enableAll(){
        fcyListeCommande.setEnabled(true);
        fcyHistory.setEnabled(true);
        fcyStock.setEnabled(true);
        tvConnect.setText(Plus.getStringPref(this, Magasinier.PRENOM)+" "+Plus.getStringPref(this, Magasinier.NOM));
        tvConnect.setPolice(getResources().getString(R.string.police_roboto_black));

        new CountDownTimer(TimeUnit.SECONDS.toMillis(2), TimeUnit.SECONDS.toMillis(1)){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                new FadeInAnimation(statutConnexion)
                        .animate();
            }
        }.start();
    }
    private void disableAll(){
        fcyListeCommande.setEnabled(false);
        fcyHistory.setEnabled(false);
        fcyStock.setEnabled(false);
        tvConnect.setText("Se connecter");
        tvConnect.setPolice(getResources().getString(R.string.police_roboto_light));


        new FadeOutAnimation(statutConnexion)
                .animate();
    }

    @Override
    public void onDiscard() {
        dialogFragment.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (dialogFragment != null && dialogFragment.isAdded()) {
            dialogFragment.onBackPressed();
        }else  if (snackbar!= null && snackbar.isShown())
            snackbar.dismiss();
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            openDialog(getResources().getString(R.string.action_about),"",FragmentAbout.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.fcyConnection:{
                handleConnexionClik();
                break;
            }
            case R.id.fcyStock:{
                handleStockClick();
                break;
            }
            case R.id.fcyHistory:{
                handleHistoryClick();
                break;
            }
            case R.id.fcyCommandes:{
                handleCommandesClick();
                break;
            }
            case R.id.fcyRetours:{
                handleRetoursClick();
                break;
            }
        }
    }
    private void handleConnexionClik(){
        String id = Plus.getStringPref(this,Magasinier.ID_MAGASINIER);

        if (Plus.isEmptyText(id)){
            openDialog("", "", FragmentConnection.class);
        }
        else {
            snackbar = Snackbar.make(fcyConnect, "Vous allez être déconnecté",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Confirmer", (view)->{

                        loadingConnect.start();
                        new ScaleInAnimation(loadingConnect)
                                .setInterpolator(new AnticipateOvershootInterpolator())
                                .setDuration(Animation.DURATION_SHORT)
                                .setListener((animation -> {
                                    ArrayList<String[]> list = new ArrayList<>();
                                    list.add(new String[]{"deconnect"});
                                    list.add(new String[]{Magasinier.NUMERO_MAT, Plus.getStringPref(MainActivity.this, Magasinier.NUMERO_MAT)});

                                    Log.i(Plus.getStringPref(MainActivity.this, Magasinier.NUMERO_MAT));

                                    Engine.getResponse(MainActivity.this, Constants.URL_MAGASINIERS, list,
                                            response -> {

                                                Log.i(response);

                                                if (Boolean.parseBoolean(response)){
                                                    new FadeOutAnimation(statutConnexion)
                                                            .animate();
                                                    Plus.sharedPreferenceClear(MainActivity.this,Magasinier.ID_MAGASINIER);
                                                    initProfils();
                                                }
                                                else {
                                                    snackbar = Snackbar.make(fcyConnect, "Echec de déconnexion",Snackbar.LENGTH_LONG);
                                                    snackbar.show();
                                                }
                                                loadingConnect.stop();

                                                new ScaleOutAnimation(loadingConnect)
                                                        .setDuration(Animation.DURATION_SHORT)
                                                        .animate();

                                    },  error -> {
                                            error.printStackTrace();

                                            loadingConnect.stop();
                                            new ScaleOutAnimation(loadingConnect)
                                                    .setDuration(Animation.DURATION_SHORT)
                                                    .animate();

                                            snackbar = Snackbar.make(fcyConnect, "Problème d\'internet",Snackbar.LENGTH_LONG);
                                            snackbar.show();

                                        });
                                })).animate();


                    })
                    .setActionTextColor(Color.WHITE);
            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();

            if (snackbar.isShown()){
                new CountDownTimer(TimeUnit.SECONDS.toMillis(10), TimeUnit.SECONDS.toMillis(1)){
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        if (snackbar.isShown())
                            snackbar.dismiss();
                    }
                }.start();
            }
        }
    }

    private void handleStockClick(){
        openDialog("", "", FragmentStock.class);
    }
    private void handleHistoryClick(){
        openDialog("", "", FragmentHistory.class);
    }
    private void handleCommandesClick(){
        openDialog("", "", FragmentListeCommande.class);
    }
    private void handleRetoursClick(){
        openDialog("", "", FragmentRetour.class);
    }
    private void openDialog(String titre, String confirmButton, Class<? extends Fragment> content){
        dialogFragment = new FullScreenDialogFragment.Builder(MainActivity.this)
                .setTitle(titre)
                .setConfirmButton(confirmButton)
                .setOnConfirmListener(MainActivity.this)
                .setOnDiscardListener(MainActivity.this)
                .setContent(content, null)
                .build();
        dialogFragment.show(getSupportFragmentManager(), content.getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (receiverConnexion == null) {
            receiverConnexion = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    if(intent.getExtras() != null)
                    {
                        NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
                        if(ni != null && ni.getState() == NetworkInfo.State.CONNECTED)
                        {
                            changeStatutInternet(R.color.green_600);
                        }else
                            changeStatutInternet(R.color.red_600);

                    }

                    if(intent.getExtras().getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE))
                    {
                        changeStatutInternet(R.color.red_600);
                    }

                }
            };
        }
        registerReceiver(receiverConnexion, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        similateCommande();
    }
    private void similateCommande(){
        new CountDownTimer(TimeUnit.MINUTES.toMillis(5), TimeUnit.SECONDS.toMillis(1)){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                try {
                    JSONObject jsonAuteur = new JSONObject();
                    jsonAuteur.put("Nom", "Kalonga");
                    jsonAuteur.put("Postnom", "Kalonga");
                    jsonAuteur.put("Prenom", "Beni");
                    jsonAuteur.put("Id", "2015020059");
                    jsonAuteur.put("Fonction", "Livreur");

                    JSONObject jsonProduit = new JSONObject();
                    jsonAuteur.put("id", Plus.getRandomInt(1200, 3400)+"");
                    jsonAuteur.put("quantite", "1500");
                    jsonAuteur.put("date", Utils.getActualDate());

                    JSONObject jsonCommande = new JSONObject();
                    jsonCommande.put("auteur", jsonAuteur.toString());
                    jsonCommande.put("produits", jsonProduit.toString());

                    receiveCommande(jsonCommande.toString());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                this.start();
            }
        }.start();
    }
    private void receiveCommande(String fromJSON){

        if (Plus.getStringPref(this, Magasinier.ID_MAGASINIER)!= null){
            if (dialogFragmentCommande == null || !dialogFragmentCommande.isAdded()){
                try {

                    Bundle bundle = new Bundle();
                    bundle.putString(FragmentCommande.class.getSimpleName(), fromJSON);

                    dialogFragmentCommande = new FullScreenDialogFragment.Builder(MainActivity.this)
                            .setTitle("Retour ")
                            .setFullScreen(false)
                            .setConfirmButton("+ Detail")
                            .setOnConfirmListener(result -> {
                                dialogFragmentCommande.dismiss();
                                //showCommandeDetail(bundle);
                            })
                            .setOnDiscardListener(() -> dialogFragmentCommande.dismiss())
                            .setContent(FragmentCommande.class, bundle)
                            .build();
                    dialogFragmentCommande.show(getSupportFragmentManager(), FragmentCommande.class.getSimpleName());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void showCommandeDetail(Bundle bundle){
        dialogFragmentCommande = new FullScreenDialogFragment.Builder(MainActivity.this)
                .setTitle("Nouvelle commande")
                .setFullScreen(true)
                .setOnConfirmListener(result1 -> dialogFragmentCommande.dismiss())
                .setOnDiscardListener(() -> dialogFragmentCommande.dismiss())
                .setContent(FragmentCommandeDetail.class, bundle)
                .build();
        dialogFragmentCommande.show(getSupportFragmentManager(), FragmentCommandeDetail.class.getSimpleName());

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiverConnexion!= null)
            unregisterReceiver(receiverConnexion);
    }

    private void changeStatutInternet(@ColorRes int color){
        statutConnexion.setBgColor(getResources().getColor(color));
    }

}
