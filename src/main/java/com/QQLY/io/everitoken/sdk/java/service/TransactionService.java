/*     */ package io.everitoken.sdk.java.service;
/*     */ 
/*     */ import com.alibaba.fastjson.JSON;
/*     */ import io.everitoken.sdk.java.Api;
/*     */ import io.everitoken.sdk.java.EvtLink;
/*     */ import io.everitoken.sdk.java.PrivateKey;
/*     */ import io.everitoken.sdk.java.PublicKey;
/*     */ import io.everitoken.sdk.java.Signature;
/*     */ import io.everitoken.sdk.java.Utils;
/*     */ import io.everitoken.sdk.java.abi.Abi;
/*     */ import io.everitoken.sdk.java.abi.AbiSerialisationProviderInterface;
/*     */ import io.everitoken.sdk.java.abi.EveriPayAction;
/*     */ import io.everitoken.sdk.java.abi.RemoteAbiSerialisationProvider;
/*     */ import io.everitoken.sdk.java.apiResource.Info;
/*     */ import io.everitoken.sdk.java.apiResource.SigningRequiredKeys;
/*     */ import io.everitoken.sdk.java.apiResource.TransactionCommit;
/*     */ import io.everitoken.sdk.java.apiResource.TransactionEstimatedCharge;
/*     */ import io.everitoken.sdk.java.dto.Charge;
/*     */ import io.everitoken.sdk.java.dto.NodeInfo;
/*     */ import io.everitoken.sdk.java.dto.Transaction;
/*     */ import io.everitoken.sdk.java.dto.TransactionData;
/*     */ import io.everitoken.sdk.java.exceptions.ApiResponseException;
/*     */ import io.everitoken.sdk.java.param.EvtLinkStatusParam;
/*     */ import io.everitoken.sdk.java.param.NetParams;
/*     */ import io.everitoken.sdk.java.param.RequestParams;
/*     */ import io.everitoken.sdk.java.provider.KeyProviderInterface;
/*     */ import io.everitoken.sdk.java.provider.SignProvider;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
import java.util.Random;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.StreamSupport;
/*     */ import org.jetbrains.annotations.Contract;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.joda.time.DateTime;
/*     */ import org.joda.time.Duration;
/*     */ import org.joda.time.ReadableDuration;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class TransactionService
/*     */ {
/*     */   private final NetParams netParams;
/*     */   private final AbiSerialisationProviderInterface actionSerializeProvider;
/*     */   
/*     */   private TransactionService(NetParams netParams, AbiSerialisationProviderInterface provider) {
/*  50 */     this.netParams = netParams;
/*  51 */     this.actionSerializeProvider = provider;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract("_ -> new")
/*  57 */   public static TransactionService of(NetParams netParams) { return new TransactionService(netParams, (AbiSerialisationProviderInterface)new RemoteAbiSerialisationProvider(netParams)); }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract("_, _ -> new")
/*  63 */   public static TransactionService of(NetParams netParams, AbiSerialisationProviderInterface provider) { return new TransactionService(netParams, provider); }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*  68 */   public static String getExpirationTime(String referenceTime) { return getExpirationTime(referenceTime, null); }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static String getExpirationTime(@NotNull String referenceTime, @Nullable String type) {
/*  73 */     Objects.requireNonNull(referenceTime);
/*     */     
/*  75 */     int TIMESTAMP_LENGTH = 19;
/*  76 */     Duration expireDuration = Duration.standardSeconds(100L);
/*     */     
/*  78 */     if (type != null && type.equals("everipay")) {
/*  79 */       expireDuration = Duration.standardSeconds(10L);
/*     */     }
/*     */     
/*  82 */     DateTime dateTime = Utils.getCorrectedTime(referenceTime);
/*  83 */     DateTime expiration = dateTime.plus((ReadableDuration)expireDuration);
/*     */     
/*  85 */     return expiration.toString().substring(0, TIMESTAMP_LENGTH);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, String> pushEveriPayAction(TransactionConfiguration trxConfig, EveriPayAction action) throws ApiResponseException {
/*  90 */     push(trxConfig, (List)Collections.singletonList(action));
/*  91 */     return (new EvtLink(this.netParams)).getStatusOfEvtLink(EvtLinkStatusParam.of(action.getLinkId()));
/*     */   }
/*     */ 
/*     */   
/*     */   public TransactionData push(TransactionConfiguration trxConfig, List<? extends Abi> actions) throws ApiResponseException {//���������������ȡ��������������
/*  96 */     Transaction rawTx = buildRawTransaction(trxConfig, actions);//�������� ��ʼ������
/*     */  System.out.println("1");
/*     */     
/*  99 */     byte[] digest = SignProvider.getSignableDigest(this.netParams, rawTx);//��ȡժҪ
//String s = new String(digest);
//System.out.println(digest.toString()+"???");
/*     */     System.out.println("2");
/* 101 */     boolean hasEveryPay = actions.stream().anyMatch(action -> action.getName().equals("everipay"));//�ж��Ƿ��������֧����
/*     */     System.out.println("3");
/* 103 */     if (hasEveryPay) {
/* 104 */       throw new IllegalArgumentException("EveriPay action is found in this action list, use \"pushEveriPayAction\" for everipay action instead.");
/*     */     }
/*     */ System.out.println("4");
/*     */     
/* 108 */     return (new TransactionCommit()).request(RequestParams.of(this.netParams, () -> {
/* 109 */             JSONObject payload = new JSONObject();
/* 110 */             payload.put("compression", "none");
/* 111 */             payload.put("transaction", new JSONObject(JSON.toJSONString(rawTx)));
/* 112 */             payload.put("signatures", new JSONArray(trxConfig.getSignProvider().sign(digest).toString()));//����ժҪ�������ļ���ȡǩ��
/* 113 */             return payload.toString();
/*     */           }));
/*     */   }
/*     */ 
/*     */   


public String push1(TransactionConfiguration trxConfig, List<? extends Abi> actions,byte[] digest) throws ApiResponseException {//���������������ȡ��������������
     Transaction rawTx = buildRawTransaction(trxConfig, actions);//�������� ��ʼ������
//     byte[] digest = SignProvider.getSignableDigest(this.netParams, rawTx);//��ȡժҪ    
     boolean hasEveryPay = actions.stream().anyMatch(action -> action.getName().equals("everipay"));//�ж��Ƿ��������֧����
     if (hasEveryPay) {
      throw new IllegalArgumentException("EveriPay action is found in this action list, use \"pushEveriPayAction\" for everipay action instead.");
     }  
     return trxConfig.getSignProvider().sign(digest).toString();
  }
//���ժҪ
public byte[] push2(TransactionConfiguration trxConfig, List<? extends Abi> actions) throws ApiResponseException {//���������������ȡ��������������
    Transaction rawTx = buildRawTransaction(trxConfig, actions);//�������� ��ʼ������
    byte[] digest = SignProvider.getSignableDigest(this.netParams, rawTx);//��ȡժҪ
      
return digest;
    
 }


/*     */   public Charge estimateCharge(TransactionConfiguration trxConfig, List<? extends Abi> actions, List<PublicKey> availablePublicKeys) throws ApiResponseException {
/* 119 */     Transaction rawTx = buildRawTransaction(trxConfig, actions);
/*     */     
/* 121 */     JSONObject txObj = new JSONObject(JSON.toJSONString(rawTx));
/* 122 */     List<String> requiredKeys = (new SigningRequiredKeys()).request(RequestParams.of(this.netParams, () -> {
/* 123 */             JSONObject json = new JSONObject();
/* 124 */             json.put("transaction", txObj);
/* 125 */             json.put("available_keys", (Collection)availablePublicKeys
/* 126 */                 .stream().map(PublicKey::toString).collect(Collectors.toList()));
/* 127 */             return json.toString();
/*     */           }));
/*     */     
/* 130 */     return (new TransactionEstimatedCharge()).request(RequestParams.of(this.netParams, () -> {
/* 131 */             JSONObject json = new JSONObject();
/* 132 */             json.put("transaction", txObj);
/* 133 */             json.put("sign_num", requiredKeys.size());
/* 134 */             return json.toString();
/*     */           }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Transaction buildRawTransaction(TransactionConfiguration trxConfig, List<? extends Abi> actions) throws ApiResponseException {//����ԭʼ������(�е�����)
/* 141 */     List<String> serializedActions = (List<String>)actions.stream().map(action -> action.serialize(this.actionSerializeProvider)).collect(Collectors.toList());
/*     */     
/* 143 */     boolean hasEveryPay = actions.stream().anyMatch(action -> action.getName().equals("everipay"));
/*     */     
/* 145 */     if (hasEveryPay && trxConfig.getExpiration() != null) {
/* 146 */       throw new IllegalArgumentException("Expiration can not be set in a transaction including a everipay action, the expiration must be set automatically by SDK");
/*     */     }
/*     */ 
/* 150 */     NodeInfo res = (new Info()).request(RequestParams.of(this.netParams));
/*     */     
/* 152 */     int refBlockNumber = Utils.getNumHash(res.getLastIrreversibleBlockId());
/* 153 */     long refBlockPrefix = Utils.getLastIrreversibleBlockPrefix(res.getLastIrreversibleBlockId());
/* 154 */     String expirationDateTime = trxConfig.getExpiration();
/*     */     
/* 156 */     if (expirationDateTime == null) {
/* 157 */       expirationDateTime = getExpirationTime(res.getHeadBlockTime(), hasEveryPay ? "everipay" : null);
/*     */     }
/*     */ 
/*     */     
/* 161 */     return new Transaction(serializedActions, expirationDateTime, refBlockNumber, refBlockPrefix, trxConfig
/* 162 */         .getMaxCharge(), trxConfig.getPayer());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Signature> getSignaturesByProposalName(KeyProviderInterface keyProvider, String proposalName) throws ApiResponseException {//�����������ǩ�����е����룩
/* 168 */     Api api = new Api(this.netParams);
/*     */     
/* 170 */     String suspendedProposalRaw = api.getSuspendedProposal(proposalName);
/* 171 */     JSONObject trxRaw = (new JSONObject(suspendedProposalRaw)).getJSONObject("trx");
/*     */ 
/*     */     
/* 174 */     byte[] trxSignableDigest = api.getSignableDigest(trxRaw.toString());//
/*     */ 
/*     */ 
/*     */     
/* 178 */     List<String> publicKeys = (List<String>)keyProvider.get().stream().map(PrivateKey::toPublicKey).map(PublicKey::toString).collect(Collectors.toList());
/*     */ 
/*     */ 
/*     */     
/* 182 */     List<String> suspendRequiredKeys = (List<String>)StreamSupport.stream(api.getSuspendRequiredKeys(proposalName, publicKeys).spliterator(), true).map(publicKey -> (String)publicKey).collect(Collectors.toList());
/*     */     //������Ҫ��Կ�ף����� ��������
/*     */     
/* 185 */     return (List<Signature>)keyProvider.get().stream().filter(privateKey -> {
/* 186 */           PublicKey publicKey = privateKey.toPublicKey();
/* 187 */           return suspendRequiredKeys.contains(publicKey.toString());
/* 188 */         }).map(privateKey -> Signature.signHash(trxSignableDigest, privateKey)).collect(Collectors.toList());
/*     */   }
/*     */ }


/* Location:              C:\Users\QQLY\Desktop\chain-sdk-1.0.0-rc1.jar!\io\everitoken\sdk\java\service\TransactionService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.1
 */