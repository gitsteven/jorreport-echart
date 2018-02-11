/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class HLOCChart extends CandlestickChart
/*     */ {
/*     */   HLOC hloc;
/*     */ 
/*     */   public HLOCChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HLOCChart(String s)
/*     */   {
/*  36 */     super(s);
/*     */   }
/*     */ 
/*     */   public void drawGraph()
/*     */   {
/*  41 */     if (this.canvas == null)
/*  42 */       return;
/*  43 */     drawGraph(this.canvas);
/*     */   }
/*     */   public void drawGraph(Graphics g) {
/*  46 */     if (g == null) {
/*  47 */       return;
/*     */     }
/*  49 */     if (getUseDisplayList())
/*  50 */       getDisplayList().clear();
/*  51 */     this.background.draw(g);
/*  52 */     this.plotarea.draw(g);
/*     */ 
/*  54 */     if (this.xAxisVisible)
/*  55 */       this.xAxis.draw(g);
/*     */     else
/*  57 */       this.xAxis.scale();
/*  58 */     if (this.yAxisVisible)
/*  59 */       this.yAxis.draw(g);
/*     */     else
/*  61 */       this.yAxis.scale();
/*  62 */     this.hloc.draw(g);
/*     */ 
/*  64 */     super.drawAxisOverlays(g);
/*  65 */     if (this.legendVisible)
/*  66 */       this.legend.draw(g);
/*     */   }
/*     */ 
/*     */   protected void initAxes() {
/*  70 */     setXAxis(new DateAxis());
/*  71 */     setYAxis(new HiLoAxis());
/*     */ 
/*  73 */     this.xAxis.setSide(0);
/*  74 */     this.yAxis.setBarScaling(false);
/*  75 */     this.xAxis.setBarScaling(true);
/*     */   }
/*     */ 
/*     */   protected void initChart() {
/*  79 */     initGlobals();
/*  80 */     setPlotarea(new Plotarea());
/*  81 */     setBackground(new Background());
/*  82 */     initDatasets();
/*  83 */     initAxes();
/*  84 */     this.hloc = new HLOC();
/*  85 */     this.hloc.setUnitScaling(false);
/*  86 */     setDataRepresentation(this.hloc);
/*  87 */     setLegend(new LineLegend());
/*  88 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   public HLOCChart(String name, Locale locale)
/*     */   {
/* 100 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public HLOCChart(Locale locale)
/*     */   {
/* 110 */     super(locale);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.HLOCChart
 * JD-Core Version:    0.6.2
 */