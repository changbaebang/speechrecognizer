package com.example.bangchangbae.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button startBtn;
    TextView speechResultTextView;
    ArrayList<String> matched_textList;
    private static final int REQ_CODE = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn =  (Button)findViewById(R.id.button);
        speechResultTextView = (TextView)findViewById(R.id.textView);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected() == false){
                    Toast.makeText(getApplicationContext(), "Please Connect to Internert", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                startActivityForResult(intent, REQ_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode != REQ_CODE || resultCode != RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        matched_textList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        String result = "";
        for(int i=0; i<matched_textList.size();i++)
            result += matched_textList.get(i) + "\r\n";
        speechResultTextView.setText(result);
    }

    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo == null)
            return false;
        if(netInfo.isAvailable() == false || netInfo.isConnected() == false)
            return false;
        return true;
    }
}
