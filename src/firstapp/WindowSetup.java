package firstapp;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;

public class WindowSetup extends Application {    
    public static void startApp(String args[]) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        TextField answer = new TextField();
      
        answer.setMinSize(280, 110);
        answer.setAlignment(Pos.CENTER_RIGHT);
        answer.setEditable(false);
        
        GridPane buttonPad = new GridPane();
        buttonPad.setMinSize(300, 430);
       
        //first row
        Button btnArr[][] = {
                {
                    new Button("+"),
                    new Button("-"),
                    new Button("x"),
                    new Button("/")
                },
                //second row
                {
                    new Button("7"),
                    new Button("8"),
                    new Button("9"),
                    new Button("(")
                },
                //third row
                {
                    new Button("4"),
                    new Button("5"),
                    new Button("6"),
                    new Button(")")
                },
                //fourth row
                {
                    new Button("1"),
                    new Button("2"),
                    new Button("3"),
                    new Button("del")
                },
                //fiths row 
                {
                    new Button("0"),
                    new Button("."),
                    new Button("CE"),
                    new Button("=")
                }
    };
        
        for (int row = 0; row < 5; row++) {
            for (int column = 0;column < 4;column++) {
                Button currBtn = btnArr[row][column];
                buttonPad.add(currBtn, column, row);
                
                currBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String temp = ((Button)event.getSource()).getText();
                        String answerBox = answer.getText();
                        String finResult = fixInput(answerBox, temp);
                        
                        answer.setText(finResult);
                    }
                });
                currBtn.setMinSize(75, 82);
            }
        }
        
        VBox root = new VBox();
        VBox.setMargin(buttonPad, new Insets(5,5,0,5));
        VBox.setMargin(answer, new Insets(5,0,0,0));
        root.getChildren().add(answer);
        root.getChildren().add(buttonPad);
        
        Scene scene = new Scene(root, 300, 530);
        scene.getStylesheets().add("index.css");
        
        primaryStage.setTitle("calculator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public String fixInput (String initial, String newString) {
        String result = "";
        int initialLength = initial.length();
        String tempInitial = initial;
        String tempNew = newString;
        
        if (newString == "del") {
            return backspace(initial);
        }else if (newString == "CE") {
            return "";
        }else if (newString == "=") {
            ParseArithmetic calculate = new ParseArithmetic(initial);
            Double value = calculate.getResult();
            if (value != Double.NaN) {
                if ((value == Math.floor(value)) && !Double.isInfinite(value)) {
                    int temp2 = (int)Math.floor(value);
                    return Integer.toString(temp2);
                }
                String temp = Double.toString(value);
                int pointerIndex = temp.indexOf('.');
                if(temp.length() - pointerIndex > 2) {
                    return temp.substring(0, pointerIndex + 3);
                }else 
                    return temp.substring(0, pointerIndex + 2);
            }
        }
        
        char newChar = tempNew.charAt(0);
        
        if (initialLength == 0 && newChar == '.') {
                tempNew = "0" + newChar;
            }
        
        if (initialLength > 0) {
            char initialEnd = tempInitial.charAt(initialLength - 1);
            if (initialLength > 2 && !Character.isDigit(initial.charAt(initialLength - 1)) && '.' == newChar) {
                tempNew = "0" + newChar;
            }
            if (!((initialEnd == '.' || Character.isDigit(initialEnd)) && (Character.isDigit(newChar) || newChar == '.'))) {
                tempNew = " " + tempNew;
            }
        }
        
        result = tempInitial + tempNew;
        
        return result;
    }
    
    public String backspace (String result) {
        if (result != null && result.length() > 0) {
                if (result.length() > 1 && result.charAt(result.length() - 2) == ' ') {
                    result = result.substring(0, result.length() - 2);
            }else {
                result = result.substring(0, result.length() - 1);
            }
    }
    return result;
    }
}