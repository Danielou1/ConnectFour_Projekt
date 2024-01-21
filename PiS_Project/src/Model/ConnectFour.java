/**
 * Die ConnectFour-Klasse repräsentiert das Connect Four-Spiel mit einem Spielfeld, Spielern und Spiellogik.
 */
package Model;
import java.util.concurrent.Semaphore;
public class ConnectFour {

    private static final int DEFAULT_ROW_COUNT = 6;
    private static final int DEFAULT_COLUMN_COUNT = 7;

    final char[][] board; // Das Spielfeld
    private final int rowCount; // Anzahl der Zeilen im Spielfeld
    private final int columnCount; // Anzahl der Spalten im Spielfeld
    public Semaphore semaphore; // Semaphore für die Synchronisation mit Threads
    private boolean gameWon; // Gibt an, ob das Spiel gewonnen wurde
    final Player[] players; // Die Spieler im Spiel

    /**
     * Konstruiert ein ConnectFour-Objekt mit den Standardabmessungen.
     */
    public ConnectFour() {
        this(DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);
    }

    /**
     * Konstruiert ein ConnectFour-Objekt mit den angegebenen Abmessungen.
     *
     * @param rows    Anzahl der Zeilen im Spielfeld
     * @param columns Anzahl der Spalten im Spielfeld
     */
    public ConnectFour(int rows, int columns) {
        this.rowCount = rows;
        this.columnCount = columns;
        this.board = new char[this.rowCount][this.columnCount];
        this.gameWon = false;
        this.players = new Player[2];
        initializeBoard();
        initializePlayers();
        this.semaphore = new Semaphore(0);
    }

    /**
     * Kopiert den Zustand des Spielfelds und der Spieler von einem anderen ConnectFour-Objekt.
     *
     * @param originalGame Das ursprüngliche ConnectFour-Objekt, dessen Zustand kopiert werden soll
     */
    public void copyBoard(ConnectFour originalGame) {
        for (int row = 0; row < this.rowCount; row++) {
            System.arraycopy(originalGame.board[row], 0, this.board[row], 0, this.columnCount);
        }
        this.gameWon = originalGame.gameWon;
        for (int i = 0; i < this.players.length; i++) {
            this.players[i].setActive(originalGame.players[i].isActive());
        }
    }


    private void initializeBoard() {
        for (int row = 0; row < this.rowCount; row++) {
            for (int col = 0; col < this.columnCount; col++) {
                this.board[row][col] = '-'; // pour vide
            }
        }
    }

