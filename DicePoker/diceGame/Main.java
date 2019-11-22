package diceGame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private int totalScore = 0;
    private int roundScore = 0;
    private int scoring = 0;
    private int rollsRem = 3;

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
    private ImageView[] dice = new ImageView[5];

    @Override
    public void start(Stage primaryStage) throws Exception{
        Label title = new Label("Dice Poker");
        Button start = new Button("Start Game");
        Button rules = new Button("Rules");
        Button exit = new Button("Exit");
        start.setMinWidth(50);
        rules.setMinWidth(50);

        VBox main = new VBox(20, title, start, rules, exit);
        main.setPadding(new Insets (10));
        main.setAlignment(Pos. CENTER);
        Scene mainScene = new Scene(main, 620, 360);
        mainScene.getStylesheets().add("sample/mainStyle");
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Project 2 (Dices)");
        primaryStage.show();

        Label rulesTitle = new Label("Rules");
        rulesTitle.setId("label-rulesTitle");
        Label rulesText = new Label(
                "You have a total of three rolls per turn. After the first roll, if you click a dice, it will\nremain " +
                        "\"held\" and not roll the next time you roll the dice. Once three rolls are up, you\nwill get an " +
                        "overall score determined by the best \"held\" hands.\n\n" +
                        "Hands:\n" +
                        "5 of A Kind - 10 Points\n" +
                        "Straight (5 numbers in a row i.e. 1, 2, 3, 4, 5 or 2, 3, 4, 5, 6) - 8 Points\n" +
                        "Four of A Kind - 7 Points\n" +
                        "Full House (3 of one kind, 2 of another) - 6 Points\n" +
                        "3 of A Kind - 5 Points\n" +
                        "2 Pair (2 of one kind, 2 of another, and 1 other value) - 4 Points\n" +
                        "2 of A Kind - 1 Point");
        Button goBack = new Button("Return");
        VBox ruling = new VBox(10, rulesTitle, rulesText, goBack);
        ruling.setPadding(new Insets (10));
        ruling.setAlignment(Pos. CENTER);
        Scene rulesScene = new Scene(ruling, 620,360);
        rulesScene.getStylesheets().add("sample/rulesStyle");

        Dice dice = new Dice();

        Button roll = new Button("Roll Dice");
        Button again = new Button("Play Again");
        Button exit2 = new Button("Exit");
        again.setDisable(true);
        Label oScore = new Label("Overall Score: " + totalScore);
        Label rScore = new Label("Round Score: " + roundScore);
        Label rolls = new Label("Rolls Remaining: " + rollsRem);

        HBox fiveDices = new HBox(20, dice.dices);
        HBox buttons = new HBox(15, again, roll, exit2);
        buttons.setAlignment(Pos. CENTER);
        fiveDices.setSpacing(15);
        fiveDices.setAlignment(Pos. CENTER);
        VBox game = new VBox(20, oScore, fiveDices, buttons, rScore, rolls);
        game.setPadding(new Insets(10));
        game.setAlignment(Pos. CENTER);
        Scene gameScene = new Scene(game, 620,360);
        gameScene.getStylesheets().add("sample/gameStyle");

        rules.setOnAction(event -> {
            primaryStage.setScene(rulesScene);
        });
        goBack.setOnAction(event -> {
            primaryStage.setScene(mainScene);
        });
        start.setOnAction(event -> {
            primaryStage.setScene(gameScene);
        });
        exit.setOnAction(event -> {
            primaryStage.close();
        });
        exit2.setOnAction(event -> {
            primaryStage.close();
        });
        roll.setOnAction(event -> {
            dice.roll();
            rollsRem--;
            rolls.setText("Rolls Remaining: " + rollsRem);
            rScore.setText("Round Score: "+ dice.getResult());
            scoring = scoring + dice.getResult();

            if (rollsRem == 0) {
                roll.setDisable(true);
                oScore.setText("Overall Score: "+ scoring);
                again.setDisable(false);
            }
        });
        again.setOnAction(event -> {
            dice.reset();
            roll.setDisable(false);
            again.setDisable(true);
            rollsRem = 3;
            scoring = 0;
            rolls.setText("Rolls Remaining: " + rollsRem);
            rScore.setText("Round Score: 0");
            oScore.setText("Overall Score: 0");
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}