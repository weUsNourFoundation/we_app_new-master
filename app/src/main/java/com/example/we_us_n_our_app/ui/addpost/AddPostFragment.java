package com.example.we_us_n_our_app.ui.addpost;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.we_us_n_our_app.R;
import com.example.we_us_n_our_app.ui.addpost.AddPostActivityViewModel;

public class AddPostFragment extends Fragment {
    private Button action_add_image;
    private static final int PICK_IMAGE=100;

    private AddPostActivityViewModel addPostActivityViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addPostActivityViewModel =
                ViewModelProviders.of(this).get(AddPostActivityViewModel.class);
        View root = inflater.inflate(R.layout.activity_add_post, container, false);
        action_add_image=(Button) root.findViewById(R.id.action_add_image);

        root.findViewById(R.id.action_add_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startGallery();
            }
        });

        return root;

    }
    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }


//    protected onCreate(Bundle savedInstanceState)
//    {
//
//    }
}
