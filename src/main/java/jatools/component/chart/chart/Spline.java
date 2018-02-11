/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.component.chart.utility.FlashGraphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Point;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Spline
/*     */ {
/*     */   private List points;
/*  78 */   private int precision = 1;
/*     */ 
/*     */   public Spline(List points, int smooth)
/*     */   {
/*  86 */     this.points = points;
/*     */   }
/*     */ 
/*     */   public int getPrecision()
/*     */   {
/*  97 */     return this.precision;
/*     */   }
/*     */ 
/*     */   public void setPrecision(int p)
/*     */   {
/* 109 */     if (p <= 0) {
/* 110 */       throw new IllegalArgumentException("Requires p > 0.");
/*     */     }
/*     */ 
/* 113 */     this.precision = p;
/*     */   }
/*     */ 
/*     */   protected void draw(Graphics2D g)
/*     */   {
/* 135 */     GeneralPath seriesPath = new GeneralPath();
/*     */ 
/* 138 */     if (this.points.size() > 1)
/*     */     {
/* 140 */       Point cp0 = (Point)this.points.get(0);
/* 141 */       seriesPath.moveTo(cp0.x, cp0.y);
/*     */ 
/* 143 */       if (this.points.size() == 2)
/*     */       {
/* 146 */         Point cp1 = (Point)this.points.get(1);
/* 147 */         seriesPath.lineTo(cp1.x, cp1.y);
/*     */       }
/*     */       else {
/* 150 */         int np = this.points.size();
/* 151 */         float[] d = new float[np];
/* 152 */         float[] x = new float[np];
/*     */ 
/* 155 */         float oldy = 0.0F;
/* 156 */         float oldt = 0.0F;
/*     */ 
/* 158 */         float[] a = new float[np];
/*     */ 
/* 161 */         float[] h = new float[np];
/*     */ 
/* 163 */         for (int i = 0; i < np; i++) {
/* 164 */           Point cpi = (Point)this.points.get(i);
/* 165 */           x[i] = cpi.x;
/* 166 */           d[i] = cpi.y;
/*     */         }
/*     */ 
/* 169 */         for (int i = 1; i <= np - 1; i++) {
/* 170 */           x[i] -= x[(i - 1)];
/*     */         }
/*     */ 
/* 173 */         float[] sub = new float[np - 1];
/* 174 */         float[] diag = new float[np - 1];
/* 175 */         float[] sup = new float[np - 1];
/*     */ 
/* 177 */         for (int i = 1; i <= np - 2; i++) {
/* 178 */           diag[i] = ((h[i] + h[(i + 1)]) / 3.0F);
/* 179 */           sup[i] = (h[(i + 1)] / 6.0F);
/* 180 */           h[i] /= 6.0F;
/* 181 */           a[i] = ((d[(i + 1)] - d[i]) / h[(i + 1)] - (d[i] - d[(i - 1)]) / h[i]);
/*     */         }
/*     */ 
/* 184 */         solveTridiag(sub, diag, sup, a, np - 2);
/*     */ 
/* 188 */         oldt = x[0];
/* 189 */         oldy = d[0];
/* 190 */         seriesPath.moveTo(oldt, oldy);
/*     */ 
/* 192 */         for (int i = 1; i <= np - 1; i++)
/*     */         {
/* 194 */           for (int j = 1; j <= this.precision; j++) {
/* 195 */             float t1 = h[i] * j / this.precision;
/* 196 */             float t2 = h[i] - t1;
/* 197 */             float y = ((-a[(i - 1)] / 6.0F * (t2 + h[i]) * t1 + d[(i - 1)]) * t2 + 
/* 198 */               (-a[i] / 6.0F * (t1 + h[i]) * t2 + d[i]) * t1) / h[i];
/* 199 */             float t = x[(i - 1)] + t1;
/* 200 */             seriesPath.lineTo(t, y);
/* 201 */             oldt = t;
/* 202 */             oldy = y;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 207 */       if ((g instanceof FlashGraphics))
/* 208 */         ((FlashGraphics)g).draw2(seriesPath);
/*     */       else
/* 210 */         g.draw(seriesPath);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void solveTridiag(float[] sub, float[] diag, float[] sup, float[] b, int n)
/*     */   {
/* 229 */     for (int i = 2; i <= n; i++) {
/* 230 */       sub[i] /= diag[(i - 1)];
/* 231 */       diag[i] -= sub[i] * sup[(i - 1)];
/* 232 */       b[i] -= sub[i] * b[(i - 1)];
/*     */     }
/*     */ 
/* 235 */     b[n] /= diag[n];
/*     */ 
/* 237 */     for (i = n - 1; i >= 1; i--)
/* 238 */       b[i] = ((b[i] - sup[i] * b[(i + 1)]) / diag[i]);
/*     */   }
/*     */ 
/*     */   class ControlPoint
/*     */   {
/*     */     public float x;
/*     */     public float y;
/*     */ 
/*     */     public ControlPoint(float x, float y)
/*     */     {
/* 259 */       this.x = x;
/* 260 */       this.y = y;
/*     */     }
/*     */ 
/*     */     public boolean equals(Object obj)
/*     */     {
/* 271 */       if (obj == this) {
/* 272 */         return true;
/*     */       }
/*     */ 
/* 275 */       if (!(obj instanceof ControlPoint)) {
/* 276 */         return false;
/*     */       }
/*     */ 
/* 279 */       ControlPoint that = (ControlPoint)obj;
/*     */ 
/* 281 */       if (this.x != that.x) {
/* 282 */         return false;
/*     */       }
/*     */ 
/* 287 */       return true;
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Spline
 * JD-Core Version:    0.6.2
 */