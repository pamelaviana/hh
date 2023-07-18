package com.pamela.hh.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final String USER_NOT_FOUND_MSG = "User with email %s not found";

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return (UserDetails) userRepository.findUserByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String add(User user) {
        boolean userExists = userRepository
                .findUserByEmail(user.getEmail())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("Email already taken");
        }

        user.setPassword(user.getPassword());
        userRepository.save(user);
        return user.getFirstName();
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
