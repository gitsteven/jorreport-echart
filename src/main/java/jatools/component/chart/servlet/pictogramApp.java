/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ParameterParser;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import jatools.component.chart.chart.Datum;
/*    */ import jatools.component.chart.chart.Gc;
/*    */ import jatools.component.chart.chart.Pictogram;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class pictogramApp extends indColumnApp
/*    */ {
/*    */   public pictogramApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public pictogramApp(Properties properties)
/*    */   {
/* 25 */     this.properties = properties;
/*    */   }
/*    */   public void getMyOptions() {
/* 28 */     this.chart.setDataRepresentation(new Pictogram());
/* 29 */     super.getMyOptions();
/* 30 */     for (int i = 0; i < _Chart.MAX_DATASETS; i++) {
/* 31 */       String str = getParameter("dataset" + i + "Images");
/* 32 */       if (str != null) {
/* 33 */         String[] images = this.parser.getLabels(str);
/* 34 */         for (int j = 0; j < images.length; j++)
/*    */           try {
/* 36 */             this.chart.getDatasets()[i].getDataElementAt(j).getGc().setImage(this.parser.makeURLImage(images[j]));
/*    */           } catch (Exception ignored) {
/* 38 */             System.out.println(ignored.getMessage());
/*    */           }
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.pictogramApp
 * JD-Core Version:    0.6.2
 */