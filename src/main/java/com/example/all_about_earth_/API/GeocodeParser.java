package com.example.all_about_earth_.API;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Ricava un nome di luogo dalla risposta JSON di Google Geocoding.
 */
public final class GeocodeParser {

    private GeocodeParser() {
    }

    public static String nome(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray results = jsonObject.getAsJsonArray("results");
        if (results == null || results.isEmpty()) {
            return null;
        }

        String cityName = null;
        String subLocality = null;
        String formattedAddress = null;

        for (JsonElement result : results) {
            JsonObject resultObj = result.getAsJsonObject();
            if (formattedAddress == null && resultObj.has("formatted_address")) {
                formattedAddress = resultObj.get("formatted_address").getAsString();
            }

            JsonArray addressComponents = resultObj.getAsJsonArray("address_components");
            if (addressComponents == null) {
                continue;
            }

            for (JsonElement component : addressComponents) {
                JsonObject componentObj = component.getAsJsonObject();
                JsonArray types = componentObj.getAsJsonArray("types");
                String longName = componentObj.get("long_name").getAsString();
                for (JsonElement type : types) {
                    String typeStr = type.getAsString();
                    if (typeStr.equals("locality")) {
                        cityName = longName;
                    } else if (typeStr.equals("sublocality") || typeStr.equals("sublocality_level_1")) {
                        subLocality = longName;
                    } else if (cityName == null && subLocality == null
                            && (typeStr.equals("administrative_area_level_3") || typeStr.equals("administrative_area_level_2"))) {
                        cityName = longName;
                    }
                }
            }
            if (cityName != null) {
                break;
            }
        }

        if (cityName == null && subLocality == null) {
            return formattedAddress;
        }

        String placeName = cityName != null ? cityName : subLocality;
        if (formattedAddress != null && formattedAddress.contains(", ")) {
            placeName += ", " + formattedAddress.substring(formattedAddress.lastIndexOf(", ") + 2);
        }
        return placeName;
    }
}
