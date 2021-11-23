package my.com.spring.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.com.spring.boot.entities.Role;

@Repository
public interface RoleRepository  extends JpaRepository<Role, String> {

	Role findByName(String name);
}
