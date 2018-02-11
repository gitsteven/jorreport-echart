/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class SectorMapChart extends _Chart
/*     */ {
/*     */   SectorMap sectorMap;
/*  22 */   boolean individualColors = false;
/*     */ 
/*     */   public SectorMapChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SectorMapChart(String s)
/*     */   {
/*  38 */     super(s);
/*     */   }
/*     */ 
/*     */   public SectorMapChart(Locale locale)
/*     */   {
/*  47 */     super(locale);
/*     */   }
/*     */ 
/*     */   public SectorMapChart(String name, Locale locale)
/*     */   {
/*  57 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public void drawGraph()
/*     */   {
/*  65 */     if (this.canvas == null) {
/*  66 */       return;
/*     */     }
/*  68 */     drawGraph(this.canvas);
/*     */   }
/*     */ 
/*     */   public void drawGraph(Graphics g)
/*     */   {
/*  77 */     if (g == null)
/*  78 */       return;
/*  79 */     super.drawGraph(g);
/*  80 */     this.background.draw(g);
/*  81 */     if (this.individualColors)
/*  82 */       ((SectorMap)this.dataRepresentation).drawInd(g);
/*     */     else
/*  84 */       this.dataRepresentation.draw(g);
/*  85 */     if (this.legendVisible)
/*  86 */       this.legend.draw(g);
/*     */   }
/*     */ 
/*     */   public boolean getIndividualColors()
/*     */   {
/*  95 */     return this.individualColors;
/*     */   }
/*     */ 
/*     */   public SectorMap getSectorMap()
/*     */   {
/* 104 */     return this.sectorMap;
/*     */   }
/*     */ 
/*     */   protected void initChart()
/*     */   {
/* 112 */     initGlobals();
/* 113 */     setPlotarea(new Plotarea());
/* 114 */     setBackground(new Background());
/* 115 */     initDatasets();
/* 116 */     this.sectorMap = new SectorMap();
/* 117 */     setDataRepresentation(this.sectorMap);
/* 118 */     setLegend(new Legend());
/* 119 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   public void setIndividualColors(boolean ind)
/*     */   {
/* 128 */     this.individualColors = ind;
/* 129 */     if ((!ind) && ((this.legend instanceof PieLegend))) {
/* 130 */       Legend pl = new Legend();
/* 131 */       PieLegend l = (PieLegend)this.legend;
/* 132 */       pl.labelFont = l.labelFont;
/* 133 */       pl.backgroundGc = l.backgroundGc;
/* 134 */       pl.labelColor = l.labelColor;
/* 135 */       pl.iconWidth = l.iconWidth;
/* 136 */       pl.iconHeight = l.iconHeight;
/* 137 */       pl.iconGap = l.iconGap;
/* 138 */       pl.llX = l.llX;
/* 139 */       pl.llY = l.llY;
/* 140 */       pl.useDisplayList = l.useDisplayList;
/* 141 */       setLegend(pl);
/*     */     }
/* 143 */     else if (!(this.legend instanceof PieLegend)) {
/* 144 */       PieLegend pl = new PieLegend();
/* 145 */       Legend l = (Legend)this.legend;
/* 146 */       pl.labelFont = l.labelFont;
/* 147 */       pl.backgroundGc = l.backgroundGc;
/* 148 */       pl.labelColor = l.labelColor;
/* 149 */       pl.iconWidth = l.iconWidth;
/* 150 */       pl.iconHeight = l.iconHeight;
/* 151 */       pl.iconGap = l.iconGap;
/* 152 */       pl.llX = l.llX;
/* 153 */       pl.llY = l.llY;
/* 154 */       pl.useDisplayList = l.useDisplayList;
/* 155 */       setLegend(pl);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setSectorMap(SectorMap sm)
/*     */   {
/* 166 */     this.sectorMap = sm;
/* 167 */     setDataRepresentation(sm);
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 175 */     applyGeneralProperty(map);
/*     */ 
/* 177 */     if (getIndividualColors()) {
/* 178 */       map.put("individualColors", "true");
/*     */     }
/*     */ 
/* 181 */     if (getSectorMap().getLabelsOn());
/* 182 */     map.put("labelsOn", getSectorMap().getLabelsOn());
/*     */ 
/* 185 */     if (getSectorMap().getBaseColor() != null) {
/* 186 */       map.put("baseColor", ChartUtil.toString(getSectorMap().getBaseColor()));
/*     */     }
/*     */ 
/* 189 */     if (Double.isInfinite(getSectorMap().getBaseValue()))
/* 190 */       map.put("baseValue", "0.0");
/*     */     else {
/* 192 */       map.put("baseValue", String.valueOf(getSectorMap().getBaseValue()));
/*     */     }
/*     */ 
/* 195 */     if (getSectorMap().getSecondaryColor() != null) {
/* 196 */       map.put("sectorSecondaryColor", ChartUtil.toString(getSectorMap().getSecondaryColor()));
/*     */     }
/*     */ 
/* 199 */     if (getSectorMap().getUseGradientColoring()) {
/* 200 */       map.put("gradientColoring", "true");
/*     */     }
/*     */ 
/* 203 */     map.put("labelStyle", String.valueOf(getSectorMap().getLabelStyle()));
/*     */ 
/* 221 */     activateOutlineFills(map, getIndividualColors());
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.SectorMapChart
 * JD-Core Version:    0.6.2
 */