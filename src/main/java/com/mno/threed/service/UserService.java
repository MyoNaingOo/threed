package com.mno.threed.service;

import com.mno.threed.entity.User;
import com.mno.threed.reposity.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;

    public void addNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public User userfindByName(String name){
      return   userRepo.findByName(name).orElseThrow(
              () -> new UsernameNotFoundException("username not fonded")
      );
    }
    public User userfindByEmail(String email){
        return   userRepo.findByEmail(email).orElse(null);
    }

    public List<User>   getUsers(){
      return   userRepo.findAll();
    }

    public Optional<User> getUser(Long id){
        return userRepo.findById(id);
    }


    public void deleteUser(Long id){
        userRepo.deleteById(id);
    }

    public User updateUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       return userRepo.save(user);
    }

    public void changeImage(Long id,String image){
        userRepo.updateImage(id,image);
    }

    public void changePass(Long id,String password){
        String pass = passwordEncoder.encode(password);
        userRepo.changePassword(id,pass);
    }

    public void changeName(Long id,String name){
        userRepo.changeName(id,name);
    }

}
