/**
 * Die Main. Main-Klasse initialisiert das Model, die View und den Controller für das Spiel.
 */
package Main;
import Controler.*;
import Model.*;
import View.*;
import processing.core.PApplet;


public class Main {

    /**
     * Die main-Methode erstellt eine Instanz des Spiels und startet die Anwendung.
     *
     * @param args Die Kommandozeilenargumente (werden ignoriert).
     */
    public static void main(String[] args) {
        // Erstellen der Model-, View- und Controller-Instanzen
        var model = new ConnectFour();
        var view = new View();
        var controller = new Controller();

        // Konfigurieren der Verbindungen zwischen Model, View und Controller
        controller.setModel(model);
        controller.setView(view);
        view.setController(controller);

        // Starten der Anwendung
        PApplet.runSketch(new String[]{"View"}, view);
    }
}
