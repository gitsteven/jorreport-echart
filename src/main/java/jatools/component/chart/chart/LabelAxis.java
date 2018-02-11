/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.engine.ProtectClass;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class LabelAxis extends Axis
/*     */   implements AxisInterface, ProtectClass
/*     */ {
/*     */   protected ArrayList labelList;
/*     */ 
/*     */   public LabelAxis()
/*     */   {
/*     */   }
/*     */ 
/*     */   public LabelAxis(Dataset[] dsets, boolean xAxis, Plotarea plt)
/*     */   {
/*  42 */     super(dsets, xAxis, plt);
/*     */   }
/*     */ 
/*     */   public synchronized void addLabel(String str)
/*     */   {
/*  50 */     if (this.labelList == null) {
/*  51 */       this.labelList = new ArrayList();
/*     */     }
/*  53 */     this.labelList.add(str);
/*     */   }
/*     */ 
/*     */   public void addLabels(String[] str)
/*     */   {
/*  61 */     replaceLabels(str);
/*     */   }
/*     */ 
/*     */   public synchronized void deleteLabel(int which)
/*     */   {
/*  72 */     if (this.labelList == null) {
/*  73 */       return;
/*     */     }
/*  75 */     this.labelList.remove(which);
/*     */   }
/*     */ 
/*     */   protected String getLabel(double dummy, int index)
/*     */   {
/*  84 */     if ((this.labelList != null) && 
/*  85 */       (this.labelList.size() > index))
/*  86 */       return (String)this.labelList.get(index);
/*     */     try {
/*  88 */       if (((Datum)this.datasets[0].data.get(index)).label != null) {
/*  89 */         return ((Datum)this.datasets[0].data.get(index)).label;
/*     */       }
/*  91 */       return super.getLabel(((Datum)this.datasets[0].data.get(index)).y, 0);
/*     */     } catch (ArrayIndexOutOfBoundsException e) {
/*     */     }
/*  94 */     return " ";
/*     */   }
/*     */ 
/*     */   public ArrayList getLabels()
/*     */   {
/* 103 */     return this.labelList;
/*     */   }
/*     */ 
/*     */   private synchronized boolean labelScale()
/*     */   {
/* 109 */     int maxNumDataPoints = 0;
/*     */ 
/* 111 */     int nmsets = datasetsInUse();
/* 112 */     if (nmsets == 0) {
/* 113 */       return false;
/*     */     }
/* 115 */     for (int i = 0; i < nmsets; i++) {
/* 116 */       if (maxNumDataPoints < this.datasets[i].data.size()) {
/* 117 */         maxNumDataPoints = this.datasets[i].data.size();
/*     */       }
/*     */     }
/*     */ 
/* 121 */     this.axisStart = 0.0D;
/*     */ 
/* 123 */     if (this.userAxisStart != null) {
/* 124 */       this.axisStart = this.userAxisStart.doubleValue();
/*     */     }
/* 126 */     if (this.userAxisEnd != null) {
/* 127 */       maxNumDataPoints = this.userAxisEnd.intValue();
/*     */     }
/*     */ 
/* 130 */     if (this.barScaling) {
/* 131 */       this.axisStart -= 1.0D;
/* 132 */       this.axisEnd = maxNumDataPoints;
/* 133 */       this.numLabels = maxNumDataPoints;
/*     */ 
/* 135 */       this.numMajTicks = (this.numLabels + 1);
/*     */     }
/*     */     else {
/* 138 */       this.axisEnd = (maxNumDataPoints - 1.0D);
/*     */ 
/* 141 */       this.numLabels = (maxNumDataPoints - 1);
/* 142 */       this.numMajTicks = this.numLabels;
/*     */     }
/* 144 */     if (this.axisStart == this.axisEnd) {
/* 145 */       this.numLabels = (this.numMajTicks = this.numGrids = this.numMinTicks = 1);
/* 146 */       this.axisEnd += 1.0D;
/*     */     }
/* 148 */     this.numGrids = this.numMajTicks;
/* 149 */     this.numMinTicks = (this.numMajTicks * 2);
/* 150 */     return true;
/*     */   }
/*     */ 
/*     */   public synchronized void replaceLabels(String[] str)
/*     */   {
/* 160 */     this.labelList = new ArrayList();
/*     */ 
/* 162 */     for (int i = 0; i < str.length; i++)
/* 163 */       this.labelList.add(str[i]);
/*     */   }
/*     */ 
/*     */   public void scale()
/*     */   {
/* 171 */     labelScale();
/*     */   }
/*     */ 
/*     */   public void setLogScaling(boolean yesNo)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected int whereOnAxis(int whichStep, int whichElement)
/*     */   {
/* 191 */     if ((whichElement == 4) || (whichElement == 3) || (whichElement == 2)) {
/* 192 */       return super.whereOnAxis(whichStep, whichElement);
/*     */     }
/* 194 */     if ((this.side == 0) || (this.side == 2)) {
/* 195 */       float increment = getIncrement(this.endPoint.x, this.startPoint.x, this.numMajTicks);
/* 196 */       if (this.barScaling) {
/* 197 */         return this.startPoint.x + (int)(increment * (whichStep + 1));
/*     */       }
/* 199 */       return this.startPoint.x + (int)(increment * whichStep);
/*     */     }
/*     */ 
/* 202 */     float increment = getIncrement(this.endPoint.y, this.startPoint.y, this.numMajTicks);
/* 203 */     if (this.barScaling) {
/* 204 */       return this.startPoint.y + (int)(increment * (whichStep + 1));
/*     */     }
/* 206 */     return this.startPoint.y + (int)(increment * whichStep);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.LabelAxis
 * JD-Core Version:    0.6.2
 */