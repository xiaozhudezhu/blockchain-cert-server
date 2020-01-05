package com.swinginwind.blockchain.controller;

import java.io.IOException;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swinginwind.blockchain.util.Web3jUtil;
import com.swinginwind.core.pager.JSONResponse;

@Controller
@RequestMapping("blockchain")
public class BlockChainController {
	
	@Autowired
	private Web3jUtil web3jUtil;
	
	
	@RequestMapping("newAccount")
	@ResponseBody
	public JSONResponse newAccount(String pwd) throws IOException {
		JSONResponse res = new JSONResponse();
		res.put("account", web3jUtil.newAccount(pwd));
		return res;
	}
	
	
	@RequestMapping("getAccounts")
	@ResponseBody
	public JSONResponse getAccounts() throws IOException {
		JSONResponse res = new JSONResponse();
		res.put("accounts", web3jUtil.getAccounts());
		return res;
	}
	
	
	/*@RequestMapping("getCertBalance")
	@ResponseBody
	public JSONResponse getCertBalance(String addr) throws Exception {
		JSONResponse res = new JSONResponse();
		res.put("balance", web3jUtil.getCertBalance(addr));
		res.put("symbol", web3jUtil.getCertSymbol());
		return res;
	}
	
	@RequestMapping("sendCertTransaction")
	@ResponseBody
	public JSONResponse sendCertTransaction(String from, String to, String pwd, BigInteger amount) throws Exception {
		JSONResponse res = new JSONResponse();
		res.put("transaction", web3jUtil.sendCertTransaction(from, to, pwd, amount));
		return res;
	}
	
	@RequestMapping("getCertTxDetailByHash")
	@ResponseBody
	public JSONResponse getCertTxDetailByHash(String txHash) throws Exception {
		JSONResponse res = new JSONResponse();
		res.put("transaction", web3jUtil.getCertTxDetailByHash(txHash));
		return res;
	}
	
	@RequestMapping("getCertTxDetailByOrderNo")
	@ResponseBody
	public JSONResponse getCertTansferEvents(String orderNo) throws Exception {
		JSONResponse res = new JSONResponse();
		res.put("transaction", web3jUtil.getCertTxDetailByOrderNo(orderNo));
		return res;
	}*/

}
