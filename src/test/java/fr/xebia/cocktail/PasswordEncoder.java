package fr.xebia.cocktail;

import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;

public class PasswordEncoder {

    public static void main(String[] args) {
        
        
        LdapShaPasswordEncoder passwordEncoder = new LdapShaPasswordEncoder();
        
        String pwd = passwordEncoder.encodePassword("foo", null);
        System.out.println(pwd);
        
    }
}
