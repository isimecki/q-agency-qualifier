package hr.igor.simecki.q_agencyqualifier.model;

import hr.igor.simecki.q_agencyqualifier.model.converters.EmailContentEncryptor;
import jakarta.persistence.*;

@Entity(name = "email_content")
public class EmailContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
//    @Convert(converter = EmailContentEncryptor.class)
    private String content;

    public EmailContent(){

    }

    public EmailContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
