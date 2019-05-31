package com.bridge.api.mongo.reposetory;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridge.api.model.Label;


public interface LabelRepository extends MongoRepository<Label, String>{
	Optional<Label> findByUserId(String userId);
}
