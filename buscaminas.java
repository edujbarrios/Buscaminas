import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Buscaminas extends JFrame {
    
    private JPanel mainPanel;
    private JButton[][] buttons;
    private int[][] board;
    private int rows;
    private int cols;
    private int mines;
    private int remainingTiles;
    private JLabel statusLabel;
    private boolean gameOver;
    
    public Buscaminas(int rows, int cols, int mines) {
        super("Buscaminas");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Crear el panel principal y agregarlo a la ventana
        mainPanel = new JPanel(new GridLayout(rows, cols));
        add(mainPanel);
        
        // Crear un array de botones y agregarlos al panel principal
        buttons = new JButton[rows][cols];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                JButton button = new JButton("");
                button.setFont(new Font("Arial", Font.BOLD, 18));
                button.setFocusPainted(false);
                button.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if(!gameOver) {
                            int row = getButtonRow(button);
                            int col = getButtonCol(button);
                            if(e.getButton() == MouseEvent.BUTTON1) {
                                revealTile(row, col);
                            }
                            else if(e.getButton() == MouseEvent.BUTTON3) {
                                markTile(row, col);
                            }
                        }
                    }
                });
                buttons[i][j] = button;
                mainPanel.add(button);
            }
        }
        
        // Inicializar el tablero y esparcir las minas
        board = new int[rows][cols];
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        remainingTiles = rows * cols - mines;
        gameOver = false;
        initBoard();
        spreadMines();
        
        // Agregar una etiqueta de estado para mostrar el número de minas restantes
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusLabel = new JLabel("Minas restantes: " + mines);
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    // Inicializar el tablero con valores de celda no minados
    private void initBoard() {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                board[i][j] = 0;
            }
        }
    }
    
    // Esparcir las minas aleatoriamente en el tablero
    private void spreadMines() {
        int count = 0;
        while(count < mines) {
            int row = (int) (Math.random() * rows);
            int col = (int) (Math.random() * cols);
            if(board[row][col] != -1) {
                board[row][col] = -1;
                count++;
            }
        }
    }
    
    // Obtener el número de minas adyacentes a una celda
    private int getAdjacentMines(int row, int col) {
        int count = 0;
        for(int i = row - 1; i <= row + 1; i++) {
            for(int j = col - 1; j <= col + 1; j++) {
                if(i >= 0 && i < rows && j >= 0 && j < cols && board[i][j] == -1) {
                    count++;
                }
            }
        }
        return count;
    }
    
    // Obtener la fila de un botón en el array de botones
    private int getButtonRow(JButton button) {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(buttons[i][j] == button) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    // Obtener la columna de un botón en el array de botones
    private int getButtonCol(JButton button) {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(buttons[i][j] == button) {
                    return j;
                }
            }
        }
        return -1;
    }
    
    // Revelar una celda del tablero
    private void revealTile(int row, int col) {
        if(board[row][col] == -1) {
            gameOver();
        }
        else if(board[row][col] == 0) {
            revealEmptyTiles(row, col);
        }
        else {
            buttons[row][col].setText("" + board[row][col]);
            remainingTiles--;
            if(remainingTiles == 0) {
                gameWon();
            }
        }
    }
    
    // Revelar todas las celdas vacías adyacentes a una celda vacía
    private void revealEmptyTiles(int row, int col) {
        if(row < 0 || row >= rows || col < 0 || col >= cols || buttons[row][col].getText().length() > 0) {
            return;
        }
        buttons[row][col].setText("");
        remainingTiles--;
        if(remainingTiles == 0) {
            gameWon();
        }
        if(board[row][col] != 0) {
            buttons[row][col].setText("" + board[row][col]);
            return;
        }
        revealEmptyTiles(row - 1, col - 1);
        revealEmptyTiles(row - 1, col);
        revealEmptyTiles(row - 1, col + 1);
        revealEmptyTiles(row, col - 1);
        revealEmptyTiles(row, col + 1);
        revealEmptyTiles(row + 1, col - 1);
        revealEmptyTiles(row + 1, col);
        revealEmptyTiles(row + 1, col + 1);
    }
    
    // Marcar o desmarcar una celda como minada
    private void markTile(int row, int col) {
        if(buttons[row][col].getText().equals("")) {
            buttons[row][col].setText("X");
            statusLabel.setText("Minas restantes: " + --mines);
        }
        else if(buttons[row][col].getText().equals("X")) {
            buttons[row][col].setText("");
            statusLabel.setText("Minas restantes: " + ++mines);
        }
    }
    
    // Terminar el juego cuando se descubre una mina
    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Perdiste. Fin del juego.");
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(board[i][j] == -1) {
                    buttons[i][j].setText("X");
                }
            }
        }
        gameOver = true;
    }
    
    // Terminar el juego cuando se descubren todas las celdas no minadas
    private void gameWon() {
        JOptionPane.showMessageDialog(this, "Ganaste. Fin del juego.");
        gameOver = true;
    }
    
    public static void main(String[] args) {
        new Buscaminas(10, 10, 10);
    }
}
