package ntpu_dmcl.ntpu_guide;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by user on 2016/10/19.
 */
public class getListName {
    String sqlCommand ;
    public getListName(String sqlCommand){
        try {
            this.sqlCommand= URLEncoder.encode(sqlCommand, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public String getServerConnect(){
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
            //Log.v("sb",sb.toString());
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

            return getFromServer;
    }
}
