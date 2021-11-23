package my.com.spring.boot.services;

import java.util.Optional;

import my.com.spring.boot.entities.User;

public interface UserService {
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsernameOrEmail(String username, String email);
	
	Optional<User> findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	User save(User user);
}
