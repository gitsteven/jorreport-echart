/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ 
/*     */ public class GanttBar extends Bar
/*     */ {
/*  22 */   boolean clusterBars = true;
/*  23 */   int discontinuityPixels = 20;
/*     */ 
/*     */   public GanttBar()
/*     */   {
/*     */   }
/*     */ 
/*     */   public GanttBar(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*     */   {
/*  41 */     super(d, xAx, yAx, subplot);
/*     */   }
/*     */ 
/*     */   protected void buildDataXfm()
/*     */   {
/*  49 */     double xAxisStart = this.xAxis.getAxisStart();
/*  50 */     double xAxisEnd = this.xAxis.getAxisEnd();
/*  51 */     double yAxisStart = this.yAxis.getAxisStart();
/*  52 */     double yAxisEnd = this.yAxis.getAxisEnd();
/*     */ 
/*  54 */     Point ll = new Point(
/*  55 */       (int)(this.globals.getMaxX() * this.plotarea.getLlX()), 
/*  56 */       (int)(this.globals.getMaxY() * this.plotarea.getLlY()));
/*     */ 
/*  58 */     Point ur = new Point(
/*  59 */       (int)(this.globals.getMaxX() * this.plotarea.getUrX()), 
/*  60 */       (int)(this.globals.getMaxY() * this.plotarea.getUrY()));
/*     */ 
/*  62 */     this.dataXfm = new Transform(yAxisStart, xAxisStart, 
/*  63 */       yAxisEnd, xAxisEnd, ll, ur);
/*     */   }
/*     */ 
/*     */   protected void doBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/*  78 */     double offsetDown = 0.5D * this.clusterWidth;
/*     */ 
/*  80 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */ 
/*  82 */     double lly = whichSet - offsetDown;
/*     */ 
/*  84 */     double ury = lly + this.clusterWidth;
/*  85 */     double urx = dat.getY();
/*  86 */     double llx = dat.getY2();
/*  87 */     Point p1 = this.dataXfm.point(llx, lly);
/*  88 */     Point p2 = this.dataXfm.point(urx, ury);
/*     */ 
/*  90 */     if (urx < 0.0D) {
/*  91 */       p1.x += this.discontinuityPixels;
/*     */     }
/*     */ 
/*  94 */     if (p2.y - p1.y < 1) {
/*  95 */       p2.translate(0, 1);
/*     */     }
/*     */ 
/*  98 */     if (urx < 0.0D) {
/*  99 */       if (individualColors)
/* 100 */         drawDiscontinuityBar(g, dat.getGc(), p1, p2);
/*     */       else {
/* 102 */         drawDiscontinuityBar(g, this.datasets[whichSet].getGc(), p1, p2);
/*     */       }
/*     */     }
/* 105 */     else if (!individualColors)
/* 106 */       this.datasets[whichSet].getGc().fillRect(g, p1, p2);
/*     */     else {
/* 108 */       dat.getGc().fillRect(g, p1, p2);
/*     */     }
/* 110 */     if ((this.useDisplayList) && (this.globals.getUseDisplayList())) {
/* 111 */       this.globals.getDisplayList().addRectangle(this.datasets[whichSet], p1, p2);
/* 112 */       this.globals.getDisplayList().addRectangle(dat, p1, p2);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void drawDiscontinuityBar(Graphics g, Gc gc, Point p1, Point p2)
/*     */   {
/* 124 */     int jagDistance = (p2.y - p1.y) / 5;
/* 125 */     int jagDepth = 2;
/*     */ 
/* 127 */     Point[] poly = new Point[8];
/* 128 */     poly[0] = p1;
/* 129 */     poly[1] = new Point(p1.x, p2.y);
/* 130 */     poly[2] = p2;
/* 131 */     int yVal = p2.y - jagDistance;
/* 132 */     poly[3] = new Point(p2.x - jagDepth, yVal);
/* 133 */     yVal -= jagDistance;
/* 134 */     poly[4] = new Point(p2.x + jagDepth, yVal);
/* 135 */     yVal -= jagDistance;
/* 136 */     poly[5] = new Point(p2.x - jagDepth, yVal);
/* 137 */     yVal -= jagDistance;
/* 138 */     poly[6] = new Point(p2.x + jagDepth, yVal);
/* 139 */     poly[7] = new Point(p2.x, p1.y);
/* 140 */     gc.drawPolygon(g, poly);
/*     */   }
/*     */ 
/*     */   protected void doBarLabel(Graphics g, double howWide, int whichSet, int whichBar)
/*     */   {
/* 152 */     double offsetDown = 0.5D * this.clusterWidth;
/* 153 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */ 
/* 155 */     double lly = whichSet - offsetDown;
/*     */ 
/* 157 */     double ury = lly + this.clusterWidth;
/* 158 */     double urx = dat.getY();
/* 159 */     double llx = dat.getY2();
/* 160 */     Point p1 = this.dataXfm.point(llx, lly);
/* 161 */     Point p2 = this.dataXfm.point(urx, ury);
/*     */ 
/* 163 */     if (urx < 0.0D) {
/* 164 */       p1.x += this.discontinuityPixels;
/*     */     }
/*     */ 
/* 167 */     if (p2.y - p1.y < 1)
/* 168 */       p2.translate(0, 1);
/*     */     String str;
/*     */     String str;
/* 174 */     if ((dat.getLabel() != null) && (!this.useValueLabels)) {
/* 175 */       str = dat.getLabel();
/*     */     }
/*     */     else {
/* 178 */       str = formatLabel(dat.getY());
/*     */     }
/* 180 */     g.setFont(this.datasets[whichSet].getLabelFont());
/* 181 */     FontMetrics fm = g.getFontMetrics();
/* 182 */     Point loc = this.dataXfm.point(llx + (urx - llx) / 2.0D, lly + (ury - lly) / 2.0D);
/* 183 */     loc.translate(0, 0 - fm.getMaxAscent() / 2);
/* 184 */     g.setColor(this.datasets[whichSet].getLabelColor());
/* 185 */     this.datasets[whichSet].getGc().drawSmartString(g, loc.x, loc.y, 2, this.labelAngle, fm, str);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.GanttBar
 * JD-Core Version:    0.6.2
 */