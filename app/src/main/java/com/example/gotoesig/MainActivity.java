package com.example.gotoesig;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener,
        AjouterFragment.OnFragmentInteractionListener,
        TrajetFragment.OnFragmentInteractionListener,
        ChercherFragment.OnFragmentInteractionListener,
        EvaluerFragment.OnFragmentInteractionListener,
        StatistiquesFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Button btnSignin;
    private ListView lv;
    private CustomAdapter customAdapter;
    public static ArrayList<Model> modelArrayList;

    // 1 - Declare fragment handled by Navigation Drawer
    private Fragment fragmentProfile;
    private Fragment fragmentAjouter;
    private Fragment fragmentTrajet;
    private Fragment fragmentChercher;
    private Fragment fragmentEvaluer;
    private Fragment fragmentStatistiques;

    //FOR DATAS
    // 2 - Identify each fragment with a number
    private static final int FRAGMENT_PROFILE = 0;
    private static final int FRAGMENT_AJOUTER = 1;
    private static final int FRAGMENT_TRAJET = 2;
    private static final int FRAGMENT_CHERCHER = 3;
    private static final int FRAGMENT_EVALUER = 4;
    private static final int FRAGMENT_STATISTIQUES = 5;

    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listView);

        modelArrayList = getModel();
        customAdapter = new CustomAdapter(this);
        //lv.setAdapter(customAdapter);

        btnSignin = (Button) findViewById(R.id.activity_main_signin);
        btnSignin.setOnClickListener(MainActivity.this);
        // Configure all views

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();
    }

    private ArrayList<Model> getModel(){
        ArrayList<Model> list = new ArrayList<>();
        for(int i = 0; i < 5; i++){

            Date date = null;
            String date1 = "22/06/2006";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            try {
                date = simpleDateFormat.parse(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Model model = new Model();
            model.setJour(date);
            model.setHoraire("9h à 12h");
            model.setLieuDepart("Rouen centre: Palais de justice");
            list.add(model);
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        startSignInActivity();
    }
    // 2 - Launch Sign-In Activity
    private void startSignInActivity(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.ic_menu_send)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void onBackPressed() {
        // Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // 6 - Show fragment after user clicked on a menu item
        switch (id){
            case R.id.nav_profile :
                this.showFragment(FRAGMENT_PROFILE);
                break;
            case R.id.nav_ajouter :
                this.showFragment(FRAGMENT_AJOUTER);
                break;
            case R.id.nav_trajet :
                this.showFragment(FRAGMENT_TRAJET);
                break;
            case R.id.nav_chercher :
                this.showFragment(FRAGMENT_CHERCHER);
                break;
            case R.id.nav_evaluer :
                this.showFragment(FRAGMENT_EVALUER);
                break;
            case R.id.nav_statistiques :
                this.showFragment(FRAGMENT_STATISTIQUES);
                break;
            case R.id.nav_quitter :
                System.exit(0);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    public static void changeFrag (int number) {
        //showFragment(number);
    }

    // 5 - Show fragment according an Identifier
    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_PROFILE :
                this.showProfileFragment();
                break;
            case FRAGMENT_AJOUTER:
                this.showAjouterFragment();
                break;
            case FRAGMENT_TRAJET:
                this.showTrajetFragment();
                break;
            case FRAGMENT_CHERCHER :
                this.showChercherFragment();
                break;
            case FRAGMENT_EVALUER:
                this.showEvaluerFragment();
                break;
            case FRAGMENT_STATISTIQUES:
                this.showStatistiquesFragment();
                break;
            default:
                break;
        }
    }

    // 3 - Generic method that will replace and show a fragment inside the MainActivity Frame Layout
    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }

    // 4 - Create each fragment page and show it
    private void showProfileFragment(){
        if (this.fragmentProfile == null) this.fragmentProfile = ProfileFragment.newInstance();
        this.startTransactionFragment(this.fragmentProfile);
    }

    private void showAjouterFragment(){
        if (this.fragmentAjouter == null) this.fragmentAjouter = AjouterFragment.newInstance();
        this.startTransactionFragment(this.fragmentAjouter);
    }

    private void showTrajetFragment(){
        if (this.fragmentTrajet == null) this.fragmentTrajet = TrajetFragment.newInstance();
        this.startTransactionFragment(this.fragmentTrajet);
    }

    private void showChercherFragment(){
        if (this.fragmentChercher == null) this.fragmentChercher = ChercherFragment.newInstance();
        this.startTransactionFragment(this.fragmentChercher);
    }

    private void showEvaluerFragment(){
        if (this.fragmentEvaluer == null) this.fragmentEvaluer = EvaluerFragment.newInstance();
        this.startTransactionFragment(this.fragmentEvaluer);
    }

    private void showStatistiquesFragment(){
        if (this.fragmentStatistiques == null) this.fragmentStatistiques = StatistiquesFragment.newInstance();
        this.startTransactionFragment(this.fragmentStatistiques);
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    // Configure Toolbar
    private void configureToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    // Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


}
