package com.rakesh.authmodule.service;

import com.rakesh.authmodule.dtos.UserDto;
import com.rakesh.authmodule.exception.InvalidTokenException;
import com.rakesh.authmodule.exception.InvalidUserException;
import com.rakesh.authmodule.models.Token;
import com.rakesh.authmodule.models.User;
import com.rakesh.authmodule.repository.TokenRepository;
import com.rakesh.authmodule.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenRepository tokenRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    public User signUp(String name, String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            //user already exists, no need to signup
            return optionalUser.get();
        }
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public Token login(String email, String password) throws InvalidUserException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            //user already exists, no need to signup
            if(bCryptPasswordEncoder.matches(password, optionalUser.get().getHashedPassword())) {
                Token token = generatetoken(optionalUser.get());
                Token token1 = tokenRepository.save(token);
                return token1;
            } else{
                throw new InvalidUserException("Incorrect password");
            }
        } else {
            throw new InvalidUserException("Email address not found");
        }
    }

    private static Token generatetoken(User user) {
        LocalDate localDate = LocalDate.now();
        LocalDate thirtyDaysDate = localDate.plusDays(30);
        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setExpiryAt(Date.from(thirtyDaysDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        token.setIsDeleted(false);
        return token;
    }

    public void logout(String tokenString) throws InvalidTokenException {
        Optional<Token> token = tokenRepository.findByValueAndIsDeleted(tokenString, false);
        if(token.isPresent()) {
            token.get().setIsDeleted(true);
            tokenRepository.save(token.get());
        }else{
            throw new InvalidTokenException("Invalid token");
        }
    }

    public UserDto validateToken(String tokenValue) {
        Optional<Token> token = tokenRepository.findByValueAndIsDeleted(tokenValue, false);
        UserDto userDto = null;
        if(token.isPresent() ) {
            userDto = UserDto.from(token.get().getUser());
        }
        return userDto;
    }
}
