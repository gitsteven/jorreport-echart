/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.PanelLabelMap;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import javax.swing.JTabbedPane;
/*    */ 
/*    */ public class SectorTabShell extends Tabs
/*    */ {
/*    */   private RegularPanel ip;
/*    */   private LegendPanel lp;
/*    */   private SectorMapOption smo;
/*    */ 
/*    */   protected void initTabbed()
/*    */   {
/* 25 */     this.ip = new RegularPanel();
/* 26 */     this.ip.setChart(this.chart);
/* 27 */     this.ip.setObject(this.chart);
/* 28 */     this.ip.addChangeListener(this);
/*    */ 
/* 30 */     this.lp = new LegendPanel();
/* 31 */     this.lp.setChart(this.chart);
/* 32 */     this.lp.setObject(this.chart.getLegend());
/* 33 */     this.lp.addChangeListener(this);
/* 34 */     this.smo = new SectorMapOption();
/* 35 */     this.smo.setChart(this.chart);
/* 36 */     this.smo.setObject(null);
/* 37 */     this.smo.addChangeListener(this);
/* 38 */     this.tp.add(this.ip, "常规");
/* 39 */     this.tp.add(this.lp, "图例");
/* 40 */     this.tp.add(this.smo, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ 
/*    */   protected void refershTabContent() {
/* 44 */     this.tp.remove(this.smo);
/* 45 */     this.smo = new SectorMapOption();
/* 46 */     this.smo.setChart(this.chart);
/* 47 */     this.smo.setObject(null);
/* 48 */     this.smo.addChangeListener(this);
/* 49 */     this.tp.add(this.smo, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.SectorTabShell
 * JD-Core Version:    0.6.2
 */