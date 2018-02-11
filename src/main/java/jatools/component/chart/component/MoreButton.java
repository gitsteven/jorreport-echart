/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Font;
/*    */ import java.awt.Insets;
/*    */ import javax.swing.JButton;
/*    */ 
/*    */ public class MoreButton extends JButton
/*    */ {
/*    */   public MoreButton()
/*    */   {
/* 14 */     this(">>");
/*    */   }
/*    */ 
/*    */   public MoreButton(String caption)
/*    */   {
/* 23 */     super(caption);
/* 24 */     setPreferredSize(new Dimension(70, 25));
/* 25 */     setFont(new Font("Dialog", 0, 12));
/* 26 */     setMaximumSize(getPreferredSize());
/* 27 */     setMinimumSize(getPreferredSize());
/* 28 */     setIconTextGap(0);
/* 29 */     setMargin(new Insets(0, 0, 0, 0));
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.MoreButton
 * JD-Core Version:    0.6.2
 */