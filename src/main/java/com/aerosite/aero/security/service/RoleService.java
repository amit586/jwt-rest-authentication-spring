package com.aerosite.aero.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aerosite.aero.security.model.ERole;
import com.aerosite.aero.security.model.Role;
import com.aerosite.aero.security.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
	RoleRepository roleRepository;

	public Optional<Role> findByName(ERole roleUser) {
		return roleRepository.findByName(roleUser);
	}
	
	public void deleteRolesById(Long id) {
		roleRepository.deleteById(id);
	}
}
