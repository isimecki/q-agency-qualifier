package hr.igor.simecki.q_agencyqualifier.service;

import hr.igor.simecki.q_agencyqualifier.model.Email;

import java.util.List;

public interface EmailService {
    List<Email> getAllEmails();

    List<Email> fetchEmails(String from);

    Email saveEmail(Email email);

    boolean deleteEmail(Long id);

    Email getEmail(Long id);

}
