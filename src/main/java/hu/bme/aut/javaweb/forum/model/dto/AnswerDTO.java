package hu.bme.aut.javaweb.forum.model.dto;

public class AnswerDTO {
    private Long id;
    private Long questionId;
    private String text;
    private Long userId;

    public AnswerDTO(Long id, Long questionId, String text, Long userId) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
        this.userId = userId;
    }

    public Long getId() { return id; }

    public Long getQuestionId() {
        return questionId;
    }

    public String getText() {
        return text;
    }

    public Long getUserId() {
        return userId;
    }
}
