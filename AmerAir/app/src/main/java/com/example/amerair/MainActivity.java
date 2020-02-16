package com.example.amerair;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

   // TextView mTv;
   // Button mBtn;

 //   public static final String EXTRA_MESSAGE = "com.example.amerair.MESSAGE";
    Calendar c;
    DatePickerDialog dpd;

    private static final String TAG = "MainActivity";
    private TextView mDisplayData;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    boolean press = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button searchBtn = findViewById(R.id.searchbtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");

                Intent activity2Intent = new Intent(getApplicationContext(), SearchResult.class);
                startActivity(activity2Intent);
            }
        });


        mDisplayData = (TextView) findViewById(R.id.txt);

      //  mTv = (TextView) findViewById(R.id.txt);
       // mBtn = (Button) findViewById(R.id.selectDate);

     //   Date d = new Date();
        mDisplayData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                c = Calendar.getInstance();
               // c.setTime(d);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

               // month = month+1;

                dpd = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay){
                        mDisplayData.setText((mMonth+1) + "/" + mDay + "/" + mYear);
                    }
                }, year, month, day);
                dpd.show();
            }

        });
    }
    //
}
