package com.personal.ncasilla.lecheymielganado.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.personal.ncasilla.lecheymielganado.R;
import com.personal.ncasilla.lecheymielganado.fragments.LoginFragment;
import com.personal.ncasilla.lecheymielganado.fragments.RegisterFragment;
import com.personal.ncasilla.lecheymielganado.models.User;

public class LoginRegisterActivity extends AppCompatActivity implements LoginFragment.OnLoginFragmentListener,
        RegisterFragment.OnRegisterFragmentListener {

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private FirebaseAuth mAuth;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference usersData = database.getReference("Users");

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
    public void logIn(User user) {
        fireBaseSignIn(user);
    }

    @Override
    public void goToRegister() {
        registerFragment = new RegisterFragment();
        registerFragment.setFragmentListener(this);
        fragmentChange(registerFragment);
    }

    @Override
    public void signUp(User user) {
        firebaseSignUp(user);
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

    private void firebaseSignUp(final User userToSignIn){
        mAuth.createUserWithEmailAndPassword(userToSignIn.getEmail(),userToSignIn.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("log success", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            usersData.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userToSignIn)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Log.d("log success", "signUpnWithEmail:success");
                                        goToCattleList(userToSignIn);
                                    }else{
                                        Toast.makeText(LoginRegisterActivity.this, task.getException().getLocalizedMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("log failed", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginRegisterActivity.this, task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void fireBaseSignIn(final User userToSignIn){
        mAuth.signInWithEmailAndPassword(userToSignIn.getEmail(), userToSignIn.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            usersData.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    DataSnapshot snapshot = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    User user = snapshot.getValue(User.class);

                                    goToCattleList(user);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("log failed", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginRegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void goToCattleList(User user){
        Intent intent = new Intent(this,CattleListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("User",user);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)ev.getRawX(), (int)ev.getRawY())) {
                    v.clearFocus();
                    hideKeyBoard(v);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void hideKeyBoard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }
}
