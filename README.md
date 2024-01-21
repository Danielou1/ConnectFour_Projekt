# ConnectFour Spiel

Kurze Beschreibung des ConnectFour-Spiels, einschließlich seiner Funktionen und Ziele.

**Entwickelt nach dem Model-View-Controller (MVC)-Entwurfsmuster**

## Inhaltsverzeichnis

1. [Beschreibung](#beschreibung)
2. [Installation](#installation)
3. [Spielregeln](#spielregeln)
4. [Pokémon als Jetons](#pokémon-als-jetons)
5. [Beispielbilder](#beispielbilder)
6. [Die KI Funktion](#mit-ki-funktion)
7. [Spiel mit der Shell](#Spiel mit der JShell)
8. [Lizenz](#autor)

## Beschreibung

ConnectFour ist ein simples Vier-Gewinnt-Spiel, das in Java und Processing erstellt wurde. Es ermöglicht zwei Spieler, abwechselnd Spielsteine in ein Raster zu setzen, mit dem Ziel, als Erster vier Steine horizontal, vertikal oder diagonal zu verbinden.


## Beispielbilder
Hier stehen einpaar illustrierte Beispiele des ConnectFours Spieles
1. Beim Starten der `main()` 


<img src="C:\Users\mouns\PiS_ProjectGenieLou\PiS_Project\images\start.png" alt="Evoli" width="250" height="200">

2. Während des Spiels

<img src="C:\Users\mouns\PiS_ProjectGenieLou\PiS_Project\images\spiel.png" alt="Evoli" width="200" height="200">

3. Beim Game Over

<img src="C:\Users\mouns\PiS_ProjectGenieLou\PiS_Project\images\GameOver.png" alt="Evoli" width="200" height="200">



## Installation

Folgende Schritte sind erforderlich, um das Spiel lokal auszuführen:

```bash
# Beispiel für Code für die Installation
git clone https://github.com/danielou1/ConnectFour.git
cd ConnectFour
java -cp Pfad/zum/Processing-Core.jar:. Main.Main
```
# Startanleitung
Zum **Starten** müssen folgende Schritten befolgt werden:
`gabe`
1. öffnen der Datei  `main()`- Methode
2. Starten der Funktion `main()` - Methode
3. Nachdem der StartScreed auf dem

*STARTEN:* Zum Starten geben Sie Ihren Namen ein und speichern Sie ihre Eingabe

## Spielregeln
Jeder Spieler setzt abwechselnd einen Spielstein in eine Spalte des Rasters.
Das Spiel endet, wenn ein Spieler vier seiner Spielsteine in einer Reihe hat (horizontal, vertikal oder diagonal) oder das Spielfeld voll ist.
Nach Spielende kann durch Drücken der 'ENTER'-Taste ein neues Spiel gestartet werden

# pokémon-als-jetons
In diesem Spiel wurden Pokémon als Jetons verwendet, um den Spielspaß zu erhöhen. Jeder Spieler platziert Pokémon-Spielsteine im Raster.

<img src="C:\Users\mouns\PiS_ProjectGenieLou\PiS_Project\images\EvoliObese.jpeg" alt="Evoli" width="100" height="100">
<img src="C:\Users\mouns\PiS_ProjectGenieLou\PiS_Project\images\PikachuObese.jpeg" alt="Pikachu" width="100" height="100">


## Mit KI-Funktion
Ein Spieler im Spiel wird von einer Künstlichen Intelligenz (KI) gesteuert, um ein herausforderndes Spielerlebnis zu bieten.

## Spiel mit der Shell
*1. Starten eine Konsole und verlinken Sie das Verzeichnis des Projekts*
` jshell --class-path C:\Users\mouns\PiS_ProjectGenieLou\out\production\PiS_ProjectGenieLou`


*2.Erstellen eines Objekts*
var obj = new ConnectFour();

*3.Aufrufen einer Funktion*
obj.dropToken(3);

*4. Prüfen, ob sich die Daten im Modell geändert haben*
obj.toString();

![illustration](C:\Users\mouns\PiS_ProjectGenieLou\PiS_Project\images\jshellTest.png)
## Verwendete Bibliothek
<a href="https://processing.org/"> Processing </a><br>
<a href="http://www.sojamo.de/libraries/controlP5"> ControlP5 </a><br>


## Autor

- Danielou Mounsande Sandamoun
