/*    */ package jatools.component.chart;
/*    */ 
/*    */ import jatools.accessor.AutoAccessor;
/*    */ 
/*    */ public class PlotData extends AutoAccessor
/*    */ {
/*    */   String field;
/*    */   Tip tooltip;
/*    */ 
/*    */   public PlotData(String field, Tip tooltip)
/*    */   {
/* 23 */     this.field = field;
/* 24 */     this.tooltip = tooltip;
/*    */   }
/*    */ 
/*    */   public PlotData()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String getField()
/*    */   {
/* 39 */     return this.field;
/*    */   }
/*    */ 
/*    */   public void setField(String field)
/*    */   {
/* 48 */     this.field = field;
/*    */   }
/*    */ 
/*    */   public Tip getTooltip()
/*    */   {
/* 57 */     return this.tooltip;
/*    */   }
/*    */ 
/*    */   public void setTooltip(Tip tooltip)
/*    */   {
/* 66 */     this.tooltip = tooltip;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.PlotData
 * JD-Core Version:    0.6.2
 */