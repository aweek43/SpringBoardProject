package springBoardProject.domain;

public class Post {
    private Integer id;
    private User author;
    private String title;
    private String content;
    private String generatedDate;
    private String revisedDate;

    public User getAuthor() {
        return author;
    }

    public Integer getId() {
        return id;
    }

    public String getRevisedDate() {
        return revisedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getGeneratedDate() {
        return generatedDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setGeneratedDate(String generatedDate) {
        this.generatedDate = generatedDate;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRevisedDate(String revisedDate) {
        this.revisedDate = revisedDate;
    }

    public Post() {}

    public Post(int id) {
        this.id = id;
    }

    public Post(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }
}
