package com.sisecofi.admingeneral;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

//@SpringBootTest
//@AutoConfigureMockMvc
class PpmcCatalogosApplicationTests {

	/*@Autowired
	private MockMvc mockMvc;

	@Test
	void faseProyecto() throws Exception {
		this.mockMvc.perform(get("/admin-plantilla/fase-proyecto")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void plantillas() throws Exception {
		this.mockMvc.perform(get("/admin-plantilla/plantillas")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void plantilla() throws Exception {
		this.mockMvc.perform(get("/admin-plantilla/obtener-plantilla/{idPlantilla}", 1)).andDo(print())
				.andExpect(status().isOk());
	}*/

}
