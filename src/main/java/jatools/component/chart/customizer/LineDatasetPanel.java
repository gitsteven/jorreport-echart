/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.JavachartUtil;
/*     */ import jatools.component.chart.chart.DataRepresentation;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.Datum;
/*     */ import jatools.component.chart.chart.HiLoCloseChart;
/*     */ import jatools.component.chart.chart._Chart;
/*     */ import jatools.component.chart.component.ChartFont;
/*     */ import jatools.component.chart.component.ColorComponent;
/*     */ import jatools.component.chart.component.FillStyleChooser;
/*     */ import jatools.component.chart.component.FillStyleInterface;
/*     */ import jatools.component.chart.component.FontComponent;
/*     */ import jatools.component.chart.component.LineStyle;
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
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ 
/*     */ public class LineDatasetPanel extends Dialog
/*     */   implements ActionListener, ItemListener
/*     */ {
/*     */   private JLabel ldatasource;
/*     */   private JComboBox cbdatasource;
/*     */   private ColorComponent cccolor;
/*     */   private StringComponent sclabel;
/*     */   private JButton bdatasource;
/*     */   private JButton bshowlabel;
/*     */   Dataset dataset;
/*     */ 
/*     */   public void initCustomizer()
/*     */   {
/*  56 */     GridBagLayout gbl = new GridBagLayout();
/*  57 */     setLayout(gbl);
/*  58 */     GridBagConstraints gbc = new GridBagConstraints();
/*  59 */     gbc.insets = CommonFinal.INSETS;
/*  60 */     gbc.fill = 2;
/*     */ 
/*  62 */     this.sclabel = new StringComponent("标签", null);
/*  63 */     this.sclabel.addChangeListener(this);
/*     */ 
/*  65 */     this.cccolor = new ColorComponent("颜色", null);
/*  66 */     this.cccolor.addChangeListener(this);
/*     */ 
/*  68 */     this.bshowlabel = new MoreButton("字体");
/*  69 */     this.bshowlabel.setActionCommand("label font");
/*  70 */     this.bshowlabel.addActionListener(this);
/*     */ 
/*  72 */     this.bdatasource = new MoreButton("线形标记");
/*  73 */     this.bdatasource.setActionCommand("line style");
/*  74 */     this.bdatasource.addActionListener(this);
/*     */ 
/*  76 */     this.ldatasource = new JLabel("数据列");
/*  77 */     this.ldatasource.setPreferredSize(CommonFinal.LABEL_SIZE);
/*     */ 
/*  79 */     this.cbdatasource = new JComboBox();
/*  80 */     for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/*  81 */       this.cbdatasource.addItem(this.chart.getDatasets()[i].getName());
/*     */     }
/*  83 */     this.cbdatasource.addItemListener(this);
/*     */ 
/*  86 */     SepratorPanel sp = new SepratorPanel("数据列设置");
/*  87 */     gbc.weightx = 1.0D;
/*  88 */     gbc.gridwidth = 0;
/*  89 */     add(sp, gbc);
/*  90 */     gbc.weightx = 0.0D;
/*     */ 
/*  92 */     gbc.gridwidth = 1;
/*  93 */     add(this.ldatasource, gbc);
/*  94 */     gbc.weightx = 1.0D;
/*     */ 
/*  96 */     add(this.cbdatasource, gbc);
/*  97 */     gbc.weightx = 0.0D;
/*  98 */     add(this.bshowlabel, gbc);
/*  99 */     gbc.gridwidth = 0;
/* 100 */     add(Box.createGlue(), gbc);
/*     */ 
/* 103 */     gbc.gridwidth = 2;
/* 104 */     add(this.cccolor, gbc);
/* 105 */     gbc.weightx = 0.0D;
/* 106 */     add(this.bdatasource, gbc);
/* 107 */     gbc.gridwidth = 0;
/* 108 */     add(Box.createGlue(), gbc);
/*     */ 
/* 111 */     gbc.gridwidth = 0;
/* 112 */     add(this.sclabel, gbc);
/*     */ 
/* 114 */     gbc.weighty = 1.0D;
/* 115 */     add(Box.createGlue(), gbc);
/*     */ 
/* 117 */     if ((this.chart instanceof HiLoCloseChart))
/* 118 */       this.sclabel.setVisible(false);
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/* 129 */     this.dataset = ((Dataset)object);
/* 130 */     initCustomizer();
/*     */   }
/*     */ 
/*     */   public void getVals() {
/* 134 */     this.cccolor.getValue().setToGc(this.dataset.getGc());
/* 135 */     int i = this.dataset.getData().size();
/* 136 */     String[] labels = new String[i];
/* 137 */     if (this.sclabel.getValue() != null) {
/* 138 */       if (!this.sclabel.getValue().equals("")) {
/* 139 */         JavachartUtil.addArray(labels, 
/* 140 */           ((String)this.sclabel.getValue()).split(","));
/*     */       }
/* 142 */       this.dataset.setLabels(labels);
/* 143 */       this.dataset.replaceLabels(labels);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setVals() {
/* 148 */     String[] s = new String[this.dataset.getData().size()];
/* 149 */     for (int i = 0; i < s.length; i++) {
/* 150 */       s[i] = this.dataset.getDataElementAt(i).getLabel();
/*     */     }
/* 152 */     this.sclabel.setValue(JavachartUtil.plus(s, ","));
/*     */ 
/* 156 */     LineStyle style = new LineStyle(true);
/*     */ 
/* 158 */     style.getFromGc(this.dataset.getGc());
/* 159 */     this.cccolor.setValue(style);
/*     */   }
/*     */ 
/*     */   public void validateEnabled() {
/* 163 */     this.bshowlabel.setEnabled(this.chart.getDataRepresentation().getLabelsOn());
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 173 */     if (e.getActionCommand().equals("line style")) {
/* 174 */       Component f = getParent();
/* 175 */       while (!(f instanceof JDialog)) {
/* 176 */         f = f.getParent();
/*     */       }
/*     */ 
/* 180 */       FillStyleInterface style = new LineStyle(true);
/*     */ 
/* 182 */       style.getFromGc(this.dataset.getGc());
/* 183 */       style = FillStyleChooser.showDialog(f, "填充样式", style, 1);
/* 184 */       this.cccolor.setValue(style);
/* 185 */       style.setToGc(this.dataset.getGc());
/* 186 */     } else if ((e.getActionCommand().equals("label font")) && 
/* 187 */       (FontComponent.getDefault().showChooser(this))) {
/* 188 */       ChartFont font = (ChartFont)FontComponent.getDefault().getValue();
/* 189 */       Color color = font.getForeColor();
/* 190 */       Font ff = new Font(font.getFace(), font.getStyle(), 
/* 191 */         font.getSize());
/*     */ 
/* 197 */       this.dataset.setLabelColor(color);
/* 198 */       this.dataset.setLabelFont(ff);
/*     */     }
/*     */ 
/* 201 */     if (this.parent != null)
/* 202 */       this.parent.fireChange(null);
/*     */   }
/*     */ 
/*     */   public void itemStateChanged(ItemEvent e)
/*     */   {
/* 213 */     this.dataset = this.chart.getDataset((String)this.cbdatasource.getSelectedItem());
/* 214 */     setVals();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.LineDatasetPanel
 * JD-Core Version:    0.6.2
 */