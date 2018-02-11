/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.accessor.ProtectPublic;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Point;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class DisplayList
/*     */   implements Serializable, ProtectPublic
/*     */ {
/*  24 */   ArrayList primitiveList = new ArrayList();
/*  25 */   ArrayList objectList = new ArrayList();
/*     */   Globals globals;
/*  27 */   private double scaleX = 1.0D;
/*  28 */   private double scaleY = 1.0D;
/*     */ 
/*     */   public DisplayList()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DisplayList(Globals g)
/*     */   {
/*  41 */     this.globals = g;
/*     */   }
/*     */ 
/*     */   public synchronized void addArc(Object obj, Point center, Point widthHeight, int startAngle, int arcAngle)
/*     */   {
/*  59 */     double increment = 0.06283185307179587D;
/*     */ 
/*  61 */     double currentRadians = startAngle / 180.0D * 3.141592653589793D;
/*  62 */     double totalRadians = arcAngle / 180.0D * 3.141592653589793D;
/*  63 */     int nPoints = (int)(totalRadians / increment);
/*     */ 
/*  65 */     Point[] arcArray = new Point[nPoints + 2];
/*  66 */     arcArray[(nPoints + 1)] = center;
/*     */ 
/*  68 */     int xVal = center.x + (int)(Math.cos(currentRadians + totalRadians) * (widthHeight.x / 2));
/*  69 */     int yVal = center.y + (int)(Math.sin(currentRadians + totalRadians) * (widthHeight.y / 2));
/*  70 */     arcArray[nPoints] = new Point(xVal, yVal);
/*     */ 
/*  73 */     for (int i = 0; i < nPoints; i++) {
/*  74 */       xVal = center.x + (int)(Math.cos(currentRadians) * (widthHeight.x / 2));
/*  75 */       yVal = center.y + (int)(Math.sin(currentRadians) * (widthHeight.y / 2));
/*  76 */       arcArray[i] = new Point(xVal, yVal);
/*  77 */       currentRadians += increment;
/*     */     }
/*     */ 
/*  80 */     this.primitiveList.add(arcArray);
/*  81 */     this.objectList.add(obj);
/*     */   }
/*     */ 
/*     */   public void addLine(Object obj, Point start, Point end)
/*     */   {
/*  92 */     Point ll = new Point(start.x - 2, start.y - 2);
/*  93 */     Point ur = new Point(end.x + 2, end.y + 2);
/*  94 */     addRectangle(obj, ll, ur);
/*     */   }
/*     */ 
/*     */   public void addLine2(Object obj, Point start, Point end)
/*     */   {
/* 105 */     Point[] points = new Point[4];
/* 106 */     points[0] = new Point(start.x - 2, start.y - 2);
/* 107 */     points[1] = new Point(start.x - 2, start.y + 2);
/* 108 */     points[2] = new Point(end.x + 2, end.y + 2);
/* 109 */     points[3] = new Point(end.x + 2, end.y - 2);
/*     */ 
/* 111 */     for (int i = 0; i < points.length; i++)
/*     */     {
/*     */       Point tmp109_108 = points[i]; tmp109_108.x = ((int)(tmp109_108.x * this.scaleX));
/*     */       Point tmp128_127 = points[i]; tmp128_127.y = ((int)(tmp128_127.y * this.scaleY));
/*     */ 
/* 115 */       points[i].y += 35;
/* 116 */       points[i].y = (this.globals.maxY - points[i].y);
/*     */     }
/*     */ 
/* 119 */     addPolygon(obj, points);
/*     */   }
/*     */ 
/*     */   public synchronized void addPolygon(Object obj, Point[] p)
/*     */   {
/* 129 */     Point[] temp = new Point[p.length];
/*     */ 
/* 131 */     for (int i = 0; i < p.length; i++) {
/* 132 */       temp[i] = p[i];
/*     */     }
/* 134 */     this.primitiveList.add(temp);
/* 135 */     this.objectList.add(obj);
/*     */   }
/*     */ 
/*     */   public synchronized void addPolyline(Object obj, Point[] p)
/*     */   {
/* 153 */     Point[] polylist = new Point[p.length * 2];
/*     */ 
/* 155 */     for (int i = 0; i < p.length; i++) {
/* 156 */       polylist[i] = p[i];
/*     */ 
/* 158 */       polylist[i].translate(2, 2);
/* 159 */       polylist[(polylist.length - i - 1)] = new Point(p[i].x - 4, p[i].y - 4);
/*     */     }
/*     */ 
/* 162 */     this.primitiveList.add(polylist);
/* 163 */     this.objectList.add(obj);
/*     */   }
/*     */ 
/*     */   public synchronized void addRectangle(Object obj, Point ll, Point ur)
/*     */   {
/* 174 */     Point[] myRect = new Point[4];
/* 175 */     myRect[0] = new Point(ll.x, ll.y);
/* 176 */     myRect[1] = new Point(ll.x, ur.y);
/* 177 */     myRect[2] = new Point(ur.x, ur.y);
/* 178 */     myRect[3] = new Point(ur.x, ll.y);
/* 179 */     this.primitiveList.add(myRect);
/* 180 */     this.objectList.add(obj);
/*     */   }
/*     */ 
/*     */   public synchronized void addTextString(Object obj, int llx, int lly, String s, FontMetrics fm)
/*     */   {
/* 193 */     int strW = fm.stringWidth(s);
/* 194 */     int strH = fm.getHeight();
/*     */ 
/* 196 */     Point[] myRect = new Point[4];
/* 197 */     myRect[0] = new Point(llx, lly);
/* 198 */     myRect[1] = new Point(llx, lly + strH);
/* 199 */     myRect[2] = new Point(llx + strW, lly + strH);
/* 200 */     myRect[3] = new Point(llx + strW, lly);
/* 201 */     this.primitiveList.add(myRect);
/* 202 */     this.objectList.add(obj);
/*     */   }
/*     */ 
/*     */   public synchronized void clear()
/*     */   {
/* 210 */     this.primitiveList = new ArrayList();
/* 211 */     this.objectList = new ArrayList();
/*     */   }
/*     */ 
/*     */   public synchronized boolean contains(Point p, ArrayList yourVector)
/*     */   {
/* 220 */     if (traverseList(p, yourVector)) {
/* 221 */       return true;
/*     */     }
/* 223 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean inPolygon(Point pickpt, Point[] pts)
/*     */   {
/* 229 */     int ret = 0;
/* 230 */     int iflip = 0;
/*     */ 
/* 238 */     int npts = pts.length;
/*     */ 
/* 240 */     for (int i = 0; i < npts; i++) {
/* 241 */       int j = i + 1;
/*     */ 
/* 243 */       if (j == npts) {
/* 244 */         j = 0;
/*     */       }
/*     */ 
/* 247 */       int xf = pts[i].x - pickpt.x;
/* 248 */       int yf = pts[i].y - pickpt.y;
/* 249 */       int xt = pts[j].x - pickpt.x;
/* 250 */       int yt = pts[j].y - pickpt.y;
/*     */ 
/* 252 */       if (((xf > 0) || (xt > 0)) && ((yf > 0) || (yt > 0)) && ((xf <= 0) || (xt <= 0)))
/*     */       {
/*     */         long flip;
/* 256 */         if ((flip = xf * yt - yf * xt) != 0L)
/*     */         {
/* 260 */           if ((flip > 0L ? 1 : 0) == (xf - xt > 0 ? 1 : 0))
/*     */           {
/* 264 */             ret++;
/*     */ 
/* 266 */             if (flip < 0L) {
/* 267 */               iflip--;
/*     */             }
/*     */ 
/* 270 */             if (flip > 0L)
/* 271 */               iflip++; 
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 275 */     ret &= 1;
/*     */ 
/* 277 */     if ((ret == 0) && (iflip != 0)) {
/* 278 */       ret = 1;
/*     */     }
/*     */ 
/* 281 */     if (ret == 1) {
/* 282 */       return true;
/*     */     }
/* 284 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean traverseList(Point p, ArrayList userVector)
/*     */   {
/* 292 */     boolean foundOne = false;
/*     */ 
/* 294 */     Point pickPoint = new Point(p.x, this.globals.maxY - p.y);
/*     */ 
/* 296 */     for (int i = 0; i < this.primitiveList.size(); i++) {
/* 297 */       Point[] myPoints = (Point[])this.primitiveList.get(i);
/*     */ 
/* 299 */       if (inPolygon(pickPoint, myPoints)) {
/* 300 */         userVector.add(this.objectList.get(i));
/* 301 */         foundOne = true;
/*     */       }
/*     */     }
/*     */ 
/* 305 */     return foundOne;
/*     */   }
/*     */ 
/*     */   public void setScale(double scaleX, double scaleY)
/*     */   {
/* 315 */     this.scaleX = scaleX;
/* 316 */     this.scaleY = scaleY;
/*     */   }
/*     */ 
/*     */   public void addPolygon2(Object obj, Point2D[] p) {
/* 320 */     Point[] temp = new Point[p.length];
/*     */ 
/* 322 */     for (int i = 0; i < p.length; i++)
/*     */     {
/* 324 */       temp[i] = new Point((int)p[i].getX(), (int)p[i].getY());
/*     */       Point tmp43_42 = temp[i]; tmp43_42.x = ((int)(tmp43_42.x * this.scaleX));
/*     */       Point tmp61_60 = temp[i]; tmp61_60.y = ((int)(tmp61_60.y * this.scaleY));
/*     */ 
/* 330 */       temp[i].y += 35;
/* 331 */       temp[i].y = (this.globals.maxY - temp[i].y);
/*     */     }
/*     */ 
/* 336 */     this.primitiveList.add(temp);
/* 337 */     this.objectList.add(obj);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.DisplayList
 * JD-Core Version:    0.6.2
 */