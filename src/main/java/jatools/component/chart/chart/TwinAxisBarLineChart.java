/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class TwinAxisBarLineChart extends LabelLineChart
/*     */   implements TwinAxisInterface, BarLineInterface
/*     */ {
/*     */   AxisInterface barYAxis;
/*     */   Bar bar;
/*     */   Dataset[] barDatasets;
/*     */   Dataset[] lineDatasets;
/*     */   int[] datasetType;
/*     */   public static final int LINE = 1;
/*     */   public static final int BAR = 0;
/*  50 */   boolean individualColors = false;
/*     */ 
/*     */   private void allocateDatasets() {
/*  53 */     int lineCounter = 0;
/*  54 */     int barCounter = 0;
/*     */ 
/*  56 */     for (int i = 0; i < this.lineDatasets.length; i++) {
/*  57 */       this.lineDatasets[i] = null;
/*  58 */       this.barDatasets[i] = null;
/*     */     }
/*     */ 
/*  65 */     getDataRepresentation().setDatasets(this.lineDatasets);
/*     */ 
/*  67 */     for (int i = 0; i < this.datasetType.length; i++) {
/*  68 */       Dataset d = this.datasets[i];
/*  69 */       if (this.datasetType[i] == 0) {
/*  70 */         this.barDatasets[barCounter] = d;
/*  71 */         barCounter++;
/*     */       } else {
/*  73 */         this.lineDatasets[lineCounter] = d;
/*  74 */         lineCounter++;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void drawGraph(Graphics g) {
/*  80 */     ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/*  81 */       RenderingHints.VALUE_ANTIALIAS_ON);
/*  82 */     allocateDatasets();
/*     */ 
/*  87 */     if (getUseDisplayList())
/*  88 */       getDisplayList().clear();
/*  89 */     this.background.draw(g);
/*  90 */     this.plotarea.draw(g);
/*  91 */     if (this.xAxisVisible)
/*  92 */       this.xAxis.draw(g);
/*  93 */     if (this.yAxisVisible)
/*  94 */       this.yAxis.draw(g);
/*  95 */     if (this.legendVisible)
/*  96 */       this.legend.draw(g);
/*  97 */     this.barYAxis.draw(g);
/*  98 */     if (this.individualColors)
/*  99 */       this.bar.drawInd(g);
/*     */     else
/* 101 */       this.bar.draw(g);
/* 102 */     getLine().draw(g);
/*     */ 
/* 104 */     super.drawAxisOverlays(g);
/*     */     try {
/* 106 */       Axis ax = (Axis)this.barYAxis;
/* 107 */       if (ax.lineVis)
/* 108 */         ax.drawLine(g);
/* 109 */       ax.drawThresholdLines(g);
/*     */     } catch (Exception localException) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public AxisInterface getAuxAxis() {
/* 115 */     return this.barYAxis;
/*     */   }
/*     */ 
/*     */   public Bar getBar()
/*     */   {
/* 125 */     return this.bar;
/*     */   }
/*     */ 
/*     */   public boolean getIndividualColors()
/*     */   {
/* 135 */     return this.individualColors;
/*     */   }
/*     */ 
/*     */   protected void initChart() {
/* 139 */     initGlobals();
/* 140 */     setPlotarea(new Plotarea());
/* 141 */     setBackground(new Background());
/* 142 */     initDatasets();
/* 143 */     initAxes();
/* 144 */     Line line = new Line();
/* 145 */     setDataRepresentation(line);
/* 146 */     setLegend(new Legend());
/*     */   }
/*     */ 
/*     */   protected void initAxes()
/*     */   {
/* 151 */     super.initAxes();
/* 152 */     this.xAxis.setBarScaling(true);
/* 153 */     this.datasetType = new int[_Chart.MAX_DATASETS];
/* 154 */     this.lineDatasets = new Dataset[_Chart.MAX_DATASETS];
/* 155 */     this.barDatasets = new Dataset[_Chart.MAX_DATASETS];
/*     */ 
/* 157 */     for (int i = 0; i < this.datasetType.length; i++) {
/* 158 */       this.datasetType[i] = 1;
/*     */     }
/* 160 */     this.yAxis = new Axis(this.lineDatasets, false, this.plotarea);
/* 161 */     this.barYAxis = new Axis(this.barDatasets, false, this.plotarea);
/* 162 */     this.barYAxis.setSide(3);
/* 163 */     this.barYAxis.setBarScaling(true);
/* 164 */     this.bar = new Bar(this.barDatasets, this.xAxis, this.barYAxis, this.plotarea);
/*     */   }
/*     */ 
/*     */   public void setAuxAxis(AxisInterface newBarYAxis)
/*     */   {
/* 174 */     this.barYAxis = newBarYAxis;
/*     */   }
/*     */ 
/*     */   public void setBar(Bar newBar)
/*     */   {
/* 185 */     this.bar = newBar;
/*     */   }
/*     */ 
/*     */   public void setDatasetType(int setNumber, int type)
/*     */   {
/* 197 */     if ((type == 1) || (type == 0))
/*     */       try {
/* 199 */         this.datasetType[setNumber] = type;
/*     */       } catch (Exception localException) {
/*     */       }
/*     */   }
/*     */ 
/*     */   public int getDatasetType(int setNumber) {
/* 205 */     return this.datasetType[setNumber];
/*     */   }
/*     */ 
/*     */   public void setIndividualColors(boolean newIndividualColors)
/*     */   {
/* 216 */     this.individualColors = newIndividualColors;
/*     */   }
/*     */ 
/*     */   public TwinAxisBarLineChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TwinAxisBarLineChart(String s)
/*     */   {
/* 235 */     super(s);
/*     */   }
/*     */ 
/*     */   public TwinAxisBarLineChart(String name, Locale locale)
/*     */   {
/* 248 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public TwinAxisBarLineChart(Locale locale)
/*     */   {
/* 259 */     super(locale);
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 264 */     applyGeneralProperty(map);
/*     */ 
/* 268 */     if (getBar() != null) {
/* 269 */       map.put("barBaseline", String.valueOf(getBar().getBaseline()));
/* 270 */       map.put("barClusterWidth", String.valueOf(getBar()
/* 271 */         .getClusterWidth()));
/* 272 */       if (getBar().getLabelsOn()) {
/* 273 */         map.put("barLabelsOn", "true");
/* 274 */         map.put("barLabelAngle", String.valueOf(getBar()
/* 275 */           .getLabelAngle()));
/* 276 */         map.put("barLabelPrecision", String.valueOf(getBar()
/* 277 */           .getLabelPrecision()));
/*     */       }
/* 279 */       if (getBar().getFormat() == null) {
/* 280 */         map.put("barLabelFormat", "0");
/* 281 */       } else if (((DecimalFormat)getBar().getFormat()).getMultiplier() == 100) {
/* 282 */         map.put("barLabelFormat", "1");
/*     */       } else {
/* 284 */         char c = ((DecimalFormat)getBar().getFormat()).toPattern()
/* 285 */           .charAt(0);
/*     */ 
/* 287 */         if (c != '#')
/* 288 */           map.put("barLabelFormat", "2");
/*     */         else {
/* 290 */           map.put("barLabelFormat", "0");
/*     */         }
/*     */       }
/* 293 */       if (getBar().getUseValueLabels()) {
/* 294 */         map.put("useValueLabels", "true");
/*     */       }
/*     */ 
/* 297 */       if (getIndividualColors()) {
/* 298 */         map.put("individualColors", "true");
/*     */       }
/*     */ 
/* 301 */       if (getBar().getDoErrorBars()) {
/* 302 */         map.put("errorBars", "true");
/*     */       }
/* 304 */       map.put("barLabelFont", ChartUtil.toString(getDatasets()[0]
/* 305 */         .getLabelFont()));
/* 306 */       map.put("barLabelColor", ChartUtil.toString(getDatasets()[0]
/* 307 */         .getLabelColor()));
/*     */     }
/*     */ 
/* 315 */     putAxOptions(getAuxAxis(), "auxAxis", map);
/*     */ 
/* 317 */     if (getLine() != null) {
/* 318 */       if (getLineVisible()) {
/* 319 */         map.put("plotLinesOn", "true");
/*     */       }
/* 321 */       if (getLine().getIndividualMarkers()) {
/* 322 */         map.put("individualMarkers", "true");
/*     */       }
/*     */ 
/* 325 */       activateOutlineFills(map, getIndividualColors());
/*     */     }
/*     */ 
/* 328 */     String dataset = "dataset";
/* 329 */     String type = "Type";
/* 330 */     for (int i = 0; i < 40; i++)
/* 331 */       if (getDatasets()[i] != null)
/* 332 */         if (this.datasetType[i] == 0)
/* 333 */           map.put(dataset + i + type, "Bar");
/*     */         else
/* 335 */           map.put(dataset + i + type, "Line");
/*     */   }
/*     */ 
/*     */   public void setChartType(int dataset, int type)
/*     */   {
/* 349 */     setDatasetType(dataset, type);
/*     */   }
/*     */ 
/*     */   public int getChartType(int dataset) {
/* 353 */     return getDatasetType(dataset);
/*     */   }
/*     */ 
/*     */   public boolean getStackedBar() {
/* 357 */     return false;
/*     */   }
/*     */ 
/*     */   public void setStackedBar(boolean yesno)
/*     */   {
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.TwinAxisBarLineChart
 * JD-Core Version:    0.6.2
 */