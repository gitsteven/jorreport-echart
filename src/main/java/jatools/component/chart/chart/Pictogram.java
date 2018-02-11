/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ 
/*     */ public class Pictogram extends Bar
/*     */ {
/*  18 */   boolean tileHorizontal = false;
/*     */ 
/*     */   public Pictogram()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Pictogram(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*     */   {
/*  33 */     super(d, xAx, yAx, subplot);
/*     */   }
/*     */ 
/*     */   protected void doBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/*  51 */     double offsetLeft = 0.5D * this.observationDelta * this.clusterWidth;
/*     */ 
/*  53 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */     double llx;
/*     */     double llx;
/*  54 */     if (this.unitScaling) {
/*  55 */       llx = whichBar - offsetLeft + howWide * whichSet;
/*     */     }
/*     */     else {
/*  58 */       llx = dat.getX() - offsetLeft + 
/*  59 */         howWide * this.observationDelta * whichSet;
/*     */     }
/*  61 */     double urx = llx + howWide * this.observationDelta;
/*     */     try {
/*  63 */       ury = dat.getY();
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException e)
/*     */     {
/*     */       double ury;
/*     */       return;
/*     */     }
/*     */     double ury;
/*  68 */     if (dat.getLabel() == "D")
/*  69 */       return;
/*  70 */     double lly = this.baseline;
/*  71 */     Point p1 = this.dataXfm.point(llx, lly);
/*  72 */     Point p2 = this.dataXfm.point(urx, ury);
/*  73 */     if (p2.x - p1.x < 1)
/*  74 */       p2.translate(1, 0);
/*  75 */     if (!individualColors)
/*     */     {
/*  77 */       fillRectWithImage(g, this.datasets[whichSet].getGc(), p1, p2);
/*     */     }
/*     */     else {
/*  80 */       fillRectWithImage(g, dat.getGc(), p1, p2);
/*     */     }
/*  82 */     if ((this.useDisplayList) && (this.globals.getUseDisplayList()))
/*     */     {
/*  84 */       if (individualColors) {
/*  85 */         this.globals.getDisplayList().addRectangle(dat, p1, p2);
/*  86 */         this.globals.getDisplayList().addRectangle(this.datasets[whichSet], p1, p2);
/*     */       }
/*     */       else {
/*  89 */         this.globals.getDisplayList().addRectangle(this.datasets[whichSet], p1, p2);
/*  90 */         this.globals.getDisplayList().addRectangle(dat, p1, p2);
/*     */       }
/*  92 */       this.globals.getDisplayList().addRectangle(this, p1, p2);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doDBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/* 110 */     double offsetLeft = 0.5D * this.observationDelta * this.clusterWidth;
/*     */ 
/* 115 */     Point[] face = new Point[4];
/* 116 */     Datum dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */     double llx;
/*     */     double llx;
/* 117 */     if (this.unitScaling) {
/* 118 */       llx = whichBar - offsetLeft + howWide * whichSet;
/*     */     }
/*     */     else {
/* 121 */       llx = dat.getX() - offsetLeft + howWide * this.observationDelta * whichSet;
/*     */     }
/* 123 */     double urx = llx + howWide * this.observationDelta;
/*     */     try {
/* 125 */       ury = dat.getY();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*     */       double ury;
/*     */       return;
/*     */     }
/*     */     double ury;
/* 130 */     double lly = this.baseline;
/* 131 */     face[0] = this.dataXfm.point(llx, lly);
/* 132 */     face[1] = this.dataXfm.point(urx, ury);
/*     */     Gc myGc;
/*     */     try
/*     */     {
/*     */       Gc myGc;
/* 134 */       if (!individualColors)
/* 135 */         myGc = this.datasets[whichSet].getGc();
/*     */       else
/* 137 */         myGc = dat.getGc();
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException e)
/*     */     {
/*     */       Gc myGc;
/* 140 */       myGc = this.datasets[whichSet].getGc();
/*     */     }
/* 142 */     fillRectWithImage(g, myGc, face[0], face[1]);
/*     */ 
/* 144 */     if ((this.useDisplayList) && (this.globals.getUseDisplayList())) {
/* 145 */       if (individualColors) {
/* 146 */         this.globals.getDisplayList().addRectangle(dat, face[0], face[1]);
/* 147 */         this.globals.getDisplayList().addRectangle(this.datasets[whichSet], face[0], face[1]);
/*     */       }
/*     */       else {
/* 150 */         this.globals.getDisplayList().addRectangle(this.datasets[whichSet], face[0], face[1]);
/* 151 */         this.globals.getDisplayList().addRectangle(dat, face[0], face[1]);
/*     */       }
/* 153 */       this.globals.getDisplayList().addRectangle(this, this.dataXfm.point(llx, lly), this.dataXfm.point(urx, ury));
/*     */     }
/*     */ 
/* 156 */     Color saveColor = myGc.getFillColor();
/* 157 */     myGc.setFillColor(saveColor.darker());
/*     */ 
/* 159 */     face[0] = this.dataXfm.point(urx, lly);
/*     */ 
/* 161 */     face[2] = new Point(face[1].x + this.globals.getXOffset(), face[1].y + this.globals.getYOffset());
/* 162 */     face[3] = new Point(face[0].x + this.globals.getXOffset(), face[0].y + this.globals.getYOffset());
/* 163 */     myGc.drawPolygon(g, face);
/*     */ 
/* 166 */     if (ury > lly) {
/* 167 */       face[0] = this.dataXfm.point(llx, ury);
/*     */ 
/* 169 */       face[3].x = (face[0].x + this.globals.getXOffset());
/* 170 */       face[3].y = (face[0].y + this.globals.getYOffset());
/* 171 */       myGc.drawPolygon(g, face);
/*     */     }
/*     */     else {
/* 174 */       face[0] = this.dataXfm.point(llx, lly);
/* 175 */       face[1] = this.dataXfm.point(urx, lly);
/*     */ 
/* 177 */       face[2].x = (face[1].x + this.globals.getXOffset());
/* 178 */       face[2].y = (face[1].y + this.globals.getYOffset());
/* 179 */       face[3].x = (face[0].x + this.globals.getXOffset());
/* 180 */       face[3].y = (face[0].y + this.globals.getYOffset());
/* 181 */       myGc.drawPolygon(g, face);
/*     */     }
/* 183 */     myGc.setFillColor(saveColor);
/*     */   }
/*     */ 
/*     */   public void fillRectWithImage(Graphics g, Gc gc, Point ll, Point ur)
/*     */   {
/* 194 */     Image img = gc.getImage();
/* 195 */     if (img == null) {
/* 196 */       gc.fillRect(g, ll, ur);
/* 197 */       return;
/*     */     }
/* 199 */     int iw = img.getWidth(null);
/* 200 */     int ih = img.getHeight(null);
/* 201 */     if (!this.tileHorizontal) {
/* 202 */       iw = ur.x - ll.x;
/*     */     }
/* 204 */     Graphics g2 = g.create();
/* 205 */     g2.clipRect(ll.x, this.globals.getMaxY() - ur.y, ur.x - ll.x, ur.y - ll.y);
/* 206 */     int i = ll.y + ih / 2; int j = ll.x + iw / 2;
/* 207 */     Point p = new Point();
/* 208 */     while (i < ur.y) {
/* 209 */       while (j < ur.x) {
/* 210 */         p.x = j;
/* 211 */         p.y = i;
/* 212 */         gc.drawImage(g2, p);
/* 213 */         j += iw;
/*     */       }
/* 215 */       j = ll.x + iw / 2;
/* 216 */       i += ih;
/*     */     }
/* 218 */     g2.dispose();
/*     */   }
/*     */ 
/*     */   public boolean getTileHorizontal()
/*     */   {
/* 225 */     return this.tileHorizontal;
/*     */   }
/*     */ 
/*     */   public void setTileHorizontal(boolean tileHorizontal)
/*     */   {
/* 232 */     this.tileHorizontal = tileHorizontal;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Pictogram
 * JD-Core Version:    0.6.2
 */