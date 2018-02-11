/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.JavachartUtil;
/*     */ import jatools.component.chart.chart.BarAreaInterface;
/*     */ import jatools.component.chart.chart.DataRepresentation;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.Datum;
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import jatools.component.chart.chart._Chart;
/*     */ import jatools.component.chart.component.ChartFont;
/*     */ import jatools.component.chart.component.ColorComponent;
/*     */ import jatools.component.chart.component.FillStyleChooser;
/*     */ import jatools.component.chart.component.FillStyleFactory;
/*     */ import jatools.component.chart.component.FillStyleInterface;
/*     */ import jatools.component.chart.component.FontComponent;
/*     */ import jatools.component.chart.component.MoreButton;
/*     */ import jatools.component.chart.component.StringComponent;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
/*     */ 
/*     */ public class BarAreaLineDatasetPanel extends Dialog
/*     */   implements ActionListener, ItemListener
/*     */ {
/*     */   private JLabel ldatasource;
/*     */   private JLabel ldatatype;
/*     */   private MoreButton bshowlabel;
/*     */   private MoreButton bdatasource;
/*     */   private StringComponent scdatalabel;
/*     */   private JComboBox cbdatasource;
/*     */   private ColorComponent datasetColor;
/*     */   private ColorComponent datasetOutline;
/*     */   private ButtonGroup bg;
/*     */   private JRadioButton rbbar;
/*     */   private JRadioButton rbarea;
/*     */   Dataset dataset;
/*     */   private boolean lineVisible;
/*     */   private JRadioButton rbline;
/*     */ 
/*     */   BarAreaLineDatasetPanel(boolean lineVisible)
/*     */   {
/*  66 */     this.lineVisible = lineVisible;
/*     */   }
/*     */ 
/*     */   public void initCustomizer()
/*     */   {
/*  73 */     GridBagLayout gbl = new GridBagLayout();
/*  74 */     setLayout(gbl);
/*     */ 
/*  76 */     GridBagConstraints gbc = new GridBagConstraints();
/*  77 */     gbc.insets = CommonFinal.INSETS;
/*  78 */     gbc.fill = 2;
/*     */ 
/*  80 */     setLayout(gbl);
/*     */ 
/*  82 */     this.ldatasource = new JLabel("数据列");
/*     */ 
/*  84 */     this.ldatatype = new JLabel("数据类型");
/*  85 */     this.bdatasource = new MoreButton("填充样式");
/*  86 */     this.bdatasource.setActionCommand("fill style");
/*  87 */     this.bdatasource.addActionListener(this);
/*     */ 
/*  89 */     this.bshowlabel = new MoreButton("字体");
/*  90 */     this.bshowlabel.setActionCommand("label font");
/*  91 */     this.bshowlabel.addActionListener(this);
/*     */ 
/*  93 */     this.scdatalabel = new StringComponent("标签", null);
/*  94 */     this.scdatalabel.addChangeListener(this);
/*  95 */     this.cbdatasource = new JComboBox();
/*     */ 
/*  97 */     for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/*  98 */       this.cbdatasource.addItem(this.chart.getDatasets()[i].getName());
/*     */     }
/*     */ 
/* 101 */     this.cbdatasource.addItemListener(this);
/* 102 */     this.datasetColor = new ColorComponent("颜色", null);
/* 103 */     this.datasetColor.addChangeListener(this);
/* 104 */     this.datasetOutline = new ColorComponent("轮廓线", null);
/* 105 */     this.datasetOutline.addChangeListener(this);
/*     */ 
/* 107 */     this.bg = new ButtonGroup();
/*     */ 
/* 109 */     JPanel p = new JPanel();
/* 110 */     p.setLayout(gbl);
/* 111 */     this.rbbar = new JRadioButton("柱形", false);
/* 112 */     this.rbbar.setHorizontalTextPosition(2);
/* 113 */     this.rbbar.addActionListener(this);
/* 114 */     this.rbarea = new JRadioButton("面积", false);
/* 115 */     this.rbarea.setHorizontalTextPosition(2);
/* 116 */     this.rbarea.addActionListener(this);
/*     */ 
/* 118 */     this.bg.add(this.rbbar);
/* 119 */     this.bg.add(this.rbarea);
/*     */ 
/* 121 */     p.add(this.rbbar, gbc);
/* 122 */     p.add(this.rbarea, gbc);
/*     */ 
/* 125 */     if (this.lineVisible) {
/* 126 */       this.rbline = new JRadioButton("线形", false);
/* 127 */       this.rbline.setHorizontalTextPosition(2);
/* 128 */       this.rbline.addActionListener(this);
/* 129 */       this.bg.add(this.rbline);
/* 130 */       p.add(this.rbline, gbc);
/*     */     }
/*     */ 
/* 133 */     SepratorPanel sp = new SepratorPanel("数据列设置");
/* 134 */     gbc.weightx = 1.0D;
/* 135 */     gbc.gridwidth = 0;
/* 136 */     add(sp, gbc);
/*     */ 
/* 138 */     gbc.weightx = 0.0D;
/* 139 */     gbc.gridwidth = 1;
/* 140 */     add(this.ldatasource, gbc);
/*     */ 
/* 143 */     add(this.cbdatasource, gbc);
/* 144 */     gbc.gridwidth = 0;
/* 145 */     add(this.bshowlabel, gbc);
/*     */ 
/* 148 */     gbc.weightx = 1.0D;
/* 149 */     gbc.gridwidth = 2;
/* 150 */     add(this.datasetColor, gbc);
/*     */ 
/* 152 */     gbc.gridwidth = 0;
/* 153 */     add(this.bdatasource, gbc);
/*     */ 
/* 155 */     gbc.weightx = 1.0D;
/* 156 */     add(this.datasetOutline, gbc);
/*     */ 
/* 160 */     add(this.scdatalabel, gbc);
/*     */ 
/* 162 */     gbc.gridwidth = 1;
/* 163 */     add(this.ldatatype, gbc);
/* 164 */     add(p, gbc);
/* 165 */     gbc.gridwidth = 0;
/* 166 */     add(Box.createGlue(), gbc);
/*     */ 
/* 168 */     gbc.weighty = 1.0D;
/* 169 */     add(Box.createGlue(), gbc);
/*     */   }
/*     */ 
/*     */   public void show()
/*     */   {
/* 176 */     setVals();
/* 177 */     super.show();
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/* 190 */     this.dataset = ((Dataset)object);
/* 191 */     this.dataset.getGc().setOutlineFills(true);
/* 192 */     initCustomizer();
/*     */   }
/*     */ 
/*     */   public void getVals()
/*     */   {
/* 203 */     this.datasetColor.getValue().setToGc(this.dataset.getGc());
/* 204 */     this.dataset.getGc().setLineColor(this.datasetOutline.getColor());
/*     */ 
/* 206 */     int i = this.dataset.getData().size();
/* 207 */     String[] labels = new String[i];
/*     */ 
/* 209 */     if (this.scdatalabel.getValue() != null) {
/* 210 */       if (!this.scdatalabel.getValue().equals("")) {
/* 211 */         JavachartUtil.addArray(labels, ((String)this.scdatalabel.getValue()).split(","));
/*     */       }
/*     */ 
/* 214 */       this.dataset.setLabels(labels);
/* 215 */       this.dataset.replaceLabels(labels);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 227 */     if (getDatasetType(this.dataset) == 0)
/* 228 */       this.rbbar.setSelected(true);
/* 229 */     else if (getDatasetType(this.dataset) == 1)
/* 230 */       this.rbarea.setSelected(true);
/*     */     else {
/* 232 */       this.rbline.setSelected(true);
/*     */     }
/*     */ 
/* 235 */     this.datasetColor.setValue(FillStyleFactory.createFillStyle(this.dataset.getGc()));
/* 236 */     this.datasetOutline.setValue(this.dataset.getGc().getLineColor());
/*     */ 
/* 238 */     String[] s = new String[this.dataset.getData().size()];
/*     */ 
/* 240 */     for (int i = 0; i < s.length; i++) {
/* 241 */       s[i] = this.dataset.getDataElementAt(i).getLabel();
/*     */     }
/*     */ 
/* 244 */     this.scdatalabel.setValue(JavachartUtil.plus(s, ","));
/*     */   }
/*     */ 
/*     */   public void validateEnabled()
/*     */   {
/* 251 */     this.bshowlabel.setEnabled(this.chart.getDataRepresentation().getLabelsOn());
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 264 */     Component f = getParent();
/*     */ 
/* 266 */     while (!(f instanceof JDialog)) {
/* 267 */       f = f.getParent();
/*     */     }
/*     */ 
/* 270 */     if (e.getActionCommand().equals("fill style")) {
/* 271 */       FillStyleInterface style = FillStyleChooser.showDialog(f, "填充样式", 
/* 272 */         FillStyleFactory.createFillStyle(this.dataset.getGc()), 0);
/* 273 */       this.datasetColor.setValue(style);
/* 274 */       style.setToGc(this.dataset.getGc());
/* 275 */     } else if (e.getActionCommand().equals("label font")) {
/* 276 */       if (FontComponent.getDefault().showChooser(this)) {
/* 277 */         ChartFont font = (ChartFont)FontComponent.getDefault().getValue();
/* 278 */         Color color = font.getForeColor();
/* 279 */         Font ff = new Font(font.getFace(), font.getStyle(), font.getSize());
/* 280 */         this.dataset.setLabelColor(color);
/* 281 */         this.dataset.setLabelFont(ff);
/*     */       }
/*     */     } else {
/* 284 */       if (this.rbbar.isSelected())
/* 285 */         ((BarAreaInterface)this.chart).setChartType(indexOfDatasets(this.dataset), 
/* 286 */           0);
/* 287 */       else if (this.rbarea.isSelected())
/* 288 */         ((BarAreaInterface)this.chart).setChartType(indexOfDatasets(this.dataset), 
/* 289 */           1);
/* 290 */       else if (this.lineVisible) {
/* 291 */         ((BarAreaInterface)this.chart).setChartType(indexOfDatasets(this.dataset), 
/* 292 */           2);
/*     */       }
/*     */ 
/* 295 */       setVals();
/*     */     }
/*     */ 
/* 298 */     if (this.parent != null)
/* 299 */       this.parent.fireChange(null);
/*     */   }
/*     */ 
/*     */   public void itemStateChanged(ItemEvent e)
/*     */   {
/* 313 */     this.dataset = this.chart.getDataset((String)this.cbdatasource.getSelectedItem());
/* 314 */     this.dataset.getGc().setOutlineFills(true);
/*     */ 
/* 316 */     setVals();
/*     */ 
/* 318 */     if (this.parent != null)
/* 319 */       this.parent.fireChange(null);
/*     */   }
/*     */ 
/*     */   private int indexOfDatasets(Dataset dataset)
/*     */   {
/* 324 */     for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 325 */       if (dataset.getName().equals(this.chart.getDatasets()[i].getName())) {
/* 326 */         return i;
/*     */       }
/*     */     }
/*     */ 
/* 330 */     return -1;
/*     */   }
/*     */ 
/*     */   private int getDatasetType(Dataset dataset) {
/* 334 */     int i = indexOfDatasets(dataset);
/*     */ 
/* 336 */     if (i == -1) {
/* 337 */       return -1;
/*     */     }
/*     */ 
/* 340 */     return ((BarAreaInterface)this.chart).getChartType(indexOfDatasets(dataset));
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.BarAreaLineDatasetPanel
 * JD-Core Version:    0.6.2
 */