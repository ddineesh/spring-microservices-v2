package com.dinesh.microservices.apigateway;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {

		Function<PredicateSpec, Buildable<Route>> routeFuntion = 
				p -> p.path("/get").uri("http://httpbin.org:80");

		return builder.routes().route(routeFuntion).build();

	}
}
