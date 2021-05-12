package hu.bme.aut.javaweb.forum.controller;

import hu.bme.aut.javaweb.forum.datasource.RoleDataSource;
import hu.bme.aut.javaweb.forum.datasource.UserDataSource;
import hu.bme.aut.javaweb.forum.model.ERole;
import hu.bme.aut.javaweb.forum.model.Role;
import hu.bme.aut.javaweb.forum.model.User;
import hu.bme.aut.javaweb.forum.model.dto.JwtResponse;
import hu.bme.aut.javaweb.forum.model.dto.LoginRequest;
import hu.bme.aut.javaweb.forum.model.dto.SignupRequest;
import hu.bme.aut.javaweb.forum.security.jwt.JwtUtils;
import hu.bme.aut.javaweb.forum.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDataSource userDataSource;

    @Autowired
    RoleDataSource roleDataSource;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        );
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody SignupRequest signUpRequest) {

        Pattern emailPattern = Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
        Matcher emailMatcher = emailPattern.matcher(signUpRequest.getEmail());

        if (!emailMatcher.matches()) {
            throw new IllegalArgumentException("Error: Email is invalid!");
        }

        if (userDataSource.existsByEmail(signUpRequest.getEmail())) {
            throw new IllegalArgumentException("Error: Email is already in use!");
        }

        Pattern usernamePattern = Pattern.compile("^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");
        Matcher usernameMatcher = usernamePattern.matcher(signUpRequest.getUsername());
        if (!usernameMatcher.matches()) {
            throw new IllegalArgumentException("Error: Username should be 4-20 character of letters, .or _ with no double . or _!");
        }

        if (userDataSource.existsByUsername(signUpRequest.getUsername())) {
            throw new IllegalArgumentException("Error: Username is already taken!");
        }

        if (signUpRequest.getPassword().length() < 8) {
            throw new IllegalArgumentException("Error: Password should be at least 8 character!");
        }

        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
        );

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleDataSource.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("admin")) {
                    Role adminRole = roleDataSource.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleDataSource.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userDataSource.save(user);

        return user;
    }
}
