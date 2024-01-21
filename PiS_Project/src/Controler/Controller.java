/**
 * Die {@code Controller}-Klasse steuert die Interaktion zwischen dem Modell ({@link ConnectFour}),
 * der Benutzeroberfläche ({@link IView}) und dem Thread für die KI ({@link Threads}).
 */
package Controler;
import Model.*;
import View.IView;
import java.util.concurrent.Semaphore;
public class Controller extends Thread implements IController {
    /** Das Modell für das Vier Gewinnt-Spiel. */
    private ConnectFour model;

    /** Die Benutzeroberfläche für das Vier Gewinnt-Spiel. */
    private IView view;

    /** Der aktuelle Spieler im Spiel. */
    private Player currentPlayer;

    /** Der Thread für die KI. */
    private Threads aiThread;

    /** Der Semaphore für die KI, um die Ausführung zu steuern. */
    private final Semaphore aiSemaphore = new Semaphore(0);

    /**
     * Konstruktor für die {@code Controller}-Klasse. Initialisiert das Modell, den aktuellen Spieler
     * und startet den Thread für die KI.
     */
    public Controller() {
        this.model = new ConnectFour();
        this.currentPlayer = model.getActivePlayer();
        this.aiThread = new Threads(model);
        this.aiThread.start(); // Starte den KI-Thread
    }

    /**
     * Lässt einen Spielstein in die angegebene Spalte fallen.
     *
     * @param col Die Spalte, in die der Spielstein fallen, gelassen werden soll.
     */
    public void dropToken(int col) {
        if (!model.isGameWon() && !model.isBoardFull()) {
            if (model.dropToken(col)) {
                view.updateView();
                if (model.checkWinCondition(currentPlayer.getColor())) {
                    view.displayWinner();
                    aiThread.interrupt(); // Unterbricht den KI-Thread, wenn das Spiel gewonnen ist
                    return;
                }
                switchActivePlayer();
            }
        }
    }
    /**
     * Setzt das Modell für den Controller.
     *
     * @param model Das Modell für das Vier Gewinnt-Spiel.
     */
    public void setModel(ConnectFour model) {
        this.model = model;
        this.aiThread = new Threads(model); // Aktualisiere das Modell für den KI-Thread
    }

    /**
     * Setzt die Benutzeroberfläche für den Controller.
     *
     * @param view Die Benutzeroberfläche für das Vier Gewinnt-Spiel.
     */
    public void setView(IView view) {
        this.view = view;
        this.aiThread = new Threads(model);
        this.aiThread.start(); // Starte den KI-Thread nach Initialisierung der Ansicht
    }
    /**
     * Gibt die Anzahl der Zeilen im Spielfeld zurück.
     *
     * @return Die Anzahl der Zeilen im Spielfeld.
     */
    public int getRowCount() {
        return model.getRowCount();
    }

    /**
     * Startet den Thread für die KI, wenn er nicht bereits aktiv ist.
     */
    public void startThread() {
        if (!aiThread.isAlive()) {
            aiThread = new Threads(model);
            aiThread.start();
        }
    }

    /**
     * Stoppt den Thread für die KI durch Unterbrechung und wartet darauf, dass der Thread ordnungsgemäß beendet wird.
     */
    public void stopThread() {
        aiThread.interrupt();
        try {
            aiThread.join(); // Warte darauf, dass der Thread ordnungsgemäß beendet wird
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Informiert den KI-Thread, um die Ausführung fortzusetzen, indem den Semaphore freigegeben wird.
     */
    public void notifyAIThread() {
        aiSemaphore.release(); // Freigebe den Semaphore, um dem KI-Thread die Fortsetzung zu ermöglichen
    }

    /**
     * Gibt die Anzahl der Spalten im Spielfeld zurück.
     *
     * @return Die Anzahl der Spalten im Spielfeld.
     */
    public int getColumnCount() {
        return model.getColumnCount();
    }

    /**
     * Überprüft, ob Spieler 1 gewonnen hat.
     *
     * @return {@code true}, wenn Spieler 1 gewonnen hat, ansonsten {@code false}.
     */
    public boolean player1Won() {
        return model.player1Won();
    }

    /**
     * Überprüft, ob Spieler 2 gewonnen hat.
     *
     * @return {@code true}, wenn Spieler 2 gewonnen hat, ansonsten {@code false}.
     */
    public boolean player2Won() {
        return model.player2Won();
    }

    /**
     * Gibt den Spielstein an der angegebenen Position auf dem Spielfeld zurück.
     *
     * @param row Die Zeile der Position.
     * @param col Die Spalte der Position.
     * @return Der Spielstein an der angegebenen Position ('Y' für Gelb, 'R' für Rot, '-' für leer).
     */
    public char getBoardToken(int row, int col) {
        return model.getBoardToken(row, col);
    }

    /**
     * Überprüft, ob das Spiel gewonnen wurde.
     *
     * @return {@code true}, wenn das Spiel gewonnen wurde, ansonsten {@code false}.
     */
    public boolean isGameWon() {
        return model.isGameWon();
    }

    /**
     * Überprüft, ob das Spielfeld voll ist.
     *
     * @return {@code true}, wenn das Spielfeld voll ist, ansonsten {@code false}.
     */
    public boolean isBoardFull() {
        return model.isBoardFull();
    }


    /**
     * Setzt das Spiel zurück und initialisiert es neu.
     */
    public void reinitializeGame() {
        model.reinitializeGame();
    }

    /**
     * Wechselt zum nächsten aktiven Spieler.
     */
    public void switchActivePlayer() {
        model.switchActivePlayer(); // Aktualisiere den aktiven Spieler im Modell
        currentPlayer = model.getActivePlayer(); // Aktualisiere den aktiven Spieler im Controller
    }

    /**
     * Gibt den aktuellen Zustand des Spiels als Zeichenkette zurück.
     *
     * @return Der aktuelle Zustand des Spiels als Zeichenkette.
     */
    public String printGameState() {
        return model.toString();
    }

    /**
     * Überprüft, ob es einen Gewinner gibt und informiert die Benutzeroberfläche.
     */
    public void checkWinner() {
        if (model.checkWinCondition(currentPlayer.getColor())) {
            view.displayWinner();
        }
    }
}
