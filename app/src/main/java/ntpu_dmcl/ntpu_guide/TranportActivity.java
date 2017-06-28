package ntpu_dmcl.ntpu_guide;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by TravorLee on 2016/11/30.
 */

public class TranportActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport);
        WebView tr_map ;
        String url = getIntent().getStringExtra("LINK");
        tr_map = (WebView)findViewById(R.id.transportPage);
        if (url != null) {
            tr_map.loadUrl(url); // get links from database
        } else {
            tr_map.loadUrl("http://www.ntpu.edu.tw/chinese/about/contact6.php");
        }
    }
}
