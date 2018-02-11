/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Point;
/*     */ 
/*     */ public class GanttAxis extends Axis
/*     */   implements AxisInterface
/*     */ {
/*     */   public GanttAxis()
/*     */   {
/*     */   }
/*     */ 
/*     */   public GanttAxis(Dataset[] dsets, boolean xAxis, Plotarea plt)
/*     */   {
/*  34 */     super(dsets, xAxis, plt);
/*     */   }
/*     */ 
/*     */   protected String getLabel(double dummy, int index)
/*     */   {
/*     */     try
/*     */     {
/*  43 */       return this.datasets[index].getName();
/*     */     } catch (Exception e) {
/*     */     }
/*  46 */     return "none";
/*     */   }
/*     */ 
/*     */   private synchronized boolean ganttScale()
/*     */   {
/*  54 */     int nmsets = datasetsInUse();
/*     */ 
/*  56 */     this.axisStart = 0.0D;
/*     */ 
/*  58 */     if (this.userAxisStart != null) {
/*  59 */       this.axisStart = this.userAxisStart.doubleValue();
/*     */     }
/*  61 */     if (this.userAxisEnd != null) {
/*  62 */       nmsets = this.userAxisEnd.intValue();
/*     */     }
/*     */ 
/*  65 */     if (getBarScaling()) {
/*  66 */       this.axisStart -= 1.0D;
/*  67 */       this.axisEnd = nmsets;
/*  68 */       this.numLabels = nmsets;
/*     */ 
/*  70 */       this.numMajTicks = (this.numLabels + 1);
/*     */     }
/*     */     else {
/*  73 */       this.axisEnd = (nmsets - 1.0D);
/*     */ 
/*  76 */       this.numLabels = (nmsets - 1);
/*  77 */       this.numMajTicks = this.numLabels;
/*     */     }
/*  79 */     if (this.axisStart == this.axisEnd) {
/*  80 */       this.numLabels = (this.numMajTicks = this.numGrids = this.numMinTicks = 1);
/*  81 */       this.axisEnd += 1.0D;
/*     */     }
/*  83 */     this.numGrids = this.numMajTicks;
/*  84 */     this.numMinTicks = (this.numMajTicks * 2);
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */   public void scale()
/*     */   {
/*  91 */     ganttScale();
/*     */   }
/*     */ 
/*     */   public void setLogScaling(boolean yesNo)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected int whereOnAxis(int whichStep, int whichElement)
/*     */   {
/* 111 */     if ((whichElement == 4) || (whichElement == 3) || (whichElement == 2)) {
/* 112 */       return super.whereOnAxis(whichStep, whichElement);
/*     */     }
/* 114 */     if ((getSide() == 0) || (getSide() == 2)) {
/* 115 */       float increment = getIncrement(this.endPoint.x, this.startPoint.x, this.numMajTicks);
/* 116 */       if (getBarScaling()) {
/* 117 */         return this.startPoint.x + (int)(increment * (whichStep + 1));
/*     */       }
/* 119 */       return this.startPoint.x + (int)(increment * whichStep);
/*     */     }
/*     */ 
/* 122 */     float increment = getIncrement(this.endPoint.y, this.startPoint.y, this.numMajTicks);
/* 123 */     if (getBarScaling()) {
/* 124 */       return this.startPoint.y + (int)(increment * (whichStep + 1));
/*     */     }
/* 126 */     return this.startPoint.y + (int)(increment * whichStep);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.GanttAxis
 * JD-Core Version:    0.6.2
 */