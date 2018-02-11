/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import jatools.component.chart.chart.Datum;
/*    */ import jatools.component.chart.chart.DiscontinuousLine;
/*    */ import jatools.component.chart.chart.LineChart;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class disLineApp extends Bean
/*    */ {
/*    */   public disLineApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public disLineApp(Properties defaultProperties)
/*    */   {
/* 26 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 32 */     LineChart l = (LineChart)this.chart;
/*    */ 
/* 34 */     String str = getParameter("plotLinesOn");
/* 35 */     if (str != null)
/* 36 */       l.setLineVisible(true);
/* 37 */     str = getParameter("plotLinesOff");
/* 38 */     if (str != null)
/* 39 */       l.setLineVisible(false); 
/*    */   }
/*    */ 
/* 42 */   public void init() { initLocale();
/* 43 */     this.chart = new LineChart("My Chart", this.userLocale);
/* 44 */     this.chart.setDataRepresentation(new DiscontinuousLine());
/* 45 */     getOptions();
/*    */ 
/* 47 */     Dataset[] d = this.chart.getDatasets();
/* 48 */     for (int i = 0; i < d.length; i++)
/* 49 */       if (d[i] != null)
/* 50 */         for (int j = 0; j < d[i].getData().size(); j++) {
/* 51 */           Datum dat = d[i].getDataElementAt(j);
/* 52 */           if ((dat.getX() == (-1.0D / 0.0D)) || (dat.getY() == (-1.0D / 0.0D)))
/* 53 */             dat.setLabel("D");
/*    */         }
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.disLineApp
 * JD-Core Version:    0.6.2
 */