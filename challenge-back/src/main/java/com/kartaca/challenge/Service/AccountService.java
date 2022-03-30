package com.kartaca.challenge.Service;

import com.kartaca.challenge.Enums.Roles;
import com.kartaca.challenge.Model.User;
import com.kartaca.challenge.dto.NewUserData;
import com.kartaca.challenge.dto.UserPrincipal;
import com.kartaca.challenge.exception.BadRequestException;
import com.kartaca.challenge.exception.ConflictException;
import com.kartaca.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService implements UserDetailsService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AccountService(UserRepository userRepository, PasswordEncoder passwordEncoder , @Value("${admin.username:admin}") String adminUsername,
                          @Value("${admin.password:admin}") String adminPassword) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
        List<User> admins = this.userRepository.findByRole(Roles.ROLE_ADMIN);
        if (admins.isEmpty()){
            User defaultAdmin = new User();
            defaultAdmin.setUsername(adminUsername);
            defaultAdmin.setPassword(passwordEncoder.encode(adminPassword));
            defaultAdmin.setRole(Roles.ROLE_ADMIN);
            this.userRepository.save(defaultAdmin);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }
    public User getUserByName(String name){
        return this.userRepository.findByUsername(name);
    }
    public Page<User> findAll(){
        return this.userRepository.findAll(PageRequest.of(0,Integer.MAX_VALUE));
    }
    public User getById(Long id){
        return this.userRepository.findById(id).get();
    }
    public void save(User user){
        this.userRepository.save(user);
    }
    @Transactional
    public void disable(Long id){
        User user = this.getById(id);
        user.setDisabled(true);
        this.userRepository.save(user);
    }
    public void saveUser(NewUserData data){
        User user = new User();
        validator(data);
        if (data.getUsername()!=null){
            try{
                User existedUser = this.getUserByName(data.getUsername());
                if (existedUser!=null){
                    throw new Exception();
                }

            }catch (Exception e){
                throw new ConflictException();
            }
        }
        this.userDataMapper(user,data);
        user.setDisabled(false);

        this.save(user);
    }
    public void validator(NewUserData data){
        if (data.getPassword()==null || data.getEmail()==null || data.getAddress()==null ||
                data.getPhone()==null || data.getUsername()==null){
            throw new BadRequestException();
        }
    }
    public void userDataMapper(User user,NewUserData data){
        if (data.getEmail()!=null){
            user.setEmail(data.getEmail());
        }
        if (data.getUsername()!=null){
            user.setUsername(data.getUsername());
        }
        if (data.getAddress()!=null){
            user.setAddress(data.getAddress());
        }
        if (data.getPhone()!=null){
            user.setPhone(data.getPhone());
        }
        if (data.getRole()!=null){
            user.setRole(data.getRole());
        }
        if (data.getPassword()!=null){
            user.setPassword(passwordEncoder.encode(data.getPassword()));
        }
    }


}