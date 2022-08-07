package com.example.productservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
public class Product {
	@Id
	private String id;
	private String description;
	private Integer price;
}
