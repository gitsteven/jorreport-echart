/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.util.Map;
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Composite;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class BarAreaLineChart extends _Chart
/*     */   implements BarAreaLineInterface
/*     */ {
/*     */   public static final int BAR = 0;
/*     */   public static final int AREA = 1;
/*     */   public static final int LINE = 2;
/*     */   Area area;
/*     */   Bar bar;
/*     */   Line line;
/*     */   Dataset[] areaData;
/*     */   Dataset[] barData;
/*     */   Dataset[] lineData;
/*  40 */   boolean individualColors = false;
/*     */   public int[] dataAllocation;
/*     */ 
/*     */   public BarAreaLineChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BarAreaLineChart(String s)
/*     */   {
/*  56 */     super(s);
/*     */   }
/*     */ 
/*     */   public BarAreaLineChart(String name, Locale locale)
/*     */   {
/*  67 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public BarAreaLineChart(Locale locale)
/*     */   {
/*  76 */     super(locale);
/*     */   }
/*     */ 
/*     */   synchronized void distributeDatasets()
/*     */   {
/*  86 */     Axis myYAxis = (Axis)this.yAxis;
/*     */ 
/*  88 */     int j = 0;
/*     */ 
/*  90 */     for (int i = 0; i < this.barData.length; i++) {
/*  91 */       this.barData[i] = null;
/*  92 */       this.areaData[i] = null;
/*  93 */       this.lineData[i] = null;
/*     */     }
/*     */ 
/*  96 */     for (i = 0; i < 20; i++) {
/*  97 */       if (this.dataAllocation[i] == 0) {
/*  98 */         this.barData[(j++)] = this.datasets[i];
/*     */       }
/*     */     }
/*     */ 
/* 102 */     this.bar.datasets = this.barData;
/* 103 */     j = 0;
/*     */ 
/* 105 */     for (i = 0; i < 20; i++) {
/* 106 */       if (this.dataAllocation[i] == 1) {
/* 107 */         this.areaData[(j++)] = this.datasets[i];
/*     */       }
/*     */     }
/*     */ 
/* 111 */     this.area.datasets = this.areaData;
/*     */ 
/* 113 */     j = 0;
/*     */ 
/* 115 */     for (i = 0; i < 20; i++) {
/* 116 */       if (this.dataAllocation[i] == 2) {
/* 117 */         this.lineData[(j++)] = this.datasets[i];
/*     */       }
/*     */     }
/*     */ 
/* 121 */     this.line.datasets = this.lineData;
/*     */ 
/* 124 */     if (!this.yAxis.getAutoScale()) {
/* 125 */       return;
/*     */     }
/*     */ 
/* 128 */     double areaMax = getMaxValsFromData(this.areaData, true);
/*     */     double barMax;
/*     */     double barMax;
/* 130 */     if ((this.bar instanceof StackColumn))
/* 131 */       barMax = getMaxValsFromData(this.barData, true);
/*     */     else {
/* 133 */       barMax = getMaxValsFromData(this.barData, false);
/*     */     }
/*     */ 
/* 136 */     if (areaMax > barMax) {
/* 137 */       myYAxis.datasets = this.areaData;
/*     */     }
/* 139 */     else if ((this.bar instanceof StackColumn))
/* 140 */       myYAxis.datasets = this.barData;
/*     */     else
/* 142 */       myYAxis.datasets = maxDataset(this.barData);
/*     */   }
/*     */ 
/*     */   public void drawGraph()
/*     */   {
/* 152 */     if (this.canvas == null) {
/* 153 */       return;
/*     */     }
/*     */ 
/* 156 */     drawGraph(this.canvas);
/*     */   }
/*     */ 
/*     */   public void drawGraph(Graphics g)
/*     */   {
/* 163 */     if (g == null) {
/* 164 */       return;
/*     */     }
/*     */ 
/* 167 */     super.drawGraph(g);
/* 168 */     distributeDatasets();
/* 169 */     this.background.draw(g);
/* 170 */     this.plotarea.draw(g);
/*     */ 
/* 172 */     if (this.xAxisVisible)
/* 173 */       this.xAxis.draw(g);
/*     */     else {
/* 175 */       this.xAxis.scale();
/*     */     }
/*     */ 
/* 178 */     if (this.yAxisVisible)
/* 179 */       this.yAxis.draw(g);
/*     */     else {
/* 181 */       this.yAxis.scale();
/*     */     }
/*     */ 
/* 184 */     this.area.draw(g);
/*     */ 
/* 186 */     Graphics2D g2 = (Graphics2D)g.create();
/*     */ 
/* 188 */     Composite alphaTemp = AlphaComposite.getInstance(3, 0.5F);
/* 189 */     g2.setComposite(alphaTemp);
/*     */ 
/* 193 */     if (this.individualColors)
/* 194 */       this.bar.drawInd(g2);
/*     */     else {
/* 196 */       this.bar.draw(g2);
/*     */     }
/*     */ 
/* 199 */     g2.dispose();
/*     */ 
/* 203 */     this.line.draw(g);
/*     */ 
/* 206 */     super.drawAxisOverlays(g);
/*     */ 
/* 208 */     if (this.legendVisible)
/* 209 */       this.legend.draw(g);
/*     */   }
/*     */ 
/*     */   public Area getArea()
/*     */   {
/* 219 */     return this.area;
/*     */   }
/*     */ 
/*     */   public Bar getBar()
/*     */   {
/* 231 */     return this.bar;
/*     */   }
/*     */ 
/*     */   public boolean getIndividualColors()
/*     */   {
/* 239 */     return this.individualColors;
/*     */   }
/*     */ 
/*     */   private double getMaxValsFromData(Dataset[] datasets, boolean stackedData) {
/* 243 */     int i = 0;
/*     */ 
/* 245 */     int length = 0;
/*     */ 
/* 247 */     double hi = -8.999999999999999E+035D;
/*     */ 
/* 249 */     if (!stackedData) {
/* 250 */       while (datasets[i] != null) {
/* 251 */         hi = Math.max(hi, datasets[i].maxY());
/* 252 */         i++;
/*     */       }
/*     */ 
/* 255 */       return hi;
/*     */     }
/*     */ 
/* 258 */     while (datasets[i] != null) {
/* 259 */       length = Math.max(length, datasets[i].data.size());
/* 260 */       i++;
/*     */     }
/*     */ 
/* 263 */     for (i = 0; i < length; i++) {
/* 264 */       double total = 0.0D;
/* 265 */       int j = 0;
/*     */ 
/* 267 */       while (datasets[j] != null) {
/*     */         try {
/* 269 */           total += datasets[j].getDataElementAt(i).y;
/*     */         }
/*     */         catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
/*     */         }
/* 273 */         j++;
/*     */       }
/*     */ 
/* 276 */       hi = Math.max(hi, total);
/*     */     }
/*     */ 
/* 279 */     return hi;
/*     */   }
/*     */ 
/*     */   protected void initAxes() {
/* 283 */     setXAxis(new LabelAxis());
/* 284 */     this.xAxis.setBarScaling(true);
/* 285 */     this.xAxis.setSide(0);
/* 286 */     setYAxis(new StackAxis());
/* 287 */     this.yAxis.setBarScaling(true);
/*     */   }
/*     */ 
/*     */   protected synchronized void initChart()
/*     */   {
/* 292 */     initGlobals();
/* 293 */     setPlotarea(new Plotarea());
/* 294 */     setBackground(new Background());
/* 295 */     initDatasets();
/* 296 */     initAxes();
/* 297 */     this.dataAllocation = new int[_Chart.MAX_DATASETS];
/*     */ 
/* 299 */     for (int i = 0; i < _Chart.MAX_DATASETS; i++) {
/* 300 */       this.dataAllocation[i] = 1;
/*     */     }
/* 302 */     this.bar = new Bar();
/* 303 */     setDataRepresentation(this.bar);
/* 304 */     this.area = new Area();
/* 305 */     setDataRepresentation(this.area);
/*     */ 
/* 307 */     setLegend(new Legend());
/*     */ 
/* 309 */     this.line = new Line();
/* 310 */     setDataRepresentation(this.line);
/*     */ 
/* 312 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   protected void initDatasets()
/*     */   {
/* 318 */     this.datasets = new Dataset[_Chart.MAX_DATASETS];
/* 319 */     this.barData = new Dataset[_Chart.MAX_DATASETS];
/* 320 */     this.areaData = new Dataset[_Chart.MAX_DATASETS];
/* 321 */     this.lineData = new Dataset[_Chart.MAX_DATASETS];
/*     */   }
/*     */ 
/*     */   private Dataset[] maxDataset(Dataset[] d) {
/* 325 */     int i = 0;
/* 326 */     int maxSet = 0;
/* 327 */     Dataset[] maxSetArray = new Dataset[40];
/* 328 */     double hi = -8.999999999999999E+035D;
/*     */ 
/* 330 */     while (d[i] != null) {
/* 331 */       maxSetArray[0] = d[i];
/*     */ 
/* 333 */       double val = getMaxValsFromData(maxSetArray, false);
/*     */ 
/* 335 */       if (val > hi) {
/* 336 */         hi = val;
/* 337 */         maxSet = i;
/*     */       }
/*     */ 
/* 340 */       i++;
/*     */     }
/*     */ 
/* 343 */     maxSetArray[0] = d[maxSet];
/*     */ 
/* 345 */     return maxSetArray;
/*     */   }
/*     */ 
/*     */   public void setArea(Area a)
/*     */   {
/* 353 */     this.area = a;
/* 354 */     setDataRepresentation(a);
/*     */   }
/*     */ 
/*     */   public void setBar(Bar b)
/*     */   {
/* 362 */     this.bar = b;
/* 363 */     setDataRepresentation(b);
/*     */   }
/*     */ 
/*     */   public void setBar(Line l)
/*     */   {
/* 372 */     this.line = l;
/* 373 */     setDataRepresentation(l);
/*     */   }
/*     */ 
/*     */   public synchronized void setChartType(int dataset, int type)
/*     */   {
/* 382 */     if ((type < 0) || (type > 2)) {
/* 383 */       System.out.println("bad Chart Type");
/*     */ 
/* 385 */       return;
/*     */     }
/*     */ 
/* 388 */     if (dataset > _Chart.MAX_DATASETS) {
/* 389 */       System.out.println("bad dataset number");
/*     */ 
/* 391 */       return;
/*     */     }
/*     */ 
/* 394 */     this.dataAllocation[dataset] = type;
/*     */   }
/*     */ 
/*     */   public int getChartType(int dataset)
/*     */   {
/* 405 */     if (dataset > this.dataAllocation.length - 1) {
/* 406 */       return -1;
/*     */     }
/*     */ 
/* 409 */     return this.dataAllocation[dataset];
/*     */   }
/*     */ 
/*     */   public void setIndividualColors(boolean trueFalse)
/*     */   {
/* 418 */     this.individualColors = trueFalse;
/*     */   }
/*     */ 
/*     */   public void setStackedBar(boolean trueFalse)
/*     */   {
/* 426 */     if (trueFalse)
/* 427 */       this.bar = new StackColumn(this.datasets, this.xAxis, this.yAxis, this.plotarea);
/*     */     else
/* 429 */       this.bar = new Bar(this.datasets, this.xAxis, this.yAxis, this.plotarea);
/*     */   }
/*     */ 
/*     */   public boolean getStackedBar()
/*     */   {
/* 439 */     if ((this.bar instanceof StackColumn)) {
/* 440 */       return true;
/*     */     }
/* 442 */     return false;
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 456 */     applyGeneralProperty(map);
/*     */ 
/* 458 */     if (getBar().getLabelsOn()) {
/* 459 */       map.put("barLabelsOn", "true");
/*     */ 
/* 461 */       if (getBar().getUseValueLabels()) {
/* 462 */         map.put("useValueLabels", "true");
/*     */       }
/*     */ 
/* 465 */       map.put("barLabelAngle", String.valueOf(getBar().getLabelAngle()));
/* 466 */       map.put("labelPrecision", String.valueOf(getBar().getLabelPrecision()));
/* 467 */       map.put("barLabelFormat", valueOfLabelFormat());
/* 468 */       map.put("barLabelColor", ChartUtil.toString(getDatasets()[0].getLabelColor()));
/* 469 */       map.put("barLabelFont", ChartUtil.toString(getDatasets()[0].getLabelFont()));
/*     */     }
/*     */ 
/* 484 */     if (getIndividualColors()) {
/* 485 */       map.put("individualColors", "true");
/*     */     }
/*     */ 
/* 491 */     map.put("barBaseline", String.valueOf(getBar().getBaseline()));
/* 492 */     map.put("barClusterWidth", String.valueOf(getBar().getClusterWidth()));
/*     */ 
/* 494 */     map.put("stackedBar", getStackedBar());
/*     */ 
/* 497 */     String dataset = "dataset";
/* 498 */     String type = "Type";
/*     */ 
/* 500 */     for (int i = 0; i < 40; i++) {
/* 501 */       if (getDatasets()[i] != null) {
/* 502 */         if (getChartType(i) == 0) {
/* 503 */           map.put(dataset + i + type, "Bar");
/*     */         }
/*     */ 
/* 506 */         if (getChartType(i) == 1) {
/* 507 */           map.put(dataset + i + type, "Area");
/*     */         }
/*     */ 
/* 510 */         if (getChartType(i) == 2) {
/* 511 */           map.put(dataset + i + type, "Line");
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 516 */     activateOutlineFills(map, getIndividualColors());
/*     */   }
/*     */ 
/*     */   public Line getLine()
/*     */   {
/* 525 */     return this.line;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.BarAreaLineChart
 * JD-Core Version:    0.6.2
 */