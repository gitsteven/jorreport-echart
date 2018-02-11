/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class LineChart extends _Chart
/*     */   implements LineInterface
/*     */ {
/*  24 */   boolean lineVisible = true;
/*     */ 
/*     */   public LineChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public LineChart(String s)
/*     */   {
/*  39 */     super(s);
/*     */   }
/*     */ 
/*     */   public void drawGraph() {
/*  43 */     if (this.canvas == null)
/*  44 */       return;
/*  45 */     drawGraph(this.canvas);
/*     */   }
/*     */   public void drawGraph(Graphics g) {
/*  48 */     if (g == null)
/*  49 */       return;
/*  50 */     super.drawGraph(g);
/*  51 */     this.background.draw(g);
/*  52 */     this.plotarea.draw(g);
/*  53 */     if (this.xAxisVisible)
/*  54 */       this.xAxis.draw(g);
/*  55 */     if (this.yAxisVisible)
/*  56 */       this.yAxis.draw(g);
/*  57 */     Line line = (Line)this.dataRepresentation;
/*  58 */     this.dataRepresentation.draw(g);
/*     */ 
/*  60 */     super.drawAxisOverlays(g);
/*  61 */     if (this.legendVisible)
/*  62 */       this.legend.draw(g); 
/*     */   }
/*     */ 
/*  65 */   public Line getLine() { return (Line)this.dataRepresentation; }
/*     */ 
/*     */ 
/*     */   public boolean getLineVisible()
/*     */   {
/*  72 */     return this.lineVisible;
/*     */   }
/*     */   protected void initAxes() {
/*  75 */     setXAxis(new Axis());
/*  76 */     this.xAxis.setSide(0);
/*  77 */     setYAxis(new Axis());
/*     */   }
/*     */ 
/*     */   protected void initChart() {
/*  81 */     initGlobals();
/*  82 */     setPlotarea(new Plotarea());
/*  83 */     setBackground(new Background());
/*  84 */     initDatasets();
/*  85 */     initAxes();
/*  86 */     Line line = new Line();
/*  87 */     setDataRepresentation(line);
/*  88 */     setLegend(new LineLegend());
/*  89 */     resize(640, 480);
/*     */   }
/*     */   public void setLine(Line l) {
/*  92 */     setDataRepresentation(l);
/*     */   }
/*     */ 
/*     */   public void setLineVisible(boolean onOff)
/*     */   {
/*  99 */     this.lineVisible = onOff;
/* 100 */     Line line = (Line)this.dataRepresentation;
/* 101 */     if (onOff)
/* 102 */       line.scatterPlot = false;
/*     */     else
/* 104 */       line.scatterPlot = true;
/*     */   }
/*     */ 
/*     */   public LineChart(String name, Locale locale)
/*     */   {
/* 115 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public LineChart(Locale locale)
/*     */   {
/* 125 */     super(locale);
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 132 */     applyGeneralProperty(map);
/*     */ 
/* 137 */     if (getLine().isScatterPlot()) {
/* 138 */       map.put("plotLinesOff", "true");
/*     */     }
/*     */ 
/* 153 */     if (getLine().getUseValueLabels())
/* 154 */       map.put("useValueLabels", "true");
/*     */     else {
/* 156 */       map.put("useValueLabels", "false");
/*     */     }
/*     */ 
/* 159 */     if (getDatasets()[0] != null) {
/* 160 */       map.put("lineLabelFont", ChartUtil.toString(getDatasets()[0].getLabelFont()));
/* 161 */       map.put("lineLabelColor", ChartUtil.toString(getDatasets()[0].getLabelColor()));
/*     */     }
/*     */ 
/* 165 */     if (getLine().getIndividualMarkers())
/* 166 */       map.put("individualMarkers", "true");
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.LineChart
 * JD-Core Version:    0.6.2
 */