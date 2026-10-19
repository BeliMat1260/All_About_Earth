package com.example.all_about_earth_.Managers;

import com.example.all_about_earth_.Object.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginManagerTest {

    @TempDir
    Path temp;

    @Test
    void ilPrimoAvvioCreaLutenteDiProva() {
        LoginManager manager = new LoginManager(temp.resolve("User.ser").toFile());
        manager.createFile();
        assertTrue(manager.checkUser(new User("Utente", "prova")));
        assertFalse(manager.checkUser(new User("Utente", "sbagliata")));
    }

    @Test
    void unFileGiaPienoNonVieneSovrascritto() throws Exception {
        File file = temp.resolve("User.ser").toFile();
        ArrayList<User> esistenti = new ArrayList<>();
        esistenti.add(new User("mattia", "segreta"));
        try (ObjectOutputStream out = new ObjectOutputStream(new java.io.FileOutputStream(file))) {
            out.writeObject(esistenti);
        }

        LoginManager manager = new LoginManager(file);
        manager.createFile();
        assertTrue(manager.checkUser(new User("mattia", "segreta")));
        assertFalse(manager.checkUser(new User("Utente", "prova")));
    }
}
