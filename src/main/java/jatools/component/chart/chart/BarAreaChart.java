/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class BarAreaChart extends _Chart
/*     */   implements BarAreaInterface
/*     */ {
/*     */   public static final int BAR = 0;
/*     */   public static final int AREA = 1;
/*     */   Area area;
/*     */   Bar bar;
/*     */   Dataset[] areaData;
/*     */   Dataset[] barData;
/*  38 */   boolean individualColors = false;
/*     */   public int[] dataAllocation;
/*     */ 
/*     */   public BarAreaChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BarAreaChart(String s)
/*     */   {
/*  54 */     super(s);
/*     */   }
/*     */ 
/*     */   public BarAreaChart(String name, Locale locale)
/*     */   {
/*  65 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public BarAreaChart(Locale locale)
/*     */   {
/*  74 */     super(locale);
/*     */   }
/*     */ 
/*     */   synchronized void distributeDatasets()
/*     */   {
/*  82 */     Axis myYAxis = (Axis)this.yAxis;
/*     */ 
/*  84 */     int j = 0;
/*     */ 
/*  86 */     for (int i = 0; i < this.barData.length; i++) {
/*  87 */       this.barData[i] = null;
/*  88 */       this.areaData[i] = null;
/*     */     }
/*     */ 
/*  91 */     for (i = 0; i < 20; i++) {
/*  92 */       if (this.dataAllocation[i] == 0) {
/*  93 */         this.barData[(j++)] = this.datasets[i];
/*     */       }
/*     */     }
/*     */ 
/*  97 */     this.bar.datasets = this.barData;
/*  98 */     j = 0;
/*     */ 
/* 100 */     for (i = 0; i < 20; i++) {
/* 101 */       if (this.dataAllocation[i] == 1) {
/* 102 */         this.areaData[(j++)] = this.datasets[i];
/*     */       }
/*     */     }
/*     */ 
/* 106 */     this.area.datasets = this.areaData;
/*     */ 
/* 109 */     if (!this.yAxis.getAutoScale()) {
/* 110 */       return;
/*     */     }
/*     */ 
/* 113 */     double areaMax = getMaxValsFromData(this.areaData, true);
/*     */     double barMax;
/*     */     double barMax;
/* 115 */     if ((this.bar instanceof StackColumn))
/* 116 */       barMax = getMaxValsFromData(this.barData, true);
/*     */     else {
/* 118 */       barMax = getMaxValsFromData(this.barData, false);
/*     */     }
/*     */ 
/* 121 */     if (areaMax > barMax) {
/* 122 */       myYAxis.datasets = this.areaData;
/*     */     }
/* 124 */     else if ((this.bar instanceof StackColumn))
/* 125 */       myYAxis.datasets = this.barData;
/*     */     else
/* 127 */       myYAxis.datasets = maxDataset(this.barData);
/*     */   }
/*     */ 
/*     */   public void drawGraph()
/*     */   {
/* 137 */     if (this.canvas == null) {
/* 138 */       return;
/*     */     }
/*     */ 
/* 141 */     drawGraph(this.canvas);
/*     */   }
/*     */ 
/*     */   public void drawGraph(Graphics g)
/*     */   {
/* 148 */     if (g == null) {
/* 149 */       return;
/*     */     }
/*     */ 
/* 152 */     super.drawGraph(g);
/* 153 */     distributeDatasets();
/* 154 */     this.background.draw(g);
/* 155 */     this.plotarea.draw(g);
/*     */ 
/* 157 */     if (this.xAxisVisible)
/* 158 */       this.xAxis.draw(g);
/*     */     else {
/* 160 */       this.xAxis.scale();
/*     */     }
/*     */ 
/* 163 */     if (this.yAxisVisible)
/* 164 */       this.yAxis.draw(g);
/*     */     else {
/* 166 */       this.yAxis.scale();
/*     */     }
/*     */ 
/* 169 */     this.area.draw(g);
/*     */ 
/* 171 */     if (this.individualColors)
/* 172 */       this.bar.drawInd(g);
/*     */     else {
/* 174 */       this.bar.draw(g);
/*     */     }
/*     */ 
/* 178 */     super.drawAxisOverlays(g);
/*     */ 
/* 180 */     if (this.legendVisible)
/* 181 */       this.legend.draw(g);
/*     */   }
/*     */ 
/*     */   public Area getArea()
/*     */   {
/* 191 */     return this.area;
/*     */   }
/*     */ 
/*     */   public Bar getBar()
/*     */   {
/* 203 */     return this.bar;
/*     */   }
/*     */ 
/*     */   public boolean getIndividualColors()
/*     */   {
/* 211 */     return this.individualColors;
/*     */   }
/*     */ 
/*     */   private double getMaxValsFromData(Dataset[] datasets, boolean stackedData) {
/* 215 */     int i = 0;
/*     */ 
/* 217 */     int length = 0;
/*     */ 
/* 219 */     double hi = -8.999999999999999E+035D;
/*     */ 
/* 221 */     if (!stackedData) {
/* 222 */       while (datasets[i] != null) {
/* 223 */         hi = Math.max(hi, datasets[i].maxY());
/* 224 */         i++;
/*     */       }
/*     */ 
/* 227 */       return hi;
/*     */     }
/*     */ 
/* 230 */     while (datasets[i] != null) {
/* 231 */       length = Math.max(length, datasets[i].data.size());
/* 232 */       i++;
/*     */     }
/*     */ 
/* 235 */     for (i = 0; i < length; i++) {
/* 236 */       double total = 0.0D;
/* 237 */       int j = 0;
/*     */ 
/* 239 */       while (datasets[j] != null) {
/*     */         try {
/* 241 */           total += datasets[j].getDataElementAt(i).y;
/*     */         }
/*     */         catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
/*     */         }
/* 245 */         j++;
/*     */       }
/*     */ 
/* 248 */       hi = Math.max(hi, total);
/*     */     }
/*     */ 
/* 251 */     return hi;
/*     */   }
/*     */ 
/*     */   protected void initAxes() {
/* 255 */     setXAxis(new LabelAxis());
/* 256 */     this.xAxis.setBarScaling(true);
/* 257 */     this.xAxis.setSide(0);
/* 258 */     setYAxis(new StackAxis());
/* 259 */     this.yAxis.setBarScaling(true);
/*     */   }
/*     */ 
/*     */   protected synchronized void initChart()
/*     */   {
/* 264 */     initGlobals();
/* 265 */     setPlotarea(new Plotarea());
/* 266 */     setBackground(new Background());
/* 267 */     initDatasets();
/* 268 */     initAxes();
/* 269 */     this.dataAllocation = new int[_Chart.MAX_DATASETS];
/*     */ 
/* 271 */     for (int i = 0; i < _Chart.MAX_DATASETS; i++) {
/* 272 */       this.dataAllocation[i] = 1;
/*     */     }
/* 274 */     this.bar = new Bar();
/* 275 */     setDataRepresentation(this.bar);
/* 276 */     this.area = new Area();
/* 277 */     setDataRepresentation(this.area);
/* 278 */     setLegend(new Legend());
/* 279 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   protected void initDatasets()
/*     */   {
/* 285 */     this.datasets = new Dataset[_Chart.MAX_DATASETS];
/* 286 */     this.barData = new Dataset[_Chart.MAX_DATASETS];
/* 287 */     this.areaData = new Dataset[_Chart.MAX_DATASETS];
/*     */   }
/*     */ 
/*     */   private Dataset[] maxDataset(Dataset[] d) {
/* 291 */     int i = 0;
/* 292 */     int maxSet = 0;
/* 293 */     Dataset[] maxSetArray = new Dataset[40];
/* 294 */     double hi = -8.999999999999999E+035D;
/*     */ 
/* 296 */     while (d[i] != null) {
/* 297 */       maxSetArray[0] = d[i];
/*     */ 
/* 299 */       double val = getMaxValsFromData(maxSetArray, false);
/*     */ 
/* 301 */       if (val > hi) {
/* 302 */         hi = val;
/* 303 */         maxSet = i;
/*     */       }
/*     */ 
/* 306 */       i++;
/*     */     }
/*     */ 
/* 309 */     maxSetArray[0] = d[maxSet];
/*     */ 
/* 311 */     return maxSetArray;
/*     */   }
/*     */ 
/*     */   public void setArea(Area a)
/*     */   {
/* 319 */     this.area = a;
/* 320 */     setDataRepresentation(a);
/*     */   }
/*     */ 
/*     */   public void setBar(Bar b)
/*     */   {
/* 328 */     this.bar = b;
/* 329 */     setDataRepresentation(b);
/*     */   }
/*     */ 
/*     */   public synchronized void setChartType(int dataset, int type)
/*     */   {
/* 338 */     if ((type < 0) || (type > 1)) {
/* 339 */       System.out.println("bad Chart Type");
/*     */ 
/* 341 */       return;
/*     */     }
/*     */ 
/* 344 */     if (dataset > _Chart.MAX_DATASETS) {
/* 345 */       System.out.println("bad dataset number");
/*     */ 
/* 347 */       return;
/*     */     }
/*     */ 
/* 350 */     this.dataAllocation[dataset] = type;
/*     */   }
/*     */ 
/*     */   public int getChartType(int dataset)
/*     */   {
/* 361 */     if (dataset > this.dataAllocation.length - 1) {
/* 362 */       return -1;
/*     */     }
/*     */ 
/* 365 */     return this.dataAllocation[dataset];
/*     */   }
/*     */ 
/*     */   public void setIndividualColors(boolean trueFalse)
/*     */   {
/* 374 */     this.individualColors = trueFalse;
/*     */   }
/*     */ 
/*     */   public void setStackedBar(boolean trueFalse)
/*     */   {
/* 382 */     if (trueFalse)
/* 383 */       this.bar = new StackColumn(this.datasets, this.xAxis, this.yAxis, this.plotarea);
/*     */     else
/* 385 */       this.bar = new Bar(this.datasets, this.xAxis, this.yAxis, this.plotarea);
/*     */   }
/*     */ 
/*     */   public boolean getStackedBar()
/*     */   {
/* 395 */     if ((this.bar instanceof StackColumn)) {
/* 396 */       return true;
/*     */     }
/* 398 */     return false;
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 411 */     applyGeneralProperty(map);
/*     */ 
/* 413 */     if (getBar().getLabelsOn()) {
/* 414 */       map.put("barLabelsOn", "true");
/*     */ 
/* 416 */       if (getBar().getUseValueLabels()) {
/* 417 */         map.put("useValueLabels", "true");
/*     */       }
/*     */ 
/* 420 */       map.put("barLabelAngle", String.valueOf(getBar().getLabelAngle()));
/* 421 */       map.put("labelPrecision", String.valueOf(getBar().getLabelPrecision()));
/* 422 */       map.put("barLabelFormat", valueOfLabelFormat());
/* 423 */       map.put("barLabelColor", ChartUtil.toString(getDatasets()[0].getLabelColor()));
/* 424 */       map.put("barLabelFont", ChartUtil.toString(getDatasets()[0].getLabelFont()));
/*     */     }
/*     */ 
/* 439 */     if (getIndividualColors()) {
/* 440 */       map.put("individualColors", "true");
/*     */     }
/*     */ 
/* 446 */     map.put("barBaseline", String.valueOf(getBar().getBaseline()));
/* 447 */     map.put("barClusterWidth", String.valueOf(getBar().getClusterWidth()));
/*     */ 
/* 449 */     map.put("stackedBar", getStackedBar());
/*     */ 
/* 452 */     String dataset = "dataset";
/* 453 */     String type = "Type";
/*     */ 
/* 455 */     for (int i = 0; i < 40; i++) {
/* 456 */       if (getDatasets()[i] != null) {
/* 457 */         if (getChartType(i) == 0) {
/* 458 */           map.put(dataset + i + type, "Bar");
/*     */         }
/*     */ 
/* 461 */         if (getChartType(i) == 1) {
/* 462 */           map.put(dataset + i + type, "Area");
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 467 */     activateOutlineFills(map, getIndividualColors());
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.BarAreaChart
 * JD-Core Version:    0.6.2
 */