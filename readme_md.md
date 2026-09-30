# 🌍 All About Earth

Benvenuto in **All About Earth**, un'applicazione desktop interattiva sviluppata in Java e JavaFX. Questo progetto permette agli utenti di esplorare luoghi, visualizzare dettagli geografici e gestire la propria cronologia di ricerca attraverso un'interfaccia grafica intuitiva e ricca di contenuti visivi.

## 🚀 Caratteristiche Principali

*   **Autenticazione Utente:** Sistema di login personalizzato con gestione della sessione (`LoginManager`, `ActualUserManager`).
*   **Esplorazione e Ricerca:** Funzionalità di ricerca avanzata per scoprire nuove località, coordinate e dettagli sul nostro pianeta.
*   **Cronologia Salvata:** Salvataggio e gestione dello storico delle ricerche degli utenti in locale.
*   **Integrazione API:** Modulo dedicato per interfacciarsi con API esterne e ottenere dati in tempo reale.
*   **Interfaccia Multimediale:** Design curato con JavaFX (`.fxml` e CSS), font personalizzati e icone interattive (audio, mappe, menu laterali).
*   **Salvataggio Dati in Locale:** Utilizzo della serializzazione Java (`.ser`) per persistere in modo efficiente i dati degli utenti e la cronologia.

## 🛠️ Tecnologie Utilizzate

*   **Linguaggio:** Java
*   **Interfaccia Grafica:** JavaFX (tramite file FXML)
*   **Build Automation:** Maven
*   **Persistenza Dati:** Java Object Serialization (`.ser` files)
*   **Styling:** JavaFX CSS

## 📁 Struttura del Progetto

Il progetto segue un'architettura Model-View-Controller (MVC) strutturata in package per una facile manutenzione:

```text
src/main/java/com/example/all_about_earth_/
 ├── API/           # Classi per la gestione delle chiamate ad API esterne
 ├── Applications/  # Controller delle schermate JavaFX (Home, Search, Locations, ecc.)
 ├── Login/         # Logica e UI per l'autenticazione degli utenti
 ├── Managers/      # Gestori della logica di business (HistoryManager, LoginManager, ecc.)
 └── Object/        # Modelli dei dati (User, Coordinate, Data)
```

## ⚙️ Prerequisiti

Per compilare ed eseguire il progetto, assicurati di avere installato sulla tua macchina:

*   **Java Development Kit (JDK):** Versione 11 o superiore (richiesto per JavaFX e i moduli Java).
*   **Maven:** Anche se il progetto include un Maven Wrapper (`mvnw`), è consigliato avere Maven installato, o un IDE compatibile (IntelliJ IDEA, Eclipse, VS Code).

## 🏃‍♂️ Come Avviare l'Applicazione

Puoi avviare il progetto facilmente clonando la repository e utilizzando il Maven wrapper incluso.

1. **Clona la repository:**
   ```bash
   git clone https://github.com/tuo-username/All_About_Earth.git
   cd All_About_Earth-main
   ```

2. **Compila ed esegui con Maven:**

   *Su Windows:*
   ```bash
   mvnw.cmd clean javafx:run
   ```

   *Su macOS/Linux:*
   ```bash
   ./mvnw clean javafx:run
   ```

## 💾 Gestione dei Dati (File `.ser`)
L'applicazione crea e utilizza dei file `.ser` (es. `User.ser`, `ActualUser.ser`, `History.ser`) posizionati nella root del progetto per memorizzare localmente lo stato dell'applicazione, gli account registrati e le ricerche passate. Se desideri resettare completamente l'applicazione, ti basterà eliminare questi file.

---
*Sviluppato con passione per l'esplorazione del mondo.* 🌎