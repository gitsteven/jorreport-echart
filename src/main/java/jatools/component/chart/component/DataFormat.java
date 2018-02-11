/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.text.Format;
/*    */ import java.text.NumberFormat;
/*    */ 
/*    */ public class DataFormat
/*    */ {
/*  7 */   NumberFormat format = NumberFormat.getNumberInstance();
/*    */   int precision;
/*  9 */   int example = 1000000;
/*    */ 
/*    */   public DataFormat()
/*    */   {
/*    */   }
/*    */ 
/*    */   public DataFormat(NumberFormat format, int precision) {
/* 16 */     if (format != null)
/* 17 */       this.format = format;
/* 18 */     this.precision = precision;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 23 */     this.format.setMinimumFractionDigits(this.precision);
/* 24 */     this.format.setMaximumFractionDigits(this.precision);
/* 25 */     return this.format.format(this.example);
/*    */   }
/*    */   public Format getFormat() {
/* 28 */     return this.format;
/*    */   }
/*    */   public void setFormat(NumberFormat format) {
/* 31 */     this.format = format;
/*    */   }
/*    */   public int getPrecision() {
/* 34 */     return this.precision;
/*    */   }
/*    */   public void setPrecision(int precision) {
/* 37 */     this.precision = precision;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj)
/*    */   {
/* 43 */     DataFormat d = (DataFormat)obj;
/* 44 */     return (this.format.equals(d.format)) && (this.precision == d.precision);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 48 */     DataFormat f = new DataFormat(NumberFormat.getPercentInstance(), 2);
/* 49 */     DataFormat f1 = new DataFormat(NumberFormat.getPercentInstance(), 2);
/*    */ 
/* 51 */     System.out.println(f.equals(f1));
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.DataFormat
 * JD-Core Version:    0.6.2
 */