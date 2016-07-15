package com.lpoezy.nexpa.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.lpoezy.nexpa.EditProfileActivity;
import com.lpoezy.nexpa.MainActivity;
import com.lpoezy.nexpa.R;
import com.lpoezy.nexpa.utils.AccountManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogoutConfirmationDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogoutConfirmationDialog extends DialogFragment {

    public static final String TAG = LogoutConfirmationDialog.class.getSimpleName();

    public LogoutConfirmationDialog() {
        // Required empty public constructor
    }


    public static LogoutConfirmationDialog newInstance() {
        LogoutConfirmationDialog fragment = new LogoutConfirmationDialog();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_logout_confirmation, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        ((Button)v.findViewById(R.id.btn_yes)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                AccountManager am = new AccountManager(getActivity());
                am.setLoggedin(false);

                Intent act = new Intent(getActivity(), MainActivity.class);
                act.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(act);

            }
        });

        ((Button)v.findViewById(R.id.btn_no)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

               dismiss();

            }
        });

        return v;
    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//
//        return new AlertDialog.Builder(getActivity())
//                //.setIcon(R.drawable.ic_nexpa)
//                .setTitle("Logout Account")
//                .setMessage("Are you sure you want to logout?")
//                .setPositiveButton(R.string.alert_dialog_ok,
//                        new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int whichButton) {
//
//                                M_SignupScreen am = new M_SignupScreen(getActivity());
//                                am.setLoggedin(false);
//
//                                Intent act = new Intent(getActivity(), MainActivity.class);
//                                act.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                                getActivity().startActivity(act);
//
//                            }
//                        }
//                )
//                .setNegativeButton(R.string.alert_dialog_cancel,
//                        new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int whichButton) {
//
//
//                            }
//                        }
//                )
//                .create();
//    }

}
