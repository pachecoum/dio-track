package com.primeiro.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.primeiro.spring.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT CASE WHEN (count(u) > 0) THEN true ELSE false end  FROM users u WHERE u.id <> :id and u.username = :username")
	boolean findUsernameNotId(@Param("id") Long id, @Param("username") String username);

}

