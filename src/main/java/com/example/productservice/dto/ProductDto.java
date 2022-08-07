package com.example.productservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductDto {
	private String id;
	private String description;
	private Integer price;

	public ProductDto(String description, Integer price) {
		this.description = description;
		this.price = price;
	}
}
