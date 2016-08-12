package ntpu_dmcl.ntpu_guide;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity implements LocationListener  {

    private WebView wv_map ;
    private LocationManager locationManager;
    private Location bestProviderLocation;
    final private String gps = LocationManager.GPS_PROVIDER;
    final private String network = LocationManager.NETWORK_PROVIDER;
    private boolean startUse = false;
    private boolean userback = false;
    private String[] list_parent = {
            "瀏覽","大樓","科系"
    };
    private String[][] list_child = {
            {"haha"},
            {"人文大樓","社會科學大樓"},
            {"資訊工程學系"},
    };
    /*
    @Override




     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wv_map = (WebView)findViewById(R.id.navigationMap);
        wv_map.getSettings().setJavaScriptEnabled(true);
        wv_map.getSettings().setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) { //allow xml send request from file html
            wv_map.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
        wv_map.setWebViewClient(new WebViewClientDemo());
        wv_map.addJavascriptInterface(this, "Android");
        wv_map.setWebChromeClient(new WebChromeClient()); //allow javascript alert
        wv_map.loadUrl("file:///android_asset/GMap.html");
        Drawerset();
    }

    @Override
    public void onProviderDisabled(String arg0) {//當GPS或網路定位功能關閉時
        // TODO 自動產生的方法 Stub
        Toast.makeText(this, "請開啟gps或3G網路", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onProviderEnabled(String arg0) { //當GPS或網路定位功能開啟
        // TODO 自動產生的方法 Stub
    }
    @Override
    public void onLocationChanged(Location location) {
        if(location != null) {
            double d_lati = location.getLatitude();
            double d_lngi = location.getLongitude();
            String latitude = String.valueOf(d_lati);
            String longitude = String.valueOf(d_lngi);
            //if (Math.abs(d_lati - Double.valueOf(EndLat)) <= 0.000003 && Math.abs(d_lngi - Double.valueOf(EndLon)) <= 0.000003) {
           //     showEditDialog();
            //}
            /*
            it is a bad way to check locate or not,but i'm lazy
             */
            Log.e("lati",latitude);
            Log.e("lngi", longitude);
            if(startUse) {
                wv_map.loadUrl("javascript:direct(\"" + latitude + "\",\"" + longitude + "\")");
                //wv_map.loadUrl("javascript:direct(" + "24.937190"+ "," + "121.361814" + ")");
            }
            else{
                wv_map.loadUrl("javascript:initMap(\"" + latitude + "\",\"" + longitude + "\")");
            }
        }
        else{
            // Log.e("getlocation","2");
            Toast.makeText(this, "無法定位座標", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                MainActivity.this.setTitle("AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                MainActivity.this.setTitle("OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                MainActivity.this.setTitle("TEMPORARILY_UNAVAILABLE");
                break;
        }

    }
    @Override
    protected void onResume(){
        super.onResume();
        locationServiceInitial();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        checkGPS();
        try {
            locationManager.removeUpdates(this);//離開頁面時停止更新
        }
        catch (SecurityException e){

        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            if(userback==false) {
                Toast.makeText(this, "再按一次離開", Toast.LENGTH_SHORT).show();

                userback=true;
                Timer backTimer = new Timer(true);
                backTimer.schedule(new backTimer(), 2000);
            }
            else if(userback == true){
                finish();
            }
        }
        return false;
    }

    /*
    function




     */
    private void Drawerset(){
        ArrayList<String> parentItems = new ArrayList<String>();
        ArrayList<Object> childItems = new ArrayList<Object>();
        ArrayList<String> child ;

        for(int i=0;i<list_parent.length;i++){
            child = new ArrayList<String>();
            parentItems.add(list_parent[i]);
            for(int j=0;j<list_child[i].length;j++){
                child.add(list_child[i][j]);
            }
            childItems.add(child);
        }

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.main_leftList);
        ExpandListAdapter adapter = new ExpandListAdapter(parentItems,childItems);
        adapter.setInflater((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE),this);
        expandableListView.setAdapter(adapter);

    }
    public void closeDrawer(){
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer) ;
        drawerLayout.closeDrawer(GravityCompat.START);
    }
    public void openDrawer(){
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer) ;
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void locationServiceInitial() {
        try {
            checkGPS();
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); //取得系統定位服務
            Criteria criteria = new Criteria();
            // 获得最好的定位效果
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(false);
            // 使用省电模式
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            String bestProvider = locationManager.getBestProvider(criteria, true);
            //Log.e("bestprovider", bestProvider);
            locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
            bestProviderLocation = locationManager.getLastKnownLocation(network);
            //Log.i("best",String.valueOf(locationManager.isProviderEnabled(bestProvider)));
            //Log.i("gps",String.valueOf(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)));
            //Log.i("network",String.valueOf(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)));
            if (bestProviderLocation != null) {
                //Log.e("getLocationWay",bestProvider);
                this.onLocationChanged(bestProviderLocation);
            } else {
                locationManager.removeUpdates(this);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                bestProviderLocation = locationManager.getLastKnownLocation(bestProvider);
                if (bestProviderLocation != null) {
                    // Log.e("getLocationWay","GPS");
                    this.onLocationChanged(bestProviderLocation);
                } else {
                    locationManager.removeUpdates(this);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                    bestProviderLocation = locationManager.getLastKnownLocation(gps);
                    if (bestProviderLocation != null) {
                        // Log.e("getLocationWay","NETWORK");
                        this.onLocationChanged(bestProviderLocation);
                    } else {
                        Toast.makeText(this, "無法定位座標", Toast.LENGTH_SHORT).show();
                        locationManager.removeUpdates(this);
                        locationServiceInitial();
                    }
                }
            }


        }
        catch (SecurityException e){

        }
    }
    /*
    確認gps跟 network 可以用
     */
    public void checkGPS(){
        final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
        final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_FINE_LOCATION);
        }
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);
        }
    }
    public void OnNavigationClick(String aim){
        wv_map.loadUrl("javascript:drawerNavigation(\"" + aim + "\")");

    }
    /*
    class




     */
    private class WebViewClientDemo extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //在这里执行你想调用的js函数
            locationServiceInitial();

        }

    }

    public class backTimer extends TimerTask
    {
        public void run()
        {
            userback = false;
        }
    }
    class closeDrawerThread extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... parm) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            openDrawer();
        }


    }
    /*
    @JavascriptInterface




     */
    @JavascriptInterface
    public void GetLonLat(float Lati,float Long)
    {
        Log.e("La+Lo",String.valueOf(Lati)+"\t"+String.valueOf(Long));
    }
    @JavascriptInterface
    public void setStartUse(){
        startUse =true ;
    }
    @JavascriptInterface
    public void htmlOpendrawer(){

        new closeDrawerThread().execute();
    }
}
