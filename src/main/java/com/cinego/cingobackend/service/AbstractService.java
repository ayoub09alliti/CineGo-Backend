package com.cinego.cingobackend.service;




import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cinego.cingobackend.model.AbstractModel;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T extends AbstractModel<Long>, Long extends Serializable> {

    private static final int PAGE_SIZE = 5;
    protected abstract JpaRepository<T, Long> getRepository();

    public Page<T> getList(Integer pageNumber) {
        PageRequest pageRequest =
                PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "id");
        
        return getRepository().findAll(pageRequest);
    }

    public T save(T entity) {
        return getRepository().save(entity);
    }

    public T get(Long id) {
        Optional<T> entityOpt = getRepository().findById(id);
        T entity = entityOpt.get();
        return entity;
    }

    public void delete(Long id) {
        try {
            getRepository().deleteById(id);
        } catch (EmptyResultDataAccessException e) {
        	System.out.println("\n************ Erreur de suppression avec ID = "+id+"\n"+e);
        }
    }

    public void update(T entity) {
        Optional<T> getEntityOpt = getRepository().findById(entity.getId());
        T getEntity = getEntityOpt.get();
        getRepository().save(entity);
    }
    
    public List<T> getListAll() {
        return getRepository().findAll();
    }
    public long count() {
        return getRepository().count();
    }
 
 

}