/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class HLOC extends Bar
/*     */ {
/*  18 */   int numberOfPoints = 0;
/*     */ 
/*     */   public HLOC()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HLOC(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*     */   {
/*  35 */     super(d, xAx, yAx, subplot);
/*     */   }
/*     */ 
/*     */   protected void doBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/*  53 */     int offset = this.globals.getMaxX() / this.numberOfPoints;
/*  54 */     offset /= 2;
/*  55 */     offset = Math.max(offset, 2);
/*  56 */     offset = Math.min(offset, 5);
/*     */     try
/*     */     {
/*  59 */       CandlestickDatum dat = (CandlestickDatum)this.datasets[whichSet].getData().get(whichBar);
/*  60 */       if (dat.getLabel() == "D")
/*     */         return;
/*     */       double llx;
/*     */       double llx;
/*  62 */       if (!this.unitScaling)
/*  63 */         llx = dat.getX();
/*     */       else
/*  65 */         llx = whichBar;
/*  66 */       double lly = dat.getLow();
/*  67 */       double ury = dat.getHigh();
/*  68 */       double closeY = dat.getClose();
/*  69 */       double openY = dat.getOpen();
/*  70 */       urx = llx;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       double urx;
/*     */       return;
/*     */     }
/*     */     double ury;
/*     */     double urx;
/*     */     double openY;
/*     */     double closeY;
/*     */     double lly;
/*     */     double llx;
/*  75 */     Gc gc = this.datasets[whichSet].getGc();
/*  76 */     gc.drawLine(
/*  77 */       g, 
/*  78 */       this.dataXfm.point(llx, lly), 
/*  79 */       this.dataXfm.point(urx, ury));
/*  80 */     Point p1 = this.dataXfm.point(llx, closeY);
/*  81 */     Point p2 = new Point(p1.x + offset, p1.y);
/*  82 */     gc.drawLine(g, p1, p2);
/*  83 */     p1 = this.dataXfm.point(llx, openY);
/*  84 */     p2 = new Point(p1.x - offset, p1.y);
/*  85 */     gc.drawLine(g, p1, p2);
/*     */ 
/*  87 */     if ((this.useDisplayList) && (this.globals.getUseDisplayList()))
/*     */     {
/*  89 */       this.globals.getDisplayList().addLine(
/*  90 */         this.datasets[whichSet], 
/*  91 */         this.dataXfm.point(llx, lly), 
/*  92 */         this.dataXfm.point(urx, ury));
/*  93 */       this.globals.getDisplayList().addLine(
/*  94 */         this.datasets[whichSet].getDataElementAt(whichBar), 
/*  95 */         this.dataXfm.point(llx, lly), 
/*  96 */         this.dataXfm.point(urx, ury));
/*  97 */       this.globals.getDisplayList().addLine(
/*  98 */         this, 
/*  99 */         this.dataXfm.point(llx, lly), 
/* 100 */         this.dataXfm.point(urx, ury));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doBarLabel(Graphics g, double howWide, int whichSet, int whichBar)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void doDBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void draw(Graphics g)
/*     */   {
/* 130 */     this.numberOfPoints = this.datasets[0].getData().size();
/* 131 */     super.draw(g);
/*     */   }
/*     */ 
/*     */   public void setUnitScaling(boolean tf)
/*     */   {
/* 138 */     this.unitScaling = tf;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.HLOC
 * JD-Core Version:    0.6.2
 */