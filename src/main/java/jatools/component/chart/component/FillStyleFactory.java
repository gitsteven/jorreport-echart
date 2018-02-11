/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import jatools.component.chart.chart.Gc;
/*    */ import java.awt.Color;
/*    */ 
/*    */ public class FillStyleFactory
/*    */ {
/*    */   public static FillStyleInterface createFillStyle(Gc gc)
/*    */   {
/* 11 */     FillStyleInterface style = null;
/* 12 */     if (gc.getFillStyle() == -1)
/* 13 */       style = new SingleColor(Color.WHITE);
/* 14 */     else if (gc.getFillStyle() == 0)
/* 15 */       style = new GradiantColor();
/* 16 */     else if (gc.getFillStyle() == 1) {
/* 17 */       style = new TextureColor();
/*    */     }
/* 19 */     style.getFromGc(gc);
/* 20 */     return style;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.FillStyleFactory
 * JD-Core Version:    0.6.2
 */