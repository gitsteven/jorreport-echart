/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Plotarea
/*     */   implements Serializable
/*     */ {
/*  22 */   Gc gc = new Gc(Color.white, null);
/*  23 */   double urX = 0.8D;
/*  24 */   double urY = 0.8D;
/*  25 */   double llX = 0.2D;
/*  26 */   double llY = 0.2D;
/*  27 */   boolean useDisplayList = true;
/*     */   Globals globals;
/*  31 */   private int gWidth = 640;
/*  32 */   private int gHeight = 480;
/*  33 */   Transform transform = new Transform(0.0D, 0.0D, 1.0D, 1.0D, 
/*  34 */     0, 0, 
/*  35 */     this.gWidth, 
/*  36 */     this.gHeight);
/*     */ 
/*     */   public Plotarea()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Plotarea(Globals w)
/*     */   {
/*  46 */     this.globals = w;
/*  47 */     this.gc.globals = this.globals;
/*     */   }
/*     */ 
/*     */   public synchronized void draw(Graphics g)
/*     */   {
/*  54 */     if (this.globals.threeD)
/*  55 */       draw3d(g);
/*     */     else
/*  57 */       this.gc.fillRect(g, 
/*  58 */         this.transform.point(this.llX, this.llY), 
/*  59 */         this.transform.point(this.urX, this.urY));
/*  60 */     if ((this.useDisplayList) && (this.globals.useDisplayList))
/*  61 */       this.globals.displayList.addRectangle(this, 
/*  62 */         this.transform.point(this.llX, this.llY), 
/*  63 */         this.transform.point(this.urX, this.urY));
/*     */   }
/*     */ 
/*     */   protected void drawOutline(Graphics g) {
/*  67 */     if (this.globals.threeD)
/*  68 */       outline3d(g);
/*     */     else
/*  70 */       drawRect(g, this.transform.point(this.llX, this.llY), this.transform.point(this.urX, this.urY));
/*     */   }
/*     */ 
/*     */   protected void outline3d(Graphics g)
/*     */   {
/*  78 */     Point[] pts = new Point[4];
/*     */ 
/*  80 */     pts[0] = this.transform.point(this.llX, this.urY);
/*  81 */     pts[1] = new Point(pts[0].x + this.globals.xOffset, pts[0].y + this.globals.yOffset);
/*  82 */     pts[2] = this.transform.point(this.urX, this.urY);
/*  83 */     pts[2].translate(this.globals.xOffset, this.globals.yOffset);
/*  84 */     pts[3] = this.transform.point(this.urX, this.llY);
/*  85 */     pts[3].translate(this.globals.xOffset, this.globals.yOffset);
/*     */ 
/*  87 */     this.gc.drawPolyline(g, pts);
/*     */   }
/*     */ 
/*     */   private void drawRect(Graphics g, Point ll, Point ur) {
/*  91 */     this.gc.drawLine(g, ll.x, ll.y, ur.x, ll.y);
/*  92 */     this.gc.drawLine(g, ll.x, ll.y, ll.x, ur.y);
/*  93 */     this.gc.drawLine(g, ll.x, ur.y, ur.x, ur.y);
/*  94 */     this.gc.drawLine(g, ur.x, ur.y, ur.x, ll.y);
/*     */   }
/*     */ 
/*     */   public synchronized void draw3d(Graphics g)
/*     */   {
/* 107 */     Point[] pts = new Point[4];
/* 108 */     if (g == null)
/* 109 */       return;
/* 110 */     if (this.gc.fillColor == Gc.TRANSPARENT)
/* 111 */       return;
/* 112 */     pts[0] = this.transform.point(this.llX, this.llY);
/* 113 */     pts[1] = new Point(pts[0].x + this.globals.xOffset, pts[0].y + this.globals.yOffset);
/* 114 */     pts[2] = this.transform.point(this.urX, this.urY);
/* 115 */     pts[3] = new Point(pts[2].x + this.globals.xOffset, pts[2].y + this.globals.yOffset);
/* 116 */     this.gc.fillRect(g, pts[1], pts[3]);
/* 117 */     if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/* 118 */       this.globals.displayList.addRectangle(this, pts[1], pts[3]);
/*     */     }
/*     */ 
/* 121 */     Color saveColor = this.gc.fillColor;
/* 122 */     this.gc.fillColor = saveColor.darker();
/* 123 */     Color secondaryColor = this.gc.getSecondaryFillColor();
/* 124 */     this.gc.setSecondaryFillColor(secondaryColor.darker());
/* 125 */     int saveURx = pts[2].x;
/* 126 */     int saveURy = pts[2].y;
/* 127 */     pts[2].x = pts[1].x;
/* 128 */     pts[2].y = pts[3].y;
/* 129 */     pts[3].x = pts[0].x;
/* 130 */     pts[3].y = saveURy;
/* 131 */     this.gc.drawPolygon(g, pts);
/* 132 */     pts[2].x = (saveURx + this.globals.xOffset);
/* 133 */     pts[2].y = pts[1].y;
/* 134 */     pts[3].x = saveURx;
/* 135 */     pts[3].y = pts[0].y;
/* 136 */     int style = this.gc.fillStyle;
/* 137 */     if (style == 0) {
/* 138 */       this.gc.fillStyle = -1;
/* 139 */       this.gc.fillColor = this.gc.getSecondaryFillColor();
/*     */     }
/* 141 */     this.gc.drawPolygon(g, pts);
/* 142 */     this.gc.fillStyle = style;
/* 143 */     this.gc.fillColor = saveColor;
/* 144 */     this.gc.setSecondaryFillColor(secondaryColor);
/*     */   }
/*     */ 
/*     */   public Gc getGc()
/*     */   {
/* 152 */     return this.gc;
/*     */   }
/*     */ 
/*     */   public double getLlX()
/*     */   {
/* 160 */     return this.llX;
/*     */   }
/*     */ 
/*     */   public double getLlY()
/*     */   {
/* 168 */     return this.llY;
/*     */   }
/*     */ 
/*     */   public double getUrX()
/*     */   {
/* 176 */     return this.urX;
/*     */   }
/*     */ 
/*     */   public double getUrY()
/*     */   {
/* 184 */     return this.urY;
/*     */   }
/*     */ 
/*     */   public boolean getUseDisplayList()
/*     */   {
/* 193 */     return this.useDisplayList;
/*     */   }
/*     */ 
/*     */   public synchronized void resize(int w, int h)
/*     */   {
/* 205 */     this.gWidth = w;
/* 206 */     this.gHeight = h;
/*     */ 
/* 208 */     this.transform = new Transform(0.0D, 0.0D, 1.0D, 1.0D, 
/* 209 */       0, 0, 
/* 210 */       this.gWidth, 
/* 211 */       this.gHeight);
/*     */   }
/*     */ 
/*     */   public void setGc(Gc g)
/*     */   {
/* 219 */     this.gc = g;
/*     */   }
/*     */ 
/*     */   public void setLlX(double d)
/*     */   {
/* 227 */     this.llX = d;
/*     */   }
/*     */ 
/*     */   public void setLlY(double d)
/*     */   {
/* 235 */     this.llY = d;
/*     */   }
/*     */ 
/*     */   public void setUrX(double d)
/*     */   {
/* 243 */     this.urX = d;
/*     */   }
/*     */ 
/*     */   public void setUrY(double d)
/*     */   {
/* 251 */     this.urY = d;
/*     */   }
/*     */ 
/*     */   public void setUseDisplayList(boolean onOff)
/*     */   {
/* 259 */     this.useDisplayList = onOff;
/*     */   }
/*     */   public String toString() {
/* 262 */     return getClass().getName() + "[" + 
/* 263 */       "urX " + this.urX + 
/* 264 */       "urY " + this.urY + 
/* 265 */       "llX " + this.llX + 
/* 266 */       "llY " + this.llY + 
/* 267 */       "]";
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Plotarea
 * JD-Core Version:    0.6.2
 */