package ntpu_dmcl.ntpu_guide;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView map ;
        setContentView(R.layout.activity_main);

        map = (WebView)findViewById(R.id.navigationMap);
        map.getSettings().setJavaScriptEnabled(true);
        map.loadUrl("file:///android_asset/GMap.html");
    }
}
