/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.component.chart.chart.Bar;
/*     */ import jatools.component.chart.chart.BarChart;
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.chart.DataRepresentation;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.HorizBarChart;
/*     */ import java.awt.Color;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class barApp extends Bean
/*     */ {
/*     */   public barApp()
/*     */   {
/*     */   }
/*     */ 
/*     */   public barApp(Properties defaultProperties)
/*     */   {
/*  36 */     this.properties = new Properties(defaultProperties);
/*     */   }
/*     */ 
/*     */   public void getMyOptions()
/*     */   {
/*  45 */     HorizBarChart b = (HorizBarChart)this.chart;
/*     */ 
/*  47 */     String str = getParameter("individualColors");
/*     */ 
/*  49 */     if (str != null) {
/*  50 */       b.setIndividualColors(Boolean.valueOf(str).booleanValue());
/*     */     }
/*     */ 
/*  53 */     str = getParameter("barBaseline");
/*     */ 
/*  55 */     if (str != null) {
/*  56 */       b.getBar().setBaseline(Double.valueOf(str).doubleValue());
/*     */     }
/*     */ 
/*  59 */     str = getParameter("barClusterWidth");
/*     */ 
/*  61 */     if (str != null) {
/*  62 */       b.getBar().setClusterWidth(Double.valueOf(str).doubleValue());
/*     */     }
/*     */ 
/*  79 */     str = getParameter("useValueLabels");
/*     */ 
/*  81 */     if (str != null) {
/*  82 */       b.getBar().setUseValueLabels(str.equalsIgnoreCase("true"));
/*     */     }
/*     */ 
/* 104 */     String strFont = getParameter("barLabelFont");
/* 105 */     String strColor = getParameter("barLabelColor");
/* 106 */     for (int i = 0; i < this.chart.getDatasets().length; i++)
/* 107 */       if (this.chart.getDatasets()[i] != null) {
/* 108 */         if (strFont != null) {
/* 109 */           this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/*     */         }
/* 111 */         if (strColor != null)
/* 112 */           this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor));
/*     */       }
/*     */   }
/*     */ 
/*     */   public static void applyMyOptions(BarChart chart, Properties options)
/*     */   {
/* 125 */     HorizBarChart b = (HorizBarChart)chart;
/*     */ 
/* 127 */     options.setProperty("individualColors", b.getIndividualColors());
/* 128 */     options.setProperty("barBaseline", b.getBar().getBaseline());
/* 129 */     options.setProperty("barClusterWidth", b.getBar().getClusterWidth());
/* 130 */     options.setProperty("barLabelsOn", b.getBar().getLabelsOn());
/* 131 */     options.setProperty("barLabelAngle", b.getBar().getLabelAngle());
/* 132 */     options.setProperty("useValueLabels", b.getBar().getUseValueLabels());
/* 133 */     options.setProperty("outlineColor", stringColor(b.getBar().getOutlineColor()));
/* 134 */     options.setProperty("labelPrecision", chart.getDataRepresentation().getLabelPrecision());
/* 135 */     Bean.applyOptions(chart, options);
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/* 144 */     initLocale();
/* 145 */     this.chart = new HorizBarChart("My Chart", this.userLocale);
/*     */ 
/* 148 */     getOptions();
/*     */   }
/*     */ 
/*     */   public static String stringColor(Color color)
/*     */   {
/* 159 */     if (color == null) {
/* 160 */       return "null";
/*     */     }
/* 162 */     return "0x" + Integer.toHexString(color.getRGB());
/*     */   }
/*     */ 
/*     */   public void applyProperties(ChartInterface chart, Properties properties)
/*     */   {
/* 170 */     HorizBarChart b = (HorizBarChart)chart;
/* 171 */     applyMyOptions(b, properties);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.barApp
 * JD-Core Version:    0.6.2
 */