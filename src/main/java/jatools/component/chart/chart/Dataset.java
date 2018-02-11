/*      */ package jatools.component.chart.chart;
/*      */ 
/*      */ import jatools.component.chart.Tip;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ 
/*      */ public class Dataset
/*      */   implements Serializable, Cloneable
/*      */ {
/*   34 */   ArrayList data = new ArrayList();
/*      */   String setName;
/*      */   Gc gc;
/*   37 */   Color labelColor = Color.black;
/*   38 */   Font labelFont = Gc.defaultFont;
/*      */   Globals globals;
/*      */   String[] labels;
/*      */   private Tip[] tips;
/*      */ 
/*      */   public Dataset()
/*      */   {
/*   49 */     this("new", null, null, new Globals());
/*      */   }
/*      */ 
/*      */   public Dataset(String name, double[] xarr, double[] yarr, double[] y2arr, double[] y3arr, int setNumber, Globals g)
/*      */   {
/*   73 */     init(name, setNumber, g);
/*      */ 
/*   75 */     for (int i = 0; i < yarr.length; i++)
/*   76 */       this.data.add(new Datum(xarr[i], yarr[i], y2arr[i], y3arr[i], g));
/*      */   }
/*      */ 
/*      */   public Dataset(String name, double[] xarr, double[] yarr, double[] y2arr, String[] label, int setNumber, Globals g)
/*      */   {
/*  103 */     init(name, setNumber, g);
/*      */ 
/*  105 */     for (int i = 0; i < yarr.length; i++) {
/*      */       try {
/*  107 */         this.data.add(new Datum(xarr[i], yarr[i], y2arr[i], label[i], setNumber, g));
/*      */       } catch (ArrayIndexOutOfBoundsException e) {
/*  109 */         return;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  115 */     this.labels = label;
/*      */   }
/*      */ 
/*      */   public Dataset(String name, double[] xarr, double[] yarr, double[] y2arr, int setNumber, Globals g)
/*      */   {
/*  137 */     init(name, setNumber, g);
/*      */ 
/*  139 */     for (int i = 0; i < yarr.length; i++)
/*  140 */       this.data.add(new Datum(xarr[i], yarr[i], y2arr[i], setNumber, g));
/*      */   }
/*      */ 
/*      */   public Dataset(String name, double[] xarr, double[] yarr, String[] label, int setNumber, Globals g)
/*      */   {
/*  163 */     init(name, setNumber, g);
/*      */ 
/*  165 */     for (int i = 0; i < yarr.length; i++) {
/*  166 */       this.data.add(new Datum(xarr[i], yarr[i], label[i], setNumber, g));
/*      */     }
/*      */ 
/*  169 */     this.labels = label;
/*      */   }
/*      */ 
/*      */   public Dataset(String name, double[] xarr, double[] yarr, int setNumber, Globals g)
/*      */   {
/*  188 */     init(name, setNumber, g);
/*      */ 
/*  190 */     for (int i = 0; i < xarr.length; i++)
/*  191 */       this.data.add(new Datum(xarr[i], yarr[i], setNumber, g));
/*      */   }
/*      */ 
/*      */   public Dataset(String name, double[] xarr, double[] yarr, Globals g)
/*      */   {
/*  209 */     init(name, -1, g);
/*      */ 
/*  211 */     if ((xarr == null) || (yarr == null)) {
/*  212 */       return;
/*      */     }
/*      */ 
/*  215 */     for (int i = 0; i < xarr.length; i++)
/*  216 */       this.data.add(new Datum(xarr[i], yarr[i], g));
/*      */   }
/*      */ 
/*      */   public Dataset(String name, double[] yarr, String[] label, int setNumber, Globals g)
/*      */   {
/*  236 */     init(name, setNumber, g);
/*      */ 
/*  238 */     for (int i = 0; i < yarr.length; i++) {
/*      */       try {
/*  240 */         this.data.add(new Datum(i, yarr[i], label[i], setNumber, g));
/*      */       } catch (ArrayIndexOutOfBoundsException e) {
/*  242 */         this.data.add(new Datum(i, yarr[i], "", setNumber, g));
/*      */       }
/*      */     }
/*      */ 
/*  246 */     this.labels = label;
/*      */   }
/*      */ 
/*      */   public Dataset(String name, double[] yarr, String[] label, boolean individual, Globals g)
/*      */   {
/*  263 */     init(name, 1, g);
/*      */ 
/*  265 */     for (int i = 0; i < yarr.length; i++) {
/*      */       try {
/*  267 */         this.data.add(new Datum(i, yarr[i], label[i], g));
/*      */       } catch (ArrayIndexOutOfBoundsException e) {
/*  269 */         this.data.add(new Datum(i, yarr[i], null, g));
/*      */       }
/*      */     }
/*      */ 
/*  273 */     this.labels = label;
/*      */   }
/*      */ 
/*      */   public Dataset(String name, double[] yarr, int setNumber, Globals g)
/*      */   {
/*  291 */     init(name, setNumber, g);
/*      */ 
/*  293 */     for (int i = 0; i < yarr.length; i++)
/*  294 */       this.data.add(new Datum(i, yarr[i], null, setNumber, g));
/*      */   }
/*      */ 
/*      */   public Dataset(String name, double[] yarr, boolean individual, Globals g)
/*      */   {
/*  312 */     init(name, 1, g);
/*      */ 
/*  314 */     for (int i = 0; i < yarr.length; i++)
/*  315 */       this.data.add(new Datum(i, yarr[i], individual, g));
/*      */   }
/*      */ 
/*      */   public Dataset(String s, double[] x, double[] y, String[] labels2, int numberOfDatasets, Globals globals2, Tip[] tips)
/*      */   {
/*  332 */     this(s, x, y, labels2, numberOfDatasets, globals2);
/*  333 */     this.tips = tips;
/*      */   }
/*      */ 
/*      */   public boolean hasTip()
/*      */   {
/*  342 */     if (this.tips != null) {
/*  343 */       for (int i = 0; i < this.tips.length; i++) {
/*  344 */         if (this.tips[i] != null) {
/*  345 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  350 */     return false;
/*      */   }
/*      */ 
/*      */   public void addDatum(Datum d)
/*      */   {
/*  361 */     this.data.add(d);
/*  362 */     d.setGlobals(this.globals);
/*      */   }
/*      */ 
/*      */   public void addPoint(double x, double y, String l)
/*      */   {
/*  378 */     addDatum(new Datum(x, y, l, this.data.size(), this.globals));
/*      */   }
/*      */ 
/*      */   public Object clone()
/*      */   {
/*      */     try
/*      */     {
/*  388 */       return super.clone(); } catch (CloneNotSupportedException e) {
/*      */     }
/*  390 */     return null;
/*      */   }
/*      */ 
/*      */   public void dump()
/*      */   {
/*  400 */     for (int i = 0; i < this.data.size(); i++)
/*  401 */       System.out.println("X " + ((Datum)this.data.get(i)).x + " Y " + ((Datum)this.data.get(i)).y + 
/*  402 */         " Y2 " + ((Datum)this.data.get(i)).y2);
/*      */   }
/*      */ 
/*      */   public ArrayList getData()
/*      */   {
/*  413 */     return this.data;
/*      */   }
/*      */ 
/*      */   public Datum getDataElementAt(int where)
/*      */   {
/*  422 */     return (Datum)this.data.get(where);
/*      */   }
/*      */ 
/*      */   public Gc getGc()
/*      */   {
/*  432 */     return this.gc;
/*      */   }
/*      */ 
/*      */   public Globals getGlobals()
/*      */   {
/*  442 */     return this.globals;
/*      */   }
/*      */ 
/*      */   public Color getLabelColor()
/*      */   {
/*  452 */     return this.labelColor;
/*      */   }
/*      */ 
/*      */   public Font getLabelFont()
/*      */   {
/*  462 */     return this.labelFont;
/*      */   }
/*      */ 
/*      */   public String getName()
/*      */   {
/*  471 */     return this.setName;
/*      */   }
/*      */ 
/*      */   public synchronized double[] getXValues()
/*      */   {
/*  483 */     double[] arr = new double[this.data.size()];
/*      */ 
/*  485 */     for (int i = 0; i < this.data.size(); i++) {
/*  486 */       arr[i] = ((Datum)this.data.get(i)).x;
/*      */     }
/*  488 */     return arr;
/*      */   }
/*      */ 
/*      */   public synchronized double[] getY2Values()
/*      */   {
/*  500 */     double[] arr = new double[this.data.size()];
/*      */ 
/*  502 */     for (int i = 0; i < this.data.size(); i++) {
/*  503 */       arr[i] = ((Datum)this.data.get(i)).y2;
/*      */     }
/*  505 */     return arr;
/*      */   }
/*      */ 
/*      */   public synchronized double[] getY3Values()
/*      */   {
/*  517 */     double[] arr = new double[this.data.size()];
/*      */ 
/*  519 */     for (int i = 0; i < this.data.size(); i++) {
/*  520 */       arr[i] = ((Datum)this.data.get(i)).y3;
/*      */     }
/*  522 */     return arr;
/*      */   }
/*      */ 
/*      */   public synchronized double[] getYValues()
/*      */   {
/*  534 */     double[] arr = new double[this.data.size()];
/*      */ 
/*  536 */     for (int i = 0; i < this.data.size(); i++) {
/*  537 */       arr[i] = ((Datum)this.data.get(i)).y;
/*      */     }
/*  539 */     return arr;
/*      */   }
/*      */ 
/*      */   private void init(String name, int setNumber, Globals g) {
/*  543 */     this.setName = name;
/*  544 */     this.globals = g;
/*      */ 
/*  546 */     if (setNumber == -1)
/*  547 */       this.gc = new Gc(g);
/*      */     else
/*  549 */       this.gc = new Gc(setNumber, g);
/*      */   }
/*      */ 
/*      */   private double max(double in, double cmp)
/*      */   {
/*  554 */     if (cmp == (-1.0D / 0.0D)) {
/*  555 */       return in;
/*      */     }
/*  557 */     return Math.max(in, cmp);
/*      */   }
/*      */ 
/*      */   public synchronized double maxX()
/*      */   {
/*  571 */     int count = this.data.size();
/*      */ 
/*  573 */     if (count == 0) {
/*  574 */       return 0.0D;
/*      */     }
/*      */ 
/*  577 */     double max = ((Datum)this.data.get(0)).x;
/*      */ 
/*  579 */     for (int i = 1; i < count; i++) {
/*  580 */       max = max(max, ((Datum)this.data.get(i)).x);
/*      */     }
/*  582 */     return max;
/*      */   }
/*      */ 
/*      */   public synchronized double maxY()
/*      */   {
/*  595 */     int count = this.data.size();
/*      */ 
/*  597 */     if (count == 0) {
/*  598 */       return 0.0D;
/*      */     }
/*      */ 
/*  601 */     double max = ((Datum)this.data.get(0)).y;
/*      */ 
/*  603 */     for (int i = 1; i < count; i++) {
/*  604 */       max = max(max, ((Datum)this.data.get(i)).y);
/*      */     }
/*  606 */     return max;
/*      */   }
/*      */ 
/*      */   public synchronized double maxY2()
/*      */   {
/*  619 */     int count = this.data.size();
/*      */ 
/*  621 */     if (count == 0) {
/*  622 */       return 0.0D;
/*      */     }
/*      */ 
/*  625 */     double max = ((Datum)this.data.get(0)).y2;
/*      */ 
/*  627 */     for (int i = 1; i < count; i++) {
/*  628 */       max = max(max, ((Datum)this.data.get(i)).y2);
/*      */     }
/*  630 */     return max;
/*      */   }
/*      */ 
/*      */   public synchronized double maxY3()
/*      */   {
/*  643 */     int count = this.data.size();
/*      */ 
/*  645 */     if (count == 0) {
/*  646 */       return 0.0D;
/*      */     }
/*      */ 
/*  649 */     double max = ((Datum)this.data.get(0)).y3;
/*      */ 
/*  651 */     for (int i = 1; i < count; i++) {
/*  652 */       max = max(max, ((Datum)this.data.get(i)).y3);
/*      */     }
/*  654 */     return max;
/*      */   }
/*      */ 
/*      */   private double min(double in, double cmp)
/*      */   {
/*  659 */     if (cmp == (-1.0D / 0.0D))
/*      */     {
/*  661 */       return in;
/*  662 */     }if (in == (-1.0D / 0.0D)) {
/*  663 */       return cmp;
/*      */     }
/*  665 */     return Math.min(in, cmp);
/*      */   }
/*      */ 
/*      */   public synchronized double minX()
/*      */   {
/*  679 */     double min = ((Datum)this.data.get(0)).x;
/*  680 */     int count = this.data.size();
/*      */ 
/*  682 */     for (int i = 1; i < count; i++)
/*      */     {
/*  684 */       min = min(min, ((Datum)this.data.get(i)).x);
/*      */     }
/*      */ 
/*  687 */     return min;
/*      */   }
/*      */ 
/*      */   public synchronized double minY()
/*      */   {
/*  700 */     if (this.data.size() == 0) {
/*  701 */       System.out.println();
/*      */     }
/*  703 */     double min = ((Datum)this.data.get(0)).y;
/*  704 */     int count = this.data.size();
/*      */ 
/*  706 */     for (int i = 1; i < count; i++) {
/*  707 */       min = min(min, ((Datum)this.data.get(i)).y);
/*      */     }
/*  709 */     return min;
/*      */   }
/*      */ 
/*      */   public synchronized double minY2()
/*      */   {
/*  722 */     double min = ((Datum)this.data.get(0)).y2;
/*  723 */     int count = this.data.size();
/*      */ 
/*  725 */     for (int i = 1; i < count; i++) {
/*  726 */       min = min(min, ((Datum)this.data.get(i)).y2);
/*      */     }
/*  728 */     return min;
/*      */   }
/*      */ 
/*      */   public synchronized double minY3()
/*      */   {
/*  741 */     double min = ((Datum)this.data.get(0)).y3;
/*  742 */     int count = this.data.size();
/*      */ 
/*  744 */     for (int i = 1; i < count; i++) {
/*  745 */       min = min(min, ((Datum)this.data.get(i)).y3);
/*      */     }
/*  747 */     return min;
/*      */   }
/*      */ 
/*      */   public synchronized void replaceLabels(String[] labels)
/*      */   {
/*  759 */     int count = Math.min(this.data.size(), labels.length);
/*      */ 
/*  761 */     for (int i = 0; i < count; i++)
/*  762 */       if (labels[i] != null)
/*  763 */         ((Datum)this.data.get(i)).label = labels[i];
/*      */   }
/*      */ 
/*      */   public synchronized void replaceXData(double[] xarr)
/*      */   {
/*  783 */     int count = this.data.size();
/*      */ 
/*  785 */     if (xarr.length > count) {
/*  786 */       for (int i = 0; i < count; i++) {
/*  787 */         ((Datum)this.data.get(i)).x = xarr[i];
/*      */       }
/*      */ 
/*  790 */       for (int i = count; i < xarr.length; i++)
/*  791 */         this.data.add(new Datum(xarr[i], 0.0D, this.globals));
/*      */     }
/*      */     else {
/*  794 */       while (this.data.size() > xarr.length)
/*      */       {
/*      */         int i;
/*  795 */         this.data.remove(this.data.size() - 1);
/*      */       }
/*      */ 
/*  798 */       for (int i = 0; i < xarr.length; i++)
/*  799 */         ((Datum)this.data.get(i)).x = xarr[i];
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized void replaceY2Data(double[] yarr)
/*      */   {
/*  817 */     int count = this.data.size();
/*      */ 
/*  819 */     if (yarr.length > count) {
/*  820 */       for (i = 0; i < count; i++) {
/*  821 */         ((Datum)this.data.get(i)).y2 = yarr[i];
/*      */       }
/*      */ 
/*  824 */       for (i = count; i < yarr.length; i++)
/*  825 */         this.data.add(new Datum(0.0D, 0.0D, yarr[i], 0.0D, this.globals));
/*      */     } else {
/*  827 */       while (this.data.size() > yarr.length)
/*      */       {
/*      */         int i;
/*  828 */         this.data.remove(this.data.size() - 1);
/*      */       }
/*      */     }
/*      */ 
/*  832 */     for (int i = 0; i < yarr.length; i++)
/*  833 */       ((Datum)this.data.get(i)).y2 = yarr[i];
/*      */   }
/*      */ 
/*      */   public synchronized void replaceY3Data(double[] yarr)
/*      */   {
/*  849 */     int count = this.data.size();
/*      */ 
/*  851 */     if (yarr.length > count) {
/*  852 */       for (int i = 0; i < count; i++) {
/*  853 */         ((Datum)this.data.get(i)).y3 = yarr[i];
/*      */       }
/*      */ 
/*  856 */       for (int i = count; i < yarr.length; i++)
/*  857 */         this.data.add(new Datum(0.0D, 0.0D, 0.0D, yarr[i], this.globals));
/*      */     } else {
/*  859 */       while (this.data.size() > yarr.length)
/*      */       {
/*      */         int i;
/*  860 */         this.data.remove(this.data.size() - 1);
/*      */       }
/*      */     }
/*      */ 
/*  864 */     for (int i = 0; i < yarr.length; i++)
/*  865 */       ((Datum)this.data.get(i)).y3 = yarr[i];
/*      */   }
/*      */ 
/*      */   public synchronized void replaceYData(double[] yarr)
/*      */   {
/*  882 */     int count = this.data.size();
/*      */ 
/*  884 */     if (yarr.length > count) {
/*  885 */       for (int i = 0; i < count; i++) {
/*  886 */         ((Datum)this.data.get(i)).y = yarr[i];
/*      */       }
/*      */ 
/*  889 */       for (int i = count; i < yarr.length; i++)
/*  890 */         this.data.add(new Datum(0.0D, yarr[i], 0.0D, 0.0D, this.globals));
/*      */     }
/*      */     else {
/*  893 */       while (this.data.size() > yarr.length)
/*      */       {
/*      */         int i;
/*  894 */         this.data.remove(this.data.size() - 1);
/*      */       }
/*      */     }
/*      */ 
/*  898 */     for (int i = 0; i < yarr.length; i++)
/*  899 */       ((Datum)this.data.get(i)).y = yarr[i];
/*      */   }
/*      */ 
/*      */   public void setData(ArrayList d)
/*      */   {
/*  911 */     this.data = d;
/*      */ 
/*  913 */     for (int i = 0; i < d.size(); i++)
/*  914 */       ((Datum)d.get(i)).setGlobals(this.globals);
/*      */   }
/*      */ 
/*      */   public void setGc(Gc g)
/*      */   {
/*  926 */     this.gc = g;
/*  927 */     g.globals = this.globals;
/*      */   }
/*      */ 
/*      */   public void setGlobals(Globals g)
/*      */   {
/*  938 */     this.globals = g;
/*      */ 
/*  940 */     for (int i = 0; i < this.data.size(); i++) {
/*  941 */       Datum d = getDataElementAt(i);
/*  942 */       d.setGlobals(g);
/*      */     }
/*      */ 
/*  945 */     this.gc.globals = g;
/*      */   }
/*      */ 
/*      */   public void setLabelColor(Color c)
/*      */   {
/*  956 */     this.labelColor = c;
/*      */   }
/*      */ 
/*      */   public void setLabelFont(Font f)
/*      */   {
/*  967 */     this.labelFont = f;
/*      */   }
/*      */ 
/*      */   public void setName(String s)
/*      */   {
/*  977 */     this.setName = s;
/*      */   }
/*      */ 
/*      */   public String[] getLabels()
/*      */   {
/*  985 */     return this.labels;
/*      */   }
/*      */ 
/*      */   public void setLabels(String[] l)
/*      */   {
/*  994 */     this.labels = l;
/*      */   }
/*      */ 
/*      */   public Tip[] getTips()
/*      */   {
/* 1003 */     return this.tips;
/*      */   }
/*      */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Dataset
 * JD-Core Version:    0.6.2
 */