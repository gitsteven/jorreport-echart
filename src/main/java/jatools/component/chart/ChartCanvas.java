/*    */ package jatools.component.chart;
/*    */ 
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Label;
/*    */ 
/*    */ public class ChartCanvas extends Label
/*    */ {
/*    */   ChartInterface chart;
/*    */ 
/*    */   public ChartCanvas(ChartInterface chart)
/*    */   {
/* 23 */     this.chart = chart;
/*    */   }
/*    */ 
/*    */   public void paintComponent(Graphics g)
/*    */   {
/* 34 */     this.chart.paint(this, g);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.ChartCanvas
 * JD-Core Version:    0.6.2
 */