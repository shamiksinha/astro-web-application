package com.astrology.web.astroweb.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@SequenceGenerator(name="sequence",sequenceName="PRODUCT_SEQ")
public class Product extends AbstractDomain {
	
	/*@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="PRODUCT_SEQ")
    private Integer id;*/
	
	private String productName;
    private String productId;
    private String noOfProducts;
    
   /* @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema="ASTROLOGY")
    private List<User> users = new ArrayList<>();*/
	@ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
