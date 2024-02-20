package com.ismail.shop.repositories;

import com.ismail.shop.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User  u")
    Page<User> findAll( Pageable pageable);

    @Query("Select u from User u where u.email =?1")
    User findByEmail(String email);

    @Query("update User u set u.enabled =?2 where u.id=?1")
    @Modifying
    void updateUserEnabledStatus(Long id, boolean enabled);
}
