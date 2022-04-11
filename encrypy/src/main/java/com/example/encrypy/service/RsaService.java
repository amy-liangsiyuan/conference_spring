package com.example.encrypy.service;

//rsa加密服务

public interface RsaService {

	//rsa解密
	public String RSADecryptDataPEM(String encryptData, String prvKey) throws Exception;

	//rsa解密
	public String RSADecryptDataBytes(byte[] encryptBytes, String prvKey) throws Exception;

    //rsa加密
	public String RSAEncryptDataPEM(String data, String pubKey) throws Exception;

	public String getRsaAlgorithm();
}
