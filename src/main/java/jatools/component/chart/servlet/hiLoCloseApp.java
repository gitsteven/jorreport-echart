/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.HiLoCloseDateReader;
/*    */ import jatools.component.chart.chart.DateAxis;
/*    */ import jatools.component.chart.chart.HiLoCloseChart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class hiLoCloseApp extends DateApp
/*    */ {
/*    */   public hiLoCloseApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public hiLoCloseApp(Properties defaultProperties)
/*    */   {
/* 23 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */   public void getMyOptions() {
/* 26 */     HiLoCloseChart l = (HiLoCloseChart)this.chart;
/* 27 */     initDateAxis((DateAxis)l.getXAxis());
/*    */   }
/*    */   public void init() {
/* 30 */     initLocale();
/* 31 */     this.chart = new HiLoCloseChart(this.userLocale);
/* 32 */     this.parser = new HiLoCloseDateReader(this.chart, this);
/* 33 */     initDateStreamReader();
/* 34 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.hiLoCloseApp
 * JD-Core Version:    0.6.2
 */