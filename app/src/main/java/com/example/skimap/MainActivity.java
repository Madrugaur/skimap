package com.example.skimap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    SkiMap skimap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        skimap = new SkiMap(this, "basic");
        ((Button)findViewById(R.id.btnFindPath)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte userp = packbits();
                String res = skimap.requestPath(userp);
                createTable(res);
            }
        });
    }
    public byte packbits(){
        CheckBox green = findViewById(R.id.cbGreen);
        CheckBox blue = findViewById(R.id.cbBlue);
        CheckBox black = findViewById(R.id.cbBlack);
        CheckBox dblBlack = findViewById(R.id.cbDouble);
        CheckBox parks = findViewById(R.id.cbPark);
        CheckBox glades = findViewById(R.id.cbGlades);
        CheckBox moguls = findViewById(R.id.cbMoguls);
        boolean[] bools = { green.isChecked() ,blue.isChecked(), black.isChecked(), dblBlack.isChecked(), glades.isChecked(), moguls.isChecked(), parks.isChecked(), true};
        byte userP = BitPacker.bitPack(bools);
        return userP;
    }

    public void createTable(String input){
        TableLayout tableLayout = (TableLayout)findViewById(R.id.tblResults);
        String[] trailnames = input.split(" ");
        for (String trail : trailnames){
            tableLayout.addView(createTableRow(trail), new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
    private TableRow createTableRow(String input){
        //Create TableRow
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        //Create TextView with trail name
        TextView tvTrailName = new TextView(this);
        tvTrailName.setText(input);
        tvTrailName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tr.addView(tvTrailName);
        //Get trail classifications from SkiMap
        String[] classifications = skimap.getTrailClassifications(input);
        //Loop and add classifications in TextViews
        for (String classi : classifications){
            TextView nTV = new TextView(this);
            nTV.setText(classi);
            nTV.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(nTV);
        }
        return tr;
    }

}
