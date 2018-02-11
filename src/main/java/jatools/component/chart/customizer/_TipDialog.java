/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.Tip;
/*     */ import jatools.swingx.CommandPanel;
/*     */ import jatools.swingx.GridBagConstraints2;
/*     */ import jatools.swingx.SwingUtil;
/*     */ import jatools.swingx.TemplateTextField;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ class _TipDialog extends JDialog
/*     */ {
/*     */   Tip tip;
/* 122 */   TemplateTextField labelField = new TemplateTextField();
/* 123 */   TemplateTextField urlField = new TemplateTextField();
/*     */   boolean done;
/* 125 */   JComboBox targetCombo = new JComboBox(new Object[] { 
/* 126 */     "_blank", 
/* 127 */     "_parent", 
/* 128 */     "_self", 
/* 129 */     "_top" });
/*     */ 
/*     */   _TipDialog(JDialog d)
/*     */   {
/* 133 */     super(d, "热点提示及超链接设置", true);
/*     */ 
/* 135 */     JPanel p = new JPanel(new GridBagLayout());
/* 136 */     GridBagConstraints2 gbc = new GridBagConstraints2(p);
/* 137 */     p.add(new JLabel("热点提示:"), gbc);
/* 138 */     gbc.fill = 2;
/* 139 */     gbc.insets = new Insets(2, 2, 2, 2);
/* 140 */     gbc.weightx = 1.0D;
/* 141 */     gbc.anchor = 17;
/* 142 */     gbc.gridwidth = 0;
/* 143 */     p.add(this.labelField, gbc);
/* 144 */     gbc.weightx = 0.0D;
/*     */ 
/* 147 */     gbc.gridwidth = 1;
/* 148 */     p.add(new JLabel("超链接:"), gbc);
/* 149 */     gbc.gridwidth = 0;
/* 150 */     p.add(this.urlField, gbc);
/*     */ 
/* 153 */     gbc.gridwidth = 1;
/* 154 */     p.add(new JLabel("目标:"), gbc);
/*     */ 
/* 156 */     gbc.add(this.targetCombo, 100);
/* 157 */     getContentPane().add(p, "Center");
/*     */ 
/* 159 */     ActionListener okListener = new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 161 */         _TipDialog.this.done();
/*     */       }
/*     */     };
/* 165 */     ActionListener cancelListener = new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 167 */         _TipDialog.this.cancel();
/*     */       }
/*     */     };
/* 171 */     JPanel command = CommandPanel.createPanel(okListener, cancelListener);
/*     */ 
/* 173 */     command.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*     */ 
/* 175 */     getContentPane().add(command, "South");
/*     */ 
/* 177 */     SwingUtil.setBorder6((JPanel)getContentPane());
/* 178 */     setSize(new Dimension(400, 180));
/*     */ 
/* 180 */     setLocationRelativeTo(d);
/*     */   }
/*     */ 
/*     */   protected void done() {
/* 184 */     this.done = true;
/* 185 */     hide();
/*     */   }
/*     */ 
/*     */   protected void cancel() {
/* 189 */     hide();
/*     */   }
/*     */ 
/*     */   public Tip getTip()
/*     */   {
/* 198 */     String tip = this.labelField.getText();
/*     */ 
/* 200 */     if ((tip != null) && (tip.trim().length() == 0)) {
/* 201 */       tip = null;
/*     */     }
/*     */ 
/* 204 */     String url = this.urlField.getText();
/*     */ 
/* 206 */     if ((url != null) && (url.trim().length() == 0)) {
/* 207 */       url = null;
/*     */     }
/*     */ 
/* 210 */     if ((tip == null) && (url == null)) {
/* 211 */       return null;
/*     */     }
/*     */ 
/* 214 */     String target = null;
/*     */ 
/* 216 */     if (url != null) {
/* 217 */       target = (String)this.targetCombo.getSelectedItem();
/*     */     }
/*     */ 
/* 220 */     return new Tip(tip, url, target);
/*     */   }
/*     */ 
/*     */   public void setTip(Tip tip)
/*     */   {
/* 229 */     if (tip == null) {
/* 230 */       tip = new Tip();
/*     */     }
/*     */ 
/* 233 */     this.labelField.setText(tip.getTip());
/* 234 */     this.urlField.setText(tip.getUrl());
/* 235 */     this.targetCombo.setSelectedItem(tip.getTarget());
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer._TipDialog
 * JD-Core Version:    0.6.2
 */