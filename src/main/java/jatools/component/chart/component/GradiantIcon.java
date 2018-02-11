/*     */ package jatools.component.chart.component;
/*     */ 
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ 
/*     */ public class GradiantIcon extends AbstractColorIcon
/*     */ {
/*  15 */   Color masterColor = Color.WHITE;
/*  16 */   Color secondColor = Color.WHITE;
/*  17 */   int style = 3;
/*     */ 
/*     */   public GradiantIcon(int width, int height) {
/*  20 */     super(width, height);
/*     */   }
/*     */ 
/*     */   public GradiantIcon(Dimension dimension) {
/*  24 */     super(dimension.width, dimension.height);
/*     */   }
/*     */ 
/*     */   protected void setStyle(FillStyleInterface style) {
/*  28 */     this.style = ((GradiantColor)style).style;
/*  29 */     this.masterColor = ((GradiantColor)style).masterColor;
/*  30 */     this.secondColor = ((GradiantColor)style).secondColor;
/*     */   }
/*     */ 
/*     */   public void paintIcon(Component c, Graphics g1, int x, int y) {
/*  34 */     Graphics g = g1.create();
/*  35 */     if (this.masterColor != Gc.TRANSPARENT) {
/*  36 */       switch (this.style) {
/*     */       case 3:
/*  38 */         doHorizontal(c, g, x, y);
/*  39 */         break;
/*     */       case 2:
/*  41 */         doVertical(c, g, x, y);
/*  42 */         break;
/*     */       case 0:
/*  44 */         doLeftRight(c, g, x, y);
/*  45 */         break;
/*     */       case 1:
/*  47 */         doTopBottom(c, g, x, y);
/*     */       }
/*     */     }
/*     */ 
/*  51 */     g.setColor(Color.black);
/*  52 */     g.drawRect(x, y, getIconWidth(), getIconHeight());
/*  53 */     g.dispose();
/*     */   }
/*     */ 
/*     */   private void doHorizontal(Component c, Graphics g, int x, int y) {
/*  57 */     int x2 = x + this.width;
/*  58 */     int y2 = y + this.height;
/*  59 */     double mr = this.masterColor.getRed(); double mg = this.masterColor.getGreen(); double mb = this.masterColor.getBlue();
/*  60 */     double sr = this.secondColor.getRed(); double sg = this.secondColor.getGreen(); double sb = this.secondColor.getBlue();
/*  61 */     double rstep = (mr - sr) * 1.0D / this.width; double gstep = (mg - sg) * 1.0D / this.width; double bstep = (mb - sb) * 1.0D / this.width;
/*  62 */     for (int i = x; i < x2; i++) {
/*  63 */       g.setColor(new Color((int)mr, (int)mg, (int)mb));
/*  64 */       g.drawLine(i, y, i, y2);
/*  65 */       mr -= rstep; mg -= gstep; mb -= bstep;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doVertical(Component c, Graphics g, int x, int y) {
/*  70 */     int x2 = x + this.width;
/*  71 */     int y2 = y + this.height;
/*  72 */     double mr = this.masterColor.getRed(); double mg = this.masterColor.getGreen(); double mb = this.masterColor.getBlue();
/*  73 */     double sr = this.secondColor.getRed(); double sg = this.secondColor.getGreen(); double sb = this.secondColor.getBlue();
/*  74 */     double rstep = (mr - sr) / this.height; double gstep = (mg - sg) / this.height; double bstep = (mb - sb) / this.height;
/*  75 */     for (int i = y; i < y2; i++) {
/*  76 */       g.setColor(new Color((int)mr, (int)mg, (int)mb));
/*  77 */       g.drawLine(x, i, x2, i);
/*  78 */       mr -= rstep; mg -= gstep; mb -= bstep;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doLeftRight(Component c, Graphics g, int x, int y) {
/*  83 */     int x2 = x + this.width;
/*  84 */     int y2 = y + this.height;
/*  85 */     double mr = this.masterColor.getRed(); double mg = this.masterColor.getGreen(); double mb = this.masterColor.getBlue();
/*  86 */     double sr = this.secondColor.getRed(); double sg = this.secondColor.getGreen(); double sb = this.secondColor.getBlue();
/*  87 */     double rstep = (mr - sr) * 2.0D / this.width; double gstep = (mg - sg) * 2.0D / this.width; double bstep = (mb - sb) * 2.0D / this.width;
/*  88 */     for (int i = x; i < x2; i++) {
/*  89 */       g.setColor(new Color((int)mr, (int)mg, (int)mb));
/*  90 */       g.drawLine(i, y, i, y2);
/*  91 */       if (i < (x + x2) / 2) {
/*  92 */         mr -= rstep; mg -= gstep; mb -= bstep;
/*     */       } else {
/*  94 */         mr += rstep; mg += gstep; mb += bstep;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doTopBottom(Component c, Graphics g, int x, int y) {
/* 100 */     int x2 = x + this.width;
/* 101 */     int y2 = y + this.height;
/* 102 */     double mr = this.masterColor.getRed(); double mg = this.masterColor.getGreen(); double mb = this.masterColor.getBlue();
/* 103 */     double sr = this.secondColor.getRed(); double sg = this.secondColor.getGreen(); double sb = this.secondColor.getBlue();
/* 104 */     double rstep = (mr - sr) * 2.0D / this.height; double gstep = (mg - sg) * 2.0D / this.height; double bstep = (mb - sb) * 2.0D / this.height;
/* 105 */     for (int i = y; i < y2; i++) {
/* 106 */       g.setColor(new Color((int)mr, (int)mg, (int)mb));
/* 107 */       g.drawLine(x, i, x2, i);
/* 108 */       if (i < (y + y2) / 2) {
/* 109 */         mr -= rstep; mg -= gstep; mb -= bstep;
/*     */       } else {
/* 111 */         mr += rstep; mg += gstep; mb += bstep;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.GradiantIcon
 * JD-Core Version:    0.6.2
 */