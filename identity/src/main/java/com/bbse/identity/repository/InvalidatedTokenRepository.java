package com.bbse.identity.repository;

import com.bbse.identity.model.InvalidatedToken;
import org.springframework.data.repository.CrudRepository;

public interface InvalidatedTokenRepository extends CrudRepository<InvalidatedToken, String> {
}
