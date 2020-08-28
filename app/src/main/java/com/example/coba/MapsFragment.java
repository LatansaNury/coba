package com.example.coba;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment {
    private GoogleMap mMap;
    private DatabaseReference databaseReference;

    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    TextView txtJarak, TxtNamaLokasi;
    FusedLocationProviderClient mFusedLocation;
    GoogleApiClient mGoogleApiClient;
 //   Double latitude, longitude;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mFusedLocation.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(final Location location) {
                    if (location == null) return;
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Double databaseLatitudeString = dataSnapshot.child("latitude").getValue(Double.class);
                            Double databaseLongitudeString = dataSnapshot.child("longitude").getValue(Double.class);
                            LatLng latLng = new LatLng(databaseLatitudeString, databaseLongitudeString);
                            LatLng currentlatlang = new LatLng(location.getLatitude(), location.getLongitude());
                            Double distance = SphericalUtil.computeDistanceBetween(latLng, currentlatlang);
                            txtJarak.setText(String.format("%.2f",distance/1000));
                            getAddress(databaseLatitudeString, databaseLongitudeString);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_maps, container, false);
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        txtJarak = (TextView) view.findViewById(R.id.TxtJarak);
        TxtNamaLokasi = (TextView) view.findViewById(R.id.TxtNamaLokasi);
//        latitude = getArguments().getDouble("latitude");
//        longitude = getArguments().getDouble("longitude");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        mFusedLocation = LocationServices.getFusedLocationProviderClient(getActivity());
        if(mMap != null)
                {
                    mMap.clear();
                }
//        LatLng latLng = new LatLng(latitude, longitude);
//        mMap.addMarker(new MarkerOptions().position(latLng).title(latitude + " , " + longitude));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
//        getAddress(databaseLatitudeString, databaseLongitudeString);
        databaseReference = FirebaseDatabase.getInstance().getReference("lokasi");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double databaseLatitudeString = dataSnapshot.child("latitude").getValue(Double.class);
                Double databaseLongitudeString = dataSnapshot.child("longitude").getValue(Double.class);
                if(mMap != null)
                {
                    mMap.clear();
                }
                LatLng latLng = new LatLng(databaseLatitudeString, databaseLongitudeString);
                mMap.addMarker(new MarkerOptions().position(latLng).title(databaseLatitudeString + " , " + databaseLongitudeString));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));

                getAddress(databaseLatitudeString, databaseLongitudeString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void getAddress() {
//        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//        try {
//            List<Address> addresseses = geocoder.getFromLocation(latitude, longitude, 1);
//            Address obj = addresseses.get(0);
//            String add = obj.getAddressLine(0);
//            TxtNamaLokasi.setText(add);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void getAddress(Double databaseLatitudeString, Double databaseLongitudeString) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresseses = geocoder.getFromLocation(databaseLatitudeString, databaseLongitudeString, 1);
            Address obj = addresseses.get(0);
            String add = obj.getAddressLine(0);
            TxtNamaLokasi.setText(add);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}