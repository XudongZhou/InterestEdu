package com.shadow.interestedu.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.opengl.GLSurfaceView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shadow.interestedu.AppApplication;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;
import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.CameraEnumerationAndroid;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RendererCommon;
import org.webrtc.VideoCapturerAndroid;
import org.webrtc.VideoRenderer;
import org.webrtc.VideoRendererGui;
import org.webrtc.VideoSource;
import org.webrtc.VideoTrack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.shadow.interestedu.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView tv;

    RequestQueue requestQueue;
    PeerConnectionFactory peerConnectionFactory;

    public static final String VIDEO_TRACK_ID = "video_track_id";
    public static final String AUDIO_TRACK_ID = "audio_track_id";
    public static final String LOCAL_MEDIA_STREAM_ID = "local_media_stream_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        PeerConnectionFactory.initializeAndroidGlobals(
                this,
                true,
                true,
                true);
        peerConnectionFactory = new PeerConnectionFactory();

        requestQueue = ((AppApplication)getApplicationContext()).requestQueue;
    }

    @OnClick(R.id.button)
    void OnTest(){

        Request request = NoHttp.createJsonObjectRequest("http://192.168.10.249:8080/InterestEdu/bb");
        requestQueue.add(0, request, listener );
        JSONObject jo;
    }

    @OnClick(R.id.button2)
    void OnTest2(){
        Request request = NoHttp.createStringRequest("http://192.168.10.249:8080/InterestEdu/aa");
        requestQueue.add(0, request, listener );
    }

    @OnClick(R.id.button3)
    void OnTest3(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e("rtc","No camera permission!");
        }
        else {
            Log.e("rtc","camera permission YES!");
        }
        String deviceName = CameraEnumerationAndroid.getNameOfFrontFacingDevice();
        Log.e("rtc",deviceName);
        VideoCapturerAndroid videoCapturer = VideoCapturerAndroid.create(deviceName, eventsHandler);
        if(videoCapturer != null)
            Log.e("rtc","videoCapturer no null!");
        MediaConstraints audioConstraints = new MediaConstraints();

        VideoSource videoSource = peerConnectionFactory.createVideoSource(videoCapturer, audioConstraints);
        VideoTrack localVideoTrack = peerConnectionFactory.createVideoTrack(VIDEO_TRACK_ID, videoSource);
        if(localVideoTrack != null)
            Log.e("rtc","localVideoTrack no null!");
        AudioSource audioSource = peerConnectionFactory.createAudioSource(audioConstraints);
        AudioTrack localAudioTrack = peerConnectionFactory.createAudioTrack(AUDIO_TRACK_ID, audioSource);

        GLSurfaceView glView = new GLSurfaceView(this);
        addContentView(glView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        VideoRendererGui.setView(glView, new Runnable(){
            @Override
            public void run() {

            }
        });

        try {
            VideoRenderer renderer = VideoRendererGui.createGui(0, 0, 50, 50, RendererCommon.ScalingType.SCALE_ASPECT_FILL,true);
            localVideoTrack.addRenderer(renderer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaStream mediaStream = peerConnectionFactory.createLocalMediaStream(LOCAL_MEDIA_STREAM_ID);
        mediaStream.addTrack(localVideoTrack);
        mediaStream.addTrack(localAudioTrack);
    }

    VideoCapturerAndroid.CameraEventsHandler eventsHandler = new VideoCapturerAndroid.CameraEventsHandler(){

        @Override
        public void onCameraError(String s) {

        }

        @Override
        public void onCameraFreezed(String s) {

        }

        @Override
        public void onCameraOpening(int i) {

        }

        @Override
        public void onFirstFrameAvailable() {

        }

        @Override
        public void onCameraClosed() {

        }
    };

    OnResponseListener listener = new OnResponseListener() {

        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response response) {
            Toast.makeText(MainActivity.this,"http onSucceed!",Toast.LENGTH_LONG).show();
            tv.setText(response.get().toString());
        }

        @Override
        public void onFailed(int what, Response response) {
            Toast.makeText(MainActivity.this,"http failed!",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFinish(int what) {

        }
    };
}
