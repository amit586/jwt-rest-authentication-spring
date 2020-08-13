package com.aerosite.aero.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aerosite.aero.security.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	Object findOneWithAuthoritiesByUsername(String lowercaseLogin);

	Object findOneWithAuthoritiesByEmailIgnoreCase(String login);

//	@Modifying(clearAutomatically = true)
//	@Query("update User u set u.activated =:activate where u.email=:email")
//	public void activateUser(@Param("activate") boolean activate,@Param("email") String email);
//	
	@Modifying
	@Query("update User u set u.activated = ?1 where u.email = ?2")
	int activateUser(boolean activated, String email);

	@Modifying
	@Query("update User u set u.password = ?1 where u.email = ?2")
	int updatePassword(String encodedPassword, String email);

	@Modifying(clearAutomatically = true)
	@Query("update User u set u.firstname = ?2, u.lastname = ?3,u.password = ?4 where u.id = ?1")
	void updateUser(Long id, String firstname, String lastname, String encodedPassword);

	@Modifying(clearAutomatically = true)
	@Query("Delete from User u where u.username = ?1")
	void deleteByUsername(String username);

}
