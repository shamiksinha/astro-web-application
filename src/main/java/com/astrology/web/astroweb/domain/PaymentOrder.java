package com.astrology.web.astroweb.domain;

import java.time.LocalDateTime;
import java.util.Currency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.SequenceGenerator;

@Entity
@Table(schema="ASTROLOGY")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=false)
@SequenceGenerator(name="sequence",sequenceName="PAYMENTORDER_SEQ")
public class PaymentOrder extends AbstractDomain {
	
	/*@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="PAYMENTORDER_SEQ")
    private Integer id;*/
	
	@NotNull
	@Column(unique=true,nullable=false)
	private String transactionId;
	
	private String name;
	private String email;
	private String phone;
	
	@NotNull
	@Column(nullable=false)
	private String currencyCode;
	
	private Double amount;
	
	private String description;
	
	private String paymentId;
	
	private String paymentStatus;
	
	private String uniqueId;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
    private User user;

	@PreUpdate
    @PrePersist
    public void saveDefaults() {
		transactionId=transactionId!=null?transactionId:"TRN"+LocalDateTime.now().getNano();
		currencyCode=currencyCode!=null?currencyCode:"INR";
		paymentStatus=paymentStatus!=null?paymentStatus:"PENDING";
	}
}
