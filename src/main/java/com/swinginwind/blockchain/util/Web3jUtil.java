package com.swinginwind.blockchain.util;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.EthLog.LogResult;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;

import com.swinginwind.blockchain.token.Ufo;
import com.swinginwind.blockchain.token.Ufo.TransferEventResponse;
import com.swinginwind.blockchain.token.Ufo.TransferWithOrderEventResponse;

import rx.functions.Action1;

/**
 * web3j调用工具类
 * 
 * @author hadoop
 *
 */
public class Web3jUtil {

	/**
	 * 私链服务地址
	 */
	private String url = "http://193.112.206.28:8545";

	/**
	 * UFO token地址
	 */
	private String ufoTokenAddress = "0x4E8d21306551b0D131B2FCCeEA4e640e5aeC436E";

	/**
	 * geth keystore目录
	 */
	private String keystoreFolder = "D:/Geth/chain/keystore";

	/**
	 * gasLimit
	 */
	private long gasLimit = 100000;
	/**
	 * gasPrice
	 */
	private long gasPrice = 10000L;

	private String adminAccount;

	private String adminPwd;

	private BigInteger userInitBalance;

	private Admin web3;

	public void init() {
		Ufo contract = Ufo.load(ufoTokenAddress, getWeb3Client(), Credentials.create(ufoTokenAddress),
				BigInteger.valueOf(100000), BigInteger.valueOf(100000));
		contract.transferEventObservable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
				.subscribe(new Action1<TransferEventResponse>() {
					@Override
					public void call(TransferEventResponse log) {
						System.out.println(log.toString());
					}
				});
		contract.transferWithOrderEventObservable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
				.subscribe(new Action1<TransferWithOrderEventResponse>() {
					@Override
					public void call(TransferWithOrderEventResponse log) {
						System.out.println(log.toString());
					}
				});
	}

	public Admin getWeb3Client() {
		if (web3 == null)
			web3 = Admin.build(new HttpService(url));
		return web3;
	}

	/**
	 * 查询版本
	 * 
	 * @return 版本号
	 * @throws IOException
	 */
	public String getVersion() throws IOException {
		Web3ClientVersion web3ClientVersion = getWeb3Client().web3ClientVersion().send();
		String clientVersion = web3ClientVersion.getWeb3ClientVersion();
		return clientVersion;
	}

	/**
	 * 查询账号列表
	 * 
	 * @return 账号列表
	 * @throws IOException
	 */
	public List<String> getAccounts() throws IOException {
		EthAccounts accounts = getWeb3Client().ethAccounts().send();
		return accounts.getAccounts();
	}

	/**
	 * 创建新账号
	 * 
	 * @param password
	 *            密码
	 * @return 账号ID
	 * @throws IOException
	 */
	public String newAccount(String password) throws IOException {
		NewAccountIdentifier id = getWeb3Client().personalNewAccount(password).send();
		return id.getAccountId();
	}

	/**
	 * 创建钱包
	 * 
	 * @param password
	 * @return 钱包文件
	 * @throws Exception
	 */
	public File createWallet(String password) throws Exception {
		String fileName = WalletUtils.generateFullNewWalletFile(password, new File(this.keystoreFolder));
		File file = new File(this.keystoreFolder + File.separator + fileName);
		file.renameTo(new File(this.keystoreFolder + File.separator + fileName.substring(0, fileName.length() - 4)));
		return file;
	}

	/**
	 * 更新密码
	 * 
	 * @param account 需要更新的账号
	 * @param oldPwd 原密码
	 * @param newPwd 新密码
	 * @return
	 * @throws Exception
	 */
	public File updateWallet(String account, String oldPwd, String newPwd) throws Exception {
		String oldFilePath = getKeystoreFilePath(account);
		if(oldFilePath == null)
			throw new Exception("key file not exist");
		Credentials credentials = WalletUtils.loadCredentials(oldPwd, oldFilePath);
		String fileName = WalletUtils.generateWalletFile(newPwd, credentials.getEcKeyPair(),
				new File(this.keystoreFolder), true);
		File file = new File(this.keystoreFolder + File.separator + fileName);
		file.renameTo(new File(this.keystoreFolder + File.separator + fileName.substring(0, fileName.length() - 4)));
		try {
			new File(oldFilePath).delete();
		} catch (Exception e) {
			file.delete();
			throw new Exception("Old key file is locked");
		}
		return file;
	}

	/**
	 * 查询区块高度
	 * 
	 * @return 区块高度
	 * @throws IOException
	 */
	public BigInteger getBlockNumber() throws IOException {
		EthBlockNumber blockNumber = getWeb3Client().ethBlockNumber().send();
		return blockNumber.getBlockNumber();
	}

