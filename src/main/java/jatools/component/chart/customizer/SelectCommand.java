/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.dataset.Column;
/*    */ 
/*    */ public class SelectCommand
/*    */ {
/*    */   static final int LABEL = 0;
/*    */   static final int PLOTDATA = 1;
/*    */   Column field;
/*    */   int type;
/*    */   String error;
/*    */ 
/*    */   public SelectCommand(Column field, int type)
/*    */   {
/* 30 */     this.field = field;
/* 31 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public void setError(String err)
/*    */   {
/* 40 */     this.error = err;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.SelectCommand
 * JD-Core Version:    0.6.2
 */