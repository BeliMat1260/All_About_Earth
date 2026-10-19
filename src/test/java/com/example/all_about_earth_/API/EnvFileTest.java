package com.example.all_about_earth_.API;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EnvFileTest {

    @TempDir
    Path temp;

    @Test
    void leggeLeChiaviEIgnoraCommentiEVirgolette() throws Exception {
        Path env = temp.resolve(".env");
        Files.writeString(env, """
                # commento
                MAPS_API_KEY=maps-test
                GEMINI_API_KEY="gemini-test"
                export ELEVENLABS_API_KEY='voice-test'
                
                """);

        Map<String, String> valori = EnvFile.leggi(env);
        assertEquals("maps-test", valori.get("MAPS_API_KEY"));
        assertEquals("gemini-test", valori.get("GEMINI_API_KEY"));
        assertEquals("voice-test", valori.get("ELEVENLABS_API_KEY"));
        assertFalse(valori.containsKey("# commento"));
    }
}
