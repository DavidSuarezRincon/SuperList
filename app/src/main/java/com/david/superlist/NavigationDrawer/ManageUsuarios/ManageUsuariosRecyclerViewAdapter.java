package com.david.superlist.NavigationDrawer.ManageUsuarios;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.david.superlist.R;
import com.david.superlist.pojos.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// Adaptador personalizado para el RecyclerView que muestra la lista de usuarios
public class ManageUsuariosRecyclerViewAdapter extends RecyclerView.Adapter<ManageUsuariosRecyclerViewAdapter.ViewHolder> {

    // Lista de usuarios y usuario actual
    private final List<Usuario> listaUsuarios = new ArrayList<>();
    private final FirebaseUser usuarioActual;

    // Constructor que toma el usuario actual como argumento
    public ManageUsuariosRecyclerViewAdapter(FirebaseUser usuario) {
        usuarioActual = usuario;
        cargarUsuariosDesdeFirebase(); // Carga los usuarios desde Firebase
    }

    // Método para cargar los usuarios desde Firebase
    private void cargarUsuariosDesdeFirebase() {
        DatabaseReference referencia = FirebaseDatabase.getInstance().getReference("SuperList");
        referencia.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot instantanea) {
                for (DataSnapshot instantaneaUsuario : instantanea.getChildren()) {
                    Usuario usuario = instantaneaUsuario.getValue(Usuario.class);
                    // Añade el usuario a la lista si no es el usuario actual
                    if (usuario != null && usuario.getEmail() != null && usuarioActual != null && usuarioActual.getEmail() != null && !usuario.getEmail().equals(usuarioActual.getEmail())) {
                        listaUsuarios.add(usuario);
                    }
                }
                notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar error aquí
            }
        });
    }

    // Método para crear ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_manage_usuarios, parent, false);
        return new ViewHolder(vista);
    }

    // Método para vincular ViewHolder con datos
    @Override
    public void onBindViewHolder(final ViewHolder contenedor, int posicion) {
        contenedor.usuario = listaUsuarios.get(posicion);
        contenedor.vistaNombre.setText(listaUsuarios.get(posicion).getNombre());
        contenedor.vistaEmail.setText(listaUsuarios.get(posicion).getEmail());

        // No mostrar el botón de opciones si el usuario es el administrador actual
        if (listaUsuarios.get(posicion).getEmail().equals(usuarioActual.getEmail())) {
            contenedor.botonOpciones.setVisibility(View.GONE);
        } else {
            contenedor.botonOpciones.setVisibility(View.VISIBLE);
        }

        // Establecer un OnClickListener en el botón de opciones
        contenedor.botonOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un PopupMenu aquí
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                MenuInflater inflador = popup.getMenuInflater();
                inflador.inflate(R.menu.manage_users_menu, popup.getMenu());

                // Establecer un listener de clic en el menú
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        DatabaseReference referencia = null;
                        if (itemId == R.id.action_send_password_reset_email) {
                            // Enviar correo electrónico de recuperación de contraseña
                            FirebaseAuth.getInstance().sendPasswordResetEmail(contenedor.usuario.getEmail());
                            return true;
                        } else if (itemId == R.id.action_ban_user || itemId == R.id.action_make_admin) {
                            // Banear usuario o hacer admin a usuario
                            referencia = FirebaseDatabase.getInstance().getReference("SuperList").child(contenedor.usuario.getId());
                            if (itemId == R.id.action_ban_user) {
                                DatabaseReference finalReferencia1 = referencia;
                                new AlertDialog.Builder(v.getContext())
                                        .setTitle("Confirmación")
                                        .setMessage("¿Estás seguro de que quieres banear a este usuario?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                finalReferencia1.child("baned").setValue(true);
                                                contenedor.usuario.setBaned(true);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            } else {
                                DatabaseReference finalReferencia = referencia;
                                new AlertDialog.Builder(v.getContext())
                                        .setTitle("Confirmación")
                                        .setMessage("¿Estás seguro de que quieres hacer administrador a este usuario?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                finalReferencia.child("rol").setValue(1);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                            return true;
                        } else if (itemId == R.id.action_send_message) {
                            // Enviar mensaje a usuario
                            // Aquí necesitarás implementar tu propio método para enviar un mensaje a un usuario
                            // enviarMensajeAUsuario(contenedor.usuario.getEmail());
                            return true;
                        } else {
                            return false;
                        }
                    }
                });

                popup.show(); // Muestra el menú emergente
            }
        });
    }

    // Método para obtener el número de elementos en la lista de usuarios
    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    // Clase ViewHolder para mantener las vistas de cada elemento
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View vista;
        public final TextView vistaNombre;
        public final TextView vistaEmail;
        public final ImageButton botonOpciones;
        public Usuario usuario; // Variable de instancia para el usuario

        public ViewHolder(View vista) {
            super(vista);
            this.vista = vista;
            vistaNombre = (TextView) vista.findViewById(R.id.nombreUsuario);
            vistaEmail = (TextView) vista.findViewById(R.id.emailUsuario);
            botonOpciones = (ImageButton) vista.findViewById(R.id.OpcionesImageButton);
        }
    }
}