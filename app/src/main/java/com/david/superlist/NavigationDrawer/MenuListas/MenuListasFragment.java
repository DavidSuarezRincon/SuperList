package com.david.superlist.NavigationDrawer.MenuListas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

public class MenuListasFragment extends Fragment implements Serializable, RecyclerViewInterface {

    private RecyclerView recView;
    private FloatingActionButton btnAniadirLista;
    private static int numbersIds;
    private static ArrayList<Lista> listData;
    private static AdaptadorLista adapter;
    private View view;
    private static FirebaseUser currentUser;
    private static FirebaseDatabase database;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_gallery, container, false);

        initializeViews();
        setClickListeners();

        numbersIds = 1;
        startLista();
        return view;
    }

    private void initializeViews() {
        btnAniadirLista = view.findViewById(R.id.btnAniadirLista);
    }

    private void setClickListeners() {
        btnAniadirLista.setOnClickListener(v -> startAniadirListaActivity());
    }

    private void startLista() {
        database = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = database.getReference("SuperList").child(currentUser.getUid()).child("userLists");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Este método se llamará cuando se realice un cambio en los datos en esta ruta
                // Puedes manejar los datos en dataSnapshot
                listData = new ArrayList<>();
                for (DataSnapshot children : snapshot.getChildren()) {
                    Lista l = children.getValue(Lista.class);
                    listData.add(l);
                }

                adapter = new AdaptadorLista(listData, getActivity(), MenuListasFragment.this);

                recView = view.findViewById(R.id.rvLista);
                recView.setHasFixedSize(true);
                recView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recView.setAdapter(adapter);
                registerForContextMenu(recView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.e("onCancelledError", "Error!", error.toException());

            }
        };
        ref.addValueEventListener(eventListener);
    }

    public static Lista createLista(String nombre, String descripcion, String fechaFin, String tipo, ArrayList<TareaLista> itemsLista) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String currentDate = sdf.format(calendar.getTime());

        return new Lista(numbersIds++, randomColor(), nombre, descripcion, fechaFin, tipo, currentDate, itemsLista);
    }

    public static void addLista(Lista list) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String currentDate = sdf.format(calendar.getTime());

        list.setColor(randomColor());
        list.setCreationDate(currentDate);

        DatabaseReference ref = database.getReference("SuperList").child(currentUser.getUid()).child("userLists");
        listData.add(list);
        ref.setValue(listData);
        adapter.notifyDataSetChanged();
    }

    public static int randomColor() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(150), random.nextInt(150), random.nextInt(150));
    }

    public void borrarItemLista(int numLista) {
        DatabaseReference ref = database.getReference("SuperList").child(currentUser.getUid()).child("userLists");

        listData.remove(numLista);
        ref.setValue(listData);

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

    @Override
    public void onItemClick(int position) {
        abrirItemLista(position);
    }

    @Override
    public void onItemLongClick(int position) {
        PopupMenu popup = new PopupMenu(getActivity(), recView.getChildAt(position));
        popup.getMenuInflater().inflate(R.menu.activity_menulista, popup.getMenu());
        popup.show();
        initializeOnClickListenerPopUp(position, popup);
    }

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

    public static void changeTasks(int pos, ArrayList<TareaLista> tasks) {
        DatabaseReference ref = database.getReference("SuperList").child(currentUser.getUid()).child("userLists");
        listData.get(pos).setTasksList(tasks);
        ref.setValue(listData);
    }

    private void startAniadirListaActivity() {
        Intent intent = new Intent(getActivity(), AniadirListaActivity.class);
        startActivity(intent);
    }
}