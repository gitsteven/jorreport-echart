/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Polygon;
/*    */ import java.util.List;
/*    */ 
/*    */ public class NatCubic extends ControlCurve
/*    */ {
/* 16 */   final int STEPS = 12;
/*    */ 
/*    */   public NatCubic(List ps)
/*    */   {
/* 24 */     super(ps);
/*    */   }
/*    */ 
/*    */   Cubic[] calcNaturalCubic(int n, int[] x) {
/* 28 */     float[] gamma = new float[n + 1];
/* 29 */     float[] delta = new float[n + 1];
/* 30 */     float[] D = new float[n + 1];
/*    */ 
/* 33 */     gamma[0] = 0.5F;
/*    */ 
/* 35 */     for (int i = 1; i < n; i++) {
/* 36 */       gamma[i] = (1.0F / (4.0F - gamma[(i - 1)]));
/*    */     }
/*    */ 
/* 39 */     gamma[n] = (1.0F / (2.0F - gamma[(n - 1)]));
/*    */ 
/* 41 */     delta[0] = (3 * (x[1] - x[0]) * gamma[0]);
/*    */ 
/* 43 */     for (i = 1; i < n; i++) {
/* 44 */       delta[i] = ((3 * (x[(i + 1)] - x[(i - 1)]) - delta[(i - 1)]) * gamma[i]);
/*    */     }
/*    */ 
/* 47 */     delta[n] = ((3 * (x[n] - x[(n - 1)]) - delta[(n - 1)]) * gamma[n]);
/*    */ 
/* 49 */     D[n] = delta[n];
/*    */ 
/* 51 */     for (i = n - 1; i >= 0; i--) {
/* 52 */       delta[i] -= gamma[i] * D[(i + 1)];
/*    */     }
/*    */ 
/* 55 */     Cubic[] C = new Cubic[n];
/*    */ 
/* 57 */     for (i = 0; i < n; i++) {
/* 58 */       C[i] = new Cubic(x[i], D[i], 3 * (x[(i + 1)] - x[i]) - 2.0F * D[i] - D[(i + 1)], 
/* 59 */         2 * (x[i] - x[(i + 1)]) + D[i] + D[(i + 1)]);
/*    */     }
/*    */ 
/* 62 */     return C;
/*    */   }
/*    */ 
/*    */   public void draw(Graphics g)
/*    */   {
/* 71 */     if (this.pts.npoints >= 2) {
/* 72 */       Cubic[] X = calcNaturalCubic(this.pts.npoints - 1, this.pts.xpoints);
/* 73 */       Cubic[] Y = calcNaturalCubic(this.pts.npoints - 1, this.pts.ypoints);
/*    */ 
/* 75 */       Polygon p = new Polygon();
/* 76 */       p.addPoint(Math.round(X[0].eval(0.0F)), Math.round(Y[0].eval(0.0F)));
/*    */ 
/* 78 */       for (int i = 0; i < X.length; i++) {
/* 79 */         for (int j = 1; j <= 12; j++) {
/* 80 */           float u = j / 12.0F;
/* 81 */           p.addPoint(Math.round(X[i].eval(u)), Math.round(Y[i].eval(u)));
/*    */         }
/*    */       }
/*    */ 
/* 85 */       g.drawPolyline(p.xpoints, p.ypoints, p.npoints);
/*    */ 
/* 87 */       for (int i = 0; i < this.pts.npoints; i++)
/* 88 */         g.drawOval(this.pts.xpoints[i], this.pts.ypoints[i], 3, 3);
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.NatCubic
 * JD-Core Version:    0.6.2
 */