/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Rectangle;
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.JList;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.ListCellRenderer;
/*    */ import javax.swing.plaf.basic.BasicComboBoxUI;
/*    */ import javax.swing.plaf.basic.BasicComboPopup;
/*    */ import javax.swing.plaf.basic.ComboPopup;
/*    */ 
/*    */ class ZFixedSizeComboBoxUI extends BasicComboBoxUI
/*    */ {
/*    */   protected Dimension getDisplaySize()
/*    */   {
/* 28 */     return getDefaultSize();
/*    */   }
/*    */ 
/*    */   public JList getPopupList()
/*    */   {
/* 37 */     return this.listBox;
/*    */   }
/*    */ 
/*    */   protected ComboPopup createPopup()
/*    */   {
/* 46 */     return new ZComboPopup(this.comboBox);
/*    */   }
/*    */ 
/*    */   class ZComboPopup extends BasicComboPopup
/*    */   {
/*    */     public ZComboPopup(JComboBox combo)
/*    */     {
/* 62 */       super();
/*    */     }
/*    */ 
/*    */     public void show()
/*    */     {
/* 69 */       ListCellRenderer rend = this.comboBox.getRenderer();
/* 70 */       Component comp = rend.getListCellRendererComponent(this.list, null, 0, false, false);
/* 71 */       Dimension popupSize = comp.getPreferredSize();
/*    */ 
/* 73 */       popupSize.setSize(popupSize.width, getPopupHeightForRowCount(this.comboBox.getMaximumRowCount()));
/* 74 */       this.scroller.setMaximumSize(popupSize);
/* 75 */       this.scroller.setPreferredSize(popupSize);
/* 76 */       this.scroller.setMinimumSize(popupSize);
/*    */ 
/* 78 */       Rectangle popupBounds = computePopupBounds(0, this.comboBox.getBounds().height, popupSize.width, 
/* 79 */         popupSize.height);
/* 80 */       this.list.invalidate();
/* 81 */       this.list.setSelectedIndex(this.comboBox.getSelectedIndex());
/* 82 */       this.list.ensureIndexIsVisible(this.list.getSelectedIndex());
/*    */ 
/* 84 */       setLightWeightPopupEnabled(this.comboBox.isLightWeightPopupEnabled());
/*    */ 
/* 86 */       show(this.comboBox, popupBounds.x, popupBounds.y);
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ZFixedSizeComboBoxUI
 * JD-Core Version:    0.6.2
 */