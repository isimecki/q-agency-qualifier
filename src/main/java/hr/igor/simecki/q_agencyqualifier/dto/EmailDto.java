package hr.igor.simecki.q_agencyqualifier.dto;

import hr.igor.simecki.q_agencyqualifier.model.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.Arrays;
import java.util.List;

public class EmailDto {

    @NotEmpty(message = "Field must not be empty")
    private String subject;

    @NotEmpty(message = "Field must not be empty")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "invalid email pattern")
    private String from;

    @NotEmpty(message = "Field must not be empty")
    private String content;

    @NotEmpty(message = "Field must not be empty")
    @Pattern(regexp = "^(([\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4})\\,*)+$", message = "invalid email pattern")
    private String to;

    @Pattern(regexp = "^(([\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4})\\,*)+$", message = "invalid email pattern")
    private String cc;

    @NotBlank(message = "Required field")
    private String importance;

    private List<String> attachment;

    public Email toEmail() {
        return Email.EmailBuilder.create()
                .setAttachments(attachment)
                .setTitle(subject)
                .setCc(cc != null ? Arrays.asList(cc.split(",")) : null)
                .setTo(Arrays.asList(to.split(",")))
                .setContent(content)
                .setFrom(from)
                .setImportance(importance)
                .build();
    }

    public static EmailDto fromEmail(Email email) {
        EmailDto result = new EmailDto();
        result.setFrom(email.getFrom().getAddress());
        result.setSubject(email.getTitle());
        result.setContent(email.getContent().getContent());
        return result;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public List<String> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<String> attachment) {
        this.attachment = attachment;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }
}
