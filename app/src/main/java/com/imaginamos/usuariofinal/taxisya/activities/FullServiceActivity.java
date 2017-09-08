package com.imaginamos.usuariofinal.taxisya.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.imaginamos.usuariofinal.taxisya.R;
import com.imaginamos.usuariofinal.taxisya.adapter.BDAdapter;
import com.imaginamos.usuariofinal.taxisya.comm.Connectivity;
import com.imaginamos.usuariofinal.taxisya.comm.MiddleConnect;
import com.imaginamos.usuariofinal.taxisya.comm.NetworkChangeReceiver;
import com.imaginamos.usuariofinal.taxisya.comm.NewAddressActivity;
import com.imaginamos.usuariofinal.taxisya.comm.Preferencias;
import com.imaginamos.usuariofinal.taxisya.dialogs.Dialogos;
import com.imaginamos.usuariofinal.taxisya.models.Conf;
import com.imaginamos.usuariofinal.taxisya.models.Error;
import com.imaginamos.usuariofinal.taxisya.models.Target;
import com.imaginamos.usuariofinal.taxisya.utils.Actions;
import com.imaginamos.usuariofinal.taxisya.utils.Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.imaginamos.taxisya.activities.MapaActivitys.isLocationEnabled;
import static com.imaginamos.usuariofinal.taxisya.R.id.ticketValue;

public class FullServiceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, NetworkChangeReceiver.NetworkReceiverListener, Connectivity.ConnectivityQualityCheckListener, OnMapReadyCallback, PlaceSelectionListener {

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
    private TextView textTotal, textSearch, mTextYaFaltaPoco,kind_text;
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
    private MapaActivity.GetPosition gp;
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
    private String[] destination;

    private RadioButton mPayType1, mPayType2, mPayType3;
    private EditText mTicket;
    private boolean isTicketValid = false;
    public String rTicket;
    public String commitUser = "";
    public String autocomplete_fragment = "";
    public String place_details = "";

