package com.example.we_us_n_our_app;


import com.example.we_us_n_our_app.User;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //defining view objects
    DatePickerDialog picker;
    private EditText editTextName;
    private EditText editTextJoiningDate;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
        }

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        editTextName=(EditText)findViewById(R.id.editTextName);
        editTextJoiningDate=(EditText)findViewById(R.id.editTextJoiningDate);
        editTextJoiningDate.setInputType(InputType.TYPE_NULL);



        editTextJoiningDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editTextJoiningDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){

        //getting email and password from edit texts
        final String email = editTextEmail.getText().toString().trim();
        final String password  = editTextPassword.getText().toString().trim();
        final String name=editTextName.getText().toString().trim();
        final String joiningdate=editTextJoiningDate.getText().toString().trim();
        //final String uid=firebaseAuth.getCurrentUser().getUid();
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter Name",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(joiningdate)){
            Toast.makeText(this,"Please enter Joining Date",Toast.LENGTH_LONG).show();
            return;
        }
        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i("Hey Entered OnComplete","");
                        //checking if success
                        if(task.isSuccessful()){
                            Log.i("Hey Entered issuccess","");
                            User user= new User(name,joiningdate,email);
                            Map map=new HashMap();
                            map.put("name",name);
                            map.put("joining date",joiningdate);
                            map.put("email",email);
                            //map.put("uid",uid);
                            database=FirebaseDatabase.getInstance();

                            myRef=database.getReference("Users");

                            myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(MainActivity.this,"Registration Successful",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

//                            String id=myRef.push().getKey();
//                            myRef.child(id).setValue(user);


                        }
                        else{
                            //display some message here
                            Toast.makeText(MainActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {

        if(view == buttonSignup){
            registerUser();
        }

        if(view == textViewSignin){
            //open login activity when user taps on the already registered textview
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}