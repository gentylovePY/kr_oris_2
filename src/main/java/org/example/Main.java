package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class Main extends Application {

    private final Weather weatherRequest = new Weather();
    VBox vBox = new VBox();
    HBox hBox = new HBox();
    TextField textField = new TextField();
    TextArea textArea = new TextArea();
    Button search = new Button("Search city");

    @Override
    public void start(Stage stage) {
        vBox.setId("root");
        hBox.setId("top");
        Label result = new Label();
        search.setOnAction(e -> {
            if (!textField.getText().isEmpty()) {
                try {
                    Map<String,String> map = weatherRequest.getRequestWeather(textField.getText());
                    textArea.appendText("city: " + map.get("name") + "\n" + "temp: " + map.get("temp") + "\n" + "temp max: " + map.get("temp_max") + "\n" + "temp min: " + map.get("temp_min") + "\n\n");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        HBox.setMargin(textField,new Insets(10, 0, 10, 15));
        HBox.setMargin(search, new Insets(10, 0, 10, 15));
        hBox.getChildren().addAll(textField,search);
        VBox.setMargin(result, new Insets(0, 0, 0, 15));
        vBox.getChildren().addAll(hBox,textArea);
        Scene scene = new Scene(vBox, 300, 300);
        stage.setScene(scene);
        stage.show();
    }
}
