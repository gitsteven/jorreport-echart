/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import jatools.component.chart.chart.Gc;
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ 
/*    */ public class ColorIcon extends AbstractColorIcon
/*    */ {
/*    */   Color color;
/*    */ 
/*    */   public ColorIcon(int width, int height, int x)
/*    */   {
/* 27 */     super(width, height);
/*    */   }
/*    */ 
/*    */   public ColorIcon(Dimension d)
/*    */   {
/* 36 */     super(d.width, d.height);
/*    */   }
/*    */ 
/*    */   public void setStyle(FillStyleInterface style)
/*    */   {
/* 45 */     this.color = ((SingleColor)style).color;
/*    */   }
/*    */ 
/*    */   public void setColor(Color color)
/*    */   {
/* 54 */     this.color = color;
/*    */   }
/*    */ 
/*    */   public void paintIcon(Component c, Graphics g1, int x, int y)
/*    */   {
/* 66 */     Graphics g = g1.create();
/*    */ 
/* 69 */     if (this.color != Gc.TRANSPARENT) {
/* 70 */       g.setColor(this.color);
/* 71 */       g.fillRect(x, y, getIconWidth(), getIconHeight());
/*    */     }
/*    */ 
/* 74 */     g.setColor(Color.black);
/* 75 */     g.drawRect(x, y, getIconWidth(), getIconHeight());
/* 76 */     g.dispose();
/*    */   }
/*    */ 
/*    */   public Color getColor()
/*    */   {
/* 85 */     return this.color;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ColorIcon
 * JD-Core Version:    0.6.2
 */