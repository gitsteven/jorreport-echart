/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import java.awt.Point;
/*    */ import java.awt.Rectangle;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ class PieLabelInfo
/*    */ {
/* 12 */   ArrayList strings = new ArrayList();
/*    */   Rectangle location;
/* 14 */   double radians = 0.0D;
/*    */   Point startPointer;
/*    */   Point endPointer;
/* 17 */   boolean isRightTop = false;
/* 18 */   boolean isRightBottom = false;
/* 19 */   boolean isLeftTop = false;
/* 20 */   boolean isLeftBottom = false;
/*    */ 
/*    */   protected void addString(String s) {
/* 23 */     this.strings.add(s);
/*    */   }
/*    */ 
/*    */   protected void translateX(int val) {
/* 27 */     this.location.translate(val, 0);
/* 28 */     this.endPointer.translate(val, 0);
/*    */   }
/*    */ 
/*    */   protected void translateY(int val) {
/* 32 */     this.location.translate(0, val);
/* 33 */     this.endPointer.translate(0, val);
/*    */   }
/*    */ 
/*    */   protected void setRadians(double val)
/*    */   {
/* 41 */     this.radians = val;
/*    */ 
/* 44 */     if ((val >= 4.71238898038469D) && (val <= 6.283185307179586D)) {
/* 45 */       this.isRightBottom = true;
/*    */     }
/* 48 */     else if ((val >= 0.0D) && (val < 1.570796326794897D)) {
/* 49 */       this.isRightTop = true;
/*    */     }
/* 52 */     else if ((val >= 1.570796326794897D) && (val < 3.141592653589793D)) {
/* 53 */       this.isLeftTop = true;
/*    */     }
/*    */     else
/*    */     {
/* 57 */       this.isLeftBottom = true;
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.PieLabelInfo
 * JD-Core Version:    0.6.2
 */