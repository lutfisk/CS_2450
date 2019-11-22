package diceGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Random;

public class Dice {
    private Image d1 = new Image("file:./DiceImages/Dice1.png");
    private Image d2 = new Image("file:./DiceImages/Dice2.png");
    private Image d3 = new Image("file:./DiceImages/Dice3.png");
    private Image d4 = new Image("file:./DiceImages/Dice4.png");
    private Image d5 = new Image("file:./DiceImages/Dice5.png");
    private Image d6 = new Image("file:./DiceImages/Dice6.png");

    private Image d1h = new Image("file:./DiceImages/Dice1Held.png");
    private Image d2h = new Image("file:./DiceImages/Dice2Held.png");
    private Image d3h = new Image("file:./DiceImages/Dice3Held.png");
    private Image d4h = new Image("file:./DiceImages/Dice4Held.png");
    private Image d5h = new Image("file:./DiceImages/Dice5Held.png");
    private Image d6h = new Image("file:./DiceImages/Dice6Held.png");

    private Image[] img = new Image[]{d1, d2,d3, d4, d5, d6};
    private Image[] imgh = new Image[]{d1h, d2h, d3h, d4h, d5h, d6h};
    private int[] values = new int[]{1, 2, 3, 4, 5};
    private boolean[] held = new boolean[5]; //none held initially
    public ImageView[] dices = new ImageView[5];

    public Dice(){
        //setup dice
        for (int i = 0; i < dices.length; i++){
            //set die image to its value
            dices[i] = new ImageView(img[values[i] - 1]);
            dices[i].setFitHeight(100);
            dices[i].setFitWidth(100);
        }

        //setup click handlers to flip held state/image
        for (int k=0; k<dices.length; k++)
        {
            final int i = k;
            ImageView die = dices[i];
            die.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                int dieval = values[i];
                if (!held[i]) {
                    held[i] = true;
                    die.setImage(imgh[dieval-1]);
                } else {
                    held[i] = false;
                    die.setImage(img[dieval-1]);
                }
            });
        }
    }

    public void reset() {
        for (int i=0; i<dices.length; i++){
            held[i] = false;
            values[i] = i+1;
            dices[i].setImage(img[i]);
        }
    }

    public void roll() {
        Random rng = new Random();
        //randomize unheld dice
        for (int i=0; i<dices.length; i++){
            if (!held[i]) {
                int dieVal = rng.nextInt(6) + 1;
                values[i] = dieVal;
                dices[i].setImage(img[dieVal-1]);
            }
        }
    }

    public int getResult() {
        //calculating score by checking value of index and how many
        int score = 0;
        for (int i=0; i<dices.length; i++){
            //5 of a kind
            if(values[i] == 5){
                score = 10;
            }
            //4 of a kind
            else if(values[i] == 4){
                score = 7;
            }
            //3 of a kind
            else if(values[i] == 3){
                score = 5;
                //combined with a pair
                for (int j=0; j<dices.length; j++){
                    if (values[j] == 2){
                        score = score+2;
                    }
                }
            }
            //a pair
            else if(values[i] == 2) {
                score = 1;
                //two pairs (of not the same face value)
                for (int j = 0; j<dices.length; j++) {
                    if (values[j] == 2 && j != i) {
                        score = score + 2;
                    }
                }
            }
            //straight
            else if(values[i] == 1){
                for (int j = 0; j<dices.length; j++){
                    if (values[j] == 1 && j!=1){
                        score = 8;
                    }
                }
            }
        }
        return score;
    }
}