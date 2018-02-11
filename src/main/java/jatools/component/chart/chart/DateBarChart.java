/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class DateBarChart extends _Chart
/*     */   implements BarInterface
/*     */ {
/*     */   Bar bar;
/*  23 */   boolean individualColors = false;
/*     */ 
/*     */   public DateBarChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DateBarChart(String s)
/*     */   {
/*  38 */     super(s);
/*     */   }
/*     */ 
/*     */   public void drawGraph()
/*     */   {
/*  44 */     if (this.canvas == null) {
/*  45 */       return;
/*     */     }
/*  47 */     drawGraph(this.canvas);
/*     */   }
/*     */ 
/*     */   public void drawGraph(Graphics g)
/*     */   {
/*  53 */     if (g == null)
/*  54 */       return;
/*  55 */     super.drawGraph(g);
/*  56 */     this.background.draw(g);
/*  57 */     this.plotarea.draw(g);
/*  58 */     if (this.xAxisVisible)
/*  59 */       this.xAxis.draw(g);
/*     */     else
/*  61 */       this.xAxis.scale();
/*  62 */     if (this.yAxisVisible)
/*  63 */       this.yAxis.draw(g);
/*     */     else
/*  65 */       this.yAxis.scale();
/*  66 */     if (this.individualColors)
/*  67 */       this.bar.drawInd(g);
/*     */     else {
/*  69 */       this.bar.draw(g);
/*     */     }
/*  71 */     super.drawAxisOverlays(g);
/*  72 */     if (this.legendVisible)
/*  73 */       this.legend.draw(g);
/*     */   }
/*     */ 
/*     */   public Bar getBar()
/*     */   {
/*  81 */     return this.bar;
/*     */   }
/*     */ 
/*     */   public boolean getIndividualColors()
/*     */   {
/*  89 */     return this.individualColors;
/*     */   }
/*     */ 
/*     */   protected void initAxes()
/*     */   {
/*  95 */     setXAxis(new DateAxis());
/*  96 */     this.xAxis.setBarScaling(true);
/*  97 */     this.xAxis.setSide(1);
/*  98 */     setYAxis(new Axis());
/*  99 */     this.yAxis.setBarScaling(true);
/* 100 */     this.yAxis.setSide(0);
/*     */   }
/*     */ 
/*     */   protected void initChart()
/*     */   {
/* 107 */     initGlobals();
/* 108 */     setPlotarea(new Plotarea());
/* 109 */     setBackground(new Background());
/* 110 */     initDatasets();
/* 111 */     initAxes();
/* 112 */     this.bar = new HorizBar();
/* 113 */     this.bar.unitScaling = false;
/* 114 */     setDataRepresentation(this.bar);
/* 115 */     setLegend(new Legend());
/* 116 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   public void setBar(Bar b)
/*     */   {
/* 124 */     this.bar = b;
/* 125 */     setDataRepresentation(b);
/*     */   }
/*     */ 
/*     */   public void setIndividualColors(boolean ind)
/*     */   {
/* 133 */     this.individualColors = ind;
/* 134 */     if (ind)
/* 135 */       setLegend(new PieLegend());
/*     */     else
/* 137 */       setLegend(new Legend());
/*     */   }
/*     */ 
/*     */   public DateBarChart(String name, Locale locale)
/*     */   {
/* 148 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public DateBarChart(Locale locale)
/*     */   {
/* 158 */     super(locale);
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 165 */     applyGeneralProperty(map);
/*     */ 
/* 167 */     initDateAxis((DateAxis)getXAxis(), map);
/*     */ 
/* 169 */     if (getBar().getLabelsOn()) {
/* 170 */       map.put("barLabelsOn", "true");
/*     */     }
/*     */ 
/* 173 */     map.put("barBaseline", String.valueOf(getBar().getBaseline()));
/*     */ 
/* 175 */     map.put("barClusterWidth", String.valueOf(getBar().getClusterWidth()));
/*     */ 
/* 177 */     map.put("barLabelAngle", String.valueOf(getBar().getLabelAngle()));
/*     */ 
/* 179 */     if (getIndividualColors()) {
/* 180 */       map.put("individualColors", "true");
/*     */     }
/*     */ 
/* 183 */     if (getBar().getUseValueLabels()) {
/* 184 */       map.put("useValueLabels", "true");
/*     */     }
/* 186 */     map.put("barLabelFont", ChartUtil.toString(getDatasets()[0].getLabelFont()));
/* 187 */     map.put("barLabelColor", ChartUtil.toString(getDatasets()[0].getLabelColor()));
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.DateBarChart
 * JD-Core Version:    0.6.2
 */