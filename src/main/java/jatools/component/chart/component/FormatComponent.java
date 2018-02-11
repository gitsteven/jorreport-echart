/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import jatools.component.chart.CommonFinal;
/*    */ import jatools.component.chart.customizer.ChangeListener;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import java.text.NumberFormat;
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public class FormatComponent extends JPanel
/*    */   implements ActionListener
/*    */ {
/*    */   private JLabel label;
/*    */   private JComboBox dataFormat;
/*    */   private ChangeListener parent;
/*    */ 
/*    */   public FormatComponent(String title)
/*    */   {
/* 23 */     setLayout(new GridBagLayout());
/* 24 */     GridBagConstraints gbc = new GridBagConstraints();
/* 25 */     gbc.insets = CommonFinal.INSETS;
/* 26 */     gbc.fill = 2;
/* 27 */     this.label = new JLabel(title);
/* 28 */     this.label.setPreferredSize(CommonFinal.SHORT_LABEL_SIZE);
/* 29 */     this.dataFormat = new JComboBox();
/* 30 */     this.dataFormat.addItem(new DataFormat(NumberFormat.getNumberInstance(), 0));
/* 31 */     this.dataFormat.addItem(new DataFormat(NumberFormat.getNumberInstance(), 1));
/* 32 */     this.dataFormat.addItem(new DataFormat(NumberFormat.getNumberInstance(), 2));
/*    */ 
/* 34 */     this.dataFormat.addItem(new DataFormat(NumberFormat.getPercentInstance(), 0));
/* 35 */     this.dataFormat.addItem(new DataFormat(NumberFormat.getPercentInstance(), 1));
/* 36 */     this.dataFormat.addItem(new DataFormat(NumberFormat.getPercentInstance(), 2));
/*    */ 
/* 38 */     this.dataFormat.addItem(new DataFormat(NumberFormat.getCurrencyInstance(), 0));
/* 39 */     this.dataFormat.addItem(new DataFormat(NumberFormat.getCurrencyInstance(), 1));
/* 40 */     this.dataFormat.addItem(new DataFormat(NumberFormat.getCurrencyInstance(), 2));
/* 41 */     this.dataFormat.setActionCommand("num format");
/* 42 */     this.dataFormat.addActionListener(this);
/* 43 */     gbc.weightx = 0.0D;
/* 44 */     gbc.gridwidth = 1;
/* 45 */     add(this.label, gbc);
/* 46 */     gbc.weightx = 1.0D;
/* 47 */     gbc.gridwidth = 0;
/* 48 */     add(this.dataFormat, gbc);
/*    */   }
/*    */ 
/*    */   public static AbstractComponent getDecimalFormatInstance(String title)
/*    */   {
/* 53 */     return new DecimalFormatComponent(title);
/*    */   }
/*    */ 
/*    */   public static AbstractComponent getDateFormatInstance(String title) {
/* 57 */     return new DateFormatComponent(title);
/*    */   }
/*    */ 
/*    */   public DataFormat getValue()
/*    */   {
/* 62 */     return (DataFormat)this.dataFormat.getSelectedItem();
/*    */   }
/*    */   public void setValue(DataFormat format) {
/* 65 */     this.dataFormat.setSelectedItem(format);
/*    */   }
/*    */   public void actionPerformed(ActionEvent e) {
/* 68 */     if (this.parent != null)
/* 69 */       this.parent.fireChange(null); 
/*    */   }
/*    */ 
/* 72 */   public void addChangeListener(ChangeListener l) { this.parent = l; }
/*    */ 
/*    */   public void removeChangeListener() {
/* 75 */     this.parent = null;
/*    */   }
/*    */   public void setEnabled(boolean enabled) {
/* 78 */     super.setEnabled(enabled);
/* 79 */     this.label.setEnabled(enabled);
/* 80 */     this.dataFormat.setEnabled(enabled);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.FormatComponent
 * JD-Core Version:    0.6.2
 */