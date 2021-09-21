package com.aspiresys.oms.model.repository.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aspiresys.oms.beans.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	@Query("from User where email = :email")
	public User getUserByUserName(@Param("email") String email);

}
