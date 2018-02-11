/*     */ package jatools.component.chart.component;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.ListModel;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ 
/*     */ public class ZListEditor extends JPanel
/*     */ {
/*     */   JList list;
/*     */   JTextField field;
/*     */   JLabel label;
/*  31 */   ArrayList listeners = new ArrayList();
/*     */ 
/*     */   public ZListEditor(Object[] items)
/*     */   {
/*  39 */     this(items, null, false);
/*     */   }
/*     */ 
/*     */   public ZListEditor(Object[] items, String title, boolean editable)
/*     */   {
/*  49 */     this.list = new JList(items);
/*  50 */     this.list.setSelectionMode(0);
/*  51 */     this.field = new JTextField();
/*  52 */     this.field.addKeyListener(new KeyAdapter() {
/*     */       public void keyReleased(KeyEvent e) {
/*  54 */         ZListEditor.this.fireChangeListener();
/*     */       }
/*     */     });
/*  58 */     this.field.setEditable(editable);
/*  59 */     setLayout(new BorderLayout());
/*     */ 
/*  61 */     if (title == null) {
/*  62 */       add(this.field, "North");
/*     */     } else {
/*  64 */       JPanel titleBox = new JPanel();
/*  65 */       titleBox.setLayout(new BoxLayout(titleBox, 1));
/*  66 */       titleBox.add(this.label = new JLabel(title));
/*  67 */       titleBox.add(this.field);
/*  68 */       add(titleBox, "North");
/*     */     }
/*     */ 
/*  71 */     add(new JScrollPane(this.list), "Center");
/*     */ 
/*  73 */     this.list.addListSelectionListener(new ListSelectionListener() {
/*     */       public void valueChanged(ListSelectionEvent e) {
/*  75 */         ZListEditor.this.field.setText(ZListEditor.this.list.getSelectedValue());
/*  76 */         ZListEditor.this.fireChangeListener();
/*     */       }
/*     */     });
/*  79 */     this.list.setSelectedIndex(0);
/*     */   }
/*     */ 
/*     */   public ZListEditor(Object[] items, String title, boolean editable, int index) {
/*  83 */     this.list = new JList(items);
/*  84 */     this.list.setSelectionMode(0);
/*  85 */     this.field = new JTextField();
/*  86 */     this.field.addKeyListener(new KeyAdapter() {
/*     */       public void keyReleased(KeyEvent e) {
/*  88 */         ZListEditor.this.fireChangeListener();
/*     */       }
/*     */     });
/*  92 */     this.field.setEditable(editable);
/*  93 */     setLayout(new BorderLayout());
/*     */ 
/*  95 */     if (title == null) {
/*  96 */       add(this.field, "North");
/*     */     } else {
/*  98 */       JPanel titleBox = new JPanel();
/*  99 */       titleBox.setLayout(new BoxLayout(titleBox, 1));
/* 100 */       titleBox.add(this.label = new JLabel(title));
/* 101 */       titleBox.add(this.field);
/* 102 */       add(titleBox, "North");
/*     */     }
/*     */ 
/* 105 */     add(new JScrollPane(this.list), "Center");
/*     */ 
/* 107 */     this.list.addListSelectionListener(new ListSelectionListener() {
/*     */       public void valueChanged(ListSelectionEvent e) {
/* 109 */         ZListEditor.this.field.setText(ZListEditor.this.list.getSelectedValue());
/* 110 */         ZListEditor.this.fireChangeListener();
/*     */       }
/*     */     });
/* 113 */     this.list.setSelectedIndex(0);
/*     */   }
/*     */ 
/*     */   public void addChangeListener(ChangeListener l)
/*     */   {
/* 122 */     this.listeners.add(l);
/*     */   }
/*     */ 
/*     */   public void setSelectable(Object[] selectable) {
/* 126 */     DefaultListModel model = new DefaultListModel();
/*     */ 
/* 128 */     for (int i = 0; i < selectable.length; i++) {
/* 129 */       model.addElement(selectable[i]);
/*     */     }
/*     */ 
/* 132 */     this.list.setModel(model);
/*     */ 
/* 134 */     if (selectable.length > 0)
/* 135 */       this.list.setSelectedIndex(0);
/*     */   }
/*     */ 
/*     */   public void setEnabled(boolean e)
/*     */   {
/* 140 */     this.list.setEnabled(e);
/* 141 */     this.field.setEditable(e);
/*     */ 
/* 143 */     if (this.label != null)
/* 144 */       this.label.setEnabled(e);
/*     */   }
/*     */ 
/*     */   public void removeChangeListener(ChangeListener l)
/*     */   {
/* 154 */     this.listeners.remove(l);
/*     */   }
/*     */ 
/*     */   protected void fireChangeListener()
/*     */   {
/* 161 */     ChangeEvent e = new ChangeEvent(this);
/*     */ 
/* 163 */     for (int i = 0; i < this.listeners.size(); i++) {
/* 164 */       ChangeListener l = (ChangeListener)this.listeners.get(i);
/* 165 */       l.stateChanged(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getSelectedValue()
/*     */   {
/* 175 */     return this.field.getText();
/*     */   }
/*     */ 
/*     */   public int getSelectedIndex()
/*     */   {
/* 184 */     return this.list.getSelectedIndex();
/*     */   }
/*     */ 
/*     */   public void setSelectedIndex(int index)
/*     */   {
/* 193 */     this.list.setSelectedIndex(index);
/*     */   }
/*     */ 
/*     */   public void setSelectedValue(Object value)
/*     */   {
/* 202 */     this.list.setSelectedValue(value, true);
/*     */   }
/*     */ 
/*     */   public Object getElementAt(int i) {
/* 206 */     if ((i >= 0) && (i < this.list.getModel().getSize())) {
/* 207 */       return this.list.getModel().getElementAt(i);
/*     */     }
/* 209 */     return null;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ZListEditor
 * JD-Core Version:    0.6.2
 */