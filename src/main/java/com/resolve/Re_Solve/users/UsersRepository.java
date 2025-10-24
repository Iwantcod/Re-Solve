package com.resolve.Re_Solve.users;

import com.resolve.Re_Solve.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);

    @Query("select u from Users u where u.email = :email and u.isDeleted = false")
    Optional<Users> findByEmailForLogin(@Param("email") String email);
    @Query("select u.username from Users u where u.usersId = :usersId")
    String findUsernameById(@Param("usersId") Long usersId);
}
