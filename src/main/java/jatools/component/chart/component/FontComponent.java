/*     */ package jatools.component.chart.component;
/*     */ 
/*     */ import jatools.designer.Main;
/*     */ import jatools.swingx.SwingUtil;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ 
/*     */ public class FontComponent extends JPanel
/*     */   implements ChangeListener, ItemListener, ActionListener
/*     */ {
/*     */   private static FontComponent defaultChooser;
/*     */   private static final String SAMPLE_TEXT = "Hello !";
/*     */   private ZListEditor faceSelector;
/*     */   private ZListEditor styleSelector;
/*     */   private ZListEditor sizeSelector;
/*  59 */   private String sampleText = "Hello !";
/*  60 */   private _SampleLabel sampleLabel = new _SampleLabel(this.sampleText);
/*     */ 
/*  67 */   Object[] styles = { "常规", "粗体", "斜体", "斜体 粗体" };
/*     */ 
/*  69 */   boolean exitOK = false;
/*     */   JDialog shareInstance;
/*     */   JButton foregroundButton;
/*     */   private ZColorIcon foreIcon;
/*     */ 
/*     */   public FontComponent()
/*     */   {
/*  81 */     buildDialog();
/*     */   }
/*     */ 
/*     */   public void stateChanged(ChangeEvent changeEvent)
/*     */   {
/*  89 */     this.sampleLabel.revalidate();
/*  90 */     this.sampleLabel.repaint();
/*     */   }
/*     */ 
/*     */   public void setValue(Object value)
/*     */   {
/* 100 */     ChartFont f = (ChartFont)value;
/* 101 */     String face = f == null ? 
/* 102 */       "Dialog" : f.getFace();
/* 103 */     int style = f == null ? 0 : f.getStyle();
/* 104 */     int size = f == null ? 12 : f.getSize();
/*     */ 
/* 106 */     this.faceSelector.setSelectedValue(face);
/* 107 */     this.styleSelector.setSelectedValue(this.styles[style]);
/* 108 */     this.sizeSelector.setSelectedValue(size);
/*     */ 
/* 111 */     this.foreIcon.setColor(f == null ? Color.white : f.getForeColor());
/*     */   }
/*     */ 
/*     */   public Object getValue()
/*     */   {
/* 123 */     int size = 16;
/*     */     try
/*     */     {
/* 126 */       size = Integer.parseInt((String)this.sizeSelector.getSelectedValue());
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException)
/*     */     {
/*     */     }
/* 131 */     return new ChartFont(
/* 132 */       (String)this.faceSelector.getSelectedValue(), 
/* 133 */       this.styleSelector.getSelectedIndex(), 
/* 134 */       size, 
/* 135 */       this.foreIcon.getColor(), 
/* 136 */       null);
/*     */   }
/*     */ 
/*     */   public static FontComponent getDefault()
/*     */   {
/* 148 */     if (defaultChooser == null) {
/* 149 */       defaultChooser = new FontComponent();
/*     */     }
/* 151 */     return defaultChooser;
/*     */   }
/*     */ 
/*     */   public boolean showChooser(JComponent owner)
/*     */   {
/* 160 */     this.exitOK = false;
/*     */ 
/* 162 */     JButton okButton = new JButton("确定");
/* 163 */     JButton cancelButton = new JButton("取消");
/*     */ 
/* 165 */     okButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 167 */         FontComponent.this.exitOK = true;
/* 168 */         FontComponent.this.shareInstance.hide();
/*     */       }
/*     */     });
/* 171 */     cancelButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 173 */         FontComponent.this.shareInstance.hide();
/*     */       }
/*     */     });
/* 176 */     this.shareInstance = new JDialog(Main.getInstance(), "字体选择");
/* 177 */     this.shareInstance.setModal(true);
/*     */ 
/* 179 */     JPanel p = new JPanel(new FlowLayout(2));
/* 180 */     p.add(okButton);
/* 181 */     p.add(cancelButton);
/*     */ 
/* 184 */     SwingUtil.setBorder6(p);
/* 185 */     this.shareInstance.getContentPane().add(this, "Center");
/* 186 */     this.shareInstance.getContentPane().add(p, "South");
/* 187 */     this.shareInstance.setSize(new Dimension(300, 380));
/* 188 */     this.shareInstance.setLocationRelativeTo(owner);
/* 189 */     this.shareInstance.show();
/*     */ 
/* 191 */     return this.exitOK;
/*     */   }
/*     */ 
/*     */   private void buildDialog()
/*     */   {
/* 201 */     Object[] faces = GraphicsEnvironment.getLocalGraphicsEnvironment()
/* 202 */       .getAvailableFontFamilyNames();
/* 203 */     this.faceSelector = new ZListEditor(faces, "字体", false);
/*     */ 
/* 205 */     this.faceSelector.setPreferredSize(new Dimension(120, 10));
/*     */ 
/* 207 */     this.styleSelector = new ZListEditor(this.styles, "风格", false);
/* 208 */     this.styleSelector.setPreferredSize(new Dimension(90, 10));
/*     */ 
/* 210 */     Object[] sizes = { 
/* 211 */       "6", "8", "10", "12", "14", "16", "18", "20", "22", "24", "26", "32", "48", "72" };
/* 212 */     this.sizeSelector = new ZListEditor(sizes, "字号", true);
/* 213 */     this.sizeSelector.setPreferredSize(new Dimension(60, 10));
/* 214 */     setLayout(new GridBagLayout());
/*     */ 
/* 216 */     GridBagConstraints gbc = new GridBagConstraints();
/* 217 */     gbc.gridwidth = 1;
/* 218 */     gbc.fill = 1;
/* 219 */     gbc.weighty = 1.0D;
/* 220 */     add(this.faceSelector, gbc);
/* 221 */     add(Box.createHorizontalStrut(5), gbc);
/* 222 */     add(this.styleSelector, gbc);
/* 223 */     add(Box.createHorizontalStrut(5), gbc);
/* 224 */     gbc.gridwidth = 0;
/* 225 */     add(this.sizeSelector, gbc);
/*     */ 
/* 229 */     gbc.fill = 1;
/* 230 */     gbc.weighty = 0.0D;
/* 231 */     gbc.insets = new Insets(5, 0, 2, 0);
/* 232 */     add(buildGroundPanel(), gbc);
/* 233 */     gbc.insets = new Insets(0, 0, 0, 0);
/* 234 */     add(Box.createVerticalStrut(5), gbc);
/*     */ 
/* 237 */     this.foregroundButton.addActionListener(this);
/*     */ 
/* 240 */     gbc.fill = 1;
/* 241 */     gbc.weighty = 0.0D;
/* 242 */     add(Box.createVerticalStrut(5), gbc);
/*     */ 
/* 244 */     add(this.sampleLabel, gbc);
/*     */ 
/* 246 */     this.sampleLabel.setBorder(BorderFactory.createEtchedBorder());
/* 247 */     this.sampleLabel.setPreferredSize(new Dimension(170, 70));
/* 248 */     this.faceSelector.addChangeListener(this);
/* 249 */     this.styleSelector.addChangeListener(this);
/* 250 */     this.sizeSelector.addChangeListener(this);
/*     */   }
/*     */ 
/*     */   public void itemStateChanged(ItemEvent itemEvent)
/*     */   {
/* 267 */     this.sampleLabel.repaint();
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 275 */     Color color = JColorChooser.showDialog(this, "颜色选择", Color.black);
/*     */ 
/* 277 */     if ((color != null) && 
/* 278 */       (e.getSource() == this.foregroundButton)) {
/* 279 */       this.foreIcon.setColor(color);
/*     */     }
/*     */ 
/* 286 */     this.sampleLabel.repaint();
/*     */   }
/*     */ 
/*     */   private JPanel buildGroundPanel()
/*     */   {
/* 294 */     JPanel panel = new JPanel();
/* 295 */     GridBagLayout gridbag = new GridBagLayout();
/* 296 */     panel.setLayout(gridbag);
/*     */ 
/* 310 */     panel.setLayout(gridbag);
/*     */ 
/* 312 */     panel.setBorder(BorderFactory.createTitledBorder("颜色"));
/*     */ 
/* 315 */     this.foreIcon = new ZColorIcon(16, 16);
/*     */ 
/* 317 */     this.foregroundButton = new JButton(
/* 318 */       "前景", this.foreIcon);
/*     */ 
/* 347 */     int gridx = 0;
/* 348 */     int gridy = 1;
/* 349 */     int gridwidth = 1;
/* 350 */     int gridheight = 1;
/* 351 */     double weightx = 1.0D;
/* 352 */     double weighty = 1.0D;
/* 353 */     int anchor = 10;
/* 354 */     int fill = 2;
/* 355 */     Insets inset = new Insets(3, 5, 3, 5);
/* 356 */     int ipadx = 0;
/* 357 */     int ipady = 0;
/* 358 */     GridBagConstraints c = new GridBagConstraints(
/* 359 */       gridx, 
/* 360 */       gridy, 
/* 361 */       gridwidth, 
/* 362 */       gridheight, 
/* 363 */       weightx, 
/* 364 */       weighty, 
/* 365 */       anchor, 
/* 366 */       fill, 
/* 367 */       inset, 
/* 368 */       ipadx, 
/* 369 */       ipady);
/* 370 */     gridbag.setConstraints(this.foregroundButton, c);
/* 371 */     panel.add(this.foregroundButton);
/*     */ 
/* 399 */     return panel;
/*     */   }
/*     */ 
/*     */   class ColorIcon extends ZEmptyIcon
/*     */   {
/* 431 */     Color color = Color.red;
/*     */ 
/*     */     public ColorIcon(int width, int height)
/*     */     {
/* 440 */       super(height);
/*     */     }
/*     */ 
/*     */     public void setColor(Color color)
/*     */     {
/* 449 */       this.color = color;
/*     */     }
/*     */ 
/*     */     public void paintIcon(Component c, Graphics g, int x, int y)
/*     */     {
/* 461 */       g.setColor(this.color);
/* 462 */       g.fillRect(x, y, getIconWidth(), getIconHeight());
/* 463 */       g.setColor(Color.black);
/* 464 */       g.drawRect(x, y, getIconWidth(), getIconHeight());
/*     */     }
/*     */   }
/*     */ 
/*     */   class _SampleLabel extends JLabel
/*     */   {
/* 404 */     String title = new String();
/*     */ 
/*     */     public _SampleLabel(String title) {
/* 407 */       this.title = title;
/*     */     }
/*     */ 
/*     */     public void paintComponent(Graphics g) {
/* 411 */       super.paintComponent(g);
/*     */ 
/* 413 */       ChartFont font = (ChartFont)FontComponent.this.getValue();
/*     */ 
/* 415 */       FontMetrics fm = g.getFontMetrics(font.asFont());
/* 416 */       int textWidth = fm.stringWidth(FontComponent.this.sampleText);
/* 417 */       int textHeight = fm.getHeight();
/*     */ 
/* 419 */       int width = getWidth();
/* 420 */       int height = getHeight();
/*     */ 
/* 422 */       int x = (width - textWidth) / 2;
/* 423 */       int y = (height - textHeight) / 2;
/* 424 */       g.translate(x, y);
/* 425 */       font.paint(g, FontComponent.this.sampleText, 0, 0);
/* 426 */       g.translate(-x, -y);
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.FontComponent
 * JD-Core Version:    0.6.2
 */