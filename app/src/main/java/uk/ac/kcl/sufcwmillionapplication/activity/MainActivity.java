package uk.ac.kcl.sufcwmillionapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.R;
import uk.ac.kcl.sufcwmillionapplication.bean.SearchBean;
import uk.ac.kcl.sufcwmillionapplication.utils.SPUtils;
import uk.ac.kcl.sufcwmillionapplication.view.MainRecAdapter;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "MainAcitvity";

    RecyclerView mainRecyclerView;
    MainRecAdapter adapter;
    ArrayList<SearchBean> histories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainRecyclerView = findViewById(R.id.main_recyclerview);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initData();
        init();
    }

    void init() {
        adapter = new MainRecAdapter(this);
        if (histories!=null){
            adapter.setHistories(histories);
        }
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainRecyclerView.setAdapter(adapter);
    }

    void initData() {
        histories = new ArrayList<>();
        histories = SPUtils.getHistories(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG,"onActivityResult");
        histories = SPUtils.getHistories(this);
        adapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }
}