package io.devzona.springboot.emailproducer.service;

import java.util.concurrent.CompletableFuture;

public interface EmailService {

    CompletableFuture<Boolean> sendFormCompletionSmsToCustomer(Object object);
}
