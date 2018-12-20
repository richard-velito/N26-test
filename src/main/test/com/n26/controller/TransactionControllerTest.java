package com.n26.controller;

import com.n26.domain.Transaction;
import com.n26.exception.OlderTransactionException;
import com.n26.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TransactionController.class, secure = false)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void testCreateTransactionUnprocessableEntity () throws Exception {
        String json = "{\"amount\":\"77.76\",\"timestamp\":\"2019-12-16T18:50:10.002Z\"}";
        TransactionService spy = Mockito.spy(transactionService);
        Mockito.doNothing().when(spy).addTransaction(any(Transaction.class), any(Long.class));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/transactions")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
    }

    @Test
    public void testCreateTransactionInvalidJson () throws Exception {
        TransactionService spy = Mockito.spy(transactionService);
        Mockito.doNothing().when(spy).addTransaction(any(Transaction.class), any(Long.class));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/transactions")
                .content("WORLD")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void testCreateTransactionOlderTransaction () throws Exception {
        String json = "{\"amount\":\"77.76\",\"timestamp\":\"2018-12-16T18:50:10.002Z\"}";
        TransactionService spy = Mockito.spy(transactionService);
        Mockito.doThrow(OlderTransactionException.class).when(spy).addTransaction(any(Transaction.class), any(Long.class));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/transactions")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

}
