/*    */ package jatools.component.chart.parts;
/*    */ 
/*    */ import java.text.DecimalFormat;
/*    */ import java.text.DecimalFormatSymbols;
/*    */ import java.text.FieldPosition;
/*    */ 
/*    */ public class SymbolFormat extends DecimalFormat
/*    */ {
/*    */   static final double BILLION = 1000000000.0D;
/*    */   static final double MILLION = 1000000.0D;
/*    */   static final double THOUSAND = 1000.0D;
/*    */   static final double HUNDRED = 100.0D;
/*    */ 
/*    */   public SymbolFormat()
/*    */   {
/*    */   }
/*    */ 
/*    */   public SymbolFormat(String pattern)
/*    */   {
/* 30 */     super(pattern);
/*    */   }
/*    */ 
/*    */   public SymbolFormat(String pattern, DecimalFormatSymbols symbols)
/*    */   {
/* 38 */     super(pattern, symbols);
/*    */   }
/*    */ 
/*    */   public StringBuffer format(double inVal, StringBuffer sb, FieldPosition fp)
/*    */   {
/* 48 */     if (inVal >= 1000000000.0D) {
/* 49 */       sb = super.format(inVal / 1000000000.0D, sb, fp);
/* 50 */       sb.append("B");
/*    */     }
/* 52 */     else if (inVal >= 1000000.0D) {
/* 53 */       sb = super.format(inVal / 1000000.0D, sb, fp);
/* 54 */       sb.append("M");
/*    */     }
/* 56 */     else if (inVal >= 1000.0D) {
/* 57 */       sb = super.format(inVal / 1000.0D, sb, fp);
/* 58 */       sb.append("T");
/*    */     }
/* 60 */     else if (inVal >= 100.0D) {
/* 61 */       sb = super.format(inVal / 100.0D, sb, fp);
/* 62 */       sb.append("H");
/*    */     }
/* 65 */     else if (inVal <= -1000000000.0D) {
/* 66 */       sb = super.format(inVal / 1000000000.0D, sb, fp);
/* 67 */       sb.append("B");
/*    */     }
/* 69 */     else if (inVal <= -1000000.0D) {
/* 70 */       sb = super.format(inVal / 1000000.0D, sb, fp);
/* 71 */       sb.append("M");
/*    */     }
/* 73 */     else if (inVal <= -1000.0D) {
/* 74 */       sb = super.format(inVal / 1000.0D, sb, fp);
/* 75 */       sb.append("T");
/*    */     }
/* 77 */     else if (inVal <= -100.0D) {
/* 78 */       sb = super.format(inVal / 100.0D, sb, fp);
/* 79 */       sb.append("H");
/*    */     } else {
/* 81 */       sb = super.format(inVal, sb, fp);
/*    */     }
/* 83 */     return sb;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.parts.SymbolFormat
 * JD-Core Version:    0.6.2
 */