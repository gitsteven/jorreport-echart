/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Area;
/*     */ import jatools.component.chart.chart.Bar;
/*     */ import jatools.component.chart.chart.BarAreaInterface;
/*     */ import jatools.component.chart.chart.DataRepresentation;
/*     */ import jatools.component.chart.chart.Dataset;
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
/*     */ public class BarAreaOption extends Dialog
/*     */   implements ActionListener
/*     */ {
/*     */   CheckComponent ckshowlabel;
/*     */   CheckComponent ckshowvalue;
/*     */   StringComponent scbaseline;
/*     */   StringComponent scdatalabel;
/*     */   RangeComponent rcbarwidth;
/*     */   JPanel p;
/*     */   JLabel ldatasource;
/*     */   JButton bdatasource;
/*     */   JComboBox cbdatasource;
/*     */   Bar bar;
/*     */   Dataset dataset;
/*     */   private BarAreaLineDatasetPanel datasetPanel;
/*     */   private Area area;
/*     */   private AbstractComponent dataFormat;
/*     */ 
/*     */   public void initCustomizer()
/*     */   {
/*  68 */     GridBagLayout gbl = new GridBagLayout();
/*  69 */     setLayout(gbl);
/*  70 */     setBorder(CommonFinal.EMPTY_BORDER);
/*  71 */     GridBagConstraints gbc = new GridBagConstraints();
/*  72 */     gbc.insets = CommonFinal.INSETS;
/*  73 */     gbc.fill = 2;
/*     */ 
/*  75 */     this.ckshowlabel = new CheckComponent("显示标签", false);
/*  76 */     this.ckshowlabel.addChangeListener(this);
/*  77 */     this.ckshowvalue = new CheckComponent("显示值", false);
/*  78 */     this.ckshowvalue.addChangeListener(this);
/*     */ 
/*  82 */     this.dataFormat = FormatComponent.getDecimalFormatInstance("数据样式");
/*  83 */     this.dataFormat.addChangeListener(this);
/*     */ 
/*  85 */     this.rcbarwidth = new RangeComponent("柱形宽度", 0.0D);
/*  86 */     this.rcbarwidth.addChangeListener(this);
/*  87 */     this.scbaseline = new StringComponent("基线", null);
/*  88 */     this.scbaseline.addChangeListener(this);
/*     */ 
/*  93 */     this.datasetPanel = getDatasetPanel();
/*  94 */     this.datasetPanel.setChart(this.chart);
/*  95 */     this.datasetPanel.setObject(this.chart.getDatasets()[0]);
/*  96 */     this.datasetPanel.addChangeListener(this);
/*     */ 
/*  99 */     gbc.gridwidth = 1;
/* 100 */     gbc.weightx = 1.0D;
/* 101 */     add(this.ckshowlabel, gbc);
/*     */ 
/* 110 */     add(this.ckshowvalue, gbc);
/* 111 */     gbc.weightx = 0.0D;
/* 112 */     gbc.gridwidth = 0;
/* 113 */     add(Box.createGlue(), gbc);
/*     */ 
/* 115 */     gbc.weightx = 1.0D;
/* 116 */     gbc.gridwidth = 0;
/* 117 */     add(this.dataFormat, gbc);
/*     */ 
/* 120 */     gbc.weightx = 1.0D;
/* 121 */     gbc.gridwidth = 0;
/* 122 */     add(this.rcbarwidth, gbc);
/*     */ 
/* 124 */     gbc.weightx = 1.0D;
/* 125 */     gbc.gridwidth = 0;
/* 126 */     add(this.scbaseline, gbc);
/*     */ 
/* 130 */     gbc.gridwidth = 0;
/* 131 */     gbc.weightx = 1.0D;
/* 132 */     add(this.datasetPanel, gbc);
/* 133 */     gbc.weighty = 1.0D;
/* 134 */     add(Box.createGlue(), gbc);
/*     */   }
/*     */ 
/*     */   public void show() {
/* 138 */     setVals();
/* 139 */     this.datasetPanel.setVals();
/* 140 */     super.show();
/*     */   }
/*     */ 
/*     */   public BarAreaLineDatasetPanel getDatasetPanel()
/*     */   {
/* 145 */     return new BarAreaLineDatasetPanel(false);
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/* 154 */     this.bar = ((BarAreaInterface)this.chart).getBar();
/* 155 */     this.area = ((BarAreaInterface)this.chart).getArea();
/* 156 */     initCustomizer();
/*     */   }
/*     */ 
/*     */   public void getVals()
/*     */   {
/* 165 */     this.bar.setLabelsOn(this.ckshowlabel.getValue());
/* 166 */     this.area.setLabelsOn(this.ckshowlabel.getValue());
/* 167 */     this.bar.setUseValueLabels(this.ckshowvalue.getValue());
/*     */ 
/* 170 */     this.bar.setClusterWidth(this.rcbarwidth.getValue());
/* 171 */     this.bar.setBaseline(Double.parseDouble((String)this.scbaseline.getValue()));
/*     */ 
/* 176 */     this.bar.setPattern((String)this.dataFormat.getValue());
/* 177 */     this.area.setPattern((String)this.dataFormat.getValue());
/* 178 */     this.chart.getDataRepresentation().setPattern((String)this.dataFormat.getValue());
/*     */ 
/* 180 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 189 */     this.scbaseline.setValue(String.valueOf(this.bar.getBaseline()));
/* 190 */     this.ckshowlabel.setValue(this.bar.getLabelsOn());
/* 191 */     this.ckshowvalue.setValue(this.bar.getUseValueLabels());
/*     */ 
/* 194 */     this.rcbarwidth.setValue(this.bar.getClusterWidth());
/*     */ 
/* 196 */     this.dataFormat.setValue(this.chart.getDataRepresentation().getPattern());
/*     */ 
/* 198 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   private void volidateEnabled() {
/* 202 */     this.ckshowvalue.setEnabled(this.ckshowlabel.getValue());
/* 203 */     this.datasetPanel.validateEnabled();
/*     */ 
/* 205 */     this.dataFormat.setEnabled(this.ckshowvalue.getValue());
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 214 */     if ((e.getActionCommand().equals("label font")) && 
/* 215 */       (FontComponent.getDefault().showChooser(this))) {
/* 216 */       ChartFont font = (ChartFont)FontComponent.getDefault().getValue();
/* 217 */       Color color = font.getForeColor();
/* 218 */       Font ff = new Font(font.getFace(), font.getStyle(), font
/* 219 */         .getSize());
/* 220 */       for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 221 */         this.chart.getDatasets()[i].setLabelColor(color);
/* 222 */         this.chart.getDatasets()[i].setLabelFont(ff);
/*     */       }
/*     */     }
/*     */ 
/* 226 */     if (this.parent != null)
/* 227 */       this.parent.fireChange(null);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.BarAreaOption
 * JD-Core Version:    0.6.2
 */