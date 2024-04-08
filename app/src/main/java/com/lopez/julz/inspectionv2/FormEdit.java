package com.lopez.julz.inspectionv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lopez.julz.inspectionv2.classes.MastPoleAdapters;
import com.lopez.julz.inspectionv2.database.AppDatabase;
import com.lopez.julz.inspectionv2.database.Blocks;
import com.lopez.julz.inspectionv2.database.LocalServiceConnectionInspections;
import com.lopez.julz.inspectionv2.database.LocalServiceConnections;
import com.lopez.julz.inspectionv2.database.MastPoles;
import com.lopez.julz.inspectionv2.database.Materials;
import com.lopez.julz.inspectionv2.database.PayTransactions;
import com.lopez.julz.inspectionv2.database.Photos;
import com.lopez.julz.inspectionv2.database.ServiceConnectionInspectionsDao;
import com.lopez.julz.inspectionv2.database.ServiceConnectionsDao;
import com.lopez.julz.inspectionv2.database.TotalPayments;
import com.lopez.julz.inspectionv2.database.Zones;
import com.lopez.julz.inspectionv2.helpers.AlertHelpers;
import com.lopez.julz.inspectionv2.helpers.ObjectHelpers;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FormEdit extends AppCompatActivity implements PermissionsListener, OnMapReadyCallback {

    public Toolbar form_toolbar;

    public String svcId;

    public AppDatabase db;

    public MaterialButton minimize, minimize_materials;
    public LinearLayout form_hidable_svc_details;
    public MaterialCardView form_svc_details;
    public TextView form_name, form_address, form_svc_id, form_contact, form_title, svcDropLength, form_classification;

    public EditText formBreakerRatingInstalled, formBreakerBranchesInstalled, formRate;
    public TextView formSdwSizeInstalled, formSdwLengthInstalled;

    public EditText formLiftPolesDiameterGI, formLiftPolesDiameterConcrete, formLiftPolesDiameterHardWood;
    public EditText formLiftPolesHeightGI, formLiftPolesHeightConcrete, formLiftPolesHeightHardWood;
    public EditText formLiftPolesQuantityGI, formLiftPolesQuantityConcrete, formLiftPolesQuantityHardWood, formLiftPolesRemarks, formPoleNo, formBillDeposit;

    // boheco 1
    public EditText neighbor1, neighbor1meter, neighbor2, neighbor2meter;

    public LocalServiceConnections serviceConnections;
    public LocalServiceConnectionInspections serviceConnectionInspections;

    public EditText formGeoTappingPole, formGeoMeteringPole, formSEPole, formGeoBuilding;
    public ImageButton formGeoTappingPoleBtn, formGeoMeteringPoleBtn, formGeoSEPoleBtn, formGeoBuildingBtn;

    public EditText formReverificationRemarks;
    public RadioGroup formRecommendation;

    public FloatingActionButton saveBtn, cameraBtn;

    // MAP
    public MapView mapView;
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private LocationComponent locationComponent;
    public Style style;

    static final int REQUEST_PICTURE_CAPTURE = 1;
    public FlexboxLayout imageFields;
    public String currentPhotoPath;

    // MAST POLES
    public MaterialButton addMastPole;
    public List<MastPoles> mastPoles;
    public RecyclerView mastPolesRecyclerview;
    public MastPoleAdapters mastPoleAdapters;
    public String LINE_SOURCE_ID = "linesource";
    public String LINE_LAYER_ID = "linelayer";
    public Double distance = 0.0, geoPointsDistance = 0.0;
    public boolean firstInit = true;

    // DEPOSITS
    public PayTransactions payTransactions;
    public TotalPayments totalPayments;

    // Load profile and line
    public EditText formNumberOfLo, formNumberOfCo, formMotor, formTotalLoad, formContractedDemand, formContractedEnergy, formDistanceFromSecondary, formSizeOfSecondary, formFeeder;
    public Spinner formZone, formBlock, formAccountType;

    // SDW
    public RadioGroup formSdwType, formSdwHeight, formLoadType;

    // TRANSFORMER
    public EditText formDistanceFromTransformer, formSizeOfTransformer, formTransformerNumber;

    // CONFIRMATIONS
    public RadioGroup formLinePassingPrivateProperty, formWrittenConsentByPropertyOwner, formObstructionOfLines, formLinePassingRoads;

    public ConstraintLayout photoArea;

    public List<Zones> zonesList;
    public List<Blocks> blocksList;
    ArrayAdapter<String> zonesAdapter;
    ArrayAdapter<String> blocksAdapter;
    ArrayAdapter<String> meteringTypesAdapter;

    public ListView filesList;

    public MaterialButton clearAssessment;

    public EditText materials_meterBaseSocket, materials_metalboxTypeA, materials_metalboxTypeB, materials_pipe, materials_entranceCap, materials_adapter, materials_locknot, materials_mailbox, materials_buckle, materials_pvcElbow, materials_stainlessStrap, materials_plyboard, materials_strainInsulator, materials_straindedWire8, materials_strandedWire6, materials_twistedWire6, materials_twistedWire4;
    public EditText materials_compressionTapAsu, materials_compressionYtdTwoFifty, materials_compressionTapYtdThreeHundred, materials_compressionTapYtdTwoHundred, materials_compressionTapYtdOneFifty, materials_metalScrew, materials_electricalTape;

    public Materials materials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_form_edit);

        zonesList = new ArrayList<>();
        blocksList = new ArrayList<>();

        form_toolbar = (Toolbar) findViewById(R.id.form_toolbar);
        svcId = getIntent().getExtras().getString("SVCID");

        setSupportActionBar(form_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24);

        db = Room.databaseBuilder(this,
                AppDatabase.class, ObjectHelpers.databaseName()).fallbackToDestructiveMigration().build();

        mapView = (MapView) findViewById(R.id.mapViewForm);

        // mapview
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        minimize = (MaterialButton) findViewById(R.id.minimize);
        form_hidable_svc_details = (LinearLayout) findViewById(R.id.form_hidable_svc_details);
        form_svc_details = (MaterialCardView) findViewById(R.id.form_svc_details);
        form_name = (TextView) findViewById(R.id.form_name);
        form_address = (TextView) findViewById(R.id.form_address);
        form_svc_id = (TextView) findViewById(R.id.form_svc_id);
        form_contact = (TextView) findViewById(R.id.form_contact);
        form_classification = (TextView) findViewById(R.id.form_classification);
        form_title = findViewById(R.id.form_title);
        formRate = findViewById(R.id.formRate);
        formBillDeposit = findViewById(R.id.formBillDeposit);
        filesList = findViewById(R.id.filesList);

        formBreakerRatingInstalled = (EditText) findViewById(R.id.formBreakerRatingInstalled);
        formBreakerBranchesInstalled = (EditText) findViewById(R.id.formBreakerBranchesInstalled);

        formSdwSizeInstalled = (TextView) findViewById(R.id.formSdwSizeInstalled);
        formSdwLengthInstalled = (TextView) findViewById(R.id.formSdwLengthInstalled);

        formGeoTappingPole = (EditText) findViewById(R.id.formGeoTappingPole);
        formGeoMeteringPole = (EditText) findViewById(R.id.formGeoMeteringPole);
        formSEPole = (EditText) findViewById(R.id.formSEPole);
        formGeoBuilding = (EditText) findViewById(R.id.formGeoBuilding);
        clearAssessment = findViewById(R.id.clearAssessment);

        //SL Poles Inits
        formLiftPolesDiameterGI = (EditText) findViewById(R.id.formLiftPolesDiameterGI);
        formLiftPolesDiameterConcrete = (EditText) findViewById(R.id.formLiftPolesDiameterConcrete);
        formLiftPolesDiameterHardWood = (EditText) findViewById(R.id.formLiftPolesDiameterHardWood);
        formLiftPolesHeightGI = (EditText) findViewById(R.id.formLiftPolesHeightGI);
        formLiftPolesHeightConcrete = (EditText) findViewById(R.id.formLiftPolesHeightConcrete);
        formLiftPolesHeightHardWood = (EditText) findViewById(R.id.formLiftPolesHeightHardWood);
        formLiftPolesQuantityGI = (EditText) findViewById(R.id.formLiftPolesQuantityGI);
        formLiftPolesQuantityConcrete = (EditText) findViewById(R.id.formLiftPolesQuantityConcrete);
        formLiftPolesQuantityHardWood = (EditText) findViewById(R.id.formLiftPolesQuantityHardWood);
        formLiftPolesRemarks = (EditText) findViewById(R.id.formLiftPolesRemarks);
        formPoleNo = findViewById(R.id.formPoleNo);
        formAccountType = findViewById(R.id.formAccountType);

        formRecommendation = (RadioGroup) findViewById(R.id.formRecommendation);
        formReverificationRemarks = (EditText) findViewById(R.id.formReverificationRemarks);

        // BUTTONS FOR THE GEO LOCS
        formGeoTappingPoleBtn = (ImageButton) findViewById(R.id.formGeoTappingPoleBtn);
        formGeoBuildingBtn = (ImageButton) findViewById(R.id.formGeoBuildingBtn);
        formGeoMeteringPoleBtn = (ImageButton) findViewById(R.id.formGeoMeteringPoleBtn);
        formGeoSEPoleBtn = (ImageButton) findViewById(R.id.formGeoSEPoleBtn);

        // mast poles
        addMastPole = findViewById(R.id.addMastPole);
        svcDropLength = findViewById(R.id.svcDropLength);
        mastPoles = new ArrayList<>();
        mastPolesRecyclerview = findViewById(R.id.mastPolesRecyclerview);
        mastPoleAdapters = new MastPoleAdapters(mastPoles, this, db);
        mastPolesRecyclerview.setAdapter(mastPoleAdapters);
        mastPolesRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        /**
         * BOHECO I
         */
        neighbor1 = findViewById(R.id.neighbor1);
        neighbor1meter = findViewById(R.id.neighbor1meter);
        neighbor2 = findViewById(R.id.neighbor2);
        neighbor2meter = findViewById(R.id.neighbor2meter);

        // added
