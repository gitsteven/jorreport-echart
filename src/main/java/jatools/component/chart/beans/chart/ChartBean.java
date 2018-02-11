/*     */ package jatools.component.chart.beans.chart;
/*     */ 
/*     */ import jatools.component.chart.beans.data.DataEvent;
/*     */ import jatools.component.chart.beans.data.DataFeedListener;
/*     */ import jatools.component.chart.chart.Background;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.Datum;
/*     */ import jatools.component.chart.chart.RotateString;
/*     */ import jatools.component.chart.chart._Chart;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Panel;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public abstract class ChartBean extends Panel
/*     */   implements Serializable, DataFeedListener, PropertyChangeListener
/*     */ {
/*  36 */   int width = 200;
/*  37 */   int height = 150;
/*     */   protected int modifiers;
/*     */   protected _Chart chart;
/*     */   private transient Image offScreenImage;
/*     */   private transient Dimension offScreenSize;
/*     */   private transient Graphics offScreenGraphics;
/*     */ 
/*     */   private void copyData(ArrayList fromData, ArrayList toData)
/*     */   {
/*  52 */     int fromSize = fromData.size();
/*     */ 
/*  56 */     while (toData.size() > fromSize) {
/*  57 */       toData.remove(fromSize);
/*     */     }
/*     */ 
/*  60 */     for (int i = 0; i < toData.size(); i++) {
/*  61 */       if ((this.modifiers & 0x1) != 0)
/*  62 */         ((Datum)toData.get(i)).setY(((Datum)fromData.get(i)).getY());
/*  63 */       if ((this.modifiers & 0x2) != 0)
/*  64 */         ((Datum)toData.get(i)).setX(((Datum)fromData.get(i)).getX());
/*  65 */       if ((this.modifiers & 0x10) != 0) {
/*  66 */         String label = ((Datum)fromData.get(i)).getLabel();
/*  67 */         ((Datum)toData.get(i)).setLabel(label);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  72 */     if (toData.size() < fromSize)
/*  73 */       for (i = toData.size(); i < fromSize; i++) {
/*  74 */         Datum d = (Datum)fromData.get(i);
/*  75 */         Datum newD = new Datum(d.getX(), d.getY(), d.getY2(), d.getLabel(), 
/*  76 */           i, this.chart.getGlobals());
/*  77 */         toData.add(newD);
/*     */       }
/*     */   }
/*     */ 
/*     */   public _Chart getChart()
/*     */   {
/*  88 */     return this.chart;
/*     */   }
/*     */ 
/*     */   public int getDatasetImageIndex(int dataset)
/*     */   {
/*  94 */     return 0;
/*     */   }
/*     */   public Dimension getMinimumSize() {
/*  97 */     return new Dimension(200, 100);
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredSize() {
/* 101 */     return new Dimension(this.width, this.height);
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 109 */     return this.chart.getBackground().getTitleString();
/*     */   }
/*     */ 
/*     */   public void newData(DataEvent e)
/*     */   {
/* 122 */     this.modifiers = e.modifiers;
/* 123 */     updateDatasets(e.datasetArray);
/* 124 */     repaint();
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g) {
/* 128 */     Dimension d = getSize();
/*     */ 
/* 131 */     this.chart.resize(d.width, d.height);
/*     */     try {
/* 133 */       if ((this.offScreenImage == null) || 
/* 134 */         (d.width != this.offScreenSize.width) || 
/* 135 */         (d.height != this.offScreenSize.height)) {
/* 136 */         this.offScreenImage = createImage(d.width, d.height);
/* 137 */         this.offScreenSize = d;
/* 138 */         this.offScreenGraphics = this.offScreenImage.getGraphics();
/*     */       }
/*     */ 
/* 141 */       this.chart.setImage(this.offScreenImage);
/* 142 */       if (this.chart.getStringRotator() == null) {
/* 143 */         this.chart.setStringRotator(new RotateString(this));
/*     */       }
/* 145 */       if (this.offScreenGraphics != null) {
/* 146 */         this.chart.drawGraph(this.offScreenGraphics);
/*     */       }
/* 148 */       g.drawImage(this.offScreenImage, 0, 0, null);
/*     */     }
/*     */     catch (Exception e) {
/* 151 */       this.chart.drawGraph(g);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void propertyChange(PropertyChangeEvent e)
/*     */   {
/* 159 */     repaint();
/*     */   }
/*     */ 
/*     */   public void reshape(int x, int y, int w, int h) {
/* 163 */     this.width = w;
/* 164 */     this.height = h;
/* 165 */     super.reshape(x, y, this.width, this.height);
/*     */   }
/*     */   public void resize(int w, int h) {
/* 168 */     this.width = w;
/* 169 */     this.height = h;
/*     */   }
/*     */   public void setBounds(int x, int y, int w, int h) {
/* 172 */     reshape(x, y, w, h);
/*     */   }
/*     */ 
/*     */   public void setDatasetImageIndex(int dataset, int image)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setPreferredSize(int w, int h)
/*     */   {
/* 181 */     this.width = w;
/* 182 */     this.height = h;
/*     */   }
/*     */   public void setPreferredSize(Dimension d) {
/* 185 */     this.width = d.width;
/* 186 */     this.height = d.height;
/*     */   }
/*     */   public void setSize(int w, int h) {
/* 189 */     this.width = w;
/* 190 */     this.height = h;
/*     */   }
/*     */ 
/*     */   public void setTitle(String s)
/*     */   {
/* 198 */     this.chart.getBackground().setTitleString(s);
/*     */   }
/*     */ 
/*     */   public synchronized void update(Graphics g) {
/* 202 */     paint(g);
/*     */   }
/*     */ 
/*     */   protected void updateDatasets(Dataset[] incomingData)
/*     */   {
/* 211 */     Dataset[] myDatasets = this.chart.getDatasets();
/* 212 */     int numberOfSets = 0;
/*     */ 
/* 214 */     while ((numberOfSets < incomingData.length) && (incomingData[numberOfSets] != null)) {
/* 215 */       numberOfSets++;
/*     */     }
/*     */ 
/* 218 */     for (int i = numberOfSets; i < myDatasets.length; i++) {
/* 219 */       myDatasets[i] = null;
/*     */     }
/* 221 */     for (int i = 0; i < numberOfSets; i++) {
/* 222 */       if (myDatasets[i] == null)
/*     */       {
/* 224 */         myDatasets[i] = new Dataset(null, new double[0], i, this.chart.getGlobals());
/* 225 */         myDatasets[i].setName(incomingData[i].getName());
/* 226 */         myDatasets[i].setData(incomingData[i].getData());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 231 */     for (int i = 0; i < numberOfSets; i++)
/* 232 */       if (incomingData[i] != null) {
/* 233 */         if ((this.modifiers & 0x20) != 0)
/* 234 */           myDatasets[i].setName(incomingData[i].getName());
/* 235 */         copyData(incomingData[i].getData(), 
/* 236 */           myDatasets[i].getData());
/*     */       }
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.beans.chart.ChartBean
 * JD-Core Version:    0.6.2
 */