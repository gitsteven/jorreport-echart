/*    */ package jatools.component.chart;
/*    */ 
/*    */ import jatools.component.Component;
/*    */ import jatools.component.painter.SimplePainter;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.Rectangle;
/*    */ import java.awt.Shape;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class ChartPainter extends SimplePainter
/*    */ {
/*    */   public void paintComponent(Graphics2D g, Component c)
/*    */   {
/* 26 */     Rectangle bounds = c.getBounds();
/*    */ 
/* 28 */     Chart chart = (Chart)c;
/* 29 */     int width = chart.getWidth();
/* 30 */     int height = chart.getHeight();
/*    */ 
/* 32 */     Properties graphProperties = chart.getProperties();
/*    */ 
/* 34 */     if (graphProperties.get("chartType") == null) {
/* 35 */       graphProperties.put("chartType", "1");
/*    */     }
/*    */ 
/* 38 */     ChartCanvas canvas = new ChartCanvas(chart.getChart());
/*    */ 
/* 40 */     if ((width > 0) && (height > 0)) {
/* 41 */       g = (Graphics2D)g.create();
/* 42 */       Shape copy = g.getClip();
/* 43 */       g.clip(bounds);
/* 44 */       g.translate(chart.getX(), chart.getY());
/*    */ 
/* 46 */       canvas.setSize(width, height);
/* 47 */       canvas.paintComponent(g);
/* 48 */       g.setClip(copy);
/* 49 */       g.dispose();
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.ChartPainter
 * JD-Core Version:    0.6.2
 */