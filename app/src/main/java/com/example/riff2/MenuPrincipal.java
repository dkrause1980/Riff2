package com.example.riff2;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MenuPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu_principal);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().add(R.id.container,new Logo()).commit();

        drawerLayout=findViewById(R.id.menu_principal);
        navigationView = findViewById(R.id.navigationView);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        cargarHeader();
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        selectItemNav(item);
        return true;
    }

    private void selectItemNav(MenuItem item) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch(item.getItemId()){

            case (R.id.inicio):
                fragmentTransaction.replace(R.id.container, new Logo()).commit();
                break;
            case (R.id.nuevo_evento):
                fragmentTransaction.replace(R.id.container, new Nuevo_Evento()).commit();
                break;
            case (R.id.lista_eventos):
                fragmentTransaction.replace(R.id.container, new Lista_Eventos()).commit();
                break;
            case (R.id.config_user):
                fragmentTransaction.replace(R.id.container, new Config_User()).commit();
                break;
            case (R.id.salir):
                finish();
                break;
        }
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();
    }

    public void cargarHeader(){
        View view = navigationView.getHeaderView(0);
        TextView legajo = (TextView)view.findViewById(R.id.tv_legajo);
        TextView nombre = (TextView)view.findViewById(R.id.tv_nombre);

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("legajo","Usuario desconocido");
        String name = preferences.getString("nombre","Usuario desconocido");
        legajo.setText(user);
        nombre.setText(name);

    }
}