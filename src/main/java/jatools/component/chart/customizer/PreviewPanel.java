/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.CommonFinal;
/*    */ import jatools.component.chart.component.ColorIcon;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.border.TitledBorder;
/*    */ 
/*    */ public class PreviewPanel extends JPanel
/*    */ {
/*    */   JLabel preview;
/*    */   Icon icon;
/*    */ 
/*    */   public PreviewPanel()
/*    */   {
/* 19 */     GridBagLayout gbl = new GridBagLayout();
/* 20 */     GridBagConstraints gbc = new GridBagConstraints();
/* 21 */     gbc.insets = CommonFinal.INSETS;
/* 22 */     gbc.fill = 1;
/* 23 */     gbc.weightx = 1.0D;
/* 24 */     gbc.weighty = 1.0D;
/* 25 */     setLayout(gbl);
/* 26 */     setBorder(new TitledBorder("预览"));
/* 27 */     gbc.weightx = 0.0D;
/* 28 */     gbc.weighty = 0.0D;
/* 29 */     this.icon = new ColorIcon(CommonFinal.VIEW_ICON_SIZE);
/* 30 */     this.preview = new JLabel();
/* 31 */     this.preview.setIcon(this.icon);
/* 32 */     add(this.preview, gbc);
/*    */   }
/*    */ 
/*    */   public void setIcon(Icon icon) {
/* 36 */     this.preview.setIcon(icon);
/* 37 */     this.preview.repaint();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.PreviewPanel
 * JD-Core Version:    0.6.2
 */