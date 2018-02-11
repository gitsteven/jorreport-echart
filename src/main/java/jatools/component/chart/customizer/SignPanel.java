/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import jatools.component.chart.component.ColorComponent;
/*     */ import jatools.component.chart.component.IntComponent;
/*     */ import jatools.component.chart.component.LineStyle;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JToggleButton;
/*     */ 
/*     */ public class SignPanel extends Dialog
/*     */   implements ActionListener
/*     */ {
/*     */   ColorComponent ccsignforecolor;
/*     */   JLabel lsignstyle;
/*     */   JToggleButton[] buttons;
/*     */   IntComponent rcsignwidth;
/*     */   ButtonGroup bg;
/*     */   JButton bfillstyle;
/*     */   JButton bpicture;
/*     */   JLabel lpicture;
/*     */   Gc gc;
/*     */   IconType iconType;
/*     */ 
/*     */   public SignPanel(IconType iconType)
/*     */   {
/*  49 */     this.iconType = iconType;
/*  50 */     GridBagLayout gbl = new GridBagLayout();
/*  51 */     setLayout(gbl);
/*  52 */     setBorder(CommonFinal.EMPTY_BORDER);
/*  53 */     GridBagConstraints gbc = new GridBagConstraints();
/*  54 */     gbc.insets = CommonFinal.INSETS;
/*  55 */     gbc.fill = 2;
/*     */ 
/*  57 */     this.ccsignforecolor = new ColorComponent("前景色", null);
/*  58 */     this.ccsignforecolor.addChangeListener(this);
/*     */ 
/*  60 */     this.lsignstyle = new JLabel("样式");
/*  61 */     this.lsignstyle.setPreferredSize(CommonFinal.LABEL_SIZE);
/*     */ 
/*  63 */     this.bg = new ButtonGroup();
/*     */ 
/*  65 */     Box box = Box.createHorizontalBox();
/*  66 */     this.buttons = new JToggleButton[5];
/*  67 */     Icon[] icons = new Icon[5];
/*  68 */     LineStyle line = new LineStyle(true);
/*  69 */     line.setIconType(1);
/*  70 */     for (int i = 0; i < 5; i++) {
/*  71 */       line.setMarkerStyle(i - 1);
/*  72 */       line.setMarkerSize(CommonFinal.STYLE_BUTTON_SIZE.width / 2);
/*  73 */       icons[i] = line.createIcon(CommonFinal.STYLE_BUTTON_SIZE);
/*  74 */       this.buttons[i] = new JToggleButton(icons[i]);
/*  75 */       this.buttons[i].setMargin(new Insets(0, 0, 0, 0));
/*  76 */       this.buttons[i].addActionListener(this);
/*  77 */       this.bg.add(this.buttons[i]);
/*  78 */       box.add(Box.createHorizontalStrut(1));
/*  79 */       box.add(this.buttons[i]);
/*     */     }
/*  81 */     box.add(Box.createHorizontalStrut(5));
/*     */ 
/*  84 */     this.rcsignwidth = new IntComponent("大小", 0, 0, 29, "像素");
/*  85 */     this.rcsignwidth.addChangeListener(this);
/*     */ 
/*  88 */     gbc.weightx = 1.0D;
/*  89 */     gbc.gridwidth = 0;
/*  90 */     add(this.rcsignwidth, gbc);
/*     */ 
/*  93 */     gbc.weightx = 0.0D;
/*  94 */     gbc.gridwidth = 1;
/*  95 */     add(this.lsignstyle, gbc);
/*  96 */     gbc.weightx = 1.0D;
/*  97 */     gbc.gridwidth = 0;
/*  98 */     add(box, gbc);
/*     */ 
/* 104 */     gbc.weighty = 1.0D;
/* 105 */     add(Box.createGlue(), gbc);
/*     */   }
/*     */ 
/*     */   public void show()
/*     */   {
/* 111 */     setVals();
/* 112 */     this.iconType.type = 1;
/* 113 */     super.show();
/* 114 */     fireChange(null);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 119 */     JFrame f = new JFrame();
/* 120 */     SignPanel ip = new SignPanel(null);
/* 121 */     f.getContentPane().add(ip);
/* 122 */     f.setSize(400, 500);
/* 123 */     f.show();
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/* 129 */     this.gc = ((Gc)object);
/*     */   }
/*     */ 
/*     */   public void getVals()
/*     */   {
/* 135 */     this.gc.setMarkerSize(this.rcsignwidth.getValue());
/* 136 */     if (this.buttons[0].isSelected())
/* 137 */       this.gc.setMarkerStyle(-1);
/* 138 */     else if (this.buttons[1].isSelected())
/* 139 */       this.gc.setMarkerStyle(0);
/* 140 */     else if (this.buttons[2].isSelected())
/* 141 */       this.gc.setMarkerStyle(1);
/* 142 */     else if (this.buttons[3].isSelected())
/* 143 */       this.gc.setMarkerStyle(2);
/* 144 */     else if (this.buttons[4].isSelected())
/* 145 */       this.gc.setMarkerStyle(3);
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 152 */     this.rcsignwidth.setValue(this.gc.getMarkerSize());
/* 153 */     if (this.gc.getMarkerStyle() == -1)
/* 154 */       this.buttons[0].setSelected(true);
/* 155 */     else if (this.gc.getMarkerStyle() == 0)
/* 156 */       this.buttons[1].setSelected(true);
/* 157 */     else if (this.gc.getMarkerStyle() == 1)
/* 158 */       this.buttons[2].setSelected(true);
/* 159 */     else if (this.gc.getMarkerStyle() == 2)
/* 160 */       this.buttons[3].setSelected(true);
/* 161 */     else if (this.gc.getMarkerStyle() == 3)
/* 162 */       this.buttons[4].setSelected(true);
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e) {
/* 166 */     fireChange(null);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.SignPanel
 * JD-Core Version:    0.6.2
 */