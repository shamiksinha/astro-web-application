package com.astrology.web.astroweb.model;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Booking {
	private String bookingDesc;
	private String bookingType;
	private String startTime;
	private String endTime;
	private boolean allDayEvent;
	private String color;
	private boolean recurring;
	private boolean disabled;
	private Payment payment;
}
