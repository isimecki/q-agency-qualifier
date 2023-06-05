package hr.igor.simecki.q_agencyqualifier.repository;

import hr.igor.simecki.q_agencyqualifier.model.EmailAddress;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface EmailAddressRepository extends CrudRepository<EmailAddress, Long> {

    Optional<EmailAddress> findByAddress(String Address);
}
