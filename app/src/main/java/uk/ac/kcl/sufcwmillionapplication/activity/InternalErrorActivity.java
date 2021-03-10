package uk.ac.kcl.sufcwmillionapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.kcl.sufcwmillionapplication.R;

public class InternalErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.internal_error_main);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ImageButton backBtn = findViewById(R.id.not_found_bar_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InternalErrorActivity.this.finish();
            }
        });
        TextView tvBackBtn = findViewById(R.id.not_found_back_btn);
        tvBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InternalErrorActivity.this.finish();
            }
        });
    }

}