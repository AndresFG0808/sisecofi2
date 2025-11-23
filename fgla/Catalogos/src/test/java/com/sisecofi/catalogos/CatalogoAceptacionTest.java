package com.sisecofi.catalogos;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.sisecofi.catalogos.utilerias.FactoryClass;
import com.sisecofi.catalogos.utilerias.Util;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
class CatalogoAceptacionTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void catalogos() throws Exception {
		log.info("Consultando catalogos");
		this.mockMvc.perform(get("/catalogos/catalogos")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void metadataCatalogoAlta() throws Exception {
		this.mockMvc.perform(get("/catalogos/catalogos/alta-meta/{idCatalogo}", 1)).andDo(print())
				.andExpect(status().isOk());
	}

	void guardarCatalogo() throws Exception {
		BaseCatalogoModel base = FactoryClass.retornarCatalogoBase();
		this.mockMvc.perform(put("/catalogos/catalogos/guardar/{idCatalogo}", 1).content(Util.asJsonString(base)))
				.andDo(print()).andExpect(status().isOk());
	}

	void actualizarCatalogo() throws Exception {
		BaseCatalogoModel base = FactoryClass.retornarCatalogoBase();
		this.mockMvc.perform(post("/catalogos/catalogos/actualizar/{idCatalogo}", 1).content(Util.asJsonString(base)))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void obtenerInformacionCatalogoId() throws Exception {
		this.mockMvc.perform(get("/catalogos/catalogos/informacion/{idCatalogo}/{id}", 1, 1)).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void obtenerInformacionCatalogoMeta() throws Exception {
		this.mockMvc.perform(get("/catalogos/catalogos/informacion/meta/{idCatalogo}/{id}", 1, 1)).andDo(print())
				.andExpect(status().isOk());
	}
}
