package com.dinesh.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {

	Logger logger =LoggerFactory.getLogger(CircuitBreakerController.class);
	@GetMapping("/sample-api")
	@Retry(name="sample-api",fallbackMethod = "defaultResponseForFailure")
	public String sampleAPI()
	{
		logger.info(" Sample Api received the call.....");
		ResponseEntity<String> forEntity =new RestTemplate().getForEntity("http://localhost:5001:80", String.class);
		return forEntity.getBody();
	}
	
	@GetMapping("/circuit-api")
	@CircuitBreaker(name="default",fallbackMethod = "defaultResponseForFailure")
	public String sampleAPI_CircuitBreaker()
	{
		logger.info(" Sample Api received the call.....");
		ResponseEntity<String> forEntity =new RestTemplate().getForEntity("http://localhost:5001:80", String.class);
		return forEntity.getBody();
	}
	
	//This Rate limiter will throttle the api calls for particular time frame
	// For ex: 10s => 1000, 10 seconds the particular api is called to call 1000 times only
	@GetMapping("/rate-api")
	@RateLimiter(name="default")
	@Bulkhead(name="default")  // This annotation to controll the concurrency service calls
	public String sampleAPI_RateLimiter()
	{
		logger.info(" Sample rate limiter Api received the call.....");
		/*
		 * ResponseEntity<String> forEntity =new
		 * RestTemplate().getForEntity("http://localhost:5001:80", String.class); return
		 * forEntity.getBody();
		 */
		return "Sample Rate Limiter API";
	}
	
	public String defaultResponseForFailure(Exception ex)
	{
		return "oOOOOps something is wrong";
	}
}
