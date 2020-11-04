package com.flowable.samldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SamlDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SamlDemoApplication.class, args);
	}

//	@Bean
//	public RelyingPartyRegistrationRepository relyingPartyRegistrations() throws IOException {
//		RelyingPartyRegistration okta = RelyingPartyRegistrations
//				.fromMetadataLocation("https://dev-2893509.okta.com/app/exkj045wmGr8Cqbtk5d5/sso/saml/metadata")
//				.registrationId("okta")
//				.build();
//
//		return new InMemoryRelyingPartyRegistrationRepository(okta);
//	}
}