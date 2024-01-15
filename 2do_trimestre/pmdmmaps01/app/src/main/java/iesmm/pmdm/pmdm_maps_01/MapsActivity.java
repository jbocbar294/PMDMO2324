package iesmm.pmdm.pmdm_maps_01;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;

import iesmm.pmdm.pmdm_maps_01.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        // Asociamos eventos escuchadores
        mMap.setOnMapClickListener(this); // Evento de click en el mapa
        mMap.setOnMarkerClickListener(this); // Evento de click en marcador

        // Add a marker in Sydney and move the camera
        LatLng sevilla = new LatLng(37.39027287076241, -5.99078972069179);
        LatLng cadiz = new LatLng(36.52120780665707, -6.2819531690051935);
        LatLng huelva = new LatLng(37.26291890829005, -6.945533323118526);

        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(cadiz)
                .title("Cádiz")
                .snippet("☎️")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

        Marker marker2 = mMap.addMarker(new MarkerOptions()
                .position(sevilla)
                .title("Sevilla")
                .snippet("☎️")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

        Marker marker3 = mMap.addMarker(new MarkerOptions()
                .position(huelva)
                .title("Huelva")
                .snippet("☎️")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sevilla, 8));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 8), 5000, null);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        Toast.makeText(this, latLng.latitude + " " + latLng.longitude, Toast.LENGTH_LONG).show();

        mMap.addMarker(new MarkerOptions().position(latLng).title("Marcador creado por el usuario"));
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Toast.makeText(this, marker.getTitle() + " " + marker.getPosition(), Toast.LENGTH_LONG).show();
        return false;
    }

    public LatLng getLocationFromAddress(String strAddress) {
        LatLng latLng = null;
        Geocoder coder = new Geocoder(this.getApplicationContext());

        try {
            ArrayList<Address> addresses = (ArrayList<Address>) coder.getFromLocationName(strAddress, 1);

            //TODO: Implement Controls to ensure it is right address such as country etc.
            double longitude = addresses.get(0).getLongitude();
            double latitude = addresses.get(0).getLatitude();

            latLng = new LatLng(latitude, longitude);

        } catch (IOException e) {
            //Timber.e("getLocationFromAddress", e);
        }

        return latLng;
    }
}
