/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.component.chart.applet.ParameterParser;
/*     */ import jatools.component.chart.chart.Area;
/*     */ import jatools.component.chart.chart.Bar;
/*     */ import jatools.component.chart.chart.BarAreaChart;
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class barAreaApp extends Bean
/*     */ {
/*     */   public barAreaApp()
/*     */   {
/*     */   }
/*     */ 
/*     */   public barAreaApp(Properties defaultProperties)
/*     */   {
/*  23 */     this.properties = new Properties(defaultProperties);
/*     */   }
/*     */ 
/*     */   public void getMyOptions()
/*     */   {
/*  30 */     BarAreaChart c = (BarAreaChart)this.chart;
/*     */ 
/*  32 */     for (int i = 0; i < 20; i++) {
/*  33 */       String str = getParameter("dataset" + i + "Type");
/*  34 */       if (str != null) {
/*  35 */         if (str.equalsIgnoreCase("Bar"))
/*  36 */           c.dataAllocation[i] = 0;
/*  37 */         else if (str.equalsIgnoreCase("Area")) {
/*  38 */           c.dataAllocation[i] = 1;
/*     */         }
/*     */       }
/*     */     }
/*  42 */     String str = getParameter("stackedBar");
/*  43 */     if ((str != null) && 
/*  44 */       (str.equalsIgnoreCase("true")))
/*  45 */       c.setStackedBar(true);
/*  46 */     str = getParameter("barLabelsOn");
/*  47 */     if (str != null)
/*  48 */       c.getBar().setLabelsOn(true);
/*  49 */     str = getParameter("barBaseline");
/*  50 */     if (str != null)
/*  51 */       c.getBar().setBaseline(Double.valueOf(str).doubleValue());
/*  52 */     str = getParameter("barClusterWidth");
/*  53 */     if (str != null)
/*  54 */       c.getBar().setClusterWidth(Double.valueOf(str).doubleValue());
/*  55 */     str = getParameter("barLabelAngle");
/*  56 */     if (str != null) {
/*  57 */       c.getBar().setLabelAngle(Integer.parseInt(str));
/*     */     }
/*     */ 
/*  72 */     str = getParameter("useValueLabels");
/*  73 */     if (str != null) {
/*  74 */       c.getBar().setUseValueLabels(str.equalsIgnoreCase("true"));
/*     */     }
/*  76 */     str = getParameter("dataPattern");
/*  77 */     if (str != null) {
/*  78 */       c.getArea().setPattern(str);
/*  79 */       c.getBar().setPattern(str);
/*     */     }
/*     */ 
/*  83 */     String strFont = getParameter("barLabelFont");
/*  84 */     String strColor = getParameter("barLabelColor");
/*  85 */     for (int i = 0; i < this.chart.getDatasets().length; i++) {
/*  86 */       if (this.chart.getDatasets()[i] != null) {
/*  87 */         if (strFont != null) {
/*  88 */           this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/*     */         }
/*  90 */         if (strColor != null) {
/*  91 */           this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor));
/*     */         }
/*     */       }
/*     */     }
/*  95 */     str = getParameter("individualColors");
/*  96 */     if (str != null)
/*  97 */       c.setIndividualColors(str.equalsIgnoreCase("true"));
/*  98 */     str = getParameter("outlineColor");
/*  99 */     if (str != null)
/* 100 */       this.parser.activateOutlineFills(this.parser.getColor(str), c.getIndividualColors());
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/* 105 */     initLocale();
/* 106 */     this.chart = new BarAreaChart("My Chart", this.userLocale);
/* 107 */     getOptions();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.barAreaApp
 * JD-Core Version:    0.6.2
 */