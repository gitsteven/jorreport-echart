/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class HorizHiLoBarChart extends BarChart
/*     */ {
/*     */   public HorizHiLoBarChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HorizHiLoBarChart(String s)
/*     */   {
/*  38 */     super(s);
/*     */   }
/*     */ 
/*     */   public void addDataset(String s, double[] y, double[] y2)
/*     */   {
/*  50 */     double[] x = new double[y.length];
/*     */ 
/*  52 */     if (this.numberOfDatasets < _Chart.MAX_DATASETS) {
/*  53 */       for (int i = 0; i < y.length; i++) {
/*  54 */         x[i] = i;
/*  55 */         y[i] -= y2[i];
/*     */       }
/*  57 */       this.datasets[this.numberOfDatasets] = 
/*  58 */         new Dataset(s, x, y, y2, this.numberOfDatasets, this.globals);
/*  59 */       this.numberOfDatasets += 1;
/*     */     }
/*     */     else {
/*  62 */       System.out.println("max datasets is " + _Chart.MAX_DATASETS);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addDataset(String s, double[] y, double[] y2, String[] dataLabels)
/*     */   {
/*  75 */     double[] x = new double[y.length];
/*     */ 
/*  77 */     if (this.numberOfDatasets < _Chart.MAX_DATASETS) {
/*  78 */       for (int i = 0; i < y.length; i++) {
/*  79 */         x[i] = i;
/*     */       }
/*     */ 
/*  82 */       this.datasets[this.numberOfDatasets] = 
/*  83 */         new Dataset(s, x, y, y2, dataLabels, this.numberOfDatasets, this.globals);
/*  84 */       this.numberOfDatasets += 1;
/*     */     }
/*     */     else {
/*  87 */       System.out.println("max datasets is " + _Chart.MAX_DATASETS);
/*     */     }
/*     */   }
/*  90 */   protected void initAxes() { setXAxis(new LabelAxis());
/*  91 */     this.xAxis.setBarScaling(true);
/*  92 */     this.xAxis.setSide(1);
/*     */ 
/*  94 */     setYAxis(new HiLoAxis());
/*  95 */     this.yAxis.setBarScaling(true);
/*  96 */     this.yAxis.setSide(0); }
/*     */ 
/*     */   protected void initChart()
/*     */   {
/* 100 */     initGlobals();
/* 101 */     setPlotarea(new Plotarea());
/* 102 */     setBackground(new Background());
/* 103 */     initDatasets();
/* 104 */     initAxes();
/* 105 */     this.bar = new HorizHiLoBar();
/* 106 */     setDataRepresentation(this.bar);
/* 107 */     setLegend(new Legend());
/* 108 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   public HorizHiLoBarChart(String name, Locale locale)
/*     */   {
/* 119 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public HorizHiLoBarChart(Locale locale)
/*     */   {
/* 129 */     super(locale);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.HorizHiLoBarChart
 * JD-Core Version:    0.6.2
 */