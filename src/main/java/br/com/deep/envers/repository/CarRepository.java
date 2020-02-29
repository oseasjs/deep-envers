package br.com.deep.envers.repository;

import br.com.deep.envers.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>, RevisionRepository<Car, Long, Integer> {
}
