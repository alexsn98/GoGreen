package com.example.gogreen;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;

public class QRCodeActivity extends AppCompatActivity {

    CameraSource cs;
    TextView tv;

    // Start without a delay
    // Vibrate for 100 milliseconds
    // Sleep for 1000 milliseconds
    long[] pattern = {0, 200, 1500};
    static ArrayList<Integer> qrCodesRead = new ArrayList<>();
    static boolean read;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        SurfaceView sv = findViewById(R.id.qrcode);

        //tv = findViewById(R.id.textqr);

        BarcodeDetector bd = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();

        cs = new CameraSource.Builder(this, bd)
                .setRequestedPreviewSize(640, 480).setAutoFocusEnabled(true).build();

        read = false;
        sv.getHolder().addCallback(new SurfaceHolder.Callback() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    cs.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cs.stop();
            }
        });

        bd.setProcessor(new Detector.Processor<Barcode>() {
            String s;

            @Override
            public void release() {

                changeScreen(s);
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();

                if (qrCodes.size() == 1 && !read) {
                    read = true;
                    String[] input = qrCodes.valueAt(0).displayValue.split(";");
                    Log.d("pau",String.valueOf(input.length));
                    Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

                    vibrator.vibrate(pattern, 0);
                    s = "Ganhas-te " + input[0] + " pontos de experiência " + " e " + input[1] + " moedas ";
                    if (input.length > 2) s.concat(" e a carta " + input[2]);

                    AvatarButtonsFragment.increaseXP(Integer.valueOf(input[0]));
                    AvatarActivity.setCoins(AvatarActivity.getCoins() + Integer.valueOf(input[1]));

                    vibrator.cancel();

                    Intent intent = new Intent(QRCodeActivity.this, AvatarActivity.class);
                    intent.putExtra("string_qr", s);
                    startActivity(intent);

                    finish();

                }


            }

        });
    }

    public void changeScreen(String s) {
        Intent intent = new Intent(this, AvatarActivity.class);
        intent.putExtra("string_qr", s);
        startActivity(intent);
        finish();
    }


}
