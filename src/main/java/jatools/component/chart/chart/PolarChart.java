/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class PolarChart extends _Chart
/*     */ {
/*     */   Polar polar;
/*     */   PolarAxis axis;
/*  25 */   boolean lineVisible = true;
/*     */ 
/*     */   public PolarChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PolarChart(String s)
/*     */   {
/*  41 */     super(s);
/*     */   }
/*     */ 
/*     */   public synchronized void drawGraph() {
/*  45 */     if (this.canvas == null)
/*  46 */       return;
/*  47 */     drawGraph(this.canvas);
/*     */   }
/*     */   public synchronized void drawGraph(Graphics g) {
/*  50 */     if (g == null)
/*  51 */       return;
/*  52 */     super.drawGraph(g);
/*  53 */     this.background.draw(g);
/*  54 */     this.plotarea.draw(g);
/*  55 */     this.axis.draw(g);
/*  56 */     this.polar.draw(g);
/*  57 */     if (this.legendVisible)
/*  58 */       this.legend.draw(g);
/*     */   }
/*     */ 
/*     */   public boolean getLineVisible()
/*     */   {
/*  65 */     return this.lineVisible;
/*     */   }
/*     */ 
/*     */   public Polar getPolar()
/*     */   {
/*  73 */     return this.polar;
/*     */   }
/*     */ 
/*     */   public AxisInterface getXAxis()
/*     */   {
/*  80 */     return this.axis;
/*     */   }
/*     */ 
/*     */   public AxisInterface getYAxis()
/*     */   {
/*  86 */     return this.axis;
/*     */   }
/*     */   protected void initAxes() {
/*  89 */     this.axis = new PolarAxis(this.datasets, false, this.plotarea);
/*  90 */     this.yAxis = this.axis;
/*     */   }
/*     */ 
/*     */   protected void initChart() {
/*  94 */     initGlobals();
/*  95 */     setPlotarea(new Plotarea());
/*  96 */     setBackground(new Background());
/*  97 */     initDatasets();
/*  98 */     initAxes();
/*  99 */     this.polar = new Polar();
/* 100 */     setDataRepresentation(this.polar);
/* 101 */     setLegend(new LineLegend());
/* 102 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   public void resize(int newWidth, int newHeight)
/*     */   {
/* 108 */     super.resize(newWidth, newHeight);
/* 109 */     this.axis.center = new Point(newWidth / 2, newHeight / 2);
/*     */   }
/*     */ 
/*     */   public void setLineVisible(boolean onOff)
/*     */   {
/* 116 */     this.lineVisible = onOff;
/* 117 */     this.polar.setScatterPlot(!onOff);
/*     */   }
/*     */ 
/*     */   public PolarChart(String name, Locale locale)
/*     */   {
/* 128 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public PolarChart(Locale locale)
/*     */   {
/* 138 */     super(locale);
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 145 */     applyGeneralProperty(map);
/*     */ 
/* 147 */     if (getLineVisible()) {
/* 148 */       map.remove("plotLinesOff");
/* 149 */       map.put("plotLinesOn", "true");
/*     */     }
/*     */     else {
/* 152 */       map.remove("plotLinesOn");
/* 153 */       map.put("plotLinesOff", "true");
/*     */     }
/*     */ 
/* 156 */     if (getPolar().getLabelsOn()) {
/* 157 */       map.put("polarLabelsOn", "true");
/* 158 */       map.put("polarLabelAngle", String.valueOf(getPolar().getLabelAngle()));
/* 159 */       map.put("labelPrecision", String.valueOf(getPolar().getLabelPrecision()));
/* 160 */       map.put("polarLabelFormat", String.valueOf(getPolar().getLabelFormat()));
/* 161 */       map.put("polarLabelFont", ChartUtil.toString(getDatasets()[0].getLabelFont()));
/* 162 */       map.put("polarLabelColor", ChartUtil.toString(getDatasets()[0].getLabelColor()));
/*     */     }
/*     */ 
/* 165 */     if (((PolarAxis)getXAxis()).getManualSpoking()) {
/* 166 */       map.put("manualSpoking", "true");
/*     */     }
/*     */ 
/* 169 */     map.put("numSpokes", String.valueOf(((PolarAxis)getXAxis()).getNumSpokes()));
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.PolarChart
 * JD-Core Version:    0.6.2
 */