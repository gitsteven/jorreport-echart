/*     */ package jatools.component.chart;
/*     */ 
/*     */ import jatools.accessor.PropertyDescriptor;
/*     */ import jatools.component.Component;
/*     */ import jatools.component.ComponentConstants;
/*     */ import jatools.component.ImageStyle;
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.servlet.Bean;
/*     */ import jatools.data.reader.DatasetReader;
/*     */ import jatools.dataset.Dataset;
/*     */ import jatools.engine.script.Script;
/*     */ import jatools.imageio.ImageWriter;
/*     */ import jatools.util.Util;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class Chart extends Component
/*     */ {
/*  33 */   private Properties properties = new Properties();
/*     */   private DatasetReader reader;
/*     */   private Dataset ds;
/*     */   private String labelField;
/*  37 */   private ArrayList plotData = new ArrayList();
/*  38 */   private int plotFrom = -1;
/*     */   public ChartInterface chart;
/*  44 */   int exportImageFormat = 3;
/*     */   private String idField;
/*     */ 
/*     */   public void refersh()
/*     */   {
/*  57 */     this.reader = null;
/*  58 */     this.labelField = null;
/*  59 */     this.plotData = null;
/*  60 */     this.chart = ChartFactory.createInstance(this, null);
/*     */   }
/*     */ 
/*     */   public void paintAsJPG(OutputStream os, Script script)
/*     */   {
/*  75 */     this.properties.put("width", getWidth());
/*  76 */     this.properties.put("height", getHeight());
/*  77 */     this.properties.put("imageType", "gif");
/*     */ 
/*  80 */     Bean bean = ChartFactory.createBeanInstance(this, script);
/*     */     ArrayList v;
/*     */     try
/*     */     {
/*  83 */       v = bean.getToolTips();
/*     */     }
/*     */     catch (Exception localException1) {
/*     */     }
/*     */     try {
/*  88 */       ImageWriter.write(os, Util.asBufferedImage(bean.getImage()), 2);
/*  89 */       os.close();
/*     */     }
/*     */     catch (IOException e) {
/*  92 */       e.printStackTrace();
/*     */     }
/*     */     catch (Exception e) {
/*  95 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public PropertyDescriptor[] getRegistrableProperties()
/*     */   {
/* 105 */     return new PropertyDescriptor[] { 
/* 106 */       ComponentConstants._NAME, ComponentConstants._BORDER, 
/* 107 */       ComponentConstants._REQUIRED_HTML_IMAGE_FORMAT2, ComponentConstants._HYPERLINK, 
/* 108 */       ComponentConstants._TOOLTIP_TEXT, ComponentConstants._X, ComponentConstants._Y, 
/* 109 */       ComponentConstants._WIDTH, ComponentConstants._HEIGHT, 
/* 111 */       ComponentConstants._PRINT_STYLE, ComponentConstants._PROPERTIES, 
/* 112 */       ComponentConstants._READER, ComponentConstants._LABEL_FIELD, 
/* 113 */       ComponentConstants._PLOT_DATA, ComponentConstants._CELL, ComponentConstants._INIT_PRINT, 
/* 114 */       ComponentConstants._AFTERPRINT, ComponentConstants._BEFOREPRINT2 };
/*     */   }
/*     */ 
/*     */   public Properties getProperties()
/*     */   {
/* 124 */     return this.properties;
/*     */   }
/*     */ 
/*     */   public void setProperties(Properties properties)
/*     */   {
/* 134 */     this.properties = properties;
/*     */   }
/*     */ 
/*     */   public void setProperty(String prop, String val)
/*     */   {
/* 144 */     if (this.properties != null)
/* 145 */       this.properties.setProperty(prop, val);
/*     */   }
/*     */ 
/*     */   public void setProperty(String prop, Object[] vals)
/*     */   {
/* 156 */     if ((this.properties != null) && (vals != null)) {
/* 157 */       String str = vals[0];
/*     */ 
/* 159 */       for (int i = 1; i < vals.length; i++) {
/* 160 */         str = str + "," + vals[1];
/*     */       }
/*     */ 
/* 163 */       this.properties.setProperty(prop, str);
/*     */     }
/*     */   }
/*     */ 
/*     */   public DatasetReader getReader()
/*     */   {
/* 173 */     return this.reader;
/*     */   }
/*     */ 
/*     */   public void setReader(DatasetReader graphReader)
/*     */   {
/* 183 */     this.reader = graphReader;
/*     */   }
/*     */ 
/*     */   public String getLabelField()
/*     */   {
/* 192 */     return this.labelField;
/*     */   }
/*     */ 
/*     */   public void setLabelField(String labelData)
/*     */   {
/* 202 */     this.labelField = labelData;
/*     */   }
/*     */ 
/*     */   public ArrayList getPlotData()
/*     */   {
/* 211 */     return this.plotData;
/*     */   }
/*     */ 
/*     */   public void setPlotData(ArrayList showData)
/*     */   {
/* 221 */     this.plotData = showData;
/*     */   }
/*     */ 
/*     */   public int getExportImageFormat()
/*     */   {
/* 238 */     return this.exportImageFormat;
/*     */   }
/*     */ 
/*     */   public void setExportImageFormat(int imageType)
/*     */   {
/* 247 */     this.exportImageFormat = imageType;
/*     */   }
/*     */ 
/*     */   public void clearProperties()
/*     */   {
/* 254 */     String[] reserved = { 
/* 255 */       "background", "legend", "plotarea", "title", "icon", "threeD", "Depth" };
/*     */ 
/* 257 */     Enumeration keys = this.properties.keys();
/*     */ 
/* 259 */     while (keys.hasMoreElements()) {
/* 260 */       boolean isDelete = true;
/* 261 */       String key = (String)keys.nextElement();
/*     */ 
/* 263 */       for (int i = 0; i < reserved.length; i++) {
/* 264 */         if (key.indexOf(reserved[i]) != -1) {
/* 265 */           isDelete = false;
/*     */ 
/* 267 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 271 */       if (isDelete)
/* 272 */         this.properties.remove(key);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ChartInterface getChart()
/*     */   {
/* 289 */     if (this.chart == null) {
/* 290 */       int width = getWidth();
/* 291 */       int height = getHeight();
/* 292 */       this.properties.put("width", width);
/* 293 */       this.properties.put("height", height);
/*     */ 
/* 296 */       this.chart = ChartFactory.createInstance(this, null);
/*     */     }
/*     */ 
/* 299 */     return this.chart;
/*     */   }
/*     */ 
/*     */   public ImageStyle getImageCSS()
/*     */   {
/* 308 */     return new ImageStyle(null, 0, 0, false, this.exportImageFormat);
/*     */   }
/*     */ 
/*     */   public int getPlotFrom()
/*     */   {
/* 317 */     return this.plotFrom;
/*     */   }
/*     */ 
/*     */   public void setPlotFrom(int plotFrom)
/*     */   {
/* 326 */     this.plotFrom = plotFrom;
/*     */   }
/*     */ 
/*     */   public Dataset getDs()
/*     */   {
/* 335 */     return this.ds;
/*     */   }
/*     */ 
/*     */   public void setDs(Dataset ds)
/*     */   {
/* 344 */     this.ds = ds;
/*     */   }
/*     */ 
/*     */   public String getIdField()
/*     */   {
/* 353 */     return this.idField;
/*     */   }
/*     */ 
/*     */   public void setIdField(String idField) {
/* 357 */     this.idField = idField;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.Chart
 * JD-Core Version:    0.6.2
 */