package com.lpoezy.nexpa.screens;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.lpoezy.nexpa.HomeTabActivity;
import com.lpoezy.nexpa.R;
import com.lpoezy.nexpa.models.M_Broadcast;
import com.lpoezy.nexpa.models.OnUpdateScreenListener;
import com.lpoezy.nexpa.service.MyPubNubService;
import com.lpoezy.nexpa.utils.AccountManager;

import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class BroadcastsScreen extends Fragment implements OnUpdateScreenListener {

    private Toolbar mToolbar;

    public BroadcastsScreen() {
        // Required empty public constructor
    }


    public static BroadcastsScreen newInstance() {
        BroadcastsScreen fragment = new BroadcastsScreen();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = (View)inflater.inflate(R.layout.fragment_broadcasts_screen, container, false);
        mToolbar = (Toolbar)v.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        android.support.v7.app.ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        ((TextView)mToolbar.findViewById(R.id.tab_title)).setText("Your Audience");
        List<String> age = new ArrayList<String>();
        for(int i=0;i<=75;++i){
            age.add(Integer.toString(i));
        }

        MaterialSpinner spinnerMin = (MaterialSpinner) v.findViewById(R.id.spinner_min_age);
        spinnerMin.setItems(age);
        spinnerMin.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        MaterialSpinner spinnerMax = (MaterialSpinner) v.findViewById(R.id.spinner_max_age);
        spinnerMax.setItems(age);
        spinnerMax.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        final EditText etBroadcastMsg = (EditText) v.findViewById(R.id.et_broadcast_msg);




        ((Button)v.findViewById(R.id.btn_share_broadcast)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                final String broadcastMsg = etBroadcastMsg.getText().toString();
                HomeTabActivity act = (HomeTabActivity) getActivity();
                if(act.isServiceConnected()){

                    AccountManager am = new AccountManager(getActivity());
                    String uname = am.getUserName();
                    String email = am.getEmail();

                    //save message to couchbase
                    M_Broadcast broadcast = new M_Broadcast(getActivity(), broadcastMsg, uname, 0,"Tondo Manila", "Y");
                    String msgId = broadcast.write();


                    act.getService().addOnPubNubCallbackListener(new MyPubNubService.OnPubNubCallbackListener() {
                        @Override
                        public void onMessageReceived(Object object) {

                        }

                        @Override
                        public void onMessageSent(Object object) {
                            //Crouton.showText(getActivity(), "Your message was sent successfully. Thanks.", Style.CONFIRM);
                            etBroadcastMsg.setText("");
                        }

                        @Override
                        public void onMessageSendingFailed() {

                        }
                    });

                    act.getService().publishTo(email, msgId);

                }
            }
        });

        return v;
    }

    @Override
    public void onUpdateScreen() {

    }
}
