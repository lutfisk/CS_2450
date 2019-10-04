import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private TextField days;
    private TextField transportCost;
    private TextField registration;
    private TextField lodging;
    private TextField food;
    private double totalCost, lodgingTotal, payTotal, foodTotal;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Label transport = new Label("");
        Label owe = new Label("");
        Label display = new Label("");

        days = new TextField();
        transportCost = new TextField();
        registration = new TextField();
        lodging = new TextField();
        food = new TextField();
        transportCost.setVisible(false);

        TextFieldListener listener = new TextFieldListener();
        days.textProperty().addListener(listener);
        transportCost.textProperty().addListener(listener);
        registration.textProperty().addListener(listener);
        lodging.textProperty().addListener(listener);
        food.textProperty().addListener(listener);

        ChoiceBox<String> transportChoice = new ChoiceBox<>();
        transportChoice.getItems().addAll("Air travel", "Driving");
        transportChoice.setOnAction(event -> {
            if (transportChoice.getSelectionModel().getSelectedItem().equals("Air travel")){
                transport.setText("Airfare cost:");
                transportCost.setVisible(true);
            }
            else if (transportChoice.getSelectionModel().getSelectedItem().equals("Driving")) {
                transport.setText("Miles driven:");
                transportCost.setVisible(true);
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.addRow(0, new Label("How many days was the trip?"), days);
        gridPane.addRow(1, new Label("Transportation method:"), transportChoice);
        gridPane.addRow(2, transport, transportCost);
        gridPane.addRow(3, new Label("Registration fee:"), registration);
        gridPane.addRow(4, new Label("Lodging costs per night:"), lodging);
        gridPane.addRow(5, new Label("Total food costs:"), food);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos. CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(10);

        Button submit = new Button("submit");
        submit.setOnAction(event -> {
            try{
                double daysValue = Double.parseDouble(days.getText());
                double transportValue = Double.parseDouble(transportCost.getText());
                double registrationValue = Double.parseDouble(registration.getText());
                double lodgingValue = Double.parseDouble(lodging.getText());
                double foodValue = Double.parseDouble(food.getText());
                totalCost = (transportValue*0.42) + registrationValue + (lodgingValue*daysValue) + foodValue;
                if (foodValue < (47*daysValue)){
                    foodTotal = foodValue;
                }
                else if (foodValue > (47*daysValue)){
                    foodTotal = 47*daysValue;
                }
                if (lodgingValue < 195){
                    lodgingTotal = (lodgingValue*daysValue);
                }
                else if (lodgingValue > 195){
                    lodgingTotal = 195*daysValue;
                }
                payTotal = totalCost - (foodTotal + lodgingTotal + (0.42*transportValue) + registrationValue);
                display.setText(String.format("Total expenses: $%.2f", totalCost));
                owe.setText(String.format("The amount to be paid: $%.2f",  payTotal));
            }
            catch (NumberFormatException e){
                if (days.getText().equals("") || transportCost.getText().equals("") || registration.getText().equals("") || lodging.getText().equals("") || food.getText().equals("")) {
                    display.setText("Please fill in ALL the empty boxes with numbers.");
                    owe.setText("");
                }
                else {
                    display.setText("Please fill in the boxes with numbers ONLY.");
                    owe.setText("");
                }
            }
        });
        VBox vbox = new VBox(20, gridPane, submit, display, owe);
        vbox.setAlignment(Pos. CENTER);

        Scene myScene = new Scene(vbox, 425, 480);
        primaryStage.setScene(myScene);
        primaryStage.setTitle("Project 1");
        primaryStage.show();
    }

    private class TextFieldListener implements ChangeListener<String>{
        @Override
        public void changed(ObservableValue<? extends String> source, String oldValue, String newValue){
            String daysValue = days.getText();
            String transportValue = transportCost.getText();
            String registrationValue = registration.getText();
            String lodgingValue = lodging.getText();
            String foodValue = food.getText();
        }
    };

    public static void main(String[] args) {
        launch(args);
    }
}
