/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ 
/*     */ public class HorizBar extends Bar
/*     */ {
/*     */   public HorizBar()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HorizBar(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*     */   {
/*  37 */     super(d, xAx, yAx, subplot);
/*     */   }
/*     */ 
/*     */   protected void doBars(Graphics g, boolean individualColors)
/*     */   {
/*     */     try
/*     */     {
/*     */       Gc testGc;
/*     */       Gc testGc;
/*  48 */       if (individualColors)
/*  49 */         testGc = this.datasets[0].getDataElementAt(0).getGc();
/*     */       else
/*  51 */         testGc = this.datasets[0].getGc();
/*  52 */       if (testGc.outlineFills) {
/*  53 */         g = g.create();
/*  54 */         g.translate(-1, 0);
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/*  59 */     super.doBars(g, individualColors);
/*     */   }
/*     */ 
/*     */   protected void buildDataXfm()
/*     */   {
/*  66 */     this.xAxisStart = this.xAxis.getAxisStart();
/*  67 */     this.xAxisEnd = this.xAxis.getAxisEnd();
/*  68 */     this.yAxisStart = this.yAxis.getAxisStart();
/*  69 */     this.yAxisEnd = this.yAxis.getAxisEnd();
/*     */ 
/*  71 */     this.dataXfm = new Transform(this.yAxisStart, this.xAxisStart, 
/*  72 */       this.yAxisEnd, this.xAxisEnd, 
/*  73 */       this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY), 
/*  74 */       this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY));
/*     */ 
/*  76 */     this.dataXfm.logXScaling = this.yAxis.getLogScaling();
/*  77 */     this.dataXfm.logYScaling = this.xAxis.getLogScaling();
/*     */   }
/*     */ 
/*     */   protected void doBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/*  91 */     double offsetDown = 0.5D * this.clusterWidth * this.observationDelta;
/*     */ 
/*  93 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */     double lly;
/*     */     double lly;
/*  95 */     if (this.unitScaling) {
/*  96 */       lly = whichBar - offsetDown + 
/*  97 */         howWide * whichSet;
/*     */     }
/*     */     else {
/* 100 */       lly = dat.x - offsetDown + 
/* 101 */         howWide * this.observationDelta * whichSet;
/*     */     }
/* 103 */     double ury = lly + howWide * this.observationDelta;
/*     */ 
/* 105 */     double llx = this.baseline;
/* 106 */     double urx = dat.y;
/* 107 */     Point llP = this.dataXfm.point(llx, lly);
/* 108 */     llP.x += 1;
/* 109 */     Point urP = this.dataXfm.point(urx, ury);
/* 110 */     if (urP.y - llP.y < 1) {
/* 111 */       urP.translate(0, 1);
/*     */     }
/*     */ 
/* 114 */     if ((urP.x - llP.x < 1) && (urx != llx)) {
/* 115 */       urP.translate(2, 0);
/*     */     }
/* 117 */     if (!individualColors)
/* 118 */       this.datasets[whichSet].gc.fillRect(g, llP, urP);
/*     */     else
/* 120 */       dat.gc.fillRect(g, llP, urP);
/* 121 */     if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/* 122 */       this.globals.displayList.addRectangle(this.datasets[whichSet], llP, urP);
/* 123 */       this.globals.displayList.addRectangle(dat, llP, urP);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doBarLabel(Graphics g, double howWide, int whichSet, int whichBar)
/*     */   {
/* 138 */     double offsetDown = 0.5D * this.clusterWidth * this.observationDelta;
/*     */ 
/* 143 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */     double lly;
/*     */     double lly;
/* 144 */     if (this.unitScaling) {
/* 145 */       lly = whichBar - offsetDown + 
/* 146 */         howWide * whichSet;
/*     */     }
/*     */     else {
/* 149 */       lly = dat.x - offsetDown + howWide * this.observationDelta * whichSet;
/*     */     }
/* 151 */     double ury = lly + howWide * this.observationDelta;
/* 152 */     double urx = dat.y;
/*     */     String str;
/*     */     String str;
/* 155 */     if ((dat.label != null) && (!this.useValueLabels)) {
/* 156 */       str = dat.label;
/*     */     }
/*     */     else {
/* 159 */       str = formatLabel(dat.y);
/*     */     }
/*     */ 
/* 162 */     g.setFont(this.datasets[whichSet].labelFont);
/* 163 */     g.setColor(this.datasets[whichSet].labelColor);
/* 164 */     FontMetrics fm = g.getFontMetrics();
/* 165 */     Point loc = this.dataXfm.point(urx, lly + (ury - lly) / 2.0D);
/*     */ 
/* 167 */     if (dat.y < this.baseline) {
/* 168 */       this.datasets[whichSet].gc.drawSmartString(g, loc.x - 3, loc.y, 
/* 169 */         1, this.labelAngle, fm, str);
/*     */     }
/*     */     else
/* 172 */       this.datasets[whichSet].gc.drawSmartString(g, loc.x + 3, loc.y, 
/* 173 */         3, this.labelAngle, fm, str);
/*     */   }
/*     */ 
/*     */   protected void doDBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/* 188 */     double offsetLeft = 0.5D * this.clusterWidth * this.observationDelta;
/*     */ 
/* 193 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/* 194 */     Point[] face = new Point[4];
/*     */     double lly;
/*     */     double lly;
/* 195 */     if (this.unitScaling) {
/* 196 */       lly = whichBar - offsetLeft + 
/* 197 */         howWide * whichSet;
/*     */     }
/*     */     else {
/* 200 */       lly = dat.x - offsetLeft + howWide * this.observationDelta * whichSet;
/*     */     }
/* 202 */     double ury = lly + howWide * this.observationDelta;
/* 203 */     double urx = dat.y;
/* 204 */     double llx = this.baseline;
/* 205 */     face[0] = this.dataXfm.point(llx, lly);
/* 206 */     face[0].x += 1;
/* 207 */     face[1] = this.dataXfm.point(urx, ury);
/*     */     Gc myGc;
/*     */     Gc myGc;
/* 208 */     if (!individualColors)
/* 209 */       myGc = this.datasets[whichSet].gc;
/*     */     else
/* 211 */       myGc = dat.gc;
/* 212 */     myGc.fillRect(g, face[0], face[1]);
/* 213 */     if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/* 214 */       this.globals.displayList.addRectangle(this.datasets[whichSet], face[0], face[1]);
/* 215 */       this.globals.displayList.addRectangle(dat, face[0], face[1]);
/*     */     }
/*     */ 
/* 218 */     Color saveColor = myGc.fillColor;
/* 219 */     myGc.fillColor = saveColor.darker();
/* 220 */     Color saveSecondaryColor = myGc.getSecondaryFillColor();
/* 221 */     myGc.setSecondaryFillColor(saveSecondaryColor.darker());
/*     */ 
/* 223 */     face[0] = this.dataXfm.point(llx, ury);
/*     */ 
/* 225 */     face[2] = new Point(face[1].x + this.globals.xOffset, face[1].y + this.globals.yOffset);
/* 226 */     face[3] = new Point(face[0].x + this.globals.xOffset, face[0].y + this.globals.yOffset);
/* 227 */     myGc.drawPolygon(g, face);
/*     */ 
/* 229 */     int style = myGc.fillStyle;
/* 230 */     if (style == 0) {
/* 231 */       myGc.fillStyle = -1;
/* 232 */       myGc.fillColor = saveSecondaryColor;
/*     */     }
/*     */ 
/* 235 */     if (urx > llx) {
/* 236 */       face[0] = this.dataXfm.point(urx, lly);
/*     */ 
/* 238 */       face[3].x = (face[0].x + this.globals.xOffset);
/* 239 */       face[3].y = (face[0].y + this.globals.yOffset);
/* 240 */       myGc.drawPolygon(g, face);
/*     */     }
/*     */     else {
/* 243 */       face[0] = this.dataXfm.point(llx, lly);
/* 244 */       face[1] = this.dataXfm.point(llx, ury);
/* 245 */       face[2].x = (face[1].x + this.globals.xOffset);
/* 246 */       face[2].y = (face[1].y + this.globals.yOffset);
/* 247 */       face[3].x = (face[0].x + this.globals.xOffset);
/* 248 */       face[3].y = (face[0].y + this.globals.yOffset);
/* 249 */       myGc.drawPolygon(g, face);
/*     */     }
/* 251 */     myGc.fillStyle = style;
/* 252 */     myGc.fillColor = saveColor;
/* 253 */     myGc.setSecondaryFillColor(saveSecondaryColor);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.HorizBar
 * JD-Core Version:    0.6.2
 */