    private TextView mPlaceDetailsText;
    private TextView mPlaceAttribution;
    private Conf mConf;
    public View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_service);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setSupportActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mPlaceDetailsText = (TextView) findViewById(R.id.place_details);
        mPlaceAttribution = (TextView) findViewById(R.id.place_attribution);

        conf = new Conf(this);
        mHandleLayout = (LinearLayout) findViewById(R.id.handle);
        mNewAddress = (LinearLayout) findViewById(R.id.add_new);
        mLayoutMap = (LinearLayout) findViewById(R.id.layout_map);
        type_content = (LinearLayout) findViewById(R.id.type_content);
        pay_content = (LinearLayout) findViewById(R.id.pay_content);
        imageMapView = (ImageView) findViewById(R.id.image_map);
        imageMapMarker = (ImageView) findViewById(R.id.ivMarker2);
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
                    Log.v("LOG1","mTicket afterTextChanged " + stk);
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

                            Log.v("LOG1","onSuccess response = " + response);
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
                                    Toast.makeText(FullServiceActivity.this, "ticket no valido", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Log.v("checkService", "Problema json" + e.toString());
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                            Log.v("LOG1", "confirmTicket onFailure");
                            String response = new String(responseBody);
                            Log.v("LOG1","failure response = " + response);
                        }

                        @Override
                        public void onFinish() {
                            Log.v("LOG1","confirmTicket onFinish =  ");
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

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);

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
        Typeface tf = Typeface.createFromAsset(FullServiceActivity.this.getResources().getAssets(), fontPath);
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
                mTicket.setVisibility(View.GONE);
                type_content.setVisibility(View.GONE);
                mTicket.getText().toString();
            }
        });

        mPayType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPayType = 2;
                mTicket.setVisibility(View.GONE);
                type_content.setVisibility(View.GONE);
                btnSolicitar.setBackgroundResource(R.drawable.btn_request);
                btnSolicitar.setEnabled(true);
                // TODO: check tipo servicio
                checkCreditCards();
            }
        });

        mPayType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPayType = 3;
                mTicket.setVisibility(View.VISIBLE);
                type_content.setVisibility(View.GONE);
                btnSolicitar.setEnabled(false);
                btnSolicitar.setBackgroundResource(R.drawable.btn_gray);
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
                    Intent in3 = new Intent(FullServiceActivity.this, HomeActivity.class);
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

        //mAddress = String.valueOf(from_lat + from_lng);
        String mTaxi = "";

        registerReceiver(mReceiver, intentFilter);

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + mAddress + "&destinations=" + mTaxi + "&mode=driving&language=fr-FR&avoid=tolls&key=AIzaSyAK5bs-8nB8_exkk5JhYOtpLD83ZRCkmPM";
        new GeoTask(FullServiceActivity.this).execute(url);

    }
    /*****End on create***/

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        //map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), map.getCameraPosition().zoom), 15, null);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latitud = mLastLocation.getLatitude();
            longitud = mLastLocation.getLongitude();
            Log.v("MAP1", "se obtuvo mLastLocation");
        }
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 15));

    }

    private void setListeners() {
        btnSolicitar.setOnClickListener(this);
        btnAsk.setOnClickListener(this);
        //bt_direcc.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
//        btnAddAddress.setOnClickListener(this);

    }

    void hideControls() {

        // solicitando
        textTotal.setVisibility(View.GONE);
        btnCancelar.setVisibility(View.GONE);
        contendorWell.setVisibility(View.GONE);
        //textSearch.setVisibility(View.GONE);
        //kind_text.setVisibility(View.GONE);
        btnAsk.setEnabled(true);

        /*sedan.setVisibility(View.GONE);
        check_sedan.setVisibility(View.GONE);
        hashback.setVisibility(View.GONE);
        check_hashback.setVisibility(View.GONE);
        suv.setVisibility(View.GONE);
        check_suv.setVisibility(View.GONE);*/

        btnSolicitar.setVisibility(View.GONE);
//        bt_direcc.setVisibility(View.VISIBLE);
//        imageHeader.setVisibility(View.VISIBLE);
        direccion_uno.setFocusableInTouchMode(true);
        direccion_uno.setFocusable(false);

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
                Intent i = new Intent(FullServiceActivity.this, PaymentsActivity.class);
                startActivity(i);
            }
        }
        else {
            Intent i = new Intent(FullServiceActivity.this, PaymentsActivity.class);
            startActivity(i);
        }

    }

    private void buildView() {

        btnSolicitar = (Button) findViewById(R.id.btnSolicitar);
        btnAsk = (Button) findViewById(R.id.btnAsk);

        btnAddAddress = (Button) findViewById(R.id.btn_add);
        if(mFromScheduling && getIntent().getStringExtra("FromActivity").equals("Schedule")){
            btnSolicitar.setText(getIntent().getBooleanExtra("PickUp",true) ? getText(R.string.text_pedir_taxi) : getString(R.string.text_pedir_destino));
        }

        direccion_uno = (EditText) findViewById(R.id.direccion_uno);
        bt_direcc = (Button) findViewById(R.id.btn_direcc);
        bg_shadow = (ImageView) findViewById(R.id.bg_shadows);

        mNoConnectivityPanel = (RelativeLayout) findViewById(R.id.layout_no_connectivity);
        mConnectivityLoaderImage = (ImageView) findViewById(R.id.loader_icon);

        myAdapter = ArrayAdapter.createFromResource(this, R.array.opciones, android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        EditText SearchEditText = (EditText) findViewById(R.id.direccion_uno);
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

        direccion_uno.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                isKeyboardShown(direccion_uno.getRootView());
            }
        });

        direccion_uno.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                direccion_uno.setFocusableInTouchMode(true);
                direccion_uno.setFocusable(true);
                btnAsk.setVisibility(View.GONE);
                btnSolicitar.setVisibility(View.VISIBLE);
                pay_content.setVisibility(View.GONE);
                btnSolicitar.setEnabled(true);
                btnSolicitar.setBackgroundResource(R.drawable.btn_request);
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
                    imageMapView.setVisibility(View.GONE);
                    imageMapMarker.setVisibility(View.VISIBLE);
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
                imageMapView.setVisibility(View.GONE);
                imageMapMarker.setVisibility(View.GONE);
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

    public void prepareForRequestService() {

        pay_content.setVisibility(View.VISIBLE);
        //kind_text.setVisibility(View.VISIBLE);
        btnSolicitar.setEnabled(true);
        btnSolicitar.setBackgroundResource(R.drawable.btn_request);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mapa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            mPref.setRootActivity("MapaActivity");
            Intent in = new Intent(FullServiceActivity.this, PerfilActivity.class);
            startActivity(in);
        }
        else if (id == R.id.nav_gallery) {
            Toast.makeText(FullServiceActivity.this, "Agendamiento", Toast.LENGTH_SHORT).show();
            Intent intmenu = new Intent(FullServiceActivity.this, AgendarActivity.class);
            startActivity(intmenu);
        }
        else if (id == R.id.nav_slideshow) {
            Toast.makeText(FullServiceActivity.this, "Reclamos", Toast.LENGTH_SHORT).show();
            Intent intmenu = new Intent(FullServiceActivity.this, HistorialActivity.class);
            startActivity(intmenu);
        }
        else if (id == R.id.nav_address) {
            Toast.makeText(FullServiceActivity.this, "Mis direcciones", Toast.LENGTH_SHORT).show();
            Intent intmenu = new Intent(FullServiceActivity.this, MyAddressesActivity.class);
            startActivity(intmenu);
        }
        else if (id == R.id.nav_share) {

            AlertDialog.Builder builder = new AlertDialog.Builder(FullServiceActivity.this);
            builder.setTitle(R.string.titulo_cierre_sesion);
            builder.setMessage(R.string.confirmacion_seguro_cierra_sesion);

            builder.setPositiveButton(R.string.text_btn_si, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    // call servicio log
                    String usr = mConf.getUser();
                    String pwd = mConf.getPass();

                    MiddleConnect.logout(FullServiceActivity.this, usr, pwd, new AsyncHttpResponseHandler() {

                        @Override
                        public void onStart() {
                            pDialog = new ProgressDialog(FullServiceActivity.this);
                            pDialog.setMessage(getString(R.string.mensaje_cerrando_sesion));
                            pDialog.setIndeterminate(false);
                            pDialog.setCancelable(false);
                            pDialog.show();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String response = new String(responseBody);
                            try {

                                JSONObject responsejson = new JSONObject(response);

                                Log.v("logout", "response = " + response);
                                Log.v("logout", "response json = " + responsejson.toString());

                                if ((responsejson.getInt("error") == 0) || (responsejson.getInt("error") == 1)) {
                                    mConf.setLogin(false);

                                    pDialog.dismiss();

                                    Intent in3 = new Intent(FullServiceActivity.this, HomeActivity.class);
                                    in3.putExtra("target", 0);
                                    startActivity(in3);
                                    finish();

                                    Toast.makeText(getApplicationContext(), R.string.mensaje_session_cerrada_con_exito, Toast.LENGTH_LONG).show();
                                    // goToActivity(target_option);
                                } else {
                                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                    vibrator.vibrate(200);
                                    Toast.makeText(getApplicationContext(), R.string.mensaje_erro_cerrando_sesion, Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                err_close_session();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            String response = new String(responseBody);
                            Log.v("logout", "failure response = " + response);
                            err_close_session();
                        }

                        @Override
                        public void onFinish() {
                            try {
                                pDialog.dismiss();
                                System.exit(0);
                                finish();
                            } catch (Exception e) {
                            }
                        }
                    });
                }

            });
            builder.setNegativeButton(R.string.text_btn_no,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builder.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void err_close_session() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.close_session_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        String new_origin = direccion_uno.getText().toString();

        String origin = "Origen: " + new_origin;
        String destiny = "Destino: " + "Sin Asignar";
        String pay_type = "Tipo de pago: " + "Efectivo";
        //String travel_destiny = "Distancia a destino: " + destination;
        //String kind_car = "Tipo de Vehículo: " + destination;
        //String value_aprox = "Valor aproximado: " + destination;

        switch (v.getId()) {

            case R.id.btnSolicitar:

                btnAsk.setVisibility(View.VISIBLE);
                btnSolicitar.setVisibility(View.GONE);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("¿Desea Solicitar el servicio con los siguientes datos?");
                alertDialog.setMessage(origin +"\n" + destiny + "\n"+ pay_type +"\n"+ "Si desea obtener más información, modifica el servicio");
                alertDialog.setPositiveButton("Solicitar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        if (verificarDireccion(direccion_uno.getText().toString())) {
                            if (mFromScheduling) {
                                Intent intent = new Intent(FullServiceActivity.this, AgendarActivity.class);
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
                                map.getUiSettings().setAllGesturesEnabled(false);
                                screenShotMap(1);
                                getTaxi();
                            }
                        }
                    }
                });

                alertDialog.setNegativeButton("Modificar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which ) {

                        Intent in3 = new Intent(FullServiceActivity.this,FullServiceActivity.class);
                        startActivity(in3);
                    }
                });

                alertDialog.show();

                break;

            case R.id.btn_cancelar:
                showDialogCancel();
                break;

            case R.id.btn_direcc:
                myAddress();
                break;

            case R.id.btn_volver:
                if (isNewAddress) {
                    mNewAddress.setVisibility(View.GONE);
                    mHandleLayout.setVisibility(View.VISIBLE);
                } else {

                    if (isRequestService) {
                        showDialogCancel();

                    } else {
                        finish();
                    }
                }
                break;

            case R.id.bg_shadows:
                updateAddress();
                break;

            case R.id.btnAsk:

                btnAsk.setVisibility(View.GONE);
                btnSolicitar.setVisibility(View.VISIBLE);
                btnSolicitar.setEnabled(true);
                btnSolicitar.setBackgroundResource(R.drawable.btn_request);
                pay_content.setVisibility(View.VISIBLE);
                direccion_uno.setFocusableInTouchMode(true);
                direccion_uno.setFocusable(true);
                direccion_uno.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_HIDDEN);

                break;

            case R.id.btn_add:

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

                                saveImage(getApplicationContext(), bitmap, "foto", "png");
                                String strLatitud = String.valueOf(latitud);
                                String strLongitud = String.valueOf(longitud);
                                mPref.setRootActivity("MapaActivitys");
                                Intent i2 = new Intent(FullServiceActivity.this, NewAddressActivity.class);
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

    private void myAddress() {

        mPref.setRootActivity("MapaActivitys");

        if (conf.getLogin()) {
            Intent in2 = new Intent(FullServiceActivity.this, MyAddressesActivity.class);
            startActivityForResult(in2, 1);
        } else {
            Intent i = new Intent(this, RegistroActivity.class);
            i.putExtra("target", Target.ADDRESS_TARGET);
            startActivityForResult(i, 10);
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

    private void cancelarService() {
        cancelService(getResources().getString(R.string.cancel_service, id_user));
    }

    public void cancelService(final String Url) {

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

        url_cancel_current = Url;
        this.sendBroadcast(new Intent("com.google.android.intent.action.GTALK_HEARTBEAT"));
        this.sendBroadcast(new Intent("com.google.android.intent.action.MCS_HEARTBEAT"));
        MiddleConnect.cancelService(Url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

                try {
                    if (!pDialog.isShowing()) {
                        pDialog = new ProgressDialog(FullServiceActivity.this);
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
                try {
                    Log.e("solicitando", "SUCCES: " + response);

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

                            finish();
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

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    private void moveToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

    }

    public boolean verificarDireccion(String addr) {
        // verificar si la dirección finaliza con -

        if (addr.charAt(addr.length() - 1) == '-') return false;
        return true;

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("LOG", "onConnected");
        Log.v("SEGUIMIENTO", "onConnected -- ");
///		mLocationClient.requestLocationUpdates(mLocationRequest, this);
        displayLocation();
        startLocationUpdates();

    }

    void showControls() {

        btnSolicitar.setVisibility(View.GONE);
        pay_content.setVisibility(View.GONE);
        //bt_direcc.setVisibility(View.GONE);
        //imageHeader.setVisibility(View.GONE);
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

    private void displayLocation() {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        Log.v("SEGUIMIENTO", "displayLocation -- ");

        if (mLastLocation != null) {
            latitud = mLastLocation.getLatitude();
            longitud = mLastLocation.getLongitude();
            Log.v("SEGUIMIENTO", "displayLocation ok lat=" + String.valueOf(latitud) + " lng=" + String.valueOf(longitud));

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
                    btnSolicitar.setEnabled(true);
                    id_user = conf.getIdUser();
                    uuid = conf.getUuid();
                    getService();
                } else {
                    mPref.setRootActivity("MapaActivitys");

                    Intent i = new Intent(FullServiceActivity.this, RegistroActivity.class);

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

                AlertDialog.Builder builder = new AlertDialog.Builder(FullServiceActivity.this);
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
            new Dialogos(FullServiceActivity.this, R.string.error_net);
            btnSolicitar.setEnabled(true);
            hideLoader();
        }

    }


    private void getService() {

        String strLatitud = String.valueOf(latitud);
        String strLongitud = String.valueOf(longitud);
        Log.i("ADDRESS ON SERVICE","Lat: "+latitud+" Long: "+longitud);

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
        }
        else if (mPayType == 2) {
            payType = "2";
            mCardReference = conf.getCardDefault();
            if (mCardReference == null) mCardReference = "0";
        }
        else {
            payType = "3";
            mCardReference = mTicket.getText().toString();
        }

        String strPhone = conf.getPhone();
        String strCode = strPhone.length() > 2 ? strPhone.substring(strPhone.length() - 2) : strPhone;
        String commit = commitUser;
        String old_destination = mPlaceDetailsText.getText().toString();
        String destination = old_destination.length() > 20 ? old_destination.substring(old_destination.length() - 10) : old_destination;

        Log.v("CODE1","strCode " + strCode);
        MiddleConnect.getServiceAddress(this, id_user, strLatitud, strLongitud, address, barrio,  uuid,  payType, "", userEmail,  mCardReference, strCode, commit, destination, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                pDialog = new ProgressDialog(FullServiceActivity.this);
                pDialog.setMessage("Solicitando Servicio....");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                //pDialog.show();
                Log.v("SOLICITANDO_SERVICIO", "onStart " + String.valueOf(new Date()));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.v("solicitando", "SUCCES: " + response);
                try {
                    Log.e("solicitando", "SUCCES: " + response);
                    Log.v("SOLICITANDO_SERVICIO", "onSucces " + String.valueOf(new Date()));
                    JSONObject responsejson = new JSONObject(response);

                    if (myTimer == null) {
                        Log.v("CNF_SRV", "myTimer is null");
                        myTimer = new Timer();
                    }

                    if (responsejson.getBoolean("success")) {

                        mServiceId = responsejson.getString("service_id");
                        conf.setServiceId(mServiceId);
                        mStatusNew = responsejson.getInt("status_id");
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
                                    // TODO Auto-generated catch block
                                    Log.v("CHECK1" ,"checkService exception " + e.toString());
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
                        if (responsejson.getInt("error") == Error.NO_DRIVER_ENABLE) {

                            Log.v("SOLICITANDO_SERVICIO", "error - Error.NO_DRIVER_ENABLE " + String.valueOf(new Date()));
                            Toast.makeText(getApplicationContext(), getString(R.string.error_no_driver), Toast.LENGTH_LONG).show();

                        } else {
                            Log.v("SOLICITANDO_SERVICIO", "error_request - " + String.valueOf(new Date()));
                            err_request();

                        }

                        finish();
                    }

                } catch (Exception e) {
                    Log.e("error_solicitando", "Problema json" + e.toString());
                    Log.v("SOLICITANDO_SERVICIO", "error - problemas json " + String.valueOf(new Date()));
                    err_request();

                    //isRequestService = false;
                    //finish();
                    hideControls();

                    map.getUiSettings().setScrollGesturesEnabled(true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String response = new String(responseBody);
                Log.e("error_solicitando", "FAILURE" + response);
                Log.v("SOLICITANDO_SERVICIO", "onFailure " + String.valueOf(new Date()));
                err_request();

                isRequestService = false;
                puente.sendEmptyMessage(2000);
            }

            @Override
            public void onFinish() {
                //isRequestService = false;
                try {
                    //pDialog.dismiss();
                } catch (Exception e) {
                }
            }

        });

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

    private void searchByLocation(final Location location) {

        Log.v("SEGUIMIENTO", "searchByLocation -- ini ");

        AsyncTask<Void, Void, List<Address>> task = new AsyncTask<Void, Void,
                List<android.location.Address>>() {

            @Override
            protected List<android.location.Address> doInBackground(Void... params) {
                Geocoder geocoder = new Geocoder(FullServiceActivity.this);
                List<android.location.Address> list = null;
                try {
                    double latitude = location.getLatitude(), longitude = location.getLongitude();
                    latitud = latitude;
                    longitud = longitude;
                    Log.i("ADDRESS UPDATE","Lat: "+latitud+" Long: "+longitude);
                    list = geocoder.getFromLocation(latitude, longitude, 2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return list;
            }

            protected void onPostExecute(List<android.location.Address> result) {
                if (result != null) {
                    if (result.size() > 1) {
                        android.location.Address address = result.get(0);
                        android.location.Address addressSecondary = result.get(1);

                        String userAddress = address.getAddressLine(0);
                        String userBarrio = addressSecondary.getAddressLine(0);

                        Log.i("SEGUIMIENTO", "searchByLocation -- onPostExecute userAddress " + userAddress);
                        Log.i("SEGUIMIENTO", "searchByLocation -- onPostExecute userBarrio " + userBarrio);
//                        longitud = address.getLongitude();
//                        latitud = address.getLatitude();

                        // se agrego para enviar
                        if (address != null && address.getAddressLine(0) != null && !address.getAddressLine(0).equals("")) {
                            //barrio = address.getAddressLine(0).split(",")[0];
                            barrio = userBarrio;
                            Log.v("SEGUIMIENTO", "searchByLocation -- onPostExecute barrio " + barrio);
                            //dir_barrio.setText(barrio);
                        }

                        int index = userAddress.indexOf("-");
                        if (index != -1) {
                            userAddress = userAddress.substring(0, index + 1);
                        }
                        Log.d("Address", userAddress);
                        Log.v("SEGUIMIENTO", "searchByLocation -- onPostExecute userAddress 2 " + userAddress);

                        Log.v("SEGUIMIENTO", "direccion_uno " + userAddress);
                        Log.i("ADDRESS SELECTED", "Barrio: " + address.getLocality());

                        direccion_uno.setText(userAddress);
                        no = userAddress;
                        direccion_uno.setSelection(userAddress.length());
                        direccion_uno.setEnabled(true);
                        //btnOk.setEnabled(true);

                        if (mGoogleApiClient.isConnected()) {
                            stopLocationUpdates();
                            mGoogleApiClient.disconnect();
                        }
                    }
                }

            }
        };

        task.execute();
    }

    protected void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    protected void startLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("LOG", "onConnectionFailed");
        Log.v("SEGUIMIENTO", "onConnectionFailed -- ");
        Toast.makeText(this, getResources().getString(R.string.enable_gps), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.v("onLocationChanged", "ini");
        if (firstTime) {
            Log.v("onLocationChanged", "firstTime ok");
            latitud = (float) location.getLatitude();
            longitud = (float) location.getLongitude();
            Log.i("GPS_1", "onLocationChanged firstTime " + String.valueOf(latitud)+ " Long "+longitud);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 15));
            firstTime = false;
        }

    }

    @Override
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

                } catch (Exception e) {
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(200);
                    Log.v("CNF_SRV", "no driver 2");
                    Toast.makeText(FullServiceActivity.this, getString(R.string.error_no_driver), Toast.LENGTH_LONG).show();

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

    public void cancelByService(final String Url) {

        service_cancel();
        url_cancel_current = Url;
        MiddleConnect.cancelServiceBySystem(Url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

                try {

                    if (!pDialog.isShowing()) {
                        pDialog = new ProgressDialog(FullServiceActivity.this);
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

    @Override
    public void onError(Status status) {
        Log.e("LOG", "onError: Status = " + status.toString());
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();

    }
    private static Spanned formatPlaceDetails(Resources res, LatLng LatLng) {
        Log.e("LOG", res.getString(R.string.place_details, LatLng));
        return Html.fromHtml(res.getString(R.string.place_details, LatLng));

    }

    @Override
    public void onNetworkConnectivityChange(boolean connected) {
        displayConnectivityPanel(!connected);
    }

    @Override
    public void onConnectivityQualityChecked(boolean Optimal) {

        displayConnectivityPanel(!Optimal);
    }

    private void displayConnectivityPanel(boolean display) {

        mNoConnectivityPanel.setVisibility(display ? View.VISIBLE : View.GONE);
        if(display)
            mConnectivityLoaderImage.startAnimation(AnimationUtils.loadAnimation(this,R.anim.connection_loader));
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

    public void updateAddress() {
        indice = (indice == null) ? "" : indice;
        comp1 = (comp1 == null) ? "" : comp1;
        comp2 = (comp2 == null) ? "" : comp2;
        no = (no == null) ? "" : no;
        barrio = (barrio == null || barrio.equals("")) ? " no especifica " : barrio;
        obs = (obs == null) ? "" : obs;
        String address = indice + " " + comp1 + " # " + comp2 + " - " + no + " - " + barrio + " - " + obs;
        Log.v("SEGUIMIENTO", "updateAddress -- " + address);
    }

    private void scheduleTryAgain() {
        Log.v("CNF_SRV1", "MapaActivitys scheduleTryAgain ");
        //marker.setEnabled(false);
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
                        barrio = address.getAddressLine(0).split(",")[0];
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

    public void setSupportActionBar(Toolbar supportActionBar) {
        //this.supportActionBar = supportActionBar;
    }


    class GetPosition extends AsyncTask<Double, Integer, List<Address>> {

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

                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 18f), 15, null);


            } else {
                Log.v("SEGUIMIENTO", "onPostExecute result PAILAS");
                Log.e("PAILAS", "PIALAS");
                Log.e("PAILAS", "reintent " + String.valueOf(reintent));
                if (reintent > 0) {
                    scheduleTryAgain();
                    reintent--;
                } else {
                    Log.e("PAILAS", "- reintent " + String.valueOf(reintent));
                    //direccion_completar_uno.setText("Por favor, verifique la configuración de internett...");
                    //activeRequest = true;
                }
            }
            activeRequest = false;
            Log.e("FIN", "FIN");
        }

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
                            gp = new MapaActivity.GetPosition();
                            gp.execute(latitud, longitud);
                        }

                    } else {
                        //direccion_completar_uno.setText("Buscando dirección en "+ count + " segundos..");
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

    public void err_request() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        Toast.makeText(getApplicationContext(), getString(R.string.error_no_procc), Toast.LENGTH_SHORT).show();
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

    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }

    }

    private void hideLoader() {
        try {
            pd.dismiss();
        } catch (Exception e) {
        }
    }


    public void setDouble(String result) {

        String res[]=result.split(",");
        Double min=Double.parseDouble(res[0])/60;
        int dist=Integer.parseInt(res[1])/1000;
        //time_arrive.setText(" " + (int) (min / 60) + " hr " + (int) (min % 60) + " minutos");

    }

}
