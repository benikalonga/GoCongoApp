package com.epsilonpros.gocongomagasin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epsilonpros.gocongomagasin.R;

public class FragmentAbout extends Fragment implements FullScreenDialogContent {

    private FullScreenDialogController dialogController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_about, container, false);
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
}