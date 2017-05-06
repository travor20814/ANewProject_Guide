package ntpu_dmcl.ntpu_guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by chenhaowei on 2017/5/7.
 */

public class CantLocate extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cantlocate);
        Button reload = (Button) findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(CantLocate.this, MainActivity.class);
                startActivity(i);
                CantLocate.this.finish();
            }
        });
    }
}
