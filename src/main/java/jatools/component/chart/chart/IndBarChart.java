/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.util.Locale;
/*     */ 
/*     */ /** @deprecated */
/*     */ public class IndBarChart extends BarChart
/*     */ {
/*     */   public IndBarChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public IndBarChart(String s)
/*     */   {
/*  37 */     super(s);
/*     */   }
/*     */ 
/*     */   public void addDataset(String s, double[] x)
/*     */   {
/*  42 */     if (this.numberOfDatasets < MAX_DATASETS) {
/*  43 */       this.datasets[this.numberOfDatasets] = new Dataset(s, x, true, this.globals);
/*  44 */       this.numberOfDatasets += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addDataset(String s, double[] x, String[] dataLabels)
/*     */   {
/*  51 */     if (this.numberOfDatasets < MAX_DATASETS) {
/*  52 */       this.datasets[this.numberOfDatasets] = new Dataset(s, x, 
/*  53 */         dataLabels, true, this.globals);
/*  54 */       this.numberOfDatasets += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void drawGraph() {
/*  59 */     if (this.canvas == null)
/*  60 */       return;
/*  61 */     drawGraph(this.canvas);
/*     */   }
/*     */   public void drawGraph(Graphics g) {
/*  64 */     if (g == null)
/*  65 */       return;
/*  66 */     if (this.globals.useDisplayList)
/*  67 */       this.globals.displayList.clear();
/*  68 */     this.background.draw(g);
/*  69 */     this.plotarea.draw(g);
/*  70 */     if (this.xAxisVisible)
/*  71 */       this.xAxis.draw(g);
/*     */     else
/*  73 */       this.xAxis.scale();
/*  74 */     if (this.yAxisVisible)
/*  75 */       this.yAxis.draw(g);
/*     */     else
/*  77 */       this.yAxis.scale();
/*  78 */     this.bar.drawInd(g);
/*     */ 
/*  80 */     super.drawAxisOverlays(g);
/*  81 */     if (this.legendVisible)
/*  82 */       this.legend.draw(g); 
/*     */   }
/*     */ 
/*  85 */   protected void initAxes() { setXAxis(new LabelAxis());
/*  86 */     this.xAxis.setSide(1);
/*  87 */     this.xAxis.setBarScaling(true);
/*  88 */     setYAxis(new Axis());
/*  89 */     this.yAxis.setBarScaling(true);
/*  90 */     this.yAxis.setSide(0); }
/*     */ 
/*     */   protected void initChart() {
/*  93 */     initGlobals();
/*  94 */     setPlotarea(new Plotarea());
/*  95 */     setBackground(new Background());
/*  96 */     initDatasets();
/*  97 */     initAxes();
/*  98 */     this.bar = new HorizBar();
/*  99 */     setDataRepresentation(this.bar);
/* 100 */     setLegend(new PieLegend());
/* 101 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   public IndBarChart(String name, Locale locale)
/*     */   {
/* 112 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public IndBarChart(Locale locale)
/*     */   {
/* 122 */     super(locale);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.IndBarChart
 * JD-Core Version:    0.6.2
 */