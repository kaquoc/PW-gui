package org.test.pwgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.json.simple.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {
    @FXML
    private TableView<credential> table;
    @FXML private TableColumn<credential, String> website_col;
    @FXML private TableColumn<credential, String> login_col;
    @FXML private TableColumn<credential, String> password_col;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //setting up our columns in the table
        website_col.setCellValueFactory(new PropertyValueFactory<credential, String>("website"));
        login_col.setCellValueFactory(new PropertyValueFactory<credential, String>("login"));
        password_col.setCellValueFactory(new PropertyValueFactory<credential, String>("password"));
        //load some data
        table.setItems(getData());

        //update table to allow for editable columns
        table.setEditable(true);
        website_col.setCellFactory(TextFieldTableCell.forTableColumn());
        login_col.setCellFactory(TextFieldTableCell.forTableColumn());
        password_col.setCellFactory(TextFieldTableCell.forTableColumn());

    }

    //return an ObservableList of credentials objects
    //ObservableList is similar to ArrayList, but with some behavior suitable for GUI
    //adding objects into the table, populating the table with credentials from creds.json using USER class
    public ObservableList<credential> getData(){
        ObservableList<credential> cred= FXCollections.observableArrayList();
        User user = new User();
        ArrayList cred_list = user.readVault();  // a list of all the websites available in the vault.
        for (int i = 0 ;i < cred_list.size();i++){
            String[] web_cred = (String[]) cred_list.get(i);//each website has 3 credentials: website, login, password
            cred.add(new credential(web_cred[0],web_cred[1],web_cred[2]));
        }

        //cred.add(new credential("google.com","ka123","password123"));

        return cred;
    }

    //method for allowing user to double click on a cell and update the website

    public void editWebsiteCell(TableColumn.CellEditEvent edit_event){
        credential selected = table.getSelectionModel().getSelectedItem();
        String website_name = selected.getWebsite();


        selected.setWebsite(edit_event.getNewValue().toString());
        String new_value = selected.getWebsite();

        updateVaultCell(website_name,0,new_value);


    }
    public void editLoginCell(TableColumn.CellEditEvent edit_event){
        credential selected = table.getSelectionModel().getSelectedItem();
        String website_name = selected.getWebsite();
        selected.setLogin(edit_event.getNewValue().toString());
        String new_value = selected.getLogin();
        updateVaultCell(website_name,1,new_value);
    }
    public void editPasswordCell(TableColumn.CellEditEvent edit_event){
        credential selected = table.getSelectionModel().getSelectedItem();
        String website_name = selected.getWebsite();
        selected.setPassword(edit_event.getNewValue().toString());
        String new_value = selected.getPassword();
        updateVaultCell(website_name,2,new_value);
    }
    //update credential value in vault
    private void updateVaultCell(String website,int choice,String new_value){
        User user = new User();
        user.updateVault(website,choice,new_value);
    }

    @FXML
    TextField website_add;
    @FXML TextField login_add;
    @FXML TextField password_add;
    @FXML TextField add_status;
    //@FXML Button add;
    public void add_entry(){
        String website = website_add.getText();
        String login = login_add.getText();
        String password = password_add.getText();
        if (website.equals("") || login.equals("") || password.equals("")){
            System.out.println("entry empty cannot add");
            add_status.setText("entry cannot be empty");
        }else{
            User user = new User();
            user.add_vault(website,login,password);
            //load some data
            table.setItems(getData());
        }

    }



}
