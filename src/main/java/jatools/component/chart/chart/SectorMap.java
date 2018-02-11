/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class SectorMap extends DataRepresentation
/*     */ {
/*     */   int numDatasets;
/*     */   ArrayList sectors;
/*     */   double datasetTotal;
/*  27 */   double[] dataTotals = new double[_Chart.MAX_DATASETS];
/*     */ 
/*  29 */   Color baseColor = Color.black;
/*  30 */   double baseValue = (-1.0D / 0.0D);
/*  31 */   Color secondaryColor = null;
/*     */ 
/*  33 */   boolean useGradientColoring = false;
/*     */   protected static final double MAX_PERCENT = 1.0D;
/*     */   protected static final double THRESHOLD = 0.5D;
/*     */   public static final int LABEL_Y_VALUE = 0;
/*     */   public static final int LABEL_X_VALUE = 1;
/*     */   public static final int LABEL_STRING = 2;
/*  42 */   int labelStyle = 2;
/*     */ 
/*     */   public SectorMap()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SectorMap(Dataset[] d, Plotarea p, Globals g)
/*     */   {
/*  61 */     this.datasets = d;
/*  62 */     this.plotarea = p;
/*  63 */     this.globals = g;
/*     */   }
/*     */ 
/*     */   protected int datasetsInUse()
/*     */   {
/*  73 */     for (int i = 0; i < this.datasets.length; i++)
/*  74 */       if (this.datasets[i] == null)
/*  75 */         return i;
/*  76 */     return i;
/*     */   }
/*     */ 
/*     */   protected void defineDatasetSectors(int x, int y, int width, int height, double percent, boolean horizontal, int start0, int end0)
/*     */   {
/* 102 */     if (start0 == end0) {
/* 103 */       Sector tsect = getDatasetSector(start0);
/* 104 */       tsect.xLoc = x;
/* 105 */       tsect.yLoc = y;
/* 106 */       tsect.width = width;
/* 107 */       tsect.height = height;
/* 108 */       return;
/*     */     }
/*     */ 
/* 111 */     if (end0 - start0 == 1)
/*     */     {
/* 113 */       if ((width / height > 1) && (!horizontal))
/* 114 */         horizontal = true;
/* 115 */       if ((height / width > 1) && (horizontal)) {
/* 116 */         horizontal = false;
/*     */       }
/*     */     }
/* 119 */     int part = start0;
/*     */ 
/* 121 */     int start = start0;
/* 122 */     int i = 0;
/* 123 */     double tempPercent = 0.0D;
/*     */     do
/*     */     {
/* 130 */       part++;
/* 131 */       Sector tsect = getDatasetSector(start);
/* 132 */       tempPercent += tsect.percentage;
/* 133 */       start++;
/* 134 */     }while ((part != end0) && 
/* 136 */       (tempPercent / percent <= 0.5D));
/*     */ 
/* 138 */     int xVal = x + (int)(width * (tempPercent / percent));
/* 139 */     int yVal = y + (int)(height * (tempPercent / percent));
/*     */ 
/* 142 */     if (horizontal) {
/* 143 */       defineDatasetSectors(x, y, xVal - x, height, tempPercent, 
/* 144 */         false, start0, part - 1);
/* 145 */       defineDatasetSectors(xVal, y, width - (xVal - x), height, 
/* 146 */         percent - tempPercent, false, part, end0);
/*     */     }
/*     */     else {
/* 149 */       defineDatasetSectors(x, y, width, yVal - y, tempPercent, 
/* 150 */         true, start0, part - 1);
/* 151 */       defineDatasetSectors(x, yVal, width, height - (yVal - y), 
/* 152 */         percent - tempPercent, true, part, end0);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void defineDatumSectors(int x, int y, int width, int height, double percent, boolean horizontal, int start0, int end0, int which)
/*     */   {
/* 180 */     if (start0 == end0) {
/* 181 */       Sector tsect = (Sector)((ArrayList)this.sectors.get(which)).get(start0);
/* 182 */       tsect.xLoc = x;
/* 183 */       tsect.yLoc = y;
/* 184 */       tsect.width = width;
/* 185 */       tsect.height = height;
/* 186 */       return;
/*     */     }
/*     */ 
/* 189 */     if (end0 - start0 == 1)
/*     */     {
/* 191 */       if ((width / height > 1) && (!horizontal))
/* 192 */         horizontal = true;
/* 193 */       if ((height / width > 1) && (horizontal)) {
/* 194 */         horizontal = false;
/*     */       }
/*     */     }
/* 197 */     int part = start0;
/*     */ 
/* 199 */     int start = start0;
/* 200 */     int i = 0;
/* 201 */     double tempPercent = 0.0D;
/*     */     do
/*     */     {
/* 208 */       part++;
/* 209 */       Sector tsect = (Sector)((ArrayList)this.sectors.get(which)).get(start);
/* 210 */       tempPercent += tsect.percentage;
/* 211 */       start++;
/* 212 */     }while ((part != end0) && 
/* 214 */       (tempPercent / percent <= 0.5D));
/*     */ 
/* 216 */     int xVal = x + (int)(width * (tempPercent / percent));
/* 217 */     int yVal = y + (int)(height * (tempPercent / percent));
/*     */ 
/* 219 */     if (horizontal) {
/* 220 */       defineDatumSectors(x, y, xVal - x, height, tempPercent, 
/* 221 */         false, start0, part - 1, which);
/* 222 */       defineDatumSectors(xVal, y, width - (xVal - x), height, 
/* 223 */         percent - tempPercent, false, part, 
/* 224 */         end0, which);
/*     */     }
/*     */     else {
/* 227 */       defineDatumSectors(x, y, width, yVal - y, tempPercent, 
/* 228 */         true, start0, part - 1, which);
/* 229 */       defineDatumSectors(x, yVal, width, height - (yVal - y), 
/* 230 */         percent - tempPercent, true, part, 
/* 231 */         end0, which);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void defineSectors()
/*     */   {
/* 240 */     int xLoc = (int)(this.plotarea.llX * this.globals.maxX);
/* 241 */     int yLoc = (int)(this.plotarea.llY * this.globals.maxY);
/* 242 */     int width = (int)((this.plotarea.urX - this.plotarea.llX) * this.globals.maxX);
/* 243 */     int height = (int)((this.plotarea.urY - this.plotarea.llY) * this.globals.maxY);
/*     */ 
/* 248 */     defineDatasetSectors(xLoc, yLoc, width, height, 1.0D, 
/* 249 */       false, 0, this.sectors.size() - 1);
/*     */ 
/* 252 */     for (int i = 0; i < this.numDatasets; i++) {
/* 253 */       Sector sector = getDatasetSector(i);
/* 254 */       xLoc = sector.xLoc;
/* 255 */       yLoc = sector.yLoc;
/* 256 */       width = sector.width;
/* 257 */       height = sector.height;
/* 258 */       defineDatumSectors(xLoc, yLoc, width, height, 1.0D, false, 
/* 259 */         1, ((ArrayList)this.sectors.get(i)).size() - 1, i);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Color determineGradientColor(Color startColor, Color endColor, double lowVal, double highVal, double val)
/*     */   {
/* 280 */     float r1 = startColor.getRed() / 255.0F;
/* 281 */     float g1 = startColor.getGreen() / 255.0F;
/* 282 */     float b1 = startColor.getBlue() / 255.0F;
/*     */ 
/* 284 */     float r2 = endColor.getRed() / 255.0F;
/* 285 */     float g2 = endColor.getGreen() / 255.0F;
/* 286 */     float b2 = endColor.getBlue() / 255.0F;
/*     */ 
/* 288 */     float position = (float)((val - lowVal) / (highVal - lowVal));
/*     */ 
/* 290 */     float red = (r2 - r1) * position + r1;
/* 291 */     float green = (g2 - g1) * position + g1;
/* 292 */     float blue = (b2 - b1) * position + b1;
/*     */ 
/* 294 */     return new Color(red, green, blue);
/*     */   }
/*     */ 
/*     */   protected void doDatasetSector(Graphics g, int which, boolean individualColors)
/*     */   {
/* 309 */     Gc gc = this.datasets[which].getGc();
/* 310 */     if (!gc.outlineFills)
/* 311 */       return;
/* 312 */     Sector dataset = getDatasetSector(which);
/* 313 */     Point[] points = new Point[5];
/* 314 */     points[0] = new Point(dataset.xLoc, dataset.yLoc);
/* 315 */     points[1] = new Point(dataset.xLoc, dataset.yLoc + dataset.height);
/* 316 */     points[2] = new Point(dataset.xLoc + dataset.width, 
/* 317 */       dataset.yLoc + dataset.height);
/* 318 */     points[3] = new Point(dataset.xLoc + dataset.width, dataset.yLoc);
/* 319 */     points[4] = points[0];
/* 320 */     gc.drawPolyline(g, points);
/*     */   }
/*     */ 
/*     */   protected void doDatumSector(Graphics g, int whichSet, int whichSector, boolean individualColors, double minX, double maxX)
/*     */   {
/*     */     Gc gc;
/*     */     Gc gc;
/* 342 */     if (individualColors)
/* 343 */       gc = this.datasets[whichSet].getDataElementAt(whichSector).gc;
/*     */     else
/* 345 */       gc = this.datasets[whichSet].gc;
/* 346 */     Sector datum = getDatumSector(whichSet, whichSector);
/* 347 */     Point ll = new Point(datum.xLoc, datum.yLoc);
/* 348 */     Point ur = new Point(datum.xLoc + datum.width, 
/* 349 */       datum.yLoc + datum.height);
/* 350 */     if (this.useGradientColoring)
/*     */     {
/* 352 */       double datumValue = this.datasets[whichSet].getDataElementAt(
/* 353 */         whichSector).x;
/* 354 */       Color high = gc.fillColor;
/*     */ 
/* 356 */       if (datumValue >= this.baseValue)
/* 357 */         gc.fillColor = determineGradientColor(this.baseColor, high, 
/* 358 */           this.baseValue, maxX, 
/* 359 */           datumValue);
/*     */       else
/* 361 */         gc.fillColor = determineGradientColor(this.secondaryColor, 
/* 362 */           this.baseColor, 
/* 363 */           minX, this.baseValue, 
/* 364 */           datumValue);
/* 365 */       gc.fillRect(g, ll, ur);
/* 366 */       gc.fillColor = high;
/*     */     }
/*     */     else {
/* 369 */       gc.fillRect(g, ll, ur);
/*     */     }
/* 371 */     if ((this.useDisplayList) && (this.globals.useDisplayList))
/*     */     {
/* 373 */       if (individualColors) {
/* 374 */         this.globals.displayList.addRectangle(this.datasets[whichSet]
/* 375 */           .getDataElementAt(whichSector), 
/* 376 */           ll, ur);
/* 377 */         this.globals.displayList.addRectangle(this.datasets[whichSet], ll, ur);
/*     */       }
/*     */       else {
/* 380 */         this.globals.displayList.addRectangle(this.datasets[whichSet], ll, ur);
/* 381 */         this.globals.displayList.addRectangle(this.datasets[whichSet]
/* 382 */           .getDataElementAt(whichSector), 
/* 383 */           ll, ur);
/*     */       }
/* 385 */       this.globals.displayList.addRectangle(this, ll, ur);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doDraw(Graphics g, boolean individualColors)
/*     */   {
/* 398 */     double minX = (-1.0D / 0.0D);
/* 399 */     double maxX = (-1.0D / 0.0D);
/*     */ 
/* 403 */     if (!individualColors)
/*     */     {
/* 405 */       for (int i = 0; i < this.numDatasets; i++) {
/* 406 */         if (!this.datasets[i].gc.outlineFills) {
/* 407 */           this.datasets[i].gc.outlineFills = true;
/* 408 */           this.datasets[i].gc.lineColor = Color.black;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 415 */     for (int i = 0; i < this.numDatasets; i++) {
/* 416 */       if (this.useGradientColoring) {
/* 417 */         maxX = this.datasets[i].maxX();
/* 418 */         minX = this.datasets[i].minX();
/*     */ 
/* 420 */         if (this.secondaryColor == null)
/*     */         {
/* 422 */           this.secondaryColor = this.datasets[i].gc.getSecondaryFillColor();
/* 423 */           if (this.secondaryColor == null)
/*     */           {
/* 425 */             this.baseValue = minX;
/*     */           }
/*     */         }
/*     */       }
/* 429 */       if (this.baseValue == (-1.0D / 0.0D)) {
/* 430 */         this.baseValue = minX;
/*     */ 
/* 432 */         this.baseColor = this.secondaryColor;
/*     */       }
/* 434 */       for (int j = 0; j < this.datasets[i].data.size(); j++) {
/* 435 */         doDatumSector(g, i, j, individualColors, minX, maxX);
/*     */       }
/* 437 */       doDatasetSector(g, i, individualColors);
/*     */     }
/*     */ 
/* 442 */     if (this.labelsOn)
/* 443 */       for (i = 0; i < this.numDatasets; i++)
/* 444 */         for (int j = 0; j < this.datasets[i].data.size(); j++)
/* 445 */           doSectorLabel(g, i, j);
/*     */   }
/*     */ 
/*     */   protected void doSectorLabel(Graphics g, int whichSet, int whichSector)
/*     */   {
/* 467 */     Datum datum = this.datasets[whichSet].getDataElementAt(whichSector);
/* 468 */     String str = datum.getLabel();
/* 469 */     Sector sector = getDatumSector(whichSet, whichSector);
/*     */ 
/* 471 */     if (this.labelsOn) {
/* 472 */       if (this.labelStyle == 2) {
/* 473 */         if (str == null)
/* 474 */           str = formatLabel(datum.y);
/*     */       }
/* 476 */       else if (this.labelStyle == 0) {
/* 477 */         str = formatLabel(datum.y);
/*     */       }
/* 479 */       else if (this.labelStyle == 1) {
/* 480 */         str = formatLabel(datum.x);
/*     */       }
/* 482 */       if (str == null) {
/* 483 */         return;
/*     */       }
/*     */     }
/* 486 */     g.setFont(this.datasets[whichSet].labelFont);
/* 487 */     g.setColor(this.datasets[whichSet].labelColor);
/* 488 */     FontMetrics fm = g.getFontMetrics();
/*     */ 
/* 490 */     Point loc = new Point(sector.xLoc + sector.width / 2, 
/* 491 */       sector.yLoc + sector.height / 2);
/* 492 */     this.datasets[whichSet].getGc().drawString(g, loc.x - 
/* 493 */       fm.stringWidth(str) / 2, 
/* 494 */       loc.y - fm.getMaxAscent() / 2, 
/* 495 */       str);
/*     */   }
/*     */ 
/*     */   protected void doSectorMap(Graphics g, boolean individualColors)
/*     */   {
/* 513 */     this.numDatasets = datasetsInUse();
/* 514 */     this.sectors = new ArrayList(this.numDatasets);
/*     */ 
/* 516 */     initSectors();
/*     */ 
/* 519 */     for (int i = 0; i < this.numDatasets; i++) {
/* 520 */       sortVector((ArrayList)this.sectors.get(i));
/*     */     }
/* 522 */     defineSectors();
/*     */ 
/* 524 */     doDraw(g, individualColors);
/*     */   }
/*     */ 
/*     */   public void draw(Graphics g)
/*     */   {
/* 534 */     if (g == null)
/* 535 */       return;
/* 536 */     doSectorMap(g, false);
/*     */   }
/*     */ 
/*     */   public void drawInd(Graphics g)
/*     */   {
/* 546 */     if (g == null)
/* 547 */       return;
/* 548 */     doSectorMap(g, true);
/*     */   }
/*     */ 
/*     */   public Color getBaseColor()
/*     */   {
/* 557 */     return this.baseColor;
/*     */   }
/*     */ 
/*     */   public double getBaseValue()
/*     */   {
/* 566 */     return this.baseValue;
/*     */   }
/*     */ 
/*     */   protected Sector getDatasetSector(int which)
/*     */   {
/* 576 */     return (Sector)((ArrayList)this.sectors.get(which)).get(0);
/*     */   }
/*     */ 
/*     */   protected Sector getDatumSector(int whichSet, int whichDatum)
/*     */   {
/* 587 */     Sector sector = null;
/* 588 */     boolean found = false;
/* 589 */     ArrayList dataset = (ArrayList)this.sectors.get(whichSet);
/* 590 */     int i = 1;
/* 591 */     while ((i < dataset.size()) && (!found)) {
/* 592 */       sector = (Sector)dataset.get(i);
/* 593 */       if (sector.datumNumber == whichDatum)
/* 594 */         found = true;
/* 595 */       i++;
/*     */     }
/*     */ 
/* 598 */     if (!found)
/* 599 */       sector = null;
/* 600 */     return sector;
/*     */   }
/*     */ 
/*     */   public Color getSecondaryColor()
/*     */   {
/* 609 */     return this.secondaryColor;
/*     */   }
/*     */ 
/*     */   public boolean getUseGradientColoring()
/*     */   {
/* 618 */     return this.useGradientColoring;
/*     */   }
/*     */ 
/*     */   public int getLabelStyle()
/*     */   {
/* 627 */     return this.labelStyle;
/*     */   }
/*     */ 
/*     */   protected void initSectors()
/*     */   {
/* 636 */     int total = 0;
/*     */ 
/* 638 */     this.datasetTotal = 0.0D;
/*     */ 
/* 641 */     for (int i = 0; i < this.numDatasets; i++) {
/* 642 */       this.sectors.add(new ArrayList(this.datasets[i].getData().size() + 1));
/*     */     }
/* 644 */     for (i = 0; i < this.numDatasets; i++) {
/* 645 */       populateSectors(i);
/*     */     }
/* 647 */     for (i = 0; i < this.numDatasets; i++) {
/* 648 */       Sector temp = getDatasetSector(i);
/* 649 */       temp.percentage = (this.dataTotals[i] / this.datasetTotal);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void populateSectors(int whichSet)
/*     */   {
/* 667 */     int length = this.datasets[whichSet].getData().size();
/* 668 */     double total = 0.0D;
/* 669 */     ArrayList thisDataset = (ArrayList)this.sectors.get(whichSet);
/*     */ 
/* 672 */     for (int i = 0; i < length; i++) {
/* 673 */       total += this.datasets[whichSet].getDataElementAt(i).y;
/*     */     }
/* 675 */     this.dataTotals[whichSet] = total;
/* 676 */     this.datasetTotal += total;
/*     */ 
/* 679 */     thisDataset.add(new Sector(whichSet, -1, 1.0D, true));
/*     */ 
/* 682 */     for (i = 0; i < length; i++) {
/* 683 */       double percent = this.datasets[whichSet].getDataElementAt(i).y / total;
/* 684 */       thisDataset.add(new Sector(whichSet, i, percent, false));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setBaseColor(Color base)
/*     */   {
/* 694 */     this.baseColor = base;
/*     */   }
/*     */ 
/*     */   public void setBaseValue(double base)
/*     */   {
/* 703 */     this.baseValue = base;
/*     */   }
/*     */ 
/*     */   public void setSecondaryColor(Color c)
/*     */   {
/* 712 */     this.secondaryColor = c;
/*     */   }
/*     */ 
/*     */   public void setUseGradientColoring(boolean tf)
/*     */   {
/* 721 */     this.useGradientColoring = tf;
/*     */   }
/*     */ 
/*     */   public void setLabelStyle(int style)
/*     */   {
/* 730 */     this.labelStyle = style;
/*     */   }
/*     */ 
/*     */   private void sort(Sector[] x, int lo0, int hi0)
/*     */   {
/* 735 */     int lo = lo0;
/* 736 */     int hi = hi0;
/*     */ 
/* 739 */     if (lo == hi - 1) {
/* 740 */       if (Sector.compare(x[hi], x[lo])) {
/* 741 */         swap(x, lo, hi);
/*     */       }
/* 743 */       return;
/*     */     }
/*     */ 
/* 746 */     if (hi0 > lo0) {
/* 747 */       Sector mid = x[((lo0 + hi0) / 2)];
        /*
/* 748 */       //for (goto 123; lo <= hi; ) {
/*     */       //  do if (lo >= hi0) break; while (Sector.compare(x[lo], mid));
/*     */ 
/* 751 */         while ((hi > lo0) && (Sector.compare(mid, x[hi])))
/* 752 */           hi--;
/* 753 */         if (lo <= hi) {
/* 754 */           swap(x, lo, hi);
/* 755 */           lo++;
/* 756 */           hi--;
/*     */         }
/*     */       }
/* 759 */       if (lo0 < hi)
/* 760 */         sort(x, lo0, lo - 1);
/* 761 */       sort(x, hi + 1, hi0);
/*     */     }

/*     */
/*     */ 
/*     */   private void sortVector(ArrayList v)
/*     */   {
/* 770 */     Sector[] sectArr = new Sector[v.size() - 1];
/* 771 */     for (int i = 0; i < sectArr.length; i++) {
/* 772 */       sectArr[i] = ((Sector)v.get(i + 1));
/*     */     }
/*     */ 
/* 775 */     sort(sectArr, 0, sectArr.length - 1);
/*     */ 
/* 777 */     for (i = 0; i < sectArr.length; i++)
/* 778 */       v.set(i + 1, sectArr[i]);
/*     */   }
/*     */ 
/*     */   private void swap(Sector[] x, int i, int j)
/*     */   {
/* 783 */     if (i == j) {
/* 784 */       return;
/*     */     }
/*     */ 
/* 787 */     Sector T = x[i];
/* 788 */     x[i] = x[j];
/* 789 */     x[j] = T;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.SectorMap
 * JD-Core Version:    0.6.2
 */