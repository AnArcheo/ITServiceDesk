package com.project.itservicedesk.dto.mapper;

@FunctionalInterface
public interface DTOtoEntityMapper <E, D> {
    E dtoToEntity(D dto);
}
