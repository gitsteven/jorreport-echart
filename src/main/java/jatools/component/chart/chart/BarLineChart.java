/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.io.PrintStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class BarLineChart extends _Chart
/*     */   implements BarLineInterface
/*     */ {
/*     */   Line line;
/*     */   Bar bar;
/*     */   Dataset[] lineData;
/*     */   Dataset[] barData;
/*     */   public int[] dataAllocation;
/*  41 */   boolean individualColors = false;
/*     */   public static final int BAR = 0;
/*     */   public static final int LINE = 1;
/*     */ 
/*     */   public BarLineChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BarLineChart(String s)
/*     */   {
/*  63 */     super(s);
/*     */   }
/*     */ 
/*     */   private void distributeDatasets()
/*     */   {
/*  70 */     for (int i = 0; i < this.barData.length; i++) {
/*  71 */       this.barData[i] = null;
/*  72 */       this.lineData[i] = null;
/*     */     }
/*     */ 
/*  75 */     int j = 0;
/*  76 */     for (i = 0; i < _Chart.MAX_DATASETS; i++) {
/*  77 */       if (this.dataAllocation[i] == 0)
/*  78 */         this.barData[(j++)] = this.datasets[i];
/*     */     }
/*  80 */     this.bar.datasets = this.barData;
/*  81 */     j = 0;
/*  82 */     for (i = 0; i < _Chart.MAX_DATASETS; i++) {
/*  83 */       if (this.dataAllocation[i] == 1)
/*  84 */         this.lineData[(j++)] = this.datasets[i];
/*     */     }
/*  86 */     this.line.datasets = this.lineData;
/*     */ 
/*  89 */     if (!this.yAxis.getAutoScale())
/*  90 */       return;
/*  91 */     StackAxis myYAxis = (StackAxis)this.yAxis;
/*     */ 
/*  94 */     myYAxis.setStackValues(false);
/*  95 */     myYAxis.datasets = this.lineData;
/*  96 */     myYAxis.scale();
/*  97 */     double lineMax = myYAxis.axisEnd;
/*     */ 
/* 101 */     if ((this.bar instanceof StackColumn)) {
/* 102 */       myYAxis.setStackValues(true);
/*     */     }
/* 104 */     myYAxis.datasets = this.barData;
/* 105 */     myYAxis.scale();
/* 106 */     double barMax = myYAxis.axisEnd;
/*     */ 
/* 110 */     if (lineMax > barMax) {
/* 111 */       myYAxis.setStackValues(false);
/* 112 */       myYAxis.datasets = this.lineData;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void drawGraph()
/*     */   {
/* 121 */     if (this.canvas == null)
/* 122 */       return;
/* 123 */     drawGraph(this.canvas);
/*     */   }
/*     */ 
/*     */   public void drawGraph(Graphics g)
/*     */   {
/* 133 */     if (g == null)
/* 134 */       return;
/* 135 */     super.drawGraph(g);
/* 136 */     distributeDatasets();
/* 137 */     this.background.draw(g);
/* 138 */     this.plotarea.draw(g);
/* 139 */     if (this.xAxisVisible)
/* 140 */       this.xAxis.draw(g);
/*     */     else
/* 142 */       this.xAxis.scale();
/* 143 */     if (this.yAxisVisible)
/* 144 */       this.yAxis.draw(g);
/*     */     else
/* 146 */       this.yAxis.scale();
/* 147 */     if (!this.individualColors)
/* 148 */       this.bar.draw(g);
/*     */     else
/* 150 */       this.bar.drawInd(g);
/* 151 */     this.line.draw(g);
/*     */ 
/* 153 */     super.drawAxisOverlays(g);
/*     */ 
/* 155 */     if (this.legendVisible)
/* 156 */       this.legend.draw(g);
/*     */   }
/*     */ 
/*     */   public Bar getBar()
/*     */   {
/* 165 */     return this.bar;
/*     */   }
/*     */ 
/*     */   public boolean getIndividualColors()
/*     */   {
/* 174 */     return this.individualColors;
/*     */   }
/*     */ 
/*     */   public Line getLine()
/*     */   {
/* 183 */     return this.line;
/*     */   }
/*     */ 
/*     */   protected void initAxes() {
/* 187 */     setXAxis(new LabelAxis());
/* 188 */     this.xAxis.setSide(0);
/* 189 */     this.xAxis.setBarScaling(true);
/* 190 */     setYAxis(new StackAxis());
/* 191 */     ((StackAxis)this.yAxis).stackValues = false;
/* 192 */     this.yAxis.setBarScaling(true);
/*     */   }
/*     */ 
/*     */   protected synchronized void initChart()
/*     */   {
/* 197 */     initGlobals();
/* 198 */     setPlotarea(new Plotarea());
/* 199 */     setBackground(new Background());
/* 200 */     initDatasets();
/* 201 */     initAxes();
/* 202 */     this.dataAllocation = new int[_Chart.MAX_DATASETS];
/* 203 */     for (int i = 0; i < _Chart.MAX_DATASETS; i++)
/* 204 */       this.dataAllocation[i] = 1;
/* 205 */     this.bar = new Bar();
/* 206 */     setDataRepresentation(this.bar);
/* 207 */     this.line = new Line();
/* 208 */     setDataRepresentation(this.line);
/* 209 */     setLegend(new Legend());
/* 210 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   protected void initDatasets()
/*     */   {
/* 215 */     this.datasets = new Dataset[_Chart.MAX_DATASETS];
/* 216 */     this.barData = new Dataset[_Chart.MAX_DATASETS];
/* 217 */     this.lineData = new Dataset[_Chart.MAX_DATASETS];
/*     */   }
/*     */ 
/*     */   public void setBar(Bar b)
/*     */   {
/* 228 */     this.bar = b;
/* 229 */     setDataRepresentation(b);
/*     */   }
/*     */ 
/*     */   public synchronized void setChartType(int dataset, int type)
/*     */   {
/* 241 */     if ((type < 0) || (type > 1)) {
/* 242 */       System.out.println("bad Chart Type");
/* 243 */       return;
/*     */     }
/* 245 */     if (dataset > _Chart.MAX_DATASETS) {
/* 246 */       System.out.println("bad dataset number");
/* 247 */       return;
/*     */     }
/* 249 */     this.dataAllocation[dataset] = type;
/*     */   }
/*     */ 
/*     */   public int getChartType(int dataset) {
/* 253 */     if (dataset > this.dataAllocation.length - 1)
/* 254 */       return -1;
/* 255 */     return this.dataAllocation[dataset];
/*     */   }
/*     */ 
/*     */   public void setIndividualColors(boolean ind)
/*     */   {
/* 267 */     this.individualColors = ind;
/*     */   }
/*     */ 
/*     */   public void setLine(Line l)
/*     */   {
/* 284 */     this.line = l;
/* 285 */     setDataRepresentation(l);
/*     */   }
/*     */ 
/*     */   public synchronized void setStackedBar(boolean trueFalse)
/*     */   {
/* 295 */     if (trueFalse) {
/* 296 */       ((StackAxis)this.yAxis).stackValues = true;
/* 297 */       this.bar = new StackColumn(this.datasets, this.xAxis, this.yAxis, this.plotarea);
/*     */     } else {
/* 299 */       this.bar = new Bar(this.datasets, this.xAxis, this.yAxis, this.plotarea);
/* 300 */       ((StackAxis)this.yAxis).stackValues = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean getStackedBar() {
/* 305 */     return ((StackAxis)this.yAxis).stackValues;
/*     */   }
/*     */ 
/*     */   public BarLineChart(String name, Locale locale)
/*     */   {
/* 318 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public BarLineChart(Locale locale)
/*     */   {
/* 329 */     super(locale);
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 339 */     applyGeneralProperty(map);
/*     */ 
/* 341 */     if (getBar() != null)
/*     */     {
/* 345 */       map.put("barBaseline", String.valueOf(getBar().getBaseline()));
/* 346 */       map.put("barClusterWidth", String.valueOf(getBar()
/* 347 */         .getClusterWidth()));
/* 348 */       if (getBar().getLabelsOn()) {
/* 349 */         map.put("barLabelsOn", "true");
/*     */       }
/* 351 */       map.put("barLabelAngle", String.valueOf(getBar().getLabelAngle()));
/* 352 */       map.put("barLabelPrecision", String.valueOf(getBar()
/* 353 */         .getLabelPrecision()));
/* 354 */       if (getBar().getFormat() == null) {
/* 355 */         map.put("barLabelFormat", "0");
/* 356 */       } else if (((DecimalFormat)getBar().getFormat()).getMultiplier() == 100) {
/* 357 */         map.put("barLabelFormat", "1");
/*     */       } else {
/* 359 */         char c = ((DecimalFormat)getBar().getFormat()).toPattern()
/* 360 */           .charAt(0);
/*     */ 
/* 362 */         if (c != '#')
/* 363 */           map.put("barLabelFormat", "2");
/*     */         else {
/* 365 */           map.put("barLabelFormat", "0");
/*     */         }
/*     */       }
/* 368 */       if (getBar().getUseValueLabels()) {
/* 369 */         map.put("useValueLabels", "true");
/*     */       }
/*     */ 
/* 372 */       if (getBar().getDoErrorBars()) {
/* 373 */         map.put("errorBars", "true");
/*     */       }
/*     */ 
/* 381 */       map.put("barLabelFont", ChartUtil.toString(getDatasets()[0]
/* 382 */         .getLabelFont()));
/* 383 */       map.put("barLabelColor", ChartUtil.toString(getDatasets()[0]
/* 384 */         .getLabelColor()));
/*     */     }
/*     */ 
/* 391 */     String dataset = "dataset";
/* 392 */     String type = "Type";
/*     */ 
/* 394 */     for (int i = 0; i < _Chart.MAX_DATASETS; i++)
/* 395 */       if (getDatasets()[i] != null) {
/* 396 */         if (getChartType(i) == 0)
/* 397 */           map.put(dataset + i + type, "Bar");
/* 398 */         else if (getChartType(i) == 1) {
/* 399 */           map.put(dataset + i + type, "Line");
/*     */         }
/* 401 */         if (getIndividualColors()) {
/* 402 */           map.put("IndividualColors", "true");
/* 403 */           Gc gc = getDatasets()[i].getGc();
/* 404 */           if (gc.getOutlineFills())
/* 405 */             map.put("OutlineFills", "true");
/*     */         }
/*     */       }
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.BarLineChart
 * JD-Core Version:    0.6.2
 */