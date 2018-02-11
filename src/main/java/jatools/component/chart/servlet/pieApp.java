/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.component.chart.applet.ParameterParser;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.Datum;
/*     */ import jatools.component.chart.chart.Pie;
/*     */ import jatools.component.chart.chart.PieChart;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class pieApp extends Bean
/*     */ {
/*     */   public pieApp()
/*     */   {
/*     */   }
/*     */ 
/*     */   public pieApp(Properties defaultProperties)
/*     */   {
/*  24 */     this.properties = new Properties(defaultProperties);
/*     */   }
/*     */ 
/*     */   public void getMyOptions()
/*     */   {
/*  30 */     PieChart p = (PieChart)this.chart;
/*  31 */     Pie pie = p.getPie();
/*  32 */     String str = getParameter("explodeSlice");
/*  33 */     if (str != null)
/*  34 */       pie.setExplosion(Integer.valueOf(str).intValue(), 0.05D);
/*  35 */     str = getParameter("explodeSlices");
/*  36 */     if (str != null) {
/*  37 */       double[] explosions = this.parser.getVals(str);
/*  38 */       for (int j = 0; j < explosions.length; j++) {
/*  39 */         pie.setExplosion(j, explosions[j]);
/*     */       }
/*     */     }
/*  42 */     str = getParameter("pointerLengths");
/*  43 */     if (str != null) {
/*  44 */       double[] vals = this.parser.getVals(str);
/*  45 */       int n = Math.min(vals.length, this.chart.getDatasets()[0].getData().size());
/*  46 */       for (int i = 0; i < n; i++) {
/*  47 */         this.chart.getDatasets()[0].getDataElementAt(i).setY3(vals[i]);
/*     */       }
/*     */     }
/*  50 */     str = getParameter("textLabelsOn");
/*  51 */     if (str != null) {
/*  52 */       pie.setTextLabelsOn(true);
/*     */     }
/*  54 */     str = getParameter("valueLabelsOn");
/*  55 */     if (str != null) {
/*  56 */       pie.setValueLabelsOn(true);
/*     */     }
/*  58 */     str = getParameter("percentLabelsOff");
/*  59 */     if (str != null) {
/*  60 */       pie.setPercentLabelsOn(false);
/*     */     }
/*  62 */     str = getParameter("labelPosition");
/*  63 */     if (str != null) {
/*  64 */       pie.setLabelPosition(Integer.parseInt(str));
/*     */     }
/*  66 */     str = getParameter("startDegrees");
/*  67 */     if (str != null) {
/*  68 */       pie.setStartDegrees(Integer.parseInt(str));
/*     */     }
/*  70 */     str = getParameter("pieWidth");
/*  71 */     if (str != null) {
/*  72 */       pie.setWidth(Double.valueOf(str).doubleValue());
/*     */     }
/*  74 */     str = getParameter("pieHeight");
/*  75 */     if (str != null) {
/*  76 */       pie.setHeight(Double.valueOf(str).doubleValue());
/*     */     }
/*  78 */     str = getParameter("xLoc");
/*  79 */     if (str != null) {
/*  80 */       pie.setXLoc(Double.valueOf(str).doubleValue());
/*     */     }
/*  82 */     str = getParameter("yLoc");
/*  83 */     if (str != null) {
/*  84 */       pie.setYLoc(Double.valueOf(str).doubleValue());
/*     */     }
/*  86 */     str = getParameter("labelFont");
/*  87 */     if (str != null) {
/*  88 */       pie.setLabelFont(this.parser.getFont(str));
/*     */     }
/*  90 */     str = getParameter("labelColor");
/*  91 */     if (str != null) {
/*  92 */       pie.setLabelColor(this.parser.getColor(str));
/*     */     }
/*  94 */     str = getParameter("percentPrecision");
/*  95 */     if (str != null) {
/*  96 */       ((NumberFormat)pie.getPercentFormat()).setMinimumFractionDigits(Integer.parseInt(str));
/*  97 */       ((NumberFormat)pie.getPercentFormat()).setMaximumFractionDigits(Integer.parseInt(str));
/*     */     }
/*  99 */     str = getParameter("lineColor");
/* 100 */     if (str != null) {
/* 101 */       pie.setLineColor(this.parser.getColor(str));
/*     */     }
/* 103 */     str = getParameter("outlineColor");
/* 104 */     if (str != null) {
/* 105 */       this.parser.activateOutlineFills(this.parser.getColor(str), true);
/*     */     }
/*     */ 
/* 113 */     String strFont = getParameter("pieLabelFont");
/* 114 */     String strColor = getParameter("pieLabelColor");
/* 115 */     if (strFont != null)
/* 116 */       pie.setLabelFont(ChartUtil.getFont(strFont));
/* 117 */     if (strColor != null)
/* 118 */       pie.setLabelColor(ChartUtil.getColor(strColor)); 
/*     */   }
/*     */ 
/* 121 */   public void init() { initLocale();
/* 122 */     this.chart = new PieChart(this.userLocale);
/* 123 */     getOptions();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.pieApp
 * JD-Core Version:    0.6.2
 */