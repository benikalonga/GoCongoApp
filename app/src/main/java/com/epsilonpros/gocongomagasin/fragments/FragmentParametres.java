package com.epsilonpros.gocongomagasin.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epsilonpros.gocongomagasin.R;
import com.epsilonpros.gocongomagasin.views.FancyButtonBK;
import com.epsilonpros.gocongomagasin.views.RecyclerViewBK;
import com.epsilonpros.gocongomagasin.views.TextViewTeach;

import net.qiujuer.genius.ui.widget.Loading;

import java.util.concurrent.TimeUnit;

/**
 * Created by fj on 25/02/17.
 */

public class FragmentParametres extends Fragment implements FullScreenDialogContent, View.OnClickListener {

    private FullScreenDialogController dialogController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_parametres, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initCallbacks();
        initDefault();
    }
    private void initCallbacks(){

    }
    private void initDefault(){

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


    }

}