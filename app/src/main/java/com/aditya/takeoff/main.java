package com.aditya.takeoff;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class main extends AppCompatActivity {
    TextView usernameTextView;
    String userPasson;
    NfcAdapter nfcAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set username
        usernameTextView = (TextView)findViewById(R.id.usernameTextView);
        if (getIntent().hasExtra("com.aditya.takeoff.USER_ID")) {
            userPasson = getIntent().getExtras().getString("com.aditya.takeoff.USER_ID");
            usernameTextView.setText(userPasson);
        }
        if (getIntent().hasExtra("com.aditya.takeoff.BACK")) {
            Toast.makeText(this, "Your submission has been recorded.", Toast.LENGTH_SHORT).show();
        }
        // Navigation bar styling and activity managing
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_history:
                        String username = usernameTextView.getText().toString();
                        Intent historyIntent = new Intent(main.this, History.class);
                        historyIntent.putExtra("com.aditya.takeoff.USER_ID", username);
                        startActivity(historyIntent);
                        finish();
                        break;
                    case R.id.ic_tasks:
                        break;
                }
                return false;
            }
        });
        // NFC set up
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (!(nfcAdapter != null && nfcAdapter.isEnabled())) {
            Toast.makeText(this, "Please turn on NFC from settings and launch the application again!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra(NfcAdapter.EXTRA_TAG)) {
            Parcelable[] parcelable = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (parcelable != null && parcelable.length > 0) {
                readTextFromMessage((NdefMessage)parcelable[0]);
            }
            else {
                Toast.makeText(this, "No NDEF Messages found.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void readTextFromMessage(NdefMessage ndefMessage) {
        NdefRecord[] ndefRecords = ndefMessage.getRecords();
        if (ndefRecords != null && ndefRecords.length > 0) {
            NdefRecord ndefRecord = ndefRecords[0];
            String NFC_ID = getTextFromNdefRecord(ndefRecord);
            String username = usernameTextView.getText().toString();
            Intent checklistIntent = new Intent(this, Checklist_screen.class);
            checklistIntent.putExtra("com.aditya.takeoff.USER_ID", username);
            checklistIntent.putExtra("com.aditya.takeoff.NFC_ID", NFC_ID);
            startActivity(checklistIntent);
        }
        else {
            Toast.makeText(this, "No NDEF records found", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        enableForegroundDispatchSystem();
    }
    @Override
    protected void onPause() {
        super.onPause();
        disableForegroundDispatchSystem();
    }
    private void enableForegroundDispatchSystem() {
        Intent intent = new Intent(this, main.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentilter = new IntentFilter[]{};
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentilter, null);
    }
    private void disableForegroundDispatchSystem() {
        nfcAdapter.disableForegroundDispatch(this);
    }
    public String getTextFromNdefRecord(NdefRecord ndefRecord) {
        String tagContent = null;
        try {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload, languageSize + 1, payload.length - languageSize - 1, textEncoding);
        }
        catch (UnsupportedEncodingException e) {
            Log.e("getTextFromNdefRecord", e.getMessage(), e);
        }
        return tagContent;
    }

    public void continueClick(View view) {
        String NFC_ID = "2";
        String username = usernameTextView.getText().toString();
        Intent checklistIntent = new Intent(this, Checklist_screen.class);
        checklistIntent.putExtra("com.aditya.takeoff.USER_ID", username);
        checklistIntent.putExtra("com.aditya.takeoff.NFC_ID", NFC_ID);
        startActivity(checklistIntent);
    }
}

/*
    TODO -> Tyler's suggestions:
        Consider the ability to move between activities in both directions.
*/
