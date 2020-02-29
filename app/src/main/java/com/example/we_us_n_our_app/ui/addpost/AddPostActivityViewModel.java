package com.example.we_us_n_our_app.ui.addpost;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddPostActivityViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AddPostActivityViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}
