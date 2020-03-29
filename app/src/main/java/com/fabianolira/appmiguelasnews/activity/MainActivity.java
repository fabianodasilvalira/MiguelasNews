package com.fabianolira.appmiguelasnews.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.fragment.CategoriasFragment;
import com.fabianolira.appmiguelasnews.fragment.NoticiasRecentesFragment;
import com.fabianolira.appmiguelasnews.fragment.TabFragment;
import com.fabianolira.appmiguelasnews.json.JsonUtils;
import com.fabianolira.appmiguelasnews.model.Categoria;
import com.fabianolira.appmiguelasnews.util.Config;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import dmax.dialog.SpotsDialog;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigationView;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toogle;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    RecyclerView recyclerViewCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("All");

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        if ( toolbar != null){
            setSupportActionBar(toolbar);
        }
        toolbar.setTitle("MiguelasNews");


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.main_drawer);
        recyclerViewCategoria = findViewById(R.id.recyclerViewCategoria);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, new TabFragment()).commit();

        toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.abrir, R.string.fechar);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                if(item.getItemId() == R.id.menu_noticia){

                    NoticiasRecentesFragment noticiasRecentes = new NoticiasRecentesFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, noticiasRecentes);
                    fragmentTransaction.commit();

                }
                if(item.getItemId() == R.id.menu_categoria){

                    CategoriasFragment categoriasFragment = new CategoriasFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, categoriasFragment);
                    fragmentTransaction.commit();

                }
                if(item.getItemId() == R.id.menu_favoritos){

                    Toast.makeText(MainActivity.this, "Menu Favoritos clicado!", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toogle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
