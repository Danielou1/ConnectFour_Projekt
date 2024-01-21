/**
 * Die Threads-Klasse repräsentiert einen Thread zur Behandlung von KI-Zügen im ConnectFour-Spiel.
 * Diese Klasse erweitert die Thread-Klasse und bietet Methoden zum Finden des besten Zugs für den KI-Spieler.
 */
package Model;
import java.util.Random;
import java.util.concurrent.Semaphore;
public class Threads extends Thread {

    private final ConnectFour model;  // Das ConnectFour-Spielmodell
    public Semaphore semaphore;  // Semaphore für die Synchronisation

    /**
     * Konstruiert ein Threads-Objekt mit dem angegebenen ConnectFour-Modell.
     *
     * @param model Das ConnectFour-Spielmodell
     */
    public Threads(ConnectFour model) {
        this.model = model;
    }

    /**
     * Die Run-Methode des Threads. Sie überprüft kontinuierlich den Spielstatus und KI-Züge,
     * bis das Spiel gewonnen ist oder das Spielfeld voll ist.
     */
    @Override
    public void run() {
        try {
            while (!model.isGameWon() && !model.isBoardFull()) {
                char RED = 'R';
                if (model.getActivePlayer().getColor() != RED) {
                    System.out.println("Thread: Warte auf Semaphore");
                    Thread.sleep(2000);
                    model.semaphore.acquire();
                } else {
                    if (model.getActivePlayer().isAI()) {
                        // Überprüfung auf Unterbrechung und Verlassen der Schleife bei Unterbrechung
                        if (Thread.interrupted()) {
                            break;
                        }

                        int aiMove = findBestMoveForAI();
                        model.dropToken(aiMove);
                        model.semaphore.release();
                    }
                }
                System.out.println("Innerhalb der Schleifen-Run-Methode");
            }
        } catch (InterruptedException e) {
            // Unterbrechung bei Bedarf protokollieren oder behandeln
            e.printStackTrace();
        }
        System.out.println("Thread beendet");
    }

    /**
     * Findet den besten Zug für den KI-Spieler, indem Spiel-Szenarien simuliert werden.
     *
     * @return Der Spaltenindex, der den besten Zug für den KI-Spieler repräsentiert
     */
    int findBestMoveForAI() {
        for (int col = 0; col < model.getColumnCount(); col++) {
            ConnectFour simulatedGame = createSimulatedGame(col);
            if (simulatedGame.player2Won()) {
                return col;
            }

            simulatedGame.switchActivePlayer();
            if (simulatedGame.player1Won()) {
                return col;
            }
            simulatedGame.switchActivePlayer(); // Revenir à l'IA
        }
        return getRandomMove();
    }

    /**
     * Erstellt ein simuliertes Spiel mit dem angegebenen Zug und kopiert den aktuellen Zustand des Spielfelds.
     *
     * @param col Der Spaltenindex, der den zu simulierenden Zug repräsentiert
     * @return Das simulierte ConnectFour-Spiel
     */
    ConnectFour createSimulatedGame(int col) {
        ConnectFour simulatedGame = new ConnectFour(model.getRowCount(), model.getColumnCount());
        simulatedGame.copyBoard(model);
        simulatedGame.dropToken(col);
        return simulatedGame;
    }

    /**
     * Generiert einen zufälligen Zug für den KI-Spieler.
     *
     * @return Der Spaltenindex, der einen zufälligen Zug für den KI-Spieler repräsentiert
     */
    int getRandomMove() {
        Random random = new Random();
        return random.nextInt(model.getColumnCount());
    }

}
