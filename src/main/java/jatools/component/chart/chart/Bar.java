/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class Bar extends DataRepresentation
/*     */ {
/*  25 */   protected double clusterWidth = 0.8D;
/*  26 */   protected double baseline = 0.0D;
/*  27 */   protected double observationDelta = 1.0D;
/*     */ 
/*  32 */   protected boolean unitScaling = true;
/*  33 */   boolean doClip = false;
/*     */   double xAxisStart;
/*     */   double xAxisEnd;
/*     */   double yAxisStart;
/*     */   double yAxisEnd;
/*     */   double barPixelWidth;
/*     */   protected Transform dataXfm;
/*  47 */   protected boolean useValueLabels = false;
/*  48 */   protected boolean doErrorBars = false;
/*  49 */   protected Color outlineColor = Gc.TRANSPARENT;
/*     */ 
/*     */   public Bar()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Bar(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*     */   {
/*  66 */     this.datasets = d;
/*  67 */     this.xAxis = xAx;
/*  68 */     this.yAxis = yAx;
/*  69 */     this.plotarea = subplot;
/*  70 */     this.globals = this.plotarea.globals;
/*     */   }
/*     */ 
/*     */   protected void buildDataXfm()
/*     */   {
/*  79 */     this.xAxisStart = this.xAxis.getAxisStart();
/*  80 */     this.xAxisEnd = this.xAxis.getAxisEnd();
/*  81 */     this.yAxisStart = this.yAxis.getAxisStart();
/*  82 */     this.yAxisEnd = this.yAxis.getAxisEnd();
/*     */ 
/*  86 */     this.dataXfm = new Transform(this.xAxisStart, this.yAxisStart, this.xAxisEnd, this.yAxisEnd, this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY), 
/*  87 */       this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY));
/*  88 */     this.dataXfm.logXScaling = this.xAxis.getLogScaling();
/*  89 */     this.dataXfm.logYScaling = this.yAxis.getLogScaling();
/*     */   }
/*     */ 
/*     */   protected int datasetsInUse()
/*     */   {
/*  98 */     for (int i = 0; i < this.datasets.length; i++) {
/*  99 */       if (this.datasets[i] == null) {
/* 100 */         return i;
/*     */       }
/*     */     }
/* 103 */     return i;
/*     */   }
/*     */ 
/*     */   protected void doBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/* 120 */     double offsetLeft = 0.5D * this.observationDelta * this.clusterWidth;
/*     */ 
/* 122 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */     double llx;
/*     */     double llx;
/* 124 */     if (this.unitScaling)
/* 125 */       llx = whichBar - offsetLeft + howWide * whichSet;
/*     */     else {
/* 127 */       llx = dat.x - offsetLeft + howWide * this.observationDelta * whichSet;
/*     */     }
/*     */ 
/* 130 */     double urx = llx + howWide * this.observationDelta;
/*     */     try
/*     */     {
/* 133 */       ury = dat.y;
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException e)
/*     */     {
/*     */       double ury;
/*     */       return;
/*     */     }
/*     */     double ury;
/* 138 */     if (dat.label == "D") {
/* 139 */       return;
/*     */     }
/*     */ 
/* 142 */     double lly = this.baseline;
/*     */ 
/* 144 */     Point p1 = this.dataXfm.point(llx, lly);
/* 145 */     Point p2 = this.dataXfm.point(urx, ury);
/*     */ 
/* 147 */     if (p2.x - p1.x < 1) {
/* 148 */       p2.translate(1, 0);
/*     */     }
/*     */ 
/* 153 */     if ((p2.y - p1.y < 1) && (ury != lly)) {
/* 154 */       p2.translate(0, 1);
/*     */     }
/*     */ 
/* 157 */     if (!individualColors)
/* 158 */       this.datasets[whichSet].gc.fillRect(g, p1, p2);
/*     */     else {
/* 160 */       dat.gc.fillRect(g, p1, p2);
/*     */     }
/*     */ 
/* 165 */     if ((this.doErrorBars) && (dat.y2 != (-1.0D / 0.0D)))
/*     */     {
/* 167 */       Point p3 = this.dataXfm.point(llx + howWide * this.observationDelta / 2.0D, dat.y + dat.y2);
/* 168 */       int width = (p2.x - p1.x) / 4;
/*     */ 
/* 170 */       if (width == 0) {
/* 171 */         width = 1;
/*     */       }
/*     */ 
/* 174 */       this.datasets[whichSet].gc.drawLine(g, p3.x, p2.y, p3.x, p3.y);
/* 175 */       this.datasets[whichSet].gc.drawLine(g, p3.x - width, p3.y, p3.x + width, p3.y);
/* 176 */       p2.translate(0, p3.y - p2.y);
/*     */     }
/*     */ 
/* 179 */     if ((this.useDisplayList) && (this.globals.useDisplayList))
/*     */     {
/* 182 */       this.globals.displayList.addRectangle(dat, p1, p2);
/* 183 */       this.globals.displayList.addRectangle(this.datasets[whichSet], p1, p2);
/*     */ 
/* 189 */       this.globals.displayList.addRectangle(this, p1, p2);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doBarLabel(Graphics g, double howWide, int whichSet, int whichBar)
/*     */   {
/* 222 */     double offsetLeft = 0.5D * this.observationDelta * this.clusterWidth;
/*     */ 
/* 227 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */     double llx;
/*     */     double llx;
/* 229 */     if (this.unitScaling)
/* 230 */       llx = whichBar - offsetLeft + howWide * whichSet;
/*     */     else {
/* 232 */       llx = dat.x - offsetLeft + howWide * this.observationDelta * whichSet;
/*     */     }
/*     */ 
/* 235 */     double urx = llx + howWide * this.observationDelta;
/* 236 */     double ury = dat.y;
/*     */ 
/* 238 */     if ((this.doErrorBars) && (dat.y2 != (-1.0D / 0.0D)))
/* 239 */       ury += dat.y2;
/*     */     String str;
/*     */     String str;
/* 243 */     if ((dat.label != null) && (!this.useValueLabels)) {
/* 244 */       str = dat.label;
/*     */     }
/*     */     else
/*     */     {
/* 248 */       str = formatLabel(dat.y);
/*     */     }
/*     */ 
/* 251 */     g.setFont(this.datasets[whichSet].labelFont);
/* 252 */     g.setColor(this.datasets[whichSet].labelColor);
/* 253 */     FontMetrics fm = g.getFontMetrics();
/* 254 */     Point loc = this.dataXfm.point(llx + (urx - llx) / 2.0D, ury);
/*     */ 
/* 256 */     if (dat.y < this.baseline)
/* 257 */       this.datasets[whichSet].gc.drawSmartString(g, loc.x, loc.y - 2, 0, this.labelAngle, fm, str);
/*     */     else
/* 259 */       this.datasets[whichSet].gc.drawSmartString(g, loc.x, loc.y + 2, 2, this.labelAngle, fm, str);
/*     */   }
/*     */ 
/*     */   protected void doBars(Graphics g, boolean individualColors)
/*     */   {
/* 272 */     int maxNumDataPoints = 0;
/*     */ 
/* 276 */     int numDatasets = datasetsInUse();
/*     */ 
/* 278 */     for (int i = 0; i < numDatasets; i++) {
/* 279 */       if (maxNumDataPoints < this.datasets[i].data.size()) {
/* 280 */         maxNumDataPoints = this.datasets[i].data.size();
/*     */       }
/*     */     }
/*     */ 
/* 284 */     double individualBarWidth = this.clusterWidth / numDatasets;
/*     */ 
/* 286 */     if (this.unitScaling) {
/* 287 */       this.observationDelta = 1.0D;
/*     */     }
/* 289 */     else if (this.datasets[0] != null) {
/*     */       try {
/* 291 */         this.observationDelta = (this.datasets[0].getDataElementAt(1).x - this.datasets[0].getDataElementAt(0).x);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */         try {
/* 296 */           this.observationDelta = ((this.datasets[0].getDataElementAt(0).x - this.xAxisStart) / 4.0D);
/*     */         }
/*     */         catch (Exception e1) {
/* 299 */           this.observationDelta = 1.0D;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 306 */     if (!this.globals.threeD) {
/* 307 */       for (i = 0; i < numDatasets; i++) {
/* 308 */         for (int j = 0; j < this.datasets[i].data.size(); j++)
/* 309 */           doBar(g, individualBarWidth, i, j, individualColors);
/*     */       }
/*     */     }
/*     */     else {
/* 313 */       for (int j = 0; j < maxNumDataPoints; j++) {
/* 314 */         for (i = 0; i < numDatasets; i++) {
/* 315 */           if (j < this.datasets[i].data.size()) {
/* 316 */             doDBar(g, individualBarWidth, i, j, individualColors);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 322 */     if (this.labelsOn)
/* 323 */       for (i = 0; i < numDatasets; i++)
/* 324 */         for (int j = 0; j < this.datasets[i].data.size(); j++)
/* 325 */           doBarLabel(g, individualBarWidth, i, j);
/*     */   }
/*     */ 
/*     */   protected void doDBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/* 343 */     double offsetLeft = 0.5D * this.observationDelta * this.clusterWidth;
/*     */ 
/* 348 */     Point[] face = new Point[4];
/*     */ 
/* 350 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */     double llx;
/*     */     double llx;
/* 352 */     if (this.unitScaling)
/* 353 */       llx = whichBar - offsetLeft + howWide * whichSet;
/*     */     else {
/* 355 */       llx = dat.x - offsetLeft + howWide * this.observationDelta * whichSet;
/*     */     }
/*     */ 
/* 358 */     double urx = llx + howWide * this.observationDelta;
/*     */     try
/*     */     {
/* 361 */       ury = dat.y;
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*     */       double ury;
/*     */       return;
/*     */     }
/*     */     double ury;
/* 366 */     double lly = this.baseline;
/* 367 */     face[0] = this.dataXfm.point(llx, lly);
/* 368 */     face[1] = this.dataXfm.point(urx, ury);
/*     */     Gc myGc;
/*     */     try
/*     */     {
/*     */       Gc myGc;
/* 371 */       if (!individualColors)
/* 372 */         myGc = this.datasets[whichSet].gc;
/*     */       else
/* 374 */         myGc = dat.gc;
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException e)
/*     */     {
/*     */       Gc myGc;
/* 377 */       myGc = this.datasets[whichSet].gc;
/*     */     }
/*     */ 
/* 380 */     myGc.fillRect(g, face[0], face[1]);
/*     */ 
/* 382 */     if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/* 383 */       if (individualColors) {
/* 384 */         this.globals.displayList.addRectangle(dat, face[0], face[1]);
/* 385 */         this.globals.displayList.addRectangle(this.datasets[whichSet], face[0], face[1]);
/*     */       } else {
/* 387 */         this.globals.displayList.addRectangle(this.datasets[whichSet], face[0], face[1]);
/* 388 */         this.globals.displayList.addRectangle(dat, face[0], face[1]);
/*     */       }
/*     */ 
/* 391 */       this.globals.displayList.addRectangle(this, this.dataXfm.point(llx, lly), this.dataXfm.point(urx, ury));
/*     */     }
/*     */ 
/* 394 */     Color saveColor = myGc.fillColor;
/* 395 */     myGc.fillColor = saveColor.darker();
/*     */ 
/* 397 */     Color saveSecondaryColor = myGc.getSecondaryFillColor();
/* 398 */     myGc.setSecondaryFillColor(saveSecondaryColor.darker());
/*     */ 
/* 401 */     face[0] = this.dataXfm.point(urx, lly);
/*     */ 
/* 404 */     face[1] = this.dataXfm.point(urx, ury);
/* 405 */     face[2] = new Point(face[1].x + this.globals.xOffset, face[1].y + this.globals.yOffset);
/* 406 */     face[3] = new Point(face[0].x + this.globals.xOffset, face[0].y + this.globals.yOffset);
/* 407 */     myGc.drawPolygon(g, face);
/*     */ 
/* 410 */     int style = myGc.fillStyle;
/*     */ 
/* 412 */     if (style == 0) {
/* 413 */       myGc.fillStyle = -1;
/*     */     }
/*     */ 
/* 416 */     if (ury > lly) {
/* 417 */       face[0] = this.dataXfm.point(llx, ury);
/*     */ 
/* 420 */       face[3].x = (face[0].x + this.globals.xOffset);
/* 421 */       face[3].y = (face[0].y + this.globals.yOffset);
/* 422 */       myGc.drawPolygon(g, face);
/*     */     } else {
/* 424 */       face[0] = this.dataXfm.point(llx, lly);
/* 425 */       face[1] = this.dataXfm.point(urx, lly);
/*     */ 
/* 428 */       face[2].x = (face[1].x + this.globals.xOffset);
/* 429 */       face[2].y = (face[1].y + this.globals.yOffset);
/* 430 */       face[3].x = (face[0].x + this.globals.xOffset);
/* 431 */       face[3].y = (face[0].y + this.globals.yOffset);
/* 432 */       myGc.drawPolygon(g, face);
/*     */     }
/*     */ 
/* 435 */     myGc.fillStyle = style;
/* 436 */     myGc.fillColor = saveColor;
/* 437 */     myGc.setSecondaryFillColor(saveSecondaryColor);
/*     */   }
/*     */ 
/*     */   private void doDraw(Graphics g, boolean ind)
/*     */   {
/* 450 */     Graphics saveG = g;
/*     */ 
/* 452 */     if (g == null) {
/* 453 */       return;
/*     */     }
/*     */ 
/* 456 */     buildDataXfm();
/* 457 */     double saveBase = this.baseline;
/*     */ 
/* 459 */     if ((this.yAxis.getLogScaling()) && 
/* 460 */       (this.baseline <= 0.0D)) {
/* 461 */       this.baseline = this.yAxis.getAxisStart();
/*     */     }
/*     */ 
/* 466 */     if (this.doClip) {
/* 467 */       g = g.create();
/* 468 */       Point clipLL = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY);
/* 469 */       Point clipUR = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY);
/* 470 */       g.clipRect(clipLL.x, this.globals.maxY - clipUR.y, clipUR.x - clipLL.x, clipUR.y - clipLL.y);
/*     */     }
/*     */ 
/* 473 */     doBars(g, ind);
/* 474 */     this.baseline = saveBase;
/* 475 */     g = saveG;
/*     */   }
/*     */ 
/*     */   public synchronized void draw(Graphics g)
/*     */   {
/* 485 */     doDraw(g, false);
/*     */   }
/*     */ 
/*     */   public synchronized void drawInd(Graphics g)
/*     */   {
/* 496 */     doDraw(g, true);
/*     */   }
/*     */ 
/*     */   protected synchronized void drawSet(Graphics g, int setNumber, boolean individualColors)
/*     */   {
/*     */     int j;
/* 510 */     if (!this.globals.threeD) {
/* 511 */       for (int j = 0; j < this.datasets[setNumber].data.size(); j++)
/* 512 */         doBar(g, this.clusterWidth, setNumber, j, individualColors);
/*     */     }
/*     */     else {
/* 515 */       for (j = 0; j < this.datasets[setNumber].data.size(); j++) {
/* 516 */         doDBar(g, this.clusterWidth, setNumber, j, individualColors);
/*     */       }
/*     */     }
/*     */ 
/* 520 */     if (this.labelsOn)
/* 521 */       for (j = 0; j < this.datasets[setNumber].data.size(); j++)
/* 522 */         doBarLabel(g, this.clusterWidth, setNumber, j);
/*     */   }
/*     */ 
/*     */   public double getBaseline()
/*     */   {
/* 533 */     return this.baseline;
/*     */   }
/*     */ 
/*     */   public double getClusterWidth()
/*     */   {
/* 542 */     return this.clusterWidth;
/*     */   }
/*     */ 
/*     */   public boolean getDoClip()
/*     */   {
/* 551 */     return this.doClip;
/*     */   }
/*     */ 
/*     */   public boolean getDoErrorBars()
/*     */   {
/* 560 */     return this.doErrorBars;
/*     */   }
/*     */ 
/*     */   public boolean getUseValueLabels()
/*     */   {
/* 569 */     return this.useValueLabels;
/*     */   }
/*     */ 
/*     */   public boolean getUnitScaling()
/*     */   {
/* 577 */     return this.unitScaling;
/*     */   }
/*     */ 
/*     */   public void setBaseline(double num)
/*     */   {
/* 586 */     this.baseline = num;
/*     */   }
/*     */ 
/*     */   public void setClusterWidth(double num)
/*     */   {
/* 596 */     this.clusterWidth = num;
/*     */   }
/*     */ 
/*     */   public void setDoClip(boolean onOff)
/*     */   {
/* 605 */     this.doClip = onOff;
/*     */   }
/*     */ 
/*     */   public void setDoErrorBars(boolean onOff)
/*     */   {
/* 614 */     this.doErrorBars = onOff;
/*     */   }
/*     */ 
/*     */   public void setUnitScaling(boolean trueFalse)
/*     */   {
/* 623 */     this.unitScaling = trueFalse;
/*     */   }
/*     */ 
/*     */   public void setUseValueLabels(boolean yesNo)
/*     */   {
/* 632 */     this.useValueLabels = yesNo;
/*     */   }
/*     */ 
/*     */   public Color getOutlineColor()
/*     */   {
/* 642 */     return this.outlineColor;
/*     */   }
/*     */ 
/*     */   public void setOutlineColor(Color outlineColor)
/*     */   {
/* 649 */     this.outlineColor = outlineColor;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Bar
 * JD-Core Version:    0.6.2
 */