/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.PanelLabelMap;
/*    */ import jatools.component.chart.chart.TwinAxisInterface;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import javax.swing.JTabbedPane;
/*    */ 
/*    */ public class TwinAxisBarAreaTabShell extends Tabs
/*    */ {
/*    */   private RegularPanel ip;
/*    */   private AxisPanel leftaxis;
/*    */   private AxisPanel rightaxis;
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
/* 38 */     this.leftaxis = new AxisPanel();
/* 39 */     this.leftaxis.setChart(this.chart);
/* 40 */     this.leftaxis.setObject(this.chart.getYAxis());
/* 41 */     this.leftaxis.addChangeListener(this);
/* 42 */     this.rightaxis = new AxisPanel();
/* 43 */     this.rightaxis.setChart(this.chart);
/* 44 */     this.rightaxis.setObject(((TwinAxisInterface)this.chart).getAuxAxis());
/* 45 */     this.rightaxis.addChangeListener(this);
/* 46 */     this.xaxis = new AxisPanel();
/* 47 */     this.xaxis.setChart(this.chart);
/* 48 */     this.xaxis.setObject(this.chart.getXAxis());
/* 49 */     this.xaxis.addChangeListener(this);
/* 50 */     this.lp = new LegendPanel();
/* 51 */     this.lp.setChart(this.chart);
/* 52 */     this.lp.setObject(this.chart.getLegend());
/* 53 */     this.lp.addChangeListener(this);
/* 54 */     this.bao = new BarAreaOption();
/* 55 */     this.bao.setChart(this.chart);
/* 56 */     this.bao.setObject(null);
/* 57 */     this.bao.addChangeListener(this);
/* 58 */     this.tp.add(this.ip, "常规");
/* 59 */     this.tp.add(this.leftaxis, "左轴");
/* 60 */     this.tp.add(this.rightaxis, "右轴");
/* 61 */     this.tp.add(this.xaxis, "X轴");
/* 62 */     this.tp.add(this.lp, "图例");
/* 63 */     this.tp.add(this.bao, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ 
/*    */   protected void refershTabContent() {
/* 67 */     this.tp.remove(this.bao);
/* 68 */     this.bao = new BarAreaOption();
/* 69 */     this.bao.setChart(this.chart);
/* 70 */     this.bao.setObject(null);
/* 71 */     this.bao.addChangeListener(this);
/* 72 */     this.tp.add(this.bao, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.TwinAxisBarAreaTabShell
 * JD-Core Version:    0.6.2
 */