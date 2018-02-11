/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public abstract class Dialog extends JPanel
/*    */   implements ChangeListener
/*    */ {
/*    */   protected _Chart chart;
/*    */   protected ChangeListener parent;
/*    */ 
/*    */   public void setChart(_Chart chart)
/*    */   {
/* 15 */     this.chart = chart;
/*    */   }
/*    */ 
/*    */   public void setObject(Object object)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void addChangeListener(ChangeListener l) {
/* 23 */     this.parent = l;
/*    */   }
/*    */ 
/*    */   public void fireChange(Object object) {
/* 27 */     getVals();
/* 28 */     if (this.parent != null)
/* 29 */       this.parent.fireChange(object); 
/*    */   }
/*    */ 
/* 32 */   public void removeChangeListener(ChangeListener l) { this.parent = null; }
/*    */ 
/*    */ 
/*    */   public abstract void getVals();
/*    */ 
/*    */   public abstract void setVals();
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.Dialog
 * JD-Core Version:    0.6.2
 */