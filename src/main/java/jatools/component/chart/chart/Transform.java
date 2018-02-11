/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class Transform
/*     */   implements Serializable
/*     */ {
/*     */   private double scaleX;
/*     */   private double scaleY;
/*     */   private double shiftX;
/*     */   private double shiftY;
/*     */   private double dateShiftX;
/*     */   private double dateScaleX;
/*     */   private double logScaleX;
/*     */   private double logScaleY;
/*     */   private double logShiftX;
/*     */   private double logShiftY;
/*  49 */   protected boolean logXScaling = false;
/*  50 */   protected boolean logYScaling = false;
/*     */   static final double LOG_10_E = 0.4342942D;
/*     */   static final int MAX_VAL = 8192;
/*     */   static final int MIN_VAL = -8192;
/*     */ 
/*     */   public Transform(double dllX, double dllY, double durX, double durY, int illX, int illY, int iurX, int iurY)
/*     */   {
/*  78 */     double logdllX = log10(dllX);
/*  79 */     double logdllY = log10(dllY);
/*  80 */     double logdurX = log10(durX);
/*  81 */     double logdurY = log10(durY);
/*     */ 
/*  83 */     this.scaleX = ((iurX - illX) / (durX - dllX));
/*  84 */     this.scaleY = ((iurY - illY) / (durY - dllY));
/*     */ 
/*  86 */     this.shiftX = (illX - this.scaleX * dllX);
/*  87 */     this.shiftY = (illY - this.scaleY * dllY);
/*     */ 
/*  90 */     this.logScaleX = ((iurX - illX) / (logdurX - logdllX));
/*  91 */     this.logScaleY = ((iurY - illY) / (logdurY - logdllY));
/*     */ 
/*  93 */     this.logShiftX = (illX - this.logScaleX * logdllX);
/*  94 */     this.logShiftY = (illY - this.logScaleY * logdllY);
/*     */   }
/*     */ 
/*     */   public Transform(double dllX, double dllY, double durX, double durY, Point ll, Point ur)
/*     */   {
/* 114 */     double logdllX = log10(dllX);
/* 115 */     double logdllY = log10(dllY);
/* 116 */     double logdurX = log10(durX);
/* 117 */     double logdurY = log10(durY);
/*     */ 
/* 123 */     this.scaleX = ((ur.x - ll.x) / (durX - dllX));
/*     */ 
/* 125 */     this.scaleY = ((ur.y - ll.y) / (durY - dllY));
/*     */ 
/* 127 */     this.shiftX = (ll.x - this.scaleX * dllX);
/*     */ 
/* 133 */     this.shiftY = (ll.y - this.scaleY * dllY);
/*     */ 
/* 136 */     this.logScaleX = ((ur.x - ll.x) / (logdurX - logdllX));
/* 137 */     this.logScaleY = ((ur.y - ll.y) / (logdurY - logdllY));
/*     */ 
/* 139 */     this.logShiftX = (ll.x - this.logScaleX * logdllX);
/* 140 */     this.logShiftY = (ll.y - this.logScaleY * logdllY);
/*     */   }
/*     */   static double log10(double inVal) {
/* 143 */     return Math.log(inVal) * 0.4342942D;
/*     */   }
/*     */ 
/*     */   public Point point(double x, double y)
/*     */   {
/*     */     int iX;
/*     */     int iX;
/* 153 */     if (!this.logXScaling)
/*     */     {
/* 158 */       double dX = x * this.scaleX + this.shiftX;
/*     */       int iX;
/* 163 */       if (dX > 8192.0D) {
/* 164 */         iX = 8192;
/*     */       }
/*     */       else
/*     */       {
/*     */         int iX;
/* 165 */         if (dX < -8192.0D)
/* 166 */           iX = -8192;
/* 167 */         else iX = (int)dX;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 172 */       iX = (int)(log10(x) * this.logScaleX + this.logShiftX);
/*     */     }
/*     */     int iY;
/*     */     int iY;
/* 174 */     if (!this.logYScaling)
/*     */     {
/* 176 */       double dY = y * this.scaleY + this.shiftY;
/*     */       int iY;
/* 177 */       if (dY > 8192.0D) {
/* 178 */         iY = 8192;
/*     */       }
/*     */       else
/*     */       {
/*     */         int iY;
/* 179 */         if (dY < -8192.0D)
/* 180 */           iY = -8192;
/* 181 */         else iY = (int)dY; 
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 185 */       iY = (int)(log10(y) * this.logScaleY + this.logShiftY);
/*     */     }
/* 187 */     return new Point(iX, iY);
/*     */   }
/*     */ 
/*     */   public Point[] pointList(double[] x, double[] y)
/*     */   {
/* 198 */     Point[] pts = new Point[x.length];
/* 199 */     for (int i = 0; i < x.length; i++) {
/* 200 */       pts[i] = point(x[i], y[i]);
/*     */     }
/* 202 */     return pts;
/*     */   }
/*     */   public String toString() {
/* 205 */     return getClass().getName() + "[" + 
/* 206 */       " shiftX " + this.shiftX + 
/* 207 */       " shiftY " + this.shiftY + 
/* 208 */       " scaleX " + this.scaleX + 
/* 209 */       " scaleY " + this.scaleY + 
/* 210 */       " ]";
/*     */   }
/*     */ 
/*     */   public double xValue(int ix)
/*     */   {
/* 219 */     double x = ix;
/* 220 */     if (!this.logXScaling) {
/* 221 */       x = (x - this.shiftX) / this.scaleX;
/* 222 */       return x;
/*     */     }
/*     */ 
/* 225 */     x = (x - this.logShiftX) / this.logScaleX;
/* 226 */     return log10(x);
/*     */   }
/*     */ 
/*     */   public double yValue(int iy)
/*     */   {
/* 237 */     double y = iy;
/* 238 */     if (!this.logYScaling) {
/* 239 */       y = (y - this.shiftY) / this.scaleY;
/* 240 */       return y;
/*     */     }
/*     */ 
/* 243 */     y = (y - this.logShiftY) / this.logScaleY;
/* 244 */     return log10(y);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 249 */     long end = new Date(105, 0, 15).getTime();
/* 250 */     long start = new Date(105, 0, 1).getTime();
/* 251 */     long now = new Date(105, 0, 3).getTime();
/*     */ 
/* 253 */     double x = 1.0D * (now - start) / (end - start);
/*     */ 
/* 255 */     System.out.println(x);
/* 256 */     System.out.println("start = " + start);
/* 257 */     System.out.println("end = " + end);
/* 258 */     System.out.println("now = " + now);
/* 259 */     System.out.println("now - start = " + (now - start));
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Transform
 * JD-Core Version:    0.6.2
 */