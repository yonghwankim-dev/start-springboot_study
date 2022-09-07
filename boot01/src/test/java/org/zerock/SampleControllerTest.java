package org.zerock;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.zerock.controller.SampleController;


@WebMvcTest(SampleController.class)	// @SpringBootTest는 사용하지 않습니다.
public class SampleControllerTest {
	@Autowired
	MockMvc mock;
	
	@Test
	public void testHello() throws Exception {
		MvcResult result = mock.perform(get("/hello"))
							.andExpect(status().isOk())
							.andExpect(content().string("Hello World")).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}
}
