package com.example.finalprojectsunday;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class Onboarding extends AppCompatActivity {

    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//       requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//       getSupportActionBar().hide();
        setContentView(R.layout.activity_onboarding);

        fragmentManager=getSupportFragmentManager();

        final PaperOnboardingFragment paperOnboardingFragment  = PaperOnboardingFragment.newInstance(getDataForOnBoarding());

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, paperOnboardingFragment);
        fragmentTransaction.commit();


        paperOnboardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                Intent myint = new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(myint);
            }
        });
    }

    private ArrayList<PaperOnboardingPage> getDataForOnBoarding(){

        PaperOnboardingPage scr1 = new PaperOnboardingPage("Meet Diet by GetFit",
                "Advanced meal tracker and nutrition analyzer to help you stay on track and ensure results that will last",
                Color.parseColor("white"), R.drawable.foods, R.drawable.taco);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("Track foods using your camera",
                "Use your camera to scan and easily look for over 100,000 items with nutrition information for each",
                Color.parseColor("white"), R.drawable.food, R.drawable.camera);


        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);


        return  elements;

    }
}