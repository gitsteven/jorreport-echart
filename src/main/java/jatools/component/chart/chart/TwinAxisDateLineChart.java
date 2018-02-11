/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class TwinAxisDateLineChart extends DateLineChart
/*     */   implements TwinAxisInterface
/*     */ {
/*     */   AxisInterface auxYAxis;
/*     */   Line auxLine;
/*     */   Dataset[] rightDatasets;
/*     */   boolean[] dataOnRight;
/*     */   Dataset[] leftDatasets;
/*     */ 
/*     */   private void allocateDatasets()
/*     */   {
/*  38 */     int leftCounter = 0;
/*  39 */     int rightCounter = 0;
/*     */ 
/*  41 */     for (int i = 0; i < this.rightDatasets.length; i++) {
/*  42 */       this.rightDatasets[i] = null;
/*  43 */       this.leftDatasets[i] = null;
/*     */     }
/*  45 */     for (int i = 0; i < this.dataOnRight.length; i++) {
/*  46 */       if (this.dataOnRight[i] != 0) {
/*  47 */         this.rightDatasets[rightCounter] = this.datasets[i];
/*  48 */         rightCounter++;
/*     */       }
/*     */       else {
/*  51 */         this.leftDatasets[leftCounter] = this.datasets[i];
/*  52 */         leftCounter++;
/*     */       }
/*     */     }
/*  55 */     this.yAxis.setDatasets(this.leftDatasets);
/*  56 */     this.auxYAxis.setDatasets(this.rightDatasets);
/*  57 */     getLine().setDatasets(this.leftDatasets);
/*     */   }
/*     */ 
/*     */   public Dataset[] getRightDatasets()
/*     */   {
/*  65 */     allocateDatasets();
/*  66 */     return this.rightDatasets;
/*     */   }
/*     */ 
/*     */   public Dataset[] getLeftDatasets()
/*     */   {
/*  73 */     allocateDatasets();
/*  74 */     return this.leftDatasets;
/*     */   }
/*     */ 
/*     */   public synchronized void assignToRightAxis(int dataset, boolean rightAxis)
/*     */   {
/*  84 */     this.dataOnRight[dataset] = rightAxis;
/*     */   }
/*     */   public void drawGraph(Graphics g) {
/*  87 */     ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/*  88 */       RenderingHints.VALUE_ANTIALIAS_ON);
/*  89 */     allocateDatasets();
/*     */ 
/*  92 */     if (getUseDisplayList())
/*  93 */       getDisplayList().clear();
/*  94 */     this.background.draw(g);
/*  95 */     this.plotarea.draw(g);
/*  96 */     if (this.xAxisVisible)
/*  97 */       this.xAxis.draw(g);
/*  98 */     if (this.yAxisVisible)
/*  99 */       this.yAxis.draw(g);
/* 100 */     if (this.legendVisible)
/* 101 */       this.legend.draw(g);
/* 102 */     this.auxYAxis.draw(g);
/*     */ 
/* 104 */     getLine().draw(g);
/* 105 */     if (this.auxLine.getXAxis() != this.xAxis) {
/* 106 */       this.auxLine.setXAxis(this.xAxis);
/*     */     }
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
/* 132 */     this.leftDatasets = new Dataset[20];
/* 133 */     this.dataOnRight = new boolean[20];
/* 134 */     for (int i = 0; i < this.dataOnRight.length; i++) {
/* 135 */       this.dataOnRight[i] = false;
/*     */     }
/* 137 */     this.yAxis = new Axis(this.datasets, false, this.plotarea);
/* 138 */     this.auxYAxis = new Axis(this.rightDatasets, false, this.plotarea);
/* 139 */     this.auxYAxis.setSide(3);
/* 140 */     this.auxLine = new Line(this.rightDatasets, this.xAxis, this.auxYAxis, this.plotarea);
/*     */   }
/*     */ 
/*     */   public void setAuxAxis(AxisInterface newAuxYAxis)
/*     */   {
/* 147 */     this.auxYAxis = newAuxYAxis;
/*     */   }
/*     */ 
/*     */   public void setAuxLine(Line newAuxLine)
/*     */   {
/* 155 */     this.auxLine = newAuxLine;
/*     */   }
/*     */ 
/*     */   public TwinAxisDateLineChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TwinAxisDateLineChart(String s)
/*     */   {
/* 173 */     super(s);
/*     */   }
/*     */ 
/*     */   public TwinAxisDateLineChart(String name, Locale locale)
/*     */   {
/* 184 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public TwinAxisDateLineChart(Locale locale)
/*     */   {
/* 194 */     super(locale);
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 199 */     applyGeneralProperty(map);
/*     */ 
/* 201 */     super.applyProperties(map);
/*     */ 
/* 203 */     putAxOptions(getAuxAxis(), "auxAxis", map);
/*     */ 
/* 205 */     String dataset = "dataset";
/* 206 */     String onright = "onRight";
/* 207 */     for (int i = 0; i < 40; i++) {
/* 208 */       if ((getDatasets()[i] != null) && 
/* 209 */         (this.dataOnRight[i] != 0)) {
/* 210 */         map.put(dataset + i + onright, "true");
/*     */       }
/*     */     }
/*     */ 
/* 214 */     if (!getAuxLine().isScatterPlot()) {
/* 215 */       map.put("auxPlotLinesOff", "true");
/*     */     }
/*     */     else
/* 218 */       map.remove("auxPlotLinesOff");
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.TwinAxisDateLineChart
 * JD-Core Version:    0.6.2
 */