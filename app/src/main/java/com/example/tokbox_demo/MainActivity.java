package com.example.tokbox_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Subscriber;
import com.opentok.android.OpentokError;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import android.opengl.GLSurfaceView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements  Session.SessionListener, PublisherKit.PublisherListener {

    private static String API_KEY ="46518622"; //46518622

    private static String SESSION_ID = "1_MX40NjUxODYyMn5-MTU4MjY4MjYzNzYwOH5iL3FveENONWw3S011VDZ4M01hNjI3V1N-fg";
    private static String TOKEN = "";
    private static String TOKEN_PUBLISHER = "T1==cGFydG5lcl9pZD00NjUxODYyMiZzaWc9NzZlNjI1NWRmOGQzNmMwMmFkNDkzNjgxNmRjNjM1ZTE3YmQyYWYwMjpzZXNzaW9uX2lkPTFfTVg0ME5qVXhPRFl5TW41LU1UVTRNalk0TWpZek56WXdPSDVpTDNGdmVFTk9OV3czUzAxMVZEWjRNMDFoTmpJM1YxTi1mZyZjcmVhdGVfdGltZT0xNTgyNjgzNTQyJm5vbmNlPTAuMTkyNTU4OTc2MDY0NjUxNTUmcm9sZT1wdWJsaXNoZXImZXhwaXJlX3RpbWU9MTU4Mjc2OTk0MSZpbml0aWFsX2xheW91dF9jbGFzc19saXN0PQ==";
    private static String TOKEN_SUBSCRIBER = "T1==cGFydG5lcl9pZD00NjUxODYyMiZzaWc9YWU1Yzk5ODY1YjFiMmU3MDQ1NTNiZDBmZmQyZTc4ZWMzMmZiNmZjZTpzZXNzaW9uX2lkPTFfTVg0ME5qVXhPRFl5TW41LU1UVTRNalk0TWpZek56WXdPSDVpTDNGdmVFTk9OV3czUzAxMVZEWjRNMDFoTmpJM1YxTi1mZyZjcmVhdGVfdGltZT0xNTgyNjgzNTY4Jm5vbmNlPTAuMDY0NDc1NDc1Mjg3NDM5NDcmcm9sZT1zdWJzY3JpYmVyJmV4cGlyZV90aW1lPTE1ODI3Njk5NjcmaW5pdGlhbF9sYXlvdXRfY2xhc3NfbGlzdD0=";
    private static String TOKEN_MODERATOR_1 = "T1==cGFydG5lcl9pZD00NjUxODYyMiZzaWc9YzM2YWNlNDRlYzYxODIzZDk2YmE0YzEyZDE5ZjU2ZmFjZjJjNTFiYzpzZXNzaW9uX2lkPTFfTVg0ME5qVXhPRFl5TW41LU1UVTRNalk0TWpZek56WXdPSDVpTDNGdmVFTk9OV3czUzAxMVZEWjRNMDFoTmpJM1YxTi1mZyZjcmVhdGVfdGltZT0xNTgyNjgyNjgwJm5vbmNlPTAuNDY1NTc1ODU0Mzg0MDExNCZyb2xlPW1vZGVyYXRvciZleHBpcmVfdGltZT0xNTgyNzY5MDgwJmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";
    private static String TOKEN_MODERATOR_2 = "T1==cGFydG5lcl9pZD00NjUxODYyMiZzaWc9MzMwOWM0OWE1MDhkODBjNDdjYzg4ZDQzYzQxZGFmMmY4ZWIwN2U1YjpzZXNzaW9uX2lkPTFfTVg0ME5qVXhPRFl5TW41LU1UVTRNalk0TWpZek56WXdPSDVpTDNGdmVFTk9OV3czUzAxMVZEWjRNMDFoTmpJM1YxTi1mZyZjcmVhdGVfdGltZT0xNTgyNjgyNzIzJm5vbmNlPTAuOTgxMzY1MDg3MTg5MjAyNiZyb2xlPW1vZGVyYXRvciZleHBpcmVfdGltZT0xNTgyNzY5MTIyJmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";

    private static final String LOG_TAG ="mlog: ";
    private static final int RC_SETTING_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;
    private Session mSession;
    private Publisher mPublisher;
    private Subscriber mSubscriber;

    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;
    private Button   call_to_btn;
    private Button   listen_btn;
    private Button  endCall_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        requestPermission();

        mPublisherViewContainer = (FrameLayout) findViewById(R.id.publisher_container);
        mSubscriberViewContainer = (FrameLayout) findViewById(R.id.subscriber_container);
        call_to_btn = (Button) findViewById(R.id.call_to_btn) ;
        listen_btn = (Button) findViewById(R.id.listen_btn);
        endCall_btn =(Button) findViewById(R.id.endcall_btn);

        setOnclick();

    }

    private void setOnclick() {
        listen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = TOKEN_MODERATOR_2;

                listen_btn.setVisibility(View.GONE);
                call_to_btn.setVisibility(View.GONE);

                requestPermission(token);

//                Toast.makeText(MainActivity.this, "TOKEN_SUBSCRIBER", Toast.LENGTH_LONG).show();
            }
        });

        call_to_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = TOKEN_MODERATOR_1;

                listen_btn.setVisibility(View.GONE);
                call_to_btn.setVisibility(View.GONE);

                requestPermission(token);
