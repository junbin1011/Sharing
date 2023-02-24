package com.jkb.junbin.sharing.feature.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.sharing.R;


public class AccountFragment extends Fragment {

    AccountController accountController = new AccountController();
    TextView tvName;
    Button btnLogOut;

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_account, container, false);
        tvName = inflate.findViewById(R.id.name);
        btnLogOut = inflate.findViewById(R.id.logout);
        tvName.setText( accountController.getCurrentUserInfo().username);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        return inflate;
    }


}