package com.personal.ncasilla.lecheymielganado.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.personal.ncasilla.lecheymielganado.R;
import com.personal.ncasilla.lecheymielganado.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private EditText nameEditText,userEditText,passwordEditText;
    private AutoCompleteTextView groupAutoCompleteTextView;
    private Button registerButton;
    private OnRegisterFragmentListener fragmentListener;


    public RegisterFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        nameEditText = view.findViewById(R.id.regis_name);
        userEditText = view.findViewById(R.id.regis_username);
        passwordEditText = view.findViewById(R.id.regis_password);
        registerButton = view.findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(userEditText.getText().toString(),
                        nameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                fragmentListener.signUp(user);
            }
        });

        return view;
    }

    public void setFragmentListener(OnRegisterFragmentListener fragmentListener) {
        this.fragmentListener = fragmentListener;
    }

    public interface OnRegisterFragmentListener{

        void signUp(User user);
    }

}
