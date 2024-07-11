package com.first.spring.config;

import java.io.FileInputStream;
import java.net.URI;
import java.net.URL;

import org.apache.hc.client5.http.classic.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class SSLConfig {
//
//	@Bean
//	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainerCustomizer() {
//		return new WebServerFactoryCustomizer<TomcatServletWebServerFactory>() {
//			@Override
//			public void customize(TomcatServletWebServerFactory factory) {
//				factory.addConnectorCustomizers(connector -> {
//					connector.setScheme("https");
//					connector.setSecure(true);
//					connector.setPort(8080);
//					connector.
//				});
//			}
//		};
//	}
//}