/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Axis;
/*     */ import jatools.component.chart.chart.AxisInterface;
/*     */ import jatools.component.chart.chart.DateAxis;
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import jatools.component.chart.chart.LabelAxis;
/*     */ import jatools.component.chart.component.AbstractComponent;
/*     */ import jatools.component.chart.component.ChartFont;
/*     */ import jatools.component.chart.component.CheckComponent;
/*     */ import jatools.component.chart.component.ColorComponent;
/*     */ import jatools.component.chart.component.FillStyleChooser;
/*     */ import jatools.component.chart.component.FillStyleInterface;
/*     */ import jatools.component.chart.component.FontComponent;
/*     */ import jatools.component.chart.component.FormatComponent;
/*     */ import jatools.component.chart.component.IntComponent;
/*     */ import jatools.component.chart.component.LineStyle;
/*     */ import jatools.component.chart.component.MoreButton;
/*     */ import jatools.component.chart.component.StringComponent;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class AxisPanel extends Dialog
/*     */   implements ActionListener, ItemListener
/*     */ {
/*     */   StringComponent scaxistitle;
/*     */   CheckComponent ckshowlabel;
/*     */   CheckComponent ckshowgrid;
/*     */   ColorComponent ccaxis;
/*     */   ColorComponent ccgrid;
/*     */   ColorComponent ccscale;
/*     */   JButton faxistitle;
/*     */   JComboBox caxisposition;
/*     */   JPanel p;
/*     */   JLabel axisposition;
/*     */   AxisInterface axis;
/*     */   private MoreButton faxislabel;
/*     */   int topBottomOffset;
/*     */   private IntComponent axisAngle;
/*     */   private MoreButton bscale;
/*     */   private CheckComponent rotator;
/*     */   private AbstractComponent dataFormat;
/*     */   private JPanel p3;
/*     */   private MoreButton blinestyle;
/*     */ 
/*     */   public void initCustomizer()
/*     */   {
/*  78 */     GridBagLayout gbl = new GridBagLayout();
/*  79 */     setLayout(gbl);
/*  80 */     setBorder(CommonFinal.EMPTY_BORDER);
/*     */ 
/*  82 */     GridBagConstraints gbc = new GridBagConstraints();
/*  83 */     gbc.insets = CommonFinal.INSETS;
/*  84 */     gbc.fill = 2;
/*     */ 
/*  86 */     this.scaxistitle = new StringComponent("轴标题", null);
/*  87 */     this.scaxistitle.addChangeListener(this);
/*     */ 
/*  89 */     this.ckshowlabel = new CheckComponent("显示标签", false);
/*  90 */     this.ckshowlabel.addChangeListener(this);
/*  91 */     this.ckshowgrid = new CheckComponent("显示网格线", false);
/*  92 */     this.ckshowgrid.addChangeListener(this);
/*     */ 
/*  94 */     this.ccaxis = new ColorComponent("轴线颜色", null);
/*  95 */     this.ccaxis.addChangeListener(this);
/*  96 */     this.ccgrid = new ColorComponent("网格线颜色", null);
/*  97 */     this.ccgrid.addChangeListener(this);
/*  98 */     this.ccscale = new ColorComponent("主刻度颜色", null);
/*  99 */     this.ccscale.addChangeListener(this);
/* 100 */     this.axisAngle = new IntComponent("标签角度", 0, -90, 89, "度");
/* 101 */     this.axisAngle.addChangeListener(this);
/*     */ 
/* 103 */     this.faxistitle = new MoreButton("字体");
/* 104 */     this.faxistitle.setActionCommand("title font");
/* 105 */     this.faxistitle.addActionListener(this);
/*     */ 
/* 107 */     this.faxislabel = new MoreButton("字体");
/* 108 */     this.faxislabel.setActionCommand("label font");
/* 109 */     this.faxislabel.addActionListener(this);
/*     */ 
/* 111 */     this.rotator = new CheckComponent("标题反转", false);
/* 112 */     this.rotator.addChangeListener(this);
/*     */ 
/* 114 */     if ((this.axis instanceof DateAxis))
/* 115 */       this.dataFormat = FormatComponent.getDateFormatInstance("日期格式");
/*     */     else {
/* 117 */       this.dataFormat = FormatComponent.getDecimalFormatInstance("数据格式");
/*     */     }
/*     */ 
/* 120 */     this.dataFormat.addChangeListener(this);
/*     */ 
/* 122 */     this.bscale = new MoreButton("轴缩放");
/* 123 */     this.bscale.setActionCommand("axis scale");
/* 124 */     this.bscale.addActionListener(this);
/*     */ 
/* 129 */     this.blinestyle = new MoreButton("线形样式");
/* 130 */     this.blinestyle.setActionCommand("line style");
/* 131 */     this.blinestyle.addActionListener(this);
/*     */ 
/* 133 */     this.caxisposition = new JComboBox();
/*     */ 
/* 135 */     if ((this.axis.getSide() == 0) || (this.axis.getSide() == 2)) {
/* 136 */       this.caxisposition.addItem("底部");
/* 137 */       this.caxisposition.addItem("顶部");
/* 138 */       this.topBottomOffset = 0;
/*     */     } else {
/* 140 */       this.caxisposition.addItem("左边");
/* 141 */       this.caxisposition.addItem("右边");
/* 142 */       this.topBottomOffset = 1;
/*     */     }
/*     */ 
/* 145 */     this.caxisposition.addItemListener(this);
/*     */ 
/* 147 */     this.p = new JPanel();
/*     */ 
/* 149 */     this.p3 = new JPanel();
/*     */ 
/* 153 */     gbc.weightx = 1.0D;
/*     */ 
/* 155 */     add(this.scaxistitle, gbc);
/* 156 */     gbc.weightx = 0.0D;
/* 157 */     gbc.gridwidth = 0;
/* 158 */     add(this.faxistitle, gbc);
/*     */ 
/* 160 */     gbc.weightx = 1.0D;
/* 161 */     gbc.gridwidth = 1;
/* 162 */     add(this.rotator, gbc);
/* 163 */     gbc.weightx = 0.0D;
/* 164 */     gbc.gridwidth = 0;
/* 165 */     add(this.bscale, gbc);
/*     */ 
/* 168 */     gbc.weightx = 1.0D;
/* 169 */     gbc.gridwidth = 1;
/* 170 */     add(this.ckshowlabel, gbc);
/* 171 */     gbc.weightx = 0.0D;
/* 172 */     gbc.gridwidth = 0;
/* 173 */     add(this.faxislabel, gbc);
/*     */ 
/* 175 */     gbc.gridwidth = 0;
/* 176 */     gbc.weightx = 1.0D;
/* 177 */     add(this.axisAngle, gbc);
/*     */ 
/* 179 */     gbc.gridwidth = 0;
/* 180 */     gbc.weightx = 1.0D;
/* 181 */     add(this.dataFormat, gbc);
/*     */ 
/* 184 */     gbc.weightx = 1.0D;
/* 185 */     gbc.gridwidth = 0;
/* 186 */     add(this.ccaxis, gbc);
/*     */ 
/* 188 */     gbc.weightx = 1.0D;
/* 189 */     add(this.ccscale, gbc);
/*     */ 
/* 192 */     this.axisposition = new JLabel("轴位置");
/* 193 */     this.axisposition.setPreferredSize(CommonFinal.SHORT_LABEL_SIZE);
/* 194 */     this.p.setLayout(new GridBagLayout());
/* 195 */     gbc.weightx = 0.0D;
/* 196 */     gbc.gridwidth = 1;
/* 197 */     this.p.add(this.axisposition, gbc);
/* 198 */     gbc.weightx = 1.0D;
/* 199 */     gbc.gridwidth = 0;
/* 200 */     this.p.add(this.caxisposition, gbc);
/*     */ 
/* 202 */     add(this.p, gbc);
/*     */ 
/* 204 */     add(this.ckshowgrid, gbc);
/*     */ 
/* 206 */     gbc.weightx = 1.0D;
/* 207 */     gbc.gridwidth = 1;
/* 208 */     add(this.ccgrid, gbc);
/* 209 */     gbc.weightx = 0.0D;
/* 210 */     gbc.gridwidth = 0;
/* 211 */     add(this.blinestyle, gbc);
/*     */ 
/* 216 */     gbc.weighty = 1.0D;
/* 217 */     add(Box.createGlue(), gbc);
/*     */ 
/* 219 */     if (this.axis.isXAxis()) {
/* 220 */       this.bscale.setEnabled(false);
/*     */ 
/* 223 */       if ((this.axis instanceof LabelAxis)) {
/* 224 */         this.dataFormat.setEnabled(false);
/*     */       }
/*     */     }
/*     */ 
/* 228 */     if ((this.axis.getSide() == 0) || (this.axis.getSide() == 2))
/* 229 */       this.rotator.setEnabled(false);
/*     */   }
/*     */ 
/*     */   public void show()
/*     */   {
/* 237 */     setVals();
/* 238 */     super.show();
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 252 */     if (e.getActionCommand().equals("axis scale")) {
/* 253 */       Component c = getParent();
/*     */ 
/* 255 */       while (!(c instanceof JDialog)) {
/* 256 */         c = c.getParent();
/*     */       }
/*     */ 
/* 259 */       AxisScaleDialog.showDialog(c, "轴缩放设置", this.axis, this);
/* 260 */     } else if (e.getActionCommand().equals("line style")) {
/* 261 */       Component c = getParent();
/*     */ 
/* 263 */       while (!(c instanceof JDialog)) {
/* 264 */         c = c.getParent();
/*     */       }
/*     */ 
/* 267 */       FillStyleInterface style = new LineStyle(false);
/* 268 */       style.getFromGc(this.axis.getGridGc());
/* 269 */       style = FillStyleChooser.showDialog(c, "线形样式", style, 1);
/*     */ 
/* 271 */       this.ccgrid.setValue(style);
/* 272 */       style.setToGc(this.axis.getGridGc());
/* 273 */       fireChange(null);
/* 274 */     } else if (((e.getActionCommand().equals("title font")) || 
/* 275 */       (e.getActionCommand().equals("label font"))) && 
/* 276 */       (FontComponent.getDefault().showChooser(this))) {
/* 277 */       ChartFont font = (ChartFont)FontComponent.getDefault().getValue();
/* 278 */       Color color = font.getForeColor();
/* 279 */       Font f = new Font(font.getFace(), font.getStyle(), font.getSize());
/*     */ 
/* 281 */       if (e.getActionCommand().equals("title font")) {
/* 282 */         this.axis.setTitleColor(color);
/* 283 */         this.axis.setTitleFont(f);
/* 284 */       } else if (e.getActionCommand().equals("label font")) {
/* 285 */         this.axis.setLabelColor(color);
/* 286 */         this.axis.setLabelFont(f);
/*     */       }
/*     */ 
/* 289 */       fireChange(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/* 305 */     this.axis = ((AxisInterface)object);
/* 306 */     initCustomizer();
/*     */   }
/*     */ 
/*     */   public void getVals()
/*     */   {
/* 318 */     this.axis.setTitleString((String)this.scaxistitle.getValue());
/* 319 */     this.axis.setLabelVis(this.ckshowlabel.getValue());
/* 320 */     this.axis.getLineGc().setLineColor(this.ccaxis.getColor());
/* 321 */     this.axis.setGridVis(this.ckshowgrid.getValue());
/*     */ 
/* 323 */     this.ccgrid.getValue().setToGc(this.axis.getGridGc());
/* 324 */     this.axis.getTickGc().setLineColor(this.ccscale.getColor());
/* 325 */     this.axis.setSide(this.caxisposition.getSelectedIndex() * 2 + this.topBottomOffset);
/* 326 */     this.axis.setLabelAngle(this.axisAngle.getValue());
/* 327 */     this.axis.setTitleRotated(this.rotator.getValue());
/*     */ 
/* 339 */     ((Axis)this.axis).setPattern((String)this.dataFormat.getValue());
/*     */ 
/* 341 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 353 */     this.scaxistitle.setValue(this.axis.getTitleString());
/* 354 */     this.ckshowlabel.setValue(this.axis.getLabelVis());
/* 355 */     this.ccaxis.setValue(this.axis.getLineGc().getLineColor());
/* 356 */     this.ckshowgrid.setValue(this.axis.getGridVis());
/*     */ 
/* 358 */     LineStyle style = new LineStyle(false);
/* 359 */     style.getFromGc(this.axis.getGridGc());
/* 360 */     this.ccgrid.setValue(style);
/* 361 */     this.ccscale.setValue(this.axis.getTickGc().getLineColor());
/*     */ 
/* 363 */     this.axisAngle.setValue(this.axis.getLabelAngle());
/* 364 */     this.rotator.setValue(this.axis.isTitleRotated());
/*     */ 
/* 375 */     this.dataFormat.setValue(((Axis)this.axis).getPattern());
/* 376 */     this.caxisposition.setSelectedIndex((this.axis.getSide() - this.topBottomOffset) / 2);
/* 377 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   private void volidateEnabled() {
/* 381 */     this.axisAngle.setEnabled(this.ckshowlabel.getValue());
/* 382 */     this.faxislabel.setEnabled(this.ckshowlabel.getValue());
/* 383 */     this.ccgrid.setEnabled(this.ckshowgrid.getValue());
/* 384 */     this.blinestyle.setEnabled(this.ckshowgrid.getValue());
/*     */ 
/* 386 */     if ((this.axis.getTitleString() == null) || (this.axis.getTitleString().equals(""))) {
/* 387 */       this.faxistitle.setEnabled(false);
/* 388 */       this.rotator.setEnabled(false);
/*     */     } else {
/* 390 */       this.faxistitle.setEnabled(true);
/* 391 */       this.rotator.setEnabled((this.axis.getSide() == 1) || (this.axis.getSide() == 3));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 401 */     JFrame f = new JFrame();
/* 402 */     AxisPanel ip = new AxisPanel();
/* 403 */     f.getContentPane().add(ip);
/* 404 */     f.setSize(600, 500);
/* 405 */     f.show();
/*     */   }
/*     */ 
/*     */   public void itemStateChanged(ItemEvent e)
/*     */   {
/* 414 */     fireChange(null);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.AxisPanel
 * JD-Core Version:    0.6.2
 */