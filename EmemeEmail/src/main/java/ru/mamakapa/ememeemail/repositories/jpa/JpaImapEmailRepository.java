package ru.mamakapa.ememeemail.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mamakapa.ememeemail.entities.jpa.ImapEmailEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaImapEmailRepository extends JpaRepository<ImapEmailEntity, Long> {
    Optional<ImapEmailEntity> findByAddress(String address);
    @Query(value =
            """
            select u.id from botemail be
            inner join botuser as u on be.botuser_id = u.id
            inner join imapemail e on e.id = be.email_id
            where e.address = :email
            """,
            nativeQuery = true)
    List<Long> getAllIdsOfUsersSubscribedOnLink(@Param("email") String email);

    @Query("select e from ImapEmailEntity e order by e.lastChecked limit 1")
    Optional<ImapEmailEntity> getLatestCheckedEmail();
}
