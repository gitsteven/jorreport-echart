/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import jatools.component.chart.customizer.ChangeListener;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public abstract class AbstractComponent extends JPanel
/*    */ {
/*    */   protected ChangeListener parent;
/*    */ 
/*    */   public void addChangeListener(ChangeListener l)
/*    */   {
/* 23 */     this.parent = l;
/*    */   }
/*    */ 
/*    */   public void removeChangeListener()
/*    */   {
/* 30 */     this.parent = null;
/*    */   }
/*    */ 
/*    */   public abstract void setValue(Object paramObject);
/*    */ 
/*    */   public abstract Object getValue();
/*    */ 
/*    */   public void setEnabled(boolean enabled)
/*    */   {
/* 53 */     super.setEnabled(enabled);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.AbstractComponent
 * JD-Core Version:    0.6.2
 */