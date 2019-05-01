package com.astrology.web.astroweb.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.astrology.web.astroweb.domain.BookingType;
import com.astrology.web.astroweb.repositories.BookingTypeRepository;
import com.astrology.web.astroweb.services.BookingTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service("bookingTypeService")
@Profile("springdatajpa")
public class BookingTypeServiceImpl implements BookingTypeService {

    private BookingTypeRepository bookingTypeRepository;
	
	@Autowired
	public void setBookingRepository(BookingTypeRepository bookingTypeRepository) {
		this.bookingTypeRepository = bookingTypeRepository;
	}

    @Override
    public List<BookingType> listAll() {
        List<BookingType> bookingTypes=new ArrayList<BookingType>();
        bookingTypeRepository.findAll().forEach(bookingTypes::add);
        return bookingTypes;
    }

    @Override
    public BookingType getById(Integer id) {
        return bookingTypeRepository.findById(id).get();
    }

    @Override
    public BookingType saveOrUpdate(BookingType domainObject) {
        return bookingTypeRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        bookingTypeRepository.delete(getById(id));
    }

    @Override
    public BookingType findByTypeDesc(String typeDesc) {
        return bookingTypeRepository.findByBookingTypeDesc(typeDesc);
    }
    
}   