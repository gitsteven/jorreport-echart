/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class TwinAxisDateComboChart extends DateLineChart
/*     */   implements TwinAxisInterface
/*     */ {
/*     */   AxisInterface auxYAxis;
/*     */   Line auxLine;
/*     */   Stick stick;
/*     */   Stick auxStick;
/*     */   Dataset[] stickData;
/*     */   Dataset[] auxStickData;
/*     */   Dataset[] lineData;
/*     */   Dataset[] auxLineData;
/*     */   Dataset[] rightDatasets;
/*     */   Dataset[] leftDatasets;
/*     */   boolean[] dataOnRight;
/*     */   int[] datasetType;
/*     */   public static final int LINE = 0;
/*     */   public static final int STICK = 1;
/*     */ 
/*     */   private void allocateDatasets()
/*     */   {
/*  61 */     int leftCounter = 0;
/*  62 */     int rightCounter = 0;
/*  63 */     int lineCounter = 0; int auxLineCounter = 0;
/*  64 */     int stickCounter = 0; int auxStickCounter = 0;
/*     */ 
/*  66 */     this.dataRepresentation.datasets = this.lineData;
/*     */ 
/*  68 */     for (int i = 0; i < this.rightDatasets.length; i++) {
/*  69 */       Dataset d = this.datasets[i];
/*  70 */       if (this.dataOnRight[i] != 0) {
/*  71 */         this.rightDatasets[rightCounter] = d;
/*  72 */         rightCounter++;
/*  73 */         if (this.datasetType[i] == 0) {
/*  74 */           this.auxLineData[(auxLineCounter++)] = d;
/*     */         } else {
/*  76 */           this.auxStickData[(auxStickCounter++)] = d;
/*  77 */           this.auxYAxis.setBarScaling(true);
/*     */         }
/*     */       }
/*     */       else {
/*  81 */         this.leftDatasets[leftCounter] = d;
/*  82 */         leftCounter++;
/*  83 */         if (this.datasetType[i] == 0) {
/*  84 */           this.lineData[(lineCounter++)] = d;
/*     */         } else {
/*  86 */           this.stickData[(stickCounter++)] = d;
/*  87 */           this.yAxis.setBarScaling(true);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void assignToRightAxis(int dataset, boolean rightAxis)
/*     */   {
/* 100 */     this.dataOnRight[dataset] = rightAxis;
/*     */   }
/*     */   public void drawGraph(Graphics g) {
/* 103 */     allocateDatasets();
/*     */ 
/* 106 */     if (getUseDisplayList())
/* 107 */       getDisplayList().clear();
/* 108 */     this.background.draw(g);
/* 109 */     this.plotarea.draw(g);
/* 110 */     if (this.xAxisVisible)
/* 111 */       this.xAxis.draw(g);
/* 112 */     if (this.yAxisVisible)
/* 113 */       this.yAxis.draw(g);
/* 114 */     if (this.legendVisible)
/* 115 */       this.legend.draw(g);
/* 116 */     this.auxYAxis.draw(g);
/* 117 */     this.stick.draw(g);
/* 118 */     this.auxStick.draw(g);
/* 119 */     getLine().draw(g);
/* 120 */     this.auxLine.draw(g);
/*     */ 
/* 122 */     super.drawAxisOverlays(g);
/*     */     try {
/* 124 */       Axis ax = (Axis)this.auxYAxis;
/* 125 */       if (ax.lineVis)
/* 126 */         ax.drawLine(g);
/* 127 */       ax.drawThresholdLines(g);
/*     */     } catch (Exception localException) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public AxisInterface getAuxAxis() {
/* 133 */     return this.auxYAxis;
/*     */   }
/*     */ 
/*     */   public Line getAuxLine()
/*     */   {
/* 141 */     return this.auxLine;
/*     */   }
/*     */ 
/*     */   public Stick getAuxStick()
/*     */   {
/* 149 */     return this.auxStick;
/*     */   }
/*     */ 
/*     */   public Stick getStick()
/*     */   {
/* 157 */     return this.stick;
/*     */   }
/*     */   protected void initAxes() {
/* 160 */     super.initAxes();
/* 161 */     this.dataOnRight = new boolean[20];
/* 162 */     this.datasetType = new int[20];
/* 163 */     this.leftDatasets = new Dataset[20];
/* 164 */     this.rightDatasets = new Dataset[20];
/* 165 */     this.stickData = new Dataset[20];
/* 166 */     this.auxStickData = new Dataset[20];
/* 167 */     this.lineData = new Dataset[20];
/* 168 */     this.auxLineData = new Dataset[20];
/*     */ 
/* 170 */     for (int i = 0; i < 20; i++) {
/* 171 */       this.dataOnRight[i] = false;
/* 172 */       this.datasetType[i] = 0;
/*     */     }
/* 174 */     this.yAxis = new Axis(this.leftDatasets, false, this.plotarea);
/* 175 */     this.auxYAxis = new Axis(this.rightDatasets, false, this.plotarea);
/* 176 */     this.auxYAxis.setSide(3);
/*     */ 
/* 178 */     this.auxLine = new Line(this.auxLineData, this.xAxis, this.auxYAxis, this.plotarea);
/* 179 */     this.stick = new Stick(this.stickData, this.xAxis, this.yAxis, this.plotarea);
/* 180 */     this.stick.unitScaling = false;
/* 181 */     this.auxStick = new Stick(this.auxStickData, this.xAxis, this.auxYAxis, this.plotarea);
/* 182 */     this.auxStick.unitScaling = false;
/*     */   }
/*     */ 
/*     */   public void setAuxAxis(AxisInterface newAuxYAxis)
/*     */   {
/* 189 */     this.auxYAxis = newAuxYAxis;
/*     */   }
/*     */ 
/*     */   public void setAuxLine(Line newAuxLine)
/*     */   {
/* 197 */     this.auxLine = newAuxLine;
/*     */   }
/*     */ 
/*     */   public void setAuxStick(Stick newAuxStick)
/*     */   {
/* 205 */     this.auxStick = newAuxStick;
/*     */   }
/*     */ 
/*     */   public void setDatasetType(int setNumber, int type)
/*     */   {
/* 213 */     if ((type == 0) || (type == 1))
/*     */       try {
/* 215 */         this.datasetType[setNumber] = type;
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/*     */       }
/*     */   }
/*     */ 
/*     */   public void setStick(Stick newStick)
/*     */   {
/* 225 */     this.stick = newStick;
/*     */   }
/*     */ 
/*     */   public TwinAxisDateComboChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TwinAxisDateComboChart(String s)
/*     */   {
/* 243 */     super(s);
/*     */   }
/*     */ 
/*     */   public TwinAxisDateComboChart(String name, Locale locale)
/*     */   {
/* 254 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public TwinAxisDateComboChart(Locale locale)
/*     */   {
/* 264 */     super(locale);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.TwinAxisDateComboChart
 * JD-Core Version:    0.6.2
 */