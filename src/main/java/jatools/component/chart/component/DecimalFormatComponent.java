/*     */ package jatools.component.chart.component;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.customizer.ChangeListener;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.ComboBoxEditor;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ 
/*     */ public class DecimalFormatComponent extends AbstractComponent
/*     */   implements ActionListener
/*     */ {
/*     */   private JLabel label;
/*     */   private JComboBox dataFormat;
/*     */ 
/*     */   public DecimalFormatComponent(String title)
/*     */   {
/*  20 */     setLayout(new GridBagLayout());
/*  21 */     GridBagConstraints gbc = new GridBagConstraints();
/*  22 */     gbc.insets = CommonFinal.INSETS;
/*  23 */     gbc.fill = 2;
/*  24 */     this.label = new JLabel(title);
/*  25 */     this.label.setPreferredSize(CommonFinal.SHORT_LABEL_SIZE);
/*  26 */     this.dataFormat = new JComboBox(new String[] { 
/*  27 */       "自定义...", 
/*  28 */       "0", 
/*  29 */       "0.0", 
/*  30 */       "0.00", 
/*  31 */       "0.000", 
/*  32 */       "0.0000", 
/*  33 */       "#,##0", 
/*  34 */       "#,##0.0", 
/*  35 */       "#,##0.00", 
/*  36 */       "#,##0.000", 
/*  37 */       "#,##0.0000", 
/*  39 */       "￥0", 
/*  40 */       "￥0.0", 
/*  41 */       "￥0.00", 
/*  42 */       "￥0.000", 
/*  43 */       "￥0.0000", 
/*  44 */       "￥#,##0", 
/*  45 */       "￥#,##0.0", 
/*  46 */       "￥#,##0.00", 
/*  47 */       "￥#,##0.000", 
/*  48 */       "￥#,##0.0000", 
/*  50 */       "0%", 
/*  51 */       "0.0%", 
/*  52 */       "0.00%", 
/*  53 */       "0.000%", 
/*  54 */       "0.0000%", 
/*  56 */       "0.##E0", "0.00E0" });
/*     */ 
/*  69 */     this.dataFormat.setActionCommand("num format");
/*  70 */     this.dataFormat.addActionListener(this);
/*  71 */     this.dataFormat.setEditable(true);
/*  72 */     this.dataFormat.getEditor().addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/*  75 */         if (DecimalFormatComponent.this.parent != null)
/*  76 */           DecimalFormatComponent.this.parent.fireChange(e);
/*     */       }
/*     */     });
/*  80 */     gbc.weightx = 0.0D;
/*  81 */     gbc.gridwidth = 1;
/*  82 */     add(this.label, gbc);
/*  83 */     gbc.weightx = 1.0D;
/*  84 */     gbc.gridwidth = 0;
/*  85 */     add(this.dataFormat, gbc);
/*     */   }
/*     */ 
/*     */   public void setValue(Object object) {
/*  89 */     this.dataFormat.getEditor().setItem(object);
/*     */   }
/*     */ 
/*     */   public Object getValue() {
/*  93 */     return this.dataFormat.getEditor().getItem();
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e) {
/*  97 */     if ((e.getActionCommand().equals("num format")) && 
/*  98 */       (!this.dataFormat.getSelectedItem().equals("自定义...")))
/*     */     {
/* 101 */       if (this.parent != null)
/* 102 */         this.parent.fireChange(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setEnabled(boolean enabled)
/*     */   {
/* 108 */     super.setEnabled(enabled);
/* 109 */     this.label.setEnabled(enabled);
/* 110 */     this.dataFormat.setEnabled(enabled);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.DecimalFormatComponent
 * JD-Core Version:    0.6.2
 */