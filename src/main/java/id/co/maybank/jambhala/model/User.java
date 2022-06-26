package id.co.maybank.jambhala.model;

import java.util.List;

public class User {

    private String userName;
    private List<Account> accounts;

    public User(String userName) {
       this.userName = userName;
    }

    public void setAccounts(List<id.co.maybank.jambhala.model.Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
