package org.test.pwgui;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.SimpleTimeZone;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class test_controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    TextField username_login;
    String username;

    @FXML
    TextField password_login;
    String masterPassword;

    @FXML
    TextField display;
    @FXML
    TextField random_pass_display;

    @FXML
    Button generate_random;


    public void login(ActionEvent event) throws IOException {

        username = username_login.getText();
        masterPassword = password_login.getText();

        User user = new User(username,masterPassword);
        //case 1: first time login, new user
        if (user.checkMasterEmpty()==true){
            display.setText("no account found! Please register");
        }else if ((user.checkMasterEmpty() == false) &&  (user.checkUserExists()==false)){
            display.setText("wrong login name");
        }else if ((user.checkMasterEmpty() == false) &&  (user.checkUserExists()==true)){
            //check if password is correct, for now some display
            boolean decrypt = user.decrypt_vault();
            if (decrypt){
                System.out.println("decrypted success");
                switchtoVault(event);
                user.storeUser();
            }else{
                display.setText("wrong password");
            }
        }
    }

    public void register(ActionEvent event) throws IOException {
        username = username_login.getText();
        masterPassword = password_login.getText();

        User user = new User(username,masterPassword);
        if (user.checkUserExists()){
            display.setText("User already existed");
        }else if(user.checkMasterEmpty()==false){
            display.setText("User account availiable");
        }else{
            Parent root = FXMLLoader.load(getClass().getResource("Generate PW.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }
    public String passwordGenerator(int len, boolean number, boolean upper, boolean special){
        Random rand = new Random();

        int passwordLengthInt = len;
        String randPassword = "";

        for (int i=0; i < passwordLengthInt; i++){
            if (number==true && upper==false && special==false) {
                if (rand.nextInt(2) == 0) {
                    randPassword = randPassword + (char) (rand.nextInt(26) + 97);
                }else{
                    randPassword = randPassword + (char) (rand.nextInt(10) + 48);
                }
            }
            else if (number==false && upper==true && special==false) {
                if (rand.nextInt(2) == 0) {
                    randPassword = randPassword + (char) (rand.nextInt(26) + 97);
                }else{
                    randPassword = randPassword + (char) (rand.nextInt(26) + 65);
                }
            }
            else if (number==false && upper==false && special==true) {
                if (rand.nextInt(2) == 0) {
                    randPassword = randPassword + (char) (rand.nextInt(26) + 97);
                }else{
                    randPassword = randPassword + (char) (rand.nextInt(4) + 35);
                }
            }
            else if (number==true && upper==true && special==false) {
                if (rand.nextInt(3) == 0) {
                    randPassword = randPassword + (char) (rand.nextInt(26) + 97);
                }else if (rand.nextInt(3) == 1) {
                    randPassword = randPassword + (char) (rand.nextInt(26) + 65);
                }else{
                    randPassword = randPassword + (char) (rand.nextInt(10) + 48);
                }
            }
            else if (number==true && upper==true && special==true) {
                if (rand.nextInt(4) == 0) {
                    randPassword = randPassword + (char) (rand.nextInt(26) + 97);
                }else if (rand.nextInt(4) == 1) {
                    randPassword = randPassword + (char) (rand.nextInt(26) + 65);
                }else if (rand.nextInt(4) == 3) {
                    randPassword = randPassword + (char) (rand.nextInt(4 ) + 35);
                }else{
                    randPassword = randPassword + (char) (rand.nextInt(10) + 48);
                }
            }
            else if (number==true && upper==false && special==true) {
                if (rand.nextInt(3) == 0) {
                    randPassword = randPassword + (char) (rand.nextInt(26) + 97);
                }else if (rand.nextInt(3) == 1) {
                    randPassword = randPassword + (char) (rand.nextInt(4 ) + 35);
                }else{
                    randPassword = randPassword + (char) (rand.nextInt(10) + 48);
                }
            }
            else if (number==false && upper==true && special==true) {
                if (rand.nextInt(3) == 0) {
                    randPassword = randPassword + (char) (rand.nextInt(26) + 97);
                }else if (rand.nextInt(3) == 1) {
                    randPassword = randPassword + (char) (rand.nextInt(4 ) + 35);
                }else{
                    randPassword = randPassword + (char) (rand.nextInt(26) + 65);
                }
            }
            else{
                randPassword = randPassword + (char) (rand.nextInt(26) + 97);
            }
        }
        return randPassword;
    }
    public void displayPassword(){

        String passwordLength = "6";
        int passwordLengthInt = Integer.parseInt(passwordLength);

        boolean numbersBool = true;
        boolean uppercaseLettersBool = true;
        boolean specialCharactersBool = true;

        String randPassword = passwordGenerator(passwordLengthInt,numbersBool,uppercaseLettersBool,specialCharactersBool);
        System.out.println(randPassword);
        random_pass_display.setText(randPassword);
    }
    @FXML
    TextField PW_username;
    public void register_new(ActionEvent event) throws IOException {
        String username = PW_username.getText();
        String password = random_pass_display.getText();

        User new_user = new User(username,password);
        new_user.storeUser();
        System.out.println("encrypting vault");
        if (new_user.encrypt_vault()) System.out.println("encrypt success");
        switchtoTitle(event);

    }
    //automatically encrypt the file and delete the password in master_creds.
    public void done(ActionEvent event){
        User user = new User();
        String password = user.getPassFromFile();
        String login = user.getLoginFromFile();
        System.out.println(login + " " + password);
        User user2 = new User(login, password);
        user2.encrypt_vault();
        user2.removePassword();
    }


    public void switchtoTitle(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Title.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchtoVault(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Vault.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//configure the table

}
