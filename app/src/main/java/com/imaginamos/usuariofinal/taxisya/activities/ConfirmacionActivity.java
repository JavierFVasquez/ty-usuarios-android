package com.imaginamos.usuariofinal.taxisya.activities;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;
import com.imaginamos.taxisya.activities.MapaActivitys;
import com.imaginamos.usuariofinal.taxisya.Model.DirectionsResponse;
import com.imaginamos.usuariofinal.taxisya.Model.FeesResponse;
import com.imaginamos.usuariofinal.taxisya.Model.UpdateResponse;
import com.imaginamos.usuariofinal.taxisya.Model.UpdateServiceResponse;
import com.imaginamos.usuariofinal.taxisya.adapter.BDAdapter;
import com.imaginamos.usuariofinal.taxisya.comm.Connectivity;
import com.imaginamos.usuariofinal.taxisya.comm.NetworkChangeReceiver;
import com.imaginamos.usuariofinal.taxisya.comm.NetworkChangeReceiver.NetworkReceiverListener;
import com.imaginamos.usuariofinal.taxisya.io.ApiService;
import com.imaginamos.usuariofinal.taxisya.models.CancelServiceResponse;
import com.imaginamos.usuariofinal.taxisya.utils.Actions;
import com.imaginamos.usuariofinal.taxisya.models.Conf;
import com.imaginamos.usuariofinal.taxisya.comm.Connect;
import com.imaginamos.usuariofinal.taxisya.comm.MiddleConnect;
import com.imaginamos.usuariofinal.taxisya.utils.Utils;
import com.imaginamos.usuariofinal.taxisya.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;

public class ConfirmacionActivity extends Activity implements OnClickListener, Connectivity.ConnectivityQualityCheckListener, NetworkReceiverListener, OnMapReadyCallback {

    private String TAG = "ConfirmacionActivity";
    private TextView nombre_taxista, tv_celular_taxista, placa_taxi, marca_taxi;
    private TextView messageTextView;
    private TextView authorizationCodeTextView;

    private ImageButton quality;
    private ImageView btn_cancelar, foto, volvermapa;
    private Button btnCancelar, btnCalificar, BT_Interrupt;
    private ProgressDialog pDialog;
    private double latitud_taxi, longitud_taxi, dir_full, from_lat, from_lng;
    private BroadcastReceiver mReceiver;
    public static boolean open = false;
    private Conf conf;

    private Timer myTimer = new
            Timer();
    private int reintento = 0;
    private int status = 0;
    private String uuid;
    private String id_user, service_id, address;
    private boolean service_agend = false;

    private String mServiceId;
    private int mStatusOld;
    private int mStatusNew;

    private String mTotUnits;
    private String mTotCharge1;
    private String mTotCharge2;
    private String mTotCharge3;
    private String mTotCharge4;
    private String mTotValue;
    private String mAddress;
    private int mPayType;

    private BDAdapter mySQLiteAdapter;
    private Cursor mCursor;

    private RelativeLayout mNoConnectivityPanel;
    private ImageView mConnectivityLoaderImage;
    private Connectivity mConnectivityChecker = new Connectivity(this);
    private NetworkChangeReceiver mNetworkMonitor;
    private Button BT_Chat;
    private boolean first_time = false;
    private DatabaseReference chat_ref;
    private ArrayList<LatLng> markerPoints;
    private LatLng mTaxi;
    private MarkerOptions options = new MarkerOptions();
    private GoogleMap map;
    private Marker marker;
    private String celular_taxista;
    private String driver_id;

    @Override
    protected void onRestart() {
        Log.v("onRestart", "ConfirmacionActivity");
        super.onRestart();

        try {

            if (getIntent().getExtras().containsKey("driver")) {
                cargarTaxista(new JSONObject(getIntent().getExtras().getString("driver")));
            }

            if (getIntent().getExtras().containsKey("kind_id")) {
                if (getIntent().getExtras().getString("driver").equals("2")) service_agend = true;
            }

        } catch (Exception e) {

            Log.e("HOLA", "" + e.toString());
        }

    }

    @Override
    protected void onDestroy() {
        Log.v("onDestroy", "ConfirmacionActivity");
        super.onDestroy();

        open = false;

        if (mySQLiteAdapter != null) {
            mySQLiteAdapter.close();
        }


        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }

