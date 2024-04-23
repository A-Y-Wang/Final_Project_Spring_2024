package LibraryDatabase;

public class LibraryUsers {
    private String username;
    private String password;

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
}
