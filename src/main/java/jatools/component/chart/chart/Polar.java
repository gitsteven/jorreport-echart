/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Polar extends DataRepresentation
/*     */   implements Serializable
/*     */ {
/*     */   protected PolarAxis axis;
/*     */   protected Plotarea plotarea;
/*     */   protected Transform dataXfm;
/*  24 */   boolean scatterPlot = false;
/*     */ 
/*     */   public Polar()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Polar(Dataset[] d, PolarAxis ax, Plotarea subplot)
/*     */   {
/*  43 */     this.datasets = d;
/*  44 */     this.axis = ax;
/*  45 */     this.plotarea = subplot;
/*  46 */     this.globals = this.plotarea.globals;
/*     */   }
/*     */ 
/*     */   protected void doElementLabel(Graphics g, Point loc, int whichSet, int whichElement)
/*     */   {
/*  57 */     Datum element = this.datasets[whichSet].getDataElementAt(whichElement);
/*     */     String str;
/*     */     String str;
/*  60 */     if (element.label != null) {
/*  61 */       str = element.label;
/*     */     }
/*     */     else {
/*  64 */       str = formatLabel(element.y);
/*     */     }
/*     */ 
/*  67 */     FontMetrics fm = g.getFontMetrics();
/*  68 */     this.datasets[whichSet].gc.drawSmartString(g, 
/*  69 */       loc.x, loc.y + 2, 2, this.labelAngle, fm, str);
/*     */   }
/*     */ 
/*     */   protected void doLabels(Graphics g, int whichSet, Point[] location)
/*     */   {
/*  74 */     g.setFont(this.datasets[whichSet].labelFont);
/*  75 */     g.setColor(this.datasets[whichSet].labelColor);
/*  76 */     for (int i = 0; i < location.length; i++)
/*  77 */       doElementLabel(g, location[i], whichSet, i);
/*     */   }
/*     */ 
/*     */   protected void doMarkers(Graphics g, int whichSet, Point[] location)
/*     */   {
/*  86 */     Gc gc = this.datasets[whichSet].gc;
/*  87 */     if (gc.image != null) {
/*  88 */       int width = gc.image.getWidth(null);
/*  89 */       int height = gc.image.getHeight(null);
/*  90 */       for (int i = 0; i < location.length; i++) {
/*  91 */         gc.drawImage(g, location[i]);
/*  92 */         if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/*  93 */           Point ur = location[i];
/*  94 */           ur.translate(width / 2, height / 2);
/*  95 */           Point ll = new Point(ur.x - width, ur.y - height);
/*  96 */           this.globals.displayList.addRectangle(
/*  97 */             this.datasets[whichSet].getDataElementAt(i), ll, ur);
/*  98 */           this.globals.displayList.addRectangle(
/*  99 */             this.datasets[whichSet], ll, ur);
/* 100 */           this.globals.displayList.addRectangle(
/* 101 */             this, ll, ur);
/*     */         }
/*     */       }
/*     */     }
/* 105 */     else if (gc.markerStyle != -1) {
/* 106 */       gc.drawPolymarker(g, location);
/* 107 */       if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/* 108 */         int width = gc.markerSize;
/* 109 */         int height = gc.markerSize;
/* 110 */         for (int i = 0; i < location.length; i++) {
/* 111 */           Point ur = location[i];
/* 112 */           ur.translate(width / 2, height / 2);
/* 113 */           Point ll = new Point(ur.x - width, ur.y - height);
/* 114 */           this.globals.displayList.addRectangle(
/* 115 */             this.datasets[whichSet].getDataElementAt(i), ll, ur);
/* 116 */           this.globals.displayList.addRectangle(
/* 117 */             this.datasets[whichSet], ll, ur);
/* 118 */           this.globals.displayList.addRectangle(
/* 119 */             this, ll, ur);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doPolar(Graphics g, boolean markersOnly)
/*     */   {
/* 135 */     if (this.axis == null) {
/* 136 */       if ((this.yAxis instanceof PolarAxis))
/* 137 */         this.axis = ((PolarAxis)this.yAxis);
/*     */       else
/* 139 */         return;
/*     */     }
/* 141 */     int length = this.axis.datasetsInUse();
/*     */ 
/* 143 */     this.dataXfm = new Transform(0.0D, this.axis.axisStart, 0.1D, this.axis.axisEnd, 
/* 144 */       this.axis.qtrPlotarea.transform.point(
/* 145 */       this.axis.qtrPlotarea.llX, this.axis.qtrPlotarea.llY), 
/* 146 */       this.axis.qtrPlotarea.transform.point(
/* 147 */       this.axis.qtrPlotarea.urX, this.axis.qtrPlotarea.urY));
/* 148 */     this.dataXfm.logYScaling = this.axis.getLogScaling();
/*     */ 
/* 150 */     for (int i = 0; i < length; i++) {
/* 151 */       double[] arr = this.datasets[i].getYValues();
/*     */ 
/* 153 */       Point[] xfmData = new Point[arr.length];
/* 154 */       for (int j = 0; j < arr.length; j++) {
/* 155 */         xfmData[j] = this.dataXfm.point(0.0D, arr[j]);
/*     */       }
/*     */ 
/* 158 */       Point[] rotatedData = rotateData(xfmData, this.datasets[i]);
/*     */ 
/* 160 */       if (markersOnly) {
/* 161 */         doMarkers(g, i, rotatedData);
/*     */       } else {
/* 163 */         this.datasets[i].gc.drawPolyline(g, rotatedData);
/* 164 */         this.datasets[i].gc.drawLine(g, rotatedData[(arr.length - 1)], 
/* 165 */           rotatedData[0]);
/* 166 */         doMarkers(g, i, rotatedData);
/*     */       }
/* 168 */       if (this.labelsOn)
/* 169 */         doLabels(g, i, rotatedData);
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void draw(Graphics g)
/*     */   {
/* 178 */     if (g == null) {
/* 179 */       return;
/*     */     }
/* 181 */     doPolar(g, this.scatterPlot);
/*     */   }
/*     */ 
/*     */   public boolean isScatterPlot()
/*     */   {
/* 188 */     return this.scatterPlot;
/*     */   }
/*     */ 
/*     */   protected Point[] rotateData(Point[] arr, Dataset d)
/*     */   {
/* 196 */     int length = arr.length;
/* 197 */     double angle = 360.0D / length;
/* 198 */     Point[] data = new Point[length];
/*     */ 
/* 200 */     for (int i = 0; i < length; i++) {
/* 201 */       double oldx = arr[i].x - this.axis.center.x;
/* 202 */       double oldy = arr[i].y - this.axis.center.y;
/*     */ 
/* 204 */       double cosine = Math.cos(3.141592653589793D * i * angle / 180.0D);
/* 205 */       double sine = Math.sin(3.141592653589793D * i * angle / 180.0D);
/*     */ 
/* 207 */       double newx = oldx * cosine - oldy * sine + this.axis.center.x;
/* 208 */       double newy = oldx * sine + oldy * cosine + this.axis.center.y;
/*     */ 
/* 210 */       data[i] = new Point((int)newx, (int)newy);
/*     */     }
/* 212 */     return data;
/*     */   }
/*     */ 
/*     */   public void setScatterPlot(boolean b)
/*     */   {
/* 219 */     this.scatterPlot = b;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Polar
 * JD-Core Version:    0.6.2
 */