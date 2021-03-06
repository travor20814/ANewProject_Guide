package ntpu_dmcl.ntpu_guide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
//import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity implements LocationListener  {

    //final private String testlon = "121.367838";
    //final private String testlat = "24.942454";
    private WebView wv_map ;
    private LocationManager locationManager;
    private boolean startUse = false;
    private boolean userback = false;
    private String oldPlace="";
    private String transportLink = "";
    private String publicArtLink = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.e("tem","tem");
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
        new catchListName().execute();
        new catchWebLinks().execute();
    }

    @Override
    public void onProviderDisabled(String arg0) {//當GPS或網路定位功能關閉時
        Toast.makeText(this, "請開啟gps或3G網路", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onProviderEnabled(String arg0) { //當GPS或網路定位功能開啟
        locationServiceInitial();
        //Log.e("GPS","enable");
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
            //Log.e("lati",latitude);
            //Log.e("lngi", longitude);
            if(startUse) {
                wv_map.loadUrl("javascript:direct(\"" + latitude + "\",\"" + longitude + "\")");
                //wv_map.loadUrl("javascript:direct(\"" + testlat + "\",\"" + testlon + "\")");
                if(!oldPlace.equals(""))
                    wv_map.loadUrl("javascript:drawerNavigation(\"" + oldPlace + "\")");
            }
            else{
                wv_map.loadUrl("javascript:initMap(\"" + latitude + "\",\"" + longitude + "\")");
                //wv_map.loadUrl("javascript:initMap(\"" + testlat + "\",\"" + testlon + "\")");

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
        isConnected();
        LocationManager locationManager
                = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Log.e("GPS","enable");
            locationServiceInitial();
        }
        if(!oldPlace.equals(""))
        wv_map.loadUrl("javascript:drawerNavigation(\"" + oldPlace + "\")");
       // Log.i("main resume oldplace",oldPlace);
    }
    @Override
    protected void onPause() {
        super.onPause();
        try {
            locationManager.removeUpdates(this);//離開頁面時停止更新
        }
        catch (SecurityException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            if(!userback) {
                Toast.makeText(this, "再按一次離開", Toast.LENGTH_SHORT).show();

                userback=true;
                Timer backTimer = new Timer(true);
                backTimer.schedule(new backTimer(), 2000);
            }
            else {
                finish();
            }
        }
        return false;
    }

    /*
    function




     */
    private void Drawerset(final ArrayList<String> parentItems_A, final ArrayList<Object> childItems_A,final ArrayList<String> parentItems_T, final ArrayList<Object> childItems_T){

        //ViewGroup header_t =(ViewGroup) getLayoutInflater().inflate(R.layout.drawer_header_teach,null);
        ExpandableListView expandableListView_t = (ExpandableListView) findViewById(R.id.main_leftList);
        //expandableListView_t.addHeaderView(header_t,null,false);
        ExpandListAdapter adapter_t = new ExpandListAdapter(parentItems_T,childItems_T);
        adapter_t.setInflater((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE),MainActivity.this);
        expandableListView_t.setAdapter(adapter_t);

/*admin list set*/
        Button setadmin = (Button) findViewById(R.id.admin);
        Button setteach = (Button) findViewById(R.id.teach);


        setadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ViewGroup header_a =(ViewGroup) getLayoutInflater().inflate(R.layout.drawer_header_admin,null);
                ExpandableListView expandableListView_a = (ExpandableListView) findViewById(R.id.main_leftList);
                expandableListView_a.setBackgroundResource(R.drawable.drawer_admin_list);
                //expandableListView_a.addHeaderView(header_a,null,false);
                ExpandListAdapter adapter_a = new ExpandListAdapter(parentItems_A,childItems_A);
                adapter_a.setInflater((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE),MainActivity.this);
                expandableListView_a.setAdapter(adapter_a);
            }
        });

/*teach list set*/
        setteach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ViewGroup header_t =(ViewGroup) getLayoutInflater().inflate(R.layout.drawer_header_teach,null);
                ExpandableListView expandableListView_t = (ExpandableListView) findViewById(R.id.main_leftList);
                //expandableListView_t.addHeaderView(header_t,null,false);
                expandableListView_t.setBackgroundResource(R.drawable.drawer_teach_list);
                ExpandListAdapter adapter_t = new ExpandListAdapter(parentItems_T,childItems_T);
                adapter_t.setInflater((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE),MainActivity.this);
                expandableListView_t.setAdapter(adapter_t);
            }
        });

