package hr.igor.simecki.q_agencyqualifier.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hr.igor.simecki.q_agencyqualifier.model.converters.EmailContentEncryptor;
import jakarta.persistence.*;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "email")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "content_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private EmailContent content;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "email_from_id")
    private EmailAddress from;

    @Column(name = "title")
    private String title;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "email_email_to", joinColumns = @JoinColumn(name = "email_id"), inverseJoinColumns = @JoinColumn(name = "email_to_id"))
    private List<EmailAddress> to;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "email_email_cc", joinColumns = @JoinColumn(name = "email_id"), inverseJoinColumns = @JoinColumn(name = "email_cc_id"))
    private List<EmailAddress> cc;

    @OneToMany(mappedBy = "email", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private List<EmailAttachment> attachments;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "importance")
    private Importance importance;


    public static class EmailBuilder {

        private String title;
        private EmailContent content;
        private List<EmailAddress> to;
        private List<EmailAddress> cc;
        private EmailAddress from;
        private List<EmailAttachment> attachments;

        private Importance importance;

        public static EmailBuilder create() {
            return new EmailBuilder();
        }

        public Email build() {
            Email result = new Email();
            result.setTitle(title);
            result.setContent(content);
            result.setTo(to);
            result.setCc(cc);
            result.setFrom(from);
            result.setAttachments(attachments);
            result.setImportance(importance);
            return result;
        }

        public EmailBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public EmailBuilder setContent(String content) {
            this.content = new EmailContent(content.replaceAll("(?i)<script.*?>.*?</script.*?>", "") // case 1
                    .replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "") // case 2
                    .replaceAll("(?i)(<.?\\s+)on.?(>.*?)", "$1$2"));
            return this;
        }

        public EmailBuilder setTo(List<String> to) {
            if (to != null) {
                this.to = to.stream().map(EmailAddress::new).collect(Collectors.toList());
            }
            return this;
        }

        public EmailBuilder setCc(List<String> cc) {
            if (cc != null) {
                this.cc = cc.stream().map(EmailAddress::new).collect(Collectors.toList());
            }
            return this;
        }

        public EmailBuilder setFrom(String from) {
            this.from = new EmailAddress(from);
            return this;
        }

        public EmailBuilder setAttachments(List<String> attachments) {
            if (attachments != null) {
                this.attachments = attachments.stream().map(EmailAttachment::new).collect(Collectors.toList());
            }
            return this;
        }

        public EmailBuilder setImportance(String importance) {
            if (StringUtils.hasText(importance)) {
                this.importance = Importance.valueOf(importance);
            }
            return this;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmailContent getContent() {
        return content;
    }

    public void setContent(EmailContent content) {
        this.content = content;
    }

    public EmailAddress getFrom() {
        return from;
    }

    public void setFrom(EmailAddress from) {
        this.from = from;
    }

    public List<EmailAddress> getTo() {
        return to;
    }

    public void setTo(List<EmailAddress> to) {
        this.to = to;
    }

    public List<EmailAddress> getCc() {
        return cc;
    }

    public void setCc(List<EmailAddress> cc) {
        this.cc = cc;
    }

    public List<EmailAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<EmailAttachment> attachments) {
        this.attachments = attachments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", content=" + content +
                ", from=" + from +
                ", to=" + to +
                ", cc=" + cc +
                ", attachments=" + attachments +
                '}';
    }
}
