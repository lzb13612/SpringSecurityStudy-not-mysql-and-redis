package com.sss.springsecuritystudy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SpringSecurityStudyApplicationTests {

    @Test
    void contextLoads() {
        PasswordEncoder p = new BCryptPasswordEncoder();
        String encode = p.encode("123");
        System.out.println(encode);
        boolean matches = p.matches("123", encode);
        System.out.println(matches);
        matches = p.matches("1234", encode);
        System.out.println(matches);
    }

}
