package com.example.we_us_n_our_app.ui.makepayment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MakePaymentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MakePaymentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}