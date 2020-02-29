package br.com.deep.envers.service;

import br.com.deep.envers.domain.Person;
import br.com.deep.envers.dto.PersonDto;
import br.com.deep.envers.repository.PersonRepository;
import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Transactional
    public PersonDto create(final PersonDto dto) {

        Person person = new Person();
        person.setName(dto.getName().concat(" - ").concat("" + System.currentTimeMillis()));
        person.setGender(dto.getGender());

        person = personRepository.save(person);

        dto.setId(person.getId());

        return dto;

    }

    @Transactional
    public PersonDto update(final PersonDto dto) {

        Person domain = personRepository.findById(dto.getId()).get();

        domain.setName(dto.getName());
        domain.setGender(dto.getGender());

        personRepository.save(domain);

        return dto;

    }

    @Transactional
    public void delete(final Long id) {
        personRepository.deleteById(id);
    }

    @Transactional
    public PersonDto findById(final Long id) {

        Person domain = personRepository.findById(id).get();

        return PersonDto
                .builder()
                .id(domain.getId())
                .name(domain.getName())
                .gender(domain.getGender())
                .build();

    }

    @Transactional
    public List<PersonDto> findAll() {

        List<PersonDto> list = new ArrayList();

        personRepository.findAll().forEach(domain ->
            list.add(
                PersonDto
                    .builder()
                    .id(domain.getId())
                    .name(domain.getName())
                    .gender(domain.getGender())
                    .build()
            ));

        return list;

    }

    public Revisions<Integer, Person> findRevisionsById(final Long id) {

      Revisions<Integer, Person> personRevisions = personRepository.findRevisions(id);
      return personRevisions;

    }

    public List<DiffResult> findRevisionsDiffById(final Long id) {

      Revisions<Integer, Person> personRevisions = personRepository.findRevisions(id);

      List<DiffResult> list = new ArrayList<>();

      for (int i = 0; i < (personRevisions.getContent().size()) -1; i++) {

        Revision<Integer, Person> rev = personRevisions.getContent().get(i);
        Revision<Integer, Person> next = personRevisions.getContent().get(i + 1);

        DiffResult result = new DiffBuilder(rev.getEntity(), next.getEntity(), ToStringStyle.JSON_STYLE)
          .append("id", rev.getEntity().getId(), next.getEntity().getId())
          .append("name", rev.getEntity().getName(), next.getEntity().getName())
          .append("gender", rev.getEntity().getGender(), next.getEntity().getGender())
          .build();
        list.add(result);

      }

      return list;

    }

}
