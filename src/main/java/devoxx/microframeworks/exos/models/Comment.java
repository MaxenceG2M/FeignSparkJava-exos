package devoxx.microframeworks.exos.models;

public class Comment {

    private String id;
    private String author;
    private String email;
    private String date;
    private String message;

    @Override
    public String toString() {
        return String.format("Comment{id='%s', author='%s', email='%s', date='%s'}", id, author, email, date);
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
