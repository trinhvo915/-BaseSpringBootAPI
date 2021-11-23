package my.com.spring.boot.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.spring.boot.entities.Role;
import my.com.spring.boot.repository.RoleRepository;
import my.com.spring.boot.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	RoleRepository roleRepository;

	@Override
	public Role getRoleByName(String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public Role getRoleById(String id) {
		return roleRepository.findById(id).get();
	}

}
