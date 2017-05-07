package ntpu_dmcl.ntpu_guide;


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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chenhaowei on 2016/8/16.
 */

public class InfoActivity extends AppCompatActivity {

    private ArrayList<View> pageViews;
    private ImageView[] imageViews;
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
    public int[][] imageArray(){
        int[][] images = {
                {//1. 法學大樓(院長室),//41,67,
                        R.drawable.img_41,R.drawable.img_67
                },
                {//2. 法律學系,//41,67,
                        R.drawable.img_41,R.drawable.img_67
                },
                {//3. 比較法資料中心,//40,67,
                        R.drawable.img_40,R.drawable.img_67
                },
                {//4. 商學大樓(院長室),//2,57,66,

                        R.drawable.img_2,R.drawable.img_57,R.drawable.img_67
                },
                {//5. 企業管理學系,//8,9,14,15,66,

                        R.drawable.img_8,R.drawable.img_9,R.drawable.img_14,R.drawable.img_15,R.drawable.img_66
                },
                {//6. 金融與合作經營學系,//18,66,

                        R.drawable.img_18,R.drawable.img_66
                },
                {//7. 會計學系,//16,66,

                        R.drawable.img_16,R.drawable.img_66
                },
                {//8. 統計學系,//17,66,

                        R.drawable.img_17,R.drawable.img_66
                },
                {//9. 休閒運動管理學系,//2,66,

                        R.drawable.img_2,R.drawable.img_66
                },
                {//10. 資訊管理研究所,//18,66,

                        R.drawable.img_18,R.drawable.img_66
                },
                {//11. 國際企業研究所,//18,66,

                        R.drawable.img_18,R.drawable.img_66
                },
                {//12. 財務金融英語碩士學位學程, (找無資料)//3,4,5,66,

                        R.drawable.img_3,R.drawable.img_4,R.drawable.img_5,R.drawable.img_66
                },
                {//13. 電子商務研究中心,//2,56,66,

                        R.drawable.img_2,R.drawable.img_56,R.drawable.img_66
                },
                {//14. 合作經濟暨非營利事業研究中心,//2,55,66,

                        R.drawable.img_2,R.drawable.img_55,R.drawable.img_66
                },
                {//15. 公共事務大樓(院長室),//68,70,

                        R.drawable.img_68,R.drawable.img_70
                },
                {//16. 公共行政暨政策學系,//29,68,70,

                        R.drawable.img_29,R.drawable.img_68,R.drawable.img_70
                },
                {//17. 財政學系,//33,34,68,70,

                        R.drawable.img_33,R.drawable.img_34,R.drawable.img_68,R.drawable.img_70
                },
                {//18. 不動產與城鄉環境學系,//31,32,68,70,

                        R.drawable.img_31,R.drawable.img_32,R.drawable.img_68,R.drawable.img_70
                },
                {//19. 都市計劃研究所,//35,36,68,70,

                        R.drawable.img_35,R.drawable.img_36,R.drawable.img_68,R.drawable.img_70
                },
                {//20. 自然資源與環境管理研究所,//37,38,68,70,

                        R.drawable.img_37,R.drawable.img_38,R.drawable.img_68,R.drawable.img_70
                },
                {//21. 城市治理英語碩士學位學程,//68,69,70,

                        R.drawable.img_68,R.drawable.img_69,R.drawable.img_70
                },
                {//22. 民意與選舉研究中心,//39,68,70,

                        R.drawable.img_39,R.drawable.img_68,R.drawable.img_70
                },
                {//23. 土地與環境規劃研究中心,//68,70,

                        R.drawable.img_68,R.drawable.img_70
                },
                {//24. 社會科學大樓(院長室),//26,72,

                        R.drawable.img_26,R.drawable.img_72
                },
                {//25. 經濟學系,//26,72,73,

                        R.drawable.img_26,R.drawable.img_72,R.drawable.img_73
                },
                {//26. 社會學系,//26,72,74,

                        R.drawable.img_26,R.drawable.img_72,R.drawable.img_74
                },
                {//27. 社會工作學系,//25,26,72,

                        R.drawable.img_25,R.drawable.img_26,R.drawable.img_72
                },
                {//28. 犯罪學研究所,//21,26,72,

                        R.drawable.img_21,R.drawable.img_26,R.drawable.img_72
                },
                {//29. 台灣發展研究中心,//26,72,

                        R.drawable.img_26,R.drawable.img_72
                },
                {//30. 人文大樓(院長室),//64,

                        R.drawable.img_64
                },
                {//31. 中國文學系,//64,80,89,

                        R.drawable.img_64,R.drawable.img_80,R.drawable.img_89
                },
                {//32. 應用外語學系,//64,79,

                        R.drawable.img_64,R.drawable.img_79
                },
                {//33. 歷史學系,//64,83,

                        R.drawable.img_64,R.drawable.img_83
                },
                {//34. 民俗藝術與文化資產研究所,//64,81,82,

                        R.drawable.img_64,R.drawable.img_81,R.drawable.img_82
                },
                {//35. 師資培育中心,//84,

                        R.drawable.img_84
                },
                {//36. 國際談判及同步翻譯中心,//64,85,

                        R.drawable.img_64,R.drawable.img_85
                },
                {//37. 東西哲學與詮釋學研究中心,//58,64,

                        R.drawable.img_58,R.drawable.img_64
                },
                {//38. 電機資訊大樓(院長室),//26,27,72,

                        R.drawable.img_26,R.drawable.img_27,R.drawable.img_72
                },
                {//39. 資訊工程學系,//20,26,72,

                        R.drawable.img_20,R.drawable.img_26,R.drawable.img_72
                },
                {//40. 通訊工程學系,//20,26,72,

                        R.drawable.img_20,R.drawable.img_26,R.drawable.img_72
                },
                {//41. 電機工程學系,//64,86,88,

                        R.drawable.img_64,R.drawable.img_86,R.drawable.img_88
                },
                {//42. 前瞻科技研究中心,//26,72,75,

                        R.drawable.img_26,R.drawable.img_72,R.drawable.img_75
                },
                {//43. 通識教育中心,

                        R.drawable.ifnullpic
                },
                {//44. 海山學研究中心,//64,78,

                        R.drawable.img_64,R.drawable.img_78
                },
                {//60. 教務處,//51,52,

                        R.drawable.img_51,R.drawable.img_52
                },
                {//61. 註冊組,//51,52,

                        R.drawable.img_51,R.drawable.img_52
                },
                {//62. 課務組,//51,52,

                        R.drawable.img_51,R.drawable.img_52
                },
                {//63. 綜合業務組,//51,52,

                        R.drawable.img_51,R.drawable.img_52
                },
                {//64. 教學發展中心,//51,

                        R.drawable.img_51
                },
                {//65. 語言中心,//64,65,

                        R.drawable.img_64,R.drawable.img_65
                },
                {//66. 學務處,

                        R.drawable.ifnullpic
                },
                {//67. 生活輔導組,//50,

                        R.drawable.img_50
                },
                {//68. 課外活動指導組,//50,

                        R.drawable.img_50
                },
                {//69. 衛生保健組,//49,

                        R.drawable.img_49
                },
                {//70. 僑生及外籍學生輔導組,//50,

                        R.drawable.img_50
                },
                {//71. 住宿輔導組,//43,

                        R.drawable.img_43
                },
                {//72. 軍訓室,//62,

                        R.drawable.img_62
                },
                {//73. 職涯發展中心,//76,77,

                        R.drawable.img_76,R.drawable.img_77
                },
                {//74. 學生諮商中心,//63,

                        R.drawable.img_63
                },
                {//75. 總務處,//49,

                        R.drawable.img_49
                },
                {//76. 文書組,//48,

                        R.drawable.img_48
                },
                {//77. 事務組,//49,

                        R.drawable.img_49
                },
                {//78. 採購組,//49,

                        R.drawable.img_49
                },
                {//79. 出納組,//48,

                        R.drawable.img_48
                },
                {//80. 營繕組,//48,

                        R.drawable.img_48
                },
                {//81. 保管組,//49,

                        R.drawable.img_49
                },
                {//82. 環境組,//48,

                        R.drawable.img_48
                },
                {//83. 研究發展處,//51,

                        R.drawable.img_51
                },
                {//84. 綜合企劃組,//51,

                        R.drawable.img_51
                },
                {//85. 研究管理組,//51,

                        R.drawable.img_51
                },
                {//86. 學術發展組,//51,

                        R.drawable.img_51
                },
                {//87. 國際事務處,//51,60,

                        R.drawable.img_51,R.drawable.img_60
                },
                {//88. 國際合作組,

                        R.drawable.ifnullpic
                },
                {//89. 國際學生組,

                        R.drawable.ifnullpic
                },
                {//90. 圖書館,//71,

                        R.drawable.img_71
                },
                {//91. 採編組,

                        R.drawable.img_71
                },
                {//92. 閱覽組,

                        R.drawable.img_71
                },
                {//93. 推廣服務組,//6,

                        R.drawable.img_6
                },
                {//94. 體育室,//51,61,

                        R.drawable.img_51,R.drawable.img_61
                },
                {//95. 教學研究組,

                        R.drawable.ifnullpic
                },
                {//96. 競賽活動組,

                        R.drawable.ifnullpic
                },
                {//97. 場地器材組,

                        R.drawable.ifnullpic
                },
                {//98. 秘書室,

                        R.drawable.ifnullpic
                },
                {//99. 人事室,//59,

                        R.drawable.img_59
                },
                {//100. 主計室,

                        R.drawable.ifnullpic
                },
                {//101. 資訊中心,//28,

                        R.drawable.img_28
                },
                {//102. 作業組,//28,

                        R.drawable.img_28
                },
                {//103. 系統組,//28,

                        R.drawable.img_28
                },
                {//104. 行政及諮詢組,//28,

                        R.drawable.img_28
                },
                {//105. 研究發展組,//28

                        R.drawable.img_28
                },
                {//106. 校友中心,//53,

                        R.drawable.img_53
                },
                {//120. 宿舍,//42,43,44,

                        R.drawable.img_42,R.drawable.img_43,R.drawable.img_44
                },
                {//121. 曉日樓,//44,

                        R.drawable.img_44
                },
                {//122. 皓月樓,//43,

                        R.drawable.img_43
                },
                {//123. 繁星樓,//42,

                        R.drawable.img_42
                },
                {//124. 運動場,//45,46,

                        R.drawable.img_45,R.drawable.img_46
                },
                {//125. 心湖,//47,

                        R.drawable.img_47
                }
        };
        return images;
    }
    // 指引页面数据适配器
    private class GuidePageAdapter extends PagerAdapter {

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
    private class GuidePageChangeListener implements OnPageChangeListener {

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

