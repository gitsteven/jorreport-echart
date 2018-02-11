/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.Datum;
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import jatools.component.chart.chart.Pie;
/*     */ import jatools.component.chart.chart.PieChart;
/*     */ import jatools.component.chart.component.AbstractComponent;
/*     */ import jatools.component.chart.component.ChartFont;
/*     */ import jatools.component.chart.component.CheckComponent;
/*     */ import jatools.component.chart.component.ColorComponent;
/*     */ import jatools.component.chart.component.FillStyleChooser;
/*     */ import jatools.component.chart.component.FillStyleFactory;
/*     */ import jatools.component.chart.component.FillStyleInterface;
/*     */ import jatools.component.chart.component.FontComponent;
/*     */ import jatools.component.chart.component.FormatComponent;
/*     */ import jatools.component.chart.component.IntComponent;
/*     */ import jatools.component.chart.component.MoreButton;
/*     */ import jatools.component.chart.component.RangeComponent;
/*     */ import jatools.component.chart.component.StringComponent;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class PieOption extends Dialog
/*     */   implements ActionListener
/*     */ {
/*     */   CheckComponent ckshowlabel;
/*     */   CheckComponent ckshowvalue;
/*     */   CheckComponent ckpercentage;
/*     */   IntComponent rcpieangle;
/*     */   RangeComponent rcpiewidth;
/*     */   RangeComponent rcpieapart;
/*     */   StringComponent sclabel;
/*     */   ColorComponent cccolor;
/*     */   ColorComponent ccoutline;
/*     */   JLabel llabelposition;
/*     */   JLabel ldatasource;
/*     */   JComboBox clabelposition;
/*     */   JComboBox cdatasource;
/*     */   JPanel p;
/*     */   JButton bshowlabel;
/*     */   JButton bdatasource;
/*     */   private Pie pie;
/*     */   private JComboBox clabelPosition;
/*     */   private Datum datum;
/*     */   private AbstractComponent dataFormat;
/*     */ 
/*     */   public PieOption()
/*     */   {
/*  78 */     GridBagLayout gbl = new GridBagLayout();
/*  79 */     setLayout(gbl);
/*  80 */     setBorder(CommonFinal.EMPTY_BORDER);
/*  81 */     GridBagConstraints gbc = new GridBagConstraints();
/*  82 */     gbc.insets = CommonFinal.INSETS;
/*  83 */     gbc.fill = 2;
/*     */ 
/*  85 */     this.ckshowlabel = new CheckComponent("显示标签", false);
/*  86 */     this.ckshowlabel.addChangeListener(this);
/*  87 */     this.ckshowvalue = new CheckComponent("显示值", false);
/*  88 */     this.ckshowvalue.addChangeListener(this);
/*  89 */     this.ckpercentage = new CheckComponent("使用百分比", false);
/*  90 */     this.ckpercentage.addChangeListener(this);
/*     */ 
/*  92 */     this.rcpieangle = new IntComponent("饼形角度", 0, 0, 359, "度");
/*  93 */     this.rcpieangle.addChangeListener(this);
/*  94 */     this.rcpiewidth = new RangeComponent("饼形宽度", 0.0D);
/*  95 */     this.rcpiewidth.addChangeListener(this);
/*  96 */     this.rcpieapart = new RangeComponent("分离", 0.0D);
/*  97 */     this.rcpieapart.addChangeListener(this);
/*     */ 
/*  99 */     this.cccolor = new ColorComponent("颜色", null);
/* 100 */     this.cccolor.addChangeListener(this);
/* 101 */     this.ccoutline = new ColorComponent("轮廓线", null);
/* 102 */     this.ccoutline.addChangeListener(this);
/*     */ 
/* 104 */     this.sclabel = new StringComponent("标签", null);
/* 105 */     this.sclabel.addChangeListener(this);
/*     */ 
/* 107 */     this.dataFormat = FormatComponent.getDecimalFormatInstance("数据样式");
/* 108 */     this.dataFormat.addChangeListener(this);
/*     */ 
/* 110 */     this.llabelposition = new JLabel("标签位置");
/* 111 */     this.llabelposition.setPreferredSize(CommonFinal.LABEL_SIZE);
/* 112 */     this.ldatasource = new JLabel("数据集");
/* 113 */     this.ldatasource.setPreferredSize(CommonFinal.LABEL_SIZE);
/* 114 */     this.clabelPosition = new JComboBox();
/* 115 */     this.clabelPosition.addItem("中间");
/* 116 */     this.clabelPosition.addItem("边缘");
/* 117 */     this.clabelPosition.addItem("外面");
/* 118 */     this.clabelPosition.setActionCommand("pie label position");
/* 119 */     this.clabelPosition.addActionListener(this);
/* 120 */     this.cdatasource = new JComboBox();
/* 121 */     this.cdatasource.setActionCommand("datasource");
/* 122 */     this.cdatasource.addActionListener(this);
/*     */ 
/* 124 */     this.bshowlabel = new MoreButton("字体");
/* 125 */     this.bshowlabel.setActionCommand("label font");
/* 126 */     this.bshowlabel.addActionListener(this);
/* 127 */     this.bdatasource = new MoreButton("填充样式");
/* 128 */     this.bdatasource.setActionCommand("fill style");
/* 129 */     this.bdatasource.addActionListener(this);
/*     */ 
/* 131 */     this.p = new JPanel();
/*     */ 
/* 133 */     this.p.setLayout(gbl);
/*     */ 
/* 136 */     gbc.gridwidth = 1;
/* 137 */     gbc.weightx = 1.0D;
/* 138 */     add(this.ckshowlabel, gbc);
/* 139 */     gbc.weightx = 0.0D;
/* 140 */     gbc.gridwidth = 1;
/* 141 */     add(this.bshowlabel, gbc);
/* 142 */     gbc.gridwidth = 0;
/* 143 */     add(Box.createGlue(), gbc);
/*     */ 
/* 146 */     gbc.gridwidth = 1;
/* 147 */     add(this.llabelposition, gbc);
/* 148 */     gbc.weightx = 1.0D;
/* 149 */     gbc.gridwidth = 0;
/* 150 */     add(this.clabelPosition, gbc);
/*     */ 
/* 153 */     gbc.gridwidth = 1;
/* 154 */     add(this.ckshowvalue, gbc);
/*     */ 
/* 156 */     gbc.gridwidth = 0;
/* 157 */     add(this.rcpieangle, gbc);
/*     */ 
/* 160 */     gbc.gridwidth = 1;
/* 161 */     add(this.ckpercentage, gbc);
/* 162 */     gbc.gridwidth = 0;
/* 163 */     add(this.rcpiewidth, gbc);
/*     */ 
/* 165 */     add(this.dataFormat, gbc);
/*     */ 
/* 168 */     gbc.gridwidth = 1;
/* 169 */     gbc.weightx = 0.0D;
/*     */ 
/* 172 */     SepratorPanel sp = new SepratorPanel("数据集设置");
/* 173 */     gbc.weightx = 1.0D;
/* 174 */     gbc.gridwidth = 0;
/* 175 */     add(sp, gbc);
/* 176 */     gbc.weightx = 0.0D;
/* 177 */     gbc.gridwidth = 1;
/*     */ 
/* 180 */     this.p.add(this.ldatasource, gbc);
/* 181 */     gbc.weightx = 1.0D;
/* 182 */     gbc.gridwidth = 0;
/* 183 */     this.p.add(this.cdatasource, gbc);
/*     */ 
/* 186 */     gbc.gridwidth = 0;
/* 187 */     this.p.add(this.sclabel, gbc);
/*     */ 
/* 190 */     gbc.gridwidth = 2;
/* 191 */     gbc.weightx = 1.0D;
/* 192 */     this.p.add(this.cccolor, gbc);
/* 193 */     gbc.weightx = 0.0D;
/* 194 */     this.p.add(this.bdatasource, gbc);
/* 195 */     gbc.gridwidth = 0;
/* 196 */     this.p.add(Box.createGlue(), gbc);
/*     */ 
/* 199 */     gbc.weightx = 1.0D;
/* 200 */     this.p.add(this.ccoutline, gbc);
/*     */ 
/* 203 */     this.p.add(this.rcpieapart, gbc);
/*     */ 
/* 205 */     gbc.weightx = 1.0D;
/* 206 */     add(this.p, gbc);
/*     */ 
/* 208 */     gbc.weighty = 1.0D;
/* 209 */     add(Box.createGlue(), gbc);
/*     */   }
/*     */ 
/*     */   public void show()
/*     */   {
/* 214 */     setVals();
/* 215 */     super.show();
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/* 224 */     this.pie = ((PieChart)this.chart).getPie();
/* 225 */     this.datum = ((PieChart)this.chart).getDatasets()[0].getDataElementAt(0);
/* 226 */     this.cdatasource.removeAllItems();
/* 227 */     for (int i = 0; i < ((PieChart)this.chart).getDatasets()[0].getData()
/* 228 */       .size(); i++) {
/* 229 */       this.cdatasource.addItem("数据项" + i);
/* 230 */       this.chart.getDatasets()[0].getDataElementAt(i).getGc().setOutlineFills(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void getVals()
/*     */   {
/* 240 */     this.pie.setTextLabelsOn(this.ckshowlabel.getValue());
/* 241 */     this.pie.setValueLabelsOn(this.ckshowvalue.getValue());
/* 242 */     this.pie.setPercentLabelsOn(this.ckpercentage.getValue());
/*     */ 
/* 244 */     this.pie.setStartDegrees(this.rcpieangle.getValue());
/* 245 */     this.pie.setWidth(this.rcpiewidth.getValue());
/* 246 */     this.pie.setHeight(this.rcpiewidth.getValue());
/* 247 */     this.pie.setLabelPosition(this.clabelPosition.getSelectedIndex());
/*     */ 
/* 251 */     this.datum = ((PieChart)this.chart).getDatasets()[0]
/* 252 */       .getDataElementAt(this.cdatasource.getSelectedIndex());
/* 253 */     if (this.sclabel.getValue().equals(""))
/* 254 */       this.datum.setLabel(null);
/*     */     else {
/* 256 */       this.datum.setLabel((String)this.sclabel.getValue());
/*     */     }
/*     */ 
/* 259 */     this.cccolor.getValue().setToGc(this.datum.getGc());
/*     */ 
/* 261 */     this.datum.getGc().setLineColor(this.ccoutline.getColor());
/* 262 */     this.datum.setY2(this.rcpieapart.getValue());
/*     */ 
/* 267 */     this.pie.setPattern((String)this.dataFormat.getValue());
/*     */ 
/* 269 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 278 */     this.ckshowlabel.setValue(this.pie.getTextLabelsOn());
/* 279 */     this.ckshowvalue.setValue(this.pie.getValueLabelsOn());
/* 280 */     this.ckpercentage.setValue(this.pie.getPercentLabelsOn());
/*     */ 
/* 282 */     this.rcpieangle.setValue(this.pie.getStartDegrees());
/*     */ 
/* 286 */     this.cdatasource.removeActionListener(this);
/* 287 */     this.cdatasource.setSelectedIndex(((PieChart)this.chart).getDatasets()[0]
/* 288 */       .getData().indexOf(this.datum));
/* 289 */     this.cdatasource.addActionListener(this);
/*     */ 
/* 291 */     this.sclabel.setValue(this.datum.getLabel());
/*     */ 
/* 293 */     this.cccolor.setValue(FillStyleFactory.createFillStyle(this.datum.getGc()));
/*     */ 
/* 295 */     this.ccoutline.setValue(this.datum.getGc().getLineColor());
/* 296 */     this.rcpieapart.setValue(this.datum.getY2());
/*     */ 
/* 298 */     this.rcpiewidth.setValue(this.pie.getWidth());
/*     */ 
/* 300 */     this.dataFormat.setValue(this.pie.getPattern());
/* 301 */     this.clabelPosition.setSelectedIndex(this.pie.getLabelPosition());
/*     */ 
/* 303 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   private void volidateEnabled() {
/* 307 */     this.ckshowvalue.setEnabled(this.ckshowlabel.getValue());
/* 308 */     this.dataFormat.setEnabled(this.ckshowvalue.getValue());
/* 309 */     this.bshowlabel.setEnabled(this.ckshowlabel.getValue());
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 319 */     if (e.getActionCommand().equals("label font")) {
/* 320 */       if (FontComponent.getDefault().showChooser(this)) {
/* 321 */         ChartFont font = (ChartFont)FontComponent.getDefault().getValue();
/* 322 */         Color color = font.getForeColor();
/* 323 */         Font ff = new Font(font.getFace(), font.getStyle(), font
/* 324 */           .getSize());
/* 325 */         this.pie.setLabelColor(color);
/* 326 */         this.pie.setLabelFont(ff);
/*     */       }
/* 328 */     } else if (e.getActionCommand().equals("fill style")) {
/* 329 */       Component f = getParent();
/* 330 */       while (!(f instanceof JDialog)) {
/* 331 */         f = f.getParent();
/*     */       }
/* 333 */       FillStyleInterface style = FillStyleChooser.showDialog(f, "填充样式", 
/* 334 */         FillStyleFactory.createFillStyle(this.datum.getGc()), 0);
/* 335 */       this.cccolor.setValue(style);
/* 336 */       style.setToGc(this.datum.getGc());
/* 337 */     } else if (e.getActionCommand().equals("datasource")) {
/* 338 */       this.datum = this.chart.getDatasets()[0].getDataElementAt(this.cdatasource
/* 339 */         .getSelectedIndex());
/* 340 */       this.datum.getGc().setOutlineFills(true);
/* 341 */       setVals();
/* 342 */     } else if (e.getActionCommand().equals("pie label position")) {
/* 343 */       this.pie.setLabelPosition(this.clabelPosition.getSelectedIndex());
/*     */     }
/* 345 */     if (this.parent != null)
/* 346 */       this.parent.fireChange(null);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.PieOption
 * JD-Core Version:    0.6.2
 */