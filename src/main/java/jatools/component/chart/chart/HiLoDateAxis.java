/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ public class HiLoDateAxis extends DateAxis
/*    */   implements AxisInterface
/*    */ {
/*    */   public HiLoDateAxis()
/*    */   {
/*    */   }
/*    */ 
/*    */   public HiLoDateAxis(Dataset[] dsets, boolean xAxis, Plotarea plt)
/*    */   {
/* 32 */     super(dsets, xAxis, plt);
/*    */   }
/*    */ 
/*    */   protected double getMinValsFromData(int nmsets)
/*    */   {
/* 41 */     double low = 8.999999999999999E+035D;
/*    */ 
/* 43 */     if (this.userAxisStart != null) {
/* 44 */       return this.userAxisStart.doubleValue();
/*    */     }
/* 46 */     for (int i = 0; i < nmsets; i++)
/*    */     {
/* 48 */       low = min(low, getDatasets()[i].minY2());
/*    */     }
/*    */ 
/* 51 */     return low;
/*    */   }
/*    */ 
/*    */   private double min(double in, double cmp) {
/* 55 */     if (cmp == (-1.0D / 0.0D)) {
/* 56 */       return in;
/*    */     }
/* 58 */     return Math.min(in, cmp);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.HiLoDateAxis
 * JD-Core Version:    0.6.2
 */