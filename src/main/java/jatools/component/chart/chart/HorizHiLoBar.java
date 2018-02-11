/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ 
/*     */ public class HorizHiLoBar extends Bar
/*     */ {
/*  23 */   boolean clusterBars = true;
/*     */ 
/*     */   public HorizHiLoBar()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HorizHiLoBar(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*     */   {
/*  41 */     super(d, xAx, yAx, subplot);
/*     */   }
/*     */ 
/*     */   protected void buildDataXfm()
/*     */   {
/*  49 */     this.xAxisStart = this.xAxis.getAxisStart();
/*  50 */     this.xAxisEnd = this.xAxis.getAxisEnd();
/*  51 */     this.yAxisStart = this.yAxis.getAxisStart();
/*  52 */     this.yAxisEnd = this.yAxis.getAxisEnd();
/*     */ 
/*  54 */     this.dataXfm = new Transform(this.yAxisStart, this.xAxisStart, 
/*  55 */       this.yAxisEnd, this.xAxisEnd, 
/*  56 */       this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY), 
/*  57 */       this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY));
/*     */ 
/*  59 */     this.dataXfm.logXScaling = this.yAxis.getLogScaling();
/*  60 */     this.dataXfm.logYScaling = this.xAxis.getLogScaling();
/*     */   }
/*     */ 
/*     */   protected void doBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/*  74 */     double offsetDown = 0.5D * this.clusterWidth * this.observationDelta;
/*     */ 
/*  76 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*  77 */     if (!this.clusterBars)
/*  78 */       howWide *= datasetsInUse();
/*     */     double lly;
/*     */     double lly;
/*  79 */     if (this.unitScaling)
/*     */     {
/*     */       double lly;
/*  80 */       if (this.clusterBars)
/*  81 */         lly = whichBar - offsetDown + 
/*  82 */           howWide * whichSet;
/*     */       else
/*  84 */         lly = whichBar - offsetDown;
/*     */     }
/*     */     else
/*     */     {
/*     */       double lly;
/*  87 */       if (this.clusterBars)
/*  88 */         lly = dat.x - offsetDown + howWide * this.observationDelta * whichSet;
/*     */       else {
/*  90 */         lly = dat.x - offsetDown;
/*     */       }
/*     */     }
/*  93 */     double ury = lly + howWide;
/*  94 */     double urx = dat.y;
/*  95 */     double llx = dat.y2;
/*  96 */     Point p1 = this.dataXfm.point(llx, lly);
/*  97 */     Point p2 = this.dataXfm.point(urx, ury);
/*  98 */     if (p2.y - p1.y < 1) {
/*  99 */       p2.translate(0, 1);
/*     */     }
/*     */ 
/* 102 */     if (p2.x - p1.x > 1) {
/* 103 */       p2.translate(1, 0);
/*     */     }
/* 105 */     if (!individualColors)
/* 106 */       this.datasets[whichSet].gc.fillRect(g, p1, p2);
/*     */     else
/* 108 */       dat.gc.fillRect(g, p1, p2);
/* 109 */     if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/* 110 */       this.globals.displayList.addRectangle(this.datasets[whichSet], p1, p2);
/* 111 */       this.globals.displayList.addRectangle(dat, p1, p2);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doBarLabel(Graphics g, double howWide, int whichSet, int whichBar)
/*     */   {
/* 127 */     double offsetDown = 0.5D * this.clusterWidth * this.observationDelta;
/*     */ 
/* 131 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/* 132 */     if (!this.clusterBars)
/* 133 */       howWide *= datasetsInUse();
/*     */     double lly;
/*     */     double lly;
/* 134 */     if (this.unitScaling)
/*     */     {
/*     */       double lly;
/* 135 */       if (this.clusterBars)
/* 136 */         lly = whichBar - offsetDown + 
/* 137 */           howWide * whichSet;
/*     */       else
/* 139 */         lly = whichBar - offsetDown;
/*     */     }
/*     */     else
/*     */     {
/*     */       double lly;
/* 142 */       if (this.clusterBars)
/* 143 */         lly = dat.x - offsetDown + howWide * this.observationDelta * whichBar;
/*     */       else
/* 145 */         lly = dat.x - offsetDown;
/*     */     }
/* 147 */     double ury = lly + howWide;
/* 148 */     double urx = dat.y;
/* 149 */     double llx = dat.y2;
/*     */     String str;
/*     */     String str;
/* 152 */     if ((dat.label != null) && (!this.useValueLabels)) {
/* 153 */       str = dat.label;
/*     */     }
/*     */     else {
/* 156 */       str = formatLabel(dat.y);
/*     */     }
/* 158 */     g.setFont(this.datasets[whichSet].labelFont);
/* 159 */     g.setColor(this.datasets[whichSet].labelColor);
/* 160 */     FontMetrics fm = g.getFontMetrics();
/* 161 */     Point loc = this.dataXfm.point(llx + (urx - llx) / 2.0D, lly + (ury - lly) / 2.0D);
/* 162 */     loc.translate(0, 0 - fm.getMaxAscent() / 2);
/* 163 */     this.datasets[whichSet].gc.drawSmartString(g, loc.x, loc.y, 2, this.labelAngle, fm, str);
/*     */   }
/*     */ 
/*     */   protected void doDBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/* 178 */     double offsetDown = 0.5D * this.clusterWidth * this.observationDelta;
/*     */ 
/* 182 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/* 183 */     if (!this.clusterBars)
/* 184 */       howWide *= datasetsInUse();
/* 185 */     Point[] face = new Point[4];
/*     */     double lly;
/*     */     double lly;
/* 186 */     if (this.unitScaling)
/*     */     {
/*     */       double lly;
/* 187 */       if (this.clusterBars)
/* 188 */         lly = whichBar - offsetDown + 
/* 189 */           howWide * whichSet;
/*     */       else
/* 191 */         lly = whichBar - offsetDown;
/*     */     }
/*     */     else
/*     */     {
/*     */       double lly;
/* 194 */       if (this.clusterBars)
/* 195 */         lly = dat.x - offsetDown + howWide * this.observationDelta * whichSet;
/*     */       else {
/* 197 */         lly = dat.x - offsetDown;
/*     */       }
/*     */     }
/* 200 */     double ury = lly + howWide;
/* 201 */     double llx = dat.y;
/* 202 */     double urx = dat.y2;
/* 203 */     face[0] = this.dataXfm.point(llx, lly);
/* 204 */     face[1] = this.dataXfm.point(urx, ury);
/*     */     Gc myGc;
/*     */     Gc myGc;
/* 205 */     if (!individualColors)
/* 206 */       myGc = this.datasets[whichSet].gc;
/*     */     else
/* 208 */       myGc = dat.gc;
/* 209 */     myGc.fillRect(g, face[0], face[1]);
/* 210 */     if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/* 211 */       this.globals.displayList.addRectangle(
/* 212 */         this.datasets[whichSet], 
/* 213 */         face[0], face[1]);
/* 214 */       this.globals.displayList.addRectangle(
/* 215 */         this.datasets[whichSet].getDataElementAt(whichBar), 
/* 216 */         face[0], face[1]);
/*     */     }
/* 218 */     Color saveColor = myGc.fillColor;
/* 219 */     myGc.fillColor = saveColor.darker();
/* 220 */     Color saveSecondaryColor = myGc.getSecondaryFillColor();
/* 221 */     myGc.setSecondaryFillColor(saveSecondaryColor.darker());
/*     */ 
/* 223 */     int style = myGc.getFillStyle();
/* 224 */     if (style == 0) {
/* 225 */       myGc.setFillStyle(-1);
/* 226 */       myGc.fillColor = myGc.getSecondaryFillColor();
/*     */     }
/*     */ 
/* 229 */     face[0] = this.dataXfm.point(urx, lly);
/*     */ 
/* 231 */     face[2] = new Point(face[1].x + this.globals.xOffset, face[1].y + this.globals.yOffset);
/* 232 */     face[3] = new Point(face[0].x + this.globals.xOffset, face[0].y + this.globals.yOffset);
/* 233 */     myGc.drawPolygon(g, face);
/* 234 */     myGc.setFillStyle(style);
/* 235 */     if (style == 0) {
/* 236 */       myGc.fillColor = saveColor.darker();
/*     */     }
/*     */ 
/* 241 */     if (ury > lly) {
/* 242 */       face[0] = this.dataXfm.point(llx, ury);
/*     */ 
/* 244 */       face[3].x = (face[0].x + this.globals.xOffset);
/* 245 */       face[3].y = (face[0].y + this.globals.yOffset);
/* 246 */       myGc.drawPolygon(g, face);
/*     */     }
/*     */     else {
/* 249 */       face[0] = this.dataXfm.point(llx, lly);
/* 250 */       face[1] = this.dataXfm.point(urx, lly);
/*     */ 
/* 252 */       face[2].x = (face[1].x + this.globals.xOffset);
/* 253 */       face[2].y = (face[1].y + this.globals.yOffset);
/* 254 */       face[3].x = (face[0].x + this.globals.xOffset);
/* 255 */       face[3].y = (face[0].y + this.globals.yOffset);
/* 256 */       myGc.drawPolygon(g, face);
/*     */     }
/*     */ 
/* 259 */     myGc.fillColor = saveColor;
/* 260 */     myGc.setSecondaryFillColor(saveSecondaryColor);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.HorizHiLoBar
 * JD-Core Version:    0.6.2
 */