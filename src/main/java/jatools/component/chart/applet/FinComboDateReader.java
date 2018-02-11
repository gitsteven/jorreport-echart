/*     */ package jatools.component.chart.applet;
/*     */ 
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.chart.FinComboChart;
/*     */ import java.util.ArrayList;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class FinComboDateReader extends DateStreamReader
/*     */ {
/*     */   public FinComboDateReader(ChartInterface c, GetParam g)
/*     */   {
/*  27 */     super(c, g);
/*     */   }
/*     */ 
/*     */   protected void convertDataBlockToChartData(boolean tf)
/*     */   {
/*  34 */     int numRows = this.dataBlockStrings.size();
/*  35 */     double[] x = new double[numRows];
/*  36 */     double[] y = new double[numRows];
/*  37 */     double[] y2 = new double[numRows];
/*  38 */     double[] y3 = new double[numRows];
/*  39 */     for (int i = 0; i < numRows; i++) {
/*  40 */       x[i] = getTimeElement(i);
/*     */     }
/*     */ 
/*  43 */     int j = 0;
/*  44 */     while (getYElements(numRows, j, y, y2, y3)) {
/*  45 */       getDatasetParameters(j, x, y, y2, y3);
/*  46 */       j++;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void getMyDatasets(String s)
/*     */   {
/*  52 */     if ((this.myInputStream = openURL(s)) == null) return;
/*  53 */     this.dataBlockStrings = new ArrayList();
/*  54 */     readDataBlock();
/*  55 */     convertDataBlockToChartData(true);
/*  56 */     if (!closeURL(this.myInputStream));
/*     */   }
/*     */ 
/*     */   protected boolean getYElements(int numRows, int setNumber, double[] y1, double[] y2, double[] y3)
/*     */   {
/*  65 */     FinComboChart c = (FinComboChart)this.chart;
/*     */     try {
/*  67 */       if ((c.dataAllocation[setNumber] == 0) || 
/*  68 */         (c.dataAllocation[setNumber] == 1)) {
/*  69 */         for (int i = 0; i < numRows; i++) {
/*  70 */           y1[i] = getDoubleElement(i);
/*     */         }
/*     */       }
/*  73 */       else if (c.dataAllocation[setNumber] == 2) {
/*  74 */         for (int i = 0; i < numRows; i++) {
/*  75 */           y3[i] = getDoubleElement(i);
/*     */         }
/*  77 */         for (i = 0; i < numRows; i++) {
/*  78 */           y2[i] = getDoubleElement(i);
/*     */         }
/*  80 */         for (i = 0; i < numRows; i++)
/*  81 */           y1[i] = getDoubleElement(i);
/*     */       }
/*     */       else
/*     */       {
/*  85 */         return false;
/*     */       }
/*     */     } catch (NoSuchElementException e) {
/*  88 */       return false;
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException a) {
/*  91 */       return false;
/*     */     }
/*     */     int i;
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */   protected void readDataBlock()
/*     */   {
/* 100 */     String s = getLineFromURL(this.myInputStream);
/* 101 */     while (s != null) {
/* 102 */       if (s.length() > 1)
/* 103 */         this.dataBlockStrings.add(new StringTokenizer(s, ","));
/* 104 */       s = getLineFromURL(this.myInputStream);
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.applet.FinComboDateReader
 * JD-Core Version:    0.6.2
 */