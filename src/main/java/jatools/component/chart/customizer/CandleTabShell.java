/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.CommonFinal;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import javax.swing.JTabbedPane;
/*    */ 
/*    */ public class CandleTabShell extends Tabs
/*    */ {
/*    */   private RegularPanel ip;
/*    */   private AxisPanel yaxis;
/*    */   private AxisPanel xaxis;
/*    */   private LegendPanel lp;
/*    */   private BarDatasetPanel bdp;
/*    */ 
/*    */   protected void initTabbed()
/*    */   {
/* 37 */     GridBagLayout gbl = new GridBagLayout();
/* 38 */     GridBagConstraints gbc = new GridBagConstraints();
/* 39 */     gbc.insets = CommonFinal.INSETS;
/* 40 */     gbc.fill = 2;
/*    */ 
/* 42 */     this.ip = new RegularPanel();
/* 43 */     this.ip.setChart(this.chart);
/* 44 */     this.ip.setObject(this.chart);
/* 45 */     this.ip.addChangeListener(this);
/* 46 */     this.yaxis = new AxisPanel();
/* 47 */     this.yaxis.setChart(this.chart);
/* 48 */     this.yaxis.setObject(this.chart.getYAxis());
/* 49 */     this.yaxis.addChangeListener(this);
/* 50 */     this.xaxis = new AxisPanel();
/* 51 */     this.xaxis.setChart(this.chart);
/* 52 */     this.xaxis.setObject(this.chart.getXAxis());
/* 53 */     this.xaxis.addChangeListener(this);
/* 54 */     this.lp = new LegendPanel();
/* 55 */     this.lp.setChart(this.chart);
/* 56 */     this.lp.setObject(this.chart.getLegend());
/* 57 */     this.lp.addChangeListener(this);
/*    */ 
/* 60 */     this.bdp = new BarDatasetPanel();
/* 61 */     this.bdp.setChart(this.chart);
/* 62 */     this.bdp.setObject(this.chart.getDatasets()[0]);
/* 63 */     this.bdp.setVals();
/* 64 */     this.bdp.addChangeListener(this);
/* 65 */     this.bdp.setBorder(CommonFinal.EMPTY_BORDER);
/*    */ 
/* 75 */     this.tp.add(this.ip, "常规");
/* 76 */     this.tp.add(this.xaxis, "X轴");
/* 77 */     this.tp.add(this.yaxis, "Y轴");
/* 78 */     this.tp.add(this.lp, "图例");
/* 79 */     this.tp.add(this.bdp, "数据集");
/*    */   }
/*    */ 
/*    */   protected void refershTabContent() {
/* 83 */     this.tp.remove(this.bdp);
/* 84 */     this.bdp = new BarDatasetPanel();
/* 85 */     this.bdp.setChart(this.chart);
/* 86 */     this.bdp.setObject(this.chart.getDatasets()[0]);
/* 87 */     this.bdp.setVals();
/* 88 */     this.bdp.addChangeListener(this);
/* 89 */     this.bdp.setBorder(CommonFinal.EMPTY_BORDER);
/*    */ 
/* 99 */     this.tp.add(this.bdp, "数据集");
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.CandleTabShell
 * JD-Core Version:    0.6.2
 */