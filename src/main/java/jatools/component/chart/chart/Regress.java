/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class Regress extends Line
/*     */ {
/*     */   private double slope;
/*     */   private double intercept;
/*     */   private double variance;
/*     */ 
/*     */   public Regress()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Regress(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*     */   {
/*  45 */     super(d, xAx, yAx, subplot);
/*     */   }
/*     */ 
/*     */   protected void doLine(Graphics g, int whichSet)
/*     */   {
/*  65 */     double[] xarr = this.datasets[whichSet].getXValues();
/*  66 */     double[] yarr = this.datasets[whichSet].getYValues();
/*  67 */     if (!regress(xarr, yarr)) {
/*  68 */       return;
/*     */     }
/*  70 */     double[] rXarr = new double[2];
/*  71 */     double[] rYarr = new double[2];
/*  72 */     if (!this.dataXfm.logXScaling) {
/*  73 */       rXarr[0] = this.xAxisStart;
/*  74 */       rXarr[1] = this.xAxisEnd;
/*     */     }
/*     */     else {
/*  77 */       rXarr[0] = Math.pow(10.0D, this.xAxisStart);
/*  78 */       rXarr[1] = Math.pow(10.0D, this.xAxisEnd);
/*     */     }
/*  80 */     rYarr[0] = (this.slope * this.xAxisStart + this.intercept);
/*  81 */     rYarr[1] = (rYarr[0] + (rXarr[1] - rXarr[0]) * this.slope);
/*  82 */     if (!this.globals.threeD) {
/*  83 */       this.datasets[whichSet].gc.drawPolyline(
/*  84 */         g, 
/*  85 */         this.dataXfm.pointList(rXarr, rYarr));
/*     */     } else {
/*  87 */       int xWidth = this.globals.xOffset / this.numDatasets;
/*  88 */       int yWidth = this.globals.yOffset / this.numDatasets;
/*  89 */       int startXOffset = xWidth * whichSet;
/*  90 */       int startYOffset = yWidth * whichSet;
/*  91 */       Point[] pts = new Point[4];
/*  92 */       pts[0] = this.dataXfm.point(rXarr[0], rYarr[0]);
/*  93 */       pts[0].translate(startXOffset, startYOffset);
/*  94 */       pts[1] = new Point(pts[0].x + xWidth, 
/*  95 */         pts[0].y + yWidth);
/*  96 */       pts[3] = this.dataXfm.point(rXarr[1], rYarr[1]);
/*  97 */       pts[3].translate(startXOffset, startYOffset);
/*  98 */       pts[2] = new Point(pts[3].x + xWidth, 
/*  99 */         pts[3].y + yWidth);
/* 100 */       this.datasets[whichSet].gc.drawPolygon(g, pts);
/* 101 */       this.datasets[whichSet].gc.drawPolyline(g, pts);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean regress(double[] X, double[] Y)
/*     */   {
/* 109 */     double sumX = 0.0D; double sumY = 0.0D; double sumXY = 0.0D; double sumX2 = 0.0D;
/* 110 */     int no = 0;
/*     */ 
/* 112 */     for (int i = 0; i < Y.length; i++) {
/* 113 */       no++;
/* 114 */       sumX += X[i];
/* 115 */       sumY += Y[i];
/* 116 */       sumX2 += X[i] * X[i];
/* 117 */       sumXY += X[i] * Y[i];
/*     */     }
/*     */ 
/* 120 */     if (no < 2) {
/* 121 */       System.out.println("Not enough points to perform linear regression");
/* 122 */       return false;
/*     */     }
/*     */ 
/* 125 */     this.slope = 
/* 126 */       ((no * sumXY - sumX * sumY) / (
/* 127 */       no * sumX2 - sumX * sumX));
/* 128 */     this.intercept = 
/* 129 */       ((sumY * sumX2 - sumXY * sumX) / (
/* 130 */       no * sumX2 - sumX * sumX));
/*     */ 
/* 144 */     return true;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Regress
 * JD-Core Version:    0.6.2
 */