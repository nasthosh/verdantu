package com.example.verdantu.ui.report;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.verdantu.R;
import com.example.verdantu.activities.DeviceData;
import com.example.verdantu.modelinterfaces.GetService;
import com.example.verdantu.models.Consumption;
import com.example.verdantu.models.Food;
import com.example.verdantu.rest.RetrofitClientInstance;
import com.example.verdantu.ui.viewmodels.ReportsViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportsFragment extends Fragment {

    private ReportsViewModel reportsViewModel;
    TextView text01;
    List<Consumption> emissionsListByCategory;
    List<Consumption> emissionsListByDay;

    List<Food> nutritionReport;
    PieChart pieChart;
    BarChart barChart;
    RadioGroup reportCategory;
    int radioID;
    RadioButton reportRadioButton;
    String reportCategoryStr;



    private BarChart nutritionChart;

    int count = 0;
    String deviceId;

    Boolean isCalled = false;
    LimitLine line;

    View root;

    RadioGroup filterActivity;
    int radioId;
    RadioButton checkedReportRadioButton;
    String filterActivityString;
    TextView textReport;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportsViewModel =
                ViewModelProviders.of(this).get(ReportsViewModel.class);
        root = inflater.inflate(R.layout.fragment_report, container, false);

        deviceId = DeviceData.getDeviceId(root.getContext());
        pieChart = root.findViewById(R.id.pieChart);
        barChart = root.findViewById(R.id.barchart);
        textReport = root.findViewById(R.id.textView_Report_type);

        nutritionChart = root.findViewById(R.id.nutrition_bar_chart);
        filterActivity = root.findViewById(R.id.reportsRadioCategory);
        filterActivity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioID = filterActivity.getCheckedRadioButtonId();
                checkedReportRadioButton = root.findViewById(radioID);
                filterActivityString = checkedReportRadioButton.getText().toString();
                if (filterActivityString.equalsIgnoreCase("By Category")) {

                    barChart.invalidate();
                    barChart.clear();
 nutritionChart.invalidate();
                    nutritionChart.clear();
                    nutritionChart.setVisibility(View.GONE);
                    barChart.setVisibility(View.GONE);
                    pieChart.setVisibility(View.VISIBLE);
                    textReport.setText("Weekly Emission Distribution for Different Categories");
                    textReport.setTextSize(20f);
                    showPieChartByCategory();
                } else if (filterActivityString.equalsIgnoreCase("By Week")) {
                    pieChart.invalidate();
                    pieChart.clear();
                    nutritionChart.invalidate();
                    nutritionChart.clear();
                    //showBarChartByWeek();
                    pieChart.setVisibility(View.GONE);
                    nutritionChart.setVisibility(View.GONE);
                    barChart.setVisibility(View.VISIBLE);
                    textReport.setText("Weekly Emission Report");
                    textReport.setTextSize(20f);
                    showChartWeek();
                } else if (filterActivityString.equalsIgnoreCase("By Nutrition")) {
                    barChart.invalidate();
                    barChart.clear();
                    pieChart.invalidate();
                    pieChart.clear();
                    pieChart.setVisibility(View.GONE);
                    barChart.setVisibility(View.GONE);
                    nutritionChart.setVisibility(View.VISIBLE);
                    textReport.setText("Weekly Nutrition Report");
                    textReport.setTextSize(20f);
                    showBarChartForNutrition();
                }
            }
        });

        // filterActivityString = parent.getItemAtPosition(position).toString();
        return root;
    }

    public void showPieChartByCategory() {

        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
        Call<List<Consumption>> call = service.getReportByCategory(deviceId);
        emissionsListByCategory = new ArrayList<>();
        System.out.println("List : " + emissionsListByCategory);
        call.enqueue(new Callback<List<Consumption>>() {
            @Override
            public void onResponse(Call<List<Consumption>> call, Response<List<Consumption>> response) {
                emissionsListByCategory = response.body();
                System.out.println("List from retrofit : " + emissionsListByCategory);
                ArrayList<PieEntry> pieEntries = new ArrayList<>();
                ArrayList<Integer> colors = new ArrayList<>();
                Integer[] productColors = {Color.rgb(124, 181, 24), Color.rgb(234, 115, 23), Color.rgb(254, 198, 1), Color.rgb(34, 124, 157)};
                for(int i = 0 ; i < emissionsListByCategory.size() ; i++) {

                    PieEntry pieEntry = new PieEntry(emissionsListByCategory.get(i).getEmission(), emissionsListByCategory.get(i).getCategoryName());
                    pieEntries.add(pieEntry);
                    colors.add(productColors[i]);
                }
                PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                pieDataSet.setColors(colors);
                PieData pieData = new PieData(pieDataSet);
                pieData.setDataSet(pieDataSet);
                pieDataSet.setValueLinePart1OffsetPercentage(10.f);
                pieDataSet.setValueLinePart1Length(0.5f);
                pieDataSet.setValueLinePart2Length(0.1f);
                pieDataSet.setValueTextColor(Color.BLACK);
                pieDataSet.setSelectionShift(50);
                pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                pieChart.getDescription().setEnabled(false);
                pieChart.setData(pieData);
                pieChart.animateXY(5000, 5000);
                pieChart.invalidate();
                pieDataSet.setValueTextSize(15f);
            }

            @Override
            public void onFailure(Call<List<Consumption>> call, Throwable t) {
                Toast.makeText(root.getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println(" Throwable error : " + t);
            }
        });


    }


    public void showBarChartForNutrition(){

        System.out.println("Inside nutrition chart");
        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
        Call<List<Food>> call = service.getNutritionReport(deviceId);
        nutritionReport = new ArrayList<>();
        System.out.println("List in nutrition : " + nutritionReport);
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                System.out.println("Inside response for nutrition");
                nutritionReport = response.body();
                System.out.println("List from retrofit for nutrition : " + nutritionReport);
                ArrayList<BarEntry> values = new ArrayList<>();
                values.add(new BarEntry(0,nutritionReport.get(0).getFoodFat()));
                values.add(new BarEntry(1,nutritionReport.get(0).getFoodProtein()));
                values.add(new BarEntry(2,nutritionReport.get(0).getFoodCarbs()));

                BarDataSet nutritionDataset = new BarDataSet(values, "Nutrition By Week");

                ArrayList<String> labels = new ArrayList<String>();
                labels.add("Fat");
                labels.add("Protein");
                labels.add("Carbohyrates");

                IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(labels);
                XAxis xAxis = nutritionChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);
                xAxis.setValueFormatter(formatter);

                BarData nutritionData = new BarData(nutritionDataset);
                nutritionData.setValueTextSize(12f);
                nutritionChart.setData(nutritionData);
                nutritionDataset.setColors(Color.rgb(79, 109, 122));
                nutritionChart.getDescription().setEnabled(false);
                nutritionChart.animateY(500);
                nutritionChart.getXAxis().setDrawGridLines(false);
                nutritionChart.getAxisLeft().setDrawGridLines(false);
                nutritionChart.getAxisRight().setDrawGridLines(false);
                nutritionChart.setNoDataText("");
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Toast.makeText(root.getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println(" Throwable error : " + t);
            }
        });
    }

    public void showChartWeek(){

        int maxCapacity = (int) 8.2;
        line = new LimitLine(maxCapacity);
        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
        Call<List<Consumption>> call = service.getReportByWeek(deviceId);
        emissionsListByDay = new ArrayList<>();
        System.out.println("List : " + emissionsListByDay);
        call.enqueue(new Callback<List<Consumption>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Consumption>> call, Response<List<Consumption>> response) {
                emissionsListByDay = response.body();
                System.out.println("List from retrofit : " + emissionsListByDay);
                ArrayList<BarEntry> values = new ArrayList<>();
                ArrayList<String> labels = new ArrayList<>();
                for(int i = 0 ; i < emissionsListByDay.size() ; i++) {
                    values.add(new BarEntry(i,emissionsListByDay.get(i).getEmission()));
                    String day = emissionsListByDay.get(i).getDay();
                    labels.add(day);
                }
                BarDataSet weekDataset = new BarDataSet(values, "Report by Week");
                ArrayList<IBarDataSet> weekBarDataset = new ArrayList<>();
                weekBarDataset.add(weekDataset);
                BarData data = new BarData(weekBarDataset);
                barChart.getDescription().setEnabled(false);
                barChart.setDrawValueAboveBar(true);
                // nutritionDataset.setColors(Color.rgb(79, 109, 122));
                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);
                IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(labels);
                xAxis.setValueFormatter(formatter);
                YAxis axisLeft = barChart.getAxisLeft();
                axisLeft.setGranularity(10f);
                axisLeft.setAxisMinimum(0);
                weekDataset.setColor(Color.rgb(69, 105, 144));
                YAxis axisRight = barChart.getAxisRight();
                axisRight.setGranularity(10f);
                axisRight.setAxisMinimum(0);
                data.setValueTextSize(12f);
                barChart.getAxisLeft().addLimitLine(line);
                line.setLineWidth(4f);
                line.setTextSize(12f);
                line.setLineColor(Color.RED);
                barChart.setData(data);
                barChart.invalidate();
                barChart.getXAxis().setDrawGridLines(false);
                barChart.getAxisLeft().setDrawGridLines(false);
                barChart.getAxisRight().setDrawGridLines(false);
                barChart.setNoDataText("");
            }

            @Override
            public void onFailure(Call<List<Consumption>> call, Throwable t) {
                Toast.makeText(root.getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println(" Throwable error : " + t);
            }
        });
    }

}
