package com.astrology.web.astroweb.services;

import com.astrology.web.astroweb.domain.BookingType;

public interface BookingTypeService extends CRUDService<BookingType>{
    public BookingType findByTypeDesc(String typeDesc);
    
}