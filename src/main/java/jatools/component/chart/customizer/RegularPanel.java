/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Background;
/*     */ import jatools.component.chart.chart.BubbleChart;
/*     */ import jatools.component.chart.chart.CandlestickChart;
/*     */ import jatools.component.chart.chart.GanttChart;
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import jatools.component.chart.chart.HiLoCloseChart;
/*     */ import jatools.component.chart.chart.PieChart;
/*     */ import jatools.component.chart.chart.Plotarea;
/*     */ import jatools.component.chart.chart.PolarChart;
/*     */ import jatools.component.chart.chart.RegressChart;
/*     */ import jatools.component.chart.chart.SectorMapChart;
/*     */ import jatools.component.chart.chart._Chart;
/*     */ import jatools.component.chart.component.ChartFont;
/*     */ import jatools.component.chart.component.CheckComponent;
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
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ 
/*     */ public class RegularPanel extends Dialog
/*     */   implements ActionListener
/*     */ {
/*     */   ColorComponent background;
/*     */   ColorComponent plotarea;
/*     */   ColorComponent backgroundOutline;
/*     */   ColorComponent plotareaOutline;
/*     */   StringComponent title;
/*     */   CheckComponent cc;
/*     */   ColorStyleDialog bcolorStyle;
/*     */   ColorStyleDialog pcolorStyle;
/*  52 */   ChartFont titleFont = new ChartFont("Dialog", 0, 12, Color.black, null);
/*     */   private ThreeDPanel threeD;
/*     */   private JButton btitle;
/*     */   private CheckComponent antialiasing;
/*     */ 
/*     */   public void initCustomizer()
/*     */   {
/*  58 */     GridBagLayout gbl = new GridBagLayout();
/*  59 */     setLayout(gbl);
/*  60 */     setBorder(CommonFinal.EMPTY_BORDER);
/*  61 */     GridBagConstraints gbc = new GridBagConstraints();
/*  62 */     gbc.insets = CommonFinal.INSETS;
/*  63 */     gbc.fill = 2;
/*     */ 
/*  65 */     this.background = new ColorComponent("背景", null);
/*  66 */     this.background.addChangeListener(this);
/*  67 */     this.backgroundOutline = new ColorComponent("轮廓线", null);
/*  68 */     this.backgroundOutline.addChangeListener(this);
/*  69 */     this.plotarea = new ColorComponent("绘图区", null);
/*  70 */     this.plotarea.addChangeListener(this);
/*  71 */     this.plotareaOutline = new ColorComponent("绘图区轮廓线", null);
/*  72 */     this.plotareaOutline.addChangeListener(this);
/*  73 */     this.title = new StringComponent("标题", null);
/*  74 */     this.title.addChangeListener(this);
/*     */ 
/*  76 */     this.threeD = new ThreeDPanel();
/*  77 */     this.threeD.setChart(this.chart);
/*  78 */     this.threeD.addChangeListener(this);
/*     */ 
/*  80 */     JButton bbg = new MoreButton("填充样式");
/*  81 */     bbg.setActionCommand("background fillstyle");
/*  82 */     bbg.addActionListener(this);
/*  83 */     JButton bhtbg = new MoreButton("填充样式");
/*  84 */     bhtbg.setActionCommand("plotarea fillstyle");
/*  85 */     bhtbg.addActionListener(this);
/*  86 */     this.btitle = new MoreButton("字体");
/*  87 */     this.btitle.setActionCommand("title font");
/*  88 */     this.btitle.addActionListener(this);
/*  89 */     JButton bMargin = new MoreButton("绘图区边距");
/*  90 */     bMargin.setActionCommand("plotarea margin");
/*  91 */     bMargin.addActionListener(this);
/*     */ 
/*  93 */     this.antialiasing = new CheckComponent("平滑边缘", true);
/*  94 */     this.antialiasing.addChangeListener(this);
/*     */ 
/* 100 */     gbc.weightx = 1.0D;
/* 101 */     add(this.background, gbc);
/* 102 */     gbc.weightx = 0.0D;
/* 103 */     gbc.gridwidth = 0;
/* 104 */     add(bbg, gbc);
/*     */ 
/* 106 */     gbc.weightx = 1.0D;
/* 107 */     gbc.gridwidth = 1;
/* 108 */     add(this.backgroundOutline, gbc);
/* 109 */     gbc.weightx = 0.0D;
/* 110 */     gbc.gridwidth = 0;
/* 111 */     add(Box.createGlue(), gbc);
/*     */ 
/* 113 */     gbc.weightx = 1.0D;
/* 114 */     gbc.gridwidth = 1;
/* 115 */     add(this.plotarea, gbc);
/* 116 */     gbc.weightx = 0.0D;
/* 117 */     gbc.gridwidth = 0;
/* 118 */     add(bhtbg, gbc);
/*     */ 
/* 120 */     gbc.weightx = 1.0D;
/* 121 */     gbc.gridwidth = 1;
/* 122 */     add(this.plotareaOutline, gbc);
/* 123 */     gbc.weightx = 0.0D;
/* 124 */     gbc.gridwidth = 0;
/* 125 */     add(bMargin, gbc);
/*     */ 
/* 127 */     gbc.weightx = 1.0D;
/* 128 */     gbc.gridwidth = 1;
/* 129 */     add(this.title, gbc);
/* 130 */     gbc.weightx = 0.0D;
/* 131 */     gbc.gridwidth = 0;
/* 132 */     add(this.btitle, gbc);
/*     */ 
/* 134 */     add(this.antialiasing, gbc);
/*     */ 
/* 136 */     gbc.weightx = 1.0D;
/* 137 */     gbc.gridwidth = 0;
/*     */ 
/* 139 */     add(this.threeD, gbc);
/*     */ 
/* 141 */     gbc.weighty = 1.0D;
/* 142 */     add(Box.createGlue(), gbc);
/* 143 */     if ((this.chart instanceof PieChart)) {
/* 144 */       this.plotarea.setEnabled(false);
/* 145 */       this.plotareaOutline.setEnabled(false);
/* 146 */       bhtbg.setEnabled(false);
/* 147 */       bMargin.setEnabled(false);
/*     */     }
/* 149 */     if ((this.chart instanceof SectorMapChart)) {
/* 150 */       this.plotarea.setEnabled(false);
/* 151 */       this.plotareaOutline.setEnabled(false);
/* 152 */       bhtbg.setEnabled(false);
/* 153 */       bMargin.setEnabled(false);
/* 154 */       this.chart.setThreeD(false);
/* 155 */       this.threeD.setEnabled(false);
/*     */     }
/*     */ 
/* 158 */     if (((this.chart instanceof CandlestickChart)) || ((this.chart instanceof HiLoCloseChart)) || ((this.chart instanceof BubbleChart)) || ((this.chart instanceof RegressChart)) || ((this.chart instanceof PolarChart)) || ((this.chart instanceof GanttChart))) {
/* 159 */       this.chart.setThreeD(false);
/* 160 */       this.threeD.setEnabled(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void show() {
/* 165 */     setVals();
/* 166 */     this.threeD.setVals();
/* 167 */     super.show();
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e) {
/* 171 */     Component f = getParent();
/* 172 */     while (!(f instanceof JDialog))
/* 173 */       f = f.getParent();
/* 174 */     if (e.getActionCommand().equals("background fillstyle")) {
/* 175 */       FillStyleInterface style = FillStyleChooser.showDialog(f, "填充样式", FillStyleFactory.createFillStyle(this.chart.getBackground().getGc()), 0);
/* 176 */       this.background.setValue(style);
/* 177 */     } else if (e.getActionCommand().equals("plotarea fillstyle")) {
/* 178 */       FillStyleInterface style = FillStyleChooser.showDialog(f, "填充样式", FillStyleFactory.createFillStyle(this.chart.getPlotarea().getGc()), 0);
/* 179 */       this.plotarea.setValue(style);
/* 180 */     } else if (e.getActionCommand().equals("title font")) {
/* 181 */       if (FontComponent.getDefault().showChooser(this))
/* 182 */         this.titleFont = ((ChartFont)FontComponent.getDefault().getValue());
/*     */     }
/* 184 */     else if (e.getActionCommand().equals("plotarea margin")) {
/* 185 */       PlotareaMargin.showDialog(f, "绘图区边距设置", this.chart.getPlotarea(), this);
/*     */     }
/*     */ 
/* 188 */     fireChange(null);
/*     */   }
/*     */ 
/*     */   public void getVals()
/*     */   {
/* 193 */     this.chart.setAntialiasing(this.antialiasing.getValue());
/*     */ 
/* 195 */     FillStyleInterface style = this.background.getValue();
/* 196 */     style.setToGc(this.chart.getBackground().getGc());
/*     */ 
/* 198 */     this.chart.getBackground().getGc().setLineColor(this.backgroundOutline.getColor());
/* 199 */     style = this.plotarea.getValue();
/* 200 */     style.setToGc(this.chart.getPlotarea().getGc());
/* 201 */     this.chart.getPlotarea().getGc().setLineColor(this.plotareaOutline.getColor());
/* 202 */     this.chart.getBackground().setTitleString((String)this.title.getValue());
/* 203 */     this.chart.getBackground().setTitleFont(new Font(this.titleFont.getFace(), this.titleFont.getStyle(), this.titleFont.getSize()));
/* 204 */     this.chart.getBackground().setTitleColor(this.titleFont.getForeColor());
/*     */ 
/* 206 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   public void setVals() {
/* 210 */     init();
/*     */ 
/* 212 */     this.antialiasing.setValue(this.chart.isAntialiasing());
/*     */ 
/* 214 */     this.background.setValue(FillStyleFactory.createFillStyle(this.chart.getBackground().getGc()));
/* 215 */     this.backgroundOutline.setValue(this.chart.getBackground().getGc().getLineColor());
/* 216 */     this.plotarea.setValue(FillStyleFactory.createFillStyle(this.chart.getPlotarea().getGc()));
/* 217 */     this.plotareaOutline.setValue(this.chart.getPlotarea().getGc().getLineColor());
/* 218 */     this.title.setValue(this.chart.getBackground().getTitleString());
/* 219 */     this.titleFont.setFace(this.chart.getBackground().getTitleFont().getFamily());
/* 220 */     this.titleFont.setStyle(this.chart.getBackground().getTitleFont().getStyle());
/* 221 */     this.titleFont.setSize(this.chart.getBackground().getTitleFont().getSize());
/* 222 */     this.titleFont.setForeColor(this.chart.getBackground().getTitleColor());
/*     */ 
/* 224 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   private void volidateEnabled() {
/* 228 */     if ((this.chart.getBackground().getTitleString() == null) || (this.chart.getBackground().getTitleString().equals("")))
/* 229 */       this.btitle.setEnabled(false);
/*     */     else
/* 231 */       this.btitle.setEnabled(true);
/*     */   }
/*     */ 
/*     */   private void init()
/*     */   {
/* 236 */     this.chart.getBackground().getGc().setOutlineFills(true);
/* 237 */     this.chart.getPlotarea().getGc().setOutlineFills(true);
/*     */   }
/*     */ 
/*     */   public void setObject(Object object) {
/* 241 */     initCustomizer();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.RegularPanel
 * JD-Core Version:    0.6.2
 */