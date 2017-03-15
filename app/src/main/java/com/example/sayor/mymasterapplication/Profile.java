package com.example.sayor.mymasterapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sayor on 3/8/2017.
 */
public class Profile extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;

    private TextView userEmail;
    private Button logout;

    private DatabaseReference databaseReference;

    private EditText couponName, couponDes;
    private Button saveDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null){
            //already login Start coupon actuvuty
            finish();
            startActivity(new Intent(getApplicationContext(), Login.class));

        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        couponName = (EditText) findViewById(R.id.couponName);
        couponDes = (EditText) findViewById(R.id.couponDescription);
        saveDetails = (Button) findViewById(R.id.save);


        FirebaseUser user = firebaseAuth.getCurrentUser();

        userEmail = (TextView) findViewById(R.id.userEmail);

        userEmail.setText("Welcome " +user.getEmail());

        logout = (Button) findViewById(R.id.buttonLogout);

        //Listener
        logout.setOnClickListener(this);
        saveDetails.setOnClickListener(this);
    }

    private void saveCouponsDetails(){
        String coupounName = couponName.getText().toString().trim();
        String couponDescription = couponDes.getText().toString().trim();

        SaveCouponsDetails saveCouponsDetails = new SaveCouponsDetails(coupounName, couponDescription);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        //save to firebase
        databaseReference.child(user.getUid()).setValue(saveCouponsDetails);

        Toast.makeText(this, "Coupons Details Added to database", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        if (v == logout){
            //logging out
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, Login.class));
        }

        if (v == saveDetails){
            saveCouponsDetails();

        }

    }
}
