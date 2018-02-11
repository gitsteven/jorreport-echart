/*     */ package jatools.component.chart.applet;
/*     */ 
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.Datum;
/*     */ import jatools.component.chart.chart.Globals;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public class DateStreamReader extends ParameterParser
/*     */ {
/*     */   protected StringTokenizer tokenLine;
/*     */   protected ArrayList dataBlockStrings;
/*     */   protected InputStream myInputStream;
/*     */   protected String incrementalUrl;
/*  31 */   DateFormat dateFormat = null;
/*  32 */   long startData = -1L;
/*  33 */   long endData = -1L;
/*     */   TimeZone timeZone;
/*     */ 
/*     */   public DateStreamReader(ChartInterface c, GetParam g)
/*     */   {
/*  42 */     super(c, g);
/*     */   }
/*     */ 
/*     */   protected void convertDataBlockToChartData(boolean firstTime)
/*     */   {
/*  51 */     double[] partialX = (double[])null;
/*  52 */     double[] partialY = (double[])null;
/*  53 */     int partialLength = 0;
/*     */ 
/*  56 */     int numRows = this.dataBlockStrings.size();
/*  57 */     double[] x = new double[numRows];
/*  58 */     double[] y = new double[numRows];
/*     */ 
/*  60 */     for (int i = 0; i < numRows; i++) {
/*  61 */       x[i] = getTimeElement(i);
/*     */     }
/*     */ 
/*  64 */     if ((this.startData != -1L) || (this.endData != -1L)) {
/*  65 */       for (i = 0; i < x.length; i++)
/*     */       {
/*  67 */         if (x[i] > 0.0D) {
/*  68 */           partialLength++;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  73 */       partialX = new double[partialLength];
/*  74 */       partialY = new double[partialLength];
/*     */     }
/*     */ 
/*  77 */     int relevantColumns = ((StringTokenizer)this.dataBlockStrings.get(0)).countTokens();
/*     */ 
/*  79 */     for (int j = 1; j <= relevantColumns; j++) {
/*  80 */       for (i = 0; i < numRows; i++) {
/*  81 */         y[i] = getDoubleElement(i);
/*     */       }
/*     */ 
/*  84 */       if ((this.startData != -1L) || (this.endData != -1L)) {
/*  85 */         int l = 0;
/*     */ 
/*  87 */         for (int k = 0; k < x.length; k++) {
/*  88 */           if (x[k] > 0.0D)
/*     */           {
/*  91 */             partialX[l] = x[k];
/*  92 */             partialY[l] = y[k];
/*  93 */             l++;
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  99 */       if ((this.startData == -1L) && (this.endData == -1L)) {
/* 100 */         if (firstTime) {
/* 101 */           getDatasetParameters(j - 1, x, y, null, null);
/*     */         } else {
/* 103 */           this.chart.getDatasets()[(j - 1)].replaceXData(x);
/* 104 */           this.chart.getDatasets()[(j - 1)].replaceYData(y);
/*     */         }
/*     */ 
/*     */       }
/* 109 */       else if (firstTime) {
/* 110 */         getDatasetParameters(j - 1, partialX, partialY, null, null);
/*     */       } else {
/* 112 */         this.chart.getDatasets()[(j - 1)].replaceXData(partialX);
/* 113 */         this.chart.getDatasets()[(j - 1)].replaceYData(partialY);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void convertDataBlockToIncrementalData(boolean firstTime)
/*     */   {
/* 129 */     int numRows = this.dataBlockStrings.size();
/* 130 */     double[] x = new double[numRows];
/* 131 */     double[] y = new double[numRows];
/*     */ 
/* 133 */     for (int i = 0; i < numRows; i++) {
/* 134 */       x[i] = getTimeElement(i);
/*     */     }
/*     */ 
/* 137 */     int relevantColumns = ((StringTokenizer)this.dataBlockStrings.get(0)).countTokens();
/*     */ 
/* 139 */     for (int j = 1; j <= relevantColumns; j++) {
/* 140 */       for (i = 0; i < numRows; i++) {
/* 141 */         y[i] = getDoubleElement(i);
/*     */       }
/*     */ 
/* 145 */       if (firstTime) {
/* 146 */         getDatasetParameters(j - 1, x, y, null, null);
/*     */       } else {
/* 148 */         ArrayList dataVector = this.chart.getDatasets()[(j - 1)].getData();
/* 149 */         Globals g = this.chart.getGlobals();
/*     */ 
/* 151 */         for (int k = 0; k < x.length; k++) {
/* 152 */           Datum d = new Datum(x[k], y[k], g);
/* 153 */           dataVector.add(d);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public long dateParse(String s)
/*     */   {
/*     */     long d;
/*     */     long d;
/* 167 */     if (this.dateFormat == null)
/* 168 */       d = Date.parse(s);
/*     */     else {
/*     */       try {
/* 171 */         d = this.dateFormat.parse(s).getTime();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */         long d;
/* 173 */         System.out.println("parse failure");
/* 174 */         d = Date.parse(s);
/*     */       }
/*     */     }
/*     */ 
/* 178 */     return d;
/*     */   }
/*     */ 
/*     */   protected double[] getDateVals(String s)
/*     */   {
/* 183 */     StringTokenizer st = new StringTokenizer(s, this.delimiter);
/* 184 */     double[] vals = new double[st.countTokens()];
/* 185 */     int i = 0;
/*     */ 
/* 187 */     while (st.hasMoreTokens()) {
/*     */       try {
/* 189 */         vals[i] = dateParse(st.nextToken().trim());
/*     */       } catch (Exception e) {
/* 191 */         vals[i] = (-1.0D / 0.0D);
/*     */       }
/*     */ 
/* 194 */       i++;
/*     */     }
/*     */ 
/* 197 */     return vals;
/*     */   }
/*     */ 
/*     */   protected double getDoubleElement(int row)
/*     */   {
/* 204 */     StringTokenizer s = (StringTokenizer)this.dataBlockStrings.get(row);
/* 205 */     String numString = s.nextToken().trim();
/*     */     try
/*     */     {
/* 208 */       return Double.valueOf(numString).doubleValue(); } catch (Exception e) {
/*     */     }
/* 210 */     return (-1.0D / 0.0D);
/*     */   }
/*     */ 
/*     */   public void getOptions()
/*     */   {
/* 218 */     String str = getParameter("inputTimeZone");
/*     */ 
/* 220 */     if (str != null) {
/* 221 */       this.timeZone = TimeZone.getTimeZone(str);
/*     */     }
/*     */ 
/* 224 */     str = getParameter("inputDateFormat");
/*     */ 
/* 226 */     if (str != null) {
/* 227 */       this.dateFormat = DateFormat.getInstance();
/*     */ 
/* 229 */       if (this.timeZone != null) {
/* 230 */         this.dateFormat.setTimeZone(this.timeZone);
/*     */       }
/*     */ 
/* 233 */       if ((this.dateFormat instanceof SimpleDateFormat))
/* 234 */         ((SimpleDateFormat)this.dateFormat).applyPattern(str);
/*     */       else {
/* 236 */         this.dateFormat = null;
/*     */       }
/*     */     }
/*     */ 
/* 240 */     str = getParameter("startData");
/*     */ 
/* 242 */     if (str != null) {
/* 243 */       this.startData = dateParse(str);
/*     */     }
/*     */ 
/* 246 */     str = getParameter("endData");
/*     */ 
/* 248 */     if (str != null) {
/* 249 */       this.endData = dateParse(str);
/*     */     }
/*     */ 
/* 252 */     str = getParameter("incrementalDataURL");
/*     */ 
/* 254 */     if (str != null) {
/* 255 */       setIncrementalURL(str);
/*     */     }
/*     */ 
/* 258 */     super.getOptions();
/*     */   }
/*     */ 
/*     */   protected double getTimeElement(int row)
/*     */   {
/* 266 */     StringTokenizer s = (StringTokenizer)this.dataBlockStrings.get(row);
/* 267 */     String dateString = s.nextToken().trim();
/*     */     try
/*     */     {
/* 270 */       long thisDate = dateParse(dateString);
/*     */ 
/* 272 */       if ((this.startData > 0L) && 
/* 273 */         (thisDate < this.startData))
/*     */       {
/* 275 */         return (-1.0D / 0.0D);
/*     */       }
/*     */ 
/* 279 */       if ((this.endData > 0L) && 
/* 280 */         (thisDate > this.endData))
/*     */       {
/* 282 */         return (-1.0D / 0.0D);
/*     */       }
/*     */ 
/* 286 */       return thisDate;
/*     */     } catch (IllegalArgumentException e) {
/* 288 */       System.out.println("can't parse " + dateString);
/*     */     }
/* 290 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   protected void readDataBlock()
/*     */   {
/* 298 */     String s = getLineFromURL(this.myInputStream);
/*     */ 
/* 300 */     while (s != null) {
/* 301 */       if (s.length() > 1) {
/* 302 */         this.dataBlockStrings.add(new StringTokenizer(s, ","));
/*     */       }
/*     */ 
/* 305 */       s = getLineFromURL(this.myInputStream);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void readStream(String s)
/*     */   {
/* 314 */     boolean firstTime = this.myInputStream == null;
/*     */ 
/* 316 */     if ((!firstTime) && 
/* 317 */       (this.incrementalUrl != null)) {
/* 318 */       s = this.incrementalUrl;
/*     */     }
/*     */ 
/* 322 */     if ((this.myInputStream = openURL(s)) == null) {
/* 323 */       return;
/*     */     }
/*     */ 
/* 326 */     this.dataBlockStrings = new ArrayList();
/* 327 */     readDataBlock();
/*     */ 
/* 329 */     if (s == this.incrementalUrl)
/* 330 */       convertDataBlockToIncrementalData(firstTime);
/*     */     else {
/* 332 */       convertDataBlockToChartData(firstTime);
/*     */     }
/*     */ 
/* 335 */     if (!closeURL(this.myInputStream));
/*     */   }
/*     */ 
/*     */   public void setIncrementalURL(String s)
/*     */   {
/* 345 */     this.incrementalUrl = s;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.applet.DateStreamReader
 * JD-Core Version:    0.6.2
 */