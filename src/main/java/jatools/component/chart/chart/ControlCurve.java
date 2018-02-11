/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Polygon;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ControlCurve
/*     */ {
/*     */   static final int EPSILON = 36;
/*     */   protected Polygon pts;
/*  20 */   protected int selection = -1;
/*     */ 
/*     */   public ControlCurve(List ps)
/*     */   {
/*  26 */     this.pts = new Polygon();
/*     */ 
/*  28 */     if (ps != null) {
/*  29 */       Iterator it = ps.iterator();
/*     */ 
/*  31 */       while (it.hasNext()) {
/*  32 */         Point p = (Point)it.next();
/*     */ 
/*  34 */         this.pts.addPoint(p.x, p.y);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int selectPoint(int x, int y)
/*     */   {
/*  50 */     int mind = 2147483647;
/*  51 */     this.selection = -1;
/*     */ 
/*  53 */     for (int i = 0; i < this.pts.npoints; i++) {
/*  54 */       int d = sqr(this.pts.xpoints[i] - x) + sqr(this.pts.ypoints[i] - y);
/*     */ 
/*  56 */       if ((d < mind) && (d < 36)) {
/*  57 */         mind = d;
/*  58 */         this.selection = i;
/*     */       }
/*     */     }
/*     */ 
/*  62 */     return this.selection;
/*     */   }
/*     */ 
/*     */   static int sqr(int x) {
/*  66 */     return x * x;
/*     */   }
/*     */ 
/*     */   public int addPoint(int x, int y)
/*     */   {
/*  78 */     this.pts.addPoint(x, y);
/*     */ 
/*  80 */     return this.selection = this.pts.npoints - 1;
/*     */   }
/*     */ 
/*     */   public void setPoint(int x, int y)
/*     */   {
/*  90 */     if (this.selection >= 0) {
/*  91 */       this.pts.xpoints[this.selection] = x;
/*  92 */       this.pts.ypoints[this.selection] = y;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removePoint()
/*     */   {
/* 100 */     if (this.selection >= 0) {
/* 101 */       this.pts.npoints -= 1;
/*     */ 
/* 103 */       for (int i = this.selection; i < this.pts.npoints; i++) {
/* 104 */         this.pts.xpoints[i] = this.pts.xpoints[(i + 1)];
/* 105 */         this.pts.ypoints[i] = this.pts.ypoints[(i + 1)];
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.ControlCurve
 * JD-Core Version:    0.6.2
 */