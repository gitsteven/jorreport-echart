/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import jatools.component.chart.applet.DataProvider;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.TagSupport;
/*     */ 
/*     */ public class DataProviderFilter extends TagSupport
/*     */   implements DataProvider
/*     */ {
/*     */   protected DataProvider dataProvider;
/*  28 */   protected String dataProviderID = "DataProviderFilter:";
/*     */ 
/*  31 */   protected int startDataset = -2147483648;
/*     */ 
/*  33 */   protected int endDataset = 2147483647;
/*  34 */   protected int startObservation = -2147483648;
/*  35 */   protected int endObservation = 2147483647;
/*     */   ArrayList datasets;
/*  39 */   String uniqueIdentifier = null;
/*     */ 
/*     */   public void setStartDataset(int i)
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/*  48 */       this.startDataset = i;
/*     */     } catch (Exception e) {
/*  50 */       throw new JspException("DataProviderFilter properties must be integers");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setEndDataset(int i)
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/*  62 */       this.endDataset = i;
/*     */     } catch (Exception e) {
/*  64 */       throw new JspException("DataProviderFilter properties must be integers");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setStartObservation(int i)
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/*  74 */       this.startObservation = i;
/*     */     } catch (Exception e) {
/*  76 */       throw new JspException("DataProviderFilter properties must be integers");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setEndObservation(int i)
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/*  88 */       this.endObservation = i;
/*     */     } catch (Exception e) {
/*  90 */       throw new JspException("DataProviderFilter properties must be integers");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setDataProvider(DataProvider dataProvider)
/*     */   {
/* 100 */     this.dataProvider = dataProvider;
/*     */   }
/*     */ 
/*     */   public void setDataProviderID(String dataProviderId)
/*     */   {
/* 108 */     this.dataProviderID = dataProviderId;
/*     */   }
/*     */ 
/*     */   public int doStartTag() throws JspException {
/* 112 */     if (this.dataProvider == null) {
/* 113 */       if (this.dataProviderID != null) {
/* 114 */         this.dataProvider = ((DataProvider)this.pageContext.getAttribute(this.dataProviderID));
/* 115 */         if (this.dataProvider == null)
/* 116 */           this.dataProvider = ((DataProvider)this.pageContext.findAttribute(this.dataProviderID));
/*     */       }
/* 118 */       if (this.dataProvider == null)
/* 119 */         throw new JspException("Couldn't find a DataProvider by ID or property.");
/*     */     }
/*     */     try {
/* 122 */       ChartTag chartTag = (ChartTag)
/* 123 */         findAncestorWithClass(this, Class.forName("javachart.servlet.ChartTag"));
/*     */ 
/* 126 */       filterData();
/* 127 */       chartTag.setDataProvider(this);
/*     */ 
/* 130 */       return 1; } catch (ClassNotFoundException e) {
/*     */     }
/* 132 */     throw new JspException("Couldn't find an outer chart tag to provide data to.");
/*     */   }
/*     */ 
/*     */   protected void filterData()
/*     */   {
/* 138 */     this.datasets = new ArrayList();
/* 139 */     if ((this.startObservation == -2147483648) && (this.endObservation == 2147483647)) {
/* 140 */       Iterator en = this.dataProvider.getDatasets();
/* 141 */       int count = 0;
/* 142 */       while (en.hasNext()) {
/* 143 */         Object o = en.next();
/* 144 */         if ((o instanceof Dataset)) {
/* 145 */           Dataset d = (Dataset)o;
/* 146 */           if ((count >= this.startDataset) && (count <= this.endDataset))
/* 147 */             this.datasets.add(d);
/* 148 */           count++;
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/* 153 */       Iterator en = this.dataProvider.getDatasets();
/* 154 */       int count = 0;
/* 155 */       while (en.hasNext()) {
/* 156 */         Object o = en.next();
/* 157 */         if ((o instanceof Dataset)) {
/* 158 */           Dataset d = (Dataset)o;
/*     */           try {
/* 160 */             Dataset d1 = (Dataset)d.clone();
/* 161 */             d1.setData((ArrayList)d.getData().clone());
/* 162 */             d = d1;
/* 163 */             if ((count >= this.startDataset) && (count <= this.endDataset))
/* 164 */               this.datasets.add(d);
/*     */           }
/*     */           catch (Exception localException) {
/*     */           }
/* 168 */           count++;
/*     */         }
/*     */       }
/* 171 */       Iterator it = this.datasets.iterator();
/* 172 */       while (it.hasNext())
/*     */       {
/* 174 */         Dataset d = (Dataset)it.next();
/* 175 */         constrainDataElements(d);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void constrainDataElements(Dataset d) {
/*     */     try {
/* 182 */       ArrayList data = d.getData();
/* 183 */       ArrayList v = (ArrayList)data.clone();
/* 184 */       data.clear();
/* 185 */       int count = 0;
/* 186 */       Iterator it = v.iterator();
/* 187 */       while (it.hasNext()) {
/* 188 */         Object o = it.next();
/* 189 */         if ((count >= this.startObservation) && (count <= this.endObservation)) {
/* 190 */           data.add(o);
/*     */         }
/* 192 */         count++;
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public Iterator getDatasets()
/*     */   {
/* 204 */     return this.datasets.iterator();
/*     */   }
/*     */ 
/*     */   public String getUniqueIdentifier()
/*     */   {
/* 212 */     String s = "DataProviderFilter:" + this.startObservation + 
/* 213 */       this.endObservation + this.startDataset + this.endDataset + this.dataProvider.getUniqueIdentifier();
/* 214 */     this.dataProvider = null;
/* 215 */     this.dataProviderID = null;
/* 216 */     return s;
/*     */   }
/*     */ 
/*     */   private void doInventory() {
/* 220 */     System.out.println("startObservation:" + this.startObservation);
/* 221 */     System.out.println("endObservation:" + this.endObservation);
/* 222 */     System.out.println("startDataset:" + this.startDataset);
/* 223 */     System.out.println("endDataset:" + this.endDataset);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.DataProviderFilter
 * JD-Core Version:    0.6.2
 */