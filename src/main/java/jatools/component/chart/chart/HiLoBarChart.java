/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.util.Map;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class HiLoBarChart extends BarChart
/*     */ {
/*     */   public HiLoBarChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HiLoBarChart(String s)
/*     */   {
/*  41 */     super(s);
/*     */   }
/*     */ 
/*     */   public void addDataset(String s, double[] y, double[] y2)
/*     */   {
/*  53 */     double[] x = new double[y.length];
/*     */ 
/*  55 */     if (this.numberOfDatasets < _Chart.MAX_DATASETS) {
/*  56 */       for (int i = 0; i < y.length; i++) {
/*  57 */         x[i] = i;
/*  58 */         y[i] -= y2[i];
/*     */       }
/*  60 */       this.datasets[this.numberOfDatasets] = 
/*  61 */         new Dataset(s, x, y, y2, this.numberOfDatasets, this.globals);
/*  62 */       this.numberOfDatasets += 1;
/*     */     }
/*     */     else {
/*  65 */       System.out.println("max datasets is " + _Chart.MAX_DATASETS);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addDataset(String s, double[] y, double[] y2, String[] dataLabels)
/*     */   {
/*  78 */     double[] x = new double[y.length];
/*     */ 
/*  80 */     if (this.numberOfDatasets < _Chart.MAX_DATASETS) {
/*  81 */       for (int i = 0; i < y.length; i++) {
/*  82 */         x[i] = i;
/*     */       }
/*     */ 
/*  85 */       this.datasets[this.numberOfDatasets] = 
/*  86 */         new Dataset(s, x, y, y2, dataLabels, this.numberOfDatasets, this.globals);
/*  87 */       this.numberOfDatasets += 1;
/*     */     }
/*     */     else {
/*  90 */       System.out.println("max datasets is " + _Chart.MAX_DATASETS);
/*     */     }
/*     */   }
/*  93 */   protected void initAxes() { setXAxis(new LabelAxis());
/*  94 */     this.xAxis.setSide(0);
/*  95 */     this.xAxis.setBarScaling(true);
/*  96 */     setYAxis(new HiLoAxis()); }
/*     */ 
/*     */   protected void initChart()
/*     */   {
/* 100 */     initGlobals();
/* 101 */     setPlotarea(new Plotarea());
/* 102 */     setBackground(new Background());
/* 103 */     initDatasets();
/* 104 */     initAxes();
/* 105 */     this.bar = new HiLoBar();
/* 106 */     setDataRepresentation(this.bar);
/* 107 */     setLegend(new Legend());
/* 108 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   public HiLoBarChart(String name, Locale locale)
/*     */   {
/* 120 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public HiLoBarChart(Locale locale)
/*     */   {
/* 130 */     super(locale);
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 135 */     applyGeneralProperty(map);
/*     */ 
/* 137 */     super.applyProperties(map);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.HiLoBarChart
 * JD-Core Version:    0.6.2
 */