package com.example.demo;
//library
import org.firmata4j.ssd1306.MonochromeCanvas;
import org.firmata4j.ssd1306.SSD1306;
import java.io.IOException;
import org.firmata4j.Pin;
import java.util.TimerTask;
//Timer Task Class
public class ButtonListner1 extends TimerTask {
    private int duration;

    private final SSD1306 OLED_Screen;
    private final Pin lightSensor;
    private final Pin light;
    private final Pin LED;

    public ButtonListner1(Pin lightSensor, SSD1306 OLED_Screen, Pin light, Pin LED) {
        this.lightSensor = lightSensor;
        this.OLED_Screen = OLED_Screen;
        this.light = light;
        this.LED = LED;
    }

    @Override
    public void run() {
        OLED_Screen.getCanvas().clear();
        //while loop to make the water plant system atraumatic system!
        while (true) {
            var lightSensor_Value = lightSensor.getValue(); // it gets values  From moisture sensor
            var lightSensor_String = Long.toString(lightSensor_Value);// to convert  values from light sensor (int) to string; so they can be shown on OLED screen or IntelliJ Console

            float max = 1023F; //Max range of moisture sensor (0 - 1023) as float point

            var i = (int) ((lightSensor_Value / max) * 400); // to reduce length of horizontal line, so it can fit the screen and move proportionally to value getting form moisture sensor
            int dark_value = 10;

            // conditional  statements
            if (lightSensor_Value >= dark_value) {
                // to print on the Intellj Console
                System.out.println("Light Readings: " + lightSensor_String + " light is clear!");
                // to show your strings on OLED screen
                OLED_Screen.getCanvas().drawString(0, 0, "Light Readings: " + lightSensor_String);
                OLED_Screen.getCanvas().setTextsize(1);
                // to draw the Horizontal Line
                OLED_Screen.getCanvas().drawHorizontalLine(0, 40, 120, MonochromeCanvas.Color.DARK);
                if (i != 0) OLED_Screen.getCanvas().drawHorizontalLine(0, 28, (int) i, MonochromeCanvas.Color.BRIGHT);

                OLED_Screen.display();
                try {
                    light.setValue(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    LED.setValue(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                OLED_Screen.clear();
            }

            else if (lightSensor_Value == dark_value) {
                // to print on the Intellj Console
                System.out.println("Light readings  : " + lightSensor_String + " - It's Dark! security lights works now!");
                // to show your strings on OLED screen
                OLED_Screen.getCanvas().drawString(0, 0, "Value: " + lightSensor_String);
                OLED_Screen.getCanvas().setTextsize(1);
                // to draw the Horizontal Line
                OLED_Screen.getCanvas().drawHorizontalLine(0, 40, 120, MonochromeCanvas.Color.DARK);
                if (i != 0) OLED_Screen.getCanvas().drawHorizontalLine(0, 28, (int) i, MonochromeCanvas.Color.BRIGHT);
                // to display on OlED Screen
                OLED_Screen.display();
                try {
                    LED.setValue(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    light.setValue(1);
                    Thread.sleep(1000);
                    light.setValue(0);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                OLED_Screen.clear();
            }

            else {
                // to print on the Intellj Console
                System.out.println("Light reading  : " + lightSensor_String + " - It's Dark! security lights works now!");
                // to show your strings on OLED screen
                OLED_Screen.getCanvas().drawString(0, 0, "Value: " + lightSensor_String);
                OLED_Screen.getCanvas().setTextsize(1);
                // to draw the Horizontal Line
                OLED_Screen.getCanvas().drawHorizontalLine(0, 40, 120, MonochromeCanvas.Color.DARK);
                if (i != 0) OLED_Screen.getCanvas().drawHorizontalLine(0, 28, (int) i, MonochromeCanvas.Color.BRIGHT);
                // to display on OlED Screen
                OLED_Screen.display();
                // to turn the pump On .setValue(1)
                try {
                    LED.setValue(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    light.setValue(1);
                    Thread.sleep(1000);
                    light.setValue(0);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                OLED_Screen.clear();
            }

        }
    }
}