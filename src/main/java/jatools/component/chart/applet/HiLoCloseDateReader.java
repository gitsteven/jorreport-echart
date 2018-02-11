/*    */ package jatools.component.chart.applet;
/*    */ 
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.StringTokenizer;
/*    */ 
/*    */ public class HiLoCloseDateReader extends DateStreamReader
/*    */ {
/*    */   public HiLoCloseDateReader(ChartInterface c, GetParam g)
/*    */   {
/* 25 */     super(c, g);
/*    */   }
/*    */ 
/*    */   protected void convertDataBlockToChartData(boolean firstTime)
/*    */   {
/* 33 */     int numRows = this.dataBlockStrings.size();
/* 34 */     double[] x = new double[numRows];
/* 35 */     double[] high = new double[numRows];
/* 36 */     double[] low = new double[numRows];
/* 37 */     double[] close = new double[numRows];
/* 38 */     for (int i = 0; i < numRows; i++) {
/* 39 */       x[i] = getTimeElement(i);
/*    */     }
/*    */ 
/* 42 */     int relevantColumns = ((StringTokenizer)this.dataBlockStrings.get(0)).countTokens();
/* 43 */     if (relevantColumns < 3) {
/* 44 */       System.out.println("not enough columns: " + relevantColumns);
/* 45 */       return;
/*    */     }
/* 47 */     for (i = 0; i < numRows; i++) {
/* 48 */       high[i] = getDoubleElement(i);
/*    */     }
/* 50 */     for (i = 0; i < numRows; i++)
/* 51 */       low[i] = getDoubleElement(i);
/* 52 */     for (i = 0; i < numRows; i++)
/* 53 */       close[i] = getDoubleElement(i);
/* 54 */     if (firstTime) {
/* 55 */       getDatasetParameters(0, x, close, high, low);
/*    */     } else {
/* 57 */       Dataset saveD = this.chart.getDatasets()[0];
/* 58 */       this.chart.getDatasets()[0] = 
/* 59 */         new Dataset(saveD.getName(), x, close, high, low, 0, this.chart.getGlobals());
/* 60 */       this.chart.getDatasets()[0].setGc(saveD.getGc());
/* 61 */       this.chart.getDatasets()[0].setLabelFont(saveD.getLabelFont());
/* 62 */       this.chart.getDatasets()[0].setLabelColor(saveD.getLabelColor());
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.applet.HiLoCloseDateReader
 * JD-Core Version:    0.6.2
 */