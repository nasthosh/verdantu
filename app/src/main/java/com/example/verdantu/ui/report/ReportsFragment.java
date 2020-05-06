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
import com.example.verdantu.modelinterfaces.GetService;
import com.example.verdantu.models.Consumption;
import com.example.verdantu.rest.RetrofitClientInstance;
import com.example.verdantu.ui.viewmodels.ReportsViewModel;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

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
    PieChart pieChart;
    HorizontalBarChart barChart;
    RadioGroup reportCategory;
    int radioID;
    RadioButton reportRadioButton;
    String reportCategoryStr;

    int count = 0;

    Boolean isCalled = false;

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportsViewModel =
                ViewModelProviders.of(this).get(ReportsViewModel.class);
        root = inflater.inflate(R.layout.fragment_report, container, false);
        //showPieChartByCategory();

        reportCategory = root.findViewById(R.id.radioReport);
        reportCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                radioID = reportCategory.getCheckedRadioButtonId();
                reportRadioButton = root.findViewById(radioID);
                reportCategoryStr = reportRadioButton.getText().toString();
                if(reportCategoryStr.equalsIgnoreCase("By Day")){
                    clearPieCharts();
                    showBarChartByWeek();
                    isCalled = true;
                }else if(reportCategoryStr.equalsIgnoreCase("By Category")){
                    if(isCalled) {
                        clearBarCharts();
                        showPieChartByCategory();
                    }else
                        showPieChartByCategory();


                }
            }
        });

        return root;
    }

    public void showPieChartByCategory() {

        pieChart = root.findViewById(R.id.pieChart);
        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
        Call<List<Consumption>> call = service.getReportByCategory();
        emissionsListByCategory = new ArrayList<>();
        System.out.println("List : " + emissionsListByCategory);
        call.enqueue(new Callback<List<Consumption>>() {
            @Override
            public void onResponse(Call<List<Consumption>> call, Response<List<Consumption>> response) {
                emissionsListByCategory = response.body();
                System.out.println("List from retrofit : " + emissionsListByCategory);
                ArrayList<PieEntry> pieEntries = new ArrayList<>();
                ArrayList<Integer> colors = new ArrayList<>();
                Integer[] productColors = {Color.DKGRAY, Color.RED, Color.GREEN, Color.BLUE};
                for(int i = 0 ; i < emissionsListByCategory.size() ; i++) {

                    PieEntry pieEntry = new PieEntry(emissionsListByCategory.get(i).getEmission(), emissionsListByCategory.get(i).getCategoryName());
                    pieEntries.add(pieEntry);
                    colors.add(productColors[i]);
                }
                PieDataSet pieDataSet = new PieDataSet(pieEntries, "Category Report");
                pieDataSet.setColors(colors);
                PieData pieData = new PieData(pieDataSet);
                pieData.setDataSet(pieDataSet);
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

    public void showBarChartByWeek() {

        barChart = root.findViewById(R.id.barchart);
        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
        Call<List<Consumption>> call = service.getReportByWeek();
        emissionsListByDay = new ArrayList<>();
        System.out.println("List : " + emissionsListByDay);
        call.enqueue(new Callback<List<Consumption>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Consumption>> call, Response<List<Consumption>> response) {
                emissionsListByDay = response.body();
                System.out.println("List from retrofit : " + emissionsListByDay);
                ArrayList<BarEntry> barEntries = new ArrayList<>();
                ArrayList<Integer> colors = new ArrayList<>();
               ArrayList<String> labels = new ArrayList<>();
                Integer[] productColors = {Color.DKGRAY, Color.RED, Color.GREEN, Color.BLUE};
                for(int i = 0 ; i < emissionsListByDay.size() ; i++) {
                    barEntries.add(new BarEntry(i,emissionsListByDay.get(i).getEmission()));
                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    String day = emissionsListByDay.get(i).getDay();
                    labels.add(day);
                    IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(labels);
                    xAxis.setGranularity(1f);
                    xAxis.setValueFormatter(formatter);
                    colors.add(productColors[i]);
                }
                BarDataSet barDataSet = new BarDataSet(barEntries,"Report by day");
               // barDataSet.setBarBorderWidth(0.9f);
                BarData data = new BarData(barDataSet);


                barChart.setData(data);
                barChart.getDescription().setEnabled(false);
                barChart.setNoDataText("");
                //data.setBarWidth(1f);
                data.setBarWidth(0.25f);
               // barDataSet.setBarBorderWidth(1f);
                barChart.invalidate();
                barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
               // barChart.setFitBars(true);
                barChart.animateXY(2000, 2000);
                barChart.setDrawValueAboveBar(false);
                barChart.getDescription().setEnabled(false);
                barChart.getLegend().setEnabled(false);
            }

            @Override
            public void onFailure(Call<List<Consumption>> call, Throwable t) {
                Toast.makeText(root.getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println(" Throwable error : " + t);
            }
        });


    }

    public void clearPieCharts(){
        //arraylist.clear();
        pieChart.invalidate();
        pieChart.clear();
    }

    public void clearBarCharts(){
        //arraylist.clear();
        barChart.invalidate();
        barChart.clear();
    }



    /*
    public void showBarChart(){
        BarChart chart = root.findViewById(R.id.barchart);

        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new BarEntry(945f, 0));
        NoOfEmp.add(new BarEntry(1040f, 1));
        NoOfEmp.add(new BarEntry(1133f, 2));
        NoOfEmp.add(new BarEntry(1240f, 3));
        NoOfEmp.add(new BarEntry(1369f, 4));
        NoOfEmp.add(new BarEntry(1487f, 5));
        NoOfEmp.add(new BarEntry(1501f, 6));
        NoOfEmp.add(new BarEntry(1645f, 7));
        NoOfEmp.add(new BarEntry(1578f, 8));
        NoOfEmp.add(new BarEntry(1695f, 9));

        ArrayList year = new ArrayList();

        year.add("2008");
        year.add("2009");
        year.add("2010");
        year.add("2011");
        year.add("2012");
        year.add("2013");
        year.add("2014");
        year.add("2015");
        year.add("2016");
        year.add("2017");

        BarDataSet bardataset = new BarDataSet(NoOfEmp, "No Of Employee");
        chart.animateY(5000);
        BarData data = new BarData(year, bardataset);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);
    }
    }
*/

}
