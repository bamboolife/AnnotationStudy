package com.sundy.simple;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sundy.simple.annotation.autowired.AutoWired;
import com.sundy.simple.annotation.butterknife.ContentView;
import com.sundy.simple.annotation.butterknife.OnClick;
import com.sundy.simple.annotation.db.Student;
import com.sundy.simple.entity.User;

@ContentView(id = R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //    public LocationReq mLocation;
    private Student student;
//    @BindView(value=R.id.textview)
//    TextView mTextView;
//    @RandomInt(minValue = 10, maxValue = 1000)
//    int mRandomInt;
//
//    @RandomString
//    String mRandomString;
    @AutoWired
    User mUser;
    //private MealFactory factory = new MealFactory();
//
//    public Meal order(String mealName){
//        return factory.create(mealName);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        // RandomUtil.inject(this);
//        ButterknifeProcess.bind(this);
//        Log.i("log_int", "onCreate: "+mRandomInt);
//        Log.i("log_int", "onCreate: "+mRandomString);
//        cofig();
//        getRequest();
        initTate();
        //mTextView=   getWindow().getDecorView().findViewById(R.id.textview);
//        mTextView.setText("这是一个测试");
//       AutoWiredUtils.bind(this);
//
//         mUser.setAge(11);
//         mUser.setName("www");
//        Log.i("log_user", "onCreate: "+mUser.toString());


    }

//    private void cofig(){
//        mLocation=new LocationReq();
//        mLocation.lan="123.09";
//        mLocation.lat="232.34";
//
//    }
//
//    /**
//     * 获取请求路径
//     */
//    private void getRequest(){
//        System.out.println(RequestParam.getParam(mLocation.getClass(),mLocation));
//    }
//
    private void initTate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    student = new Student();
                    student.age = i + "";
                    student.name = "name-" + i;
                    if (i % 2 == 0) {
                        student.sex = "男";
                    } else {
                        student.sex = "女";
                    }

                }
                student.open(MainActivity.this,student.getClass(),student).isInsert(true);
                Log.d("log_data", "onCreate: "+student.getDate());

            }
        }).start();
    }

    @OnClick(R.id.button)
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "这是一个测试", Toast.LENGTH_LONG).show();
    }
}
