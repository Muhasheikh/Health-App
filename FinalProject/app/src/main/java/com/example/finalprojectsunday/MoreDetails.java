package com.example.finalprojectsunday;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MoreDetails extends AppCompatActivity {

    private Integer simV,value,simD,simRo,simE,simR,simM;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);
        TextView vege = (TextView)findViewById(R.id.vege_info_value);
        TextView egg = (TextView)findViewById(R.id.Egg_info_value);
        TextView dhal = (TextView)findViewById(R.id.Dhal_info_value);
        TextView meat = (TextView)findViewById(R.id.Meat_info_value);
        TextView rice = (TextView)findViewById(R.id.Rice_info_value);
        TextView rotti = (TextView)findViewById(R.id.Rotti_info_value);
        TextView TotalCount=(TextView)findViewById(R.id.TotalcountVal);

        ModelActivity classone = new ModelActivity();



        vege.setText(String.valueOf(classone.vegtable_show)+"Kj/pixel");
        egg.setText(String.valueOf(classone.egg_show)+"Kj/pixel");
        dhal.setText(String.valueOf(classone.dhal_show)+"Kj/pixel");
        meat.setText(String.valueOf(classone.meat_show)+"Kj/pixel");
        rice.setText(String.valueOf(classone.rice_show)+"Kj/pixel");
        rotti.setText(String.valueOf(classone.rotti_show)+"Kj/pixel");

        ProgressBar simpleProgressBarV=(ProgressBar) findViewById(R.id.vegeprogressBar); // initiate the progress bar
        ProgressBar simpleProgressBarD=(ProgressBar) findViewById(R.id.dhalprogressBar); // initiate the progress bar
        ProgressBar simpleProgressBarRo=(ProgressBar) findViewById(R.id.rottiprogressBar); // initiate the progress bar
        ProgressBar simpleProgressBarE=(ProgressBar) findViewById(R.id.eggprogressBar); // initiate the progress bar
        ProgressBar simpleProgressBarR=(ProgressBar) findViewById(R.id.riceprogressBar); // initiate the progress bar
        ProgressBar simpleProgressBarM=(ProgressBar) findViewById(R.id.meatprogressBar); // initiate the progress bar

        TotalCount.setText(String.valueOf(classone.rotti_show+classone.rice_show+classone.meat_show+classone.dhal_show+classone.egg_show+classone.vegtable_show)+"Kj/pixel");



        simV=calaculation(classone.vegtable_show);
        simD=calaculation(classone.dhal_show);
        simRo=calaculation(classone.rotti_show);
        simE=calaculation(classone.egg_show);
        simR=calaculation(classone.rice_show);
        simM=calaculation(classone.meat_show);


        simpleProgressBarV.setProgress(simV); // 100 maximum value for the progress bar
        simpleProgressBarD.setProgress(simD);
        simpleProgressBarRo.setProgress(simRo);
        simpleProgressBarE.setProgress(simE);
        simpleProgressBarR.setProgress(simR);
        simpleProgressBarM.setProgress(simM);






    }

    private Integer calaculation(int vegtable_show) {


        float value1=((float)vegtable_show/(float)10000);
        float value2=(float)value1*(float)100;
        int value=Math.round(value2);

        return value;
    }


}