package ntpu_dmcl.ntpu_guide;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

/**
 * Created by user on 2016/9/13.
 */
public class floorDialog extends DialogFragment {
    public  floorDialog newInstance (int number){
        floorDialog frag = new floorDialog();
        Bundle args = new Bundle();
        args.putInt("number", number);
        frag.setArguments(args);
        return frag;
    }
    @Override
    public View onCreateView (final LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState)
    {  //******************************
        final int number = getArguments().getInt("number");
        final int[] floorPlans={R.mipmap.floor_plan_csie};
        //*****************************
        final View view = inflater.inflate(R.layout.floor_dialog, container);
        WebView webView = (WebView) view.findViewById(R.id.floorplan);
        webView.loadUrl("file:///android_asset/images/social_5.Png");
        webView.getSettings().setBuiltInZoomControls(true);
        return view;
    }
    @Override
    public void onCancel (DialogInterface dialog){
        dismiss();

    }
}
