package com.creditas.cotador;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"mail.host=fakehost",
		"spring.mail.host=fakehost",
		"spring.mail.port=1025"
})
class CotadorApplicationTests {

	@Test
	void contextLoads() {
	}

}
