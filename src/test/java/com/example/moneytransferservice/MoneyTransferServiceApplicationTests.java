package com.example.moneytransferservice;

import com.example.moneytransferservice.dto.Amount;
import com.example.moneytransferservice.dto.Operation;
import com.example.moneytransferservice.dto.Transfer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    public static GenericContainer<?> container = new GenericContainer<>("appserver")
            .withExposedPorts(8080);

    @BeforeAll
    public static void setUp() {
        container.start();
    }

    @Test
    void testTransfer() throws URISyntaxException {
        final Integer port = container.getMappedPort(8080);
        final String url = "http://localhost:" + port + "/transfer";
        final URI uri = new URI(url);
        final Amount amount = new Amount(500, "RUB");
        final Transfer transfer = new Transfer("1111111111111111", "02/22", "111", "2222222222222222", amount);

        final HttpEntity<Transfer> request = new HttpEntity<>(transfer);

        final ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        Assertions.assertNotNull(result);
    }

    @Test
    void testConfirmOperation() throws URISyntaxException {
        final Integer port = container.getMappedPort(8080);
        final String url = "http://localhost:" + port + "/confirmOperation";
        final URI uri = new URI(url);
        final Operation operation = new Operation("123", "456");

        final HttpEntity<Operation> request = new HttpEntity<>(operation);

        final ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        Assertions.assertEquals("{\"operationId\":\"Success\"}", result.getBody());
    }
}
