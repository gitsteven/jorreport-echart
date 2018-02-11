/*    */ package jatools.component.chart.utility;
/*    */ 
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.RotateString;
/*    */ import jatools.component.chart.servlet.Bean;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ 
/*    */ public class FlashRenderer
/*    */ {
/*    */   FlashGraphics flashGraphics;
/* 18 */   static BufferedImage img = new BufferedImage(100, 100, 1);
/*    */ 
/*    */   public FlashRenderer() {
/* 21 */     this.flashGraphics = null;
/*    */   }
/*    */ 
/*    */   public void draw(ChartInterface chart, Bean overlay) {
/* 25 */     Graphics2D g = img.createGraphics();
/* 26 */     chart.setStringRotator(new RotateString(null));
/* 27 */     this.flashGraphics = new FlashGraphics(g, chart.getWidth(), chart
/* 28 */       .getHeight());
/* 29 */     chart.paint(this.flashGraphics);
/* 30 */     overlay.drawMyStuff(this.flashGraphics);
/*    */ 
/* 33 */     this.flashGraphics.getLinkMap(overlay);
/*    */   }
/*    */ 
/*    */   public byte[] getSWFBytes() {
/* 37 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/*    */     try {
/* 39 */       this.flashGraphics.write(out);
/*    */     } catch (Exception e) {
/* 41 */       e.printStackTrace();
/*    */     }
/* 43 */     return out.toByteArray();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.utility.FlashRenderer
 * JD-Core Version:    0.6.2
 */