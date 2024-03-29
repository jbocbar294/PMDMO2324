package iesmm.pmdm.pmdm_t4_users;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import iesmm.pmdm.pmdm_t4_users.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    File archivoUsers;
    ArrayList<Usuario> listaUsuarios;
    BufferedReader br;
    String linea, email, contrasenya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Vuelve a usar el fichero como en LoginActivity
        archivoUsers = new File(getFilesDir() + File.separator + "users.csv");
        // Obtiene los valores del bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) { // Si el bundle no está vacío...
            // Almacena los valores en variables
            email = bundle.getString("email");
            contrasenya = bundle.getString("contrasenya");
        }
        // Guarda todos los usuarios del fichero en un arraylist
        listaUsuarios = new ArrayList<>();
        String[] datos = new String[5];
        try {
            br = new BufferedReader(new FileReader(archivoUsers));
            while ((linea = br.readLine()) != null) { // Mientras pueda leer...
                // Almacena los datos del usuario actual en un array
                datos = linea.split(";");
                // Añade un objeto Usuario al arraylist
                listaUsuarios.add(new Usuario(datos[0], datos[1], datos[2], Integer.parseInt(datos[3]), datos[4]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (Usuario usuarioActual : listaUsuarios) { // Recorremos el arraylist de usuarios
            // Llama al método getLocationFromAddress para obtener la latitud y longitud mediante un String.
            LatLng ubicacion = getLocationFromAddress(usuarioActual.getPoblacion());
            if (ubicacion != null) { // Si la ubicación no es nula...
                // Crea un MarkerOptions y le asignamos la ubicación y el usuario como título
                MarkerOptions marker = new MarkerOptions()
                        .position(ubicacion)
                        .title(usuarioActual.getUsuario());
                // Si el usuario actual es con el que se inició sesión...
                if (usuarioActual.getEmail().equals(email) && usuarioActual.getContrasenya().equals(contrasenya)) {
                    // También le añade el snippet con la población, el email y el teléfono
                    marker.snippet(usuarioActual.getPoblacion() + "\n" +
                            usuarioActual.getEmail() + "\n" +
                            usuarioActual.getTelefono());
                    // Mueve la posición a su ubicación y cambia el zoom
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 10));
                }
                mMap.addMarker(marker); // Añade el marker
            } else {
                Log.d(":::::NO ENCONTRADA", usuarioActual.getPoblacion());            }
        }
    }

    // Método que devuelve un objeto LatLng a partir de un String usando Geocoder
    public LatLng getLocationFromAddress(String strAddress) {
        LatLng latLng = null;
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocationName(strAddress, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                double latitude = address.getLatitude();
                double longitude = address.getLongitude();
                latLng = new LatLng(latitude, longitude);
            } else {
                Toast.makeText(this, "No se ha encontrado la ubicación.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            
        }
        return latLng;
    }
}