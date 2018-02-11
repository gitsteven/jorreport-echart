/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.JLabel;
/*    */ 
/*    */ public class HeaderLabel extends JLabel
/*    */ {
/*    */   Icon icon;
/*    */   String label;
/*    */   int width;
/*    */   int height;
/*    */ 
/*    */   public HeaderLabel(Icon icon, String label, Dimension d)
/*    */   {
/* 15 */     this.icon = icon;
/* 16 */     this.label = label;
/* 17 */     this.width = d.width;
/* 18 */     this.height = d.height;
/*    */   }
/*    */ 
/*    */   public void paintComponent(Graphics g1) {
/* 22 */     Graphics g = g1.create();
/* 23 */     this.icon.paintIcon(this, g, 3, 3);
/* 24 */     g.setColor(Color.BLACK);
/* 25 */     g.drawString(this.label, this.icon.getIconWidth() + 6, this.height / 2 + 3);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.HeaderLabel
 * JD-Core Version:    0.6.2
 */