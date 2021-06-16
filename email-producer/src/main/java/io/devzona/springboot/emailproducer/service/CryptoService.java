package io.devzona.springboot.emailproducer.service;

public interface CryptoService {

    String encrypt(String plainText);

    String decrypt(String encryptedText);
}
