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
	
	
	@RequestMapping("getUFOBalance")
	@ResponseBody
	public JSONResponse getUFOBalance(String addr) throws Exception {
		JSONResponse res = new JSONResponse();
		res.put("balance", web3jUtil.getUFOBalance(addr));
		res.put("symbol", web3jUtil.getUFOSymbol());
		return res;
	}
	
	@RequestMapping("sendUFOTransaction")
	@ResponseBody
	public JSONResponse sendUFOTransaction(String from, String to, String pwd, BigInteger amount) throws Exception {
		JSONResponse res = new JSONResponse();
		res.put("transaction", web3jUtil.sendUFOTransaction(from, to, pwd, amount));
		return res;
	}
	
	@RequestMapping("sendUFOTransactionWithOrder")
	@ResponseBody
	public JSONResponse sendUFOTransactionWithOrder(String from, String to, String pwd, BigInteger amount, String orderNo) throws Exception {
		JSONResponse res = new JSONResponse();
		res.put("transaction", web3jUtil.sendUFOTransactionWithOrder(from, to, pwd, amount, orderNo));
		return res;
	}
	
	@RequestMapping("getUFOTxDetailByHash")
	@ResponseBody
	public JSONResponse getUFOTxDetailByHash(String txHash) throws Exception {
		JSONResponse res = new JSONResponse();
		res.put("transaction", web3jUtil.getUFOTxDetailByHash(txHash));
		return res;
	}
	
	@RequestMapping("getUFOTxDetailByOrderNo")
	@ResponseBody
	public JSONResponse getUFOTansferEvents(String orderNo) throws Exception {
		JSONResponse res = new JSONResponse();
		res.put("transaction", web3jUtil.getUFOTxDetailByOrderNo(orderNo));
		return res;
	}

}
