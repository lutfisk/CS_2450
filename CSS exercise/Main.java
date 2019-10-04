import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private TextField awe;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Label awesome = new Label("Choose and type AWESOME in the boxes if you're awesome!");
        Label typeIt = new Label("Type it in here:");
        Label chooseIt = new Label("Choose it here:");
        awe = new TextField();
        Button yesIam = new Button("I AM AWESOME!");

        TextFieldListener listener = new TextFieldListener();
        awe.textProperty().addListener(listener);

        ChoiceBox<String> areYouAwesome = new ChoiceBox<>();
        areYouAwesome.getItems().addAll("AWE", "AWESUM", "AWESOME", "AWUWU", "WTF IS THIS!?");

        HBox hbox1 = new HBox(5, typeIt, awe);
        hbox1.setPadding(new Insets(5));
        hbox1.setAlignment(Pos. CENTER);

        HBox hbox2 = new HBox(5, chooseIt, areYouAwesome);
        hbox2.setPadding(new Insets(5));
        hbox2.setAlignment(Pos. CENTER);

        VBox vbox = new VBox(20, awesome, hbox2, hbox1, yesIam);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos. CENTER);

        yesIam.setOnAction(event -> {
            String aweInput = awe.getText();
            if (awe.getText().equals("AWESOME") && areYouAwesome.getSelectionModel().getSelectedItem().equals("AWESOME")){
                Alert success = new Alert (Alert.AlertType.INFORMATION);
                success.setTitle("AWESOME!");
                success.setHeaderText("YOU MADE IT!");
                success.setContentText("YOU ARE AWESOME!! WOHOOO!");
                success.show();
            }
            else{
                Alert fail = new Alert (Alert.AlertType.ERROR);
                fail.setTitle("NOPE!");
                fail.setHeaderText("Not today =(");
                fail.setContentText("You are unfortunately not awesome..");
                fail.show();
            }
        });

        Scene myScene = new Scene(vbox, 600, 300);
        primaryStage.setScene(myScene);
        myScene.getStylesheets().add("sample/mystyle");
        primaryStage.setTitle("In-Class Assignment 6");
        primaryStage.show();
    }

    private class TextFieldListener implements ChangeListener<String>{
        @Override
        public void changed(ObservableValue<? extends String> source, String oldValue, String newValue){
            String aweInput = awe.getText();
        }
    };

    public static void main(String[] args) {
        launch(args);
    }
}
