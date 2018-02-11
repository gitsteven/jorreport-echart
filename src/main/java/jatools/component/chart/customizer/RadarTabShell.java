/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.PanelLabelMap;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import javax.swing.JTabbedPane;
/*    */ 
/*    */ public class RadarTabShell extends Tabs
/*    */ {
/*    */   private RegularPanel ip;
/*    */   private AxisPanel xaxis;
/*    */   private LegendPanel lp;
/*    */   private RadarOption ro;
/*    */ 
/*    */   protected void initTabbed()
/*    */   {
/* 28 */     this.ip = new RegularPanel();
/* 29 */     this.ip.setChart(this.chart);
/* 30 */     this.ip.setObject(this.chart);
/* 31 */     this.ip.addChangeListener(this);
/* 32 */     this.xaxis = new AxisPanel();
/* 33 */     this.xaxis.setChart(this.chart);
/* 34 */     this.xaxis.setObject(this.chart.getXAxis());
/* 35 */     this.xaxis.addChangeListener(this);
/* 36 */     this.lp = new LegendPanel();
/* 37 */     this.lp.setChart(this.chart);
/* 38 */     this.lp.setObject(this.chart.getLegend());
/* 39 */     this.lp.addChangeListener(this);
/* 40 */     this.ro = new RadarOption();
/* 41 */     this.ro.setChart(this.chart);
/* 42 */     this.ro.setObject(null);
/* 43 */     this.ro.addChangeListener(this);
/* 44 */     this.tp.add(this.ip, "常规");
/* 45 */     this.tp.add(this.xaxis, "X轴");
/* 46 */     this.tp.add(this.lp, "图例");
/* 47 */     this.tp.add(this.ro, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ 
/*    */   protected void refershTabContent() {
/* 51 */     this.tp.remove(this.ro);
/* 52 */     this.ro = new RadarOption();
/* 53 */     this.ro.setChart(this.chart);
/* 54 */     this.ro.setObject(null);
/* 55 */     this.ro.addChangeListener(this);
/* 56 */     this.tp.add(this.ro, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.RadarTabShell
 * JD-Core Version:    0.6.2
 */