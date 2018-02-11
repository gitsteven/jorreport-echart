/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ public class SplitAxis extends Axis
/*    */ {
/* 14 */   boolean ignoreFirstLabel = false;
/* 15 */   boolean ignoreLastLabel = false;
/*    */ 
/*    */   public SplitAxis()
/*    */   {
/*    */   }
/*    */ 
/*    */   public SplitAxis(Dataset[] dsets, boolean xAxis, Plotarea plt)
/*    */   {
/* 27 */     super(dsets, xAxis, plt);
/*    */   }
/*    */ 
/*    */   protected String getLabel(double inVal, int count)
/*    */   {
/* 36 */     if ((this.ignoreFirstLabel) && (count == 0))
/* 37 */       return " ";
/* 38 */     if ((this.ignoreLastLabel) && (count == this.numLabels))
/* 39 */       return " ";
/* 40 */     return super.getLabel(inVal, count);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.SplitAxis
 * JD-Core Version:    0.6.2
 */