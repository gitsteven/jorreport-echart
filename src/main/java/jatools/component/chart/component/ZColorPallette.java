/*     */ package jatools.component.chart.component;
/*     */ 
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ 
/*     */ public class ZColorPallette extends JLabel
/*     */ {
/*     */   public static final int NULL_BOX_VISIBLE = 1;
/*     */   public static final int OTHER_BOX_VISIBLE = 2;
/*     */   static final int NULL_COLOR_INDEX = 64;
/*     */   static final int OTHER_COLOR_INDEX = 65;
/*     */   static final int COLUMN = 8;
/*     */   static final int ROW = 8;
/*     */   static final String NULL_COLOR_TEXT = "透明";
/*     */   static final String OTHER_COLOR_TEXT = "定制";
/*     */   static final int COLOR_DROP_SIZE = 12;
/*     */   static final int DROP_GAP = 7;
/*     */   static final int TEXT_BOX_HEIGHT = 25;
/*     */   private static Color[] colors8x8;
/*     */   private Color color;
/*  56 */   Rectangle indexColorBox = new Rectangle();
/*  57 */   Rectangle nullColorBox = new Rectangle();
/*  58 */   Rectangle otherColorBox = new Rectangle();
/*     */   private boolean atHeader;
/*     */   private int textBoxVisible;
/*  67 */   private int selectedIndex = -1;
/*     */ 
/*  70 */   private int hittedIndex = -1;
/*     */ 
/*  73 */   ArrayList listeners = new ArrayList();
/*     */   Color backColor;
/*     */ 
/*     */   public ZColorPallette()
/*     */   {
/*  82 */     this(1);
/*     */   }
/*     */ 
/*     */   public ZColorPallette(int textBoxVisible)
/*     */   {
/*  89 */     this.textBoxVisible = textBoxVisible;
/*     */ 
/*  91 */     int w = 159;
/*  92 */     int h = 166;
/*     */ 
/*  94 */     if ((textBoxVisible & 0x1) != 0) {
/*  95 */       this.nullColorBox.width = w;
/*  96 */       this.nullColorBox.height = 25;
/*  97 */       h += 25;
/*     */     }
/*     */ 
/* 100 */     if ((textBoxVisible & 0x2) != 0) {
/* 101 */       this.otherColorBox.width = w;
/* 102 */       this.otherColorBox.height = 25;
/* 103 */       h += 25;
/*     */     }
/*     */ 
/* 106 */     this.indexColorBox.width = w;
/* 107 */     setPreferredSize(new Dimension(w, h));
/* 108 */     setMinimumSize(getPreferredSize());
/*     */ 
/* 110 */     this.backColor = getBackground();
/*     */ 
/* 112 */     listenToMouse();
/*     */   }
/*     */ 
/*     */   public static Color[] get8x8Colors()
/*     */   {
/* 121 */     if (colors8x8 == null) {
/* 122 */       int[] values = { 0, 128, 192, 255 };
/* 123 */       colors8x8 = new Color[64];
/*     */ 
/* 125 */       int index = 0;
/*     */ 
/* 128 */       for (int r = 0; r < values.length; r++) {
/* 129 */         for (int g = 0; g < values.length; g++) {
/* 130 */           for (int b = 0; b < values.length; b++) {
/* 131 */             colors8x8[index] = new Color(values[r], values[g], values[b]);
/* 132 */             index++;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 138 */     return colors8x8;
/*     */   }
/*     */ 
/*     */   public void addChangeListener(ChangeListener lst)
/*     */   {
/* 147 */     this.listeners.add(lst);
/*     */   }
/*     */ 
/*     */   public void removeChangeListener(ChangeListener lst)
/*     */   {
/* 156 */     this.listeners.remove(lst);
/*     */   }
/*     */ 
/*     */   public void fireChangeListener()
/*     */   {
/* 163 */     ChangeEvent evt = new ChangeEvent(this);
/*     */ 
/* 165 */     for (int i = 0; i < this.listeners.size(); i++) {
/* 166 */       ChangeListener lst = (ChangeListener)this.listeners.get(i);
/* 167 */       lst.stateChanged(evt);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAtHeader(boolean atHeader)
/*     */   {
/* 179 */     this.atHeader = atHeader;
/*     */   }
/*     */ 
/*     */   public void setColor(Color color)
/*     */   {
/* 188 */     this.color = color;
/*     */   }
/*     */ 
/*     */   public Color getColor()
/*     */   {
/* 197 */     if ((this.selectedIndex < 64) && (this.selectedIndex >= 0)) {
/* 198 */       Color[] colors = get8x8Colors();
/*     */ 
/* 200 */       return colors[this.selectedIndex];
/* 201 */     }if (this.selectedIndex == 64) {
/* 202 */       this.color = Gc.TRANSPARENT;
/*     */     }
/*     */ 
/* 206 */     return this.color;
/*     */   }
/*     */ 
/*     */   public void mouseExited(MouseEvent e)
/*     */   {
/* 215 */     this.hittedIndex = -1;
/*     */ 
/* 218 */     JComponent comp = (JComponent)e.getSource();
/* 219 */     comp.repaint();
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent e)
/*     */   {
/* 228 */     int i = hitIndex(e.getX(), e.getY());
/*     */ 
/* 230 */     boolean isChanged = false;
/*     */ 
/* 232 */     if (i == 65) {
/* 233 */       Color selectedColor = JColorChooser.showDialog(this, "选取颜色", this.color);
/*     */ 
/* 235 */       if ((selectedColor != null) && (!selectedColor.equals(this.color))) {
/* 236 */         this.color = selectedColor;
/* 237 */         isChanged = true;
/*     */       }
/* 239 */     } else if (i != this.selectedIndex) {
/* 240 */       this.selectedIndex = i;
/* 241 */       this.color = getColor();
/* 242 */       isChanged = true;
/*     */     }
/*     */ 
/* 245 */     if (isChanged) {
/* 246 */       this.selectedIndex = -1;
/*     */ 
/* 248 */       JComponent comp = (JComponent)e.getSource();
/* 249 */       comp.repaint();
/* 250 */       fireChangeListener();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mouseMoved(MouseEvent e)
/*     */   {
/* 260 */     int i = hitIndex(e.getX(), e.getY());
/*     */ 
/* 262 */     if (this.hittedIndex != i) {
/* 263 */       this.hittedIndex = i;
/*     */ 
/* 265 */       JComponent comp = (JComponent)e.getSource();
/* 266 */       comp.repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void listenToMouse()
/*     */   {
/* 278 */     addMouseListener(new MouseAdapter() {
/*     */       public void mousePressed(MouseEvent e) {
/* 280 */         ZColorPallette.this.mousePressed(e);
/*     */       }
/*     */ 
/*     */       public void mouseExited(MouseEvent e) {
/* 284 */         ZColorPallette.this.mouseExited(e);
/*     */       }
/*     */     });
/* 287 */     addMouseMotionListener(new MouseMotionAdapter() {
/*     */       public void mouseMoved(MouseEvent e) {
/* 289 */         ZColorPallette.this.mouseMoved(e);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public int hitIndex(int x, int y)
/*     */   {
/* 310 */     int i = -1;
/*     */ 
/* 312 */     if (this.nullColorBox.contains(x, y)) {
/* 313 */       i = 64;
/* 314 */     } else if (this.otherColorBox.contains(x, y)) {
/* 315 */       i = 65;
/* 316 */     } else if (this.indexColorBox.contains(x, y))
/*     */     {
/* 318 */       int column = (x - 3) / 19;
/* 319 */       column = column >= 8 ? 7 : column;
/*     */ 
/* 321 */       int row = (y - 3) / 19;
/* 322 */       row = row >= 8 ? 7 : row;
/* 323 */       i = row * 8 + column;
/*     */     }
/*     */ 
/* 326 */     return i;
/*     */   }
/*     */ 
/*     */   public void paintComponent(Graphics g_)
/*     */   {
/* 338 */     Graphics2D g2 = (Graphics2D)g_;
/*     */ 
/* 341 */     if (this.atHeader) {
/* 342 */       paintAtHeader(g2);
/*     */ 
/* 344 */       return;
/*     */     }
/*     */ 
/* 348 */     int x = 7;
/* 349 */     int y = 7;
/*     */ 
/* 353 */     g2.setColor(this.backColor);
/*     */ 
/* 355 */     Rectangle bounds = getBounds();
/* 356 */     g2.fill(bounds);
/*     */ 
/* 358 */     Rectangle box = new Rectangle(x, y, 12, 12);
/*     */ 
/* 361 */     Color[] colors = get8x8Colors();
/*     */ 
/* 363 */     for (int i = 0; i < colors.length; i++) {
/* 364 */       Color c = colors[i];
/* 365 */       box.x = x;
/* 366 */       box.y = y;
/* 367 */       g2.setColor(c);
/* 368 */       g2.fill(box);
/* 369 */       g2.setColor(Color.darkGray);
/* 370 */       g2.draw(box);
/*     */ 
/* 372 */       if ((this.color != null) && (c.equals(this.color)))
/*     */       {
/* 374 */         draw3DBox(g2, box, 3, 3, false);
/* 375 */       } else if (this.hittedIndex == i)
/*     */       {
/* 377 */         draw3DBox(g2, box, 3, 3, true);
/*     */       }
/*     */ 
/* 380 */       x += 19;
/*     */ 
/* 382 */       if ((i + 1) % 8 == 0) {
/* 383 */         x = 7;
/* 384 */         y += 19;
/*     */       }
/*     */     }
/*     */ 
/* 388 */     this.indexColorBox.height = y;
/*     */ 
/* 391 */     FontMetrics fm = g2.getFontMetrics(g2.getFont());
/*     */ 
/* 393 */     if ((this.textBoxVisible & 0x1) != 0) {
/* 394 */       this.nullColorBox.y = y;
/* 395 */       this.nullColorBox.x = 0;
/*     */ 
/* 399 */       x = (this.nullColorBox.width - fm.stringWidth("透明")) / 2;
/* 400 */       y = this.nullColorBox.y + this.nullColorBox.height - (this.nullColorBox.height - fm.getHeight()) / 2;
/* 401 */       g2.setColor(Color.black);
/* 402 */       g2.drawString("透明", x, y);
/*     */ 
/* 404 */       g2.setColor(Color.darkGray);
/* 405 */       box = (Rectangle)this.nullColorBox.clone();
/* 406 */       box.grow(-7, -3);
/*     */ 
/* 408 */       g2.draw(box);
/*     */ 
/* 411 */       if (this.color == null)
/* 412 */         draw3DBox(g2, this.nullColorBox, -7, -3, false);
/* 413 */       else if (this.hittedIndex == 64) {
/* 414 */         draw3DBox(g2, this.nullColorBox, -7, -3, true);
/*     */       }
/*     */ 
/* 419 */       y = this.nullColorBox.y + this.nullColorBox.height;
/*     */     }
/*     */ 
/* 422 */     if ((this.textBoxVisible & 0x2) != 0)
/*     */     {
/* 424 */       this.otherColorBox.y = y;
/* 425 */       this.otherColorBox.x = 0;
/* 426 */       x = (this.otherColorBox.width - fm.stringWidth("定制")) / 2;
/* 427 */       y = this.otherColorBox.y + this.otherColorBox.height - (this.otherColorBox.height - fm.getHeight()) / 2;
/*     */ 
/* 429 */       g2.drawString("定制", x, y - 3);
/*     */ 
/* 431 */       if (this.hittedIndex == 65)
/* 432 */         draw3DBox(g2, this.otherColorBox, -3, 0, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void paintAtHeader(Graphics2D g2)
/*     */   {
/* 445 */     Rectangle box = new Rectangle(1, 2, 18, 10);
/* 446 */     g2.setColor(this.color);
/* 447 */     g2.fill(box);
/* 448 */     g2.setColor(Color.DARK_GRAY);
/* 449 */     g2.draw(box);
/*     */   }
/*     */ 
/*     */   public void draw3DBox(Graphics2D g2, Rectangle b, int gx, int gy, boolean raised)
/*     */   {
/* 462 */     Rectangle border = (Rectangle)b.clone();
/*     */ 
/* 464 */     border.grow(gx, gy);
/* 465 */     g2.setColor(Color.LIGHT_GRAY);
/* 466 */     g2.draw3DRect(border.x, border.y, border.width, border.height, raised);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ZColorPallette
 * JD-Core Version:    0.6.2
 */