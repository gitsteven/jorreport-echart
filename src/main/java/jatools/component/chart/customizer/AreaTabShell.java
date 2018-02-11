/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.PanelLabelMap;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import javax.swing.JTabbedPane;
/*    */ 
/*    */ public class AreaTabShell extends Tabs
/*    */ {
/*    */   RegularPanel ip;
/*    */   AxisPanel yaxis;
/*    */   AxisPanel xaxis;
/*    */   LegendPanel lp;
/*    */   AreaOption ao;
/*    */ 
/*    */   public void initTabbed()
/*    */   {
/* 29 */     this.ip = new RegularPanel();
/* 30 */     this.ip.setChart(this.chart);
/* 31 */     this.ip.setObject(this.chart);
/* 32 */     this.ip.addChangeListener(this);
/* 33 */     this.yaxis = new AxisPanel();
/* 34 */     this.yaxis.setChart(this.chart);
/* 35 */     this.yaxis.setObject(this.chart.getYAxis());
/* 36 */     this.yaxis.addChangeListener(this);
/* 37 */     this.xaxis = new AxisPanel();
/* 38 */     this.xaxis.setChart(this.chart);
/* 39 */     this.xaxis.setObject(this.chart.getXAxis());
/* 40 */     this.xaxis.addChangeListener(this);
/* 41 */     this.lp = new LegendPanel();
/* 42 */     this.lp.setChart(this.chart);
/* 43 */     this.lp.setObject(this.chart.getLegend());
/* 44 */     this.lp.addChangeListener(this);
/* 45 */     this.ao = new AreaOption();
/* 46 */     this.ao.setChart(this.chart);
/* 47 */     this.ao.setObject(null);
/* 48 */     this.ao.addChangeListener(this);
/*    */ 
/* 51 */     this.tp.add(this.ip, "常规");
/* 52 */     this.tp.add(this.xaxis, "X轴");
/* 53 */     this.tp.add(this.yaxis, "Y轴");
/* 54 */     this.tp.add(this.lp, "图例");
/* 55 */     this.tp.add(this.ao, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ 
/*    */   public void refershTabContent()
/*    */   {
/* 60 */     this.tp.remove(this.ao);
/* 61 */     this.ao = new AreaOption();
/* 62 */     this.ao.setChart(this.chart);
/* 63 */     this.ao.setObject(null);
/* 64 */     this.ao.addChangeListener(this);
/* 65 */     this.tp.add(this.ao, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.AreaTabShell
 * JD-Core Version:    0.6.2
 */