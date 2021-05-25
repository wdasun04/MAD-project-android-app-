package com.example.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    Button addNew;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);

        menu = navigationView.getMenu();


    }
        @Override
        public void onBackPressed() {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }


        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem menuItem){
            switch (menuItem.getItemId()) {
                case R.id.home:
                    break;
                case R.id.FirstPay:
                    Intent intent = new Intent(MainActivity.this, FirstPay.class);
                    startActivity(intent);
                    break;
                case R.id.profile:
                    Intent intent2 = new Intent(MainActivity.this, Myprofile.class);
                    startActivity(intent2);
                    break;
                case R.id.feedback:
                    Intent intent3 = new Intent(MainActivity.this, Feedback.class);
                    startActivity(intent3);
                    break;
                case R.id.upload:
                    Intent intent4 = new Intent(MainActivity.this, AdminUpload.class);
                    startActivity(intent4);
                    break;
                case R.id.shop:
                    Intent intent5 = new Intent(MainActivity.this, Shop.class);
                    startActivity(intent5);
                    break;

                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), loginpage.class));
                    break;


                //case R.id.nav_share: Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show(); break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;

        }




}