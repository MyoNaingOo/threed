package com.mno.threed.reposity;

import com.mno.threed.entity.Number;
import com.mno.threed.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NumberRepo extends JpaRepository<Number,Long> {

   List<Number> findByCustomer(User customer, Sort sort);


   List<Number> findByOwner(User owner,Sort sort);


   List<Number> findByDate(LocalDate date,Sort sort);




}
