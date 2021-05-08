package hu.bme.aut.javaweb.forum.model.dto;

public class AnswerDTO {
    Long questionId;
    String text;
    Long userId;

    public AnswerDTO(Long questionId, String text, Long userId) {
        this.questionId = questionId;
        this.text = text;
        this.userId = userId;
    }

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
