package com.project.itservicedesk.dto.mapper;

@FunctionalInterface
public interface EntityToDTOMapper<E, D> {

    D entityToDto(E entity);

}
