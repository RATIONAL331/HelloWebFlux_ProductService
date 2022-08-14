package com.example.productservice.controller;

import com.example.productservice.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductStreamController {
	private final Flux<ProductDto> productDtoFlux;

	@GetMapping(value = "stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ProductDto> getProductUpdates() {
		return productDtoFlux;
	}

	@GetMapping(value = "stream/{maxPrice}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ProductDto> getProductPriceUpdates(@PathVariable int maxPrice) {
		return productDtoFlux.filter(dto -> dto.getPrice() >= maxPrice);
	}
}
