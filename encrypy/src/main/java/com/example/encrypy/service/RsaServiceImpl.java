package com.example.encrypy.service;


import com.example.encrypy.rsa.Base64Utils;
import com.example.encrypy.rsa.RSA;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;


//rsa加密

@Service("RsaService")
public class RsaServiceImpl implements RsaService {

    //rsa解密
    public String RSADecryptDataPEM(String encryptData, String prvKey) throws Exception {
    	byte[] encryptBytes = encryptData.getBytes();
        byte[] prvdata = RSA.decryptByPrivateKey(Base64Utils.decode(encryptData), prvKey);

        String outString = new String(prvdata, "UTF-8");
        return outString;
    }

    @Override
	public String RSADecryptDataBytes(byte[] encryptBytes, String prvKey)
			throws Exception {
    	byte[] prvdata = RSA.decryptByPrivateKey(encryptBytes, prvKey);
        String outString = new String(prvdata, "utf-8");
        return outString;
	}

    //rsa加密
    public String RSAEncryptDataPEM(String data, String pubKey) throws Exception {

        byte[] pubdata = RSA.encryptByPublicKey(data.getBytes("UTF-8"), pubKey);
        String outString = new String(Base64Utils.encode(pubdata));

        return outString;
    }

	@Override
	public String getRsaAlgorithm() {

		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return keyFactory.getAlgorithm();
	}


}
