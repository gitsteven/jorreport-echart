/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ public class HiLoAxis extends Axis
/*    */   implements AxisInterface
/*    */ {
/*    */   public HiLoAxis()
/*    */   {
/*    */   }
/*    */ 
/*    */   public HiLoAxis(Dataset[] dsets, boolean xAxis, Plotarea plt)
/*    */   {
/* 28 */     super(dsets, xAxis, plt);
/*    */   }
/*    */ 
/*    */   protected double getMaxValsFromData(int nmsets)
/*    */   {
/* 36 */     double hi = -8.999999999999999E+035D;
/*    */ 
/* 38 */     if (this.userAxisEnd != null) {
/* 39 */       return this.userAxisEnd.doubleValue();
/*    */     }
/* 41 */     if (this.isXAxis)
/* 42 */       for (int i = 0; i < nmsets; i++)
/* 43 */         hi = Math.max(hi, this.datasets[i].maxX());
/*    */     else
/* 45 */       for (int i = 0; i < nmsets; i++) {
/* 46 */         hi = max(hi, this.datasets[i].maxY());
/* 47 */         hi = max(hi, this.datasets[i].maxY2());
/* 48 */         hi = max(hi, this.datasets[i].maxY3());
/*    */       }
/* 50 */     return hi;
/*    */   }
/*    */ 
/*    */   protected double getMinValsFromData(int nmsets)
/*    */   {
/* 59 */     double low = 8.999999999999999E+035D;
/*    */ 
/* 61 */     if (this.userAxisStart != null) {
/* 62 */       return this.userAxisStart.doubleValue();
/*    */     }
/* 64 */     for (int i = 0; i < nmsets; i++) {
/* 65 */       low = min(low, this.datasets[i].minY());
/* 66 */       low = min(low, this.datasets[i].minY2());
/* 67 */       low = min(low, this.datasets[i].minY3());
/*    */     }
/* 69 */     return low;
/*    */   }
/*    */   private double max(double in, double cmp) {
/* 72 */     if (cmp == (-1.0D / 0.0D)) {
/* 73 */       return in;
/*    */     }
/* 75 */     return Math.max(in, cmp);
/*    */   }
/*    */ 
/*    */   private double min(double in, double cmp) {
/* 79 */     if (cmp == (-1.0D / 0.0D)) {
/* 80 */       return in;
/*    */     }
/* 82 */     return Math.min(in, cmp);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.HiLoAxis
 * JD-Core Version:    0.6.2
 */