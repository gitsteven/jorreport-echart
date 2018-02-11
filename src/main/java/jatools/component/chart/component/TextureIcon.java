/*     */ package jatools.component.chart.component;
/*     */ 
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.TexturePaint;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.FilteredImageSource;
/*     */ import java.awt.image.ImageFilter;
/*     */ import java.awt.image.ReplicateScaleFilter;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class TextureIcon extends AbstractColorIcon
/*     */ {
/*  24 */   Color masterColor = Color.WHITE;
/*     */ 
/*  26 */   Color secondColor = Color.WHITE;
/*     */ 
/*  28 */   int style = 0;
/*     */   Image image;
/*     */ 
/*     */   public TextureIcon(int width, int height)
/*     */   {
/*  33 */     super(width, height);
/*     */   }
/*     */ 
/*     */   public TextureIcon(Dimension dimension) {
/*  37 */     super(dimension.width, dimension.height);
/*     */   }
/*     */ 
/*     */   public void setStyle(FillStyleInterface style) {
/*  41 */     this.style = ((TextureColor)style).style;
/*  42 */     this.masterColor = ((TextureColor)style).masterColor;
/*  43 */     this.secondColor = ((TextureColor)style).secondColor;
/*  44 */     this.image = ((TextureColor)style).image;
/*     */   }
/*     */ 
/*     */   public void paintIcon(Component c, Graphics g1, int x, int y)
/*     */   {
/*  49 */     Graphics2D g = (Graphics2D)g1.create();
/*     */ 
/*  51 */     if (this.masterColor != Gc.TRANSPARENT) {
/*  52 */       g.setPaint(buildTexture());
/*  53 */       g.fillRect(x, y, this.width, this.height);
/*     */     }
/*  55 */     g.setColor(Color.black);
/*  56 */     g.drawRect(x, y, getIconWidth(), getIconHeight());
/*  57 */     g.dispose();
/*     */   }
/*     */ 
/*     */   public TexturePaint buildTexture()
/*     */   {
/*  62 */     int sz = 6;
/*     */     TexturePaint texture;
/*     */     TexturePaint texture;
/*  64 */     if ((this.image != null) && (this.style == -1))
/*     */     {
/*  67 */       BufferedImage img = null;
/*     */ 
/*  69 */       ImageFilter resizeFilter = new ReplicateScaleFilter(this.width, this.height);
/*  70 */       FilteredImageSource source = new FilteredImageSource(this.image
/*  71 */         .getSource(), resizeFilter);
/*     */ 
/*  73 */       this.image = Toolkit.getDefaultToolkit().createImage(source);
/*     */ 
/*  75 */       int xSize = this.image.getWidth(null);
/*  76 */       int ySize = this.image.getHeight(null);
/*  77 */       if ((xSize < 1) || (ySize < 1))
/*  78 */         return null;
/*  79 */       if ((this.image instanceof BufferedImage)) {
/*  80 */         img = (BufferedImage)this.image;
/*     */       } else {
/*  82 */         System.out.println("doing conversion");
/*  83 */         img = new BufferedImage(xSize, ySize, 
/*  84 */           1);
/*  85 */         img.getGraphics().drawImage(this.image, 0, 0, null);
/*     */       }
/*  87 */       Rectangle r = new Rectangle(0, 0, xSize, ySize);
/*  88 */       texture = new TexturePaint(img, r);
/*     */     } else {
/*  90 */       BufferedImage img = new BufferedImage(sz, sz, 
/*  91 */         1);
/*  92 */       Graphics ig = img.createGraphics();
/*  93 */       if (this.style == -1)
/*  94 */         ig.setColor(this.masterColor);
/*     */       else
/*  96 */         ig.setColor(this.secondColor);
/*  97 */       ig.fillRect(0, 0, sz, sz);
/*  98 */       ig.setColor(this.masterColor);
/*  99 */       switch (this.style) {
/*     */       case 0:
/* 101 */         ig.drawLine(0, sz / 2, sz, sz / 2);
/*     */ 
/* 103 */         break;
/*     */       case 1:
/* 105 */         ig.drawLine(sz / 2, 0, sz / 2, sz);
/*     */ 
/* 107 */         break;
/*     */       case 2:
/* 109 */         ig.drawLine(0, 0, sz - 1, sz - 1);
/*     */ 
/* 111 */         break;
/*     */       case 3:
/* 113 */         ig.drawLine(0, sz - 1, sz - 1, 0);
/*     */ 
/* 115 */         break;
/*     */       case 4:
/* 117 */         ig.drawLine(0, sz - 1, sz - 1, 0);
/* 118 */         ig.drawLine(0, 0, sz, sz);
/*     */       }
/*     */ 
/* 123 */       Rectangle r = new Rectangle(0, 0, sz, sz);
/* 124 */       texture = new TexturePaint(img, r);
/*     */     }
/* 126 */     return texture;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.TextureIcon
 * JD-Core Version:    0.6.2
 */