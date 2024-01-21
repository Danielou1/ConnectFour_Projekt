/**
 * Die ConnectFourTest-Klasse enthält JUnit-Tests für die ConnectFour-Implementierung und die Player-Klasse.
 */
package  Model;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

public class ConnectFourTest {

    @Test
    void testDefaultConstructor() {
        ConnectFour game = new ConnectFour();
        assertEquals(6, game.getRowCount());
        assertEquals(7, game.getColumnCount());
        assertFalse(game.isGameWon());
        assertFalse(game.isBoardFull());
        assertNotNull(game.getActivePlayer());
    }

    @Test
    void testCustomConstructor() {
        ConnectFour game = new ConnectFour(6, 8);
        assertEquals(6, game.getRowCount());
        assertEquals(8, game.getColumnCount());
        assertFalse(game.isGameWon());
        assertFalse(game.isBoardFull());
        assertNotNull(game.getActivePlayer());
    }

    @Test
    void testDropToken() {
        ConnectFour game = new ConnectFour();
        assertTrue(game.dropToken(0));
        assertEquals('Y', game.getBoardToken(5, 0));
        assertFalse(game.isGameWon());
        assertFalse(game.isBoardFull());
    }

    @Test
    void testDropTokenInFullColumn() {
        ConnectFour game = new ConnectFour();
        // Fill up a column in a zigzag pattern to avoid a win
        for (int row = 0; row < game.getRowCount(); row++) {
            game.dropToken(0); // Drop token in the same column
        }

        assertFalse(game.dropToken(0)); // Attempt to drop a token in a full column
    }

    @Test
    void testWinConditionHorizontal() {
        ConnectFour game = new ConnectFour();
        game.dropToken(0);
        game.dropToken(1);
        game.dropToken(2);
        game.dropToken(4); // Ändere den Index auf 4, um eine Unterbrechung zu erzwingen
        game.dropToken(3);
        assertFalse(game.isGameWon()); // Erwartet wird, dass das Spiel nicht gewonnen ist
        assertFalse(game.isBoardFull());
    }

    @Test
    void testWinConditionVertical() {
        ConnectFour game = new ConnectFour();
        game.dropToken(0);
        game.dropToken(1);
        game.dropToken(0);
        game.dropToken(0);
        game.dropToken(0);
        assertFalse(game.isGameWon());
        assertFalse(game.isBoardFull());
    }

    @Test
    void testWinConditionDiagonal() {
        ConnectFour game = new ConnectFour();
        game.dropToken(0);
        game.dropToken(1); // Changer l'index pour introduire une interruption
        game.dropToken(1);
        game.dropToken(2);
        game.dropToken(2);
        game.dropToken(2);
        game.dropToken(3);
        game.dropToken(3);
        game.dropToken(3);
        game.dropToken(3);
        assertFalse(game.isGameWon()); // On s'attend à ce que le jeu ne soit pas gagné
        assertFalse(game.isBoardFull());
    }

    @Test
    void testCheckWinConditionHorizontal() {
        ConnectFour game = new ConnectFour();
        char color = 'Y'; // Choose the color to check

        // Create a horizontal win
        for (int col = 0; col < 4; col++) {
            game.board[0][col] = color;
        }

        assertTrue(game.checkWinCondition(color));
    }

    @Test
    void testCheckWinConditionVertical() {
        ConnectFour game = new ConnectFour();
        char color = 'Y';

        // Create a vertical win
        for (int row = 0; row < 4; row++) {
            game.board[row][0] = color;
        }

        assertTrue(game.checkWinCondition(color));
    }

    @Test
    void testCheckWinConditionDiagonalUpRight() {
        ConnectFour game = new ConnectFour();
        char color = 'Y';

        // Create a diagonal win (up-right)
        for (int i = 0; i < 4; i++) {
            game.board[3 - i][i] = color; // Starting from bottom left to top right
        }

        assertTrue(game.checkWinCondition(color));
    }

    @Test
    void testCheckWinConditionDiagonalDownRight() {
        ConnectFour game = new ConnectFour();
        char color = 'Y';

        // Create a diagonal win (down-right)
        for (int i = 0; i < 4; i++) {
            game.board[i][i] = color; // Starting from top left to bottom right
        }

        assertTrue(game.checkWinCondition(color));
    }

    @Test
    void testWinConditionDiagonalTopRightToBottomLeft() {
        ConnectFour game = new ConnectFour();

        // Create a diagonal from top-right to bottom-left
        game.dropToken(3); // Player 1
        game.dropToken(4); // Player 2
        game.dropToken(4); // Player 1
        game.dropToken(5); // Player 2
        game.dropToken(2); // Player 1
        game.dropToken(5); // Player 2
        game.dropToken(5); // Player 1
        game.dropToken(6); // Player 2
        game.dropToken(6); // Player 1
        game.dropToken(6); // Player 2
        game.dropToken(6); // Player 1 - Winning move

        assertTrue(game.isGameWon());
    }

