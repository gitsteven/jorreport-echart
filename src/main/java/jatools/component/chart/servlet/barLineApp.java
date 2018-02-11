/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.component.chart.applet.ParameterParser;
/*     */ import jatools.component.chart.chart.Bar;
/*     */ import jatools.component.chart.chart.BarLineChart;
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.Line;
/*     */ import jatools.component.chart.chart._Chart;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class barLineApp extends Bean
/*     */ {
/*     */   public barLineApp()
/*     */   {
/*     */   }
/*     */ 
/*     */   public barLineApp(Properties defaultProperties)
/*     */   {
/*  31 */     this.properties = new Properties(defaultProperties);
/*     */   }
/*     */ 
/*     */   public void getMyOptions()
/*     */   {
/*  41 */     BarLineChart c = (BarLineChart)this.chart;
/*     */ 
/*  43 */     for (int i = 0; i < _Chart.MAX_DATASETS; i++) {
/*  44 */       String str = getParameter("dataset" + i + "Type");
/*     */ 
/*  46 */       if (str != null) {
/*  47 */         if (str.equalsIgnoreCase("Bar"))
/*  48 */           c.dataAllocation[i] = 0;
/*  49 */         else if (str.equalsIgnoreCase("Line")) {
/*  50 */           c.dataAllocation[i] = 1;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  55 */     String str = getParameter("stackedBar");
/*     */ 
/*  57 */     if ((str != null) && 
/*  58 */       (str.equalsIgnoreCase("true"))) {
/*  59 */       c.setStackedBar(true);
/*     */     }
/*     */ 
/*  63 */     str = getParameter("barLabelsOn");
/*     */ 
/*  65 */     if (str != null) {
/*  66 */       c.getBar().setLabelsOn(true);
/*  67 */       c.getLine().setLabelsOn(true);
/*     */     } else {
/*  69 */       c.getBar().setLabelsOn(false);
/*  70 */       c.getBar().setLabelsOn(false);
/*     */     }
/*     */ 
/*  73 */     str = getParameter("userValueLabels");
/*     */ 
/*  75 */     if (str != null) {
/*  76 */       c.getBar().setUseValueLabels(true);
/*  77 */       c.getLine().setUseValueLabels(true);
/*     */     } else {
/*  79 */       c.getBar().setUseValueLabels(false);
/*  80 */       c.getLine().setUseValueLabels(false);
/*     */     }
/*     */ 
/*  83 */     str = getParameter("smoothLine");
/*     */ 
/*  85 */     if (str != null) {
/*  86 */       c.getLine().setSmooth(Integer.parseInt(str));
/*     */     }
/*     */ 
/*  89 */     str = getParameter("barBaseline");
/*     */ 
/*  91 */     if (str != null) {
/*  92 */       c.getBar().setBaseline(Double.valueOf(str).doubleValue());
/*     */     }
/*     */ 
/*  95 */     str = getParameter("barClusterWidth");
/*     */ 
/*  97 */     if (str != null) {
/*  98 */       c.getBar().setClusterWidth(Double.valueOf(str).doubleValue());
/*     */     }
/*     */ 
/* 101 */     str = getParameter("useValueLabels");
/*     */ 
/* 103 */     if (str != null) {
/* 104 */       c.getBar().setUseValueLabels(true);
/* 105 */       c.getLine().setUseValueLabels(true);
/*     */     }
/*     */ 
/* 126 */     str = getParameter("dataPattern");
/*     */ 
/* 128 */     if (str != null) {
/* 129 */       c.getBar().setPattern(str);
/* 130 */       c.getLine().setPattern(str);
/*     */     }
/*     */ 
/* 133 */     str = getParameter("barLabelAngle");
/*     */ 
/* 135 */     if (str != null) {
/* 136 */       c.getBar().setLabelAngle(Integer.parseInt(str));
/* 137 */       c.getLine().setLabelAngle(Integer.parseInt(str));
/*     */     }
/*     */ 
/* 142 */     String strFont = getParameter("barLabelFont");
/* 143 */     String strColor = getParameter("barLabelColor");
/*     */ 
/* 145 */     for (int i = 0; i < this.chart.getDatasets().length; i++) {
/* 146 */       if (this.chart.getDatasets()[i] != null) {
/* 147 */         if (strFont != null) {
/* 148 */           this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/*     */         }
/*     */ 
/* 151 */         if (strColor != null) {
/* 152 */           this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 157 */     str = getParameter("individualColors");
/*     */ 
/* 159 */     if (str != null) {
/* 160 */       c.setIndividualColors(str.equalsIgnoreCase("true"));
/*     */     }
/*     */ 
/* 163 */     str = getParameter("errorBars");
/*     */ 
/* 165 */     if (str != null) {
/* 166 */       c.getBar().setDoErrorBars(true);
/*     */     }
/*     */ 
/* 169 */     str = getParameter("outlineColor");
/*     */ 
/* 171 */     if (str != null)
/* 172 */       for (int j = 0; j < c.getDatasets().length; j++)
/* 173 */         if (c.dataAllocation[j] == 0)
/* 174 */           this.parser.activateOutlineFills(c.getDatasets()[j], this.parser.getColor(str), 
/* 175 */             c.getIndividualColors());
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/* 186 */     initLocale();
/* 187 */     this.chart = new BarLineChart(this.userLocale);
/* 188 */     getOptions();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.barLineApp
 * JD-Core Version:    0.6.2
 */