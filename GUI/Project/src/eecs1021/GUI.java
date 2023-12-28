//Ali Shandhor - 218932178 - EECS1021 - Prof. James Smith
//Interactive GUI Interface for Light Sensor Readings with Arduino Board
//Resouces:
//Note: To test my project in IntelliJ IDEA, ensure you have JavaFX set up. Instructions for configuring JavaFX can be found on the JavaFX website: https://openjfx.io/
package com.example.demo;
//Javafx library
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Arduino - firmata4j Libraries, Watch this video to download it: https://www.youtube.com/watch?v=RibLS6Ly9Uw by Prof. James Smith
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.ssd1306.SSD1306;
import org.firmata4j.I2CDevice;
import org.firmata4j.Pin;
import java.io.IOException;
import java.util.Timer;

public class GUI extends ButtonListner1 {
    Button button1, button2, button3, button4, button5, button6;
    static final int D2 = 2; // light
    static final int D4 = 4; //LED
    static final int A0 = 14; //Light sensor

    static Pin light, LED, LightSensor;
    Stage windows;
    Scene scene1, scene3,  scene2;

    Label label_P;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        windows = primaryStage;
        var Board_Ali = new FirmataDevice("COM3"); // My Port
        //The Board
        Board_Ali.start(); // to make the board starts
        Board_Ali.ensureInitializationIsDone(); //to initialise the board
        //LED
        LED = Board_Ali.getPin(D4);
        LED.setMode(Pin.Mode.OUTPUT);
        //Screen
        I2CDevice i2cObject = Board_Ali.getI2CDevice((byte) 0x3C); // Use 0x3C for the Grove OLED
        SSD1306 OlED = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64);
        //Light
        light = Board_Ali.getPin(D2);  // light
        light.setMode(Pin.Mode.OUTPUT);
        //Light Sensor
        LightSensor = Board_Ali.getPin(A0);
        LightSensor.setMode(Pin.Mode.ANALOG);



        //Button 1
        Label label1 = new Label("Welcome to My Project!");
        button1 = new Button("Dear user, click here to go the next page");
        button1.setOnAction(e -> windows.setScene(scene2));

        //Layout 1 - childrent are lai out in vectrical column
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1);
        scene1 = new Scene(layout1, 500, 500);

        //button 2
        button2 = new Button("Go back to the main page");
        button2.setOnAction(e -> windows.setScene(scene1));

        //button 3 - LED
        button3 = new Button("LED");
        button3.setOnAction(e -> {
            try {
                LED.setValue(1);
                Thread.sleep(1000);
                LED.setValue(0);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } );



        //Button 4- Screen
        button4 = new Button("OLED screen");
        button4.setOnAction(e -> {
            OlED.getCanvas().clear();
            OlED.getCanvas().drawString(0,0,"Ali Shandhor");
            OlED.display();
            OlED.clear();


        });

        button5 = new Button("External Light");
        button5.setOnAction(e -> {
            OlED.getCanvas().clear();
            try {
                light.setValue(1);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            try {
                light.setValue(0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //Button 6 - light
        button6 = new Button(" Start the application (Light Security System)");
        button6.setOnAction(e -> {
            OlED.getCanvas().clear();
            var task1 = new ButtonListner1(LightSensor, OlED, light, LED); // to define  a TimerTask object with  my variables
            new Timer().schedule(task1, 0, 1000);// the timer

        });

        //BUtton 6
        //layout 2
        VBox layout_Buttons = new VBox(20);
        layout_Buttons.getChildren().addAll(button2,button3, button4, button5 ,button6);
        scene2 = new Scene(layout_Buttons, 500, 500);
        windows.setScene(scene1);
        windows.setTitle("Project");
        windows.show();


    }

}
