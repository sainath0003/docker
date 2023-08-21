package com.epam.javaproject.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.epam.javaproject.exception.UnauthorizedException;
import com.epam.javaproject.proxy.WebFluxAuthenticationProxy;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;


	@Autowired
	private WebFluxAuthenticationProxy proxy;

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			if (validator.isSecured.test(exchange.getRequest())) {
				// header contains token or not
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new UnauthorizedException("missing authorization header");
				}

				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}
				try {


					proxy.validateToken(authHeader);
				} catch (Exception e) {

					throw new UnauthorizedException("un authorized access to application");
				}
			}
			return chain.filter(exchange);
		});
	}

	public static class Config {
		public Config() {
			super();
		}

	}
}
