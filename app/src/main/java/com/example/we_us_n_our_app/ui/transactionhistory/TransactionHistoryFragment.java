package com.example.we_us_n_our_app.ui.transactionhistory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.we_us_n_our_app.R;
import com.example.we_us_n_our_app.Transaction;
import com.example.we_us_n_our_app.ui.makepayment.Transactions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class TransactionHistoryFragment extends Fragment {

    private TransactionHistoryViewModel transactionhistoryViewModel;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Transactions tr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        transactionhistoryViewModel =
                ViewModelProviders.of(this).get(TransactionHistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_transaction_history, container, false);
        database= FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        myRef=database.getReference("Transactions").child(firebaseAuth.getCurrentUser().getUid());
        list= new ArrayList<String>();
        adapter= new ArrayAdapter<String>(getContext(),R.layout.colmn_row,R.id.transactionInfo,list);

        tr=new Transactions();

        final ListView listView=(ListView)root.findViewById(R.id.listViewTransactionHistory);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    tr= ds.getValue(Transactions.class);
                    list.add(tr.getAmount().toString()+"             "+tr.getDate().toString());

                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //final TextView textView = root.findViewById(R.id.text_transaction_history);
//        transactionhistoryViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        return root;
    }


}