/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import jatools.component.chart.applet.DateStreamReader;
/*     */ import jatools.component.chart.chart.DateAxis;
/*     */ import jatools.component.chart.chart.Datum;
/*     */ import jatools.component.chart.chart.Globals;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public abstract class DateApp extends Bean
/*     */ {
/*  25 */   DateFormat dwellXFormatter = DateFormat.getInstance();
/*     */ 
/*     */   public String getDwellLabelXString(Datum dat) {
/*  28 */     double d = dat.getX();
/*  29 */     Date myDate = new Date((long)d);
/*     */ 
/*  31 */     String dateString = this.dwellXFormatter.format(myDate);
/*  32 */     int where = this.dwellXString.indexOf("XX");
/*  33 */     return this.dwellXString.substring(0, where) + dateString + 
/*  34 */       this.dwellXString.substring(where + 2);
/*     */   }
/*     */   public void getMyDatasets(String s) {
/*  37 */     ((DateStreamReader)this.parser).readStream(s);
/*     */   }
/*     */ 
/*     */   protected void initDateAxis(DateAxis ax)
/*     */   {
/*  47 */     String str = getParameter("startDate");
/*  48 */     if (str != null)
/*     */     {
/*  50 */       ax.setAxisStart(Double.parseDouble(str));
/*  51 */     }str = getParameter("endDate");
/*  52 */     if (str != null)
/*     */     {
/*  54 */       ax.setAxisEnd(Double.parseDouble(str));
/*  55 */     }str = getParameter("scalingType");
/*  56 */     if (str != null)
/*  57 */       ax.setScalingType(Integer.parseInt(str));
/*  58 */     str = getParameter("axisTimeZone");
/*  59 */     if (str != null)
/*  60 */       ax.setTimeZone(TimeZone.getTimeZone(str));
/*  61 */     TimeZone timeZone = ax.getTimeZone();
/*  62 */     str = getParameter("axisDateFormat");
/*  63 */     if (str != null) {
/*  64 */       DateFormat df = null;
/*     */       try {
/*  66 */         df = DateFormat.getDateInstance(3, ax.getGlobals().locale);
/*     */       }
/*     */       catch (MissingResourceException e) {
/*  69 */         df = DateFormat.getInstance();
/*     */       }
/*  71 */       if (timeZone != null)
/*  72 */         df.setTimeZone(timeZone);
/*  73 */       ((SimpleDateFormat)df).applyPattern(str);
/*  74 */       ax.setDateFormat(df);
/*     */     }
/*  76 */     str = getParameter("axisSecondaryDateFormat");
/*  77 */     if (str != null) {
/*  78 */       DateFormat df = null;
/*     */       try {
/*  80 */         df = DateFormat.getDateInstance(3, ax.getGlobals().locale);
/*     */       }
/*     */       catch (MissingResourceException e) {
/*  83 */         df = DateFormat.getInstance();
/*     */       }
/*  85 */       if (timeZone != null)
/*  86 */         df.setTimeZone(timeZone);
/*  87 */       ((SimpleDateFormat)df).applyPattern(str);
/*  88 */       ax.setSecondaryDateFormat(df);
/*     */     }
/*  90 */     str = getParameter("dwellLabelDateFormat");
/*  91 */     if (str != null) {
/*  92 */       ((SimpleDateFormat)this.dwellXFormatter).applyPattern(str);
/*     */     }
/*     */ 
/*  95 */     if (timeZone != null) {
/*  96 */       this.dwellXFormatter.setTimeZone(timeZone);
/*     */     }
/*  98 */     str = getParameter("dwellXString");
/*  99 */     if (str != null)
/* 100 */       this.dwellXString = str;
/*     */     else
/* 102 */       this.dwellXString = "XX";
/* 103 */     str = getParameter("dateStepSize");
/* 104 */     if (str != null)
/* 105 */       ax.setStepSize(Integer.parseInt(str));
/*     */   }
/*     */ 
/*     */   protected void initDateStreamReader()
/*     */   {
/* 113 */     if (this.parser == null)
/* 114 */       this.parser = new DateStreamReader(this.chart, this);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.DateApp
 * JD-Core Version:    0.6.2
 */