	/**
	 * 获取以太币余额
	 * 
	 * @param address
	 *            地址
	 * @return 余额
	 * @throws IOException
	 */
	public BigInteger getBalance(String address) throws IOException {
		EthGetBalance balance = getWeb3Client().ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
		return balance.getBalance();
	}

	/**
	 * 创建交易（本地钱包）
	 * 
	 * @param from
	 *            源账号
	 * @param to
	 *            目标账号
	 * @param pwd
	 *            源账号密码
	 * @param amount
	 *            金额
	 * @return 交易码
	 * @throws Exception
	 */
	/*
	 * public String sendTransaction(String from, String to, String pwd,
	 * BigInteger amount) throws IOException { Transaction transaction =
	 * Transaction.createEtherTransaction(from, null, null, null, to, amount);
	 * getWeb3Client().personalUnlockAccount(from, pwd).send();
	 * EthSendTransaction sendt =
	 * getWeb3Client().ethSendTransaction(transaction).send(); return
	 * sendt.getTransactionHash(); }
	 */
	public String sendTransaction(String from, String to, String pwd, BigInteger amount) throws Exception {
		Credentials credentials = WalletUtils.loadCredentials(pwd, getKeystoreFilePath(from));
		RawTransactionManager txManager = new RawTransactionManager(getWeb3Client(), credentials);
		EthSendTransaction sendt = txManager.sendTransaction(null, null, to, null, amount);
		return sendt.getTransactionHash();
	}

	/**
	 * 估算ETH交易费用
	 * 
	 * @param from
	 * @param to
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public BigInteger estimateEthTxGas(String from, String to, BigInteger amount) throws Exception {
		return getWeb3Client().ethEstimateGas(Transaction.createEtherTransaction(from, null, BigInteger.valueOf(gasPrice), BigInteger.valueOf(gasLimit), to, amount))
				.send().getAmountUsed();
	}

	/**
	 * 估算ETH交易费用
	 * 
	 * @param from
	 * @param to
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public BigInteger estimateFunctionCallTxGas(String from, String to, BigInteger amount, Function function)
			throws Exception {
		return getWeb3Client().ethEstimateGas(Transaction.createFunctionCallTransaction(from, null,
				BigInteger.valueOf(gasPrice), BigInteger.valueOf(gasLimit), to, FunctionEncoder.encode(function)))
				.send().getAmountUsed();
	}

	/**
	 * 获取UFO货币标志
	 * 
	 * @return 货币标志
	 * @throws Exception
	 */
	public String getUFOSymbol() throws Exception {
		Ufo contract = Ufo.load(ufoTokenAddress, getWeb3Client(), Credentials.create(ufoTokenAddress),
				BigInteger.valueOf(100000), BigInteger.valueOf(100000));
		String balanceToken = contract.symbol().send();
		return balanceToken;
	}

	/**
	 * 获取UFO币余额
	 * 
	 * @param addr
	 *            地址
	 * @return UFO余额
	 * @throws Exception
	 */
	public BigInteger getUFOBalance(String addr) throws Exception {
		Ufo contract = Ufo.load(ufoTokenAddress, getWeb3Client(), Credentials.create(ufoTokenAddress),
				BigInteger.valueOf(100000), BigInteger.valueOf(100000));
		BigInteger balanceToken = contract.balanceOf(addr).send();
		return balanceToken;
	}

