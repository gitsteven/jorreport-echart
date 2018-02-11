/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.Image;
/*    */ import java.awt.RenderingHints;
/*    */ import java.awt.image.BufferedImage;
/*    */ 
/*    */ public class Java2DRenderer
/*    */ {
/*    */   public void draw(Image img, ChartInterface chart, Bean overlay)
/*    */   {
/* 39 */     Graphics2D g = ((BufferedImage)img).createGraphics();
/* 40 */     if (overlay.antialiasOn) {
/* 41 */       g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*    */     }
/* 43 */     chart.drawGraph(g);
/* 44 */     overlay.drawMyStuff(g);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.Java2DRenderer
 * JD-Core Version:    0.6.2
 */