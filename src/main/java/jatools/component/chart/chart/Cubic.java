/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ public class Cubic
/*    */ {
/*    */   float a;
/*    */   float b;
/*    */   float c;
/*    */   float d;
/*    */ 
/*    */   public Cubic(float a, float b, float c, float d)
/*    */   {
/* 24 */     this.a = a;
/* 25 */     this.b = b;
/* 26 */     this.c = c;
/* 27 */     this.d = d;
/*    */   }
/*    */ 
/*    */   public float eval(float u)
/*    */   {
/* 38 */     return ((this.d * u + this.c) * u + this.b) * u + this.a;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Cubic
 * JD-Core Version:    0.6.2
 */