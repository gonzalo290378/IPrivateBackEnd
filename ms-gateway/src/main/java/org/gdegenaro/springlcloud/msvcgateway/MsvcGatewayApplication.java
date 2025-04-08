package org.gdegenaro.springlcloud.msvcgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsvcGatewayApplication {

	//Orden ejecucion ms: Eureka, ConfigServer, MsUsers, MsAccounts, MsPayments, MsQueue, ApiGateway

	public static void main(String[] args) {
		SpringApplication.run(MsvcGatewayApplication.class, args);
	}

}
