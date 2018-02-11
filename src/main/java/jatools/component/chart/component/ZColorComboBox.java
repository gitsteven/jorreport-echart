/*     */ package jatools.component.chart.component;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ComboBoxEditor;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.ListCellRenderer;
/*     */ 
/*     */ public class ZColorComboBox extends JComboBox
/*     */   implements ListCellRenderer
/*     */ {
/*     */   public static final int NULL_BOX_VISIBLE = 1;
/*     */   public static final int OTHER_BOX_VISIBLE = 2;
/*     */   public static final String PROPERTY_POPUP_CLOSED = "popup_closed";
/*  35 */   private static final Dimension HEAD_ICON_SIZE = new Dimension(20, 10);
/*     */   private ZColorPallette listRenderer;
/*  43 */   private Color color = Color.black;
/*     */ 
/*  46 */   private DefaultComboBoxModel model = new DefaultComboBoxModel();
/*     */   private FillStyleInterface style;
/*     */ 
/*     */   public ZColorComboBox()
/*     */   {
/*  54 */     this(3);
/*     */   }
/*     */ 
/*     */   public ZColorComboBox(int textVisible)
/*     */   {
/*  65 */     this.model.addElement(this.color);
/*     */ 
/*  67 */     setModel(this.model);
/*     */ 
/*  71 */     this.listRenderer = new ZColorPallette(textVisible);
/*     */ 
/*  75 */     setRenderer(this);
/*     */ 
/*  78 */     ZFixedSizeComboBoxUI ui = new ZFixedSizeComboBoxUI();
/*  79 */     setUI(ui);
/*     */ 
/*  83 */     listenToList(ui.getPopupList());
/*     */ 
/*  87 */     setSelectedIndex(0);
/*     */ 
/*  91 */     setEditable(false);
/*     */ 
/*  93 */     JComponent jc = (JComponent)getEditor().getEditorComponent();
/*  94 */     jc.setBorder(BorderFactory.createLineBorder(Color.red));
/*     */   }
/*     */ 
/*     */   public void setColor(Color color)
/*     */   {
/* 103 */     this.color = color;
/*     */ 
/* 105 */     repaint();
/*     */   }
/*     */ 
/*     */   public void setFillStyle(FillStyleInterface style) {
/* 109 */     this.style = style;
/* 110 */     repaint();
/*     */   }
/*     */   public FillStyleInterface getFillStyle() {
/* 113 */     return this.style;
/*     */   }
/*     */ 
/*     */   public Color getColor()
/*     */   {
/* 121 */     return this.color;
/*     */   }
/*     */ 
/*     */   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
/*     */   {
/* 137 */     this.listRenderer.setColor(this.color);
/*     */ 
/* 139 */     if (index == -1) {
/* 140 */       return this.style.createLabel(getSize());
/*     */     }
/*     */ 
/* 144 */     this.listRenderer.setAtHeader(index == -1);
/*     */ 
/* 146 */     return this.listRenderer;
/*     */   }
/*     */ 
/*     */   private void listenToList(JList list)
/*     */   {
/* 157 */     list.addMouseListener(new MouseAdapter() {
/*     */       public void mousePressed(MouseEvent e) {
/* 159 */         ZColorComboBox.this.listRenderer.mousePressed(e);
/* 160 */         ZColorComboBox.this.color = ZColorComboBox.this.listRenderer.getColor();
/* 161 */         ZColorComboBox.this.setSelectedIndex(0);
/*     */       }
/*     */ 
/*     */       public void mouseReleased(MouseEvent e)
/*     */       {
/* 166 */         ZColorComboBox.this.firePropertyChange("popup_closed", false, true);
/*     */       }
/*     */ 
/*     */       public void mouseExited(MouseEvent e) {
/* 170 */         ZColorComboBox.this.listRenderer.mouseExited(e);
/*     */       }
/*     */     });
/* 173 */     list.addMouseMotionListener(new MouseMotionAdapter() {
/*     */       public void mouseMoved(MouseEvent e) {
/* 175 */         ZColorComboBox.this.listRenderer.mouseMoved(e);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static void main(String[] argv)
/*     */   {
/* 186 */     ZColorComboBox content = new ZColorComboBox();
/* 187 */     SingleColor s = new SingleColor(Color.RED);
/* 188 */     content.setFillStyle(s);
/* 189 */     JDialog d = new JDialog(null, "Just For Test !");
/* 190 */     d.setModal(true);
/* 191 */     d.getContentPane().add(content, "Center");
/* 192 */     d.pack();
/* 193 */     d.show();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ZColorComboBox
 * JD-Core Version:    0.6.2
 */