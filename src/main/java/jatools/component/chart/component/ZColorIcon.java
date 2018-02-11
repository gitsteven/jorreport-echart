/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ 
/*    */ public class ZColorIcon extends ZEmptyIcon
/*    */ {
/* 15 */   Color color = Color.red;
/*    */ 
/*    */   public ZColorIcon(int width, int height)
/*    */   {
/* 24 */     super(width, height);
/*    */   }
/*    */ 
/*    */   public void setColor(Color color)
/*    */   {
/* 33 */     this.color = color;
/*    */   }
/*    */ 
/*    */   public void paintIcon(Component c, Graphics g, int x, int y)
/*    */   {
/* 45 */     g.setColor(this.color);
/* 46 */     g.fillRect(x, y, getIconWidth(), getIconHeight());
/* 47 */     g.setColor(Color.black);
/* 48 */     g.drawRect(x, y, getIconWidth(), getIconHeight());
/*    */   }
/*    */ 
/*    */   public Color getColor()
/*    */   {
/* 54 */     return this.color;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ZColorIcon
 * JD-Core Version:    0.6.2
 */