package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.model.BaseModel;
import com.newsmanagementsystem.repository.BaseRepository;
import com.newsmanagementsystem.service.BaseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BaseServiceImpl <T extends BaseModel, ID> implements BaseService<T, ID>{

    @Autowired
    private BaseRepository<T, Integer> baseRepository;

    @Override
    public T save(T entity) {
        return (T) baseRepository.save(entity);
    }

    @Override
    public void deleteById(ID entityID) {
        baseRepository.deleteById((Integer) entityID);
    }
}
