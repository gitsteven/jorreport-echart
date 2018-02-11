/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class DateColumnChart extends _Chart
/*     */   implements BarInterface
/*     */ {
/*     */   Bar bar;
/*  24 */   boolean individualColors = false;
/*     */ 
/*     */   public DateColumnChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DateColumnChart(String s)
/*     */   {
/*  39 */     super(s);
/*     */   }
/*     */ 
/*     */   public void drawGraph()
/*     */   {
/*  45 */     if (this.canvas == null) {
/*  46 */       return;
/*     */     }
/*  48 */     drawGraph(this.canvas);
/*     */   }
/*     */ 
/*     */   public void drawGraph(Graphics g)
/*     */   {
/*  54 */     if (g == null)
/*  55 */       return;
/*  56 */     super.drawGraph(g);
/*  57 */     this.background.draw(g);
/*  58 */     this.plotarea.draw(g);
/*  59 */     if (this.xAxisVisible)
/*  60 */       this.xAxis.draw(g);
/*     */     else
/*  62 */       this.xAxis.scale();
/*  63 */     if (this.yAxisVisible)
/*  64 */       this.yAxis.draw(g);
/*     */     else
/*  66 */       this.yAxis.scale();
/*  67 */     if (this.individualColors)
/*  68 */       this.bar.drawInd(g);
/*     */     else {
/*  70 */       this.bar.draw(g);
/*     */     }
/*  72 */     super.drawAxisOverlays(g);
/*  73 */     if (this.legendVisible)
/*  74 */       this.legend.draw(g);
/*     */   }
/*     */ 
/*     */   public Bar getBar()
/*     */   {
/*  82 */     return this.bar;
/*     */   }
/*     */ 
/*     */   public boolean getIndividualColors()
/*     */   {
/*  90 */     return this.individualColors;
/*     */   }
/*     */ 
/*     */   protected void initAxes()
/*     */   {
/*  96 */     setXAxis(new DateAxis());
/*  97 */     this.xAxis.setBarScaling(true);
/*  98 */     this.xAxis.setSide(0);
/*  99 */     setYAxis(new Axis());
/* 100 */     this.yAxis.setBarScaling(true);
/*     */   }
/*     */ 
/*     */   protected void initChart()
/*     */   {
/* 107 */     initGlobals();
/* 108 */     setPlotarea(new Plotarea());
/* 109 */     setBackground(new Background());
/* 110 */     initDatasets();
/* 111 */     initAxes();
/* 112 */     this.bar = new Bar();
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
/*     */   public DateColumnChart(String name, Locale locale)
/*     */   {
/* 148 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public DateColumnChart(Locale locale)
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
/*     */ 
/* 187 */     map.put("barLabelFont", ChartUtil.toString(getDatasets()[0].getLabelFont()));
/* 188 */     map.put("barLabelColor", ChartUtil.toString(getDatasets()[0].getLabelColor()));
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.DateColumnChart
 * JD-Core Version:    0.6.2
 */