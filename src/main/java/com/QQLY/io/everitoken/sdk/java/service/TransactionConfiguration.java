/*    */ package io.everitoken.sdk.java.service;
/*    */ 
/*    */ import io.everitoken.sdk.java.PublicKey;
/*    */ import io.everitoken.sdk.java.provider.KeyProviderInterface;
/*    */ import io.everitoken.sdk.java.provider.SignProvider;
/*    */ import io.everitoken.sdk.java.provider.SignProviderInterface;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ public class TransactionConfiguration
/*    */ {
/*    */   private final int maxCharge;
/*    */   private final PublicKey payer;
/*    */   private final SignProviderInterface signProvider;
/*    */   private String expiration;
/*    */   
/*    */   public TransactionConfiguration(int maxCharge, PublicKey payer, SignProviderInterface signProvider, @Nullable String expiration) {
/* 18 */     this.maxCharge = maxCharge;
/* 19 */     this.payer = payer;
/* 20 */     this.signProvider = signProvider;
/* 21 */     this.expiration = expiration;
/*    */   }
/*    */ 
/*    */   
/* 25 */   public TransactionConfiguration(int maxCharge, PublicKey payer, KeyProviderInterface keyProvider) { this(maxCharge, payer, (SignProviderInterface)SignProvider.of(keyProvider), null); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 30 */   public TransactionConfiguration(int maxCharge, PublicKey payer, KeyProviderInterface keyProvider, String expiration) { this(maxCharge, payer, (SignProviderInterface)SignProvider.of(keyProvider), expiration); }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public int getMaxCharge() { return this.maxCharge; }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public String getPayer() { return this.payer.toString(); }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public SignProviderInterface getSignProvider() { return this.signProvider; }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public String getExpiration() { return this.expiration; }
/*    */ }


/* Location:              C:\Users\QQLY\Desktop\chain-sdk-1.0.0-rc1.jar!\io\everitoken\sdk\java\service\TransactionConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.1
 */