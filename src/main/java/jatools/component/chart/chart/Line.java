/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class Line extends DataRepresentation
/*     */   implements Serializable
/*     */ {
/*     */   boolean scatterPlot;
/*  25 */   boolean doClip = false;
/*     */   Transform dataXfm;
/*     */   double xAxisStart;
/*     */   double xAxisEnd;
/*     */   double yAxisStart;
/*     */   double yAxisEnd;
/*     */   protected int numDatasets;
/*  34 */   protected boolean useValueLabels = false;
/*  35 */   protected boolean individualMarkers = false;
/*     */   private int smooth;
/*     */ 
/*     */   public Line()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Line(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*     */   {
/*  54 */     this.datasets = d;
/*  55 */     this.xAxis = xAx;
/*  56 */     this.yAxis = yAx;
/*  57 */     this.plotarea = subplot;
/*  58 */     this.globals = this.plotarea.globals;
/*     */   }
/*     */ 
/*     */   protected void buildDataXfm() {
/*  62 */     this.xAxisStart = this.xAxis.getAxisStart();
/*  63 */     this.xAxisEnd = this.xAxis.getAxisEnd();
/*  64 */     this.yAxisStart = this.yAxis.getAxisStart();
/*  65 */     this.yAxisEnd = this.yAxis.getAxisEnd();
/*     */ 
/*  68 */     this.dataXfm = new Transform(this.xAxisStart, this.yAxisStart, this.xAxisEnd, this.yAxisEnd, 
/*  69 */       this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY), 
/*  70 */       this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY));
/*  71 */     this.dataXfm.logXScaling = this.xAxis.getLogScaling();
/*  72 */     this.dataXfm.logYScaling = this.yAxis.getLogScaling();
/*     */   }
/*     */ 
/*     */   private synchronized int datasetsInUse()
/*     */   {
/*  78 */     for (int i = 0; i < this.datasets.length; i++) {
/*  79 */       if (this.datasets[i] == null) {
/*  80 */         return i;
/*     */       }
/*     */     }
/*  83 */     return i;
/*     */   }
/*     */ 
/*     */   protected void doElementLabel(Graphics g, int whichSet, int whichElement)
/*     */   {
/*  98 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichElement);
/*     */     String str;
/*     */     String str;
/* 101 */     if ((dat.label != null) && (!this.useValueLabels)) {
/* 102 */       str = dat.label;
/*     */     }
/*     */     else
/*     */     {
/* 106 */       str = formatLabel(dat.y);
/*     */     }
/*     */ 
/* 109 */     FontMetrics fm = g.getFontMetrics();
/* 110 */     Point loc = this.dataXfm.point(dat.x, dat.y);
/* 111 */     this.datasets[whichSet].gc.drawSmartString(g, loc.x, loc.y + 2, 2, this.labelAngle, fm, str);
/*     */   }
/*     */ 
/*     */   protected void doLabels(Graphics g)
/*     */   {
/* 119 */     for (int i = 0; this.datasets[i] != null; i++) {
/* 120 */       g.setFont(this.datasets[i].labelFont);
/* 121 */       g.setColor(this.datasets[i].labelColor);
/*     */ 
/* 123 */       for (int j = 0; j < this.datasets[i].data.size(); j++)
/* 124 */         doElementLabel(g, i, j);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doLine(Graphics g, int whichSet)
/*     */   {
/* 142 */     double[] xarr = this.datasets[whichSet].getXValues();
/* 143 */     double[] yarr = this.datasets[whichSet].getYValues();
/*     */ 
/* 145 */     if (!this.globals.threeD) {
/* 146 */       Point[] points = this.dataXfm.pointList(xarr, yarr);
/*     */ 
/* 148 */       this.datasets[whichSet].gc.drawPolyline(g, points, this.smooth);
/*     */ 
/* 150 */       if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/* 151 */         this.globals.displayList.addPolyline(this.datasets[whichSet], points);
/* 152 */         this.globals.displayList.addPolyline(this, points);
/*     */       }
/*     */     } else {
/* 155 */       int xWidth = this.globals.xOffset / this.numDatasets;
/* 156 */       int yWidth = this.globals.yOffset / this.numDatasets;
/* 157 */       int startXOffset = xWidth * whichSet;
/* 158 */       int startYOffset = yWidth * whichSet;
/* 159 */       Point[] pts = new Point[4];
/*     */ 
/* 161 */       for (int i = 0; i < xarr.length - 1; i++) {
/* 162 */         pts[0] = this.dataXfm.point(xarr[i], yarr[i]);
/* 163 */         pts[0].translate(startXOffset, startYOffset);
/* 164 */         pts[1] = new Point(pts[0].x + xWidth, pts[0].y + yWidth);
/* 165 */         pts[3] = this.dataXfm.point(xarr[(i + 1)], yarr[(i + 1)]);
/* 166 */         pts[3].translate(startXOffset, startYOffset);
/* 167 */         pts[2] = new Point(pts[3].x + xWidth, pts[3].y + yWidth);
/*     */ 
/* 169 */         this.datasets[whichSet].gc.drawPolygon(g, pts);
/*     */ 
/* 172 */         if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/* 173 */           this.globals.displayList.addPolygon(this.datasets[whichSet], pts);
/* 174 */           this.globals.displayList.addPolyline(this, pts);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected synchronized void doLines(Graphics g, boolean markersOnly)
/*     */   {
/* 189 */     Graphics saveG = g;
/*     */ 
/* 191 */     this.numDatasets = datasetsInUse();
/* 192 */     buildDataXfm();
/*     */ 
/* 194 */     if (this.doClip) {
/* 195 */       g = g.create();
/* 196 */       Point clipLL = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY);
/* 197 */       Point clipUR = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY);
/* 198 */       g = g.create();
/* 199 */       g.clipRect(clipLL.x, this.globals.maxY - clipUR.y, clipUR.x - clipLL.x, 
/* 200 */         clipUR.y - clipLL.y + 1);
/*     */     }
/*     */ 
/* 203 */     for (int i = 0; i < this.numDatasets; i++) {
/* 204 */       if (markersOnly) {
/* 205 */         doMarkers(g, this.numDatasets - 1 - i);
/*     */       } else {
/* 207 */         doLine(g, this.numDatasets - 1 - i);
/* 208 */         doMarkers(g, this.numDatasets - 1 - i);
/*     */       }
/*     */ 
/* 211 */       if (this.labelsOn) {
/* 212 */         doLabels(g);
/*     */       }
/*     */     }
/*     */ 
/* 216 */     if (this.doClip)
/* 217 */       g = saveG;
/*     */   }
/*     */ 
/*     */   protected void doMarkers(Graphics g, int whichSet)
/*     */   {
/* 227 */     boolean hasDatasetImage = true;
/*     */ 
/* 230 */     if ((!this.individualMarkers) && (this.datasets[whichSet].gc.image == null) && 
/* 231 */       (this.datasets[whichSet].gc.markerStyle == -1))
/*     */     {
/* 233 */       if (this.useDisplayList)
/* 234 */         hasDatasetImage = false;
/*     */       else {
/* 236 */         return;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 241 */     if (this.individualMarkers) {
/* 242 */       hasDatasetImage = false;
/*     */     }
/*     */ 
/* 245 */     double[] xarr = this.datasets[whichSet].getXValues();
/* 246 */     double[] yarr = this.datasets[whichSet].getYValues();
/*     */     int imgHeight;
/*     */     int imgWidth;
/*     */     int imgHeight;
/* 250 */     if ((hasDatasetImage) && (this.datasets[whichSet].gc.image != null)) {
/* 251 */       int imgWidth = this.datasets[whichSet].gc.image.getWidth(null);
/* 252 */       imgHeight = this.datasets[whichSet].gc.image.getHeight(null);
/*     */     } else {
/* 254 */       imgWidth = 8;
/* 255 */       imgHeight = 8;
/*     */ 
/* 257 */       if (this.individualMarkers) {
/* 258 */         Point[] pts = this.dataXfm.pointList(xarr, yarr);
/* 259 */         Point[] ipt = new Point[1];
/*     */ 
/* 261 */         for (int i = 0; i < xarr.length; i++) {
/* 262 */           Gc gc = this.datasets[whichSet].getDataElementAt(i).gc;
/*     */ 
/* 264 */           if (gc.image != null) {
/* 265 */             gc.drawImage(g, pts[i]);
/*     */           } else {
/* 267 */             ipt[0] = pts[i];
/* 268 */             gc.drawPolymarker(g, ipt);
/*     */           }
/*     */         }
/*     */       } else {
/* 272 */         this.datasets[whichSet].gc.drawPolymarker(g, this.dataXfm.pointList(xarr, yarr));
/*     */       }
/*     */     }
/*     */ 
/* 276 */     for (int i = 0; i < yarr.length; i++) {
/* 277 */       if (hasDatasetImage) {
/* 278 */         this.datasets[whichSet].gc.drawImage(g, this.dataXfm.point(xarr[i], yarr[i]));
/*     */       }
/*     */ 
/* 281 */       if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/* 282 */         Point ur = this.dataXfm.point(xarr[i], yarr[i]);
/* 283 */         ur.translate(imgWidth / 2, imgHeight / 2);
/* 284 */         Point ll = new Point(ur.x - imgWidth, ur.y - imgHeight);
/* 285 */         this.globals.displayList.addRectangle(this.datasets[whichSet], ll, ur);
/* 286 */         this.globals.displayList.addRectangle(this.datasets[whichSet].getDataElementAt(i), ll, ur);
/* 287 */         this.globals.displayList.addRectangle(this, ll, ur);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void draw(Graphics g)
/*     */   {
/* 298 */     if (g == null) {
/* 299 */       return;
/*     */     }
/*     */ 
/* 302 */     doLines(g, this.scatterPlot);
/*     */   }
/*     */ 
/*     */   synchronized void drawSet(Graphics g, int setNumber, boolean markersOnly)
/*     */   {
/* 313 */     buildDataXfm();
/* 314 */     this.numDatasets = 1;
/*     */ 
/* 317 */     if (this.doClip) {
/* 318 */       Point clipLL = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY);
/* 319 */       Point clipUR = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY);
/* 320 */       g.clipRect(clipLL.x, this.globals.maxY - clipUR.y, clipUR.x - clipLL.x, clipUR.y - clipLL.y);
/*     */     }
/*     */ 
/* 323 */     if (markersOnly) {
/* 324 */       doMarkers(g, setNumber);
/*     */     } else {
/* 326 */       doLine(g, setNumber);
/* 327 */       doMarkers(g, setNumber);
/*     */     }
/*     */ 
/* 330 */     if (this.labelsOn) {
/* 331 */       doLabels(g);
/*     */     }
/*     */ 
/* 338 */     if (this.doClip)
/* 339 */       g.clipRect(0, 0, this.globals.maxX, this.globals.maxY);
/*     */   }
/*     */ 
/*     */   public boolean getClip()
/*     */   {
/* 348 */     return this.doClip;
/*     */   }
/*     */ 
/*     */   public Dataset[] getDatasets()
/*     */   {
/* 356 */     return this.datasets;
/*     */   }
/*     */ 
/*     */   public boolean getIndividualMarkers()
/*     */   {
/* 365 */     return this.individualMarkers;
/*     */   }
/*     */ 
/*     */   public Plotarea getPlotarea()
/*     */   {
/* 373 */     return this.plotarea;
/*     */   }
/*     */ 
/*     */   public boolean getUseValueLabels()
/*     */   {
/* 382 */     return this.useValueLabels;
/*     */   }
/*     */ 
/*     */   public boolean isScatterPlot()
/*     */   {
/* 391 */     return this.scatterPlot;
/*     */   }
/*     */ 
/*     */   public void setClip(boolean onOff)
/*     */   {
/* 402 */     this.doClip = onOff;
/*     */   }
/*     */ 
/*     */   public void setIndividualMarkers(boolean trueFalse)
/*     */   {
/* 411 */     this.individualMarkers = trueFalse;
/*     */   }
/*     */ 
/*     */   public void setScatterPlot(boolean b)
/*     */   {
/* 420 */     this.scatterPlot = b;
/*     */   }
/*     */ 
/*     */   public void setUseValueLabels(boolean yesNo)
/*     */   {
/* 428 */     this.useValueLabels = yesNo;
/*     */   }
/*     */ 
/*     */   public void setSmooth(int smooth)
/*     */   {
/* 433 */     this.smooth = smooth;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Line
 * JD-Core Version:    0.6.2
 */