/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.PanelLabelMap;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import javax.swing.JTabbedPane;
/*    */ 
/*    */ public class BarTabShell extends Tabs
/*    */ {
/*    */   private RegularPanel ip;
/*    */   private AxisPanel xaxis;
/*    */   private AxisPanel yaxis;
/*    */   private LegendPanel lp;
/*    */   private BarOption bo;
/*    */ 
/*    */   protected void initTabbed()
/*    */   {
/* 17 */     this.ip = new RegularPanel();
/* 18 */     this.xaxis = new AxisPanel();
/* 19 */     this.yaxis = new AxisPanel();
/* 20 */     this.lp = new LegendPanel();
/* 21 */     this.bo = new BarOption();
/*    */ 
/* 23 */     this.ip.setChart(this.chart);
/* 24 */     this.ip.setObject(null);
/* 25 */     this.xaxis.setChart(this.chart);
/* 26 */     this.xaxis.setObject(this.chart.getXAxis());
/* 27 */     this.yaxis.setChart(this.chart);
/* 28 */     this.yaxis.setObject(this.chart.getYAxis());
/* 29 */     this.lp.setChart(this.chart);
/* 30 */     this.lp.setObject(this.chart.getLegend());
/* 31 */     this.bo.setChart(this.chart);
/* 32 */     this.bo.setObject(null);
/*    */ 
/* 34 */     this.ip.addChangeListener(this);
/* 35 */     this.xaxis.addChangeListener(this);
/* 36 */     this.yaxis.addChangeListener(this);
/* 37 */     this.lp.addChangeListener(this);
/* 38 */     this.bo.addChangeListener(this);
/*    */ 
/* 40 */     this.tp.add(this.ip, "常规");
/* 41 */     this.tp.add(this.xaxis, "X轴");
/* 42 */     this.tp.add(this.yaxis, "Y轴");
/* 43 */     this.tp.add(this.lp, "图例");
/* 44 */     this.tp.add(this.bo, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ 
/*    */   protected void refershTabContent() {
/* 48 */     this.tp.remove(this.bo);
/* 49 */     this.bo = new BarOption();
/* 50 */     this.bo.setChart(this.chart);
/* 51 */     this.bo.setObject(null);
/* 52 */     this.bo.addChangeListener(this);
/* 53 */     this.tp.add(this.bo, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.BarTabShell
 * JD-Core Version:    0.6.2
 */