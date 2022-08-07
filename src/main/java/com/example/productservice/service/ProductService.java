package com.example.productservice.service;

import com.example.productservice.dto.ProductDto;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.utils.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;

	public Flux<ProductDto> getAll() {
		return productRepository.findAll().map(EntityDtoUtil::toDto);
	}

	public Mono<ProductDto> getById(String id) {
		return productRepository.findById(id).map(EntityDtoUtil::toDto);
	}

	public Flux<ProductDto> getProductByPriceRange(int min, int max) {
		return productRepository.findByPriceBetween(Range.closed(min, max))
		                        .map(EntityDtoUtil::toDto);
	}

	public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono) {
		return productDtoMono.map(EntityDtoUtil::toEntity)
		                     .flatMap(productRepository::insert)
		                     .map(EntityDtoUtil::toDto);
	}

	public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono) {
		return productRepository.findById(id)
		                        // 새롭게 들어온 상품에 대해서 아이디를 기존 것으로 설정하여 저장
		                        .flatMap(product -> productDtoMono.map(EntityDtoUtil::toEntity)
		                                                          .doOnNext(entity -> entity.setId(id)))
		                        .flatMap(productRepository::save)
		                        .map(EntityDtoUtil::toDto);
	}

	public Mono<Void> deleteProduct(String id) {
		return productRepository.deleteById(id);
	}
}
