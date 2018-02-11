/*    */ package jatools.component.chart;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import sun.misc.BASE64Decoder;
/*    */ import sun.misc.BASE64Encoder;
/*    */ 
/*    */ public class JavachartUtil
/*    */ {
/* 10 */   private static BASE64Encoder encoder = new BASE64Encoder();
/* 11 */   private static BASE64Decoder decoder = new BASE64Decoder();
/*    */ 
/*    */   public static String plus(String[] labels, String separator) {
/* 14 */     StringBuffer buf = new StringBuffer();
/* 15 */     for (int i = 0; i < labels.length; i++) {
/* 16 */       if (labels[i] == null) {
/*    */         break;
/*    */       }
/* 19 */       buf.append(labels[i]);
/* 20 */       if (i < labels.length - 1) {
/* 21 */         buf.append(separator);
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 26 */     return buf.toString();
/*    */   }
/*    */ 
/*    */   public static void addArray(String[] s1, String[] s2) {
/* 30 */     int len = Math.min(s1.length, s2.length);
/* 31 */     for (int i = 0; i < len; i++)
/* 32 */       s1[i] = s2[i];
/*    */   }
/*    */ 
/*    */   public static String edcodeBase64(byte[] bytes)
/*    */   {
/* 37 */     return encoder.encode(bytes);
/*    */   }
/*    */ 
/*    */   public static byte[] decoderBase64(String str) {
/* 41 */     byte[] bytes = (byte[])null;
/*    */     try {
/* 43 */       bytes = decoder.decodeBuffer(str);
/*    */     }
/*    */     catch (IOException e) {
/* 46 */       e.printStackTrace();
/*    */     }
/* 48 */     return bytes;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.JavachartUtil
 * JD-Core Version:    0.6.2
 */