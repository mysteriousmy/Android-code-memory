package com.example.apps.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.apps.R;
@RequiresApi(api = Build.VERSION_CODES.N)
public class HomeFragment extends Fragment {
    HomeViewModel homeViewModel;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel = new HomeViewModel(root);
        return root;
    }


    public void refresh(View roots){

        homeViewModel.initObserver(roots);
    }
}