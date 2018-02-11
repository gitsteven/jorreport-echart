/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import jatools.component.chart.component.ColorComponent;
/*     */ import jatools.component.chart.component.GradiantColor;
/*     */ import jatools.component.chart.component.GradiantIcon;
/*     */ import jatools.component.chart.component.SingleColor;
/*     */ import java.awt.Color;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JToggleButton;
/*     */ 
/*     */ public class CrossColorPanel extends Dialog
/*     */   implements ActionListener
/*     */ {
/*     */   ColorComponent bmaster;
/*     */   ColorComponent bsecond;
/*     */   Gc gc;
/*     */   JToggleButton[] buttons;
/*     */   GradiantIcon[] icons;
/*     */   GradiantIcon icon;
/*     */ 
/*     */   public void show()
/*     */   {
/*  34 */     this.gc.setFillStyle(0);
/*  35 */     setVals();
/*  36 */     super.show();
/*  37 */     fireChange(null);
/*     */   }
/*     */ 
/*     */   public void setObject(Object object) {
/*  41 */     this.gc = ((Gc)object);
/*  42 */     initCustomizer();
/*     */   }
/*     */ 
/*     */   public void getVals() {
/*  46 */     this.gc.setFillColor(((SingleColor)this.bmaster.getValue()).getColor());
/*  47 */     this.gc.setSecondaryFillColor(((SingleColor)this.bsecond.getValue()).getColor());
/*  48 */     if (this.buttons[0].isSelected())
/*  49 */       this.gc.setGradient(3);
/*  50 */     else if (this.buttons[1].isSelected())
/*  51 */       this.gc.setGradient(2);
/*  52 */     else if (this.buttons[2].isSelected())
/*  53 */       this.gc.setGradient(1);
/*  54 */     else if (this.buttons[3].isSelected())
/*  55 */       this.gc.setGradient(0);
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/*  60 */     this.bmaster.setValue(new SingleColor(this.gc.getFillColor()));
/*  61 */     this.bsecond.setValue(new SingleColor(this.gc.getSecondaryFillColor()));
/*  62 */     if (this.gc.getGradient() == 3)
/*  63 */       this.buttons[0].setSelected(true);
/*  64 */     else if (this.gc.getGradient() == 2)
/*  65 */       this.buttons[1].setSelected(true);
/*  66 */     else if (this.gc.getGradient() == 1)
/*  67 */       this.buttons[2].setSelected(true);
/*  68 */     else if (this.gc.getGradient() == 0)
/*  69 */       this.buttons[3].setSelected(true);
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/*  74 */     fireChange(null);
/*     */   }
/*     */ 
/*     */   protected void initCustomizer() {
/*  78 */     GridBagLayout gbl = new GridBagLayout();
/*  79 */     setLayout(gbl);
/*  80 */     setBorder(CommonFinal.EMPTY_BORDER);
/*  81 */     GridBagConstraints gbc = new GridBagConstraints();
/*  82 */     gbc.insets = CommonFinal.INSETS;
/*  83 */     gbc.fill = 1;
/*     */ 
/*  85 */     this.bmaster = new ColorComponent("主要颜色", null);
/*  86 */     this.bmaster.addChangeListener(this);
/*  87 */     this.bsecond = new ColorComponent("次要颜色", null);
/*  88 */     this.bsecond.addChangeListener(this);
/*     */ 
/*  90 */     JLabel lmode = new JLabel("样式");
/*  91 */     ButtonGroup group = new ButtonGroup();
/*  92 */     Box box = Box.createHorizontalBox();
/*  93 */     this.buttons = new JToggleButton[4];
/*  94 */     this.icons = new GradiantIcon[4];
/*  95 */     GradiantColor grad = new GradiantColor();
/*  96 */     grad.setMasterColor(new Color(153, 153, 255));
/*  97 */     grad.setSecondColor(Color.white);
/*  98 */     for (int i = 0; i < 4; i++) {
/*  99 */       grad.setStyle(3 - i);
/* 100 */       this.icons[i] = ((GradiantIcon)grad.createIcon(CommonFinal.STYLE_BUTTON_SIZE));
/* 101 */       this.buttons[i] = new JToggleButton(this.icons[i]);
/* 102 */       this.buttons[i].setMargin(new Insets(0, 0, 0, 0));
/* 103 */       this.buttons[i].addActionListener(this);
/* 104 */       group.add(this.buttons[i]);
/* 105 */       box.add(Box.createHorizontalStrut(5));
/* 106 */       box.add(this.buttons[i]);
/*     */     }
/*     */ 
/* 109 */     box.add(Box.createHorizontalStrut(5));
/*     */ 
/* 111 */     gbc.weightx = 1.0D;
/* 112 */     gbc.gridwidth = 0;
/* 113 */     add(this.bmaster, gbc);
/*     */ 
/* 115 */     gbc.weightx = 1.0D;
/* 116 */     gbc.gridwidth = 0;
/* 117 */     add(this.bsecond, gbc);
/*     */ 
/* 119 */     gbc.gridwidth = 1;
/* 120 */     add(lmode, gbc);
/* 121 */     gbc.weightx = 1.0D;
/* 122 */     gbc.gridwidth = 0;
/* 123 */     add(box, gbc);
/*     */ 
/* 125 */     gbc.weighty = 1.0D;
/* 126 */     add(Box.createGlue(), gbc);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.CrossColorPanel
 * JD-Core Version:    0.6.2
 */