/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import javax.swing.Icon;
/*    */ 
/*    */ public abstract class AbstractColorIcon
/*    */   implements Icon
/*    */ {
/*    */   int width;
/*    */   int height;
/*    */ 
/*    */   public AbstractColorIcon(int width, int height)
/*    */   {
/* 23 */     this.width = width;
/* 24 */     this.height = height;
/*    */   }
/*    */ 
/*    */   public int getIconHeight()
/*    */   {
/* 33 */     return this.height;
/*    */   }
/*    */ 
/*    */   public int getIconWidth()
/*    */   {
/* 42 */     return this.width;
/*    */   }
/*    */ 
/*    */   protected abstract void setStyle(FillStyleInterface paramFillStyleInterface);
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.AbstractColorIcon
 * JD-Core Version:    0.6.2
 */