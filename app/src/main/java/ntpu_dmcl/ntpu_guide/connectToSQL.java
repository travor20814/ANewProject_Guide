package ntpu_dmcl.ntpu_guide;

//import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2016/9/12.
 */
public class connectToSQL {
    String sqlCommand ;
    public connectToSQL(String sqlCommand){
        try {
            this.sqlCommand= URLEncoder.encode(sqlCommand, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
     public ArrayList<HashMap<String, String>> getServerConnect() {
         String sqlurl = "http://dmcl.twbbs.org/NTPU_guidance/haowei_query.php?sql=";
         String getFromServer = new String();
         try {
             String connectToDB = sqlurl + sqlCommand;
             //Log.v("connect1", connectToDB);
             URL url = new URL(connectToDB);
             InputStream in = url.openStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(in));
             String str = new String();
             StringBuilder sb = new StringBuilder();
             while ((str = br.readLine()) != null) {
                 sb.append(str);
             }
             //Log.v("connect",sb.toString());
             String[] check = sb.toString().split(":");

             if (check[0].equals("Warning"))
                 getFromServer = "wrong";
             else
                 getFromServer = sb.toString();

         } catch (MalformedURLException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }

         if (getFromServer.equals("") || getFromServer.equals("wrong")) {
             ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
             HashMap<String, String> map = new HashMap<String, String>();
             map.put("ID", "暫時沒有");
             list.add(map);
             return list;
         } else {
             String[] items = getFromServer.split("@@@@@");
             ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
             for (int i = 0; i < items.length; i++) {
                 String[] content = items[i].split("###");
                 HashMap<String, String> map = new HashMap<String, String>();
                 map.put("name", content[0]);
                 map.put("longitude", content[1]);
                 map.put("latitude", content[2]);
                 map.put("description", content[3]);
                 map.put("number", content[4]);
                 //Log.e("name",content[0]);
                 list.add(map);
             }
             return list;
         }
     }
}
