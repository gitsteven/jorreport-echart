/*     */ package jatools.component.chart.component;
/*     */ 
/*     */ import jatools.util.Util;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.ListCellRenderer;
/*     */ 
/*     */ public class ReportListRenderer extends JLabel
/*     */   implements ListCellRenderer
/*     */ {
/*     */   private static final int INSETS = 10;
/*     */   private static final int GAP = 5;
/*     */   private static final int BORDER = 3;
/*     */   private static final int ROW_ICONSNUM = 6;
/*     */   private static final int COL_ICONSNUM = 5;
/*     */   private static final int ICON_WIDTH = 53;
/*     */   private static final int ICON_HEIGHT = 54;
/*  27 */   private static final String[] labels = { 
/*  28 */     "条形图", 
/*  29 */     "柱形图", 
/*  30 */     "面积图", 
/*  31 */     "堆叠条形图", 
/*  32 */     "堆叠柱形图", 
/*  33 */     "饼图", 
/*  34 */     "线形图", 
/*  35 */     "柱形线形图", 
/*  36 */     "柱形面积图", 
/*  37 */     "时间线形图", 
/*  38 */     "时间面积图", 
/*  39 */     "时间柱形图", 
/*  40 */     "时间堆叠柱形图", 
/*  41 */     "时间条形图", 
/*  42 */     "双轴时间线形图", 
/*  43 */     "时间堆叠条形图", 
/*  44 */     "汽泡图", 
/*  45 */     "趋势图", 
/*  47 */     "雷达图", 
/*  48 */     "方块图", 
/*  49 */     "双轴柱形堆积图", 
/*  50 */     "双轴柱形线形图", 
/*  51 */     "双轴线形图", 
/*  52 */     "高低条形图", 
/*  53 */     "高低柱形图", 
/*  54 */     "K线图", 
/*  55 */     "高低收盘图", 
/*  56 */     "甘特图", "柱形面积线形" };
/*     */ 
/*  60 */   private static final String[] imgurls = { 
/*  61 */     "0", 
/*  62 */     "1", 
/*  63 */     "2", 
/*  64 */     "3", 
/*  65 */     "4", 
/*  66 */     "5", 
/*  67 */     "6", 
/*  68 */     "7", 
/*  69 */     "8", 
/*  70 */     "9", 
/*  71 */     "10", 
/*  72 */     "11", 
/*  73 */     "12", 
/*  74 */     "13", 
/*  75 */     "14", 
/*  76 */     "15", 
/*  77 */     "16", 
/*  78 */     "17", 
/*  80 */     "19", 
/*  81 */     "20", 
/*  82 */     "21", 
/*  83 */     "22", 
/*  84 */     "23", 
/*  85 */     "24", 
/*  86 */     "25", 
/*  87 */     "26", 
/*  88 */     "27", 
/*  89 */     "28", 
/*  90 */     "29" };
/*     */ 
/*  94 */   private ImageIcon[] icons = new ImageIcon[29];
/*  95 */   private boolean atHeader = true;
/*  96 */   private String type = "1";
/*  97 */   private int hitIndex = -1;
/*  98 */   private int selectedIndex = 1;
/*  99 */   int bw = 59;
/* 100 */   int bh = 60;
/*     */ 
/*     */   public ReportListRenderer()
/*     */   {
/* 106 */     for (int i = 0; i < this.icons.length; i++) {
/* 107 */       this.icons[i] = Util.getIcon("/jatools/component/chart/icons/chart" + imgurls[i] + ".gif");
/*     */     }
/*     */ 
/* 110 */     int width = 399;
/*     */ 
/* 112 */     int height = 340;
/*     */ 
/* 114 */     setPreferredSize(new Dimension(width, height));
/*     */   }
/*     */ 
/*     */   private void setAtHeader(boolean yesno) {
/* 118 */     this.atHeader = yesno;
/*     */   }
/*     */ 
/*     */   public void paintComponent(Graphics g1)
/*     */   {
/* 127 */     Graphics2D g = (Graphics2D)g1.create();
/*     */ 
/* 129 */     if (this.atHeader) {
/* 130 */       g.setColor(Color.BLACK);
/* 131 */       g.drawString(labels[Integer.parseInt(this.type)], 3, 13);
/*     */ 
/* 133 */       return;
/*     */     }
/*     */ 
/* 136 */     int x = 10;
/* 137 */     int y = 10;
/*     */ 
/* 139 */     g.setColor(Color.WHITE);
/* 140 */     g.fillRect(0, 0, getBounds().width, getBounds().height);
/*     */ 
/* 142 */     for (int i = 0; i < this.icons.length; i++) {
/* 143 */       g.setColor(Color.GRAY);
/*     */ 
/* 145 */       if (this.selectedIndex == i)
/* 146 */         g.fillRect(x, y, this.bw, this.bh);
/*     */       else {
/* 148 */         g.drawRect(x, y, this.bw, this.bh);
/*     */       }
/*     */ 
/* 151 */       this.icons[i].paintIcon(this, g, x + 3, y + 3);
/*     */ 
/* 153 */       if ((i + 1) % 6 == 0) {
/* 154 */         x = 10;
/* 155 */         y += this.bh + 5;
/*     */       } else {
/* 157 */         x += this.bw + 5;
/*     */       }
/*     */     }
/*     */ 
/* 161 */     g.dispose();
/*     */   }
/*     */ 
/*     */   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
/*     */   {
/* 177 */     setAtHeader(index == -1);
/*     */ 
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */   private int hitIndex(int x, int y) {
/* 183 */     int row = (y - 10 + 5) / (this.bh + 5);
/* 184 */     int col = (x - 10 + 5) / (this.bw + 5);
/* 185 */     int returnVal = row * 6 + col;
/*     */ 
/* 187 */     if ((x < 10 + col * (this.bw + 5)) || (y < 10 + row * (this.bh + 5))) {
/* 188 */       return -1;
/*     */     }
/*     */ 
/* 191 */     if (returnVal >= 30) {
/* 192 */       returnVal = -1;
/*     */     }
/*     */ 
/* 195 */     return returnVal;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 204 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent e)
/*     */   {
/* 213 */     if (this.hitIndex != -1)
/* 214 */       this.type = String.valueOf(this.selectedIndex);
/*     */   }
/*     */ 
/*     */   public void mouseMoved(MouseEvent e)
/*     */   {
/* 229 */     this.hitIndex = hitIndex(e.getX(), e.getY());
/*     */ 
/* 231 */     if ((this.hitIndex != -1) && (this.hitIndex != this.selectedIndex)) {
/* 232 */       setToolTipText(labels[this.hitIndex]);
/* 233 */       this.selectedIndex = this.hitIndex;
/*     */ 
/* 235 */       JComponent c = (JComponent)e.getSource();
/* 236 */       c.repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/* 246 */     this.type = type;
/* 247 */     this.selectedIndex = Integer.parseInt(type);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ReportListRenderer
 * JD-Core Version:    0.6.2
 */