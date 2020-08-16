package com.fabianolira.appmiguelasnews.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fabianolira.appmiguelasnews.R;
import androidx.appcompat.widget.Toolbar;

public class ApoioActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apoio);


        configuraToolbar();

    }

    private void configuraToolbar() {
        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Apoio");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_24dp);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }

}