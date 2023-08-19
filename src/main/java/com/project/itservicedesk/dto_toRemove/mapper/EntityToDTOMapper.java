package com.project.itservicedesk.dto_toRemove.mapper;

@FunctionalInterface
public interface EntityToDTOMapper<E, D> {

    D entityToDto(E entity);

}