    private void initializePlayers() {
        char[] colors = {'Y', 'R'};
        boolean[] isAI = {false, true}; // Indiquez ici si chaque joueur est une IA (false pour le joueur 1, true pour le joueur 2)

        for (int i = 0; i < this.players.length; i++) {
            this.players[i] = new Player(colors[i % colors.length], isAI[i]);
        }

        this.players[0].setActive(true); // Définir le premier joueur comme actif
    }
    /**
     * Gibt die Anzahl der Reihen im Spielfeld zurück.
     *
     * @return Die Anzahl der Reihen im Spielfeld
     */
    public int getRowCount() {
        return this.rowCount;
    }
    /**
     * Gibt die Anzahl der Spalten im Spielfeld zurück.
     *
     * @return Die Anzahl der Spalten im Spielfeld
     */
    public int getColumnCount() {
        return this.columnCount;
    }
    /**
     * Gibt das Token an der angegebenen Position auf dem Spielfeld zurück.
     *
     * @param row Die Zeilenindexe
     * @param col Der Spaltenindex
     * @return Das Token an der angegebenen Position
     */
    public char getBoardToken(int row, int col) {
        try {
            if ((row >= 0 && row < this.rowCount) && (col >= 0 && col < this.columnCount)) {
                return this.board[row][col];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return '-';
    }

    /**
     * Gibt an, ob das Spiel gewonnen wurde.
     *
     * @return {@code true}, wenn das Spiel gewonnen wurde, andernfalls {@code false}
     */
    public boolean isGameWon() {
        return this.gameWon;
    }
    /**
     * Gibt an, ob das Spielfeld voll ist.
     *
     * @return {@code true}, wenn das Spielfeld voll ist, andernfalls {@code false}
     */
    public boolean isBoardFull() {
        for (int col = 0; col < this.columnCount; col++) {
            if (this.board[0][col] == '-') {
                return false;  // Si une colonne a une case vide, le tableau n'est pas plein
            }
        }
        return true;  // Si aucune colonne n'a de case vide, le tableau est plein
    }
    /**
     * Gibt den Spieler am angegebenen Index zurück.
     *
     * @param playerIndex Der Index des Spielers
     * @return Der Spieler am angegebenen Index oder {@code null}, wenn der Index außerhalb der Grenzen liegt
     */
    public Player getPlayer(int playerIndex) {
        if (playerIndex >= 0 && playerIndex < this.players.length) {
            return this.players[playerIndex];
        }
        return null; // Retourner null si l'index est hors limites
    }
    /**
     * Gibt den gerade aktiven Spieler zurück.
     *
     * @return Der gerade aktive Spieler oder {@code null}, wenn kein Spieler aktiv ist
     */
    public Player getActivePlayer() {
        for (Player player : this.players) {
            if (player.isActive()) {
                return player;
            }
        }
        return null;
    }
    /**
     * Initialisiert das Spiel auf seinen Anfangszustand zurück.
     */
    public void reinitializeGame() {
        this.gameWon = false;
        initializeBoard();
        initializePlayers();
        Player.resetPlayerCount();

    }
    /**
     * Setzt einen Token in der angegebenen Spalte.
     *
     * @param col Der Spaltenindex, in dem der Token abgelegt werden soll
     * @return {@code true}, wenn der Token erfolgreich abgelegt wurde, andernfalls {@code false}
     */
    public boolean dropToken(int col) {
        if (col < 0 || col >= this.columnCount || this.gameWon) {
            return false; // Retourner false si hors limites ou jeu déjà gagné
        }

        for (int row = this.rowCount - 1; row >= 0; row--) {
            if (this.board[row][col] == '-') {
                this.board[row][col] = getActivePlayer().getColor();
                checkWinCondition(row, col);
                switchActivePlayer();
                return true;
            }
        }
        return false;
    }

    /**
     * Überprüft, ob das Setzen eines Tokens an der angegebenen Position zu einem Gewinnzustand führt.
     *
     * @param row Die Zeilenposition, an der das Token platziert wurde
     * @param col Die Spaltenposition, an der das Token platziert wurde
     */
    private void checkWinCondition(int row, int col) {
        char currentPlayerToken = board[row][col];

        // Horizontal überprüfen
        if (checkAlignment(row, col, 0, 1, currentPlayerToken) ||
                checkAlignment(row, col, 0, -1, currentPlayerToken)) {
            gameWon = true;
            return;
        }

        // Vertikal überprüfen
        if (checkAlignment(row, col, 1, 0, currentPlayerToken)) {
            gameWon = true;
            return;
        }

        // Diagonal überprüfen (oben links nach unten rechts)
        if (checkAlignment(row, col, 1, 1, currentPlayerToken) ||
                checkAlignment(row, col, -1, -1, currentPlayerToken)) {
            gameWon = true;
            return;
        }

        // Diagonal überprüfen (oben rechts nach unten links)
        if (checkAlignment(row, col, 1, -1, currentPlayerToken) ||
                checkAlignment(row, col, -1, 1, currentPlayerToken)) {
            gameWon = true;
        }
    }

    /**
     * Überprüft, ob eine Ausrichtung von mindestens vier aufeinander folgenden Tokens in einer Richtung vorliegt.
     *
     * @param rowDir              Der Zeilenrichtungsindex
     * @param colDir              Der Spaltenrichtungsindex
     * @param currentPlayerToken Das Token des aktiven Spielers
     * @return {@code true}, wenn mindestens vier Tokens in der angegebenen Ausrichtung vorhanden sind, andernfalls {@code false}
     */
    private boolean checkAlignment(int row, int col, int rowDir, int colDir, char currentPlayerToken) {
        int tokensInLine = 0;

        while (row >= 0 && row < rowCount &&
                col >= 0 && col < columnCount &&
                board[row][col] == currentPlayerToken) {
            tokensInLine++;
            row += rowDir;
            col += colDir;
        }

        return tokensInLine >= 4; // Gibt true zurück, wenn mindestens vier Tokens ausgerichtet sind
    }

    /**
     * Wechselt zum aktiven Spieler.
     */
    public void switchActivePlayer() {
        for (Player player : this.players) {
            player.setActive(!player.isActive());
        }
        if (getActivePlayer().getColor() == 'R') {
            semaphore.release();
        }
    }
    /**
     * Überprüft, ob die angegebene Farbe das Spiel gewonnen hat.
     *
     * @param color Die Farbe, die auf einen Gewinn überprüft werden soll
     * @return {@code true}, wenn die angegebene Farbe gewonnen hat, andernfalls {@code false}
     */
    public boolean checkWinCondition(char color) {
        // Check horizontal and vertical alignments
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                if (board[row][col] == color) {
                    // Check horizontal
                    if (col <= columnCount - 4 &&
                            board[row][col + 1] == color &&
                            board[row][col + 2] == color &&
                            board[row][col + 3] == color) {
                        return true; // Horizontal win
                    }

                    // Check vertical
                    if (row <= rowCount - 4 &&
                            board[row + 1][col] == color &&
                            board[row + 2][col] == color &&
                            board[row + 3][col] == color) {
                        return true; // Vertical win
                    }

                    // Check diagonal (up-right)
                    if (row >= 3 && col <= columnCount - 4 &&
                            board[row - 1][col + 1] == color &&
                            board[row - 2][col + 2] == color &&
                            board[row - 3][col + 3] == color) {
                        return true; // Diagonal up-right win
                    }

                    // Check diagonal (down-right)
                    if (row <= rowCount - 4 && col <= columnCount - 4 &&
                            board[row + 1][col + 1] == color &&
                            board[row + 2][col + 2] == color &&
                            board[row + 3][col + 3] == color) {
                        return true; // Diagonal down-right win
                    }
                }
            }
        }

        return false; // No winner found
    }
    /**
     * Überprüft, ob Spieler 1 das Spiel gewonnen hat.
     *
     * @return {@code true}, wenn Spieler 1 gewonnen hat, andernfalls {@code false}
     */
    public boolean player1Won() {
        return checkWinCondition('Y');
    }

    /**
     * Überprüft, ob Spieler 2 das Spiel gewonnen hat.
     *
     * @return {@code true}, wenn Spieler 2 gewonnen hat, andernfalls {@code false}
     */
    public boolean player2Won() {
        return checkWinCondition('R');
    }


    /**
     * Liefert eine Zeichenkette, die den aktuellen Zustand des Spiels repräsentiert.
     *
     * @return Eine Zeichenkette, die den Spielzustand repräsentiert
     */
    @Override
    public String toString() {
        StringBuilder boardState = new StringBuilder();
        boardState.append(isGameWon() ? "Spiel beendet" + "\n" : "Zug für " + getActivePlayer() + " \n");
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                boardState.append(board[row][col]).append(" ");
            }
            boardState.append("\n");
        }
        return boardState.toString();
    }

}
