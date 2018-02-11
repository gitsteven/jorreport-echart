/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class TwinAxisStackBarLineChart extends LabelLineChart
/*     */   implements TwinAxisInterface
/*     */ {
/*     */   AxisInterface barYAxis;
/*     */   Bar bar;
/*     */   Dataset[] barDatasets;
/*     */   Dataset[] lineDatasets;
/*     */   int[] datasetType;
/*     */   public static final int LINE = 0;
/*     */   public static final int BAR = 1;
/*  38 */   boolean individualColors = false;
/*     */ 
/*  40 */   private void allocateDatasets() { int lineCounter = 0;
/*  41 */     int barCounter = 0;
/*     */ 
/*  50 */     this.dataRepresentation.datasets = this.lineDatasets;
/*     */ 
/*  52 */     for (int i = 0; i < this.datasetType.length; i++) {
/*  53 */       Dataset d = this.datasets[i];
/*  54 */       if (this.datasetType[i] == 1) {
/*  55 */         this.barDatasets[barCounter] = d;
/*  56 */         barCounter++;
/*     */       }
/*     */       else {
/*  59 */         this.lineDatasets[lineCounter] = d;
/*  60 */         lineCounter++;
/*     */       }
/*     */     } }
/*     */ 
/*     */   public void drawGraph(Graphics g) {
/*  65 */     ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/*  66 */       RenderingHints.VALUE_ANTIALIAS_ON);
/*  67 */     allocateDatasets();
/*     */ 
/*  70 */     if (getUseDisplayList())
/*  71 */       getDisplayList().clear();
/*  72 */     this.background.draw(g);
/*  73 */     this.plotarea.draw(g);
/*  74 */     if (this.xAxisVisible)
/*  75 */       this.xAxis.draw(g);
/*  76 */     if (this.yAxisVisible)
/*  77 */       this.yAxis.draw(g);
/*  78 */     if (this.legendVisible)
/*  79 */       this.legend.draw(g);
/*  80 */     this.barYAxis.draw(g);
/*  81 */     if (this.individualColors)
/*  82 */       this.bar.drawInd(g);
/*     */     else
/*  84 */       this.bar.draw(g);
/*  85 */     getLine().draw(g);
/*     */ 
/*  87 */     super.drawAxisOverlays(g);
/*     */     try {
/*  89 */       Axis ax = (Axis)this.barYAxis;
/*  90 */       if (ax.lineVis)
/*  91 */         ax.drawLine(g);
/*  92 */       ax.drawThresholdLines(g); } catch (Exception localException) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public AxisInterface getAuxAxis() {
/*  97 */     return this.barYAxis;
/*     */   }
/*     */ 
/*     */   public Bar getBar()
/*     */   {
/* 105 */     return this.bar;
/*     */   }
/*     */ 
/*     */   public boolean getIndividualColors()
/*     */   {
/* 113 */     return this.individualColors;
/*     */   }
/*     */ 
/*     */   protected void initAxes() {
/* 117 */     super.initAxes();
/* 118 */     this.xAxis.setBarScaling(true);
/* 119 */     this.datasetType = new int[20];
/* 120 */     this.lineDatasets = new Dataset[20];
/* 121 */     this.barDatasets = new Dataset[20];
/*     */ 
/* 123 */     for (int i = 0; i < this.datasetType.length; i++) {
/* 124 */       this.datasetType[i] = 0;
/*     */     }
/* 126 */     this.yAxis = new Axis(this.lineDatasets, false, this.plotarea);
/* 127 */     this.barYAxis = new StackAxis(this.barDatasets, false, this.plotarea);
/* 128 */     this.barYAxis.setSide(3);
/* 129 */     this.barYAxis.setBarScaling(true);
/* 130 */     this.bar = new StackColumn(this.barDatasets, this.xAxis, this.barYAxis, this.plotarea);
/*     */   }
/*     */ 
/*     */   public void setAuxAxis(AxisInterface newBarYAxis)
/*     */   {
/* 137 */     this.barYAxis = newBarYAxis;
/*     */   }
/*     */ 
/*     */   public void setBar(Bar newBar)
/*     */   {
/* 145 */     this.bar = newBar;
/*     */   }
/*     */ 
/*     */   public void setDatasetType(int setNumber, int type)
/*     */   {
/* 153 */     if ((type == 0) || (type == 1))
/*     */       try {
/* 155 */         this.datasetType[setNumber] = type;
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/*     */       }
/*     */   }
/*     */ 
/*     */   public void setIndividualColors(boolean newIndividualColors)
/*     */   {
/* 165 */     this.individualColors = newIndividualColors;
/*     */   }
/*     */ 
/*     */   public TwinAxisStackBarLineChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TwinAxisStackBarLineChart(String s)
/*     */   {
/* 183 */     super(s);
/*     */   }
/*     */ 
/*     */   public TwinAxisStackBarLineChart(String name, Locale locale)
/*     */   {
/* 194 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public TwinAxisStackBarLineChart(Locale locale)
/*     */   {
/* 204 */     super(locale);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.TwinAxisStackBarLineChart
 * JD-Core Version:    0.6.2
 */