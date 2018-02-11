/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import jatools.component.chart.CommonFinal;
/*    */ import jatools.component.chart.customizer.ChangeListener;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.ComboBoxEditor;
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.JLabel;
/*    */ 
/*    */ public class DateFormatComponent extends AbstractComponent
/*    */   implements ActionListener
/*    */ {
/*    */   private JLabel dateLabel;
/*    */   private JComboBox dateFormat;
/*    */ 
/*    */   public DateFormatComponent(String title)
/*    */   {
/* 20 */     setLayout(new GridBagLayout());
/* 21 */     GridBagConstraints gbc = new GridBagConstraints();
/* 22 */     gbc.insets = CommonFinal.INSETS;
/* 23 */     gbc.fill = 2;
/* 24 */     this.dateLabel = new JLabel(title);
/* 25 */     this.dateLabel.setPreferredSize(CommonFinal.SHORT_LABEL_SIZE);
/* 26 */     this.dateFormat = new JComboBox();
/* 27 */     this.dateFormat.addItem("自定义...");
/* 28 */     this.dateFormat.addItem("yy-MM-dd");
/* 29 */     this.dateFormat.addItem("yy-MM-dd HH-mm-ss");
/* 30 */     this.dateFormat.addItem("yy年MM月dd日");
/* 31 */     this.dateFormat.addItem("yy年MM月dd日 HH时mm分ss秒");
/*    */ 
/* 33 */     this.dateFormat.setEditable(true);
/* 34 */     this.dateFormat.setActionCommand("date format");
/* 35 */     this.dateFormat.getEditor().addActionListener(new ActionListener()
/*    */     {
/*    */       public void actionPerformed(ActionEvent e) {
/* 38 */         if (DateFormatComponent.this.parent != null)
/* 39 */           DateFormatComponent.this.parent.fireChange(e);
/*    */       }
/*    */     });
/* 43 */     this.dateFormat.addActionListener(this);
/* 44 */     gbc.weightx = 0.0D;
/* 45 */     gbc.gridwidth = 1;
/* 46 */     add(this.dateLabel, gbc);
/* 47 */     gbc.weightx = 1.0D;
/* 48 */     gbc.gridwidth = 0;
/* 49 */     add(this.dateFormat, gbc);
/*    */   }
/*    */ 
/*    */   public void setValue(Object object) {
/* 53 */     this.dateFormat.getEditor().setItem(object);
/*    */   }
/*    */ 
/*    */   public Object getValue() {
/* 57 */     return this.dateFormat.getEditor().getItem();
/*    */   }
/*    */ 
/*    */   public void actionPerformed(ActionEvent e)
/*    */   {
/* 62 */     if ((e.getActionCommand().equals("date format")) && 
/* 63 */       (!this.dateFormat.getSelectedItem().equals("自定义...")))
/*    */     {
/* 66 */       if (this.parent != null)
/* 67 */         this.parent.fireChange(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void setEnabled(boolean enabled)
/*    */   {
/* 73 */     super.setEnabled(enabled);
/* 74 */     this.dateLabel.setEnabled(enabled);
/* 75 */     this.dateFormat.setEditable(enabled);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.DateFormatComponent
 * JD-Core Version:    0.6.2
 */