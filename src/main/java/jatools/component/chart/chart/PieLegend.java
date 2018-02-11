/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class PieLegend extends Legend
/*     */   implements LegendInterface
/*     */ {
/*     */   Dataset dataset;
/*     */ 
/*     */   public PieLegend()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PieLegend(Dataset[] d, Globals g)
/*     */   {
/*  38 */     super(d, g);
/*  39 */     this.datasets = d;
/*     */   }
/*     */ 
/*     */   protected int cellWidth(int numPoints, FontMetrics fm)
/*     */   {
/*  48 */     int maxStringWidth = 0;
/*  49 */     for (int i = 0; i < numPoints; i++) {
/*  50 */       int wid = Gc.getStringWidth(fm, this.datasets[0].getDataElementAt(i).getString());
/*  51 */       if (wid > maxStringWidth)
/*  52 */         maxStringWidth = wid;
/*     */     }
/*  54 */     return maxStringWidth + (int)(this.globals.maxX * (this.iconWidth + this.iconGap * 2.0D));
/*     */   }
/*     */ 
/*     */   protected synchronized void doHorizontalIcons(Graphics g)
/*     */   {
/*  60 */     int numDatasets = this.datasets[0].data.size();
/*  61 */     g.setFont(this.labelFont);
/*  62 */     FontMetrics fm = g.getFontMetrics();
/*  63 */     int cellWidth = cellWidth(numDatasets, fm);
/*     */ 
/*  65 */     int pIconWidth = (int)(this.iconWidth * this.gWidth);
/*  66 */     int pIconHeight = (int)(this.iconHeight * this.gHeight);
/*  67 */     int pFudge = (int)(this.iconGap * this.gWidth);
/*     */ 
/*  69 */     Point fromPoint = this.transform.point(this.llX + this.iconGap, this.llY + this.iconGap);
/*  70 */     Point toPoint = new Point(fromPoint.x + pIconWidth, fromPoint.y + pIconHeight);
/*  71 */     int startX = fromPoint.x;
/*  72 */     int numColumns = (this.globals.maxX - fromPoint.x + pFudge) / cellWidth;
/*  73 */     if (numColumns <= 1) {
/*  74 */       numColumns = 1;
/*     */     }
/*  76 */     int numLines = numDatasets / numColumns;
/*  77 */     if (numDatasets % numColumns == 0) numLines--;
/*  78 */     int shiftUp = numLines * cellHeight();
/*  79 */     fromPoint.translate(0, shiftUp);
/*  80 */     toPoint.translate(0, shiftUp);
/*     */ 
/*  82 */     int rowNum = 0;
/*  83 */     if (!this.invertLegend)
/*  84 */       for (int i = 0; i < numDatasets; i++)
/*     */       {
/*  86 */         if ((fromPoint.x - pFudge + cellWidth > this.globals.maxX) && (i != 0))
/*     */         {
/*  88 */           fromPoint.x = startX;
/*  89 */           toPoint.x = (startX + pIconWidth);
/*  90 */           fromPoint.translate(0, -cellHeight());
/*  91 */           toPoint.translate(0, -cellHeight());
/*     */         }
/*     */ 
/*  94 */         this.datasets[0].getDataElementAt(i).gc.fillRect(g, fromPoint, toPoint);
/*  95 */         if ((this.useDisplayList) && (this.globals.useDisplayList))
/*  96 */           this.globals.displayList.addRectangle(this.datasets[0].getDataElementAt(i), fromPoint, toPoint);
/*  97 */         g.setColor(this.labelColor);
/*  98 */         this.backgroundGc.drawString(g, toPoint.x + pFudge, fromPoint.y, this.datasets[0].getDataElementAt(i).getString().replace('|', ' '));
/*  99 */         fromPoint.translate(cellWidth, 0);
/* 100 */         toPoint.translate(cellWidth, 0);
/*     */       }
/*     */     else
/* 103 */       for (int i = numDatasets - 1; i >= 0; i--)
/*     */       {
/* 105 */         if (fromPoint.x - pFudge + cellWidth > this.globals.maxX) {
/* 106 */           fromPoint.x = startX;
/* 107 */           toPoint.x = (startX + pIconWidth);
/* 108 */           fromPoint.translate(0, -cellHeight());
/* 109 */           toPoint.translate(0, -cellHeight());
/*     */         }
/*     */ 
/* 112 */         this.datasets[0].getDataElementAt(i).gc.fillRect(g, fromPoint, toPoint);
/* 113 */         if ((this.useDisplayList) && (this.globals.useDisplayList))
/* 114 */           this.globals.displayList.addRectangle(this.datasets[0].getDataElementAt(i), fromPoint, toPoint);
/* 115 */         g.setColor(this.labelColor);
/* 116 */         this.backgroundGc.drawString(g, toPoint.x + pFudge, fromPoint.y, this.datasets[0].getDataElementAt(i).getString().replace('|', ' '));
/* 117 */         fromPoint.translate(cellWidth, 0);
/* 118 */         toPoint.translate(cellWidth, 0);
/*     */       }
/*     */   }
/*     */ 
/*     */   protected synchronized void doVerticalIcons(Graphics g)
/*     */   {
/* 126 */     g.setFont(this.labelFont);
/* 127 */     FontMetrics fm = g.getFontMetrics();
/* 128 */     int numDatasets = this.datasets[0].data.size();
/* 129 */     int cellWidth = cellWidth(numDatasets, fm);
/* 130 */     int cellHeight = cellHeight();
/*     */ 
/* 132 */     int pIconWidth = (int)(this.iconWidth * this.gWidth);
/* 133 */     int pIconHeight = (int)(this.iconHeight * this.gHeight);
/* 134 */     int pFudge = (int)(this.iconGap * this.gWidth);
/*     */ 
/* 136 */     Point fromPoint = this.transform.point(this.llX + this.iconGap, this.llY + this.iconGap);
/* 137 */     Point toPoint = new Point(fromPoint.x + pIconWidth, fromPoint.y + pIconHeight);
/* 138 */     int startY = fromPoint.y;
/*     */ 
/* 140 */     Point pixSize = new Point(toPoint.x - fromPoint.x, toPoint.y - fromPoint.y);
/* 141 */     if (!this.invertLegend) {
/* 142 */       for (int i = 0; i < numDatasets; i++) {
/* 143 */         this.datasets[0].getDataElementAt(i).gc.fillRect(g, fromPoint, toPoint);
/* 144 */         if ((this.useDisplayList) && (this.globals.useDisplayList))
/* 145 */           this.globals.displayList.addRectangle(this.datasets[0].getDataElementAt(i), fromPoint, toPoint);
/* 146 */         g.setColor(this.labelColor);
/* 147 */         this.backgroundGc.drawString(g, toPoint.x + pFudge, fromPoint.y, this.datasets[0].getDataElementAt(i).getString().replace('|', ' '));
/* 148 */         fromPoint.translate(0, cellHeight);
/* 149 */         toPoint.translate(0, cellHeight);
/*     */ 
/* 151 */         if (toPoint.y > this.globals.maxY) {
/* 152 */           fromPoint.y = startY;
/* 153 */           fromPoint.y += pIconHeight;
/* 154 */           fromPoint.translate(cellWidth, 0);
/* 155 */           toPoint.translate(cellWidth, 0);
/*     */         }
/*     */       }
/*     */     } else {
/* 159 */       int height = (int)Math.floor((this.globals.maxY - startY) / cellHeight);
/* 160 */       if (height == 0)
/* 161 */         height = 1;
/* 162 */       if (height > numDatasets)
/* 163 */         height = numDatasets;
/* 164 */       fromPoint.y = (startY + (height - 1) * cellHeight);
/* 165 */       fromPoint.y += pIconHeight;
/* 166 */       for (int i = 0; i < numDatasets; i++) {
/* 167 */         this.datasets[0].getDataElementAt(i).gc.fillRect(g, fromPoint, toPoint);
/* 168 */         if ((this.useDisplayList) && (this.globals.useDisplayList))
/* 169 */           this.globals.displayList.addRectangle(this.datasets[0].getDataElementAt(i), fromPoint, toPoint);
/* 170 */         g.setColor(this.labelColor);
/* 171 */         this.backgroundGc.drawString(g, toPoint.x + pFudge, fromPoint.y, this.datasets[0].getDataElementAt(i).getString().replace('|', ' '));
/* 172 */         fromPoint.translate(0, -cellHeight);
/* 173 */         toPoint.translate(0, -cellHeight);
/*     */ 
/* 175 */         if (fromPoint.y < startY) {
/* 176 */           fromPoint.y = (startY + (height - 1) * cellHeight);
/* 177 */           fromPoint.y += pIconHeight;
/* 178 */           fromPoint.translate(cellWidth, 0);
/* 179 */           toPoint.translate(cellWidth, 0);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void recalculateSize(Graphics g)
/*     */   {
/* 189 */     g.setFont(this.labelFont);
/* 190 */     FontMetrics fm = g.getFontMetrics();
/* 191 */     int numDatasets = this.datasets[0].data.size();
/* 192 */     int cellWidth = cellWidth(numDatasets, fm);
/* 193 */     int cellHeight = cellHeight();
/* 194 */     int nRows = 0; int nColumns = 0;
/*     */ 
/* 196 */     if (this.verticalLayout) {
/* 197 */       int vPixAvail = (int)(this.globals.maxY * (1.0D - this.llY));
/* 198 */       nRows = vPixAvail / cellHeight;
/* 199 */       if (nRows > numDatasets) nRows = numDatasets;
/* 200 */       if (nRows == 0) nRows = 1;
/* 201 */       nColumns = numDatasets / nRows;
/* 202 */       if (numDatasets % nRows != 0) nColumns++; 
/*     */     }
/*     */     else
/*     */     {
/* 205 */       int hPixAvail = (int)(this.globals.maxX * (1.0D - this.llX));
/* 206 */       nColumns = hPixAvail / cellWidth;
/* 207 */       if (nColumns > numDatasets) nColumns = numDatasets;
/* 208 */       if (nColumns == 0) nColumns = 1;
/* 209 */       nRows = numDatasets / nColumns;
/* 210 */       if (numDatasets % nColumns != 0) nRows++;
/*     */     }
/* 212 */     if (nRows == 0) nRows = 1;
/* 213 */     if (nColumns == 0) nColumns = 1;
/* 214 */     this.urX = (this.llX + this.iconGap + nColumns * cellWidth / this.globals.maxX);
/* 215 */     this.urY = (this.llY + this.iconGap + nRows * cellHeight / this.globals.maxY);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.PieLegend
 * JD-Core Version:    0.6.2
 */