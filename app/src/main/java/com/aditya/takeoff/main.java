package com.aditya.takeoff;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class main extends AppCompatActivity {

    // Create variables to store info from  previous activity
    TextView usernameTextView;
    String userPasson;

    // Create variables related to current activity
    NfcAdapter nfcAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().hasExtra("com.aditya.takeoff.USER_ID")) {
            usernameTextView = (TextView)findViewById(R.id.usernameTextView);
            userPasson = getIntent().getExtras().getString("com.aditya.takeoff.USER_ID");
            usernameTextView.setText(userPasson);

        }

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (!(nfcAdapter != null && nfcAdapter.isEnabled())) {
            Toast.makeText(this, "Please turn on NFC from settings and launch the application again!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

//    public void scanPart(View view) {
//        String username = usernameTextView.getText().toString();
//
//        // TODO -> Write code to identify which NFC tag was scanned
//        int NFC_ID = 5;
//        Intent startIntent = new Intent(this, Checklist_screen.class);
//        startIntent.putExtra("com.aditya.takeoff.USER_ID", username);
//        startIntent.putExtra("com.aditya.takeoff.NFC_ID", NFC_ID);
//        startActivity(startIntent);
//    }


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
//            Toast.makeText(this, tagContent, Toast.LENGTH_SHORT).show();


            // Setting up intent to go the the next activity -->>
            String username = usernameTextView.getText().toString();
            Intent startIntent = new Intent(this, Checklist_screen.class);
            startIntent.putExtra("com.aditya.takeoff.USER_ID", username);
            startIntent.putExtra("com.aditya.takeoff.NFC_ID", NFC_ID);
            startActivity(startIntent);
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
}

/*
    TODO -> Tyler's suggestions:
        Consider the ability to move between activities in both directions.
*/
