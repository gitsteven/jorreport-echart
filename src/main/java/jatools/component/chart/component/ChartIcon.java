/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.JLabel;
/*    */ 
/*    */ public class ChartIcon
/*    */   implements Icon
/*    */ {
/*    */   int width;
/*    */   int height;
/*    */   ChartInterface chart;
/* 20 */   JLabel label = new JLabel();
/*    */ 
/*    */   public ChartIcon(ChartInterface chart, int width, int height) {
/* 23 */     this.chart = chart;
/* 24 */     this.width = width;
/* 25 */     this.height = height;
/* 26 */     initChart();
/*    */   }
/*    */ 
/*    */   private void initChart() {
/*    */   }
/*    */ 
/*    */   public int getIconHeight() {
/* 33 */     return this.height;
/*    */   }
/*    */ 
/*    */   public int getIconWidth() {
/* 37 */     return this.width;
/*    */   }
/*    */ 
/*    */   public void paintIcon(Component c, Graphics g, int x, int y) {
/* 41 */     this.chart.paint(c, g, this.width, this.height);
/* 42 */     g.setColor(Color.black);
/* 43 */     g.drawRect(x, y, getIconWidth(), getIconHeight() - 1);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ChartIcon
 * JD-Core Version:    0.6.2
 */