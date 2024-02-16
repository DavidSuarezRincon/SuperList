package com.david.superlist.NavigationDrawer.grafico;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.david.superlist.R;
import com.david.superlist.pojos.Lista;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GraficoFragment extends Fragment {

    private PieChart pieChart;
    private TextView textViewTotal;
    private TextView textoNoHayListas;
    private String[] tiposDeListas;
    private int[] cantidadesTiposDeListas;
    int naranja = Color.parseColor("#FFA500");
    private final int[] coloresTiposDeListas = {Color.RED, naranja, Color.BLUE, Color.GREEN, Color.MAGENTA};

    // Variables de instancia
    private int listaCompra = 0;
    private int listaDeseos = 0;
    private int listaDeTareas = 0;
    private int receta = 0;
    private int otro = 0;
    private Context contextoStrings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_grafico, container, false);

        // Inicializa el gráfico de pastel
        pieChart = view.findViewById(R.id.pieChart);
        textViewTotal = view.findViewById(R.id.textoTotalListas);
        textoNoHayListas = view.findViewById(R.id.textoNoHayListas);
        inicializarCantidadListas();

        contextoStrings = this.getContext();

        String textoListaCompra = contextoStrings.getString(R.string.textoListaCompra);
        String textoListaDeseos = contextoStrings.getString(R.string.textoListaDeseos);
        String textoListaTareas = contextoStrings.getString(R.string.textoListaTareas);
        String textoReceta = contextoStrings.getString(R.string.textoReceta);
        String textoOtraLista = contextoStrings.getString(R.string.textoOtraLista);

        tiposDeListas = new String[5];

        tiposDeListas[0] = textoListaCompra;
        tiposDeListas[1] = textoListaDeseos;
        tiposDeListas[2] = textoListaTareas;
        tiposDeListas[3] = textoReceta;
        tiposDeListas[4] = textoOtraLista;

        return view;
    }

    private void inicializarCantidadListas() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("SuperList");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot listSnapshot : userSnapshot.child("userLists").getChildren()) {
                        Lista lista = listSnapshot.getValue(Lista.class);
                        if (lista != null) {
                            String tipo = lista.getType();
                            if (tipo.equals(tiposDeListas[0])) {
                                listaCompra++;
                            }
                            if (tipo.equals(tiposDeListas[1])) {
                                listaDeseos++;
                            }
                            if (tipo.equals(tiposDeListas[2])) {
                                listaDeTareas++;
                            }
                            if (tipo.equals(tiposDeListas[3])) {
                                receta++;
                            }
                            if (tipo.equals(tiposDeListas[4])) {
                                otro++;
                            }
                        }
                    }
                }
                cantidadesTiposDeListas = new int[5];
                cantidadesTiposDeListas[0] = listaCompra;
                cantidadesTiposDeListas[1] = listaDeseos;
                cantidadesTiposDeListas[2] = listaDeTareas;
                cantidadesTiposDeListas[3] = receta;
                cantidadesTiposDeListas[4] = otro;

                createChart();
                setTextTotalListas();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar error
            }
        });
    }

    // Método para configurar el gráfico
    private Chart getSameChart(Chart chart, int background, int animateY) {
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
            float percentage = 0;

            if (total != 0){
                 percentage = (float) cantidadesTiposDeListas[i] / total * 100;
            }

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
        pieChart = (PieChart) getSameChart(pieChart, Color.TRANSPARENT, 2000);
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
        dataSet.setValueTextColor(Color.WHITE);
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

        textViewTotal.setText("Total: " + total);

        if (total == 0) {
            textoNoHayListas.setVisibility(View.VISIBLE);
        }
    }
}