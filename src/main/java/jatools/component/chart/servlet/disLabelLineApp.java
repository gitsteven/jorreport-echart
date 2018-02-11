/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import jatools.component.chart.chart.Datum;
/*    */ import jatools.component.chart.chart.DiscontinuousLine;
/*    */ import jatools.component.chart.chart.LabelLineChart;
/*    */ import jatools.component.chart.chart.LineChart;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class disLabelLineApp extends Bean
/*    */ {
/*    */   public disLabelLineApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public disLabelLineApp(Properties defaultProperties)
/*    */   {
/* 25 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 31 */     LineChart l = (LineChart)this.chart;
/*    */ 
/* 33 */     String str = getParameter("plotLinesOn");
/* 34 */     if (str != null)
/* 35 */       l.setLineVisible(true);
/* 36 */     str = getParameter("plotLinesOff");
/* 37 */     if (str != null)
/* 38 */       l.setLineVisible(false); 
/*    */   }
/*    */ 
/* 41 */   public void init() { initLocale();
/* 42 */     this.chart = new LabelLineChart("My Chart", this.userLocale);
/* 43 */     this.chart.setDataRepresentation(new DiscontinuousLine());
/* 44 */     getOptions();
/*    */ 
/* 46 */     Dataset[] d = this.chart.getDatasets();
/* 47 */     for (int i = 0; i < d.length; i++)
/* 48 */       if (d[i] != null)
/* 49 */         for (int j = 0; j < d[i].getData().size(); j++) {
/* 50 */           Datum dat = d[i].getDataElementAt(j);
/* 51 */           if ((dat.getX() == (-1.0D / 0.0D)) || (dat.getY() == (-1.0D / 0.0D)))
/* 52 */             dat.setLabel("D");
/*    */         }
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.disLabelLineApp
 * JD-Core Version:    0.6.2
 */