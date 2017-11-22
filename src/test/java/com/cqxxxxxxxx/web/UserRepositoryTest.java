package com.cqxxxxxxxx.web;

import com.cqxxxxxxxx.web.dao.UserRepository;
import com.cqxxxxxxxx.web.model.Authorities;
import com.cqxxxxxxxx.web.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by BG307435 on 2017/11/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    public void test111() {
        User user = new User();
        user.setUsername("cqxxxxxxxx");
        user.setPassword("cqx534");
        user.setEnabled(true);
        user.setAuthorities(new ArrayList<>());
        Authorities authorities = new Authorities();
        authorities.setAuthority("ROLE_ADMIN");
        Authorities authorities1 = new Authorities();
        authorities1.setAuthority("ROLE_USER");
        user.getAuthorities().add(authorities);
        user.getAuthorities().add(authorities1);

//        userRepository.save(user);

        Optional<User> userOptional = userRepository.findByUsername("cqxxxxxxxx");
        Assert.assertTrue(userOptional.isPresent());
        List<Authorities> authoritiesList = userOptional.get().getAuthorities();
        Assert.assertEquals(2, authoritiesList.size());

        try(Stream<User> userStream = userRepository.findAllByEnabled(true)) {
//            Assert.assertTrue(userStream.findFirst().isPresent());
            userStream.forEach(System.out::print);
        }


    }
}
