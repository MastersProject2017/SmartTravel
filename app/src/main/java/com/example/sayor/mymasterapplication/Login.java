package com.example.sayor.mymasterapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by sayor on 3/8/2017.
 */
public class Login extends AppCompatActivity implements View.OnClickListener{
    private Button buttonSignin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView signup;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            //already login Start coupon actuvuty
            finish();
            startActivity(new Intent(getApplicationContext(), Profile.class));

        }

        buttonSignin = (Button) findViewById(R.id.buttonSignin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        signup = (TextView) findViewById(R.id.textiewSignup);

        progressDialog = new ProgressDialog(this);

        //Setting on-click listener
        buttonSignin.setOnClickListener(this);
        signup.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v == buttonSignin){
            userLogin();
        }

        if (v == signup){
            finish();
            startActivity(new Intent(this, Auth.class));
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email address", Toast.LENGTH_SHORT).show();
            // Stop execution
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            // Stop execution
            return;
        }

        progressDialog.setMessage("Please Wait!! Login in progress");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            //start profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                        }else{
                            Toast.makeText(Login.this, "Login Unsuccessfull. Please try again!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}
