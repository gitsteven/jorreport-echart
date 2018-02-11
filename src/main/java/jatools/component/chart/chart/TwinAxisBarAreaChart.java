/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class TwinAxisBarAreaChart extends AreaChart
/*     */   implements TwinAxisInterface, BarAreaInterface
/*     */ {
/*     */   AxisInterface barYAxis;
/*     */   Bar bar;
/*     */   int[] datasetType;
/*     */   Dataset[] barDatasets;
/*     */   Dataset[] areaDatasets;
/*     */   public static final int BAR = 0;
/*     */   public static final int AREA = 1;
/*  42 */   boolean individualColors = false;
/*     */ 
/*  44 */   private void allocateDatasets() { int areaCounter = 0;
/*  45 */     int barCounter = 0;
/*     */ 
/*  48 */     for (int i = 0; i < this.barDatasets.length; i++) {
/*  49 */       this.barDatasets[i] = null;
/*  50 */       this.areaDatasets[i] = null;
/*     */     }
/*     */ 
/*  54 */     this.area.datasets = this.areaDatasets;
/*     */ 
/*  57 */     for (int i = 0; i < this.datasetType.length; i++) {
/*  58 */       Dataset d = this.datasets[i];
/*  59 */       if (this.datasetType[i] == 0) {
/*  60 */         this.barDatasets[barCounter] = d;
/*  61 */         barCounter++;
/*     */       }
/*     */       else {
/*  64 */         this.areaDatasets[areaCounter] = d;
/*  65 */         areaCounter++;
/*     */       }
/*     */     } }
/*     */ 
/*     */   public void drawGraph(Graphics g) {
/*  70 */     ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/*  71 */       RenderingHints.VALUE_ANTIALIAS_ON);
/*  72 */     allocateDatasets();
/*     */ 
/*  75 */     if (getUseDisplayList())
/*  76 */       getDisplayList().clear();
/*  77 */     this.background.draw(g);
/*  78 */     this.plotarea.draw(g);
/*  79 */     if (this.xAxisVisible)
/*  80 */       this.xAxis.draw(g);
/*  81 */     if (this.yAxisVisible)
/*  82 */       this.yAxis.draw(g);
/*  83 */     if (this.legendVisible)
/*  84 */       this.legend.draw(g);
/*  85 */     this.barYAxis.draw(g);
/*  86 */     this.area.draw(g);
/*  87 */     if (this.individualColors)
/*  88 */       this.bar.drawInd(g);
/*     */     else {
/*  90 */       this.bar.draw(g);
/*     */     }
/*  92 */     super.drawAxisOverlays(g);
/*     */     try {
/*  94 */       Axis ax = (Axis)this.barYAxis;
/*  95 */       if (ax.lineVis)
/*  96 */         ax.drawLine(g);
/*  97 */       ax.drawThresholdLines(g); } catch (Exception localException) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public AxisInterface getAuxAxis() {
/* 102 */     return this.barYAxis;
/*     */   }
/*     */ 
/*     */   public Bar getBar()
/*     */   {
/* 110 */     return this.bar;
/*     */   }
/*     */ 
/*     */   public Area getArea()
/*     */   {
/* 117 */     return this.area;
/*     */   }
/*     */ 
/*     */   public boolean getIndividualColors()
/*     */   {
/* 125 */     return this.individualColors;
/*     */   }
/*     */ 
/*     */   protected void initAxes() {
/* 129 */     super.initAxes();
/* 130 */     this.xAxis.setBarScaling(true);
/* 131 */     this.datasetType = new int[20];
/* 132 */     this.areaDatasets = new Dataset[20];
/* 133 */     this.barDatasets = new Dataset[20];
/*     */ 
/* 135 */     for (int i = 0; i < this.datasetType.length; i++) {
/* 136 */       this.datasetType[i] = 1;
/*     */     }
/* 138 */     this.yAxis = new StackAxis(this.areaDatasets, false, this.plotarea);
/* 139 */     this.barYAxis = new Axis(this.barDatasets, false, this.plotarea);
/* 140 */     this.barYAxis.setSide(3);
/* 141 */     this.barYAxis.setBarScaling(true);
/* 142 */     this.bar = new Bar(this.barDatasets, this.xAxis, this.barYAxis, this.plotarea);
/*     */   }
/*     */ 
/*     */   public void setAuxAxis(AxisInterface newBarYAxis)
/*     */   {
/* 149 */     this.barYAxis = newBarYAxis;
/*     */   }
/*     */ 
/*     */   public void setBar(Bar newBar)
/*     */   {
/* 157 */     this.bar = newBar;
/*     */   }
/*     */ 
/*     */   public void setDatasetType(int setNumber, int type)
/*     */   {
/* 165 */     if ((type == 1) || (type == 0))
/*     */       try {
/* 167 */         this.datasetType[setNumber] = type; } catch (Exception localException) {
/*     */       }
/*     */   }
/*     */ 
/*     */   public int getDatasetType(int setNumber) {
/*     */     try {
/* 173 */       return this.datasetType[setNumber];
/*     */     } catch (Exception localException) {
/*     */     }
/* 176 */     return -1;
/*     */   }
/*     */ 
/*     */   public void setIndividualColors(boolean newIndividualColors)
/*     */   {
/* 184 */     this.individualColors = newIndividualColors;
/*     */   }
/*     */ 
/*     */   public TwinAxisBarAreaChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TwinAxisBarAreaChart(String s)
/*     */   {
/* 202 */     super(s);
/*     */   }
/*     */ 
/*     */   public TwinAxisBarAreaChart(String name, Locale locale)
/*     */   {
/* 213 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public TwinAxisBarAreaChart(Locale locale)
/*     */   {
/* 223 */     super(locale);
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 228 */     applyGeneralProperty(map);
/*     */ 
/* 232 */     putAxOptions(getAuxAxis(), "auxAxis", map);
/*     */ 
/* 236 */     if (getBar().getLabelsOn()) {
/* 237 */       map.put("barLabelsOn", "true");
/* 238 */       if (getBar().getUseValueLabels())
/*     */       {
/* 240 */         map.put("useValueLabels", "true");
/* 241 */         map.put("barLabelAngle", String.valueOf(getBar().getLabelAngle()));
/* 242 */         map.put("barLabelPrecision", String.valueOf(getBar().getLabelPrecision()));
/* 243 */         map.put("barLabelFormat", valueOfLabelFormat());
/* 244 */         map.put("barLabelColor", ChartUtil.toString(getDatasets()[0].getLabelColor()));
/* 245 */         map.put("barLabelFont", ChartUtil.toString(getDatasets()[0].getLabelFont()));
/*     */       }
/*     */     }
/* 248 */     if (getIndividualColors()) {
/* 249 */       map.put("individualColors", "true");
/*     */     }
/*     */ 
/* 254 */     map.put("barBaseline", String.valueOf(getBar().getBaseline()));
/* 255 */     map.put("barClusterWidth", String.valueOf(getBar().getClusterWidth()));
/*     */ 
/* 257 */     map.put("stackedArea", getStackAreas());
/*     */ 
/* 261 */     String dataset = "dataset";
/* 262 */     String type = "Type";
/* 263 */     for (int i = 0; i < 40; i++) {
/* 264 */       if (getDatasets()[i] != null) {
/* 265 */         if (this.datasetType[i] == 0) {
/* 266 */           map.put(dataset + i + type, "Bar");
/*     */         }
/* 268 */         if (this.datasetType[i] == 1) {
/* 269 */           map.put(dataset + i + type, "Area");
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 274 */     activateOutlineFills(map, getIndividualColors());
/*     */   }
/*     */   public void setChartType(int dataset, int type) {
/* 277 */     setDatasetType(dataset, type);
/*     */   }
/*     */   public int getChartType(int dataset) {
/* 280 */     return getDatasetType(dataset);
/*     */   }
/*     */   public boolean getStackedBar() {
/* 283 */     return false;
/*     */   }
/*     */ 
/*     */   public void setStackedBar(boolean yesno)
/*     */   {
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.TwinAxisBarAreaChart
 * JD-Core Version:    0.6.2
 */