package hu.bme.aut.javaweb.forum.model;

import java.util.Date;

public class Answer {
    private int id;
    private int userId;
    private int questionId;
    private Date time;
    private String text;

    public Answer(int id, int userId, int questionId, Date time, String text) {
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
        this.time = time;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
