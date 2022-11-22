package com.down.book.today.questions;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

import io.github.muddz.styleabletoast.StyleableToast;

public class windows_asila extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    private List<item> mDataList;
    private databaseClass mdata;

    String msgend ;
    TextView txtFalse, txtTrue , btn2, btn3, btn4, btn5, btnTimer ,btn ,btn6, btn7, button4 ,button5;
    ProgressBar circularProgressBar ;
    static Random rand = new Random(); // static li 3adam tikrar ra9m
    int rnd , id ,sizeData, count = R.string.Timer , count_b , point=3 ;
    Handler handler = new Handler();
    MediaPlayer media_false , media_true ;
    CheckBox box_vol;
    CardView card_3 ,card_4 ;
    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //////// Start // Ads Admob Interstitial ///////////////
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId((getString(R.string.Interstitial)));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                timer();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
        //////// End // Ads Admob Interstitial ///////////////

        btn = findViewById(R.id.button);
        btn2 =  findViewById(R.id.button2);
        btn3 =  findViewById(R.id.button3);
        btn4 =  findViewById(R.id.button4);
        btn5 =  findViewById(R.id.button5);
        btn6 =  findViewById(R.id.button6);
        button5 =  findViewById(R.id.button5);
        button4 =  findViewById(R.id.button4);
        btn7 =  findViewById(R.id.button7);
        card_3 =  findViewById(R.id.card_3);
        card_4 =  findViewById(R.id.card_4);
        btnTimer = findViewById(R.id.btnTimer);
        circularProgressBar = findViewById(R.id.circularProgressBar);
        txtFalse =  findViewById(R.id.txtFalse);
        txtTrue =  findViewById(R.id.txtTrue);


        media_true = MediaPlayer.create(this,R.raw.sound_true);
        media_false = MediaPlayer.create(this,R.raw.sound_false);

        // بدايه كود اظهر واخفاء
        button5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    card_3.setVisibility(View.GONE);
                } else {
                    card_3.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        button4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    card_4.setVisibility(View.GONE);
                } else {
                    card_4.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // نهاية كود اظهر واخفاء


        box_vol = (CheckBox)findViewById(R.id.box_vol);

        mdata = new databaseClass(this);

        ///////////////////////"جلب قيم من زر return  "//////
        Bundle b = getIntent().getExtras();
        boolean rtn = b.getBoolean("rtn");

        //// نسخ قاعدة البينات الى البرنامج ////////////////
        File database = getApplicationContext().getDatabasePath(databaseClass.DBNAME);
        if (false == database.exists()) {
            mdata.getReadableDatabase();
            //Copy db
            if (copyDatabase(this)) {
                Toast.makeText(this, "نسخ قاعدة البيانات", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "تعذر نسخ قاعدة البيانات", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        ////// جلب معلومات قاعدة الببانات الى list //////////
        mDataList = mdata.getListProduct();
        sizeData = mDataList.size();

        if (rtn){
            clear_savechange();
        }else{
            LoadSating();
        }

        timer();
        if (mDataList.size() <= 1 ){
            AlertDialog.Builder builder=new AlertDialog.Builder(this) ;
            builder.setMessage("لقد أنهيت جميع المراحل\n انتظر الاصدار القادم \n وإلا أعد المحاولة إذا كانت أكثر اجوبتك خاطئة");
            builder.setPositiveButton("إعادة المحاولة", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    restarteGame() ;
                }
            });
            builder.setNegativeButton("أرسل التطبيق", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    share();
                }
            });
            builder.show();

            btn6.setText(sizeData+"\n"+"ــ"+"\n"+sizeData);
            handler.removeCallbacks(run);
        }else{
            table();
            btn6.setText(txtTrue.getText().toString()+"\n"+"ــ"+"\n"+sizeData);
            btn7.setText(" مساعدة : "+point);
        }
    }
    //////////////Timer--------------------------
    Runnable run = new Runnable() {
        @Override
        public void run() {
            timer();
        }
    };

    public void timer(){
        handler.postDelayed(run , 1000);
        circularProgressBar.setProgress(count);
        btnTimer.setText(count+"");
        count--;
        if (mDataList.size() <= 1 ){
            handler.removeCallbacks(run);
        }else{
            if(count==0){
                table();
                count= Integer.parseInt((getString(R.string.Timer)));
            }
        }
    }
    //------------timer////////////////
    public void addpoint(View view) {
        MediaPlayer mediaaddpoint = MediaPlayer.create(this,R.raw.sound_click);
        mediaaddpoint.start();

        if (point==0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("لم يتبقى أي نقط \n يمكنك إضافة نقط عن طريق زيارة موقعنا أو مشاركة البرنامج بالضغط على زر إضافة نقط");
            builder.setPositiveButton("إضافة نقط", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id)
                {
                    addpoint();
                }
            });
            builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else{
            handler.removeCallbacks(run);
            count= Integer.parseInt((getString(R.string.Timer)));
            point--;
            btn7.setText(" مساعدة : "+point);
            AlertDialog.Builder builder=new AlertDialog.Builder(this) ;
            builder.setMessage("الجواب الصحيح : \n رقم: "+count_b);
            builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });
            builder.show();
        }
    }
    public void addpoint(){
        Intent addPoin = new Intent(this, addPoint.class);
        startActivity(addPoin);
    }

    public void table() {

        handler.removeCallbacks(run);
        count = Integer.parseInt((getString(R.string.Timer)));
        rnd = rand.nextInt(mDataList.size());
        id = mDataList.get(rnd).ID;
        btn.setText(mDataList.get(rnd).Question);
        btn2.setText(mDataList.get(rnd).Answer_1);
        btn3.setText(mDataList.get(rnd).Answer_2);
        btn4.setText(mDataList.get(rnd).Answer_3);
        btn5.setText(mDataList.get(rnd).Answer_4);
        count_b = mDataList.get(rnd).ID_answer;
        timer();
        if (mInterstitialAd.isLoaded() & rnd == 2 || rnd == 4 || rnd == 8 || rnd == 16 || rnd == 32 || rnd == 64 || rnd == 128) {
            handler.removeCallbacks(run);
            mInterstitialAd.show();
        }
    }

    private boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(databaseClass.DBNAME);
            String outFileName = databaseClass.DBLOCATION + databaseClass.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("Home", "DB copied");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void btn1(View view) { //// butun 1 ///////
        if (count_b == 1) {BtnTrue();
        } else {
            BtnFalse();}
    }

    public void btn2(View view) { //// butun 2 //////
        if (count_b == 2) {BtnTrue();}
        else {BtnFalse();}
    }

    public void btn3(View view) { //// butun 3 //////
        if (count_b == 3) { BtnTrue(); }
        else { BtnFalse(); }
    }

    public void btn4(View view) { //// butun 4 /////
        if (count_b == 4) { BtnTrue(); }
        else { BtnFalse(); }
    }

    ///////////"إذا كان الجواب صحيح طبق هذه الدالة"/////////////////////////////////////
    @SuppressLint("SetTextI18n")
    public void BtnTrue() {

        if(!box_vol.isChecked()){
            media_true.start();
        }

        int m = Integer.valueOf(txtTrue.getText().toString()) + 1;
        txtTrue.setText("" + m);
        btn6.setText(m +"\n"+"ــ"+"\n"+sizeData);

        if (mDataList.size() <= 1 ){
            msgEnd();
            AlertDialog.Builder builder=new AlertDialog.Builder(this) ;
            builder.setMessage(msgend +
                    "عدد الاجوبة الصحيحة: "+txtTrue.getText().toString()+"\n"+
                    "عدد الأجوبة الخاطئة: "+txtFalse.getText().toString());
            builder.setPositiveButton("إعادة المحاولة", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    restarteGame() ;
                }
            });
            builder.setNegativeButton("أرسل التطبيق", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    share();
                }
            });
            builder.show();
            handler.postDelayed(run , 1000);
        }else{
            SaveSating(); ////"حفظ التغييرات ////////
            mDataList.remove(rnd);
            table();
        }

    }
    ///////////////"إذا كان الجواب خطأ طبق هذه الدالة"//////////////////////////////
    public void BtnFalse() {
        StyleableToast.makeText(this ,"إجابة خاطئة الجواب الصحيح رقم : "+count_b,R.style.The_answer_is_wrong).show();

        if(!box_vol.isChecked()){
            media_false.start();
        }
        int m = Integer.valueOf(txtFalse.getText().toString()) + 1;
        txtFalse.setText("" + m);

        if (mDataList.size() <= 1 ){
            msgEnd();
            AlertDialog.Builder builder=new AlertDialog.Builder(this) ;
            builder.setMessage("الجوب الصحيح هو: "+count_b+"\n"+msgend +
                    "عدد الاجوبة الصحيحة: "+txtTrue.getText().toString()+"\n"+
                    "عدد الأجوبة الخاطئة: "+txtFalse.getText().toString());
            builder.setPositiveButton("إعادة المحاولة", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    restarteGame() ;
                }
            });
            builder.setNegativeButton("أرسل التطبيق", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    share();
                }
            });
            builder.show();
            handler.postDelayed(run , 1000);
        }else{
            id = -1; /////// هذا الرقم لكي لا يتم تخزين قيمة موجودة لان الجواب خطأ ////////
            SaveSating(); ////"حفظ التغييرات ////////
            table();
        }

    }
    //////////// دالة الرجوع للبداية بعد انتهاء المراحل //////////////
    public void restarteGame(){
        Intent Home = new Intent(this, MainActivity.class);
        startActivity(Home);
    }

    ///////////////////الرسالة التي تظهر بعض انتهاء المراحل /////////////////
    public void msgEnd(){
        int txttrue = Integer.valueOf(txtTrue.getText().toString()) ;
        int txtfalse = Integer.valueOf(txtFalse.getText().toString()) ;

        if (txttrue > txtfalse){
            msgend = "رائع لقد أنهيت المراحل بشكل جيد. \n " ;
        }
        if (txttrue <= txtfalse){
            msgend = "كنت ضعيف في الإجابة.\n " ;
        }
    }
    public void share(){
        Intent myintent = new Intent(Intent.ACTION_SEND);
        myintent.setType("text/plain");
        String body = "تطبيق نسألك وانت تجيب رائع  \n" + "\n" +
                "https://play.google.com/store/apps/com.test";
        String sub = "تطبيق نسالك وانت تجيب \n";
        myintent.putExtra(Intent.EXTRA_SUBJECT, sub);
        myintent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(myintent, "مشاركة البرنامج"));
    }

    ////////////////////////// "حفظ التغييرات بالبرنامج" //////////////////////////////////
    public void SaveSating() {
        SharedPreferences savechange = this.getSharedPreferences("savechange", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = savechange.edit();

        editor.putString("txtTrue", txtTrue.getText().toString());
        editor.putString("txtFalse", txtFalse.getText().toString());
        editor.putString("btn6", btn6.getText().toString());

        editor.putInt("Point",point);
        editor.putInt("list"+id ,id);
        editor.apply();
    }

    ////////////////////////// "جلب التغييرات السابقة للبرنامج"//////////////////////////////////
    public void LoadSating() {
        SharedPreferences savechange = this.getSharedPreferences("savechange", Context.MODE_PRIVATE);

        String txttrue = savechange.getString("txtTrue", "0");
        txtTrue.setText(txttrue);
        String txtfalse = savechange.getString("txtFalse", "0");

        txtFalse.setText(txtfalse);
        String butn6 = savechange.getString("btn6",sizeData+"\n"+"ــ"+"\n"+sizeData);
        btn6.setText(butn6);

        int Point = savechange.getInt("Point",point);
        this.point = Point;

        int i= 0;
        int data = mDataList.size() ;
        while (i<data){
            try{
                int ii=0;
                while (ii<data){
                    int x = mDataList.get(ii).ID ;
                    int listvale = savechange.getInt("list"+x ,-1);

                    if (listvale==x){
                        mDataList.remove(ii) ;
                    }
                    ii++;
                }

            }catch (Exception e){
            }
            i++;
        }
    }

    public void clear_savechange() {
        SharedPreferences savechange = this.getSharedPreferences("savechange", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = savechange.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        table();
/////////////////////// استرجاع النقط المضافة /////////////////////
        SharedPreferences savechange = this.getSharedPreferences("savechange", Context.MODE_PRIVATE);
        int Point = savechange.getInt("Point",point);
        this.point = Point;
        btn7.setText(" مساعدة : "+point);
    }

}
