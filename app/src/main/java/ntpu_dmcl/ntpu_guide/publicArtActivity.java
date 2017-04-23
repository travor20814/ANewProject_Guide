package ntpu_dmcl.ntpu_guide;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by chenhaowei on 2017/4/23.
 */

public class publicArtActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicart);
        toolbarSet();
        setTitle(R.string.PA);
        WebView wv = (WebView)findViewById(R.id.webview);
        WebSettings settings = wv.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        wv.loadUrl("http://www.ntpu.edu.tw/repair/art_02_01.html");


    }

    public void toolbarSet(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.pa_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.rgb(63, 81, 181));
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
