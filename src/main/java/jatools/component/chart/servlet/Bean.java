/*      */ package jatools.component.chart.servlet;
/*      */ 
/*      */ import jatools.component.chart.Tip;
/*      */ import jatools.component.chart.applet.ChartUtil;
/*      */ import jatools.component.chart.applet.DataProvider;
/*      */ import jatools.component.chart.applet.GetParam;
/*      */ import jatools.component.chart.applet.ParameterParser;
/*      */ import jatools.component.chart.chart.AxisInterface;
/*      */ import jatools.component.chart.chart.Background;
/*      */ import jatools.component.chart.chart.ChartInterface;
/*      */ import jatools.component.chart.chart.Datum;
/*      */ import jatools.component.chart.chart.Gc;
/*      */ import jatools.component.chart.chart.Globals;
/*      */ import jatools.component.chart.chart.Highlighter;
/*      */ import jatools.component.chart.chart.Plotarea;
/*      */ import jatools.component.chart.chart._Chart;
/*      */ import jatools.core.view._Tip;
/*      */ import jatools.data.reader.DatasetReader;
/*      */ import jatools.engine.ProtectClass;
/*      */ import jatools.engine.script.Script;
/*      */ import java.awt.Font;
/*      */ import java.awt.Frame;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Image;
/*      */ import java.awt.MediaTracker;
/*      */ import java.awt.Panel;
/*      */ import java.awt.Point;
/*      */ import java.awt.Polygon;
/*      */ import java.awt.Toolkit;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.text.Format;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Locale;
/*      */ import java.util.Properties;
/*      */ 
/*      */ public abstract class Bean
/*      */   implements ProtectClass, GetParam, Serializable
/*      */ {
/*   80 */   private static Frame frame = null;
/*      */   static final int DEFAULT_WIDTH = 200;
/*      */   static final int DEFAULT_HEIGHT = 150;
/*  100 */   public ParameterParser parser = null;
/*      */   public transient ChartInterface chart;
/*  106 */   MediaTracker imageTracker = null;
/*  107 */   private boolean noisy = false;
/*  108 */   protected Properties properties = new Properties();
/*      */   String fileName;
/*  110 */   byte[] bytes = null;
/*      */ 
/*  115 */   public boolean useToolTips = true;
/*  116 */   protected OutputStream logStream = null;
/*  117 */   protected boolean useDwellLabel = true;
/*      */ 
/*  122 */   public NumberFormat dwellXLabelFormat = null;
/*      */ 
/*  126 */   protected NumberFormat dwellYLabelFormat = null;
/*      */ 
/*  131 */   public boolean dwellUseXValue = true;
/*      */ 
/*  136 */   public boolean dwellUseYValue = true;
/*      */ 
/*  141 */   public boolean dwellUseY2Value = false;
/*      */ 
/*  146 */   public boolean dwellUseString = false;
/*      */ 
/*  151 */   public boolean dwellUseDatasetName = false;
/*      */   protected String dwellXString;
/*      */   protected String dwellYString;
/*      */   protected String dwellY2String;
/*  155 */   protected UserImagingCodec userImagingCodec = null;
/*  156 */   protected boolean antialiasOn = false;
/*      */   protected DataProvider dataProvider;
/*      */   public String delimiter;
/*  168 */   public boolean isChartServlet = false;
/*      */   Locale userLocale;
/*      */   protected DatasetReader graphReader;
/*      */   protected Script jatoolsDataProvider;
/*      */   protected String graphLabelField;
/*      */   protected ArrayList graphShowData;
/*      */   private Image image;
/*      */   private int plotFrom;
/*      */   private jatools.dataset.Dataset ds;
/*      */   private String idField;
/*      */ 
/*      */   public void setProperties(Properties props)
/*      */   {
/*  186 */     Enumeration en = props.keys();
/*      */ 
/*  188 */     while (en.hasMoreElements()) {
/*  189 */       String key = (String)en.nextElement();
/*  190 */       this.properties.setProperty(key, props.getProperty(key));
/*      */     }
/*      */   }
/*      */ 
/*      */   public void accumulateProperty(String name, Object value)
/*      */   {
/*  206 */     String currentVal = getParameter(name);
/*      */ 
/*  208 */     if (currentVal == null) {
/*  209 */       setProperty(name, value);
/*      */ 
/*  211 */       return;
/*      */     }
/*      */ 
/*  214 */     if (this.delimiter == null) {
/*  215 */       setDelimiter();
/*      */     }
/*      */ 
/*  219 */     setProperty(name, currentVal + this.delimiter + value);
/*      */   }
/*      */ 
/*      */   private void addToolTipText(StringBuffer sb, jatools.component.chart.chart.Dataset set, Datum d)
/*      */   {
/*  233 */     if (this.dwellXLabelFormat == null) {
/*  234 */       initializeToolTipVars();
/*      */     }
/*      */ 
/*  237 */     sb.append("\" alt=\"");
/*      */ 
/*  239 */     if (this.dwellUseDatasetName) {
/*  240 */       sb.append(set.getName());
/*  241 */       sb.append("\n");
/*      */     }
/*      */ 
/*  244 */     if (this.dwellUseString) {
/*  245 */       sb.append(getDwellLabelLabelString(d));
/*  246 */       sb.append("\n");
/*      */     }
/*      */ 
/*  249 */     if (this.dwellUseXValue) {
/*  250 */       sb.append(getDwellLabelXString(d));
/*  251 */       sb.append("\n");
/*      */     }
/*      */ 
/*  254 */     if (this.dwellUseYValue) {
/*  255 */       sb.append(getDwellLabelYString(d));
/*      */     }
/*      */ 
/*  258 */     if (this.dwellUseY2Value) {
/*  259 */       sb.append(getDwellLabelY2String(d));
/*      */     }
/*      */ 
/*  263 */     sb.append("\" title=\"");
/*      */ 
/*  265 */     if (this.dwellUseDatasetName) {
/*  266 */       sb.append(set.getName());
/*  267 */       sb.append("\n");
/*      */     }
/*      */ 
/*  270 */     if (this.dwellUseString) {
/*  271 */       sb.append(getDwellLabelLabelString(d));
/*  272 */       sb.append("\n");
/*      */     }
/*      */ 
/*  275 */     if (this.dwellUseXValue) {
/*  276 */       sb.append(getDwellLabelXString(d));
/*  277 */       sb.append("\n");
/*      */     }
/*      */ 
/*  280 */     if (this.dwellUseYValue) {
/*  281 */       sb.append(getDwellLabelYString(d));
/*      */     }
/*      */ 
/*  284 */     if (this.dwellUseY2Value)
/*  285 */       sb.append(getDwellLabelY2String(d));
/*      */   }
/*      */ 
/*      */   private String getToolTipText(jatools.component.chart.chart.Dataset set, Datum d)
/*      */   {
/*  290 */     if (this.dwellXLabelFormat == null) {
/*  291 */       initializeToolTipVars();
/*      */     }
/*      */ 
/*  294 */     StringBuffer sb = new StringBuffer();
/*      */ 
/*  296 */     if (this.dwellUseDatasetName) {
/*  297 */       sb.append(set.getName());
/*  298 */       sb.append("\n");
/*      */     }
/*      */ 
/*  301 */     if (this.dwellUseString) {
/*  302 */       sb.append(getDwellLabelLabelString(d));
/*  303 */       sb.append("\n");
/*      */     }
/*      */ 
/*  306 */     if (this.dwellUseXValue) {
/*  307 */       sb.append(getDwellLabelXString(d));
/*  308 */       sb.append("\n");
/*      */     }
/*      */ 
/*  311 */     if (this.dwellUseYValue) {
/*  312 */       sb.append(getDwellLabelYString(d));
/*      */     }
/*      */ 
/*  315 */     if (this.dwellUseY2Value) {
/*  316 */       sb.append(getDwellLabelY2String(d));
/*      */     }
/*      */ 
/*  319 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public void buildChart()
/*      */   {
/*  326 */     String s = getParameter("debug");
/*      */ 
/*  328 */     if (s != null) {
/*  329 */       if (s.equalsIgnoreCase("true")) {
/*  330 */         this.noisy = true;
/*  331 */         log("isn't cached, building chart");
/*      */       } else {
/*  333 */         this.noisy = false;
/*      */       }
/*      */     }
/*  336 */     else this.noisy = false;
/*      */ 
/*  339 */     init();
/*  340 */     s = getParameter("hasLinkMap");
/*      */ 
/*  342 */     if ((s == null) || (s.equalsIgnoreCase("true"))) {
/*  343 */       this.chart.setUseDisplayList(true);
/*      */       try
/*      */       {
/*  347 */         this.chart.getXAxis().setUseDisplayList(false);
/*  348 */         this.chart.getYAxis().setUseDisplayList(false);
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*      */       }
/*      */     }
/*  354 */     if (this.noisy) {
/*  355 */       log("chart created");
/*  356 */       log("displayList is " + this.chart.getGlobals().getDisplayList());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void drawMyStuff(Graphics g)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void generate()
/*      */     throws Exception
/*      */   {
/*  374 */     generate(false);
/*      */   }
/*      */ 
/*      */   public void generate(boolean geometriesOnly)
/*      */     throws Exception
/*      */   {
/*  384 */     CacheManager manager = null;
/*      */ 
/*  387 */     if (this.dataProvider != null) {
/*  388 */       setProperty("dataProviderID", this.dataProvider.getUniqueIdentifier());
/*      */     }
/*      */ 
/*  391 */     manager = new CacheManager(this);
/*      */ 
/*  394 */     if (this.isChartServlet) {
/*  395 */       manager.useCache = false;
/*  396 */       manager.byteStream = true;
/*      */     }
/*      */ 
/*  399 */     String s = getProperty("imageType");
/*      */ 
/*  401 */     if (s != null) {
/*  402 */       manager.imageType = s;
/*      */     }
/*      */ 
/*  405 */     if ((manager.isCached()) || (geometriesOnly)) {
/*  406 */       boolean redraw = true;
/*  407 */       s = getProperty("toolTips");
/*      */ 
/*  409 */       if ((s != null) && (s.equalsIgnoreCase("false"))) {
/*  410 */         redraw = false;
/*  411 */         this.useToolTips = false;
/*      */       }
/*      */ 
/*  414 */       s = getProperty("hasLinkMap");
/*      */ 
/*  416 */       if ((s == null) || (s.equalsIgnoreCase("true"))) {
/*  417 */         redraw = true;
/*      */       }
/*      */ 
/*  420 */       if (redraw)
/*      */       {
/*  423 */         if (this.chart == null) {
/*  424 */           buildChart();
/*      */         }
/*      */ 
/*  427 */         if (this.imageTracker != null) {
/*      */           try {
/*  429 */             this.imageTracker.waitForID(0);
/*      */           } catch (Exception ex) {
/*  431 */             log("interrupted when trying to get image from param");
/*      */           }
/*      */         }
/*      */ 
/*  435 */         manager.chart = this.chart;
/*  436 */         manager.setupAndDrawChart();
/*      */       }
/*      */     } else {
/*      */       try {
/*  440 */         if (this.chart == null)
/*  441 */           buildChart();
/*      */       }
/*      */       catch (Exception ex) {
/*  444 */         ex.printStackTrace();
/*      */       }
/*      */ 
/*  447 */       if (this.imageTracker != null) {
/*      */         try {
/*  449 */           this.imageTracker.waitForID(0);
/*      */         } catch (Exception ex) {
/*  451 */           log("interrupted when trying to get image from param");
/*      */         }
/*      */       }
/*      */ 
/*  455 */       manager.chart = this.chart;
/*      */ 
/*  457 */       manager.generate();
/*      */     }
/*      */ 
/*  460 */     if (!geometriesOnly)
/*      */     {
/*  463 */       this.bytes = manager.getImageBytes();
/*      */     }
/*      */ 
/*  466 */     this.fileName = manager.getImageName();
/*  467 */     this.image = manager.image;
/*      */   }
/*      */ 
/*      */   public ChartInterface getChart()
/*      */   {
/*  476 */     if (this.chart == null) {
/*  477 */       buildChart();
/*      */     }
/*      */ 
/*  480 */     return this.chart;
/*      */   }
/*      */ 
/*      */   public Image getImage()
/*      */   {
/*  489 */     return this.image;
/*      */   }
/*      */ 
/*      */   public jatools.component.chart.chart.Dataset getDataset(ChartInterface chart, int which)
/*      */   {
/*  504 */     return chart.getDatasets()[which];
/*      */   }
/*      */ 
/*      */   public String getDwellLabelLabelString(Datum d)
/*      */   {
/*  511 */     String s = d.getLabel();
/*      */ 
/*  513 */     return s;
/*      */   }
/*      */ 
/*      */   public String getDwellLabelXString(Datum d)
/*      */   {
/*      */     String s;
/*  522 */     if ((this.dwellXLabelFormat instanceof NumberFormat))
/*  523 */       s = this.dwellXLabelFormat.format(d.getX());
/*      */     else
/*  525 */       return this.dwellXLabelFormat.format(new Double(d.getX()));
/*      */     String s;
/*  528 */     int where = this.dwellXString.indexOf("XX");
/*      */ 
/*  530 */     return this.dwellXString.substring(0, where) + s + this.dwellXString.substring(where + 2);
/*      */   }
/*      */ 
/*      */   public String getDwellLabelYString(Datum d)
/*      */   {
/*      */     String s;
/*  539 */     if ((this.dwellYLabelFormat instanceof NumberFormat))
/*  540 */       s = this.dwellYLabelFormat.format(d.getY());
/*      */     else
/*  542 */       return this.dwellYLabelFormat.format(new Double(d.getY()));
/*      */     String s;
/*  545 */     int where = this.dwellYString.indexOf("XX");
/*      */ 
/*  547 */     return this.dwellYString.substring(0, where) + s + this.dwellYString.substring(where + 2);
/*      */   }
/*      */ 
/*      */   public String getDwellLabelY2String(Datum d)
/*      */   {
/*      */     String s;
/*  561 */     if ((this.dwellYLabelFormat instanceof NumberFormat))
/*  562 */       s = this.dwellYLabelFormat.format(d.getY2());
/*      */     else
/*  564 */       return this.dwellYLabelFormat.format(new Double(d.getY2()));
/*      */     String s;
/*  567 */     int where = this.dwellY2String.indexOf("XX");
/*      */ 
/*  569 */     return this.dwellY2String.substring(0, where) + s + this.dwellY2String.substring(where + 2);
/*      */   }
/*      */ 
/*      */   public String getFileName()
/*      */     throws Exception
/*      */   {
/*  580 */     return getFileName(false);
/*      */   }
/*      */ 
/*      */   protected String getFileName(boolean geometriesOnly)
/*      */     throws Exception
/*      */   {
/*  596 */     if (this.fileName == null) {
/*      */       try {
/*  598 */         generate(geometriesOnly);
/*      */       } catch (Exception e) {
/*  600 */         log("failed to generate while retrieving filename");
/*      */ 
/*  611 */         throw e;
/*      */       }
/*      */     }
/*      */ 
/*  615 */     return this.fileName;
/*      */   }
/*      */ 
/*      */   public byte[] getImageBytes()
/*      */     throws Exception
/*      */   {
/*  624 */     if (this.bytes == null) {
/*      */       try {
/*  626 */         generate();
/*      */       } catch (Exception e) {
/*  628 */         if (this.noisy) {
/*  629 */           log("unable to generate bytes in servlets.Bean:getImageBytes()");
/*      */         }
/*      */ 
/*  632 */         throw e;
/*      */       }
/*      */     }
/*      */ 
/*  636 */     return this.bytes;
/*      */   }
/*      */ 
/*      */   public String getLinkMap()
/*      */     throws Exception
/*      */   {
/*  644 */     return getLinkMap(false);
/*      */   }
/*      */ 
/*      */   protected String getLinkMap(boolean geometriesOnly)
/*      */     throws Exception
/*      */   {
/*  660 */     StringBuffer sb = new StringBuffer();
/*      */ 
/*  662 */     if (this.chart == null) {
/*      */       try {
/*  664 */         generate(geometriesOnly);
/*      */       } catch (Exception e) {
/*  666 */         if (this.noisy) {
/*  667 */           log("failed to generate chart");
/*      */         }
/*      */ 
/*  672 */         throw e;
/*      */       }
/*      */     }
/*      */ 
/*  676 */     Highlighter linker = new Highlighter(this.chart.getGlobals());
/*  677 */     int h = this.chart.getGlobals().getMaxY();
/*      */ 
/*  686 */     int i = 0;
/*  687 */     jatools.component.chart.chart.Dataset[] datasets = this.chart.getDatasets();
/*  688 */     String[] links = (String[])null;
/*  689 */     String[] targets = (String[])null;
/*      */ 
/*  691 */     while (datasets[i] != null) {
/*  692 */       String linkList = getProperty("dataset" + i + "Links");
/*      */ 
/*  694 */       if (linkList != null) {
/*  695 */         links = this.parser.getLabels(linkList);
/*      */       }
/*      */ 
/*  698 */       String targetList = getProperty("dataset" + i + "Targets");
/*      */ 
/*  700 */       if (targetList != null) {
/*  701 */         targets = this.parser.getLabels(targetList);
/*      */       }
/*      */ 
/*  704 */       jatools.component.chart.chart.Dataset d = datasets[i];
/*      */ 
/*  706 */       for (int j = 0; j < d.getData().size(); j++) {
/*      */         try {
/*  708 */           Point[][] coords = linker.getHighlightPointSet(d.getData().get(j));
/*      */ 
/*  710 */           if (coords != null)
/*  711 */             for (int l = 0; l < coords.length; l++) {
/*  712 */               sb.append("<area shape=\"poly\" coords=\"");
/*      */ 
/*  714 */               for (int k = 0; k < coords[l].length; k++) {
/*  715 */                 if (k != 0) {
/*  716 */                   sb.append(", ");
/*      */                 }
/*      */ 
/*  719 */                 sb.append(coords[l][k].x + ", " + (h - coords[l][k].y));
/*      */               }
/*      */ 
/*  722 */               if (this.useToolTips) {
/*  723 */                 addToolTipText(sb, d, d.getDataElementAt(j));
/*      */               }
/*      */ 
/*  726 */               if (links != null) {
/*  727 */                 sb.append("\" href=\"" + links[j]);
/*      */               }
/*      */ 
/*  730 */               if (targets != null) {
/*  731 */                 sb.append("\" target=\"" + targets[j]);
/*      */               }
/*      */ 
/*  734 */               sb.append("\">\n");
/*      */             }
/*      */           else
/*  737 */             log("coords is null!");
/*      */         }
/*      */         catch (Exception ignored) {
/*  740 */           log("problem with link map");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  751 */       i++;
/*  752 */       links = (String[])null;
/*  753 */       targets = (String[])null;
/*      */     }
/*      */ 
/*  757 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public ArrayList getToolTips1()
/*      */     throws Exception
/*      */   {
/*  768 */     StringBuffer sb = new StringBuffer();
/*      */ 
/*  770 */     if (this.chart == null) {
/*      */       try {
/*  772 */         generate(false);
/*      */       } catch (Exception e) {
/*  774 */         if (this.noisy) {
/*  775 */           log("failed to generate chart");
/*      */         }
/*      */ 
/*  780 */         throw e;
/*      */       }
/*      */     }
/*      */ 
/*  784 */     Highlighter linker = new Highlighter(this.chart.getGlobals());
/*  785 */     int h = this.chart.getGlobals().getMaxY();
/*      */ 
/*  794 */     int i = 0;
/*  795 */     jatools.component.chart.chart.Dataset[] datasets = this.chart.getDatasets();
/*  796 */     String[] links = (String[])null;
/*  797 */     String[] targets = (String[])null;
/*  798 */     ArrayList v = new ArrayList();
/*      */ 
/*  800 */     while (datasets[i] != null) {
/*  801 */       String linkList = getProperty("dataset" + i + "Links");
/*      */ 
/*  803 */       if (linkList != null) {
/*  804 */         links = this.parser.getLabels(linkList);
/*      */       }
/*      */ 
/*  807 */       String targetList = getProperty("dataset" + i + "Targets");
/*      */ 
/*  809 */       if (targetList != null) {
/*  810 */         targets = this.parser.getLabels(targetList);
/*      */       }
/*      */ 
/*  813 */       jatools.component.chart.chart.Dataset d = datasets[i];
/*      */ 
/*  815 */       for (int j = 0; j < d.getData().size(); j++) {
/*      */         try {
/*  817 */           Point[][] coords = linker.getHighlightPointSet(d.getData().get(j));
/*      */ 
/*  819 */           if (coords != null)
/*  820 */             for (int l = 0; l < coords.length; l++) {
/*  821 */               _Tip tip = new _Tip();
/*      */ 
/*  823 */               sb.append("<area shape=\"poly\" coords=\"");
/*      */ 
/*  825 */               int[] xs = new int[coords[l].length];
/*  826 */               int[] ys = new int[xs.length];
/*      */ 
/*  828 */               for (int k = 0; k < coords[l].length; k++) {
/*  829 */                 xs[k] = coords[l][k].x;
/*  830 */                 ys[k] = (h - coords[l][k].y);
/*      */               }
/*      */ 
/*  833 */               tip.shape = new Polygon(xs, ys, xs.length);
/*      */ 
/*  835 */               if (this.useToolTips) {
/*  836 */                 tip.alt = getToolTipText(d, d.getDataElementAt(j));
/*      */               }
/*      */ 
/*  839 */               if (links != null) {
/*  840 */                 tip.url = links[j];
/*      */               }
/*      */ 
/*  843 */               if (targets != null) {
/*  844 */                 tip.target = targets[j];
/*      */               }
/*      */ 
/*  847 */               v.add(tip);
/*      */             }
/*      */           else
/*  850 */             log("coords is null!");
/*      */         }
/*      */         catch (Exception ignored) {
/*  853 */           log("problem with link map");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  864 */       i++;
/*  865 */       links = (String[])null;
/*  866 */       targets = (String[])null;
/*      */     }
/*      */ 
/*  870 */     return v;
/*      */   }
/*      */ 
/*      */   public ArrayList getToolTips()
/*      */     throws Exception
/*      */   {
/*  882 */     if (this.chart == null) {
/*      */       try {
/*  884 */         generate(false);
/*      */       } catch (Exception e) {
/*  886 */         if (this.noisy) {
/*  887 */           log("failed to generate chart");
/*      */ 
/*  889 */           e.printStackTrace();
/*      */         }
/*      */ 
/*  892 */         throw e;
/*      */       }
/*      */     }
/*      */ 
/*  896 */     Highlighter linker = new Highlighter(this.chart.getGlobals());
/*  897 */     int h = this.chart.getGlobals().getMaxY();
/*      */ 
/*  906 */     int i = 0;
/*  907 */     jatools.component.chart.chart.Dataset[] datasets = this.chart.getDatasets();
/*      */ 
/*  909 */     ArrayList v = new ArrayList();
/*      */ 
/*  911 */     while (datasets[i] != null) {
/*  912 */       if (datasets[i].hasTip()) {
/*  913 */         jatools.component.chart.chart.Dataset d = datasets[i];
/*  914 */         Tip[] tips = d.getTips();
/*      */ 
/*  916 */         for (int j = 0; j < d.getData().size(); j++) {
/*      */           try {
/*  918 */             Point[][] coords = linker.getHighlightPointSet(d.getData().get(j));
/*      */ 
/*  920 */             if (coords != null)
/*  921 */               for (int l = 0; l < coords.length; l++) {
/*  922 */                 _Tip tip = new _Tip();
/*      */ 
/*  925 */                 int[] xs = new int[coords[l].length];
/*  926 */                 int[] ys = new int[xs.length];
/*      */ 
/*  928 */                 for (int k = 0; k < coords[l].length; k++) {
/*  929 */                   xs[k] = coords[l][k].x;
/*  930 */                   ys[k] = (h - coords[l][k].y);
/*      */                 }
/*      */ 
/*  933 */                 tip.shape = new Polygon(xs, ys, xs.length);
/*      */ 
/*  935 */                 if (tips[j].tip != null) {
/*  936 */                   tip.alt = tips[j].tip;
/*      */                 }
/*      */ 
/*  939 */                 if (tips[j].url != null) {
/*  940 */                   tip.url = tips[j].url;
/*      */                 }
/*      */ 
/*  943 */                 if (tips[j].target != null) {
/*  944 */                   tip.target = tips[j].target;
/*      */                 }
/*      */ 
/*  947 */                 v.add(tip);
/*      */               }
/*      */             else
/*  950 */               log("coords is null!");
/*      */           }
/*      */           catch (Exception ignored) {
/*  953 */             log("problem with link map");
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  965 */       i++;
/*      */     }
/*      */ 
/*  969 */     return v;
/*      */   }
/*      */ 
/*      */   public OutputStream getLogStream()
/*      */   {
/*  979 */     return this.logStream;
/*      */   }
/*      */ 
/*      */   public void getMyDatasets(String s)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void getMyOptions()
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void getOptions()
/*      */   {
/* 1006 */     if (this.parser == null)
/*      */     {
/* 1008 */       this.parser = new ParameterParser(this.chart, this);
/*      */     }
/*      */ 
/* 1011 */     String s = getParameter("antialiasOn");
/*      */ 
/* 1013 */     if ((s != null) && 
/* 1014 */       (s.equalsIgnoreCase("true"))) {
/* 1015 */       this.antialiasOn = true;
/*      */     }
/*      */ 
/* 1019 */     s = getParameter("userImagingCodec");
/*      */ 
/* 1021 */     if (s != null) {
/*      */       try {
/* 1023 */         Class codec = Class.forName(s);
/* 1024 */         this.userImagingCodec = ((UserImagingCodec)codec.newInstance());
/*      */       } catch (Exception ee) {
/* 1026 */         log("userImagingCodec: " + s + "  is not valid");
/*      */       }
/*      */     }
/*      */ 
/* 1030 */     this.parser.getOptions();
/* 1031 */     getMyOptions();
/*      */   }
/*      */ 
/*      */   public Format toLabelFormat(String type)
/*      */   {
/* 1042 */     Format returnValue = null;
/*      */ 
/* 1044 */     if (type == null)
/* 1045 */       returnValue = NumberFormat.getNumberInstance();
/* 1046 */     else if (type.equals("0"))
/* 1047 */       returnValue = NumberFormat.getNumberInstance();
/* 1048 */     else if (type.equals("1"))
/* 1049 */       returnValue = NumberFormat.getPercentInstance();
/* 1050 */     else if (type.equals("2")) {
/* 1051 */       returnValue = NumberFormat.getCurrencyInstance();
/*      */     }
/*      */ 
/* 1054 */     return returnValue;
/*      */   }
/*      */ 
/*      */   public String getParameter(String name)
/*      */   {
/*      */     try
/*      */     {
/* 1067 */       return this.properties.getProperty(name);
/*      */     } catch (Exception e) {
/* 1069 */       log("couldn't get parameter " + name);
/*      */     }
/* 1071 */     return null;
/*      */   }
/*      */ 
/*      */   public Enumeration getParameterNames()
/*      */   {
/* 1081 */     return this.properties.propertyNames();
/*      */   }
/*      */ 
/*      */   public String getProperty(String property)
/*      */   {
/* 1092 */     return getParameter(property);
/*      */   }
/*      */ 
/*      */   public abstract void init();
/*      */ 
/*      */   public void initializeToolTipVars()
/*      */   {
/* 1107 */     String str = getParameter("dwellUseDatasetName");
/*      */ 
/* 1109 */     if ((str != null) && (str.equalsIgnoreCase("true"))) {
/* 1110 */       this.dwellUseDatasetName = true;
/*      */     }
/*      */ 
/* 1113 */     str = getParameter("dwellUseLabelString");
/*      */ 
/* 1115 */     if ((str != null) && (str.equalsIgnoreCase("true"))) {
/* 1116 */       this.dwellUseString = true;
/*      */     }
/*      */ 
/* 1119 */     str = getParameter("dwellUseXValue");
/*      */ 
/* 1121 */     if ((str != null) && (str.equalsIgnoreCase("false"))) {
/* 1122 */       this.dwellUseXValue = false;
/*      */     }
/*      */ 
/* 1125 */     str = getParameter("dwellUseYValue");
/*      */ 
/* 1127 */     if ((str != null) && (str.equalsIgnoreCase("false"))) {
/* 1128 */       this.dwellUseYValue = false;
/*      */     }
/*      */ 
/* 1131 */     str = getParameter("dwellUseY2Value");
/*      */ 
/* 1133 */     if ((str != null) && (str.equalsIgnoreCase("true"))) {
/* 1134 */       this.dwellUseY2Value = true;
/*      */     }
/*      */ 
/* 1137 */     str = getParameter("dwellXString");
/*      */ 
/* 1139 */     if (str != null)
/* 1140 */       this.dwellXString = str;
/*      */     else {
/* 1142 */       this.dwellXString = "X: XX";
/*      */     }
/*      */ 
/* 1145 */     str = getParameter("dwellYString");
/*      */ 
/* 1147 */     if (str != null)
/* 1148 */       this.dwellYString = str;
/*      */     else {
/* 1150 */       this.dwellYString = "Y: XX";
/*      */     }
/*      */ 
/* 1153 */     str = getParameter("dwellY2String");
/*      */ 
/* 1155 */     if (str != null)
/* 1156 */       this.dwellY2String = str;
/*      */     else {
/* 1158 */       this.dwellY2String = "Y2: XX";
/*      */     }
/*      */ 
/* 1161 */     str = getParameter("dwellXPercentFormat");
/*      */ 
/* 1163 */     if ((str != null) && 
/* 1164 */       (str.equalsIgnoreCase("true"))) {
/* 1165 */       this.dwellXLabelFormat = NumberFormat.getPercentInstance(this.chart.getGlobals().locale);
/*      */     }
/*      */ 
/* 1169 */     str = getParameter("dwellXCurrencyFormat");
/*      */ 
/* 1171 */     if ((str != null) && 
/* 1172 */       (str.equalsIgnoreCase("true"))) {
/* 1173 */       this.dwellXLabelFormat = NumberFormat.getCurrencyInstance(this.chart.getGlobals().locale);
/*      */     }
/*      */ 
/* 1177 */     str = getParameter("dwellYPercentFormat");
/*      */ 
/* 1179 */     if ((str != null) && 
/* 1180 */       (str.equalsIgnoreCase("true"))) {
/* 1181 */       this.dwellYLabelFormat = NumberFormat.getPercentInstance(this.chart.getGlobals().locale);
/*      */     }
/*      */ 
/* 1185 */     str = getParameter("dwellYCurrencyFormat");
/*      */ 
/* 1187 */     if ((str != null) && 
/* 1188 */       (str.equalsIgnoreCase("true"))) {
/* 1189 */       this.dwellYLabelFormat = NumberFormat.getCurrencyInstance(this.chart.getGlobals().locale);
/*      */     }
/*      */ 
/* 1193 */     if (this.dwellXLabelFormat == null)
/*      */     {
/* 1195 */       this.dwellXLabelFormat = NumberFormat.getInstance(this.chart.getGlobals().locale);
/*      */     }
/*      */ 
/* 1198 */     if (this.dwellYLabelFormat == null) {
/* 1199 */       this.dwellYLabelFormat = NumberFormat.getInstance(this.chart.getGlobals().locale);
/*      */     }
/*      */ 
/* 1202 */     str = getParameter("dwellXLabelPrecision");
/*      */ 
/* 1204 */     if (str != null) {
/* 1205 */       this.dwellXLabelFormat.setMaximumFractionDigits(Integer.parseInt(str));
/* 1206 */       this.dwellXLabelFormat.setMinimumFractionDigits(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 1209 */     str = getParameter("dwellYLabelPrecision");
/*      */ 
/* 1211 */     if (str != null) {
/* 1212 */       this.dwellYLabelFormat.setMaximumFractionDigits(Integer.parseInt(str));
/* 1213 */       this.dwellYLabelFormat.setMinimumFractionDigits(Integer.parseInt(str));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void initLocale()
/*      */   {
/* 1222 */     if (this.userLocale == null) {
/* 1223 */       this.userLocale = ParameterParser.getLocale(getParameter("locale"));
/*      */     }
/*      */ 
/* 1226 */     if (this.userLocale == null) {
/* 1227 */       this.userLocale = Locale.getDefault();
/*      */     }
/*      */ 
/* 1230 */     String s = getParameter("defaultFont");
/*      */ 
/* 1232 */     if (s != null) {
/* 1233 */       if (this.delimiter == null) {
/* 1234 */         setDelimiter();
/*      */       }
/*      */ 
/* 1237 */       Gc.defaultFont = ParameterParser.getFont(s, this.delimiter);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void loadProperties(String filename)
/*      */   {
/* 1245 */     InputStream is = openFile(filename);
/*      */ 
/* 1247 */     if (is != null)
/*      */       try {
/* 1249 */         this.properties.load(is);
/* 1250 */         is.close();
/*      */       } catch (Exception ignored) {
/* 1252 */         ignored.printStackTrace();
/*      */       }
/*      */     else
/* 1255 */       System.out.println("couldn't open" + filename);
/*      */   }
/*      */ 
/*      */   public void log(String s)
/*      */   {
/* 1266 */     if (this.logStream == null)
/* 1267 */       System.out.println(s);
/*      */     else
/*      */       try {
/* 1270 */         this.logStream.write(s.getBytes());
/*      */       } catch (IOException e) {
/* 1272 */         System.out.println(s);
/*      */       }
/*      */   }
/*      */ 
/*      */   public Image makeURLImage(String s)
/*      */   {
/* 1282 */     if (this.imageTracker == null)
/*      */     {
/* 1284 */       String version = System.getProperty("java.specification.version");
/*      */ 
/* 1286 */       if ((version != null) && (
/* 1287 */         (version.equals("1.4")) || (version.equals("1.3")) || (version.equals("1.2"))))
/*      */       {
/* 1292 */         this.imageTracker = new MediaTracker(new Panel());
/*      */       }
/*      */       else {
/* 1295 */         if (frame == null) {
/* 1296 */           frame = new Frame();
/* 1297 */           frame.addNotify();
/*      */         }
/*      */ 
/* 1300 */         this.imageTracker = new MediaTracker(frame);
/*      */       }
/*      */     }
/*      */ 
/* 1304 */     Image img = Toolkit.getDefaultToolkit().createImage(s);
/*      */ 
/* 1306 */     if (this.noisy) {
/* 1307 */       log("trying to get image " + s);
/*      */     }
/*      */ 
/* 1310 */     this.imageTracker.addImage(img, 0);
/*      */ 
/* 1312 */     return img;
/*      */   }
/*      */ 
/*      */   protected InputStream openFile(String s)
/*      */   {
/* 1324 */     if (this.noisy) {
/* 1325 */       log("trying to open file " + s);
/*      */     }
/*      */     try
/*      */     {
/* 1329 */       return new FileInputStream(s);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1333 */       log("can't open file " + s);
/*      */     }
/*      */ 
/* 1336 */     return null;
/*      */   }
/*      */ 
/*      */   public InputStream openURL(String s)
/*      */   {
/* 1350 */     if (this.noisy) {
/* 1351 */       log("trying to open URL " + s);
/*      */     }
/*      */     try
/*      */     {
/* 1355 */       myUrl = new URL(s);
/*      */     }
/*      */     catch (MalformedURLException e)
/*      */     {
/*      */       URL myUrl;
/* 1357 */       log("couldn't open " + s);
/*      */ 
/* 1359 */       return openFile(s);
/*      */     }
/*      */     try
/*      */     {
/*      */       URL myUrl;
/* 1364 */       URLConnection connection = myUrl.openConnection();
/* 1365 */       connection.setUseCaches(false);
/* 1366 */       myInputStream = connection.getInputStream();
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*      */       InputStream myInputStream;
/* 1368 */       log("can't open stream " + s);
/*      */ 
/* 1370 */       return null;
/*      */     }
/*      */     InputStream myInputStream;
/*      */     URLConnection connection;
/* 1373 */     return myInputStream;
/*      */   }
/*      */ 
/*      */   public void setLogStream(OutputStream out)
/*      */   {
/* 1384 */     this.logStream = out;
/*      */   }
/*      */ 
/*      */   public void setStringProperty(String property, String value)
/*      */   {
/* 1398 */     this.properties.put(property, value);
/*      */   }
/*      */ 
/*      */   public void setProperty(String property, Object value)
/*      */   {
/* 1410 */     if ((value instanceof String))
/* 1411 */       this.properties.put(property, value);
/* 1412 */     else if ((value instanceof double[]))
/* 1413 */       this.properties.put(property, doubleArrToString((double[])value));
/* 1414 */     else if ((value instanceof int[]))
/* 1415 */       this.properties.put(property, intArrToString((int[])value));
/*      */     else
/* 1417 */       this.properties.put(property, value);
/*      */   }
/*      */ 
/*      */   private String doubleArrToString(double[] array)
/*      */   {
/* 1431 */     if (this.delimiter == null) {
/* 1432 */       setDelimiter();
/*      */     }
/*      */ 
/* 1435 */     StringBuffer sb = new StringBuffer();
/* 1436 */     sb.append(Double.toString(array[0]));
/*      */ 
/* 1438 */     for (int i = 1; i < array.length; i++) {
/* 1439 */       sb.append(this.delimiter);
/* 1440 */       sb.append(Double.toString(array[i]));
/*      */     }
/*      */ 
/* 1443 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   private String intArrToString(int[] array)
/*      */   {
/* 1455 */     if (this.delimiter == null) {
/* 1456 */       setDelimiter();
/*      */     }
/*      */ 
/* 1459 */     StringBuffer sb = new StringBuffer();
/* 1460 */     sb.append(Integer.toString(array[0]));
/*      */ 
/* 1462 */     for (int i = 1; i < array.length; i++) {
/* 1463 */       sb.append(this.delimiter);
/* 1464 */       sb.append(Integer.toString(array[i]));
/*      */     }
/*      */ 
/* 1467 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public void setUserImagingCodec(UserImagingCodec codec)
/*      */   {
/* 1478 */     this.userImagingCodec = codec;
/*      */   }
/*      */ 
/*      */   protected void setDelimiter()
/*      */   {
/* 1485 */     this.delimiter = getParameter("delimiter");
/*      */ 
/* 1487 */     if (this.delimiter == null)
/* 1488 */       this.delimiter = ",";
/*      */   }
/*      */ 
/*      */   public void setDataProvider(DataProvider dp)
/*      */   {
/* 1502 */     this.dataProvider = dp;
/*      */   }
/*      */ 
/*      */   public DataProvider getDataProvider()
/*      */   {
/* 1512 */     return this.dataProvider;
/*      */   }
/*      */ 
/*      */   public static void applyOptions(_Chart chart, Properties options)
/*      */   {
/* 1520 */     options.setProperty("dataset0LabelFont", toString(chart.getDatasets()[0].getLabelFont()));
/*      */ 
/* 1523 */     Gc gc = chart.getBackground().getGc();
/* 1524 */     int style = gc.getFillStyle();
/*      */ 
/* 1531 */     if (style == -1) {
/* 1532 */       options.setProperty("backgroundColor", ChartUtil.toString(gc.getFillColor()));
/* 1533 */     } else if (style == 0) {
/* 1534 */       options.setProperty("backgroundColor", ChartUtil.toString(gc.getFillColor()));
/* 1535 */       options.setProperty("backgroundSecondaryColor", 
/* 1536 */         ChartUtil.toString(gc.getSecondaryFillColor()));
/* 1537 */       options.setProperty("backgroundGradient", gc.getGradient());
/*      */     }
/*      */ 
/* 1543 */     options.setProperty("titleFont", ChartUtil.toString(chart.getBackground().getTitleFont()));
/* 1544 */     options.setProperty("titleColor", ChartUtil.toString(chart.getBackground().getTitleColor()));
/*      */ 
/* 1546 */     String str = chart.getBackground().getTitleString();
/*      */ 
/* 1548 */     if (str != null) {
/* 1549 */       options.setProperty("titleString", str);
/*      */     }
/*      */ 
/* 1552 */     options.setProperty("subTitleFont", 
/* 1553 */       ChartUtil.toString(chart.getBackground().getSubTitleFont()));
/* 1554 */     options.setProperty("subTitleColor", 
/* 1555 */       ChartUtil.toString(chart.getBackground().getSubTitleColor()));
/*      */ 
/* 1557 */     str = chart.getBackground().getSubTitleString();
/*      */ 
/* 1559 */     if (str != null) {
/* 1560 */       options.setProperty("subTitleString", str);
/*      */     }
/*      */ 
/* 1563 */     options.setProperty("plotAreaLeft", chart.getPlotarea().getLlX());
/* 1564 */     options.setProperty("plotAreaRight", chart.getPlotarea().getUrX());
/* 1565 */     options.setProperty("plotAreaTop", chart.getPlotarea().getUrY());
/* 1566 */     options.setProperty("plotAreaBottom", chart.getPlotarea().getLlY());
/*      */ 
/* 1568 */     if (chart.isThreeD()) {
/* 1569 */       options.setProperty("threeD", "true");
/* 1570 */       options.setProperty("XDepth", chart.getXOffset());
/* 1571 */       options.setProperty("YDepth", chart.getYOffset());
/*      */     }
/*      */   }
/*      */ 
/*      */   static String toString(Font font)
/*      */   {
/* 1587 */     return font.getName() + "," + font.getSize() + "," + font.getStyle();
/*      */   }
/*      */ 
/*      */   public DatasetReader getGraphReader()
/*      */   {
/* 1595 */     return this.graphReader;
/*      */   }
/*      */ 
/*      */   public void setGraphReader(DatasetReader graphReader)
/*      */   {
/* 1604 */     this.graphReader = graphReader;
/*      */   }
/*      */ 
/*      */   public String getGraphLabelField()
/*      */   {
/* 1612 */     return this.graphLabelField;
/*      */   }
/*      */ 
/*      */   public void setGraphLabelField(String graphLabelField)
/*      */   {
/* 1621 */     this.graphLabelField = graphLabelField;
/*      */   }
/*      */ 
/*      */   public ArrayList getGraphShowData()
/*      */   {
/* 1629 */     return this.graphShowData;
/*      */   }
/*      */ 
/*      */   public void setGraphShowData(ArrayList graphShowData)
/*      */   {
/* 1638 */     this.graphShowData = graphShowData;
/*      */   }
/*      */ 
/*      */   public void setJatoolsDataProvider(Script jatoolsDataProvider)
/*      */   {
/* 1647 */     this.jatoolsDataProvider = jatoolsDataProvider;
/*      */   }
/*      */ 
/*      */   public Script getJatoolsDataProvider()
/*      */   {
/* 1656 */     return this.jatoolsDataProvider;
/*      */   }
/*      */ 
/*      */   public void setGraphPlotFrom(int plotFrom)
/*      */   {
/* 1665 */     this.plotFrom = plotFrom;
/*      */   }
/*      */ 
/*      */   public int getPlotFrom()
/*      */   {
/* 1674 */     return this.plotFrom;
/*      */   }
/*      */ 
/*      */   public void setGraphDataset(jatools.dataset.Dataset ds)
/*      */   {
/* 1683 */     this.ds = ds;
/*      */   }
/*      */ 
/*      */   public jatools.dataset.Dataset getDs()
/*      */   {
/* 1692 */     return this.ds;
/*      */   }
/*      */ 
/*      */   public void setDs(jatools.dataset.Dataset ds)
/*      */   {
/* 1701 */     this.ds = ds;
/*      */   }
/*      */ 
/*      */   public String getIdField()
/*      */   {
/* 1710 */     return this.idField;
/*      */   }
/*      */ 
/*      */   public void setIdField(String idField)
/*      */   {
/* 1721 */     this.idField = idField;
/*      */   }
/*      */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.Bean
 * JD-Core Version:    0.6.2
 */