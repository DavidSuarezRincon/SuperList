package com.david.superlist.NavigationDrawer.MenuListas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.david.superlist.Activities.AddItemsListaActivity;
import com.david.superlist.Activities.AniadirListaActivity;
import com.david.superlist.Activities.mostrarInfoListaActivity;
import com.david.superlist.Adaptadores.AdaptadorLista;
import com.david.superlist.Interfaces.RecyclerViewInterface;
import com.david.superlist.R;
import com.david.superlist.pojos.Lista;
import com.david.superlist.pojos.TareaLista;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

// Esta clase representa el fragmento del menú de listas en la aplicación
public class MenuListasFragment extends Fragment implements Serializable, RecyclerViewInterface {

    // Declaración de variables de instancia
    private RecyclerView recView;
    private FloatingActionButton btnAniadirLista;
    private static int numbersIds;
    private static ArrayList<Lista> lists;
    private static AdaptadorLista adapter;
    private View view;
    private static FirebaseUser currentUser;
    private static FirebaseDatabase database;

    // Método para crear la vista del fragmento
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_gallery, container, false);

        // Inicialización de las vistas y los listeners
        initializeViews();
        setClickListeners();

        // Inicialización de la lista
        numbersIds = 1;
        startLista();
        return view;
    }

    // Método para inicializar las vistas
    private void initializeViews() {
        // Asignación de vistas a variables
        btnAniadirLista = view.findViewById(R.id.btnAniadirLista);
    }

    // Método para establecer los listeners de los botones
    private void setClickListeners() {
        // Asignación de listeners a botones
        btnAniadirLista.setOnClickListener(v -> startAniadirListaActivity());
    }

    // Método para iniciar la lista
    private void startLista() {
        // Inicialización de la base de datos y el usuario actual
        database = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Cargando datos...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        // Referencia a la base de datos
        DatabaseReference ref = database.getReference("SuperList").child(currentUser.getUid()).child("userLists");

        // Listener para cambios en los datos
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lists = new ArrayList<>();
                for (DataSnapshot children : snapshot.getChildren()) {
                    Lista l = children.getValue(Lista.class);
                    lists.add(l);
                }



                adapter = new AdaptadorLista(lists, getActivity(), MenuListasFragment.this);
                recView = view.findViewById(R.id.rvLista);
                recView.setAdapter(adapter);
                recView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("onCancelledError", "Error!", error.toException());
                Toast.makeText(getActivity(), "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };
        ref.addValueEventListener(eventListener);
    }

    // Método para crear una lista
    public static Lista createLista(String nombre, String descripcion, String fechaFin, String tipo, ArrayList<TareaLista> itemsLista) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String currentDate = sdf.format(calendar.getTime());

        return new Lista(numbersIds++, randomColor(), nombre, descripcion, fechaFin, tipo, currentDate, itemsLista);
    }

    // Método para añadir una lista
    public static void addLista(Lista list) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String currentDate = sdf.format(calendar.getTime());

        list.setColor(randomColor());
        list.setCreationDate(currentDate);

        DatabaseReference ref = database.getReference("SuperList").child(currentUser.getUid()).child("userLists");
        lists.add(list);
        ref.setValue(lists);
        adapter.notifyDataSetChanged();
    }

    // Método para generar un color aleatorio
    public static int randomColor() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(150), random.nextInt(150), random.nextInt(150));
    }

    // Método para borrar un elemento de la lista
    public void borrarItemLista(int numLista) {
        DatabaseReference ref = database.getReference("SuperList").child(currentUser.getUid()).child("userLists");

        lists.remove(numLista);
        ref.setValue(lists);

        adapter.notifyItemRemoved(numLista);
    }

    // Método para abrir un elemento de la lista
    public void abrirItemLista(int numLista) {
        Lista listaAbrir = lists.get(numLista);
        ArrayList<TareaLista> listaDetareas = listaAbrir.getTasksList();

        Intent intent = new Intent(getActivity(), AddItemsListaActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("listaDeTareas", listaDetareas);
        bundle.putInt("posLista", numLista);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    // Método para mostrar la información de la lista
    public void mostrarInformacionlista(int numLista) {
        Lista pickedList = lists.get(numLista);

        Intent intent = new Intent(getActivity(), mostrarInfoListaActivity.class);
        intent.putExtra("listaSeleccionada", pickedList);
        startActivity(intent);
    }

    // Método para manejar el clic en un elemento
    @Override
    public void onItemClick(int position) {
        abrirItemLista(position);
    }

    // Método para manejar el clic largo en un elemento
    @Override
    public void onItemLongClick(int position) {
        showPopupMenu(position);
    }

    // Método para mostrar el menú emergente
    private void showPopupMenu(int position) {
        PopupMenu popup = new PopupMenu(getActivity(), recView.getChildAt(position));
        popup.getMenuInflater().inflate(R.menu.activity_menulista, popup.getMenu());
        popup.show();
        initializeOnClickListenerPopUp(position, popup);
    }

    // Método para inicializar el listener del menú emergente
    private void initializeOnClickListenerPopUp(int position, PopupMenu popup) {
        popup.setOnMenuItemClickListener(item -> {
            String optionClicked = item.getTitle().toString();
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

    // Método para cambiar las tareas
    public static void changeTasks(int pos, ArrayList<TareaLista> tasks) {
        DatabaseReference ref = database.getReference("SuperList").child(currentUser.getUid()).child("userLists");
        lists.get(pos).setTasksList(tasks);
        ref.setValue(lists);
    }

    // Método para iniciar la actividad de añadir lista
    private void startAniadirListaActivity() {
        Intent intent = new Intent(getActivity(), AniadirListaActivity.class);
        startActivity(intent);
    }
}