package com.swinginwind.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;

import com.swinginwind.blockchain.util.Web3jUtil;
import com.swinginwind.certificate.model.Certificate;
import com.swinginwind.core.utils.Identities;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:applicationContext-test.xml" })
public class BlockChainTest {

	@Autowired
	private Web3jUtil web3jUtil;

	@Test
	public void test() throws Exception {
		System.out.println(web3jUtil.getVersion());
		System.out.println(web3jUtil.getAccounts());
		// System.out.println(web3jUtil.getBlockNumber());
		// List<String> accounts = web3jUtil.getAccounts();
		// System.out.println(web3jUtil.getBalance(accounts.get(0)));
		// System.out.println(web3jUtil.getBalance(accounts.get(1)));
		// System.out.println(Web3jUtil.sendTransaction(accounts.get(0),
		// accounts.get(1), "11111111", new
		// BigInteger("10000000000000000000")));
		// System.out.println(Web3jUtil.newAccount("dmj"));

		// String symbol = web3jUtil.getUFOSymbol();
		// System.out.println(web3jUtil.getUFOBalance(accounts.get(0)) + " " +
		// symbol);
		// System.out.println(web3jUtil.getUFOBalance(accounts.get(1)) + " " +
		// symbol);
		// System.out.println(
		// web3jUtil.sendUFOTransaction(accounts.get(0), accounts.get(1),
		// "11111111", new BigInteger("1000")));
		//System.out.println(web3jUtil.createWallet("22aixiaomeidan"));
		// System.out.println(web3jUtil.updateWallet("0x7994885e2a046f7e3303a1d0f15ef1e74de1022a",
		// "12345", "123456"));
		// System.out.println(web3jUtil.updateWallet("0x7994885e2a046f7e3303a1d0f15ef1e74de1022a",
		// "12345", "123456"));
		// System.out.println(web3jUtil.estimateEthTxGas("0x7994885e2a046f7e3303a1d0f15ef1e74de1022a",
		// "0x7994885e2a046f7e3303a1d0f15ef1e74de10221", new
		// BigInteger("10000000000")));
		/*
		 * System.out.println(web3jUtil.estimateFunctionCallTxGas(
		 * "0x7994885e2a046f7e3303a1d0f15ef1e74de1022a",
		 * "0x7994885e2a046f7e3303a1d0f15ef1e74de10221", BigInteger.ZERO, new
		 * Function( "transfer", Arrays.<Type>asList(new
		 * org.web3j.abi.datatypes.Address(
		 * "0x7994885e2a046f7e3303a1d0f15ef1e74de10221"), new
		 * org.web3j.abi.datatypes.generated.Uint256(new BigInteger("1000"))),
		 * Collections.<TypeReference<?>>emptyList())));
		 */
		// System.out.println("0x" + TypeEncoder.encode(new
		// Address("0x7994885e2a046f7e3303a1d0f15ef1e74de10221")));
		// web3jUtil.getUFOTansferWithOrderEvents("0x" + TypeEncoder.encode(new
		// Address("0x77d45e310abb70c358b94241be9ca19ca554a0cb")));
		// web3jUtil.getUFOTxsByAccount("0x77d45e310abb70c358b94241be9ca19ca554a0cb");
		//String from = "0xcb257df64a30f96c3ce4aff16c81b3ee6f7bdf2a";
		//String pwd = "22aixiaomeidan";
/*		BigInteger amount = new BigInteger("50000000000");
*/		//BigInteger amount = new BigInteger("7800000000000");
		/*String[] toArray = "0x81824cd167b903cd7049980aef106586157630e2,0x00d577289c05a48fa433d61a7037b63e1006f544,0xed7188e75c45aacec9fc1dde2b4ae35e49a50d4a,0xC341903f0bf210EF199bD62eE531EA01dee06509,0x5773d41dee950d2622767c60ac8595828e4af0d1,0xa0faea2e7892e751757316b91674eb0f7c3c10fc,0x123223bd8f4fa3a84f96f27a883524b980a1bfe6,0x8a77d3087d16b7b808576801a47b6e620103e20c,0x32a0d890b8dbedc0e87df49938f8db6d1d684090,0x42a15e19fa6cca598960ae816114d70772e9cddc,0x99df2813fba984da58c20cc4d68e3354a4440fd2,0xdaD084047d060d137b1510b4099D0e5e4C1BECdC,0xa0faea2e7892e751757316b91674eb0f7c3c10fc,0xa0faea2e7892e751757316b91674eb0f7c3c10fc,0xa0faea2e7892e751757316b91674eb0f7c3c10fc,0xa0faea2e7892e751757316b91674eb0f7c3c10fc,0xa0faea2e7892e751757316b91674eb0f7c3c10fc,0xa0faea2e7892e751757316b91674eb0f7c3c10fc,0xfcda9f67f244d45a9f851bedcb1f3b3e4d4cf94e,0x35d6B743e3c3c24F49e5096E3392279B8bb23BDf,0x07dffbAB7cdF535719b8ADe368D6F94e43Cd1cDe,0x8d1cd685223a90711ce7522fc3e34ba4601b1206,0xcd33956b0f28aa9fe2912459e15ff26bd761418b,0xCE928cE8fe16EE0EC71c4C090e9484FB5f2c8e88,0xCE928cE8fe16EE0EC71c4C090e9484FB5f2c8e88,0x7ae42e0e6dd25f6748978b04ba24cb9c3ab7058c,0x7ae42e0e6dd25f6748978b04ba24cb9c3ab7058c,0x7ae42e0e6dd25f6748978b04ba24cb9c3ab7058c,0x7ae42e0e6dd25f6748978b04ba24cb9c3ab7058c,0x99df2813fba984da58c20cc4d68e3354a4440fd2,0x99df2813fba984da58c20cc4d68e3354a4440fd2,0xa0faea2e7892e751757316b91674eb0f7c3c10fc,0xC341903f0bf210EF199bD62eE531EA01dee06509,0x81d83975778321117bdb702f1ac7fa86e992fa95,0xaa2d5a9144d8a558ecf88861079cb726ffd2084c,0x6e5d8d79774be384d0db8d4fd2a153be33f5ccfc,0xbae12ba33565bf82aaeaf466af8dcc9adf7ce12e,0x2953385aa185ee8f8235d835d31183993d424a7f,0x8a77d3087d16b7b808576801a47b6e620103e20c,0xc32dc9D51dE2C913a59Ab806860cBAf6D99b1005,0xc32dc9D51dE2C913a59Ab806860cBAf6D99b1005,0xac543345dd27cfb931c655baf8791da2826510b8,0x96c2EF9513627f3aB389f94DCDF8E05692688542,0x8488f1701b75c96A559FD67497ABB879B646CF92"
				.split(",");*/
		//String[] toArray = "0xF1E40ab7573BdcBd033165628e83d376AD55fa52"
				//.split(",");
		//System.out.println(toArray.length);
		System.out.println("Accounts: " + web3jUtil.getAccounts());
		System.out.println("XTT Balance: " + web3jUtil.getCertBalance(web3jUtil.getAccounts().get(1)));
		Certificate certificate = new Certificate();
		certificate.setCertName("123");
		certificate.setId("1e48868390b64bb2ae6b5a74f0bd8c0c");
		//System.out.println(web3jUtil.updateCertificate(certificate, web3jUtil.getAdminAccount(), web3jUtil.getAdminPwd()));
		//System.out.println(web3jUtil.getCreateCertificateEvents("0x821c6efdd0e49fe9fcdc57a83e0eda1f6975ef8fc1b7aecb3ae2e9abdd47b808"));
		//System.out.println(web3jUtil.deleteCertificate(certificate.getId(), web3jUtil.getAdminAccount(), web3jUtil.getAdminPwd()));
		//System.out.println(web3jUtil.increSupply(new BigInteger("1000"), web3jUtil.getAdminAccount(), web3jUtil.getAdminPwd()));
		//System.out.println(web3jUtil.getCertificate(certificate.getId()));
		//System.out.println(web3jUtil.createWallet("123"));
		//System.out.println("ETH Balance: " + web3jUtil.getBalance(from));
		/*System.out.println("Transaction: "
				+ web3jUtil.sendUFOTransaction(from, "0x81824cd167b903cd7049980aef106586157630e2", pwd, amount));*/
		//int i = 0;
		/*for(String to : toArray) {
			System.out.println("Transaction To: " + to + ","
					+ web3jUtil.sendUFOTransaction(from, to, pwd, amount));
			System.out.println("Balance Of " + to + " : "
					+ web3jUtil.getUFOBalance(to).longValue()/100000000);
			i ++;
		}*/
	}

}
