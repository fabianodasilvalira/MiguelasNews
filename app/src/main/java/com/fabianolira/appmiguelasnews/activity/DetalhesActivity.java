package com.fabianolira.appmiguelasnews.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.fabianolira.appmiguelasnews.R;

public class DetalhesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Intent intent = getIntent();
        Toast.makeText(this, "O id e " + intent.getStringExtra("idNoticia: "), Toast.LENGTH_SHORT).show();
    }
}