/*buuton set*/
        Button toSchool = (Button) findViewById(R.id.drawer_toSchool);
        toSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv_map.loadUrl("javascript:drawerNavigation(\"三峽校區正門\")");
                //oldPlace ="三峽校區正門" ;
                closeDrawer();
            }
        });
        Button notice  = (Button) findViewById(R.id.drawer_notice);
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(MainActivity.this,NoticeActivity.class);
                startActivity(i);
            }
        });

    }
    public void closeDrawer(){
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer) ;
        drawerLayout.closeDrawer(GravityCompat.START);
    }
    private void openDrawer(){
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer) ;
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void locationServiceInitial() {
        try {
            Location bestProviderLocation;
            final String gps = LocationManager.GPS_PROVIDER;
            final String network = LocationManager.NETWORK_PROVIDER;
            //Log.e("locationInitial","suspend");
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
            locationManager.requestLocationUpdates(bestProvider, 3000, 10, this);
            bestProviderLocation = locationManager.getLastKnownLocation(network);
            //Log.i("best",String.valueOf(locationManager.isProviderEnabled(bestProvider)));
            //Log.i("gps",String.valueOf(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)));
            //Log.i("network",String.valueOf(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)));
            if (bestProviderLocation != null) {
                //Log.e("getLocationWay",bestProvider);
                this.onLocationChanged(bestProviderLocation);
            } else {
                locationManager.removeUpdates(this);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000,10, this);
                bestProviderLocation = locationManager.getLastKnownLocation(bestProvider);
                if (bestProviderLocation != null) {
                    // Log.e("getLocationWay","GPS");
                    this.onLocationChanged(bestProviderLocation);
                } else {
                    locationManager.removeUpdates(this);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, this);
                    bestProviderLocation = locationManager.getLastKnownLocation(gps);
                    if (bestProviderLocation != null) {
                        // Log.e("getLocationWay","NETWORK");
                        this.onLocationChanged(bestProviderLocation);
                    } else {
                        Toast.makeText(this, "無法定位座標", Toast.LENGTH_SHORT).show();
                        locationManager.removeUpdates(this);
                        //locationServiceInitial();
                        Intent i = new Intent();
                        i.setClass(MainActivity.this, CantLocate.class);
                        startActivity(i);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }
            }


        }
        catch (SecurityException e){
            e.printStackTrace();
        }
    }


    public void OnNavigationClick(String aim){
        isConnected();
        wv_map.loadUrl("javascript:drawerNavigation(\"" + aim + "\")");
        //oldPlace =aim ;

    }

    private void showFloorDialog(int number)
    {
        floorDialog editNameDialog = new floorDialog().newInstance(number);
        editNameDialog.show(getFragmentManager(), "EditNameDialog");
    }


    /*
    class




     */
    private class WebViewClientDemo extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //在这里执行你想调用的js函数
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //Log.e("GPS","enable");
                locationServiceInitial();
            }
            //Log.i("mainactivity","webview be reset");
        }

    }

    private class backTimer extends TimerTask
    {
        public void run()
        {
            userback = false;
        }
    }
    private class closeDrawerThread extends AsyncTask<Void,Void,Void> {
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
    private class catchSqlData extends AsyncTask<String,Void, ArrayList<HashMap<String, String>>> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            isConnected();
        }
        @Override
        protected  ArrayList<HashMap<String, String>> doInBackground(String... param) {
            return new connectToSQL("select * from places where name ='"+param[0]+"'").getServerConnect();
        }
        @Override
        protected void onPostExecute( ArrayList<HashMap<String, String>> result) {
            super.onPostExecute(result);
            // 展現圖片
           // Log.e("name",result.get(0).get("description"));
            showFloorDialog(Integer.parseInt(result.get(0).get("number")));
        }
    }

    private class catchListName extends AsyncTask<Integer,Void,String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }
        @Override
        protected  String doInBackground(Integer... param) {
            return new getSqlString("select distinct Pname,Cname from list_name where `group` = 0  order by sequence ASC" ).getServerConnect()+
                    "&&&"+
                    new getSqlString("select distinct Pname,Cname from list_name where `group` = 1  order by sequence ASC").getServerConnect();
        }
        @Override
        protected void onPostExecute( String result) {
            super.onPostExecute(result);
            result=result.replace("<br />",System.getProperty("line.separator"));
            result=result.replace("&nbsp;","  ");
            String[] AandT = result.split("&&&");
           // Log.e("result",result);
            /////////////////////////////////////////////////////////////
            if(!(AandT[0].equals("wrong")||AandT[1].equals("wrong"))) {
                ArrayList<String> parentItems_A = new ArrayList<String>();
                ArrayList<Object> childItems_A = new ArrayList<Object>();
                ArrayList<String> child_A = new ArrayList<String>();
                ArrayList<String> parentItems_T = new ArrayList<String>();
                ArrayList<Object> childItems_T = new ArrayList<Object>();
                ArrayList<String> child_T = new ArrayList<String>();
                ////////////////////////////////////////////////////////////
                String[] items_A = AandT[0].split("@@@@@");
                String oldcontent_A = "";
                for (int i = 0; i < items_A.length; i++) {
                    String[] content = items_A[i].split("###");
                    if (!content[0].equals(oldcontent_A)) {
                        parentItems_A.add(content[0]);
                        oldcontent_A = content[0];
                        if (i != 0) {
                            childItems_A.add(child_A);
                        }
                        child_A = new ArrayList<String>();
                    }
                    child_A.add(content[1]);
                    if (i == items_A.length - 1) {
                        childItems_A.add(child_A);
                        child_A = new ArrayList<String>();
                    }
                }
                ////////////////////////////////////////////////////////////////
                String[] items_T = AandT[1].split("@@@@@");
                String oldcontent_T = "";
                for (int i = 0; i < items_T.length; i++) {
                    String[] content = items_T[i].split("###");
                    if (!content[0].equals(oldcontent_T)) {
                        parentItems_T.add(content[0]);
                        oldcontent_T = content[0];
                        if (i != 0) {
                            childItems_T.add(child_T);
                        }
                        child_T = new ArrayList<String>();
                    }
                    child_T.add(content[1]);


                    if (i == items_T.length - 1) {
                        childItems_T.add(child_T);
                        child_T = new ArrayList<String>();
                    }
                }
                //////////////////////////////////////////////////////////
                Drawerset(parentItems_A, childItems_A, parentItems_T, childItems_T);
            }
        }
    }

    private class catchWebLinks extends AsyncTask<Integer,Void,String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected  String doInBackground(Integer... param) {
            return new getSqlString("select * from links" ).getServerConnect();
        }
        @Override
        protected void onPostExecute( String result) {
            super.onPostExecute(result);
            String[] linkWrap = result.split("@@@@@");
            for (int i = 0; i < linkWrap.length; i++) {
                String[] content = linkWrap[i].split("###");

                switch (content[0]) {
                    case "交通資訊": {
                        transportLink = content[1];
                        break;
                    }
                    case "公共藝術": {
                        publicArtLink = content[1];
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        }
    }

    /*
    @JavascriptInterface




     */
    @JavascriptInterface
    public void GetLonLat(float Lati,float Long)
    {
        //Log.e("La+Lo",String.valueOf(Lati)+"\t"+String.valueOf(Long));
    }
    @JavascriptInterface
    public void GetOldplace(String place )
    {
        oldPlace = place;
    }
    @JavascriptInterface
    public void setStartUse(){
        startUse =true ;
    }
    @JavascriptInterface
    public void htmlOpendrawer(){

        new closeDrawerThread().execute();
    }
    @JavascriptInterface
    public void getWhereToGo(String name){
        new catchSqlData().execute(name);
    }
    @JavascriptInterface
    public void OnInfoClick(String name){
        isConnected();
        //Log.e("infoclick",name);
        if(name.equals("公共藝術"))
        {
            Intent i = new Intent();
            i.setClass(MainActivity.this, publicArtActivity.class);
            i.putExtra("LINK", publicArtLink);
            startActivity(i);
        }
        else {
            Intent i = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            i.putExtras(bundle);
            i.setClass(MainActivity.this, InfoActivity.class);
            startActivity(i);
        }
    }
    @JavascriptInterface
    public void OnTransportClick() {
        isConnected();
        Intent i = new Intent();
        i.setClass(MainActivity.this, TranportActivity.class);
        i.putExtra("LINK", transportLink);
        startActivity(i);
    }
    @JavascriptInterface
    public void isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            Intent i = new Intent();
            i.setClass(MainActivity.this, CantLocate.class);
            startActivity(i);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
