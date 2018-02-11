/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class HiLoCloseChart extends _Chart
/*     */ {
/*     */   HiLoClose hiLoClose;
/*     */ 
/*     */   public HiLoCloseChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HiLoCloseChart(String s)
/*     */   {
/*  37 */     super(s);
/*     */   }
/*     */ 
/*     */   public void drawGraph() {
/*  41 */     if (this.canvas == null)
/*  42 */       return;
/*  43 */     drawGraph(this.canvas);
/*     */   }
/*     */   public void drawGraph(Graphics g) {
/*  46 */     if (g == null)
/*  47 */       return;
/*  48 */     super.drawGraph(g);
/*  49 */     this.background.draw(g);
/*  50 */     this.plotarea.draw(g);
/*  51 */     if (this.xAxisVisible)
/*  52 */       this.xAxis.draw(g);
/*     */     else
/*  54 */       this.xAxis.scale();
/*  55 */     if (this.yAxisVisible)
/*  56 */       this.yAxis.draw(g);
/*     */     else
/*  58 */       this.yAxis.scale();
/*  59 */     this.hiLoClose.draw(g);
/*     */ 
/*  61 */     super.drawAxisOverlays(g);
/*  62 */     if (this.legendVisible)
/*  63 */       this.legend.draw(g);
/*     */   }
/*     */ 
/*     */   public HiLoClose getHiLoClose()
/*     */   {
/*  71 */     return this.hiLoClose;
/*     */   }
/*     */   protected void initAxes() {
/*  74 */     setXAxis(new DateAxis());
/*  75 */     setYAxis(new HiLoAxis());
/*     */ 
/*  77 */     this.xAxis.setBarScaling(false);
/*  78 */     this.xAxis.setSide(0);
/*  79 */     this.yAxis.setBarScaling(false);
/*     */   }
/*     */ 
/*     */   protected void initChart() {
/*  83 */     initGlobals();
/*  84 */     setPlotarea(new Plotarea());
/*  85 */     setBackground(new Background());
/*  86 */     initDatasets();
/*  87 */     initAxes();
/*  88 */     this.hiLoClose = new HiLoClose();
/*  89 */     this.hiLoClose.unitScaling = false;
/*  90 */     setDataRepresentation(this.hiLoClose);
/*  91 */     setLegend(new Legend());
/*  92 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   public void setHiLoClose(HiLoClose b)
/*     */   {
/* 100 */     this.hiLoClose = b;
/* 101 */     setDataRepresentation(b);
/*     */   }
/*     */ 
/*     */   public HiLoCloseChart(String name, Locale locale)
/*     */   {
/* 112 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public HiLoCloseChart(Locale locale)
/*     */   {
/* 122 */     super(locale);
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 129 */     applyGeneralProperty(map);
/*     */ 
/* 131 */     initDateAxis((DateAxis)getXAxis(), map);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.HiLoCloseChart
 * JD-Core Version:    0.6.2
 */