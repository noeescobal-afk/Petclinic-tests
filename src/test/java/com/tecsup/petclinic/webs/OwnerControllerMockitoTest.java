package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.mappers.OwnerMapper;
import com.tecsup.petclinic.owner.Owner;
import com.tecsup.petclinic.owner.OwnerNotFoundException;
import com.tecsup.petclinic.owner.OwnerService;
import com.tecsup.petclinic.util.OwnerTObjectCreator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class OwnerControllerMockitoTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OwnerMapper mapper;

    @MockitoBean
    private OwnerService ownerService;

    @Test
    public void testUpdateOwner() throws Exception {
        OwnerDTO newOwnerTO = OwnerTObjectCreator.getOwnerTO();
        Owner createdOwner = mapper.mapToEntity(newOwnerTO);
        createdOwner.setId(10000);

        Mockito.when(ownerService.create(mapper.mapToEntity(newOwnerTO)))
                .thenReturn(createdOwner);

        ResultActions mvcActions = mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        OwnerDTO updatedOwnerTO = OwnerTObjectCreator.getOwnerTO();
        updatedOwnerTO.setId(id);
        updatedOwnerTO.setAddress("Av. Arequipa 456");
        updatedOwnerTO.setCity("Arequipa");
        Owner updatedOwner = mapper.mapToEntity(updatedOwnerTO);

        Mockito.when(ownerService.findById(id)).thenReturn(createdOwner);
        Mockito.when(ownerService.update(updatedOwner)).thenReturn(updatedOwner);

        mockMvc.perform(put("/owners/" + id)
                        .content(om.writeValueAsString(updatedOwnerTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address", is(updatedOwnerTO.getAddress())))
                .andExpect(jsonPath("$.city", is(updatedOwnerTO.getCity())));

        Mockito.verify(ownerService).update(updatedOwner);
        Mockito.when(ownerService.findById(id)).thenReturn(updatedOwner);

        mockMvc.perform(get("/owners/" + id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.address", is(updatedOwnerTO.getAddress())))
                .andExpect(jsonPath("$.city", is(updatedOwnerTO.getCity())));
    }

    @Test
    public void testDeleteOwner() throws Exception {
        OwnerDTO newOwnerTO = OwnerTObjectCreator.getOwnerTO();
        Owner createdOwner = mapper.mapToEntity(newOwnerTO);
        createdOwner.setId(10000);

        Mockito.when(ownerService.create(mapper.mapToEntity(newOwnerTO)))
                .thenReturn(createdOwner);

        ResultActions mvcActions = mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        Mockito.doNothing().when(ownerService).delete(id);

        mockMvc.perform(delete("/owners/" + id))
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(ownerService).delete(id);
    }

    @Test
    public void testDeleteOwnerKO() throws Exception {
        Integer ID_NOT_EXIST = 1000;
        Mockito.doThrow(new OwnerNotFoundException("Record not found...!"))
                .when(ownerService).delete(ID_NOT_EXIST);

        mockMvc.perform(delete("/owners/" + ID_NOT_EXIST))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
