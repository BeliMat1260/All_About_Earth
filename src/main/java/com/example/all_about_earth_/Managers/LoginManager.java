package com.example.all_about_earth_.Managers;


import com.example.all_about_earth_.Object.User;

import java.io.*;
import java.util.ArrayList;

//todo:aggiungere controllo email
public class LoginManager {
    private final File f;
    private ArrayList<User> users = new ArrayList<>();

    public LoginManager() {
        this(new File("User.ser"));
    }

    public LoginManager(File file) {
        this.f = file;
    }

    public void createFile() {
        if (f.exists() && f.length() > 0) {
            return;
        }
        users = new ArrayList<>();
        users.add(new User("Utente", "prova"));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUser(User user){
        readFile();

        for(User checkUser : users){
            if(checkUser.getEmail().equals(user.getEmail())){
                if(checkUser.getPsw().equals(user.getPsw())){
                    return true;
                }
            }
        }

        return false;
    }

    public void readFile() {
        if (!f.exists() || f.length() == 0) {
            users = new ArrayList<>();
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            users = (ArrayList<User>) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }
}
