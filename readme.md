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

## Chiavi API

Le chiavi stanno nel file `.env`, nella cartella del progetto:

```text
MAPS_API_KEY=
GEMINI_API_KEY=
ELEVENLABS_API_KEY=
```

`.env` è già presente in locale ed è ignorato da Git. Il modello committato è `.env.example`. Se una chiave è già finita in una commit precedente, va revocata dal fornitore e ricreata. I file `User.ser`, `ActualUser.ser` e `History.ser` restano solo in locale.

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

2. **Incolla le tre chiavi** nel file `.env`, come nella sezione sopra.

3. **Compila, testa ed esegui:**
   Su **Windows**:
   ```cmd
   mvnw.cmd test
   mvnw.cmd javafx:run
   ```
   Su **macOS / Linux**:
   ```bash
   ./mvnw test
   ./mvnw javafx:run
   ```

Il primo avvio crea l'utente `Utente` con password `prova` e non cancella un file utenti già presente. Invio nel campo password esegue il login. Il pulsante casuale imposta sia latitudine sia longitudine.

---
*Progetto sviluppato a scopo didattico / accademico.*
