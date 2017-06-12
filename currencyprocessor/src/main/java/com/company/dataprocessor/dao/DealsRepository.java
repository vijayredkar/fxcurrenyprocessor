package com.company.dataprocessor.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.company.dataprocessor.model.Deal;

@Repository
public interface DealsRepository extends MongoRepository<Deal, String>
{
}

