/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.PanelLabelMap;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import javax.swing.JTabbedPane;
/*    */ 
/*    */ public class BarLineTabShell extends Tabs
/*    */ {
/*    */   private RegularPanel ip;
/*    */   private AxisPanel yaxis;
/*    */   private AxisPanel xaxis;
/*    */   private LegendPanel lp;
/*    */   private BarLineOption blo;
/*    */ 
/*    */   protected void initTabbed()
/*    */   {
/* 31 */     this.ip = new RegularPanel();
/* 32 */     this.ip.setChart(this.chart);
/* 33 */     this.ip.setObject(this.chart);
/* 34 */     this.ip.addChangeListener(this);
/* 35 */     this.yaxis = new AxisPanel();
/* 36 */     this.yaxis.setChart(this.chart);
/* 37 */     this.yaxis.setObject(this.chart.getYAxis());
/* 38 */     this.yaxis.addChangeListener(this);
/* 39 */     this.xaxis = new AxisPanel();
/* 40 */     this.xaxis.setChart(this.chart);
/* 41 */     this.xaxis.setObject(this.chart.getXAxis());
/* 42 */     this.xaxis.addChangeListener(this);
/* 43 */     this.lp = new LegendPanel();
/* 44 */     this.lp.setChart(this.chart);
/* 45 */     this.lp.setObject(this.chart.getLegend());
/* 46 */     this.lp.addChangeListener(this);
/* 47 */     this.blo = new BarLineOption();
/* 48 */     this.blo.setChart(this.chart);
/* 49 */     this.blo.setObject(null);
/* 50 */     this.blo.addChangeListener(this);
/* 51 */     this.tp.add(this.ip, "常规");
/* 52 */     this.tp.add(this.xaxis, "X轴");
/* 53 */     this.tp.add(this.yaxis, "Y轴");
/* 54 */     this.tp.add(this.lp, "图例");
/* 55 */     this.tp.add(this.blo, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ 
/*    */   protected void refershTabContent() {
/* 59 */     this.tp.remove(this.blo);
/* 60 */     this.blo = new BarLineOption();
/* 61 */     this.blo.setChart(this.chart);
/* 62 */     this.blo.setObject(null);
/* 63 */     this.blo.addChangeListener(this);
/* 64 */     this.tp.add(this.blo, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.BarLineTabShell
 * JD-Core Version:    0.6.2
 */