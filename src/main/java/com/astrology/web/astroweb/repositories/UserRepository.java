package com.astrology.web.astroweb.repositories;

import org.springframework.data.repository.CrudRepository;

import com.astrology.web.astroweb.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	User findByUsername(String username);
	
	/**
	 * Finds an user by username or phone
	 * @param username to find with
	 * @param phone to find with
	 * @return - the user entity
	 */
	User findByUsernameOrPhone(String username,String phone);
	
}