/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.util.Locale;
/*     */ 
/*     */ /** @deprecated */
/*     */ public class IndColumnChart extends BarChart
/*     */ {
/*     */   public IndColumnChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public IndColumnChart(String s)
/*     */   {
/*  39 */     super(s);
/*     */   }
/*     */ 
/*     */   public void addDataset(String s, double[] x)
/*     */   {
/*  44 */     if (this.numberOfDatasets < MAX_DATASETS) {
/*  45 */       this.datasets[this.numberOfDatasets] = new Dataset(s, x, true, this.globals);
/*  46 */       this.numberOfDatasets += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addDataset(String s, double[] x, String[] dataLabels)
/*     */   {
/*  54 */     if (this.numberOfDatasets < MAX_DATASETS) {
/*  55 */       this.datasets[this.numberOfDatasets] = new Dataset(s, x, 
/*  56 */         dataLabels, true, this.globals);
/*  57 */       this.numberOfDatasets += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void drawGraph()
/*     */   {
/*  63 */     if (this.canvas == null)
/*  64 */       return;
/*  65 */     drawGraph(this.canvas);
/*     */   }
/*     */   public void drawGraph(Graphics g) {
/*  68 */     if (g == null)
/*  69 */       return;
/*  70 */     if (this.globals.useDisplayList)
/*  71 */       this.globals.displayList.clear();
/*  72 */     this.background.draw(g);
/*  73 */     this.plotarea.draw(g);
/*  74 */     if (this.xAxisVisible)
/*  75 */       this.xAxis.draw(g);
/*     */     else
/*  77 */       this.xAxis.scale();
/*  78 */     if (this.yAxisVisible)
/*  79 */       this.yAxis.draw(g);
/*     */     else
/*  81 */       this.yAxis.scale();
/*  82 */     ((Bar)this.dataRepresentation).drawInd(g);
/*     */ 
/*  84 */     super.drawAxisOverlays(g);
/*  85 */     if (this.legendVisible)
/*  86 */       this.legend.draw(g); 
/*     */   }
/*     */ 
/*  89 */   protected void initChart() { super.initChart();
/*  90 */     setLegend(new PieLegend());
/*     */   }
/*     */ 
/*     */   public IndColumnChart(String name, Locale locale)
/*     */   {
/* 101 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public IndColumnChart(Locale locale)
/*     */   {
/* 111 */     super(locale);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.IndColumnChart
 * JD-Core Version:    0.6.2
 */