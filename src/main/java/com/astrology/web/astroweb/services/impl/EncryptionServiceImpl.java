package com.astrology.web.astroweb.services.impl;

import com.astrology.web.astroweb.services.EncryptionService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.commons.CommonUtils;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceImpl implements EncryptionService {
 
    private StrongPasswordEncryptor strongEncryptor;
    
    private final Logger log = LogManager.getLogger(EncryptionServiceImpl.class);
 
    @Autowired
    public void setStrongEncryptor(StrongPasswordEncryptor strongEncryptor) {
        this.strongEncryptor = strongEncryptor;
    }
 
    private String encryptString(String input){
    	if (CommonUtils.isEmpty(input)) {
    		log.warn("Password is empty");
    	}
        return strongEncryptor.encryptPassword(input);
    }
 
    private boolean checkPassword(String plainPassword, String encryptedPassword){
    	if (CommonUtils.isEmpty(plainPassword)) {
    		log.warn("Input Password is empty");
    	}
        return strongEncryptor.checkPassword(plainPassword, encryptedPassword);
    }

	/*@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword!=null?encryptString(rawPassword.toString()):encryptString("");
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return rawPassword!=null?checkPassword(rawPassword.toString(),encodedPassword):checkPassword("",encodedPassword);
	}*/
}
