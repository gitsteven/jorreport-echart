/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Bar;
/*     */ import jatools.component.chart.chart.BarLineInterface;
/*     */ import jatools.component.chart.chart.DataRepresentation;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.Line;
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
/*     */ public class BarLineOption extends Dialog
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
/*     */   Line line;
/*     */   Dataset dataset;
/*     */   private BarLineDatasetPanel datasetPanel;
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
/*  80 */     this.dataFormat = FormatComponent.getDecimalFormatInstance("数据样式");
/*  81 */     this.dataFormat.addChangeListener(this);
/*     */ 
/*  83 */     this.rcbarwidth = new RangeComponent("柱形宽度", 0.0D);
/*  84 */     this.rcbarwidth.addChangeListener(this);
/*  85 */     this.scbaseline = new StringComponent("基线", null);
/*  86 */     this.scbaseline.addChangeListener(this);
/*     */ 
/*  91 */     this.datasetPanel = new BarLineDatasetPanel();
/*  92 */     this.datasetPanel.setChart(this.chart);
/*  93 */     this.datasetPanel.setObject(this.chart.getDatasets()[0]);
/*  94 */     this.datasetPanel.addChangeListener(this);
/*     */ 
/*  97 */     gbc.gridwidth = 1;
/*  98 */     gbc.weightx = 1.0D;
/*  99 */     add(this.ckshowlabel, gbc);
/*     */ 
/* 106 */     add(this.ckshowvalue, gbc);
/* 107 */     gbc.weightx = 0.0D;
/* 108 */     gbc.gridwidth = 0;
/* 109 */     add(Box.createGlue(), gbc);
/*     */ 
/* 112 */     gbc.weightx = 1.0D;
/* 113 */     gbc.gridwidth = 0;
/* 114 */     add(this.dataFormat, gbc);
/*     */ 
/* 116 */     gbc.gridwidth = 0;
/* 117 */     gbc.weightx = 1.0D;
/* 118 */     add(this.rcbarwidth, gbc);
/*     */ 
/* 121 */     gbc.gridwidth = 0;
/* 122 */     gbc.weightx = 1.0D;
/* 123 */     add(this.scbaseline, gbc);
/*     */ 
/* 127 */     gbc.gridwidth = 0;
/* 128 */     gbc.weightx = 1.0D;
/* 129 */     add(this.datasetPanel, gbc);
/* 130 */     gbc.weighty = 1.0D;
/* 131 */     add(Box.createGlue(), gbc);
/*     */   }
/*     */ 
/*     */   public void show() {
/* 135 */     setVals();
/* 136 */     this.datasetPanel.setVals();
/* 137 */     super.show();
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/* 146 */     this.bar = ((BarLineInterface)this.chart).getBar();
/* 147 */     this.line = ((BarLineInterface)this.chart).getLine();
/* 148 */     initCustomizer();
/*     */   }
/*     */ 
/*     */   public void getVals()
/*     */   {
/* 157 */     this.bar.setLabelsOn(this.ckshowlabel.getValue());
/* 158 */     this.line.setLabelsOn(this.ckshowlabel.getValue());
/* 159 */     this.bar.setUseValueLabels(this.ckshowvalue.getValue());
/* 160 */     this.line.setUseValueLabels(this.ckshowvalue.getValue());
/*     */ 
/* 163 */     this.bar.setClusterWidth(this.rcbarwidth.getValue());
/* 164 */     this.bar.setBaseline(Double.parseDouble((String)this.scbaseline.getValue()));
/*     */ 
/* 171 */     this.bar.setPattern((String)this.dataFormat.getValue());
/* 172 */     this.line.setPattern((String)this.dataFormat.getValue());
/* 173 */     this.chart.getDataRepresentation().setPattern((String)this.dataFormat.getValue());
/*     */ 
/* 175 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 184 */     this.scbaseline.setValue(String.valueOf(this.bar.getBaseline()));
/* 185 */     this.ckshowlabel.setValue(this.bar.getLabelsOn());
/* 186 */     this.ckshowvalue.setValue(this.bar.getUseValueLabels());
/*     */ 
/* 189 */     this.rcbarwidth.setValue(this.bar.getClusterWidth());
/*     */ 
/* 191 */     this.dataFormat.setValue(this.chart.getDataRepresentation().getPattern());
/*     */ 
/* 193 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   private void volidateEnabled() {
/* 197 */     this.ckshowvalue.setEnabled(this.ckshowlabel.getValue());
/*     */ 
/* 199 */     this.dataFormat.setEnabled(this.ckshowvalue.getValue());
/* 200 */     this.datasetPanel.validateEnabled();
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 209 */     if ((e.getActionCommand().equals("label font")) && 
/* 210 */       (FontComponent.getDefault().showChooser(this))) {
/* 211 */       ChartFont font = (ChartFont)FontComponent.getDefault().getValue();
/* 212 */       Color color = font.getForeColor();
/* 213 */       Font ff = new Font(font.getFace(), font.getStyle(), font
/* 214 */         .getSize());
/* 215 */       for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 216 */         this.chart.getDatasets()[i].setLabelColor(color);
/* 217 */         this.chart.getDatasets()[i].setLabelFont(ff);
/*     */       }
/*     */     }
/*     */ 
/* 221 */     if (this.parent != null)
/* 222 */       this.parent.fireChange(null);
/*     */   }
/*     */ 
/*     */   public void fileChange(Object object)
/*     */   {
/* 227 */     repaint();
/* 228 */     if (this.parent != null)
/* 229 */       this.parent.fireChange(object);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.BarLineOption
 * JD-Core Version:    0.6.2
 */