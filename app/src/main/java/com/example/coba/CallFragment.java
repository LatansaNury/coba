package com.example.coba;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.SphericalUtil;

import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CallFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    TextView txtDetection, txtPosisiJatuh;
    TextView txtPosisiRuangan, txtLokasi, txtJarak, txtWaktuTempuh;
    Button buttonOk;
    FusedLocationProviderClient mFusedLocation;
    ObjectAnimator animator;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CallFragment newInstance(String param1, String param2) {
        CallFragment fragment = new CallFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CallFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call, container, false);
        txtDetection = view.findViewById(R.id.txtDetection);
        txtPosisiJatuh = view.findViewById(R.id.txtPosisiJatuh);
        txtPosisiRuangan = view.findViewById(R.id.txtPosisiRuangan);
        txtLokasi = view.findViewById(R.id.txtLokasi);
        txtJarak = view.findViewById(R.id.txtJarak);
        txtWaktuTempuh = view.findViewById(R.id.txtWaktuTempuh);
        buttonOk = view.findViewById(R.id.ButtonOk);
        txtDetection.setText("Normal");
        txtDetection.setTextColor(ContextCompat.getColor(getContext(), R.color.colorText));
        fallDetect();
        getLokasi();
        getDistance();
        getPosisiRuangan();
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HideFall(view);
            }
        });
        return view;
        //return inflater.inflate(R.layout.fragment_call, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        mFusedLocation = LocationServices.getFusedLocationProviderClient(getActivity());
    }

    private void fallDetect() {
        myRef = FirebaseDatabase.getInstance().getReference("sensor");
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String detection = dataSnapshot.child("detection").getValue(String.class);
                String falldetect = "Fall";
                if (falldetect.equals(detection)) {
//                    Intent intent = new Intent(getActivity(), Notif.class);
//                    startActivity(intent);
                    txtDetection.setText("Fall Detect");
                    showPosisiJatuh();
                    animator = ObjectAnimator.ofInt(txtDetection, "textColor", Color.RED, Color.YELLOW);
                    animator.setDuration(500);
                    animator.setEvaluator(new ArgbEvaluator());
                    animator.setRepeatMode(Animation.REVERSE);
                    animator.setRepeatCount(Animation.INFINITE);
                    animator.start();
                    buttonOk.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.i("Info Error", "Failed to read value.", error.toException());
            }
        });
    }

    private void getPosisiRuangan() {
        myRef = FirebaseDatabase.getInstance().getReference("falldetect");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String PosisiRuangan = dataSnapshot.child("posisi").getValue(String.class);
                txtPosisiRuangan.setText(PosisiRuangan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getLokasi() {
        myRef = FirebaseDatabase.getInstance().getReference("lokasi");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double Latitude = dataSnapshot.child("latitude").getValue(Double.class);
                Double Longitude = dataSnapshot.child("longitude").getValue(Double.class);
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                try {
                    List<Address> addresseses = geocoder.getFromLocation(Latitude, Longitude, 1);
                    Address obj = addresseses.get(0);
                    String add = obj.getAddressLine(0);
                    txtLokasi.setText(add);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDistance() {
        myRef = FirebaseDatabase.getInstance().getReference("lokasi");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Double Latitude = dataSnapshot.child("latitude").getValue(Double.class);
                final Double Longitude = dataSnapshot.child("longitude").getValue(Double.class);
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
                    public void onSuccess(Location location) {
                        if (location == null) return;
                        if (Latitude != null || Longitude != null) return;
                        LatLng latLng = new LatLng(Latitude, Longitude);
                        LatLng currentlatlang = new LatLng(location.getLatitude(), location.getLongitude());
                        Double distance = SphericalUtil.computeDistanceBetween(latLng, currentlatlang);
                        int speed40kmperjam = 667;
                        float estimateDriveTime = distance.floatValue() / speed40kmperjam;
                        int hours = (int) estimateDriveTime / 60;
                        int minutes = (int) estimateDriveTime % 60;
                        txtWaktuTempuh.setText("Estimasi waktu tempuh : " + hours + " Jam " + minutes + " Menit");
                        txtJarak.setText(String.format("Jarak(Km) : %.2f", distance / 1000));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showNotif() {
        String NOTIFICATION_CHANNEL_ID = "channel_androidnotif";
        Context context = this.getContext();
        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channelName = "Android Notif Channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000, 1000, 1000});
            mChannel.enableVibration(true);
            notificationManager.createNotificationChannel(mChannel);
            //  v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }
//        else {
//            v.vibrate(500);
//        }
        Intent mIntent = new Intent(getActivity(), CallFragment.class);
        Bundle bundle = new Bundle();
        bundle.putString("fromnotif", "notif");
        mIntent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), NOTIFICATION_CHANNEL_ID);
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher_foreground))
                .setTicker("notif starting")
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000})
                .setLights(Color.RED, 3000, 3000)
                .setDefaults(Notification.DEFAULT_ALL)
                .setCategory(Notification.CATEGORY_CALL)
                .setContentTitle("Notification Android")
                .setContentText("by imamfarisi.com")
                .setPriority(NotificationCompat.PRIORITY_MAX);
        notificationManager.notify(115, builder.build());
    }

    private void HideFall(View view) {
        txtDetection.clearAnimation();
        animator.cancel();
        animator.end();
        txtDetection.setText("Normal");
        txtPosisiJatuh.setText("");
        txtDetection.setTextColor(ContextCompat.getColor(getContext(), R.color.colorText));
        myRef = FirebaseDatabase.getInstance().getReference("sensor");
        myRef.child("detection").setValue("-");
        buttonOk.setVisibility(View.GONE);
    }

    private void showPosisiJatuh() {
        myRef = FirebaseDatabase.getInstance().getReference("acc");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String posisi = dataSnapshot.child("posisi").getValue(String.class);
                String posisinormal = "Normal";
                if (posisi.equals(posisinormal)) {
                    txtPosisiJatuh.setText("");
                } else {
                    txtPosisiJatuh.setText(posisi);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}