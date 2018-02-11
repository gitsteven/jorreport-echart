/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ 
/*     */ public class StackBar extends StackColumn
/*     */ {
/*     */   public StackBar()
/*     */   {
/*     */   }
/*     */ 
/*     */   public StackBar(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*     */   {
/*  43 */     super(d, xAx, yAx, subplot);
/*     */   }
/*     */ 
/*     */   protected void doBars(Graphics g, boolean individualColors)
/*     */   {
/*     */     try
/*     */     {
/*     */       Gc testGc;
/*     */       Gc testGc;
/*  53 */       if (individualColors)
/*  54 */         testGc = this.datasets[0].getDataElementAt(0).getGc();
/*     */       else
/*  56 */         testGc = this.datasets[0].getGc();
/*  57 */       if (testGc.outlineFills) {
/*  58 */         g = g.create();
/*  59 */         g.translate(-1, 0);
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/*  64 */     super.doBars(g, individualColors);
/*     */   }
/*     */ 
/*     */   protected void buildDataXfm()
/*     */   {
/*  70 */     this.xAxisStart = this.xAxis.getAxisStart();
/*  71 */     this.xAxisEnd = this.xAxis.getAxisEnd();
/*  72 */     this.yAxisStart = this.yAxis.getAxisStart();
/*  73 */     this.yAxisEnd = this.yAxis.getAxisEnd();
/*     */ 
/*  75 */     this.dataXfm = new Transform(this.yAxisStart, this.xAxisStart, 
/*  76 */       this.yAxisEnd, this.xAxisEnd, 
/*  77 */       this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY), 
/*  78 */       this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY));
/*     */ 
/*  80 */     this.dataXfm.logXScaling = this.yAxis.getLogScaling();
/*  81 */     this.dataXfm.logYScaling = this.xAxis.getLogScaling();
/*     */   }
/*     */ 
/*     */   protected void doBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/*  95 */     double offsetDown = 0.5D * this.clusterWidth * this.observationDelta;
/*     */ 
/*  97 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */     double lly;
/*     */     double lly;
/*  98 */     if (this.unitScaling) {
/*  99 */       lly = whichBar - offsetDown;
/*     */     }
/*     */     else {
/* 102 */       lly = dat.x - offsetDown;
/*     */     }
/* 104 */     double ury = lly + this.clusterWidth * this.observationDelta;
/*     */ 
/* 106 */     double llx = getBaseY(whichSet, whichBar);
/* 107 */     double urx = llx + dat.y;
/*     */ 
/* 109 */     Point llP = this.dataXfm.point(llx, lly);
/* 110 */     if (whichSet == 0)
/* 111 */       llP.x += 1;
/* 112 */     Point urP = this.dataXfm.point(urx, ury);
/* 113 */     if (urP.y - llP.y < 1) {
/* 114 */       urP.translate(0, 1);
/*     */     }
/* 116 */     if (!individualColors)
/* 117 */       this.datasets[whichSet].gc.fillRect(g, llP, urP);
/*     */     else
/* 119 */       dat.gc.fillRect(g, llP, urP);
/* 120 */     if ((this.useDisplayList) && (this.globals.useDisplayList))
/*     */     {
/* 122 */       this.globals.displayList.addRectangle(this.datasets[whichSet], llP, urP);
/* 123 */       this.globals.displayList.addRectangle(dat, llP, urP);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doBarLabel(Graphics g, double howWide, int whichSet, int whichBar)
/*     */   {
/* 138 */     double offsetDown = 0.5D * this.clusterWidth;
/*     */ 
/* 154 */     double lly = this.datasets[whichSet].getDataElementAt(whichBar).x;
/* 155 */     double ury = lly;
/* 156 */     double llx = getBaseY(whichSet, whichBar);
/* 157 */     double urx = llx + this.datasets[whichSet].getDataElementAt(whichBar).y;
/*     */     String str;
/*     */     String str;
/* 160 */     if ((this.datasets[whichSet].getDataElementAt(whichBar).label != null) && (!this.useValueLabels)) {
/* 161 */       str = this.datasets[whichSet].getDataElementAt(whichBar).label;
/*     */     }
/*     */     else {
/* 164 */       if (this.datasets[whichSet].getDataElementAt(whichBar).y == 0.0D)
/* 165 */         return;
/* 166 */       str = formatLabel(this.datasets[whichSet].getDataElementAt(whichBar).y);
/*     */     }
/* 168 */     g.setFont(this.datasets[whichSet].labelFont);
/* 169 */     g.setColor(this.datasets[whichSet].labelColor);
/* 170 */     Point loc = this.dataXfm.point(llx + (urx - llx) / 2.0D, lly + (ury - lly) / 2.0D);
/* 171 */     FontMetrics fm = g.getFontMetrics();
/* 172 */     this.datasets[whichSet].gc.drawSmartString(g, loc.x, loc.y - fm.getMaxAscent() / 2, 2, this.labelAngle, fm, str);
/*     */   }
/*     */ 
/*     */   protected void doDBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/* 187 */     double offsetDown = 0.5D * this.clusterWidth;
/*     */ 
/* 193 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */     double lly;
/*     */     double lly;
/* 194 */     if (this.unitScaling) {
/* 195 */       lly = whichBar - offsetDown;
/*     */     }
/*     */     else {
/* 198 */       lly = dat.x - offsetDown;
/*     */     }
/* 200 */     double ury = lly + this.clusterWidth * this.observationDelta;
/*     */ 
/* 202 */     double llx = getBaseY(whichSet, whichBar);
/*     */     try {
/* 204 */       urx = llx + this.datasets[whichSet].getDataElementAt(whichBar).y;
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException e)
/*     */     {
/*     */       double urx;
/*     */       return;
/*     */     }
/*     */     double urx;
/*     */     Gc myGc;
/*     */     Gc myGc;
/* 210 */     if (!individualColors)
/* 211 */       myGc = this.datasets[whichSet].gc;
/*     */     else
/* 213 */       myGc = this.datasets[whichSet].getDataElementAt(whichBar).gc;
/* 214 */     Point[] face = new Point[4];
/* 215 */     if (urx > llx) {
/* 216 */       face[0] = this.dataXfm.point(llx, lly);
/* 217 */       face[1] = this.dataXfm.point(urx, ury);
/*     */     }
/*     */     else {
/* 220 */       face[0] = this.dataXfm.point(urx, lly);
/* 221 */       face[1] = this.dataXfm.point(llx, ury);
/*     */     }
/* 223 */     myGc.fillRect(g, face[0], face[1]);
/* 224 */     if ((this.useDisplayList) && (this.globals.useDisplayList))
/*     */     {
/* 226 */       this.globals.displayList.addRectangle(
/* 227 */         this.datasets[whichSet], 
/* 228 */         face[0], face[1]);
/* 229 */       this.globals.displayList.addRectangle(
/* 230 */         this.datasets[whichSet].getDataElementAt(whichBar), 
/* 231 */         face[0], face[1]);
/*     */     }
/*     */ 
/* 234 */     Color saveColor = myGc.fillColor;
/* 235 */     myGc.fillColor = saveColor.darker();
/* 236 */     Color saveSecondaryColor = myGc.getSecondaryFillColor();
/* 237 */     myGc.setSecondaryFillColor(saveSecondaryColor.darker());
/* 238 */     int save = face[0].y;
/* 239 */     face[0].y = face[1].y;
/* 240 */     face[2] = new Point(face[1].x + this.globals.xOffset, face[1].y + this.globals.yOffset);
/* 241 */     face[3] = new Point(face[0].x + this.globals.xOffset, face[0].y + this.globals.yOffset);
/* 242 */     myGc.drawPolygon(g, face);
/* 243 */     if (topBar(whichSet, whichBar)) {
/* 244 */       int style = myGc.fillStyle;
/* 245 */       if (style == 0) {
/* 246 */         myGc.fillStyle = -1;
/* 247 */         myGc.fillColor = saveSecondaryColor;
/*     */       }
/*     */ 
/* 250 */       face[0].x = face[1].x;
/* 251 */       face[0].y = save;
/* 252 */       face[2].x = (face[1].x + this.globals.xOffset);
/* 253 */       face[2].y = (face[1].y + this.globals.yOffset);
/* 254 */       face[3].x = (face[0].x + this.globals.xOffset);
/* 255 */       face[3].y = (face[0].y + this.globals.yOffset);
/* 256 */       myGc.drawPolygon(g, face);
/* 257 */       myGc.fillStyle = style;
/*     */     }
/* 259 */     myGc.fillColor = saveColor;
/* 260 */     myGc.setSecondaryFillColor(saveSecondaryColor);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.StackBar
 * JD-Core Version:    0.6.2
 */