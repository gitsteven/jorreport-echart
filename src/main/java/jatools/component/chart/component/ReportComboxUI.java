/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.plaf.ComboBoxUI;
/*    */ 
/*    */ public class ReportComboxUI extends ComboBoxUI
/*    */ {
/*    */   public boolean isFocusTraversable(JComboBox c)
/*    */   {
/* 10 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean isPopupVisible(JComboBox c)
/*    */   {
/* 15 */     return false;
/*    */   }
/*    */ 
/*    */   public void setPopupVisible(JComboBox c, boolean v)
/*    */   {
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ReportComboxUI
 * JD-Core Version:    0.6.2
 */