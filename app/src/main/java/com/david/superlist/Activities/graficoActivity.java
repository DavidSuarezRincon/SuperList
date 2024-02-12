package com.david.superlist.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.david.superlist.R;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

public class graficoActivity extends AppCompatActivity {

    private PieChart pieChart;
    private TextView textViewTotal;
    private TextView textoNoHayListas;
    private String[] tiposDeListas = {"Lista de la compra", "Lista de deseos", "Lista de tareas", "Receta", "Otro"};
    private int[] cantidadesTiposDeListas = {0, 0, 1, 0, 0};
    private int[] coloresTiposDeListas = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.MAGENTA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa el gráfico de pastel
        pieChart = (PieChart) findViewById(R.id.pieChart);
        textViewTotal = findViewById(R.id.textoTotalListas);
        textoNoHayListas = findViewById(R.id.textoNoHayListas);
        createChart();
        setTextTotalListas();
    }

    // Método para configurar el gráfico
    private Chart getSameChart(Chart chart, int textColor, int background, int animateY) {
        // Deshabilita la descripción del gráfico (elimina el título)
        chart.getDescription().setEnabled(false);
        // Establece el color de fondo del gráfico
        chart.setBackgroundColor(background);
        // Establece la animación del gráfico
        chart.animateY(animateY);

        // Configura la leyenda del gráfico
        legend(chart);
        return chart;
    }

    // Método para configurar la leyenda del gráfico
    private void legend(Chart chart) {
        Legend legend = chart.getLegend();
        // Establece la forma de los ítems de la leyenda
        legend.setForm(Legend.LegendForm.CIRCLE);
        // Establece la alineación horizontal de la leyenda
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        // Establece el tamaño del texto de la leyenda
        legend.setTextSize(16);
        // Habilita el ajuste de línea en la leyenda
        legend.setWordWrapEnabled(true);

        // Crea las entradas de la leyenda
        ArrayList<LegendEntry> entries = new ArrayList<>();
        int total = 0;

        // Calcula el total de todas las cantidades
        for (int cantidad : cantidadesTiposDeListas) {
            total += cantidad;
        }

        for (int i = 0; i < tiposDeListas.length; i++) {
            LegendEntry entry = new LegendEntry();
            // Establece el color y la etiqueta de cada entrada
            entry.formColor = coloresTiposDeListas[i];
            // Calcula el porcentaje que cada cantidad representa del total
            float percentage = (float) cantidadesTiposDeListas[i] / total * 100;
            // Añade la cantidad y el porcentaje al lado del nombre
            entry.label = tiposDeListas[i] + " (" + cantidadesTiposDeListas[i] + " | " + String.format("%.1f", percentage) + "%)";

            // Añade la entrada a la lista
            entries.add(entry);
        }

        // Establece las entradas personalizadas en la leyenda
        legend.setCustom(entries);
    }

    // Método para obtener las entradas del gráfico de pastel
    private ArrayList<PieEntry> getPieEntries() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        int total = 0;

        // Calcula el total de todas las cantidades
        for (int cantidad : cantidadesTiposDeListas) {
            total += cantidad;
        }

        // Calcula los porcentajes y crea las entradas del gráfico de pastel
        for (int i = 0; i < cantidadesTiposDeListas.length; i++) {
            // Calcula el porcentaje que cada cantidad representa del total
            float percentage = (float) cantidadesTiposDeListas[i] / total * 100;
            // Crea una entrada con el porcentaje y la añade a la lista
            entries.add(new PieEntry(percentage));
        }

        return entries;
    }

    // Método para crear el gráfico
    public void createChart() {
        // Configura el gráfico
        pieChart = (PieChart) getSameChart(pieChart, Color.BLACK, Color.TRANSPARENT, 2000);
        // Establece el radio del agujero en el centro del gráfico
        pieChart.setHoleRadius(10);
        // Establece el radio del círculo transparente alrededor del agujero
        pieChart.setTransparentCircleRadius(12);
        // Establece los datos del gráfico
        pieChart.setData(getPieData());
        // Actualiza el gráfico
        pieChart.invalidate();
    }

    // Método para configurar el conjunto de datos
    private DataSet getData(DataSet dataSet) {
        // Establece los colores de las porciones del gráfico de pastel
        dataSet.setColors(coloresTiposDeListas);
        // Establece el tamaño del texto de los valores
        dataSet.setValueTextSize(Color.WHITE);
        dataSet.setValueTextSize(20);
        return dataSet;
    }

    // Método para obtener los datos del gráfico de pastel
    private PieData getPieData() {
        // Crea y configura el conjunto de datos del gráfico de pastel
        PieDataSet pieDataSet = (PieDataSet) getData(new PieDataSet(getPieEntries(), ""));
        // Establece el espacio entre las porciones del gráfico de pastel
        pieDataSet.setSliceSpace(2);
        // Establece el formateador de valores personalizado
        pieDataSet.setValueFormatter(new PercentFormatter() {

            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                // Si el valor es 0, no muestra el texto
                if (value < 6) {
                    return "";
                }

                // De lo contrario, formatea el valor como un porcentaje
                return super.getFormattedValue(value, entry, dataSetIndex, viewPortHandler);
            }
        });

        // Crea los datos del gráfico de pastel con el conjunto de datos
        return new PieData(pieDataSet);
    }

    private void setTextTotalListas() {

        int total = 0;

        for (int cantidadListas : cantidadesTiposDeListas) {

            total += cantidadListas;

        }

        textViewTotal.setText("Total: " + total + " listas.");

        if (total == 0) {
            textoNoHayListas.setVisibility(View.VISIBLE);
        }
    }

}