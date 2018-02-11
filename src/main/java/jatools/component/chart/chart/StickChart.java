/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class StickChart extends _Chart
/*     */ {
/*     */   Stick stick;
/*     */ 
/*     */   public StickChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public StickChart(String s)
/*     */   {
/*  35 */     super(s);
/*     */   }
/*     */ 
/*     */   public void drawGraph() {
/*  39 */     if (this.canvas == null)
/*  40 */       return;
/*  41 */     drawGraph(this.canvas);
/*     */   }
/*     */   public void drawGraph(Graphics g) {
/*  44 */     if (g == null)
/*  45 */       return;
/*  46 */     super.drawGraph(g);
/*  47 */     this.background.draw(g);
/*  48 */     this.plotarea.draw(g);
/*  49 */     if (this.xAxisVisible)
/*  50 */       this.xAxis.draw(g);
/*     */     else
/*  52 */       this.xAxis.scale();
/*  53 */     if (this.yAxisVisible)
/*  54 */       this.yAxis.draw(g);
/*     */     else
/*  56 */       this.yAxis.scale();
/*  57 */     if ((this.xAxisVisible) && (((Axis)this.xAxis).lineVis))
/*  58 */       ((Axis)this.xAxis).drawLine(g);
/*  59 */     this.stick.draw(g);
/*  60 */     if (this.legendVisible)
/*  61 */       this.legend.draw(g);
/*     */   }
/*     */ 
/*     */   public Stick getStick()
/*     */   {
/*  70 */     return this.stick;
/*     */   }
/*     */ 
/*     */   public AxisInterface getXAxis()
/*     */   {
/*  77 */     return this.xAxis;
/*     */   }
/*     */ 
/*     */   public AxisInterface getYAxis()
/*     */   {
/*  84 */     return this.yAxis;
/*     */   }
/*     */   protected void initAxes() {
/*  87 */     setXAxis(new DateAxis());
/*  88 */     this.xAxis.setSide(0);
/*  89 */     setYAxis(new Axis());
/*  90 */     this.yAxis.setBarScaling(true);
/*     */   }
/*     */ 
/*     */   protected void initChart() {
/*  94 */     initGlobals();
/*  95 */     setPlotarea(new Plotarea());
/*  96 */     setBackground(new Background());
/*  97 */     initDatasets();
/*  98 */     initAxes();
/*  99 */     this.stick = new Stick();
/* 100 */     this.stick.unitScaling = false;
/* 101 */     setDataRepresentation(this.stick);
/* 102 */     setLegend(new Legend());
/* 103 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   public void setStick(Stick s)
/*     */   {
/* 111 */     this.stick = s;
/* 112 */     setDataRepresentation(s);
/*     */   }
/*     */ 
/*     */   public StickChart(String name, Locale locale)
/*     */   {
/* 123 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public StickChart(Locale locale)
/*     */   {
/* 133 */     super(locale);
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 140 */     applyGeneralProperty(map);
/*     */ 
/* 142 */     initDateAxis((DateAxis)getXAxis(), map);
/*     */ 
/* 144 */     map.put("barBaseline", String.valueOf(getStick().getBaseline()));
/*     */ 
/* 146 */     map.put("barWidth", String.valueOf(getStick().getWidth()));
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.StickChart
 * JD-Core Version:    0.6.2
 */