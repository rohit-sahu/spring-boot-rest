package io.devzona.springboot.apachekafkaproducer.service;

public interface CryptoService {

    String encrypt(String plainText);

    String decrypt(String encryptedText);
}
