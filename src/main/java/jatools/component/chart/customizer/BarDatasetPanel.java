/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.JavachartUtil;
/*     */ import jatools.component.chart.chart.AreaInterface;
/*     */ import jatools.component.chart.chart.CandlestickChart;
/*     */ import jatools.component.chart.chart.DataRepresentation;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.Datum;
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import jatools.component.chart.chart.HiLoCloseChart;
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
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ 
/*     */ public class BarDatasetPanel extends Dialog
/*     */   implements ActionListener, ItemListener
/*     */ {
/*     */   private JLabel ldatasource;
/*     */   private MoreButton bdatasource;
/*     */   protected StringComponent scdatalabel;
/*     */   protected JComboBox cbdatasource;
/*     */   private ColorComponent datasetColor;
/*     */   private ColorComponent datasetOutline;
/*     */   private JButton bshowlabel;
/*     */   Dataset dataset;
/*     */ 
/*     */   public void initCustomizer()
/*     */   {
/*  47 */     GridBagLayout gbl = new GridBagLayout();
/*  48 */     setLayout(gbl);
/*     */ 
/*  50 */     GridBagConstraints gbc = new GridBagConstraints();
/*  51 */     gbc.insets = CommonFinal.INSETS;
/*  52 */     gbc.fill = 2;
/*     */ 
/*  56 */     this.bshowlabel = new MoreButton("字体");
/*  57 */     this.bshowlabel.setActionCommand("label font");
/*  58 */     this.bshowlabel.addActionListener(this);
/*     */ 
/*  62 */     this.ldatasource = new JLabel("数据列");
/*  63 */     this.ldatasource.setPreferredSize(CommonFinal.LABEL_SIZE);
/*  64 */     this.bdatasource = new MoreButton("填充样式");
/*  65 */     this.bdatasource.setActionCommand("dataset fillstyle");
/*  66 */     this.bdatasource.addActionListener(this);
/*  67 */     this.scdatalabel = new StringComponent("标签", null);
/*  68 */     this.scdatalabel.addChangeListener(this);
/*  69 */     this.cbdatasource = new JComboBox();
/*  70 */     for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/*  71 */       this.cbdatasource.addItem(this.chart.getDatasets()[i].getName());
/*     */     }
/*  73 */     this.cbdatasource.addItemListener(this);
/*  74 */     this.datasetColor = new ColorComponent("颜色", null);
/*  75 */     this.datasetColor.addChangeListener(this);
/*  76 */     this.datasetOutline = new ColorComponent("轮廓线", null);
/*  77 */     this.datasetOutline.addChangeListener(this);
/*     */ 
/*  82 */     SepratorPanel sp = new SepratorPanel("数据列设置");
/*  83 */     gbc.weightx = 1.0D;
/*  84 */     gbc.gridwidth = 0;
/*  85 */     add(sp, gbc);
/*  86 */     gbc.weightx = 0.0D;
/*     */ 
/*  88 */     gbc.weightx = 0.0D;
/*  89 */     gbc.gridwidth = 1;
/*  90 */     add(this.ldatasource, gbc);
/*     */ 
/*  93 */     add(this.cbdatasource, gbc);
/*  94 */     add(this.bshowlabel, gbc);
/*  95 */     gbc.gridwidth = 0;
/*  96 */     add(Box.createGlue(), gbc);
/*     */ 
/* 101 */     gbc.weightx = 1.0D;
/* 102 */     gbc.gridwidth = 2;
/* 103 */     add(this.datasetColor, gbc);
/* 104 */     gbc.weightx = 0.0D;
/* 105 */     gbc.gridwidth = 1;
/* 106 */     add(this.bdatasource, gbc);
/* 107 */     gbc.gridwidth = 0;
/* 108 */     add(Box.createGlue(), gbc);
/*     */ 
/* 110 */     gbc.weightx = 1.0D;
/* 111 */     gbc.gridwidth = 0;
/* 112 */     add(this.datasetOutline, gbc);
/*     */ 
/* 117 */     add(this.scdatalabel, gbc);
/* 118 */     gbc.weighty = 1.0D;
/* 119 */     add(Box.createGlue(), gbc);
/*     */ 
/* 121 */     this.dataset.getGc().setOutlineFills(true);
/* 122 */     if (((this.chart instanceof AreaInterface)) || ((this.chart instanceof CandlestickChart)) || ((this.chart instanceof HiLoCloseChart)))
/* 123 */       this.scdatalabel.setVisible(false);
/*     */   }
/*     */ 
/*     */   public void getVals()
/*     */   {
/* 129 */     this.datasetColor.getValue().setToGc(this.dataset.getGc());
/* 130 */     this.dataset.getGc().setLineColor(this.datasetOutline.getColor());
/* 131 */     int i = this.dataset.getData().size();
/* 132 */     String[] labels = new String[i];
/* 133 */     if (this.scdatalabel.getValue() != null) {
/* 134 */       if (!this.scdatalabel.getValue().equals("")) {
/* 135 */         JavachartUtil.addArray(labels, ((String)this.scdatalabel.getValue()).split(","));
/*     */       }
/* 137 */       this.dataset.setLabels(labels);
/* 138 */       this.dataset.replaceLabels(labels);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 144 */     String[] s = new String[this.dataset.getData().size()];
/* 145 */     for (int i = 0; i < s.length; i++) {
/* 146 */       s[i] = this.dataset.getDataElementAt(i).getLabel();
/*     */     }
/* 148 */     this.scdatalabel.setValue(JavachartUtil.plus(s, ","));
/* 149 */     this.datasetColor.setValue(FillStyleFactory.createFillStyle(this.dataset.getGc()));
/* 150 */     this.datasetOutline.setValue(this.dataset.getGc().getLineColor());
/*     */   }
/*     */ 
/*     */   public void validateEnabled() {
/* 154 */     this.bshowlabel.setEnabled(this.chart.getDataRepresentation().getLabelsOn());
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 159 */     if (e.getActionCommand().equals("dataset fillstyle")) {
/* 160 */       Component f = getParent();
/* 161 */       while (!(f instanceof JDialog)) {
/* 162 */         f = f.getParent();
/*     */       }
/* 164 */       FillStyleInterface style = FillStyleChooser.showDialog(f, 
/* 165 */         "填充样式", FillStyleFactory.createFillStyle(this.dataset.getGc()), 0);
/* 166 */       this.datasetColor.setValue(style);
/* 167 */       style.setToGc(this.dataset.getGc());
/*     */     }
/* 169 */     else if ((e.getActionCommand().equals("label font")) && 
/* 170 */       (FontComponent.getDefault().showChooser(this))) {
/* 171 */       ChartFont font = (ChartFont)FontComponent.getDefault().getValue();
/* 172 */       Color color = font.getForeColor();
/* 173 */       Font ff = new Font(font.getFace(), font.getStyle(), font
/* 174 */         .getSize());
/* 175 */       this.dataset.setLabelColor(color);
/* 176 */       this.dataset.setLabelFont(ff);
/*     */     }
/*     */ 
/* 183 */     if (this.parent != null)
/* 184 */       this.parent.fireChange(null);
/*     */   }
/*     */ 
/*     */   public void itemStateChanged(ItemEvent e)
/*     */   {
/* 189 */     this.dataset = this.chart.getDataset((String)this.cbdatasource.getSelectedItem());
/* 190 */     this.dataset.getGc().setOutlineFills(true);
/* 191 */     setVals();
/*     */   }
/*     */ 
/*     */   public void setObject(Object object) {
/* 195 */     this.dataset = ((Dataset)object);
/* 196 */     initCustomizer();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.BarDatasetPanel
 * JD-Core Version:    0.6.2
 */