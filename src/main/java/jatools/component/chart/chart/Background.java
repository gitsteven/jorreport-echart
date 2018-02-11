/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Background
/*     */   implements Serializable
/*     */ {
/*  25 */   protected Gc gc = new Gc(Color.white, null);
/*     */ 
/*  27 */   protected Color titleColor = Color.black;
/*     */ 
/*  29 */   protected Font titleFont = Gc.defaultFont;
/*     */ 
/*  31 */   protected String titleString = null;
/*     */ 
/*  33 */   protected Color subTitleColor = Color.black;
/*     */ 
/*  35 */   protected Font subTitleFont = Gc.defaultFont;
/*     */ 
/*  37 */   protected String subTitleString = null;
/*     */ 
/*  39 */   protected boolean useDisplayList = true;
/*     */   protected Globals globals;
/*     */   protected Double titleX;
/*     */   protected Double titleY;
/*     */   protected Double subTitleX;
/*     */   protected Double subTitleY;
/*     */ 
/*     */   public Background()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Background(Globals g)
/*     */   {
/*  62 */     this.globals = g;
/*  63 */     this.gc.globals = g;
/*     */   }
/*     */ 
/*     */   public synchronized void draw(Graphics g)
/*     */   {
/*  74 */     int startY = 7;
/*     */ 
/*  76 */     if (g == null) {
/*  77 */       System.out.println("null graphics in background");
/*  78 */       return;
/*     */     }
/*  80 */     if (this.gc.getOutlineFills())
/*  81 */       this.gc.fillRect(g, new Point(0, 1), new Point(this.globals.maxX - 1, 
/*  82 */         this.globals.maxY));
/*     */     else
/*  84 */       this.gc.fillRect(g, new Point(0, 0), new Point(this.globals.maxX, 
/*  85 */         this.globals.maxY));
/*  86 */     if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/*  87 */       this.globals.displayList.addRectangle(this, new Point(0, 0), 
/*  88 */         new Point(this.globals.maxX, this.globals.maxY));
/*     */     }
/*  90 */     if (this.titleString != null) {
/*  91 */       g.setFont(this.titleFont);
/*  92 */       g.setColor(this.titleColor);
/*  93 */       FontMetrics fm = g.getFontMetrics();
/*     */       int startX;
/*     */       int startX;
/*  95 */       if (this.titleX == null)
/*  96 */         startX = this.globals.maxX / 2 - fm.stringWidth(this.titleString) / 2;
/*     */       else {
/*  98 */         startX = (int)(this.titleX.doubleValue() * this.globals.maxX);
/*     */       }
/* 100 */       if (this.titleY == null)
/* 101 */         startY = fm.getMaxAscent() + 10;
/*     */       else {
/* 103 */         startY = this.globals.maxY - 
/* 104 */           (int)(this.titleY.doubleValue() * this.globals.maxY);
/*     */       }
/* 106 */       g.drawString(this.titleString, startX, startY);
/* 107 */       if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/* 108 */         this.globals.displayList.addTextString(this, startX, 
/* 109 */           startY, this.titleString, fm);
/*     */       }
/*     */     }
/* 112 */     if (this.subTitleString != null) {
/* 113 */       g.setFont(this.subTitleFont);
/* 114 */       g.setColor(this.subTitleColor);
/* 115 */       FontMetrics fm = g.getFontMetrics();
/*     */       int startX;
/*     */       int startX;
/* 117 */       if (this.subTitleX == null)
/* 118 */         startX = this.globals.maxX / 2 - 
/* 119 */           fm.stringWidth(this.subTitleString) / 2;
/*     */       else {
/* 121 */         startX = (int)(this.subTitleX.doubleValue() * this.globals.maxX);
/*     */       }
/* 123 */       if (this.subTitleY == null)
/* 124 */         startY = startY + fm.getMaxAscent() + 3;
/*     */       else {
/* 126 */         startY = this.globals.maxY - 
/* 127 */           (int)(this.subTitleY.doubleValue() * this.globals.maxY);
/*     */       }
/* 129 */       g.drawString(this.subTitleString, startX, startY);
/* 130 */       if ((this.useDisplayList) && (this.globals.useDisplayList))
/* 131 */         this.globals.displayList.addTextString(this, startX, 
/* 132 */           startY, this.subTitleString, fm);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Gc getGc()
/*     */   {
/* 143 */     return this.gc;
/*     */   }
/*     */ 
/*     */   public Color getSubTitleColor()
/*     */   {
/* 152 */     return this.subTitleColor;
/*     */   }
/*     */ 
/*     */   public Font getSubTitleFont()
/*     */   {
/* 161 */     return this.subTitleFont;
/*     */   }
/*     */ 
/*     */   public String getSubTitleString()
/*     */   {
/* 170 */     return this.subTitleString;
/*     */   }
/*     */ 
/*     */   public Double getSubTitleX()
/*     */   {
/* 179 */     return this.subTitleX;
/*     */   }
/*     */ 
/*     */   public Double getSubTitleY()
/*     */   {
/* 188 */     return this.subTitleY;
/*     */   }
/*     */ 
/*     */   public Color getTitleColor()
/*     */   {
/* 197 */     return this.titleColor;
/*     */   }
/*     */ 
/*     */   public Font getTitleFont()
/*     */   {
/* 206 */     return this.titleFont;
/*     */   }
/*     */ 
/*     */   public String getTitleString()
/*     */   {
/* 215 */     return this.titleString;
/*     */   }
/*     */ 
/*     */   public Double getTitleX()
/*     */   {
/* 224 */     return this.titleX;
/*     */   }
/*     */ 
/*     */   public Double getTitleY()
/*     */   {
/* 233 */     return this.titleY;
/*     */   }
/*     */ 
/*     */   public boolean getUseDisplayList()
/*     */   {
/* 242 */     return this.useDisplayList;
/*     */   }
/*     */ 
/*     */   protected synchronized void resize(int w, int h)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setGc(Gc g)
/*     */   {
/* 265 */     this.gc = g;
/* 266 */     this.gc.globals = this.globals;
/*     */   }
/*     */ 
/*     */   public void setSubTitleColor(Color c)
/*     */   {
/* 275 */     this.subTitleColor = c;
/*     */   }
/*     */ 
/*     */   public void setSubTitleFont(Font f)
/*     */   {
/* 284 */     this.subTitleFont = f;
/*     */   }
/*     */ 
/*     */   public void setSubTitleString(String s)
/*     */   {
/* 293 */     this.subTitleString = s;
/*     */   }
/*     */ 
/*     */   public void setSubTitleX(Double d)
/*     */   {
/* 302 */     this.subTitleX = d;
/*     */   }
/*     */ 
/*     */   public void setSubTitleY(Double d)
/*     */   {
/* 311 */     this.subTitleY = d;
/*     */   }
/*     */ 
/*     */   public void setTitleColor(Color c)
/*     */   {
/* 320 */     this.titleColor = c;
/*     */   }
/*     */ 
/*     */   public void setTitleFont(Font f)
/*     */   {
/* 329 */     this.titleFont = f;
/*     */   }
/*     */ 
/*     */   public void setTitleString(String s)
/*     */   {
/* 338 */     this.titleString = s;
/*     */   }
/*     */ 
/*     */   public void setTitleX(Double d)
/*     */   {
/* 347 */     this.titleX = d;
/*     */   }
/*     */ 
/*     */   public void setTitleY(Double d)
/*     */   {
/* 356 */     this.titleY = d;
/*     */   }
/*     */ 
/*     */   public void setUseDisplayList(boolean onOff)
/*     */   {
/* 368 */     this.useDisplayList = onOff;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Background
 * JD-Core Version:    0.6.2
 */