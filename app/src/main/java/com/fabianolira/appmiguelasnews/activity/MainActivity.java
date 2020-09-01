package com.fabianolira.appmiguelasnews.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.fragment.TabFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toogle;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RecyclerView recyclerViewCategoria;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("All");

        chamaAdMob();
        configuraToolbar();
        inicializaComponentes();
        configuraFragmentTransaction();
        configuraToogle();
        configuraMenuDrawer();

    }

    private void configuraToogle() {
        toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.abrir, R.string.fechar);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
    }

    private void configuraFragmentTransaction() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, new TabFragment()).commit();
    }

    private void inicializaComponentes() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.main_drawer);
        recyclerViewCategoria = findViewById(R.id.recyclerViewCategoria);
    }

    private void configuraMenuDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                if (item.getItemId() == R.id.menu_noticia) {
                    String url = "http://miguelasnews.online/noticia/create";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }

                if (item.getItemId() == R.id.menu_favoritos) {

                    //Toast.makeText(MainActivity.this, "Menu Favoritos clicado!", Toast.LENGTH_SHORT).show();
                }

               // if (item.getItemId() == R.id.menu_dicas_noticias) {
                //     Intent intent = new Intent(MainActivity.this, DicasNoticiasActivity.class);
                //  startActivity(intent);
                //  //Toast.makeText(MainActivity.this, "Menu Escrever clicado!", Toast.LENGTH_SHORT).show();
                //}

                if (item.getItemId() == R.id.menu_apoio) {
                    Intent intent = new Intent(MainActivity.this, ApoioActivity.class);
                    startActivity(intent);
                }

                if (item.getItemId() == R.id.menu_sobre) {
                    chamarDialogSobre();


                }


                return false;
            }
        });
    }

    private void chamarDialogSobre() {
        // custom dialog
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_sobre);

        // set the custom dialog components - text, image and button

        ImageView image = dialog.findViewById(R.id.sobre_image);


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void configuraToolbar() {
        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        toolbar.setTitle("MiguelasNews");
    }

    private void chamaAdMob() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toogle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
