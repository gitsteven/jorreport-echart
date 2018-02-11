/*     */ package jatools.component.chart.applet;
/*     */ 
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import java.text.DateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class GanttDateReader extends DateStreamReader
/*     */ {
/*     */   String[] labels;
/*     */ 
/*     */   public GanttDateReader(ChartInterface c, GetParam g)
/*     */   {
/*  27 */     super(c, g);
/*     */   }
/*     */ 
/*     */   public long dateParse(String s)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       d = this.dateFormat.parse(s).getTime();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       long d;
/*  41 */       return -1L;
/*     */     }
/*     */     long d;
/*  44 */     return d;
/*     */   }
/*     */ 
/*     */   protected void convertDataBlockToChartData(boolean firstTime)
/*     */   {
/*  57 */     int numRows = this.dataBlockStrings.size();
/*  58 */     this.labels = new String[numRows];
/*  59 */     double[] startDates = new double[numRows];
/*  60 */     double[] endDates = new double[numRows];
/*  61 */     double[] xVals = new double[numRows];
/*     */ 
/*  63 */     for (int i = 0; i < numRows; i++) {
/*  64 */       this.labels[i] = getLabelElement(i);
/*  65 */       xVals[i] = i;
/*     */     }
/*     */ 
/*  68 */     int relevantColumns = ((StringTokenizer)this.dataBlockStrings.get(0)).countTokens();
/*  69 */     int j = 1;
/*     */ 
/*  71 */     while (j <= relevantColumns) {
/*  72 */       for (i = 0; i < numRows; i++) {
/*  73 */         startDates[i] = getTimeElement(i);
/*  74 */         endDates[i] = getTimeElement(i);
/*     */       }
/*     */ 
/*  77 */       j += 2;
/*     */ 
/*  80 */       if (firstTime) {
/*  81 */         getDatasetParameters(j / 2 - 1, null, endDates, startDates, null);
/*  82 */         this.chart.getDatasets()[(j / 2 - 1)].replaceLabels(this.labels);
/*     */       } else {
/*  84 */         this.chart.getDatasets()[(j / 2 - 1)].replaceLabels(this.labels);
/*  85 */         this.chart.getDatasets()[(j / 2 - 1)].replaceYData(startDates);
/*  86 */         this.chart.getDatasets()[(j / 2 - 1)].replaceY2Data(endDates);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean getDataset(int which)
/*     */   {
/*  97 */     double[] xArr = (double[])null;
/*  98 */     double[] yArr = (double[])null;
/*  99 */     double[] y2Arr = (double[])null;
/* 100 */     double[] y3Arr = (double[])null;
/*     */ 
/* 104 */     Dataset d = this.getter.getDataset(this.chart, which);
/*     */ 
/* 106 */     if (d != null) {
/* 107 */       this.chart.addDataset(d);
/* 108 */       getDatasetPropertiesFromParameters(which, d);
/*     */ 
/* 110 */       return true;
/*     */     }
/*     */ 
/* 114 */     if (this.getter.getDataProvider() != null) {
/* 115 */       if (this.dataProvider == null) {
/* 116 */         this.dataProvider = this.getter.getDataProvider().getDatasets();
/*     */       }
/*     */ 
/* 120 */       if (this.dataProvider.hasNext()) {
/* 121 */         Object o = this.dataProvider.next();
/*     */ 
/* 123 */         if ((o instanceof Dataset)) {
/* 124 */           this.chart.addDataset((Dataset)o);
/* 125 */           getDatasetPropertiesFromParameters(which, (Dataset)o);
/*     */ 
/* 127 */           return true;
/*     */         }
/*     */       }
/*     */ 
/* 131 */       return false;
/*     */     }
/*     */ 
/* 135 */     String str = getParameter("dataset" + which + "xValues");
/*     */ 
/* 137 */     if (str != null) {
/* 138 */       xArr = getVals(str);
/*     */     }
/*     */ 
/* 141 */     str = getParameter("dataset" + which + "dateValues");
/*     */ 
/* 143 */     if (str != null) {
/* 144 */       xArr = getDateVals(str);
/*     */     }
/*     */ 
/* 147 */     str = getParameter("dataset" + which + "endDates");
/*     */ 
/* 149 */     if (str != null) {
/* 150 */       yArr = getDateVals(str);
/*     */     }
/*     */ 
/* 153 */     str = getParameter("dataset" + which + "startDates");
/*     */ 
/* 155 */     if (str != null) {
/* 156 */       y2Arr = getDateVals(str);
/*     */     }
/*     */ 
/* 168 */     str = getParameter("dataset" + which + "yValues");
/*     */ 
/* 170 */     if (str != null) {
/* 171 */       yArr = getVals(str);
/*     */     }
/*     */ 
/* 174 */     str = getParameter("dataset" + which + "y2Values");
/*     */ 
/* 176 */     if (str != null) {
/* 177 */       y2Arr = getVals(str);
/*     */     }
/*     */ 
/* 180 */     str = getParameter("dataset" + which + "y3Values");
/*     */ 
/* 182 */     if (str != null) {
/* 183 */       y3Arr = getVals(str);
/*     */     }
/*     */ 
/* 186 */     str = getParameter("dataset" + which + "xyValues");
/*     */ 
/* 188 */     if (str != null) {
/* 189 */       StringTokenizer st = new StringTokenizer(str, this.delimiter);
/* 190 */       xArr = new double[st.countTokens() / 2];
/* 191 */       yArr = new double[xArr.length];
/*     */ 
/* 193 */       int i = 0;
/*     */ 
/* 195 */       while (st.hasMoreTokens()) {
/*     */         try {
/* 197 */           xArr[i] = Double.valueOf(st.nextToken().trim()).doubleValue();
/* 198 */           yArr[i] = Double.valueOf(st.nextToken().trim()).doubleValue();
/*     */         }
/*     */         catch (Exception e) {
/* 201 */           yArr[i] = (-1.0D / 0.0D);
/*     */         }
/*     */ 
/* 204 */         i++;
/*     */       }
/*     */     }
/*     */ 
/* 208 */     if ((y2Arr != null) && (yArr == null)) {
/* 209 */       yArr = new double[y2Arr.length];
/*     */ 
/* 211 */       for (int i = 0; i < yArr.length; i++) {
/* 212 */         yArr[i] = -1.0D;
/*     */       }
/*     */     }
/* 215 */     return getDatasetParameters(which, xArr, yArr, y2Arr, y3Arr);
/*     */   }
/*     */ 
/*     */   protected String getLabelElement(int row)
/*     */   {
/* 222 */     StringTokenizer s = (StringTokenizer)this.dataBlockStrings.get(row);
/* 223 */     String labelString = s.nextToken();
/*     */ 
/* 225 */     return labelString;
/*     */   }
/*     */ 
/*     */   public String[] getLabels(String s)
/*     */   {
/* 237 */     if (this.labels != null) {
/* 238 */       return this.labels;
/*     */     }
/* 240 */     return super.getLabels(s);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.applet.GanttDateReader
 * JD-Core Version:    0.6.2
 */