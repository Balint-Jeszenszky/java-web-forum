package hu.bme.aut.javaweb.forum.model.dto;

import java.util.Date;

public class QuestionDTO {
    private Long id;
    private Long userId;
    private Long categoryId;
    private String title;
    private String description;
    private Date time;

    public QuestionDTO(Long id, Long userId, Long categoryId, String title, String description, Date time) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getTime() {
        return time;
    }
}
