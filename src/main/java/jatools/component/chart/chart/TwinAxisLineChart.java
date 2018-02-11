/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class TwinAxisLineChart extends LineChart
/*     */   implements TwinAxisInterface
/*     */ {
/*     */   AxisInterface auxYAxis;
/*     */   Line auxLine;
/*     */   Dataset[] rightDatasets;
/*  35 */   Dataset[] leftDatasets = new Dataset[20];
/*     */   boolean[] dataOnRight;
/*     */ 
/*     */   public Dataset[] getRightDatasets()
/*     */   {
/*  43 */     allocateDatasets();
/*  44 */     return this.rightDatasets;
/*     */   }
/*     */ 
/*     */   public Dataset[] getLeftDatasets()
/*     */   {
/*  51 */     allocateDatasets();
/*  52 */     return this.leftDatasets;
/*     */   }
/*     */ 
/*     */   private void allocateDatasets() {
/*  56 */     int leftCounter = 0;
/*  57 */     int rightCounter = 0;
/*  58 */     for (int i = 0; i < this.leftDatasets.length; i++) {
/*  59 */       this.leftDatasets[i] = null;
/*  60 */       this.rightDatasets[i] = null;
/*     */     }
/*     */ 
/*  63 */     for (i = 0; i < this.dataOnRight.length; i++) {
/*  64 */       if (this.dataOnRight[i] != 0) {
/*  65 */         this.rightDatasets[rightCounter] = this.datasets[i];
/*  66 */         rightCounter++;
/*     */       }
/*     */       else {
/*  69 */         this.leftDatasets[leftCounter] = this.datasets[i];
/*  70 */         leftCounter++;
/*     */       }
/*     */     }
/*  73 */     this.yAxis.setDatasets(this.leftDatasets);
/*  74 */     this.auxYAxis.setDatasets(this.rightDatasets);
/*  75 */     getLine().setDatasets(this.leftDatasets);
/*     */   }
/*     */ 
/*     */   public synchronized void assignToRightAxis(int dataset, boolean rightAxis)
/*     */   {
/*  85 */     this.dataOnRight[dataset] = rightAxis;
/*     */   }
/*     */   public void drawGraph(Graphics g) {
/*  88 */     ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/*  89 */       RenderingHints.VALUE_ANTIALIAS_ON);
/*  90 */     if (this.auxLine == null)
/*  91 */       this.auxLine = new Line(this.rightDatasets, this.xAxis, this.auxYAxis, this.plotarea);
/*  92 */     allocateDatasets();
/*     */ 
/*  95 */     if (getUseDisplayList())
/*  96 */       getDisplayList().clear();
/*  97 */     this.background.draw(g);
/*  98 */     this.plotarea.draw(g);
/*  99 */     if (this.xAxisVisible)
/* 100 */       this.xAxis.draw(g);
/* 101 */     if (this.yAxisVisible)
/* 102 */       this.yAxis.draw(g);
/* 103 */     if (this.legendVisible)
/* 104 */       this.legend.draw(g);
/* 105 */     this.auxYAxis.draw(g);
/*     */ 
/* 107 */     getLine().draw(g);
/* 108 */     this.auxLine.draw(g);
/*     */ 
/* 110 */     super.drawAxisOverlays(g);
/*     */     try {
/* 112 */       Axis ax = (Axis)this.auxYAxis;
/* 113 */       if (ax.lineVis)
/* 114 */         ax.drawLine(g);
/* 115 */       ax.drawThresholdLines(g); } catch (Exception localException) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public AxisInterface getAuxAxis() {
/* 120 */     return this.auxYAxis;
/*     */   }
/*     */ 
/*     */   public Line getAuxLine()
/*     */   {
/* 127 */     return this.auxLine;
/*     */   }
/*     */   protected void initAxes() {
/* 130 */     super.initAxes();
/* 131 */     this.rightDatasets = new Dataset[20];
/* 132 */     this.dataOnRight = new boolean[20];
/* 133 */     for (int i = 0; i < this.dataOnRight.length; i++) {
/* 134 */       this.dataOnRight[i] = false;
/*     */     }
/* 136 */     this.auxYAxis = new Axis(this.rightDatasets, false, this.plotarea);
/* 137 */     this.auxYAxis.setSide(3);
/*     */   }
/*     */ 
/*     */   public void setAuxAxis(AxisInterface newAuxYAxis)
/*     */   {
/* 144 */     this.auxYAxis = newAuxYAxis;
/*     */   }
/*     */ 
/*     */   public void setAuxLine(Line newAuxLine)
/*     */   {
/* 152 */     this.auxLine = newAuxLine;
/*     */   }
/*     */ 
/*     */   public TwinAxisLineChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TwinAxisLineChart(String s)
/*     */   {
/* 170 */     super(s);
/*     */   }
/*     */ 
/*     */   public TwinAxisLineChart(String name, Locale locale)
/*     */   {
/* 181 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public TwinAxisLineChart(Locale locale)
/*     */   {
/* 191 */     super(locale);
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 198 */     super.applyProperties(map);
/*     */ 
/* 200 */     putAxOptions(getAuxAxis(), "auxAxis", map);
/*     */ 
/* 202 */     if ((getAuxLine() != null) && 
/* 203 */       (getAuxLine().isScatterPlot())) {
/* 204 */       map.put("auxPlotLinesOn", "true");
/*     */     }
/*     */ 
/* 208 */     String dataset = "dataset";
/* 209 */     String onright = "onRight";
/* 210 */     for (int i = 0; i < _Chart.MAX_DATASETS; i++) {
/* 211 */       if ((getDatasets()[i] != null) && 
/* 212 */         (this.dataOnRight[i] != 0)) {
/* 213 */         map.put(dataset + i + onright, "true");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 218 */     String axis = "Axis";
/* 219 */     for (int i = 0; i < 20; i++)
/* 220 */       if ((getDatasets()[i] != null) && 
/* 221 */         (this.dataOnRight[i] != 0))
/* 222 */         map.put(dataset + i + axis, "true");
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.TwinAxisLineChart
 * JD-Core Version:    0.6.2
 */