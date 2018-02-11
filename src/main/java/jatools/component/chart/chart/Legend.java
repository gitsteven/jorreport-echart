/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Legend
/*     */   implements LegendInterface, Serializable
/*     */ {
/*     */   protected Globals globals;
/*  27 */   protected Gc backgroundGc = new Gc(Color.white, null);
/*     */ 
/*  29 */   protected Font labelFont = Gc.defaultFont;
/*     */ 
/*  31 */   protected Color labelColor = Color.black;
/*     */ 
/*  33 */   protected boolean verticalLayout = false;
/*     */ 
/*  35 */   protected boolean backgroundVisible = true;
/*     */ 
/*  37 */   protected double llX = 0.0D;
/*  38 */   protected double llY = 0.0D;
/*     */   protected double urX;
/*     */   protected double urY;
/*  43 */   protected double iconHeight = 0.05D;
/*     */ 
/*  45 */   protected double iconWidth = 0.07000000000000001D;
/*     */ 
/*  47 */   protected double iconGap = 0.02D;
/*     */ 
/*  49 */   protected boolean useDisplayList = true;
/*     */ 
/*  51 */   protected boolean invertLegend = false;
/*     */   protected Dataset[] datasets;
/*     */   protected Transform transform;
/*  62 */   int gWidth = 640;
/*     */ 
/*  64 */   int gHeight = 480;
/*     */ 
/*     */   public Legend()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Legend(Dataset[] d, Globals g)
/*     */   {
/*  83 */     this.backgroundGc.globals = g;
/*  84 */     this.globals = g;
/*  85 */     this.datasets = d;
/*     */   }
/*     */ 
/*     */   protected int cellHeight()
/*     */   {
/*  94 */     return (int)(this.globals.maxY * (this.iconHeight + this.iconGap + this.iconGap));
/*     */   }
/*     */ 
/*     */   protected int cellWidth(int numDataSets, FontMetrics fm)
/*     */   {
/* 103 */     int maxStringWidth = 0;
/* 104 */     for (int i = 0; i < numDataSets; i++) {
/* 105 */       int wid = fm.stringWidth(this.datasets[i].setName);
/* 106 */       if (wid > maxStringWidth)
/* 107 */         maxStringWidth = wid;
/*     */     }
/* 109 */     return maxStringWidth + 
/* 110 */       (int)(this.globals.maxX * (this.iconWidth + this.iconGap * 2.0D));
/*     */   }
/*     */ 
/*     */   protected synchronized int datasetsInUse()
/*     */   {
/* 116 */     for (int i = 0; i < this.datasets.length; i++)
/* 117 */       if (this.datasets[i] == null)
/* 118 */         return i;
/* 119 */     return i;
/*     */   }
/*     */ 
/*     */   protected synchronized void doHorizontalIcons(Graphics g)
/*     */   {
/* 129 */     int numDatasets = datasetsInUse();
/* 130 */     g.setFont(this.labelFont);
/* 131 */     FontMetrics fm = g.getFontMetrics();
/* 132 */     int cellWidth = cellWidth(numDatasets, fm);
/*     */ 
/* 134 */     int pIconWidth = (int)(this.iconWidth * this.gWidth);
/* 135 */     int pIconHeight = (int)(this.iconHeight * this.gHeight);
/* 136 */     int pFudge = (int)(this.iconGap * this.gWidth);
/*     */ 
/* 138 */     Point fromPoint = this.transform.point(this.llX + this.iconGap, this.llY + this.iconGap);
/* 139 */     Point toPoint = new Point(fromPoint.x + pIconWidth, fromPoint.y + 
/* 140 */       pIconHeight);
/* 141 */     int startX = fromPoint.x;
/* 142 */     int numColumns = (this.globals.maxX - fromPoint.x + pFudge) / cellWidth;
/* 143 */     if (numColumns < 1)
/* 144 */       numColumns = 1;
/* 145 */     int numLines = numDatasets / numColumns;
/* 146 */     if (numDatasets % numColumns == 0)
/* 147 */       numLines--;
/* 148 */     int shiftUp = numLines * cellHeight();
/* 149 */     fromPoint.translate(0, shiftUp);
/* 150 */     toPoint.translate(0, shiftUp);
/*     */ 
/* 154 */     int rowNum = 0;
/* 155 */     if (!this.invertLegend)
/* 156 */       for (int i = 0; i < numDatasets; i++)
/*     */       {
/* 158 */         if ((fromPoint.x - pFudge + cellWidth > this.globals.maxX) && 
/* 159 */           (i != 0))
/*     */         {
/* 161 */           fromPoint.x = startX;
/* 162 */           toPoint.x = (startX + pIconWidth);
/* 163 */           fromPoint.translate(0, -cellHeight());
/* 164 */           toPoint.translate(0, -cellHeight());
/*     */         }
/*     */ 
/* 167 */         this.datasets[i].gc.fillRect(g, fromPoint, toPoint);
/* 168 */         if ((this.useDisplayList) && (this.globals.useDisplayList))
/* 169 */           this.globals.displayList.addRectangle(this.datasets[i], fromPoint, 
/* 170 */             toPoint);
/* 171 */         g.setColor(this.labelColor);
/* 172 */         this.backgroundGc.drawString(g, toPoint.x + pFudge, fromPoint.y, 
/* 173 */           this.datasets[i].setName.replace("|".charAt(0), 
/* 174 */           ' '));
/* 175 */         fromPoint.translate(cellWidth, 0);
/* 176 */         toPoint.translate(cellWidth, 0);
/*     */       }
/*     */     else
/* 179 */       for (int i = numDatasets - 1; i >= 0; i--)
/*     */       {
/* 181 */         if (fromPoint.x - pFudge + cellWidth > this.globals.maxX)
/*     */         {
/* 184 */           fromPoint.x = startX;
/* 185 */           toPoint.x = (startX + pIconWidth);
/* 186 */           fromPoint.translate(0, -cellHeight());
/* 187 */           toPoint.translate(0, -cellHeight());
/*     */         }
/*     */ 
/* 190 */         this.datasets[i].gc.fillRect(g, fromPoint, toPoint);
/* 191 */         if ((this.useDisplayList) && (this.globals.useDisplayList))
/* 192 */           this.globals.displayList.addRectangle(this.datasets[i], fromPoint, 
/* 193 */             toPoint);
/* 194 */         g.setColor(this.labelColor);
/* 195 */         this.backgroundGc.drawString(g, toPoint.x + pFudge, fromPoint.y, 
/* 196 */           this.datasets[i].setName.replace("|".charAt(0), 
/* 197 */           ' '));
/* 198 */         fromPoint.translate(cellWidth, 0);
/* 199 */         toPoint.translate(cellWidth, 0);
/*     */       }
/*     */   }
/*     */ 
/*     */   protected synchronized void doVerticalIcons(Graphics g)
/*     */   {
/* 213 */     g.setFont(this.labelFont);
/* 214 */     FontMetrics fm = g.getFontMetrics();
/* 215 */     int numDatasets = datasetsInUse();
/* 216 */     int cellWidth = cellWidth(numDatasets, fm);
/* 217 */     int cellHeight = cellHeight();
/*     */ 
/* 219 */     int pIconWidth = (int)(this.iconWidth * this.gWidth);
/* 220 */     int pIconHeight = (int)(this.iconHeight * this.gHeight);
/* 221 */     int pFudge = (int)(this.iconGap * this.gWidth);
/*     */ 
/* 223 */     Point fromPoint = this.transform.point(this.llX + this.iconGap, this.llY + this.iconGap);
/* 224 */     Point toPoint = new Point(fromPoint.x + pIconWidth, fromPoint.y + 
/* 225 */       pIconHeight);
/* 226 */     int startY = fromPoint.y;
/*     */ 
/* 228 */     Point pixSize = new Point(toPoint.x - fromPoint.x, toPoint.y - 
/* 229 */       fromPoint.y);
/* 230 */     if (!this.invertLegend) {
/* 231 */       for (int i = 0; i < numDatasets; i++) {
/* 232 */         this.datasets[i].gc.fillRect(g, fromPoint, toPoint);
/* 233 */         if ((this.useDisplayList) && (this.globals.useDisplayList))
/* 234 */           this.globals.displayList.addRectangle(this.datasets[i], 
/* 235 */             fromPoint, toPoint);
/* 236 */         g.setColor(this.labelColor);
/* 237 */         this.backgroundGc.drawString(g, toPoint.x + pFudge, fromPoint.y, 
/* 238 */           this.datasets[i].setName.replace("|".charAt(0), 
/* 239 */           ' '));
/* 240 */         fromPoint.translate(0, cellHeight);
/* 241 */         toPoint.translate(0, cellHeight);
/*     */ 
/* 243 */         if (toPoint.y > this.globals.maxY) {
/* 244 */           fromPoint.y = startY;
/* 245 */           fromPoint.y += pIconHeight;
/* 246 */           fromPoint.translate(cellWidth, 0);
/* 247 */           toPoint.translate(cellWidth, 0);
/*     */         }
/*     */       }
/*     */     } else {
/* 251 */       int height = (int)Math.floor((this.globals.maxY - startY) / cellHeight);
/* 252 */       if (height == 0)
/* 253 */         height = 1;
/* 254 */       if (height > numDatasets)
/* 255 */         height = numDatasets;
/* 256 */       fromPoint.y = (startY + (height - 1) * cellHeight);
/* 257 */       fromPoint.y += pIconHeight;
/* 258 */       for (int i = 0; i < numDatasets; i++) {
/* 259 */         this.datasets[i].gc.fillRect(g, fromPoint, toPoint);
/* 260 */         if ((this.useDisplayList) && (this.globals.useDisplayList))
/* 261 */           this.globals.displayList.addRectangle(this.datasets[i], 
/* 262 */             fromPoint, toPoint);
/* 263 */         g.setColor(this.labelColor);
/* 264 */         this.backgroundGc.drawString(g, toPoint.x + pFudge, fromPoint.y, 
/* 265 */           this.datasets[i].setName.replace("|".charAt(0), 
/* 266 */           ' '));
/* 267 */         fromPoint.translate(0, -cellHeight);
/* 268 */         toPoint.translate(0, -cellHeight);
/*     */ 
/* 270 */         if (fromPoint.y < startY) {
/* 271 */           fromPoint.y = (startY + (height - 1) * cellHeight);
/* 272 */           fromPoint.y += pIconHeight;
/* 273 */           fromPoint.translate(cellWidth, 0);
/* 274 */           toPoint.translate(cellWidth, 0);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void draw(Graphics g)
/*     */   {
/* 287 */     if (g == null)
/* 288 */       return;
/* 289 */     if (this.transform == null) {
/* 290 */       resize(this.globals.maxX, this.globals.maxY);
/*     */     }
/* 292 */     recalculateSize(g);
/* 293 */     if (this.backgroundVisible) {
/* 294 */       Point p1 = this.transform.point(this.llX, this.llY);
/* 295 */       Point p2 = this.transform.point(this.urX, this.urY);
/* 296 */       if (this.backgroundGc.getOutlineFills()) {
/* 297 */         p2.translate(-1, 0);
/* 298 */         p1.translate(0, 1);
/*     */       }
/* 300 */       this.backgroundGc.fillRect(g, p1, p2);
/* 301 */       if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/* 302 */         this.globals.displayList.addRectangle(this, p1, p2);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 308 */     if (this.verticalLayout)
/* 309 */       doVerticalIcons(g);
/*     */     else
/* 311 */       doHorizontalIcons(g);
/*     */   }
/*     */ 
/*     */   public Gc getBackgroundGc()
/*     */   {
/* 319 */     return this.backgroundGc;
/*     */   }
/*     */ 
/*     */   public boolean getBackgroundVisible()
/*     */   {
/* 328 */     return this.backgroundVisible;
/*     */   }
/*     */ 
/*     */   public double getIconGap()
/*     */   {
/* 337 */     return this.iconGap;
/*     */   }
/*     */ 
/*     */   public double getIconHeight()
/*     */   {
/* 346 */     return this.iconHeight;
/*     */   }
/*     */ 
/*     */   public double getIconWidth()
/*     */   {
/* 355 */     return this.iconWidth;
/*     */   }
/*     */ 
/*     */   public boolean getInvertLegend()
/*     */   {
/* 365 */     return this.invertLegend;
/*     */   }
/*     */ 
/*     */   public Color getLabelColor()
/*     */   {
/* 374 */     return this.labelColor;
/*     */   }
/*     */ 
/*     */   public Font getLabelFont()
/*     */   {
/* 383 */     return this.labelFont;
/*     */   }
/*     */ 
/*     */   public double getLlX()
/*     */   {
/* 392 */     return this.llX;
/*     */   }
/*     */ 
/*     */   public double getLlY()
/*     */   {
/* 401 */     return this.llY;
/*     */   }
/*     */ 
/*     */   public double getUrX()
/*     */   {
/* 410 */     return this.urX;
/*     */   }
/*     */ 
/*     */   public double getUrY()
/*     */   {
/* 419 */     return this.urY;
/*     */   }
/*     */ 
/*     */   public boolean getUseDisplayList()
/*     */   {
/* 429 */     return this.useDisplayList;
/*     */   }
/*     */ 
/*     */   public boolean getVerticalLayout()
/*     */   {
/* 437 */     return this.verticalLayout;
/*     */   }
/*     */ 
/*     */   public synchronized void recalculateSize(Graphics g)
/*     */   {
/* 449 */     g.setFont(this.labelFont);
/* 450 */     FontMetrics fm = g.getFontMetrics();
/* 451 */     int numDatasets = datasetsInUse();
/* 452 */     int cellWidth = cellWidth(numDatasets, fm);
/* 453 */     int cellHeight = cellHeight();
/* 454 */     int nRows = 0; int nColumns = 0;
/*     */ 
/* 456 */     if (this.verticalLayout) {
/* 457 */       int vPixAvail = (int)(this.globals.maxY * (1.0D - this.llY));
/* 458 */       nRows = vPixAvail / cellHeight;
/* 459 */       if (nRows > numDatasets)
/* 460 */         nRows = numDatasets;
/* 461 */       if (nRows == 0)
/* 462 */         nRows = 1;
/* 463 */       nColumns = numDatasets / nRows;
/* 464 */       if (numDatasets % nRows != 0)
/* 465 */         nColumns++;
/*     */     } else {
/* 467 */       int hPixAvail = (int)(this.globals.maxX * (1.0D - this.llX));
/* 468 */       nColumns = hPixAvail / cellWidth;
/* 469 */       if (nColumns > numDatasets)
/* 470 */         nColumns = numDatasets;
/* 471 */       if (nColumns == 0)
/* 472 */         nColumns = 1;
/* 473 */       nRows = numDatasets / nColumns;
/* 474 */       if (numDatasets % nColumns != 0)
/* 475 */         nRows++;
/*     */     }
/* 477 */     if (nRows == 0)
/* 478 */       nRows = 1;
/* 479 */     if (nColumns == 0)
/* 480 */       nColumns = 1;
/* 481 */     this.urX = 
/* 483 */       (this.llX + 
/* 482 */       this.iconGap + 
/* 483 */       nColumns * cellWidth / this.globals.maxX);
/* 484 */     this.urY = 
/* 486 */       (this.llY + 
/* 485 */       this.iconGap + 
/* 486 */       nRows * cellHeight / this.globals.maxY);
/*     */   }
/*     */ 
/*     */   public void resize(int w, int h)
/*     */   {
/* 500 */     this.gWidth = w;
/* 501 */     this.gHeight = h;
/*     */ 
/* 503 */     this.transform = new Transform(0.0D, 0.0D, 1.0D, 1.0D, 0, 0, this.gWidth, 
/* 504 */       this.gHeight);
/*     */   }
/*     */ 
/*     */   public void setBackgroundGC(Gc g)
/*     */   {
/* 514 */     this.backgroundGc = g;
/* 515 */     g.globals = this.globals;
/*     */   }
/*     */ 
/*     */   public void setBackgroundVisible(boolean vis)
/*     */   {
/* 524 */     this.backgroundVisible = vis;
/*     */   }
/*     */ 
/*     */   public void setIconGap(double d)
/*     */   {
/* 533 */     this.iconGap = d;
/*     */   }
/*     */ 
/*     */   public void setIconHeight(double d)
/*     */   {
/* 542 */     this.iconHeight = d;
/*     */   }
/*     */ 
/*     */   public void setIconWidth(double d)
/*     */   {
/* 551 */     this.iconWidth = d;
/*     */   }
/*     */ 
/*     */   public void setInvertLegend(boolean trueFalse)
/*     */   {
/* 561 */     this.invertLegend = trueFalse;
/*     */   }
/*     */ 
/*     */   public void setLabelColor(Color c)
/*     */   {
/* 570 */     this.labelColor = c;
/*     */   }
/*     */ 
/*     */   public void setLabelFont(Font f)
/*     */   {
/* 579 */     this.labelFont = f;
/*     */   }
/*     */ 
/*     */   public void setLlX(double d)
/*     */   {
/* 588 */     this.llX = d;
/*     */   }
/*     */ 
/*     */   public void setLlY(double d)
/*     */   {
/* 597 */     this.llY = d;
/*     */   }
/*     */ 
/*     */   public void setUseDisplayList(boolean onOff)
/*     */   {
/* 607 */     this.useDisplayList = onOff;
/*     */   }
/*     */ 
/*     */   public void setVerticalLayout(boolean trueFalse)
/*     */   {
/* 616 */     this.verticalLayout = trueFalse;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 620 */     return getClass().getName() + "[" + "urX " + this.urX + "urY " + this.urY + 
/* 621 */       "llX " + this.llX + "llY " + this.llY + "]";
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Legend
 * JD-Core Version:    0.6.2
 */