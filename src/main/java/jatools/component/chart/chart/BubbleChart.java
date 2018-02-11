/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class BubbleChart extends _Chart
/*     */ {
/*     */   Bubble bubble;
/*  23 */   boolean crossAxes = true;
/*  24 */   double xCrossVal = 0.0D;
/*  25 */   double yCrossVal = 0.0D;
/*     */ 
/*     */   public BubbleChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BubbleChart(String name)
/*     */   {
/*  39 */     super(name);
/*     */   }
/*     */ 
/*     */   public BubbleChart(String name, Locale locale)
/*     */   {
/*  49 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public BubbleChart(Locale locale)
/*     */   {
/*  58 */     super(locale);
/*     */   }
/*     */   public void drawGraph() {
/*  61 */     if (this.canvas == null) {
/*  62 */       return;
/*     */     }
/*  64 */     drawGraph(this.canvas);
/*     */   }
/*     */   public void drawGraph(Graphics g) {
/*  67 */     if (g == null)
/*  68 */       return;
/*  69 */     super.drawGraph(g);
/*  70 */     this.background.draw(g);
/*  71 */     this.plotarea.draw(g);
/*     */ 
/*  73 */     if (this.crossAxes) {
/*  74 */       Graphics axisG = g.create();
/*  75 */       if (this.xAxis.getAutoScale())
/*  76 */         this.xAxis.scale();
/*  77 */       if (this.yAxis.getAutoScale())
/*  78 */         this.yAxis.scale();
/*  79 */       double xScalePixels = (this.plotarea.getUrX() - this.plotarea.getLlX()) * this.globals.getMaxX();
/*  80 */       double xRange = this.xAxis.getAxisEnd() - this.xAxis.getAxisStart();
/*  81 */       double xZeroLoc = (this.xCrossVal - this.xAxis.getAxisStart()) / xRange * xScalePixels;
/*  82 */       axisG.translate((int)xZeroLoc, 0);
/*  83 */       this.yAxis.draw(axisG);
/*  84 */       axisG.translate(-(int)xZeroLoc, 0);
/*  85 */       double yScalePixels = (this.plotarea.getUrY() - this.plotarea.getLlY()) * this.globals.getMaxY();
/*  86 */       double yRange = this.yAxis.getAxisEnd() - this.yAxis.getAxisStart();
/*  87 */       double yZeroLoc = (this.yCrossVal - this.yAxis.getAxisStart()) / yRange * yScalePixels;
/*  88 */       axisG.translate(0, -(int)yZeroLoc);
/*  89 */       this.xAxis.draw(axisG);
/*     */     }
/*     */     else {
/*  92 */       if (this.xAxisVisible)
/*  93 */         this.xAxis.draw(g);
/*     */       else
/*  95 */         this.xAxis.scale();
/*  96 */       if (this.yAxisVisible)
/*  97 */         this.yAxis.draw(g);
/*     */       else
/*  99 */         this.yAxis.scale();
/* 100 */       if ((this.xAxisVisible) && (((Axis)this.xAxis).lineVis))
/* 101 */         ((Axis)this.xAxis).drawLine(g);
/*     */     }
/* 103 */     this.bubble.draw(g);
/* 104 */     if (this.legendVisible)
/* 105 */       this.legend.draw(g);
/*     */   }
/*     */ 
/*     */   public boolean getAutoZScale()
/*     */   {
/* 112 */     return this.bubble.getAutoZScale();
/*     */   }
/*     */ 
/*     */   public Bubble getBubble()
/*     */   {
/* 120 */     return this.bubble;
/*     */   }
/*     */ 
/*     */   public boolean getCrossAxes()
/*     */   {
/* 128 */     return this.crossAxes;
/*     */   }
/*     */ 
/*     */   public double getZScale()
/*     */   {
/* 134 */     return this.bubble.getZScale();
/*     */   }
/*     */ 
/*     */   protected void initAxes()
/*     */   {
/* 141 */     setXAxis(new LabelAxis());
/* 142 */     this.xAxis.setSide(0);
/* 143 */     setYAxis(new Axis());
/*     */   }
/*     */ 
/*     */   protected void initChart()
/*     */   {
/* 150 */     initGlobals();
/* 151 */     setPlotarea(new Plotarea());
/* 152 */     setBackground(new Background());
/* 153 */     initDatasets();
/* 154 */     initAxes();
/* 155 */     this.bubble = new Bubble();
/* 156 */     setDataRepresentation(this.bubble);
/* 157 */     setLegend(new LineLegend());
/* 158 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   public void setAutoZScale(boolean trueFalse)
/*     */   {
/* 164 */     this.bubble.setAutoZScale(trueFalse);
/*     */   }
/*     */ 
/*     */   public void setBubble(Bubble b)
/*     */   {
/* 172 */     this.bubble = b;
/* 173 */     setDataRepresentation(b);
/*     */   }
/*     */ 
/*     */   public void setCrossAxes(boolean crossAxes)
/*     */   {
/* 181 */     this.crossAxes = crossAxes;
/*     */   }
/*     */ 
/*     */   public void setXCrossVal(double d)
/*     */   {
/* 189 */     this.xCrossVal = d;
/*     */   }
/*     */ 
/*     */   public void setYCrossVal(double d)
/*     */   {
/* 197 */     this.yCrossVal = d;
/*     */   }
/*     */ 
/*     */   public double getXCrossVal()
/*     */   {
/* 204 */     return this.xCrossVal;
/*     */   }
/*     */ 
/*     */   public double getYCrossVal()
/*     */   {
/* 212 */     return this.yCrossVal;
/*     */   }
/*     */ 
/*     */   public void setZScale(double z) {
/* 216 */     this.bubble.setZScale(z);
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 223 */     applyGeneralProperty(map);
/*     */ 
/* 225 */     map.put("setZScale", String.valueOf(getBubble().getZScale()));
/*     */ 
/* 227 */     if (!getAutoZScale()) {
/* 228 */       map.put("zAutoScaleOff", "true");
/*     */     }
/*     */ 
/* 231 */     map.put("maxDiameter", String.valueOf(((Bubble)getDataRepresentation()).maxDiameter));
/*     */ 
/* 233 */     if (((Bubble)getDataRepresentation()).fillBubbles) {
/* 234 */       map.put("fillBubbles", "true");
/*     */     }
/*     */ 
/* 237 */     if (getCrossAxes()) {
/* 238 */       map.put("crossAxes", "true");
/*     */     }
/*     */ 
/* 243 */     map.put("xCrossVal", String.valueOf(this.xCrossVal));
/*     */ 
/* 245 */     map.put("yCrossVal", String.valueOf(this.yCrossVal));
/*     */ 
/* 247 */     if (getBubble().getLabelsOn()) {
/* 248 */       map.put("useValueLabels", "true");
/*     */     }
/*     */ 
/* 251 */     map.put("bubbleLabelFont", ChartUtil.toString(getDatasets()[0].getLabelFont()));
/* 252 */     map.put("bubbleLabelColor", ChartUtil.toString(getDatasets()[0].getLabelColor()));
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.BubbleChart
 * JD-Core Version:    0.6.2
 */