/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ 
/*     */ public class ThresholdLine
/*     */ {
/*  19 */   double value = (-1.0D / 0.0D);
/*  20 */   String labelString = null;
/*  21 */   Color labelColor = Color.black;
/*  22 */   Font labelFont = Gc.defaultFont;
/*  23 */   Gc gc = new Gc(null);
/*     */   public static final int START = 0;
/*     */   public static final int END = 1;
/*     */ 
/*     */   public ThresholdLine(double value, String label)
/*     */   {
/*  32 */     this();
/*  33 */     this.value = value;
/*  34 */     this.labelString = label;
/*     */   }
/*     */ 
/*     */   public ThresholdLine()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void draw(Axis parent, Graphics g)
/*     */   {
/*  54 */     this.gc.globals = parent.globals;
/*  55 */     if ((parent.side == 1) || (parent.side == 3)) {
/*  56 */       Point pixelLoc = parent.transformer.point(0.0D, this.value);
/*  57 */       Point left = new Point((int)(parent.plotarea.llX * parent.globals.maxX), pixelLoc.y);
/*  58 */       Point right = new Point((int)(parent.plotarea.urX * parent.globals.maxX), pixelLoc.y);
/*  59 */       this.gc.drawLine(g, right.x, right.y, left.x, left.y);
/*  60 */       if (parent.useDisplayList) {
/*  61 */         parent.globals.displayList.addLine(this, right, left);
/*     */       }
/*  63 */       if (this.labelString == null)
/*  64 */         return;
/*  65 */       g.setFont(this.labelFont);
/*  66 */       g.setColor(this.labelColor);
/*  67 */       FontMetrics fm = g.getFontMetrics();
/*  68 */       if (parent.side == 1) {
/*  69 */         this.gc.drawSmartString(g, right.x, right.y + fm.getMaxAscent() / 2 + 3, 1, 0, fm, this.labelString);
/*  70 */         if (parent.useDisplayList)
/*  71 */           parent.globals.displayList.addTextString(this, right.x - fm.stringWidth(this.labelString), right.y + 3, this.labelString, fm);
/*     */       }
/*     */       else {
/*  74 */         this.gc.drawSmartString(g, left.x, left.y + fm.getMaxAscent() / 2 + 3, 3, 0, fm, this.labelString);
/*  75 */         if (parent.useDisplayList)
/*  76 */           parent.globals.displayList.addTextString(this, left.x, left.y + 3, this.labelString, fm);
/*     */       }
/*     */     } else {
/*  79 */       Point pixelLoc = parent.transformer.point(this.value, 0.0D);
/*  80 */       Point top = new Point(pixelLoc.x, (int)(parent.plotarea.urY * parent.globals.maxY));
/*  81 */       Point bottom = new Point(pixelLoc.x, (int)(parent.plotarea.llY * parent.globals.maxY));
/*  82 */       this.gc.drawLine(g, top.x, top.y, bottom.x, bottom.y);
/*     */ 
/*  86 */       if (this.labelString == null)
/*  87 */         return;
/*  88 */       g.setFont(this.labelFont);
/*  89 */       g.setColor(this.labelColor);
/*  90 */       FontMetrics fm = g.getFontMetrics();
/*  91 */       if (parent.useDisplayList) {
/*  92 */         parent.globals.displayList.addLine(this, top, bottom);
/*     */       }
/*  94 */       if (parent.side == 2) {
/*  95 */         this.gc.drawSmartString(g, bottom.x + 3, bottom.y + fm.getMaxAscent() / 2 + 3, 3, 0, fm, this.labelString);
/*  96 */         if (parent.useDisplayList)
/*  97 */           parent.globals.displayList.addTextString(this, bottom.x + 3, bottom.y + 3, this.labelString, fm);
/*     */       }
/*     */       else {
/* 100 */         this.gc.drawSmartString(g, top.x + 3, top.y - fm.getMaxAscent() / 2, 3, 0, fm, this.labelString);
/* 101 */         if (parent.useDisplayList)
/* 102 */           parent.globals.displayList.addTextString(this, top.x + 3, top.y - 3 - fm.getMaxAscent(), this.labelString, fm);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setGc(Gc gc)
/*     */   {
/* 112 */     this.gc = gc;
/*     */   }
/*     */ 
/*     */   public Gc getGc()
/*     */   {
/* 120 */     return this.gc;
/*     */   }
/*     */ 
/*     */   public void setLabelString(String s)
/*     */   {
/* 128 */     this.labelString = s;
/*     */   }
/*     */ 
/*     */   public String getLabelString()
/*     */   {
/* 136 */     return this.labelString;
/*     */   }
/*     */ 
/*     */   public void setValue(double d)
/*     */   {
/* 144 */     this.value = d;
/*     */   }
/*     */ 
/*     */   public double getValue()
/*     */   {
/* 152 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void setLabelFont(Font f)
/*     */   {
/* 160 */     this.labelFont = f;
/*     */   }
/*     */ 
/*     */   public Font getLabelFont()
/*     */   {
/* 168 */     return this.labelFont;
/*     */   }
/*     */ 
/*     */   public void setLabelColor(Color c)
/*     */   {
/* 176 */     this.labelColor = c;
/*     */   }
/*     */ 
/*     */   public Color getLabelColor()
/*     */   {
/* 184 */     return this.labelColor;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.ThresholdLine
 * JD-Core Version:    0.6.2
 */