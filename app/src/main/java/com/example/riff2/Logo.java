package com.example.riff2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Logo extends Fragment {

    ImageView logo;
    Random random = new Random();

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_logo, container, false);
    }

    
}
