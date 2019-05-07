package com.astrology.web.astroweb;

import com.astrology.web.astroweb.repositories.converters.BookingConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private BookingConverter bookingConverter;

    /**
     * @param bookingConverter the bookingConverter to set
     */
    @Autowired
    public void setBookingConverter(BookingConverter bookingConverter) {
        this.bookingConverter = bookingConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        //WebMvcConfigurer.super.addFormatters(registry);
        registry.addConverter(bookingConverter);
    }
    
}