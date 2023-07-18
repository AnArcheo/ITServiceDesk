package com.project.itservicedesk.dto.mapper;

import java.util.List;

public interface Mapper<E, D> {
    E dtoToEntity(D dto);

    D entityToDto(E entity);

//    List<E> dtoToEntity(List<D> dtoList);
//
//    List<D> entityToDto(List<E> entityList);
}
