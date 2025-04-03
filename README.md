# Space Invaders Game

Ein Java-basiertes Space Invaders Spiel mit Sandbox-Modus, Power-ups und Punktesystem.

## Projektstruktur

Das Projekt folgt einer standardmäßigen Java-Struktur mit Hauptcode in `src/java`:

```
src/java/
├── app/       # Starter-Klassen der Anwendung
├── core/      # Kern-Spielmechaniken und Modelle
│   ├── items/ # Power-ups und Spielgegenstände
├── ui/        # Benutzeroberflächen-Komponenten
```

## Spielfunktionen

- Klassisches Space Invaders Gameplay
- Sandbox-Modus mit anpassbaren Einstellungen
- Power-up-System:
    - Schussgeschwindigkeits-Upgrades
    - Bewegungsgeschwindigkeits-Verbesserungen
    - Multi-Shot-Fähigkeit
- Leben-System mit konfigurierbaren Herzen
- Hintergrundmusik
- Punkteverfolgung und Rangliste
- Wellenbasierte Gegner-Progression

## Steuerung

- **A/Pfeil links**: Nach links bewegen
- **D/Pfeil rechts**: Nach rechts bewegen
- **Leertaste**: Schießen
- **ESC**: Zurück zum Menü
- **1**: Spiel neustarten (wenn Game Over)
- **Umschalttaste**: Namen für Highscore eingeben (wenn Game Over)

## Voraussetzungen

- Java 8 oder höher
- Externer MySQL Server für die Highscore-Funktionen (nicht im Repository enthalten)

## Installation und Start

1. Repository klonen
2. MySQL-Server einrichten und Verbindungsdaten konfigurieren
3. Projekt in IntelliJ IDEA öffnen
4. Hauptanwendungsklasse im `app` Paket ausführen

## Datenbank-Konfiguration

Die Anwendung erfordert einen externen MySQL Server, der vor dem Start konfiguriert werden muss. 

## Entwicklung

Das Projekt verwendet reines Java mit AWT und Swing für die Rendering-Komponenten.

## Mitwirkende

[Noah Klaholz](https://noahklaholz.netlify.app)
[Emilia Zomorodi](https://www.linkedin.com/in/emilia-zomorodi-4a4b57329?lipi=urn%3Ali%3Apage%3Ad_flagship3_profile_view_base_contact_details%3BBNaOdMOyQwiuGO0dnkgbAA%3D%3D)
[Golmi09](https://github.com/Golmi09)