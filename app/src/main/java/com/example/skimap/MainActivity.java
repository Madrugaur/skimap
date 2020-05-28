package com.example.skimap;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int TABLE_TEXT_SIZE = 24;
    private static final int TABLE_TRAIL_NAME_MARGIN_TOP = 0;
    private static final int TABLE_TRAIL_NAME_MARGIN_LEFT = 100;
    private static final int TABLE_TRAIL_NAME_MARGIN_RIGHT = 15;
    private static final int TABLE_TRAIL_NAME_MARGIN_BOTTOM = 0;
    private static final int TABLE_TRAIL_IMAGE_MARGIN_TOP = 0;
    private static final int TABLE_TRAIL_IMAGE_MARGIN_LEFT = 15;
    private static final int TABLE_TRAIL_IMAGE_MARGIN_RIGHT = 15;
    private static final int TABLE_TRAIL_IMAGE_MARGIN_BOTTOM = 0;
    private static final String JNI_RETURN_STRING_REGEX = " ";

    SkiMap skimap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        skimap = new SkiMap(this, "jakesmtn");
        final Button findpathButton  = (Button)findViewById(R.id.btnFindPath);
        findpathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                byte userp = packbits();
                System.out.println(userp);
                String res = skimap.requestPath(userp);
                createTable(res);
                ((TextView)findViewById(R.id.tvResultsLabel)).setVisibility(View.VISIBLE);
            }
        });

    }
    private void initFindPathButton(){}

    public byte packbits(){
        CheckBox green = findViewById(R.id.cbGreen);
        CheckBox blue = findViewById(R.id.cbBlue);
        CheckBox black = findViewById(R.id.cbBlack);
        CheckBox dblBlack = findViewById(R.id.cbDouble);
        CheckBox parks = findViewById(R.id.cbPark);
        CheckBox glades = findViewById(R.id.cbGlades);
        CheckBox moguls = findViewById(R.id.cbMoguls);
        boolean[] bools = { green.isChecked() ,blue.isChecked(), black.isChecked(), dblBlack.isChecked(), parks.isChecked(), glades.isChecked(), moguls.isChecked(),  true };
        byte userP = BitPacker.bitPack(bools);
        return userP;
    }

    public void createTable(String input){
        TableLayout tableLayout = (TableLayout)findViewById(R.id.tblResults);
        tableLayout.removeAllViews();
        String[] trailnames = input.split(JNI_RETURN_STRING_REGEX);
        for (String trail : trailnames){
            tableLayout.addView(createTableRow(trail), new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
    private TableRow createTableRow(String input){
        //Create TableRow
        TableRow tr = new TableRow(this);
        tr.setGravity(Gravity.LEFT);

        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        //Create TextView with trail name
        TextView tvTrailName = new TextView(this);
        {
            tvTrailName.setTextSize(TABLE_TEXT_SIZE);
            tvTrailName.setText(input.replace('_', ' '));
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            params.setMargins(TABLE_TRAIL_NAME_MARGIN_LEFT, TABLE_TRAIL_NAME_MARGIN_TOP, TABLE_TRAIL_NAME_MARGIN_RIGHT, TABLE_TRAIL_NAME_MARGIN_BOTTOM);
            tvTrailName.setLayoutParams(params);
            tvTrailName.setGravity(Gravity.LEFT);
            tr.addView(tvTrailName);
        }
        //Get trail classifications from SkiMap
        String[] classifications = skimap.getTrailClassifications(input);
        //Loop and add classifications in ImageViews. size 30dp x 30dp
        Drawable[] images = skimap.getTrailImages(classifications);
        for (Drawable image : images){
            ImageView imgview = new ImageView(this);
            imgview.setImageDrawable(image);
            //imgview.setPadding(15, 0, 15,0);
            TableRow.LayoutParams params = new TableRow.LayoutParams(60, 60);
            params.setMargins(TABLE_TRAIL_IMAGE_MARGIN_LEFT,TABLE_TRAIL_IMAGE_MARGIN_TOP ,TABLE_TRAIL_IMAGE_MARGIN_RIGHT,TABLE_TRAIL_IMAGE_MARGIN_BOTTOM);
            imgview.setLayoutParams(params);

            tr.addView(imgview);
        }

        return tr;
    }

}
