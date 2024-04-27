package LibraryDatabase;

import java.io.Serializable;

public class LibraryUsers implements Serializable {
    public String username;
    public String password;

    public LibraryUsers(){}

    public LibraryUsers(String n, String p){
        this.username = n;
        this.password = p;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    @Override
    public String toString(){
        return "Username: "+this.username + "\n"+ "Password: "+this.password;
    }
}