//        billDeposit = findViewById(R.id.billDeposit);

        /**
         * LOAD PROFILING
         */
        formNumberOfLo = findViewById(R.id.formNumberOfLo);
        formNumberOfCo = findViewById(R.id.formNumberOfCo);
        formMotor = findViewById(R.id.formMotor);
        formTotalLoad = findViewById(R.id.formTotalLoad);
        formContractedDemand = findViewById(R.id.formContractedDemand);
        formContractedEnergy = findViewById(R.id.formContractedEnergy);
        formDistanceFromSecondary = findViewById(R.id.formDistanceFromSecondary);
        formSizeOfSecondary = findViewById(R.id.formSizeOfSecondary);
        formLoadType = findViewById(R.id.formLoadType);
        formZone = findViewById(R.id.formZone);
        formBlock = findViewById(R.id.formBlock);
        formFeeder = findViewById(R.id.formFeeder);
        minimize_materials = findViewById(R.id.minimize_materials);

        /**
         * MATERIALS
         */
        materials_meterBaseSocket = findViewById(R.id.materials_meterBaseSocket);
        materials_metalboxTypeA = findViewById(R.id.materials_metalboxTypeA);
        materials_metalboxTypeB = findViewById(R.id.materials_metalboxTypeB);
        materials_pipe = findViewById(R.id.materials_pipe);
        materials_entranceCap = findViewById(R.id.materials_entranceCap);
        materials_adapter = findViewById(R.id.materials_adapter);
        materials_locknot = findViewById(R.id.materials_locknot);
        materials_mailbox = findViewById(R.id.materials_mailbox);
        materials_buckle = findViewById(R.id.materials_buckle);
        materials_pvcElbow = findViewById(R.id.materials_pvcElbow);
        materials_stainlessStrap = findViewById(R.id.materials_stainlessStrap);
        materials_plyboard = findViewById(R.id.materials_plyboard);
        materials_strainInsulator = findViewById(R.id.materials_strainInsulator);
        materials_straindedWire8 = findViewById(R.id.materials_straindedWire8);
        materials_strandedWire6 = findViewById(R.id.materials_strandedWire6);
        materials_twistedWire6 = findViewById(R.id.materials_twistedWire6);
        materials_twistedWire4 = findViewById(R.id.materials_twistedWire4);
        materials_compressionTapAsu = findViewById(R.id.materials_compressionTapAsu);
        materials_compressionYtdTwoFifty = findViewById(R.id.materials_compressionYtdTwoFifty);
        materials_compressionTapYtdThreeHundred = findViewById(R.id.materials_compressionTapYtdThreeHundred);
        materials_compressionTapYtdTwoHundred = findViewById(R.id.materials_compressionTapYtdTwoHundred);
        materials_compressionTapYtdOneFifty = findViewById(R.id.materials_compressionTapYtdOneFifty);
        materials_metalScrew = findViewById(R.id.materials_metalScrew);
        materials_electricalTape = findViewById(R.id.materials_electricalTape);


        /**
         * SDW
         */
        formSdwType = findViewById(R.id.formSdwType);
        formSdwHeight = findViewById(R.id.formSdwHeight);

        /**
         * TRANSFORMER
         */
        formDistanceFromTransformer = findViewById(R.id.formDistanceFromTransformer);
        formSizeOfTransformer = findViewById(R.id.formSizeOfTransformer);
        formTransformerNumber = findViewById(R.id.formTransformerNumber);

        /**
         * CONFIRMATIONS
         */
        formLinePassingPrivateProperty = findViewById(R.id.formLinePassingPrivateProperty);
        formWrittenConsentByPropertyOwner = findViewById(R.id.formWrittenConsentByPropertyOwner);
        formObstructionOfLines = findViewById(R.id.formObstructionOfLines);
        formLinePassingRoads = findViewById(R.id.formLinePassingRoads);


        cameraBtn = findViewById(R.id.cameraBtn);
        imageFields = findViewById(R.id.imageFields);

        saveBtn = (FloatingActionButton) findViewById(R.id.saveBtn);

        photoArea = findViewById(R.id.photoArea);
