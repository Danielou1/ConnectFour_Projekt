/**
 * Die View-Klasse ist für die Darstellung des Spiels verantwortlich.
 */
package View;
import Controler.IController;
import controlP5.*;
import processing.core.PApplet;
import processing.core.PImage;

public class View extends PApplet implements IView {

    /**
     * Startpunkt der Anwendung.
     *
     * @param args Die Argumente für die Anwendung.
     */
    public static void main(String[] args) {
        PApplet.runSketch(new String[]{"View"}, new View());
    }

    private Textfield player1NameInput;
    private Button saveButton;
    private IController controller;
    private boolean gameEnded = false;
    private boolean userTurn = true;
    private PImage emptyImg;
    private PImage redImg;
    private PImage yellowImg;
    private static final int COLS = 7;
    private static final int ROWS = 6;
    private PImage arrowImg;
    /**
     * Setzt den Controller für die View.
     *
     * @param controller Der Controller.
     */
    public void setController(IController controller) {
        this.controller = controller;
    }

    /**
     * Konfiguriert die Größe des Fensters.
     */
    public void settings() {
        size(700, 700);
    }

    /**
     * Initialisiert die View und die Steuerelemente.
     */
    public void setup() {
        ControlP5 cp5 = new ControlP5(this);
        int inputWidth = 200;
        int inputHeight = 40;
        int centerX = width / 2 - inputWidth / 2;
        int centerY = height / 2 - inputHeight;

        player1NameInput = cp5.addTextfield("HumanPlayer's Name")
                .setPosition(centerX, centerY)
                .setSize(300, 60)
                .setFont(createFont("arial", 25))
                .setAutoClear(true)
                .setColor(color(0))
                .setColorBackground(color(100))
                .setColorForeground(color(0))
                .setLabel("Enter Human's Name");

        saveButton = cp5.addButton("saveNames")
                .setPosition(300, 400)
                .setSize(200, 60)
                .setFont(createFont("arial", 20))
                .setLabel("Save");
        saveButton.addListener(this::saveNames);

        arrowImg = loadImage("images/fleches.png");
        arrowImg.resize(110, 55);
        emptyImg = loadImage("images/empty.png");
        emptyImg.resize(100, 100);
        redImg = loadImage("images/EvoliObese.jpeg");
        redImg.resize(100, 100);
        yellowImg = loadImage("images/PikachuObese.jpeg");
        yellowImg.resize(100, 100);
        controller.startThread();
    }

    /**
     * Verarbeitet das Speichern der Spielernamen.
     *
     * @param controlEvent Das Ereignis des Steuerelements.
     */
    public void saveNames(ControlEvent controlEvent) {
        String player1Name = player1NameInput.getText();

        if (!player1Name.isEmpty()) {
            gameEnded = false;
            saveButton.hide();
            player1NameInput.hide();
            controller.reinitializeGame();
            userTurn = true;
            redraw();
        }
    }

    /**
     * Zeichnet die Ansicht und aktualisiert sie.
     */
    public void draw() {
        background(175);
        drawArrows();
        drawBoard();
        displayWinner();
    }

    /**
     * Zeichnet die Pfeile über dem Spielfeld.
     */
    public void drawArrows() {
        for (int i = 0; i < COLS; i++) {
            image(arrowImg, i * 100, 0);
        }
    }

    /**
     * Aktualisiert die Ansicht.
     */
    public void updateView() {
        redraw();
    }

    /**
     * Verarbeitet den Mausklick und führt den Spielzug aus.
     */
    @Override
    public void mousePressed() {
        updateView();
        String player1Name = player1NameInput.getText();

        if (player1Name != null && !player1Name.isEmpty()) {
            if (!gameEnded) {
                if (userTurn) {
                    int col = (int) (map(mouseX, 0, width, 0, COLS));
                    int row = (int) (map(mouseY, 0, height, 0, ROWS));

                    float cellWidth = (float) width / COLS;
                    float cellHeight = (float) height / ROWS;

                    if (col >= 0 && col < COLS && row >= 0 && row < ROWS && mouseX >= 0 && mouseY >= 0) {
                        if (mouseX >= col * cellWidth && mouseX <= (col + 1) * cellWidth &&
                                mouseY >= row * cellHeight && mouseY <= (row + 1) * cellHeight) {
                            controller.dropToken(col);
                            controller.checkWinner();
                            if (controller.isGameWon() || controller.isBoardFull()) {
                                gameEnded = true;
                                println(controller.printGameState());
                            }
                        }
                    }
                }
            }

            userTurn = true;
            controller.switchActivePlayer();
            controller.notifyAIThread();
            println(controller.printGameState());
        }
    }

    /**
     * Zeigt an, dass Pikachu gewonnen hat.
     */
    public void pikachuWon() {
        //image(loadImage("images/PikachuObese.jpeg"), 150, 0, 350, 350);
        fill(0);
        textSize(50);
        text(player1NameInput.getText() + " YOU WON!!!", 150, 200);
        fill(232,30,234);
        stroke(255);
        text("Press 'ENTER' for a new Game", 0, 250);
    }

    /**
     * Zeigt an, dass Evoli(KI) gewonnen hat.
     */
    public void evolisWon() {
        //image(loadImage("images/EvoliObese.jpeg"), 150, 0, 350, 350);
        fill(0);
        textSize(50);
        text("Die KI WON!!!", 150, 200);
        fill(232,30,234);
        stroke(0);
        text("Press 'ENTER' for a Rematch", 20, 250);
    }

    /**
     * Zeigt an, dass es keinen Gewinner gibt.
     */
    public void ohneWinner() {
        background(0);
        PImage ohneWinnerImg = loadImage("images/OhneWinner.jpeg");
        image(ohneWinnerImg, 0, 0, width, height);
        fill(255, 255, 0);
        textSize(100);
        text("CONNECTFOUR PROFIS", 150, 400);
        fill(232,30,234);
        text("Drücken Sie 'ENTER', um erneut zu spielen", 100, 450);
    }

    /**
     * Verarbeitet die Eingabe der Enter-Taste.
     */
    @Override
    public void keyPressed() {
        if (keyCode == ENTER) {
            controller.stopThread();
            controller.reinitializeGame();
            gameEnded = false;
            controller.startThread();
        }
    }

    /**
     * Zeichnet das Spielfeld.
     */
    void drawBoard() {
        float cellSize = width / 7;
        float offsetX = (width - 7 * cellSize) / 2;
        float offsetY = (height - 6 * cellSize) / 2;

        for (int col = 0; col < controller.getColumnCount(); col++) {
            for (int row = 0; row < controller.getRowCount(); row++) {
                float x = col * cellSize + offsetX;
                float y = row * cellSize + offsetY;

                char token = controller.getBoardToken(row, col);

                if (token == '-') {
                    image(emptyImg, x, y, cellSize, cellSize);
                } else if (token == 'Y') {
                    image(yellowImg, x, y, cellSize, cellSize);
                } else if (token == 'R') {
                    image(redImg, x, y, cellSize, cellSize);
                }
            }
        }
    }

    /**
     * Zeigt den Gewinner oder das Unentschieden an.
     */
    @Override
    public void displayWinner() {
        if (controller.player1Won()) {
            pikachuWon();
        } else if (controller.player2Won()) {
            evolisWon();
        } else if (controller.isBoardFull()) {
            ohneWinner();
        }
    }
}
