/*     */ package jatools.component.chart.applet;
/*     */ 
/*     */ import jatools.component.chart.chart.CandlestickChart;
/*     */ import jatools.component.chart.chart.CandlestickDatum;
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class CandlestickDateReader extends DateStreamReader
/*     */ {
/*     */   public CandlestickDateReader(ChartInterface c, GetParam g)
/*     */   {
/*  28 */     super(c, g);
/*     */   }
/*     */ 
/*     */   protected void convertDataBlockToChartData(boolean firstTime)
/*     */   {
/*  35 */     int numRows = this.dataBlockStrings.size();
/*  36 */     double[] x = new double[numRows];
/*  37 */     double[] high = new double[numRows];
/*  38 */     double[] low = new double[numRows];
/*  39 */     double[] open = new double[numRows];
/*  40 */     double[] close = new double[numRows];
/*     */ 
/*  42 */     for (int i = 0; i < numRows; i++) {
/*  43 */       x[i] = getTimeElement(i);
/*     */     }
/*  45 */     int relevantColumns = ((StringTokenizer)this.dataBlockStrings.get(0)).countTokens();
/*  46 */     int j = 0;
/*     */ 
/*  48 */     while (j <= relevantColumns) {
/*  49 */       if (relevantColumns - j < 4)
/*     */       {
/*  51 */         return;
/*     */       }
/*     */ 
/*  54 */       for (i = 0; i < numRows; i++)
/*  55 */         open[i] = getDoubleElement(i);
/*  56 */       for (i = 0; i < numRows; i++)
/*  57 */         high[i] = getDoubleElement(i);
/*  58 */       for (i = 0; i < numRows; i++)
/*  59 */         low[i] = getDoubleElement(i);
/*  60 */       for (i = 0; i < numRows; i++)
/*  61 */         close[i] = getDoubleElement(i);
/*  62 */       if (firstTime) {
/*  63 */         getDatasetParameters((j - 1) / 4, x, high, low, open, close);
/*     */       }
/*     */       else {
/*  66 */         replaceData(this.chart.getDatasets()[((j - 1) / 4)], x, high, low, open, close);
/*     */       }
/*  68 */       j += 4;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean getDataset(int which)
/*     */   {
/*  75 */     double[] xArr = (double[])null;
/*  76 */     double[] hiArr = (double[])null;
/*  77 */     double[] loArr = (double[])null;
/*  78 */     double[] openArr = (double[])null;
/*  79 */     double[] closeArr = (double[])null;
/*     */ 
/*  83 */     Dataset d = this.getter.getDataset(this.chart, which);
/*  84 */     if (d != null) {
/*  85 */       this.chart.addDataset(d);
/*  86 */       getDatasetPropertiesFromParameters(which, d);
/*  87 */       return true;
/*     */     }
/*     */ 
/*  90 */     if (this.getter.getDataProvider() != null) {
/*  91 */       if (this.dataProvider == null) {
/*  92 */         this.dataProvider = this.getter.getDataProvider().getDatasets();
/*     */       }
/*     */ 
/*  95 */       if (this.dataProvider.hasNext()) {
/*  96 */         Object o = this.dataProvider.next();
/*  97 */         if ((o instanceof Dataset)) {
/*  98 */           this.chart.addDataset((Dataset)o);
/*  99 */           getDatasetPropertiesFromParameters(which, (Dataset)o);
/* 100 */           return true;
/*     */         }
/*     */       }
/* 103 */       return false;
/*     */     }
/*     */ 
/* 107 */     String str = getParameter("dataset" + which + "xValues");
/* 108 */     if (str != null) {
/* 109 */       xArr = getVals(str);
/*     */     }
/*     */ 
/* 112 */     str = getParameter("dataset" + which + "dateValues");
/* 113 */     if (str != null) {
/* 114 */       xArr = getDateVals(str);
/*     */     }
/*     */ 
/* 117 */     str = getParameter("dataset" + which + "highValues");
/* 118 */     if (str != null) {
/* 119 */       hiArr = getVals(str);
/*     */     }
/*     */ 
/* 122 */     str = getParameter("dataset" + which + "lowValues");
/* 123 */     if (str != null) {
/* 124 */       loArr = getVals(str);
/*     */     }
/*     */ 
/* 127 */     str = getParameter("dataset" + which + "openValues");
/* 128 */     if (str != null) {
/* 129 */       openArr = getVals(str);
/*     */     }
/*     */ 
/* 132 */     str = getParameter("dataset" + which + "closeValues");
/* 133 */     if (str != null) {
/* 134 */       closeArr = getVals(str);
/*     */     }
/*     */ 
/* 137 */     str = getParameter("dataset" + which + "yValues");
/* 138 */     if (str != null) {
/* 139 */       hiArr = getVals(str);
/*     */     }
/*     */ 
/* 142 */     str = getParameter("dataset" + which + "y2Values");
/* 143 */     if (str != null) {
/* 144 */       loArr = getVals(str);
/*     */     }
/*     */ 
/* 147 */     str = getParameter("dataset" + which + "y3Values");
/* 148 */     if (str != null) {
/* 149 */       openArr = getVals(str);
/*     */     }
/*     */ 
/* 152 */     str = getParameter("dataset" + which + "y4Values");
/* 153 */     if (str != null) {
/* 154 */       closeArr = getVals(str);
/*     */     }
/*     */ 
/* 157 */     return getDatasetParameters(which, xArr, hiArr, loArr, openArr, closeArr);
/*     */   }
/*     */ 
/*     */   public boolean getDatasetParameters(int which, double[] xArr, double[] hiArr, double[] loArr, double[] openArr, double[] closeArr)
/*     */   {
/* 169 */     if ((xArr == null) || (hiArr == null) || (loArr == null) || (openArr == null) || (closeArr == null))
/*     */     {
/* 171 */       return false;
/*     */     }
/*     */ 
/* 179 */     String str = getParameter("dataset" + which + "Name");
/*     */     String setName;
/*     */     String setName;
/* 180 */     if (str != null)
/* 181 */       setName = str;
/*     */     else {
/* 183 */       setName = new String("dataset" + which);
/*     */     }
/* 185 */     ((CandlestickChart)this.chart).addDataset(setName, xArr, hiArr, loArr, openArr, closeArr);
/*     */ 
/* 187 */     getDatasetPropertiesFromParameters(which, this.chart.getDatasets()[which]);
/* 188 */     return true;
/*     */   }
/*     */ 
/*     */   protected void replaceData(Dataset d, double[] x, double[] high, double[] low, double[] open, double[] close)
/*     */   {
/* 193 */     ArrayList data = d.getData();
/*     */ 
/* 195 */     d.replaceXData(x);
/* 196 */     d.replaceYData(high);
/* 197 */     d.replaceY2Data(low);
/* 198 */     d.replaceY3Data(open);
/*     */ 
/* 200 */     int count = data.size();
/* 201 */     if (close.length > count) {
/* 202 */       for (i = 0; i < count; i++) {
/* 203 */         ((CandlestickDatum)data.get(i)).setClose(close[i]);
/*     */       }
/* 205 */       for (i = count; i < close.length; i++)
/* 206 */         data.add(new CandlestickDatum(0.0D, 0.0D, 0.0D, 0.0D, close[i], this.chart.getGlobals()));
/*     */     }
/*     */     else {
/* 209 */       while (data.size() > close.length)
/*     */       {
/*     */         int i;
/* 210 */         data.remove(data.size() - 1);
/*     */       }
/*     */     }
/* 212 */     for (int i = 0; i < close.length; i++)
/* 213 */       ((CandlestickDatum)data.get(i)).setClose(close[i]);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.applet.CandlestickDateReader
 * JD-Core Version:    0.6.2
 */