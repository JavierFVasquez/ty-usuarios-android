package com.imaginamos.taxisya.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.imaginamos.usuariofinal.taxisya.Model.DirectionsResponse;
import com.imaginamos.usuariofinal.taxisya.Model.FeesResponse;
import com.imaginamos.usuariofinal.taxisya.Model.InterruptResponse;
import com.imaginamos.usuariofinal.taxisya.Model.PlacesResponse;
import com.imaginamos.usuariofinal.taxisya.Model.RequestServiceAddressResponse;
import com.imaginamos.usuariofinal.taxisya.Model.ResultsItem;
import com.imaginamos.usuariofinal.taxisya.Model.ReverseGeocodingResponse;
import com.imaginamos.usuariofinal.taxisya.activities.AgendarActivity;
import com.imaginamos.usuariofinal.taxisya.activities.ConfirmacionActivity;
import com.imaginamos.usuariofinal.taxisya.activities.HistorialActivity;
import com.imaginamos.usuariofinal.taxisya.activities.HomeActivity;
import com.imaginamos.usuariofinal.taxisya.activities.ListaAdapter;
import com.imaginamos.usuariofinal.taxisya.activities.MyAddressesActivity;
import com.imaginamos.usuariofinal.taxisya.activities.NotificationActivity;
import com.imaginamos.usuariofinal.taxisya.activities.PaymentsActivity;
import com.imaginamos.usuariofinal.taxisya.activities.PerfilActivity;
import com.imaginamos.usuariofinal.taxisya.activities.RegistroActivity;
import com.imaginamos.usuariofinal.taxisya.adapter.AutocompleteAdapter;
import com.imaginamos.usuariofinal.taxisya.adapter.BDAdapter;
import com.imaginamos.usuariofinal.taxisya.comm.Connect;
import com.imaginamos.usuariofinal.taxisya.comm.NetworkChangeReceiver;
import com.imaginamos.usuariofinal.taxisya.comm.Preferencias;
import com.imaginamos.usuariofinal.taxisya.dialogs.Dialogos;
import com.imaginamos.usuariofinal.taxisya.io.ApiService;
import com.imaginamos.usuariofinal.taxisya.models.CancelServiceResponse;
import com.imaginamos.usuariofinal.taxisya.utils.Actions;
import com.imaginamos.usuariofinal.taxisya.models.Conf;
import com.imaginamos.usuariofinal.taxisya.comm.Connectivity;
import com.imaginamos.usuariofinal.taxisya.comm.MiddleConnect;
import com.imaginamos.usuariofinal.taxisya.comm.NewAddressActivity;
import com.imaginamos.usuariofinal.taxisya.models.Target;
import com.imaginamos.usuariofinal.taxisya.models.Error;
import com.imaginamos.usuariofinal.taxisya.utils.Utils;
import com.imaginamos.usuariofinal.taxisya.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yinglan.keyboard.HideUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;
import static com.imaginamos.usuariofinal.taxisya.R.id.ticketValue;

public class MapaActivitys extends FragmentActivity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener, LocationListener, NetworkChangeReceiver.NetworkReceiverListener, Connectivity.ConnectivityQualityCheckListener, OnMapReadyCallback, PlaceSelectionListener {

    public static final int NOTIFICATION_ID = 1;
    private Animation traslation;
    private boolean isAddressFocus = false;
    private Fragment mFragment;
    private double latitud = 4.598889;
    private double longitud = -74.080833;
    private LinearLayout mHandleLayout, mNewAddress, mLayoutMap, type_content, pay_content;
    private Bitmap myBitmap;
    private ImageView imageMapView, imageMapMarker;
    private boolean activeRequest = false;
    private boolean isNewAddress = false;
    private boolean isRequestService = false;
    private int count = 10;
    private int reintent = 3;
    private RelativeLayout contendorWell;
    private GoogleMap map;
    private ImageView bg_shadow, imageHeader;
    private EditText direccion_uno, mAddress;
    private Button btnAddAddress, btn_menu, btnSolicitar, bt_direcc, btnCancelar, btnAsk;
    private TextView textTotal, textSearch, mTextYaFaltaPoco, kind_text;
    private RelativeLayout mNoConnectivityPanel;
    private ImageView mConnectivityLoaderImage;
    private Connectivity mConnectivityChecker = new Connectivity(this);
    private boolean mFromScheduling = false;

    private Spinner sp;
    ArrayAdapter<?> myAdapter;
    private String indice, comp1, comp2, no, obs = "";
    private String id_user, uuid, service_id;
    private Timer myTimer = new Timer();
    private BroadcastReceiver mBroadCastReceiver;
    private int reintento = 0;
    private AlertDialog alert;
    private String url_cancel_current;
    private boolean isError = false;
    private String barrio = " no especifica ";
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    private static final int MILLISECONDS_PER_SECOND = 1000;
    private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
    private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
    private static final int NORMAL_INTERVAL_IN_SECONDS = 30;
    private static final long NORMAL_INTERVAL = MILLISECONDS_PER_SECOND * NORMAL_INTERVAL_IN_SECONDS;
    private int indice_posicion = 2;
    private SupportMapFragment fm;
    private ProgressDialog pd;
    private ProgressDialog pDialog;
    private Conf conf;
    private Timer timer;
    private GetPosition gp;
    private BroadcastReceiver mReceiver;
    private NetworkChangeReceiver mNetworkMonitor;
    private CircleImageView imageMap;
    private ProgressBar circleProgressBar;
    private boolean isReceiverRegistered = false;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 1000; //10000; // 10 sec
    private static int FATEST_INTERVAL = 1000; // 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters
    private boolean firstTime = true;

    private String mServiceId;
    private int mStatusOld;
    private int mStatusNew;

    private BDAdapter mySQLiteAdapter;
    private Cursor mCursor;
    private Preferencias mPref;
    private ImageButton hashback, sedan, suv, check_sedan, check_hashback, check_suv;
    // 1= efectivo, 2=tc,3=vale
    private int mPayType = 1;
    private String mCardReference;
    private LatLng destination;
    private String destination_address = "";

    private RadioButton mPayType1, mPayType2, mPayType3;
    private EditText mTicket;
    private boolean isTicketValid = false;
    public String rTicket;
    public String commitUser = "";
    public String autocomplete_fragment = "";
    public String place_details = "";

    private TextView mPlaceDetailsText;
    private TextView mPlaceAttribution;

    private TextView TV_Distance;
    private TextView TV_Estimated_Time;
    private TextView TV_Estimated_Price;

