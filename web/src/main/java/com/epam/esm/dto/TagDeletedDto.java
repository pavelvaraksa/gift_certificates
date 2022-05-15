package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * Tag deleted dto
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TagDeletedDto extends RepresentationModel<TagDeletedDto> {
    private String name;

    private boolean deleted;
}