//                Toast.makeText(MainActivity.this, "TOKEN_PUBLISHER", Toast.LENGTH_LONG).show();
            }
        });


        endCall_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSession.disconnect();

                listen_btn.setVisibility(View.VISIBLE);
                call_to_btn.setVisibility(View.VISIBLE);
                endCall_btn.setVisibility(View.GONE);

            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermission(String token){
        String [] perms = {Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};

        if(EasyPermissions.hasPermissions(this, perms)){

            // initialize view objects from your layout
//            mPublisherViewContainer = (FrameLayout) findViewById(R.id.publisher_container);
//            mSubscriberViewContainer = (FrameLayout) findViewById(R.id.subscriber_container);
//            subscriber_btn = (Button) findViewById(R.id.subscriber_id) ;
//            publisher_btn = (Button) findViewById(R.id.publisher_id);
                endCall_btn.setVisibility(View.VISIBLE);

            // initialize and connect to the session
            mSession = new Session.Builder( this, API_KEY, SESSION_ID ).build();
            mSession.setSessionListener( this );
            mSession.connect(token);

        }else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }


   // Todo: SessionListener methods interface
    @Override
    public void onConnected(Session session) {
        Log.d(LOG_TAG, "Session Connected");


        mPublisher = new Publisher.Builder( this )
                .resolution(Publisher.CameraCaptureResolution.HIGH)
//                .frameRate(Publisher.CameraCaptureFrameRate.FPS_30)
                .build();
        mPublisher.setPublisherListener( this ); // need implement PublisherKit.PublisherListener
        mPublisherViewContainer.addView(mPublisher.getView());

        if( mPublisher.getView() instanceof GLSurfaceView ){
            ( (GLSurfaceView) mPublisher.getView()).setZOrderOnTop(true); ;
        }


        mSession.publish( mPublisher );

    }

    @Override
    public void onDisconnected(Session session) {
        //End call vao day 1
        Log.d(LOG_TAG, "Session Disconnected");
//        Toast.makeText(MainActivity.this, "Session Disconnected", Toast.LENGTH_LONG).show();
        mPublisherViewContainer.removeAllViews();
        mSubscriberViewContainer.removeAllViews();
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.d(LOG_TAG, "Stream Received");

//        if (mSubscriber == null) {
//
//            mSubscriber = new Subscriber.Builder(this, stream).build();
//            mSession.subscribe(mSubscriber);
//            mSubscriberViewContainer.addView(mSubscriber.getView());
//        }
        mSubscriber = new Subscriber.Builder(this, stream).build();
        mSession.subscribe(mSubscriber);
        mSubscriberViewContainer.addView(mSubscriber.getView());

    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.d(LOG_TAG, "Stream Dropped");

        if (mSubscriber != null) {
            mSubscriber = null;
            mSubscriberViewContainer.removeAllViews();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.d(LOG_TAG, "Session error: " + opentokError.getMessage() );
    }

    // Todo: PublisherListener methods
    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        Log.d(LOG_TAG, "Publisher onStreamCreated");
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        //End call vao day 1
        Log.d(LOG_TAG, "Publisher onStreamDestroyed");
//        Toast.makeText(MainActivity.this, "Publisher onStreamDestroyed", Toast.LENGTH_LONG).show();
        mPublisherViewContainer.removeAllViews();
        mSubscriberViewContainer.removeAllViews();
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        Log.d( LOG_TAG, "Publisher error: " + opentokError.getMessage() );
    }
}
