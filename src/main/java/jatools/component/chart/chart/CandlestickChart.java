/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class CandlestickChart extends _Chart
/*     */ {
/*     */   Candlestick candlestick;
/*     */ 
/*     */   public CandlestickChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CandlestickChart(String s)
/*     */   {
/*  38 */     super(s);
/*     */   }
/*     */ 
/*     */   public CandlestickChart(String name, Locale locale)
/*     */   {
/*  48 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public CandlestickChart(Locale locale)
/*     */   {
/*  57 */     super(locale);
/*     */   }
/*     */ 
/*     */   public void addDataset(String s, double[] dateVals, double[] hiVals, double[] loVals, double[] openVals, double[] closeVals)
/*     */   {
/*  73 */     double[] x = { 0.0D };
/*  74 */     double[] y = { 0.0D };
/*  75 */     Dataset d = new Dataset(s, x, y, this.numberOfDatasets, this.globals);
/*  76 */     d.getData().clear();
/*  77 */     for (int i = 0; i < dateVals.length; i++) {
/*  78 */       CandlestickDatum cd = new CandlestickDatum(dateVals[i], hiVals[i], loVals[i], openVals[i], closeVals[i], this.globals);
/*  79 */       d.getData().add(cd);
/*     */     }
/*  81 */     addDataset(d);
/*     */   }
/*     */ 
/*     */   public void drawGraph() {
/*  85 */     if (this.canvas == null)
/*  86 */       return;
/*  87 */     drawGraph(this.canvas);
/*     */   }
/*     */   public void drawGraph(Graphics g) {
/*  90 */     if (g == null)
/*  91 */       return;
/*  92 */     super.drawGraph(g);
/*  93 */     this.background.draw(g);
/*  94 */     this.plotarea.draw(g);
/*     */ 
/* 108 */     if (this.xAxisVisible)
/* 109 */       this.xAxis.draw(g);
/*     */     else
/* 111 */       this.xAxis.scale();
/* 112 */     if (this.yAxisVisible)
/* 113 */       this.yAxis.draw(g);
/*     */     else
/* 115 */       this.yAxis.scale();
/* 116 */     if ((this.xAxisVisible) && (((Axis)this.xAxis).lineVis))
/* 117 */       ((Axis)this.xAxis).drawLine(g);
/* 118 */     this.candlestick.draw(g);
/* 119 */     super.drawAxisOverlays(g);
/* 120 */     if (this.legendVisible)
/* 121 */       this.legend.draw(g);
/*     */   }
/*     */ 
/*     */   public Candlestick getCandlestick()
/*     */   {
/* 128 */     return this.candlestick;
/*     */   }
/*     */ 
/*     */   protected void initAxes()
/*     */   {
/* 133 */     setXAxis(new DateAxis());
/* 134 */     setYAxis(new HiLoAxis());
/*     */ 
/* 137 */     this.xAxis.setSide(0);
/* 138 */     this.yAxis.setBarScaling(false);
/* 139 */     this.xAxis.setBarScaling(true);
/*     */   }
/*     */ 
/*     */   protected void initChart() {
/* 143 */     initGlobals();
/* 144 */     setPlotarea(new Plotarea());
/* 145 */     setBackground(new Background());
/* 146 */     initDatasets();
/* 147 */     initAxes();
/* 148 */     this.candlestick = new Candlestick();
/* 149 */     this.candlestick.setUnitScaling(false);
/* 150 */     setDataRepresentation(this.candlestick);
/* 151 */     setLegend(new Legend());
/* 152 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   public void setCandlestick(Candlestick clstick)
/*     */   {
/* 160 */     this.candlestick = clstick;
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 167 */     applyGeneralProperty(map);
/*     */ 
/* 169 */     map.put("barClusterWidth", String.valueOf(getCandlestick().getClusterWidth()));
/*     */ 
/* 171 */     if (getCandlestick().getXAxis().getBarScaling())
/* 172 */       map.put("xAxisBarScaling", "true");
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.CandlestickChart
 * JD-Core Version:    0.6.2
 */