    private class catchInfo extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }
        @Override
        protected  String doInBackground(String... param) {
            return new getSqlString("select location , description ,howcome, connectway,number from places where name ='"+param[0]+"'").getServerConnect();
        }
        @Override
        protected void onPostExecute( String result) {
            super.onPostExecute(result);
            ViewPager viewPager;
            ViewGroup main;
            ViewGroup group;
            if(!result.equals("wrong")) {
                result = result.replace("<br />", System.getProperty("line.separator"));
                result = result.replace("&nbsp;", "  ");
                String tem[] = result.split("@@@@@");
                String data[] = tem[0].split("###");


                int[][] imagearray = imageArray();
                int index = Integer.parseInt(data[4]);
                if (index >= 60 && index < 120) {
                    index = index - 15;
                } else if (index > 119) {
                    index = index - 28;
                }
                int[] img = imagearray[index - 1];
                //int[] img = new int[] { R.mipmap.ic_action_refresh,R.mipmap.ic_launcher,R.mipmap.image_go,R.drawable.d0262};
                LayoutInflater inflater = getLayoutInflater();
                pageViews = new ArrayList<View>();
                for (int i = 0; i < img.length; i++) {
                    LinearLayout layout = new LinearLayout(getBaseContext());
                    LayoutParams ltp = new LayoutParams(LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT);
                    final ImageView imageView = new ImageView(getBaseContext());
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
                    ImageView imageView = new ImageView(InfoActivity.this);
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

                TextView location = (TextView) findViewById(R.id.location);
                TextView history = (TextView) findViewById(R.id.history);
                TextView howcome = (TextView) findViewById(R.id.howtocome);
                TextView connectway = (TextView) findViewById(R.id.connectway);
                location.setText(data[0]);
                history.setText(data[1]);
                howcome.setText(data[2]);
                connectway.setText(data[3]);
            }
            else {
                TextView location = (TextView) findViewById(R.id.location);
                TextView history = (TextView) findViewById(R.id.history);
                TextView howcome = (TextView) findViewById(R.id.howtocome);
                TextView connectway = (TextView) findViewById(R.id.connectway);
                final String error = "error";
                location.setText(error);
                history.setText(error);
                howcome.setText(error);
                connectway.setText(error);
            }


        }
    }
}