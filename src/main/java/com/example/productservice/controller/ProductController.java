package com.example.productservice.controller;

import com.example.productservice.dto.ProductDto;
import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductController {
	private final ProductService productService;

	@GetMapping
	public ResponseEntity<Flux<ProductDto>> getAll() {
		// DO NOT Flux<ResponseEntity<V>> !!! Only a single ResponseEntity supported
		return ResponseEntity.ok(productService.getAll());
	}

	@GetMapping("{id}")
	public Mono<ResponseEntity<ProductDto>> getProductById(@PathVariable String id) {
		return productService.getById(id)
		                     .map(ResponseEntity::ok)
		                     .defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@GetMapping("/price-range")
	public ResponseEntity<Flux<ProductDto>> getRangeProduct(@RequestParam int min,
	                                                        @RequestParam int max) {
		return ResponseEntity.ok(productService.getProductByPriceRange(min, max));
	}

	@PostMapping
	public Mono<ResponseEntity<ProductDto>> insertProduct(@RequestBody Mono<ProductDto> productDtoMono) {
		return productService.insertProduct(productDtoMono)
		                     .map(ResponseEntity::ok);
	}

	@PutMapping("{id}")
	public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable String id,
	                                                      @RequestBody Mono<ProductDto> productDtoMono) {
		return productService.updateProduct(id, productDtoMono)
		                     .map(ResponseEntity::ok)
		                     .defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping("{id}")
	public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable String id) {
		return productService.deleteProduct(id)
		                     .map(ResponseEntity::ok);
	}
}
