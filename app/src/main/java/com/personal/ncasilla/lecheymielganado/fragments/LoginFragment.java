package com.personal.ncasilla.lecheymielganado.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.personal.ncasilla.lecheymielganado.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private OnLoginFragmentListener fragmentListener;
    private EditText usernameText, passwordText;
    private Button loginButton, signupButton;
    private CheckBox remenberMeCheckBox;


    public LoginFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        usernameText = view.findViewById(R.id.username);
        passwordText = view.findViewById(R.id.password);
        loginButton = view.findViewById(R.id.login);
        signupButton = view.findViewById(R.id.signup_button);
        remenberMeCheckBox = view.findViewById(R.id.remenberme);

        loginButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);
        return view;
    }

    public void setFragmentListener(OnLoginFragmentListener fragmentListener) {
        this.fragmentListener = fragmentListener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                fragmentListener.logIn();
                break;
            case R.id.signup_button:
                fragmentListener.goToRegister();
                break;
        }
    }

    public interface OnLoginFragmentListener {
        void logIn();

        void goToRegister();
    }

}
