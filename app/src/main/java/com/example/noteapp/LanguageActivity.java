package com.example.noteapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LanguageActivity extends AppCompatActivity {
    Button englishButton;
    Button arabicButton;
    SharedPreferences sharedPreferences;
    public static final int ARABIC = 0;
    public static final int ENGLISH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_language);
        arabicButton = findViewById(R.id.btn_arabic);
        englishButton = findViewById(R.id.btn_English);

        sharedPreferences = getPreferences(MODE_PRIVATE);

        if (sharedPreferences.contains("language")) {

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("language",sharedPreferences.getInt("language",999));
            startActivity(i);
        }


        arabicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("language", ARABIC);
                editor.apply();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("language",ARABIC);
                startActivity(i);
            }
        });

        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("language", ENGLISH);
                editor.apply();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("language",ENGLISH);
                startActivity(i);
            }
        });
    }
}