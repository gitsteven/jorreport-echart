/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import jatools.component.chart.applet.DataProvider;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspTagException;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import javax.servlet.jsp.tagext.BodyTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ 
/*     */ public class ChartTag
/*     */   implements BodyTag
/*     */ {
/*  33 */   protected String styleString = null;
/*  34 */   protected Properties style = null;
/*  35 */   protected String chartType = "barApp";
/*  36 */   protected boolean reloadStyle = false;
/*  37 */   protected String streamServletName = "/servlet/javachart.servlet.ChartStream";
/*  38 */   protected boolean useLinkMap = true;
/*     */   protected DataProvider dataProvider;
/*     */   protected String dataProviderID;
/*  45 */   protected String resourceBundleID = null;
/*  46 */   protected String resourceBundleBaseName = null;
/*     */ 
/*  49 */   protected String localeID = null;
/*  50 */   protected Locale locale = null;
/*     */   ArrayList internalPropertyNames;
/*     */   ArrayList internalPropertyValues;
/*     */   protected BodyContent bodyOut;
/*     */   protected PageContext pageContext;
/*     */   protected Tag parent;
/*     */ 
/*     */   public void setLocaleID(String s)
/*     */   {
/*  61 */     this.localeID = s;
/*     */   }
/*     */ 
/*     */   public void setLocale(Locale l)
/*     */   {
/*  69 */     this.locale = l;
/*     */   }
/*     */ 
/*     */   public void setResourceBundleID(String s)
/*     */   {
/*  77 */     this.resourceBundleID = s;
/*     */   }
/*     */ 
/*     */   public void setResourceBundleBaseName(String s)
/*     */   {
/*  86 */     this.resourceBundleBaseName = s;
/*     */   }
/*     */ 
/*     */   public void setStyle(String s)
/*     */   {
/* 101 */     this.styleString = s;
/*     */   }
/*     */ 
/*     */   public void setChartType(String s)
/*     */   {
/* 109 */     this.chartType = s;
/*     */   }
/*     */ 
/*     */   public void setReloadStyle(String s)
/*     */   {
/* 119 */     this.reloadStyle = Boolean.valueOf(s).booleanValue();
/*     */   }
/*     */ 
/*     */   public void setUseLinkMap(boolean use)
/*     */   {
/* 127 */     this.useLinkMap = use;
/*     */   }
/*     */ 
/*     */   public void setDataProvider(DataProvider dataProvider)
/*     */   {
/* 135 */     this.dataProvider = dataProvider;
/*     */   }
/*     */ 
/*     */   public void setDataProviderID(String dataProviderId)
/*     */   {
/* 143 */     this.dataProviderID = dataProviderId;
/*     */   }
/*     */ 
/*     */   public void setStreamServletName(String name)
/*     */   {
/* 151 */     this.streamServletName = name;
/*     */   }
/*     */ 
/*     */   public void setParent(Tag parent)
/*     */   {
/* 163 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   public void setBodyContent(BodyContent bodyOut) {
/* 167 */     this.bodyOut = bodyOut;
/*     */   }
/*     */ 
/*     */   public void setPageContext(PageContext pageContext)
/*     */   {
/* 175 */     this.pageContext = pageContext;
/*     */   }
/*     */ 
/*     */   public Tag getParent()
/*     */   {
/* 183 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public int doStartTag() throws JspException {
/* 187 */     return 2;
/*     */   }
/*     */ 
/*     */   public int doEndTag() throws JspException {
/* 191 */     return 6;
/*     */   }
/*     */ 
/*     */   public void release() {
/* 195 */     this.bodyOut = null;
/* 196 */     this.pageContext = null;
/* 197 */     this.parent = null;
/*     */   }
/*     */ 
/*     */   public void doInitBody()
/*     */     throws JspException
/*     */   {
/* 205 */     this.internalPropertyNames = null;
/* 206 */     this.internalPropertyValues = null;
/*     */   }
/*     */ 
/*     */   public int doAfterBody() throws JspException
/*     */   {
/*     */     try {
/* 212 */       if (this.bodyOut != null) {
/* 213 */         doChart();
/* 214 */         this.bodyOut.writeOut(this.bodyOut.getEnclosingWriter());
/*     */       }
/*     */ 
/* 217 */       this.internalPropertyNames = null;
/* 218 */       this.internalPropertyValues = null;
/* 219 */       this.dataProvider = null;
/* 220 */       this.styleString = null;
/* 221 */       this.style = null;
/* 222 */       this.chartType = "barApp";
/* 223 */       this.reloadStyle = false;
/* 224 */       this.streamServletName = "/servlet/javachart.servlet.ChartStream";
/* 225 */       this.useLinkMap = true;
/*     */ 
/* 227 */       this.dataProvider = null;
/* 228 */       this.dataProviderID = null;
/*     */ 
/* 230 */       this.resourceBundleID = null;
/* 231 */       this.resourceBundleBaseName = null;
/*     */ 
/* 233 */       this.localeID = null;
/* 234 */       this.locale = null;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 239 */       throw new JspTagException(e.toString());
/*     */     }
/* 241 */     return 0;
/*     */   }
/*     */ 
/*     */   protected void doChart()
/*     */     throws Exception
/*     */   {
/* 250 */     loadStyle();
/* 251 */     Bean chartBean = createChartBean();
/* 252 */     applyLocalizedResources(chartBean);
/* 253 */     chartBean.setProperty("useCache", "false");
/* 254 */     chartBean.setProperty("byteStream", "true");
/*     */ 
/* 256 */     if ((this.dataProvider == null) && (this.dataProviderID != null))
/* 257 */       this.dataProvider = ((DataProvider)this.pageContext.getAttribute(this.dataProviderID));
/* 258 */     if ((this.dataProvider == null) && (this.dataProviderID != null))
/* 259 */       this.dataProvider = ((DataProvider)this.pageContext.findAttribute(this.dataProviderID));
/* 260 */     if (this.dataProvider != null) {
/* 261 */       chartBean.setDataProvider(this.dataProvider);
/*     */     }
/* 263 */     String imageType = chartBean.getProperty("imageType");
/* 264 */     if ((imageType != null) && ((imageType.equalsIgnoreCase("flash")) || (imageType.equalsIgnoreCase("swf")))) {
/* 265 */       doFlashChart(chartBean);
/* 266 */       return;
/*     */     }
/* 268 */     if ((imageType != null) && (imageType.equalsIgnoreCase("svg"))) {
/* 269 */       doSVGChart(chartBean);
/* 270 */       return;
/*     */     }
/*     */ 
/* 273 */     String contextPath = ((HttpServletRequest)this.pageContext.getRequest()).getContextPath();
/* 274 */     String filename = "foo";
/*     */     try {
/* 276 */       filename = chartBean.getFileName();
/*     */     } catch (Exception sh) {
/* 278 */       this.pageContext.getServletContext().log("chart image generation problem:" + sh);
/*     */     }
/* 280 */     if (this.useLinkMap) {
/* 281 */       String mapName = chartBean.getProperty("mapName");
/* 282 */       if (mapName == null)
/* 283 */         chartBean.setProperty("mapName", filename);
/* 284 */       this.bodyOut.println(chartBean.getLinkMap());
/*     */     }
/* 286 */     String chartHeight = chartBean.getProperty("height");
/* 287 */     String chartWidth = chartBean.getProperty("width");
/* 288 */     if (chartWidth == null)
/* 289 */       chartWidth = "200";
/* 290 */     if (chartHeight == null) {
/* 291 */       chartHeight = "150";
/*     */     }
/* 293 */     this.pageContext.setAttribute(filename, chartBean, 4);
/* 294 */     this.bodyOut.print("<IMG SRC=\"" + contextPath + this.streamServletName + "?sn=" + filename + "\" ");
/* 295 */     this.bodyOut.print("width=\"" + chartWidth + "\" height=\"" + chartHeight + "\" ");
/* 296 */     if (this.useLinkMap) {
/* 297 */       String mapName = chartBean.getProperty("mapName");
/* 298 */       this.bodyOut.print(" BORDER=0 ISMAP USEMAP=#" + mapName);
/*     */     }
/* 300 */     this.bodyOut.print(">");
/*     */   }
/*     */ 
/*     */   protected Bean createChartBean() {
/*     */     Bean chartBean;
/*     */     try {
/* 306 */       Class beanClass = Class.forName("javachart.servlet." + this.chartType);
/* 307 */       Class[] args = new Class[1];
/* 308 */       args[0] = Class.forName("java.util.Properties");
/* 309 */       Constructor constructor = beanClass.getConstructor(args);
/* 310 */       Object[] constructArgs = new Object[1];
/* 311 */       constructArgs[0] = this.style;
/* 312 */       chartBean = (Bean)constructor.newInstance(constructArgs);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       try
/*     */       {
/*     */         Bean chartBean;
/* 315 */         Class beanClass = Class.forName(this.chartType);
/* 316 */         Class[] args = new Class[1];
/* 317 */         args[0] = Class.forName("java.util.Properties");
/* 318 */         Constructor constructor = beanClass.getConstructor(args);
/* 319 */         Object[] constructArgs = new Object[1];
/* 320 */         constructArgs[0] = this.style;
/* 321 */         chartBean = (Bean)constructor.newInstance(constructArgs);
/*     */       }
/*     */       catch (Exception e2)
/*     */       {
/*     */         try
/*     */         {
/*     */           Bean chartBean;
/* 325 */           Class beanClass = Class.forName(this.chartType);
/* 326 */           chartBean = (Bean)beanClass.newInstance();
/*     */         }
/*     */         catch (Exception e3)
/*     */         {
/*     */           Bean chartBean;
/* 328 */           this.pageContext.getServletContext().log("chart servlet can't load requested chart class.  Using BarChart instead");
/* 329 */           chartBean = new barApp(this.style);
/*     */         }
/*     */       }
/*     */     }
/* 333 */     return chartBean;
/*     */   }
/*     */ 
/*     */   protected void loadStyle()
/*     */   {
/* 339 */     if (this.styleString == null) {
/* 340 */       this.style = new Properties();
/* 341 */       return;
/*     */     }
/* 343 */     this.style = ((Properties)this.pageContext.getAttribute(this.styleString, 4));
/* 344 */     if ((this.reloadStyle) || (this.style == null)) {
/* 345 */       this.style = new Properties();
/*     */       try {
/* 347 */         InputStream in = 
/* 348 */           this.pageContext.getServletContext().getResourceAsStream(this.styleString);
/* 349 */         this.style.load(in);
/* 350 */         in.close();
/* 351 */         if (!this.reloadStyle)
/* 352 */           this.pageContext.setAttribute(this.styleString, this.style, 4);
/*     */       } catch (Exception e) {
/* 354 */         this.pageContext.getServletContext().log("no such properties file: " + this.styleString);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void applyLocalizedResources(Bean chartBean)
/*     */   {
/* 367 */     applyInternalProperties(chartBean);
/* 368 */     if (this.locale == null) {
/* 369 */       if (this.localeID != null) {
/* 370 */         this.locale = ((Locale)this.pageContext.getAttribute(this.localeID));
/* 371 */         if (this.locale == null)
/* 372 */           this.locale = ((Locale)this.pageContext.findAttribute(this.localeID));
/*     */       }
/* 374 */       if (this.locale == null)
/* 375 */         this.locale = ((HttpServletRequest)this.pageContext.getRequest()).getLocale();
/*     */     }
/* 377 */     chartBean.userLocale = this.locale;
/* 378 */     chartBean.setProperty("tagLocaleInfo", this.locale.toString());
/*     */ 
/* 380 */     ResourceBundle resourceBundle = getResourceBundleFromID();
/* 381 */     if (resourceBundle == null)
/* 382 */       resourceBundle = buildResourceBundle(chartBean);
/* 383 */     if (resourceBundle == null)
/* 384 */       return;
/* 385 */     applyResourceBundleToChartBean(resourceBundle, chartBean);
/*     */   }
/*     */ 
/*     */   protected ResourceBundle getResourceBundleFromID()
/*     */   {
/* 393 */     if (this.resourceBundleID == null)
/* 394 */       return null;
/* 395 */     ResourceBundle rb = (ResourceBundle)this.pageContext.getAttribute(this.resourceBundleID);
/* 396 */     if (rb == null)
/* 397 */       rb = (ResourceBundle)this.pageContext.findAttribute(this.resourceBundleID);
/* 398 */     if (rb == null) {
/* 399 */       this.pageContext.getServletContext().log("couldn't find resource bundle at id " + this.resourceBundleID);
/* 400 */       return null;
/*     */     }
/* 402 */     return rb;
/*     */   }
/*     */ 
/*     */   protected ResourceBundle buildResourceBundle(Bean chartBean)
/*     */   {
/* 410 */     if (this.resourceBundleBaseName == null) {
/* 411 */       return null;
/*     */     }
/* 413 */     ServletContext sc = this.pageContext.getServletContext();
/*     */     try {
/* 415 */       rb = ResourceBundle.getBundle(this.resourceBundleBaseName, chartBean.userLocale);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       ResourceBundle rb;
/* 417 */       this.pageContext.getServletContext().log("can't load localized chart resources: " + this.resourceBundleBaseName + ", locale " + chartBean.userLocale);
/* 418 */       return null;
/*     */     }
/*     */     ResourceBundle rb;
/* 420 */     return rb;
/*     */   }
/*     */ 
/*     */   protected void applyResourceBundleToChartBean(ResourceBundle rb, Bean chartBean)
/*     */   {
/* 430 */     Enumeration en = rb.getKeys();
/* 431 */     while (en.hasMoreElements()) {
/* 432 */       String key = (String)en.nextElement();
/* 433 */       String value = rb.getString(key);
/* 434 */       chartBean.setProperty(key, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addInternalProperty(String name, String value)
/*     */   {
/* 444 */     if (this.internalPropertyNames == null) {
/* 445 */       this.internalPropertyNames = new ArrayList();
/* 446 */       this.internalPropertyValues = new ArrayList();
/*     */     }
/* 448 */     this.internalPropertyNames.add(name);
/* 449 */     this.internalPropertyValues.add(value);
/*     */   }
/*     */ 
/*     */   protected void applyInternalProperties(Bean chartBean) {
/* 453 */     if (this.internalPropertyNames == null)
/* 454 */       return;
/* 455 */     for (int i = 0; i < this.internalPropertyNames.size(); i++)
/* 456 */       chartBean.setProperty((String)this.internalPropertyNames.get(i), 
/* 457 */         (String)this.internalPropertyValues.get(i));
/*     */   }
/*     */ 
/*     */   public static String getFlashString(String width, String height, String location)
/*     */   {
/* 463 */     String s = 
/* 464 */       "<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" \n\t\tcodebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0\" width=\"" + 
/* 465 */       width + "\" height=\"" + height + "\" id=\"movie\">\n" + 
/* 466 */       "\t<param name=\"movie\" value=\"" + location + "\">\n" + 
/* 467 */       "\t<embed src=\"" + location + "\" quality=\"high\" width=\"" + width + "\" height=\"" + height + "\" name=\"movie\" \n" + 
/* 468 */       "\t\talign=\"\" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\"> \n" + 
/* 469 */       "</object>\n\n";
/* 470 */     return s;
/*     */   }
/*     */ 
/*     */   protected void doFlashChart(Bean chartBean)
/*     */     throws IOException
/*     */   {
/* 477 */     String contextPath = ((HttpServletRequest)this.pageContext.getRequest()).getContextPath();
/* 478 */     String filename = "foo";
/*     */     try {
/* 480 */       filename = chartBean.getFileName();
/*     */     } catch (Exception sh) {
/* 482 */       this.pageContext.getServletContext().log("chart flash output generation problem:" + sh);
/*     */     }
/* 484 */     this.pageContext.setAttribute(filename, chartBean, 4);
/*     */ 
/* 486 */     String movieSource = contextPath + this.streamServletName + "?sn=" + filename;
/*     */ 
/* 488 */     String chartHeight = chartBean.getProperty("height");
/* 489 */     if (chartHeight == null)
/* 490 */       chartHeight = Integer.toString(150);
/* 491 */     String chartWidth = chartBean.getProperty("width");
/* 492 */     if (chartWidth == null)
/* 493 */       chartWidth = Integer.toString(200);
/* 494 */     this.bodyOut.println(getFlashString(chartWidth, chartHeight, movieSource));
/*     */   }
/*     */ 
/*     */   protected void doSVGChart(Bean chartBean)
/*     */     throws IOException
/*     */   {
/* 500 */     String contextPath = ((HttpServletRequest)this.pageContext.getRequest()).getContextPath();
/* 501 */     String filename = "foo";
/*     */     try {
/* 503 */       filename = chartBean.getFileName();
/*     */     } catch (Exception sh) {
/* 505 */       this.pageContext.getServletContext().log("chart SVG output generation problem:" + sh);
/*     */     }
/* 507 */     this.pageContext.setAttribute(filename, chartBean, 4);
/*     */ 
/* 509 */     String svgSource = contextPath + this.streamServletName + "?sn=" + filename;
/*     */ 
/* 511 */     String chartHeight = chartBean.getProperty("height");
/* 512 */     if (chartHeight == null)
/* 513 */       chartHeight = Integer.toString(150);
/* 514 */     String chartWidth = chartBean.getProperty("width");
/* 515 */     if (chartWidth == null)
/* 516 */       chartWidth = Integer.toString(200);
/* 517 */     int i = 0;
/*     */ 
/* 519 */     this.bodyOut.println("\t<embed width=\"" + chartWidth + "\" height=\"" + chartHeight + "\"");
/* 520 */     this.bodyOut.println("\t\ttype=\"image/svg-xml\"");
/* 521 */     this.bodyOut.println("\t\tsrc=\"" + svgSource + "\"");
/* 522 */     this.bodyOut.println("\t</embed>");
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.ChartTag
 * JD-Core Version:    0.6.2
 */