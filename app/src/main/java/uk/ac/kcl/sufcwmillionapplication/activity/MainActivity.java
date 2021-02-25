package uk.ac.kcl.sufcwmillionapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
        histories = SPUtils.getObject(this,"history");
//        for (int i=0;i<5;i++){
//            SearchBean searchBean = new SearchBean();
//            searchBean.setName("History "+i);
//            histories.add(searchBean);
//        }
    }
}