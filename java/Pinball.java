import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Pinball extends JPanel implements ActionListener {

    private Timer timer;
    private int ballX, ballY;// co-ordinates for the ball
    private int paddleX;// co-ordinates for the paddle which move in x-axis
    private int dx, dy;// horizontal and vertical movement of the ball
    private int score;// scores of the user
    private boolean gameOver;// game over if ball doesn't touch the paddle

    private JButton startButton;
    private JButton stopButton;
    private JButton resumeButton;
    private JButton replayButton;

    public Pinball() {

        setBackground(Color.ORANGE);
        setPreferredSize(new Dimension(2000, 1000));// SIZE OF THE SCREEN TO BE DISPLAYED
        setFocusable(true);

        ballX = 200;
        ballY = 200;
        paddleX = 300;
        dx = 3;
        dy = 3;
        score = 0;
        gameOver = false;

        timer = new Timer(12, this);

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!timer.isRunning()) {
                    timer.start();
                    requestFocusInWindow();
                }
            }
        });
        stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (timer.isRunning()) {
                    timer.stop();
                }
            }
        });

        resumeButton = new JButton("Resume");
        resumeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!timer.isRunning()) {
                    timer.start();
                    requestFocusInWindow();
                }
            }
        });

        replayButton = new JButton("Replay");
        replayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
                timer.start();
                requestFocusInWindow();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resumeButton);
        buttonPanel.add(replayButton);
        add(buttonPanel);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!gameOver) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        paddleX -= 10;
                        if (paddleX < 0)
                            paddleX = 0;
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        paddleX += 10;
                        if (paddleX > getWidth() - 100)
                            paddleX = getWidth() - 100;
                    }
                }
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillOval(ballX, ballY, 30, 30);
        g.fillRect(paddleX, getHeight() - 20, 200, 20);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score: " + score, 10, 30);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 100));
            g.drawString("GAME OVER !!", getWidth() / 2 - 300, getHeight() / 2);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            ballX += dx;
            ballY += dy;

            if (ballX < 0 || ballX > getWidth() - 40)
                dx = -dx;

            if (ballY < 0)
                dy = -dy;

            if (ballY > getHeight() - 30) {
                if (ballX >= paddleX && ballX <= paddleX + 200) {
                    dy = -dy;
                    score++;
                    if (score % 10 == 0) {
                        timer.setDelay(timer.getDelay() - 2);
                    }
                } else {
                    gameOver = true;
                    timer.stop();
                }
            }

            repaint();
        }
    }

    private void resetGame() {
        ballX = 200;
        ballY = 200;
        paddleX = 300;
        dx = 3;
        dy = 3;
        score = 0;
        gameOver = false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pinball");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new Pinball());
            frame.pack();
            frame.setVisible(true);
        });
    }
}
