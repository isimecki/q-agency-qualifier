package hr.igor.simecki.q_agencyqualifier.repository;


import hr.igor.simecki.q_agencyqualifier.model.Email;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("email_repository")
public interface EmailRepository extends CrudRepository<Email, Long> {

    @Override
    <S extends Email> S save(S entity);

    @Override
    Optional<Email> findById(Long aLong);

    @Override
    Iterable<Email> findAll();

    @Override
    void deleteById(Long aLong);

    @Query("select e from email e where e.from.address like ?1% or e.title like ?1% or e.content.content like ?1%")
    List<Email> queryForEmails(String fromAddress);
}
