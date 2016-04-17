package devoxx.microframeworks.exos.models;

public class User {
    private String name;
    private String email;

    @Override
    public String toString() {
        return String.format("User{name='%s', email='%s'}", name, email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
