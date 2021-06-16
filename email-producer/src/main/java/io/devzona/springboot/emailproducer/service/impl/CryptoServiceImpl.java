package io.devzona.springboot.emailproducer.service.impl;

import io.devzona.springboot.emailproducer.service.CryptoService;
import org.springframework.stereotype.Service;

@Service
public class CryptoServiceImpl implements CryptoService {

    @Override
    public String encrypt(String plainText) {
        // Write your logic to encrypt password
        return plainText;
    }

    @Override
    public String decrypt(String encryptedText) {
        // Write your logic to decrypt password
        return encryptedText;
    }
}
