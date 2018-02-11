/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.Tip;
/*     */ import java.awt.Component;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.AbstractCellEditor;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.TableCellEditor;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ 
/*     */ public class ButtonCellEditor extends AbstractCellEditor
/*     */   implements TableCellEditor, TableCellRenderer
/*     */ {
/*     */   _TipDialog tipEditor;
/*     */   JButton b;
/*     */   JTable t;
/*     */   Tip tip;
/*     */ 
/*     */   public ButtonCellEditor(String text)
/*     */   {
/*  49 */     this.b = new JButton(text);
/*  50 */     this.b.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  52 */         ButtonCellEditor.this.doEdit();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
/*     */   {
/*  70 */     this.tip = ((Tip)value);
/*  71 */     this.t = table;
/*     */ 
/*  73 */     return this.b;
/*     */   }
/*     */ 
/*     */   private void doEdit() {
/*  77 */     if (this.tipEditor == null) {
/*  78 */       this.tipEditor = new _TipDialog((JDialog)this.t.getTopLevelAncestor());
/*     */     }
/*     */ 
/*  81 */     this.tipEditor.setTip(this.tip);
/*  82 */     this.tipEditor.show();
/*     */ 
/*  84 */     if (this.tipEditor.done) {
/*  85 */       this.tip = this.tipEditor.getTip();
/*  86 */       stopCellEditing();
/*     */     } else {
/*  88 */       cancelCellEditing();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
/*     */   {
/* 106 */     return this.b;
/*     */   }
/*     */ 
/*     */   public Object getCellEditorValue()
/*     */   {
/* 115 */     return this.tip;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.ButtonCellEditor
 * JD-Core Version:    0.6.2
 */