package com.david.superlist.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.david.superlist.Adaptadores.AdaptadorLista;
import com.david.superlist.Interfaces.RecyclerViewInterface;
import com.david.superlist.R;
import com.david.superlist.Pojos.Lista;
import com.david.superlist.Pojos.TareaLista;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

// Clase MainActivity que extiende de AppCompatActivity e implementa Serializable y RecyclerViewInterface
public class MainActivity extends AppCompatActivity implements Serializable, RecyclerViewInterface {

    // Declaración de variables
    private RecyclerView recView;
    private static int numbersIds;
    private FloatingActionButton btnAniadirLista;
    private static ArrayList<Lista> datosLista;
    private static AdaptadorLista adaptador;


    // Método onCreate que se ejecuta al iniciar la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de botones y asignación de eventos onClick

        btnAniadirLista = findViewById(R.id.btnAniadirLista);
        btnAniadirLista.setOnClickListener(view -> {
            Intent intent = new Intent(this, AniadirListaActivity.class);
            startActivity(intent);
        });

        numbersIds = 1;

        // Llamada a métodos iniciar
        iniciarLista();
    }

    // Método para inicializar la lista y el adaptador
    private void iniciarLista() {

        datosLista = new ArrayList<>();
        adaptador = new AdaptadorLista(datosLista, this.getApplicationContext(), this);

//        ArrayList<Lista> listas = Lista.getTestListas();

//        for (Lista lista : listas) {
//            aniadirLista(lista);
//        }

        crearLista("Lista de la compra 1", "Esto es una prueba de descripción", "Hola", "adios", new ArrayList<TareaLista>());
        crearLista("Lista", "Lista", "Hola", "adios", new ArrayList<TareaLista>());

        recView = findViewById(R.id.rvLista);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(adaptador);
        registerForContextMenu(recView);
    }

    // Método para añadir una lista
    public static void crearLista(String nombre, String Descripcion, String fechaFin, String tipo, ArrayList<TareaLista> itemsLista) {
        Random rand = new Random();
        Calendar calendario = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String currentDate = sdf.format(calendario.getTime());

        datosLista.add(new Lista(numbersIds++, colorRandom(), nombre, Descripcion, fechaFin, tipo, currentDate, itemsLista));
        adaptador.notifyDataSetChanged();
    }

    // Método para generar un color aleatorio
    public static int colorRandom() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(150), random.nextInt(150), random.nextInt(150));
    }

    // Método para borrar una lista
    public void borrarItemLista(int numLista) {
        datosLista.remove(numLista);
        adaptador.notifyItemRemoved(numLista);
    }

    public void abrirItemLista(int numLista) {
        Lista listaAbrir = datosLista.get(numLista);
        ArrayList<TareaLista> listaDetareas = listaAbrir.getItemsLista();

        Intent intent = new Intent(this, AddItemsListaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("listaDeTareas", listaDetareas);
        bundle.putInt("posLista", numLista);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    // Método que se ejecuta al hacer click en un item
    @Override
    public void onItemClick(int position) {
        abrirItemLista(position);
    }

    // Método que se ejecuta al hacer click largo en un item
    @Override
    public void onItemLongClick(int position) {

        // Crear un nuevo menú emergente en la posición del elemento seleccionado
        PopupMenu popup = new PopupMenu(MainActivity.this, recView.getChildAt(position));
        // Inflar el menú con los elementos definidos en 'R.menu.activity_menulista'
        popup.getMenuInflater().inflate(R.menu.activity_menulista, popup.getMenu());
        popup.show();

        // Establecer un listener de eventos para los elementos del menú
        popup.setOnMenuItemClickListener(item -> {

            String nombreItemClicado = (String) item.getTitle(); // El nombre del item que fue clicado (Abrir, Borrar).
            String textAbrir = getResources().getString(R.string.textoMenuListaAbrir);
            String textBorrar = getResources().getString(R.string.textoMenuListaBorrar);

            if (nombreItemClicado.equalsIgnoreCase(textAbrir)) {
                abrirItemLista(position);
            } else if (nombreItemClicado.equalsIgnoreCase(textBorrar)) {
                borrarItemLista(position);
            }
            return true;
        });
    }

    public static void cambiarTareasLista(int pos, ArrayList<TareaLista> tareas) {

        datosLista.get(pos).setItemsLista(tareas);

    }

}