/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.proj.demo;

import java.util.Locale;

import ds.proj.gui.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author NZXT-PC
 */
public class GuiDemoExocution extends Application {

    public static void main(String[] args) {

        GuiDemoExocution.launch(args);
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus 
        setUserAgentStylesheet(STYLESHEET_MODENA);    //Nauja FX išvaizda
        MainWindow.createAndShowGui(primaryStage);
    }
}