        if (myTimer != null) {
            myTimer.cancel();
            myTimer.purge();
            myTimer = null;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirmacion);

        conf = new Conf(this);

        uuid = Utils.uuid(this);

        mySQLiteAdapter = new BDAdapter(this);
        mySQLiteAdapter.openToWrite();

        Bundle reicieveParams = getIntent().getExtras();
        mPayType = 1;
        Log.v("LOG1", "Confirmacion mPayType " + mPayType);
        if (reicieveParams != null && reicieveParams.containsKey("pay_type")) {
            mPayType = reicieveParams.getInt("pay_type");
            Log.v("LOG1", "    2 Confirmacion mPayType " + mPayType);

        }
        Log.v("LOG1", "    3 Confirmacion mPayType " + mPayType);


        MapFragment fm = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        //map = fm.getMap();
        fm.getMapAsync(this);

        markerPoints = new ArrayList<LatLng>();
        mTaxi = new LatLng(latitud_taxi, longitud_taxi);
        markerPoints.add(mTaxi);
        options.position(mTaxi);

        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.taxista_carrito));

        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(Actions.ACTION_TAXI_ARRIVED);
        intentFilter.addAction(Actions.ACTION_CANCEL_DRIVER_SERVICE);
        intentFilter.addAction(Actions.ACTION_CANCEL_OP_SERVICES);
        intentFilter.addAction(Actions.ACTION_USER_CLOSE_SESSION);
        intentFilter.addAction(Actions.ACTION_MESSAGE_MASSIVE);
        intentFilter.addAction(Actions.ACTION_SERVICE_ENDED);
        intentFilter.addAction(Actions.ACTION_TAXI_GO);

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Actions.ACTION_CANCEL_DRIVER_SERVICE)) {
                    // muestra dialogo
                    mServiceId = intent.getExtras().getString("service_id");

                    String message = intent.getExtras().getString("message");

                    Log.v("SERVICE_PUSH", "CANCEL_DRIVER_SERVICE " + message);
                    Log.e("SERVICE_CMS", "GCM CNF CANCEL_DRIVER_SERVICE " + mServiceId + " " + message);

                    // chequear el estado anterior y cambiarlo
                    mostrarAviso(message, true);
                    //finish();
                } else if (intent.getAction().equals(Actions.ACTION_CANCEL_OP_SERVICES)) {
                    Log.v("SERVICE_PUSH", "CANCEL_OP_SERVICES servicio cancelado");
                    mServiceId = intent.getExtras().getString("service_id");
                    Log.e("SERVICE_CMS", "GCM CNF CANCEL_OP_SERVICES " + mServiceId);

                    //finish();
                    callMap();
                } else if (intent.getAction().equals(Actions.ACTION_TAXI_ARRIVED)) {
                    Log.v("SERVICE_PUSH", "TAXI_ARRIVED servicio");
                    Log.v("TAXI_ARRIVED", "el taxi llegó");
                    String message = intent.getExtras().getString("message");

                    mServiceId = intent.getExtras().getString("service_id");
                    Log.e("SERVICE_CMS", "GCM CNF TAXI_ARRIVED " + mServiceId);


                    mostrarAviso(message, false);
                    //btn_cancelar.setVisibility(View.GONE);
                    btnCancelar.setVisibility(View.GONE);
                    BT_Interrupt.setVisibility(View.VISIBLE);

                } else if (intent.getAction().equals(Actions.ACTION_SERVICE_ENDED)) {
                    try {
                        checkService();
                    } catch (Exception e) {

                    }
                } else if (intent.getAction().equals(Actions.ACTION_USER_CLOSE_SESSION)) {
                    Log.v("USER_CLOSE_SESSION", "Sesión cerrada - confirmación");
                    Conf conf = new Conf(getApplicationContext());
                    conf.setLogin(false);

                    Intent in3 = new Intent(ConfirmacionActivity.this, LoginActivity.class);
                    in3.putExtra("target", 1);
                    startActivity(in3);
                    finish();

                } else if (intent.getAction().equals(Actions.ACTION_MESSAGE_MASSIVE)) {
                    Log.v("MESSAGE_MASSIVE", "mensaje global recibido");
                    String message = intent.getExtras().getString("message");
                    mostrarMensaje(message);

                } else if (intent.getAction().equals(Actions.ACTION_TAXI_ARRIVED) || intent.getAction().equals(Actions.ACTION_CANCEL_DRIVER_SERVICE)
                        || intent.getAction().equals(Actions.ACTION_CANCEL_OP_SERVICES)) {
                    toFinish();

                } else if (intent.getAction().equals(Actions.ACTION_TAXI_GO)) {
                    try {
                        JSONObject position = new JSONObject(intent.getExtras().getString("service"));
                        latitud_taxi = position.getDouble("lat");
                        longitud_taxi = position.getDouble("lng");
//                        dibujarTaxi(latitud_taxi, longitud_taxi);

                    } catch (Exception e) {
                        Log.e("ERROR", e.toString() + "");
                    }
                } else if (intent.getAction().equals(Actions.ACTION_USER_CLOSE_SESSION)) {
                    Log.v("USER_CLOSE_SESSION", "Sesión cerrada");
                    Conf conf = new Conf(getApplicationContext());
                    conf.setLogin(false);

                    Intent in3 = new Intent(ConfirmacionActivity.this, LoginActivity.class);
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

        try {
            registerReceiver(mReceiver, intentFilter);
        } catch (Exception e) {

        }

        nombre_taxista = (TextView) findViewById(R.id.nom_taxista);
        tv_celular_taxista = (TextView) findViewById(R.id.celular);
        placa_taxi = (TextView) findViewById(R.id.numero_placa);
        marca_taxi = (TextView) findViewById(R.id.marca_taxi);
        foto = (ImageView) findViewById(R.id.Globoperfil);
        messageTextView = (TextView) findViewById(R.id.message);
        authorizationCodeTextView = (TextView) findViewById(R.id.authorizationCode);

        //if (mPayType == 3) {
        String strPhone = conf.getPhone();
        String strCode = strPhone.length() > 2 ? strPhone.substring(strPhone.length() - 2) : strPhone;
        String strMessage = getResources().getString(R.string.title_code_authorization) + strCode;
        authorizationCodeTextView.setText(strMessage);
        authorizationCodeTextView.setVisibility(View.VISIBLE);
        //}

        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        BT_Interrupt = (Button) findViewById(R.id.BT_Interrupt);

        btnCalificar = (Button) findViewById(R.id.btnCalificar);

        mNoConnectivityPanel = (RelativeLayout) findViewById(R.id.layout_no_connectivity);
        mConnectivityLoaderImage = (ImageView) findViewById(R.id.loader_icon);

        mConnectivityChecker.setConnectivityCheckInterval(5000);
        mConnectivityChecker.setConnectivityIndicator(1500);

        // servicio sin activity_calificar
        if (getIntent().getExtras().getString("qualification") != null) {
            if (getIntent().getExtras().getString("qualification").equals("1")) {
                btnCalificar.setVisibility(View.VISIBLE);
                btnCancelar.setVisibility(View.GONE);
                BT_Interrupt.setVisibility(View.GONE);
                // set test
                messageTextView.setText(R.string.agradecimiento_uso_taxisya);
            }
        }

        tv_celular_taxista.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!celular_taxista.equals("")) {
                    call(celular_taxista);
                }

            }
        });

        btnCancelar.setOnClickListener(this);
        btnCalificar.setOnClickListener(this);
        BT_Interrupt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mDialog = new AlertDialog.Builder(ConfirmacionActivity.this);
                mDialog.setTitle(getString(R.string.interrupt_request));
                mDialog.setMessage(getString(R.string.confirm_interrupt));
                mDialog.setCancelable(false);

                mDialog.setPositiveButton(R.string.text_si,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                interruptService(false);
                            }
                        });
                mDialog.setNeutralButton(R.string.select_new_destination,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                interruptService(true);
                            }
                        });

                mDialog.setNegativeButton(R.string.text_no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                mDialog.show();
            }
        });

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).defaultDisplayImageOptions(defaultOptions).build();

        ImageLoader.getInstance().init(config);

        try {

            cargarTaxista(new JSONObject(getIntent().getExtras().getString("driver")));

            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Log.e("TIMER_EJECUTANDO", "CARGANDO TAXISTA INI EJECUCIÓN *** ");
                    try {
                        updateTaxiPosition();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, 5000, 3000);



        } catch (Exception e) {
            Log.e("HOLA", "" + e.toString());
        }


        try {
            checkService();
        } catch (JSONException e) {
            Log.e("JSON", e.toString());
        }
    }

    private void interruptService(final boolean new_destiny) {
        pDialog = new ProgressDialog(ConfirmacionActivity.this);
        pDialog.setMessage(getString(R.string.text_interrumpiendo));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connect.BASE_GOOGLE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        String dir1= from_lat + "," + from_lng;

        Call<DirectionsResponse> call_directions=service.directions(dir1,latitud_taxi + "," + longitud_taxi);
        call_directions.enqueue(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {

                final double distance = response.body().getRoutes().get(0).getLegs().get(0).getDistance().getValue();
                final int time = response.body().getRoutes().get(0).getLegs().get(0).getDuration().getValue();
                int inhours = (int) ((time / 60) / 60);
                int minutes = ((time / 60) % 60);
                final String time_formated = inhours + ":" + minutes;


                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(logging);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Connect.BASE_URL_IP)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();

                ApiService service = retrofit.create(ApiService.class);
                Call<FeesResponse> call_profile = service.requestFees(String.valueOf(latitud_taxi), String.valueOf(longitud_taxi), "0:00");
                call_profile.enqueue(new Callback<FeesResponse>() {
                    @Override
                    public void onResponse(Call<FeesResponse> call, Response<FeesResponse> response) {

                        int recargo_nocturno = Integer.parseInt(response.body().getNocturno().trim());
                        int recargo_aeropuerto = Integer.parseInt(response.body().getKm().trim());
                        int recargo_puerta_a_puerta = Integer.parseInt(response.body().getPuerta_a_puerta().trim());

                        final int price = MapaActivitys.getEstimatedPrice((int) distance, time, response.body().getTotal_recargo(),true);

                        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                        httpClient.addInterceptor(logging);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Connect.BASE_URL_IP)
                                .addConverterFactory(GsonConverterFactory.create())
                                .client(httpClient.build())
                                .build();

                        ApiService service = retrofit.create(ApiService.class);

                        Call<UpdateServiceResponse> call_profile=service.service_update(Integer.parseInt(service_id),latitud_taxi,longitud_taxi,price,distance,time_formated);
                        call_profile.enqueue(new Callback<UpdateServiceResponse>() {
                            @Override
                            public void onResponse(Call<UpdateServiceResponse> call, Response<UpdateServiceResponse> response) {

                                Toast.makeText(ConfirmacionActivity.this,"Total a pagar : " + price,Toast.LENGTH_SHORT).show();
                                if(new_destiny){
                                    Intent i =  new Intent(ConfirmacionActivity.this,MapaActivitys.class);
                                    Bundle b =  new Bundle();
                                    b.putBoolean("new_destination",true);
                                    b.putInt("last_service_price",price);
                                    b.putString("charge1",mTotCharge1);
                                    b.putString("charge2",mTotCharge2);
                                    b.putString("charge3",mTotCharge3);
                                    b.putString("charge4",mTotCharge4);
                                    b.putDouble("from_lat",latitud_taxi);
                                    b.putDouble("from_lng",longitud_taxi);
                                    b.putInt("last_service",Integer.parseInt(service_id));
                                    i.putExtras(b);
                                    startActivity(i);
                                }else{
                                    try {
                                        checkService();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                pDialog.dismiss();

                            }

                            @Override
                            public void onFailure(Call<UpdateServiceResponse> call, Throwable t) {
                                Log.w("-----Error-----",t.toString());
                                pDialog.dismiss();
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<FeesResponse> call, Throwable t) {
                        Log.w("-----Error-----", t.toString());
                        pDialog.dismiss();
                    }
                });



            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                Log.w("-----Error-----",t.toString());
                pDialog.dismiss();
            }
        });


    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        return super.onKeyUp(keyCode, event);
    }

    void mostrarMensaje(final String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_informacion_importante_2);
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

    private void toFinish() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
        finish();
    }

    void mostrarAviso(final String mensaje, final boolean end) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_informacion);
        builder.setMessage(mensaje);
        builder.setNeutralButton(R.string.text_aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.e("PUSH", "mensaje: ");
                if (end) {
                    callMap();
                }
            }
        });
        builder.setCancelable(false);
        builder.create();
        builder.show();

    }

    void callMap() {
        Intent in3 = new Intent(ConfirmacionActivity.this, MapaActivitys.class);
        startActivity(in3);
        finish();
    }

    void servicioEnOtroDispositivo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_informacion);
        builder.setMessage(R.string.mensaje_servicio_tomado);
        builder.setNeutralButton(R.string.text_aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.e("PUSH", "mensaje: ");
                //
                Conf conf = new Conf(getApplicationContext());
                conf.setLogin(false);

                Intent in3 = new Intent(ConfirmacionActivity.this, LoginActivity.class);
                in3.putExtra("target", 1);
                startActivity(in3);
                finish();
            }
        });
        builder.setCancelable(false);
        builder.create();
        builder.show();

    }

    @Override
    public void onClick(final View v) {

        switch (v.getId()) {

//            case R.id.btnMapa:
//                Log.v("Main_MapActivity1", "VerMapa 1");
//                Log.v("ConfirmacionActivity", "btn_map");
//                Intent in = new Intent(ConfirmacionActivity.this, Main_MapActivity.class);
//                in.putExtra("latitud", latitud_taxi);
//                in.putExtra("longitud", longitud_taxi);
//                in.putExtra("from_lat", from_lat);
//                in.putExtra("from_lng", from_lng);
//
//                startActivity(in);
//                break;

            case R.id.btnCancelar:
                AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
                mDialog.setTitle(getString(R.string.important));
                mDialog.setMessage(getString(R.string.confirm_cancel));
                mDialog.setCancelable(false);

                mDialog.setPositiveButton(R.string.text_si,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                cancelarService();
                            }
                        });

                mDialog.setNegativeButton(R.string.text_no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                mDialog.show();

                break;

            case R.id.btnCalificar:
                myTimer.cancel();
                myTimer.purge();
                myTimer = null;

                Intent i = new Intent(ConfirmacionActivity.this, CalificarActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        open = true;
        displayConnectivityPanel(!Connectivity.isConnected(this) && !mConnectivityChecker.getConnectivityCheckResult());
        mConnectivityChecker.startConnectivityMonitor();
        mNetworkMonitor = new NetworkChangeReceiver(this);
        registerReceiver(mNetworkMonitor, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        try {
            checkService();
        } catch (JSONException e) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mConnectivityChecker.stopConnectivityMonitor();
        unregisterReceiver(mNetworkMonitor);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 5) {
            this.setResult(5);
            this.finish();
        }
    }

    public void cancelarService() {


        pDialog = new ProgressDialog(ConfirmacionActivity.this);
        pDialog.setMessage("Cancelando Servicio....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connect.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<CancelServiceResponse> call_profile = service.cancelService(conf.getIdUser());
        call_profile.enqueue(new Callback<CancelServiceResponse>() {
            @Override
            public void onResponse(Call<CancelServiceResponse> call, Response<CancelServiceResponse> response) {
                pDialog.dismiss();
                try {

                    if (response.body() != null && response.body().getError() == 0) {

                        myTimer.cancel();
                        myTimer.purge();
                        myTimer = null;

                        callMap();
                        //finish();
                    } else {
                        err_cancel();
                    }
                } catch (Exception e) {
                    Log.v("CANCEL_SERVICE", "responsejson catch");
                    err_cancel();
                }

            }

            @Override
            public void onFailure(Call<CancelServiceResponse> call, Throwable t) {
                Log.w("-----Error-----", t.toString());
                err_cancel();
                pDialog.dismiss();

            }
        });


//        MiddleConnect.cancelService(
//                getString(R.string.cancel_service, conf.getIdUser()),
//                new AsyncHttpResponseHandler() {
//
//                    @Override
//                    public void onStart() {
//                        try {
//                            pDialog = new ProgressDialog(ConfirmacionActivity.this);
//                            pDialog.setMessage(getString(R.string.title_cancelando_servicio));
//                            pDialog.setIndeterminate(false);
//                            pDialog.setCancelable(false);
//                            pDialog.show();
//                        } catch (Exception e) {
//                        }
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                        String response = new String(responseBody);
//                        Log.v("CANCEL_SERVICE","response " + response);
//                        try {
//
//                            JSONObject responsejson = new JSONObject(response);
//
//                            Log.v("CANCEL_SERVICE","responsejson " + responsejson);
//
//                            if (responsejson != null && responsejson.getInt("error") == 0) {
//
//                                myTimer.cancel();
//                                myTimer.purge();
//                                myTimer = null;
//
//                                callMap();
//                                //finish();
//                            } else {
//                                err_cancel();
//                            }
//                        } catch (Exception e) {
//                            Log.v("CANCEL_SERVICE","responsejson catch");
//                            err_cancel();
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                        String response = new String(responseBody);
//                        Log.v("CANCEL_SERVICE","failure response " + response);
//                        err_cancel();
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        try {
//                            pDialog.dismiss();
//                        } catch (Exception e) {
//                        }
//                    }
//                });

    }

    public void err_cancel() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        Toast.makeText(getApplicationContext(),
                getString(R.string.error_cancel_service), Toast.LENGTH_SHORT)
                .show();
    }

    public void err_driver() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        Toast.makeText(getApplicationContext(),
                getString(R.string.error_load_driver), Toast.LENGTH_SHORT)
                .show();
    }

    private void cargarTaxista(JSONObject driver) {

        try {
            Log.v("cargaTaxista", "driver1 = " + driver.toString());
            Log.v("cargaTaxista", "taxi0 = " + driver.getJSONObject("car").toString());

            String nombre;
            String telefono;
            String foto;
            String latitud;
            String longitud;

            JSONObject validaCampo;

            if (driver.has("cellphone")) {
                telefono = driver.getString("cellphone");
            } else {
                telefono = driver.getString("telephone");
            }

            Log.v("checkService", "cellphone");


            if (driver.has("lat")) {
                latitud = driver.getString("lat");
                longitud = driver.getString("lng");
            } else {
                latitud = driver.getString("crt_lat");
                longitud = driver.getString("crt_lng");
            }
            nombre = driver.getString("name");
            foto = driver.getString("picture");

            //Log.v("cargaTaxista", "taxi1 = " + driver.getJSONObject("car").toString());
            //Log.v("cargaTaxista", "taxi2 = " + driver.getJSONArray("car").toString());

            JSONObject taxi = driver.getJSONObject("car");

            latitud_taxi = Double.parseDouble(latitud);
            longitud_taxi = Double.parseDouble(longitud);

            String placa;
            String marca;

            if (taxi.has("plate")) {
                placa = taxi.getString("plate");
                marca = taxi.getString("brand");
            } else {
                placa = taxi.getString("placa");
                marca = taxi.getString("car_brand");
            }
            descargarFoto(Connect.BASE_URL_IP + foto);

            nombre_taxista.setText(nombre);
            placa_taxi.setText(placa);
            //placa_taxi.setTextSize(TypedValue.COMPLEX_UNIT_DIP,28);
            celular_taxista =  telefono;
            tv_celular_taxista.setText(R.string.llamar);
            marca_taxi.setText(marca);

        } catch (JSONException e) {
            Log.e(TAG, "cargaTaxista error" + e.toString());
        }
    }

    private void descargarFoto(String imageHttpAddress) {
        Log.e("IMAGE", imageHttpAddress);
        try {
            ImageLoader.getInstance().displayImage(imageHttpAddress, foto);
        } catch (Exception e) {
            Log.e(TAG, "ERROR CARGANDO IMAGEN");
        }

    }

    private ValueEventListener el = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e("The read failed: " , String.valueOf(databaseError.getCode()));
        }
    };



    public void updateTaxiPosition () throws JSONException {
        id_user = conf.getIdUser();
        service_id = conf.getServiceId();
        mServiceId = service_id;

        MiddleConnect.checkStatusService(this, id_user, service_id, "uuid", address, from_lat, from_lng, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.v("checkService", "onStart");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    Log.v("checkService", "SUCCES: "+response);
                    JSONObject responsejson = new JSONObject(response);
                    latitud_taxi = responsejson.getJSONObject("driver").getDouble("crt_lat");
                    longitud_taxi = responsejson.getJSONObject("driver").getDouble("crt_lng");
                    dibujarTaxi(latitud_taxi, longitud_taxi);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("checkService",  e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Log.v("checkService", "CONFIRMTION onFailure");

            }

            @Override
            public void onFinish() {
            }
        });
    }


    public void checkService() throws JSONException {

        id_user = conf.getIdUser();
        service_id = conf.getServiceId();
        driver_id = conf.getDriverId();
        mServiceId = service_id;



        BT_Chat = (Button) findViewById(R.id.BT_Chat);

        FirebaseApp secondary = FirebaseApp.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance(secondary);
        chat_ref = database.getReference("chat/taxi_user/"+service_id);


//        chat_ref.addValueEventListener(el);

        BT_Chat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConfirmacionActivity.this, ChatActivity.class);
                Bundle b = new Bundle();
                b.putString("service_id", service_id);
                b.putString("driver_id", driver_id);
                i.putExtras(b);
                startActivity(i);
            }
        });

        Log.v("checkService", "CONFIRMATION id_user=" + id_user + " service_id=" + service_id);

        MiddleConnect.checkStatusService(this, id_user, service_id, "uuid", address, from_lat, from_lng, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.v("checkService", "onStart");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    Log.v("checkService", "SUCCES: "+response);
                    JSONObject responsejson = new JSONObject(response);
                    //if (responsejson.getInt("status_id"))
                    //{
                    status = responsejson.getInt("status_id");
                    Log.i("SERVICE - STATUS", String.valueOf(status));
                    if(status > 5){
                        callMap();
                    }
                    String user_uuid_service = responsejson.getJSONObject("user").getString("uuid");


                    mAddress = responsejson.getString("address");
                    from_lat = responsejson.getDouble("from_lat");
                    from_lng = responsejson.getDouble("from_lng");
                    latitud_taxi = responsejson.getJSONObject("driver").getDouble("crt_lat");
                    longitud_taxi = responsejson.getJSONObject("driver").getDouble("crt_lng");

                    dibujarTaxi(latitud_taxi, longitud_taxi);
                    conf.setDriverId(responsejson.getString("driver_id"));
                    conf.setCarId(responsejson.getString("car_id"));


                    if (user_uuid_service.equals(uuid)) {
                        Log.v("checkService", "el uuid es el mismo");
                    } else {
                        Log.v("checkService", "ell uuid es diferente, parece que fue tomado en otro dispositivo");
                        servicioEnOtroDispositivo();
                    }

                    if ((status == 2) || (status == 4) || (status == 5)) {

                        // actualizar estado del servicio en la base de datos
                        mCursor = mySQLiteAdapter.filterService(mServiceId);
                        if (mCursor.getCount() > 0) {
                            mCursor.moveToPosition(0);
                            mStatusOld = Integer.valueOf(mCursor.getString(mCursor.getColumnIndex(BDAdapter.SRV_STA)));
                            mStatusNew = status;

                            mCursor.close();

                            Log.e("SERVICE_CMS", "    CHECK_SERVICE service_id= " + mServiceId + " status " + mStatusNew);

                            if (mStatusNew > mStatusOld) {
                                mySQLiteAdapter.updateStatusService(mServiceId, String.valueOf(mStatusNew));
                            }
                        }

                        Log.v("checkService", "servicio en progreso!");
                        if (status == 4) {
                            hand.sendEmptyMessage(3);
                        } else if (status == 5) {
                            // el servicio esta finalizado - activity_calificar

                            mTotUnits = responsejson.getString("units");
                            mTotCharge1 = responsejson.getString("charge1");
                            mTotCharge2 = responsejson.getString("charge2");
                            mTotCharge3 = responsejson.getString("charge3");
                            mTotCharge4 = responsejson.getString("charge4");
                            mTotValue = responsejson.getString("value");

                            hand.sendEmptyMessage(4);
                        }
                    }
                    //}

                } catch (Exception e) {
                    Log.v("checkService", "CONFIRMATION Problema json" + e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Log.v("checkService", "CONFIRMTION onFailure");

            }

            @Override
            public void onFinish() {

                Log.v("checkService", "CONFIRMATION onFinish");
                Log.e("SERVICE_CMS", "    CHECK_SERVICE finish service_id= " + mServiceId + " status " + String.valueOf(status));

                if (status >= 6) {
//                    myTimer.cancel();
                    Log.v("checkService", "Servicio cancelado por conductor");
                    if (status == 8) {
                        Log.v("checkService", "Servicio cancelado por conductor 2");
                        hand.sendEmptyMessage(1);
                    } else {
                        hand.sendEmptyMessage(2);
                    }
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!moveTaskToBack(true)) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_HOME);
                this.startActivity(i);

            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void call(String number) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + number));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e(TAG, "Error al llamar:", activityException);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler hand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Log.v("CANCEL_SERVICE","handler 0");
                    mostrarAviso(getString(R.string.aviso_cancelacion_servicio_por_conductor), true);
                    Toast.makeText(getApplicationContext(), getString(R.string.aviso_cancelacion_servicio_por_conductor), Toast.LENGTH_SHORT).show();
                    break;

                case 2:
                    Log.v("CANCEL_SERVICE","handler 2");
                    Toast.makeText(getApplicationContext(), getString(R.string.servicio_cancelado), Toast.LENGTH_SHORT).show();
                    //finish();
                    callMap();
                    break;

                case 3:
                    messageTextView.setText(R.string.driver_arrived_message);
                    btnCancelar.setVisibility(View.GONE);
                    BT_Interrupt.setVisibility(View.VISIBLE);
                    break;

                case 4:
                    btnCalificar.setVisibility(View.VISIBLE);
                    btnCancelar.setVisibility(View.GONE);
                    BT_Interrupt.setVisibility(View.GONE);

                    // set test
                    messageTextView.setText(R.string.agradecimiento_uso_taxisya);

                    showResumeService();
                    break;

                default:
                    break;
            }

        }
    };

    public void showResumeService() {
        // TODO: si es tipo credito muestra el dialogo
        Log.v("SHOW_RESUME","service ini");

        Log.v("SHOW_RESUME","units " + mTotUnits);
        Log.v("SHOW_RESUME","c1 " + mTotCharge1);
        Log.v("SHOW_RESUME","c2 " + mTotCharge2);
        Log.v("SHOW_RESUME","c3 " + mTotCharge3);
        Log.v("SHOW_RESUME","c4 " + mTotCharge4);
        Log.v("SHOW_RESUME","val " + mTotValue);



        TextView totUnits = (TextView)findViewById(R.id.totUnits);
        TextView totCharge = (TextView)findViewById(R.id.totCharges);
        TextView totValue = (TextView)findViewById(R.id.totValue);

        if (!mTotUnits.equals("0")) {

            totUnits.setVisibility(View.VISIBLE);
            totCharge.setVisibility(View.VISIBLE);
            totValue.setVisibility(View.VISIBLE);

            totUnits.setText("Total unidades: " + mTotUnits);

            totCharge.setText("Total recargos: " +
                    (Integer.valueOf(mTotCharge1 ) +
                            (Integer.valueOf(mTotCharge2)) +
                            (Integer.valueOf(mTotCharge3)) +
                            (Integer.valueOf(mTotCharge4))
                    ));

            totValue.setText("Total: COP " + mTotValue);
        }
        Log.v("SHOW_RESUME","service fin");

    }

    private void displayConnectivityPanel(boolean display) {

        mNoConnectivityPanel.setVisibility(display ? View.VISIBLE : View.GONE);
        if(display)
            mConnectivityLoaderImage.startAnimation(AnimationUtils.loadAnimation(this,R.anim.connection_loader));

    }

    @Override
    public void onNetworkConnectivityChange(boolean connected) {
        displayConnectivityPanel(!connected);
    }


    @Override
    public void onConnectivityQualityChecked(boolean Optimal) {
//        displayConnectivityPanel(!Optimal);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
        marker = map.addMarker(options);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mTaxi, 17.0f));
    }

    public void dibujarTaxi(final double latitud, final double longitud) {

        Handler mainHandler = new Handler(ConfirmacionActivity.this.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                markerPoints = new ArrayList<LatLng>();
                LatLng taxi = new LatLng(latitud, longitud);
                marker.setPosition(taxi);
                map.moveCamera(CameraUpdateFactory.newLatLng(taxi));
                Log.i("==POSICIÓN TAXI==",latitud + "," + longitud);

            }
        };
        mainHandler.post(myRunnable);



    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
