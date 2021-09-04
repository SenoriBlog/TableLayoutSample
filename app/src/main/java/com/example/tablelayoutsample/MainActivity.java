package com.example.tablelayoutsample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TableLayout mTableLayout;
    ProgressDialog mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = new ProgressDialog(this);
        mTableLayout = (TableLayout) findViewById(R.id.tablePlayers);
        mTableLayout.setStretchAllColumns(true);
        startLoadData();
    }

    public void startLoadData() {
        mProgressBar.setCancelable(false);
        mProgressBar.setMessage("Fetching Players..");
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressBar.show();
        new LoadDataTask().execute(0);
    }

    public void loadData() {
        int leftRowMargin=0;
        int topRowMargin=0;
        int rightRowMargin=0;
        int bottomRowMargin = 0;
        int textSize = 0, smallTextSize =0, mediumTextSize = 0;
        textSize = (int) getResources().getDimension(R.dimen.font_size_verysmall);
        smallTextSize = (int) getResources().getDimension(R.dimen.font_size_small);
        mediumTextSize = (int) getResources().getDimension(R.dimen.font_size_medium);
        Player players = new Player();
        PlayerData[] data = players.getPlayers();
        int rows = data.length;
        getSupportActionBar().setTitle("Players (" + String.valueOf(rows) + ")");
        TextView textSpacer = null;
        mTableLayout.removeAllViews();
        // -1 はヘッダー行
        for(int i = -1; i < rows; i ++) {
            PlayerData row = null;
            if (i > -1)
                row = data[i];
            else {
                textSpacer = new TextView(this);
                textSpacer.setText("");
            }
            // 1列目(No)
            final TextView tv = new TextView(this);
            tv.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.LEFT);
            tv.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tv.setText("No");
                tv.setBackgroundColor(Color.parseColor("#f0f0f0"));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
            }
            else {
                tv.setText(String.valueOf(row.getNo()));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
            // 2列目(Name)
            final TextView tv2 = new TextView(this);
            tv2.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv2.setGravity(Gravity.LEFT);
            tv2.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tv2.setText("Name");
                tv2.setBackgroundColor(Color.parseColor("#f0f0f0"));
                tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
            }
            else {
                tv2.setText(row.getName());
            }
            // 3列目(Position)
            final TextView tv3 = new TextView(this);
            tv3.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv3.setGravity(Gravity.LEFT);
            tv3.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tv3.setText("Position");
                tv3.setBackgroundColor(Color.parseColor("#f0f0f0"));
                tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
            }
            else {
                tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                tv3.setText(row.getPosition());
            }
            // 4列目(Birth)
            final TextView tv4 = new TextView(this);
            tv4.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv4.setGravity(Gravity.LEFT);
            tv4.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tv4.setText("Birth");
                tv4.setBackgroundColor(Color.parseColor("#f0f0f0"));
                tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
            }
            else {
                tv4.setText(new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN).format(row.getBirth()));
                tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
            // テーブルに行を追加
            final TableRow tr = new TableRow(this);
            tr.setId(i + 1);
            TableLayout.LayoutParams trParams = new
                    TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            trParams.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);
            tr.setPadding(0,0,0,0);
            tr.setLayoutParams(trParams);
            tr.addView(tv);
            tr.addView(tv2);
            tr.addView(tv3);
            tr.addView(tv4);
            mTableLayout.addView(tr, trParams);
            // 罫線を追加
            if (i > -1) {
                final TableRow trSep = new TableRow(this);
                TableLayout.LayoutParams trParamsSep = new
                        TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
                trParamsSep.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);
                trSep.setLayoutParams(trParamsSep);
                TextView tvSep = new TextView(this);
                TableRow.LayoutParams tvSepLay = new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                tvSepLay.span = 4;
                tvSep.setLayoutParams(tvSepLay);
                tvSep.setBackgroundColor(Color.parseColor("#d9d9d9"));
                tvSep.setHeight(1);
                trSep.addView(tvSep);
                mTableLayout.addView(trSep, trParamsSep);
            }
        }
    }
    class LoadDataTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Task Completed.";
        }
        @Override
        protected void onPostExecute(String result) {
            mProgressBar.hide();
            loadData();
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }
}