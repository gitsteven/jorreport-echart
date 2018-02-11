/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import jatools.component.chart.applet.DataProvider;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.Datum;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ 
/*     */ public class AppletChartTag extends ChartTag
/*     */ {
/*     */   protected String codebase;
/*     */   protected String archive;
/*  34 */   String delimiter = ",";
/*     */ 
/*  36 */   Properties properties = new Properties();
/*     */ 
/*     */   public int doAfterBody() throws JspException {
/*  39 */     this.properties = null;
/*  40 */     return super.doAfterBody();
/*     */   }
/*     */ 
/*     */   public void setCodebase(String s)
/*     */   {
/*  48 */     this.codebase = s;
/*     */   }
/*     */ 
/*     */   public void setArchive(String s)
/*     */   {
/*  56 */     this.archive = s;
/*     */   }
/*     */ 
/*     */   protected void doChart() throws Exception {
/*  60 */     if (this.properties == null)
/*  61 */       this.properties = new Properties();
/*  62 */     loadStyle();
/*  63 */     if (this.style != null) {
/*  64 */       Enumeration en = this.style.keys();
/*  65 */       while (en.hasMoreElements()) {
/*  66 */         String key = (String)en.nextElement();
/*  67 */         this.properties.setProperty(key, this.style.getProperty(key));
/*     */       }
/*     */     }
/*     */ 
/*  71 */     applyLocalizedResources();
/*     */ 
/*  74 */     String s = this.properties.getProperty("delimiter");
/*  75 */     if (s != null) {
/*  76 */       this.delimiter = s;
/*     */     }
/*  78 */     if ((this.dataProvider == null) && (this.dataProviderID != null))
/*  79 */       this.dataProvider = ((DataProvider)this.pageContext.getAttribute(this.dataProviderID));
/*  80 */     if ((this.dataProvider == null) && (this.dataProviderID != null))
/*  81 */       this.dataProvider = ((DataProvider)this.pageContext.findAttribute(this.dataProviderID));
/*  82 */     if (this.dataProvider != null)
/*  83 */       applyDataProviderInfo();
/*  84 */     String contextPath = ((HttpServletRequest)this.pageContext.getRequest()).getContextPath();
/*  85 */     if (this.codebase == null)
/*  86 */       this.codebase = contextPath;
/*  87 */     String width = this.properties.getProperty("width");
/*  88 */     if ((width == null) && 
/*  89 */       (this.style != null))
/*  90 */       width = this.style.getProperty("width");
/*  91 */     if (width == null)
/*  92 */       width = "500";
/*  93 */     String height = this.properties.getProperty("height");
/*  94 */     if ((height == null) && 
/*  95 */       (this.style != null))
/*  96 */       height = this.style.getProperty("height");
/*  97 */     if (height == null) {
/*  98 */       height = "300";
/*     */     }
/* 100 */     this.bodyOut.print("<APPLET CODE=\"javachart.applet." + 
/* 101 */       this.chartType + "\" ARCHIVE=\"" + this.chartType + 
/* 102 */       ".jar\" CODEBASE=\"" + this.codebase + "\" WIDTH=" + width + 
/* 103 */       " HEIGHT=" + height + ">\n");
/* 104 */     populateParams();
/* 105 */     this.bodyOut.print("</APPLET>");
/*     */   }
/*     */ 
/*     */   protected void applyLocalizedResources() {
/* 109 */     applyInternalProperties();
/* 110 */     if (this.locale == null) {
/* 111 */       if (this.localeID != null) {
/* 112 */         this.locale = ((Locale)this.pageContext.getAttribute(this.localeID));
/* 113 */         if (this.locale == null)
/* 114 */           this.locale = ((Locale)this.pageContext.findAttribute(this.localeID));
/*     */       }
/* 116 */       if (this.locale == null)
/* 117 */         this.locale = ((HttpServletRequest)this.pageContext.getRequest()).getLocale();
/*     */     }
/* 119 */     if (this.locale != null) {
/* 120 */       this.properties.setProperty("locale", this.locale.toString());
/*     */     }
/* 122 */     ResourceBundle resourceBundle = getResourceBundleFromID();
/* 123 */     if (resourceBundle == null)
/* 124 */       resourceBundle = buildResourceBundle();
/* 125 */     if (resourceBundle == null)
/* 126 */       return;
/* 127 */     applyResourceBundleToApplet(resourceBundle);
/*     */   }
/*     */ 
/*     */   private ResourceBundle buildResourceBundle() {
/* 131 */     if (this.resourceBundleBaseName == null) {
/* 132 */       return null;
/*     */     }
/* 134 */     ServletContext sc = this.pageContext.getServletContext();
/*     */     try {
/* 136 */       rb = ResourceBundle.getBundle(this.resourceBundleBaseName, this.locale);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       ResourceBundle rb;
/* 138 */       this.pageContext.getServletContext().log("can't load localized chart resources: " + this.resourceBundleBaseName + ", locale " + this.locale);
/* 139 */       return null;
/*     */     }
/*     */     ResourceBundle rb;
/* 141 */     return rb;
/*     */   }
/*     */ 
/*     */   private void applyResourceBundleToApplet(ResourceBundle rb) {
/* 145 */     Enumeration en = rb.getKeys();
/* 146 */     while (en.hasMoreElements()) {
/* 147 */       String key = (String)en.nextElement();
/* 148 */       String value = rb.getString(key);
/* 149 */       this.properties.setProperty(key, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void applyInternalProperties()
/*     */   {
/* 155 */     if (this.internalPropertyNames == null)
/* 156 */       return;
/* 157 */     for (int i = 0; i < this.internalPropertyNames.size(); i++)
/* 158 */       this.properties.setProperty((String)this.internalPropertyNames.get(i), 
/* 159 */         (String)this.internalPropertyValues.get(i));
/*     */   }
/*     */ 
/*     */   protected void applyDataProviderInfo()
/*     */   {
/* 164 */     Iterator en = this.dataProvider.getDatasets();
/* 165 */     int count = 0;
/* 166 */     while (en.hasNext()) {
/* 167 */       Dataset d = (Dataset)en.next();
/* 168 */       this.properties.setProperty("dataset" + count + "Name", d.getName());
/*     */ 
/* 171 */       double[] yVals = d.getYValues();
/* 172 */       StringBuffer sb = new StringBuffer();
/* 173 */       for (int i = 0; i < yVals.length; i++) {
/* 174 */         sb.append(Double.toString(yVals[i]));
/* 175 */         if (i + 1 < yVals.length)
/* 176 */           sb.append(this.delimiter);
/*     */       }
/* 178 */       this.properties.setProperty("dataset" + count + "yValues", sb.toString());
/*     */ 
/* 181 */       double[] xVals = d.getXValues();
/* 182 */       sb = new StringBuffer();
/* 183 */       if (xVals[0] != (-1.0D / 0.0D)) {
/* 184 */         for (int i = 0; i < xVals.length; i++) {
/* 185 */           sb.append(Double.toString(xVals[i]));
/* 186 */           if (i + 1 < xVals.length)
/* 187 */             sb.append(this.delimiter);
/*     */         }
/* 189 */         this.properties.setProperty("dataset" + count + "xValues", sb.toString());
/*     */       }
/*     */ 
/* 193 */       double[] y2Vals = d.getY2Values();
/* 194 */       if (y2Vals[0] != (-1.0D / 0.0D)) {
/* 195 */         sb = new StringBuffer();
/* 196 */         for (int i = 0; i < y2Vals.length; i++) {
/* 197 */           sb.append(Double.toString(y2Vals[i]));
/* 198 */           if (i + 1 < y2Vals.length)
/* 199 */             sb.append(this.delimiter);
/*     */         }
/* 201 */         this.properties.setProperty("dataset" + count + "y2Values", sb.toString());
/*     */       }
/*     */ 
/* 205 */       double[] y3Vals = d.getY3Values();
/* 206 */       if (y3Vals[0] != (-1.0D / 0.0D)) {
/* 207 */         sb = new StringBuffer();
/* 208 */         for (int i = 0; i < y3Vals.length; i++) {
/* 209 */           sb.append(Double.toString(y3Vals[i]));
/* 210 */           if (i + 1 < y3Vals.length)
/* 211 */             sb.append(this.delimiter);
/*     */         }
/* 213 */         this.properties.setProperty("dataset" + count + "y3Values", sb.toString());
/*     */       }
/*     */ 
/* 217 */       int length = d.getData().size();
/* 218 */       if (d.getDataElementAt(0).getLabel() != null) {
/* 219 */         sb = new StringBuffer();
/* 220 */         for (int i = 0; i < length; i++) {
/* 221 */           sb.append(d.getDataElementAt(i).getLabel());
/* 222 */           if (i + 1 < length)
/* 223 */             sb.append(this.delimiter);
/*     */         }
/* 225 */         this.properties.setProperty("dataset" + count + "Labels", sb.toString());
/*     */       }
/*     */ 
/* 228 */       count++;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addParam(String name, String value) {
/*     */     try {
/* 234 */       if (name.equals("dwellXString"))
/* 235 */         value = changeDwellString(value);
/* 236 */       else if (name.equals("dwellYString"))
/* 237 */         value = changeDwellString(value);
/* 238 */       this.bodyOut.print("\t<PARAM NAME=\"" + name + "\" VALUE=\"" + value + "\">\n");
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/*     */   }
/*     */ 
/*     */   private void populateParams() {
/* 245 */     Enumeration en = this.properties.keys();
/* 246 */     while (en.hasMoreElements()) {
/* 247 */       String key = (String)en.nextElement();
/* 248 */       addParam(key, this.properties.getProperty(key));
/*     */     }
/* 250 */     if (!CacheManager.isDemoMode())
/* 251 */       addParam("CopyrightNotification", "chart is a copyrighted work, and subject to full legal protection");
/*     */   }
/*     */ 
/*     */   private String changeDwellString(String in)
/*     */   {
/* 256 */     if (in == null)
/* 257 */       return "val:#";
/* 258 */     int where = in.indexOf("XX");
/* 259 */     if (where == -1)
/* 260 */       return in;
/* 261 */     String s = in.substring(0, where) + "#" + in.substring(where + 2);
/* 262 */     return s;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.AppletChartTag
 * JD-Core Version:    0.6.2
 */