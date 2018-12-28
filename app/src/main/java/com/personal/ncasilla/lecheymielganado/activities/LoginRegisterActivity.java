package com.personal.ncasilla.lecheymielganado.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.nsd.NsdManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.personal.ncasilla.lecheymielganado.R;
import com.personal.ncasilla.lecheymielganado.fragments.LoginFragment;
import com.personal.ncasilla.lecheymielganado.fragments.RegisterFragment;

public class LoginRegisterActivity extends AppCompatActivity implements LoginFragment.OnLoginFragmentListener, RegisterFragment.OnRegisterFragmentListener {

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        loadLoginFragment();
    }

    private void loadLoginFragment(){
        loginFragment = new LoginFragment();
        loginFragment.setFragmentListener(this);
        fragmentChange(loginFragment);
    }

    private void fragmentChange(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment).addToBackStack(null).commit();
    }

    @Override
    public void logIn() {

    }

    @Override
    public void goToRegister() {
        registerFragment = new RegisterFragment();
        registerFragment.setFragmentListener(this);
        fragmentChange(registerFragment);
    }

    @Override
    public void signUp() {

    }

    @Override
    public void onBackPressed() {
        int backStackCount = getFragmentManager().getBackStackEntryCount();
        if(backStackCount == 1){
            finish();
        }else{
            getFragmentManager().popBackStack();
        }
    }

    public void firebaseSignUp(String email, String password ){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("log success", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("log failed", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginRegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
