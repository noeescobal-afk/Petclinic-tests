package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.util.OwnerTObjectCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class OwnerControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUpdateOwner() throws Exception {
        OwnerDTO newOwnerTO = OwnerTObjectCreator.getOwnerTO();

        ResultActions mvcActions = mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        newOwnerTO.setAddress("Av. Arequipa 456");
        newOwnerTO.setCity("Arequipa");

        mockMvc.perform(put("/owners/" + id)
                        .content(om.writeValueAsString(newOwnerTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/owners/" + id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.address", is(newOwnerTO.getAddress())))
                .andExpect(jsonPath("$.city", is(newOwnerTO.getCity())));

        // Limpiar el propietario creado durante la prueba.
        mockMvc.perform(delete("/owners/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteOwner() throws Exception {
        OwnerDTO newOwnerTO = OwnerTObjectCreator.getOwnerTO();

        ResultActions mvcActions = mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/owners/" + id))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/owners/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteOwnerKO() throws Exception {
        mockMvc.perform(delete("/owners/1000"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


// ===== INTEGRANTE B: Creación =====

    @Test
    public void testCreateOwner() throws Exception {

        String FIRST_NAME = "Carlos";
        String LAST_NAME = "Lajara";
        String ADDRESS = "Av. Test 123";
        String CITY = "Lima";
        String TELEPHONE = "999888777";

        OwnerDTO newOwnerTO = new OwnerDTO();
        newOwnerTO.setFirstName(FIRST_NAME);
        newOwnerTO.setLastName(LAST_NAME);
        newOwnerTO.setAddress(ADDRESS);
        newOwnerTO.setCity(CITY);
        newOwnerTO.setTelephone(TELEPHONE);

        mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.address", is(ADDRESS)))
                .andExpect(jsonPath("$.city", is(CITY)))
                .andExpect(jsonPath("$.telephone", is(TELEPHONE)));
    }

    @Test
    public void testCreateOwner_BadRequest() throws Exception {

        OwnerDTO invalidOwnerTO = new OwnerDTO();   // sin lastName
        invalidOwnerTO.setFirstName("Sin Apellido");
        invalidOwnerTO.setCity("Lima");

        mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(invalidOwnerTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFindByLastName() throws Exception {

        String LAST_NAME = "Davis";
        int NRO_RECORD = 2;

        mockMvc.perform(get("/owners").param("lastName", LAST_NAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", is(NRO_RECORD)))
                .andExpect(jsonPath("$[0].lastName", is(LAST_NAME)));
    }
}