package com.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.InetSocketAddress;

import static java.util.UUID.fromString;

public class Application {

    public static void main(String[] args) throws IOException {

        final ApplicationConfiguration configuration = ApplicationConfiguration.INSTANCE;
        ObjectMapper objectMapper = configuration.createObjectMapper();
        FundTransferService service = configuration.createService();


        int serverPort = 8777;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);


        transferMoneyAPI(objectMapper, service, server);

        server.setExecutor(null);
        server.start();

    }

    private static void transferMoneyAPI(ObjectMapper objectMapper, FundTransferService service, HttpServer server) {
        server.createContext("/api/transfer-money", exchange -> {
            try {
                if ("POST".equals(exchange.getRequestMethod())) {

                    FundTransferRequest request = objectMapper.readValue(exchange.getRequestBody(), FundTransferRequest.class);
                    FundTransferResponse response = doPost(service, request);
                    byte[] bytes = objectMapper.writeValueAsBytes(response);
                    exchange.sendResponseHeaders(200, bytes.length);
                    OutputStream responseBody = exchange.getResponseBody();
                    responseBody.write(bytes);
                    responseBody.flush();
                } else {
                    throw new IllegalArgumentException("UnSupported Method Type");
                }
            } catch (Exception e) { //TODO: Make it Specific
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setCode(500);
                errorResponse.setMessage(e.getMessage());

                byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
                OutputStream responseBody = exchange.getResponseBody();
                responseBody.write(bytes);
                responseBody.flush();
            }
            exchange.close();
        });
    }


    private static FundTransferResponse doPost(FundTransferService service, FundTransferRequest request) {
        service.transfer(
                fromString(request.fromAccountNumber),
                fromString(request.toAccountNumber),
                new BigDecimal(request.amount));

        FundTransferResponse response = new FundTransferResponse();
        response.setStatusCode(200);
        response.setMessage("Success");

        return response;
    }

    private static class FundTransferRequest {
        String fromAccountNumber;
        String toAccountNumber;
        Double amount;

        public String getFromAccountNumber() {
            return fromAccountNumber;
        }

        public void setFromAccountNumber(String fromAccountNumber) {
            this.fromAccountNumber = fromAccountNumber;
        }

        public String getToAccountNumber() {
            return toAccountNumber;
        }

        public void setToAccountNumber(String toAccountNumber) {
            this.toAccountNumber = toAccountNumber;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }
    }

    private static class FundTransferResponse {
        int statusCode;
        String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }
    }

    private static class ErrorResponse {
        int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
