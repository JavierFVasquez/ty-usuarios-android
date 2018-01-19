package com.imaginamos.usuariofinal.taxisya.activities;


import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.imaginamos.usuariofinal.taxisya.comm.Connectivity;
import com.imaginamos.usuariofinal.taxisya.comm.NetworkChangeReceiver;
import com.imaginamos.usuariofinal.taxisya.utils.Actions;
import com.imaginamos.usuariofinal.taxisya.models.Conf;
import com.imaginamos.usuariofinal.taxisya.R;

import android.widget.RelativeLayout;
import android.widget.TextView;


public class Main_MapActivity extends AppCompatActivity implements OnClickListener, NetworkChangeReceiver.NetworkReceiverListener,
        Connectivity.ConnectivityQualityCheckListener, OnMapReadyCallback{

    //private ImageView ver_taxista;
    private Button btnVerTaxista;

    private GoogleMap map;
    private ArrayList<LatLng> markerPoints;
    private double latitud, longitud, from_lat, from_lng;
    private String mAddress;
    private BroadcastReceiver mReceiver;
    private MarkerOptions options = new MarkerOptions();
    private Marker marker;
    private RelativeLayout mNoConnectivityPanel;
    public TextView time_arrive;
    private  LatLng mTaxi;
    private Button BT_Chat;

    private NetworkChangeReceiver mNetworkMonitor;

    private Connectivity connectivityChecker = new Connectivity(this);
    //private String AddressFromMapa;

    @Override
    public void onRestart() {

        super.onRestart();

        overridePendingTransition(R.anim.hold, R.anim.pull_out_to_right);

    }

    @Override
    protected void onResume() {

        super.onResume();
        displayConnectivityPanel(!Connectivity.isConnected(this) && !connectivityChecker.getConnectivityCheckResult());
        connectivityChecker.startConnectivityMonitor();
        mNetworkMonitor = new NetworkChangeReceiver(this);
        registerReceiver(mNetworkMonitor, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        connectivityChecker.stopConnectivityMonitor();
        unregisterReceiver(mNetworkMonitor);
    }

    @Override
    protected void onDestroy() {

        Log.v("onDestroy", "Main_MapActivity");
        super.onDestroy();

    }

    private void toFinish() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.mapa_taxista);
        //ver_taxista = (ImageView) findViewById(R.id.ver_taxista);
        btnVerTaxista = (Button) findViewById(R.id.btnVerTaxista);
        mNoConnectivityPanel = (RelativeLayout) findViewById(R.id.layout_no_connectivity);
        btnVerTaxista.setOnClickListener(this);

//        BT_Chat = (Button) findViewById(R.id.BT_Chat);
//
//        BT_Chat.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Main_MapActivity.this,);
//                Bundle b = new Bundle();
//                b.putString("service_id");
//                i.putExtras(b);
//                startActivity(i;);
//            }
//        });

        Bundle reicieveParams = getIntent().getExtras();
        if (reicieveParams != null) {

            latitud = reicieveParams.getDouble("latitud");
            longitud = reicieveParams.getDouble("longitud");

            from_lat = reicieveParams.getDouble("from_lat");
            from_lng = reicieveParams.getDouble("from_lng");
            //String from_lat = reicieveParams.getString("from_lat");
            //String from_lng = reicieveParams.getString("from_lng");
        }

        time_arrive = (TextView) findViewById(R.id.time_arrive);

//		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        MapFragment fm = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        //map = fm.getMap();
        fm.getMapAsync(this);

//        map.setMyLocationEnabled(true);

        markerPoints = new ArrayList<LatLng>();
        mTaxi = new LatLng(latitud, longitud);
        markerPoints.add(mTaxi);
        options.position(mTaxi);

        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.taxista_carrito));

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Actions.ACTION_TAXI_ARRIVED);
        intentFilter.addAction(Actions.ACTION_TAXI_GO);
        intentFilter.addAction(Actions.ACTION_CANCEL_OP_SERVICES);
        intentFilter.addAction(Actions.ACTION_CANCEL_DRIVER_SERVICE);
        intentFilter.addAction(Actions.ACTION_USER_CLOSE_SESSION);
        intentFilter.addAction(Actions.ACTION_MESSAGE_MASSIVE);

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (action.equals(Actions.ACTION_TAXI_ARRIVED) || action.equals(Actions.ACTION_CANCEL_DRIVER_SERVICE)
                        || action.equals(Actions.ACTION_CANCEL_OP_SERVICES)) {
                    toFinish();

                } else if (action.equals(Actions.ACTION_TAXI_GO)) {
                    try {
                        JSONObject position = new JSONObject(intent.getExtras().getString("service"));
                        latitud = position.getDouble("lat");
                        longitud = position.getDouble("lng");
                        dibujarTaxi(latitud, longitud);

                    } catch (Exception e) {
                        Log.e("ERROR", e.toString() + "");
                    }
                } else if (action.equals(Actions.ACTION_USER_CLOSE_SESSION)) {
                    Log.v("USER_CLOSE_SESSION", "Sesión cerrada");
                    Conf conf = new Conf(getApplicationContext());
                    conf.setLogin(false);

                    Intent in3 = new Intent(Main_MapActivity.this, LoginActivity.class);
                    in3.putExtra("target", 1);
                    startActivity(in3);
                    finish();

                } else if (intent.getAction().equals(Actions.ACTION_MESSAGE_MASSIVE)) {

                    Log.v("MESSAGE_MASSIVE", "mensaje global recibido");
                    String message = intent.getExtras().getString("message");
                    mostrarMensaje(message);

                }

            }
        };

        mAddress = String.valueOf(from_lat + from_lng);

        registerReceiver(mReceiver, intentFilter);

        //String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + mAddress + "&destinations=" + mTaxi + "&mode=driving&language=fr-FR&avoid=tolls&key=AIzaSyAK5bs-8nB8_exkk5JhYOtpLD83ZRCkmPM";
        //new GeoTask(Main_MapActivity.this).execute(url);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

            map = googleMap;
            map.setMyLocationEnabled(true);
            marker = map.addMarker(options);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(mTaxi, 17.0f));

    }


    void mostrarMensaje(final String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.informacion_importante);
        builder.setMessage(message);
        builder.setNeutralButton(R.string.text_aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.e("PUSH", "mensaje: " + message);
            }
        });
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }

    public void dibujarTaxi(double latitud, double longitud) {
        markerPoints = new ArrayList<LatLng>();
        LatLng taxi = new LatLng(latitud, longitud);
        marker.setPosition(taxi);
        map.animateCamera(CameraUpdateFactory.newLatLng(taxi));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.v("Main_MapActivity1", "onKeyDown");

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            toFinish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void err_driver() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onClick(View v) {
        Log.v("Main_MapActivity1", "onClick");
        toFinish();
    }

    public void onBackPressed() {
        Log.v("Main_MapActivity1", "onBackPressed");
    }


    private void displayConnectivityPanel(boolean display) {
        mNoConnectivityPanel.setVisibility(display ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onNetworkConnectivityChange(boolean connected) {
        displayConnectivityPanel(!connected);
    }

    @Override
    public void onConnectivityQualityChecked(boolean Optimal) {
//        displayConnectivityPanel(!Optimal);
    }

    /*@Override
    public void setDouble(String result) {

        String res[]=result.split(",");
        Double min=Double.parseDouble(res[0])/60;
        int dist=Integer.parseInt(res[1])/1000;
        time_arrive.setText(" " + (int) (min / 60) + " hr " + (int) (min % 60) + " minutos");

    }*/
}
