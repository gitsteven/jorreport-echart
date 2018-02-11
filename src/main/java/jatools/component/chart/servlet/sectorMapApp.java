/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ParameterParser;
/*    */ import jatools.component.chart.chart.SectorMap;
/*    */ import jatools.component.chart.chart.SectorMapChart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class sectorMapApp extends Bean
/*    */ {
/*    */   public sectorMapApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public sectorMapApp(Properties defaultProperties)
/*    */   {
/* 20 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 26 */     SectorMapChart s = (SectorMapChart)this.chart;
/*    */ 
/* 28 */     String str = getParameter("individualColors");
/* 29 */     if (str != null)
/* 30 */       s.setIndividualColors(Boolean.valueOf(str).booleanValue());
/* 31 */     str = getParameter("labelsOn");
/* 32 */     if (str != null) {
/* 33 */       s.getSectorMap().setLabelsOn(Boolean.valueOf(str).booleanValue());
/*    */     }
/* 35 */     str = getParameter("baseColor");
/* 36 */     if (str != null)
/* 37 */       s.getSectorMap().setBaseColor(this.parser.getColor(str));
/* 38 */     str = getParameter("baseValue");
/* 39 */     if (str != null)
/* 40 */       s.getSectorMap().setBaseValue(Double.valueOf(str).doubleValue());
/* 41 */     str = getParameter("sectorSecondaryColor");
/* 42 */     if (str != null)
/* 43 */       s.getSectorMap().setSecondaryColor(this.parser.getColor(str));
/* 44 */     str = getParameter("gradientColoring");
/* 45 */     if (str != null) {
/* 46 */       s.getSectorMap().setUseGradientColoring(Boolean.valueOf(
/* 47 */         str).booleanValue());
/*    */     }
/* 49 */     str = getParameter("labelStyle");
/* 50 */     if (str != null) {
/* 51 */       s.getSectorMap().setLabelStyle(Integer.parseInt(str));
/*    */     }
/* 53 */     str = getParameter("outlineColor");
/* 54 */     if (str != null)
/* 55 */       this.parser.activateOutlineFills(this.parser.getColor(str), 
/* 56 */         s.getIndividualColors());
/*    */   }
/*    */ 
/*    */   public void init() {
/* 60 */     initLocale();
/* 61 */     this.chart = new SectorMapChart("My Chart", this.userLocale);
/* 62 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.sectorMapApp
 * JD-Core Version:    0.6.2
 */