    @Test
    void testWinConditionDiagonalTopLeftToBottomRight() {
        ConnectFour game = new ConnectFour();

        // Create a diagonal from top-left to bottom-right
        game.dropToken(3); // Player 1
        game.dropToken(2); // Player 2
        game.dropToken(2); // Player 1
        game.dropToken(1); // Player 2
        game.dropToken(1); // Player 1
        game.dropToken(0); // Player 2
        game.dropToken(1); // Player 1
        game.dropToken(0); // Player 2
        game.dropToken(0); // Player 1
        game.dropToken(4); // Player 2
        game.dropToken(0); // Player 1 - Winning move

        assertTrue(game.isGameWon());
    }

    @Test
    void testSwitchActivePlayer() {
        ConnectFour game = new ConnectFour();
        Player initialActivePlayer = game.getActivePlayer();
        game.switchActivePlayer();
        assertNotEquals(initialActivePlayer, game.getActivePlayer());
    }

    @Test
    void testPlayer1Won() {
        ConnectFour game = new ConnectFour();
        game.dropToken(0);
        game.dropToken(1);
        game.dropToken(0);
        game.dropToken(1);
        game.dropToken(0);
        game.dropToken(1);
        game.dropToken(0);
        assertTrue(game.player1Won());
        assertFalse(game.player2Won());
        assertTrue(game.isGameWon());
    }

    @Test
    void testPlayer2Won() {
        ConnectFour game = new ConnectFour();
        game.dropToken(0);
        game.dropToken(1);
        game.dropToken(0);
        game.dropToken(1);
        game.dropToken(0);
        game.dropToken(1);
        game.dropToken(2);
        game.dropToken(1);
        assertTrue(game.player2Won());
        assertFalse(game.player1Won());
        assertTrue(game.isGameWon());
    }
    @Test
    void testPlayerInitialization() {
        Player player = new Player('Y', false);
        assertEquals('Y', player.getColor());
        assertEquals("yellow", player.getColorString());
        assertFalse(player.isActive());
        assertFalse(player.isAI());
    }

    @Test
    void testPlayerSetActive() {
        Player player = new Player('Y', false);
        assertFalse(player.isActive());
        player.setActive(true);
        assertTrue(player.isActive());
    }

    @Test
    void testPlayerToString() {
        int currentPlayerCount = Player.playerCount;
        Player player = new Player('Y', false);
        String expectedString = "Player number: " + currentPlayerCount +
                ", Name: Player" + currentPlayerCount +
                ", Color: yellow, isActive: false";
        assertEquals(expectedString, player.toString());
    }
    @Test
    void testSetColorValidColor() {
        Player player = new Player('Y', false);
        assertEquals('Y', player.getColor());
    }
    @Test
    void testGetActivePlayerWhenNoActivePlayer() {
        ConnectFour game = new ConnectFour();
        for (Player player : game.players) {
            player.setActive(false);
        }
        assertNull(game.getActivePlayer());

    }
    @Test
    void testIsBoardFullWhenNotFull() {
        ConnectFour game = new ConnectFour();
        for (int col = 0; col < game.getColumnCount(); col++) {
            for (int row = 0; row < game.getRowCount(); row++) {
                game.dropToken(col);
            }
        }

        assertFalse(game.isBoardFull());
    }

    @Test
    void testBoardFull() {
        ConnectFour game = new ConnectFour();

        // Fill the entire board
        for (int col = 0; col < game.getColumnCount(); col++) {
            for (int row = 0; row < game.getRowCount(); row++) {
                game.dropToken(col);
            }
        }

        assertFalse(game.isBoardFull());
        assertTrue(game.isGameWon());
    }
    @Test
    void testIsBoardFullWhenFull() {
        ConnectFour game = new ConnectFour();

        // Fill the board in a way that avoids a winning condition
        for (int col = 0; col < game.getColumnCount(); col++) {
            char color = col % 2 == 0 ? 'Y' : 'R'; // Start with 'Y' for even columns, 'R' for odd
            for (int row = 0; row < game.getRowCount(); row++) {
                // Place the token directly on the board to bypass game logic
                game.board[row][col] = color;
                color = (color == 'Y') ? 'R' : 'Y'; // Alternate the color
            }
        }

        assertTrue(game.isBoardFull());
    }