	/**
	 * 发送UFO交易
	 * 
	 * @param from
	 *            源账号
	 * @param to
	 *            目标账号
	 * @param pwd
	 *            密码
	 * @param amount
	 *            金额
	 * @return 交易码
	 * @throws Exception
	 */
	public String sendUFOTransaction(String from, String to, String pwd, BigInteger amount) throws Exception {
		Credentials credentials = WalletUtils.loadCredentials(pwd, getKeystoreFilePath(from));
		Ufo contract = Ufo.load(ufoTokenAddress, getWeb3Client(), credentials, BigInteger.valueOf(gasPrice),
				BigInteger.valueOf(gasLimit));
		TransactionReceipt tr = null;
		try {
			// getWeb3Client().personalUnlockAccount(from, pwd).send();
			/*contract.transfer(to, amount).sendAsync().whenComplete((s, e) -> {
                s.
            });*/
			tr = contract.transfer(to, amount).send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (tr != null)
			return tr.getTransactionHash();
		return null;

	}

	/**
	 * 发送UFO交易（含业务订单号）
	 * 
	 * @param from
	 *            源账号
	 * @param to
	 *            目标账号
	 * @param pwd
	 *            密码
	 * @param amount
	 *            金额
	 * @param orderNo
	 *            业务订单号
	 * @return 交易码
	 * @throws Exception
	 */
	public String sendUFOTransactionWithOrder(String from, String to, String pwd, BigInteger amount, String orderNo)
			throws Exception {
		Credentials credentials = WalletUtils.loadCredentials(pwd, getKeystoreFilePath(from));
		Ufo contract = Ufo.load(ufoTokenAddress, getWeb3Client(), credentials, BigInteger.valueOf(gasPrice),
				BigInteger.valueOf(gasLimit));
		EthSendTransaction tr = null;
		try {
			getWeb3Client().personalUnlockAccount(from, pwd).send();
			orderNo = StringUtils.leftPad(orderNo, 32);
			tr = contract.transferWithOrder(orderNo.getBytes(), to, pwd, amount).send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (tr != null)
			return tr.getTransactionHash();
		return null;

	}

	/**
	 * 根据交易Hash查询交易详情
	 * 
	 * @param txHash
	 * @return 交易详情
	 * @throws Exception
	 */
	public UfoTransaction getUFOTxDetailByHash(String txHash) throws Exception {
		Credentials credentials = Credentials.create(ufoTokenAddress);
		Ufo contract = Ufo.load(ufoTokenAddress, getWeb3Client(), credentials, BigInteger.valueOf(gasPrice),
				BigInteger.valueOf(gasLimit));
		EthGetTransactionReceipt etr = getWeb3Client().ethGetTransactionReceipt(txHash).send();
		TransactionReceipt tr = etr.getResult();
		if (tr != null) {
			UfoTransaction tx = new UfoTransaction();
			BeanUtils.copyProperties(tr, tx);
			List<TransferWithOrderEventResponse> response1 = contract.getTransferWithOrderEvents(tr);
			if (response1 != null && response1.size() > 0) {
				tx.setFrom(response1.get(0).from);
				tx.setTo(response1.get(0).to);
				tx.setAmount(response1.get(0).value);
				tx.setOrderNo(new String(response1.get(0).order).trim());
			} else {
				List<TransferEventResponse> response = contract.getTransferEvents(tr);
				contract.getTransferWithOrderEvents(tr);
				if (response != null && response.size() > 0) {
					tx.setFrom(response.get(0).from);
					tx.setTo(response.get(0).to);
					tx.setAmount(response.get(0).value);
				}
			}
			return tx;
		}
		return null;
	}

	/**
	 * 根据账户查询交易列表
	 * 
	 * @param account
	 *            账号
	 * @return 交易列表
	 * @throws Exception
	 */
	public List<UfoTransaction> getUFOTxsByAccount(String account) throws Exception {
		List<UfoTransaction> result = new ArrayList<UfoTransaction>();
		List<LogResult> logs = getUFOTansferEvents("0x" + TypeEncoder.encode(new Address(account)));
		if (logs != null && logs.size() > 0) {
			for(LogResult log : logs) {
				UfoTransaction tx = getUFOTxDetailByHash(((Log) log).getTransactionHash());
				if(tx != null) {
					tx.setLogs(null);
					result.add(tx);
				}
			}
		}
		return result;
	}
	
	/**
	 * 根据业务订单号查询交易详情
	 * 
	 * @param orderNo
	 *            业务单号
	 * @return 交易详情
	 * @throws Exception
	 */
	public UfoTransaction getUFOTxDetailByOrderNo(String orderNo) throws Exception {
		orderNo = StringUtils.leftPad(orderNo, 32);
		List<LogResult> logs = getUFOTansferWithOrderEvents(strTo16(orderNo));
		if (logs != null && logs.size() > 0) {
			Log logObject = (Log) logs.get(0);
			return getUFOTxDetailByHash(logObject.getTransactionHash());
		}
		return null;
	}
	
	

	private List<LogResult> getUFOTansferEvents(String param) {
		Event event = new Event("Transfer", Arrays.<TypeReference<?>> asList(new TypeReference<Address>() {
		}, new TypeReference<Address>() {
		}), Arrays.<TypeReference<?>> asList(new TypeReference<Uint256>() {
		}));
		EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST,
				this.getUfoTokenAddress());
		filter.addSingleTopic(EventEncoder.encode(event));
		EthLog log;
		try {
			filter.addSingleTopic(param);
			log = getWeb3Client().ethGetLogs(filter).send();
			List<LogResult> logs = log.getResult();
			filter.getTopics().remove(1);
			filter.addSingleTopic(null);
			filter.addSingleTopic(param);
			log = getWeb3Client().ethGetLogs(filter).send();
			logs.addAll(log.getLogs());
			return logs;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public List<LogResult> getUFOTansferWithOrderEvents(String param) {
		Event event = new Event("TransferWithOrder", Arrays.<TypeReference<?>> asList(new TypeReference<Bytes32>() {
		}, new TypeReference<Address>() {
		}, new TypeReference<Address>() {
		}), Arrays.<TypeReference<?>> asList(new TypeReference<Uint256>() {
		}));
		EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST,
				this.getUfoTokenAddress());
		filter.addSingleTopic(EventEncoder.encode(event));
		EthLog log;
		try {
			filter.addSingleTopic(param);
			log = getWeb3Client().ethGetLogs(filter).send();
			List<LogResult> logs = log.getResult();
			return logs;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private String getKeystoreFilePath(String addr) {
		for (File file : new File(keystoreFolder).listFiles()) {
			if (file.getName().endsWith(addr.substring(2)))
				return file.getAbsolutePath();
		}
		return null;
	}

	/**
	 * 字符串转化成为16进制字符串
	 * 
	 * @param s
	 * @return
	 */
	public String strTo16(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return "0x" + str;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUfoTokenAddress() {
		return ufoTokenAddress;
	}

	public void setUfoTokenAddress(String ufoTokenAddress) {
		this.ufoTokenAddress = ufoTokenAddress;
	}

	public String getKeystoreFolder() {
		return keystoreFolder;
	}

	public void setKeystoreFolder(String keystoreFolder) {
		this.keystoreFolder = keystoreFolder;
	}

	public long getGasLimit() {
		return gasLimit;
	}

	public void setGasLimit(long gasLimit) {
		this.gasLimit = gasLimit;
	}

	public long getGasPrice() {
		return gasPrice;
	}

	public void setGasPrice(long gasPrice) {
		this.gasPrice = gasPrice;
	}

	public String getAdminAccount() {
		return adminAccount;
	}

	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}

	public BigInteger getUserInitBalance() {
		return userInitBalance;
	}

	public void setUserInitBalance(BigInteger userInitBalance) {
		this.userInitBalance = userInitBalance;
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}

	public static class UfoTransaction {

		/**
		 * 交易Hash
		 */
		private String transactionHash;
		private BigInteger transactionIndex;
		/**
		 * 区块Hash
		 */
		private String blockHash;
		private BigInteger blockNumber;
		private BigInteger cumulativeGasUsed;
		private BigInteger gasUsed;
		private String contractAddress;
		private String root;
		private String status;
		private List<Log> logs;

		/**
		 * 付款方
		 */
		private String from;
		/**
		 * 收款方
		 */
		private String to;
		/**
		 * 交易金额
		 */
		private BigInteger amount;

		/**
		 * 订单号
		 */
		private String orderNo;

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}

		public BigInteger getAmount() {
			return amount;
		}

		public void setAmount(BigInteger amount) {
			this.amount = amount;
		}

		public String getTransactionHash() {
			return transactionHash;
		}

		public void setTransactionHash(String transactionHash) {
			this.transactionHash = transactionHash;
		}

		public BigInteger getTransactionIndex() {
			return transactionIndex;
		}

		public void setTransactionIndex(BigInteger transactionIndex) {
			this.transactionIndex = transactionIndex;
		}

		public String getBlockHash() {
			return blockHash;
		}

		public void setBlockHash(String blockHash) {
			this.blockHash = blockHash;
		}

		public BigInteger getBlockNumber() {
			return blockNumber;
		}

		public void setBlockNumber(BigInteger blockNumber) {
			this.blockNumber = blockNumber;
		}

		public BigInteger getCumulativeGasUsed() {
			return cumulativeGasUsed;
		}

		public void setCumulativeGasUsed(BigInteger cumulativeGasUsed) {
			this.cumulativeGasUsed = cumulativeGasUsed;
		}

		public BigInteger getGasUsed() {
			return gasUsed;
		}

		public void setGasUsed(BigInteger gasUsed) {
			this.gasUsed = gasUsed;
		}

		public String getContractAddress() {
			return contractAddress;
		}

		public void setContractAddress(String contractAddress) {
			this.contractAddress = contractAddress;
		}

		public String getRoot() {
			return root;
		}

		public void setRoot(String root) {
			this.root = root;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public List<Log> getLogs() {
			return logs;
		}

		public void setLogs(List<Log> logs) {
			this.logs = logs;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

	}

}
