package com.lopez.julz.inspectionv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.lopez.julz.inspectionv2.api.RequestPlaceHolder;
import com.lopez.julz.inspectionv2.api.RetrofitBuilder;
import com.lopez.julz.inspectionv2.api.ServiceConnectionInspections;
import com.lopez.julz.inspectionv2.api.ServiceConnections;
import com.lopez.julz.inspectionv2.classes.ServiceConnectionsAdapter;
import com.lopez.julz.inspectionv2.database.AppDatabase;
import com.lopez.julz.inspectionv2.database.LocalServiceConnectionInspections;
import com.lopez.julz.inspectionv2.database.LocalServiceConnections;
import com.lopez.julz.inspectionv2.database.ServiceConnectionInspectionsDao;
import com.lopez.julz.inspectionv2.database.ServiceConnectionsDao;
import com.lopez.julz.inspectionv2.database.Settings;
import com.lopez.julz.inspectionv2.helpers.AlertHelpers;
import com.lopez.julz.inspectionv2.helpers.FileExtractor;
import com.lopez.julz.inspectionv2.helpers.ObjectHelpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Download extends AppCompatActivity {

    public Toolbar download_toolbar;

    public RecyclerView download_recyclerview;
    public ServiceConnectionsAdapter serviceConnectionsAdapter;
    public List<ServiceConnections> serviceConnectionsList;
    public List<ServiceConnectionInspections> serviceConnectionInspectionsList;

    public RetrofitBuilder retrofitBuilder;
    private RequestPlaceHolder requestPlaceHolder;

    public FloatingActionButton download_button;

    public AppDatabase db;

    public String userid;

    public Settings settings;

    public AlertDialog progressDialog;
    public List<ServiceConnections> tmpScList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        download_toolbar = (Toolbar) findViewById(R.id.download_toolbar);
        download_recyclerview = (RecyclerView) findViewById(R.id.download_recyclerview);

        db = Room.databaseBuilder(this,
                AppDatabase.class, ObjectHelpers.databaseName()).fallbackToDestructiveMigration().build();

        userid = getIntent().getExtras().getString("USERID");

        setSupportActionBar(download_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24);

        serviceConnectionsList = new ArrayList<>();
        serviceConnectionInspectionsList = new ArrayList<>();
        serviceConnectionsAdapter = new ServiceConnectionsAdapter(serviceConnectionsList, this);
        download_recyclerview.setAdapter(serviceConnectionsAdapter);
        download_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        download_button = (FloatingActionButton) findViewById(R.id.download_button);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchSettings().execute();
    }

    public void fetchTotalServiceConnections(String userId) {
        try {
            Call<List<ServiceConnections>> svcConnectionCall = requestPlaceHolder.getServiceConnections(userId);

            svcConnectionCall.enqueue(new Callback<List<ServiceConnections>>() {
                @Override
                public void onResponse(Call<List<ServiceConnections>> call, Response<List<ServiceConnections>> response) {
                    if (!response.isSuccessful()) {
                        Log.e("DWNLD_SVC_CON_ERR", response.message());
                    } else {
                        if (response.code() == 200) {
                            List<ServiceConnections> serviceConnections = response.body();

                            if (serviceConnections != null) {
                                new FetchDownloadableServiceConnections().execute(serviceConnections);
                            } else {

                            }

                        } else {
                            Log.e("DWNLD_SVC_CON_ERR", response.message());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ServiceConnections>> call, Throwable t) {
                    Log.e("DWNLD_SVC_CON_ERR", t.getMessage());
                }
            });
        } catch (Exception e ){
            Log.e("DWNLD_SVC_CON_ERR", e.getMessage());
        }
    }

    public void notifyDownloaded(String name, String number, String id) {
        try {
            Call<Void> notifyDownloaded = requestPlaceHolder.notifyDownloaded(name, number, id);

            notifyDownloaded.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.e("SUCCESS_NOTIF", "Notification sent to consumer");
                    } else {
                        Log.e("ERROR_NOTIF", response.message() + "\n" + response.raw() + "\n" + response.body());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("ERROR_NOTIF", t.getMessage());
                }
            });

        } catch (Exception e ){
            Log.e("DWNLD_SVC_CON_ERR", e.getMessage());
        }
    }

    public void fetchTotalServiceConnectionInspections(String userId) {
        try {
            Call<List<ServiceConnectionInspections>> svcConnectionInspectionCall = requestPlaceHolder.getServiceConnectionInspections(userId);

            svcConnectionInspectionCall.enqueue(new Callback<List<ServiceConnectionInspections>>() {
                @Override
                public void onResponse(Call<List<ServiceConnectionInspections>> call, Response<List<ServiceConnectionInspections>> response) {
                    if (!response.isSuccessful()) {
                        Log.e("DWNLD_SVC_CON_INSP_ERR", response.message());
                    } else {
                        if (response.code() == 200) {
                            List<ServiceConnectionInspections> serviceConnectionInspections = response.body();

                            if (serviceConnectionInspections != null) {
                                new FetchDownloadableServiceConnectionInspections().execute(serviceConnectionInspections);
                            } else {

                            }

                        } else {
                            Log.e("DWNLD_SVC_CON_INSP_ERR", response.message());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ServiceConnectionInspections>> call, Throwable t) {
                    Log.e("DWNLD_SVC_CON_ERR", t.getMessage());
                }
            });
        } catch (Exception e ){
            Log.e("DWNLD_SVC_CON_ERR", e.getMessage());
        }
    }

    class DownloadServiceConnections extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                for (int i=0; i<serviceConnectionsList.size(); i++) {
                    ServiceConnectionsDao serviceConnectionsDao = db.serviceConnectionsDao();
                    LocalServiceConnections localServiceConnections = serviceConnectionsDao.getOne(serviceConnectionsList.get(i).getId());

                    if (localServiceConnections == null) {
                        LocalServiceConnections newLocalSC = new LocalServiceConnections(serviceConnectionsList.get(i).getId(),
                                serviceConnectionsList.get(i).getMemberConsumerId(),
                                serviceConnectionsList.get(i).getDateOfApplication(),
                                serviceConnectionsList.get(i).getServiceAccountName(),
                                serviceConnectionsList.get(i).getAccountCount(),
                                serviceConnectionsList.get(i).getSitio(),
                                serviceConnectionsList.get(i).getBarangay(),
                                serviceConnectionsList.get(i).getTown(),
                                serviceConnectionsList.get(i).getContactNumber(),
                                serviceConnectionsList.get(i).getEmailAddress(),
                                serviceConnectionsList.get(i).getAccountType(),
                                serviceConnectionsList.get(i).getAccountOrganization(),
                                serviceConnectionsList.get(i).getOrganizationAccountNumber(),
                                serviceConnectionsList.get(i).getIsNIHE(),
                                serviceConnectionsList.get(i).getAccountApplicationType(),
                                serviceConnectionsList.get(i).getConnectionApplicationType(),
                                serviceConnectionsList.get(i).getBuildingType(),
                                "FOR INSPECTION",
                                serviceConnectionsList.get(i).getNotes(),
                                serviceConnectionsList.get(i).getBarangayFull(),
                                serviceConnectionsList.get(i).getTownFull());
                        serviceConnectionsDao.insertAll(newLocalSC);
                    } else {
                        serviceConnectionsDao.updateServiceConnections(localServiceConnections);
                    }

//                    notifyDownloaded(serviceConnectionsList.get(i).getServiceAccountName(), serviceConnectionsList.get(i).getContactNumber(), serviceConnectionsList.get(i).getId());
                }

                return null;
            } catch (Exception e){
                Log.e("ERR", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            String data = convertListIdToString(serviceConnectionsList);

            tmpScList = new ArrayList<>();
            tmpScList.addAll(serviceConnectionsList);

            notifyDownloaded(data);

            serviceConnectionsList.clear();
            serviceConnectionsAdapter.notifyDataSetChanged();

            /**
             * DOWNLOAD ASSOCIATED FILES
             */
            progressDialog = AlertHelpers.progressDialog(Download.this, "Downloading", "Downloading data and files. Please wait...");
            progressDialog.show();
            downloadFiles();
        }
    }

    public void downloadFiles() {
        try {
            if (tmpScList != null && tmpScList.size() > 0) {
                ServiceConnections sc = tmpScList.get(0);

                if (sc != null) {
                    String serviceId = sc.getId();
                    Call<ResponseBody> dlFiles = requestPlaceHolder.getFiles(serviceId);
                    dlFiles.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                // Assuming permission and external storage availability are already handled
//                                File downloadsDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + "SC_FILES");

                                File downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                                File downloadedFile = new File(downloadsDirectory, ObjectHelpers.getTimeInMillis() + ".zip");

                                if (!downloadedFile.exists()) {
                                    try (InputStream inputStream = response.body().byteStream();
                                         OutputStream outputStream = new FileOutputStream(downloadedFile)) {
                                        byte[] fileReader = new byte[4096];
                                        while (true) {
                                            int read = inputStream.read(fileReader);
                                            if (read == -1) {
                                                break;
                                            }
                                            outputStream.write(fileReader, 0, read);
                                        }
                                        outputStream.flush();

                                        // Extract file
                                        String zipFile = downloadedFile.getAbsolutePath();
                                        String folderSc = downloadsDirectory.getAbsolutePath() + "/" + serviceId;
                                        FileExtractor.extractZipFile(zipFile, folderSc);

                                        tmpScList.remove(0);
                                        downloadFiles();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                AlertHelpers.infoDialog(Download.this, "Error saving files", response.message());
                                try {
                                    Log.e("ERR_DL_FILES", response.errorBody().string());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            AlertHelpers.infoDialog(Download.this, "Error downloading files", t.getMessage());
                            t.printStackTrace();
                        }
                    });
                }
            } else {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                AlertHelpers.infoDialog(Download.this, "Download Success", "Inspection data and files downloaded!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertHelpers.infoDialog(Download.this, "Error downloading files", e.getMessage());
        }
    }

    class DownloadServiceConnectionInspections extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                for (int i=0; i<serviceConnectionInspectionsList.size(); i++) {
                    ServiceConnectionInspectionsDao serviceConnectionInspectionsDao = db.serviceConnectionInspectionsDao();
                    LocalServiceConnectionInspections localServiceConnectionInspections = serviceConnectionInspectionsDao.getOne(serviceConnectionInspectionsList.get(i).getId());

                    if (localServiceConnectionInspections == null) {
                        LocalServiceConnectionInspections newLocalSC = new LocalServiceConnectionInspections(serviceConnectionInspectionsList.get(i).getId(),
                                serviceConnectionInspectionsList.get(i).getServiceConnectionId(),
                                serviceConnectionInspectionsList.get(i).getSEMainCircuitBreakerAsPlan(),
                                serviceConnectionInspectionsList.get(i).getSEMainCircuitBreakerAsInstalled(),
                                serviceConnectionInspectionsList.get(i).getSENoOfBranchesAsPlan(),
                                serviceConnectionInspectionsList.get(i).getSENoOfBranchesAsInstalled(),
                                serviceConnectionInspectionsList.get(i).getPoleGIEstimatedDiameter(),
                                serviceConnectionInspectionsList.get(i).getPoleGIHeight(),
                                serviceConnectionInspectionsList.get(i).getPoleGINoOfLiftPoles(),
                                serviceConnectionInspectionsList.get(i).getPoleConcreteEstimatedDiameter(),
                                serviceConnectionInspectionsList.get(i).getPoleConcreteHeight(),
                                serviceConnectionInspectionsList.get(i).getPoleConcreteNoOfLiftPoles(),
                                serviceConnectionInspectionsList.get(i).getPoleHardwoodEstimatedDiameter(),
                                serviceConnectionInspectionsList.get(i).getPoleHardwoodHeight(),
                                serviceConnectionInspectionsList.get(i).getPoleHardwoodNoOfLiftPoles(),
                                serviceConnectionInspectionsList.get(i).getPoleRemarks(),
                                serviceConnectionInspectionsList.get(i).getSDWSizeAsPlan(),
                                serviceConnectionInspectionsList.get(i).getSDWSizeAsInstalled(),
                                serviceConnectionInspectionsList.get(i).getSDWLengthAsPlan(),
                                serviceConnectionInspectionsList.get(i).getSDWLengthAsInstalled(),
                                serviceConnectionInspectionsList.get(i).getGeoBuilding(),
                                serviceConnectionInspectionsList.get(i).getGeoTappingPole(),
                                serviceConnectionInspectionsList.get(i).getGeoMeteringPole(),
                                serviceConnectionInspectionsList.get(i).getGeoSEPole(),
                                serviceConnectionInspectionsList.get(i).getFirstNeighborName(),
                                serviceConnectionInspectionsList.get(i).getFirstNeighborMeterSerial(),
                                serviceConnectionInspectionsList.get(i).getSecondNeighborName(),
                                serviceConnectionInspectionsList.get(i).getSecondNeighborMeterSerial(),
                                serviceConnectionInspectionsList.get(i).getEngineerInchargeName(),
                                serviceConnectionInspectionsList.get(i).getEngineerInchargeTitle(),
                                serviceConnectionInspectionsList.get(i).getEngineerInchargeLicenseNo(),
                                serviceConnectionInspectionsList.get(i).getEngineerInchargeLicenseValidity(),
                                serviceConnectionInspectionsList.get(i).getEngineerInchargeContactNo(),
                                serviceConnectionInspectionsList.get(i).getStatus(),
                                serviceConnectionInspectionsList.get(i).getInspector(),
                                serviceConnectionInspectionsList.get(i).getDateOfVerification(),
                                serviceConnectionInspectionsList.get(i).getEstimatedDateForReinspection(),
                                serviceConnectionInspectionsList.get(i).getNotes(),
                                null,
                                null,
                                serviceConnectionInspectionsList.get(i).getLightingOutlets(),
                                serviceConnectionInspectionsList.get(i).getConvenienceOutlets(),
                                serviceConnectionInspectionsList.get(i).getMotor(),
                                serviceConnectionInspectionsList.get(i).getTotalLoad(),
                                serviceConnectionInspectionsList.get(i).getContractedDemand(),
                                serviceConnectionInspectionsList.get(i).getContractedEnergy(),
                                serviceConnectionInspectionsList.get(i).getDistanceFromSecondaryLine(),
                                serviceConnectionInspectionsList.get(i).getSizeOfSecondary(),
                                serviceConnectionInspectionsList.get(i).getSizeOfSDW(),
                                serviceConnectionInspectionsList.get(i).getTypeOfSDW(),
                                serviceConnectionInspectionsList.get(i).getServiceEntranceStatus(),
                                serviceConnectionInspectionsList.get(i).getHeightOfSDW(),
                                serviceConnectionInspectionsList.get(i).getDistanceFromTransformer(),
                                serviceConnectionInspectionsList.get(i).getSizeOfTransformer(),
                                serviceConnectionInspectionsList.get(i).getTransformerNo(),
                                serviceConnectionInspectionsList.get(i).getPoleNo(),
                                serviceConnectionInspectionsList.get(i).getConnectedFeeder(),
                                serviceConnectionInspectionsList.get(i).getSizeOfSvcPoles(),
                                serviceConnectionInspectionsList.get(i).getHeightOfSvcPoles(),
                                serviceConnectionInspectionsList.get(i).getLinePassingPrivateProperty(),
                                serviceConnectionInspectionsList.get(i).getWrittenConsentByPropertyOwner(),
                                serviceConnectionInspectionsList.get(i).getObstructionOfLines(),
                                serviceConnectionInspectionsList.get(i).getLinePassingRoads(),
                                serviceConnectionInspectionsList.get(i).getRecommendation(),
                                serviceConnectionInspectionsList.get(i).getForPayment(),
                                serviceConnectionInspectionsList.get(i).getZone(),
                                serviceConnectionInspectionsList.get(i).getBlock(),
                                serviceConnectionInspectionsList.get(i).getFeeder(),
                                serviceConnectionInspectionsList.get(i).getBillDeposit(),
                                serviceConnectionInspectionsList.get(i).getLoadType(),
                                serviceConnectionInspectionsList.get(i).getRate(),
                                serviceConnectionInspectionsList.get(i).getMeteringType()
                                );
                        serviceConnectionInspectionsDao.insertAll(newLocalSC);
                    } else {
                        serviceConnectionInspectionsDao.updateServiceConnectionInspections(localServiceConnectionInspections);
                    }
                }

                return null;
            } catch (Exception e){
                Log.e("ERR", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            Log.e("INSPCT_DWNLD_STATUS", "Downloaded " + serviceConnectionInspectionsList.size() + " inspections data");
        }
    }

    class FetchDownloadableServiceConnections extends AsyncTask<List<ServiceConnections>, Void, Void> {

        @Override
        protected Void doInBackground(List<ServiceConnections>... lists) {
            for (int i=0; i<lists[0].size(); i++) {
                ServiceConnectionsDao serviceConnectionsDao = db.serviceConnectionsDao();
                LocalServiceConnections localServiceConnections = serviceConnectionsDao.getOne(lists[0].get(i).getId());

                if (localServiceConnections == null) {
                    serviceConnectionsList.add(lists[0].get(i));
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            serviceConnectionsAdapter.notifyDataSetChanged();
        }
    }

    class FetchDownloadableServiceConnectionInspections extends AsyncTask<List<ServiceConnectionInspections>, Void, Void> {

        @Override
        protected Void doInBackground(List<ServiceConnectionInspections>... lists) {
            for (int i=0; i<lists[0].size(); i++) {
                ServiceConnectionInspectionsDao serviceConnectionInspectionsDao = db.serviceConnectionInspectionsDao();
                LocalServiceConnectionInspections localServiceConnectionInspections = serviceConnectionInspectionsDao.getOne(lists[0].get(i).getId());

                if (localServiceConnectionInspections == null) {
                    serviceConnectionInspectionsList.add(lists[0].get(i));
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Log.e("INSPCT_STATUS", "Logged " + serviceConnectionInspectionsList.size() + " inspections to be downloaded");
        }
    }

    public class FetchSettings extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                settings = db.settingsDao().getSettings();
            } catch (Exception e) {
                Log.e("ERR_FETCH_SETTINGS", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if (settings != null) {
                retrofitBuilder = new RetrofitBuilder(settings.getDefaultServer());
                requestPlaceHolder = retrofitBuilder.getRetrofit().create(RequestPlaceHolder.class);

                fetchTotalServiceConnections(userid);
                fetchTotalServiceConnectionInspections(userid);

                download_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DownloadServiceConnections().execute();
                        new DownloadServiceConnectionInspections().execute();
                    }
                });
            } else {
                startActivity(new Intent(Download.this, SettingsActivity.class));
            }
        }
    }

    public String convertListIdToString(List<ServiceConnections> list) {
        try {
            String data = "";
            int i=0;
            int size = list.size();
            for (ServiceConnections serviceConnection : list) {
                if (i == (size -1)) {
                    data += serviceConnection.getId();
                } else {
                    data += serviceConnection.getId() + ",";
                }
                i++;
            }

            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void notifyDownloaded(String data) {
        try {
            Call<Void> notifCall = requestPlaceHolder.notifyDownloadedInspection(data);

            notifCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.e("NOTIF_SUCCESS", "Downloaded data notified");
                    } else {
                        try {
                            Log.e("NOTIF_ERROR", "Downloaded data error notifying: " + response.errorBody().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("NOTIF_ERROR", "Downloaded data error notifying: " + t.getMessage());
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}