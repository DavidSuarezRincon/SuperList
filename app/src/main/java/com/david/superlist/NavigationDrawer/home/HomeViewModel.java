package com.david.superlist.NavigationDrawer.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// Esta clase representa el ViewModel para el fragmento de inicio
public class HomeViewModel extends ViewModel {

    // Variable MutableLiveData que contiene el texto a mostrar en la vista
    private final MutableLiveData<String> mText;

    // Constructor de la clase
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        // Establece el valor inicial del texto
        mText.setValue("Welcome to our app!");
    }

    // Método para obtener el texto a mostrar en la vista
    public LiveData<String> getText() {
        return mText;
    }

    // Método para establecer el texto de bienvenida con el nombre del usuario
    public void setTextUserName(String userName) {
        mText.setValue(userName + ", welcome to SuperList!");
    }
}