package ntpu_dmcl.ntpu_guide;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chenhaowei on 2016/8/16.
 */

public class InfoActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<View> pageViews;
    private ImageView imageView;
    private ImageView[] imageViews;
    // 包裹滑动图片LinearLayout
    private ViewGroup main;
    // 包裹小圆点的LinearLayout
    private ViewGroup group;
    @SuppressWarnings("unused")
    private TextView content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        String name = bundle.getString("name");
        setTitle(name);
        ///
        // 设置无标题窗口
        int[] img = new int[] { R.mipmap.ic_action_refresh,R.mipmap.ic_launcher,R.mipmap.image_go,
        R.drawable.d0262};
        LayoutInflater inflater = getLayoutInflater();
        pageViews = new ArrayList<View>();
        for (int i = 0; i < img.length; i++) {
            LinearLayout layout = new LinearLayout(this);
            LayoutParams ltp = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            final ImageView imageView = new ImageView(this);
            imageView.setScaleType(ScaleType.CENTER_INSIDE);
            imageView.setImageResource(img[i]);
            imageView.setPadding(15, 30, 15, 30);
            imageView.setAdjustViewBounds(true);
            layout.addView(imageView, ltp);
            pageViews.add(layout);
        }
        imageViews = new ImageView[pageViews.size()];
        main = (ViewGroup) inflater.inflate(R.layout.aim_info, null);
        group = (ViewGroup) main.findViewById(R.id.viewGroup);
        viewPager = (ViewPager) main.findViewById(R.id.guidePages);
        //content = (TextView) findViewById(R.id.photo_content);

        /**
         * 有几张图片 下面就显示几个小圆点
         */

        for (int i = 0; i < pageViews.size(); i++) {
            LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置每个小圆点距离左边的间距
            margin.setMargins(10, 0, 0, 0);
            imageView = new ImageView(InfoActivity.this);
            //设置每个小圆点的宽高
            //imageView.setLayoutParams(new ViewGroup.LayoutParams(5, 5));
            imageViews[i] = imageView;
            if (i == 0) {
                // 默认选中第一张图片
                imageViews[i]
                        .setBackgroundResource(R.drawable.focused_circle);
            } else {
                //其他图片都设置未选中状态
                imageViews[i]
                        .setBackgroundResource(R.drawable.unfocused_circle);
            }
            group.addView(imageViews[i], margin);
        }
        setContentView(main);
        //给viewpager设置适配器
        viewPager.setAdapter(new GuidePageAdapter());
        //给viewpager设置监听事件
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
        toolbarSet();
        new catchInfo().execute(name);
    }

    public void toolbarSet(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.info_toolbar);
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
    // 指引页面数据适配器
    class GuidePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            // TODO Auto-generated method stub
            ((ViewPager) arg0).removeView(pageViews.get(arg1));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            // TODO Auto-generated method stub
            ((ViewPager) arg0).addView(pageViews.get(arg1));
            return pageViews.get(arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }
    }

    // 指引页面更改事件监听器
    class GuidePageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            //遍历数组让当前选中图片下的小圆点设置颜色
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0]
                        .setBackgroundResource(R.drawable.focused_circle);

                if (arg0 != i) {
                    imageViews[i]
                            .setBackgroundResource(R.drawable.unfocused_circle);
                }
            }
        }
    }

    class catchInfo extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }
        @Override
        protected  String doInBackground(String... param) {
            return new getSqlString("select location , description ,howcome, connectway from places where name ='"+param[0]+"'").getServerConnect();
        }
        @Override
        protected void onPostExecute( String result) {
            super.onPostExecute(result);
            result=result.replace("<br />",System.getProperty("line.separator"));
            result=result.replace("&nbsp;","  ");
            String tem[] = result.split("@@@@@");
            String data[] = tem[0].split("###");
            TextView location = (TextView) findViewById(R.id.location);
            TextView history = (TextView) findViewById(R.id.history);
            TextView howcome = (TextView) findViewById(R.id.howtocome);
            TextView connectway = (TextView) findViewById(R.id.connectway);
            location.setText(data[0]);
            history.setText(data[1]);
            howcome.setText(data[2]);
            connectway.setText(data[3]);

        }
    }
}