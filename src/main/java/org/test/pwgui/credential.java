package org.test.pwgui;

import javafx.beans.property.SimpleStringProperty;

public class credential {
    private SimpleStringProperty website;
    private SimpleStringProperty login;
    private SimpleStringProperty password;



    public credential(String website, String login, String password) {
        this.website = new SimpleStringProperty(website);
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
    }

    public String getWebsite() {
        return website.get();
    }
    public void setWebsite(String website) {
        this.website = new SimpleStringProperty(website);
    }
    public String getLogin() {
        return login.get();
    }
    public void setLogin(String login) {
        this.login = new SimpleStringProperty(login);
    }
    public String getPassword() {
        return password.get();
    }
    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    }
}
