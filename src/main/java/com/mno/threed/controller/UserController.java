package com.mno.threed.controller;

import com.mno.threed.auth.AuthenticationResponse;
import com.mno.threed.auth.AuthenticationService;
import com.mno.threed.config.JwtService;
import com.mno.threed.dto.UserDto;
import com.mno.threed.entity.User;
import com.mno.threed.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;


   @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }

    @PutMapping("update")
    public AuthenticationResponse update(@RequestBody User user,HttpServletRequest request){
        User getu = jwtService.getuser(request);

        User saveuser = User.builder()
                .id(getu.getId())
                .user_img(user.getUser_img())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();

        User userRe = userService.updateUser(saveuser);
        String token = jwtService.generateToken(user);
        authenticationService.saveUserToken(saveuser,token);
        return AuthenticationResponse.builder()
                .user(saveuser)
                .build();
    }

    @GetMapping("users")
    public List<User> getusers(){
       return userService.getUsers();
    }

    @GetMapping("userid/{id}")
    public Optional<User> getUser(@PathVariable ("id") Long id){
        return userService.getUser(id);
    }

    @GetMapping("username/{name}")
    public User getUsername(@PathVariable ("name") String name){
        return userService.userfindByName(name);
    }


    @GetMapping("usereamil/{email}")
    public User getUserEmail(@PathVariable ("email") String email){
        return userService.userfindByEmail(email);
    }

    @GetMapping("user")
    private User getUserByOwner(HttpServletRequest request){
       User user = jwtService.getuser(request);

       User userRe = User.builder()
               .user_img(user.getUser_img())
               .email(user.getEmail())
               .name(user.getName())
               .role(user.getRole())
               .build();

        return userRe;
    }

    @PostMapping("image")
    public void changeImage(@RequestBody UserDto user , HttpServletRequest request){
        User userjwt = jwtService.getuser(request);
        userService.changeImage(userjwt.getId(),user.getUser_img());

    }

    @PostMapping("changePass")
    public void changePass(@RequestBody UserDto user , HttpServletRequest request){
        User userjwt = jwtService.getuser(request);
        userService.changePass(userjwt.getId(),user.getPassword());
    }

    @PostMapping("changeName")
    public void changeName(@RequestBody UserDto user , HttpServletRequest request){
        User userjwt = jwtService.getuser(request);
        userService.changeName(userjwt.getId(),user.getName());
    }

}
