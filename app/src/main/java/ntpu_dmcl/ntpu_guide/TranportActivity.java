package ntpu_dmcl.ntpu_guide;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by TravorLee on 2016/11/30.
 */

public class TranportActivity extends Activity{
    private WebView tr_map ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport);
        tr_map = (WebView)findViewById(R.id.transportPage);
        tr_map.loadUrl("http://www.ntpu.edu.tw/chinese/about/contact6.php");
    }
}
