import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class gamepanel extends JPanel implements ActionListener {
//this is where the size of the game panel
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNIT= (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNIT];
    final int y[] = new int[GAME_UNIT];
    int bodypart = 6;
    int appleseaten = 0;
    int applex;
    int appley;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;


    //this is the constructor for the gamepanel class
    gamepanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new Mykeyadapter() {});
        startgame();

    }
    public void startgame(){
        newapple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        //this is where the graphics of the window are drawn
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){

        if (running){
            /* Grid lines to help visualization only of me tho #cheekyboy 💀
            for (int i = 0; i<SCREEN_HEIGHT/UNIT_SIZE; i++){
               g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
               g.drawLine(0, i*UNIT_SIZE, SCREEN_HEIGHT, i*UNIT_SIZE);
           } */
            //this is where the apple is drawn
                g.setColor(Color.red);
                g.fillOval(applex, appley, UNIT_SIZE, UNIT_SIZE);
            //this is where the snake is drawn
              for (int i = 0; i<bodypart; i++){
                    if (i == 0){
                        g.setColor(Color.green);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                    else{
                        g.setColor(new Color(45, 180, 0));
                        /* Make this active if you're gay or John
                        g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                        */
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
            }

            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Score: " + appleseaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + appleseaten))/2, g.getFont().getSize());

        }

        else{
            gameover(g);
        }

    }
    public void newapple(){
        applex = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appley = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        //this is where the snake body moves
        for (int i = bodypart; i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        //this is where the snake moves in the direction it is facing
        switch(direction) {
            case 'U':
            y[0] = y[0] - UNIT_SIZE;
            break;

            case 'D':
            y[0] = y[0] + UNIT_SIZE;
            break;

            case 'L':
            x[0] = x[0] - UNIT_SIZE;
            break;

            case 'R':
            x[0] = x[0] + UNIT_SIZE;
            break;
        }

    }
    public void checkapple(){
        //this is where the snake eats the apple and grows
        if ((x[0] == applex) && (y[0] == appley)){
            bodypart++;
            appleseaten++;
            newapple();
        }

    }
    public void checkcollosion(){
        //check if head collides with body
        for (int i = bodypart; i>0; i--){
            if ((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }  
        //check if head touches left border
        if (x[0] < 0){
            running = false;
        }
        //check if head touches right border
        if (x[0] >= SCREEN_WIDTH){
            running = false;
        }
        //check if head touches top border
        if (y[0] < 0){
            running = false;
        }
        //check if head touches bottom border
        if (y[0] >= SCREEN_HEIGHT){
            running = false;
        }
        //if the game is not running then stop the timer
        if (!running){
            timer.stop();
        }

    }
    public void gameover(Graphics g){
        //this is the game over screen
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics1.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
        //this is where the score is on the game over screen
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + appleseaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + appleseaten))/2, g.getFont().getSize());

    } 
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (running){
            move();
            checkapple();
            checkcollosion();
        }

        repaint();

    }
    //this is an inner class
    public class Mykeyadapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                //preventing the snake from going in the opposite direction into itself
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }

        }
    }

}
