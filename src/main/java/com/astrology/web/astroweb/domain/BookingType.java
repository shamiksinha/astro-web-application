package com.astrology.web.astroweb.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema="ASTROLOGY")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=false)
public class BookingType extends AbstractDomain {
	
	private String bookingTypeId;
	private String bookingTypeDesc;
	private int bookingTypeduration;

}
