/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.Chart;
/*     */ import jatools.component.chart.ChartSourceUtil;
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.PlotData;
/*     */ import jatools.component.chart.beans.chart.ChartBean;
/*     */ import jatools.component.chart.chart.CandlestickChart;
/*     */ import jatools.component.chart.chart.DateAxis;
/*     */ import jatools.component.chart.chart.GanttChart;
/*     */ import jatools.component.chart.chart.HiLoBarChart;
/*     */ import jatools.component.chart.chart.HiLoCloseChart;
/*     */ import jatools.component.chart.chart.HorizHiLoBarChart;
/*     */ import jatools.component.chart.chart._Chart;
/*     */ import jatools.dataset.Column;
/*     */ import jatools.engine.ProtectClass;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ 
/*     */ public abstract class Tabs extends JPanel
/*     */   implements ChangeListener, ProtectClass, javax.swing.event.ChangeListener
/*     */ {
/*  41 */   static String[] dataPrompts = { 
/*  42 */     "X轴标签数据类型不限.显示列数不限,类型为数值型.", 
/*  43 */     "X轴标签数据类型不限.显示列数不限,类型为数值型.", 
/*  44 */     "X轴标签数据类型不限.显示列数不限,类型为数值型.", 
/*  45 */     "X轴标签数据类型不限.显示列数不限,类型为数值型.", 
/*  46 */     "X轴标签数据类型不限.显示列数不限,类型为数值型.", 
/*  47 */     "X轴标签数据类型不限.只能一列类型为数值型.", 
/*  48 */     "X轴标签数据类型不限.显示列数不限,类型为数值型.", 
/*  49 */     "X轴标签数据类型不限.显示列数不限,类型为数值型.", 
/*  50 */     "X轴标签数据类型不限.显示列数不限,类型为数值型.", 
/*  51 */     "X轴标签数据类型需日期型.显示列数不限,类型为数值型.", 
/*  52 */     "X轴标签数据类型需日期型.显示列数不限,类型为数值型.", 
/*  53 */     "X轴标签数据类型需日期型.显示列数不限,类型为数值型.", 
/*  54 */     "X轴标签数据类型需日期型.显示列数不限,类型为数值型.", 
/*  55 */     "X轴标签数据类型需日期型.显示列数不限,类型为数值型.", 
/*  56 */     "X轴标签数据类型需日期型.显示列数不限,类型为数值型.", 
/*  57 */     "X轴标签数据类型需日期型.显示列数不限,类型为数值型.", 
/*  58 */     "X轴标签数据类型不限.显示列数不限,类型为数值型.", 
/*  59 */     "X轴标签数据类型不限.显示列数不限,类型为数值型.", 
/*  60 */     "X轴标签数据类型不限.显示列数只限一列,类型为数值型.", 
/*  61 */     "X轴标签数据类型不限.显示列数不限,类型为数值型.", 
/*  62 */     "X轴标签数据类型不限.显示列数不限,类型为数值型.", 
/*  63 */     "X轴标签数据类型不限.显示列数不限,类型为数值型.", 
/*  64 */     "X轴标签数据类型不限.显示列数不限,类型为数值型.", 
/*  65 */     "X轴标签数据类型不限.显示列数不限,类型为数值型.", 
/*  66 */     "X轴标签数据类型不限.显示列数须二列,类型为数值型,第一列表示最小值，第二列表示最大值.", 
/*  67 */     "X轴标签数据类型不限.显示列数须二列,类型为数值型.第一列表示最小值，第二列表示最大值", 
/*  68 */     "X轴标签数据类型不限.显示列数须四列,类型为数值型,依次分别是最高,最低,开盘,收盘价.", 
/*  69 */     "X轴标签数据类型不限.显示列数须四列,类型为数值型,依次分别是最高,最低,开盘,收盘价.", 
/*  70 */     "X轴类型不限.显示列数必须是两列,分别表示开始时间和结束时间,显示列类型必须为日期型.", 
/*  71 */     "X轴标签数据类型不限.显示列数不限,类型为数值型." };
/*     */   protected Chart javachart;
/*     */   protected _Chart chart;
/*     */   protected ChartBean chartBean;
/*     */   private ChangeListener parent;
/*     */   protected DataSelector dataPanel;
/*     */   protected JTabbedPane tp;
/*     */ 
/*     */   public void addChangeListener(ChangeListener l)
/*     */   {
/*  87 */     this.parent = l;
/*     */   }
/*     */ 
/*     */   protected abstract void initTabbed();
/*     */ 
/*     */   protected abstract void refershTabContent();
/*     */ 
/*     */   public void fireChange(Object object)
/*     */   {
/* 101 */     if (this.parent != null)
/* 102 */       this.parent.fireChange(object);
/*     */   }
/*     */ 
/*     */   public final void setChart(_Chart chart, int type)
/*     */   {
/*     */     try
/*     */     {
/* 130 */       this.chart = chart;
/*     */     } catch (ClassCastException e) {
/* 132 */       System.out.println("oops!");
/* 133 */       System.out.println("this is not a javachart bean!");
/* 134 */       System.out.println("it's " + e.getMessage());
/*     */ 
/* 136 */       return;
/*     */     }
/*     */ 
/* 139 */     initializeCustomizer(type);
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void stateChanged(ChangeEvent e)
/*     */   {
/* 156 */     if ((e.getSource() instanceof SelectCommand)) {
/* 157 */       SelectCommand cmd = (SelectCommand)e.getSource();
/*     */ 
/* 159 */       Class cls = cmd.field.getColumnClass();
/*     */ 
/* 161 */       if (cls != null) {
/* 162 */         if (cmd.type == 1) {
/* 163 */           if ((!(this.chart instanceof GanttChart)) && (!Number.class.isAssignableFrom(cls)))
/* 164 */             cmd.error = "显示数据项必须是数值型!";
/* 165 */           else if (((this.chart instanceof GanttChart)) && (!Date.class.isAssignableFrom(cls))) {
/* 166 */             cmd.error = "显示数据项必须日期型!";
/*     */           }
/*     */         }
/* 169 */         else if (((this.chart.getXAxis() instanceof DateAxis)) && 
/* 170 */           (!Date.class.isAssignableFrom(cls))) {
/* 171 */           cmd.error = "标签数据项必须日期型!";
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 177 */       boolean update = false;
/*     */ 
/* 179 */       if ((this.dataPanel.getReader() != null) && (this.dataPanel.getLabelField() != null) && 
/* 180 */         (this.dataPanel.getShowData().length > 0)) {
/* 181 */         update = true;
/*     */ 
/* 183 */         if ((((this.chart instanceof HiLoBarChart)) || ((this.chart instanceof HorizHiLoBarChart))) && 
/* 184 */           (this.dataPanel.getShowData().length < 2))
/* 185 */           update = false;
/* 186 */         else if (((this.chart instanceof HiLoCloseChart)) && (this.dataPanel.getShowData().length < 3))
/* 187 */           update = false;
/* 188 */         else if (((this.chart instanceof CandlestickChart)) && 
/* 189 */           (this.dataPanel.getShowData().length < 4)) {
/* 190 */           update = false;
/*     */         }
/*     */       }
/*     */ 
/* 194 */       dataPanelToJavaChart();
/*     */ 
/* 196 */       if (update) {
/* 197 */         ChartSourceUtil.loadSource(this.javachart, this.chart, null);
/* 198 */         refershTabContent();
/* 199 */         fireChange(null);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dataPanelToJavaChart()
/*     */   {
/* 208 */     boolean update = false;
/*     */ 
/* 225 */     this.javachart.setReader(this.dataPanel.getReader());
/* 226 */     this.javachart.setLabelField(this.dataPanel.getLabelField());
/*     */ 
/* 228 */     ArrayList show = new ArrayList();
/*     */ 
/* 230 */     for (int i = 0; i < this.dataPanel.getShowData().length; i++) {
/* 231 */       show.add(this.dataPanel.getShowData()[i]);
/*     */     }
/*     */ 
/* 234 */     this.javachart.setPlotData(show);
/*     */   }
/*     */ 
/*     */   protected final void initializeCustomizer(int type)
/*     */   {
/* 240 */     GridBagLayout gbl = new GridBagLayout();
/* 241 */     setLayout(gbl);
/*     */ 
/* 243 */     GridBagConstraints gbc = new GridBagConstraints();
/* 244 */     gbc.insets = CommonFinal.INSETS;
/* 245 */     gbc.fill = 1;
/*     */ 
/* 247 */     this.tp = new JTabbedPane();
/*     */ 
/* 249 */     gbc.weightx = 1.0D;
/* 250 */     gbc.weighty = 1.0D;
/*     */ 
/* 252 */     this.dataPanel = new DataSelector();
/* 253 */     initDataPanel();
/* 254 */     this.dataPanel.addChangeListener(this);
/*     */ 
/* 256 */     this.dataPanel.setPrompt(dataPrompts[type]);
/* 257 */     this.dataPanel.setType(type);
/* 258 */     this.tp.add(this.dataPanel, "数据集");
/*     */ 
/* 260 */     initTabbed();
/*     */ 
/* 262 */     add(this.tp, gbc);
/*     */   }
/*     */ 
/*     */   public void initDataPanel()
/*     */   {
/* 270 */     PlotData[] data = (PlotData[])null;
/*     */ 
/* 272 */     if (this.javachart.getPlotData() != null) {
/* 273 */       data = (PlotData[])this.javachart.getPlotData().toArray(new PlotData[0]);
/*     */     }
/*     */ 
/* 276 */     this.dataPanel.init2(this.javachart.getReader(), this.javachart.getLabelField(), data);
/*     */   }
/*     */ 
/*     */   public void setJavaChart(Chart javachart, int type)
/*     */   {
/*     */     try
/*     */     {
/* 291 */       this.javachart = javachart;
/* 292 */       this.chart = ((_Chart)javachart.chart);
/*     */     } catch (ClassCastException e) {
/* 294 */       System.out.println("oops!");
/* 295 */       System.out.println("this is not a javachart bean!");
/* 296 */       System.out.println("it's " + e.getMessage());
/*     */ 
/* 298 */       return;
/*     */     }
/*     */ 
/* 301 */     initializeCustomizer(type);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.Tabs
 * JD-Core Version:    0.6.2
 */