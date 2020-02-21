package com.example.we_us_n_our_app.ui.transactionhistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private ArrayList<HashMap<String, String>> list;
    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";
    public static final String FOURTH_COLUMN="Fourth";

    private TransactionHistoryViewModel transactionhistoryViewModel;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        transactionhistoryViewModel =
                ViewModelProviders.of(this).get(TransactionHistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_transaction_history, container, false);
        database= FirebaseDatabase.getInstance();
        myRef=database.getReference("Transactions");
        firebaseAuth = FirebaseAuth.getInstance();

        ListView listView=(ListView)root.findViewById(R.id.listViewTransactionHistory);
        populateList();
        ListViewAdapter adapter=new ListViewAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        //final TextView textView = root.findViewById(R.id.text_transaction_history);
//        transactionhistoryViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        return root;
    }

    private void populateList() {
        // TODO Auto-generated method stub
        final ArrayList<Transactions> items  = new ArrayList<Transactions>();
//
        list=new ArrayList<HashMap<String,String>>();
//        String uid=firebaseAuth.getCurrentUser().getUid();
//        myRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot item : dataSnapshot.getChildren()){
//                    items.add(new Transactions(
//                            item.child("amount").getValue().toString(),
//                            item.child("date").getValue().toString(),
//                            item.child("Uid").getValue().toString()
//                    ));
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        HashMap<String,String> hashmap=new HashMap<String, String>();

        for(Transactions t: items){
        hashmap.put(FIRST_COLUMN, t.getAmount());
        hashmap.put(SECOND_COLUMN, t.getDate());
        hashmap.put(THIRD_COLUMN, "Free");
        hashmap.put(FOURTH_COLUMN, "App");
        list.add(hashmap);

        }



//        HashMap<String,String> hashmap=new HashMap<String, String>();
//        hashmap.put(FIRST_COLUMN, "Allo messaging");
//        hashmap.put(SECOND_COLUMN, "google");
//        hashmap.put(THIRD_COLUMN, "Free");
//        hashmap.put(FOURTH_COLUMN, "App");
//        list.add(hashmap);
//
//        HashMap<String,String> hashmap2=new HashMap<String, String>();
//        hashmap2.put(FIRST_COLUMN, "Allo messaging");
//        hashmap2.put(SECOND_COLUMN, "google");
//        hashmap2.put(THIRD_COLUMN, "Free");
//        hashmap2.put(FOURTH_COLUMN, "App");
//        list.add(hashmap2);
//
//        HashMap<String,String> hashmap3=new HashMap<String, String>();
//        hashmap3.put(FIRST_COLUMN, "Allo messaging");
//        hashmap3.put(SECOND_COLUMN, "google");
//        hashmap3.put(THIRD_COLUMN, "Free");
//        hashmap3.put(FOURTH_COLUMN, "App");
//        list.add(hashmap3);
//
//        HashMap<String,String> hashmap4=new HashMap<String, String>();
//        hashmap4.put(FIRST_COLUMN, "Allo messaging");
//        hashmap4.put(SECOND_COLUMN, "google");
//        hashmap4.put(THIRD_COLUMN, "Free");
//        hashmap4.put(FOURTH_COLUMN, "App");
//        list.add(hashmap4);
//
//        HashMap<String,String> hashmap5=new HashMap<String, String>();
//        hashmap5.put(FIRST_COLUMN, "Allo messaging");
//        hashmap5.put(SECOND_COLUMN, "google");
//        hashmap5.put(THIRD_COLUMN, "Free");
//        hashmap5.put(FOURTH_COLUMN, "App");
//        list.add(hashmap5);
//
//        HashMap<String,String> hashmap6=new HashMap<String, String>();
//        hashmap6.put(FIRST_COLUMN, "Allo messaging");
//        hashmap6.put(SECOND_COLUMN, "google");
//        hashmap6.put(THIRD_COLUMN, "Free");
//        hashmap6.put(FOURTH_COLUMN, "App");
//        list.add(hashmap6);

        HashMap<String,String> hashmap7=new HashMap<String, String>();
        hashmap7.put(FIRST_COLUMN, "Allo messaging");
        hashmap7.put(SECOND_COLUMN, "google");
        hashmap7.put(THIRD_COLUMN, "Free");
        hashmap7.put(FOURTH_COLUMN, "App");
        list.add(hashmap7);

    }
}