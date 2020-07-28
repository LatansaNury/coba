package com.example.coba;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.coba.Adapter.MyAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPAger;
    TabLayout tabLayout;
    RecyclerView recyclerView;

    private static final String TAG = "fireiot";
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        PagerAdapter.addFragment(new ChatFragment());
        PagerAdapter.addFragment(new CallFragment());
        PagerAdapter.addFragment(new StatusFragment());

        viewPAger = findViewById(R.id.viewpager);
        viewPAger.setAdapter(pagerAdapter);

        tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPAger);

        tabLayout.getTabAt(0).setText("Chat");
        tabLayout.getTabAt(1).setText("Call");
        tabLayout.getTabAt(2).setText("Status");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
//        FirebaseMessaging.getInstance().subscribeToTopic("android");
//        myRef = database.getInstance().getReference().child("detection");
        widget();
//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String detection = dataSnapshot.child("sensor/detection").getValue(String.class);
//                //Log.d(TAG, "Value is: " + detection);
//
//
//                txtDetection.setText(detection);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.i("Info Error", "Failed to read value.", error.toException());
//
//            }
//
//
//
//        });
//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                String detection = txtDetection.getText().toString();
//                if (detection.isEmpty()){
//
//                }else {
//                    createNotification();
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
//    private  void notification(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel channel =
//                    new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager manager =
//                    getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }
//        NotificationCompat.Builder builder  = new NotificationCompat.Builder(this, "n")
//                .setContentText("code sphare")
//                .setSmallIcon(R.drawable.notification_icon)
//                .setAutoCancel(true)
//                .setContentText("data added");
//        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
//        managerCompat.notify(999, builder.build());
//    }
//private void createNotification () {
//    NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
//    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity. this, default_notification_channel_id ) ;
//    mBuilder.setContentTitle( "Notification Title" ) ;
//    mBuilder.setContentText( "Content to be added" ) ;
////    mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
//    mBuilder.setLargeIcon(BitmapFactory. decodeResource (getResources() , R.drawable. ic_launcher_foreground )) ;
//    mBuilder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
//    mBuilder.setAutoCancel( true ) ;
//    if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
//        int importance = NotificationManager. IMPORTANCE_HIGH ;
//        NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
//        mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
//        assert mNotificationManager != null;
//        mNotificationManager.createNotificationChannel(notificationChannel) ;
//    }
//    assert mNotificationManager != null;
//
//    mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
//public void onMessageReceived(RemoteMessage remoteMessage) {
//
//    // Check if message contains a data payload.
//    if (remoteMessage.getData().size() > 0) {
//        showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("author"));
//    }
//
//    // Check if message contains a notification payload.
//    if (remoteMessage.getNotification() != null) {
//
//    }
//}
//
//    private void showNotification(String title, String author) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setContentTitle("New Article: " + title)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentText("By " + author)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }

    public  void widget() {
//        txtDetection = (TextView) findViewById(R.id.txtDetection);
    }
}