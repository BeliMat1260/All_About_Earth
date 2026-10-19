package com.example.all_about_earth_.API;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GeocodeParserTest {

    @Test
    void preferisceLaLocalitaEAggiungeIlPaese() {
        String json = """
                {"results":[{
                  "formatted_address":"Via Roma, Milano, Italia",
                  "address_components":[
                    {"long_name":"Milano","types":["locality"]},
                    {"long_name":"Lombardia","types":["administrative_area_level_1"]}
                  ]
                }]}
                """;
        assertEquals("Milano, Italia", GeocodeParser.nome(json));
    }

    @Test
    void senzaRisultatiRestituisceNull() {
        assertNull(GeocodeParser.nome("{\"results\":[]}"));
    }
}
