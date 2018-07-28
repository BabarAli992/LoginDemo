package com.example.latitude.logindemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.SyncStateContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.latitude.logindemo.Common.Common;
import com.example.latitude.logindemo.Helper.Helper;
import com.example.latitude.logindemo.Helper.Helper;
import com.example.latitude.logindemo.Model.OpenWeatherMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.time.Instant;

import javax.sql.CommonDataSource;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SecondActivity extends AppCompatActivity implements LocationListener {

    private TextView txtCty;
    private TextView txtDescription;
    private ImageView imageView;
    private TextView txtLastUpdatee;
    private TextView txtTime;
    private TextView txtCelsius;
    private TextView txtHumidity;
    LocationManager locationManager;
    String provider;
    static double lat, lon;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();
    int MY_PERMISSION = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //control

        txtCty = (TextView) findViewById(R.id.txtCty);
        txtLastUpdatee = (TextView) findViewById(R.id.txtLastUpdatee);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtCelsius = (TextView) findViewById(R.id.txtCelsius);
        imageView = (ImageView) findViewById(R.id.imageView);
        txtHumidity = (TextView) findViewById(R.id.txtHumidity);


        //Get Coordinates

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        if (ActivityCompat.checkPremission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SecondActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.ACCESS_NETWORK_STATE
            }, MY_PERMISSION);

        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location == null)
            Log.e("TAG", "No Location");


    }

    @Override
    public void onPause() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SecondActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.ACCESS_NETWORK_STATE
            }, MY_PERMISSION);


            super.onPause();
        }
    }


    @Override
    public void onResume() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SecondActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.ACCESS_NETWORK_STATE
            }, MY_PERMISSION);


        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
        super.onResume();
   }

    @Override
    public void onLocationChanged(Location location) {

        lat = location.getLatitude();
        lon = location.getLongitude();
        new getWeather().execute(Common.api_request(String.valueOf(lat),String.valueOf(lon)));

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {


    }
    private class getWeather extends AsyncTask<String,Void,String> {
        ProgressDialog pd = new ProgressDialog(SecondActivity.this);


        @Override
        protected void onPreExecute(){

            super.onPreExecute();
            pd.setTitle("Please Wait....");
            pd.show();

        }


        @Override
        protected String doInBackground(String... params)  {
            String stream = null;
            String urlString = params[0];
            Helper http = new Helper();
            stream = http.getHttpData(urlString);
            return stream;


        }



        @Override
        protected void  onPostExecute(String s){
            if (s.contains("Error!City not Found")){
                pd.dismiss();
                return;
            }
            Gson gson = new Gson();
            Type mtype = new TypeToken<OpenWeatherMap>(){}.getType();
            openWeatherMap = gson.fromJson(s,mtype);
            pd.dismiss();
            txtCty.setText(String.format("%s,%s ",openWeatherMap.getName(),openWeatherMap.getSys().getCountry()));
            txtLastUpdatee.setText(String.format("Last Update: %s", Common.getDateNow()));
            txtDescription.setText(String.format("%s",openWeatherMap.getWeatherList().get(0).getDescription()));
            txtHumidity.setText(String.format("%d%%",openWeatherMap.getMain().getHumidity()));
            txtTime.setText(String.format("%s/@s",Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise()),Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunset())));
            txtCelsius.setText(String.format("%.2f °C",openWeatherMap.getMain().getTemp()));
            Picasso.with(SecondActivity.this)
                    .load(Common.getImage(openWeatherMap.getWeatherList()
                            .get(0).getIcon())).into(imageView);


            super.onPostExecute(s);
        }
    }



}
