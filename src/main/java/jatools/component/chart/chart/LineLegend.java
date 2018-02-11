/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ 
/*     */ public class LineLegend extends Legend
/*     */   implements LegendInterface
/*     */ {
/*     */   public LineLegend()
/*     */   {
/*     */   }
/*     */ 
/*     */   public LineLegend(Dataset[] d, Globals g)
/*     */   {
/*  35 */     super(d, g);
/*     */   }
/*     */ 
/*     */   protected synchronized void doHorizontalIcons(Graphics g)
/*     */   {
/*  43 */     int numDatasets = datasetsInUse();
/*  44 */     g.setFont(this.labelFont);
/*  45 */     FontMetrics fm = g.getFontMetrics();
/*  46 */     int vertPad = fm.getMaxAscent() / 2;
/*  47 */     int cellWidth = cellWidth(numDatasets, fm);
/*     */ 
/*  49 */     int pIconWidth = (int)(this.iconWidth * this.gWidth);
/*  50 */     int pIconHeight = (int)(this.iconHeight * this.gHeight);
/*  51 */     int pFudge = (int)(this.iconGap * this.gWidth);
/*     */ 
/*  53 */     Point fromPoint = this.transform.point(this.llX + this.iconGap, this.llY + this.iconGap);
/*  54 */     Point toPoint = new Point(fromPoint.x + pIconWidth, fromPoint.y + pIconHeight);
/*  55 */     int startX = fromPoint.x;
/*  56 */     int numColumns = (this.globals.maxX - fromPoint.x + pFudge) / cellWidth;
/*  57 */     if (numColumns < 1) numColumns = 1;
/*  58 */     int numLines = numDatasets / numColumns;
/*  59 */     if (numDatasets % numColumns == 0) numLines--;
/*  60 */     int shiftUp = numLines * cellHeight();
/*  61 */     fromPoint.translate(0, shiftUp);
/*  62 */     toPoint.translate(0, shiftUp);
/*     */ 
/*  64 */     int rowNum = 0;
/*  65 */     if (!this.invertLegend)
/*  66 */       for (int i = 0; i < numDatasets; i++)
/*     */       {
/*  68 */         if ((fromPoint.x - pFudge + cellWidth > this.globals.maxX) && (i != 0))
/*     */         {
/*  70 */           fromPoint.x = startX;
/*  71 */           toPoint.x = (startX + pIconWidth);
/*  72 */           fromPoint.translate(0, -cellHeight());
/*  73 */           toPoint.translate(0, -cellHeight());
/*     */         }
/*     */ 
/*  76 */         this.datasets[i].gc.drawLine(g, fromPoint.x, fromPoint.y + vertPad, fromPoint.x + pIconWidth, fromPoint.y + vertPad);
/*  77 */         Point p = new Point(fromPoint.x + pIconWidth / 2, fromPoint.y + vertPad);
/*  78 */         this.datasets[i].gc.drawImage(g, p);
/*  79 */         Point[] pArr = new Point[1];
/*  80 */         pArr[0] = p;
/*  81 */         this.datasets[i].gc.drawPolymarker(g, pArr);
/*     */ 
/*  83 */         if ((this.useDisplayList) && (this.globals.useDisplayList))
/*  84 */           this.globals.displayList.addRectangle(this.datasets[i], fromPoint, toPoint);
/*  85 */         g.setColor(this.labelColor);
/*  86 */         this.backgroundGc.drawString(g, toPoint.x + pFudge, fromPoint.y, this.datasets[i].setName.replace("|".charAt(0), ' '));
/*  87 */         fromPoint.translate(cellWidth, 0);
/*  88 */         toPoint.translate(cellWidth, 0);
/*     */       }
/*     */     else
/*  91 */       for (int i = numDatasets - 1; i >= 0; i--)
/*     */       {
/*  93 */         if (fromPoint.x - pFudge + cellWidth > this.globals.maxX) {
/*  94 */           fromPoint.x = startX;
/*  95 */           toPoint.x = (startX + pIconWidth);
/*  96 */           fromPoint.translate(0, -cellHeight());
/*  97 */           toPoint.translate(0, -cellHeight());
/*     */         }
/*     */ 
/* 100 */         this.datasets[i].gc.drawLine(g, fromPoint.x, fromPoint.y + vertPad, fromPoint.x + pIconWidth, fromPoint.y + vertPad);
/* 101 */         Point p = new Point(fromPoint.x + pIconWidth / 2, fromPoint.y + vertPad);
/* 102 */         this.datasets[i].gc.drawImage(g, p);
/* 103 */         Point[] pArr = new Point[1];
/* 104 */         pArr[0] = p;
/* 105 */         this.datasets[i].gc.drawPolymarker(g, pArr);
/*     */ 
/* 107 */         if ((this.useDisplayList) && (this.globals.useDisplayList))
/* 108 */           this.globals.displayList.addRectangle(this.datasets[i], fromPoint, toPoint);
/* 109 */         g.setColor(this.labelColor);
/* 110 */         this.backgroundGc.drawString(g, toPoint.x + pFudge, fromPoint.y, this.datasets[i].setName.replace("|".charAt(0), ' '));
/* 111 */         fromPoint.translate(cellWidth, 0);
/* 112 */         toPoint.translate(cellWidth, 0);
/*     */       }
/*     */   }
/*     */ 
/*     */   protected synchronized void doVerticalIcons(Graphics g)
/*     */   {
/* 122 */     g.setFont(this.labelFont);
/* 123 */     FontMetrics fm = g.getFontMetrics();
/* 124 */     int vertPad = fm.getMaxAscent() / 2;
/* 125 */     int numDatasets = datasetsInUse();
/* 126 */     int cellWidth = cellWidth(numDatasets, fm);
/* 127 */     int cellHeight = cellHeight();
/*     */ 
/* 129 */     int pIconWidth = (int)(this.iconWidth * this.gWidth);
/* 130 */     int pIconHeight = (int)(this.iconHeight * this.gHeight);
/* 131 */     int pFudge = (int)(this.iconGap * this.gWidth);
/*     */ 
/* 133 */     Point fromPoint = this.transform.point(this.llX + this.iconGap, this.llY + this.iconGap);
/* 134 */     Point toPoint = new Point(fromPoint.x + pIconWidth, fromPoint.y + pIconHeight);
/* 135 */     int startY = fromPoint.y;
/*     */ 
/* 137 */     Point pixSize = new Point(toPoint.x - fromPoint.x, toPoint.y - fromPoint.y);
/* 138 */     if (!this.invertLegend) {
/* 139 */       for (int i = 0; i < numDatasets; i++) {
/* 140 */         this.datasets[i].gc.drawLine(g, fromPoint.x, fromPoint.y + vertPad, fromPoint.x + pIconWidth, fromPoint.y + vertPad);
/* 141 */         Point p = new Point(fromPoint.x + pIconWidth / 2, fromPoint.y + vertPad);
/* 142 */         this.datasets[i].gc.drawImage(g, p);
/* 143 */         Point[] pArr = new Point[1];
/* 144 */         pArr[0] = p;
/* 145 */         this.datasets[i].gc.drawPolymarker(g, pArr);
/*     */ 
/* 147 */         if ((this.useDisplayList) && (this.globals.useDisplayList))
/* 148 */           this.globals.displayList.addRectangle(this.datasets[i], fromPoint, toPoint);
/* 149 */         g.setColor(this.labelColor);
/* 150 */         this.backgroundGc.drawString(g, toPoint.x + 2, fromPoint.y, this.datasets[i].setName.replace("|".charAt(0), ' '));
/* 151 */         fromPoint.translate(0, cellHeight);
/* 152 */         toPoint.translate(0, cellHeight);
/*     */ 
/* 154 */         if (toPoint.y > this.globals.maxY) {
/* 155 */           fromPoint.y = startY;
/* 156 */           fromPoint.y += pIconHeight;
/* 157 */           fromPoint.translate(cellWidth, 0);
/* 158 */           toPoint.translate(cellWidth, 0);
/*     */         }
/*     */       }
/*     */     } else {
/* 162 */       int height = (int)Math.floor((this.globals.maxY - startY) / cellHeight);
/* 163 */       if (height == 0)
/* 164 */         height = 1;
/* 165 */       if (height > numDatasets)
/* 166 */         height = numDatasets;
/* 167 */       fromPoint.y = (startY + (height - 1) * cellHeight);
/* 168 */       fromPoint.y += pIconHeight;
/* 169 */       for (int i = 0; i < numDatasets; i++) {
/* 170 */         this.datasets[i].gc.drawLine(g, fromPoint.x, fromPoint.y + vertPad, fromPoint.x + pIconWidth, fromPoint.y + vertPad);
/* 171 */         Point p = new Point(fromPoint.x + pIconWidth / 2, fromPoint.y + vertPad);
/* 172 */         this.datasets[i].gc.drawImage(g, p);
/* 173 */         Point[] pArr = new Point[1];
/* 174 */         pArr[0] = p;
/* 175 */         this.datasets[i].gc.drawPolymarker(g, pArr);
/*     */ 
/* 177 */         if ((this.useDisplayList) && (this.globals.useDisplayList))
/* 178 */           this.globals.displayList.addRectangle(this.datasets[i], fromPoint, toPoint);
/* 179 */         g.setColor(this.labelColor);
/* 180 */         this.backgroundGc.drawString(g, toPoint.x + 2, fromPoint.y, this.datasets[i].setName.replace("|".charAt(0), ' '));
/* 181 */         fromPoint.translate(0, -cellHeight);
/* 182 */         toPoint.translate(0, -cellHeight);
/*     */ 
/* 184 */         if (fromPoint.y < startY) {
/* 185 */           fromPoint.y = (startY + (height - 1) * cellHeight);
/* 186 */           fromPoint.y += pIconHeight;
/* 187 */           fromPoint.translate(cellWidth, 0);
/* 188 */           toPoint.translate(cellWidth, 0);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.LineLegend
 * JD-Core Version:    0.6.2
 */