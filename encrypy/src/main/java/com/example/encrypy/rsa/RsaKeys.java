package com.example.encrypy.rsa;

/**
 * rsa加解密用的公钥和私钥
 * @author Administrator
 *
 */
public class RsaKeys {

	//生成秘钥对的方法可以参考这篇帖子
	//https://www.cnblogs.com/yucy/p/8962823.html
//	公钥：
//	MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwYUA+IvrCTFry+48Futz
//	ykuFWWc+zoQIn/eQPGQMVtLtJm8g+lD3RMWaP+c7cik6NULTVVYQcEhvNBls/du+
//	FMF0Mb8PUVc1E00hAwpOdDgl8+7eWz77co8c+06tI1c4aj9zjFyUv13W968hjlGE
//	zGXq4XPUEhTvUm+gQ3j5gqR3MktFoXQmkMIl2l/OiVf6hA9qRd+rpYbyFDM6xU75
//	CHJbUd43DVXCAJsDdSvuZcCbeDoXIz30jsbe5ZZQuzz610fQW6dLGJDJ20/ERS8J
//	AI6LIOT8LsgwlwpTGxs/HNAwQ08ha2oO+0Qegn8Wo3tFi1E1Fj/lfxQG3hdOfSJiGwIDAQAB
//
// 私钥：
//	MIIEowIBAAKCAQEAwYUA+IvrCTFry+48FutzykuFWWc+zoQIn/eQPGQMVtLtJm8g
//+lD3RMWaP+c7cik6NULTVVYQcEhvNBls/du+FMF0Mb8PUVc1E00hAwpOdDgl8+7e
//	Wz77co8c+06tI1c4aj9zjFyUv13W968hjlGEzGXq4XPUEhTvUm+gQ3j5gqR3MktF
//	oXQmkMIl2l/OiVf6hA9qRd+rpYbyFDM6xU75CHJbUd43DVXCAJsDdSvuZcCbeDoX
//	Iz30jsbe5ZZQuzz610fQW6dLGJDJ20/ERS8JAI6LIOT8LsgwlwpTGxs/HNAwQ08h
//	a2oO+0Qegn8Wo3tFi1E1Fj/lfxQG3hdOfSJiGwIDAQABAoIBADEq5MtBaW8Fgnjm
//	x5efLOUp+PB1He0EeNiD4r6lQMlSf/Ry6bMPnTN47S7tRhpERUjA+MbGvhh5Ht/i
//0eFR0mKdoDL/cxnciVylDQ0jsjkvBt2KRC50uxgpKljjbXP8NhYwluADqbtRUUvO
//0DzDk3UHM/0iG7CYZxHP0K0z8FjzlGkojEGWcoUk+BHhFm3c2yspD/Ox6RueHG1X
//			ZaxKwVvspTRHYC3mgB2md1tbQvx0osVyVDGCrEuk6aPHRHuohD78AvS3OUxJpG2p
//	BSgrw7ZDVuaPl6ZQHBxtcRLuJ9rLKWZXcvZa7asqSCcJLsXuSwSf+hxuJOddKyG8
//	m+4MjlECgYEA++QdpuYA8ez12bRbh4DstDdd/50LAhXqJQl8cFX2JVYi4PhpzBxq
//	DmAz3/WCe6B4lSPXZAI4mSjaS1drfrTN2i+MeGXYWvvmP/UP+Z/NpXKe0LyOZUME
//	ZTojMk69BAMGX0/y/6BXopTQ0EHDYdUtCmNMBhe2YStuzpD2+ifCAPMCgYEAxK0h
//	pwBotAYApOhU/RUhG5bPSZfAdp4HxpPfSW7Fg6l8RvA9DBtzjNGzJlxt9ZSwQpDC
//	bTVdBm8dZrpqlvE3pIOg7mNVS4k18XUCa2npyvZroNdU4TY2LH2Mt/g2ZiJ1k508
//			CnU1E7v6sC7oucE0njcWWHPyrErhftso0j16JDkCgYARX2JMKIDYLqemJDJL3jB1
//	HXx2LLadpjVH6KeUd9lKKe6t5XQmh68o3qm1QJGHRUILxzULyLtulMq5d4YKbx3j
//	dnQRubR6UfVHY/d+F+9vAcDCVL7dbQnYkXHr/lI6/Jpr6G+yAbUJmYntUxvOm41H
//	siGEKg6CFSTHSmg+SI+w0QKBgQC4nL8C8JpXqW6/XKoyKbw7FnOa48wwS6SG3NTy
//	K/1Q2i2itOzIyZfMLibYslgw9hIBWbp3ImkgmfpiLEfTSbduQHQ2cJ1m2vP8o1XT
//	F8bx0h8zCg1NnUJ6vprxMRd3BY+SMTxjoKNi08e7yzx+6IGIpbLe8m9vFJbYKcYG
//	mthk2QKBgG3XadpfcRobAVoa4cibERzmoXob51TmxP5SsC5N0zBrISE7r7Hf/Ofi
//			VeV0QFL6LCQWLmAFLGVrFx36SVqasadw9P3B1tKEmFfwxjRVxiapWUulsC9KigtH
//	AfIokFLPdAeUWCu4BIX8/EmeeGNYTaUi9PDQ4wHqO9IfxEOVFOp5
	//服务器公钥
	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwYUA+IvrCTFry+48Futz\n" +
			"ykuFWWc+zoQIn/eQPGQMVtLtJm8g+lD3RMWaP+c7cik6NULTVVYQcEhvNBls/du+\n" +
			"FMF0Mb8PUVc1E00hAwpOdDgl8+7eWz77co8c+06tI1c4aj9zjFyUv13W968hjlGE\n" +
			"zGXq4XPUEhTvUm+gQ3j5gqR3MktFoXQmkMIl2l/OiVf6hA9qRd+rpYbyFDM6xU75\n" +
			"CHJbUd43DVXCAJsDdSvuZcCbeDoXIz30jsbe5ZZQuzz610fQW6dLGJDJ20/ERS8J\n" +
			"AI6LIOT8LsgwlwpTGxs/HNAwQ08ha2oO+0Qegn8Wo3tFi1E1Fj/lfxQG3hdOfSJi\n" +
			"GwIDAQAB";

