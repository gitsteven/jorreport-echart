/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.PanelLabelMap;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import javax.swing.JTabbedPane;
/*    */ import javax.swing.event.ChangeListener;
/*    */ 
/*    */ public class BarAreaTabShell extends Tabs
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
/* 34 */     this.ip = new RegularPanel();
/* 35 */     this.ip.setChart(this.chart);
/* 36 */     this.ip.setObject(this.chart);
/* 37 */     this.ip.addChangeListener(this);
/* 38 */     this.yaxis = new AxisPanel();
/* 39 */     this.yaxis.setChart(this.chart);
/* 40 */     this.yaxis.setObject(this.chart.getYAxis());
/* 41 */     this.yaxis.addChangeListener(this);
/* 42 */     this.xaxis = new AxisPanel();
/* 43 */     this.xaxis.setChart(this.chart);
/* 44 */     this.xaxis.setObject(this.chart.getXAxis());
/* 45 */     this.xaxis.addChangeListener(this);
/* 46 */     this.lp = new LegendPanel();
/* 47 */     this.lp.setChart(this.chart);
/* 48 */     this.lp.setObject(this.chart.getLegend());
/* 49 */     this.lp.addChangeListener(this);
/* 50 */     this.bao = new BarAreaOption();
/* 51 */     this.bao.setChart(this.chart);
/* 52 */     this.bao.setObject(null);
/* 53 */     this.bao.addChangeListener(this);
/* 54 */     this.tp.add(this.ip, "常规");
/* 55 */     this.tp.add(this.xaxis, "X轴");
/* 56 */     this.tp.add(this.yaxis, "Y轴");
/* 57 */     this.tp.add(this.lp, "图例");
/* 58 */     this.tp.add(this.bao, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ 
/*    */   protected void refershTabContent() {
/* 62 */     this.tp.remove(this.bao);
/* 63 */     this.bao = new BarAreaOption();
/* 64 */     this.bao.setChart(this.chart);
/* 65 */     this.bao.setObject(null);
/* 66 */     this.bao.addChangeListener(this);
/* 67 */     this.tp.add(this.bao, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.BarAreaTabShell
 * JD-Core Version:    0.6.2
 */