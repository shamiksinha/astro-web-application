package com.astrology.web.astroweb.repositories;

import com.astrology.web.astroweb.domain.BookingType;

import org.springframework.data.repository.CrudRepository;

public interface BookingTypeRepository extends CrudRepository<BookingType, Integer> {

    public BookingType findByBookingTypeDesc(String bookingTypeDesc);
    
}