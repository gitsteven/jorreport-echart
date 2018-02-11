/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Bar;
/*     */ import jatools.component.chart.chart.BarInterface;
/*     */ import jatools.component.chart.chart.DataRepresentation;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.DateStackBarChart;
/*     */ import jatools.component.chart.chart.DateStackColumnChart;
/*     */ import jatools.component.chart.chart.StackBarChart;
/*     */ import jatools.component.chart.chart.StackColumnChart;
/*     */ import jatools.component.chart.chart._Chart;
/*     */ import jatools.component.chart.component.AbstractComponent;
/*     */ import jatools.component.chart.component.ChartFont;
/*     */ import jatools.component.chart.component.CheckComponent;
/*     */ import jatools.component.chart.component.FontComponent;
/*     */ import jatools.component.chart.component.FormatComponent;
/*     */ import jatools.component.chart.component.RangeComponent;
/*     */ import jatools.component.chart.component.StringComponent;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class BarOption extends Dialog
/*     */   implements ActionListener
/*     */ {
/*     */   CheckComponent ckshowlabel;
/*     */   CheckComponent ckshowvalue;
/*     */   CheckComponent cksinglecolor;
/*     */   StringComponent scbaseline;
/*     */   StringComponent scdatalabel;
/*     */   RangeComponent rcbarwidth;
/*     */   JPanel p;
/*     */   JLabel ldatasource;
/*     */   JButton bdatasource;
/*     */   JComboBox cbdatasource;
/*     */   Bar bar;
/*     */   private BarDatasetPanel datasetPanel;
/*     */   private AbstractComponent dataFormat;
/*     */ 
/*     */   public void initCustomizer()
/*     */   {
/*  66 */     GridBagLayout gbl = new GridBagLayout();
/*  67 */     setLayout(gbl);
/*  68 */     setBorder(CommonFinal.EMPTY_BORDER);
/*  69 */     GridBagConstraints gbc = new GridBagConstraints();
/*  70 */     gbc.insets = CommonFinal.INSETS;
/*  71 */     gbc.fill = 2;
/*     */ 
/*  73 */     this.ckshowlabel = new CheckComponent("显示标签", false);
/*  74 */     this.ckshowlabel.addChangeListener(this);
/*  75 */     this.ckshowvalue = new CheckComponent("显示值", false);
/*  76 */     this.ckshowvalue.addChangeListener(this);
/*  77 */     this.cksinglecolor = new CheckComponent("单独的颜色", false);
/*  78 */     this.cksinglecolor.addChangeListener(this);
/*     */ 
/*  80 */     this.dataFormat = FormatComponent.getDecimalFormatInstance("数据样式");
/*  81 */     this.dataFormat.addChangeListener(this);
/*     */ 
/*  83 */     this.rcbarwidth = new RangeComponent("柱形宽度", 0.0D);
/*  84 */     this.rcbarwidth.addChangeListener(this);
/*  85 */     this.scbaseline = new StringComponent("基线", null);
/*  86 */     this.scbaseline.addChangeListener(this);
/*     */ 
/*  91 */     this.datasetPanel = new BarDatasetPanel();
/*  92 */     this.datasetPanel.setChart(this.chart);
/*  93 */     this.datasetPanel.setObject(this.chart.getDatasets()[0]);
/*  94 */     this.datasetPanel.addChangeListener(this);
/*     */ 
/*  97 */     gbc.gridwidth = 1;
/*  98 */     gbc.weightx = 1.0D;
/*  99 */     add(this.ckshowlabel, gbc);
/*     */ 
/* 102 */     gbc.weightx = 0.0D;
/* 103 */     gbc.gridwidth = 0;
/* 104 */     add(Box.createGlue(), gbc);
/*     */ 
/* 113 */     gbc.weightx = 1.0D;
/* 114 */     gbc.gridwidth = 1;
/* 115 */     add(this.ckshowvalue, gbc);
/* 116 */     gbc.gridwidth = 0;
/* 117 */     add(this.cksinglecolor, gbc);
/*     */ 
/* 121 */     gbc.weightx = 1.0D;
/* 122 */     gbc.gridwidth = 0;
/* 123 */     add(this.dataFormat, gbc);
/*     */ 
/* 125 */     gbc.gridwidth = 0;
/* 126 */     gbc.weightx = 1.0D;
/* 127 */     add(this.rcbarwidth, gbc);
/*     */ 
/* 133 */     gbc.gridwidth = 0;
/* 134 */     gbc.weightx = 1.0D;
/* 135 */     add(this.scbaseline, gbc);
/*     */ 
/* 143 */     gbc.weightx = 1.0D;
/* 144 */     add(this.datasetPanel, gbc);
/* 145 */     gbc.weightx = 0.0D;
/* 146 */     gbc.gridwidth = 0;
/* 147 */     add(Box.createGlue(), gbc);
/* 148 */     gbc.weighty = 1.0D;
/* 149 */     add(Box.createGlue(), gbc);
/*     */ 
/* 151 */     this.bar = ((BarInterface)this.chart).getBar();
/*     */ 
/* 153 */     if (((this.chart instanceof DateStackBarChart)) || ((this.chart instanceof DateStackColumnChart)) || ((this.chart instanceof StackBarChart)) || ((this.chart instanceof StackColumnChart)))
/* 154 */       this.scbaseline.setVisible(false);
/*     */   }
/*     */ 
/*     */   public void show()
/*     */   {
/* 159 */     setVals();
/* 160 */     this.datasetPanel.setVals();
/* 161 */     super.show();
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 170 */     if ((e.getActionCommand().equals("label font")) && 
/* 171 */       (FontComponent.getDefault().showChooser(this))) {
/* 172 */       ChartFont font = (ChartFont)FontComponent.getDefault().getValue();
/* 173 */       Color color = font.getForeColor();
/* 174 */       Font ff = new Font(font.getFace(), font.getStyle(), font
/* 175 */         .getSize());
/* 176 */       for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 177 */         this.chart.getDatasets()[i].setLabelColor(color);
/* 178 */         this.chart.getDatasets()[i].setLabelFont(ff);
/*     */       }
/*     */     }
/*     */ 
/* 182 */     if (this.parent != null)
/* 183 */       this.parent.fireChange(null);
/*     */   }
/*     */ 
/*     */   public void getVals()
/*     */   {
/* 194 */     this.bar.setLabelsOn(this.ckshowlabel.getValue());
/* 195 */     this.bar.setUseValueLabels(this.ckshowvalue.getValue());
/* 196 */     ((BarInterface)this.chart).setIndividualColors(this.cksinglecolor.getValue());
/* 197 */     this.bar.setClusterWidth(this.rcbarwidth.getValue());
/* 198 */     if ((this.scbaseline.getValue() == null) || (this.scbaseline.getValue().equals("")))
/* 199 */       this.bar.setBaseline(0.0D);
/*     */     else {
/* 201 */       this.bar.setBaseline(Double.parseDouble((String)this.scbaseline.getValue()));
/*     */     }
/*     */ 
/* 205 */     this.chart.getDataRepresentation().setPattern((String)this.dataFormat.getValue());
/*     */ 
/* 207 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 216 */     this.ckshowlabel.setValue(this.bar.getLabelsOn());
/* 217 */     this.ckshowvalue.setValue(this.bar.getUseValueLabels());
/*     */ 
/* 219 */     this.cksinglecolor.setValue(((BarInterface)this.chart).getIndividualColors());
/* 220 */     this.rcbarwidth.setValue(this.bar.getClusterWidth());
/* 221 */     this.scbaseline.setValue(String.valueOf(this.bar.getBaseline()));
/*     */ 
/* 223 */     this.dataFormat.setValue(this.chart.getDataRepresentation().getPattern());
/*     */ 
/* 225 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   private void volidateEnabled()
/*     */   {
/* 232 */     this.ckshowvalue.setEnabled(this.ckshowlabel.getValue());
/* 233 */     this.dataFormat.setEnabled(this.ckshowvalue.getValue());
/* 234 */     this.datasetPanel.validateEnabled();
/* 235 */     if (this.chart.getNumDatasets() < 2) {
/* 236 */       this.cksinglecolor.setEnabled(true);
/*     */     }
/*     */     else
/* 239 */       this.cksinglecolor.setEnabled(false);
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/* 244 */     this.bar = ((BarInterface)this.chart).getBar();
/* 245 */     initCustomizer();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.BarOption
 * JD-Core Version:    0.6.2
 */