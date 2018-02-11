/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.SectorMap;
/*     */ import jatools.component.chart.chart.SectorMapChart;
/*     */ import jatools.component.chart.component.AbstractComponent;
/*     */ import jatools.component.chart.component.CheckComponent;
/*     */ import jatools.component.chart.component.ColorComponent;
/*     */ import jatools.component.chart.component.FormatComponent;
/*     */ import jatools.component.chart.component.StringComponent;
/*     */ import java.awt.Color;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ 
/*     */ public class SectorMapOption extends Dialog
/*     */   implements ItemListener
/*     */ {
/*     */   CheckComponent cksinglecolor;
/*     */   CheckComponent ckshowlabel;
/*     */   CheckComponent ckdiagram;
/*     */   StringComponent scprecision;
/*     */   StringComponent sccolorvalue;
/*     */   StringComponent sclabel;
/*     */   ColorComponent ccbasecolor;
/*     */   ColorComponent ccminorcolor;
/*     */   JLabel llabelstyle;
/*     */   JComboBox cblabelstyle;
/*     */   private BarDatasetPanel datasetPanel;
/*     */   private SectorMap sector;
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
/*  55 */     this.cksinglecolor = new CheckComponent("单独颜色", false);
/*  56 */     this.cksinglecolor.addChangeListener(this);
/*  57 */     this.ckshowlabel = new CheckComponent("显示标签", false);
/*  58 */     this.ckshowlabel.addChangeListener(this);
/*  59 */     this.ckdiagram = new CheckComponent("分度", false);
/*  60 */     this.ckdiagram.addChangeListener(this);
/*     */ 
/*  62 */     this.dataFormat = FormatComponent.getDecimalFormatInstance("数据样式");
/*  63 */     this.dataFormat.addChangeListener(this);
/*     */ 
/*  65 */     this.scprecision = new StringComponent("精度", null);
/*  66 */     this.scprecision.addChangeListener(this);
/*  67 */     this.sccolorvalue = new StringComponent("颜色基本值", null);
/*  68 */     this.sccolorvalue.addChangeListener(this);
/*  69 */     this.sclabel = new StringComponent("标签", null);
/*  70 */     this.sclabel.addChangeListener(this);
/*     */ 
/*  72 */     this.ccbasecolor = new ColorComponent("基本色", null);
/*  73 */     this.ccbasecolor.addChangeListener(this);
/*  74 */     this.ccminorcolor = new ColorComponent("次要色", null);
/*  75 */     this.ccminorcolor.addChangeListener(this);
/*     */ 
/*  77 */     this.llabelstyle = new JLabel("标签样式");
/*  78 */     this.llabelstyle.setPreferredSize(CommonFinal.LABEL_SIZE);
/*     */ 
/*  80 */     this.datasetPanel = new BarDatasetPanel();
/*  81 */     this.datasetPanel.setChart(this.chart);
/*  82 */     this.datasetPanel.setObject(this.chart.getDatasets()[0]);
/*  83 */     this.datasetPanel.addChangeListener(this);
/*     */ 
/*  85 */     this.cblabelstyle = new JComboBox();
/*  86 */     this.cblabelstyle.addItem("文本值");
/*  87 */     this.cblabelstyle.addItem("x轴值");
/*  88 */     this.cblabelstyle.addItem("y轴值");
/*  89 */     this.cblabelstyle.addItemListener(this);
/*     */ 
/*  94 */     add(this.ckshowlabel, gbc);
/*  95 */     add(this.llabelstyle, gbc);
/*  96 */     gbc.gridwidth = 0;
/*  97 */     add(this.cblabelstyle, gbc);
/*     */ 
/* 100 */     gbc.gridwidth = 1;
/* 101 */     add(this.ckdiagram, gbc);
/* 102 */     gbc.gridwidth = 0;
/* 103 */     add(this.scprecision, gbc);
/*     */ 
/* 106 */     add(this.dataFormat, gbc);
/*     */ 
/* 108 */     add(this.ccbasecolor, gbc);
/*     */ 
/* 111 */     add(this.ccminorcolor, gbc);
/*     */ 
/* 114 */     add(this.sccolorvalue, gbc);
/*     */ 
/* 117 */     gbc.weightx = 1.0D;
/* 118 */     add(this.datasetPanel, gbc);
/* 119 */     gbc.weightx = 1.0D;
/* 120 */     gbc.gridwidth = 0;
/* 121 */     add(Box.createGlue(), gbc);
/* 122 */     gbc.weighty = 1.0D;
/* 123 */     add(Box.createGlue(), gbc);
/*     */   }
/*     */ 
/*     */   public void show() {
/* 127 */     setVals();
/* 128 */     this.datasetPanel.setVals();
/* 129 */     super.show();
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/* 134 */     this.sector = ((SectorMapChart)this.chart).getSectorMap();
/* 135 */     initCustomizer();
/*     */   }
/*     */ 
/*     */   public void getVals()
/*     */   {
/* 140 */     this.sector.setLabelsOn(this.ckshowlabel.getValue());
/* 141 */     if (this.cblabelstyle.getSelectedIndex() == 0)
/* 142 */       this.sector.setLabelStyle(2);
/* 143 */     else if (this.cblabelstyle.getSelectedIndex() == 1)
/* 144 */       this.sector.setLabelStyle(1);
/* 145 */     else if (this.cblabelstyle.getSelectedIndex() == 2) {
/* 146 */       this.sector.setLabelStyle(0);
/*     */     }
/* 148 */     this.sector.setLabelPrecision(Integer.parseInt((String)this.scprecision.getValue()));
/* 149 */     this.sector.setBaseColor(this.ccbasecolor.getColor());
/* 150 */     if ((this.sccolorvalue.getValue() == null) || (this.sccolorvalue.getValue().equals("")))
/* 151 */       this.sector.setBaseValue(0.0D);
/*     */     else
/* 153 */       this.sector.setBaseValue(Double.parseDouble((String)this.sccolorvalue.getValue()));
/* 154 */     this.sector.setSecondaryColor(this.ccminorcolor.getColor());
/* 155 */     this.sector.setUseGradientColoring(this.ckdiagram.getValue());
/*     */ 
/* 159 */     this.sector.setPattern((String)this.dataFormat.getValue());
/* 160 */     this.dataFormat.setEnabled(this.ckshowlabel.getValue());
/* 161 */     if (this.sector.getLabelsOn())
/* 162 */       this.cblabelstyle.setEnabled(true);
/*     */     else {
/* 164 */       this.cblabelstyle.setEnabled(false);
/*     */     }
/* 166 */     if (this.sector.getUseGradientColoring()) {
/* 167 */       this.ccbasecolor.setEnabled(true);
/* 168 */       this.sccolorvalue.setEnabled(true);
/* 169 */       this.ccminorcolor.setEnabled(true);
/*     */     } else {
/* 171 */       this.ccbasecolor.setEnabled(false);
/* 172 */       this.sccolorvalue.setEnabled(false);
/* 173 */       this.ccminorcolor.setEnabled(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 179 */     this.scprecision.setValue(String.valueOf(this.sector.getLabelPrecision()));
/* 180 */     this.ckshowlabel.setValue(this.sector.getLabelsOn());
/* 181 */     this.dataFormat.setValue(this.sector.getPattern());
/* 182 */     this.dataFormat.setEnabled(this.ckshowlabel.getValue());
/* 183 */     if (this.sector.getBaseColor() == null) {
/* 184 */       this.sector.setBaseColor(Color.black);
/*     */     }
/* 186 */     this.ccbasecolor.setValue(this.sector.getBaseColor());
/* 187 */     if (Double.isInfinite(this.sector.getBaseValue()))
/* 188 */       this.sccolorvalue.setValue("0.0");
/*     */     else {
/* 190 */       this.sccolorvalue.setValue(String.valueOf(this.sector.getBaseValue()));
/*     */     }
/* 192 */     this.ccminorcolor.setValue(this.sector.getSecondaryColor());
/* 193 */     this.ckdiagram.setValue(this.sector.getUseGradientColoring());
/* 194 */     if (this.sector.getLabelsOn())
/* 195 */       this.cblabelstyle.setEnabled(true);
/*     */     else {
/* 197 */       this.cblabelstyle.setEnabled(false);
/*     */     }
/*     */ 
/* 200 */     int style = this.sector.getLabelStyle();
/* 201 */     if (style == 0)
/* 202 */       this.cblabelstyle.setSelectedIndex(2);
/* 203 */     else if (style == 1)
/* 204 */       this.cblabelstyle.setSelectedIndex(1);
/* 205 */     else if (style == 2) {
/* 206 */       this.cblabelstyle.setSelectedIndex(0);
/*     */     }
/*     */ 
/* 211 */     if (this.sector.getUseGradientColoring()) {
/* 212 */       this.ccbasecolor.setEnabled(true);
/* 213 */       this.sccolorvalue.setEnabled(true);
/* 214 */       this.ccminorcolor.setEnabled(true);
/*     */     } else {
/* 216 */       this.ccbasecolor.setEnabled(false);
/* 217 */       this.sccolorvalue.setEnabled(false);
/* 218 */       this.ccminorcolor.setEnabled(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void itemStateChanged(ItemEvent e) {
/* 223 */     fireChange(null);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.SectorMapOption
 * JD-Core Version:    0.6.2
 */