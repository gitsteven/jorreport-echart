/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ 
/*     */ public class StackColumn extends Bar
/*     */ {
/*     */   public StackColumn()
/*     */   {
/*     */   }
/*     */ 
/*     */   public StackColumn(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*     */   {
/*  39 */     super(d, xAx, yAx, subplot);
/*     */   }
/*     */ 
/*     */   protected void doBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/*  52 */     double offsetLeft = 0.5D * this.observationDelta * this.clusterWidth;
/*     */ 
/*  55 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */     double llx;
/*     */     double llx;
/*  56 */     if (this.unitScaling)
/*  57 */       llx = whichBar - offsetLeft;
/*     */     else {
/*  59 */       llx = dat.x - offsetLeft;
/*     */     }
/*  61 */     double urx = llx + this.clusterWidth * this.observationDelta;
/*  62 */     double lly = getBaseY(whichSet, whichBar);
/*     */     try
/*     */     {
/*  71 */       ury = lly + dat.y;
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException e)
/*     */     {
/*     */       double ury;
/*     */       return;
/*     */     }
/*     */     double ury;
/*     */     Gc myGc;
/*     */     Gc myGc;
/*  77 */     if (!individualColors)
/*  78 */       myGc = this.datasets[whichSet].gc;
/*     */     else
/*  80 */       myGc = dat.gc;
/*  81 */     Point p1 = this.dataXfm.point(llx, lly);
/*  82 */     Point p2 = this.dataXfm.point(urx, ury);
/*  83 */     if (p2.x - p1.x < 1)
/*  84 */       p2.translate(1, 0);
/*  85 */     myGc.fillRect(g, p1, p2);
/*  86 */     if ((this.useDisplayList) && (this.globals.useDisplayList))
/*     */     {
/*  88 */       this.globals.displayList.addRectangle(this.datasets[whichSet], p1, p2);
/*  89 */       this.globals.displayList.addRectangle(dat, p1, p2);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doBarLabel(Graphics g, double howWide, int whichSet, int whichBar)
/*     */   {
/* 105 */     double offsetLeft = 0.5D * this.clusterWidth * this.observationDelta;
/*     */ 
/* 109 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */     double llx;
/*     */     double llx;
/* 111 */     if (this.unitScaling)
/* 112 */       llx = whichBar - offsetLeft;
/*     */     else {
/* 114 */       llx = dat.x - offsetLeft;
/*     */     }
/*     */ 
/* 117 */     double urx = llx + this.clusterWidth * this.observationDelta;
/* 118 */     double lly = getBaseY(whichSet, whichBar);
/* 119 */     double ury = lly + dat.y;
/*     */     String str;
/*     */     String str;
/* 123 */     if ((dat.label != null) && (!this.useValueLabels)) {
/* 124 */       str = dat.label;
/*     */     }
/*     */     else {
/* 127 */       if (dat.y == 0.0D)
/* 128 */         return;
/* 129 */       str = formatLabel(dat.y);
/*     */     }
/*     */ 
/* 132 */     g.setFont(this.datasets[whichSet].labelFont);
/* 133 */     g.setColor(this.datasets[whichSet].labelColor);
/*     */ 
/* 135 */     FontMetrics fm = g.getFontMetrics();
/* 136 */     Point loc = this.dataXfm.point(llx + (urx - llx) / 2.0D, lly + (ury - lly) / 2.0D);
/* 137 */     this.datasets[whichSet].gc.drawSmartString(g, loc.x, loc.y, 2, this.labelAngle, fm, str);
/*     */   }
/*     */ 
/*     */   protected void doDBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/* 151 */     double offsetLeft = 0.5D * this.clusterWidth * this.observationDelta;
/*     */ 
/* 157 */     Point[] face = new Point[4];
/* 158 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */     double llx;
/*     */     double llx;
/* 159 */     if (this.unitScaling)
/* 160 */       llx = whichBar - offsetLeft;
/*     */     else
/* 162 */       llx = dat.x - offsetLeft;
/* 163 */     double urx = llx + this.clusterWidth * this.observationDelta;
/* 164 */     double lly = getBaseY(whichSet, whichBar);
/*     */     try {
/* 166 */       ury = lly + dat.y;
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException e)
/*     */     {
/*     */       double ury;
/*     */       return;
/*     */     }
/*     */     double ury;
/*     */     Gc myGc;
/*     */     Gc myGc;
/* 171 */     if (!individualColors)
/* 172 */       myGc = this.datasets[whichSet].gc;
/*     */     else
/* 174 */       myGc = dat.gc;
/* 175 */     if (lly < ury) {
/* 176 */       face[0] = this.dataXfm.point(llx, lly);
/* 177 */       face[1] = this.dataXfm.point(urx, ury);
/*     */     }
/*     */     else {
/* 180 */       face[0] = this.dataXfm.point(llx, ury);
/* 181 */       face[1] = this.dataXfm.point(urx, lly);
/*     */     }
/* 183 */     myGc.fillRect(g, face[0], face[1]);
/* 184 */     if ((this.useDisplayList) && (this.globals.useDisplayList))
/*     */     {
/* 186 */       this.globals.displayList.addRectangle(this.datasets[whichSet], face[0], face[1]);
/* 187 */       this.globals.displayList.addRectangle(dat, face[0], face[1]);
/*     */     }
/* 189 */     Color saveColor = myGc.fillColor;
/* 190 */     myGc.fillColor = saveColor.darker();
/* 191 */     Color saveSecondaryColor = myGc.getSecondaryFillColor();
/* 192 */     myGc.setSecondaryFillColor(saveSecondaryColor.darker());
/*     */ 
/* 194 */     int save = face[0].x;
/* 195 */     face[0].x = face[1].x;
/* 196 */     face[2] = new Point(face[1].x + this.globals.xOffset, face[1].y + this.globals.yOffset);
/* 197 */     face[3] = new Point(face[0].x + this.globals.xOffset, face[0].y + this.globals.yOffset);
/* 198 */     myGc.drawPolygon(g, face);
/*     */ 
/* 200 */     if (topBar(whichSet, whichBar)) {
/* 201 */       int style = myGc.fillStyle;
/* 202 */       if (style == 0) {
/* 203 */         myGc.fillStyle = -1;
/*     */       }
/* 205 */       face[0].x = save;
/* 206 */       face[0].y = face[1].y;
/*     */ 
/* 208 */       face[3].x = (face[0].x + this.globals.xOffset);
/* 209 */       face[3].y = (face[0].y + this.globals.yOffset);
/* 210 */       myGc.drawPolygon(g, face);
/* 211 */       myGc.fillStyle = style;
/*     */     }
/* 213 */     myGc.fillColor = saveColor;
/* 214 */     myGc.setSecondaryFillColor(saveSecondaryColor);
/*     */   }
/*     */ 
/*     */   protected double getBaseY(int whichSet, int whichBar)
/*     */   {
/* 225 */     double val = this.baseline;
/*     */ 
/* 227 */     if (this.datasets[whichSet].getDataElementAt(whichBar).y > this.baseline) {
/* 228 */       for (int i = 0; i < whichSet; i++)
/* 229 */         if (this.datasets[i].getDataElementAt(whichBar).y > this.baseline)
/* 230 */           val += this.datasets[i].getDataElementAt(whichBar).y;
/*     */     }
/*     */     else {
/* 233 */       for (int i = 0; i < whichSet; i++)
/* 234 */         if (this.datasets[i].getDataElementAt(whichBar).y < this.baseline)
/* 235 */           val += this.datasets[i].getDataElementAt(whichBar).y;
/*     */     }
/* 237 */     return val;
/*     */   }
/*     */ 
/*     */   protected boolean topBar(int whichSet, int whichBar)
/*     */   {
/* 247 */     int numDatasets = datasetsInUse() - 1;
/*     */ 
/* 249 */     if ((whichSet == numDatasets) && (this.datasets[whichSet].getDataElementAt(whichBar).y > this.baseline)) {
/* 250 */       return true;
/*     */     }
/*     */ 
/* 253 */     if (this.datasets[whichSet].getDataElementAt(whichBar).y == this.baseline)
/*     */     {
/* 256 */       int i = whichSet - 1;
/* 257 */       while (i >= 0) {
/* 258 */         if ((this.datasets[i].getDataElementAt(whichBar).y >= this.baseline) && 
/* 259 */           (this.baseline > 0.0D))
/* 260 */           return true;
/* 261 */         i--;
/*     */       }
/* 263 */       return false;
/*     */     }
/*     */ 
/* 268 */     int i = whichSet + 1;
/* 269 */     while (this.datasets[i] != null) {
/* 270 */       if (this.datasets[i].getDataElementAt(whichBar).y > this.baseline)
/* 271 */         return false;
/* 272 */       i++;
/*     */     }
/* 274 */     return true;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.StackColumn
 * JD-Core Version:    0.6.2
 */