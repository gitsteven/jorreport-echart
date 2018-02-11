/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class PolarAxis extends Axis
/*     */   implements AxisInterface
/*     */ {
/*     */   double interval;
/*     */   double centerX;
/*     */   double centerY;
/*     */   Plotarea fullPlotarea;
/*     */   Plotarea qtrPlotarea;
/*     */   private int numSpoke;
/*     */   public Point center;
/*     */   Transform dataXfm;
/*     */   ArrayList labelList;
/*  28 */   protected boolean manualSpoking = false;
/*     */ 
/*     */   public PolarAxis(Dataset[] dsets, boolean xAxis, Plotarea plt)
/*     */   {
/*  33 */     super(dsets, false, plt);
/*     */   }
/*     */ 
/*     */   public void addLabels(String[] str)
/*     */   {
/*  42 */     replaceLabels(str);
/*     */   }
/*     */ 
/*     */   private void doTitle(Graphics g, int spoke, Point center, Point edge) {
/*     */     try {
/*  47 */       s = (String)this.labelList.get(spoke % this.labelList.size());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       String s;
/*     */       return;
/*     */     }
/*     */     String s;
/*  51 */     int leftRight = edge.x - center.x;
/*  52 */     int topBottom = edge.y - center.y;
/*     */     int align;
/*  54 */     if (leftRight == 0)
/*     */     {
/*     */       int align;
/*  55 */       if (topBottom < 0)
/*  56 */         align = 0;
/*     */       else
/*  58 */         align = 2;
/*     */     }
/*  60 */     else if (topBottom == 0)
/*     */     {
/*     */       int align;
/*  61 */       if (leftRight < 0)
/*  62 */         align = 1;
/*     */       else
/*  64 */         align = 3;
/*     */     }
/*     */     int align;
/*  66 */     if (topBottom > 0) {
/*  67 */       int align = 2;
/*  68 */       edge.translate(0, 3);
/*     */     }
/*     */     else {
/*  71 */       align = 0;
/*  72 */       edge.translate(0, -3);
/*     */     }
/*  74 */     g.setFont(this.titleFont);
/*  75 */     g.setColor(this.titleColor);
/*  76 */     this.lineGc.drawSmartString(g, edge.x, edge.y, align, 0, g.getFontMetrics(), s);
/*     */   }
/*     */ 
/*     */   public synchronized void draw(Graphics g)
/*     */   {
/*  87 */     double hCircleSize = this.plotarea.urX - this.plotarea.llX;
/*  88 */     double vCircleSize = this.plotarea.urY - this.plotarea.llY;
/*  89 */     this.centerX = (this.plotarea.llX + hCircleSize / 2.0D);
/*  90 */     this.centerY = (this.plotarea.llY + vCircleSize / 2.0D);
/*  91 */     this.center = this.plotarea.transform.point(this.centerX, this.centerY);
/*     */ 
/*  93 */     quarterPlotarea();
/*  94 */     super.draw(g);
/*  95 */     Point[] originalAxis = getPoints();
/*  96 */     int numPoints = originalAxis.length;
/*     */ 
/*  98 */     this.plotarea = this.fullPlotarea;
/*  99 */     if (!this.manualSpoking) {
/* 100 */       setInterval();
/*     */     }
/* 102 */     for (int i = 1; i <= this.numSpoke; i++) {
/* 103 */       Point[] rotatedAxis = rotateAxis(originalAxis, i * this.interval);
/*     */ 
/* 105 */       this.lineGc.drawLine(g, 
/* 106 */         rotatedAxis[0].x, rotatedAxis[0].y, 
/* 107 */         rotatedAxis[1].x, rotatedAxis[1].y);
/*     */ 
/* 109 */       for (int j = 2; j < numPoints; j++) {
/* 110 */         this.tickGc.drawLine(g, 
/* 111 */           rotatedAxis[j].x, rotatedAxis[(j++)].y, 
/* 112 */           rotatedAxis[j].x, rotatedAxis[j].y);
/*     */       }
/* 114 */       if (this.labelList != null)
/* 115 */         doTitle(g, i, rotatedAxis[0], rotatedAxis[1]);
/*     */     }
/*     */   }
/*     */ 
/*     */   public double getInterval()
/*     */   {
/* 123 */     return this.interval;
/*     */   }
/*     */ 
/*     */   public boolean getManualSpoking()
/*     */   {
/* 130 */     return this.manualSpoking;
/*     */   }
/*     */ 
/*     */   public int getNumSpokes()
/*     */   {
/* 136 */     return this.numSpoke;
/*     */   }
/*     */ 
/*     */   private Point[] getPoints()
/*     */   {
/* 143 */     ArrayList axis = new ArrayList();
/*     */ 
/* 145 */     Point start = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY);
/* 146 */     Point end = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.urY);
/* 147 */     this.startPoint = start;
/*     */ 
/* 149 */     axis.add(0, start);
/* 150 */     axis.add(1, end);
/*     */ 
/* 152 */     int tickBase = start.x;
/*     */ 
/* 154 */     if (this.majTickVis) {
/* 155 */       this.increment = getIncrement(end.y, start.y, this.numMajTicks);
/* 156 */       for (int i = 1; i < this.numMajTicks; i++) {
/* 157 */         int y = whereOnAxis(i, 4);
/* 158 */         axis.add(new Point(tickBase, y));
/* 159 */         axis.add(new Point(tickBase - this.majTickLength, y));
/*     */       }
/* 161 */       axis.add(new Point(tickBase, end.y));
/* 162 */       axis.add(new Point(tickBase - this.majTickLength, end.y));
/*     */     }
/*     */ 
/* 165 */     if (this.minTickVis) {
/* 166 */       this.increment = getIncrement(end.y, start.y, this.numMinTicks);
/* 167 */       for (int i = 0; i < this.numMinTicks; i++) {
/* 168 */         int y = whereOnAxis(i, 3);
/* 169 */         axis.add(new Point(tickBase, y));
/* 170 */         axis.add(new Point(tickBase - this.minTickLength, y));
/*     */       }
/* 172 */       axis.add(new Point(tickBase, end.y));
/* 173 */       axis.add(new Point(tickBase - this.minTickLength, end.y));
/*     */     }
/*     */ 
/* 176 */     Point[] array = new Point[axis.size()];
/* 177 */     for (int i = 0; i < axis.size(); i++) {
/* 178 */       array[i] = ((Point)axis.get(i));
/*     */     }
/*     */ 
/* 181 */     return array;
/*     */   }
/*     */ 
/*     */   protected void quarterPlotarea()
/*     */   {
/* 186 */     this.fullPlotarea = this.plotarea;
/*     */ 
/* 188 */     this.plotarea = new Plotarea(this.fullPlotarea.globals);
/* 189 */     this.plotarea.setLlX(this.centerX);
/* 190 */     this.plotarea.setLlY(this.centerY);
/* 191 */     this.plotarea.setUrX(this.fullPlotarea.getUrX());
/* 192 */     this.plotarea.setUrY(this.fullPlotarea.getUrY());
/* 193 */     this.plotarea.setGc(this.fullPlotarea.getGc());
/* 194 */     this.plotarea.setUseDisplayList(this.fullPlotarea.getUseDisplayList());
/* 195 */     this.plotarea.resize(this.globals.maxX, this.globals.maxY);
/*     */ 
/* 197 */     this.qtrPlotarea = this.plotarea;
/*     */   }
/*     */ 
/*     */   public synchronized void replaceLabels(String[] str)
/*     */   {
/* 207 */     this.labelList = new ArrayList();
/*     */ 
/* 209 */     for (int i = 0; i < str.length; i++)
/* 210 */       this.labelList.add(str[i]);
/*     */   }
/*     */ 
/*     */   private Point[] rotateAxis(Point[] oldAxis, double angle)
/*     */   {
/* 218 */     Point[] newAxis = new Point[oldAxis.length];
/*     */ 
/* 220 */     double cosine = Math.cos(3.141592653589793D * angle / 180.0D);
/* 221 */     double sine = Math.sin(3.141592653589793D * angle / 180.0D);
/*     */ 
/* 223 */     for (int i = 0; i < oldAxis.length; i++) {
/* 224 */       double oldx = oldAxis[i].x - this.center.x;
/* 225 */       double oldy = oldAxis[i].y - this.center.y;
/*     */ 
/* 227 */       double newx = oldx * cosine - oldy * sine + this.center.x;
/* 228 */       double newy = oldx * sine + oldy * cosine + this.center.y;
/*     */ 
/* 230 */       newAxis[i] = new Point((int)newx, (int)newy);
/*     */     }
/* 232 */     return newAxis;
/*     */   }
/*     */   private void setInterval() {
/* 235 */     setNumSpokes(this.datasets);
/* 236 */     this.interval = (360.0D / this.numSpoke);
/*     */   }
/*     */ 
/*     */   public void setManualSpoking(boolean manual)
/*     */   {
/* 243 */     this.manualSpoking = manual;
/*     */   }
/*     */   private void setNumSpokes(Dataset[] datasets) {
/* 246 */     int spoke = 0;
/*     */ 
/* 248 */     int length = datasetsInUse();
/*     */ 
/* 250 */     for (int i = 0; i < length; i++) {
/* 251 */       if (spoke < datasets[i].data.size()) {
/* 252 */         spoke = datasets[i].data.size();
/*     */       }
/*     */     }
/* 255 */     this.numSpoke = spoke;
/*     */   }
/*     */ 
/*     */   public void setNumSpokes(int num)
/*     */   {
/* 263 */     this.numSpoke = num;
/* 264 */     this.interval = (360 / this.numSpoke);
/*     */   }
/*     */ 
/*     */   public ArrayList getLabels()
/*     */   {
/* 271 */     return this.labelList;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.PolarAxis
 * JD-Core Version:    0.6.2
 */