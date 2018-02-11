/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.JavachartUtil;
/*     */ import jatools.component.chart.chart.BarLineInterface;
/*     */ import jatools.component.chart.chart.DataRepresentation;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.Datum;
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import jatools.component.chart.chart._Chart;
/*     */ import jatools.component.chart.component.ColorComponent;
/*     */ import jatools.component.chart.component.FillStyleChooser;
/*     */ import jatools.component.chart.component.FillStyleFactory;
/*     */ import jatools.component.chart.component.FillStyleInterface;
/*     */ import jatools.component.chart.component.LineStyle;
/*     */ import jatools.component.chart.component.MoreButton;
/*     */ import jatools.component.chart.component.StringComponent;
/*     */ import java.awt.Component;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
/*     */ 
/*     */ public class BarLineDatasetPanel extends Dialog
/*     */   implements ActionListener, ItemListener
/*     */ {
/*     */   private JLabel ldatasource;
/*     */   private JLabel ldatatype;
/*     */   private JComboBox cbdatasource;
/*     */   private ColorComponent cccolor;
/*     */   private StringComponent sclabel;
/*     */   private JButton bdatasource;
/*     */   private JButton bshowlabel;
/*     */   private ButtonGroup bg;
/*     */   private JRadioButton rbbar;
/*     */   private JRadioButton rbline;
/*     */   Dataset dataset;
/*     */   private MoreButton lldatasource;
/*     */   private ColorComponent datasetOutline;
/*     */ 
/*     */   public void initCustomizer()
/*     */   {
/*  62 */     GridBagLayout gbl = new GridBagLayout();
/*  63 */     setLayout(gbl);
/*  64 */     GridBagConstraints gbc = new GridBagConstraints();
/*  65 */     gbc.insets = CommonFinal.INSETS;
/*  66 */     gbc.fill = 2;
/*  67 */     setLayout(gbl);
/*     */ 
/*  69 */     this.sclabel = new StringComponent("标签", null);
/*  70 */     this.sclabel.addChangeListener(this);
/*     */ 
/*  72 */     this.bshowlabel = new MoreButton("字体");
/*  73 */     this.bshowlabel.setActionCommand("label font");
/*  74 */     this.bshowlabel.addActionListener(this);
/*     */ 
/*  77 */     this.cccolor = new ColorComponent("颜色", null);
/*  78 */     this.cccolor.addChangeListener(this);
/*  79 */     this.datasetOutline = new ColorComponent("轮廓线", null);
/*  80 */     this.datasetOutline.addChangeListener(this);
/*  81 */     this.bdatasource = new MoreButton("填充样式");
/*  82 */     this.bdatasource.setActionCommand("fill style");
/*  83 */     this.bdatasource.addActionListener(this);
/*  84 */     this.lldatasource = new MoreButton("线形标记");
/*  85 */     this.lldatasource.setActionCommand("line style");
/*  86 */     this.lldatasource.addActionListener(this);
/*     */ 
/*  92 */     this.bg = new ButtonGroup();
/*  93 */     JPanel p = new JPanel();
/*  94 */     p.setLayout(gbl);
/*     */ 
/* 100 */     this.rbbar = new JRadioButton("柱形", false);
/* 101 */     this.rbbar.setHorizontalTextPosition(2);
/* 102 */     this.rbbar.addActionListener(this);
/* 103 */     this.rbline = new JRadioButton("线形", false);
/* 104 */     this.rbline.setHorizontalTextPosition(2);
/* 105 */     this.rbline.addActionListener(this);
/*     */ 
/* 108 */     p.add(this.rbbar, gbc);
/* 109 */     p.add(this.rbline, gbc);
/* 110 */     this.bg.add(this.rbbar);
/* 111 */     this.bg.add(this.rbline);
/*     */ 
/* 114 */     this.ldatasource = new JLabel("数据列");
/*     */ 
/* 116 */     this.ldatatype = new JLabel("数据类型");
/*     */ 
/* 119 */     this.cbdatasource = new JComboBox();
/* 120 */     for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 121 */       this.cbdatasource.addItem(this.chart.getDatasets()[i].getName());
/*     */     }
/* 123 */     this.cbdatasource.addItemListener(this);
/*     */ 
/* 127 */     SepratorPanel sp = new SepratorPanel("数据列设置");
/* 128 */     gbc.weightx = 1.0D;
/* 129 */     gbc.gridwidth = 0;
/* 130 */     add(sp, gbc);
/*     */ 
/* 133 */     gbc.weightx = 0.0D;
/* 134 */     gbc.gridwidth = 1;
/* 135 */     add(this.ldatasource, gbc);
/* 136 */     gbc.weightx = 1.0D;
/* 137 */     add(this.cbdatasource, gbc);
/* 138 */     gbc.weightx = 0.0D;
/* 139 */     gbc.gridwidth = 0;
/* 140 */     add(this.bshowlabel, gbc);
/*     */ 
/* 144 */     gbc.gridwidth = 2;
/* 145 */     gbc.weightx = 1.0D;
/* 146 */     add(this.cccolor, gbc);
/* 147 */     gbc.weightx = 0.0D;
/* 148 */     gbc.gridwidth = 0;
/* 149 */     add(this.bdatasource, gbc);
/* 150 */     gbc.gridwidth = 0;
/* 151 */     add(this.lldatasource, gbc);
/*     */ 
/* 155 */     gbc.weightx = 1.0D;
/* 156 */     gbc.gridwidth = 0;
/* 157 */     add(this.datasetOutline, gbc);
/*     */ 
/* 163 */     gbc.weightx = 1.0D;
/* 164 */     gbc.gridwidth = 0;
/* 165 */     add(this.sclabel, gbc);
/*     */ 
/* 173 */     gbc.gridwidth = 1;
/* 174 */     add(this.ldatatype, gbc);
/* 175 */     add(p, gbc);
/* 176 */     gbc.gridwidth = 0;
/* 177 */     add(Box.createGlue(), gbc);
/*     */ 
/* 180 */     gbc.weighty = 1.0D;
/* 181 */     add(Box.createGlue(), gbc);
/* 182 */     if (getDatasetType(this.dataset) == 0) {
/* 183 */       this.bdatasource.setVisible(true);
/* 184 */       this.lldatasource.setVisible(false);
/*     */     } else {
/* 186 */       this.bdatasource.setVisible(false);
/* 187 */       this.lldatasource.setVisible(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void show()
/*     */   {
/* 193 */     setVals();
/* 194 */     super.show();
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/* 201 */     this.dataset = ((Dataset)object);
/* 202 */     initCustomizer();
/*     */   }
/*     */ 
/*     */   public void getVals() {
/* 206 */     if (getDatasetType(this.dataset) == 0) {
/* 207 */       this.cccolor.getValue().setToGc(this.dataset.getGc());
/* 208 */       this.dataset.getGc().setLineColor(this.datasetOutline.getColor());
/*     */     } else {
/* 210 */       this.cccolor.getValue().setToGc(this.dataset.getGc());
/*     */     }
/*     */ 
/* 214 */     int i = this.dataset.getData().size();
/* 215 */     String[] labels = new String[i];
/* 216 */     if (this.sclabel.getValue() != null) {
/* 217 */       if (!this.sclabel.getValue().equals("")) {
/* 218 */         JavachartUtil.addArray(labels, ((String)this.sclabel.getValue()).split(","));
/*     */       }
/* 220 */       this.dataset.setLabels(labels);
/* 221 */       this.dataset.replaceLabels(labels);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 227 */     if (getDatasetType(this.dataset) == 0) {
/* 228 */       this.cccolor.setValue(FillStyleFactory.createFillStyle(this.dataset.getGc()));
/* 229 */       this.rbbar.setSelected(true);
/* 230 */       this.datasetOutline.setVisible(true);
/* 231 */       this.datasetOutline.setValue(this.dataset.getGc().getLineColor());
/* 232 */       this.bdatasource.setVisible(true);
/* 233 */       this.lldatasource.setVisible(false);
/*     */     } else {
/* 235 */       this.rbline.setSelected(true);
/* 236 */       this.datasetOutline.setVisible(false);
/* 237 */       LineStyle ls = new LineStyle(true);
/* 238 */       ls.getFromGc(this.dataset.getGc());
/* 239 */       this.cccolor.setValue(ls);
/* 240 */       this.bdatasource.setVisible(false);
/* 241 */       this.lldatasource.setVisible(true);
/*     */     }
/* 243 */     String[] s = new String[this.dataset.getData().size()];
/* 244 */     for (int i = 0; i < s.length; i++) {
/* 245 */       s[i] = this.dataset.getDataElementAt(i).getLabel();
/*     */     }
/* 247 */     this.sclabel.setValue(JavachartUtil.plus(s, ","));
/*     */   }
/*     */ 
/*     */   public void validateEnabled()
/*     */   {
/* 252 */     this.bshowlabel.setEnabled(this.chart.getDataRepresentation().getLabelsOn());
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 259 */     Component f = getParent();
/* 260 */     while (!(f instanceof JDialog)) {
/* 261 */       f = f.getParent();
/*     */     }
/* 263 */     if (e.getActionCommand().equals("fill style"))
/*     */     {
/* 265 */       FillStyleInterface style = FillStyleChooser.showDialog(f, 
/* 266 */         "填充样式", FillStyleFactory.createFillStyle(this.dataset.getGc()), 0);
/* 267 */       this.cccolor.setValue(style);
/* 268 */       style.setToGc(this.dataset.getGc());
/* 269 */     } else if (e.getActionCommand().equals("line style")) {
/* 270 */       LineStyle style = new LineStyle(true);
/* 271 */       style.getFromGc(this.dataset.getGc());
/* 272 */       style = (LineStyle)FillStyleChooser.showDialog(f, "线形标记样式", style, 1);
/* 273 */       style.setToGc(this.dataset.getGc());
/* 274 */       this.cccolor.setValue(style);
/*     */     } else {
/* 276 */       if (this.rbbar.isSelected()) {
/* 277 */         ((BarLineInterface)this.chart).setChartType(indexOfDatasets(this.dataset), 0);
/* 278 */         this.dataset.getGc().setFillColor(this.dataset.getGc().getLineColor());
/*     */       } else {
/* 280 */         ((BarLineInterface)this.chart).setChartType(indexOfDatasets(this.dataset), 1);
/* 281 */         this.dataset.getGc().setLineColor(this.dataset.getGc().getFillColor());
/*     */       }
/* 283 */       setVals();
/*     */     }
/* 285 */     if (this.parent != null)
/* 286 */       this.parent.fireChange(null);
/*     */   }
/*     */ 
/*     */   public void itemStateChanged(ItemEvent e)
/*     */   {
/* 294 */     this.dataset = this.chart.getDataset((String)this.cbdatasource.getSelectedItem());
/* 295 */     this.dataset.getGc().setOutlineFills(true);
/*     */ 
/* 297 */     setVals();
/* 298 */     if (this.parent != null)
/* 299 */       this.parent.fireChange(null);
/*     */   }
/*     */ 
/*     */   private int indexOfDatasets(Dataset dataset)
/*     */   {
/* 305 */     for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 306 */       if (dataset.getName().equals(this.chart.getDatasets()[i].getName())) {
/* 307 */         return i;
/*     */       }
/*     */     }
/* 310 */     return -1;
/*     */   }
/*     */ 
/*     */   private int getDatasetType(Dataset dataset) {
/* 314 */     int i = indexOfDatasets(dataset);
/* 315 */     if (i == -1) {
/* 316 */       return -1;
/*     */     }
/* 318 */     return ((BarLineInterface)this.chart).getChartType(indexOfDatasets(dataset));
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.BarLineDatasetPanel
 * JD-Core Version:    0.6.2
 */