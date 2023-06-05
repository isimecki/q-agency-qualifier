package hr.igor.simecki.q_agencyqualifier.service;

import hr.igor.simecki.q_agencyqualifier.model.Email;
import hr.igor.simecki.q_agencyqualifier.model.EmailAddress;
import hr.igor.simecki.q_agencyqualifier.repository.EmailAddressRepository;
import hr.igor.simecki.q_agencyqualifier.repository.EmailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final EmailAddressRepository emailAddressRepository;

    @Autowired
    public EmailServiceImpl(EmailRepository emailRepository, EmailAddressRepository emailAddressRepository) {
        this.emailRepository = emailRepository;
        this.emailAddressRepository = emailAddressRepository;
    }

    @Override
    public List<Email> getAllEmails() {
        return StreamSupport.stream(emailRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<Email> fetchEmails(String query) {
        return emailRepository.queryForEmails(query);
    }

    @Override
    public Email saveEmail(Email email) {
        emailAddressRepository.findByAddress(email.getFrom().getAddress()).ifPresent(email::setFrom);
        replaceWithExistingAddresses(email.getTo());
        replaceWithExistingAddresses(email.getCc());
        return emailRepository.save(email);
    }

    private void replaceWithExistingAddresses(List<EmailAddress> addressList) {
        if (addressList == null) return;

        ArrayList<EmailAddress> addressListCopy = new ArrayList<>(addressList.size());
        addressListCopy.addAll(addressList);
        addressListCopy.forEach(emailAddress -> {
            emailAddressRepository.findByAddress(emailAddress.getAddress()).ifPresent(existingAddress -> {
                addressList.remove(emailAddress);
                addressList.add(existingAddress);
            });
        });
    }

    @Override
    public boolean deleteEmail(Long id) {
        emailRepository.findById(id).ifPresent(emailRepository::delete);
        return true;
    }

    @Override
    public Email getEmail(Long id) {
        return emailRepository.findById(id).orElseThrow(() -> new RuntimeException("Failed to fetch by id."));
    }
}
