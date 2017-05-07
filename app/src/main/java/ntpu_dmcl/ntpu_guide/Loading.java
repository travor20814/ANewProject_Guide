package ntpu_dmcl.ntpu_guide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
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
        return (networkInfo != null && networkInfo.isConnected());
    }

    private class checkgps extends AsyncTask<Void,Void, Void> {
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
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute( Void result) {
            super.onPostExecute(result);
            String command = "select version_code ,version_name from version ";
            new checkversion().execute(command);
            Intent i = new Intent();
            i.setClass(Loading.this, MainActivity.class);
            startActivity(i);
            Loading.this.finish();
        }
    }
    private class checkversion extends AsyncTask<String,Void, String> {
        @Override
        protected  String doInBackground(String... param) {
            return new getSqlString(param[0]).getServerConnect();
        }
        @Override
        protected void onPostExecute( String result) {
            super.onPostExecute(result);
            if(result.equals("wrong")){
                Toast.makeText(Loading.this, "version無法取得", Toast.LENGTH_SHORT).show();
            }
            else {
                try {

                    PackageInfo pkgInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    int verCode = pkgInfo.versionCode;
                    String verName = pkgInfo.versionName;
                    String ver_Code = String.valueOf(verCode);
                    //Log.e("vercode",ver_Code);
                    String tem[] = result.split("@@@@@");
                    String data[] = tem[0].split("###");
                    //Log.e("data",data[0]+"\t"+data[1]);
                    if (!data[0].equals(ver_Code)||!data[1].equals(verName))
                    {
                        Toast.makeText(Loading.this, "有新版本可以更新喔！", Toast.LENGTH_LONG).show();
                    }
                }
                catch (PackageManager.NameNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
