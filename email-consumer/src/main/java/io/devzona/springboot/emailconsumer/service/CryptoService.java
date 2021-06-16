package io.devzona.springboot.emailconsumer.service;

public interface CryptoService {

    String encrypt(String plainText);

    String decrypt(String encryptedText);
}
