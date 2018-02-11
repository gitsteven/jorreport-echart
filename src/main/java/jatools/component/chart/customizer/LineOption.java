/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.Line;
/*     */ import jatools.component.chart.chart.LineInterface;
/*     */ import jatools.component.chart.chart._Chart;
/*     */ import jatools.component.chart.component.AbstractComponent;
/*     */ import jatools.component.chart.component.ChartFont;
/*     */ import jatools.component.chart.component.CheckComponent;
/*     */ import jatools.component.chart.component.FontComponent;
/*     */ import jatools.component.chart.component.FormatComponent;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.Box;
/*     */ 
/*     */ public class LineOption extends Dialog
/*     */   implements ActionListener
/*     */ {
/*     */   CheckComponent ckshowlabel;
/*     */   CheckComponent ckshowvalue;
/*     */   CheckComponent ckshowsignonly;
/*     */   private LineDatasetPanel datasetPanel;
/*     */   Line line;
/*     */   private AbstractComponent dataFormat;
/*     */ 
/*     */   public void initCustomizer()
/*     */   {
/*  48 */     GridBagLayout gbl = new GridBagLayout();
/*  49 */     setLayout(gbl);
/*  50 */     setBorder(CommonFinal.EMPTY_BORDER);
/*  51 */     GridBagConstraints gbc = new GridBagConstraints();
/*  52 */     gbc.insets = CommonFinal.INSETS;
/*  53 */     gbc.fill = 2;
/*     */ 
/*  55 */     this.ckshowlabel = new CheckComponent("显示标签", false);
/*  56 */     this.ckshowlabel.addChangeListener(this);
/*  57 */     this.ckshowvalue = new CheckComponent("显示值", false);
/*  58 */     this.ckshowvalue.addChangeListener(this);
/*  59 */     this.ckshowsignonly = new CheckComponent("只显示标记", false);
/*  60 */     this.ckshowsignonly.addChangeListener(this);
/*     */ 
/*  62 */     this.dataFormat = FormatComponent.getDecimalFormatInstance("数据样式");
/*  63 */     this.dataFormat.addChangeListener(this);
/*     */ 
/*  65 */     this.datasetPanel = new LineDatasetPanel();
/*     */ 
/*  67 */     this.datasetPanel.setChart(this.chart);
/*  68 */     this.datasetPanel.setObject(this.chart.getDatasets()[0]);
/*  69 */     this.datasetPanel.addChangeListener(this);
/*     */ 
/*  76 */     gbc.gridwidth = 2;
/*  77 */     gbc.weightx = 1.0D;
/*  78 */     add(this.ckshowlabel, gbc);
/*  79 */     gbc.weightx = 0.0D;
/*     */ 
/*  81 */     gbc.gridwidth = 0;
/*  82 */     add(Box.createGlue(), gbc);
/*     */ 
/*  85 */     gbc.gridwidth = 1;
/*  86 */     add(this.ckshowvalue, gbc);
/*  87 */     add(this.ckshowsignonly, gbc);
/*  88 */     gbc.gridwidth = 0;
/*  89 */     add(Box.createGlue(), gbc);
/*     */ 
/*  92 */     gbc.weightx = 1.0D;
/*  93 */     gbc.gridwidth = 0;
/*  94 */     add(this.dataFormat, gbc);
/*     */ 
/*  98 */     gbc.weightx = 1.0D;
/*  99 */     add(this.datasetPanel, gbc);
/* 100 */     gbc.weightx = 1.0D;
/* 101 */     gbc.gridwidth = 0;
/* 102 */     add(Box.createGlue(), gbc);
/* 103 */     gbc.weighty = 1.0D;
/* 104 */     add(Box.createGlue(), gbc);
/*     */   }
/*     */ 
/*     */   public void show() {
/* 108 */     setVals();
/* 109 */     this.datasetPanel.setVals();
/* 110 */     super.show();
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/* 119 */     this.line = ((LineInterface)this.chart).getLine();
/* 120 */     initCustomizer();
/*     */   }
/*     */ 
/*     */   public void getVals()
/*     */   {
/* 129 */     this.line.setLabelsOn(this.ckshowlabel.getValue());
/* 130 */     this.line.setUseValueLabels(this.ckshowvalue.getValue());
/* 131 */     this.line.setScatterPlot(this.ckshowsignonly.getValue());
/*     */ 
/* 135 */     this.line.setPattern((String)this.dataFormat.getValue());
/*     */ 
/* 137 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 146 */     this.ckshowlabel.setValue(this.line.getLabelsOn());
/* 147 */     this.ckshowvalue.setValue(this.line.getUseValueLabels());
/* 148 */     this.ckshowsignonly.setValue(this.line.isScatterPlot());
/*     */ 
/* 150 */     this.dataFormat.setValue(this.line.getPattern());
/*     */ 
/* 152 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   private void volidateEnabled() {
/* 156 */     this.ckshowvalue.setEnabled(this.ckshowlabel.getValue());
/* 157 */     this.dataFormat.setEnabled(this.ckshowvalue.getValue());
/* 158 */     this.datasetPanel.validateEnabled();
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 167 */     if ((e.getActionCommand().equals("label font")) && 
/* 168 */       (FontComponent.getDefault().showChooser(this))) {
/* 169 */       ChartFont font = (ChartFont)FontComponent.getDefault().getValue();
/* 170 */       Color color = font.getForeColor();
/* 171 */       Font ff = new Font(font.getFace(), font.getStyle(), font
/* 172 */         .getSize());
/* 173 */       for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 174 */         this.chart.getDatasets()[i].setLabelColor(color);
/* 175 */         this.chart.getDatasets()[i].setLabelFont(ff);
/*     */       }
/*     */     }
/*     */ 
/* 179 */     if (this.parent != null)
/* 180 */       this.parent.fireChange(null);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.LineOption
 * JD-Core Version:    0.6.2
 */