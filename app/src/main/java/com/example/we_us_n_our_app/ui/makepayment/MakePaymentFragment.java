package com.example.we_us_n_our_app.ui.makepayment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.we_us_n_our_app.MenuActivity;
import com.example.we_us_n_our_app.ProfileActivity;
import com.example.we_us_n_our_app.R;
import com.example.we_us_n_our_app.Transaction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MakePaymentFragment extends Fragment {

    DatePickerDialog picker;
    private EditText editTextDate;
    private EditText editTextAmount;
    private Button cnfrmbtn;

    private MakePaymentViewModel makepaymentViewModel;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    /*
        private Context mContext;


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mContext=context;
    }
    */

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        makepaymentViewModel =
                ViewModelProviders.of(this).get(MakePaymentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_make_payment, container, false);
        database= FirebaseDatabase.getInstance();
        myRef=database.getReference("Pending Transactions");
        firebaseAuth = FirebaseAuth.getInstance();
        editTextDate=(EditText) root.findViewById(R.id.editTextPaymentDate);
        editTextAmount=(EditText) root.findViewById(R.id.editTextPaymentAmount);
        editTextDate.setInputType(InputType.TYPE_NULL);



        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });



        cnfrmbtn = (Button) root.findViewById(R.id.confirm_button);

        cnfrmbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V){
                String dt = editTextDate.getText().toString().trim();
                String amt=editTextAmount.getText().toString().trim();
                showAlertDialogButtonClicked(V, amt,dt);
            }
        });







        /*
        final TextView textView = root.findViewById(R.id.text_make_payment);
        makepaymentViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */
        return root;
    }


    public void showAlertDialogButtonClicked(View view, String amt, String dt) {
        final String amount=new String(amt);
        final String date=new String(dt);

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Your Payment Details");
        builder.setMessage("Amount: "+amt+"\nPayment Date: "+dt);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:

                        // User clicked the Yes button
                        String uid=firebaseAuth.getCurrentUser().getUid();
                        String tid= new String(uid+amount+date);
                        Transactions transaction=new Transactions(amount,date,uid);
//                        Map map=new HashMap();
//                        map.put("amount",amount);
//                        map.put("date",date);
//                        map.put("Uid",uid);
//                        map.put("Tid", tid);




                        myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .push().setValue(transaction).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(),"Transaction Successful",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getContext(), MenuActivity.class));
                                }
                                else{
                                    Toast.makeText(getActivity(),"Transaction Error",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // User clicked the No button
                        break;
                }
            }
        };




        // add the buttons


        builder.setPositiveButton("Continue", dialogClickListener);



        builder.setNegativeButton("Cancel", dialogClickListener);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}