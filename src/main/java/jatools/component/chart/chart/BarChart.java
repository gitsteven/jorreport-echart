/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class BarChart extends _Chart
/*     */   implements BarInterface
/*     */ {
/*     */   Bar bar;
/*  23 */   boolean individualColors = false;
/*     */ 
/*     */   public BarChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BarChart(String s)
/*     */   {
/*  38 */     super(s);
/*     */   }
/*     */ 
/*     */   public BarChart(String name, Locale locale)
/*     */   {
/*  50 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public BarChart(Locale locale)
/*     */   {
/*  60 */     super(locale);
/*     */   }
/*     */ 
/*     */   public void drawGraph()
/*     */   {
/*  67 */     if (this.canvas == null) {
/*  68 */       return;
/*     */     }
/*  70 */     drawGraph(this.canvas);
/*     */   }
/*     */ 
/*     */   public void drawGraph(Graphics g)
/*     */   {
/*  76 */     if (g == null)
/*  77 */       return;
/*  78 */     super.drawGraph(g);
/*  79 */     this.background.draw(g);
/*  80 */     this.plotarea.draw(g);
/*  81 */     if (this.xAxisVisible)
/*  82 */       this.xAxis.draw(g);
/*     */     else
/*  84 */       this.xAxis.scale();
/*  85 */     if (this.yAxisVisible)
/*  86 */       this.yAxis.draw(g);
/*     */     else
/*  88 */       this.yAxis.scale();
/*  89 */     if (this.individualColors)
/*  90 */       ((Bar)this.dataRepresentation).drawInd(g);
/*     */     else
/*  92 */       this.dataRepresentation.draw(g);
/*  93 */     super.drawAxisOverlays(g);
/*  94 */     if (this.legendVisible)
/*  95 */       this.legend.draw(g);
/*     */   }
/*     */ 
/*     */   public Bar getBar()
/*     */   {
/* 103 */     return this.bar;
/*     */   }
/*     */ 
/*     */   public boolean getIndividualColors()
/*     */   {
/* 111 */     return this.individualColors;
/*     */   }
/*     */ 
/*     */   protected void initAxes()
/*     */   {
/* 117 */     setXAxis(new LabelAxis());
/* 118 */     this.xAxis.setBarScaling(true);
/* 119 */     this.xAxis.setSide(0);
/* 120 */     setYAxis(new Axis());
/* 121 */     this.yAxis.setBarScaling(true);
/*     */   }
/*     */ 
/*     */   protected void initChart()
/*     */   {
/* 128 */     initGlobals();
/* 129 */     setPlotarea(new Plotarea());
/* 130 */     setBackground(new Background());
/* 131 */     initDatasets();
/* 132 */     initAxes();
/* 133 */     this.bar = new Bar();
/* 134 */     setDataRepresentation(this.bar);
/* 135 */     setLegend(new Legend());
/* 136 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   public void setBar(Bar b)
/*     */   {
/* 144 */     this.bar = b;
/* 145 */     setDataRepresentation(b);
/*     */   }
/*     */ 
/*     */   public void setIndividualColors(boolean ind)
/*     */   {
/* 153 */     this.individualColors = ind;
/*     */ 
/* 155 */     if ((!ind) && ((this.legend instanceof PieLegend))) {
/* 156 */       Legend pl = new Legend();
/* 157 */       PieLegend l = (PieLegend)this.legend;
/* 158 */       pl.verticalLayout = l.verticalLayout;
/* 159 */       pl.labelFont = l.labelFont;
/* 160 */       pl.backgroundGc = l.backgroundGc;
/* 161 */       pl.labelColor = l.labelColor;
/* 162 */       pl.iconWidth = l.iconWidth;
/* 163 */       pl.iconHeight = l.iconHeight;
/* 164 */       pl.iconGap = l.iconGap;
/* 165 */       pl.llX = l.llX;
/* 166 */       pl.llY = l.llY;
/* 167 */       pl.useDisplayList = l.useDisplayList;
/* 168 */       setLegend(pl);
/*     */     }
/*     */ 
/* 171 */     if ((ind) && (!(this.legend instanceof PieLegend))) {
/* 172 */       PieLegend pl = new PieLegend();
/* 173 */       Legend l = (Legend)this.legend;
/* 174 */       pl.verticalLayout = l.verticalLayout;
/* 175 */       pl.labelFont = l.labelFont;
/* 176 */       pl.backgroundGc = l.backgroundGc;
/* 177 */       pl.labelColor = l.labelColor;
/* 178 */       pl.iconWidth = l.iconWidth;
/* 179 */       pl.iconHeight = l.iconHeight;
/* 180 */       pl.iconGap = l.iconGap;
/* 181 */       pl.llX = l.llX;
/* 182 */       pl.llY = l.llY;
/* 183 */       pl.useDisplayList = l.useDisplayList;
/* 184 */       setLegend(pl);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 192 */     applyGeneralProperty(map);
/*     */ 
/* 207 */     if (getBar().getUseValueLabels())
/* 208 */       map.put("useValueLabels", "true");
/*     */     else {
/* 210 */       map.put("useValueLabels", "false");
/*     */     }
/* 212 */     map.put("barLabelFont", ChartUtil.toString(getDatasets()[0].getLabelFont()));
/* 213 */     map.put("barLabelColor", ChartUtil.toString(getDatasets()[0].getLabelColor()));
/* 214 */     if (getIndividualColors()) {
/* 215 */       map.put("individualColors", "true");
/*     */     }
/*     */ 
/* 219 */     if (getBar().getOutlineColor() != null) {
/* 220 */       map.put("outlineColor", ChartUtil.toString(getBar().getOutlineColor()));
/*     */     }
/* 222 */     map.put("barBaseline", String.valueOf(getBar().getBaseline()));
/* 223 */     map.put("barClusterWidth", String.valueOf(getBar().getClusterWidth()));
/*     */ 
/* 225 */     activateOutlineFills(map, getIndividualColors());
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.BarChart
 * JD-Core Version:    0.6.2
 */