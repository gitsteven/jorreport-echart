/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class DiscontinuousLine extends Line
/*     */ {
/*  20 */   boolean hasDiscontinuities = false;
/*     */ 
/*     */   public DiscontinuousLine()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DiscontinuousLine(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*     */   {
/*  38 */     super(d, xAx, yAx, subplot);
/*     */   }
/*     */ 
/*     */   private void doDiscontinuousLine(Graphics g, int whichSet)
/*     */   {
/*  48 */     double[] startingXarr = this.datasets[whichSet].getXValues();
/*  49 */     double[] startingYarr = this.datasets[whichSet].getYValues();
/*  50 */     ArrayList partialDataList = new ArrayList();
/*  51 */     int i = 0;
/*  52 */     while (i < startingXarr.length) {
/*  53 */       while ((i < startingXarr.length) && (this.datasets[whichSet].getDataElementAt(i).label != "D")) {
/*  54 */         partialDataList.add(this.datasets[whichSet].getDataElementAt(i));
/*  55 */         i++;
/*     */       }
/*  57 */       double[] xarr = new double[partialDataList.size()];
/*  58 */       double[] yarr = new double[partialDataList.size()];
/*  59 */       for (int j = 0; j < xarr.length; j++) {
/*  60 */         Datum myDat = (Datum)partialDataList.get(j);
/*  61 */         xarr[j] = myDat.x;
/*  62 */         yarr[j] = myDat.y;
/*     */       }
/*  64 */       if (!this.scatterPlot)
/*  65 */         doLineSegment(xarr, yarr, whichSet, g);
/*  66 */       doMarkerSegment(xarr, yarr, whichSet, partialDataList, g);
/*  67 */       partialDataList.clear();
/*  68 */       i++;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doElementLabel(Graphics g, int whichSet, int whichElement)
/*     */   {
/*  80 */     Datum element = this.datasets[whichSet].getDataElementAt(whichElement);
/*  81 */     if (element.label == "D")
/*  82 */       return;
/*  83 */     super.doElementLabel(g, whichSet, whichElement);
/*     */   }
/*     */ 
/*     */   protected void doLine(Graphics g, int whichSet)
/*     */   {
/*  95 */     this.hasDiscontinuities = false;
/*  96 */     for (int i = 0; i < this.datasets[whichSet].getData().size(); i++) {
/*  97 */       if (this.datasets[whichSet].getDataElementAt(i).label == "D")
/*  98 */         this.hasDiscontinuities = true;
/*     */     }
/* 100 */     if ((!this.hasDiscontinuities) && (!this.scatterPlot))
/* 101 */       super.doLine(g, whichSet);
/*     */     else
/* 103 */       doDiscontinuousLine(g, whichSet);
/*     */   }
/*     */ 
/*     */   private void doLineSegment(double[] xarr, double[] yarr, int whichSet, Graphics g)
/*     */   {
/* 115 */     if (!this.globals.threeD) {
/* 116 */       this.datasets[whichSet].gc.drawPolyline(
/* 117 */         g, 
/* 118 */         this.dataXfm.pointList(xarr, yarr));
/*     */     } else {
/* 120 */       int xWidth = this.globals.xOffset / this.numDatasets;
/* 121 */       int yWidth = this.globals.yOffset / this.numDatasets;
/* 122 */       int startXOffset = xWidth * whichSet;
/* 123 */       int startYOffset = yWidth * whichSet;
/* 124 */       Point[] pts = new Point[4];
/* 125 */       for (int i = 0; i < xarr.length - 1; i++) {
/* 126 */         pts[0] = this.dataXfm.point(xarr[i], yarr[i]);
/* 127 */         pts[0].translate(startXOffset, startYOffset);
/* 128 */         pts[1] = new Point(pts[0].x + xWidth, 
/* 129 */           pts[0].y + yWidth);
/* 130 */         pts[3] = this.dataXfm.point(xarr[(i + 1)], yarr[(i + 1)]);
/* 131 */         pts[3].translate(startXOffset, startYOffset);
/* 132 */         pts[2] = new Point(pts[3].x + xWidth, 
/* 133 */           pts[3].y + yWidth);
/* 134 */         this.datasets[whichSet].gc.drawPolygon(g, pts);
/* 135 */         this.datasets[whichSet].gc.drawPolyline(g, pts);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doMarkers(Graphics g, int whichSet)
/*     */   {
/* 144 */     if (this.scatterPlot)
/* 145 */       doLine(g, whichSet);
/* 146 */     if (this.hasDiscontinuities)
/* 147 */       return;
/* 148 */     super.doMarkers(g, whichSet);
/*     */   }
/*     */ 
/*     */   private void doMarkerSegment(double[] xarr, double[] yarr, int whichSet, ArrayList dataVector, Graphics g)
/*     */   {
/* 159 */     boolean hasDatasetImage = true;
/*     */ 
/* 162 */     if ((!this.individualMarkers) && (this.datasets[whichSet].gc.image == null) && (this.datasets[whichSet].gc.markerStyle == -1)) {
/* 163 */       if (this.useDisplayList)
/* 164 */         hasDatasetImage = false;
/*     */       else {
/* 166 */         return;
/*     */       }
/*     */     }
/* 169 */     if (this.individualMarkers)
/* 170 */       hasDatasetImage = false;
/*     */     int imgHeight;
/*     */     int imgWidth;
/*     */     int imgHeight;
/* 174 */     if ((hasDatasetImage) && (this.datasets[whichSet].gc.image != null)) {
/* 175 */       int imgWidth = this.datasets[whichSet].gc.image.getWidth(null);
/* 176 */       imgHeight = this.datasets[whichSet].gc.image.getHeight(null);
/*     */     }
/*     */     else {
/* 179 */       imgWidth = 8;
/* 180 */       imgHeight = 8;
/* 181 */       if (this.individualMarkers) {
/* 182 */         Point[] pts = this.dataXfm.pointList(xarr, yarr);
/* 183 */         Point[] ipt = new Point[1];
/* 184 */         for (int i = 0; i < xarr.length; i++) {
/* 185 */           Gc gc = this.datasets[whichSet].getDataElementAt(i).gc;
/* 186 */           if (gc.image != null) {
/* 187 */             gc.drawImage(g, pts[i]);
/*     */           } else {
/* 189 */             ipt[0] = pts[i];
/* 190 */             gc.drawPolymarker(g, ipt);
/*     */           }
/*     */         }
/*     */       }
/*     */       else {
/* 195 */         this.datasets[whichSet].gc.drawPolymarker(g, this.dataXfm.pointList(xarr, yarr));
/*     */       }
/*     */     }
/* 198 */     for (int i = 0; i < yarr.length; i++) {
/* 199 */       if (hasDatasetImage) {
/* 200 */         this.datasets[whichSet].gc.drawImage(g, this.dataXfm.point(xarr[i], yarr[i]));
/*     */       }
/* 202 */       if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/* 203 */         Point ur = this.dataXfm.point(xarr[i], yarr[i]);
/* 204 */         ur.translate(imgWidth / 2, imgHeight / 2);
/* 205 */         Point ll = new Point(ur.x - imgWidth, ur.y - imgHeight);
/* 206 */         this.globals.displayList.addRectangle(
/* 207 */           this.datasets[whichSet], 
/* 208 */           ll, ur);
/* 209 */         this.globals.displayList.addRectangle(
/* 210 */           dataVector.get(i), 
/* 211 */           ll, ur);
/* 212 */         this.globals.displayList.addRectangle(
/* 213 */           this, 
/* 214 */           ll, ur);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.DiscontinuousLine
 * JD-Core Version:    0.6.2
 */