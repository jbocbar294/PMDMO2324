package iesmm.pmdm.t4_01;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import iesmm.pmdm.t4_01.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements GestorAplicacion {

    private AppBarConfiguration mAppBarConfiguration;
private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityMainBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // cargar métodos de operación del GestorAplicacion
        // abrirMarcadorLlamada();
        // marcarLlamada("900900100");
        // realizarLlamada("123");
        mandarMensaje("Hola esto es un SMS");
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        
        return super.onOptionsItemSelected(item);

    }

    
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public void cargarNavegadorWeb(String url) {

    }


    @Override
    public void abrirMarcadorLlamada() {

        this.startActivity(new Intent(Intent.ACTION_DIAL));

    }


    @Override
    public void marcarLlamada(String telefono) {

        Intent intent = new Intent(Intent.ACTION_DIAL);

        intent.setData(Uri.parse("tel:" + telefono));

        this.startActivity(intent);

    }


    @Override
    public void realizarLlamada(String telefono) {

        if (confirmarPermisoLlamada()) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telefono));
            this.startActivity(intent);
        }

    }

    @Override
    public void mandarMensaje(String contenido) {

        if (confirmarPermisoMensaje()) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:123"));
            intent.putExtra("sms_body", contenido);
            this.startActivity(intent);
        }

    }

    @Override
    public void mandarMensaje(String telefono, String contenido) {

        if (confirmarPermisoMensaje()) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + telefono));
            intent.putExtra("sms_body", contenido);
            this.startActivity(intent);
        }

    }


    private boolean confirmarPermisoLlamada() {
        boolean confirmado = false;

        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)  {
            this.requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 0);
        } else
            confirmado = true;

        return confirmado;
    }


    private boolean confirmarPermisoMensaje() {
        boolean confirmado = false;

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)  {
            this.requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 0);
        } else
            confirmado = true;

        return confirmado;
    }
}