package com.example.arieshu.sqliteexample;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int FP = ViewGroup.LayoutParams.MATCH_PARENT;

    private List<StudentProfile> m_StuList;

    private TableLayout m_TableRow;

    private StudentDAO stuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addStudentData();
        processViews();
        processControllers();
    }

    private void processViews() {

        m_TableRow = (TableLayout) findViewById(R.id.tableRow);
        m_TableRow.setStretchAllColumns(true);

        int nCnt = m_StuList.size();

//        for ( int i = 0 ; i < nCnt ; i++ ) {
//            //Log.i("Test", m_StuList.get(i).m_stdName);
//        }

        int sizeInDP = 1;
        int nTableCol = 4;
        int nTableRow = m_StuList.size();

        for( int i = 0 ; i < nTableRow ; i++ )
        {
            TableRow tableRow = new TableRow(this);
            for( int j = 0; j < nTableCol ; j++ )
            {
                //tv用于显示
                android.widget.TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);

                int marginInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDP, getResources().getDisplayMetrics());

                int marginInDpTop    = marginInDp;
                int marginInDpLeft   = (j==0)?marginInDp*2:marginInDp;
                int marginInDpRight  = (j==(nTableCol-1))?marginInDp*2:marginInDp;
                int marginInDpBottom = (i==(nTableRow-1))?marginInDp*2:marginInDp;

                layoutParams.setMargins(marginInDpLeft, marginInDpTop, marginInDpRight, marginInDpBottom);

                TextView tv = new TextView(this);
                //tv.setText("("+col+","+row+")");
                tv.setBackgroundResource(R.color.tableItem);
                tv.setTypeface(null, Typeface.BOLD);
                tv.setTextSize(20);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                tv.setWidth(0);

                if(j == 0) tv.setText(String.valueOf(m_StuList.get(i).m_nID));
                else if(j == 1) tv.setText(m_StuList.get(i).m_stdName);
                else if(j == 2) tv.setText(m_StuList.get(i).m_strSex);
                else tv.setText(String.valueOf(m_StuList.get(i).m_nScore));

                tableRow.setBackgroundResource(R.color.tableBackground);
                tableRow.addView(tv, layoutParams);
            }
            // Add table row to table layout
            m_TableRow.addView(tableRow, new TableLayout.LayoutParams(FP, WC));
        }
    }

    private void processControllers() {

    }

    private void addStudentData() {

        stuDAO = new StudentDAO(getApplicationContext());
        if (stuDAO.getCount() == 0) stuDAO.sample();
        m_StuList = stuDAO.getAll();
    }
}
