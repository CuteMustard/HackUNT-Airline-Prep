package com.example.amerair;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class SearchResult extends AppCompatActivity {

  //  private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        MainActivity ma = new MainActivity();
        String a = ma.getInputText();
        TextView tw = (TextView) findViewById(R.id.flightNum);
        tw.setText("6666");

        //----------------------------------------------------
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> myAdpater = new ArrayAdapter<String>(SearchResult.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categories));
        myAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdpater);
    }
        //--------------------------------------------------------------------------------


}
