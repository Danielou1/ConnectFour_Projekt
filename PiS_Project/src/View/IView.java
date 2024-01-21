/**
 * Das IView-Interface definiert die Methoden, die von der View implementiert werden müssen.
 */
package View;

public interface IView {

    /**
     * Zeichnet die Pfeile über dem Spielfeld.
     */
    void drawArrows();

    /**
     * Aktualisiert die Ansicht.
     */
    void updateView();

    /**
     * Zeigt an, dass Pikachu gewonnen hat.
     */
    void pikachuWon();

    /**
     * Zeigt an, dass Evoli gewonnen hat.
     */
    void evolisWon();

    /**
     * Zeigt den Gewinner oder das Unentschieden an.
     */
    void displayWinner();
}
