/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.MemoryImageSource;
/*     */ import java.awt.image.PixelGrabber;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class RotateString
/*     */   implements Serializable
/*     */ {
/*     */   private Component parent;
/*     */   private Color c;
/*     */   private Font f;
/*     */   private transient Image tmp;
/*     */ 
/*     */   public RotateString(Component p)
/*     */   {
/*  44 */     this.parent = p;
/*  45 */     this.c = null;
/*  46 */     this.f = null;
/*     */   }
/*     */ 
/*     */   private void drawString(String str, int x, int y, int ax, int ay, int angle, Image img) {
/*  50 */     if ((str == null) || (img == null)) {
/*  51 */       return;
/*     */     }
/*  53 */     double th = -3.141592653589793D * angle / 180.0D;
/*  54 */     Graphics g = img.getGraphics();
/*     */ 
/*  66 */     if (this.f == null) this.f = g.getFont();
/*  67 */     if (this.c == null) this.c = g.getColor();
/*  68 */     if (this.c == null) this.c = Color.black;
/*     */ 
/*  71 */     Rectangle rect = getExtent(str, x, y, 0, this.f);
/*  72 */     int ox = rect.x;
/*  73 */     int oy = rect.y;
/*  74 */     int ow = rect.width;
/*  75 */     int oh = rect.height;
/*     */     int nh;
/*     */     int nx;
/*     */     int ny;
/*     */     int nw;
/*     */     int nh;
/*  78 */     if (angle == 0)
/*     */     {
/*  80 */       int nx = ox;
/*  81 */       int ny = oy;
/*  82 */       int nw = ow;
/*  83 */       nh = oh;
/*     */     }
/*     */     else
/*     */     {
/*  87 */       rect = rotateRectangle(rect, ax, ay, th);
/*  88 */       nx = rect.x;
/*  89 */       ny = rect.y;
/*  90 */       nw = rect.width;
/*  91 */       nh = rect.height;
/*     */     }
/*     */ 
/*  95 */     this.tmp = this.parent.createImage(ow, oh);
/*  96 */     Graphics tg = this.tmp.getGraphics();
/*     */ 
/* 108 */     int clrRGB = txtRGB = this.c.getRGB();
/*     */ 
/* 120 */     int txtRGB = Color.black.getRGB();
/* 121 */     tg.setColor(Color.black);
/* 122 */     tg.setFont(this.f);
/*     */ 
/* 127 */     tg.drawString(str, x - ox, y - oy);
/*     */ 
/* 130 */     int[] txtPix = new int[ow * oh];
/* 131 */     PixelGrabber grab = new PixelGrabber(this.tmp, 0, 0, ow, oh, txtPix, 0, ow);
/*     */     try { grab.grabPixels();
/*     */     } catch (InterruptedException localInterruptedException)
/*     */     {
/*     */     }
/* 136 */     int[] newPix = new int[nw * nh];
/*     */ 
/* 139 */     int i = ny; for (int index = 0; i < nh + ny; i++)
/*     */     {
/* 141 */       for (int j = nx; j < nw + nx; j++)
/*     */       {
/* 143 */         int ix = (int)(0.5D + (j - ax) * Math.cos(-th) - (i - ay) * Math.sin(-th) + ax);
/* 144 */         int iy = (int)(0.5D + (j - ax) * Math.sin(-th) + (i - ay) * Math.cos(-th) + ay);
/*     */ 
/* 146 */         ix -= ox;
/* 147 */         iy -= oy;
/* 148 */         if ((ix >= 0) && (iy >= 0) && (ix < ow) && (iy < oh))
/*     */         {
/* 150 */           int ii = iy * ow + ix;
/* 151 */           if (txtPix[ii] == txtRGB)
/* 152 */             newPix[index] = clrRGB;
/*     */         }
/* 154 */         index++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 159 */     this.tmp = this.parent.createImage(new MemoryImageSource(nw, nh, ColorModel.getRGBdefault(), newPix, 0, nw));
/* 160 */     g.drawImage(this.tmp, nx, ny, null);
/* 161 */     tg.dispose();
/*     */   }
/*     */ 
/*     */   public void drawString(String str, int x, int y, int angle, Image img)
/*     */   {
/* 168 */     drawString(str, x, y, x, y, angle, img);
/*     */   }
/*     */ 
/*     */   private void drawString(String str, int x, int y, Image img) {
/* 172 */     drawString(str, x, y, x, y, 0, img);
/*     */   }
/*     */ 
/*     */   private Rectangle getExtent(String str, int x, int y, int angle) {
/* 176 */     return getExtent(str, x, y, x, y, angle, this.f);
/*     */   }
/*     */ 
/*     */   private Rectangle getExtent(String str, int x, int y, int ax, int ay, int angle)
/*     */   {
/* 181 */     return getExtent(str, x, y, ax, ay, angle, this.f);
/*     */   }
/*     */ 
/*     */   private Rectangle getExtent(String str, int x, int y, int ax, int ay, int angle, Font fn) {
/* 185 */     Rectangle rect = new Rectangle();
/*     */ 
/* 187 */     FontMetrics fm = this.parent.getFontMetrics(fn);
/* 188 */     int descent = fm.getMaxDescent();
/* 189 */     int ascent = fm.getMaxAscent();
/*     */ 
/* 191 */     rect.x = x;
/* 192 */     rect.y = (y - ascent + descent);
/* 193 */     rect.width = fm.stringWidth(str);
/*     */ 
/* 197 */     rect.height = ascent;
/*     */ 
/* 199 */     if (angle != 0) rect = rotateRectangle(rect, ax, ay, -3.141592653589793D * angle / 180.0D);
/*     */ 
/* 201 */     return rect;
/*     */   }
/*     */ 
/*     */   private Rectangle getExtent(String str, int x, int y, int ax, int ay, int angle, FontMetrics fm) {
/* 205 */     Rectangle rect = new Rectangle();
/*     */ 
/* 207 */     int descent = fm.getMaxDescent();
/* 208 */     int ascent = fm.getMaxAscent();
/*     */ 
/* 210 */     rect.x = x;
/* 211 */     rect.y = (y - (ascent + descent));
/* 212 */     rect.width = fm.stringWidth(str);
/* 213 */     rect.height = (ascent + descent);
/*     */ 
/* 215 */     if (angle != 0) rect = rotateRectangle(rect, ax, ay, -3.141592653589793D * angle / 180.0D);
/*     */ 
/* 217 */     return rect;
/*     */   }
/*     */ 
/*     */   private Rectangle getExtent(String str, int x, int y, int angle, Font fn) {
/* 221 */     return getExtent(str, x, y, x, y, angle, fn);
/*     */   }
/*     */ 
/*     */   protected Rectangle getExtent(String str, int x, int y, int angle, FontMetrics fm)
/*     */   {
/* 229 */     return getExtent(str, x, y, x, y, angle, fm);
/*     */   }
/*     */ 
/*     */   private Rectangle rotateRectangle(Rectangle rect, int ax, int ay, double th)
/*     */   {
/* 239 */     double xmax;
/* 235 */     double xmin = xmax = (rect.x - ax) * Math.cos(th) - (rect.y - ay) * Math.sin(th);
/*     */     double ymax;
/* 236 */     double ymin = ymax = (rect.x - ax) * Math.sin(th) + (rect.y - ay) * Math.cos(th);
/* 237 */     double fx = (rect.x + rect.width - 1 - ax) * Math.cos(th) - (rect.y - ay) * Math.sin(th);
/* 238 */     double fy = (rect.x + rect.width - 1 - ax) * Math.sin(th) + (rect.y - ay) * Math.cos(th);
/* 239 */     if (fx < xmin) xmin = fx; if (fx > xmax) xmax = fx;
/* 240 */     if (fy < ymin) ymin = fy; if (fy > ymax) ymax = fy;
/* 241 */     fx = (rect.x - ax) * Math.cos(th) - (rect.y + rect.height - 1 - ay) * Math.sin(th);
/* 242 */     fy = (rect.x - ax) * Math.sin(th) + (rect.y + rect.height - 1 - ay) * Math.cos(th);
/* 243 */     if (fx < xmin) xmin = fx; if (fx > xmax) xmax = fx;
/* 244 */     if (fy < ymin) ymin = fy; if (fy > ymax) ymax = fy;
/* 245 */     fx = (rect.x + rect.width - 1 - ax) * Math.cos(th) - (rect.y + rect.height - 1 - ay) * Math.sin(th);
/* 246 */     fy = (rect.x + rect.width - 1 - ax) * Math.sin(th) + (rect.y + rect.height - 1 - ay) * Math.cos(th);
/* 247 */     if (fx < xmin) xmin = fx; if (fx > xmax) xmax = fx;
/* 248 */     if (fy < ymin) ymin = fy; if (fy > ymax) ymax = fy;
/*     */ 
/* 250 */     rect.x = ((int)xmin + ax);
/* 251 */     rect.y = ((int)ymin + ay);
/* 252 */     rect.width = ((int)(xmax - xmin + 1.0D));
/* 253 */     rect.height = ((int)(ymax - ymin + 1.0D));
/*     */ 
/* 255 */     return rect;
/*     */   }
/*     */ 
/*     */   void setColor(Color color)
/*     */   {
/* 264 */     this.c = color;
/*     */   }
/*     */ 
/*     */   void setFont(Font font)
/*     */   {
/* 273 */     this.f = font;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.RotateString
 * JD-Core Version:    0.6.2
 */