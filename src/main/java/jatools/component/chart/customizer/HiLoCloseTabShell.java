/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.CommonFinal;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import javax.swing.JTabbedPane;
/*    */ 
/*    */ public class HiLoCloseTabShell extends Tabs
/*    */ {
/*    */   private RegularPanel ip;
/*    */   private AxisPanel yaxis;
/*    */   private AxisPanel xaxis;
/*    */   private LegendPanel lp;
/*    */   private LineDatasetPanel bdp;
/*    */ 
/*    */   protected void initTabbed()
/*    */   {
/* 22 */     GridBagLayout gbl = new GridBagLayout();
/* 23 */     GridBagConstraints gbc = new GridBagConstraints();
/* 24 */     gbc.insets = CommonFinal.INSETS;
/* 25 */     gbc.fill = 2;
/*    */ 
/* 27 */     this.ip = new RegularPanel();
/* 28 */     this.ip.setChart(this.chart);
/* 29 */     this.ip.setObject(this.chart);
/* 30 */     this.ip.addChangeListener(this);
/* 31 */     this.yaxis = new AxisPanel();
/* 32 */     this.yaxis.setChart(this.chart);
/* 33 */     this.yaxis.setObject(this.chart.getYAxis());
/* 34 */     this.yaxis.addChangeListener(this);
/* 35 */     this.xaxis = new AxisPanel();
/* 36 */     this.xaxis.setChart(this.chart);
/* 37 */     this.xaxis.setObject(this.chart.getXAxis());
/* 38 */     this.xaxis.addChangeListener(this);
/* 39 */     this.lp = new LegendPanel();
/* 40 */     this.lp.setChart(this.chart);
/* 41 */     this.lp.setObject(this.chart.getLegend());
/* 42 */     this.lp.addChangeListener(this);
/* 43 */     this.bdp = new LineDatasetPanel();
/* 44 */     this.bdp.setChart(this.chart);
/* 45 */     this.bdp.setObject(this.chart.getDatasets()[0]);
/* 46 */     this.bdp.setVals();
/* 47 */     this.bdp.addChangeListener(this);
/* 48 */     this.bdp.setBorder(CommonFinal.EMPTY_BORDER);
/*    */ 
/* 62 */     this.tp.add(this.ip, "常规");
/* 63 */     this.tp.add(this.xaxis, "X轴");
/* 64 */     this.tp.add(this.yaxis, "Y轴");
/* 65 */     this.tp.add(this.lp, "图例");
/* 66 */     this.tp.add(this.bdp, "数据集");
/*    */   }
/*    */ 
/*    */   protected void refershTabContent() {
/* 70 */     this.tp.remove(this.bdp);
/* 71 */     this.bdp = new LineDatasetPanel();
/* 72 */     this.bdp.setChart(this.chart);
/* 73 */     this.bdp.setObject(this.chart.getDatasets()[0]);
/* 74 */     this.bdp.setVals();
/* 75 */     this.bdp.addChangeListener(this);
/* 76 */     this.bdp.setBorder(CommonFinal.EMPTY_BORDER);
/* 77 */     this.tp.add(this.bdp, "数据集");
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.HiLoCloseTabShell
 * JD-Core Version:    0.6.2
 */