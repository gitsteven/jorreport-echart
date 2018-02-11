/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ 
/*     */ public class HiLoBar extends Bar
/*     */ {
/*     */   public HiLoBar()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HiLoBar(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*     */   {
/*  34 */     super(d, xAx, yAx, subplot);
/*     */   }
/*     */ 
/*     */   protected void doBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/*  48 */     double offsetLeft = 0.5D * this.clusterWidth * this.observationDelta;
/*     */ 
/*  50 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */     double llx;
/*     */     double llx;
/*  51 */     if (this.unitScaling) {
/*  52 */       llx = whichBar - offsetLeft + 
/*  53 */         howWide * whichSet;
/*     */     }
/*     */     else {
/*  56 */       llx = dat.x - offsetLeft + 
/*  57 */         howWide * this.observationDelta * whichSet;
/*     */     }
/*  59 */     double urx = llx + howWide * this.observationDelta;
/*  60 */     double ury = dat.y;
/*  61 */     double lly = dat.y2;
/*  62 */     Point p1 = this.dataXfm.point(llx, lly);
/*  63 */     Point p2 = this.dataXfm.point(urx, ury);
/*  64 */     if (p2.x - p1.x < 1)
/*  65 */       p2.translate(1, 0);
/*  66 */     if (!individualColors)
/*  67 */       this.datasets[whichSet].gc.fillRect(g, p1, p2);
/*     */     else
/*  69 */       dat.gc.fillRect(g, p1, p2);
/*  70 */     if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/*  71 */       this.globals.displayList.addRectangle(this.datasets[whichSet], p1, p2);
/*  72 */       this.globals.displayList.addRectangle(dat, p1, p2);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doBarLabel(Graphics g, double howWide, int whichSet, int whichBar)
/*     */   {
/*  87 */     double offsetLeft = 0.5D * this.clusterWidth * this.observationDelta;
/*     */ 
/*  91 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */     double llx;
/*     */     double llx;
/*  92 */     if (this.unitScaling) {
/*  93 */       llx = whichBar - offsetLeft + 
/*  94 */         howWide * whichSet;
/*     */     }
/*     */     else {
/*  97 */       llx = dat.x - offsetLeft + 
/*  98 */         howWide * this.observationDelta * whichSet;
/*     */     }
/*     */ 
/* 101 */     double urx = llx + howWide * this.observationDelta;
/* 102 */     double ury = dat.y;
/*     */     String str;
/*     */     String str;
/* 105 */     if ((dat.label != null) && (!this.useValueLabels)) {
/* 106 */       str = dat.label;
/*     */     }
/*     */     else {
/* 109 */       str = formatLabel(dat.y);
/*     */     }
/* 111 */     g.setFont(this.datasets[whichSet].labelFont);
/* 112 */     g.setColor(this.datasets[whichSet].labelColor);
/* 113 */     FontMetrics fm = g.getFontMetrics();
/* 114 */     Point loc = this.dataXfm.point(llx + (urx - llx) / 2.0D, ury);
/* 115 */     this.datasets[whichSet].gc.drawSmartString(g, loc.x, loc.y, 2, this.labelAngle, fm, str);
/*     */   }
/*     */ 
/*     */   protected void doDBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/* 131 */     double offsetLeft = 0.5D * this.clusterWidth * this.observationDelta;
/*     */ 
/* 135 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */ 
/* 137 */     Point[] face = new Point[4];
/*     */     double llx;
/*     */     double llx;
/* 138 */     if (this.unitScaling) {
/* 139 */       llx = whichBar - offsetLeft + 
/* 140 */         howWide * whichSet;
/*     */     }
/*     */     else {
/* 143 */       llx = dat.x - offsetLeft + 
/* 144 */         howWide * this.observationDelta * whichSet;
/*     */     }
/*     */ 
/* 147 */     double urx = llx + howWide * this.observationDelta;
/* 148 */     double ury = dat.y;
/* 149 */     double lly = dat.y2;
/* 150 */     face[0] = this.dataXfm.point(llx, lly);
/* 151 */     face[1] = this.dataXfm.point(urx, ury);
/*     */     Gc myGc;
/*     */     try
/*     */     {
/*     */       Gc myGc;
/* 153 */       if (!individualColors)
/* 154 */         myGc = this.datasets[whichSet].gc;
/*     */       else
/* 156 */         myGc = dat.gc;
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException e)
/*     */     {
/*     */       Gc myGc;
/* 159 */       myGc = this.datasets[whichSet].gc;
/*     */     }
/* 161 */     Point ur = new Point(face[1].x, face[1].y);
/* 162 */     myGc.fillRect(g, face[0], face[1]);
/* 163 */     if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/* 164 */       this.globals.displayList.addRectangle(this.datasets[whichSet], face[0], face[1]);
/* 165 */       this.globals.displayList.addRectangle(dat, face[0], face[1]);
/*     */     }
/* 167 */     Color saveColor = myGc.fillColor;
/* 168 */     myGc.fillColor = saveColor.darker();
/* 169 */     Color saveSecondaryColor = myGc.getSecondaryFillColor();
/* 170 */     myGc.setSecondaryFillColor(saveSecondaryColor.darker());
/*     */ 
/* 172 */     face[0] = this.dataXfm.point(urx, lly);
/*     */ 
/* 174 */     face[1] = ur;
/* 175 */     face[2] = new Point(face[1].x + this.globals.xOffset, face[1].y + this.globals.yOffset);
/* 176 */     face[3] = new Point(face[0].x + this.globals.xOffset, face[0].y + this.globals.yOffset);
/* 177 */     myGc.drawPolygon(g, face);
/*     */ 
/* 180 */     int style = myGc.fillStyle;
/* 181 */     if (style == 0)
/* 182 */       myGc.fillStyle = -1;
/* 183 */     if (ury > lly) {
/* 184 */       face[0] = this.dataXfm.point(llx, ury);
/*     */ 
/* 186 */       face[3].x = (face[0].x + this.globals.xOffset);
/* 187 */       face[3].y = (face[0].y + this.globals.yOffset);
/* 188 */       myGc.drawPolygon(g, face);
/*     */     }
/*     */     else {
/* 191 */       face[0] = this.dataXfm.point(llx, lly);
/* 192 */       face[1] = this.dataXfm.point(urx, lly);
/*     */ 
/* 194 */       face[2].x = (face[1].x + this.globals.xOffset);
/* 195 */       face[2].y = (face[1].y + this.globals.yOffset);
/* 196 */       face[3].x = (face[0].x + this.globals.xOffset);
/* 197 */       face[3].y = (face[0].y + this.globals.yOffset);
/* 198 */       myGc.drawPolygon(g, face);
/*     */     }
/* 200 */     myGc.fillStyle = style;
/* 201 */     myGc.fillColor = saveColor;
/* 202 */     myGc.setSecondaryFillColor(saveSecondaryColor);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.HiLoBar
 * JD-Core Version:    0.6.2
 */