package com.example.all_about_earth_.API;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Legge le chiavi da un file .env nella cartella di avvio.
 * Una variabile d'ambiente già impostata vale solo se nel file la chiave è vuota.
 */
public final class EnvFile {

    private static final Map<String, String> VALORI = leggi(Path.of(System.getProperty("user.dir", "."), ".env"));

    private EnvFile() {
    }

    public static String valore(String nome) {
        String dalFile = VALORI.get(nome);
        if (dalFile != null && !dalFile.isBlank()) {
            return dalFile.trim();
        }
        String dallAmbiente = System.getenv(nome);
        return dallAmbiente == null ? "" : dallAmbiente.trim();
    }

    static Map<String, String> leggi(Path percorso) {
        Map<String, String> valori = new LinkedHashMap<>();
        if (percorso == null || !Files.exists(percorso)) {
            return valori;
        }
        try {
            for (String riga : Files.readAllLines(percorso, StandardCharsets.UTF_8)) {
                String linea = riga.trim();
                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }
                if (linea.startsWith("export ")) {
                    linea = linea.substring("export ".length()).trim();
                }
                int uguale = linea.indexOf('=');
                if (uguale <= 0) {
                    continue;
                }
                String chiave = linea.substring(0, uguale).trim();
                String valore = linea.substring(uguale + 1).trim();
                if (valore.length() >= 2
                        && ((valore.startsWith("\"") && valore.endsWith("\""))
                        || (valore.startsWith("'") && valore.endsWith("'")))) {
                    valore = valore.substring(1, valore.length() - 1);
                }
                valori.put(chiave, valore);
            }
        } catch (IOException ignored) {
            return valori;
        }
        return valori;
    }
}
