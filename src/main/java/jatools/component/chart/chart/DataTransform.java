/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DataTransform
/*     */   implements Serializable
/*     */ {
/*     */   private Transform dataXfm;
/*     */   private _Chart myChart;
/*     */   private double xAxisStart;
/*     */   private double xAxisEnd;
/*     */   private double yAxisStart;
/*     */   private double yAxisEnd;
/*     */   private int chartMaxX;
/*     */   private int chartMaxY;
/*     */ 
/*     */   public DataTransform(_Chart c)
/*     */   {
/*  29 */     this.myChart = c;
/*  30 */     buildTransform();
/*     */   }
/*     */ 
/*     */   private void buildTransform() {
/*  34 */     this.xAxisStart = this.myChart.getXAxis().getAxisStart();
/*  35 */     this.xAxisEnd = this.myChart.getXAxis().getAxisEnd();
/*  36 */     this.yAxisStart = this.myChart.getYAxis().getAxisStart();
/*  37 */     this.yAxisEnd = this.myChart.getYAxis().getAxisEnd();
/*     */ 
/*  39 */     this.chartMaxX = this.myChart.globals.maxX;
/*  40 */     this.chartMaxY = this.myChart.globals.maxY;
/*     */ 
/*  44 */     int ySide = this.myChart.getYAxis().getSide();
/*  45 */     int xSide = this.myChart.getXAxis().getSide();
/*     */ 
/*  47 */     if (((ySide == 0) || (ySide == 2)) && ((xSide == 1) || (xSide == 3)))
/*     */     {
/*  50 */       this.dataXfm = new Transform(this.yAxisStart, this.xAxisStart, 
/*  51 */         this.yAxisEnd, this.xAxisEnd, 
/*  52 */         this.myChart.plotarea.transform.point(this.myChart.plotarea.llX, this.myChart.plotarea.llY), 
/*  53 */         this.myChart.plotarea.transform.point(this.myChart.plotarea.urX, this.myChart.plotarea.urY));
/*     */ 
/*  55 */       this.dataXfm.logXScaling = this.myChart.getYAxis().getLogScaling();
/*  56 */       this.dataXfm.logYScaling = this.myChart.getXAxis().getLogScaling();
/*     */     }
/*     */     else {
/*  59 */       this.dataXfm = new Transform(this.xAxisStart, this.yAxisStart, 
/*  60 */         this.xAxisEnd, this.yAxisEnd, 
/*  61 */         this.myChart.plotarea.transform.point(this.myChart.plotarea.llX, this.myChart.plotarea.llY), 
/*  62 */         this.myChart.plotarea.transform.point(this.myChart.plotarea.urX, this.myChart.plotarea.urY));
/*     */ 
/*  64 */       this.dataXfm.logXScaling = this.myChart.getXAxis().getLogScaling();
/*  65 */       this.dataXfm.logYScaling = this.myChart.getYAxis().getLogScaling();
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean checkDirty() {
/*  70 */     if ((this.xAxisStart != this.myChart.getXAxis().getAxisStart()) || 
/*  71 */       (this.xAxisEnd != this.myChart.getXAxis().getAxisEnd()) || 
/*  72 */       (this.yAxisStart != this.myChart.getYAxis().getAxisStart()) || 
/*  73 */       (this.yAxisEnd != this.myChart.getYAxis().getAxisEnd()) || 
/*  74 */       (this.chartMaxX != this.myChart.globals.maxX) || 
/*  75 */       (this.chartMaxY != this.myChart.globals.maxY))
/*     */     {
/*  77 */       return true;
/*  78 */     }return false;
/*     */   }
/*     */ 
/*     */   public Point datumToPoint(Datum d)
/*     */   {
/*  85 */     if (checkDirty()) {
/*  86 */       buildTransform();
/*     */     }
/*  88 */     Point p = this.dataXfm.point(d.x, d.y);
/*  89 */     p.y = (this.myChart.globals.maxY - p.y);
/*  90 */     return p;
/*     */   }
/*     */ 
/*     */   public Datum pointToDatum(Point p)
/*     */   {
/*  98 */     if (checkDirty())
/*  99 */       buildTransform();
/* 100 */     return new Datum(this.dataXfm.xValue(p.x), 
/* 101 */       this.dataXfm.yValue(this.myChart.globals.maxY - p.y), 
/* 102 */       this.myChart.globals);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void resize()
/*     */   {
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.DataTransform
 * JD-Core Version:    0.6.2
 */