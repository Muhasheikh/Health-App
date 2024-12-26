package com.example.finalprojectsunday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finalprojectsunday.configuration.PersonalInformation;
import com.example.finalprojectsunday.customFoods.CustomFoodOverview;
import com.example.finalprojectsunday.foodJournal.FoodGroupOverview;
import com.example.finalprojectsunday.foodJournal.FoodJournalOverview;
import com.example.finalprojectsunday.other.Database;
import com.example.finalprojectsunday.other.LocaleHelper;
import com.example.finalprojectsunday.other.Utils;

import com.example.finalprojectsunday.recommendation.RecommendationProteinListAdapter;
import com.example.finalprojectsunday.recommendation.Recommendations;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private final List<Integer> colors = new ArrayList<>();
    private Database db;
    private LocalDate currentDateParsed;
    private ProgressBar energyBar;
    private TextView energyBarText;
    private PieChart pieChart;
    private RecyclerView chartList;

    /* Detect language change from Configuration-class */
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String broadcastMessage = intent.getAction();
            if (broadcastMessage == "LANGUAGE_CHANGED"){
                recreate();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main);

        /* load database on Application start */
        db = new Database(this);
        currentDateParsed = LocalDate.now();

        /* set default language */
        String savedLanguage = db.getLanguagePref();
        if (savedLanguage != null) LocaleHelper.setDefaultLanguage(this, savedLanguage);

        /* Setup Toolbar */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* drawer (navigation sidebar) */
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /* Night Mode */
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        View navHeader = navigationView.getHeaderView(0);
        ImageButton switchToNight = navHeader.findViewById(R.id.switchToNight);
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            switchToNight.setImageResource(R.mipmap.ic_sun_foreground);
        }
        switchToNight.setOnClickListener(v -> {
            switch (currentNightMode) {
                case Configuration.UI_MODE_NIGHT_NO:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
                case Configuration.UI_MODE_NIGHT_YES:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
                default:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            }
        });

        /* Language */
        String currentLanguage = LocaleHelper.getLanguage(this);
        Button setLanguage = navHeader.findViewById(R.id.languageSelect);
        setLanguage.setText(R.string.otherLanguage);
        setLanguage.setOnClickListener(v -> {
            switch (currentLanguage) {
                case "en":
                    LocaleHelper.setLocale(this, "de");
                    db.setLanguagePref("de");
                    this.recreate();
                    break;
                case "de":
                    LocaleHelper.setLocale(this, "en");
                    db.setLanguagePref("en");
                    this.recreate();
                    break;
            }
        });

        /* Listen for LanguageChanged-notification from PersonalInformation */
        IntentFilter filter = new IntentFilter();
        filter.addAction("LANGUAGE_CHANGED");
        registerReceiver(broadcastReceiver, filter);


        /* ---- JOURNAL ------*/
        View foodJournalButtonView = findViewById(R.id.food_journal);
        /* FIXME: Click Animation for TILE Issue#38 */
        // foodJournalButtonView.setBackgroundResource(R.drawable.button_ripple_animation_blue);


        TextView foodJournalButtonTitle = foodJournalButtonView.findViewById(R.id.recommendation_button_title);
        ImageButton foodJournalButtonAdd = foodJournalButtonView.findViewById(R.id.button_add);
        ImageButton foodJournalButtonViewJournal = foodJournalButtonView.findViewById(R.id.button_view_journal);

        foodJournalButtonTitle.setText(R.string.foodJournalButtonTitle);

        foodJournalButtonAdd.setOnClickListener(v -> {
            Intent add = new Intent(v.getContext(), FoodGroupOverview.class);
            startActivity(add, Utils.getDefaultTransition(this));
        });

        foodJournalButtonViewJournal.setOnClickListener(v -> {
            Intent viewJournal = new Intent(v.getContext(), FoodJournalOverview.class);
            startActivity(viewJournal, Utils.getDefaultTransition(this));
        });


        /* ---- CONFIGURATION ----- */
        View configButtonView = findViewById(R.id.config);
        /* FIXME: Click Animation for TILE Issue#38 */
        // configButtonView.setBackgroundResource(R.drawable.button_ripple_animation_orange);

        TextView configButtonTitle = configButtonView.findViewById(R.id.recommendation_button_title);
        TextView configButtonLeftTag = configButtonView.findViewById(R.id.buttonDescription);

        configButtonTitle.setText(R.string.configButtonTitle);
        configButtonLeftTag.setText(R.string.configButtonLeftTag);

        configButtonView.setOnClickListener(v -> {
            Intent configuration = new Intent(v.getContext(), PersonalInformation.class);
            startActivity(configuration, Utils.getDefaultTransition(this));
        });

        /* ---- CUSTOM FOOD CREATION ---- */
        View createCustomFoodsView = findViewById(R.id.create_foods);
        /* FIXME: Click Animation for TILE Issue#38 */
        // createCustomFoodsView.setBackgroundResource(R.drawable.button_ripple_animation_purple);

        TextView createCustomFoodButtonTitle = createCustomFoodsView.findViewById(R.id.recommendation_button_title);
        TextView createCustomFoodTagLef = createCustomFoodsView.findViewById(R.id.buttonDescription);

        createCustomFoodButtonTitle.setText(R.string.createFoodsButton);
        createCustomFoodTagLef.setText(R.string.createFoodsButtonLeftText);

        createCustomFoodsView.setOnClickListener(v -> {
            Intent createCustomFood = new Intent(v.getContext(), CustomFoodOverview.class);
            startActivity(createCustomFood, Utils.getDefaultTransition(this));
        });

        /* ---- RECOMMENDATION ---- */
        View recommendationTileView = findViewById(R.id.recommendations);
        TextView analysisButtonTitle = recommendationTileView.findViewById(R.id.recommendation_button_title);
        Button showAnalysisButton = recommendationTileView.findViewById(R.id.button_recommendation);

        energyBar = recommendationTileView.findViewById(R.id.progressbar_main);
        energyBarText = recommendationTileView.findViewById(R.id.progressbarTV_main);

        analysisButtonTitle.setText(R.string.analysisButtonTitle);
        showAnalysisButton.setText(R.string.showAnalysis);

        /* get logged foods of day */
        Recommendations.setProgressBar(currentDateParsed, this.db, this.energyBar, this.energyBarText, this);

        recommendationTileView.setOnClickListener(v -> {
            Intent analysis = new Intent(v.getContext(), Recommendations.class);
            startActivity(analysis, Utils.getDefaultTransition(this));
        });

        showAnalysisButton.setOnClickListener(v -> {
            Intent analysis = new Intent(v.getContext(), Recommendations.class);
            startActivity(analysis, Utils.getDefaultTransition(this));
        });

        /* PieChart */
        pieChart = findViewById(R.id.piChartNutrition);
        Pair<PieData, ArrayList<Integer>> pieAndListData = Recommendations.generatePieChartContent(currentDateParsed, this.db, this.colors);
        Recommendations.visualSetupPieChart(pieAndListData, pieChart);

        /* PieChartList with percent protein, carbs, fat*/
        chartList = findViewById(R.id.chartList);
        chartList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        Recommendations.setChartSupportingList(pieChart, pieAndListData, this, chartList);
    }

    @Override
    protected void attachBaseContext(Context base){
        super.attachBaseContext(LocaleHelper.setDefaultLanguage(base));
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        recreate();
    }

    protected void onResume() {
        super.onResume();
        Recommendations.setProgressBar(currentDateParsed, this.db, this.energyBar, this.energyBarText, this);
        Pair<PieData, ArrayList<Integer>> pieAndListData = Recommendations.generatePieChartContent(currentDateParsed, this.db, this.colors);
        pieAndListData.first.setDrawValues(false);
        pieChart.setData(pieAndListData.first);
        pieChart.invalidate();
        RecyclerView.Adapter<?> adapter = new RecommendationProteinListAdapter(getApplicationContext(), pieAndListData.first, pieAndListData.second);
        chartList.setAdapter(adapter);
    }

    /* goto selected item in navigation sidebar */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_foodJournal) {
            Intent journal = new Intent(this, FoodJournalOverview.class);
            startActivity(journal, Utils.getDefaultTransition(this));
        } else if (itemId == R.id.nav_configuration) {
            Intent configuration = new Intent(this, PersonalInformation.class);
            startActivity(configuration, Utils.getDefaultTransition(this));
        } else if (itemId == R.id.nav_customFoods) {
            Intent createCustomFood = new Intent(this, CustomFoodOverview.class);
            startActivity(createCustomFood, Utils.getDefaultTransition(this));
        } else if (itemId == R.id.nav_analysis) {
            Intent analysis = new Intent(this, Recommendations.class);
            startActivity(analysis, Utils.getDefaultTransition(this));
        } else if (itemId == R.id.nav_about) {
            Intent about = new Intent(this, AboutPage.class);
            startActivity(about);
        } else if (itemId == R.id.nav_weight_tracking) {
            Intent weight = new Intent(this, WeightTracking.class);
            startActivity(weight);
        } else {
            Log.wtf("Click", "Unknown ID for onNavigationItemSelected: " + item.getItemId());
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /* toggle navigation sidebar */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}