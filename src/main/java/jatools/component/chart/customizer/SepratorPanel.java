/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.CommonFinal;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JSeparator;
/*    */ 
/*    */ public class SepratorPanel extends JPanel
/*    */ {
/*    */   public SepratorPanel(String s)
/*    */   {
/* 32 */     GridBagLayout gbl = new GridBagLayout();
/* 33 */     setLayout(gbl);
/* 34 */     GridBagConstraints gbc = new GridBagConstraints();
/* 35 */     gbc.insets = CommonFinal.INSETS;
/* 36 */     gbc.fill = 2;
/*    */ 
/* 38 */     JLabel l = new JLabel(s);
/* 39 */     JSeparator js = new JSeparator(0);
/* 40 */     js.setPreferredSize(new Dimension(100, 2));
/*    */ 
/* 42 */     gbc.gridwidth = 1;
/* 43 */     add(l, gbc);
/* 44 */     gbc.gridwidth = 0;
/* 45 */     gbc.weightx = 1.0D;
/* 46 */     add(js, gbc);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.SepratorPanel
 * JD-Core Version:    0.6.2
 */