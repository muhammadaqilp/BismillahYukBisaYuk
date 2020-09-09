package com.example.bismillahyukbisayuk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bismillahyukbisayuk.Model.User;
import com.example.bismillahyukbisayuk.Model.XYValue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;

public class GrafikActivity extends AppCompatActivity {

    Toolbar toolbar;

    TextView tvStatusGizi, rekomendasi1, rekomendasi2;

    private static final String TAG = "NyobainGrafik";

    //add PointsGraphSeries of DataPoint type
    PointsGraphSeries<DataPoint> xySeries;

    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;

    GraphView mScatterPlot;


    //make xyValueArray global
    private ArrayList<XYValue> xyValueArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik);

        toolbar = findViewById(R.id.toolbar);
        tvStatusGizi = findViewById(R.id.statusgizinya);
        rekomendasi1 = findViewById(R.id.rekomen1);
        rekomendasi2 = findViewById(R.id.rekomen2);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mScatterPlot = findViewById(R.id.scatterPlot);
        xyValueArray = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                String a = user.getUsiaBulan();
                String s = user.getBeratBadan();
                String gizi = user.getStatusGizi();

                tvStatusGizi.setText(gizi);
                init(a, s);

                if (gizi.equals("Gizi Buruk")){
                    rekomendasi1.setText("•\tTambahkan 1/3 bagian dari setengah piring untuk lauk pauk dan kurangi untuk buah");
                    rekomendasi2.setText("•\tMakan buah 1-2 porsi, makanan sumber protein hewani dan nabati 4-5 porsi");
                }
                else if (gizi.equals("Mendekati Buruk")){
                    rekomendasi1.setText("•\tTambahkan 1/3 bagian dari setengah piring untuk lauk pauk dan kurangi untuk buah");
                    rekomendasi2.setText("•\tMakan buah 1-2 porsi, makanan sumber protein hewani dan nabati 4-5 porsi");
                }
                else if (gizi.equals("Normal")){
                    rekomendasi1.setText("•\tIsilah 2/3 bagian dari setengah piring masing-masing untuk makanan pokok dan untuk sayuran, 1/3 bagian dari setengah piring masing-masing untuk lauk-pauk dan untuk buah");
                    rekomendasi2.setText("•\tMakan sumber karbohidrat 3-4 porsi, makan sayur 3-4 porsi, buah 2-3 porsi, makanan sumber protein hewani dan nabati 2-4 porsi");
                }
                else if (gizi.equals("Overweight")){
                    rekomendasi1.setText("•\tKurangi 2/3 bagian dari setengah piring untuk makanan pokok dan tambahkan untuk sayuran");
                    rekomendasi2.setText("•\tMakan sumber karbohidrat 2-3 porsi, makan sayur 4-5 porsi");
                }
                else {
                    rekomendasi1.setText("•\tKurangi 2/3 bagian dari setengah piring untuk makanan pokok dan tambahkan untuk sayuran");
                    rekomendasi2.setText("•\tMakan sumber karbohidrat 2-3 porsi, makan sayur 4-5 porsi");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void init(String a, String s) {
        //declare the xySeries Object
        xySeries = new PointsGraphSeries<>();

        Double x = Double.parseDouble(a);
        Double y = Double.parseDouble(s);
        xyValueArray.add(new XYValue(x, y));
//        init();

        //little bit of exception handling for if there is no data.
        if (xyValueArray.size() != 0) {
            createScatterPlot();
        } else {
            Log.d(TAG, "onCreate: No data to plot.");
        }
    }

    private void createScatterPlot() {
        Log.d(TAG, "createScatterPlot: Creating scatter plot.");

        //sort the array of xy values
        xyValueArray = sortArray(xyValueArray);

        //add the data to the series
        for (int i = 0; i < xyValueArray.size(); i++) {
            try {
                double x = xyValueArray.get(i).getX();
                double y = xyValueArray.get(i).getY();
                xySeries.appendData(new DataPoint(x, y), true, 1000);
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "createScatterPlot: IllegalArgumentException: " + e.getMessage());
            }
        }

        //set some properties
        xySeries.setShape(PointsGraphSeries.Shape.POINT);
        xySeries.setColor(Color.BLACK);
        xySeries.setSize(8f);

        //set Scrollable and Scaleable
//        mScatterPlot.getViewport().setScalable(true);
//        mScatterPlot.getViewport().setScalableY(true);
//        mScatterPlot.getViewport().setScrollable(true);
//        mScatterPlot.getViewport().setScrollableY(true);

        //set manual x bounds
        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxY(18);
        mScatterPlot.getViewport().setMinY(-0);

        //set manual y bounds
        mScatterPlot.getViewport().setXAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxX(24);
        mScatterPlot.getViewport().setMinX(-0);

        mScatterPlot.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);

        mScatterPlot.addSeries(xySeries);
    }

    /**
     * Sorts an ArrayList<XYValue> with respect to the x values.
     *
     * @param array
     * @return
     */
    private ArrayList<XYValue> sortArray(ArrayList<XYValue> array) {
        /*
        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>
         */
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(), 2))));
        int m = array.size() - 1;
        int count = 0;
        Log.d(TAG, "sortArray: Sorting the XYArray.");


        while (true) {
            m--;
            if (m <= 0) {
                m = array.size() - 1;
            }
            Log.d(TAG, "sortArray: m = " + m);
            try {
                //print out the y entrys so we know what the order looks like
                //Log.d(TAG, "sortArray: Order:");
                //for(int n = 0;n < array.size();n++){
                //Log.d(TAG, "sortArray: " + array.get(n).getY());
                //}
                double tempY = array.get(m - 1).getY();
                double tempX = array.get(m - 1).getX();
                if (tempX > array.get(m).getX()) {
                    array.get(m - 1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m - 1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                } else if (tempX == array.get(m).getX()) {
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                } else if (array.get(m).getX() > array.get(m - 1).getX()) {
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }
                //break when factorial is done
                if (count == factor) {
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +
                        e.getMessage());
                break;
            }
        }
        return array;
    }

    /**
     * customizable toast
     *
     * @param message
     */
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}