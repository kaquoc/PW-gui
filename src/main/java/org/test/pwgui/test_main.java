package org.test.pwgui;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Stack;

public class test_main extends Application {

    Button button;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Title.fxml"));
        primaryStage.setTitle("Password Manager");
        Scene scene = new Scene(root, 600,400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @Override
    //pressing close program will automatically encrypt the file and delete the password in master_creds.
    public void stop(){
        User user = new User();
        String password = user.getPassFromFile();
        String login = user.getLoginFromFile();
        System.out.println(login + " " + password);
        User user2 = new User(login, password);
        user2.encrypt_vault();
        user2.removePassword();
        System.out.println("Stage is closing");
        // Save file
    }

    public static void main(String[] args) {
        launch(args);
    }


}