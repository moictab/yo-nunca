package com.moictab.yonunca.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moictab.yonunca.R;
import com.moictab.yonunca.model.Entry;
import com.moictab.yonunca.model.EntryType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    public static final String TAG = "PlayActivity";

    private List<Entry> entries = new ArrayList<>();

    private TextView tvType;
    private TextView tvMessage;
    private TextView tvAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        tvMessage = findViewById(R.id.tv_message);
        tvAuthor = findViewById(R.id.tv_author);
        tvType = findViewById(R.id.tv_type);

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

                        if (map.containsKey("type")) {
                            entries.add(new Entry((String) map.get("message"), (String) map.get("user"), ((Long) map.get("type")).intValue()));
                        } else {
                            entries.add(new Entry((String) map.get("message"), (String) map.get("user"), 0));
                        }
                    }
                }

                setMessage();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value", error.toException());
            }
        });

        Button btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMessage();
            }
        });
    }

    private void setMessage() {
        Entry entry = entries.get(new Random().nextInt(entries.size()));
        tvMessage.setText(entry.message);
        tvAuthor.setText("El autor es...\n" + entry.user + "\nDe verdad que te digooo");

        switch (entry.type) {
            case EntryType.I_NEVER_HAVE:
                tvType.setText(R.string.i_never_have);
                break;
            case EntryType.I_NEVER_MYSELF:
                tvType.setText(R.string.i_never_myself);
                break;
            case EntryType.I_NEVER_HAVE_BEEN:
                tvType.setText(R.string.i_never_have_been);
                break;
        }
    }
}
