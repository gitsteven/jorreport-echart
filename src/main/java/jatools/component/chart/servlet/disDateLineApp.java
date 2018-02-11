/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import jatools.component.chart.chart.DateAxis;
/*    */ import jatools.component.chart.chart.DateLineChart;
/*    */ import jatools.component.chart.chart.Datum;
/*    */ import jatools.component.chart.chart.DiscontinuousLine;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class disDateLineApp extends DateApp
/*    */ {
/*    */   public disDateLineApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public disDateLineApp(Properties defaultProperties)
/*    */   {
/* 24 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 31 */     DateLineChart l = (DateLineChart)this.chart;
/* 32 */     initDateAxis((DateAxis)l.getXAxis());
/*    */ 
/* 34 */     String str = getParameter("plotLinesOn");
/* 35 */     if (str != null)
/* 36 */       l.setLineVisible(true);
/* 37 */     str = getParameter("plotLinesOff");
/* 38 */     if (str != null)
/* 39 */       l.setLineVisible(false);
/*    */   }
/*    */ 
/*    */   public void init()
/*    */   {
/* 44 */     initLocale();
/* 45 */     this.chart = new DateLineChart(this.userLocale);
/* 46 */     this.chart.setDataRepresentation(new DiscontinuousLine());
/* 47 */     initDateStreamReader();
/* 48 */     getOptions();
/*    */ 
/* 51 */     Dataset[] d = this.chart.getDatasets();
/* 52 */     for (int i = 0; i < d.length; i++)
/* 53 */       if (d[i] != null)
/* 54 */         for (int j = 0; j < d[i].getData().size(); j++) {
/* 55 */           Datum dat = d[i].getDataElementAt(j);
/* 56 */           if ((dat.getX() == (-1.0D / 0.0D)) || (dat.getY() == (-1.0D / 0.0D)))
/* 57 */             dat.setLabel("D");
/*    */         }
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.disDateLineApp
 * JD-Core Version:    0.6.2
 */