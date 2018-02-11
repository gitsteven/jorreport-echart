/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ public class CandlestickDatum extends Datum
/*    */ {
/*    */   double high;
/*    */ 
/*    */   public CandlestickDatum(double date, double hi, double lo, double open, double close, Globals g)
/*    */   {
/* 23 */     super(date, close, lo, open, g);
/* 24 */     this.high = hi;
/*    */   }
/*    */ 
/*    */   public double getClose()
/*    */   {
/* 30 */     return getY();
/*    */   }
/*    */ 
/*    */   public double getHigh()
/*    */   {
/* 38 */     return this.high;
/*    */   }
/*    */ 
/*    */   public double getLow()
/*    */   {
/* 45 */     return getY2();
/*    */   }
/*    */ 
/*    */   public double getOpen()
/*    */   {
/* 52 */     return getY3();
/*    */   }
/*    */ 
/*    */   public void setClose(double d)
/*    */   {
/* 59 */     setY(d);
/*    */   }
/*    */ 
/*    */   public void setHigh(double d)
/*    */   {
/* 67 */     this.high = d;
/*    */   }
/*    */   public void setOpen(double d) {
/* 70 */     setY3(d);
/*    */   }
/*    */   public void setLow(double d) {
/* 73 */     setY2(d);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.CandlestickDatum
 * JD-Core Version:    0.6.2
 */