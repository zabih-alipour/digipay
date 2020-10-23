package com.digipay.product.cardmanagmentservice.setvices;

import com.digipay.product.cardmanagmentservice.dtos.EditDto;
import com.digipay.product.cardmanagmentservice.dtos.ParentDto;
import com.digipay.product.cardmanagmentservice.dtos.SearchDto;
import com.digipay.product.cardmanagmentservice.models.ParentEntity;
import com.digipay.product.cardmanagmentservice.repositories.ParentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Transactional
public abstract class ParentServiceImpl<T extends ParentEntity, K extends Serializable> implements ParentService<T, K> {
    protected final ParentRepository<T, K> repository;

    public ParentServiceImpl(ParentRepository<T, K> repository) {
        this.repository = repository;
    }

    @Override
    public T add(ParentDto<T> dto) {
        Optional<T> entity = dto.getEntity();
        return entity
                .map(this::validate)
                .map(repository::save)
                .orElseThrow(RuntimeException::new);
    }

    protected T validate(T t) {
        return t;
    }

    @Override
    public T edit(K id, EditDto<T> dto) {
        T entity = repository.getOne(id);
        return dto.merge(entity)
                .map(this::validate)
                .map(repository::save)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public T get(K id) {
        return repository.getOne(id);
    }

    @Override
    public void delete(K id) {
        repository.deleteById(id);
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public Page<T> find(SearchDto searchDto) {
        return repository.findAll(PageRequest.of(0, 50));
    }
}
