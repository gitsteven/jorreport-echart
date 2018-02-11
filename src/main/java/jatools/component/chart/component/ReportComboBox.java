/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import java.awt.event.MouseAdapter;
/*    */ import java.awt.event.MouseEvent;
/*    */ import java.awt.event.MouseMotionAdapter;
/*    */ import javax.swing.DefaultComboBoxModel;
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JList;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public class ReportComboBox extends JComboBox
/*    */ {
/* 18 */   private DefaultComboBoxModel model = new DefaultComboBoxModel();
/*    */   private ReportListRenderer renderer;
/* 21 */   private String type = "1";
/*    */ 
/*    */   public ReportComboBox() {
/* 24 */     this.renderer = new ReportListRenderer();
/* 25 */     setRenderer(this.renderer);
/* 26 */     setModel(this.model);
/* 27 */     ZFixedSizeComboBoxUI ui = new ZFixedSizeComboBoxUI();
/* 28 */     setUI(ui);
/* 29 */     listenToList(ui.getPopupList());
/* 30 */     this.model.addElement(this.type);
/*    */   }
/*    */ 
/*    */   public void setType(String type) {
/* 34 */     this.type = type;
/* 35 */     this.renderer.setType(type);
/* 36 */     repaint();
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 40 */     return this.type;
/*    */   }
/*    */ 
/*    */   private void listenToList(JList popupList) {
/* 44 */     JList list = popupList;
/* 45 */     list.addMouseListener(new MouseAdapter()
/*    */     {
/*    */       public void mousePressed(MouseEvent e) {
/* 48 */         ReportComboBox.this.renderer.mousePressed(e);
/* 49 */         ReportComboBox.this.type = ReportComboBox.this.renderer.getType();
/* 50 */         ReportComboBox.this.setSelectedIndex(0);
/*    */       }
/*    */     });
/* 55 */     list.addMouseMotionListener(new MouseMotionAdapter()
/*    */     {
/*    */       public void mouseMoved(MouseEvent e) {
/* 58 */         ReportComboBox.this.renderer.mouseMoved(e);
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 65 */     JFrame frame = new JFrame();
/* 66 */     JPanel panel = new JPanel(new GridBagLayout());
/* 67 */     GridBagConstraints gbc = new GridBagConstraints();
/* 68 */     gbc.fill = 2;
/* 69 */     gbc.weightx = 1.0D;
/* 70 */     panel.add(new ReportComboBox(), gbc);
/*    */ 
/* 72 */     frame.getContentPane().add(panel);
/* 73 */     frame.setSize(400, 400);
/* 74 */     frame.setVisible(true);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ReportComboBox
 * JD-Core Version:    0.6.2
 */