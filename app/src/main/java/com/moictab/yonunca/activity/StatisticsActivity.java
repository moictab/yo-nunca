package com.moictab.yonunca.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moictab.yonunca.R;
import com.moictab.yonunca.adapter.StatisticsAdapter;
import com.moictab.yonunca.controller.StatisticController;
import com.moictab.yonunca.model.Entry;
import com.moictab.yonunca.model.Statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    public static final String TAG = "StatisticsActivity";

    private TextView tvCurrent;
    private RecyclerView rvStatistics;
    private LinearLayoutManager manager;
    private StatisticsAdapter adapter;

    private List<Statistic> statistics = new ArrayList<>();
    private List<Entry> entries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tvCurrent = findViewById(R.id.tv_current);
        tvCurrent.setText("Actualmente hay " + entries.size() + " Yo Nuncas, que corresponden a:");

        rvStatistics = findViewById(R.id.rv_statistics);
        rvStatistics.setHasFixedSize(false);

        manager = new LinearLayoutManager(this);
        rvStatistics.setLayoutManager(manager);

        statistics = new StatisticController().getSortedStatistics(entries);
        adapter = new StatisticsAdapter(statistics);
        rvStatistics.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("entries");
        reference.keepSynced(true);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<DataSnapshot> children = Lists.newArrayList(dataSnapshot.getChildren());

                for (DataSnapshot child : children) {
                    HashMap map = (HashMap) child.getValue();

                    if (map != null && map.get("message") != null && map.get("user") != null) {
                        if (map.containsKey("type") && map.get("tpye") != null) {
                            entries.add(new Entry((String) map.get("message"), (String) map.get("user"), (int) map.get("type")));
                        } else {
                            entries.add(new Entry((String) map.get("message"), (String) map.get("user"), 0));
                        }
                    }
                }

                statistics = new StatisticController().getSortedStatistics(entries);
                rvStatistics.setAdapter(new StatisticsAdapter(statistics));
                tvCurrent.setText("Actualmente hay " + entries.size() + " Yo Nuncas, que corresponden a:");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value", error.toException());
            }
        });
    }
}