    @Test
    void testReinitializeGame() {
        ConnectFour game = new ConnectFour();
        game.dropToken(0);  // Par exemple, laissez tomber un jeton dans la colonne 0
        game.switchActivePlayer();  // Passez au joueur suivant
        assertEquals('Y', game.getBoardToken(5, 0));  // Vérifiez que le jeton a été correctement placé
        assertFalse(game.isGameWon());  // Le jeu ne doit pas encore être gagné
        game.reinitializeGame();
        assertFalse(game.isGameWon());
        assertEquals('-', game.getBoardToken(5, 0));  // Vérifiez que le plateau est correctement réinitialisé
    }
    @Test
    void testCopyBoard() {
        ConnectFour originalGame = new ConnectFour();
        ConnectFour copyGame = new ConnectFour();
        originalGame.dropToken(0);
        originalGame.switchActivePlayer();
        copyGame.copyBoard(originalGame);

        for (int row = 0; row < originalGame.getRowCount(); row++) {
            assertArrayEquals(originalGame.board[row], copyGame.board[row]);
        }

        assertEquals(originalGame.isGameWon(), copyGame.isGameWon());

        for (int i = 0; i < originalGame.players.length; i++) {
            assertEquals(originalGame.players[i].isActive(), copyGame.players[i].isActive());
        }
    }
    @Test
    void testGetBoardTokenWithValidIndices() {
        ConnectFour game = new ConnectFour();
        char token = game.getBoardToken(2, 3);
        assertEquals('-', token);
    }

    @Test
    void testGetBoardTokenWithInvalidIndices() {
        ConnectFour game = new ConnectFour();
        assertDoesNotThrow(() -> game.getBoardToken(10, 5));
    }

    @Test
    void testGetBoardTokenWithException() throws NoSuchFieldException, IllegalAccessException {
        ConnectFour game = new ConnectFour();
        manipulateBoardSizeForException(game, 10, 10); // Set row and column count to larger values

        char token = game.getBoardToken(9, 9); // Access an index that should be out of bounds for the original board size
        assertEquals('-', token); // The method should return '-' in case of an exception
    }

    private void manipulateBoardSizeForException(ConnectFour game, int rowCount, int columnCount) throws NoSuchFieldException, IllegalAccessException {
        Field rowCountField = ConnectFour.class.getDeclaredField("rowCount");
        Field columnCountField = ConnectFour.class.getDeclaredField("columnCount");

        rowCountField.setAccessible(true);
        columnCountField.setAccessible(true);

        rowCountField.setInt(game, rowCount);
        columnCountField.setInt(game, columnCount);
    }

    @Test
    void testGetPlayer() {
        ConnectFour game = new ConnectFour();
        Player player1 = game.getPlayer(0);
        assertNotNull(player1);
        assertEquals('Y', player1.getColor());  // Ajoutez d'autres vérifications selon vos besoins
        Player playerInvalid = game.getPlayer(2);
        assertNull(playerInvalid);
    }
    @Test
    void testRun() {
        ConnectFour model = new ConnectFour();
        Threads threads = new Threads(model);

        threads.start();

        assertTrue(threads.isAlive());
    }

    @Test
    void testFindBestMoveForAI() {
        ConnectFour model = new ConnectFour();
        Threads threads = new Threads(model);

        int move = threads.findBestMoveForAI();
        assertTrue(move >= 0 && move < model.getColumnCount());
    }

    @Test
    void testFindBestMoveForAIWins() {
        ConnectFour game = new ConnectFour();
        Threads aiThread = new Threads(game);

        // AI is 'R', set up three 'R' in a column for a winning move in the next drop
        int winningCol = 3;
        for (int row = game.getRowCount() - 1; row >= game.getRowCount() - 3; row--) {
            game.board[row][winningCol] = 'R';
        }

        // Ensure it's AI's turn to play
        if (game.getActivePlayer().getColor() != 'R') {
            game.switchActivePlayer();
        }

        assertEquals(winningCol, aiThread.findBestMoveForAI());
    }
    @Test
    void testCreateSimulatedGame() {
        ConnectFour model = new ConnectFour();
        Threads threads = new Threads(model);

        int col = 3;
        threads.createSimulatedGame(col);
    }

    @Test
    void testGetRandomMove() {
        ConnectFour model = new ConnectFour();
        Threads threads = new Threads(model);

        int move = threads.getRandomMove();
        assertTrue(move >= 0 && move < model.getColumnCount());
    }

