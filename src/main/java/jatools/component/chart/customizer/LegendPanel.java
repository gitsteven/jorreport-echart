/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import jatools.component.chart.chart.Legend;
/*     */ import jatools.component.chart.chart._Chart;
/*     */ import jatools.component.chart.component.ChartFont;
/*     */ import jatools.component.chart.component.CheckComponent;
/*     */ import jatools.component.chart.component.ColorComponent;
/*     */ import jatools.component.chart.component.FillStyleChooser;
/*     */ import jatools.component.chart.component.FillStyleFactory;
/*     */ import jatools.component.chart.component.FillStyleInterface;
/*     */ import jatools.component.chart.component.FontComponent;
/*     */ import jatools.component.chart.component.MoreButton;
/*     */ import jatools.component.chart.component.RangeComponent;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
/*     */ 
/*     */ public class LegendPanel extends Dialog
/*     */   implements ActionListener
/*     */ {
/*     */   PropertyChangeListener myParent;
/*     */   CheckComponent ckshowlegend;
/*     */   CheckComponent backgroundVis;
/*     */   ColorComponent ccbackground;
/*     */   RangeComponent xposition;
/*     */   RangeComponent yposition;
/*     */   RangeComponent iclegendheight;
/*     */   RangeComponent iclegendwidth;
/*     */   JLabel ldirection;
/*     */   JPanel p;
/*     */   Legend legend;
/*     */   JRadioButton rvertical;
/*     */   JRadioButton rhorizontal;
/*     */   private JButton mlegendbackground;
/*     */   private JButton ftext;
/*     */   private ColorComponent ccoutline;
/*     */ 
/*     */   public LegendPanel()
/*     */   {
/*  80 */     GridBagLayout gbl = new GridBagLayout();
/*  81 */     setLayout(gbl);
/*  82 */     setBorder(CommonFinal.EMPTY_BORDER);
/*  83 */     GridBagConstraints gbc = new GridBagConstraints();
/*  84 */     gbc.insets = CommonFinal.INSETS;
/*  85 */     gbc.fill = 2;
/*     */ 
/*  87 */     this.iclegendheight = new RangeComponent("图标高度", 0.0D);
/*  88 */     this.iclegendheight.addChangeListener(this);
/*  89 */     this.iclegendwidth = new RangeComponent("图标宽度", 0.0D);
/*  90 */     this.iclegendwidth.addChangeListener(this);
/*     */ 
/*  92 */     this.p = new JPanel();
/*     */ 
/*  94 */     this.p.setLayout(gbl);
/*     */ 
/*  96 */     this.ldirection = new JLabel("方向");
/*  97 */     this.ldirection.setPreferredSize(CommonFinal.LABEL_SIZE);
/*     */ 
/*  99 */     this.ckshowlegend = new CheckComponent("显示图例", false);
/* 100 */     this.ckshowlegend.addChangeListener(this);
/*     */ 
/* 102 */     this.backgroundVis = new CheckComponent("显示背景", false);
/* 103 */     this.backgroundVis.addChangeListener(this);
/* 104 */     this.ccbackground = new ColorComponent("背景颜色", null);
/* 105 */     this.ccbackground.addChangeListener(this);
/* 106 */     this.ccoutline = new ColorComponent("轮廓线", null);
/* 107 */     this.ccoutline.addChangeListener(this);
/*     */ 
/* 109 */     this.ftext = new MoreButton("字体");
/* 110 */     this.ftext.setActionCommand("legend font");
/* 111 */     this.ftext.addActionListener(this);
/* 112 */     this.mlegendbackground = new MoreButton("填充样式");
/* 113 */     this.mlegendbackground.setActionCommand("legend fillstyle");
/* 114 */     this.mlegendbackground.addActionListener(this);
/*     */ 
/* 116 */     this.rvertical = new JRadioButton("垂直");
/* 117 */     this.rvertical.setHorizontalTextPosition(2);
/* 118 */     this.rvertical.addActionListener(this);
/* 119 */     this.rhorizontal = new JRadioButton("水平");
/* 120 */     this.rhorizontal.addActionListener(this);
/* 121 */     this.rhorizontal.setHorizontalTextPosition(2);
/* 122 */     ButtonGroup bg = new ButtonGroup();
/* 123 */     bg.add(this.rvertical);
/* 124 */     bg.add(this.rhorizontal);
/*     */ 
/* 126 */     this.xposition = new RangeComponent("位置   X:", 0.0D);
/* 127 */     this.xposition.addChangeListener(this);
/* 128 */     this.yposition = new RangeComponent("       Y:", 0.0D);
/* 129 */     this.yposition.addChangeListener(this);
/*     */ 
/* 135 */     gbc.gridwidth = 1;
/* 136 */     gbc.weightx = 1.0D;
/* 137 */     add(this.ckshowlegend, gbc);
/* 138 */     gbc.weightx = 0.0D;
/* 139 */     gbc.gridwidth = 0;
/* 140 */     add(this.ftext, gbc);
/*     */ 
/* 142 */     gbc.gridwidth = 1;
/* 143 */     gbc.weightx = 1.0D;
/* 144 */     add(this.backgroundVis, gbc);
/* 145 */     gbc.weightx = 0.0D;
/* 146 */     gbc.gridwidth = 0;
/* 147 */     add(Box.createGlue(), gbc);
/*     */ 
/* 149 */     gbc.gridwidth = 1;
/* 150 */     gbc.weightx = 1.0D;
/* 151 */     add(this.ccbackground, gbc);
/* 152 */     gbc.weightx = 0.0D;
/* 153 */     gbc.gridwidth = 0;
/* 154 */     add(this.mlegendbackground, gbc);
/*     */ 
/* 156 */     gbc.gridwidth = 1;
/* 157 */     gbc.weightx = 1.0D;
/* 158 */     add(this.ccoutline, gbc);
/* 159 */     gbc.weightx = 0.0D;
/* 160 */     gbc.gridwidth = 0;
/* 161 */     add(Box.createGlue(), gbc);
/*     */ 
/* 166 */     SepratorPanel sp = new SepratorPanel("图例布局");
/* 167 */     gbc.weightx = 1.0D;
/* 168 */     gbc.gridwidth = 0;
/* 169 */     add(sp, gbc);
/* 170 */     gbc.weightx = 0.0D;
/* 171 */     gbc.gridwidth = 1;
/* 172 */     this.p.add(this.ldirection, gbc);
/* 173 */     this.p.add(this.rvertical, gbc);
/* 174 */     gbc.gridwidth = 0;
/* 175 */     this.p.add(this.rhorizontal, gbc);
/* 176 */     gbc.weightx = 1.0D;
/*     */ 
/* 182 */     this.p.add(this.iclegendheight, gbc);
/*     */ 
/* 184 */     this.p.add(this.iclegendwidth, gbc);
/*     */ 
/* 186 */     this.p.add(this.xposition, gbc);
/*     */ 
/* 188 */     this.p.add(this.yposition, gbc);
/*     */ 
/* 190 */     add(this.p, gbc);
/*     */ 
/* 192 */     gbc.weighty = 1.0D;
/* 193 */     add(Box.createGlue(), gbc);
/*     */   }
/*     */ 
/*     */   public void show()
/*     */   {
/* 198 */     setVals();
/* 199 */     super.show();
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 208 */     Component f = getParent();
/* 209 */     while (!(f instanceof JDialog))
/* 210 */       f = f.getParent();
/* 211 */     if (e.getActionCommand().equals("legend fillstyle")) {
/* 212 */       FillStyleInterface style = FillStyleChooser.showDialog(f, "填充样式", 
/* 213 */         FillStyleFactory.createFillStyle(this.legend.getBackgroundGc()), 
/* 214 */         0);
/* 215 */       this.ccbackground.setValue(style);
/* 216 */     } else if ((e.getActionCommand().equals("legend font")) && 
/* 217 */       (FontComponent.getDefault().showChooser(this))) {
/* 218 */       ChartFont font = (ChartFont)FontComponent.getDefault().getValue();
/* 219 */       Color color = font.getForeColor();
/* 220 */       Font ff = new Font(font.getFace(), font.getStyle(), font
/* 221 */         .getSize());
/* 222 */       this.legend.setLabelFont(ff);
/* 223 */       this.legend.setLabelColor(color);
/*     */     }
/*     */ 
/* 227 */     fireChange(null);
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/* 237 */     this.legend = ((Legend)object);
/* 238 */     this.legend.getBackgroundGc().setOutlineFills(true);
/*     */   }
/*     */ 
/*     */   public void getVals()
/*     */   {
/* 247 */     this.legend = ((Legend)this.chart.getLegend());
/* 248 */     this.chart.setLegendVisible(this.ckshowlegend.getValue());
/* 249 */     this.legend.setBackgroundVisible(this.backgroundVis.getValue());
/* 250 */     this.ccbackground.getValue().setToGc(this.legend.getBackgroundGc());
/* 251 */     if (this.rvertical.isSelected())
/* 252 */       this.legend.setVerticalLayout(true);
/*     */     else {
/* 254 */       this.legend.setVerticalLayout(false);
/*     */     }
/* 256 */     this.legend.setIconWidth(this.iclegendwidth.getValue());
/* 257 */     this.legend.setIconHeight(this.iclegendheight.getValue());
/* 258 */     this.legend.setLlX(this.xposition.getValue());
/* 259 */     this.legend.setLlY(this.yposition.getValue());
/* 260 */     this.legend.getBackgroundGc().setLineColor(this.ccoutline.getColor());
/*     */ 
/* 262 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 271 */     this.legend = ((Legend)this.chart.getLegend());
/* 272 */     this.ckshowlegend.setValue(this.chart.isLegendVisible());
/* 273 */     this.backgroundVis.setValue(this.legend.getBackgroundVisible());
/* 274 */     this.ccbackground.setValue(FillStyleFactory.createFillStyle(this.legend
/* 275 */       .getBackgroundGc()));
/* 276 */     this.ccoutline.setValue(this.legend.getBackgroundGc().getLineColor());
/* 277 */     if (this.legend.getVerticalLayout())
/* 278 */       this.rvertical.setSelected(true);
/*     */     else {
/* 280 */       this.rhorizontal.setSelected(true);
/*     */     }
/* 282 */     this.iclegendheight.setValue(this.legend.getIconHeight());
/* 283 */     this.iclegendwidth.setValue(this.legend.getIconWidth());
/* 284 */     this.xposition.setValue(this.legend.getLlX());
/* 285 */     this.yposition.setValue(this.legend.getLlY());
/*     */ 
/* 287 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   private void volidateEnabled() {
/* 291 */     if (this.ckshowlegend.getValue()) {
/* 292 */       this.ccbackground.setEnabled(this.backgroundVis.getValue());
/* 293 */       this.mlegendbackground.setEnabled(this.backgroundVis.getValue());
/* 294 */       this.ccoutline.setEnabled(this.backgroundVis.getValue());
/*     */     } else {
/* 296 */       this.ccbackground.setEnabled(false);
/* 297 */       this.mlegendbackground.setEnabled(false);
/* 298 */       this.ccoutline.setEnabled(false);
/*     */     }
/* 300 */     this.ftext.setEnabled(this.ckshowlegend.getValue());
/* 301 */     this.backgroundVis.setEnabled(this.ckshowlegend.getValue());
/* 302 */     this.ldirection.setEnabled(this.ckshowlegend.getValue());
/* 303 */     this.rvertical.setEnabled(this.ckshowlegend.getValue());
/* 304 */     this.rhorizontal.setEnabled(this.ckshowlegend.getValue());
/* 305 */     this.xposition.setEnabled(this.ckshowlegend.getValue());
/* 306 */     this.yposition.setEnabled(this.ckshowlegend.getValue());
/* 307 */     this.iclegendheight.setEnabled(this.ckshowlegend.getValue());
/* 308 */     this.iclegendwidth.setEnabled(this.ckshowlegend.getValue());
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.LegendPanel
 * JD-Core Version:    0.6.2
 */