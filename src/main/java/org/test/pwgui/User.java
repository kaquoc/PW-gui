package org.test.pwgui;

import org.test.pwgui.AES;
import org.test.pwgui.JSON_Reader;
import org.test.pwgui.JSON_Writer;

import java.io.*;
import java.util.ArrayList;

public class User {
    String master_password;
    String login;
    AES encrypt;
    JSON_Writer j_write;
    JSON_Reader j_read;
    File vault;

    public User(String login, String password){
        this.login = login;
        this.master_password = password;
        this.encrypt = new AES(login,password);
        this.j_write = new JSON_Writer("creds.json");
        this.j_read = new JSON_Reader();
        this.vault = new File("creds.json");

    }

    public String getLogin(){
        return login;
    }
    public String getPassword(){
        return master_password;
    }
    public void setLogin(String new_login){
        login = new_login;
    }
    public void setPassword(String new_password){
        master_password = new_password;
    }

    public User(){
        this.j_write = new JSON_Writer("creds.json");
        this.j_read = new JSON_Reader();
    }
    public static void main(String[] args){
        User user = new User();
        System.out.println(user.getPassFromFile());
    }
    public boolean checkUserExists() {
        if(this.login.equals(j_read.readMaster("master_creds.json"))){
            return true;
        }
        return false;
    }

    public boolean checkMasterEmpty() {
        String master_read = j_read.readMaster("master_creds.json");
        if(master_read.equals("null") || master_read.equals("")) {
            return true;
        }
        return false;
    }
    //get master password from master_creds
    public String getPassFromFile(){
        String read = (j_read.read_pass());
        return read;
    }
    public String getLoginFromFile(){
        return j_read.readMaster("master_creds.json");
    }
    //store login and master password into a file
    public boolean storeUser() {
        byte[] iv = encrypt.getByteIv();
        j_write.initANDstoreUser(this.login,this.master_password);
        return true;
    }
    public void removePassword(){
        j_write.removePass();
    }
    //display login_databse
    public void readUser(){
        String read = (j_read.readMaster("master_creds.json"));
        System.out.println(read);
    }

    private String getFileString(){
        return "creds";
    }

    public void add_vault(String website, String web_login, String web_password){
        j_write.addEntry(website,web_login,web_password);
        try {
            j_write.export(this.getFileString());
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public void remove_vault(String website){
        j_write.remove(website);
        try {
            j_write.export(this.getFileString());
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public boolean updateMasterPassword(String new_password){
        this.master_password = new_password;
        return encrypt.updatePassword(new_password);
    }
    //return an ArrayList of all website credentials, with each credential holding a String[] of website, login, password
    public ArrayList readVault() {
        System.out.println();
        ArrayList creds_arr;
        try {
            creds_arr = j_read.read("creds.json");
        } catch(Exception e) {
            System.out.println("File not found!");
            return null;
        }
        return creds_arr;
    }
    public String displayUserVault(String userfile) {
        ArrayList creds_arr;
        try {
            creds_arr = j_read.read(userfile);
        } catch(Exception e) {
            return "File not found.";
        }
        String[] cur_creds;
        String entry = "";
        int cred_size = 0;
        if (creds_arr == null){
            cred_size = 0;
            return "VAULT IS EMPTY";
        }else{
            cred_size = creds_arr.size();
        }
        for(int i = 0;i < cred_size;i++){
            cur_creds = (String[]) creds_arr.get(i);
            entry = entry + ("Entry " + (i+1) + ":" + "\n");
            entry = entry + ("Website: " + cur_creds[0] + "\n");
            entry = entry + ("Username: " + cur_creds[1] + "\n");
            entry = entry + ("Password: " + cur_creds[2]) + "\n";
            entry = entry + ("-------------------------------" + "\n");
        }
        return entry;
    }

    //method for updating vault vales
    public void updateVault(String website, int choice, String new_value)  {
        if (j_write.editEntry(website,choice,new_value)){
            //System.out.println(website + " " + choice + " " + new_value);
            System.out.println("update success");
        }else System.out.println("update fail, cannot find website");
    }



    public boolean isEncrypted() {
        ArrayList ar;
        try {
            ar = j_read.read("creds.json");
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public boolean encrypt_vault(){
        if (encrypt.encrypt(this.vault)){
            return true;
        }else{
            return false;
        }
    }
    //perform password checking on the frontend side

    public boolean decrypt_vault(){
        if (encrypt.decrypt(this.vault)){
            return true;
        }else{
            return false;
        }
    }
}
