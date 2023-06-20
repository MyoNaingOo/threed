package com.mno.threed.reposity;


import com.mno.threed.entity.Nument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumentRepo extends JpaRepository<Nument,Long> {

}
