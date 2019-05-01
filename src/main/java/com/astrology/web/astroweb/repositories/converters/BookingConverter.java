package com.astrology.web.astroweb.repositories.converters;

import java.time.format.DateTimeFormatter;

import com.astrology.web.astroweb.domain.Booking;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookingConverter implements Converter<Booking,com.astrology.web.astroweb.model.Booking> {

    @Override
    public com.astrology.web.astroweb.model.Booking convert(Booking bookingDomain) {
        com.astrology.web.astroweb.model.Booking modelBooking=new com.astrology.web.astroweb.model.Booking();
        modelBooking.setBookingDesc(bookingDomain.getBookingDesc());
        modelBooking.setBookingType(bookingDomain.getBookingType().getBookingTypeDesc());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
        modelBooking.setEndTime(bookingDomain.getEndTime().format(formatter));
        modelBooking.setStartTime(bookingDomain.getStartTime().format(formatter));
        return modelBooking;
    }
    
}