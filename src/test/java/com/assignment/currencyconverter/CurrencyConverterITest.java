package com.assignment.currencyconverter;

import com.assignment.currencyconverter.model.CurrencyConverterRequestDto;
import com.assignment.currencyconverter.model.enums.Currency;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyConverterITest {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    void converter() throws Exception {

        var verifyRequest = new CurrencyConverterRequestDto(new BigDecimal(100), Currency.EUR, Currency.USD);

        var result = mvc.perform(MockMvcRequestBuilders.post("/converter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(verifyRequest)))
                .andExpect(status().isOk())
                .andReturn();


        var asd = result;
//        var response = objectMapper.readValue(
//                result.getResponse().getContentAsString(),
//                new TypeReference<BaseResponseDto<RevertResponseDto>>() {
//                }
//        ).getResponse();

    }
}