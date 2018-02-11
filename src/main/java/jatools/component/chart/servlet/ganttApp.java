/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import jatools.component.chart.applet.GanttDateReader;
/*     */ import jatools.component.chart.applet.ParameterParser;
/*     */ import jatools.component.chart.chart.Bar;
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.DateAxis;
/*     */ import jatools.component.chart.chart.Datum;
/*     */ import jatools.component.chart.chart.GanttChart;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class ganttApp extends dateLineApp
/*     */ {
/*  20 */   String dwellStartString = "Start: #";
/*  21 */   String dwellEndString = "End: #";
/*  22 */   String undefinedString = "Indefinite";
/*     */   Datum dwellDat;
/*     */ 
/*     */   public ganttApp()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ganttApp(Properties properties)
/*     */   {
/*  37 */     this.properties = properties;
/*     */   }
/*     */ 
/*     */   public void getMyOptions()
/*     */   {
/*  46 */     GanttChart b = (GanttChart)this.chart;
/*     */ 
/*  48 */     initDateAxis((DateAxis)b.getYAxis());
/*     */ 
/*  50 */     String str = getParameter("barLabelsOn");
/*     */ 
/*  52 */     if ((str != null) && 
/*  53 */       (!str.equalsIgnoreCase("false"))) {
/*  54 */       b.getBar().setLabelsOn(true);
/*     */     }
/*     */ 
/*  58 */     str = getParameter("barClusterWidth");
/*     */ 
/*  60 */     if (str != null) {
/*  61 */       b.getBar().setClusterWidth(Double.valueOf(str).doubleValue());
/*     */     }
/*     */ 
/*  64 */     str = getParameter("barLabelAngle");
/*     */ 
/*  66 */     if (str != null) {
/*  67 */       b.getBar().setLabelAngle(Integer.valueOf(str).intValue());
/*     */     }
/*     */ 
/*  70 */     str = getParameter("individualColors");
/*     */ 
/*  72 */     if (str != null) {
/*  73 */       b.setIndividualColors(str.equalsIgnoreCase("true"));
/*     */     }
/*     */ 
/*  78 */     str = getParameter("useValueLabels");
/*     */ 
/*  80 */     if (str != null) {
/*  81 */       b.getBar().setUseValueLabels(str.equalsIgnoreCase("true"));
/*     */     }
/*     */ 
/*  84 */     str = getParameter("dwellStartString");
/*     */ 
/*  86 */     if (str != null) {
/*  87 */       this.dwellStartString = str;
/*     */     }
/*     */ 
/*  90 */     str = getParameter("dwellEndString");
/*     */ 
/*  92 */     if (str != null) {
/*  93 */       this.dwellEndString = str;
/*     */     }
/*     */ 
/*  96 */     str = getParameter("dwellIndefiniteString");
/*     */ 
/*  98 */     if (str != null) {
/*  99 */       this.undefinedString = str;
/*     */     }
/*     */ 
/* 102 */     str = getParameter("outlineColor");
/*     */ 
/* 104 */     if (str != null)
/* 105 */       this.parser.activateOutlineFills(this.parser.getColor(str), b.getIndividualColors());
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/* 113 */     initLocale();
/* 114 */     this.chart = new GanttChart("My Chart");
/* 115 */     this.parser = new GanttDateReader(this.chart, this);
/* 116 */     getOptions();
/*     */   }
/*     */ 
/*     */   protected String getDwellLabelXString(Datum dat, Dataset dataset)
/*     */   {
/* 123 */     if (this.dwellDat == null) {
/* 124 */       this.dwellDat = new Datum(0.0D, 0.0D, this.chart.getGlobals());
/*     */     }
/*     */ 
/* 127 */     this.dwellXString = this.dwellStartString;
/*     */ 
/* 129 */     if (dat.getY2() == -1.0D) {
/* 130 */       return this.undefinedString;
/*     */     }
/*     */ 
/* 133 */     this.dwellDat.setX(dat.getY2());
/*     */ 
/* 135 */     return super.getDwellLabelXString(this.dwellDat);
/*     */   }
/*     */ 
/*     */   protected String getDwellLabelYString(Datum dat, Dataset dataset)
/*     */   {
/* 142 */     if (this.dwellDat == null) {
/* 143 */       this.dwellDat = new Datum(0.0D, 0.0D, this.chart.getGlobals());
/*     */     }
/*     */ 
/* 146 */     this.dwellXString = this.dwellEndString;
/*     */ 
/* 148 */     if (dat.getY() == -1.0D) {
/* 149 */       return this.undefinedString;
/*     */     }
/*     */ 
/* 152 */     this.dwellDat.setX(dat.getY());
/*     */ 
/* 154 */     return super.getDwellLabelXString(this.dwellDat);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.ganttApp
 * JD-Core Version:    0.6.2
 */