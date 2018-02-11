/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import java.awt.Image;
/*    */ 
/*    */ public abstract class UserImagingCodec
/*    */ {
/*    */   public byte[] drawChartToOutputStream(ChartInterface chart)
/*    */   {
/* 40 */     return null;
/*    */   }
/*    */ 
/*    */   public byte[] encodeImageBytes(Image img)
/*    */   {
/* 50 */     return null;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.UserImagingCodec
 * JD-Core Version:    0.6.2
 */