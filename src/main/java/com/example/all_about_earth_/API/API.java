package com.example.all_about_earth_.API;

import com.example.all_about_earth_.Applications.Error;
import com.example.all_about_earth_.Applications.Illustration;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import javafx.stage.Stage;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class API {

    private static final String MAPS_API_KEY = chiave("MAPS_API_KEY");
    private static final String GEMINI_API_KEY = chiave("GEMINI_API_KEY");
    private static final String ELEVENLABS_API_KEY = chiave("ELEVENLABS_API_KEY");
    private double latitude, longitude;
    private String writtenSpeech;
    private ByteArrayInputStream spokenSpeech;
    private String place_name;
    private String place_id;

    private boolean exceptionOpened = false;
    private boolean placeFound = true;

    public API(String writtenSpeech, String place_name, String place_id) {
        this.writtenSpeech = writtenSpeech;
        this.place_name = place_name;
        this.place_id = place_id;
    }

    public API() {}

    private static String chiave(String nome) {
        return EnvFile.valore(nome);
    }

    private boolean chiaveMancante(String chiave, String nome) {
        if (!chiave.isEmpty()) {
            return false;
        }
        placeFound = false;
        try {
            new Error("Manca " + nome + " nel file .env.").start(new Stage());
        } catch (Exception ignored) {
        }
        return true;
    }

    public void sendPrompt() {
            if (chiaveMancante(GEMINI_API_KEY, "GEMINI_API_KEY")) {
                return;
            }

            OkHttpClient httpClient = new OkHttpClient();
            try {
                JSONObject part = new JSONObject();
                part.put("text", "Genera un testo informativo dettagliato sulla località geografica specificata, che può essere una città, un insediamento o un'area di interesse. Il testo deve seguire questo formato:\n" +
                                "\n" +
                                "[Nome della località], [Paese/Regione]\n" +
                                "\n" +
                                "Descrizione: Fornisci una descrizione concisa della località, evidenziando le sue caratteristiche geografiche, ambientali o culturali rilevanti. Se si tratta di un insediamento, descrivi il tipo di insediamento e le sue peculiarità.\n" +
                                "Storia: Narra brevemente la storia della località, includendo la sua fondazione, eventi storici significativi o sviluppi chiave.\n" +
                                "Curiosità: Presenta uno o due fatti interessanti, insoliti o unici sulla località, che possano catturare l'attenzione del lettore.\n" +
                                "Utilizza questo nome come riferimento della località, o un insediamento" + place_name + "\n Rispondi solo con il testo informativo" +
                                "\n" +
                                "Assicurati che il testo sia accurato, informativo e coinvolgente. Rispondi fornendo solo il testo informativo, senza preamboli o conclusioni aggiuntive.");

                JSONArray partsArray = new JSONArray();
                partsArray.put(part);

                JSONObject content = new JSONObject();
                content.put("parts", partsArray);

                JSONArray contentsArray = new JSONArray();
                contentsArray.put(content);

                JSONObject requestBody = new JSONObject();
                requestBody.put("contents", contentsArray);

                RequestBody body = RequestBody.create(
                        MediaType.get("application/json; charset=utf-8"),
                        requestBody.toString()
                );

                Request request = new Request.Builder()
                        .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + GEMINI_API_KEY)
                        .post(body)
                        .build();

                try (Response response = httpClient.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        new Error("Errore nella richiesta a Gemini (" + response.code() + ").").start(new Stage());
                        return;
                    }

                    assert response.body() != null;
                    String responseBody = response.body().string();
                    JSONObject jsonResponse = new JSONObject(responseBody);

                    //Parsing
                    JSONArray candidates = jsonResponse.optJSONArray("candidates");
                    if (candidates != null && !candidates.isEmpty()) {
                        JSONObject firstCandidate = candidates.getJSONObject(0);
                        JSONObject content0 = firstCandidate.getJSONObject("content");
                        JSONArray parts = content0.getJSONArray("parts");
                        if (!parts.isEmpty()) {
                            JSONObject firstPart = parts.getJSONObject(0);
                            getPlaceId();
                            writtenSpeech = firstPart.getString("text");
                            getSpeech();
                            return;
                        }
                    }
                    Error error = new Error("Nessuna risposta disponibile.");
                    error.start(new Stage());
                }
            }catch (Exception e) {
                Error error = new Error("Nessuna risposta disponibile.");
                try {
                    error.start(new Stage());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
    }

    //Se è un luogo sconosciuto ritorna false
    public boolean getPlaceNameFromCoordinates() {
        if (chiaveMancante(MAPS_API_KEY, "MAPS_API_KEY")) {
            return false;
        }
        try {
            String urlString = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&key=" + MAPS_API_KEY;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            place_name = GeocodeParser.nome(response.toString());
            if (place_name == null) {
                place_name = "Luogo sconosciuto";
                placeFound = false;
                return false;
            }

            if (place_name.contains("+")){
                Error error = new Error("Hai selezionato un luogo non disponibile");
                error.start(new Stage());
                return false;
            }
            getPlaceId();
            sendPrompt();
            if (writtenSpeech == null || writtenSpeech.isBlank()) {
                placeFound = false;
                return false;
            }
            placeFound = true;
            return true;

        } catch (Exception e) {
            place_name = "Luogo sconosciuto";
            placeFound = false;
            Error error = new Error("Errore durante il recupero del nome del luogo: " + e.getMessage());
            error.start(new Stage());
        }
        return false;
    }

    public void getCityByText(String text) {
        placeFound = true;
        if (chiaveMancante(MAPS_API_KEY, "MAPS_API_KEY")) {
            return;
        }
        try {
            String urlString = "https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(text, StandardCharsets.UTF_8) + "&key=" + MAPS_API_KEY;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonArray results = jsonObject.getAsJsonArray("results");

            if (results == null || results.isEmpty()) {
                placeFound = false;
                new Error("Nessun risultato trovato per: " + text).start(new Stage());
                return;
            }

            JsonObject firstResult = results.get(0).getAsJsonObject();
            JsonArray addressComponents = firstResult.getAsJsonArray("address_components");

            String foundCity = null;
            String foundCountry = null;

            for (JsonElement element : addressComponents) {
                JsonObject obj = element.getAsJsonObject();
                JsonArray types = obj.getAsJsonArray("types");

                if (types.toString().contains("locality")) {
                    foundCity = obj.get("long_name").getAsString();
                }
                if (types.toString().contains("country")) {
                    foundCountry = obj.get("long_name").getAsString();
                }
            }

            if (foundCity != null && foundCountry != null) {
                place_name = foundCity + ", " + foundCountry;
                System.out.println("✅ Città trovata: " + place_name);
            } else {
                throw new Exception("Nessuna città trovata per: " + text);
            }

        } catch (Exception e) {
            System.err.println("Errore: " + e.getMessage());
            placeFound = false;
            Error error = new Error(e.getMessage());
            try {
                error.start(new Stage());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void getSpeech() {
        if (chiaveMancante(ELEVENLABS_API_KEY, "ELEVENLABS_API_KEY") || writtenSpeech == null) {
            return;
        }

        JSONObject json = new JSONObject();
        json.put("text", writtenSpeech);
        json.put("model_id", "eleven_multilingual_v2");

        try {
            HttpResponse<InputStream> mp3 = Unirest.post("https://api.elevenlabs.io/v1/text-to-speech/JBFqnCBsd6RMkjVDRZzb?output_format=mp3_44100_128")
                    .header("xi-api-key", ELEVENLABS_API_KEY)
                    .header("Content-Type", "application/json")
                    .body(json.toString())
                    .asBinary();

            if (mp3.getStatus() == 200) {
                spokenSpeech = new ByteArrayInputStream(mp3.getBody().readAllBytes());
            } else {
                System.out.println("Errore nella richiesta API: " + mp3.getStatus());
                Error error = new Error("Errore nella richiesta API");
                error.start(new Stage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPlaceId(){
        try{
            place_name = place_name.replace("*","");
            if (!place_name.equals("Il luogo inserito non è valido")){
                OkHttpClient client = new OkHttpClient();
                String url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?" + "input=" + place_name.replace(" ", "%20") + "&inputtype=textquery&fields=place_id&key=" + MAPS_API_KEY;

                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                assert response.body() != null;
                String jsonResponse = response.body().string();

                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray candidates = jsonObject.getJSONArray("candidates");

                if (!candidates.isEmpty()) {
                    place_id = candidates.getJSONObject(0).getString("place_id");
                }
            }else {
                place_id = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] getPlacePhotos(){
        exceptionOpened = false;

        String[] photo = new String[10];
        System.out.println(place_name);
        System.out.println(place_id);
        try{
            if (place_id != null){
                OkHttpClient client = new OkHttpClient();
                String url = "https://maps.googleapis.com/maps/api/place/details/json?" + "place_id=" + place_id + "&fields=photos&key=" + MAPS_API_KEY;
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                assert response.body() != null;
                String jsonResponse = response.body().string();

                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONObject result = jsonObject.getJSONObject("result");

                System.out.println(isExceptionOpened());
                if (result.has("photos")) {
                    JSONArray photos = result.getJSONArray("photos");
                    int count = Math.min(photos.length(), 10);

                    for (int i = 0; i < count; i++) {
                        String photoReference = photos.getJSONObject(i).getString("photo_reference");
                        photo[i] = "https://maps.googleapis.com/maps/api/place/photo?" + "maxwidth=1050&photo_reference=" + photoReference + "&key=" + MAPS_API_KEY;
                    }

                } else {
                    Error error = new Error("Nessuna foto trovata.");
                    exceptionOpened = true;
                    System.out.println(isExceptionOpened());
                    error.start(new Stage());

                }
            } else {
                Error error = new Error("Nessuna foto trovata.");
                exceptionOpened = true;
                error.start(new Stage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return photo;
    }

    public boolean isExceptionOpened() {
        return exceptionOpened;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ByteArrayInputStream getSpokenSpeech() {
        return spokenSpeech;
    }

    public String getWrittenSpeech() {
        return writtenSpeech;
    }

    public String getPlace_name() {
        return place_name;
    }

    public boolean isPlaceFound() {
        return placeFound;
    }

    public void setPlaceFound(boolean placeFound) {
        this.placeFound = placeFound;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }
}
