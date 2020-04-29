package springBoardProject.domain;

public class User {
    private String id;
    private String password;

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {}

    public User(String id) {
        this.id = id;
    }

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

}
