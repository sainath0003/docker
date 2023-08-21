package com.epam.eureka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EurekaSErverApplicationTests {
String result="hello";
	@Test
	void contextLoads() {
		
		assertEquals("hello",result);
	}
	

}
