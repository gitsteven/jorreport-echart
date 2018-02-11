/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.PanelLabelMap;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import javax.swing.JTabbedPane;
/*    */ 
/*    */ public class BubbleTabShell extends Tabs
/*    */ {
/*    */   private RegularPanel ip;
/*    */   private AxisPanel yaxis;
/*    */   private AxisPanel xaxis;
/*    */   private LegendPanel lp;
/*    */   private BubbleOption bo;
/*    */ 
/*    */   protected void initTabbed()
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
/* 45 */     this.bo = new BubbleOption();
/* 46 */     this.bo.setChart(this.chart);
/* 47 */     this.bo.setObject(null);
/* 48 */     this.bo.addChangeListener(this);
/* 49 */     this.tp.add(this.ip, "常规");
/* 50 */     this.tp.add(this.xaxis, "X轴");
/* 51 */     this.tp.add(this.yaxis, "Y轴");
/* 52 */     this.tp.add(this.lp, "图例");
/* 53 */     this.tp.add(this.bo, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ 
/*    */   protected void refershTabContent() {
/* 57 */     this.tp.remove(this.bo);
/* 58 */     this.bo = new BubbleOption();
/* 59 */     this.bo.setChart(this.chart);
/* 60 */     this.bo.setObject(null);
/* 61 */     this.bo.addChangeListener(this);
/* 62 */     this.tp.add(this.bo, PanelLabelMap.getLabel(this.chart) + "选项");
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.BubbleTabShell
 * JD-Core Version:    0.6.2
 */