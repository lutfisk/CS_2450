package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Main extends Application {
    private TextField locationX;
    private TextField locationY;
    private TextField radius;
    private TextField height;
    private TextField length;
    private TextField width;

    private HashMap<Shape3D, Boolean> shapeMap;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Button addShape = new Button("Add Shape");
        Button clearAll = new Button("Clear All Shapes");

        Label yAxis = new Label("Y-Axis");
        Slider ySlider = new Slider(0, 360, 180);
        ySlider.setPrefWidth(100.0);
        Label xAxis = new Label("X-Axis");
        Slider xSlider = new Slider(0, 360, 180);
        xSlider.setPrefWidth(100.0);

        ChoiceBox<String> shapeColor = new ChoiceBox<>();
        shapeColor.getItems().addAll("","Red", "Blue", "Green", "Purple", "White");
        shapeColor.setPrefWidth(180);
        Label color = new Label("Object Color: ");

        ChoiceBox<String> background = new ChoiceBox<>();
        background.getItems().addAll("Gray", "Beige", "Sea Green", "Salmon", "Black");
        background.setPrefWidth(180);
        Label bgrColor = new Label("Background Color: ");

        Label translate = new Label("Translate: ");
        Slider xT = new Slider(-0.5, 0.5, 0);
        xT.setPrefWidth(150.0);
        xT.setMinorTickCount(0);
        xT.setMajorTickUnit(0.25);
        Slider yT = new Slider(-0.5, 0.5, 0);
        yT.setPrefWidth(150.0);
        yT.setMinorTickCount(0);
        yT.setMajorTickUnit(0.25);
        Slider zT = new Slider(-0.5, 0.5, 0);
        zT.setPrefWidth(150.0);
        zT.setMinorTickCount(0);
        zT.setMajorTickUnit(0.25);
        Label xTranslate = new Label("X:");
        Label yTranslate = new Label("Y:");
        Label zTranslate = new Label("Z:");


        Label scale = new Label("Scaling: ");
        Slider sX = new Slider(-100, 200, 50);
        sX.setPrefWidth(150.0);
        Slider sY = new Slider(-100, 200, 50);
        sY.setPrefWidth(150.0);
        Slider sZ = new Slider(-100, 200, 50);
        sZ.setPrefWidth(150.0);
        Label xScale = new Label("X:");
        Label yScale = new Label("Y:");
        Label zScale = new Label("Z:");

        TextFieldListener listener = new TextFieldListener();

        VBox rootNode = new VBox(15);
        rootNode.setPadding(new Insets(20));
        rootNode.setAlignment(Pos. CENTER);

        Group shapesGroup = new Group();
        SubScene shapesSub = new SubScene(shapesGroup, 450, 420, true, SceneAntialiasing. DISABLED);
        shapesSub.setFill(Color.GAINSBORO);
        PerspectiveCamera pCamera = new PerspectiveCamera(true);
        Rotate rotateX = new Rotate(25, Rotate.X_AXIS);
        pCamera.getTransforms().addAll(rotateX, new Translate(0,0, -75));
        shapesSub.setCamera(pCamera);

        background.getSelectionModel().selectedItemProperty().addListener(((source, oldString, newString)->{
            if (newString.equals("Gray")){
                shapesSub.setFill(Color.GAINSBORO);
            }
            if (newString.equals("Beige")){
                shapesSub.setFill(Color.BISQUE);
            }
            if (newString.equals("Sea Green")){
                shapesSub.setFill(Color.DARKSEAGREEN);
            }
            if (newString.equals("Salmon")){
                shapesSub.setFill(Color.LIGHTCORAL);
            }
            if (newString.equals("Black")){
                shapesSub.setFill(Color.BLACK);
            }
        }));

        VBox bgrBox = new VBox(5, bgrColor, background);
        bgrBox.setPadding(new Insets(10));
        bgrBox.setAlignment(Pos.CENTER);

        HBox xRotation = new HBox(10, xAxis, xSlider);
        xRotation.setPadding(new Insets(10,10,2,10));
        xRotation.setAlignment(Pos.CENTER);

        HBox yRotation = new HBox(10, yAxis, ySlider);
        yRotation.setPadding(new Insets(2,10,10,10));
        yRotation.setAlignment(Pos.CENTER);

        VBox clrBox = new VBox(5, color, shapeColor);
        clrBox.setPadding(new Insets(10));
        clrBox.setAlignment(Pos.CENTER);

        HBox transX = new HBox(5, xTranslate, xT);
        transX.setPadding(new Insets(5));
        transX.setAlignment(Pos. CENTER);

        HBox transY = new HBox(5, yTranslate, yT);
        transY.setPadding(new Insets(5));
        transY.setAlignment(Pos. CENTER);

        HBox transZ = new HBox(5, zTranslate, zT);
        transZ.setPadding(new Insets(5));
        transZ.setAlignment(Pos. CENTER);

        VBox transBox = new VBox(2, translate, transX, transY, transZ);
        transBox.setPadding(new Insets (5));
        transBox.setAlignment(Pos. CENTER);

        HBox scaleX = new HBox(5, xScale, sX);
        scaleX.setPadding(new Insets(5));
        scaleX.setAlignment(Pos.CENTER);

        HBox scaleY = new HBox(5, yScale, sY);
        scaleY.setPadding(new Insets(5));
        scaleY.setAlignment(Pos.CENTER);

        HBox scaleZ = new HBox(5, zScale, sZ);
        scaleZ.setPadding(new Insets(5));
        scaleZ.setAlignment(Pos.CENTER);

        VBox scaleBox = new VBox(2, scale, scaleX, scaleY, scaleZ);
        scaleBox.setPadding(new Insets(5));
        scaleBox.setAlignment(Pos.CENTER);

        VBox controls = new VBox(5, bgrBox, xRotation, yRotation, clrBox, transBox, scaleBox);
        controls.setPadding(new Insets(0,15,0,15));
        controls.setAlignment(Pos. CENTER);

        shapeMap = new HashMap<>();
        BorderPane bp = new BorderPane();
        MenuBar menu = new MenuBar();
        Menu file = new Menu("File");
        Menu exit = new Menu("Exit");
        menu.getMenus().addAll(file, exit);
        MenuItem save = new MenuItem("Save");
        MenuItem load = new MenuItem("Load");
        MenuItem quit = new MenuItem("Quit");
        file.getItems().addAll(save, load);
        exit.getItems().addAll(quit);
        bp.setTop(menu);

        quit.setOnAction(event ->{
            System.exit(0);
        });

        FileChooser fc = new FileChooser();
        fc.setTitle("Save");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files","*.txt"));

        save.setOnAction(event -> {
            File f = fc.showSaveDialog(primaryStage);
            try
            {
                PrintWriter outputFile = new PrintWriter(f);
                for(int i = 0; i < shapesGroup.getChildren().size(); i++)
                {
                    Node thisShape = shapesGroup.getChildren().get(i);
                    outputFile.println(thisShape.getClass().getSimpleName());

                    outputFile.println("Current location");
                    outputFile.println(thisShape.getTranslateX());
                    outputFile.println(thisShape.getTranslateY());

                    outputFile.println("Current Scale");
                    outputFile.println(Math.floor(thisShape.getScaleX() * 100) / 100);
                    outputFile.println(Math.floor(thisShape.getScaleY() * 100) / 100);
                    outputFile.println(Math.floor(thisShape.getScaleZ() * 100) / 100);
                    outputFile.println("---");
                }
                outputFile.flush();
                outputFile.close();
            }
            catch (Exception fne)
            {
                Alert alert = new Alert(Alert.AlertType. ERROR);
                alert.setHeaderText( "Error Creating File!");
                alert.show();
            }
        });

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        load.setOnAction(event -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    Scanner inFile = new Scanner(selectedFile);
                    while(inFile.hasNextLine()) {
                        String line;
                        Shape3D shape;
                        line = inFile.nextLine();
                        if (line.equals("Box")) {
                            shape = new Box();
                            inFile.nextLine();
                            shape.setTranslateX(inFile.nextDouble());
                            shape.setTranslateY(inFile.nextDouble());
                            inFile.nextLine();
                            shape.setScaleX(inFile.nextDouble());
                            shape.setScaleY(inFile.nextDouble());
                            shape.setScaleZ(inFile.nextDouble());
                            inFile.nextLine();
                        } else if (line.equals("Cylinder")) {
                            shape = new Cylinder();
                            inFile.nextLine();
                            shape.setTranslateX(inFile.nextDouble());
                            shape.setTranslateY(inFile.nextDouble());
                            inFile.nextLine();
                            shape.setScaleX(inFile.nextDouble());
                            shape.setScaleY(inFile.nextDouble());
                            shape.setScaleZ(inFile.nextDouble());
                            inFile.nextLine();
                        } else if (line.equals("Sphere")) {
                            shape = new Sphere();
                            inFile.nextLine();
                            shape.setTranslateX(inFile.nextDouble());
                            shape.setTranslateY(inFile.nextDouble());
                            inFile.nextLine();
                            shape.setScaleX(inFile.nextDouble());
                            shape.setScaleY(inFile.nextDouble());
                            shape.setScaleZ(inFile.nextDouble());
                            inFile.nextLine();
                        }
                    }
                    inFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Scene myScene = new Scene(bp, 720, 580);

        locationX = new TextField();
        locationY = new TextField();
        radius = new TextField();
        height = new TextField();
        length = new TextField();
        width = new TextField();
        Label chooseShape = new Label("Choose a shape:");
        Label x = new Label("X Coordinate:");
        Label y = new Label("Y Coordinate:");
        Label r = new Label("Radius:");
        Label h = new Label("Height:");
        Label l = new Label("Length:");
        Label w = new Label("Width:");
        Button submit = new Button("Submit");
        Button cancel = new Button("Cancel");
        BorderPane bPane = new BorderPane();
        Scene shapeScene = new Scene(bPane, 400, 450);

        ChoiceBox<String> cBox = new ChoiceBox<>();
        cBox.getItems().addAll("","Box","Cylinder","Sphere");

        addShape.setOnAction(actionEvent -> {
            cBox.valueProperty().set("");
            locationX.clear();
            locationY.clear();
            width.clear();
            radius.clear();
            height.clear();
            length.clear();
            GridPane gPane = new GridPane();
            gPane.addRow(1,x,locationX);
            gPane.addRow(2,y,locationY);
            gPane.addRow(3,r,radius);
            gPane.addRow(4,h,height);
            gPane.addRow(5,l,length);
            gPane.addRow(6,w,width);

            gPane.setAlignment(Pos. CENTER);

            HBox hbox1 = new HBox(10,chooseShape,cBox);
            hbox1.setAlignment(Pos.CENTER);
            hbox1.setPadding(new Insets(10));
            gPane.add(hbox1,1,0);

            HBox hbox2 = new HBox(80,cancel,submit);
            hbox2.setAlignment(Pos.CENTER);
            hbox2.setPadding(new Insets(10));
            gPane.add(hbox2,1,7);

            gPane.setAlignment(Pos.CENTER);
            gPane.setPadding(new Insets(10));
            gPane.setHgap(15);
            gPane.setVgap(15);

            locationX.setDisable(true);
            locationY.setDisable(true);
            radius.setDisable(true);
            height.setDisable(true);
            length.setDisable(true);
            width.setDisable(true);

            locationX.textProperty().addListener(listener);
            locationY.textProperty().addListener(listener);
            width.textProperty().addListener(listener);
            height.textProperty().addListener(listener);
            length.textProperty().addListener(listener);

            cancel.setOnAction(e -> {
                cBox.valueProperty().set("");
                locationX.clear();
                locationY.clear();
                width.clear();
                height.clear();
                length.clear();
                radius.clear();
                primaryStage.setScene(myScene);
            });

            cBox.getSelectionModel().selectedItemProperty().addListener(((source, oldString, newString)->{
                locationX.setDisable(cBox.getSelectionModel().getSelectedItem() == null || cBox.getSelectionModel().getSelectedItem().equals(""));
                locationY.setDisable(cBox.getSelectionModel().getSelectedItem() == null || cBox.getSelectionModel().getSelectedItem().equals(""));
                x.setDisable(newString.equals(""));
                y.setDisable(newString.equals(""));
                radius.setDisable(newString.equals("Box") || newString.equals(""));
                r.setDisable(newString.equals("Box") || newString.equals(""));
                height.setDisable(newString.equals("Sphere") || newString.equals(""));
                h.setDisable(newString.equals("Sphere") || newString.equals(""));
                length.setDisable(newString.equals("Cylinder") || newString.equals("Sphere") || newString.equals(""));
                l.setDisable(newString.equals("Cylinder") || newString.equals("Sphere") || newString.equals(""));
                width.setDisable(newString.equals("Cylinder") || newString.equals("Sphere") || newString.equals(""));
                w.setDisable(newString.equals("Cylinder") || newString.equals("Sphere") || newString.equals(""));

                submit.setOnAction(e -> {
                    if(newString.equals("Box")){
                        try {
                            Double p1 = Double.parseDouble(locationX.getText());
                            Double p2 = Double.parseDouble(locationY.getText());
                            Double b1 = Double.parseDouble(width.getText());
                            Double b2 = Double.parseDouble(height.getText());
                            Double b3 = Double.parseDouble(length.getText());

                            if (b1 <= 0 || b2 <= 0 || b3 <= 0) {
                                Alert error1 = new Alert(Alert.AlertType.ERROR, "The width, height, and length needs to be positive.");
                                Optional<ButtonType> choice = error1.showAndWait();
                                if (choice.isPresent() && choice.get() == ButtonType.OK)
                                    error1.close();
                            }
                            if (p1 < -20 || p2 < -20 || p1 > 20 || p2 > 20) {
                                Alert error2 = new Alert(Alert.AlertType.ERROR, "Both x and y location has to be between -20 and 20.");
                                Optional<ButtonType> choice = error2.showAndWait();
                                if (choice.isPresent() && choice.get() == ButtonType.OK)
                                    error2.close();
                            } else {
                                Box b = new Box(b1, b2, b3);
                                b.setTranslateX(p1);
                                b.setTranslateY(p2);
                                shapesGroup.getChildren().add(b);
                                shapeMap.put(b, false);
                                b.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                                    xSlider.setValue(180);
                                    ySlider.setValue(180);
                                    xT.setValue(0);
                                    yT.setValue(0);
                                    zT.setValue(0);
                                    shapeColor.valueProperty().set("");
                                    for (Map.Entry<Shape3D, Boolean> ent : shapeMap.entrySet()) {
                                        if (ent.getKey() != b) {
                                            ent.setValue(false);
                                        } else {
                                            ent.setValue(true);
                                        }
                                    }
                                    xSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double xRotate = (xSlider.getValue());
                                        if (shapeMap.get(b)) b.getTransforms().add(new Rotate(xRotate, Rotate.X_AXIS));
                                    }));
                                    ySlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double xRotate = (ySlider.getValue());
                                        if (shapeMap.get(b)) b.getTransforms().add(new Rotate(xRotate, Rotate.Y_AXIS));
                                    }));
                                    xT.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double xTran = (xT.getValue());
                                        if(shapeMap.get(b)){
                                            b.getTransforms().addAll(new Translate(xTran, yT.getValue(), zT.getValue()));
                                        }
                                    }));
                                    yT.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double yTran = (yT.getValue());
                                        if(shapeMap.get(b)){
                                            b.getTransforms().addAll(new Translate(xT.getValue(), yTran, zT.getValue()));
                                        }
                                    }));
                                    zT.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double zTran = (zT.getValue());
                                        if(shapeMap.get(b)){
                                            b.getTransforms().addAll(new Translate(xT.getValue(), yT.getValue(), zTran));
                                        }
                                    }));
                                    sX.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double xSc = (sX.getValue()/100);
                                        if(shapeMap.get(b)){
                                            b.setScaleX(xSc);
                                        }
                                    }));
                                    sY.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double ySc = (sY.getValue()/100);
                                        if(shapeMap.get(b)){
                                            b.setScaleY(ySc);
                                        }
                                    }));
                                    sZ.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double zSc = (sZ.getValue()/100);
                                        if(shapeMap.get(b)){
                                            b.setScaleZ(zSc);
                                        }
                                    }));
                                    shapeColor.getSelectionModel().selectedItemProperty().addListener(((s, o, n) -> {
                                        if (n.equals("Red")) {
                                            if (shapeMap.get(b)) b.setMaterial(new PhongMaterial(Color.RED));
                                        }
                                        if (n.equals("Blue")) {
                                            if (shapeMap.get(b)) b.setMaterial(new PhongMaterial(Color.BLUE));
                                        }
                                        if (n.equals("Green")) {
                                            if (shapeMap.get(b)) b.setMaterial(new PhongMaterial(Color.GREEN));
                                        }
                                        if (n.equals("Purple")) {
                                            if (shapeMap.get(b)) b.setMaterial(new PhongMaterial(Color.PURPLE));
                                        }
                                        if (n.equals("White")) {
                                            if (shapeMap.get(b)) b.setMaterial(new PhongMaterial(Color.GHOSTWHITE));
                                        }
                                    }));
                                });
                                primaryStage.setScene(myScene);
                            }
                        }
                        catch(Exception e1)
                        {
                            Alert error2 = new Alert(Alert.AlertType.ERROR, "Only submit integer values please.\n i.e. (1,2,3,...)");
                            Optional<ButtonType> choice = error2.showAndWait();
                            if (choice.isPresent() && choice.get() == ButtonType.OK)
                                error2.close();
                        }
                    }
                    if(newString.equals(("Cylinder"))){
                        try {
                            Double q1 = Double.parseDouble(locationX.getText());
                            Double q2 = Double.parseDouble(locationY.getText());
                            Double c1 = Double.parseDouble(radius.getText());
                            Double c2 = Double.parseDouble(height.getText());
                            if(c1 <= 0||c2 <= 0){
                                Alert error1 = new Alert(Alert.AlertType.ERROR, "The radius and height needs to be positive.");
                                Optional<ButtonType> choice = error1.showAndWait();
                                if (choice.isPresent() && choice.get() == ButtonType.OK)
                                    error1.close();
                            }
                            if(q1 <- 20 || q2 <- 20 || q1 > 20 || q2 > 20) {
                                Alert error1 = new Alert(Alert.AlertType.ERROR, "Both x and y location has to be between -20 and 20.");
                                Optional<ButtonType> choice = error1.showAndWait();
                                if (choice.isPresent() && choice.get() == ButtonType.OK)
                                    error1.close();
                            }
                            else
                            {
                                Cylinder c = new Cylinder(c1,c2);
                                c.setTranslateX(q1);
                                c.setTranslateY(q2);
                                shapesGroup.getChildren().add(c);
                                shapeMap.put(c, false);
                                c.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                                    xSlider.setValue(180);
                                    ySlider.setValue(180);
                                    xT.setValue(0);
                                    yT.setValue(0);
                                    zT.setValue(0);
                                    shapeColor.valueProperty().set("");
                                    for (Map.Entry<Shape3D, Boolean> ent : shapeMap.entrySet()) {
                                        if (ent.getKey() != c) {
                                            ent.setValue(false);
                                        } else {
                                            ent.setValue(true);
                                        }
                                    }
                                    xSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double xRotate = (xSlider.getValue());
                                        if (shapeMap.get(c)) c.getTransforms().add(new Rotate(xRotate, Rotate.X_AXIS));
                                    }));
                                    ySlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double xRotate = (ySlider.getValue());
                                        if (shapeMap.get(c)) c.getTransforms().add(new Rotate(xRotate, Rotate.Y_AXIS));
                                    }));
                                    xT.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double xTran = (xT.getValue());
                                        if(shapeMap.get(c)){
                                            c.getTransforms().addAll(new Translate(xTran, yT.getValue(), zT.getValue()));
                                        }
                                    }));
                                    yT.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double yTran = (yT.getValue());
                                        if(shapeMap.get(c)){
                                            c.getTransforms().addAll(new Translate(xT.getValue(), yTran, zT.getValue()));
                                        }
                                    }));
                                    zT.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double zTran = (zT.getValue());
                                        if(shapeMap.get(c)){
                                            c.getTransforms().addAll(new Translate(xT.getValue(), yT.getValue(), zTran));
                                        }
                                    }));
                                    sX.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double xSc = (sX.getValue()/100);
                                        if(shapeMap.get(c)){
                                            c.setScaleX(xSc);
                                        }
                                    }));
                                    sY.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double ySc = (sY.getValue()/100);
                                        if(shapeMap.get(c)){
                                            c.setScaleY(ySc);
                                        }
                                    }));
                                    sZ.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double zSc = (sZ.getValue()/100);
                                        if(shapeMap.get(c)){
                                            c.setScaleZ(zSc);
                                        }
                                    }));
                                    shapeColor.getSelectionModel().selectedItemProperty().addListener(((s, o, n) -> {
                                        if (n.equals("Red")) {
                                            if (shapeMap.get(c)) c.setMaterial(new PhongMaterial(Color.RED));
                                        }
                                        if (n.equals("Blue")) {
                                            if (shapeMap.get(c)) c.setMaterial(new PhongMaterial(Color.BLUE));
                                        }
                                        if (n.equals("Green")) {
                                            if (shapeMap.get(c)) c.setMaterial(new PhongMaterial(Color.GREEN));
                                        }
                                        if (n.equals("Purple")) {
                                            if (shapeMap.get(c)) c.setMaterial(new PhongMaterial(Color.PURPLE));
                                        }
                                        if (n.equals("White")) {
                                            if (shapeMap.get(c)) c.setMaterial(new PhongMaterial(Color.GHOSTWHITE));
                                        }
                                    }));
                                });
                                primaryStage.setScene(myScene);
                            }
                        }
                        catch(Exception e2)
                        {
                            Alert error2 = new Alert(Alert.AlertType.ERROR, "Only submit integer values please.\n i.e. (1,2,3,...)");
                            Optional<ButtonType> choice = error2.showAndWait();
                            if (choice.isPresent() && choice.get() == ButtonType.OK)
                                error2.close();
                        }

                    }
                    if(newString.equals(("Sphere")))
                    {
                        try{
                            Double j1 = Double.parseDouble(locationX.getText());
                            Double j2 = Double.parseDouble(locationY.getText());
                            Double v = Double.parseDouble(radius.getText());
                            if(v <= 0){
                                Alert error1 = new Alert(Alert.AlertType.ERROR, "The radius needs to be positive.");
                                Optional<ButtonType> choice = error1.showAndWait();
                                if (choice.isPresent() && choice.get() == ButtonType.OK)
                                    error1.close();
                            }
                            if(j1 <- 20 || j2 <- 20 || j1 > 20 || j2 > 20) {
                                Alert error1 = new Alert(Alert.AlertType.ERROR, "Both x and y location has to be between -20 and 20.");
                                Optional<ButtonType> choice = error1.showAndWait();
                                if (choice.isPresent() && choice.get() == ButtonType.OK)
                                    error1.close();
                            }
                            else
                            {
                                Sphere s = new Sphere(v);
                                s.setTranslateX(j1);
                                s.setTranslateY(j2);
                                shapesGroup.getChildren().add(s);
                                shapeMap.put(s, false);
                                s.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                                    xSlider.setValue(180);
                                    ySlider.setValue(180);
                                    xT.setValue(0);
                                    yT.setValue(0);
                                    zT.setValue(0);
                                    shapeColor.valueProperty().set("");
                                    for (Map.Entry<Shape3D, Boolean> ent : shapeMap.entrySet()) {
                                        if (ent.getKey() != s) {
                                            ent.setValue(false);
                                        } else {
                                            ent.setValue(true);
                                        }
                                    }
                                    xSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double xRotate = (xSlider.getValue());
                                        if (shapeMap.get(s)) s.getTransforms().add(new Rotate(xRotate, Rotate.X_AXIS));
                                    }));
                                    ySlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double xRotate = (ySlider.getValue());
                                        if (shapeMap.get(s)) s.getTransforms().add(new Rotate(xRotate, Rotate.Y_AXIS));
                                    }));

                                    xT.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double xTran = (xT.getValue());
                                        if(shapeMap.get(s)){
                                            s.getTransforms().addAll(new Translate(xTran, yT.getValue(), zT.getValue()));
                                        }
                                    }));
                                    yT.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double yTran = (yT.getValue());
                                        if(shapeMap.get(s)){
                                            s.getTransforms().addAll(new Translate(xT.getValue(), yTran, zT.getValue()));
                                        }
                                    }));
                                    zT.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double zTran = (zT.getValue());
                                        if(shapeMap.get(s)){
                                            s.getTransforms().addAll(new Translate(xT.getValue(), yT.getValue(), zTran));
                                        }
                                    }));
                                    sX.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double xSc = (sX.getValue()/100);
                                        if(shapeMap.get(s)){
                                            s.setScaleX(xSc);
                                        }
                                    }));
                                    sY.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double ySc = (sY.getValue()/100);
                                        if(shapeMap.get(s)){
                                            s.setScaleY(ySc);
                                        }
                                    }));
                                    sZ.valueProperty().addListener(((observable, oldValue, newValue) -> {
                                        double zSc = (sZ.getValue()/100);
                                        if(shapeMap.get(s)){
                                            s.setScaleZ(zSc);
                                        }
                                    }));
                                    shapeColor.getSelectionModel().selectedItemProperty().addListener(((st, o, n) -> {
                                        if (n.equals("Red")) {
                                            if (shapeMap.get(s)) s.setMaterial(new PhongMaterial(Color.RED));
                                        }
                                        if (n.equals("Blue")) {
                                            if (shapeMap.get(s)) s.setMaterial(new PhongMaterial(Color.BLUE));
                                        }
                                        if (n.equals("Green")) {
                                            if (shapeMap.get(s)) s.setMaterial(new PhongMaterial(Color.GREEN));
                                        }
                                        if (n.equals("Purple")) {
                                            if (shapeMap.get(s)) s.setMaterial(new PhongMaterial(Color.PURPLE));
                                        }
                                        if (n.equals("White")) {
                                            if (shapeMap.get(s)) s.setMaterial(new PhongMaterial(Color.GHOSTWHITE));
                                        }
                                    }));
                                });
                                primaryStage.setScene(myScene);
                            }
                        }
                        catch(Exception e3)
                        {
                            Alert error2 = new Alert(Alert.AlertType.ERROR, "Only submit integer values please.\n i.e. (1,2,3,...)");
                            Optional<ButtonType> choice = error2.showAndWait();
                            if (choice.isPresent() && choice.get() == ButtonType.OK)
                                error2.close();
                        }
                    }
                });
            }));
            bPane.setCenter(gPane);
            primaryStage.setScene(shapeScene);
        });

        clearAll.setOnAction(actionEvent -> {
            shapesGroup.getChildren().clear();
        });

        HBox buttons = new HBox(35, addShape, clearAll);
        buttons.setPadding(new Insets(10));
        buttons.setAlignment(Pos. CENTER);

        VBox screen = new VBox(15);
        screen.getChildren().addAll(shapesSub, buttons);

        HBox splitScreen = new HBox(10);
        splitScreen.getChildren().addAll(screen, controls);

        rootNode.getChildren().addAll(splitScreen);
        bp.setCenter(rootNode);

        primaryStage.setTitle("Project 4");
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    private class TextFieldListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> source, String oldValue, String newValue){
            String locaX = locationX.getText();
            String locaY = locationY.getText();
            String rad = radius.getText();
            String hght = height.getText();
            String lngth = length.getText();
            String wdth = width.getText();
        }
    };

    public static void main(String[] args) {
        launch(args);
    }
}
