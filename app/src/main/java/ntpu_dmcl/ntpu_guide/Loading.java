package ntpu_dmcl.ntpu_guide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chenhaowei on 2017/5/6.
 */

public class Loading extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        new checkgps().execute();
    }
    public void checkGPS(){
        final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_FINE_LOCATION);
        }
    }

    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    class checkgps extends AsyncTask<Void,Void, Void> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected  Void doInBackground(Void... param) {
            try {
                checkGPS();
                LocationManager locationManager
                        = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                // 通過GPS衛星定位，定位級別可以精確到街（通過24顆衛星定位，在室外和空曠的地方定位準確、速度快）
                while (ContextCompat.checkSelfPermission( Loading.this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED||
                        !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||
                        !isConnected())
                {
                    Log.e("tem","tem");
                    Thread.sleep(3000);
                }
                //Thread.sleep(3000);
            }
            catch (InterruptedException e){

            }

            return null;
        }
        @Override
        protected void onPostExecute( Void result) {
            super.onPostExecute(result);
            Intent i = new Intent();
            i.setClass(Loading.this, MainActivity.class);
            startActivity(i);
            Loading.this.finish();
        }
    }
}
