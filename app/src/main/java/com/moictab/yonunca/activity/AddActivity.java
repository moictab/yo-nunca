package com.moictab.yonunca.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moictab.yonunca.R;
import com.moictab.yonunca.dialog.NameDialog;
import com.moictab.yonunca.model.Entry;
import com.moictab.yonunca.model.EntryType;

public class AddActivity extends AppCompatActivity {

    public static final String TAG = "AddActivity";

    private Entry entry;
    private int tpyeSelected = 0;

    private EditText etMessage;
    private RadioButton rbFirts;
    private RadioButton rbSecond;
    private RadioButton rbThird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button btnSend = findViewById(R.id.btn_send);
        etMessage = findViewById(R.id.et_message);
        etMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                if (action == EditorInfo.IME_ACTION_SEND) {
                    send();
                    return true;
                }
                return false;
            }
        });

        rbFirts = findViewById(R.id.rb_first);
        rbSecond = findViewById(R.id.rb_second);
        rbThird = findViewById(R.id.rb_third);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();
                switch (view.getId()) {
                    case R.id.rb_first:
                        if (checked) {
                            tpyeSelected = EntryType.I_NEVER_HAVE;
                            etMessage.setHint(R.string.i_never_have);
                        }
                        break;
                    case R.id.rb_second:
                        if (checked) {
                            tpyeSelected = EntryType.I_NEVER_MYSELF;
                            etMessage.setHint(R.string.i_never_myself);
                        }
                        break;
                    case R.id.rb_third:
                        if (checked) {
                            tpyeSelected = EntryType.I_NEVER_HAVE_BEEN;
                            etMessage.setHint(R.string.i_never_have_been);
                        }
                        break;
                }
            }
        };

        rbFirts.setOnClickListener(listener);
        rbSecond.setOnClickListener(listener);
        rbThird.setOnClickListener(listener);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });
    }

    private void send() {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AddActivity.this);
        etMessage.setError(null);
        String text = etMessage.getText().toString();

        if (isValid(text)) {
            entry = new Entry(etMessage.getText().toString(), preferences.getString(NameDialog.NAME, NameDialog.DEFAULT), tpyeSelected);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("entries");
            reference.push().setValue(entry);

            etMessage.setText("");
            Toast.makeText(getApplicationContext(), R.string.sent, Toast.LENGTH_SHORT).show();
        } else if (text.length() == 0) {
            etMessage.setError(getString(R.string.mandatory_field));
        } else if (text.length() > 128) {
            etMessage.setError(getString(R.string.max_characters_error));
        }
    }

    private boolean isValid(String text) {
        return !(text.length() == 0 || text.length() > 64);
    }
}