    private AutoCompleteTextView direccion_dos;
    private String destination_name;
    private String travel_distance;
    private String travel_estimated_time;
    private String pay_type = "Efectivo";
    private List<ResultsItem> places_result;
    private TextWatcher tw;
    private String dir_by_result = "";
    private String total_con_recargos = "";
    private double to_lat;
    private double to_lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFromScheduling = getIntent().getStringExtra("FromActivity") != null && getIntent().getStringExtra("FromActivity").equals("Schedule");
        overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);
        setContentView(R.layout.activity_pedir);

        Toast.makeText(MapaActivitys.this, "Completa la dirección continuar", Toast.LENGTH_SHORT).show();

        final ListView menuLateral = (ListView) findViewById(R.id.menuLateral);
        final ListaAdapter adapter;
        String[] titulos = {"", "Perfil", "Agendamientos", "Reclamos"};
        int[] imagenes = {
                R.drawable.navbar_logo,
                R.drawable.perfil_over,
                R.drawable.agendar_over,
                R.drawable.reclamos_over
        };

        adapter = new ListaAdapter(this, titulos, imagenes);
        menuLateral.setAdapter(adapter);
        menuLateral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 1) {
                    if (conf.getLogin()) {
                        mPref.setRootActivity("MapaActivitys");
                        Intent in = new Intent(MapaActivitys.this, PerfilActivity.class);
                        startActivity(in);
                    } else {
                        mPref.setRootActivity("MapaActivitys");
                        Intent in = new Intent(MapaActivitys.this, RegistroActivity.class);
                        in.putExtra("target", Target.HISTORY_TARGET);
                        startActivity(in);
                    }
                } else if (i == 2) {
                    Toast.makeText(MapaActivitys.this, "Agendamiento", Toast.LENGTH_SHORT).show();
                    Intent intmenu = new Intent(MapaActivitys.this, AgendarActivity.class);
                    startActivity(intmenu);

                } else if (i == 3) {
                    Toast.makeText(MapaActivitys.this, "Reclamos", Toast.LENGTH_SHORT).show();
                    Intent intmenu = new Intent(MapaActivitys.this, HistorialActivity.class);
                    startActivity(intmenu);
                }

            }
        });

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        autocompleteFragment.setOnPlaceSelectedListener(this);

        TV_Distance = (TextView) findViewById(R.id.TV_Distance);
        TV_Estimated_Time = (TextView) findViewById(R.id.TV_Estimated_Time);
        TV_Estimated_Price = (TextView) findViewById(R.id.TV_Estimated_Price);

        mPlaceDetailsText = (TextView) findViewById(R.id.place_details);
        mPlaceAttribution = (TextView) findViewById(R.id.place_attribution);

        conf = new Conf(this);
        mHandleLayout = (LinearLayout) findViewById(R.id.handle);
        mNewAddress = (LinearLayout) findViewById(R.id.add_new);
        mLayoutMap = (LinearLayout) findViewById(R.id.layout_map);
        type_content = (LinearLayout) findViewById(R.id.type_content);
        pay_content = (LinearLayout) findViewById(R.id.pay_content);
        imageMapView = (ImageView) findViewById(R.id.image_map);
        imageMapMarker = (ImageView) findViewById(R.id.ivMarker);
        //kind_text = (TextView) findViewById(R.id.kind_text);

        hashback = (ImageButton) findViewById(R.id.hashback);
        sedan = (ImageButton) findViewById(R.id.sedan);
        suv = (ImageButton) findViewById(R.id.suv);
        check_hashback = (ImageButton) findViewById(R.id.check_hashback);
        check_sedan = (ImageButton) findViewById(R.id.check_sedan);
        check_suv = (ImageButton) findViewById(R.id.check_suv);

        mPayType1 = (RadioButton) findViewById(R.id.payType1);
        mPayType2 = (RadioButton) findViewById(R.id.payType2);
        mPayType3 = (RadioButton) findViewById(R.id.payType3);
        mTicket = (EditText) findViewById(ticketValue);


        mTicket.addTextChangedListener(new TextWatcher() {

            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!charSequence.toString().equals(current)) {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                final String stk = mTicket.getText().toString();
                rTicket = new String(stk);
                if ((stk != null) && stk.length() >= 7) {
                    Log.v("LOG1", "mTicket afterTextChanged " + stk);
                    isTicketValid = false;

                    MiddleConnect.confirmTicker(getApplicationContext(), stk, new AsyncHttpResponseHandler() {

                        @Override
                        public void onStart() {
                            Log.v("LOG1", "confirmTicket onStart");
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String response = new String(responseBody);
                            isTicketValid = false;

                            Log.v("LOG1", "onSuccess response = " + response);
                            try {
                                Log.v("LOG1", "SUCCES: " + response);
                                JSONObject responsejson = new JSONObject(response);
                                Log.v("LOG1", "SUCCES json: " + responsejson.toString());

                                if (responsejson.getString("error").equals("0")) {
                                    Log.v("LOG1", "SUCCES vale correcto: ");
                                    btnSolicitar.setEnabled(true);
                                    btnSolicitar.setBackgroundResource(R.drawable.btn_request);
                                    isTicketValid = true;
                                }
                                if (responsejson.getString("error").equals("1")) {
                                    btnSolicitar.setEnabled(false);
                                    btnSolicitar.setBackgroundResource(R.drawable.btn_gray);
                                    Toast.makeText(MapaActivitys.this, "ticket no valido", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Log.v("checkService", "Problema json" + e.toString());
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                            Log.v("LOG1", "confirmTicket onFailure");
                            String response = new String(responseBody);
                            Log.v("LOG1", "failure response = " + response);
                        }

                        @Override
                        public void onFinish() {
                            Log.v("LOG1", "confirmTicket onFinish =  ");
                        }

                    });

                } else {
                    btnSolicitar.setEnabled(false);
                    btnSolicitar.setBackgroundResource(R.drawable.btn_gray);
                    isTicketValid = false;
                    //Toast.makeText(MapaActivitys.this, "ticket no valido", Toast.LENGTH_SHORT).show();
                }
            }

        });

        mPayType1.setChecked(true);

        imageMap = (CircleImageView) findViewById(R.id.imageMap);
        circleProgressBar = (ProgressBar) findViewById(R.id.circleProgressBar);

        isNewAddress = false;
        btnCancelar = (Button) findViewById(R.id.btn_cancelar);
        textTotal = (TextView) findViewById(R.id.text_total);
        imageHeader = (ImageView) findViewById(R.id.banner_arriba);
        textSearch = (TextView) findViewById(R.id.text_search);
        contendorWell = (RelativeLayout) findViewById(R.id.contenedor_rueda);

        mTextYaFaltaPoco = (TextView) findViewById(R.id.text_falta);
        String fontPath = "fonts/WCManoNegraBoldBta.otf";
        Typeface tf = Typeface.createFromAsset(MapaActivitys.this.getResources().getAssets(), fontPath);
        mTextYaFaltaPoco.setTypeface(tf);
        mySQLiteAdapter = new BDAdapter(this);
        mySQLiteAdapter.openToWrite();
        mConnectivityChecker.setConnectivityCheckInterval(5000);
        mConnectivityChecker.setConnectivityIndicator(1500);

        buildView();
        btnSolicitar.setBackgroundResource(R.drawable.btn_request);

        mPayType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPayType = 1;
                btnSolicitar.setEnabled(true);
                btnSolicitar.setBackgroundResource(R.drawable.btn_request);
                mTicket.setVisibility(GONE);
                type_content.setVisibility(GONE);
                mTicket.getText().toString();
                pay_type = "Efectivo";
            }
        });

        mPayType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPayType = 2;
                mTicket.setVisibility(GONE);
                type_content.setVisibility(GONE);
                btnSolicitar.setBackgroundResource(R.drawable.btn_request);
                btnSolicitar.setEnabled(true);
                // TODO: check tipo servicio
                checkCreditCards();
                pay_type = "Tarjeta";
            }
        });

        mPayType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPayType = 3;
                mTicket.setVisibility(View.VISIBLE);
                type_content.setVisibility(GONE);
                btnSolicitar.setEnabled(false);
                btnSolicitar.setBackgroundResource(R.drawable.btn_gray);
                pay_type = "Vale";
            }
        });

        hashback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPayType = 3;
                check_hashback.setVisibility(View.VISIBLE);
                check_suv.setVisibility(GONE);
                check_sedan.setVisibility(GONE);

                hashback.setVisibility(GONE);
                sedan.setVisibility(View.VISIBLE);
                suv.setVisibility(View.VISIBLE);

                btnSolicitar.setEnabled(true);
                btnSolicitar.setBackgroundResource(R.drawable.btn_request);
            }
        });

        sedan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPayType = 3;
                check_hashback.setVisibility(GONE);
                check_suv.setVisibility(GONE);
                check_sedan.setVisibility(View.VISIBLE);

                hashback.setVisibility(View.VISIBLE);
                sedan.setVisibility(GONE);
                suv.setVisibility(View.VISIBLE);

                btnSolicitar.setEnabled(true);
                btnSolicitar.setBackgroundResource(R.drawable.btn_request);
            }
        });

        suv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPayType = 3;
                check_hashback.setVisibility(GONE);
                check_suv.setVisibility(View.VISIBLE);
                check_sedan.setVisibility(GONE);

                hashback.setVisibility(View.VISIBLE);
                sedan.setVisibility(View.VISIBLE);
                suv.setVisibility(GONE);

                btnSolicitar.setEnabled(true);
                btnSolicitar.setBackgroundResource(R.drawable.btn_request);
            }
        });

        setListeners();
        hideControls();

        Log.v("SEGUIMIENTO", "onCreate - 1 - MainActivity before checkPlayServices");
        if (checkPlayServices()) {
            Log.e("checkPlayServices", "checkPlayServices");
            Log.v("SEGUIMIENTO", "onCreate - 1 - MainActivity after checkPlayServices ok ");

            // Building the GoogleApi client
            buildGoogleApiClient();

            fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            fm.getMapAsync(this);
            if (!Connectivity.isConnectedFast(this)) {
                Toast.makeText(this, getResources().getString(R.string.error_speed), Toast.LENGTH_LONG).show();
            }
        }

        Log.e("checkPlayServices", "antes de register intentFilter");
        Log.v("SEGUIMIENTO", "onCreate - 1 - MainActivity after checkPlayServices 2");

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Actions.ACTION_USER_CLOSE_SESSION);
        intentFilter.addAction(Actions.ACTION_MESSAGE_MASSIVE);
        intentFilter.addAction(Actions.CONFIRM_NEW_SERVICES);

        Log.v("MAP1", "camera1");

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null) {
            latitud = location.getLatitude();
            longitud = location.getLongitude();
        }

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Actions.ACTION_USER_CLOSE_SESSION)) {
                    Log.v("USER_CLOSE_SESSION", "Sesión cerrada - confirmación");
                    Conf conf = new Conf(getApplicationContext());
                    conf.setLogin(false);

                    //Intent in3 = new Intent(MapaActivitys.this,LoginActivity.class);
                    //in3.putExtra("target", 1);
                    //startActivity(in3);
                    finish();
                } else if (intent.getAction().equals(Actions.ACTION_MESSAGE_MASSIVE)) {

                    Log.v("MESSAGE_MASSIVE", "mensaje global recibido");
                    String message = intent.getExtras().getString("message");
                    mostrarMensaje(message);

                }
                // status services
                // validate +
                else if (intent.getAction().equals(Actions.CONFIRM_NEW_SERVICES)) {
                    Log.v("SOLICITANDO_ACTION", "CONFIRM_NEW_SERVICES");
                    Log.e("SERVICE_CMS", "GCM MAP CONFIRM_SERVICE service_id= " + intent.getExtras().getString("service_id"));

                    try {
                        if (myTimer != null) {
                            myTimer.cancel();
                            myTimer.purge();
                            myTimer = null;
                        }
                        mPref.setRootActivity("MapaActivitys");
                        mServiceId = intent.getExtras().getString("service_id");
                        conf.setServiceId(mServiceId);

                        Intent mIntent = new Intent(getApplicationContext(), ConfirmacionActivity.class);
                        mIntent.putExtra("driver", intent.getExtras().getString("driver"));
                        mIntent.putExtra("pay_type", mPayType);
                        startActivity(mIntent);
                        finish();

                    } catch (Exception e) {
                        Log.v("CNF_SRV", "err_request 2 " + e.toString());
                        err_request();
                        finish();
                    }
                } else if (intent.getAction().equals(Actions.ACTION_USER_CLOSE_SESSION)) {

                    Log.v("SOLICITANDO_ACTION", "ACTION_CANCEL_DRIVER_SERVICE");
                    Log.v("CNF_SRV", "requestServiceAddress ACTION_CANCEL_DRIVER_SERVICE");
                    Conf conf = new Conf(getApplicationContext());
                    conf.setLogin(false);
                    mPref.setRootActivity("MapaActivitys");
                    Intent in3 = new Intent(MapaActivitys.this, HomeActivity.class);
                    startActivity(in3);
                    finish();

                }
                // validate -

            }
        };

        try {
            registerReceiver(mReceiver, intentFilter);
        } catch (Exception e) {

        }

        Log.e("sendEmptyMessage", "sendEmptyMessage");
        Log.v("SEGUIMIENTO", "onCreate - 1 - MainActivity after sendEmptyMessage ");
        Log.v("CNF_SRV", "onCreate");
        hand.sendEmptyMessage(2);


        mPref = new Preferencias(this);
        //mPref.setRootActivity("HomeActivity");
        if (!isLocationEnabled(this)) {
            Log.v("TEST_GPS", "disabled");
            hand.sendEmptyMessage(1);
        } else {
            Log.v("TEST_GPS", "enabled");
        }

        btnSolicitar.setEnabled(false);
        btnSolicitar.setBackgroundResource(R.drawable.btn_gray);

        direccion_uno.setFocusable(true);
        direccion_uno.setFocusableInTouchMode(true);
        Log.e("NO SE", "QUE ESTA PASANDO");
        if (direccion_uno.requestFocus()) {
            Log.e("NO SE NADA", "QUE ESTA PASANDO");
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }

        direccion_dos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    imageMapMarker.setVisibility(View.GONE);

                    Log.e("LLENO", "EL CAMPO DE DIRECCION DOS ESTA LLENO");

                    direccion_dos.addTextChangedListener(tw);
                    direccion_dos.setFocusableInTouchMode(true);
                    direccion_dos.setFocusable(true);
                    btnAsk.setVisibility(GONE);
                    btnSolicitar.setVisibility(View.VISIBLE);
                    pay_content.setVisibility(View.VISIBLE);
                    btnSolicitar.setEnabled(true);
                    btnSolicitar.setBackgroundResource(R.drawable.btn_request);

                }
            }
        });


    }

    public void onPlaceSelected(Place place) {

        mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getLatLng()));

        CharSequence attributions = place.getAttributions();
        if (!TextUtils.isEmpty(attributions)) {
            mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
        } else {
            mPlaceAttribution.setText("");
        }

        pay_content.setVisibility(View.VISIBLE);
        btnSolicitar.setEnabled(true);
        btnSolicitar.setBackgroundResource(R.drawable.btn_request);
        kind_text.setVisibility(View.VISIBLE);
    }

    private boolean isKeyboardShown(View rootView) {
    /* 128dp = 32dp * 4, minimum button height 32dp and generic 4 rows soft keyboard */
        final int SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128;

        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
    /* heightDiff = rootView height - status bar height (r.top) - visible frame height (r.bottom - r.top) */
        int heightDiff = rootView.getBottom() - r.bottom;
    /* Threshold size: dp to pixels, multiply with display density */
        boolean isKeyboardShown = heightDiff > SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD * dm.density;

        Log.d("TECLADO", "isKeyboardShown ? " + isKeyboardShown + ", heightDiff:" + heightDiff + ", density:" + dm.density + "root view height:" + rootView.getHeight() + ", rect:" + r);

        if (!isKeyboardShown) {
            if (isAddressFocus) {
                map.getUiSettings().setScrollGesturesEnabled(true);
                imageMapView.setVisibility(GONE);
                imageMapMarker.setVisibility(GONE);

                if (((direccion_uno.hasFocus()) || (direccion_dos.hasFocus())) && (!direccion_uno.getText().toString().isEmpty()) && (!direccion_dos.getText().toString().isEmpty()) && (!destination_address.isEmpty())) {

                    Snackbar.make(getCurrentFocus(), "Calculando ruta", Snackbar.LENGTH_LONG).show();


                    final String dir1 = direccion_uno.getText().toString() + "Bogota Colombia";
                    String dir2 = direccion_dos.getText().toString() + "Bogota Colombia";


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
                    Call<DirectionsResponse> call_profile = service.directions(dir1, destination_address);
                    call_profile.enqueue(new Callback<DirectionsResponse>() {
                        @Override
                        public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {

                            String poly_encode = response.body().getRoutes().get(0).getOverview_polyline().getPoints();
                            List<LatLng> points = PolyUtil.decode(poly_encode);

                            PolylineOptions rectOptions = new PolylineOptions().width(10).color(getResources().getColor(R.color.text_orange));

                            for (LatLng l : points) {
                                rectOptions.add(l);
                            }

                            int height = convertSpToPixels(40, MapaActivitys.this);
                            int width = convertSpToPixels(90, MapaActivitys.this);
                            ;
                            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.pointer_1);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker_1 = Bitmap.createScaledBitmap(b, width, height, false);
                            BitmapDrawable bitmapdraw_2 = (BitmapDrawable) getResources().getDrawable(R.drawable.pointer_2);
                            b = bitmapdraw_2.getBitmap();
                            Bitmap smallMarker_2 = Bitmap.createScaledBitmap(b, width, height, false);

                            map.clear();
                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(response.body().getRoutes().get(0).getLegs().get(0).getStart_location().getLat(), response.body().getRoutes().get(0).getLegs().get(0).getStart_location().getLng()))
                                    .title(direccion_uno.getText().toString()))
                                    .setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker_1));
                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(response.body().getRoutes().get(0).getLegs().get(0).getEnd_location().getLat(), response.body().getRoutes().get(0).getLegs().get(0).getEnd_location().getLng()))
                                    .title(direccion_dos.getText().toString()))
                                    .setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker_2));

                            Polyline polyline = map.addPolyline(rectOptions);
                            LinearLayout LL_Estimated = (LinearLayout) findViewById(R.id.LL_Estimated);
                            LL_Estimated.setVisibility(View.VISIBLE);
                            travel_distance = response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText();
                            travel_estimated_time = response.body().getRoutes().get(0).getLegs().get(0).getDuration().getText();
                            TV_Distance.setText(response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText());
                            TV_Estimated_Time.setText(response.body().getRoutes().get(0).getLegs().get(0).getDuration().getText());

                            getEstimatedPriceWithFees(String.valueOf(response.body().getRoutes().get(0).getLegs().get(0).getEnd_location().getLat()), String.valueOf(response.body().getRoutes().get(0).getLegs().get(0).getEnd_location().getLng()), response.body().getRoutes().get(0).getLegs().get(0).getDistance().getValue(), response.body().getRoutes().get(0).getLegs().get(0).getDuration().getValue());

                            map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                                @Override
                                public void onCameraChange(CameraPosition cameraPosition) {

                                }
                            });
                            imageMapMarker.setVisibility(GONE);

                            LatLngBounds.Builder builder = new LatLngBounds.Builder();


                            builder.include(new LatLng(response.body().getRoutes().get(0).getBounds().getNortheast().getLat(), response.body().getRoutes().get(0).getBounds().getNortheast().getLng()));
                            builder.include(new LatLng(response.body().getRoutes().get(0).getBounds().getSouthwest().getLat(), response.body().getRoutes().get(0).getBounds().getSouthwest().getLng()));

                            LatLngBounds bounds = builder.build();

                            int padding = convertSpToPixels(40, MapaActivitys.this); // offset from edges of the map in pixels
                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                            map.setPadding(0, 0, 0, convertSpToPixels(150, MapaActivitys.this));
                            map.animateCamera(cu);
                        }

                        @Override
                        public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                            Log.w("-----Error-----", t.toString());
                        }
                    });

                }
            }
            isAddressFocus = false;
        } else {
            isAddressFocus = true;
            Log.v("TECLADO", "i");
//			if (isAddressFocus) {
//				Log.v("TECLADO","i");
//				imageMapView.setVisibility(View.GONE);
//			}
        }

        return isKeyboardShown;
    }


    public static final int cost_by_kilometer_sfc = 881;
    public static final int cost_by_kilometer_cfc = 998;
    public static final int banderazo_sfc = 2500;
    public static final int banderazo_cfc = 2800;
    public static int recargo_nocturno = 0;
    public static int recargo_puerta_a_puerta = 0;
    public static int recargo_aeropuerto = 0;
    public static final int carrera_minima_sfc = 4400;
    public static final int carrera_minima_cfc = 5000;

    public static final double vfl = 36;
    public static final double vc = 18;
    public static final double b1 = 0.83;
    public static final double b2 = 2.48;
    public static final double c = -0.825;

    public static final double factorDeCalidad = 0;


    public static int getEstimatedPrice(int distance, int time, int recargos) {

        double distanceInKm = distance / 1000;
        double timeInHours = (time / 60) / 60;

        double costoServicio = 0;

        double fc = factorDeCongestion(distanceInKm / timeInHours);


        if (factorDeCalidad == 0) {
            costoServicio = banderazo_sfc + ((cost_by_kilometer_sfc * (1 + factorDeCalidad)) * (1 + fc) * distanceInKm) + recargos;
            costoServicio = (costoServicio < carrera_minima_sfc) ? carrera_minima_sfc : costoServicio;
        } else {
            costoServicio = banderazo_cfc + ((cost_by_kilometer_cfc * (1 + factorDeCalidad)) * (1 + fc) * distanceInKm) + recargos;
            costoServicio = (costoServicio < carrera_minima_cfc) ? carrera_minima_cfc : costoServicio;

        }

        return (int) costoServicio;
    }

    public static double factorDeCongestion(double velocidadPromedio) {
        if (velocidadPromedio >= vfl) {
            return 0;
        } else if ((vc <= velocidadPromedio) && (velocidadPromedio < vfl)) {
            return ((vfl - velocidadPromedio) / vfl) * b1;
        } else if (velocidadPromedio < vc) {
            return (((vfl - velocidadPromedio) / vfl) * b2) + c;
        } else {
            return 0;
        }
    }

    public void getEstimatedPriceWithFees(String lat, String lng, final int distance, final int time) {

        int inhours = (int) ((time / 60) / 60);
        int minutes = ((time / 60) % 60);
        String time_formated = inhours + ":" + minutes;

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
        Call<FeesResponse> call_profile = service.requestFees(lat, lng, time_formated);
        call_profile.enqueue(new Callback<FeesResponse>() {
            @Override
            public void onResponse(Call<FeesResponse> call, Response<FeesResponse> response) {

                total_con_recargos = "$ " + String.format("%,d", getEstimatedPrice(distance, time, response.body().getTotal_recargo()));
                TV_Estimated_Price.setText(total_con_recargos);
                recargo_nocturno = Integer.parseInt(response.body().getNocturno().trim());
                recargo_aeropuerto = Integer.parseInt(response.body().getKm().trim());
                recargo_puerta_a_puerta = Integer.parseInt(response.body().getPuerta_a_puerta().trim());

            }

            @Override
            public void onFailure(Call<FeesResponse> call, Throwable t) {
                Log.w("-----Error-----", t.toString());
            }
        });

    }

    @Override
    protected void onStop() {
        Log.v("onStop", "MapActivity");
        super.onStop();
///		mLocationClient.disconnect();
    }

    public static int convertSpToPixels(float sp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
        return px;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.v("SEGUIMIENTO", "onStart - MainActivity");
///		mLocationClient.connect();
///        if (mGoogleApiClient != null) {
///            mGoogleApiClient.connect();
///        }
        if (mLocationRequest == null) {
            mLocationRequest = createLocationRequest();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("SEGUIMIENTO", "onResume - MainActivity");
        checkPlayServices();
        displayConnectivityPanel(!Connectivity.isConnected(this) && !mConnectivityChecker.getConnectivityCheckResult());
        mConnectivityChecker.startConnectivityMonitor();
        mNetworkMonitor = new NetworkChangeReceiver(this);
        registerReceiver(mNetworkMonitor, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    protected void onPause() {
        super.onPause();
        mConnectivityChecker.stopConnectivityMonitor();
        //connectivityQualityMonitor.interrupt();
        unregisterReceiver(mNetworkMonitor);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.e("onRestart", "onRestart");
        overridePendingTransition(R.anim.hold, R.anim.pull_out_to_right);
        displayLocation();

        try {
            timer.purge();
            timer.cancel();
            count = 10;
            //reintent = 3;
        } catch (Exception e) {

        }
    }

    @Override
    protected void onDestroy() {
        Log.v("CNF_SRV1", "MainActivity onDestroy 1");
        Log.v("onDestroy", "MapActivity");
        Log.v("SEGUIMIENTO", "onDestroy - MainActivity");
        super.onDestroy();

        if (mySQLiteAdapter != null) {
            mySQLiteAdapter.close();
        }

        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }

        try {
            timer.purge();
            timer.cancel();
        } catch (Exception e) {
        }

        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        //map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), map.getCameraPosition().zoom), 15, null);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if(getIntent().getExtras() != null && getIntent().getExtras().getBoolean("new_destination",false)){
            latitud = getIntent().getExtras().getDouble("from_lat",0);
            longitud = getIntent().getExtras().getDouble("from_lng",0);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 17));
            direccion_uno.setEnabled(false);
            direccion_uno.clearFocus();
            direccion_dos.requestFocus();
        }else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                latitud = mLastLocation.getLatitude();
                longitud = mLastLocation.getLongitude();
                Log.v("MAP1", "se obtuvo mLastLocation");
            }
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 17));
        }
    }

    void showControls() {

//        btnSolicitar.setVisibility(GONE);
//        pay_content.setVisibility(View.GONE);
        bt_direcc.setVisibility(GONE);
        imageHeader.setVisibility(GONE);
        textSearch.setVisibility(View.VISIBLE);
        contendorWell.setVisibility(View.VISIBLE);
        textTotal.setVisibility(View.VISIBLE);
        btnCancelar.setVisibility(View.VISIBLE);

        /*sedan.setVisibility(View.VISIBLE);
        check_sedan.setVisibility(View.VISIBLE);
        hashback.setVisibility(View.VISIBLE);
        check_hashback.setVisibility(View.VISIBLE);
        suv.setVisibility(View.VISIBLE);
        check_suv.setVisibility(View.VISIBLE);*/

    }

    void hideControls() {

        // solicitando
        textTotal.setVisibility(GONE);
        btnCancelar.setVisibility(GONE);
        contendorWell.setVisibility(GONE);
        textSearch.setVisibility(GONE);
        //kind_text.setVisibility(View.GONE);
        btnAsk.setEnabled(true);

        mTicket.setFocusableInTouchMode(true);
        mTicket.setFocusable(true);
        /*sedan.setVisibility(View.GONE);
        check_sedan.setVisibility(View.GONE);
        hashback.setVisibility(View.GONE);
        check_hashback.setVisibility(View.GONE);
        suv.setVisibility(View.GONE);
        check_suv.setVisibility(View.GONE);*/

//        btnSolicitar.setVisibility(GONE);
//        direccion_dos.setFocusableInTouchMode(true);
//        direccion_dos.setFocusable(false);
        bt_direcc.setVisibility(View.VISIBLE);
        imageHeader.setVisibility(View.VISIBLE);
        direccion_uno.setFocusableInTouchMode(true);
        direccion_uno.setFocusable(false);

    }

    void mostrarMensaje(final String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Información importante");
        builder.setMessage(message);
        builder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.e("PUSH", "mensaje: " + message);
            }
        });
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }

    private void setListeners() {
        btnSolicitar.setOnClickListener(this);
        btnAsk.setOnClickListener(this);
        bt_direcc.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        btnAddAddress.setOnClickListener(this);

    }

    private void buildView() {

        btnSolicitar = (Button) findViewById(R.id.btnSolicitar);
        btnAsk = (Button) findViewById(R.id.btnAsk);

        btnAddAddress = (Button) findViewById(R.id.btn_add);
        if(mFromScheduling && getIntent().getStringExtra("FromActivity").equals("Schedule")){
            btnSolicitar.setText(getIntent().getBooleanExtra("PickUp",true) ? getText(R.string.text_pedir_taxi) : getString(R.string.text_pedir_destino));
        }

        EditText SearchDestinyEditText = (EditText) findViewById(R.id.direccion_dos);
        EditText SearchEditText = (EditText) findViewById(R.id.direccion_uno);


        direccion_dos = (AutoCompleteTextView) findViewById(R.id.direccion_dos);
        direccion_uno = (EditText) findViewById(R.id.direccion_uno);


        bt_direcc = (Button) findViewById(R.id.btn_direcc);
        bg_shadow = (ImageView) findViewById(R.id.bg_shadows);

        mNoConnectivityPanel = (RelativeLayout) findViewById(R.id.layout_no_connectivity);
        mConnectivityLoaderImage = (ImageView) findViewById(R.id.loader_icon);

        myAdapter = ArrayAdapter.createFromResource(this, R.array.opciones, android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        SearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    Log.v("CUSTOM", "presionado");
                    // search pressed and perform your functionality.
                }
                return true;
            }
        });
        direccion_dos.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                isKeyboardShown(direccion_dos.getRootView());
            }
        });



        direccion_dos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                direccion_dos.setFocusableInTouchMode(true);
                direccion_dos.setFocusable(true);
                btnAsk.setVisibility(GONE);
                btnSolicitar.setVisibility(View.VISIBLE);
                pay_content.setVisibility(View.VISIBLE);
                btnSolicitar.setEnabled(true);
                btnSolicitar.setBackgroundResource(R.drawable.btn_request);




            }
        });

        tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null) {
                    timer.cancel();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                Log.i("======", "afterTextChanged");
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(!direccion_dos.getText().toString().trim().isEmpty()) {
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
                            Call<PlacesResponse> call_profile = service.places(direccion_dos.getText().toString().trim());
                            call_profile.enqueue(new Callback<PlacesResponse>() {
                                @Override
                                public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {

                                    if (response.body().getResults().size() > 0) {

                                        places_result = response.body().getResults();

                                        String[] results = new String[response.body().getResults().size()];

                                        for (int i = 0 ; i < response.body().getResults().size() ; i++){
                                            results[i] = response.body().getResults().get(i).getVicinity();
                                        }
                                        AutocompleteAdapter adapter = new AutocompleteAdapter(MapaActivitys.this,R.layout.autocomplete_item,response.body().getResults());

                                        direccion_dos.setThreshold(3);
                                        direccion_dos.setAdapter(adapter);
                                        if(direccion_dos.hasFocus()) {
                                            direccion_dos.showDropDown();
                                        }

                                    }

                                }

                                @Override
                                public void onFailure(Call<PlacesResponse> call, Throwable t) {
                                    Log.w("-----Error-----", t.toString());
                                }
                            });
                        }
                    }
                }, 600); // 600ms delay before the timer executes the „run“ method from TimerTask
            }
        };

        direccion_dos.addTextChangedListener(tw);


        direccion_dos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                direccion_dos.removeTextChangedListener(tw);


                double lat = places_result.get(i).getGeometry().getLocation().getLat();
                double lng = places_result.get(i).getGeometry().getLocation().getLng();
                destination = new LatLng(lat, lng);
                to_lat =  places_result.get(i).getGeometry().getLocation().getLat();
                to_lng =  places_result.get(i).getGeometry().getLocation().getLng();
                destination_address = places_result.get(i).getGeometry().getLocation().getLat() + "," +  places_result.get(i).getGeometry().getLocation().getLng();
                destination_name = places_result.get(i).getName();
                direccion_dos.setText(destination_name);
                direccion_dos.clearFocus();
                direccion_dos.dismissDropDown();


                InputMethodManager imm1 = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm1.hideSoftInputFromWindow(mTicket.getWindowToken(), 0);
                HideUtil.hideSoftKeyboard(MapaActivitys.this);


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
                if((!direccion_uno.getText().toString().isEmpty())&&(!direccion_dos.getText().toString().isEmpty())&&(!destination_address.isEmpty())){

                    Snackbar.make(getCurrentFocus(),"Calculando ruta...",Snackbar.LENGTH_LONG).show();


                    String dir1= direccion_uno.getText().toString()+" Bogota Colombia";

                    Call<DirectionsResponse> call_directions=service.directions(dir1,destination_address);
                    call_directions.enqueue(new Callback<DirectionsResponse>() {
                        @Override
                        public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                            String poly_encode = response.body().getRoutes().get(0).getOverview_polyline().getPoints();
                            List<LatLng> points = PolyUtil.decode(poly_encode);

                            PolylineOptions rectOptions = new PolylineOptions().width(10).color(getResources().getColor(R.color.text_orange));

                            for (LatLng l : points){
                                rectOptions.add(l);
                            }

                            int height = convertSpToPixels(40,MapaActivitys.this) ;
                            int width = convertSpToPixels(90,MapaActivitys.this) ;;
                            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.pointer_1);
                            Bitmap b=bitmapdraw.getBitmap();
                            Bitmap smallMarker_1 = Bitmap.createScaledBitmap(b, width, height, false);
                            BitmapDrawable bitmapdraw_2=(BitmapDrawable)getResources().getDrawable(R.drawable.pointer_2);
                            b=bitmapdraw_2.getBitmap();
                            Bitmap smallMarker_2 = Bitmap.createScaledBitmap(b, width, height, false);

                            map.clear();
                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(response.body().getRoutes().get(0).getLegs().get(0).getStart_location().getLat(), response.body().getRoutes().get(0).getLegs().get(0).getStart_location().getLng()))
                                    .title(direccion_uno.getText().toString()))
                                    .setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker_1));
                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(response.body().getRoutes().get(0).getLegs().get(0).getEnd_location().getLat(), response.body().getRoutes().get(0).getLegs().get(0).getEnd_location().getLng()))
                                    .title(direccion_dos.getText().toString()))
                                    .setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker_2));

                            Polyline polyline = map.addPolyline(rectOptions);
                            LinearLayout LL_Estimated = (LinearLayout) findViewById(R.id.LL_Estimated);
                            LL_Estimated.setVisibility(View.VISIBLE);
                            travel_distance = response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText();
                            travel_estimated_time = response.body().getRoutes().get(0).getLegs().get(0).getDuration().getText();
                            TV_Distance .setText(response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText());
                            TV_Estimated_Time.setText(response.body().getRoutes().get(0).getLegs().get(0).getDuration().getText());
                            getEstimatedPriceWithFees(String.valueOf(response.body().getRoutes().get(0).getLegs().get(0).getEnd_location().getLat()),String.valueOf(response.body().getRoutes().get(0).getLegs().get(0).getEnd_location().getLng()), response.body().getRoutes().get(0).getLegs().get(0).getDistance().getValue(),response.body().getRoutes().get(0).getLegs().get(0).getDuration().getValue());


                            map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                                @Override
                                public void onCameraChange(CameraPosition cameraPosition) {

                                }
                            });
                            imageMapMarker.setVisibility(GONE);

                            LatLngBounds.Builder builder = new LatLngBounds.Builder();


                            builder.include(new LatLng(response.body().getRoutes().get(0).getBounds().getNortheast().getLat(),response.body().getRoutes().get(0).getBounds().getNortheast().getLng()));
                            builder.include(new LatLng(response.body().getRoutes().get(0).getBounds().getSouthwest().getLat(),response.body().getRoutes().get(0).getBounds().getSouthwest().getLng()));

                            LatLngBounds bounds = builder.build();

                            int padding = convertSpToPixels(40,MapaActivitys.this); // offset from edges of the map in pixels
                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                            map.setPadding(0,0,0,convertSpToPixels(150,MapaActivitys.this));
                            map.animateCamera(cu);

                        }

                        @Override
                        public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                            Log.w("-----Error-----",t.toString());
                        }
                    });

                }
            }
        });

