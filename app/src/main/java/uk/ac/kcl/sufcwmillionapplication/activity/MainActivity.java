package uk.ac.kcl.sufcwmillionapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import uk.ac.kcl.sufcwmillionapplication.R;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "MainAcitvity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}