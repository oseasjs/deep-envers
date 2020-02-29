package br.com.deep.envers.service;

import br.com.deep.envers.domain.Car;
import br.com.deep.envers.dto.CarDto;
import br.com.deep.envers.repository.CarRepository;
import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarService {

    @Autowired
    CarRepository carRepository;

    @Transactional
    public CarDto create(final CarDto dto) {

        Car domain = new Car();
        domain.setName(dto.getName().concat(" - ").concat("" + System.currentTimeMillis()));
        domain.setModel(dto.getModel());

        domain = carRepository.save(domain);

        dto.setId(domain.getId());

        return dto;

    }

    @Transactional
    public CarDto update(final CarDto dto) {

        Car domain = carRepository.findById(dto.getId()).get();

        domain.setName(dto.getName());
        domain.setModel(dto.getModel());

        carRepository.save(domain);

        return dto;

    }

    @Transactional
    public void delete(final Long id) {
        carRepository.deleteById(id);
    }

    @Transactional
    public CarDto findById(final Long id) {

        Car domain = carRepository.findById(id).get();

        return CarDto
                .builder()
                .id(domain.getId())
                .name(domain.getName())
                .model(domain.getModel())
                .build();

    }

    @Transactional
    public List<CarDto> findAll() {

        List<CarDto> list = new ArrayList();

        carRepository.findAll().forEach(domain ->
            list.add(
                CarDto
                    .builder()
                    .id(domain.getId())
                    .name(domain.getName())
                    .model(domain.getModel())
                    .build()
            ));

        return list;

    }

    public Revisions<Integer, Car> findRevisionsById(final Long id) {

        Revisions<Integer, Car> revisions = carRepository.findRevisions(id);
        return revisions;

    }

    public List<DiffResult> findRevisionsDiffById(final Long id) {

        Revisions<Integer, Car> revisions = carRepository.findRevisions(id);

        List<DiffResult> list = new ArrayList<>();

        for (int i = 0; i < (revisions.getContent().size()) -1; i++) {

            Car rev = revisions.getContent().get(i).getEntity();
            Car next = revisions.getContent().get(i + 1).getEntity();

            DiffResult result = new DiffBuilder(rev, next, ToStringStyle.JSON_STYLE)
                    .append("id", rev.getId(), next.getId())
                    .append("name", rev.getName(), next.getName())
                    .append("model", rev.getModel(), next.getModel())
                    .build();
            list.add(result);

        }

        return list;

    }

}
