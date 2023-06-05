package hr.igor.simecki.q_agencyqualifier.service;

import hr.igor.simecki.q_agencyqualifier.model.Email;
import hr.igor.simecki.q_agencyqualifier.model.EmailAddress;
import hr.igor.simecki.q_agencyqualifier.repository.EmailAddressRepository;
import hr.igor.simecki.q_agencyqualifier.repository.EmailRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @InjectMocks
    private EmailServiceImpl emailService;
    @Mock
    private EmailRepository emailRepository;

    @Mock
    private EmailAddressRepository emailAddressRepository;

    @Test
    public void getAllEmails_returnsData() {
        ArrayList<Email> mockResult = new ArrayList<>();
        mockResult.add(new Email());
        when(emailRepository.findAll()).thenReturn(mockResult);
        assertFalse(emailService.getAllEmails().isEmpty());
    }

    @Test
    public void saveEmail_returnsData() {
        Email email = new Email();
        email.setFrom(new EmailAddress());
        email.setTo(new ArrayList<>());
        email.setCc(new ArrayList<>());
        when(emailRepository.save(any(Email.class))).thenReturn(email);
        assertNotNull(emailService.saveEmail(email));
    }

    @Test
    public void saveEmail_usesSavedAddresses() {
        Email email = new Email();
        EmailAddress from = new EmailAddress();
        from.setAddress("test@test.test");
        email.setFrom(from);
        email.setTo(new ArrayList<>());
        email.setCc(new ArrayList<>());
        EmailAddress savedAddress = new EmailAddress();
        savedAddress.setId(1L);
        when(emailAddressRepository.findByAddress(any())).thenReturn(Optional.of(savedAddress));
        when(emailRepository.save(any(Email.class))).thenReturn(email);
        assertEquals(1L, emailService.saveEmail(email).getFrom().getId());
    }

    @Test
    public void fetchEmails_returnsData() {
        ArrayList<Email> mockResult = new ArrayList<>();
        mockResult.add(new Email());
        when(emailRepository.queryForEmails(matches("test"))).thenReturn(mockResult);
        assertFalse(emailService.fetchEmails("test").isEmpty());
    }
}
