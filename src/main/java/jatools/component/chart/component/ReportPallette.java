/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import jatools.component.chart.ChartFactory;
/*    */ import jatools.component.chart.PanelLabelMap;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import jatools.component.chart.servlet.Bean;
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Container;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JList;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.ListCellRenderer;
/*    */ 
/*    */ public class ReportPallette extends JLabel
/*    */   implements ListCellRenderer
/*    */ {
/*    */   static final int DROP_GAP = 7;
/*    */   static final int ICON_SIZE = 30;
/*    */   static final int COLOR_DROP_SIZE = 12;
/* 27 */   static final Object[][] contents = new Object[29][2];
/*    */ 
/* 29 */   static { for (int i = 0; i < 29; i++) {
/* 30 */       ChartInterface chart = ChartFactory.createInstance(i).getChart();
/* 31 */       Icon icon = new ChartIcon(chart, 30, 30);
/* 32 */       String label = PanelLabelMap.getLabel((_Chart)chart);
/* 33 */       contents[i][0] = icon;
/* 34 */       contents[i][1] = label;
/*    */     }
/*    */   }
/*    */ 
/*    */   public void paintComponent(Graphics g)
/*    */   {
/* 42 */     g.setColor(Color.RED);
/* 43 */     g.fillRect(0, 0, 100, 100);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 47 */     JFrame frame = new JFrame();
/* 48 */     JPanel p = new JPanel();
/* 49 */     ReportComboBox r = new ReportComboBox();
/* 50 */     p.add(r);
/* 51 */     frame.getContentPane().add(p);
/* 52 */     frame.setSize(400, 400);
/* 53 */     frame.setVisible(true);
/*    */   }
/*    */   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
/* 56 */     _Chart chart = (_Chart)ChartFactory.createInstance(Integer.parseInt((String)value)).getChart();
/* 57 */     setIcon(new ChartIcon(chart, 10, 10));
/* 58 */     setText(PanelLabelMap.getLabel(chart));
/* 59 */     return this;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ReportPallette
 * JD-Core Version:    0.6.2
 */