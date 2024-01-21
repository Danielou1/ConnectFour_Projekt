/**
 * Schnittstelle für den Controller, der die Interaktion zwischen Model und View verwaltet.
 */
package Controler;
public interface IController {

    /**
     * Wirft einen Spielstein in die angegebene Spalte.
     *
     * @param col Die Spalte, in die der Spielstein geworfen werden soll.
     */
    void dropToken(int col);

    /**
     * Gibt die Anzahl der Zeilen im Spielfeld zurück.
     *
     * @return Die Anzahl der Zeilen im Spielfeld.
     */
    int getRowCount();

    /**
     * Gibt die Anzahl der Spalten im Spielfeld zurück.
     *
     * @return Die Anzahl der Spalten im Spielfeld.
     */
    int getColumnCount();

    /**
     * Gibt den Spielstein an der angegebenen Position im Spielfeld zurück.
     *
     * @param row Die Zeile der Position.
     * @param col Die Spalte der Position.
     * @return Der Spielstein an der angegebenen Position.
     */
    char getBoardToken(int row, int col);

    /**
     * Überprüft, ob das Spiel gewonnen wurde.
     *
     * @return {@code true}, wenn das Spiel gewonnen wurde, ansonsten {@code false}.
     */
    boolean isGameWon();

    /**
     * Überprüft, ob das Spielfeld voll ist.
     *
     * @return {@code true}, wenn das Spielfeld voll ist, ansonsten {@code false}.
     */
    boolean isBoardFull();

    /**
     * Überprüft, ob Spieler 1 gewonnen hat.
     *
     * @return {@code true}, wenn Spieler 1 gewonnen hat, ansonsten {@code false}.
     */
    boolean player1Won();

    /**
     * Überprüft, ob Spieler 2 gewonnen hat.
     *
     * @return {@code true}, wenn Spieler 2 gewonnen hat, ansonsten {@code false}.
     */
    boolean player2Won();

    /**
     * Gibt den aktuellen Zustand des Spiels als Zeichenkette zurück.
     *
     * @return Der aktuelle Zustand des Spiels.
     */
    String printGameState();

    /**
     * Setzt das Spiel zurück und initialisiert es neu.
     */
    void reinitializeGame();

    /**
     * Wechselt den aktiven Spieler.
     */
    void switchActivePlayer();

    /**
     * Überprüft den Gewinner und zeigt ihn gegebenenfalls an.
     */
    void checkWinner();

    /**
     * Startet den KI-Thread.
     */
    void startThread();

    /**
     * Informiert den KI-Thread, um die Ausführung fortzusetzen.
     */
    void notifyAIThread();

    /**
     * Stoppt den KI-Thread.
     */
    void stopThread();
}
