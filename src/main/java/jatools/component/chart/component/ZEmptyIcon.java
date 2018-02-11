/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.Icon;
/*    */ 
/*    */ public class ZEmptyIcon
/*    */   implements Icon
/*    */ {
/*    */   private int height;
/*    */   private int width;
/*    */ 
/*    */   public ZEmptyIcon(int width, int height)
/*    */   {
/* 14 */     this.height = height;
/* 15 */     this.width = width;
/*    */   }
/*    */ 
/*    */   public int getIconHeight() {
/* 19 */     return this.height;
/*    */   }
/*    */ 
/*    */   public int getIconWidth() {
/* 23 */     return this.width;
/*    */   }
/*    */ 
/*    */   public void paintIcon(Component c, Graphics g, int x, int y)
/*    */   {
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ZEmptyIcon
 * JD-Core Version:    0.6.2
 */