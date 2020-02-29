package br.com.deep.envers.repository;

import br.com.deep.envers.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, RevisionRepository<Person, Long, Integer> {
}
