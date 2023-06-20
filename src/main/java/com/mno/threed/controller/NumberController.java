package com.mno.threed.controller;


import com.mno.threed.auth.AuthenticationService;
import com.mno.threed.config.JwtService;
import com.mno.threed.dto.NumDto;
import com.mno.threed.dto.NumberDto;
import com.mno.threed.entity.Number;
import com.mno.threed.entity.Nument;
import com.mno.threed.entity.User;
import com.mno.threed.reposity.NumentRepo;
import com.mno.threed.service.NumberService;
import com.mno.threed.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/number")
@RequiredArgsConstructor
public class NumberController {


    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    private final NumberService numberService;

    private final UserService userService;
    private final NumentRepo numentRepo;


    @GetMapping("all")
    public List<Number> getAll(){
        return numberService.getAll();
    }



    @PostMapping("save/{id}")
    private void save(HttpServletRequest request,@PathVariable("id") Long id,@RequestBody NumDto[] numberDto){
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        User user = userService.userfindByEmail(userEmail);
        User owner = userService.getUser(id).orElse(null);
        List<Nument> numli = new ArrayList<>();
        for (NumDto numdto : numberDto ) {
            Nument nument = Nument.builder()
                    .number(numdto.getNumber())
                    .price(numdto.getPrice())
                    .build();
            numentRepo.save(nument);
            numli.add(nument);
        }
        System.out.println(numli.toString());
        System.out.println(Arrays.toString(numberDto));
        Number number = Number.builder()
                .number(numli)
                .customer(user)
                .owner(owner)
                .date(LocalDate.now())
                .time(LocalTime.now())
                .build();
        numberService.save(number);
    }

    @GetMapping("owner")
    public List<Number> getByOwner(HttpServletRequest request){
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        User user = userService.userfindByEmail(userEmail);
        return numberService.getByOwner(user);
    }

    @GetMapping("customer")
    private List<Number> getByCustomer(HttpServletRequest request){
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        User user = userService.userfindByEmail(userEmail);
        return numberService.getByCustomer(user);
    }

    @PostMapping("dateByOwner")
    private List<Number> findByDateForOwner(@RequestBody  NumberDto numberDto,HttpServletRequest request){

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        User user = userService.userfindByEmail(userEmail);

        List<Number> numbers = numberService.findByDate(numberDto.getDate());
        List<Number> numberList = new ArrayList<>();
        for (Number number:numbers ) {
            if(number.getOwner()==user){
                numberList.add(number);
            }
        }
        return numberList;
    }


    @PostMapping("dateByCustomer")
    private List<Number> findByDateForCustomer(@RequestBody  NumberDto numberDto,HttpServletRequest request){

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        User user = userService.userfindByEmail(userEmail);

        List<Number> numbers = numberService.findByDate(numberDto.getDate());
        List<Number> numberList = new ArrayList<>();
        for (Number number:numbers ) {
            if(number.getCustomer()==user){
                numberList.add(number);
            }
        }
        return numberList;
    }

}
