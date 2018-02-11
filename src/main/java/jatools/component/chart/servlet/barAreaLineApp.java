/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.component.chart.applet.ParameterParser;
/*     */ import jatools.component.chart.chart.Area;
/*     */ import jatools.component.chart.chart.Bar;
/*     */ import jatools.component.chart.chart.BarAreaLineChart;
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class barAreaLineApp extends Bean
/*     */ {
/*     */   public barAreaLineApp()
/*     */   {
/*     */   }
/*     */ 
/*     */   public barAreaLineApp(Properties defaultProperties)
/*     */   {
/*  29 */     this.properties = new Properties(defaultProperties);
/*     */   }
/*     */ 
/*     */   public void getMyOptions()
/*     */   {
/*  39 */     BarAreaLineChart c = (BarAreaLineChart)this.chart;
/*     */ 
/*  41 */     for (int i = 0; i < 20; i++) {
/*  42 */       String str = getParameter("dataset" + i + "Type");
/*     */ 
/*  44 */       if (str != null) {
/*  45 */         if (str.equalsIgnoreCase("Bar"))
/*  46 */           c.dataAllocation[i] = 0;
/*  47 */         else if (str.equalsIgnoreCase("Area"))
/*  48 */           c.dataAllocation[i] = 1;
/*     */         else {
/*  50 */           c.dataAllocation[i] = 2;
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
/*     */     }
/*     */ 
/*  69 */     str = getParameter("barBaseline");
/*     */ 
/*  71 */     if (str != null) {
/*  72 */       c.getBar().setBaseline(Double.valueOf(str).doubleValue());
/*     */     }
/*     */ 
/*  75 */     str = getParameter("barClusterWidth");
/*     */ 
/*  77 */     if (str != null) {
/*  78 */       c.getBar().setClusterWidth(Double.valueOf(str).doubleValue());
/*     */     }
/*     */ 
/*  81 */     str = getParameter("barLabelAngle");
/*     */ 
/*  83 */     if (str != null) {
/*  84 */       c.getBar().setLabelAngle(Integer.parseInt(str));
/*     */     }
/*     */ 
/* 101 */     str = getParameter("useValueLabels");
/*     */ 
/* 103 */     if (str != null) {
/* 104 */       c.getBar().setUseValueLabels(str.equalsIgnoreCase("true"));
/*     */     }
/*     */ 
/* 107 */     str = getParameter("dataPattern");
/*     */ 
/* 109 */     if (str != null) {
/* 110 */       c.getArea().setPattern(str);
/* 111 */       c.getBar().setPattern(str);
/*     */     }
/*     */ 
/* 116 */     String strFont = getParameter("barLabelFont");
/* 117 */     String strColor = getParameter("barLabelColor");
/*     */ 
/* 119 */     for (int i = 0; i < this.chart.getDatasets().length; i++) {
/* 120 */       if (this.chart.getDatasets()[i] != null) {
/* 121 */         if (strFont != null) {
/* 122 */           this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/*     */         }
/*     */ 
/* 125 */         if (strColor != null) {
/* 126 */           this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 131 */     str = getParameter("individualColors");
/*     */ 
/* 133 */     if (str != null) {
/* 134 */       c.setIndividualColors(str.equalsIgnoreCase("true"));
/*     */     }
/*     */ 
/* 137 */     str = getParameter("outlineColor");
/*     */ 
/* 139 */     if (str != null)
/* 140 */       this.parser.activateOutlineFills(this.parser.getColor(str), c.getIndividualColors());
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/* 149 */     initLocale();
/* 150 */     this.chart = new BarAreaLineChart("My Chart", this.userLocale);
/* 151 */     getOptions();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.barAreaLineApp
 * JD-Core Version:    0.6.2
 */