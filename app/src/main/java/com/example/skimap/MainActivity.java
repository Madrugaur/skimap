package com.example.skimap;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteTableLockedException;
import android.os.Bundle;
import android.text.Layout;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createTable();
    }
    public void createTable(){
        TableLayout tableLayout = (TableLayout)findViewById(R.id.tblvResultTable);
        tableLayout.addView(createTableRow(tableLayout), new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }
    private TableRow createTableRow(TableLayout parent){
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView text = new TextView(this);
        text.setText("hello world");
        text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        Button button = new Button(this);
        button.setText("click");
        button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        tr.addView(text);
        tr.addView(button);
        return tr;
    }

}
