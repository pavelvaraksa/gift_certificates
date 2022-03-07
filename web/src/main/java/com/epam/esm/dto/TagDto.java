package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * Tag dto
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TagDto extends RepresentationModel<TagDto> {
    private String name;
}
