/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class Candlestick extends Bar
/*     */ {
/*  16 */   int numberOfPoints = 0;
/*     */ 
/*     */   public Candlestick()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Candlestick(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*     */   {
/*  38 */     super(d, xAx, yAx, subplot);
/*     */   }
/*     */ 
/*     */   protected void doBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/*  54 */     howWide *= this.globals.getMaxX() / 360.0D;
/*  55 */     howWide *= 12.0D / this.numberOfPoints;
/*     */     try
/*     */     {
/*  59 */       CandlestickDatum cd = (CandlestickDatum)this.datasets[whichSet].getData().get(whichBar);
/*  60 */       double open = cd.getOpen();
/*  61 */       double close = cd.getClose();
/*     */       Point ll;
/*     */       Point ur;
/*  63 */       if (open > close)
/*     */       {
/*     */         double llx;
/*     */         double llx;
/*  64 */         if (this.unitScaling)
/*  65 */           llx = whichBar;
/*     */         else
/*  67 */           llx = cd.getX();
/*  68 */         double lly = cd.getLow();
/*  69 */         double ury = cd.getHigh();
/*  70 */         double urx = llx;
/*  71 */         this.datasets[whichSet].getGc().drawLine(
/*  72 */           g, 
/*  73 */           this.dataXfm.point(llx, lly), 
/*  74 */           this.dataXfm.point(urx, ury));
/*  75 */         lly = open;
/*  76 */         ury = close;
/*  77 */         Point ll = this.dataXfm.point(llx, lly);
/*  78 */         Point ur = this.dataXfm.point(urx, ury);
/*  79 */         double trans = 5.5D * howWide;
/*  80 */         if (trans < 1.0D)
/*  81 */           trans = 1.0D;
/*  82 */         ll.translate((int)-trans, 0);
/*  83 */         ur.translate((int)(trans + 1.0D), 0);
/*  84 */         this.datasets[whichSet].getGc().fillRect(g, ll, ur);
/*     */       }
/*     */       else
/*     */       {
/*     */         double llx;
/*     */         double llx;
/*  87 */         if (this.unitScaling)
/*  88 */           llx = whichBar;
/*     */         else
/*  90 */           llx = cd.getX();
/*  91 */         double lly = close;
/*  92 */         double ury = cd.getHigh();
/*  93 */         double urx = llx;
/*  94 */         this.datasets[whichSet].getGc().drawLine(
/*  95 */           g, 
/*  96 */           this.dataXfm.point(llx, lly), 
/*  97 */           this.dataXfm.point(urx, ury));
/*  98 */         ury = open;
/*  99 */         lly = cd.getLow();
/* 100 */         this.datasets[whichSet].getGc().drawLine(
/* 101 */           g, 
/* 102 */           this.dataXfm.point(llx, lly), 
/* 103 */           this.dataXfm.point(urx, ury));
/* 104 */         lly = open;
/* 105 */         ury = close;
/* 106 */         ll = this.dataXfm.point(llx, lly);
/* 107 */         ur = this.dataXfm.point(urx, ury);
/* 108 */         double trans = 5.5D * howWide;
/* 109 */         if (trans < 1.0D)
/* 110 */           trans = 1.0D;
/* 111 */         ll.translate((int)-trans, 0);
/* 112 */         ur.translate((int)trans, 0);
/*     */ 
/* 119 */         this.datasets[whichSet].getGc().drawLine(g, ll.x, ll.y, ll.x, ur.y);
/* 120 */         this.datasets[whichSet].getGc().drawLine(g, ll.x, ur.y, ur.x, ur.y);
/* 121 */         this.datasets[whichSet].getGc().drawLine(g, ur.x, ur.y, ur.x, ll.y);
/* 122 */         this.datasets[whichSet].getGc().drawLine(g, ur.x, ll.y, ll.x, ll.y);
/*     */       }
/* 124 */       if ((this.useDisplayList) && (this.globals.useDisplayList))
/*     */       {
/* 126 */         if (individualColors) {
/* 127 */           this.globals.displayList.addRectangle(cd, ll, ur);
/* 128 */           this.globals.displayList.addRectangle(this.datasets[whichSet], ll, ur);
/*     */         }
/*     */         else {
/* 131 */           this.globals.displayList.addRectangle(this.datasets[whichSet], ll, ur);
/* 132 */           this.globals.displayList.addRectangle(cd, ll, ur);
/*     */         }
/* 134 */         this.globals.displayList.addRectangle(this, ll, ur);
/*     */       }
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException e)
/*     */     {
/*     */     }
/*     */     catch (ClassCastException ex) {
/* 141 */       double llx = this.datasets[whichSet].getDataElementAt(whichBar).getX();
/*     */ 
/* 143 */       double lly = this.baseline;
/* 144 */       double ury = this.datasets[whichSet].getDataElementAt(whichBar).getY();
/* 145 */       double urx = llx;
/* 146 */       this.datasets[whichSet].getGc().drawLine(
/* 147 */         g, 
/* 148 */         this.dataXfm.point(llx, lly), 
/* 149 */         this.dataXfm.point(urx, ury));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doBarLabel(Graphics g, double howWide, int whichSet, int whichBar)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void doDBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/* 173 */     doBar(g, howWide, whichSet, whichBar, individualColors);
/*     */   }
/*     */ 
/*     */   public void draw(Graphics g)
/*     */   {
/* 179 */     if (this.datasets[0] == null) {
/* 180 */       return;
/*     */     }
/* 182 */     this.numberOfPoints = this.datasets[0].getData().size();
/* 183 */     super.draw(g);
/*     */   }
/*     */ 
/*     */   public boolean getUnitScaling()
/*     */   {
/* 190 */     return this.unitScaling;
/*     */   }
/*     */ 
/*     */   public void setUnitScaling(boolean unitScaling)
/*     */   {
/* 200 */     this.unitScaling = unitScaling;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Candlestick
 * JD-Core Version:    0.6.2
 */