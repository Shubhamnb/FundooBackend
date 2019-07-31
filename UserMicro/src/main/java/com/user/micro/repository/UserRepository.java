package com.user.micro.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.user.micro.model.User;





@Repository
public interface UserRepository extends MongoRepository<User, String>{
      Optional<User> findByEmail(String userName);
}
