package model;

import java.time.LocalDateTime;
import java.util.Date;

public class Post {
    private Integer id;
    private String authorId;
    private String description;
    private String photoUrl;
    private Date creationDate;

    public Post(Integer id, String authorId, String description, String photoUrl, Date creationDate) {
        this.id = id;
        this.authorId = authorId;
        this.description = description;
        this.photoUrl = photoUrl;
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Date getCreationDate() {
        return creationDate;
    }


}
