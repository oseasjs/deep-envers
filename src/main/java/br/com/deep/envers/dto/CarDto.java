package br.com.deep.envers.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDto {

    private Long id;

    private String name;

    private String model;

}
