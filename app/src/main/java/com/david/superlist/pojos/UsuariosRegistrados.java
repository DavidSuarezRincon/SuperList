package com.david.superlist.pojos;

import java.util.ArrayList;

public class UsuariosRegistrados {

    private static ArrayList<Usuario> users = new ArrayList<>();
    private static int usersIds = 1;

    public static Usuario getUser(int index) {
        return users.get(index);
    }

    public static void addNormalUser(String email, String password) {
        users.add(new Usuario(usersIds++, email, password, 0));
    }

    public static void addAdminlUser(String email, String password) {
        users.add(new Usuario(usersIds++, email, password, 1));
    }

    public static Usuario getUser(String email) {
        for (Usuario user : users) {
            if (user.hasThisUser(email)) {
                return user;
            }
        }
        return null;
    }

    public static boolean removeUser(String email, String password) {
        if (users.isEmpty()) {
            return false;
        }

        Usuario user = null;

        for (int i = 0; i < users.size(); i++) {
            user = users.get(i);
            if (user.hasThisUser(email) && user.hasThisPassword(password)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public static boolean updateUserPassword(String email, String oldPassword, String newPassword) {
        if (users.isEmpty()) {
            return false;
        }
        for (Usuario user : users) {
            if (user.hasThisUser(email) && user.hasThisPassword(oldPassword)) {
                user.setPassword(newPassword);
            }
        }
        return false;
    }

    public static boolean existUser(String email) {
        for (Usuario user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public static boolean existUser(Usuario user) {

        String emailUser = user.getEmail();

        for (Usuario auxUser : users) {
            if (auxUser.hasThisUser(emailUser)) {
                return true;
            }
        }

        return false;
    }
}