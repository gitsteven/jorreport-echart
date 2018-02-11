/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Image;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JLabel;
/*    */ 
/*    */ public class ZImageViewer extends JLabel
/*    */ {
/* 16 */   ImageIcon icon = null;
/*    */   ImageIcon rawIcon;
/*    */ 
/*    */   public void setImageIcon(ImageIcon rawIcon)
/*    */   {
/* 20 */     if (rawIcon != null)
/*    */     {
/* 22 */       if ((rawIcon.getIconWidth() <= getWidth()) && 
/* 23 */         (rawIcon.getIconHeight() <= getHeight())) {
/* 24 */         this.icon = rawIcon;
/*    */       }
/* 27 */       else if (getWidth() * 1.0D / rawIcon.getIconWidth() > 
/* 27 */         getHeight() * 1.0D / rawIcon.getIconHeight())
/* 28 */         this.icon = 
/* 29 */           new ImageIcon(
/* 30 */           rawIcon.getImage().getScaledInstance(
/* 31 */           -1, 
/* 32 */           getHeight(), 
/* 33 */           1));
/*    */       else {
/* 35 */         this.icon = 
/* 36 */           new ImageIcon(
/* 37 */           rawIcon.getImage().getScaledInstance(
/* 38 */           getWidth(), 
/* 39 */           -1, 
/* 40 */           1));
/*    */       }
/*    */ 
/* 45 */       this.rawIcon = rawIcon;
/*    */     }
/*    */   }
/*    */ 
/*    */   public ImageIcon getRawIcon()
/*    */   {
/* 52 */     return this.rawIcon;
/*    */   }
/*    */ 
/*    */   public void paintComponent(Graphics g) {
/* 56 */     g.setColor(getBackground());
/* 57 */     g.fillRect(0, 0, getWidth(), getHeight());
/*    */ 
/* 59 */     if (this.icon != null) {
/* 60 */       int x = getWidth() / 2 - this.icon.getIconWidth() / 2;
/* 61 */       int y = getHeight() / 2 - this.icon.getIconHeight() / 2;
/*    */ 
/* 63 */       if (y < 0) {
/* 64 */         y = 0;
/*    */       }
/*    */ 
/* 67 */       if (x < 5) {
/* 68 */         x = 5;
/*    */       }
/*    */ 
/* 71 */       this.icon.paintIcon(this, g, x, y);
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ZImageViewer
 * JD-Core Version:    0.6.2
 */