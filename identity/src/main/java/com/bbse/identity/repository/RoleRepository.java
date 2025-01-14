package com.bbse.identity.repository;

import com.bbse.identity.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, String> {
}
