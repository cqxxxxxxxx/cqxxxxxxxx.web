package com.cqxxxxxxxx.web.dao;

import com.cqxxxxxxxx.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by BG307435 on 2017/11/16.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

//    @Query(value = "select u, a from User u left outer join Authorities a on u.uid = a.uid where u.username = ?1")
    Optional<User> findByUsername(String username);

    Stream<User> findAllByEnabled(Boolean enabled);

    @Modifying
    @Transactional
    @Query(value = "update User u set u.enabled = ?1 where u.uid = ?2")
    int modifyEnabledByUid(Boolean enabled, Integer id);
}
