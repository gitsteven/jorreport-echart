/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ 
/*     */ public class Stick extends Bar
/*     */ {
/*  17 */   int width = 0;
/*     */ 
/*     */   public Stick()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Stick(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*     */   {
/*  39 */     super(d, xAx, yAx, subplot);
/*     */   }
/*     */ 
/*     */   protected void doBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/*     */     try
/*     */     {
/*  55 */       dat = this.datasets[whichSet].getDataElementAt(whichBar);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       Datum dat;
/*     */       return;
/*     */     }
/*     */     Datum dat;
/*     */     double llx;
/*     */     double llx;
/*  60 */     if (this.unitScaling)
/*  61 */       llx = whichBar;
/*     */     else
/*  63 */       llx = dat.x;
/*  64 */     double lly = this.baseline;
/*  65 */     double ury = dat.y;
/*  66 */     if (ury == (-1.0D / 0.0D))
/*  67 */       return;
/*  68 */     double urx = llx;
/*  69 */     if (this.width == 0) {
/*  70 */       Point p1 = this.dataXfm.point(llx, lly);
/*  71 */       Point p2 = this.dataXfm.point(urx, ury);
/*  72 */       if (!individualColors)
/*  73 */         this.datasets[whichSet].gc.drawLine(g, p1, p2);
/*     */       else
/*  75 */         dat.gc.drawLine(g, p1, p2);
/*  76 */       if ((this.useDisplayList) && (this.globals.useDisplayList))
/*     */       {
/*  78 */         if (individualColors) {
/*  79 */           this.globals.displayList.addLine(dat, p1, p2);
/*  80 */           this.globals.displayList.addLine(this.datasets[whichSet], p1, p2);
/*     */         }
/*     */         else {
/*  83 */           this.globals.displayList.addLine(this.datasets[whichSet], p1, p2);
/*  84 */           this.globals.displayList.addLine(dat, p1, p2);
/*     */         }
/*  86 */         this.globals.displayList.addLine(this, p1, p2);
/*     */       }
/*     */     }
/*     */     else {
/*  90 */       Point ll = this.dataXfm.point(llx, lly);
/*  91 */       Point ur = this.dataXfm.point(urx, ury);
/*  92 */       ll.translate(-this.width, 0);
/*  93 */       ur.translate(this.width, 0);
/*  94 */       if (!individualColors)
/*  95 */         this.datasets[whichSet].gc.fillRect(g, ll, ur);
/*     */       else {
/*  97 */         dat.gc.fillRect(g, ll, ur);
/*     */       }
/*  99 */       if ((this.useDisplayList) && (this.globals.useDisplayList))
/*     */       {
/* 101 */         if (individualColors) {
/* 102 */           this.globals.displayList.addRectangle(dat, ll, ur);
/* 103 */           this.globals.displayList.addRectangle(this.datasets[whichSet], ll, ur);
/*     */         }
/*     */         else {
/* 106 */           this.globals.displayList.addRectangle(this.datasets[whichSet], ll, ur);
/* 107 */           this.globals.displayList.addRectangle(dat, ll, ur);
/*     */         }
/* 109 */         this.globals.displayList.addRectangle(this, ll, ur);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doBarLabel(Graphics g, double howWide, int whichSet, int whichBar)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void doDBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*     */   {
/* 134 */     doBar(g, howWide, whichSet, whichBar, individualColors);
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/* 141 */     return this.width;
/*     */   }
/*     */ 
/*     */   public void setWidth(int width)
/*     */   {
/* 149 */     this.width = width;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Stick
 * JD-Core Version:    0.6.2
 */