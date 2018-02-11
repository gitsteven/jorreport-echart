/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.Chart;
/*     */ import jatools.component.chart.PlotData;
/*     */ import jatools.component.chart.Tip;
/*     */ import jatools.data.reader.DatasetReader;
/*     */ import jatools.dataset.Column;
/*     */ import jatools.designer.App;
/*     */ import jatools.designer.DataTree;
/*     */ import jatools.designer.DataTreeUtil;
/*     */ import jatools.designer.data.ReaderChooser;
/*     */ import jatools.swingx.CustomTable;
/*     */ import jatools.swingx.MessageBox;
/*     */ import jatools.swingx.SimpleTreeNode;
/*     */ import jatools.swingx.SwingUtil;
/*     */ import jatools.util.Util;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import javax.swing.event.TableModelEvent;
/*     */ import javax.swing.event.TableModelListener;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ 
/*     */ public class DataSelector extends JPanel
/*     */   implements ChangeListener, ListSelectionListener, TableModelListener
/*     */ {
/*  58 */   private JPanel leftPanel = new JPanel();
/*  59 */   private JPanel rightPanel = new JPanel();
/*  60 */   private JPanel labelPanel = new JPanel();
/*  61 */   private JPanel dataPanel = new JPanel();
/*  62 */   private GridBagLayout gridBagLayout1 = new GridBagLayout();
/*  63 */   private JPanel labelCommandPanel = new JPanel(new GridBagLayout());
/*  64 */   private JPanel dataCommandPanel = new JPanel(new GridBagLayout());
/*  65 */   private BorderLayout borderLayout1 = new BorderLayout();
/*  66 */   private BorderLayout borderLayout2 = new BorderLayout();
/*  67 */   private GridLayout gridLayout1 = new GridLayout();
/*     */   JLabel jLabel3;
/*     */   JLabel jLabel2;
/*     */   JButton labelSelectCommand;
/*     */   JButton labelUnselectCommand;
/*     */   JButton dataSelectCommand;
/*     */   JButton dataUnselectCommand;
/*     */   JButton dataUnselectAllCommand;
/*     */   JButton downCommand;
/*     */   JButton upCommand;
/*     */   JPanel commandPanel;
/*     */   JPanel jPanel1;
/*     */   CustomTable labelTable;
/*     */   CustomTable dataTable;
/*     */   DataTree variableExplorer;
/*     */   Column hitField;
/*     */   DatasetReader reader;
/*     */   private ArrayList changeListeners;
/*     */   private JLabel promptLabel;
/*     */   private int type;
/*     */ 
/*     */   public DataSelector()
/*     */   {
/*  94 */     setLayout(this.gridBagLayout1);
/*     */ 
/*  96 */     this.labelSelectCommand = new JButton(Util.getIcon("/jatools/icons/select.gif"));
/*  97 */     this.labelUnselectCommand = new JButton(Util.getIcon("/jatools/icons/unselect.gif"));
/*     */ 
/*  99 */     this.dataSelectCommand = new JButton(Util.getIcon("/jatools/icons/select.gif"));
/* 100 */     this.dataUnselectCommand = new JButton(Util.getIcon("/jatools/icons/unselect.gif"));
/* 101 */     this.dataUnselectAllCommand = new JButton(Util.getIcon("/jatools/icons/unselectall.gif"));
/*     */ 
/* 103 */     this.downCommand = new JButton(Util.getIcon("/jatools/icons/movedown.gif"));
/* 104 */     this.upCommand = new JButton(Util.getIcon("/jatools/icons/moveup.gif"));
/*     */ 
/* 106 */     this.rightPanel.setLayout(new GridLayout(2, 1));
/* 107 */     this.dataPanel.setLayout(this.borderLayout1);
/* 108 */     this.labelPanel.setLayout(this.borderLayout2);
/*     */ 
/* 110 */     this.dataPanel.add(this.dataCommandPanel, "West");
/* 111 */     this.rightPanel.add(this.labelPanel);
/* 112 */     this.rightPanel.add(this.dataPanel);
/*     */ 
/* 114 */     GridBagConstraints gbc2 = new GridBagConstraints();
/* 115 */     gbc2.fill = 1;
/* 116 */     gbc2.weighty = 1.0D;
/* 117 */     gbc2.weightx = 1.0D;
/* 118 */     add(this.leftPanel, gbc2);
/* 119 */     this.leftPanel.setBorder(BorderFactory.createEmptyBorder(14, 0, 10, 0));
/* 120 */     gbc2.gridwidth = 0;
/*     */ 
/* 122 */     add(this.rightPanel, gbc2);
/* 123 */     gbc2.weighty = 0.0D;
/* 124 */     this.promptLabel = new JLabel("提示:");
/*     */ 
/* 126 */     this.promptLabel.setMinimumSize(new Dimension(20, 60));
/* 127 */     add(this.promptLabel, gbc2);
/* 128 */     add(Box.createVerticalStrut(60));
/* 129 */     SwingUtil.setBorder6(this);
/*     */ 
/* 132 */     this.variableExplorer = new DataTree(null);
/* 133 */     this.variableExplorer.addChangeListener(this);
/*     */ 
/* 135 */     this.leftPanel.setLayout(new BorderLayout());
/*     */ 
/* 137 */     this.leftPanel.setPreferredSize(new Dimension(132, 183));
/*     */ 
/* 139 */     this.leftPanel.add(new JLabel("可用字段"), 
/* 140 */       "North");
/*     */ 
/* 142 */     this.leftPanel.add(new JScrollPane(this.variableExplorer), "Center");
/*     */ 
/* 144 */     JButton moreReaderCommand = new JButton("选择数据集...");
/*     */ 
/* 146 */     moreReaderCommand.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 148 */         DataSelector.this.selectReader();
/*     */       }
/*     */     });
/* 152 */     this.leftPanel.add(moreReaderCommand, "South");
/*     */ 
/* 154 */     this.labelTable = new CustomTable(new String[] { "名称" });
/*     */ 
/* 157 */     JPanel labelPanel1 = new JPanel(new BorderLayout());
/*     */ 
/* 159 */     labelPanel1.add(new JLabel("X轴标签数据"), 
/* 160 */       "North");
/* 161 */     labelPanel1.add(new JScrollPane(this.labelTable), "Center");
/* 162 */     labelPanel1.setBorder(BorderFactory.createEmptyBorder(14, 0, 10, 0));
/* 163 */     this.labelSelectCommand = new JButton(Util.getIcon("/jatools/icons/select.gif"));
/*     */ 
/* 165 */     GridBagConstraints gbc = new GridBagConstraints();
/* 166 */     gbc.gridwidth = 0;
/* 167 */     gbc.insets = new Insets(5, 0, 0, 0);
/*     */ 
/* 169 */     this.labelCommandPanel.add(this.labelSelectCommand, gbc);
/* 170 */     this.labelUnselectCommand = new JButton(Util.getIcon("/jatools/icons/unselect.gif"));
/* 171 */     this.labelCommandPanel.add(this.labelUnselectCommand, gbc);
/*     */ 
/* 173 */     this.labelPanel.add(this.labelCommandPanel, "West");
/* 174 */     this.labelCommandPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 15));
/*     */ 
/* 176 */     this.labelPanel.add(labelPanel1, "Center");
/*     */ 
/* 178 */     this.dataTable = new CustomTable(new String[] { "名称", 
/* 179 */       "热点标签" });
/*     */ 
/* 182 */     this.dataTable.getColumn("名称").setMinWidth(65);
/*     */ 
/* 185 */     JPanel dataPanel1 = new JPanel(new GridBagLayout());
/*     */ 
/* 187 */     gbc = new GridBagConstraints();
/*     */ 
/* 189 */     gbc.anchor = 17;
/* 190 */     gbc.weightx = 1.0D;
/*     */ 
/* 193 */     dataPanel1.add(new JLabel("显示数据"), gbc);
/*     */ 
/* 195 */     gbc.weightx = 0.0D;
/* 196 */     dataPanel1.add(this.upCommand, gbc);
/* 197 */     gbc.gridwidth = 0;
/* 198 */     dataPanel1.add(this.downCommand, gbc);
/* 199 */     gbc.weighty = 1.0D;
/*     */ 
/* 201 */     gbc.fill = 1;
/*     */ 
/* 203 */     dataPanel1.add(new JScrollPane(this.dataTable), gbc);
/* 204 */     gbc = new GridBagConstraints();
/* 205 */     gbc.gridwidth = 0;
/* 206 */     gbc.insets = new Insets(2, 0, 2, 0);
/* 207 */     this.dataCommandPanel.add(this.dataSelectCommand, gbc);
/* 208 */     this.dataCommandPanel.add(this.dataUnselectCommand, gbc);
/* 209 */     this.dataCommandPanel.add(this.dataUnselectAllCommand, gbc);
/* 210 */     this.dataCommandPanel.setBorder(this.labelCommandPanel.getBorder());
/*     */ 
/* 212 */     this.dataPanel.add(this.dataCommandPanel, "West");
/* 213 */     this.dataPanel.add(dataPanel1, "Center");
/*     */ 
/* 215 */     this.labelPanel.add(labelPanel1, "Center");
/* 216 */     this.dataPanel.add(dataPanel1, "Center");
/*     */ 
/* 218 */     this.dataPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
/*     */ 
/* 220 */     this.labelSelectCommand.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 222 */         String data = null;
/*     */ 
/* 224 */         if (DataSelector.this.hitField != null) {
/* 225 */           SelectCommand cmd = new SelectCommand(DataSelector.this.hitField, 0);
/* 226 */           DataSelector.this.fireChangeListener(cmd);
/*     */ 
/* 228 */           if (cmd.error != null) {
/* 229 */             MessageBox.error(DataSelector.this, cmd.error);
/*     */ 
/* 231 */             return;
/*     */           }
/*     */ 
/* 234 */           data = DataSelector.this.hitField.getName();
/*     */         } else {
/* 236 */           data = "标签列";
/*     */         }
/*     */ 
/* 239 */         DataSelector.this.labelTable.removeAllRows();
/*     */ 
/* 241 */         DataSelector.this.labelTable.addRow(new Object[] { data }, true);
/* 242 */         DataSelector.this.treeNextRow();
/* 243 */         DataSelector.this.fireChangeListener();
/*     */       }
/*     */     });
/* 247 */     this.dataSelectCommand.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 249 */         String data = null;
/*     */ 
/* 251 */         if (DataSelector.this.hitField != null) {
/* 252 */           SelectCommand cmd = new SelectCommand(DataSelector.this.hitField, 1);
/* 253 */           DataSelector.this.fireChangeListener(cmd);
/*     */ 
/* 255 */           if (cmd.error != null) {
/* 256 */             MessageBox.error(DataSelector.this, cmd.error);
/*     */ 
/* 258 */             return;
/*     */           }
/*     */ 
/* 261 */           data = DataSelector.this.hitField.getName();
/*     */         } else {
/* 263 */           data = "显示列";
/*     */         }
/*     */ 
/* 266 */         DataSelector.this.dataTable.addRow(new Object[] { data }, true);
/* 267 */         DataSelector.this.treeNextRow();
/* 268 */         DataSelector.this.fireChangeListener();
/*     */       }
/*     */     });
/* 272 */     this.labelUnselectCommand.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 274 */         int row = DataSelector.this.labelTable.getSelectedRow();
/* 275 */         DataSelector.this.labelTable.removeRow(row, true);
/* 276 */         DataSelector.this.fireChangeListener();
/*     */       }
/*     */     });
/* 280 */     this.dataUnselectCommand.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 282 */         int row = DataSelector.this.dataTable.getSelectedRow();
/* 283 */         DataSelector.this.dataTable.removeRow(row, true);
/* 284 */         DataSelector.this.fireChangeListener();
/*     */       }
/*     */     });
/* 288 */     this.dataUnselectAllCommand.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 290 */         DataSelector.this.dataTable.removeAllRows();
/* 291 */         DataSelector.this.fireChangeListener();
/*     */       }
/*     */     });
/* 294 */     this.upCommand.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 296 */         DataSelector.this.dataTable.upRow();
/* 297 */         DataSelector.this.fireChangeListener();
/*     */       }
/*     */     });
/* 301 */     this.downCommand.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 303 */         DataSelector.this.dataTable.downRow();
/* 304 */         DataSelector.this.fireChangeListener();
/*     */       }
/*     */     });
/* 308 */     this.labelTable.getSelectionModel().addListSelectionListener(this);
/*     */ 
/* 310 */     this.labelTable.getModel().addTableModelListener(this);
/*     */ 
/* 312 */     this.labelTable.setEditable(0, true);
/*     */ 
/* 314 */     this.dataTable.getModel().addTableModelListener(this);
/* 315 */     this.dataTable.getSelectionModel().addListSelectionListener(this);
/* 316 */     this.dataTable.setEditable(0, true);
/*     */ 
/* 318 */     TableColumn col = this.dataTable.getColumn("热点标签");
/* 319 */     ButtonCellEditor tooltip = new ButtonCellEditor("...");
/*     */ 
/* 321 */     col.setCellEditor(new ButtonCellEditor("..."));
/* 322 */     col.setCellRenderer(tooltip);
/*     */ 
/* 324 */     this.dataTable.setRowHeight(20);
/* 325 */     this.labelTable.setRowHeight(20);
/*     */ 
/* 328 */     this.labelUnselectCommand.setEnabled(false);
/*     */ 
/* 330 */     this.dataUnselectCommand.setEnabled(false);
/* 331 */     this.dataUnselectAllCommand.setEnabled(false);
/*     */ 
/* 333 */     Dimension size = new Dimension(50, 25);
/* 334 */     this.labelSelectCommand.setPreferredSize(size);
/* 335 */     this.labelUnselectCommand.setPreferredSize(size);
/* 336 */     this.dataSelectCommand.setPreferredSize(size);
/* 337 */     this.dataUnselectCommand.setPreferredSize(size);
/* 338 */     this.dataUnselectAllCommand.setPreferredSize(size);
/*     */ 
/* 340 */     this.upCommand.setEnabled(false);
/* 341 */     this.downCommand.setEnabled(false);
/*     */ 
/* 344 */     this.downCommand.setMargin(new Insets(2, 2, 2, 2));
/* 345 */     this.upCommand.setMargin(new Insets(2, 2, 2, 2));
/* 346 */     this.dataTable.setEditable(1, true);
/*     */   }
/*     */ 
/*     */   protected void fireChangeListener()
/*     */   {
/* 354 */     fireChangeListener(this);
/*     */   }
/*     */ 
/*     */   public void setPrompt(String prompt)
/*     */   {
/* 363 */     this.promptLabel.setText("<html>提示:<br>" + prompt + "</html>");
/*     */   }
/*     */ 
/*     */   protected void selectReader()
/*     */   {
/* 370 */     ReaderChooser chooser = new ReaderChooser(Util.getCDF(this), App.getConfiguration());
/*     */ 
/* 372 */     chooser.show();
/*     */ 
/* 376 */     if (!chooser.isCancel()) {
/* 377 */       DatasetReader r = chooser.getReader();
/* 378 */       setReader(r);
/*     */     }
/*     */ 
/* 381 */     fireChangeListener();
/*     */   }
/*     */ 
/*     */   void treeNextRow() {
/* 385 */     if (this.variableExplorer.getSelectionRows() != null) {
/* 386 */       int index = this.variableExplorer.getSelectionRows()[0];
/*     */ 
/* 388 */       if (index < this.variableExplorer.getRowCount() - 1)
/* 389 */         this.variableExplorer.setSelectionRow(this.variableExplorer.getSelectionRows()[0] + 1);
/* 390 */       else if (index == this.variableExplorer.getRowCount() - 1)
/* 391 */         refreshSelectedNode();
/*     */     }
/*     */   }
/*     */ 
/*     */   public ArrayList getSelectedRows()
/*     */   {
/* 402 */     return this.labelTable.cloneRows();
/*     */   }
/*     */ 
/*     */   public void stateChanged(ChangeEvent e)
/*     */   {
/* 412 */     this.hitField = null;
/*     */ 
/* 414 */     if (e.getSource() == this.variableExplorer)
/*     */     {
/* 416 */       TreePath path = this.variableExplorer.getSelectionPath();
/*     */ 
/* 418 */       if (path != null) {
/* 419 */         SimpleTreeNode node = (SimpleTreeNode)path.getLastPathComponent();
/* 420 */         boolean hitReader = ((node.getUserObject() instanceof Column)) && (!node.isDisabled());
/*     */ 
/* 422 */         if (hitReader)
/* 423 */           this.hitField = 
/* 424 */             ((Column)((SimpleTreeNode)this.variableExplorer.getSelectionPath()
/* 424 */             .getLastPathComponent()).getUserObject());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addChangeListener(ChangeListener lst)
/*     */   {
/* 440 */     if (this.changeListeners == null) {
/* 441 */       this.changeListeners = new ArrayList();
/*     */     }
/*     */ 
/* 444 */     this.changeListeners.add(lst);
/*     */   }
/*     */ 
/*     */   void fireChangeListener(Object src) {
/* 448 */     if (this.changeListeners != null)
/* 449 */       for (Iterator it = this.changeListeners.iterator(); it.hasNext(); ) {
/* 450 */         ChangeListener lst = (TableModelListener)it.next();
/* 451 */         lst.stateChanged(new ChangeEvent(src));
/*     */       }
/*     */   }
/*     */ 
/*     */   public void setReader(DatasetReader reader)
/*     */   {
/* 462 */     this.variableExplorer.setRoot(DataTreeUtil.asTree(reader));
/* 463 */     this.labelTable.removeAllRows();
/* 464 */     this.dataTable.removeAllRows();
/*     */ 
/* 466 */     if (this.variableExplorer.getRowCount() > 0) {
/* 467 */       this.variableExplorer.expandRow(0);
/*     */     }
/*     */ 
/* 470 */     this.reader = reader;
/*     */   }
/*     */ 
/*     */   public static void showGUI()
/*     */   {
/*     */     try
/*     */     {
/* 480 */       JFrame frame = new JFrame();
/* 481 */       DataSelector inst = new DataSelector();
/* 482 */       frame.setContentPane(inst);
/* 483 */       frame.pack();
/* 484 */       frame.setVisible(true);
/*     */     } catch (Exception e) {
/* 486 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void valueChanged(ListSelectionEvent e)
/*     */   {
/* 502 */     this.upCommand.setEnabled(this.dataTable.canUp());
/* 503 */     this.downCommand.setEnabled(this.dataTable.canDown());
/*     */   }
/*     */ 
/*     */   public void tableChanged(TableModelEvent e)
/*     */   {
/* 518 */     this.labelUnselectCommand.setEnabled(this.labelTable.getRowCount() > 0);
/*     */ 
/* 520 */     this.dataUnselectCommand.setEnabled(this.dataTable.getRowCount() > 0);
/* 521 */     this.dataUnselectAllCommand.setEnabled(this.dataUnselectCommand.isEnabled());
/*     */ 
/* 523 */     refreshSelectedNode();
/* 524 */     this.variableExplorer.repaint();
/* 525 */     fireChangeListener();
/*     */   }
/*     */ 
/*     */   void refreshSelectedNode() {
/* 529 */     int[] selectedRows = this.variableExplorer.getSelectionRows();
/*     */ 
/* 531 */     this.variableExplorer.setSelectionRow(0);
/* 532 */     this.variableExplorer.setSelectionRows(selectedRows);
/*     */   }
/*     */ 
/*     */   public void init(Chart chart)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void init2(DatasetReader reader, String xlabel, PlotData[] showData)
/*     */   {
/* 576 */     setReader(reader);
/*     */ 
/* 579 */     if (xlabel != null) {
/* 580 */       this.labelTable.addRow(new Object[] { xlabel }, true);
/*     */     }
/*     */ 
/* 583 */     if ((showData != null) && (showData.length > 0))
/*     */     {
/* 585 */       for (int i = 0; i < showData.length; i++) {
/* 586 */         this.dataTable.addRow(new Object[] { showData[i].getField(), showData[i].getTooltip() }, 
/* 587 */           false);
/*     */       }
/*     */ 
/* 590 */       this.dataTable.setSelectedRow(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PlotData[] getShowData()
/*     */   {
/* 602 */     PlotData[] data = new PlotData[this.dataTable.getRowCount()];
/*     */ 
/* 604 */     for (int i = 0; i < this.dataTable.getRowCount(); i++) {
/* 605 */       data[i] = new PlotData((String)this.dataTable.getValueAt(i, 0), 
/* 606 */         (Tip)this.dataTable.getValueAt(i, 1));
/*     */     }
/*     */ 
/* 609 */     return data;
/*     */   }
/*     */ 
/*     */   public String getLabelField()
/*     */   {
/* 618 */     return this.labelTable.getRowCount() > 0 ? (String)this.labelTable.getValueAt(0, 0) : null;
/*     */   }
/*     */ 
/*     */   public DatasetReader getReader()
/*     */   {
/* 626 */     return this.reader;
/*     */   }
/*     */ 
/*     */   public void setType(int type)
/*     */   {
/* 635 */     this.type = type;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.DataSelector
 * JD-Core Version:    0.6.2
 */