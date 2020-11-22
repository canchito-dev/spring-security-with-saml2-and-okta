/**
 * This content is released under the MIT License (MIT)
 *
 * Copyright (c) 2020, canchito-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author 		José Carlos Mendoza Prego
 * @copyright	Copyright (c) 2020, canchito-dev (http://www.canchito-dev.com)
 * @license		http://opensource.org/licenses/MIT	MIT License
 * @link		http://www.canchito-dev.com/public/blog/2020/11/15/spring-security-with-saml2-and-okta/
 * @link		https://github.com/canchito-dev/spring-security-with-saml2-and-okta
 **/
package com.canchitodev.samldemo;

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
//				.fromMetadataLocation("https://dev-5847200.okta.com/app/exkrrk0mYG9Vwe8wA5d5/sso/saml/metadata")
//				.registrationId("okta")
//				.build();
//
//		return new InMemoryRelyingPartyRegistrationRepository(okta);
//	}
}