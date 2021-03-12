package tacos;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import tacos.web.WebConfig;

@WebMvcTest(WebConfig.class)
class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void test() throws Exception {
		mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("home"))
		.andExpect((ResultMatcher) content().string(containsString("Welcome")));
	}

	private void andExpect(ResultMatcher string) {
		// TODO Auto-generated method stub
		
	}

}
