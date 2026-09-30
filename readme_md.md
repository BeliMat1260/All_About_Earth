# 🌍 All About Earth

Benvenuto in **All About Earth**, un'applicazione desktop interattiva sviluppata in **Java** con **JavaFX**. Questo progetto è progettato per esplorare dati geografici, gestire ricerche di posizioni e coordinate, offrendo al contempo un sistema completo di gestione utenti e cronologia.

## 🚀 Caratteristiche Principali

L'applicazione è strutturata con un'architettura solida basata su Manager e Modelli ad oggetti, e include:

* **Integrazione API (`/API/API.java`):** Connessione a servizi esterni per il recupero in tempo reale di dati geografici, ambientali e coordinate.
* **Gestione Utenti (`/Login`, `/Managers/ActualUserManager.java`):** Sistema di login, profilazione e salvataggio dei dati utente tramite serializzazione (`User.ser`, `ActualUser.ser`).
* **Cronologia Ricerche (`/Managers/HistoryManager.java`):** Tracciamento e salvataggio locale della cronologia dell'utente (`History.ser`).
* **Interfaccia Grafica Interattiva (`/Applications`):** UI moderna costruita con JavaFX (`hello-view.fxml`), font personalizzati (`LuckiestGuy-Regular.ttf`) e icone dedicate (`/resources`).
* **Ricerca e Navigazione:** Menu per le location, illustrazioni e moduli di ricerca interattivi.

## 🛠️ Tecnologie Utilizzate

* **Linguaggio:** Java
* **Framework UI:** JavaFX
* **Build System:** Maven (`pom.xml`, `mvnw`)
* **Dati & Serializzazione:** Java Object Serialization (`.ser` files)

## 🔑 Configurazione API Key (Fondamentale)

Per permettere all'applicazione di comunicare con i server esterni e recuperare i dati del mondo (es. coordinate, meteo, mappe), **devi inserire una API Key valida**. 

Senza questa chiave, le funzionalità di ricerca e i menu delle location (`Search.java`, `LocationsMenu.java`) genereranno errori di connessione.

**Come configurare la chiave:**

1. Ottieni una chiave API dal provider utilizzato dal progetto (es. Google Maps API, OpenWeatherMap, Mapbox o simili).
2. Apri il file sorgente dedicato alle chiamate di rete:
   `src/main/java/com/example/all_about_earth_/API/API.java`
3. Cerca la costante o la variabile dedicata alla chiave (spesso chiamata `API_KEY` o `TOKEN`) e sostituisci la stringa vuota o il placeholder con la tua chiave reale.
   
   *Esempio:*
   ```java
   // All'interno di API.java
   private static final String API_KEY = "INSERISCI_QUI_LA_TUA_CHIAVE_API";
   ```
4. *Nota di sicurezza:* Ricordati di aggiungere eventuali file di configurazione locale al tuo `.gitignore` se decidi di spostare la chiave in un file `.env` o `.properties` per non esporla pubblicamente su GitHub!

## 📁 Struttura del Progetto

```
All_About_Earth-main/
 ├── .mvn/ & mvnw          # Wrapper e configurazioni Maven
 ├── pom.xml               # Dipendenze del progetto
 ├── *.ser                 # File di salvataggio serializzati (User, History, ecc.)
 └── src/
     └── main/
         ├── java/com/example/all_about_earth_/
         │   ├── API/           # Logica di connessione ai servizi esterni
         │   ├── Applications/  # Controller delle view (Home, Search, Error, ecc.)
         │   ├── Login/         # Logica di autenticazione
         │   ├── Managers/      # Gestori della logica di business (History, User, Login)
         │   └── Object/        # Classi modello (Coordinate, Data, User)
         └── resources/         # Asset grafici (Immagini, FXML, CSS, Font)
```

## ⚙️ Installazione ed Esecuzione

Il progetto utilizza **Maven Wrapper**, quindi non hai bisogno di avere Maven preinstallato sul tuo computer.

1. **Clona la repository:**
   ```bash
   git clone https://github.com/tuo-username/All_About_Earth.git
   cd All_About_Earth
   ```

2. **Configura le API Key:** 
   (Vedi la sezione *Configurazione API Key* qui sopra).

3. **Compila ed esegui l'app:**
   Su **Windows**:
   ```cmd
   mvnw.cmd clean javafx:run
   ```
   Su **macOS / Linux**:
   ```bash
   ./mvnw clean javafx:run
   ```

---
*Progetto sviluppato a scopo didattico / accademico.*