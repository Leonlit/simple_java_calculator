package calculator;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PopUpWindow {
    public static void showPopUp(String msg){
        Stage popUp = new Stage();
        StackPane layout= new StackPane();
        Label text = new Label(msg);
        layout.getChildren().add(text);

        Scene stageScene = new Scene(layout, 250, 100);
        popUp.setScene(stageScene);
        popUp.show();
    }
}
