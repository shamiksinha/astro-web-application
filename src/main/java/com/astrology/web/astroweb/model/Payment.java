package com.astrology.web.astroweb.model;

import javax.persistence.metamodel.StaticMetamodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Payment {
	private Integer noOfItems;
	private String email;	
	private String name;
	private String phone;
	private Double amount;	
	private String description;
	private String transactionId;
	private User user;
	private final String currencyCode="INR";
	private String paymentStatus="PENDING";
	private String paymentId;
	private String uniqueId;
	private boolean disabled;

}