	//服务器私钥(经过pkcs8格式处理)
	private static final String serverPrvKeyPkcs8 = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDBhQD4i+sJMWvL\n" +
			"7jwW63PKS4VZZz7OhAif95A8ZAxW0u0mbyD6UPdExZo/5ztyKTo1QtNVVhBwSG80\n" +
			"GWz9274UwXQxvw9RVzUTTSEDCk50OCXz7t5bPvtyjxz7Tq0jVzhqP3OMXJS/Xdb3\n" +
			"ryGOUYTMZerhc9QSFO9Sb6BDePmCpHcyS0WhdCaQwiXaX86JV/qED2pF36ulhvIU\n" +
			"MzrFTvkIcltR3jcNVcIAmwN1K+5lwJt4OhcjPfSOxt7lllC7PPrXR9Bbp0sYkMnb\n" +
			"T8RFLwkAjosg5PwuyDCXClMbGz8c0DBDTyFrag77RB6Cfxaje0WLUTUWP+V/FAbe\n" +
			"F059ImIbAgMBAAECggEAMSrky0FpbwWCeObHl58s5Sn48HUd7QR42IPivqVAyVJ/\n" +
			"9HLpsw+dM3jtLu1GGkRFSMD4xsa+GHke3+LR4VHSYp2gMv9zGdyJXKUNDSOyOS8G\n" +
			"3YpELnS7GCkqWONtc/w2FjCW4AOpu1FRS87QPMOTdQcz/SIbsJhnEc/QrTPwWPOU\n" +
			"aSiMQZZyhST4EeEWbdzbKykP87HpG54cbVdlrErBW+ylNEdgLeaAHaZ3W1tC/HSi\n" +
			"xXJUMYKsS6Tpo8dEe6iEPvwC9Lc5TEmkbakFKCvDtkNW5o+XplAcHG1xEu4n2ssp\n" +
			"Zldy9lrtqypIJwkuxe5LBJ/6HG4k510rIbyb7gyOUQKBgQD75B2m5gDx7PXZtFuH\n" +
			"gOy0N13/nQsCFeolCXxwVfYlViLg+GnMHGoOYDPf9YJ7oHiVI9dkAjiZKNpLV2t+\n" +
			"tM3aL4x4Zdha++Y/9Q/5n82lcp7QvI5lQwRlOiMyTr0EAwZfT/L/oFeilNDQQcNh\n" +
			"1S0KY0wGF7ZhK27OkPb6J8IA8wKBgQDErSGnAGi0BgCk6FT9FSEbls9Jl8B2ngfG\n" +
			"k99JbsWDqXxG8D0MG3OM0bMmXG31lLBCkMJtNV0Gbx1mumqW8Tekg6DuY1VLiTXx\n" +
			"dQJraenK9mug11ThNjYsfYy3+DZmInWTnTwKdTUTu/qwLui5wTSeNxZYc/KsSuF+\n" +
			"2yjSPXokOQKBgBFfYkwogNgup6YkMkveMHUdfHYstp2mNUfop5R32Uop7q3ldCaH\n" +
			"ryjeqbVAkYdFQgvHNQvIu26Uyrl3hgpvHeN2dBG5tHpR9Udj934X728BwMJUvt1t\n" +
			"CdiRcev+Ujr8mmvob7IBtQmZie1TG86bjUeyIYQqDoIVJMdKaD5Ij7DRAoGBALic\n" +
			"vwLwmlepbr9cqjIpvDsWc5rjzDBLpIbc1PIr/VDaLaK07MjJl8wuJtiyWDD2EgFZ\n" +
			"unciaSCZ+mIsR9NJt25AdDZwnWba8/yjVdMXxvHSHzMKDU2dQnq+mvExF3cFj5Ix\n" +
			"PGOgo2LTx7vLPH7ogYilst7yb28Ultgpxgaa2GTZAoGAbddp2l9xGhsBWhrhyJsR\n" +
			"HOahehvnVObE/lKwLk3TMGshITuvsd/85+JV5XRAUvosJBYuYAUsZWsXHfpJWpqx\n" +
			"p3D0/cHW0oSYV/DGNFXGJqlZS6WwL0qKC0cB8iiQUs90B5RYK7gEhfz8SZ54Y1hN\n" +
			"pSL08NDjAeo70h/EQ5UU6nk=";

	public static String getServerPubKey() {
		return serverPubKey;
	}

	public static String getServerPrvKeyPkcs8() {
		return serverPrvKeyPkcs8;
	}
	
}
