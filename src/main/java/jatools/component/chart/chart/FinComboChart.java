/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.io.PrintStream;
/*     */ import java.text.Format;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class FinComboChart extends _Chart
/*     */ {
/*     */   HiLoClose hiLoClose;
/*     */   Stick stick;
/*     */   Line line;
/*     */   Dataset[] lineData;
/*     */   Dataset[] stickData;
/*     */   Dataset[] hlocData;
/*     */   public int[] dataAllocation;
/*     */   public AxisInterface stickYAxis;
/*     */   public AxisInterface lineYAxis;
/*     */   Plotarea stickPlotarea;
/*     */   Plotarea linePlotarea;
/*  43 */   boolean splitWindow = true;
/*  44 */   boolean hasStickData = false;
/*  45 */   boolean hasLineData = false;
/*  46 */   boolean hasHLOCData = false;
/*     */   public static final int LINE = 0;
/*     */   public static final int STICK = 1;
/*     */   public static final int HLOC = 2;
/*     */ 
/*     */   public FinComboChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public FinComboChart(String s)
/*     */   {
/*  76 */     super(s);
/*     */   }
/*     */ 
/*     */   void distributeAxisAttributes() {
/*  80 */     Axis ax1 = (Axis)this.stickYAxis;
/*  81 */     Axis ax2 = (Axis)this.yAxis;
/*  82 */     ax1.majTickVis = ax2.majTickVis;
/*  83 */     ax1.minTickVis = ax2.minTickVis;
/*  84 */     ax1.gridVis = ax2.gridVis;
/*  85 */     ax1.labelVis = ax2.labelVis;
/*  86 */     ax1.lineVis = ax2.lineVis;
/*  87 */     ax1.labelFormat = ax2.labelFormat;
/*  88 */     ax1.labelPrecision = ax2.labelPrecision;
/*  89 */     ax1.labelColor = ax2.labelColor;
/*  90 */     ax1.labelFont = ax2.labelFont;
/*  91 */     ax1.labelAngle = ax2.labelAngle;
/*  92 */     ax1.lineGc = ax2.lineGc;
/*  93 */     ax1.gridGc = ax2.gridGc;
/*  94 */     ax1.tickGc = ax2.tickGc;
/*  95 */     ax1.side = ax2.side;
/*     */ 
/*  97 */     ax1 = (Axis)this.lineYAxis;
/*  98 */     ax1.majTickVis = ax2.majTickVis;
/*  99 */     ax1.minTickVis = ax2.minTickVis;
/* 100 */     ax1.gridVis = ax2.gridVis;
/* 101 */     ax1.labelVis = ax2.labelVis;
/* 102 */     ax1.lineVis = ax2.lineVis;
/* 103 */     ax1.labelFormat = ax2.labelFormat;
/* 104 */     ax1.labelPrecision = ax2.labelPrecision;
/* 105 */     ax1.labelColor = ax2.labelColor;
/* 106 */     ax1.labelFont = ax2.labelFont;
/* 107 */     ax1.labelAngle = ax2.labelAngle;
/* 108 */     ax1.lineGc = ax2.lineGc;
/* 109 */     ax1.gridGc = ax2.gridGc;
/* 110 */     ax1.tickGc = ax2.tickGc;
/* 111 */     ax1.side = ax2.side;
/*     */   }
/*     */ 
/*     */   void distributeDatasets() {
/* 115 */     this.hasLineData = false;
/* 116 */     int j = 0;
/* 117 */     for (int i = 0; i < 20; i++) {
/* 118 */       if (this.dataAllocation[i] == 0) {
/* 119 */         this.lineData[(j++)] = this.datasets[i];
/* 120 */         if (this.datasets[i] != null) {
/* 121 */           this.hasLineData = true;
/*     */         }
/*     */       }
/*     */     }
/* 125 */     this.hasStickData = false;
/* 126 */     j = 0;
/* 127 */     for (i = 0; i < 20; i++) {
/* 128 */       if (this.dataAllocation[i] == 1) {
/* 129 */         this.stickData[(j++)] = this.datasets[i];
/* 130 */         this.hasStickData = true;
/*     */       }
/*     */     }
/*     */ 
/* 134 */     this.hasHLOCData = false;
/* 135 */     j = 0;
/* 136 */     for (i = 0; i < 20; i++)
/* 137 */       if (this.dataAllocation[i] == 2) {
/* 138 */         this.hlocData[(j++)] = this.datasets[i];
/* 139 */         this.hasHLOCData = true;
/*     */       }
/*     */   }
/*     */ 
/*     */   public void drawGraph()
/*     */   {
/* 147 */     if (this.canvas == null)
/* 148 */       return;
/* 149 */     drawGraph(this.canvas);
/*     */   }
/*     */ 
/*     */   public void drawGraph(Graphics g)
/*     */   {
/* 155 */     if (g == null)
/* 156 */       return;
/* 157 */     super.drawGraph();
/* 158 */     distributeDatasets();
/* 159 */     distributeAxisAttributes();
/* 160 */     this.background.draw(g);
/* 161 */     if (!this.splitWindow) {
/* 162 */       this.plotarea.draw(g);
/* 163 */       if (this.xAxisVisible)
/* 164 */         this.xAxis.draw(g);
/*     */       else
/* 166 */         this.xAxis.scale();
/* 167 */       if (this.yAxisVisible)
/* 168 */         this.yAxis.draw(g);
/*     */       else
/* 170 */         this.yAxis.scale();
/* 171 */       if ((this.xAxisVisible) && (((Axis)this.xAxis).lineVis))
/* 172 */         ((Axis)this.xAxis).drawLine(g);
/* 173 */       this.hiLoClose.draw(g);
/* 174 */       this.stick.draw(g);
/* 175 */       this.line.draw(g);
/*     */     }
/*     */     else {
/* 178 */       double saveBase = this.plotarea.llY;
/*     */ 
/* 180 */       double numberOfWindows = 0.0D;
/* 181 */       if (this.hasStickData)
/* 182 */         numberOfWindows += 1.0D;
/* 183 */       if (this.hasLineData)
/* 184 */         numberOfWindows += 1.0D;
/* 185 */       if (this.hasHLOCData)
/* 186 */         numberOfWindows += 1.0D;
/* 187 */       double windowHeight = (this.plotarea.urY - this.plotarea.llY) / numberOfWindows;
/*     */ 
/* 189 */       if (this.xAxisVisible)
/* 190 */         this.xAxis.draw(g);
/*     */       else
/* 192 */         this.xAxis.scale();
/* 193 */       if (this.hasHLOCData) {
/* 194 */         this.plotarea.llY = (this.plotarea.urY - windowHeight);
/* 195 */         this.plotarea.draw(g);
/*     */ 
/* 197 */         this.yAxis.setDatasets(this.hlocData);
/* 198 */         if (this.yAxisVisible)
/* 199 */           this.yAxis.draw(g);
/*     */         else
/* 201 */           this.yAxis.scale();
/* 202 */         this.hiLoClose.draw(g);
/*     */       }
/*     */       else {
/* 205 */         this.plotarea.llY = this.plotarea.urY;
/*     */       }
/* 207 */       if (this.hasLineData) {
/* 208 */         this.linePlotarea.gc = this.plotarea.gc;
/* 209 */         this.linePlotarea.urY = this.plotarea.llY;
/* 210 */         this.linePlotarea.llY = (this.plotarea.llY - windowHeight);
/* 211 */         this.linePlotarea.llX = this.plotarea.llX;
/* 212 */         this.linePlotarea.urX = this.plotarea.urX;
/* 213 */         this.plotarea.llY = this.linePlotarea.llY;
/* 214 */         this.linePlotarea.draw(g);
/* 215 */         if (this.yAxisVisible) {
/* 216 */           if (this.hasHLOCData) {
/* 217 */             ((SplitAxis)this.lineYAxis).ignoreLastLabel = true;
/*     */           }
/* 219 */           if (this.hasStickData) {
/* 220 */             ((SplitAxis)this.lineYAxis).ignoreFirstLabel = true;
/*     */           }
/* 222 */           this.lineYAxis.draw(g);
/*     */         }
/*     */         else {
/* 225 */           this.lineYAxis.scale();
/* 226 */         }this.line.plotarea = this.linePlotarea;
/* 227 */         this.line.yAxis = this.lineYAxis;
/* 228 */         this.line.draw(g);
/*     */       }
/* 230 */       if (this.hasStickData) {
/* 231 */         this.stickPlotarea.gc = this.plotarea.gc;
/* 232 */         this.stickPlotarea.urY = this.plotarea.llY;
/* 233 */         this.stickPlotarea.llY = (this.plotarea.llY - windowHeight);
/* 234 */         this.stickPlotarea.llX = this.plotarea.llX;
/* 235 */         this.stickPlotarea.urX = this.plotarea.urX;
/* 236 */         this.stickPlotarea.draw(g);
/* 237 */         this.stick.plotarea = this.stickPlotarea;
/* 238 */         if (this.yAxisVisible)
/* 239 */           this.stickYAxis.draw(g);
/*     */         else
/* 241 */           this.stickYAxis.scale();
/* 242 */         this.stick.yAxis = this.stickYAxis;
/* 243 */         this.stick.draw(g);
/*     */       }
/* 245 */       this.plotarea.llY = saveBase;
/*     */     }
/* 247 */     if (this.legendVisible)
/* 248 */       this.legend.draw(g);
/*     */   }
/*     */ 
/*     */   public HiLoClose getHiLoClose()
/*     */   {
/* 255 */     return this.hiLoClose;
/*     */   }
/*     */ 
/*     */   public boolean getSplitWindow()
/*     */   {
/* 262 */     return this.splitWindow;
/*     */   }
/*     */   protected void initAxes() {
/* 265 */     setXAxis(new DateAxis());
/* 266 */     this.xAxis.setBarScaling(false);
/* 267 */     this.xAxis.setSide(0);
/* 268 */     setYAxis(new HiLoAxis());
/* 269 */     this.yAxis.setBarScaling(false);
/* 270 */     this.stickPlotarea = new Plotarea(getGlobals());
/* 271 */     this.linePlotarea = new Plotarea(getGlobals());
/* 272 */     this.stickYAxis = new Axis(this.stickData, false, this.stickPlotarea);
/* 273 */     this.stickYAxis.setBarScaling(true);
/* 274 */     this.lineYAxis = new SplitAxis(this.lineData, false, this.linePlotarea);
/*     */   }
/*     */ 
/*     */   protected void initChart()
/*     */   {
/* 279 */     initGlobals();
/* 280 */     this.plotarea = new Plotarea(this.globals);
/* 281 */     this.background = new Background(this.globals);
/* 282 */     initDatasets();
/* 283 */     initAxes();
/* 284 */     this.dataAllocation = new int[20];
/* 285 */     for (int i = 0; i < 20; i++) this.dataAllocation[i] = 0;
/* 286 */     this.hiLoClose = new HiLoClose(this.hlocData, this.xAxis, this.yAxis, this.plotarea);
/* 287 */     this.hiLoClose.unitScaling = false;
/* 288 */     this.stick = new Stick(this.stickData, this.xAxis, this.yAxis, this.plotarea);
/* 289 */     this.stick.unitScaling = false;
/* 290 */     this.line = new Line(this.lineData, this.xAxis, this.yAxis, this.plotarea);
/* 291 */     this.dataRepresentation = this.line;
/* 292 */     this.legend = new Legend(this.datasets, this.globals);
/* 293 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   protected void initDatasets() {
/* 297 */     this.datasets = new Dataset[_Chart.MAX_DATASETS];
/* 298 */     this.lineData = new Dataset[_Chart.MAX_DATASETS];
/* 299 */     this.hlocData = new Dataset[_Chart.MAX_DATASETS];
/* 300 */     this.stickData = new Dataset[_Chart.MAX_DATASETS];
/*     */   }
/*     */   public void resize(int width, int height) {
/* 303 */     super.resize(width, height);
/* 304 */     this.stickPlotarea.resize(width, height);
/* 305 */     this.linePlotarea.resize(width, height);
/*     */   }
/*     */ 
/*     */   public synchronized void setChartType(int dataset, int type)
/*     */   {
/* 314 */     if ((type < 0) || (type > 2)) {
/* 315 */       System.out.println("bad Chart Type");
/* 316 */       return;
/*     */     }
/* 318 */     if (dataset > _Chart.MAX_DATASETS) {
/* 319 */       System.out.println("bad dataset number");
/* 320 */       return;
/*     */     }
/* 322 */     this.dataAllocation[dataset] = type;
/*     */   }
/*     */ 
/*     */   public void setHiLoClose(HiLoClose b)
/*     */   {
/* 329 */     this.hiLoClose = b;
/*     */   }
/*     */ 
/*     */   public void setSplitWindow(boolean split)
/*     */   {
/* 336 */     this.splitWindow = split;
/* 337 */     if (split)
/* 338 */       ((SplitAxis)this.lineYAxis).ignoreFirstLabel = true;
/*     */     else
/* 340 */       ((SplitAxis)this.lineYAxis).ignoreFirstLabel = false;
/*     */   }
/*     */ 
/*     */   public void setXAxis(AxisInterface a)
/*     */   {
/* 347 */     super.setXAxis(a);
/* 348 */     if (this.stick == null)
/* 349 */       return;
/* 350 */     this.stick.xAxis = ((Axis)a);
/* 351 */     this.line.xAxis = ((Axis)a);
/* 352 */     this.hiLoClose.xAxis = ((Axis)a);
/*     */   }
/*     */ 
/*     */   public void setYAxisLabelFormat(Format f)
/*     */   {
/* 360 */     this.stickYAxis.setLabelFormat(f);
/* 361 */     this.lineYAxis.setLabelFormat(f);
/* 362 */     this.yAxis.setLabelFormat(f);
/*     */   }
/*     */ 
/*     */   public FinComboChart(String name, Locale locale)
/*     */   {
/* 373 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public FinComboChart(Locale locale)
/*     */   {
/* 383 */     super(locale);
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 390 */     applyGeneralProperty(map);
/*     */ 
/* 392 */     initDateAxis((DateAxis)getXAxis(), map);
/*     */ 
/* 394 */     if (getSplitWindow()) {
/* 395 */       map.put("splitWindow", "true");
/*     */     }
/*     */ 
/* 398 */     String dataset = "dataset";
/* 399 */     String type = "Type";
/* 400 */     for (int i = 0; i < 40; i++)
/* 401 */       if ((getDatasets()[i] != null) && 
/* 402 */         (getHiLoClose() != null))
/* 403 */         map.put(dataset + i + type, "HLOC");
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.FinComboChart
 * JD-Core Version:    0.6.2
 */