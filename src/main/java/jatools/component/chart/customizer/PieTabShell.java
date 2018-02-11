/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.PanelLabelMap;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import javax.swing.JTabbedPane;
/*    */ 
/*    */ public class PieTabShell extends Tabs
/*    */ {
/*    */   private RegularPanel ip;
/*    */   private LegendPanel lp;
/*    */   private PieOption bo;
/*    */ 
/*    */   protected void initTabbed()
/*    */   {
/* 25 */     this.ip = new RegularPanel();
/* 26 */     this.ip.setChart(this.chart);
/* 27 */     this.ip.setObject(this.chart);
/* 28 */     this.ip.addChangeListener(this);
/* 29 */     this.lp = new LegendPanel();
/* 30 */     this.lp.setChart(this.chart);
/* 31 */     this.lp.setObject(this.chart.getLegend());
/* 32 */     this.lp.addChangeListener(this);
/* 33 */     this.bo = new PieOption();
/* 34 */     this.bo.setChart(this.chart);
/* 35 */     this.bo.setObject(null);
/* 36 */     this.bo.addChangeListener(this);
/* 37 */     this.tp.add(this.ip, "常规");
/* 38 */     this.tp.add(this.lp, "图例");
/* 39 */     this.tp.add(this.bo, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ 
/*    */   protected void refershTabContent() {
/* 43 */     this.tp.remove(this.bo);
/* 44 */     this.bo = new PieOption();
/* 45 */     this.bo.setChart(this.chart);
/* 46 */     this.bo.setObject(null);
/* 47 */     this.bo.addChangeListener(this);
/* 48 */     this.tp.add(this.bo, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.PieTabShell
 * JD-Core Version:    0.6.2
 */