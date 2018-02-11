/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class GanttChart extends HorizHiLoBarChart
/*     */ {
/*     */   HiLoDateAxis timeAxis;
/*     */   GanttBar ganttBar;
/*     */ 
/*     */   public GanttChart()
/*     */   {
/*  19 */     initChart();
/*  20 */     initializeLocalVars();
/*     */   }
/*     */ 
/*     */   public GanttChart(String name)
/*     */   {
/*  30 */     super(name);
/*  31 */     initializeLocalVars();
/*     */   }
/*     */ 
/*     */   public GanttChart(String name, Locale locale)
/*     */   {
/*  42 */     super(name, locale);
/*  43 */     initializeLocalVars();
/*     */   }
/*     */ 
/*     */   public GanttChart(Locale locale)
/*     */   {
/*  53 */     super(locale);
/*  54 */     initializeLocalVars();
/*     */   }
/*     */ 
/*     */   public void addDataset(String s, double[] y, double[] y2)
/*     */   {
/*  62 */     double[] x = new double[y.length];
/*     */ 
/*  64 */     if (this.numberOfDatasets < MAX_DATASETS) {
/*  65 */       for (int i = 0; i < y.length; i++) {
/*  66 */         x[i] = i;
/*     */       }
/*  68 */       getDatasets()[this.numberOfDatasets] = 
/*  69 */         new Dataset(s, x, y, y2, this.numberOfDatasets, getGlobals());
/*  70 */       this.numberOfDatasets += 1;
/*     */     }
/*     */     else {
/*  73 */       System.out.println("max datasets is " + MAX_DATASETS);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addDataset(String s, double[] y, double[] y2, String[] dataLabels)
/*     */   {
/*  81 */     double[] x = new double[y.length];
/*     */ 
/*  83 */     if (this.numberOfDatasets < MAX_DATASETS) {
/*  84 */       for (int i = 0; i < y.length; i++) {
/*  85 */         x[i] = i;
/*     */       }
/*  87 */       getDatasets()[this.numberOfDatasets] = 
/*  88 */         new Dataset(s, x, y, y2, dataLabels, this.numberOfDatasets, getGlobals());
/*  89 */       this.numberOfDatasets += 1;
/*     */     }
/*     */     else {
/*  92 */       System.out.println("max datasets is " + MAX_DATASETS);
/*     */     }
/*     */   }
/*     */ 
/*  96 */   public Bar getBar() { return this.ganttBar; }
/*     */ 
/*     */   public void drawGraph(Graphics g)
/*     */   {
/* 100 */     if (g == null) {
/* 101 */       return;
/*     */     }
/* 103 */     if (getUseDisplayList())
/* 104 */       this.globals.getDisplayList().clear();
/* 105 */     getBackground().draw(g);
/* 106 */     getPlotarea().draw(g);
/* 107 */     getXAxis().draw(g);
/* 108 */     this.timeAxis.draw(g);
/* 109 */     if (getIndividualColors())
/* 110 */       this.ganttBar.drawInd(g);
/*     */     else {
/* 112 */       this.ganttBar.draw(g);
/*     */     }
/* 114 */     super.drawAxisOverlays(g);
/* 115 */     if (isLegendVisible())
/* 116 */       getLegend().draw(g);
/*     */   }
/*     */ 
/*     */   public AxisInterface getYAxis() {
/* 120 */     return this.timeAxis;
/*     */   }
/*     */ 
/*     */   private void initializeLocalVars() {
/* 124 */     this.xAxis = new GanttAxis();
/* 125 */     this.xAxis.setSide(1);
/* 126 */     this.xAxis.setBarScaling(true);
/* 127 */     setXAxis(this.xAxis);
/* 128 */     this.timeAxis = new HiLoDateAxis(getDatasets(), false, getPlotarea());
/* 129 */     this.timeAxis.setBarScaling(false);
/* 130 */     this.timeAxis.setSide(0);
/* 131 */     this.ganttBar = new GanttBar(getDatasets(), getXAxis(), this.timeAxis, getPlotarea());
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.GanttChart
 * JD-Core Version:    0.6.2
 */