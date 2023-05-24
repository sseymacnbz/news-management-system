package com.newsmanagementsystem.service;

import com.newsmanagementsystem.model.BaseModel;

public interface BaseService <T extends BaseModel, ID>{
    public T save(T entity);

    public void deleteById(ID entityID);
}
