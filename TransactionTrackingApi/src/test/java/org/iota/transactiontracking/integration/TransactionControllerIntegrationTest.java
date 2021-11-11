package org.iota.transactiontracking.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.iota.transactiontracking.api.domain.model.Transaction;
import org.iota.transactiontracking.api.domain.repository.TransactionRepository;
import org.iota.transactiontracking.api.dto.TransactionRequest;
import org.iota.transactiontracking.api.dto.TransactionUpdateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
  @Author:Dieudonne Takougang
  @Date: 17/10/2021
  @Description: Perform full integration tests for the rest api endpoints to  ensure all components working as expected
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class TransactionControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository transactionRepository;

    @Value("${api.apiAccessKey}")
    private String apiKey = "";

    private String apiBaseUrl = "/api/protected/transactions";
    private Transaction savedTransaction;

    @BeforeEach
    void setUp() {
        Transaction transaction = new Transaction();
        transaction.setTimestamp(1634473449593L);
        transaction.setConfirmed(Boolean.FALSE);
        transaction.setReceiver("Dieudonne");
        transaction.setSender("Takougang");
        transaction.setValue(1000L);
        transaction.setId("12222-aadd-123-as");
        savedTransaction = transactionRepository.save(transaction);
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
    }

    @Test
    void createTransaction_success_returns_201() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setSender("Dieudonne");
        transactionRequest.setReceiver("Takougang");
        transactionRequest.setConfirmed(Boolean.FALSE);
        transactionRequest.setTimestamp(1634473449593L);
        transactionRequest.setValue(2000L);
        RequestBuilder requestBuilder = post(apiBaseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "ApiKey " + apiKey)
                .content(new ObjectMapper().writeValueAsString(transactionRequest));
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void updateTransaction_success_returns_204() throws Exception {
        TransactionUpdateRequest transactionUpdateRequest = new TransactionUpdateRequest();
        transactionUpdateRequest.setSender("UpdatedSender");
        transactionUpdateRequest.setReceiver("UpdatedReceiver");
        transactionUpdateRequest.setConfirmed(Boolean.TRUE);
        transactionUpdateRequest.setValue(10000L);
        RequestBuilder requestBuilder = put(apiBaseUrl + "/{transactionId}", savedTransaction.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "ApiKey " + apiKey)
                .content(new ObjectMapper().writeValueAsString(transactionUpdateRequest))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void updateTransaction_failure_returns_transactionNotFound() throws Exception {

        TransactionUpdateRequest transactionUpdateRequest = new TransactionUpdateRequest();
        transactionUpdateRequest.setSender("UpdatedSender");
        transactionUpdateRequest.setReceiver("UpdatedReceiver");
        transactionUpdateRequest.setConfirmed(Boolean.TRUE);
        transactionUpdateRequest.setValue(10000L);
        RequestBuilder requestBuilder = put(apiBaseUrl + "/{transactionId}", "12add-add-ass")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "ApiKey " + apiKey)
                .content(new ObjectMapper().writeValueAsString(transactionUpdateRequest))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void getTransaction_success_returns_200() throws Exception {

        RequestBuilder requestBuilder = get(apiBaseUrl + "/{transactionId}", savedTransaction.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "ApiKey " + apiKey);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getTransaction_failure_when_transactionNotFound() throws Exception {
        RequestBuilder requestBuilder = get(apiBaseUrl + "/{transactionId}", "12-add-123-as")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "ApiKey " + apiKey);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void getTransactions_success_returns_200() throws Exception{

        RequestBuilder requestBuilder = get(apiBaseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "ApiKey " + apiKey);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void searchTransactions_success_returns_200() throws Exception{

        RequestBuilder requestBuilder = get(apiBaseUrl + "/searches")
                .header("Authorization", "ApiKey " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .param("transactionId",savedTransaction.getId());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionResource.size()").value(1))
                .andExpect(jsonPath("$.transactionResource[0].transactionId").value(savedTransaction.getId()))
                .andExpect(jsonPath("$.transactionResource[0].transactionValue").value(savedTransaction.getValue()))
                .andReturn();

    }

}
