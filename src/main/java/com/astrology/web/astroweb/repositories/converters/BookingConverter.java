package com.astrology.web.astroweb.repositories.converters;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.astrology.web.astroweb.domain.Booking;
import com.astrology.web.astroweb.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class BookingConverter implements Converter<Page<Booking>, Page<com.astrology.web.astroweb.model.Booking>> {
    private Converter<User, UserDetails> userUserDetailsConverter;

    @Autowired
    @Qualifier(value = "userToUserDetails")
    public void setUserUserDetailsConverter(Converter<User, UserDetails> userUserDetailsConverter) {
        this.userUserDetailsConverter = userUserDetailsConverter;
    }
 

    @Override
    public Page<com.astrology.web.astroweb.model.Booking> convert(Page<Booking> bookingDomainPage) {

        List<com.astrology.web.astroweb.model.Booking> modelBookings = new ArrayList<com.astrology.web.astroweb.model.Booking>();
        modelBookings=bookingDomainPage.get().map(bookingDomain -> {
            com.astrology.web.astroweb.model.Booking modelBooking = new com.astrology.web.astroweb.model.Booking();
            modelBooking.setBookingDesc(bookingDomain.getBookingDesc());
            modelBooking.setBookingType(bookingDomain.getBookingType().getBookingTypeDesc());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
            modelBooking.setEndTime(bookingDomain.getEndTime().format(formatter));
            modelBooking.setStartTime(bookingDomain.getStartTime().format(formatter));
            modelBooking.setUser((com.astrology.web.astroweb.model.User)userUserDetailsConverter.convert(bookingDomain.getUser()));
            return modelBooking;
        }).collect(Collectors.toList());
        Page<com.astrology.web.astroweb.model.Booking> modelBookingPage = new PageImpl<com.astrology.web.astroweb.model.Booking>(
                modelBookings,bookingDomainPage.getPageable(),bookingDomainPage.getTotalPages());
        return modelBookingPage; 
    }

}