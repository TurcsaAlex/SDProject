package com.example.project1.services;

import com.example.project1.config.security.AuthUser;
import com.example.project1.config.security.JwtTokenUtil;
import com.example.project1.model.User;
import com.example.project1.model.dto.Cashier.CashierCreateDTO;
import com.example.project1.model.dto.Cashier.CashierSignInDTO;
import com.example.project1.model.dto.User.UserChangePasswordDTO;
import com.example.project1.model.dto.User.UserLoginDTO;
import com.example.project1.model.dto.User.UserSignUpDTO;
import com.example.project1.model.exceptions.NotAuthenticatedException;
import com.example.project1.model.exceptions.UserAlreadyExistsException;
import com.example.project1.model.exceptions.service.MyError;
import com.example.project1.model.exceptions.service.ResourceNotFoundException;
import com.example.project1.model.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@AllArgsConstructor
@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserRepository repository;
    private JwtTokenUtil jwtTokenUtil;
    private AuthenticationManager authenticationManager;
    private UserDetailsServiceImpl userDetailsService;

    public String signUp(UserSignUpDTO userSignUpDTO) {
        String email = userSignUpDTO.getEmail();
        String firstName=userSignUpDTO.getFirstName();
        String lastName=userSignUpDTO.getLastName();
        String password=userSignUpDTO.getPassword();

        if(repository.findByEmail(email).isPresent()){
            throw new UserAlreadyExistsException();
        }

        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password));
        if(email != null && password != null)
            repository.save(user);
        return jwtTokenUtil.generateAccessToken(new AuthUser(user.getId(), user.getEmail(), user.getPassword()));
    }

    public String createCashier(CashierCreateDTO userSignUpDTO) {
        String email = userSignUpDTO.getEmail();


        if(repository.findByEmail(email).isPresent()){
            throw new MyError("AlreadyExists");
        }

        User user = new User();
        user.setEmail(email);
        if(email != null)
            repository.save(user);
        return jwtTokenUtil.generateAccessToken(new AuthUser(user.getId(), user.getEmail(), user.getPassword()));
    }

    public String login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getEmail();
        String password = userLoginDTO.getPassword();
        AuthUser authUser = userDetailsService.loadUserByUsername(username);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authUser, password));
        return jwtTokenUtil.generateAccessToken(authUser);
    }

    public String signInStudent(CashierSignInDTO cashierSignInDTO){
        String email = cashierSignInDTO.getEmail();
        String firstName= cashierSignInDTO.getFirstName();
        String lastName= cashierSignInDTO.getLastName();
        String password= cashierSignInDTO.getPassword();

        User user = getById(getCurrentUserId());
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password));
        if(email != null && password != null)
            repository.save(user);
        return jwtTokenUtil.generateAccessToken(new AuthUser(user.getId(), user.getEmail(), user.getPassword()));
    }

    public void changePassword(UserChangePasswordDTO userChangePasswordDTO) {
        String newPassword = userChangePasswordDTO.getNewPassword();
        String oldPassword = userChangePasswordDTO.getOldPassword();


        User user = getById(getCurrentUserId());
        String username=user.getEmail();

        AuthUser authUser = userDetailsService.loadUserByUsername(username);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authUser, oldPassword));
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);
    }

    public User getById(Integer id) throws ResourceNotFoundException {
        User entity = getRepository().findById(id)
                .orElse(null);
        if (entity == null) {
            throw new ResourceNotFoundException();
        }
        return entity;
    }

    protected Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication == null) {
            throw new NotAuthenticatedException();
        }
        return ((AuthUser) authentication.getPrincipal()).getId();
    }
    protected JpaRepository<User, Integer> getRepository() {
        return repository;
    }

}
