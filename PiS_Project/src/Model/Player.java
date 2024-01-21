/**
 * Die {@code Player}-Klasse repräsentiert einen Spieler im Connect Four-Spiel.
 * Jeder Spieler hat eine eindeutige Spieler-ID, einen Namen, eine Farbe, einen Aktivitätsstatus und kann eine KI sein.
 */
package Model;
public class Player {
    /** Zählt die Anzahl der erstellten Spieler. */
    static int playerCount = 0;

    /** Die eindeutige ID des Spielers. */
    private final int playerId;

    /** Der Name des Spielers. */
    private final String playerName;

    /** Die Farbe des Spielers. */
    private char color;

    /** Der Aktivitätsstatus des Spielers. */
    private boolean isActive;

    /** Gibt an, ob der Spieler eine KI ist. */
    private boolean isAI;

    /**
     * Konstruktor für die {@code Player}-Klasse. Initialisiert die Spieler-ID, den Namen, die Farbe und den Aktivitätsstatus.
     *
     * @param color Die Farbe des Spielers ('Y' für Gelb, 'R' für Rot).
     * @param isAI  {@code true}, wenn der Spieler eine KI ist, ansonsten {@code false}.
     */
    public Player(char color, boolean isAI) {
        this.playerId = Player.playerCount++;
        this.playerName = "Player" + this.playerId;
        setColor(color);
        this.isActive = false;
        this.isAI = isAI;
    }

    /**
     * Gibt die Farbe des Spielers zurück.
     *
     * @return Die Farbe des Spielers ('Y' für Gelb, 'R' für Rot).
     */
    public char getColor() {
        return this.color;
    }

    /**
     * Gibt die Farbe des Spielers als Zeichenkette zurück.
     *
     * @return Die Farbe des Spielers als Zeichenkette ("yellow" für Gelb, "red" für Rot, "unknown color" für unbekannte Farbe).
     */
    public String getColorString() {
        return switch (this.color) {
            case 'Y' -> "yellow";
            case 'R' -> "red";
            default -> "unknown color";
        };
    }

    /**
     * Überprüft, ob der Spieler aktiv ist.
     *
     * @return {@code true}, wenn der Spieler aktiv ist, ansonsten {@code false}.
     */
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Setzt den Aktivitätsstatus des Spielers.
     *
     * @param isActive {@code true}, um den Spieler als aktiv zu markieren, ansonsten {@code false}.
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Setzt die Farbe des Spielers.
     *
     * @param color Die Farbe des Spielers ('Y' für Gelb, 'R' für Rot).
     */
    public void setColor(char color) {
        this.color = color;
    }

    /**
     * Gibt eine Zeichenkettendarstellung des Spielers zurück.
     *
     * @return Eine Zeichenkettendarstellung des Spielers.
     */
    @Override
    public String toString() {
        return "Player number: " + this.playerId +
                ", Name: " + this.playerName +
                ", Color: " + getColorString() +
                ", isActive: " + this.isActive;
    }

    /**
     * Setzt die Spieler-ID zurück.
     */
    public static void resetPlayerCount() {
        playerCount = 0;
    }

    /**
     * Überprüft, ob der Spieler eine KI ist.
     *
     * @return {@code true}, wenn der Spieler eine KI ist, ansonsten {@code false}.
     */
    public boolean isAI() {
        return this.isAI;
    }
}
