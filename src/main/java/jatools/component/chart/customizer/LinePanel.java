/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import jatools.component.chart.component.ColorComponent;
/*     */ import jatools.component.chart.component.IntComponent;
/*     */ import jatools.component.chart.component.LineStyle;
/*     */ import java.awt.Container;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JToggleButton;
/*     */ 
/*     */ public class LinePanel extends Dialog
/*     */   implements ActionListener
/*     */ {
/*     */   ColorComponent cclineforecolor;
/*     */   JLabel llinestyle;
/*     */   JToggleButton[] buttons;
/*     */   ButtonGroup bg;
/*     */   IntComponent rclinewidth;
/*     */   Gc gc;
/*     */   IconType iconType;
/*     */ 
/*     */   public LinePanel(IconType iconType)
/*     */   {
/*  46 */     this.iconType = iconType;
/*  47 */     GridBagLayout gbl = new GridBagLayout();
/*  48 */     setLayout(gbl);
/*  49 */     setBorder(CommonFinal.EMPTY_BORDER);
/*  50 */     GridBagConstraints gbc = new GridBagConstraints();
/*  51 */     gbc.insets = CommonFinal.INSETS;
/*  52 */     gbc.fill = 2;
/*     */ 
/*  54 */     this.cclineforecolor = new ColorComponent("前景色", null);
/*  55 */     this.cclineforecolor.addChangeListener(this);
/*     */ 
/*  57 */     this.llinestyle = new JLabel("样式");
/*  58 */     this.llinestyle.setPreferredSize(CommonFinal.LABEL_SIZE);
/*     */ 
/*  60 */     this.bg = new ButtonGroup();
/*     */ 
/*  62 */     Box box = Box.createHorizontalBox();
/*  63 */     this.buttons = new JToggleButton[4];
/*  64 */     Icon[] icons = new Icon[4];
/*  65 */     LineStyle ls = new LineStyle(true);
/*  66 */     ls.setIconType(0);
/*  67 */     for (int i = 0; i < 4; i++) {
/*  68 */       ls.setLineStyle(i - 1);
/*  69 */       icons[i] = ls.createIcon(CommonFinal.STYLE_BUTTON_SIZE);
/*  70 */       this.buttons[i] = new JToggleButton(icons[i]);
/*  71 */       this.buttons[i].setMargin(new Insets(0, 0, 0, 0));
/*  72 */       this.buttons[i].addActionListener(this);
/*  73 */       this.bg.add(this.buttons[i]);
/*  74 */       box.add(Box.createHorizontalStrut(5));
/*  75 */       box.add(this.buttons[i]);
/*     */     }
/*     */ 
/*  78 */     box.add(Box.createHorizontalStrut(5));
/*     */ 
/*  80 */     this.rclinewidth = new IntComponent("线宽", 0, 0, 9, "像素");
/*  81 */     this.rclinewidth.addChangeListener(this);
/*     */ 
/*  84 */     gbc.gridwidth = 0;
/*  85 */     gbc.weightx = 1.0D;
/*  86 */     add(this.cclineforecolor, gbc);
/*     */ 
/*  89 */     gbc.gridwidth = 1;
/*  90 */     gbc.weightx = 0.0D;
/*  91 */     add(this.llinestyle, gbc);
/*  92 */     gbc.weightx = 1.0D;
/*  93 */     gbc.gridwidth = 0;
/*  94 */     add(box, gbc);
/*     */ 
/*  98 */     gbc.weightx = 1.0D;
/*  99 */     add(this.rclinewidth, gbc);
/*     */ 
/* 102 */     gbc.weighty = 1.0D;
/* 103 */     add(Box.createGlue(), gbc);
/*     */   }
/*     */ 
/*     */   public void show()
/*     */   {
/* 109 */     setVals();
/* 110 */     this.iconType.type = 0;
/* 111 */     super.show();
/* 112 */     fireChange(null);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 117 */     JFrame f = new JFrame();
/* 118 */     LinePanel ip = new LinePanel(null);
/* 119 */     f.getContentPane().add(ip);
/* 120 */     f.setSize(400, 500);
/* 121 */     f.show();
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/* 128 */     this.gc = ((Gc)object);
/*     */   }
/*     */   public void getVals() {
/* 131 */     this.gc.setLineColor(this.cclineforecolor.getColor());
/*     */ 
/* 133 */     this.gc.setLineWidth(this.rclinewidth.getValue());
/* 134 */     if (this.buttons[0].isSelected())
/* 135 */       this.gc.setLineStyle(-1);
/* 136 */     else if (this.buttons[1].isSelected())
/* 137 */       this.gc.setLineStyle(0);
/* 138 */     else if (this.buttons[2].isSelected())
/* 139 */       this.gc.setLineStyle(1);
/* 140 */     else if (this.buttons[3].isSelected())
/* 141 */       this.gc.setLineStyle(2);
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 148 */     this.cclineforecolor.setValue(this.gc.getLineColor());
/* 149 */     this.rclinewidth.setValue(this.gc.getLineWidth());
/* 150 */     if (this.gc.getLineStyle() == -1)
/* 151 */       this.buttons[0].setSelected(true);
/* 152 */     else if (this.gc.getLineStyle() == 0)
/* 153 */       this.buttons[1].setSelected(true);
/* 154 */     else if (this.gc.getLineStyle() == 1)
/* 155 */       this.buttons[2].setSelected(true);
/* 156 */     else if (this.gc.getLineStyle() == 2)
/* 157 */       this.buttons[3].setSelected(true);
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 164 */     fireChange(null);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.LinePanel
 * JD-Core Version:    0.6.2
 */