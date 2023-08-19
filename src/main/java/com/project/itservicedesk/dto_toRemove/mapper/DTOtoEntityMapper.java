package com.project.itservicedesk.dto_toRemove.mapper;

@FunctionalInterface
public interface DTOtoEntityMapper <E, D> {
    E dtoToEntity(D dto);
}
