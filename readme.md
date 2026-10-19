# All About Earth

Applicazione desktop in Java 21 e JavaFX. Si cerca una località scrivendone il nome, cliccando il globo o estraendo un punto a caso. Il testo arriva da Gemini, le foto da Google Maps e l'audio da ElevenLabs.

## Chiavi

Le chiavi non stanno nel codice. Dopo il clone copia il modello e incolla i valori:

```text
copy .env.example .env
```

Su macOS e Linux: `cp .env.example .env`.

Il file `.env` resta nella cartella del progetto e Git lo ignora.

```text
MAPS_API_KEY=
GEMINI_API_KEY=
ELEVENLABS_API_KEY=
```

| Nome | Servizio |
| --- | --- |
| `MAPS_API_KEY` | Geocoding e foto di Google Maps |
| `GEMINI_API_KEY` | Testo del luogo |
| `ELEVENLABS_API_KEY` | Lettura audio |

L'app legge `.env` all'avvio, dalla cartella in cui viene lanciata. Se una chiave manca, la finestra di errore indica il nome. Se una chiave è già finita in una commit vecchia, va revocata dal fornitore e ricreata.

## Avvio

Serve il JDK 21. Dalla cartella del repository:

Su Windows:

```cmd
mvnw.cmd test
mvnw.cmd javafx:run
```

Su macOS e Linux:

```bash
./mvnw test
./mvnw javafx:run
```

Il primo avvio crea l'utente `Utente` con password `prova`. Un file `User.ser` già presente non viene sovrascritto. Invio nel campo password esegue il login. `User.ser`, `ActualUser.ser` e `History.ser` restano solo in locale.

## Struttura

```text
├── .env.example
├── pom.xml
├── mvnw / mvnw.cmd
└── src/main/java/com/example/all_about_earth_/
    ├── API/            Maps, Gemini, ElevenLabs e lettura del .env
    ├── Applications/   Globo, ricerca, scheda del luogo
    ├── Login/
    ├── Managers/       Utenti e cronologia
    └── Object/
```
