package br.com.deep.envers.controller;

import br.com.deep.envers.domain.Car;
import br.com.deep.envers.dto.CarDto;
import br.com.deep.envers.service.CarService;
import org.apache.commons.lang3.builder.DiffResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revisions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("car")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping
    public CarDto create(@RequestBody final CarDto dto) {
        return carService.create(dto);
    }

    @PutMapping
    public CarDto update(@RequestBody final CarDto dto) {
        return carService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final Long id) {
        carService.delete(id);
    }

    @GetMapping("/{id}")
    public CarDto findById(@PathVariable("id") final Long id) {
        return carService.findById(id);
    }

    @GetMapping
    public List<CarDto> findByAll() {
        return carService.findAll();
    }

    @GetMapping("/audit/{id}")
    public Revisions<Integer, Car> findRevisionsById(@PathVariable("id") final Long id) {
        return carService.findRevisionsById(id);
    }

    @GetMapping("/audit/diff/{id}")
    public List<DiffResult> findRevisionsDiffById(@PathVariable("id") final Long id) {
        return carService.findRevisionsDiffById(id);
    }

}
