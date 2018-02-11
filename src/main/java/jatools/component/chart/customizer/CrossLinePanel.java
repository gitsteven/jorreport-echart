/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import jatools.component.chart.component.ColorComponent;
/*     */ import jatools.component.chart.component.SingleColor;
/*     */ import jatools.component.chart.component.TextureColor;
/*     */ import jatools.component.chart.component.TextureIcon;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
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
/*     */ public class CrossLinePanel extends Dialog
/*     */   implements ActionListener
/*     */ {
/*     */   ColorComponent bmaster;
/*     */   ColorComponent bsecond;
/*     */   Gc gc;
/*     */   JToggleButton[] buttons;
/*     */   TextureIcon[] icons;
/*     */   TextureIcon icon;
/*     */ 
/*     */   public CrossLinePanel()
/*     */   {
/*  36 */     GridBagLayout gbl = new GridBagLayout();
/*  37 */     setLayout(gbl);
/*  38 */     setBorder(CommonFinal.EMPTY_BORDER);
/*  39 */     GridBagConstraints gbc = new GridBagConstraints();
/*  40 */     gbc.insets = CommonFinal.INSETS;
/*  41 */     gbc.fill = 1;
/*     */ 
/*  43 */     this.bmaster = new ColorComponent("主要颜色", null);
/*  44 */     this.bmaster.addChangeListener(this);
/*  45 */     this.bsecond = new ColorComponent("次要颜色", null);
/*  46 */     this.bsecond.addChangeListener(this);
/*     */ 
/*  48 */     JLabel lmode = new JLabel("样式");
/*  49 */     ButtonGroup group = new ButtonGroup();
/*  50 */     Box box = Box.createHorizontalBox();
/*  51 */     this.buttons = new JToggleButton[5];
/*  52 */     this.icons = new TextureIcon[5];
/*  53 */     TextureColor text = new TextureColor();
/*  54 */     text.setMasterColor(new Color(153, 153, 255));
/*  55 */     text.setSecondColor(Color.white);
/*  56 */     for (int i = 0; i < 5; i++) {
/*  57 */       text.setStyle(i);
/*  58 */       this.icons[i] = ((TextureIcon)text.createIcon(new Dimension(CommonFinal.STYLE_BUTTON_SIZE)));
/*  59 */       this.buttons[i] = new JToggleButton(this.icons[i]);
/*  60 */       this.buttons[i].setMargin(new Insets(0, 0, 0, 0));
/*  61 */       this.buttons[i].addActionListener(this);
/*  62 */       group.add(this.buttons[i]);
/*  63 */       box.add(Box.createHorizontalStrut(5));
/*  64 */       box.add(this.buttons[i]);
/*     */     }
/*     */ 
/*  67 */     box.add(Box.createHorizontalStrut(5));
/*     */ 
/*  69 */     gbc.weightx = 1.0D;
/*  70 */     gbc.gridwidth = 0;
/*  71 */     add(this.bmaster, gbc);
/*     */ 
/*  73 */     gbc.weightx = 1.0D;
/*  74 */     gbc.gridwidth = 0;
/*  75 */     add(this.bsecond, gbc);
/*     */ 
/*  77 */     gbc.gridwidth = 1;
/*  78 */     add(lmode, gbc);
/*  79 */     gbc.weightx = 1.0D;
/*  80 */     gbc.gridwidth = 0;
/*  81 */     add(box, gbc);
/*     */ 
/*  83 */     gbc.weighty = 1.0D;
/*  84 */     add(Box.createGlue(), gbc);
/*     */   }
/*     */ 
/*     */   public void show() {
/*  88 */     this.gc.setFillStyle(1);
/*  89 */     setVals();
/*  90 */     super.show();
/*  91 */     fireChange(null);
/*     */   }
/*     */ 
/*     */   public void setObject(Object object) {
/*  95 */     this.gc = ((Gc)object);
/*     */   }
/*     */ 
/*     */   public void getVals() {
/*  99 */     this.gc.setFillColor(((SingleColor)this.bmaster.getValue()).getColor());
/* 100 */     this.gc.setSecondaryFillColor(((SingleColor)this.bsecond.getValue()).getColor());
/* 101 */     if (this.buttons[0].isSelected())
/* 102 */       this.gc.setTexture(0);
/* 103 */     else if (this.buttons[1].isSelected())
/* 104 */       this.gc.setTexture(1);
/* 105 */     else if (this.buttons[2].isSelected())
/* 106 */       this.gc.setTexture(2);
/* 107 */     else if (this.buttons[3].isSelected())
/* 108 */       this.gc.setTexture(3);
/* 109 */     else if (this.buttons[4].isSelected())
/* 110 */       this.gc.setTexture(4);
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 115 */     this.bmaster.setValue(new SingleColor(this.gc.getFillColor()));
/* 116 */     this.bsecond.setValue(new SingleColor(this.gc.getSecondaryFillColor()));
/* 117 */     if (this.gc.getTexture() == 0)
/* 118 */       this.buttons[0].setSelected(true);
/* 119 */     else if (this.gc.getTexture() == 1)
/* 120 */       this.buttons[1].setSelected(true);
/* 121 */     else if (this.gc.getTexture() == 2)
/* 122 */       this.buttons[2].setSelected(true);
/* 123 */     else if (this.gc.getTexture() == 3)
/* 124 */       this.buttons[3].setSelected(true);
/* 125 */     else if (this.gc.getTexture() == 4)
/* 126 */       this.buttons[4].setSelected(true);
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 131 */     fireChange(null);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.CrossLinePanel
 * JD-Core Version:    0.6.2
 */