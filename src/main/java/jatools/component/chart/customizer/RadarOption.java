/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Polar;
/*     */ import jatools.component.chart.chart.PolarAxis;
/*     */ import jatools.component.chart.chart.PolarChart;
/*     */ import jatools.component.chart.chart._Chart;
/*     */ import jatools.component.chart.component.AbstractComponent;
/*     */ import jatools.component.chart.component.CheckComponent;
/*     */ import jatools.component.chart.component.FormatComponent;
/*     */ import jatools.component.chart.component.StringComponent;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import javax.swing.Box;
/*     */ 
/*     */ public class RadarOption extends Dialog
/*     */ {
/*     */   CheckComponent ckshowline;
/*     */   CheckComponent ckshowlabel;
/*     */   StringComponent scaxisnum;
/*     */   StringComponent sclabel;
/*     */   private LineDatasetPanel datasetPanel;
/*     */   private Polar polar;
/*     */   private CheckComponent manualSpoking;
/*     */   private AbstractComponent dataFormat;
/*     */ 
/*     */   public void initCustomizer()
/*     */   {
/*  40 */     GridBagLayout gbl = new GridBagLayout();
/*  41 */     setLayout(gbl);
/*  42 */     setBorder(CommonFinal.EMPTY_BORDER);
/*  43 */     GridBagConstraints gbc = new GridBagConstraints();
/*  44 */     gbc.insets = CommonFinal.INSETS;
/*  45 */     gbc.fill = 2;
/*     */ 
/*  47 */     this.ckshowline = new CheckComponent("显示线", false);
/*  48 */     this.ckshowline.addChangeListener(this);
/*  49 */     this.ckshowlabel = new CheckComponent("显示标签", false);
/*  50 */     this.ckshowlabel.addChangeListener(this);
/*     */ 
/*  52 */     this.manualSpoking = new CheckComponent("手动调整轴数量", false);
/*  53 */     this.manualSpoking.addChangeListener(this);
/*  54 */     this.scaxisnum = new StringComponent("轴数量", null);
/*  55 */     this.scaxisnum.addChangeListener(this);
/*     */ 
/*  58 */     this.datasetPanel = new LineDatasetPanel();
/*  59 */     this.datasetPanel.setChart(this.chart);
/*  60 */     this.datasetPanel.setObject(this.chart.getDatasets()[0]);
/*  61 */     this.datasetPanel.addChangeListener(this);
/*     */ 
/*  64 */     this.dataFormat = FormatComponent.getDecimalFormatInstance("数据样式");
/*  65 */     this.dataFormat.addChangeListener(this);
/*     */ 
/*  68 */     add(this.ckshowline, gbc);
/*  69 */     gbc.gridwidth = 0;
/*  70 */     add(Box.createGlue(), gbc);
/*     */ 
/*  73 */     gbc.gridwidth = 1;
/*  74 */     add(this.ckshowlabel, gbc);
/*  75 */     gbc.gridwidth = 0;
/*  76 */     add(Box.createGlue(), gbc);
/*     */ 
/*  78 */     gbc.gridwidth = 1;
/*  79 */     add(this.manualSpoking, gbc);
/*  80 */     gbc.gridwidth = 0;
/*  81 */     add(this.scaxisnum, gbc);
/*     */ 
/*  83 */     add(this.dataFormat, gbc);
/*     */ 
/*  85 */     gbc.weightx = 1.0D;
/*  86 */     add(this.datasetPanel, gbc);
/*  87 */     gbc.weightx = 1.0D;
/*  88 */     gbc.gridwidth = 0;
/*  89 */     add(Box.createGlue(), gbc);
/*  90 */     gbc.weighty = 1.0D;
/*  91 */     add(Box.createGlue(), gbc);
/*     */   }
/*     */ 
/*     */   public void show()
/*     */   {
/*  97 */     setVals();
/*  98 */     this.datasetPanel.setVals();
/*  99 */     super.show();
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/* 105 */     this.polar = ((PolarChart)this.chart).getPolar();
/* 106 */     initCustomizer();
/*     */   }
/*     */ 
/*     */   public void getVals()
/*     */   {
/* 113 */     this.polar.setLabelsOn(this.ckshowlabel.getValue());
/* 114 */     ((PolarChart)this.chart).setLineVisible(this.ckshowline.getValue());
/* 115 */     ((PolarAxis)this.chart.getXAxis()).setManualSpoking(this.manualSpoking.getValue());
/* 116 */     if ((this.scaxisnum.getValue() == null) || (this.scaxisnum.getValue().equals("")))
/* 117 */       ((PolarAxis)this.chart.getXAxis()).setNumSpokes(0);
/*     */     else {
/* 119 */       ((PolarAxis)this.chart.getXAxis()).setNumSpokes(Integer.valueOf((String)this.scaxisnum.getValue()).intValue());
/*     */     }
/*     */ 
/* 125 */     this.polar.setPattern((String)this.dataFormat.getValue());
/* 126 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 133 */     this.ckshowlabel.setValue(this.polar.getLabelsOn());
/* 134 */     this.ckshowline.setValue(((PolarChart)this.chart).getLineVisible());
/* 135 */     this.manualSpoking.setValue(((PolarAxis)this.chart.getXAxis()).getManualSpoking());
/* 136 */     if (((PolarAxis)this.chart.getXAxis()).getNumSpokes() != 0)
/* 137 */       this.scaxisnum.setValue(String.valueOf(((PolarAxis)this.chart.getXAxis()).getNumSpokes()));
/*     */     else
/* 139 */       this.scaxisnum.setValue("4");
/* 140 */     this.dataFormat.setValue(this.polar.getPattern());
/* 141 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   public void volidateEnabled()
/*     */   {
/* 147 */     this.dataFormat.setEnabled(this.ckshowlabel.getValue());
/* 148 */     this.datasetPanel.validateEnabled();
/* 149 */     this.scaxisnum.setEnabled(this.manualSpoking.getValue());
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.RadarOption
 * JD-Core Version:    0.6.2
 */