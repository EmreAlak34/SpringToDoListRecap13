package org.example.springtodolistrecap13.todo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<org.example.springtodolistrecap13, String> {
}