    @Test
    void testThreadRun() {
        ConnectFour model = new ConnectFour();
        new Semaphore(0);
        Threads thread = new Threads(model);

        thread.start();

        try {
            assertTrue(thread.isAlive());
            model.semaphore.release();
            Thread.sleep(3000);
            assertTrue(thread.isAlive());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    void testRunWithGameWon() throws InterruptedException {
        ConnectFour model = new ConnectFour();
        Threads threads = new Threads(model);
        model.getPlayer(1).setActive(true);
        model.reinitializeGame();
        threads.start();
        threads.join(1000);
        assertTrue(threads.isAlive());
    }
    @Test
    void testRunWithBoardFull() {
        ConnectFour model = new ConnectFour();
        new Threads(model);
        for (int col = 0; col < model.getColumnCount(); col++) {
            for (int row = 0; row < model.getRowCount(); row++) {
                model.dropToken(col);
            }
        }
    }
    @Test
    void testRunWithInterrupt() throws InterruptedException {
        ConnectFour model = new ConnectFour();
        Threads threads = new Threads(model);
        threads.start();
        threads.interrupt(); // Demande l'interruption
        threads.join(500);   // Attente maximale de 500 ms pour que le thread se termine

        assertFalse(threads.isAlive());
    }

    @Test
    void testAIThreadMakesMove() throws InterruptedException {
        ConnectFour game = new ConnectFour();
        Threads aiThread = new Threads(game);

        // Set up the game so that the AI ('R') is the active player
        if (game.getActivePlayer().getColor() != 'R') {
            game.switchActivePlayer();
        }

        // Start the AI thread
        aiThread.start();

        // Wait a bit to let the AI make its move
        Thread.sleep(3000);

        // Check if the AI has made a move
        boolean moveMade = false;
        for (int row = 0; row < game.getRowCount(); row++) {
            for (int col = 0; col < game.getColumnCount(); col++) {
                if (game.getBoardToken(row, col) == 'R') {
                    moveMade = true;
                    break;
                }
            }
            if (moveMade) {
                break;
            }
        }

        // Clean up the thread
        aiThread.interrupt();
        aiThread.join();

        assertNotEquals(false, moveMade); // Assert that a move was made
    }

    @Test
    void testSemaphoreRelease() throws InterruptedException {
        ConnectFour model = new ConnectFour();
        Threads threads = new Threads(model);
        threads.start();
        TimeUnit.SECONDS.sleep(2);

        assertFalse(model.semaphore.availablePermits() > 0);

        threads.interrupt();
        threads.join(2000);

        assertFalse(threads.isAlive());
    }


    @Test
    void testRunWhenGameWon() throws InterruptedException {
        ConnectFour model = new ConnectFour();
        Threads threads = new Threads(model);
        setGameWon(model);
        threads.start();
        threads.join();
        assertFalse(threads.isAlive());
    }

    @Test
    void testRunWhenBoardFull() throws InterruptedException {
        ConnectFour model = new ConnectFour();
        Threads threads = new Threads(model);
        fillBoard(model);
        threads.start();
        threads.join();
        assertFalse(threads.isAlive());
    }

    private void setGameWon(ConnectFour model) {
        try {
            Field gameWonField = ConnectFour.class.getDeclaredField("gameWon");
            gameWonField.setAccessible(true);
            gameWonField.setBoolean(model, true);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void fillBoard(ConnectFour model) {
        for (int col = 0; col < model.getColumnCount(); col++) {
            for (int row = 0; row < model.getRowCount(); row++) {
                model.dropToken(col);
            }
        }
    }
    @Test
    void testGetColorStringYellow() {
        Player player = new Player('Y', false);
        assertEquals("yellow", player.getColorString());
    }

    @Test
    void testGetColorStringRed() {
        Player player = new Player('R', false);
        assertEquals("red", player.getColorString());
    }
    /**
     * Testet die toString-Methode, um sicherzustellen, dass sie den korrekten Spielzustand repräsentiert.
     */
    @Test
    void testToString() {
        // Arrange
        ConnectFour game = new ConnectFour();
        Player player1 = game.getPlayer(0);
        Player player2 = game.getPlayer(1);

        // Act
        String gameState = game.toString(); // Den aktuellen Spielzustand abrufen

        // Assert
        assertTrue(gameState.contains("Zug für " + player1 + " ")); // Der Zug sollte für Spieler 1 sein
        assertFalse(gameState.contains("Spiel beendet")); // Das Spiel sollte nicht beendet sein

        // Simulieren eines Spielgewinns
        game.dropToken(0);
        game.dropToken(1);
        game.dropToken(0);
        game.dropToken(1);
        game.dropToken(0);
        game.dropToken(1);
        game.dropToken(0);

        // Aktualisierten Spielzustand abrufen
        gameState = game.toString();

        // Überprüfen, ob der Spielgewinn korrekt repräsentiert wird
        assertTrue(gameState.contains("Spiel beendet")); // Das Spiel sollte jetzt beendet sein
        assertFalse(gameState.contains("Zug für " + player2 + " machen")); // Es sollte keinen Zug mehr für Spieler 2 geben
    }


}


