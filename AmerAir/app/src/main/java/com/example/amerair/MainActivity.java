package com.example.amerair;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Calendar c;
    DatePickerDialog dpd;

    String inputText;

    public String getInputText() {
        return inputText;
    }
    public void setInputText(String text)
    {
        inputText = text;
    }

    private static final String TAG = "MainActivity";
    private TextView mDisplayData;
    private EditText flightNum;
    private Button searchbtn;

    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flightNum = (EditText) findViewById(R.id.flightNum);
        mDisplayData = (TextView) findViewById(R.id.datetxt);
        searchbtn = (Button) findViewById(R.id.searchbtn);



        flightNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                processButtonByTextLength();
            }
        });


        flightNum.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                int action = keyEvent.getAction();
                if (action == KeyEvent.ACTION_UP) {
                    processButtonByTextLength();
                }
                return false;
            }
        });
        mDisplayData.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                processButtonByTextLength();
            }
        });
        mDisplayData.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                int action = keyEvent.getAction();
                if (action == KeyEvent.ACTION_UP) {
                    processButtonByTextLength();
                }
                return false;
            }
        });
        searchbtn.setEnabled(false);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), SearchResult.class);
                startActivity(activity2Intent);
            }
        });


        //---- calender shit ---
        mDisplayData = (TextView) findViewById(R.id.datetxt);
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
    public void test(View view) {

    }
    private void processButtonByTextLength()
    {

        String it = flightNum.getText().toString();
        setInputText(it);
        String inputDate = mDisplayData.getText().toString();
      /*  Intent in = new Intent(this, SearchResult.class);
        Bundle b = new Bundle();
        b.putString("stuff", it);
        in.putExtras(b);
        startActivity(in); */
        if((it.length() == 4) && (inputDate.length() > 0))
        {
            searchbtn.setText("Perfect");
            searchbtn.setEnabled(true);
        }else
        {
            searchbtn.setText("Search");
            searchbtn.setEnabled(false);
        }
    }
   }
