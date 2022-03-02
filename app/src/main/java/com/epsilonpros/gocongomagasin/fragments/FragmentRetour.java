package com.epsilonpros.gocongomagasin.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epsilonpros.gocongomagasin.R;

/**
 * Created by fj on 25/02/17.
 */

public class FragmentRetour extends Fragment implements FullScreenDialogContent, View.OnClickListener {

    public FullScreenDialogController dialogController;
    private String jsonCommande;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_retours, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jsonCommande = getArguments().getString(getClass().getSimpleName());

        view.findViewById(R.id.fcyApprouver)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogController.confirm(null);
                    }
                });
        view.findViewById(R.id.fcyAnnuler)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogController.discard();
                    }
                });

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