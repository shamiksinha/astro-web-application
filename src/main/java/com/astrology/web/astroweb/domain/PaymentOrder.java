package com.astrology.web.astroweb.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
@SequenceGenerator(name="sequence",sequenceName="PAYMENTORDER_SEQ")
@org.hibernate.annotations.Cache(
    usage = CacheConcurrencyStrategy.READ_WRITE
)
@NaturalIdCache
public class PaymentOrder extends AbstractDomain {
	
	/*@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="PAYMENTORDER_SEQ")
    private Integer id;*/
	
	@NaturalId
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

	@ToString.Exclude
	@OneToOne(fetch = FetchType.LAZY,optional = true)	
	private Booking booking;


	@PreUpdate
    @PrePersist
    public void saveDefaults() {
		transactionId=transactionId!=null?transactionId:"TRN"+LocalDateTime.now().getNano();
		currencyCode=currencyCode!=null?currencyCode:"INR";
		paymentStatus=paymentStatus!=null?paymentStatus:"PENDING";
	}
}
