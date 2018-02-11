/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class Area extends DataRepresentation
/*     */   implements Serializable
/*     */ {
/*     */   double xAxisStart;
/*     */   double xAxisEnd;
/*     */   double yAxisStart;
/*     */   double yAxisEnd;
/*  28 */   boolean doClip = false;
/*     */   Transform dataXfm;
/*  30 */   double baseline = 0.0D;
/*  31 */   private boolean stackAreas = true;
/*     */   private int numDatasets;
/*     */ 
/*     */   public Area()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Area(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*     */   {
/*  54 */     this.datasets = d;
/*  55 */     this.xAxis = xAx;
/*  56 */     this.yAxis = yAx;
/*  57 */     this.plotarea = subplot;
/*  58 */     this.globals = this.plotarea.globals;
/*     */   }
/*     */ 
/*     */   private int datasetsInUse()
/*     */   {
/*  64 */     for (int i = 0; i < this.datasets.length; i++)
/*  65 */       if (this.datasets[i] == null)
/*  66 */         return i;
/*  67 */     return i;
/*     */   }
/*     */ 
/*     */   private synchronized void doArea(Graphics g, int whichSet)
/*     */   {
/*  79 */     double[] xarr = this.datasets[whichSet].getXValues();
/*  80 */     double[] yarr = this.datasets[whichSet].getYValues();
/*     */ 
/*  82 */     double[] stackedYarr = new double[yarr.length + 2];
/*  83 */     double[] stackedXarr = new double[xarr.length + 2];
/*  84 */     for (int i = 0; i < yarr.length; i++) {
/*  85 */       yarr[i] += getBaseY(whichSet, i);
/*  86 */       stackedXarr[i] = xarr[i];
/*     */     }
/*  88 */     stackedXarr[i] = stackedXarr[(i - 1)];
/*  89 */     stackedXarr[(i + 1)] = stackedXarr[0];
/*  90 */     if (this.stackAreas) {
/*  91 */       stackedYarr[i] = this.yAxis.getAxisStart();
/*     */     }
/*  94 */     else if (this.baseline > this.yAxis.getAxisStart())
/*  95 */       stackedYarr[i] = this.baseline;
/*     */     else {
/*  97 */       stackedYarr[i] = this.yAxis.getAxisStart();
/*     */     }
/*  99 */     stackedYarr[(i + 1)] = stackedYarr[i];
/*     */ 
/* 101 */     if (this.globals.threeD) {
/* 102 */       if ((this.baseline < this.yAxis.getAxisEnd()) && (this.baseline > this.yAxis.getAxisStart())) {
/* 103 */         doBaseline(g, whichSet);
/*     */       }
/* 105 */       if ((whichSet == this.numDatasets - 1) || (!this.stackAreas))
/* 106 */         doTop(g, whichSet, stackedXarr, stackedYarr);
/* 107 */       doSide(g, whichSet, stackedXarr[(stackedXarr.length - 3)], 
/* 108 */         stackedYarr[(stackedYarr.length - 3)]);
/*     */     }
/* 110 */     Point[] p = this.dataXfm.pointList(stackedXarr, stackedYarr);
/* 111 */     this.datasets[whichSet].gc.drawPolygon(g, p);
/* 112 */     if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/* 113 */       Dataset dataset = this.datasets[whichSet];
/* 114 */       this.globals.displayList.addPolygon(dataset, p);
/* 115 */       int polygonBase = p[(p.length - 1)].y;
/*     */ 
/* 117 */       Point[] oneArea = new Point[4];
/* 118 */       oneArea[0] = p[0];
/* 119 */       oneArea[1] = halfwayPoint(p[0], p[1]);
/* 120 */       oneArea[2] = new Point(oneArea[1].x, polygonBase);
/* 121 */       oneArea[3] = p[(p.length - 1)];
/* 122 */       this.globals.displayList.addPolygon(dataset.getData().get(0), oneArea);
/*     */ 
/* 124 */       oneArea[0] = halfwayPoint(p[(p.length - 3)], p[(p.length - 4)]);
/* 125 */       oneArea[1] = p[(p.length - 3)];
/* 126 */       oneArea[2] = p[(p.length - 2)];
/* 127 */       oneArea[3] = new Point(oneArea[0].x, polygonBase);
/* 128 */       this.globals.displayList.addPolygon(dataset.getData().get(dataset.getData().size() - 1), oneArea);
/* 129 */       oneArea = new Point[5];
/* 130 */       int start = 1;
/* 131 */       int end = dataset.getData().size() - 1;
/* 132 */       for (i = start; i < end; i++) {
/* 133 */         oneArea[0] = halfwayPoint(p[(i - 1)], p[i]);
/* 134 */         oneArea[1] = p[i];
/* 135 */         oneArea[2] = halfwayPoint(p[i], p[(i + 1)]);
/* 136 */         oneArea[3] = new Point(oneArea[2].x, polygonBase);
/* 137 */         oneArea[4] = new Point(oneArea[0].x, polygonBase);
/* 138 */         this.globals.displayList.addPolygon(dataset.getData().get(i), oneArea);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private Point halfwayPoint(Point p1, Point p2) {
/* 144 */     return new Point(p1.x + (p2.x - p1.x) / 2, p1.y + (p2.y - p1.y) / 2);
/*     */   }
/*     */ 
/*     */   private synchronized void doAreas(Graphics g)
/*     */   {
/* 153 */     this.numDatasets = datasetsInUse();
/*     */ 
/* 155 */     this.xAxisStart = this.xAxis.getAxisStart();
/* 156 */     this.xAxisEnd = this.xAxis.getAxisEnd();
/*     */ 
/* 160 */     this.dataXfm = new Transform(this.xAxisStart, this.yAxis.getAxisStart(), 
/* 161 */       this.xAxisEnd, this.yAxis.getAxisEnd(), 
/* 162 */       this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY), 
/* 163 */       this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY));
/* 164 */     this.dataXfm.logXScaling = this.xAxis.getLogScaling();
/* 165 */     this.dataXfm.logYScaling = this.yAxis.getLogScaling();
/*     */ 
/* 167 */     Graphics saveG = g;
/* 168 */     if ((this.doClip) || ((this.globals.threeD) && (!this.stackAreas))) {
/* 169 */       g = g.create();
/* 170 */       if (this.doClip) {
/* 171 */         Point clipLL = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY);
/* 172 */         Point clipUR = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY);
/* 173 */         g.clipRect(clipLL.x, this.globals.maxY - clipUR.y, 
/* 174 */           clipUR.x - clipLL.x, 
/* 175 */           clipUR.y - clipLL.y);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 180 */     if ((this.globals.threeD) && (!this.stackAreas)) {
/* 181 */       g.translate(this.globals.xOffset, -this.globals.yOffset);
/*     */     }
/* 183 */     for (int i = 0; i < this.numDatasets; i++) {
/* 184 */       if ((this.globals.threeD) && (!this.stackAreas)) {
/* 185 */         g.translate(-this.globals.xOffset / this.numDatasets, this.globals.yOffset / this.numDatasets);
/*     */       }
/* 187 */       doArea(g, this.numDatasets - i - 1);
/*     */     }
/*     */ 
/* 190 */     g = saveG;
/*     */   }
/*     */ 
/*     */   private synchronized void doSide(Graphics g, int whichSet, double topX, double topY)
/*     */   {
/* 200 */     Point[] pts = new Point[4];
/*     */ 
/* 202 */     Color saveColor = this.datasets[whichSet].gc.fillColor;
/* 203 */     this.datasets[whichSet].gc.fillColor = saveColor.darker();
/* 204 */     pts[0] = this.dataXfm.point(topX, topY);
/* 205 */     double bottomX = topX;
/* 206 */     double bottomY = getBaseY(whichSet, this.datasets[whichSet].data.size() - 1);
/* 207 */     bottomY = this.yAxis.getAxisStart() > bottomY ? this.yAxis.getAxisStart() : bottomY;
/* 208 */     pts[1] = this.dataXfm.point(bottomX, bottomY);
/* 209 */     int xOffset = this.globals.xOffset;
/* 210 */     int yOffset = this.globals.yOffset;
/* 211 */     if (!this.stackAreas) {
/* 212 */       xOffset /= this.numDatasets;
/* 213 */       yOffset /= this.numDatasets;
/*     */     }
/* 215 */     pts[2] = new Point(pts[1].x + xOffset, pts[1].y + yOffset);
/* 216 */     pts[3] = new Point(pts[0].x + xOffset, pts[0].y + yOffset);
/* 217 */     this.datasets[whichSet].gc.drawPolygon(g, pts);
/* 218 */     this.datasets[whichSet].gc.fillColor = saveColor;
/*     */   }
/*     */ 
/*     */   private synchronized void doBaseline(Graphics g, int whichSet)
/*     */   {
/* 227 */     Point[] pts = new Point[4];
/*     */ 
/* 229 */     Dataset dataset = this.datasets[whichSet];
/*     */ 
/* 231 */     Color saveColor = dataset.gc.fillColor;
/* 232 */     dataset.gc.fillColor = saveColor.darker();
/* 233 */     double firstX = dataset.getDataElementAt(0).x;
/* 234 */     double lastX = dataset.getDataElementAt(dataset.getData().size() - 1).x;
/* 235 */     pts[0] = this.dataXfm.point(firstX, this.baseline);
/* 236 */     pts[1] = this.dataXfm.point(lastX, this.baseline);
/*     */ 
/* 238 */     int xOffset = this.globals.xOffset;
/* 239 */     int yOffset = this.globals.yOffset;
/* 240 */     if (!this.stackAreas) {
/* 241 */       xOffset /= this.numDatasets;
/* 242 */       yOffset /= this.numDatasets;
/*     */     }
/* 244 */     pts[2] = new Point(pts[1].x + xOffset, pts[1].y + yOffset);
/* 245 */     pts[3] = new Point(pts[0].x + xOffset, pts[0].y + yOffset);
/* 246 */     this.datasets[whichSet].gc.drawPolygon(g, pts);
/* 247 */     this.datasets[whichSet].gc.fillColor = saveColor;
/*     */   }
/*     */ 
/*     */   private synchronized void doTop(Graphics g, int whichSet, double[] xarr, double[] yarr)
/*     */   {
/* 260 */     int xWidth = this.globals.xOffset;
/* 261 */     int yWidth = this.globals.yOffset;
/* 262 */     if (!this.stackAreas) {
/* 263 */       xWidth /= this.numDatasets;
/* 264 */       yWidth /= this.numDatasets;
/*     */     }
/* 266 */     Point[] pts = new Point[4];
/* 267 */     Color saveColor = this.datasets[whichSet].gc.fillColor;
/* 268 */     this.datasets[whichSet].gc.fillColor = saveColor.darker();
/* 269 */     for (int i = 0; i < xarr.length - 3; i++) {
/* 270 */       pts[0] = this.dataXfm.point(xarr[i], yarr[i]);
/* 271 */       pts[1] = new Point(pts[0].x + xWidth, 
/* 272 */         pts[0].y + yWidth);
/* 273 */       pts[3] = this.dataXfm.point(xarr[(i + 1)], yarr[(i + 1)]);
/* 274 */       pts[2] = new Point(pts[3].x + xWidth, 
/* 275 */         pts[3].y + yWidth);
/* 276 */       this.datasets[whichSet].gc.drawPolygon(g, pts);
/*     */     }
/*     */ 
/* 279 */     this.datasets[whichSet].gc.fillColor = saveColor;
/*     */   }
/*     */ 
/*     */   public void draw(Graphics g)
/*     */   {
/* 286 */     if (g == null) {
/* 287 */       return;
/*     */     }
/* 289 */     double saveBase = this.baseline;
/* 290 */     if ((this.yAxis.getLogScaling()) && 
/* 291 */       (this.baseline <= 0.0D)) {
/* 292 */       this.baseline = this.yAxis.getAxisStart();
/*     */     }
/* 294 */     doAreas(g);
/* 295 */     this.baseline = saveBase;
/*     */   }
/*     */ 
/*     */   public double getBaseline()
/*     */   {
/* 303 */     return this.baseline;
/*     */   }
/*     */ 
/*     */   protected synchronized double getBaseY(int whichSet, int whichPoint)
/*     */   {
/* 313 */     double val = 0.0D;
/*     */ 
/* 315 */     if (!this.stackAreas) {
/* 316 */       return 0.0D;
/*     */     }
/*     */ 
/* 319 */     for (int i = 0; i < whichSet; i++) {
/* 320 */       val += this.datasets[i].getDataElementAt(whichPoint).y;
/*     */     }
/* 322 */     if ((val <= 0.0D) && 
/* 323 */       (this.yAxis.getLogScaling())) {
/* 324 */       val = this.yAxis.getAxisStart();
/*     */     }
/* 326 */     return val;
/*     */   }
/*     */ 
/*     */   public boolean getDoClip()
/*     */   {
/* 334 */     return this.doClip;
/*     */   }
/*     */ 
/*     */   public boolean getStackAreas()
/*     */   {
/* 342 */     return this.stackAreas;
/*     */   }
/*     */ 
/*     */   public void setBaseline(double baseline)
/*     */   {
/* 350 */     this.baseline = baseline;
/*     */   }
/*     */ 
/*     */   public void setDoClip(boolean onOff)
/*     */   {
/* 363 */     this.doClip = onOff;
/*     */   }
/*     */ 
/*     */   public void setStackAreas(boolean stackAreas)
/*     */   {
/* 371 */     this.stackAreas = stackAreas;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Area
 * JD-Core Version:    0.6.2
 */