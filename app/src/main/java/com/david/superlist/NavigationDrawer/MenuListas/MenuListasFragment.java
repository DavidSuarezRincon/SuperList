package com.david.superlist.NavigationDrawer.MenuListas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.david.superlist.Activities.AddItemsListaActivity;
import com.david.superlist.Activities.AniadirListaActivity;
import com.david.superlist.Activities.activity_mostrarInfo_Lista;
import com.david.superlist.Adaptadores.AdaptadorLista;
import com.david.superlist.Interfaces.RecyclerViewInterface;
import com.david.superlist.R;
import com.david.superlist.pojos.Lista;
import com.david.superlist.pojos.TareaLista;
import com.david.superlist.pojos.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MenuListasFragment extends Fragment implements Serializable, RecyclerViewInterface {

    private Usuario logedUser;
    private RecyclerView recView;
    private static int numbersIds;
    private FloatingActionButton btnAniadirLista;
    private static ArrayList<Lista> listData;
    private static AdaptadorLista adapter;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_gallery, container, false);

        btnAniadirLista = view.findViewById(R.id.btnAniadirLista);
        btnAniadirLista.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AniadirListaActivity.class);
            startActivity(intent);
        });

        numbersIds = 1;

        startLista();

        return view;
    }

    // Método para inicializar la lista y el adaptador
    private void startLista() {

        logedUser = getActivity().getIntent().getExtras().getParcelable("usuarioLogeado");
        listData = logedUser.getUserLists();
        adapter = new AdaptadorLista(listData, getActivity(), this);

        if (listData.isEmpty()) {
            addLista(createLista("Lista de la compra 1", "Esto es una prueba de descripción", "10/10/2080", "otros", new ArrayList<TareaLista>()));
            addLista(createLista("Lista", "Lista", "20/10/2050", "Lista de la compra", new ArrayList<TareaLista>()));
        }

        recView = view.findViewById(R.id.rvLista);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView.setAdapter(adapter);
        registerForContextMenu(recView);
    }

    // Método para añadir una lista
    public static Lista createLista(String nombre, String Descripcion, String fechaFin, String tipo, ArrayList<TareaLista> itemsLista) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String currentDate = sdf.format(calendar.getTime());

        return new Lista(numbersIds++, randomColor(), nombre, Descripcion, fechaFin, tipo, currentDate, itemsLista);
    }

    public static void addLista(Lista list) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String currentDate = sdf.format(calendar.getTime());

        list.setColor(randomColor());
        list.setCreationDate(currentDate);

        listData.add(list);
        adapter.notifyDataSetChanged();
    }

    // Método para generar un color aleatorio
    public static int randomColor() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(150), random.nextInt(150), random.nextInt(150));
    }

    // Método para borrar una lista
    public void borrarItemLista(int numLista) {
        listData.remove(numLista);
        adapter.notifyItemRemoved(numLista);
    }

    public void abrirItemLista(int numLista) {
        Lista listaAbrir = listData.get(numLista);
        ArrayList<TareaLista> listaDetareas = listaAbrir.getTasksList();

        Intent intent = new Intent(getActivity(), AddItemsListaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("listaDeTareas", listaDetareas);
        bundle.putInt("posLista", numLista);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void mostrarInformacionlista(int numLista) {
        Lista pickedList = listData.get(numLista);

        Intent intent = new Intent(getActivity(), activity_mostrarInfo_Lista.class);
        intent.putExtra("listaSeleccionada", pickedList);
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
        PopupMenu popup = new PopupMenu(getActivity(), recView.getChildAt(position));
        // Inflar el menú con los elementos definidos en 'R.menu.activity_menulista'
        popup.getMenuInflater().inflate(R.menu.activity_menulista, popup.getMenu());
        popup.show();

        // Establecer un listener de eventos para los elementos del menú
        popup.setOnMenuItemClickListener(item -> {

            String optionClicked = (String) item.getTitle(); // El nombre del item que fue clicado (Abrir, Info, Borrar).
            String openText = getResources().getString(R.string.textoMenuListaAbrir);
            String infoText = getResources().getString(R.string.textDesplegableBotonInfo);
            String deleteText = getResources().getString(R.string.textoMenuListaBorrar);

            if (optionClicked.equalsIgnoreCase(openText)) {
                abrirItemLista(position);
            } else if (optionClicked.equalsIgnoreCase(deleteText)) {
                borrarItemLista(position);
            } else if (optionClicked.equalsIgnoreCase(infoText)) {
                mostrarInformacionlista(position);
            }
            return true;
        });
    }

    public static void changeTasks(int pos, ArrayList<TareaLista> tasks) {
        listData.get(pos).setTasksList(tasks);
    }

}