//        direccion_dos.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView arg0, int actionId, KeyEvent arg2) {
//                // hide the keyboard and search the web when the enter key
//                // button is pressed
//                if (actionId == EditorInfo.IME_ACTION_GO
//                        || actionId == EditorInfo.IME_ACTION_DONE
//                        || actionId == EditorInfo.IME_ACTION_NEXT
//                        || actionId == EditorInfo.IME_ACTION_SEND
//                        || actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    Log.v("SEGUIMIENTO", "oculta teclado");
//
//                    //map.getUiSettings().setScrollGesturesEnabled(true);
////                    imageMapView.setVisibility(View.GONE);
////                    imageMapMarker.setVisibility(View.VISIBLE);
//                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//                    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//                    httpClient.addInterceptor(logging);
//
//                    Retrofit retrofit = new Retrofit.Builder()
//                            .baseUrl(Connect.BASE_GOOGLE_URL)
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .client(httpClient.build())
//                            .build();
//
//                    ApiService service = retrofit.create(ApiService.class);
//                    if((!direccion_uno.getText().toString().isEmpty())&&(!direccion_dos.getText().toString().isEmpty())&&(!destination_address.isEmpty())){
//
//                        Snackbar.make(getCurrentFocus(),"Calculando ruta...",Snackbar.LENGTH_LONG).show();
//
//
//                        String dir1= direccion_uno.getText().toString()+" Bogota Colombia";
//                        String dir2= direccion_dos.getText().toString()+" Bogota Colombia";
//
//
//
//                        Call<DirectionsResponse> call_directions=service.directions(dir1,destination_address);
//                        call_directions.enqueue(new Callback<DirectionsResponse>() {
//                            @Override
//                            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
//
//                                direccion_dos.setText(destination_name);
//                                String poly_encode = response.body().getRoutes().get(0).getOverview_polyline().getPoints();
//                                List<LatLng> points = PolyUtil.decode(poly_encode);
//
//                                PolylineOptions rectOptions = new PolylineOptions().width(10).color(getResources().getColor(R.color.text_orange));
//
//                                for (LatLng l : points){
//                                    rectOptions.add(l);
//                                }
//
//
//                                Polyline polyline = map.addPolyline(rectOptions);
//                                LinearLayout LL_Estimated = (LinearLayout) findViewById(R.id.LL_Estimated);
//                                LL_Estimated.setVisibility(View.VISIBLE);
//                                travel_distance = response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText();
//                                travel_estimated_time = response.body().getRoutes().get(0).getLegs().get(0).getDuration().getText();
//                                TV_Distance .setText(response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText());
//                                TV_Estimated_Time.setText(response.body().getRoutes().get(0).getLegs().get(0).getDuration().getText());
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
//                                Log.w("-----Error-----",t.toString());
//                            }
//                        });
//
//                    }
//                    imm.hideSoftInputFromWindow(direccion_dos.getWindowToken(), 0);
//
//                    if (actionId == EditorInfo.IME_ACTION_DONE) {
//                        Log.v("PREPARE", "------");
//
//                        prepareForRequestService();
//
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });

        direccion_uno.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                isKeyboardShown(direccion_uno.getRootView());
            }
        });

        direccion_uno.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imageMapMarker.setVisibility(View.VISIBLE);
                direccion_uno.setFocusableInTouchMode(true);
                direccion_uno.setFocusable(true);
                btnAsk.setVisibility(GONE);
                btnSolicitar.setVisibility(View.VISIBLE);
                pay_content.setVisibility(View.VISIBLE);
                btnSolicitar.setEnabled(true);
                btnSolicitar.setBackgroundResource(R.drawable.btn_request);

                map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {
                        Log.v("SEGUIMIENTO", "displayLocation -- onCameraChange ");
                        Location l = new Location("");
                        l.setLatitude(cameraPosition.target.latitude);
                        l.setLongitude(cameraPosition.target.longitude);
                        searchByLocation(l);
                    }
                });
                return false;
            }
        });

        direccion_uno.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int actionId, KeyEvent arg2) {
                // hide the keyboard and search the web when the enter key
                // button is pressed
                if (actionId == EditorInfo.IME_ACTION_GO
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_NEXT
                        || actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    Log.v("SEGUIMIENTO", "oculta teclado");

                    //map.getUiSettings().setScrollGesturesEnabled(true);
                    imageMapView.setVisibility(GONE);
                    imageMapMarker.setVisibility(GONE);
                    imm.hideSoftInputFromWindow(direccion_uno.getWindowToken(), 0);

                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        Log.v("PREPARE", "------");

                        prepareForRequestService();

                    }
                    return true;
                }
                return false;
            }
        });


        direccion_uno.setFocusable(true);
        direccion_uno.requestFocus();

    }

    public void updateAddress() {
        indice = (indice == null) ? "" : indice;
        comp1 = (comp1 == null) ? "" : comp1;
        comp2 = (comp2 == null) ? "" : comp2;
        no = (no == null) ? "" : no;
        barrio = (barrio == null || barrio.equals("")) ? " No especifica " : barrio;
        obs = (obs == null) ? "" : obs;
        String address = indice + " " + comp1 + " # " + comp2 + " - " + no + " - " + barrio + " - " + obs;
        Log.v("SEGUIMIENTO", "updateAddress -- " + address);
    }

    private void setAddress(List<Address> list) {

        try {

            if (list != null && !list.isEmpty()) {
                String full_address = "";
                Address address = list.get(0);
                String dir_full = address.getAddressLine(0);
                String partes[] = dir_full.split(" ");
                full_address = full_address.concat(partes[0] + " ");

                if (list.size() > 1) {
                    address = list.get(1);
                    if (address != null && address.getAddressLine(0) != null && !address.getAddressLine(0).equals("")) {
//                        barrio = address.getAddressLine(0).split(",")[0];
                    }
                }
                updateAddress();

            } else {
                activeRequest = true;
                scheduleTryAgain();
            }

        } catch (Exception e) {
            Log.e("ERROR", "error " + e.toString() + "");
            Toast.makeText(getApplicationContext(), getString(R.string.error_net), Toast.LENGTH_LONG).show();
        }
    }

    public boolean verificarDireccion(String addr) {
        // verificar si la dirección finaliza con -

        if (addr.length() > 0 && addr.charAt(addr.length() - 1) == '-') return false;
        return true;

    }

    @Override
    public void onClick(View v) {

        String new_destiny = direccion_dos.getText().toString();
        String new_origin = direccion_uno.getText().toString();

        String origin = "Origen: " + new_origin;
        String destiny = "Destino: " + new_destiny;
        String time_destiny = "Tiempo a destino: " + travel_estimated_time;
        String trave_destiny = "Distancia a destino: " + travel_distance;
        String nocturno = recargo_nocturno > 0 ?  "Recargo nocturno: " + recargo_nocturno + "\n" : "";
        String aeropuerto = recargo_aeropuerto > 0 ?  "Recargo aeropuerto: " + recargo_aeropuerto + "\n" : "";
        String puerta = recargo_puerta_a_puerta > 0 ?  "Recargo puerta a puerta: " + recargo_puerta_a_puerta + "\n" : "";
        String total_recargos =  "Total con recargos: " + total_con_recargos + "\n";
        String pay_type_name = "Metodo de pago: " + pay_type;


        switch (v.getId()) {

            case R.id.btnSolicitar:


                if (direccion_uno.getText().toString().trim().length() != 0 && destination_name.trim().length() != 0){

                    if (!Utils.checkPlayServices(this)) {
                        hand.sendEmptyMessage(1);

                    } else {

                        Toast.makeText(MapaActivitys.this, "Completa la dirección para continuar", Toast.LENGTH_SHORT).show();

                        if(mPayType == 1){

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                            alertDialog.setTitle("¿Desea Solicitar el servicio en efectivo?");
                            alertDialog.setMessage(origin+"\n"+destiny +"\n"+ time_destiny + "\n" + trave_destiny + "\n" + pay_type_name + "\n" + nocturno + aeropuerto + puerta+ total_recargos);
                            alertDialog.setPositiveButton("Solicitar", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {

                                    if (verificarDireccion(direccion_uno.getText().toString())) {
                                        if (mFromScheduling) {
                                            Intent intent = new Intent(MapaActivitys.this, AgendarActivity.class);
                                            intent.putExtra("FromActivity", "MapaActivity");
                                            intent.putExtra("AddressFromMapa", direccion_uno.getText().toString());
                                            intent.putExtra("latitud",String.valueOf(latitud));
                                            intent.putExtra("longitud",String.valueOf(longitud));
                                            Log.i("ADDRESS TO SEND", "Lat: "+latitud+" Long: "+longitud);
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        } else {
                                            showControls();
                                            map.getUiSettings().setScrollGesturesEnabled(false);
                                            direccion_uno.clearFocus();
                                            direccion_uno.setFocusableInTouchMode(false);
                                            direccion_uno.setFocusable(false);

                                            direccion_dos.clearFocus();
                                            direccion_dos.setFocusableInTouchMode(false);
                                            direccion_dos.setFocusable(false);
                                            map.getUiSettings().setAllGesturesEnabled(false);
                                            screenShotMap(1);
                                            getTaxi();
                                        }
                                    }
                                }
                            });

                            alertDialog.setNegativeButton("Modificar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    // call fragment
                                    Fragment fragment1 = new Fragment();
                                    moveToFragment(fragment1);
                                }
                            });

                            alertDialog.show();

                        } else if (mPayType == 3){

                            Log.v("PAY_TYPE","forma de pago vale");

                            if (!isTicketValid||rTicket.equals("")) {
                                // show toast
                                Toast.makeText(this, getResources().getString(R.string.ticket_invalid), Toast.LENGTH_LONG).show();
                                return;
                            }

                            else if (isTicketValid) {

                                final EditText txtUrl = new EditText(this);
                                txtUrl.setHint("Motivo desplazamiento");

                                new AlertDialog.Builder(this)
                                        .setTitle("¿Desea pedir un servicio con vale?")
                                        .setMessage("Digita el motivo del vale")
                                        .setView(txtUrl)
                                        .setPositiveButton("Pedir", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                commitUser = txtUrl.getText().toString();
                                                if(commitUser.equals("")){
                                                    Toast.makeText(getApplicationContext(), "El motivo no puede estar vacio", Toast.LENGTH_SHORT).show();
                                                }else{
                                                    if (verificarDireccion(direccion_uno.getText().toString())) {
                                                        if (mFromScheduling) {
                                                            Intent intent = new Intent(MapaActivitys.this, AgendarActivity.class);
                                                            intent.putExtra("FromActivity", "MapaActivity");
                                                            intent.putExtra("AddressFromMapa", direccion_uno.getText().toString());
                                                            intent.putExtra("latitud",String.valueOf(latitud));
                                                            intent.putExtra("longitud",String.valueOf(longitud));
                                                            Log.i("ADDRESS TO SEND", "Lat: "+latitud+" Long: "+longitud);
                                                            setResult(RESULT_OK, intent);
                                                            finish();
                                                        } else {
                                                            map.getUiSettings().setScrollGesturesEnabled(false);
                                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_HIDDEN);
                                                            showControls();
                                                            InputMethodManager imm1 = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                            imm1.hideSoftInputFromWindow(mTicket.getWindowToken(), 0);
                                                            HideUtil.hideSoftKeyboard(MapaActivitys.this);
                                                            map.getUiSettings().setAllGesturesEnabled(false);
                                                            screenShotMap(1);
                                                            getTaxi();
                                                        }
                                                    }
                                                }
                                            }
                                        })
                                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {

                                            }
                                        })
                                        .show();
                            }
                        }

                    }

                }else{
                    Toast.makeText(MapaActivitys.this,R.string.fill_fields,Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_cancelar:
                showDialogCancel();
                break;

            case R.id.btn_direcc:
                myAddress();
                break;

            case R.id.btn_volver:
                if (isNewAddress) {
                    // disable view newAddress
//                    mNewAddress.setVisibility(View.GONE);
//                    mHandleLayout.setVisibility(View.VISIBLE);
                } else {

                    if (isRequestService) {

                        showDialogCancel();

                    } else {
                        // Intent in3 = new Intent(MapaActivitys.this,HomeActivity.class);
                        // startActivity(in3);
                        finish();
                    }
                }
                break;
            case R.id.bg_shadows:
                //slider.close();
                updateAddress();
                break;
//            case R.id.marker:
//                moveToLocation();
//                break;

            case R.id.btnAsk:

                btnAsk.setVisibility(GONE);
                btnSolicitar.setVisibility(View.VISIBLE);
                btnSolicitar.setEnabled(true);
                btnSolicitar.setBackgroundResource(R.drawable.btn_request);

                direccion_uno.setFocusableInTouchMode(true);
                direccion_uno.setFocusable(true);
                direccion_uno.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_HIDDEN);

                break;

            case R.id.btn_add:

                direccion_dos.setFocusableInTouchMode(true);
                direccion_dos.setFocusable(true);


                direccion_uno.setFocusableInTouchMode(true);
                direccion_uno.setFocusable(true);

                InputMethodManager imm1 = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                Log.v("SEGUIMIENTO", "oculta teclado");
                imm1.hideSoftInputFromWindow(direccion_uno.getWindowToken(), 0);

                // take screenshot
                Log.v("SCREENSHOT", "inicio");

                map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    public void onMapLoaded() {
                        map.snapshot(new GoogleMap.SnapshotReadyCallback() {
                            public void onSnapshotReady(Bitmap bitmap) {

                                myBitmap = bitmap;
                                Log.v("SCREENSHOT", "snapshot");

                                // saveToInternalSorage(bitmap);

                                saveImage(getApplicationContext(), bitmap, "foto", "png");
                                String strLatitud = String.valueOf(latitud);
                                String strLongitud = String.valueOf(longitud);
                                mPref.setRootActivity("MapaActivitys");
                                Intent i2 = new Intent(MapaActivitys.this, NewAddressActivity.class);
                                i2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                i2.putExtra("direccion", direccion_uno.getText().toString());
                                i2.putExtra("barrio", barrio);
                                i2.putExtra("lat", strLatitud);
                                i2.putExtra("lng", strLongitud);
                                startActivity(i2);

                            }
                        });
                    }
                });

                break;
        }

    }

    private void moveToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

    }

    public void screenShotMap(final int i) {
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            public void onMapLoaded() {
                map.snapshot(new GoogleMap.SnapshotReadyCallback() {
                    public void onSnapshotReady(Bitmap bitmap) {
                        Log.v("SCREENSHOT", "snapshot");
                        map.getUiSettings().setScrollGesturesEnabled(false);

                        if (i == 0) {
                            imageMapView.setImageBitmap(bitmap);
                            imageMapView.setVisibility(View.VISIBLE);
                            imageMapMarker.setVisibility(View.VISIBLE);
                        } else {
                            imageMap.setImageBitmap(bitmap);
                        }
                    }
                });
            }
        });
    }

    public void saveImage(Context context, Bitmap b, String name, String extension) {
        name = name + "." + extension;
        FileOutputStream out;
        try {
            out = context.openFileOutput(name, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getImageBitmap(Context context, String name, String extension) {
        name = name + "." + extension;
        try {
            FileInputStream fis = context.openFileInput(name);
            Bitmap b = BitmapFactory.decodeStream(fis);
            fis.close();
            return b;
        } catch (Exception e) {
        }
        return null;
    }

    private String saveToInternalSorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

    public static Bitmap captureScreen(View v) {

        Bitmap screenshot = null;
        try {

            if (v != null) {

                screenshot = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(screenshot);
                v.draw(canvas);
            }

        } catch (Exception e) {
            Log.d("ScreenShotActivity", "Failed to capture screenshot because:" + e.getMessage());
        }

        return screenshot;
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    public void showDialogForAddress() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapaActivitys.this);
        builder.setTitle("Información");
        builder.setMessage("Por favor, Completa la dirección");
        builder.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("PULSADO", "----------");
                        direccion_uno.setFocusableInTouchMode(true);
                        direccion_uno.setFocusable(true);
                        direccion_uno.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_HIDDEN);

                    }
                });
        builder.setCancelable(false);
        builder.create();
        builder.show();

    }

    public void prepareForRequestService() {

        pay_content.setVisibility(View.VISIBLE);
        //kind_text.setVisibility(View.VISIBLE);
        btnSolicitar.setEnabled(true);
        btnSolicitar.setBackgroundResource(R.drawable.btn_request);

    }

    //Location Related Methods
    private void searchByLocation(final Location location) {


        double latitude = location.getLatitude(), longitude = location.getLongitude();
        latitud = latitude;
        longitud = longitude;

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
        Call<ReverseGeocodingResponse> call_profile=service.reverse_geocoding(latitud + "," +longitud);
        call_profile.enqueue(new Callback<ReverseGeocodingResponse>() {


            @Override
            public void onResponse(Call<ReverseGeocodingResponse> call, Response<ReverseGeocodingResponse> response) {

                if (response.body().getStatus().equalsIgnoreCase("OK")){
                    String userAddress = response.body().getResults().get(0).getFormatted_address();
                    String userBarrio = response.body().getResults().get(0).getAddress_components().get(2).getLong_name();


                    Log.e("INFO DIR---", barrio + "  -   "+ userAddress);


                    int index = userAddress.indexOf("-");
                    if (index != -1) {
                        userAddress = userAddress.substring(0, index + 1);
                    }

                    if(direccion_uno.hasFocus()) {

                        if(dir_by_result.length() > 0){
                            dir_by_result = "";
                        }else {

                            if(getIntent().getExtras() != null && getIntent().getExtras().getBoolean("new_destination",false)){
                                barrio = userBarrio;
                                direccion_uno.setText(userAddress + " 1");
                                no = userAddress;
                                direccion_uno.setEnabled(false);
                                direccion_uno.clearFocus();
                                direccion_dos.requestFocus();
                            }else {
                                direccion_uno.setEnabled(true);
                                barrio = userBarrio;
                                direccion_uno.setText(userAddress);
                                no = userAddress;
                                direccion_uno.setSelection(userAddress.length());
                            }
                            //btnOk.setEnabled(true);
                        }
                    }
                    if(getIntent().getExtras() != null && getIntent().getExtras().getBoolean("new_destination",false)){
                        barrio = userBarrio;
                        direccion_uno.setText(userAddress + " 1");
                        no = userAddress;
                        direccion_uno.setEnabled(false);
                        direccion_uno.clearFocus();
                        direccion_dos.requestFocus();
                    }

                    if (mGoogleApiClient.isConnected()) {
                        stopLocationUpdates();
                        mGoogleApiClient.disconnect();
                    }
                }


            }

            @Override
            public void onFailure(Call<ReverseGeocodingResponse> call, Throwable t) {
                Log.w("-----Error-----",t.toString());
            }
        });


        Log.v("SEGUIMIENTO", "searchByLocation -- ini ");

//        AsyncTask<Void, Void, List<Address>> task = new AsyncTask<Void, Void,
//                List<android.location.Address>>() {
//
//            @Override
//            protected List<android.location.Address> doInBackground(Void... params) {
//                Geocoder geocoder = new Geocoder(MapaActivitys.this);
//                List<android.location.Address> list = null;
//                try {
//                    double latitude = location.getLatitude(), longitude = location.getLongitude();
//                    latitud = latitude;
//                    longitud = longitude;
//                    Log.i("ADDRESS UPDATE","Lat: "+latitud+" Long: "+longitude);
//                    list = geocoder.getFromLocation(latitude, longitude, 2);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return list;
//            }
//
//            protected void onPostExecute(List<android.location.Address> result) {
//                if (result != null) {
//                    if (result.size() > 1) {
//                        android.location.Address address = result.get(0);
//                        android.location.Address addressSecondary = result.get(1);
//
//                        String userAddress = address.getAddressLine(0);
//                        String userBarrio = addressSecondary.getAddressLine(0);
//
//                        Log.i("SEGUIMIENTO", "searchByLocation -- onPostExecute userAddress " + userAddress);
//                        Log.i("SEGUIMIENTO", "searchByLocation -- onPostExecute userBarrio " + userBarrio);
////                        longitud = address.getLongitude();
////                        latitud = address.getLatitude();
//
//                        // se agrego para enviar
//                        if (address != null && address.getAddressLine(0) != null && !address.getAddressLine(0).equals("")) {
//                            //barrio = address.getAddressLine(0).split(",")[0];
//                            barrio = userBarrio;
//                            Log.v("SEGUIMIENTO", "searchByLocation -- onPostExecute barrio " + barrio);
//                            //dir_barrio.setText(barrio);
//                        }
//
//                        int index = userAddress.indexOf("-");
//                        if (index != -1) {
//                            userAddress = userAddress.substring(0, index + 1);
//                        }
//                        Log.d("Address", userAddress);
//                        Log.v("SEGUIMIENTO", "searchByLocation -- onPostExecute userAddress 2 " + userAddress);
//
//                        Log.v("SEGUIMIENTO", "direccion_uno " + userAddress);
//                        Log.i("ADDRESS SELECTED", "Barrio: " + address.getLocality());
//
//                        if (direccion_dos.isFocused()) {
//
//                            direccion_dos.setText(userAddress);
//                            no = userAddress;
//                            direccion_dos.setSelection(userAddress.length());
//                            direccion_dos.setEnabled(true);
//                            //btnOk.setEnabled(true);
//
//                        }else {
//                            direccion_uno.setText(userAddress);
//                            no = userAddress;
//                            direccion_uno.setSelection(userAddress.length());
//                            direccion_uno.setEnabled(true);
//                            //btnOk.setEnabled(true);
//                        }
//
//                        if (mGoogleApiClient.isConnected()) {
//                            stopLocationUpdates();
//                            mGoogleApiClient.disconnect();
//                        }
//                    }
//                }
//
//            }
//        };
//
//        task.execute();
    }

    private void myAddress() {

        mPref.setRootActivity("MapaActivitys");

        if (conf.getLogin()) {
            Intent in2 = new Intent(MapaActivitys.this, MyAddressesActivity.class);
            startActivityForResult(in2, 1);
        } else {
            Intent i = new Intent(this, RegistroActivity.class);
            i.putExtra("target", Target.ADDRESS_TARGET);
            startActivityForResult(i, 10);
        }
    }

    private void getTaxi() {
        btnSolicitar.setEnabled(false);

        if (Connectivity.isConnected(this)) {

            if (verificarDireccion(direccion_uno.getText().toString())) {

                String dir_full = direccion_uno.getText().toString();
                Pattern mPattern = Pattern.compile("^(.*)\\ (.*)\\#(.*)\\-(.*)");
                Matcher matcher = mPattern.matcher(dir_full.toString());
                comp1 = direccion_uno.getText().toString();
                comp1 = comp1.replaceAll("[^0-9 ]", "") + " " + comp1.replaceAll("[^A-Z a-z]", "");

                if (conf.getLogin()) {
                    if (direccion_uno.getText().toString().trim().length() != 0 && direccion_dos.getText().toString().trim().length() != 0) {
                        btnSolicitar.setEnabled(true);
                    }
                    id_user = conf.getIdUser();
                    uuid = conf.getUuid();
                    getService();
                } else {
                    mPref.setRootActivity("MapaActivitys");

                    Intent i = new Intent(MapaActivitys.this, RegistroActivity.class);

                    i.putExtra("index", indice);
                    i.putExtra("position", String.valueOf(indice_posicion));
                    i.putExtra("comp1", comp1);
                    i.putExtra("comp2", comp2);
                    i.putExtra("no", direccion_uno.getText().toString());
                    i.putExtra("barrio", barrio);
                    i.putExtra("obs", "");
                    i.putExtra("latitud", String.valueOf(latitud));
                    i.putExtra("longitud", String.valueOf(longitud));
                    i.putExtra("target", Target.TAXI_TARGET);
                    startActivity(i);
                    btnSolicitar.setEnabled(true);
                    hideLoader();
                }

            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(MapaActivitys.this);
                builder.setTitle("Información");
                builder.setMessage("Verifica los datos de la dirección");
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //slider.open();
                        btnSolicitar.setEnabled(true);
                        hideLoader();
                    }
                });
                builder.setCancelable(false);
                builder.create();
                builder.show();
            }
        }
        else {
            new Dialogos(MapaActivitys.this, R.string.error_net);
            btnSolicitar.setEnabled(true);
            hideLoader();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("SEGUIMIENTO", "onActivityResult ini resultCode " + String.valueOf(resultCode));
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Log.v("SEGUIMIENTO", "onActivityResult - data " + data.toString());
                String dir_completa = null;

                String indice1 = data.getStringExtra("index");
                comp1 = data.getStringExtra("comp1");
                comp2 = data.getStringExtra("comp2");
                no = data.getStringExtra("no");
                barrio = data.getStringExtra("barrio");
                obs = data.getStringExtra("obs");

                if (data.getStringExtra("address") != null) {
                    dir_completa = data.getStringExtra("address");
                } else {
                    dir_completa = indice1 + " " + comp1 + " #" + comp2 + "-" + no;
                }

                //String dir_completa = indice1 + " " + comp1 + " #" + comp2 + "-" + no;
                Log.v("SEGUIMIENTO", "onActivityResult - data " + dir_completa);

                if (data.getStringExtra("lat") != null) {
                    Log.v("ADDRESS1", "dirección con coordenada");
                    latitud = Double.valueOf(data.getStringExtra("lat"));
                    longitud = Double.valueOf(data.getStringExtra("lng"));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 17));
                }
                dir_by_result = dir_completa;
                direccion_uno.setText(dir_completa);
            }

        } else if (resultCode == 2) {
            mPref.setRootActivity("MapaActivitys");
            // Intent in2 = new Intent(MapaActivitys.this, Mis_direcciones.class);
            // startActivityForResult(in2, 1);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.v("onKey", "onKeyDown --- keyCode " + String.valueOf(keyCode));

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Log.v("onKey", "onKeyDown --- KeyEvent.KEYCODE_BACK");

            String activityName = mPref.getRootActivity();

            if (isRequestService) {

                showDialogCancel();

            } else {
                if (activityName.equals("MapaActivitys")) {
                    Intent in3 = new Intent(MapaActivitys.this, HomeActivity.class);
                    startActivity(in3);
                    finish();
                } else {
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.v("onKey", "onKeyUp ------ keyCode " + String.valueOf(keyCode));
        return super.onKeyUp(keyCode, event);
    }

    private void hideLoader() {
        try {
            pd.dismiss();
        } catch (Exception e) {
        }
    }

    private void displayConnectivityPanel(boolean display) {

        mNoConnectivityPanel.setVisibility(display ? View.VISIBLE : GONE);
        if(display)
            mConnectivityLoaderImage.startAnimation(AnimationUtils.loadAnimation(this,R.anim.connection_loader));
    }

    @Override
    public void onNetworkConnectivityChange(boolean connected) {
        displayConnectivityPanel(!connected);
    }


    private int waitTime = 5000;

    @Override
    public void onConnectivityQualityChecked(boolean Optimal) {
//        displayConnectivityPanel(!Optimal);
    }

    public class GetPosition extends AsyncTask<Double, Integer, List<Address>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v("CNF_SRV", "GetPosition 1");
            hand.sendEmptyMessage(2);
            activeRequest = true;
        }

        @Override
        protected List<Address> doInBackground(Double... params) {

            Log.e("PIDIENDO", "PIDIENDO");
            Log.v("PIDIENDO", "params" + String.valueOf(params));
            if (params[0] != 0 && params[1] != 0) {
                Log.v("PIDIENDO", "por llamar returnAddress");
                return returnAddress(params[0], params[1]);
            } else {
                Log.e("NULL", "NULL");
                return null;
            }
        }

        protected void onPostExecute(List<Address> result) {
            Log.v("SEGUIMIENTO", "onPostExecute -- ");
            Log.v("onPostExecute", "result 0 " + String.valueOf(result));
            if (result != null && !result.isEmpty()) {

                Log.v("onPostExecute", "result 1 " + String.valueOf(result));
                Log.v("SEGUIMIENTO", "onPostExecute result 1 " + String.valueOf(result));

                // Animation shake = AnimationUtils.loadAnimation(MapaActivitys.this, R.anim.shake);
                // findViewById(R.id.handle).startAnimation(shake);
                setAddress(result);

                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 18f), 17, null);


            } else {
                Log.v("SEGUIMIENTO", "onPostExecute result PAILAS");
                Log.e("PAILAS", "PIALAS");
                Log.e("PAILAS", "reintent " + String.valueOf(reintent));
                if (reintent > 0) {
                    scheduleTryAgain();
                    reintent--;
                } else {
                    Log.e("PAILAS", "- reintent " + String.valueOf(reintent));
///			    	direccion_completar_uno.setText("Por favor, verifique la configuración de internett...");
                    //activeRequest = true;
                }
            }
            activeRequest = false;
            Log.e("FIN", "FIN");
        }

    }

    private void scheduleTryAgain() {
        Log.v("CNF_SRV1", "MapaActivitys scheduleTryAgain ");
//        marker.setEnabled(false);
        count = 10;
        timer = new Timer();
        TimerTask scanTask;
        final Handler handler = new Handler();
        scanTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        hand.sendEmptyMessage(3);
                    }
                });
            }
        };

        timer.schedule(scanTask, 5, 1500);
    }

    @SuppressLint("HandlerLeak")
    private Handler hand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    showSettingsAlert();
                    break;
                case 2:
                    Log.v("SEGUIMIENTO", "Handle updateAddress -- " + getString(R.string.sd));
                    break;

                case 3:
                    if (count <= 0) {

                        if (!activeRequest) {
                            timer.cancel();
                            timer.purge();
                            gp.cancel(true);
                            gp = new GetPosition();
                            gp.execute(latitud, longitud);
                        }

                    } else {
///					direccion_completar_uno.setText("Buscando dirección en "+ count + " segundos..");
                        count--;
                    }
                case 4:
                    showInternetAlert();
                    break;

                default:
                    break;
            }

        }

    };

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("El GPS no está habilitado");
        alertDialog.setMessage(getString(R.string.enable_gps));
        alertDialog.setPositiveButton("Habilitar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                finish();
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        alertDialog.show();
    }

    public void showInternetAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("No tiene una conexión a internet");
        alertDialog.setMessage(getString(R.string.enable_internet));
        alertDialog.setPositiveButton("Habilitar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                //finish();
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        alertDialog.show();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
                // Intent in3 = new Intent(MapaActivitys.this,HomeActivity.class);
                // startActivity(in3);

                finish();
            }
            return false;
        }
        return true;
    }

    private void displayLocation() {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        Log.v("SEGUIMIENTO", "displayLocation -- ");

        if (mLastLocation != null) {
            latitud = mLastLocation.getLatitude();
            longitud = mLastLocation.getLongitude();
            Log.v("SEGUIMIENTO", " displayLocation ok lat=" + String.valueOf(latitud) + " lng=" + String.valueOf(longitud));

            //map = fm.getMap();
///			map.setMyLocationEnabled(false);
///         map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 10f), 40, null);
///            moveToLocation();
            map.getUiSettings().setMyLocationButtonEnabled(true);
            //map.getUiSettings().setZoomControlsEnabled(false);
            //map.getUiSettings().setCompassEnabled(false);
            map.setMyLocationEnabled(true);

            map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    Log.v("SEGUIMIENTO", "displayLocation -- onCameraChange ");
                    Location l = new Location("");
                    l.setLatitude(cameraPosition.target.latitude);
                    l.setLongitude(cameraPosition.target.longitude);
                    searchByLocation(l);
                }
            });

        } else {
            Log.v("displayLocation", "bad lat=" + String.valueOf(latitud) + " lng=" + String.valueOf(longitud));
            Log.v("TEST_GPS", "displayLocation");
            //hand.sendEmptyMessage(1);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.e("LOG", "onConnectionFailed");
        Log.v("SEGUIMIENTO", "onConnectionFailed -- ");

        Toast.makeText(this, getResources().getString(R.string.enable_gps), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.e("LOG", "onConnected");
        Log.v("SEGUIMIENTO", "onConnected -- ");
///		mLocationClient.requestLocationUpdates(mLocationRequest, this);
        displayLocation();
        startLocationUpdates();
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }

    }

    @Override
    public void onConnectionSuspended(int arg0) {
//        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("onLocationChanged", "ini");
        if (firstTime) {
            Log.v("onLocationChanged", "firstTime ok");
            if(getIntent().getExtras() != null && getIntent().getExtras().getBoolean("new_destination",false)){

            }else {
                if (direccion_uno.isFocused()) {
                    latitud = (float) location.getLatitude();
                    longitud = (float) location.getLongitude();
                }
                Log.i("GPS_1", "onLocationChanged firstTime " + String.valueOf(latitud)+ " Long "+longitud);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 17));
                firstTime = false;
            }

        }
    }

    public List<Address> returnAddress(double latitude, double longitude) {
        List<Address> addresses = null;

        try {

            if (Geocoder.isPresent()) {

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                addresses = geocoder.getFromLocation(latitude, longitude, 2);

                if (addresses.isEmpty() || addresses == null) {
                    addresses = Utils.getFromLocation(latitude, longitude, 2);
                }

            } else {
                addresses = Utils.getFromLocation(latitude, longitude, 2);
            }
        } catch (Exception e) {
            Log.e("ERROR", e.toString());

            addresses = Utils.getFromLocation(latitude, longitude, 2);
        }

        Log.v("returnAddress", "address = " + String.valueOf(addresses));
        return addresses;
    }

    public LocationRequest createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    protected void startLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }
    }

    protected void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler puente = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Log.v("SolicitandoActivity", "Message msg = " + msg.toString());

            if (msg.what == 0) {
                //animar();
                // Intent in3 = new Intent(MapaActivitys.this, HomeActivity.class);
                // startActivity(in3);
                finish();

            } else if (msg.what == 2) {
                Log.v("CNF_SRV", "err_request 1");
                err_request();

            } else if (msg.what == 3) {

                try {

                    Log.v("CNF_SRV", "no driver 1");
                    Toast.makeText(getApplicationContext(), getString(R.string.error_no_driver), Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(MapaActivitys.this);
                    builder.setMessage(getString(R.string.error_no_driver))
                            .setTitle(getString(R.string.llamar_pbx))
                            .setPositiveButton(getString(R.string.llamar_pbx), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    try {
                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                        callIntent.setData(Uri.parse("tel:0312000000"));
                                        if (ActivityCompat.checkSelfPermission(MapaActivitys.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            return;
                                        }
                                        startActivity(callIntent);
                                    } catch (Exception e) {
                                        Log.e("LLAMADA", "Error al llamar:", e);
                                    }
                                }
                            });

                    builder.create();

                    builder.show();

                } catch (Exception e) {
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(200);
                    Log.v("CNF_SRV", "no driver 2");
                    Toast.makeText(MapaActivitys.this, getString(R.string.error_no_driver), Toast.LENGTH_LONG).show();

                    //                Intent in3 = new Intent(MapaActivitys.this,HomeActivity.class);
                    // startActivity(in3);
                    finish();
                }
            } else if (msg.what == 1500) {

                Toast.makeText(getApplicationContext(), getString(R.string.cancel_service), Toast.LENGTH_SHORT).show();

            }
            else if (msg.what == 2000) {

                cancelByService(getResources().getString(R.string.cancel_service, id_user));
                Log.v("SolicitandoActivity", "msg.what = 2000");
                Log.v("CNF_SRV", "no driver 3");
                Toast.makeText(getApplicationContext(), getString(R.string.error_no_driver), Toast.LENGTH_SHORT).show();

                hideControls();

                map.getUiSettings().setScrollGesturesEnabled(true);

            }
        }

    };


    private void getService() {

        if (direccion_uno.getText().toString().trim().length() != 0 && direccion_dos.getText().toString().trim().length() != 0) {

            String strLatitud = String.valueOf(latitud);
            String strLongitud = String.valueOf(longitud);
            Log.i("ADDRESS ON SERVICE", "Lat: " + latitud + " Long: " + longitud);

            final ObjectAnimator animation = ObjectAnimator.ofInt(circleProgressBar, "progress", 1, 90);

            if (android.os.Build.VERSION.SDK_INT >= 11) {
                animation.setDuration(10000).addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        Log.v("ANIMATION", "START");
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        Log.v("ANIMATION", "STOP");
                    }
                });
                animation.setRepeatCount(10);
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
            } else {


            }
            animation.start();

            isRequestService = true;
            String address = direccion_uno.getText().toString();
            Log.v("SOLICITANDO_SERVICIO", "address " + address);
            this.sendBroadcast(new Intent("com.google.android.intent.action.GTALK_HEARTBEAT"));
            this.sendBroadcast(new Intent("com.google.android.intent.action.MCS_HEARTBEAT"));

            // TODO: prepare data antes de enviar el servicio
            String userEmail = conf.getUser();
            String userUuid = conf.getIdUser();
            String payType = "3";
            if (mPayType == 1) {
                payType = "1";
                mCardReference = "0";
            } else if (mPayType == 2) {
                payType = "2";
                mCardReference = conf.getCardDefault();
                if (mCardReference == null) mCardReference = "0";
            } else {
                payType = "3";
                mCardReference = mTicket.getText().toString();
            }

            String strPhone = conf.getPhone();
            String strCode = strPhone.length() > 2 ? strPhone.substring(strPhone.length() - 2) : strPhone;
            String commit = commitUser;
            String old_destination = mPlaceDetailsText.getText().toString();
            String destination = destination_name;

            Log.v("CODE1", "strCode " + strCode);

            pDialog = new ProgressDialog(MapaActivitys.this);
            pDialog.setMessage(getString(R.string.texto_solicitando_servicio));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);

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
            if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("new_destination", false)) {
                Call<InterruptResponse> call_profile = service.interrupt(Integer.parseInt(conf.getCarId()),Integer.parseInt(conf.getDriverId()), Double.parseDouble(strLatitud),  Double.parseDouble(strLongitud), to_lat, to_lng, address, "", barrio, "", 0,"", 0, getIntent().getExtras().getInt("last_service",-1),1);
                call_profile.enqueue(new Callback<InterruptResponse>() {
                    @Override
                    public void onResponse(Call<InterruptResponse> call, Response<InterruptResponse> response) {


                        try {

                            if (myTimer == null) {
                                Log.v("CNF_SRV", "myTimer is null");
                                myTimer = new Timer();
                            }

                                mServiceId = response.body().getId();
                                conf.setServiceId(mServiceId);
                                mStatusNew = Integer.parseInt(response.body().getStatus_id());
                                Log.v("SERVICE_STATUS", "getServiceAddress onSuccess service_id " + mServiceId);
                                Log.e("SERVICE_CMS", "    GET_SERVICE_ADDRESS success service_id= " + mServiceId);

                                final Calendar c = Calendar.getInstance();
                                long actualDate = c.getTimeInMillis();

                                mySQLiteAdapter.insertService(mServiceId, String.valueOf(mStatusNew), "", id_user, "", actualDate);
                                reintento = 0;
                                myTimer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        Log.e("TIMER_EJECUTANDO", "EJECUTANDO *** " + String.valueOf(reintento));
                                        Log.v("SOLICITANDO_SERVICIO", "TIMER - EJECUTANDO " + String.valueOf(new Date()));

                                        reintento++;

                                        try {
                                            checkService();
                                        } catch (JSONException e) {
                                            Log.v("CHECK1", "checkService exception " + e.toString());
                                            e.printStackTrace();
                                        }

                                        //if (reintento >= 3) {
                                        if (reintento >= 29) {
                                            Log.e("TIMER_EJECUTANDO", "FIN EJECUTANDO *** ");
                                            Log.v("SOLICITANDO_SERVICIO", "TIMER - FIN EJECUTANDO " + String.valueOf(new Date()));


                                            isReceiverRegistered = false;
                                            puente.sendEmptyMessage(2000);
                                            myTimer.cancel();
                                            myTimer.purge();
                                            myTimer = null;

                                        }
                                    }

                                }, 5000, 3000);


                        } catch (Exception e) {
                            err_request();
//
                            isRequestService = false;
                        }

                    }

                    @Override
                    public void onFailure(Call<InterruptResponse> call, Throwable t) {
                        Log.w("-----Error-----", t.toString());
                        err_request();
                        pDialog.dismiss();
                    }
                });
            } else {

                retrofit = new Retrofit.Builder()
                        .baseUrl(Connect.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
                service = retrofit.create(ApiService.class);

                Call<RequestServiceAddressResponse> call_profile = service.requestServiceAddress(id_user, strLatitud, strLongitud, String.valueOf(to_lat) ,String.valueOf(to_lng), barrio, address, uuid, payType, "", userEmail, mCardReference, strCode, commit, destination);
            call_profile.enqueue(new Callback<RequestServiceAddressResponse>() {
                @Override
                public void onResponse(Call<RequestServiceAddressResponse> call, Response<RequestServiceAddressResponse> response) {


                    try {

                        if (myTimer == null) {
                            Log.v("CNF_SRV", "myTimer is null");
                            myTimer = new Timer();
                        }

                        if (response.body().getSuccess()) {

                            mServiceId = response.body().getService_id();
                            conf.setServiceId(mServiceId);
                            mStatusNew = response.body().getStatus_id();
                            Log.v("SERVICE_STATUS", "getServiceAddress onSuccess service_id " + mServiceId);
                            Log.e("SERVICE_CMS", "    GET_SERVICE_ADDRESS success service_id= " + mServiceId);

                            final Calendar c = Calendar.getInstance();
                            long actualDate = c.getTimeInMillis();

                            mySQLiteAdapter.insertService(mServiceId, String.valueOf(mStatusNew), "", id_user, "", actualDate);
                            reintento = 0;
                            myTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Log.e("TIMER_EJECUTANDO", "EJECUTANDO *** " + String.valueOf(reintento));
                                    Log.v("SOLICITANDO_SERVICIO", "TIMER - EJECUTANDO " + String.valueOf(new Date()));

                                    reintento++;

                                    try {
                                        checkService();
                                    } catch (JSONException e) {
                                        Log.v("CHECK1", "checkService exception " + e.toString());
                                        e.printStackTrace();
                                    }

                                    //if (reintento >= 3) {
                                    if (reintento >= 29) {
                                        Log.e("TIMER_EJECUTANDO", "FIN EJECUTANDO *** ");
                                        Log.v("SOLICITANDO_SERVICIO", "TIMER - FIN EJECUTANDO " + String.valueOf(new Date()));


                                        isReceiverRegistered = false;
                                        puente.sendEmptyMessage(2000);
                                        myTimer.cancel();
                                        myTimer.purge();
                                        myTimer = null;

                                    }
                                }

                            }, 5000, 3000);


                        } else {
                            if (response.body().getError() == Error.NO_DRIVER_ENABLE) {

                                Log.v("SOLICITANDO_SERVICIO", "error - Error.NO_DRIVER_ENABLE " + String.valueOf(new Date()));
                                Toast.makeText(getApplicationContext(), getString(R.string.error_no_driver), Toast.LENGTH_LONG).show();

                            } else {
                                Log.v("SOLICITANDO_SERVICIO", "error_request - " + String.valueOf(new Date()));
                                err_request();

                            }

                            finish();
                        }

                    } catch (Exception e) {
                        err_request();
//
                        isRequestService = false;
                        puente.sendEmptyMessage(2000);
                    }

                }

                @Override
                public void onFailure(Call<RequestServiceAddressResponse> call, Throwable t) {
                    Log.w("-----Error-----", t.toString());
                    err_request();
                    puente.sendEmptyMessage(2000);
                    pDialog.dismiss();
                }
            });

        }

        }else{
            Toast.makeText(MapaActivitys.this,R.string.fill_fields,Toast.LENGTH_SHORT).show();
        }

    }

    private void showDialogCancel() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);

        dialogo1.setTitle(getResources().getString(R.string.app_name));
        dialogo1.setMessage(getResources().getString(R.string.confirm_cancel));
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        hideControls();
                        map.getUiSettings().setScrollGesturesEnabled(true);

                        if (myTimer != null) {
                            myTimer.cancel();
                            myTimer.purge();
                            myTimer = null;
                        }

                        isRequestService = false;
                        cancelarService();
                    }
                });
        dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.cancel();
            }
        });
        dialogo1.show();
    }


    public void checkService() throws JSONException {

        service_id = conf.getServiceId();
//        if ((mServiceId != null) && (mServiceId.length() > 1)) service_id = mServiceId;
        Log.e("SERVICE_CMS", "    CHECK_SERVICE");
        Log.v("CHECK1","checkService service_id =  " + service_id);

        Log.v("checkService", "id_user=" + id_user + " service_id=" + service_id);
        this.sendBroadcast(new Intent("com.google.android.intent.action.GTALK_HEARTBEAT"));
        this.sendBroadcast(new Intent("com.google.android.intent.action.MCS_HEARTBEAT"));

        Log.v("CHECK1","checkService 2 service_id =  " + service_id);

    }

    private void cancelarService() {

        cancelService(id_user);

    }

    public void cancelService(final String id_user) {

        // add randome delay
        int delay = randInt(350, 1500);

        // call function
        Log.v("CANCEL_SERVICE", "ini " + String.valueOf(new Date()));
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.v("CANCEL_SERVICE", "fin " + String.valueOf(new Date()));


        this.sendBroadcast(new Intent("com.google.android.intent.action.GTALK_HEARTBEAT"));
        this.sendBroadcast(new Intent("com.google.android.intent.action.MCS_HEARTBEAT"));

        try {
            if (!pDialog.isShowing()) {
                pDialog = new ProgressDialog(MapaActivitys.this);
                pDialog.setMessage("Cancelando Servicio....");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }
        } catch (Exception e) {
        }


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

        Call<CancelServiceResponse> call_profile=service.cancelService( id_user);
        call_profile.enqueue(new Callback<CancelServiceResponse>() {
            @Override
            public void onResponse(Call<CancelServiceResponse> call, Response<CancelServiceResponse> response) {
                try {

                    if (response.body().getSuccess()) {
                        try {
                            isError = false;

                            pDialog.dismiss();

                        } catch (Exception e) {
                        }

                        hideControls();

                        map.getUiSettings().setScrollGesturesEnabled(true);

                        //finish();

                    } else {

                        if (response.body().getError() == 404) {
                            try {
                                pDialog.dismiss();
                            } catch (Exception e) {
                            }

                            finish();
                            isError = false;

                        } else {
                            try {
                                pDialog.dismiss();
                            } catch (Exception e) {
                            }
                            puente.sendEmptyMessage(1500);
                        }

                    }

                } catch (Exception e) {

                    try {
                        pDialog.dismiss();
                    } catch (Exception e1) {
                    }
                    puente.sendEmptyMessage(1500);
                }

            }

            @Override
            public void onFailure(Call<CancelServiceResponse> call, Throwable t) {
                Log.w("-----Error-----",t.toString());
                isError = true;
                try {
                    pDialog.dismiss();
                } catch (Exception e1) {
                }
                puente.sendEmptyMessage(1500);

            }
        });


//        MiddleConnect.cancelService(Url, new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onStart() {
//
//                try {
//                    if (!pDialog.isShowing()) {
//                        pDialog = new ProgressDialog(MapaActivitys.this);
//                        pDialog.setMessage("Cancelando Servicio....");
//                        pDialog.setIndeterminate(false);
//                        pDialog.setCancelable(false);
//                        pDialog.show();
//                    }
//                } catch (Exception e) {
//                }
//
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                String response = new String(responseBody);
//                try {
//                    Log.e("solicitando", "SUCCES: " + response);
//
//                    JSONObject responsejson = new JSONObject(response);
//
//                    if (responsejson.getBoolean("success")) {
//                        try {
//                            isError = false;
//
//                            pDialog.dismiss();
//
//                        } catch (Exception e) {
//                        }
//
//                        hideControls();
//
//                        map.getUiSettings().setScrollGesturesEnabled(true);
//
//                        //finish();
//
//                    } else {
//
//                        if (responsejson.getInt("error") == 404) {
//                            try {
//                                pDialog.dismiss();
//                            } catch (Exception e) {
//                            }
//
//                            finish();
//                            isError = false;
//
//                        } else {
//                            isError = true;
//                        }
//
//                    }
//
//                } catch (Exception e) {
//
//                    isError = true;
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                String response = new String(responseBody);
//                Log.e("solicitando", "onFailure: " + response);
//
//                isError = true;
//                try {
//                    pDialog.dismiss();
//                } catch (Exception e1) {
//                }
//                puente.sendEmptyMessage(1500);
//            }
//
//            @Override
//            public void onFinish() {
//
//                if (isError) {
//                    try {
//                        pDialog.dismiss();
//                    } catch (Exception e) {
//                    }
//                    puente.sendEmptyMessage(1500);
//                }
//            }
//
//        });

    }

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public void cancelByService(final String Url) {

        service_cancel();
        url_cancel_current = Url;
        MiddleConnect.cancelServiceBySystem(Url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

                try {

                    if (!pDialog.isShowing()) {
                        pDialog = new ProgressDialog(MapaActivitys.this);
                        pDialog.setMessage("Cancelando Servicio....");
                        pDialog.setIndeterminate(false);
                        pDialog.setCancelable(false);
                        pDialog.show();
                    }
                } catch (Exception e) {
                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.e("solicitando", "SUCCES: " + response);

                try {

                    JSONObject responsejson = new JSONObject(response);

                    if (responsejson.getBoolean("success")) {
                        try {
                            isError = false;
                            pDialog.dismiss();

                        } catch (Exception e) {
                        }

                        hideControls();
                        map.getUiSettings().setScrollGesturesEnabled(true);
                        //finish();

                    } else {

                        if (responsejson.getInt("error") == 404) {
                            try {
                                pDialog.dismiss();
                            } catch (Exception e) {
                            }
                            //finish();
                            hideControls();

                            map.getUiSettings().setScrollGesturesEnabled(true);
                            isError = false;

                        } else {
                            isError = true;
                        }
                    }

                } catch (Exception e) {

                    isError = true;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String response = new String(responseBody);
                Log.e("solicitando", "onFailure: " + response);

                isError = true;
                try {
                    pDialog.dismiss();
                } catch (Exception e1) {
                }
                puente.sendEmptyMessage(1500);
            }

            @Override
            public void onFinish() {

                if (isError) {
                    try {
                        pDialog.dismiss();
                    } catch (Exception e) {
                    }
                    puente.sendEmptyMessage(1500);
                }
            }

        });
    }

    public void service_cancel() {

        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        mPref.setRootActivity("MapaActivitys");
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(getResources().getString(R.string.error_no_driver)))
                .setAutoCancel(true)
                .setContentText(getResources().getString(R.string.error_no_driver));

        mBuilder.setContentIntent(contentIntent);
        mBuilder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.audio2), 1);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(250);

    }

    public void err_cancel() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_cancel_service), Toast.LENGTH_SHORT).show();
    }


    public void err_request() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        Toast.makeText(getApplicationContext(), getString(R.string.error_no_procc), Toast.LENGTH_SHORT).show();
    }


    public void checkCreditCards() {
        Log.v("CHECK_TC","inicio ");

        // TODO: verifica si tiene
        // configurar mCardReference

        String card = conf.getCardDefault();
        if (card != null) {
            if (card.length() > 0) {
                mCardReference = card;
            } else {
                // llamar a la creacion de tarjeta
                Intent i = new Intent(MapaActivitys.this, PaymentsActivity.class);
                startActivity(i);
            }
        }
        else {
            Intent i = new Intent(MapaActivitys.this, PaymentsActivity.class);
            startActivity(i);
        }

    }

    @Override
    public void onError(Status status) {
        Log.e("LOG", "onError: Status = " + status.toString());
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
    }

    private static Spanned formatPlaceDetails(Resources res,  LatLng LatLng) {
        Log.e("LOG", res.getString(R.string.place_details, LatLng));
        return Html.fromHtml(res.getString(R.string.place_details, LatLng));

    }

}