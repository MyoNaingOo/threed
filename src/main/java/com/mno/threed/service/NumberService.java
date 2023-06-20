package com.mno.threed.service;

import com.mno.threed.entity.Number;
import com.mno.threed.entity.User;
import com.mno.threed.reposity.NumberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NumberService {

    private final NumberRepo numberRepo;

    public void save(Number number){
        numberRepo.save(number);
    }


    public List<Number> getAll(){
        return numberRepo.findAll(Sort.by("id").descending());
    }

    public List<Number> getByOwner(User user){
        return numberRepo.findByOwner(user,Sort.by("id").descending());
    }

    public List<Number> getByCustomer(User customer){
        return numberRepo.findByCustomer(customer,Sort.by("id").descending());
    }

    public List<Number> findByDate(LocalDate date){
        return numberRepo.findByDate(date,Sort.by("id").descending());
    }



}
