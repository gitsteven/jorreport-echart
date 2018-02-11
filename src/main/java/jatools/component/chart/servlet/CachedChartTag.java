/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import jatools.component.chart.applet.DataProvider;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ 
/*     */ public class CachedChartTag extends ChartTag
/*     */ {
/*  20 */   String cacheDirectory = "/images/chartImages/";
/*     */ 
/*     */   public void setCacheDirectory(String s)
/*     */   {
/*  27 */     if (s == null)
/*  28 */       return;
/*  29 */     this.cacheDirectory = s;
/*     */   }
/*     */ 
/*     */   protected void doChart() throws Exception {
/*  33 */     loadStyle();
/*  34 */     Bean chartBean = createChartBean();
/*  35 */     applyLocalizedResources(chartBean);
/*  36 */     chartBean.setProperty("useCache", "true");
/*  37 */     chartBean.setProperty("byteStream", "false");
/*  38 */     if ((this.dataProvider == null) && (this.dataProviderID != null))
/*  39 */       this.dataProvider = ((DataProvider)this.pageContext.getAttribute(this.dataProviderID));
/*  40 */     if ((this.dataProvider == null) && (this.dataProviderID != null))
/*  41 */       this.dataProvider = ((DataProvider)this.pageContext.findAttribute(this.dataProviderID));
/*  42 */     if (this.dataProvider != null) {
/*  43 */       chartBean.setDataProvider(this.dataProvider);
/*     */     }
/*  45 */     chartBean.setProperty("writeDirectory", this.pageContext.getServletContext().getRealPath(this.cacheDirectory));
/*     */ 
/*  47 */     String contextPath = ((HttpServletRequest)this.pageContext.getRequest()).getContextPath();
/*  48 */     String filename = chartBean.getFileName();
/*     */ 
/*  50 */     String imageType = chartBean.getProperty("imageType");
/*     */ 
/*  53 */     char[] chars = this.cacheDirectory.toCharArray();
/*  54 */     if (chars[0] != '/')
/*  55 */       this.cacheDirectory = ("/" + this.cacheDirectory);
/*  56 */     if (chars[(chars.length - 1)] != '/') {
/*  57 */       this.cacheDirectory += "/";
/*     */     }
/*  59 */     if ((imageType != null) && ((imageType.equalsIgnoreCase("flash")) || (imageType.equalsIgnoreCase("swf")))) {
/*  60 */       doFlashChart(chartBean, contextPath + this.cacheDirectory + filename);
/*  61 */       return;
/*     */     }
/*  63 */     if ((imageType != null) && (imageType.equalsIgnoreCase("svg"))) {
/*  64 */       doSVGChart(chartBean, contextPath + this.cacheDirectory + filename);
/*  65 */       return;
/*     */     }
/*     */ 
/*  68 */     if (this.useLinkMap) {
/*  69 */       String mapName = chartBean.getProperty("mapName");
/*  70 */       if (mapName == null)
/*  71 */         chartBean.setProperty("mapName", filename);
/*  72 */       this.bodyOut.println(chartBean.getLinkMap());
/*     */     }
/*  74 */     String chartHeight = chartBean.getProperty("height");
/*  75 */     String chartWidth = chartBean.getProperty("width");
/*  76 */     if (chartWidth == null)
/*  77 */       chartWidth = "200";
/*  78 */     if (chartHeight == null) {
/*  79 */       chartHeight = "150";
/*     */     }
/*  81 */     this.bodyOut.print("<IMG SRC=\"" + contextPath + this.cacheDirectory + filename + "\" ");
/*  82 */     this.bodyOut.print("width=\"" + chartWidth + "\" height=\"" + chartHeight + "\" ");
/*  83 */     if (this.useLinkMap) {
/*  84 */       String mapName = chartBean.getProperty("mapName");
/*  85 */       this.bodyOut.print(" BORDER=0 ISMAP USEMAP=#" + mapName);
/*     */     }
/*  87 */     this.bodyOut.print(">");
/*     */   }
/*     */ 
/*     */   protected void doFlashChart(Bean chartBean, String movieSource)
/*     */     throws IOException
/*     */   {
/*  95 */     String chartHeight = chartBean.getProperty("height");
/*  96 */     if (chartHeight == null)
/*  97 */       chartHeight = Integer.toString(150);
/*  98 */     String chartWidth = chartBean.getProperty("width");
/*  99 */     if (chartWidth == null)
/* 100 */       chartWidth = Integer.toString(200);
/* 101 */     int i = 0;
/*     */ 
/* 103 */     this.bodyOut.println(ChartTag.getFlashString(chartWidth, chartHeight, movieSource));
/*     */   }
/*     */ 
/*     */   protected void doSVGChart(Bean chartBean, String svgSource)
/*     */     throws IOException
/*     */   {
/* 110 */     String chartHeight = chartBean.getProperty("height");
/* 111 */     if (chartHeight == null)
/* 112 */       chartHeight = Integer.toString(150);
/* 113 */     String chartWidth = chartBean.getProperty("width");
/* 114 */     if (chartWidth == null) {
/* 115 */       chartWidth = Integer.toString(200);
/*     */     }
/* 117 */     this.bodyOut.println("\t<embed width=\"" + chartWidth + "\" height=\"" + chartHeight + "\"");
/* 118 */     this.bodyOut.println("\t\ttype=\"image/svg-xml\"");
/* 119 */     this.bodyOut.println("\t\tsrc=\"" + svgSource + "\"");
/* 120 */     this.bodyOut.println("\t</embed>");
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.CachedChartTag
 * JD-Core Version:    0.6.2
 */