/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.PanelLabelMap;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import javax.swing.JTabbedPane;
/*    */ import javax.swing.event.ChangeListener;
/*    */ 
/*    */ public class BarAreaLineTabShell extends Tabs
/*    */   implements ChangeListener
/*    */ {
/*    */   private RegularPanel ip;
/*    */   private AxisPanel yaxis;
/*    */   private AxisPanel xaxis;
/*    */   private LegendPanel lp;
/*    */   private BarAreaOption bao;
/*    */ 
/*    */   protected void initTabbed()
/*    */   {
/* 22 */     this.ip = new RegularPanel();
/* 23 */     this.ip.setChart(this.chart);
/* 24 */     this.ip.setObject(this.chart);
/* 25 */     this.ip.addChangeListener(this);
/* 26 */     this.yaxis = new AxisPanel();
/* 27 */     this.yaxis.setChart(this.chart);
/* 28 */     this.yaxis.setObject(this.chart.getYAxis());
/* 29 */     this.yaxis.addChangeListener(this);
/* 30 */     this.xaxis = new AxisPanel();
/* 31 */     this.xaxis.setChart(this.chart);
/* 32 */     this.xaxis.setObject(this.chart.getXAxis());
/* 33 */     this.xaxis.addChangeListener(this);
/* 34 */     this.lp = new LegendPanel();
/* 35 */     this.lp.setChart(this.chart);
/* 36 */     this.lp.setObject(this.chart.getLegend());
/* 37 */     this.lp.addChangeListener(this);
/* 38 */     this.bao = new BarAreaLineOption();
/* 39 */     this.bao.setChart(this.chart);
/* 40 */     this.bao.setObject(null);
/* 41 */     this.bao.addChangeListener(this);
/* 42 */     this.tp.add(this.ip, "常规");
/* 43 */     this.tp.add(this.xaxis, "X轴");
/* 44 */     this.tp.add(this.yaxis, "Y轴");
/* 45 */     this.tp.add(this.lp, "图例");
/* 46 */     this.tp.add(this.bao, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ 
/*    */   protected void refershTabContent() {
/* 50 */     this.tp.remove(this.bao);
/* 51 */     this.bao = new BarAreaOption();
/* 52 */     this.bao.setChart(this.chart);
/* 53 */     this.bao.setObject(null);
/* 54 */     this.bao.addChangeListener(this);
/* 55 */     this.tp.add(this.bao, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.BarAreaLineTabShell
 * JD-Core Version:    0.6.2
 */