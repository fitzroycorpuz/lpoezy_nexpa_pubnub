package com.lpoezy.nexpa.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.lpoezy.nexpa.MainActivity;
import com.lpoezy.nexpa.R;
import com.lpoezy.nexpa.utils.AccountManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TryAgainDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TryAgainDialog extends DialogFragment {

    public static final String TAG = TryAgainDialog.class.getSimpleName();
    private static final String MESSAGE = "MESSAGE";

    public TryAgainDialog() {
        // Required empty public constructor
    }


    public static TryAgainDialog newInstance(String message) {
        TryAgainDialog fragment = new TryAgainDialog();
        Bundle args = new Bundle();
        args.putString(MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_try_again, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        String message = getArguments().getString(MESSAGE);

        ((TextView)v.findViewById(R.id.tv_message)).setText(message);

        ((Button)v.findViewById(R.id.btn_ok)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

               dismiss();

            }
        });

        return v;
    }



}
