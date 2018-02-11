/*     */ package jatools.component.chart.component;
/*     */ 
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
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ 
/*     */ public class ZFontChooser extends JPanel
/*     */   implements ChangeListener, ItemListener, ActionListener
/*     */ {
/*     */   private static ZFontChooser defaultChooser;
/*     */   private static final String SAMPLE_TEXT = "Hello !";
/*     */   private ZListEditor faceSelector;
/*     */   private ZListEditor styleSelector;
/*     */   private ZListEditor sizeSelector;
/*  59 */   private String sampleText = "Hello !";
/*  60 */   private _SampleLabel sampleLabel = new _SampleLabel(this.sampleText);
/*     */ 
/*  67 */   Object[] styles = { "常规", "斜体", "粗体", "斜体 粗体" };
/*     */ 
/*  69 */   boolean exitOK = false;
/*     */   JDialog shareInstance;
/*     */   JCheckBox checkbox;
/*     */   JButton backgroundButton;
/*     */   JButton foregroundButton;
/*     */   private ZColorIcon backIcon;
/*     */   private ZColorIcon foreIcon;
/*     */ 
/*     */   public ZFontChooser()
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
/* 112 */     this.backIcon.setColor(f == null ? Color.blue : f.getBackColor());
/* 113 */     this.checkbox.setSelected(this.backIcon.getColor() == null);
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
/* 136 */       this.backIcon.getColor());
/*     */   }
/*     */ 
/*     */   public static ZFontChooser getDefault()
/*     */   {
/* 148 */     if (defaultChooser == null) {
/* 149 */       defaultChooser = new ZFontChooser();
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
/* 167 */         ZFontChooser.this.exitOK = true;
/* 168 */         ZFontChooser.this.shareInstance.hide();
/*     */       }
/*     */     });
/* 171 */     cancelButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 173 */         ZFontChooser.this.shareInstance.hide();
/*     */       }
/*     */     });
/* 176 */     this.shareInstance = new JDialog(null, "字体选择");
/* 177 */     this.shareInstance.setModal(true);
/*     */ 
/* 179 */     JPanel p = new JPanel(new FlowLayout(2));
/* 180 */     p.add(okButton);
/* 181 */     p.add(cancelButton);
/*     */ 
/* 183 */     p.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 40));
/* 184 */     this.shareInstance.getContentPane().add(this, "Center");
/* 185 */     this.shareInstance.getContentPane().add(p, "South");
/* 186 */     this.shareInstance.pack();
/* 187 */     this.shareInstance.setLocationRelativeTo(owner);
/* 188 */     this.shareInstance.show();
/*     */ 
/* 190 */     return this.exitOK;
/*     */   }
/*     */ 
/*     */   private void buildDialog()
/*     */   {
/* 200 */     Object[] faces = GraphicsEnvironment.getLocalGraphicsEnvironment()
/* 201 */       .getAvailableFontFamilyNames();
/* 202 */     this.faceSelector = new ZListEditor(faces, "字体", false);
/*     */ 
/* 204 */     this.faceSelector.setPreferredSize(new Dimension(120, 10));
/*     */ 
/* 206 */     this.styleSelector = new ZListEditor(this.styles, "风格", false);
/* 207 */     this.styleSelector.setPreferredSize(new Dimension(90, 10));
/*     */ 
/* 209 */     Object[] sizes = { 
/* 210 */       "6", "8", "10", "12", "14", "16", "18", "20", "22", "24", "26", "32", "48", "72" };
/* 211 */     this.sizeSelector = new ZListEditor(sizes, "字号", true);
/* 212 */     this.sizeSelector.setPreferredSize(new Dimension(60, 10));
/* 213 */     setLayout(new GridBagLayout());
/*     */ 
/* 215 */     GridBagConstraints gbc = new GridBagConstraints();
/* 216 */     gbc.gridwidth = 1;
/* 217 */     gbc.fill = 1;
/* 218 */     gbc.weighty = 1.0D;
/* 219 */     add(this.faceSelector, gbc);
/* 220 */     add(Box.createHorizontalStrut(5), gbc);
/* 221 */     add(this.styleSelector, gbc);
/* 222 */     add(Box.createHorizontalStrut(5), gbc);
/* 223 */     gbc.gridwidth = 0;
/* 224 */     add(this.sizeSelector, gbc);
/*     */ 
/* 228 */     gbc.fill = 1;
/* 229 */     gbc.weighty = 0.0D;
/* 230 */     gbc.insets = new Insets(5, 0, 2, 0);
/* 231 */     add(buildGroundPanel(), gbc);
/* 232 */     gbc.insets = new Insets(0, 0, 0, 0);
/* 233 */     add(Box.createVerticalStrut(5), gbc);
/*     */ 
/* 235 */     this.checkbox.addItemListener(this);
/* 236 */     this.foregroundButton.addActionListener(this);
/* 237 */     this.backgroundButton.addActionListener(this);
/*     */ 
/* 239 */     gbc.fill = 1;
/* 240 */     gbc.weighty = 0.0D;
/* 241 */     add(Box.createVerticalStrut(5), gbc);
/*     */ 
/* 243 */     add(this.sampleLabel, gbc);
/*     */ 
/* 245 */     this.sampleLabel.setBorder(BorderFactory.createEtchedBorder());
/* 246 */     this.sampleLabel.setPreferredSize(new Dimension(170, 70));
/* 247 */     this.faceSelector.addChangeListener(this);
/* 248 */     this.styleSelector.addChangeListener(this);
/* 249 */     this.sizeSelector.addChangeListener(this);
/*     */ 
/* 251 */     SwingUtil.setBorder6(this);
/*     */ 
/* 253 */     setPreferredSize(new Dimension(350, 350));
/*     */   }
/*     */ 
/*     */   public void itemStateChanged(ItemEvent itemEvent)
/*     */   {
/* 261 */     this.backgroundButton
/* 262 */       .setEnabled(itemEvent.getStateChange() != 1);
/* 263 */     Color backColor = this.backgroundButton.isEnabled() ? Color.WHITE : null;
/* 264 */     this.backIcon.setColor(backColor);
/*     */ 
/* 266 */     this.sampleLabel.repaint();
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 274 */     Color color = JColorChooser.showDialog(this, "颜色选择", Color.black);
/*     */ 
/* 276 */     if (color != null) {
/* 277 */       if (e.getSource() == this.foregroundButton)
/* 278 */         this.foreIcon.setColor(color);
/*     */       else {
/* 280 */         this.backIcon.setColor(color);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 285 */     this.sampleLabel.repaint();
/*     */   }
/*     */ 
/*     */   private JPanel buildGroundPanel()
/*     */   {
/* 293 */     JPanel panel = new JPanel();
/* 294 */     GridBagLayout gridbag = new GridBagLayout();
/* 295 */     panel.setLayout(gridbag);
/*     */ 
/* 309 */     panel.setLayout(gridbag);
/*     */ 
/* 311 */     panel.setBorder(BorderFactory.createTitledBorder("颜色"));
/* 312 */     this.checkbox = new JCheckBox("透明背景", true);
/* 313 */     this.backIcon = new ZColorIcon(16, 16);
/* 314 */     this.foreIcon = new ZColorIcon(16, 16);
/*     */ 
/* 316 */     this.foregroundButton = new JButton(
/* 317 */       "前景", this.foreIcon);
/* 318 */     this.backgroundButton = new JButton(
/* 319 */       "背景", this.backIcon);
/* 320 */     int gridx = 0;
/* 321 */     int gridy = 0;
/* 322 */     int gridwidth = 1;
/* 323 */     int gridheight = 1;
/* 324 */     double weightx = 1.0D;
/* 325 */     double weighty = 1.0D;
/* 326 */     int anchor = 10;
/* 327 */     int fill = 2;
/* 328 */     Insets inset = new Insets(0, 0, 0, 0);
/* 329 */     int ipadx = 0;
/* 330 */     int ipady = 0;
/* 331 */     GridBagConstraints c = new GridBagConstraints(
/* 332 */       gridx, 
/* 333 */       gridy, 
/* 334 */       gridwidth, 
/* 335 */       gridheight, 
/* 336 */       weightx, 
/* 337 */       weighty, 
/* 338 */       anchor, 
/* 339 */       fill, 
/* 340 */       inset, 
/* 341 */       ipadx, 
/* 342 */       ipady);
/* 343 */     gridbag.setConstraints(this.checkbox, c);
/* 344 */     panel.add(this.checkbox);
/*     */ 
/* 346 */     gridx = 0;
/* 347 */     gridy = 1;
/* 348 */     gridwidth = 1;
/* 349 */     gridheight = 1;
/* 350 */     weightx = 1.0D;
/* 351 */     weighty = 1.0D;
/* 352 */     anchor = 10;
/* 353 */     fill = 2;
/* 354 */     inset = new Insets(3, 5, 3, 5);
/* 355 */     ipadx = 0;
/* 356 */     ipady = 0;
/* 357 */     c = new GridBagConstraints(
/* 358 */       gridx, 
/* 359 */       gridy, 
/* 360 */       gridwidth, 
/* 361 */       gridheight, 
/* 362 */       weightx, 
/* 363 */       weighty, 
/* 364 */       anchor, 
/* 365 */       fill, 
/* 366 */       inset, 
/* 367 */       ipadx, 
/* 368 */       ipady);
/* 369 */     gridbag.setConstraints(this.foregroundButton, c);
/* 370 */     panel.add(this.foregroundButton);
/*     */ 
/* 372 */     gridx = 1;
/* 373 */     gridy = 1;
/* 374 */     gridwidth = 1;
/* 375 */     gridheight = 1;
/* 376 */     weightx = 1.0D;
/* 377 */     weighty = 1.0D;
/* 378 */     anchor = 10;
/* 379 */     fill = 2;
/* 380 */     inset = new Insets(3, 5, 3, 5);
/* 381 */     ipadx = 0;
/* 382 */     ipady = 0;
/* 383 */     c = new GridBagConstraints(
/* 384 */       gridx, 
/* 385 */       gridy, 
/* 386 */       gridwidth, 
/* 387 */       gridheight, 
/* 388 */       weightx, 
/* 389 */       weighty, 
/* 390 */       anchor, 
/* 391 */       fill, 
/* 392 */       inset, 
/* 393 */       ipadx, 
/* 394 */       ipady);
/* 395 */     gridbag.setConstraints(this.backgroundButton, c);
/* 396 */     panel.add(this.backgroundButton);
/*     */ 
/* 398 */     return panel;
/*     */   }
/*     */ 
/*     */   class ColorIcon extends ZEmptyIcon
/*     */   {
/* 430 */     Color color = Color.red;
/*     */ 
/*     */     public ColorIcon(int width, int height)
/*     */     {
/* 439 */       super(height);
/*     */     }
/*     */ 
/*     */     public void setColor(Color color)
/*     */     {
/* 448 */       this.color = color;
/*     */     }
/*     */ 
/*     */     public void paintIcon(Component c, Graphics g, int x, int y)
/*     */     {
/* 460 */       g.setColor(this.color);
/* 461 */       g.fillRect(x, y, getIconWidth(), getIconHeight());
/* 462 */       g.setColor(Color.black);
/* 463 */       g.drawRect(x, y, getIconWidth(), getIconHeight());
/*     */     }
/*     */   }
/*     */ 
/*     */   class _SampleLabel extends JLabel
/*     */   {
/* 403 */     String title = new String();
/*     */ 
/*     */     public _SampleLabel(String title) {
/* 406 */       this.title = title;
/*     */     }
/*     */ 
/*     */     public void paintComponent(Graphics g) {
/* 410 */       super.paintComponent(g);
/*     */ 
/* 412 */       ChartFont font = (ChartFont)ZFontChooser.this.getValue();
/*     */ 
/* 414 */       FontMetrics fm = g.getFontMetrics(font.asFont());
/* 415 */       int textWidth = fm.stringWidth(ZFontChooser.this.sampleText);
/* 416 */       int textHeight = fm.getHeight();
/*     */ 
/* 418 */       int width = getWidth();
/* 419 */       int height = getHeight();
/*     */ 
/* 421 */       int x = (width - textWidth) / 2;
/* 422 */       int y = (height - textHeight) / 2;
/* 423 */       g.translate(x, y);
/* 424 */       font.paint(g, ZFontChooser.this.sampleText, 0, 0);
/* 425 */       g.translate(-x, -y);
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ZFontChooser
 * JD-Core Version:    0.6.2
 */