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

    private static String SESSION_ID = "2_MX40NjUxODYyMn5-MTU4MjYwMjcxNTQyNX5DU0ZIc0IvR2w2M200UFVuNzkxYUIrU0Z-fg";
    private static String TOKEN = "";
    private static String TOKEN_PUBLISHER = "T1==cGFydG5lcl9pZD00NjUxODYyMiZzaWc9NDIzM2Q4ODZhZTFhYjM1NjllZjdjMjNlNzYyYWViZGFjNDA0YzU5YjpzZXNzaW9uX2lkPTJfTVg0ME5qVXhPRFl5TW41LU1UVTRNakkyTURrMU5EUXhPSDVMYzFoU1dFRlFlVnAwVlRoa2RscEhNR3BYT0VOd05VVi1mZyZjcmVhdGVfdGltZT0xNTgyMjY4ODc4Jm5vbmNlPTAuNjA5MDAwNjYzMDk5MDA2OCZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNTgyMjcyNDc2JmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";
    private static String TOKEN_SUBSCRIBER = "T1==cGFydG5lcl9pZD00NjUxODYyMiZzaWc9YzA4NzM4NTZlMWQ4OTg4YTBhNTcyNjE4Mjc5MTMyZDE2ZjkxMmFiZDpzZXNzaW9uX2lkPTJfTVg0ME5qVXhPRFl5TW41LU1UVTRNakkyTURrMU5EUXhPSDVMYzFoU1dFRlFlVnAwVlRoa2RscEhNR3BYT0VOd05VVi1mZyZjcmVhdGVfdGltZT0xNTgyMjY4ODk1Jm5vbmNlPTAuNzcyMDY0MTMxNTMyMjM3JnJvbGU9c3Vic2NyaWJlciZleHBpcmVfdGltZT0xNTgyMjcyNDk0JmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";
    private static String TOKEN_MODERATOR_1 = "T1==cGFydG5lcl9pZD00NjUxODYyMiZzaWc9YWQzNWE3NmMzMDU0MWI0YTA0N2M2Y2Y1MjgyODZmODRiYmVkNmQwYjpzZXNzaW9uX2lkPTJfTVg0ME5qVXhPRFl5TW41LU1UVTRNall3TWpjeE5UUXlOWDVEVTBaSWMwSXZSMncyTTIwMFVGVnVOemt4WVVJclUwWi1mZyZjcmVhdGVfdGltZT0xNTgyNjAyNzQyJm5vbmNlPTAuMDM4ODgyNjIzMDc5MjkwOTEmcm9sZT1tb2RlcmF0b3ImZXhwaXJlX3RpbWU9MTU4MjY4OTE0MSZpbml0aWFsX2xheW91dF9jbGFzc19saXN0PQ==";
    private static String TOKEN_MODERATOR_2 = "T1==cGFydG5lcl9pZD00NjUxODYyMiZzaWc9NGI3MGYwNzUwYjc5OWRjMjZhMjk1MDRlOWM0MjY5Yjg0YWU1YjIzNzpzZXNzaW9uX2lkPTJfTVg0ME5qVXhPRFl5TW41LU1UVTRNall3TWpjeE5UUXlOWDVEVTBaSWMwSXZSMncyTTIwMFVGVnVOemt4WVVJclUwWi1mZyZjcmVhdGVfdGltZT0xNTgyNjAyNzUzJm5vbmNlPTAuODA5MTM4NzYwNzg5NTU5NyZyb2xlPW1vZGVyYXRvciZleHBpcmVfdGltZT0xNTgyNjg5MTUyJmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";

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
                TOKEN = TOKEN_SUBSCRIBER;

                listen_btn.setVisibility(View.GONE);
                call_to_btn.setVisibility(View.GONE);

                requestPermission(TOKEN_MODERATOR_2);

//                Toast.makeText(MainActivity.this, "TOKEN_SUBSCRIBER", Toast.LENGTH_LONG).show();
            }
        });

        call_to_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TOKEN = TOKEN_PUBLISHER;

                listen_btn.setVisibility(View.GONE);
                call_to_btn.setVisibility(View.GONE);

                requestPermission(TOKEN_MODERATOR_1);
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
                .frameRate(Publisher.CameraCaptureFrameRate.FPS_30)
                .resolution(Publisher.CameraCaptureResolution.HIGH)
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

        if (mSubscriber == null) {

            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSession.subscribe(mSubscriber);
            mSubscriberViewContainer.addView(mSubscriber.getView());
        }
//        mSubscriber = new Subscriber.Builder(this, stream).build();
//        mSession.subscribe(mSubscriber);
//        mSubscriberViewContainer.addView(mSubscriber.getView());

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
