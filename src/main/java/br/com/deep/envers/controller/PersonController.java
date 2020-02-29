package br.com.deep.envers.controller;

import br.com.deep.envers.domain.Person;
import br.com.deep.envers.dto.PersonDto;
import br.com.deep.envers.service.PersonService;
import org.apache.commons.lang3.builder.DiffResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revisions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public PersonDto create(@RequestBody final PersonDto dto) {
        return personService.create(dto);
    }

    @PutMapping
    public PersonDto update(@RequestBody final PersonDto dto) {
        return personService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final Long id) {
        personService.delete(id);
    }

    @GetMapping("/{id}")
    public PersonDto findById(@PathVariable("id") final Long id) {
        return personService.findById(id);
    }

    @GetMapping
    public List<PersonDto> findByAll() {
        return personService.findAll();
    }

    @GetMapping("/audit/{id}")
    public Revisions<Integer, Person> findRevisionsById(@PathVariable("id") final Long id) {
      return personService.findRevisionsById(id);
    }

    @GetMapping("/audit/diff/{id}")
    public List<DiffResult> findRevisionsDiffById(@PathVariable("id") final Long id) {
      return personService.findRevisionsDiffById(id);
    }

}
