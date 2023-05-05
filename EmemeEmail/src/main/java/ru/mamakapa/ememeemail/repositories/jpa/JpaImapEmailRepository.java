package ru.mamakapa.ememeemail.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mamakapa.ememeemail.entities.jpa.ImapEmailEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaImapEmailRepository extends JpaRepository<ImapEmailEntity, Long> {
    Optional<ImapEmailEntity> findByAddress(String address);
}
