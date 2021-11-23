package my.com.spring.boot.controller;

import java.net.URI;
import java.util.Collections;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import my.com.spring.boot.entities.Role;
import my.com.spring.boot.entities.User;
import my.com.spring.boot.models.response.DataResponse;
import my.com.spring.boot.models.response.JwtAuthenticationResponse;
import my.com.spring.boot.models.user.UserLoginRequest;
import my.com.spring.boot.models.user.UserSignUpRequest;
import my.com.spring.boot.security.JwtTokenProvider;
import my.com.spring.boot.services.RoleService;
import my.com.spring.boot.services.UserService;
import my.com.spring.boot.utils.Constant;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
	RoleService roleService;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
	
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginRequest loginRequest){
		Boolean isAvailableUsername = userService.existsByUsername(loginRequest.getUsernameOrEmail());
		Boolean isAvailableEmail = userService.existsByEmail(loginRequest.getUsernameOrEmail());
		
		if(!(isAvailableUsername || isAvailableEmail)) {
            return new ResponseEntity<Object>(new DataResponse(false, Constant.USERNAME_USER_EXIST),
                    HttpStatus.BAD_REQUEST);
        }

		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
		
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}

	@RequestMapping(value="/signup", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserSignUpRequest signUpRequest){
		
		if(userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new DataResponse(false, Constant.USERNAME_USER_EXIST));
        }

        if(userService.existsByEmail(signUpRequest.getEmail())) {
        	return ResponseEntity.badRequest().body(new DataResponse(false, Constant.EMAIL_USER_EXIST));
        }

        // Creating user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getPassword(),signUpRequest.getFullName(),
                signUpRequest.getEmail(), signUpRequest.getMobile());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleService.getRoleByName(Constant.USER);
               
        user.setRoles(Collections.singleton(userRole));

        User result = userService.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new DataResponse(true, "User registered successfully"));
    }
	
}
