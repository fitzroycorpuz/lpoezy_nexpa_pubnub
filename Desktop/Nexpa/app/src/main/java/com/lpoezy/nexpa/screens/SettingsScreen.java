package com.lpoezy.nexpa.screens;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpoezy.nexpa.R;
import com.lpoezy.nexpa.utils.DividerItemDecoration;


public class SettingsScreen extends Fragment {

    private Toolbar mToolbar;
    private RecyclerView mRvSettings;
    private SettingsAdapter adapter;

    public SettingsScreen() {
        // Required empty public constructor
    }


    public static SettingsScreen newInstance() {
        SettingsScreen fragment = new SettingsScreen();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = (View)inflater.inflate(R.layout.fragment_settings_screen, container, false);

        mToolbar = (Toolbar)v.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        android.support.v7.app.ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        ((TextView)mToolbar.findViewById(R.id.tab_title)).setText("Settings");

        mRvSettings= (RecyclerView) v.findViewById(R.id.rv_settings);


        mRvSettings.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvSettings.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));



        adapter = new SettingsAdapter(getActivity());
        mRvSettings.setAdapter(adapter);

        return v;
    }

    private class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

        private final Context context;
        private LayoutInflater inflater;

        final String[] items = getResources().getStringArray(R.array.settings);

        public SettingsAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);

            this.context = context;

        }


        @Override
        public int getItemCount() {

            return items.length;
        }

        @Override
        public void onBindViewHolder(final ViewHolder vh, int position) {

            final String item = items[position];
            vh.tvItem.setText(item);

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            View itemView = inflater.inflate(R.layout.custom_settings_view, parent, false);
            return new ViewHolder(itemView);
        }

        class ViewHolder extends RecyclerView.ViewHolder  {


            TextView tvItem;

            public ViewHolder(View view) {
                super(view);

                tvItem = (TextView) view.findViewById(R.id.tv_settings_item);


            }

        }
    }

}
