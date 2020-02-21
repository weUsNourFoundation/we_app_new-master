package com.example.we_us_n_our_app.ui.transactionhistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TransactionHistoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TransactionHistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}