//        photoArea.setVisibility(View.GONE);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateInspectionData().execute();
            }
        });

        new GetZonesAndBlocks().execute();
        new FetchServiceConnectionData().execute(svcId);
        new FetchServiceConnectionInspectionData().execute(svcId);

        minimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (form_hidable_svc_details.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(form_svc_details,
                            new AutoTransition());
                    form_hidable_svc_details.setVisibility(View.GONE);
                    minimize.setIcon(getDrawable(R.drawable.ic_round_add_24));
                } else {
                    TransitionManager.beginDelayedTransition(form_svc_details,
                            new AutoTransition());
                    form_hidable_svc_details.setVisibility(View.VISIBLE);
                    minimize.setIcon(getDrawable(R.drawable.ic_round_minimize_24));
                }
            }
        });

        minimize_materials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        formGeoTappingPoleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchCoordinates(formGeoTappingPole, "tw-provincial-2");
                new FetchMastPoles().execute(svcId);
            }
        });

        formGeoMeteringPoleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchCoordinates(formGeoMeteringPole, "hu-main-2");
                new FetchMastPoles().execute(svcId);
            }
        });

        formGeoSEPoleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchCoordinates(formSEPole, "tw-provincial-expy-2");
                new FetchMastPoles().execute(svcId);
            }
        });

        formGeoBuildingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchCoordinates(formGeoBuilding, "in-national-3");
                new FetchMastPoles().execute(svcId);
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        addMastPole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormEdit.this);

                    EditText mastPoleRemarks = new EditText(FormEdit.this);
                    mastPoleRemarks.setHint("Pole Name or Pole Description");
                    mastPoleRemarks.setText("Lift Pole # " + (mastPoles.size()+1));

                    builder.setTitle("Add Lift Pole Details")
                            .setView(mastPoleRemarks)
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new AddMastPole().execute(mastPoleRemarks.getText().toString(), svcId);
                                    firstInit = false;
                                }
                            });

                    AlertDialog dialog = builder.create();

                    dialog.show();
                } catch (Exception e) {
                    Log.e("ERR_ADD_MST_PL", e.getMessage());
                }
            }
        });

        formTotalLoad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // CONTRACTED ENERGY
                if (formRate.getText() != null && formRate.getText().toString().length() > 0 && s != null && s.length() > 0) {
                    String load = s.toString();
                    if (load.substring(0,1).equals(".")) {
                        load = "0" + load;
                    }
                    formContractedEnergy.setText(ObjectHelpers.getContractedEnergy(Double.valueOf(load), Double.valueOf(formRate.getText().toString())));
                    formBillDeposit.setText("" + getBillDeposit(serviceConnections.getAccountType(), Double.valueOf(s.toString()), Double.valueOf(formRate.getText().toString())));
                } else {
                    formContractedEnergy.setText("0");
                    formBillDeposit.setText("0");
                }

                // CONTRACTED DEMAND
                if (s != null && s.length() > 0) {
                    String load = s.toString();
                    if (load.substring(0,1).equals(".")) {
                        load = "0" + load;
                    }
                    formContractedDemand.setText(ObjectHelpers.getContractedDemand(Double.valueOf(load)));
                } else {
                    formContractedDemand.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        filesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String info = ( (TextView) arg1 ).getText().toString();
//                Toast.makeText(FormEdit.this, info, Toast.LENGTH_SHORT).show();

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/" + serviceConnections.getId() + "/" + info);

                String extension = "";
                int lastDotIndex = info.lastIndexOf('.');
                if (lastDotIndex > 0) {
                    extension = info.substring(lastDotIndex + 1);
                }

                if (extension != null && extension.equals("pdf")) {
                    Intent intent = new Intent(FormEdit.this, PdfViewerActivity.class);
                    intent.putExtra("PDF_PATH", info);
                    intent.putExtra("SC_ID", serviceConnections.getId());
                    startActivity(intent);
                } else if (extension != null && (extension.equals("jpeg") | extension.equals("jpg") | extension.equals("png") | extension.equals("webp"))) {
                    Intent intent = new Intent(FormEdit.this, ImageViewerActivity.class);
                    intent.putExtra("IMG_PATH", info);
                    intent.putExtra("SC_ID", serviceConnections.getId());
                    startActivity(intent);
                } else {
                    Toast.makeText(FormEdit.this, "No application can open this file", Toast.LENGTH_SHORT).show();
                }

            }
        });

        clearAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formRecommendation.check(-1);
            }
        });

        new GetPhotos().execute();
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        try {
            this.mapboxMap = mapboxMap;
            mapboxMap.setStyle(new Style.Builder()
                    .fromUri("mapbox://styles/julzlopez/ckahntemo048l1il7edks77wb"), new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    setStyle(style);

                    enableLocationComponent(style);

                    new FetchMastPoles().execute(svcId);
                }
            });
        } catch (Exception e) {
            Log.e("ERR_INIT_MAPBOX", e.getMessage());
        }
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        try {
            // Check if permissions are enabled and if not request
            if (PermissionsManager.areLocationPermissionsGranted(this)) {

                // Get an instance of the component
                locationComponent = mapboxMap.getLocationComponent();

                // Activate with options
                locationComponent.activateLocationComponent(
                        LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

                // Enable to make component visible
                locationComponent.setLocationComponentEnabled(true);

                // Set the component's camera mode
                locationComponent.setCameraMode(CameraMode.TRACKING);

                // Set the component's render mode
                locationComponent.setRenderMode(RenderMode.COMPASS);

            } else {
                permissionsManager = new PermissionsManager(this);
                permissionsManager.requestLocationPermissions(this);
            }
        } catch (Exception e) {
            Log.e("ERR_LOAD_MAP", e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICTURE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new  File(currentPhotoPath);
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getPath());
            Bitmap scaledBmp = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/8, bitmap.getHeight()/8, true);

            ImageView imageView = new ImageView(FormEdit.this);
            Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(scaledBmp.getWidth(), scaledBmp.getHeight());
            imageView.setLayoutParams(layoutParams);
            imageView.setPadding(0, 5, 5, 0);
            if (imgFile.exists()) {
                imageView.setImageBitmap(scaledBmp);
            }
            imageFields.addView(imageView);

            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popup = new PopupMenu(FormEdit.this,imageView);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.image_menu);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete_img:
                                    if (imgFile.exists()) {
                                        imgFile.delete();
                                        new GetPhotos().execute();
                                    }
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    //displaying the popup
                    popup.show();
                    return false;
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    public void fetchCoordinates(EditText destination, String iconImage) {
        if (locationComponent == null) {
            Toast.makeText(FormEdit.this, "Location service unavailable. Switch to manual mode.", Toast.LENGTH_LONG).show();
        } else {
            if (null != locationComponent.getLastKnownLocation()) {
                destination.setText(locationComponent.getLastKnownLocation().getLatitude() + "," + locationComponent.getLastKnownLocation().getLongitude());
                try {
                    addMarkers(style, new LatLng(locationComponent.getLastKnownLocation().getLatitude(), locationComponent.getLastKnownLocation().getLongitude()), iconImage);
                } catch (Exception e) {
                    Log.e("ERR_PLOT_MARKER", e.getMessage());
                    Toast.makeText(FormEdit.this, "Unable to plot marker\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    class FetchServiceConnectionData extends AsyncTask<String, Void, Void> {

        private String barangay, town;

        @Override
        protected Void doInBackground(String... strings) {
            try {
                ServiceConnectionsDao serviceConnectionsDao = db.serviceConnectionsDao();
                serviceConnections = serviceConnectionsDao.getOne(strings[0]);

                barangay = db.barangaysDao().getOne(serviceConnections.getBarangay()).getBarangay();
                town = db.townsDao().getOne(serviceConnections.getTown()).getTown();

                // DEPOSITS
                totalPayments = db.totalPaymentsDao().getOneByServiceConnectionId(strings[0]);
                payTransactions = db.payTransactionsDao().getOneByServiceConnectionId(strings[0]);

                // material presets
                materials = db.materialsDao().getOneByServiceConnectionId(strings[0]);
            } catch (Exception e) {
                Log.e("ERR_GET_SVC_FORM", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            try {
                if (serviceConnections == null) {
                    AlertHelpers.infoDialog(FormEdit.this, "Not Found (404)", "Service connection data not found!");
                } else {
                    form_name.setText(serviceConnections.getServiceAccountName());
                    form_svc_id.setText(serviceConnections.getId());
                    form_address.setText(serviceConnections.getSitio() + ", " + barangay + ", " + town);
                    form_contact.setText(serviceConnections.getContactNumber());
                    form_classification.setText(serviceConnections.getAccountType());
                    form_title.setText(serviceConnections.getAccountApplicationType());

                    getFiles();
                }

                if (materials != null) {
                    materials_meterBaseSocket.setText(materials.getMeterBaseSocket());
                    materials_metalboxTypeA.setText(materials.getMetalboxTypeA());
                    materials_metalboxTypeB.setText(materials.getMetalboxTypeB());
                    materials_pipe.setText(materials.getPipe());
                    materials_entranceCap.setText(materials.getEntranceCap());
                    materials_adapter.setText(materials.getAdapter());
                    materials_locknot.setText(materials.getLocknot());
                    materials_mailbox.setText(materials.getMailbox());
                    materials_buckle.setText(materials.getBuckle());
                    materials_pvcElbow.setText(materials.getPvcElbow());
                    materials_stainlessStrap.setText(materials.getStainlessStrap());
                    materials_plyboard.setText(materials.getPlyboard());
                    materials_strainInsulator.setText(materials.getStrainInsulator());
                    materials_straindedWire8.setText(materials.getStraindedWireEight());
                    materials_strandedWire6.setText(materials.getStrandedWireSix());
                    materials_twistedWire4.setText(materials.getTwistedWireFour());
                    materials_twistedWire6.setText(materials.getTwistedWireSix());
                    materials_compressionTapAsu.setText(materials.getCompressionTapAsu());
                    materials_compressionYtdTwoFifty.setText(materials.getCompressionTapYtdTwoFifty());
                    materials_compressionTapYtdThreeHundred.setText(materials.getCompressionTapYtdThreeHundred());
                    materials_compressionTapYtdTwoHundred.setText(materials.getCompressionTapYtdTwoHundred());
                    materials_compressionTapYtdOneFifty.setText(materials.getCompressionTapYtdOneFifty());
                    materials_metalScrew.setText(materials.getMetalScrew());
                    materials_electricalTape.setText(materials.getElectricalTape());
                }

                if (payTransactions != null) {
//                    billDeposit.setText(payTransactions.getAmount());
                }
            } catch (Exception e) {
                Log.e("ERR_GET_SVC", e.getMessage());
            }
        }
    }

    class FetchServiceConnectionInspectionData extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                ServiceConnectionInspectionsDao serviceConnectionInspectionsDao = db.serviceConnectionInspectionsDao();
                serviceConnectionInspections = serviceConnectionInspectionsDao.getOneBySvcId(strings[0]);
            } catch (Exception e) {
                Log.e("ERR_GET_SVC_INS_FORM", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            try {
                if (serviceConnectionInspections == null) {
                    AlertHelpers.infoDialog(FormEdit.this, "Not Found (404)", "Service connection inspection data not found!");
                } else {
                    formBreakerRatingInstalled.setText(serviceConnectionInspections.getSEMainCircuitBreakerAsInstalled());
                    formBreakerBranchesInstalled.setText(serviceConnectionInspections.getSENoOfBranchesAsInstalled());

                    formSdwSizeInstalled.setText(serviceConnectionInspections.getSDWSizeAsInstalled());
                    formSdwLengthInstalled.setText(serviceConnectionInspections.getSDWLengthAsInstalled());

                    formLiftPolesDiameterGI.setText(serviceConnectionInspections.getPoleGIEstimatedDiameter());
                    formLiftPolesDiameterConcrete.setText(serviceConnectionInspections.getPoleConcreteEstimatedDiameter());
                    formLiftPolesDiameterHardWood.setText(serviceConnectionInspections.getPoleHardwoodEstimatedDiameter());
                    formLiftPolesHeightGI.setText(serviceConnectionInspections.getPoleGIEstimatedDiameter());
                    formLiftPolesHeightConcrete.setText(serviceConnectionInspections.getPoleConcreteHeight());
                    formLiftPolesHeightHardWood.setText(serviceConnectionInspections.getPoleHardwoodHeight());
                    formLiftPolesQuantityGI.setText(serviceConnectionInspections.getPoleGINoOfLiftPoles());
                    formLiftPolesQuantityConcrete.setText(serviceConnectionInspections.getPoleConcreteNoOfLiftPoles());
                    formLiftPolesQuantityHardWood.setText(serviceConnectionInspections.getPoleHardwoodNoOfLiftPoles());
                    formLiftPolesRemarks.setText(serviceConnectionInspections.getPoleRemarks());
                    formPoleNo.setText(serviceConnectionInspections.getPoleNo());
                    formBillDeposit.setText(serviceConnectionInspections.getBillDeposit());

                    formGeoTappingPole.setText(serviceConnectionInspections.getGeoTappingPole());
                    formGeoMeteringPole.setText(serviceConnectionInspections.getGeoMeteringPole());
                    formSEPole.setText(serviceConnectionInspections.getGeoSEPole());
                    formGeoBuilding.setText(serviceConnectionInspections.getGeoBuilding());

                    /**
                     * BOHECO  I
                     */
                    neighbor1.setText(serviceConnectionInspections.getFirstNeighborName());
                    neighbor2.setText(serviceConnectionInspections.getSecondNeighborName());
                    neighbor1meter.setText(serviceConnectionInspections.getFirstNeighborMeterSerial());
                    neighbor2meter.setText(serviceConnectionInspections.getSecondNeighborMeterSerial());

                    /**
                     * LOAD PROFILING
                     */
                    formRate.setText(serviceConnectionInspections.getRate());
                    formNumberOfLo.setText(serviceConnectionInspections.getLightingOutlets());
                    formNumberOfCo.setText(serviceConnectionInspections.getConvenienceOutlets());
                    formMotor.setText(serviceConnectionInspections.getMotor());
                    formTotalLoad.setText(serviceConnectionInspections.getTotalLoad());
                    formContractedDemand.setText(serviceConnectionInspections.getContractedDemand());
                    formContractedEnergy.setText(serviceConnectionInspections.getContractedEnergy());
                    formDistanceFromSecondary.setText(serviceConnectionInspections.getDistanceFromSecondaryLine());
                    formSizeOfSecondary.setText(serviceConnectionInspections.getSizeOfSecondary());
                    formZone.setSelection(zonesAdapter.getPosition(serviceConnectionInspections.getZone()));
                    formBlock.setSelection(blocksAdapter.getPosition(serviceConnectionInspections.getBlock()));
                    formFeeder.setText(serviceConnectionInspections.getFeeder());
                    formAccountType.setSelection(meteringTypesAdapter.getPosition(serviceConnectionInspections.getMeteringType()));

                    // STATUS
                    if (null==serviceConnectionInspections.getStatus()) {

                    } else {
                        formRecommendation.check(getRecommendationSelected(serviceConnectionInspections.getStatus()));
                    }

                    // TYPE OF SDW
                    if (null==serviceConnectionInspections.getTypeOfSDW()) {

                    } else {
                        formSdwType.check(getSDWTypeSelected(serviceConnectionInspections.getTypeOfSDW()));
                    }

                    // HEIGHT OF SDW
                    if (null==serviceConnectionInspections.getHeightOfSDW()) {

                    } else {
                        formSdwHeight.check(getSDWHeightSelected(serviceConnectionInspections.getHeightOfSDW()));
                    }

                    // LOAD TYPE
                    if (null==serviceConnectionInspections.getLoadType()) {

                    } else {
                        formLoadType.check(getLoadTypeSelected(serviceConnectionInspections.getLoadType()));
                    }

                    /**
                     * TRANSFORMER
                     */
                    formDistanceFromTransformer.setText(serviceConnectionInspections.getDistanceFromTransformer());
                    formSizeOfTransformer.setText(serviceConnectionInspections.getSizeOfTransformer());
                    formTransformerNumber.setText(serviceConnectionInspections.getTransformerNo());

                    /**
                     * CONFIRMATIONS
                     */
                    if (null==serviceConnectionInspections.getLinePassingPrivateProperty() | serviceConnectionInspections.getLinePassingPrivateProperty().equals("")) {

                    } else {
                        formLinePassingPrivateProperty.check(getConfirmations(serviceConnectionInspections.getLinePassingPrivateProperty(), R.id.passing_yes, R.id.passing_no));
                    }

                    if (null==serviceConnectionInspections.getWrittenConsentByPropertyOwner() | serviceConnectionInspections.getWrittenConsentByPropertyOwner().equals("")) {

                    } else {
                        formWrittenConsentByPropertyOwner.check(getConfirmations(serviceConnectionInspections.getWrittenConsentByPropertyOwner(), R.id.consent_yes, R.id.consent_no));
                    }

                    if (null==serviceConnectionInspections.getObstructionOfLines() | serviceConnectionInspections.getObstructionOfLines().equals("")) {

                    } else {
                        formObstructionOfLines.check(getConfirmations(serviceConnectionInspections.getObstructionOfLines(), R.id.obstruction_yes, R.id.obstruction_no));
                    }

                    if (null==serviceConnectionInspections.getLinePassingRoads() | serviceConnectionInspections.getLinePassingRoads().equals("")) {

                    } else {
                        formLinePassingRoads.check(getConfirmations(serviceConnectionInspections.getLinePassingRoads(), R.id.roads_yes, R.id.roads_no));
                    }

                    formReverificationRemarks.setText(serviceConnectionInspections.getNotes());

                }
            } catch (Exception e) {
                Log.e("ERR_GET_INSP", e.getMessage());
            }

        }
    }

    class FetchMastPoles extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mastPoles.clear();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                if (strings != null) {
                    mastPoles.addAll(db.mastPolesDao().getAllMastPoles(strings[0]));
                }
            } catch (Exception e) {
                Log.e("ERR_GET_MST_PLS", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            mastPoleAdapters.notifyDataSetChanged();

            List<Point> latLngList = new ArrayList<>();

            // DISPLAY POINTS
            if (serviceConnectionInspections != null) {
                if (formGeoMeteringPole.getText() != null) {
                    String splitted[] = formGeoMeteringPole.getText().toString().split(",");
                    if (splitted.length > 1) {
                        String lat = formGeoMeteringPole.getText().toString().split(",")[0];
                        String longi = formGeoMeteringPole.getText().toString().split(",")[1];
                        addMarkers(style, new LatLng(Double.valueOf(lat), Double.valueOf(longi)), "hu-main-2");
                        latLngList.add(Point.fromLngLat(Double.valueOf(longi), Double.valueOf(lat)));
                    }
                }

                if (formGeoTappingPole.getText() != null) {
                    String splitted[] = formGeoTappingPole.getText().toString().split(",");
                    if (splitted.length > 1) {
                        String lat = formGeoTappingPole.getText().toString().split(",")[0];
                        String longi = formGeoTappingPole.getText().toString().split(",")[1];
                        addMarkers(style, new LatLng(Double.valueOf(lat), Double.valueOf(longi)), "tw-provincial-2");
                        latLngList.add(Point.fromLngLat(Double.valueOf(longi), Double.valueOf(lat)));
                    }
                }

                if (formSEPole.getText() != null) {
                    String splitted[] = formSEPole.getText().toString().split(",");
                    if (splitted.length > 1) {
                        String lat = formSEPole.getText().toString().split(",")[0];
                        String longi = formSEPole.getText().toString().split(",")[1];
                        addMarkers(style, new LatLng(Double.valueOf(lat), Double.valueOf(longi)), "tw-provincial-expy-2");
                        latLngList.add(Point.fromLngLat(Double.valueOf(longi), Double.valueOf(lat)));
                    }
                }

                if (formGeoBuilding.getText() != null) {
                    String splitted[] = formGeoBuilding.getText().toString().split(",");
                    if (splitted.length > 1) {
                        String lat = formGeoBuilding.getText().toString().split(",")[0];
                        String longi = formGeoBuilding.getText().toString().split(",")[1];
                        addMarkers(style, new LatLng(Double.valueOf(lat), Double.valueOf(longi)), "in-national-3");
                        latLngList.add(Point.fromLngLat(Double.valueOf(longi), Double.valueOf(lat)));
                    }
                }
            }

            // ADD LINE TO MAP
            try {
                List<Point> routes = new ArrayList<>();
                distance = 0.0;

                /**
                 *
                 * SERVICE CONNECTION LONG LATS
                 */
                LatLng prevPoint;
                LatLng presPoint;
                for (int x=0; x<latLngList.size(); x++) {
                    routes.add(latLngList.get(x));

                    Log.e("TEST_SC", Double.valueOf(latLngList.get(x).longitude()) + "");
                }

                /**
                 * MAST POLES
                 */
                for (int i=0; i<mastPoles.size(); i++) {
                    routes.add(Point.fromLngLat(Double.valueOf(mastPoles.get(i).getLongitude()), Double.valueOf(mastPoles.get(i).getLatitude())));

                    addMarkers(style, new LatLng(Double.valueOf(mastPoles.get(i).getLatitude()), Double.valueOf(mastPoles.get(i).getLongitude())), "za-provincial-2");
                }

                Log.e("TST", routes.size() + "");

                /**
                 * GET DISTANCES
                 */
                for(int j=0; j<routes.size(); j++) {
                    if (routes.size() > 0) {
                        if (j > 0) {
                            prevPoint = new LatLng(Double.valueOf(routes.get(j-1).latitude()), Double.valueOf(routes.get(j-1).longitude()));
                            presPoint = new LatLng(Double.valueOf(routes.get(j).latitude()), Double.valueOf(routes.get(j).longitude()));

                            distance += presPoint.distanceTo(prevPoint);
                        }
                    } else {
                        distance = 0.0;
                    }
                }

                svcDropLength.setText("Len: " + ObjectHelpers.roundTwo(distance)  + " mtrs");

//                if (!firstInit) {
//                    formSdwLengthInstalled.setText(ObjectHelpers.roundTwo(getTotalServiceDrop()));
//                }

            } catch (Exception e) {
                Log.e("ERR_ADD_LN_MST_PL", e.getMessage());
            }
        }
    }

    class AddMastPole extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                if (strings != null) {
                    MastPoles mastPoles = new MastPoles();
                    mastPoles.setId(ObjectHelpers.generateIDandRandString());
                    mastPoles.setPoleRemarks(strings[0]);
                    mastPoles.setServiceConnectionId(strings[1]);

                    if (locationComponent != null) {
                        mastPoles.setLatitude(locationComponent.getLastKnownLocation() != null ? (locationComponent.getLastKnownLocation().getLatitude() + "") : "");
                        mastPoles.setLongitude(locationComponent.getLastKnownLocation() != null ? (locationComponent.getLastKnownLocation().getLongitude() + "") : "");
                    }

                    mastPoles.setDateTimeTaken(ObjectHelpers.getDateTime());
                    mastPoles.setIsUploaded("No");

                    db.mastPolesDao().insertAll(mastPoles);
                }
            } catch (Exception e) {
                Log.e("ERR_ADD_MST_POLE", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Toast.makeText(FormEdit.this, "Lift Pole Added", Toast.LENGTH_SHORT).show();
            new FetchMastPoles().execute(svcId);
        }
    }

    public void addMarkers(Style style, LatLng latLng, String iconImage) {
        try {
            // ADD MARKERS
            SymbolManager symbolManager = new SymbolManager(mapView, mapboxMap, style);

            symbolManager.setIconAllowOverlap(true);
            symbolManager.setTextAllowOverlap(true);

            if (latLng != null) {
                SymbolOptions symbolOptions = new SymbolOptions()
                        .withLatLng(latLng)
                        .withIconImage(iconImage)
                        .withIconSize(.3f);

                Symbol symbol = symbolManager.create(symbolOptions);

                mapboxMap.setCameraPosition(new CameraPosition.Builder()
                        .target(latLng)
                        .zoom(14)
                        .build());
            }
        } catch (Exception e) {
            Log.e("ERR_PLOT_MRKERS", e.getMessage());
        }
    }

    public void refreshMap() {
        new FetchMastPoles().execute(svcId);
        firstInit = false;
    }

    class UpdateInspectionData extends AsyncTask<String, Void, Void> {

        Object billDepFill;

        @Override
        protected void onPreExecute() {
            serviceConnectionInspections.setSEMainCircuitBreakerAsInstalled(formBreakerRatingInstalled.getText().toString());
            serviceConnectionInspections.setSENoOfBranchesAsInstalled(formBreakerBranchesInstalled.getText().toString());
            serviceConnectionInspections.setSDWLengthAsInstalled(formSdwLengthInstalled.getText().toString());
            serviceConnectionInspections.setSDWSizeAsInstalled(formSdwSizeInstalled.getText().toString());

            serviceConnectionInspections.setPoleGIEstimatedDiameter(formLiftPolesDiameterGI.getText().toString());
            serviceConnectionInspections.setPoleGIHeight(formLiftPolesHeightGI.getText().toString());
            serviceConnectionInspections.setPoleGINoOfLiftPoles(formLiftPolesQuantityGI.getText().toString());

            serviceConnectionInspections.setPoleConcreteEstimatedDiameter(formLiftPolesDiameterConcrete.getText().toString());
            serviceConnectionInspections.setPoleConcreteHeight(formLiftPolesHeightConcrete.getText().toString());
            serviceConnectionInspections.setPoleConcreteNoOfLiftPoles(formLiftPolesQuantityConcrete.getText().toString());

            serviceConnectionInspections.setPoleHardwoodEstimatedDiameter(formLiftPolesDiameterHardWood.getText().toString());
            serviceConnectionInspections.setPoleHardwoodHeight(formLiftPolesHeightHardWood.getText().toString());
            serviceConnectionInspections.setPoleHardwoodNoOfLiftPoles(formLiftPolesQuantityHardWood.getText().toString());

            serviceConnectionInspections.setPoleRemarks(formLiftPolesRemarks.getText().toString());
            serviceConnectionInspections.setPoleNo(formPoleNo.getText().toString());

            serviceConnectionInspections.setGeoTappingPole(formGeoTappingPole.getText().toString());
            serviceConnectionInspections.setGeoMeteringPole(formGeoMeteringPole.getText().toString());
            serviceConnectionInspections.setGeoSEPole(formSEPole.getText().toString());
            serviceConnectionInspections.setGeoBuilding(formGeoBuilding.getText().toString());

            serviceConnectionInspections.setStatus(ObjectHelpers.getSelectedTextFromRadioGroup(formRecommendation, getWindow().getDecorView()));
            serviceConnectionInspections.setNotes(formReverificationRemarks.getText().toString());
            serviceConnectionInspections.setDateOfVerification(ObjectHelpers.getDateTime());

//            serviceConnectionInspections.setLooping(looping.getText().toString());
//            serviceConnectionInspections.setSDWLengthSubTotal(lengthSubTotal.getText().toString());

            /**
             * BOHECO I
             */
            serviceConnectionInspections.setFirstNeighborName(neighbor1.getText().toString());
            serviceConnectionInspections.setFirstNeighborMeterSerial(neighbor1meter.getText().toString());
            serviceConnectionInspections.setSecondNeighborName(neighbor2.getText().toString());
            serviceConnectionInspections.setSecondNeighborMeterSerial(neighbor2meter.getText().toString());

            // UPDATE STATUS OF SERVICE CONNECTION
            serviceConnections.setStatus(ObjectHelpers.getSelectedTextFromRadioGroup(formRecommendation, getWindow().getDecorView()));

            /**
             * LOAD PROFILE AND LINE
             */
            serviceConnectionInspections.setRate(formRate.getText().toString());
            serviceConnectionInspections.setLightingOutlets(formNumberOfLo.getText().toString());
            serviceConnectionInspections.setConvenienceOutlets(formNumberOfCo.getText().toString());
            serviceConnectionInspections.setMotor(formMotor.getText().toString());
            serviceConnectionInspections.setTotalLoad(formTotalLoad.getText().toString());
            serviceConnectionInspections.setContractedDemand(formContractedDemand.getText().toString());
            serviceConnectionInspections.setContractedEnergy(formContractedEnergy.getText().toString());
            serviceConnectionInspections.setDistanceFromSecondaryLine(formDistanceFromSecondary.getText().toString());
            serviceConnectionInspections.setSizeOfSecondary(formSizeOfSecondary.getText().toString());
            serviceConnectionInspections.setLoadType(ObjectHelpers.getSelectedTextFromRadioGroup(formLoadType, getWindow().getDecorView()));
            serviceConnectionInspections.setZone(formZone.getSelectedItem().toString());
            serviceConnectionInspections.setBlock(formBlock.getSelectedItem().toString());
            serviceConnectionInspections.setFeeder(formFeeder.getText().toString());
            serviceConnectionInspections.setBillDeposit(formBillDeposit.getText().toString());
            serviceConnectionInspections.setMeteringType(formAccountType.getSelectedItem().toString());

            /**
             * SDW
             */
            serviceConnectionInspections.setTypeOfSDW(ObjectHelpers.getSelectedTextFromRadioGroup(formSdwType, getWindow().getDecorView()));
            serviceConnectionInspections.setHeightOfSDW(ObjectHelpers.getSelectedTextFromRadioGroup(formSdwHeight, getWindow().getDecorView()));

            /**
             * TRANSFORMER
             */
            serviceConnectionInspections.setDistanceFromTransformer(formDistanceFromTransformer.getText().toString());
            serviceConnectionInspections.setSizeOfTransformer(formSizeOfTransformer.getText().toString());
            serviceConnectionInspections.setTransformerNo(formTransformerNumber.getText().toString());

            /**
             * CONFIRMATIONS
             */
            serviceConnectionInspections.setLinePassingPrivateProperty(ObjectHelpers.getSelectedTextFromRadioGroup(formLinePassingPrivateProperty, getWindow().getDecorView()));
            serviceConnectionInspections.setWrittenConsentByPropertyOwner(ObjectHelpers.getSelectedTextFromRadioGroup(formWrittenConsentByPropertyOwner, getWindow().getDecorView()));
            serviceConnectionInspections.setObstructionOfLines(ObjectHelpers.getSelectedTextFromRadioGroup(formObstructionOfLines, getWindow().getDecorView()));
            serviceConnectionInspections.setLinePassingRoads(ObjectHelpers.getSelectedTextFromRadioGroup(formLinePassingRoads, getWindow().getDecorView()));

            // material presets
            if (materials != null) {

            } else {
                materials = new Materials();
                materials.setId(ObjectHelpers.generateIDandRandString());
                materials.setServiceConnectionId(svcId);
            }
            materials.setMeterBaseSocket(materials_meterBaseSocket.getText().toString());
            materials.setMetalboxTypeA(materials_metalboxTypeA.getText().toString());
            materials.setMetalboxTypeB(materials_metalboxTypeB.getText().toString());
            materials.setPipe(materials_pipe.getText().toString());
            materials.setEntranceCap(materials_entranceCap.getText().toString());
            materials.setAdapter(materials_adapter.getText().toString());
            materials.setLocknot(materials_locknot.getText().toString());
            materials.setMailbox(materials_mailbox.getText().toString());
            materials.setBuckle(materials_buckle.getText().toString());
            materials.setPvcElbow(materials_pvcElbow.getText().toString());
            materials.setStainlessStrap(materials_stainlessStrap.getText().toString());
            materials.setPlyboard(materials_plyboard.getText().toString());
            materials.setStrainInsulator(materials_strainInsulator.getText().toString());
            materials.setStraindedWireEight(materials_straindedWire8.getText().toString());
            materials.setStrandedWireSix(materials_strandedWire6.getText().toString());
            materials.setTwistedWireSix(materials_twistedWire6.getText().toString());
            materials.setTwistedWireFour(materials_twistedWire4.getText().toString());
            materials.setCompressionTapAsu(materials_compressionTapAsu.getText().toString());
            materials.setCompressionTapYtdTwoFifty(materials_compressionYtdTwoFifty.getText().toString());
            materials.setCompressionTapYtdThreeHundred(materials_compressionTapYtdThreeHundred.getText().toString());
            materials.setCompressionTapYtdTwoHundred(materials_compressionTapYtdTwoHundred.getText().toString());
            materials.setCompressionTapYtdOneFifty(materials_compressionTapYtdOneFifty.getText().toString());
            materials.setMetalScrew(materials_metalScrew.getText().toString());
            materials.setElectricalTape(materials_electricalTape.getText().toString());

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                ServiceConnectionInspectionsDao serviceConnectionInspectionsDao = db.serviceConnectionInspectionsDao();
                serviceConnectionInspectionsDao.updateServiceConnectionInspections(serviceConnectionInspections);

                ServiceConnectionsDao serviceConnectionsDao = db.serviceConnectionsDao();
                serviceConnectionsDao.updateServiceConnections(serviceConnections);

                // ADD BILL DEPOSIT
//                billDepFill = billDeposit.getText();
                if (billDepFill != null) {
                    if (payTransactions != null) {
                        payTransactions.setAmount(billDepFill.toString());
                        payTransactions.setTotal(billDepFill.toString());

                        db.payTransactionsDao().updateAll(payTransactions);
                    } else {
                        payTransactions = new PayTransactions();
                        payTransactions.setId(ObjectHelpers.generateIDandRandString());
                        payTransactions.setAmount(billDepFill.toString());
                        payTransactions.setTotal(billDepFill.toString());
                        payTransactions.setParticular(com.lopez.julz.inspectionv2.classes.ObjectHelpers.getBillDepositId());
                        payTransactions.setServiceConnectionId(serviceConnections.getId());

                        db.payTransactionsDao().insertAll(payTransactions);
                    }
                }

                if (materials != null) {
                    db.materialsDao().insertAll(materials);
                }
            } catch (Exception e) {
                Log.e("ERR_SV_INSP", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Toast.makeText(FormEdit.this, "Inspection data saved!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public int getRecommendationSelected(String recommendation) {
        if (recommendation != null) {
            if (recommendation.equals("Approved")) {
                return R.id.opsApproved;
            } else if (recommendation.equals("Re-Inspection")) {
                return R.id.opsReInspection;
            } else {
                return -1;
            }
        } else {
            return 0;
        }
    }

    public int getSDWTypeSelected(String type) {
        if (type != null) {
            if (type.equals("Copper")) {
                return R.id.type_copper;
            } else {
                return R.id.type_aluminum;
            }
        } else {
            return 0;
        }
    }

    public int getLoadTypeSelected(String type) {
        if (type != null) {
            if (type.equals("DEDICATED")) {
                return R.id.load_dedicated;
            } else {
                return R.id.load_common;
            }
        } else {
            return 0;
        }
    }

    public int getSDWHeightSelected(String height) {
        if (height != null) {
            if (height.equals("12ft")) {
                return R.id.height12;
            } else if (height.equals("14ft")) {
                return R.id.height14;
            } else if (height.equals("20ft")) {
                return R.id.height20;
            } else {
                return R.id.height25;
            }
        } else {
            return 0;
        }
    }

    public int getConfirmations(String condition, int yesProp, int noProp) {
        if (condition != null) {
            if (condition.equals("Yes")) {
                return yesProp;
            } else {
                return noProp;
            }
        } else {
            return 0;
        }
    }

    public double getBillDeposit(String consumerType, double totalLoad, double rate) {
        try {
            double PF_COM = .5;
            double PF_RES = .35;
            double DF = .8;
            int COM_THRESHOLD = 10000;
            int RES_THRESHOLD = 3000;
            if (consumerType != null) {
                if (consumerType.equals("COMMERCIAL")) {
                    if (totalLoad > COM_THRESHOLD) {
                        double excess = totalLoad - COM_THRESHOLD;
                        double excessPF = excess * PF_COM;
                        double wattage = (excessPF + COM_THRESHOLD) * DF;
                        double deposit = (wattage * rate * 8 /* 8 hours */ * 26 /* 26 days */) / 1000;
                        return Double.valueOf(ObjectHelpers.roundTwoNoComma(deposit));
                    } else {
                        double wattage = totalLoad * DF;
                        double deposit = (wattage * rate * 8 /* 8 hours */ * 26 /* 26 days */) / 1000;
                        return Double.valueOf(ObjectHelpers.roundTwoNoComma(deposit));
                    }
                } else if (consumerType.equals("RESIDENTIAL")) {
                    if (totalLoad > RES_THRESHOLD) {
                        double excess = totalLoad - RES_THRESHOLD;
                        double excessPF = excess * PF_RES;
                        double wattage = (excessPF + RES_THRESHOLD) * DF;
                        double deposit = (wattage * rate * 8 /* 8 hours */ * 30 /* 30 days */) / 1000;
                        return Double.valueOf(ObjectHelpers.roundTwoNoComma(deposit));
                    } else {
                        double wattage = totalLoad * DF;
                        double deposit = (wattage * rate * 8 /* 8 hours */ * 30 /* 30 days */) / 1000;
                        return Double.valueOf(ObjectHelpers.roundTwoNoComma(deposit));
                    }
                } else {
                    return 0.0;
                }
            } else {
                return  0.0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    /**
     * TAKE PHOTOS
     */
    private void dispatchTakePictureIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra( MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);

            File pictureFile = null;
            try {
                pictureFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this,
                        "Photo file can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (pictureFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.lopez.julz.inspectionv2",
                        pictureFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pictureFile = "CRM_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(pictureFile,  ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        new SavePhotoToDatabase().execute(svcId, currentPhotoPath);
        return image;
    }

    public class SavePhotoToDatabase extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                if (strings != null) {
                    String scId = strings[0];
                    String photo = strings[1];

                    Photos photoObject = new Photos(photo, scId, null);
                    db.photosDao().insertAll(photoObject);
                }
            } catch (Exception e) {
                Log.e("ERR_SAVE_PHOTO_DB", e.getMessage());
            }

            return null;
        }
    }

    public class GetPhotos extends AsyncTask<Void, Void, Void> {

        List<Photos> photosList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imageFields.removeAllViews();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                photosList.addAll(db.photosDao().getAllPhotos(svcId));
            } catch (Exception e) {
                Log.e("ERR_GET_IMGS", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            try {
                if (photosList != null) {
                    for(int i=0; i<photosList.size(); i++) {
                        File file = new File(photosList.get(i).getPath());
                        if (file.exists()) {
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                            Bitmap scaledBmp = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/8, bitmap.getHeight()/8, true);
                            ImageView imageView = new ImageView(FormEdit.this);
                            Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(scaledBmp.getWidth(), scaledBmp.getHeight());
                            imageView.setLayoutParams(layoutParams);
                            imageView.setPadding(0, 5, 5, 0);
                            imageView.setImageBitmap(scaledBmp);
                            imageFields.addView(imageView);

                            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    PopupMenu popup = new PopupMenu(FormEdit.this,imageView);
                                    //inflating menu from xml resource
                                    popup.inflate(R.menu.image_menu);
                                    //adding click listener
                                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                        @Override
                                        public boolean onMenuItemClick(MenuItem item) {
                                            switch (item.getItemId()) {
                                                case R.id.delete_img:
                                                    file.delete();
                                                    new GetPhotos().execute();
                                                    return true;
                                                default:
                                                    return false;
                                            }
                                        }
                                    });
                                    //displaying the popup
                                    popup.show();
                                    return false;
                                }
                            });
                        } else {
                            Log.e("ERR_RETRV_FILE", "Error retriveing file");
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("ERR_PHPTOS", e.getMessage());
            }

        }
    }

    class GetZonesAndBlocks extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                zonesList.addAll(db.zonesDao().getAll());
                blocksList.addAll(db.blocksDao().getAll());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            try {
                /**
                 * ZONES
                 */
                String[] zones = new String[zonesList.size()];
                for (int i=0; i<zones.length; i++) {
                    zones[i] = zonesList.get(i).getZone();
                }
                zonesAdapter = new ArrayAdapter<String>(FormEdit.this,
                        android.R.layout.simple_spinner_item, zones);
                zonesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                formZone.setAdapter(zonesAdapter);

                /**
                 * BLOCKS
                 */
                String[] blocks = new String[blocksList.size()];
                for (int i=0; i<blocks.length; i++) {
                    blocks[i] = blocksList.get(i).getBlock();
                }
                blocksAdapter = new ArrayAdapter<String>(FormEdit.this,
                        android.R.layout.simple_spinner_item, blocks);
                blocksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                formBlock.setAdapter(blocksAdapter);

                /**
                 * Account Type
                 */
                String[] meteringTypes = { "RESIDENTIAL", "COMMERCIAL LV", "COMMERCIAL HV", "INDUSTRIAL LV", "INDUSTRIAL HV" };
                meteringTypesAdapter = new ArrayAdapter<String>(FormEdit.this,
                        android.R.layout.simple_spinner_item, meteringTypes);
                meteringTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                formAccountType.setAdapter(meteringTypesAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getFiles() {
        try {
            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/" + serviceConnections.getId());
            File[] files = directory.listFiles();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    getFileNames(files));

            filesList.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
            AlertHelpers.infoDialog(this, "Error Getting Files", e.getMessage());
        }
    }

    private String[] getFileNames(File[] files) {
        if (files == null) {
            return new String[0]; // Return an empty array if files is null
        }

        String[] fileNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            fileNames[i] = files[i].getName(); // Extract the file name
            Log.e("TEST", files[i].getName() + "");
        }
        return fileNames;
    }

}