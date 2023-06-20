package com.mno.threed.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NumberDto {


    private NumDto[] numbers;
    private LocalDate date;
    private LocalTime time;

    private Long owner;

    private Long customer;
}
