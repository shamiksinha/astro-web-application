package com.astrology.web.astroweb.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

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
@org.hibernate.annotations.Cache(
    usage = CacheConcurrencyStrategy.READ_WRITE
)
@NaturalIdCache
public class BookingType extends AbstractDomain {
	
	@NaturalId
	@Column(nullable = false, unique = true)
	private String bookingTypeId;
	private String bookingTypeDesc;
	private int bookingTypeduration;

	@ToString.Exclude
	@OneToMany(mappedBy = "bookingType", fetch = FetchType.LAZY)
	private List<Booking> bookings;

}