import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
    private TextField input1;
    private TextField input2;
    private TextField input3;
    private TextField input4;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Label jan = new Label("January:");
        Label feb = new Label("February:");
        Label mar = new Label("March:");
        Label apr = new Label("April:");
        Label message = new Label("");
        Label instruction = new Label("Enter amount (in inches) of rainfall for each month");
        input1 = new TextField();
        input2 = new TextField();
        input3 = new TextField();
        input4 = new TextField();
        Button submit = new Button("Submit");

        TextFieldListener listener = new TextFieldListener();
        input1.textProperty().addListener(listener);
        input2.textProperty().addListener(listener);
        input3.textProperty().addListener(listener);
        input4.textProperty().addListener(listener);

        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        Menu view = new Menu("View");
        view.setDisable(true);
        MenuItem newData = new MenuItem("New");
        MenuItem quit = new MenuItem("Quit");
        MenuItem bar = new MenuItem("Bar Chart");
        MenuItem line = new MenuItem("Line Chart");
        MenuItem area = new MenuItem("Area Chart");
        menuBar.getMenus().addAll(file, view);
        file.getItems().addAll(newData, quit);
        view.getItems().addAll(bar, line, area);

        GridPane gridPane = new GridPane();
        gridPane.addRow(0, jan, input1);
        gridPane.addRow(1, feb, input2);
        gridPane.addRow(2, mar, input3);
        gridPane.addRow(3, apr, input4);
        gridPane.setPadding(new Insets(15));
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos. CENTER);

        VBox vbox = new VBox(10, instruction, gridPane, submit, message);
        vbox.setAlignment(Pos. CENTER);
        vbox.setPadding(new Insets(10));

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(vbox);

        submit.setOnAction(event -> {
            try {
                double input1Value = Double.parseDouble(input1.getText());
                double input2Value = Double.parseDouble(input2.getText());
                double input3Value = Double.parseDouble(input3.getText());
                double input4Value = Double.parseDouble(input4.getText());

                if (input1Value < 0 || input2Value < 0 || input3Value < 0 || input4Value < 0){
                    throw new NumberFormatException("lt0");
                }

                message.setText("");
                view.setDisable(false);

                borderPane.setCenter(createBarChart(input1Value, input2Value, input3Value, input4Value));
            }
            catch (NumberFormatException e){
                if (input1.getText().equals("") || input2.getText().equals("") || input3.getText().equals("") || input4.getText().equals("")){
                    message.setText("Please fill in ALL the empty boxes with non-negative numbers.");
                }
                else{
                    message.setText("Please fill in the boxes with positive numbers ONLY.");
                }
            }
        });

        bar.setOnAction(event -> {
            double input1Value = Double.parseDouble(input1.getText());
            double input2Value = Double.parseDouble(input2.getText());
            double input3Value = Double.parseDouble(input3.getText());
            double input4Value = Double.parseDouble(input4.getText());

            borderPane.setCenter(createBarChart(input1Value, input2Value, input3Value, input4Value));
        });

        line.setOnAction(event -> {
            double input1Value = Double.parseDouble(input1.getText());
            double input2Value = Double.parseDouble(input2.getText());
            double input3Value = Double.parseDouble(input3.getText());
            double input4Value = Double.parseDouble(input4.getText());

            borderPane.setCenter(createLineChart(input1Value, input2Value, input3Value, input4Value));
        });

        area.setOnAction(event -> {
            double input1Value = Double.parseDouble(input1.getText());
            double input2Value = Double.parseDouble(input2.getText());
            double input3Value = Double.parseDouble(input3.getText());
            double input4Value = Double.parseDouble(input4.getText());

            borderPane.setCenter(createAreaChart(input1Value, input2Value, input3Value, input4Value));
        });

        newData.setOnAction(event -> {
            input1.clear();
            input2.clear();
            input3.clear();
            input4.clear();

            message.setText("");
            borderPane.setCenter(vbox);
            view.setDisable(true);
        });

        quit.setOnAction(event -> {
            primaryStage.close();
        });

        primaryStage.setTitle("Project3");
        primaryStage.setScene(new Scene(borderPane, 560, 380));
        primaryStage.show();
    }

    private class TextFieldListener implements ChangeListener<String>{
        @Override
        public void changed(ObservableValue<? extends String> source, String oldValue, String newValue){
            String input1Value = input1.getText();
            String input2Value = input2.getText();
            String input3Value = input3.getText();
            String input4Value = input4.getText();
        }
    };

    private BarChart<String,Number> createBarChart(double i1, double i2, double i3, double i4){
        CategoryAxis hAxis = new CategoryAxis();
        hAxis.setLabel("Months");
        NumberAxis vAxis = new NumberAxis();
        vAxis.setLabel("Rainfall (in inches)");

        BarChart<String, Number> barChart = new BarChart(hAxis, vAxis);
        barChart.setTitle("Rainfall in inches for each month");

        XYChart.Series<String, Number> points = new XYChart.Series<String, Number>();
        points.getData().add(new XYChart.Data<>("January", i1));
        points.getData().add(new XYChart.Data<>("February", i2));
        points.getData().add(new XYChart.Data<>("March", i3));
        points.getData().add(new XYChart.Data<>("April", i4));

        barChart.getData().add(points);
        barChart.setLegendVisible(false);

        return barChart;
    }

    private LineChart<String,Number> createLineChart(double i1, double i2, double i3, double i4){
        CategoryAxis hAxis = new CategoryAxis();
        hAxis.setLabel("Months");
        NumberAxis vAxis = new NumberAxis();
        vAxis.setLabel("Rainfall (in inches)");

        LineChart<String, Number> lineChart = new LineChart(hAxis, vAxis);
        lineChart.setTitle("Rainfall in inches for each month");

        XYChart.Series<String, Number> points = new XYChart.Series<String, Number>();
        points.getData().add(new XYChart.Data<>("January", i1));
        points.getData().add(new XYChart.Data<>("February", i2));
        points.getData().add(new XYChart.Data<>("March", i3));
        points.getData().add(new XYChart.Data<>("April", i4));

        lineChart.getData().add(points);
        lineChart.setLegendVisible(false);

        return lineChart;
    }

    private AreaChart<String,Number> createAreaChart(double i1, double i2, double i3, double i4){
        CategoryAxis hAxis = new CategoryAxis();
        hAxis.setLabel("Months");
        NumberAxis vAxis = new NumberAxis();
        vAxis.setLabel("Rainfall (in inches)");

        AreaChart<String, Number> areaChart = new AreaChart(hAxis, vAxis);
        areaChart.setTitle("Rainfall in inches for each month");

        XYChart.Series<String, Number> points = new XYChart.Series<String, Number>();
        points.getData().add(new XYChart.Data<>("January", i1));
        points.getData().add(new XYChart.Data<>("February", i2));
        points.getData().add(new XYChart.Data<>("March", i3));
        points.getData().add(new XYChart.Data<>("April", i4));
        areaChart.getData().add(points);
        areaChart.setLegendVisible(false);

        return areaChart;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
