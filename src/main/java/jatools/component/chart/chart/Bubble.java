/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class Bubble extends Bar
/*     */ {
/*     */   static final int STARTANGLE = 0;
/*     */   static final int ENDANGLE = 360;
/*  24 */   double zScale = 1.0D;
/*     */   double xAxisStart;
/*     */   double xAxisEnd;
/*     */   double yAxisStart;
/*     */   double yAxisEnd;
/*  34 */   protected boolean autoZScale = true;
/*     */ 
/*  40 */   public double maxDiameter = 0.25D;
/*  41 */   public boolean fillBubbles = true;
/*     */   private static final int nCirclePoints = 100;
/*     */ 
/*     */   public Bubble()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Bubble(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*     */   {
/*  50 */     this.datasets = d;
/*  51 */     this.xAxis = xAx;
/*  52 */     this.yAxis = yAx;
/*  53 */     this.plotarea = subplot;
/*     */   }
/*     */ 
/*     */   protected int datasetsInUse()
/*     */   {
/*  63 */     for (int i = 0; i < this.datasets.length; i++)
/*  64 */       if (this.datasets[i] == null)
/*  65 */         return i;
/*  66 */     return i;
/*     */   }
/*     */ 
/*     */   protected void doBubble(Graphics g, int whichset, int whichbubble)
/*     */   {
/*  79 */     Datum dat = this.datasets[whichset].getDataElementAt(whichbubble);
/*     */ 
/*  81 */     double xcord = dat.x;
/*  82 */     double ycord = dat.y;
/*     */     double diameter;
/*     */     double diameter;
/*  83 */     if (!Double.isInfinite(dat.y2)) {
/*  84 */       diameter = dat.y2 * 2.0D;
/*     */     }
/*     */     else {
/*  87 */       diameter = dat.y / 5.0D;
/*     */     }
/*     */ 
/*  90 */     if (Double.isInfinite(this.zScale)) {
/*  91 */       this.zScale = 1.0D;
/*     */     }
/*     */ 
/*  94 */     Point center = this.dataXfm.point(xcord, ycord);
/*     */     Point widthHeight;
/*     */     Point widthHeight;
/*  96 */     if (this.autoZScale)
/*     */     {
/*  98 */       widthHeight = new Point(
/*  99 */         (int)(this.maxDiameter * diameter * this.zScale), 
/* 100 */         (int)(this.maxDiameter * diameter * this.zScale));
/*     */     }
/* 102 */     else widthHeight = new Point((int)(diameter * this.zScale), (int)(diameter * this.zScale));
/*     */ 
/* 105 */     if (this.fillBubbles) {
/* 106 */       fillCircle(this.datasets[whichset].gc, g, center, widthHeight);
/*     */     }
/*     */     else {
/* 109 */       drawCircle(this.datasets[whichset].gc, g, center, widthHeight);
/*     */     }
/*     */ 
/* 113 */     if ((this.useDisplayList) && (this.globals.getUseDisplayList())) {
/* 114 */       this.globals.getDisplayList().addArc(
/* 115 */         this.datasets[whichset], center, widthHeight, 0, 360);
/* 116 */       this.globals.getDisplayList().addArc(
/* 117 */         this.datasets[whichset].getDataElementAt(whichbubble), center, widthHeight, 0, 360);
/* 118 */       this.globals.getDisplayList().addArc(
/* 119 */         this, center, widthHeight, 0, 360);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doBubbleLabel(Graphics g, int whichset, int whichbubble)
/*     */   {
/* 142 */     Datum element = this.datasets[whichset].getDataElementAt(whichbubble);
/*     */ 
/* 144 */     String elementLabel = element.getLabel();
/*     */     String str;
/*     */     String str;
/* 147 */     if (elementLabel != null) {
/* 148 */       str = elementLabel;
/*     */     }
/*     */     else
/*     */     {
/* 152 */       str = formatLabel(element.getY());
/*     */     }
/*     */ 
/* 155 */     FontMetrics fm = g.getFontMetrics();
/* 156 */     Point loc = this.dataXfm.point(element.getX(), element.getY());
/*     */ 
/* 159 */     this.datasets[whichset].getGc().drawString(g, loc.x - fm.stringWidth(str) / 2, 
/* 160 */       loc.y - fm.getMaxAscent() / 2, str);
/*     */   }
/*     */ 
/*     */   protected void doBubbles(Graphics g)
/*     */   {
/* 201 */     int numDatasets = datasetsInUse();
/*     */ 
/* 204 */     for (int i = 0; i < numDatasets; i++) {
/* 205 */       for (int j = 0; j < this.datasets[i].data.size(); j++) {
/* 206 */         doBubble(g, i, j);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 211 */     if (this.labelsOn)
/*     */     {
/* 213 */       for (i = 0; this.datasets[i] != null; i++) {
/* 214 */         g.setFont(this.datasets[i].getLabelFont());
/* 215 */         g.setColor(this.datasets[i].getLabelColor());
/* 216 */         for (int j = 0; j < this.datasets[i].getData().size(); j++)
/* 217 */           doBubbleLabel(g, i, j);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void draw(Graphics g)
/*     */   {
/* 229 */     buildDataXfm();
/*     */ 
/* 232 */     if (this.autoZScale) {
/* 233 */       getNewZScale();
/*     */     }
/* 235 */     doBubbles(g);
/*     */   }
/*     */ 
/*     */   public boolean getAutoZScale()
/*     */   {
/* 243 */     return this.autoZScale;
/*     */   }
/*     */ 
/*     */   protected void getNewZScale()
/*     */   {
/* 251 */     double maxZ = 0.0D;
/*     */ 
/* 253 */     int numDatasets = datasetsInUse();
/*     */ 
/* 256 */     for (int i = 0; i < numDatasets; i++) {
/* 257 */       double tempMax = this.datasets[i].maxY2();
/* 258 */       if (tempMax > maxZ) {
/* 259 */         maxZ = tempMax;
/*     */       }
/*     */     }
/*     */ 
/* 263 */     this.zScale = 
/* 264 */       (2.0D * this.maxDiameter / (
/* 264 */       2.0D * maxZ / (this.globals.getMaxX() * (this.plotarea.urX - this.plotarea.llX)) + 
/* 265 */       2.0D * maxZ / (this.globals.getMaxY() * (this.plotarea.urY - this.plotarea.llY))));
/*     */   }
/*     */ 
/*     */   public double getZScale()
/*     */   {
/* 273 */     return this.zScale;
/*     */   }
/*     */ 
/*     */   public void setAutoZScale(boolean newAutoZScale)
/*     */   {
/* 281 */     this.autoZScale = newAutoZScale;
/*     */   }
/*     */ 
/*     */   public void setZScale(double z)
/*     */   {
/* 289 */     this.zScale = z;
/*     */   }
/*     */ 
/*     */   protected void fillCircle(Gc gc, Graphics g, Point center, Point heightWidth)
/*     */   {
/* 300 */     doCircle(true, gc, g, center, heightWidth);
/*     */   }
/*     */ 
/*     */   protected void drawCircle(Gc gc, Graphics g, Point center, Point heightWidth)
/*     */   {
/* 305 */     doCircle(false, gc, g, center, heightWidth);
/*     */   }
/*     */ 
/*     */   protected void doCircle(boolean doFill, Gc gc, Graphics g, Point center, Point heightWidth)
/*     */   {
/* 313 */     double increment = 0.06283185307179587D;
/*     */ 
/* 315 */     Point[] arcArray = new Point[100];
/* 316 */     float[] xFloat = new float[100];
/* 317 */     float[] yFloat = new float[100];
/* 318 */     double currentRadians = 0.0D;
/*     */ 
/* 320 */     for (int i = 0; i < 100; i++) {
/* 321 */       double xVal = center.x + Math.cos(currentRadians) * (heightWidth.x / 2);
/* 322 */       double yVal = center.y + Math.sin(currentRadians) * (heightWidth.y / 2);
/* 323 */       xFloat[i] = ((float)xVal);
/* 324 */       yFloat[i] = ((float)yVal);
/* 325 */       arcArray[i] = new Point((int)Math.round(xVal), (int)Math.round(yVal));
/* 326 */       currentRadians += increment;
/*     */     }
/* 328 */     if (doFill) {
/* 329 */       if (gc.getHelper() != null) {
/* 330 */         gc.getHelper().drawPolygon(g, xFloat, yFloat);
/*     */       }
/*     */       else {
/* 333 */         gc.drawPolygon(g, arcArray);
/*     */       }
/*     */     }
/*     */     else
/* 337 */       gc.drawPolyline(g, arcArray);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Bubble
 * JD-Core Version:    0.6.2
 */