package my.com.spring.boot.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.spring.boot.entities.User;
import my.com.spring.boot.repository.UserRepository;
import my.com.spring.boot.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Optional<User> findByUsernameOrEmail(String username, String email) {
		return userRepository.findByUsernameOrEmail(username, email);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

}
