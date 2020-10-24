package com.digipay.product.cardmanagmentservice.setvices;

import com.digipay.product.cardmanagmentservice.dtos.EditDto;
import com.digipay.product.cardmanagmentservice.dtos.ParentDto;
import com.digipay.product.cardmanagmentservice.dtos.SearchDto;
import com.digipay.product.cardmanagmentservice.models.ParentEntity;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface ParentService<T extends ParentEntity, K extends Serializable> {

    T add(ParentDto<T> dto);

    T edit(K id, EditDto<T> dto) throws Exception;

    T get(K id);

    void delete(K id) throws Exception;

    List<T> getAll();

    Page<T> find(SearchDto searchDto);
}
