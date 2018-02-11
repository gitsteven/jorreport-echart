/*      */ package jatools.component.chart;
/*      */ 
/*      */ import bsh.NameSpace;
/*      */ import bsh.UtilEvalError;
/*      */ import jatools.component.chart.chart.Axis;
/*      */ import jatools.component.chart.chart.AxisInterface;
/*      */ import jatools.component.chart.chart.BubbleChart;
/*      */ import jatools.component.chart.chart.CandlestickChart;
/*      */ import jatools.component.chart.chart.ChartInterface;
/*      */ import jatools.component.chart.chart.DateAxis;
/*      */ import jatools.component.chart.chart.HiLoBarChart;
/*      */ import jatools.component.chart.chart.HiLoCloseChart;
/*      */ import jatools.component.chart.chart.HorizHiLoBarChart;
/*      */ import jatools.component.chart.chart.PieChart;
/*      */ import jatools.data.reader.DatasetCursor;
/*      */ import jatools.data.reader.DatasetReader;
/*      */ import jatools.data.reader.ScrollableField;
/*      */ import jatools.dataset.Dataset;
/*      */ import jatools.dataset.DatasetException;
/*      */ import jatools.engine.script.ReportContext;
/*      */ import jatools.engine.script.Script;
/*      */ import jatools.util.StringUtil;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ 
/*      */ public class ChartSourceUtil
/*      */ {
/*      */   public static void loadSource(Chart javaChart, ChartInterface chart, String[] stringValues)
/*      */   {
/*   51 */     DatasetReader reader = javaChart.getReader();
/*   52 */     ArrayList showData = javaChart.getPlotData();
/*   53 */     String labelField = javaChart.getLabelField();
/*   54 */     Dataset ds = javaChart.getDs();
/*      */ 
/*   56 */     loadSource(null, reader, ds, labelField, showData, javaChart
/*   57 */       .getPlotFrom(), chart, stringValues, null);
/*      */   }
/*      */ 
/*      */   public static boolean loadSource(Script script, DatasetReader reader, Dataset ds, String labelField, ArrayList plotData, int plotFrom, ChartInterface chart, String[] stringValues, String idField)
/*      */   {
/*   80 */     if (plotFrom == -2) {
/*   81 */       return loadSourceEx(script, idField, reader, ds, labelField, 
/*   82 */         plotData, plotFrom, chart, stringValues);
/*      */     }
/*   84 */     Dataset set = ds;
/*      */ 
/*   86 */     if (set == null) {
/*      */       try {
/*   88 */         set = reader.read(script, -1);
/*      */       }
/*      */       catch (DatasetException e1) {
/*   91 */         e1.printStackTrace();
/*      */ 
/*   93 */         return false;
/*      */       }
/*      */     }
/*      */ 
/*   97 */     if (plotFrom > 0) {
/*   98 */       Tip tip = null;
/*      */ 
/*  100 */       if ((plotData != null) && (plotData.size() > 0)) {
/*  101 */         PlotData tpl = (PlotData)plotData.get(0);
/*  102 */         tip = tpl.tooltip;
/*      */       }
/*      */ 
/*  105 */       plotData = new ArrayList();
/*      */ 
/*  107 */       for (int i = plotFrom; i < set.getColumnCount(); i++) {
/*  108 */         Tip t = null;
/*      */ 
/*  110 */         if (tip != null) {
/*      */           try {
/*  112 */             t = (Tip)tip.clone();
/*      */           }
/*      */           catch (CloneNotSupportedException e) {
/*  115 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */ 
/*  119 */         plotData.add(new PlotData(set.getColumnName(i), t));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  124 */     String[] datasetName2 = new String[plotData.size()];
/*      */ 
/*  132 */     double[][] data = new double[plotData.size()][set.getRowCount()];
/*      */ 
/*  134 */     Tip[][] tips = new Tip[plotData.size()][set.getRowCount()];
/*      */ 
/*  137 */     int datasetNum = chart.getNumDatasets();
/*      */ 
/*  140 */     for (int i = 0; i < datasetNum; i++) {
/*  141 */       chart.deleteDataset(chart.getDatasets()[0]);
/*      */     }
/*      */ 
/*  144 */     String[] labels = labelField.split(",");
/*  145 */     String[] datasetnames2 = new String[labels.length];
/*      */ 
/*  147 */     for (int i = 0; i < datasetnames2.length; i++) {
/*  148 */       String[] lab = labels[i].split(";");
/*      */ 
/*  150 */       if (lab.length > 1) {
/*  151 */         labels[i] = lab[0];
/*  152 */         datasetnames2[i] = lab[1];
/*      */       }
/*      */     }
/*      */ 
/*  156 */     String[][] datasetLabel2 = new String[labels.length][set.getRowCount()];
/*      */ 
/*  158 */     for (int i = 0; i < labels.length; i++)
/*      */     {
/*  160 */       int labelIndex = set.getColumnIndex(labels[i]);
/*      */ 
/*  164 */       for (int j = 0; j < set.getRowCount(); j++) {
/*  165 */         datasetLabel2[i][j] = String.valueOf(set.getValueAt(j, 
/*  166 */           labelIndex));
/*      */       }
/*      */     }
/*      */ 
/*  170 */     int rows = set.getRowCount();
/*      */ 
/*  172 */     if ((script instanceof ReportContext)) {
/*  173 */       NameSpace localNameSpace = script.createNameSpace();
/*  174 */       script.pushNameSpace(localNameSpace);
/*      */ 
/*  176 */       DatasetCursor rowsCursor = new DatasetCursor(set);
/*      */ 
/*  179 */       for (int i = 0; i < set.getColumnCount(); i++) {
/*  180 */         ScrollableField field = new ScrollableField(i, rowsCursor);
/*      */         try
/*      */         {
/*  183 */           localNameSpace
/*  184 */             .setLocalVariable(set.getColumnName(i), field);
/*      */         }
/*      */         catch (UtilEvalError e) {
/*  187 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */ 
/*  191 */       for (int i = 0; i < plotData.size(); i++)
/*      */       {
/*  193 */         datasetName2[i] = ((PlotData)plotData.get(i)).getField();
/*      */       }
/*  196 */       for (int i = 0; rowsCursor.hasNext();i < plotData.size())
/*      */       {
/*  197 */         rowsCursor.next();
/*      */ 
/*  199 */         i = 0; continue;
/*      */ 
/*  201 */         PlotData d = (PlotData)plotData.get(i);
/*      */         try
/*      */         {
/*  204 */           int c = set.getColumnIndex(d.field);
/*      */ 
/*  206 */           localNameSpace.setLocalVariable("$column", set
/*  207 */             .getColumn(c));
/*      */ 
/*  209 */           Object value = set.getValueAt(rowsCursor.getRow(), c);
/*  210 */           localNameSpace.setLocalVariable("$value", value);
/*      */         }
/*      */         catch (UtilEvalError e1) {
/*  213 */           e1.printStackTrace();
/*      */         }
/*      */ 
/*  216 */         Tip tip = d.getTooltip();
/*      */         try
/*      */         {
/*  219 */           if (tip != null)
/*  220 */             tip = (Tip)tip.clone();
/*      */         }
/*      */         catch (CloneNotSupportedException e)
/*      */         {
/*  224 */           e.printStackTrace();
/*      */         }
/*      */ 
/*  227 */         if (tip != null) {
/*  228 */           if ((tip.tip != null) && (tip.tip.indexOf("${") != -1)) {
/*  229 */             tip.tip = ((String)script.evalTemplate(tip.tip));
/*      */           }
/*      */ 
/*  232 */           if ((tip.url != null) && (tip.url.indexOf("${") != -1)) {
/*  233 */             tip.url = ((String)script.evalTemplate(tip.url));
/*  234 */             tip.url = StringUtil.encodeURI(tip.url);
/*      */           }
/*      */ 
/*  237 */           if (tip.url == null) {
/*  238 */             tip.target = null;
/*      */           }
/*      */ 
/*  241 */           tips[i][rowsCursor.getRow()] = tip;
/*      */         }
/*      */ 
/*  244 */         int columnIndex = set.getColumnIndex(datasetName2[i]
/*  245 */           .split(",")[0]);
/*      */ 
/*  249 */         Object value = set.getValueAt(rowsCursor.getRow(), 
/*  250 */           columnIndex);
/*      */ 
/*  252 */         if (stringValues == null) {
/*  253 */           if ((value instanceof Date)) {
/*  254 */             value = ((Date)value).getTime();
/*      */           }
/*      */ 
/*  257 */           if (value != null)
/*  258 */             data[i][rowsCursor.getRow()] = Double.valueOf(
/*  259 */               value).doubleValue();
/*      */         }
/*      */         else {
/*  262 */           data[i][rowsCursor.getRow()] = indexOf(stringValues, 
/*  263 */             value);
/*      */         }
/*  199 */         i++;
/*      */       }
/*      */ 
/*  268 */       script.popNameSpace();
/*      */     } else {
/*  270 */       for (int i = 0; i < plotData.size(); i++)
/*      */       {
/*  272 */         datasetName2[i] = ((PlotData)plotData.get(i)).getField();
/*      */ 
/*  275 */         int columnIndex = set
/*  276 */           .getColumnIndex(datasetName2[i].split(",")[0]);
/*      */ 
/*  279 */         for (int j = 0; j < set.getRowCount(); j++) {
/*  280 */           Object val = set.getValueAt(j, columnIndex);
/*      */ 
/*  282 */           if ((val instanceof Date))
/*  283 */             data[i][j] = ((Date)val).getTime();
/*      */           else {
/*  285 */             data[i][j] = Double.valueOf(String.valueOf(val))
/*  286 */               .doubleValue();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  292 */     return doLoad(chart, datasetName2, data, tips, datasetnames2, 
/*  293 */       datasetLabel2);
/*      */   }
/*      */ 
/*      */   public static boolean loadSourceEx(Script script, String idField, DatasetReader reader, Dataset ds, String labelField, ArrayList plotData, int plotFrom, ChartInterface chart, String[] stringValues)
/*      */   {
/*  300 */     Dataset set = ds;
/*      */ 
/*  302 */     if (set == null) {
/*      */       try {
/*  304 */         set = reader.read(script, -1);
/*      */       }
/*      */       catch (DatasetException e1) {
/*  307 */         e1.printStackTrace();
/*      */ 
/*  309 */         return false;
/*      */       }
/*      */     }
/*      */ 
/*  313 */     PlotData plt = (PlotData)plotData.get(0);
/*      */ 
/*  316 */     int idIndex = set.getColumnIndex(idField);
/*  317 */     int valIndex = set.getColumnIndex(plt.getField());
/*      */ 
/*  319 */     int labelIndex = set.getColumnIndex(labelField);
/*      */ 
/*  321 */     ArrayList fields = set.getUniqueList(labelField);
/*      */ 
/*  323 */     Collections.sort(fields);
/*      */ 
/*  325 */     Map fieldsMap = new HashMap();
/*  326 */     for (int i = 0; i < fields.size(); i++) {
/*  327 */       fieldsMap.put(fields.get(i), new Integer(i));
/*      */     }
/*      */ 
/*  330 */     ArrayList _data = new ArrayList();
/*  331 */     ArrayList _tips = plt.tooltip == null ? null : new ArrayList();
/*  332 */     ArrayList _datasetName = new ArrayList();
/*      */ 
/*  334 */     NameSpace localNameSpace = script.createNameSpace();
/*  335 */     script.pushNameSpace(localNameSpace);
/*      */ 
/*  337 */     DatasetCursor rowsCursor = new DatasetCursor(set);
/*  338 */     for (int i = 0; i < set.getColumnCount(); i++) {
/*  339 */       ScrollableField field = new ScrollableField(i, rowsCursor);
/*      */       try
/*      */       {
/*  342 */         localNameSpace.setLocalVariable(set.getColumnName(i), field);
/*      */       }
/*      */       catch (UtilEvalError e) {
/*  345 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */ 
/*  349 */     SimpleValue thisValue = new SimpleValue();
/*      */     try {
/*  351 */       localNameSpace.setLocalVariable("$value", thisValue);
/*      */     }
/*      */     catch (UtilEvalError e) {
/*  354 */       e.printStackTrace();
/*      */     }
/*      */ 
/*  357 */     if (rowsCursor.hasNext()) {
/*  358 */       rowsCursor.next();
/*  359 */       Object lastId = rowsCursor.getValue(idIndex);
/*      */ 
/*  361 */       double[] vals = new double[fields.size()];
/*  362 */       while (rowsCursor.hasNext()) {
/*  363 */         rowsCursor.next();
/*  364 */         Object id = rowsCursor.getValue(idIndex);
/*      */ 
/*  366 */         boolean eq = id == null ? false : lastId == null ? true : id.equals(lastId);
/*  367 */         if (!eq)
/*      */         {
/*  369 */           _data.add(vals);
/*      */ 
/*  372 */           if (plt.tooltip != null) {
/*  373 */             rowsCursor.save();
/*  374 */             int row = rowsCursor.getRow();
/*      */ 
/*  376 */             rowsCursor.setRow(row - 1);
/*      */ 
/*  378 */             Tip[] tips = new Tip[vals.length];
/*  379 */             _tips.add(tips);
/*      */ 
/*  381 */             for (int i = 0; i < tips.length; i++) {
/*  382 */               thisValue.setValue(new Double(vals[i]));
/*  383 */               Tip tip = null;
/*      */               try {
/*  385 */                 tip = (Tip)plt.tooltip.clone();
/*      */               }
/*      */               catch (CloneNotSupportedException e) {
/*  388 */                 e.printStackTrace();
/*      */               }
/*      */ 
/*  391 */               if (tip != null) {
/*  392 */                 if ((tip.tip != null) && 
/*  393 */                   (tip.tip.indexOf("${") != -1)) {
/*  394 */                   tip.tip = 
/*  395 */                     ((String)script
/*  395 */                     .evalTemplate(tip.tip));
/*      */                 }
/*      */ 
/*  398 */                 if ((tip.url != null) && 
/*  399 */                   (tip.url.indexOf("${") != -1)) {
/*  400 */                   tip.url = 
/*  401 */                     ((String)script
/*  401 */                     .evalTemplate(tip.url));
/*  402 */                   tip.url = StringUtil.encodeURI(tip.url);
/*      */                 }
/*      */ 
/*  405 */                 if (tip.url == null) {
/*  406 */                   tip.target = null;
/*      */                 }
/*      */ 
/*  409 */                 tips[i] = tip;
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*  414 */             rowsCursor.rollback();
/*      */           }
/*      */ 
/*  418 */           _datasetName.add(id);
/*      */ 
/*  420 */           lastId = id;
/*  421 */           vals = new double[fields.size()];
/*      */         }
/*      */ 
/*  425 */         int col = ((Integer)fieldsMap.get(rowsCursor
/*  426 */           .getValue(labelIndex))).intValue();
/*      */ 
/*  428 */         Object val = rowsCursor.getValue(valIndex);
/*  429 */         if ((val instanceof Number)) {
/*  430 */           vals[col] = ((Number)val).doubleValue();
/*      */         }
/*      */       }
/*  433 */       _data.add(vals);
/*  434 */       if (plt.tooltip != null) {
/*  435 */         rowsCursor.end();
/*      */ 
/*  437 */         Tip[] tips = new Tip[vals.length];
/*  438 */         _tips.add(tips);
/*      */ 
/*  440 */         for (int i = 0; i < tips.length; i++) {
/*  441 */           thisValue.setValue(new Double(vals[i]));
/*  442 */           Tip tip = null;
/*      */           try {
/*  444 */             tip = (Tip)plt.tooltip.clone();
/*      */           }
/*      */           catch (CloneNotSupportedException e) {
/*  447 */             e.printStackTrace();
/*      */           }
/*      */ 
/*  450 */           if (tip != null) {
/*  451 */             if ((tip.tip != null) && (tip.tip.indexOf("${") != -1)) {
/*  452 */               tip.tip = ((String)script.evalTemplate(tip.tip));
/*      */             }
/*      */ 
/*  455 */             if ((tip.url != null) && (tip.url.indexOf("${") != -1)) {
/*  456 */               tip.url = ((String)script.evalTemplate(tip.url));
/*  457 */               tip.url = StringUtil.encodeURI(tip.url);
/*      */             }
/*      */ 
/*  460 */             if (tip.url == null) {
/*  461 */               tip.target = null;
/*      */             }
/*      */ 
/*  464 */             tips[i] = tip;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  471 */       _datasetName.add(lastId);
/*      */     }
/*      */ 
/*  475 */     script.popNameSpace();
/*      */ 
/*  477 */     String[] datasetName = (String[])_datasetName.toArray(new String[0]);
/*  478 */     double[][] data = (double[][])_data.toArray(new double[0][]);
/*  479 */     Tip[][] tips = (Tip[][])null;
/*  480 */     if (_tips != null) {
/*  481 */       tips = (Tip[][])_tips.toArray(new Tip[0][]);
/*      */     }
/*  483 */     String[] labels = labelField.split(",");
/*  484 */     String[] datasetnames = new String[labels.length];
/*      */ 
/*  486 */     for (int i = 0; i < datasetnames.length; i++) {
/*  487 */       String[] lab = labels[i].split(";");
/*      */ 
/*  489 */       if (lab.length > 1) {
/*  490 */         labels[i] = lab[0];
/*  491 */         datasetnames[i] = lab[1];
/*      */       }
/*      */     }
/*      */ 
/*  495 */     String[][] datasetLabel = new String[labels.length][fields.size()];
/*      */ 
/*  497 */     for (int i = 0; i < labels.length; i++)
/*      */     {
/*  501 */       for (int j = 0; j < fields.size(); j++) {
/*  502 */         datasetLabel[i][j] = ((String)fields.get(j));
/*      */       }
/*      */     }
/*      */ 
/*  506 */     return doLoad(chart, datasetName, data, tips, datasetnames, 
/*  507 */       datasetLabel);
/*      */   }
/*      */ 
/*      */   private static boolean doLoad(ChartInterface chart, String[] datasetName, double[][] data, Tip[][] tips, String[] datasetnames2, String[][] datasetLabel)
/*      */   {
/*  513 */     int rows = data[0].length;
/*      */ 
/*  515 */     double[] xValue = new double[rows];
/*      */ 
/*  517 */     String[] nullLabels = new String[rows];
/*  518 */     Axis ax = (Axis)chart.getXAxis();
/*      */ 
/*  521 */     if ((ax instanceof DateAxis)) {
/*  522 */       SimpleDateFormat format = new SimpleDateFormat();
/*  523 */       format.applyPattern("yy-MM-dd HH:mm:ss");
/*      */ 
/*  525 */       for (int m = 0; m < datasetLabel[0].length; m++) {
/*      */         try {
/*  527 */           Date d = format.parse(datasetLabel[0][m]);
/*  528 */           xValue[m] = d.getTime();
/*      */         }
/*      */         catch (ParseException e)
/*      */         {
/*  532 */           e.printStackTrace();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  538 */       if ((chart instanceof HiLoCloseChart)) {
/*  539 */         if (data.length < 3) {
/*  540 */           return false;
/*      */         }
/*  542 */         String name = datasetnames2[0];
/*      */ 
/*  544 */         if (name == null) {
/*  545 */           name = "列1";
/*      */         }
/*      */ 
/*  548 */         ((HiLoCloseChart)chart).addDataset(name, xValue, data[0], 
/*  549 */           data[1], data[2]);
/*      */       }
/*  551 */       else if ((chart instanceof CandlestickChart)) {
/*  552 */         if (data.length < 4) {
/*  553 */           return false;
/*      */         }
/*  555 */         String name = datasetnames2[0];
/*      */ 
/*  557 */         if (name == null) {
/*  558 */           name = "列1";
/*      */         }
/*      */ 
/*  561 */         ((CandlestickChart)chart).addDataset(name, xValue, 
/*  562 */           data[0], data[1], data[2], data[3]);
/*      */       }
/*      */       else {
/*  565 */         for (int i = 0; i < datasetName.length; i++) {
/*  566 */           String[] columns = datasetName[i].split(",");
/*      */ 
/*  572 */           chart.addDataset(datasetName[i], xValue, data[i], 
/*  573 */             nullLabels);
/*      */         }
/*      */ 
/*  577 */         if (chart.getXAxis() != null) {
/*  578 */           chart.getXAxis().addLabels(datasetLabel[0]);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  587 */       if ((chart instanceof HiLoBarChart)) {
/*  588 */         if (data.length < 2) {
/*  589 */           return false;
/*      */         }
/*  591 */         String name = datasetnames2[0];
/*      */ 
/*  593 */         if (name == null) {
/*  594 */           name = "列1";
/*      */         }
/*      */ 
/*  597 */         ((HiLoBarChart)chart).addDataset(name, data[0], data[1], 
/*  598 */           nullLabels);
/*      */       } else {
/*  600 */         if ((chart instanceof HorizHiLoBarChart)) {
/*  601 */           if (data.length < 2) {
/*  602 */             return false;
/*      */           }
/*  604 */           for (int i = 0; i < rows; i++) {
/*  605 */             String name = datasetLabel[0][i];
/*      */ 
/*  607 */             ((HorizHiLoBarChart)chart).addDataset(name, 
/*  608 */               new double[] { data[1][i] }, 
/*  609 */               new double[] { data[0][i] }, new String[1]);
/*      */           }
/*      */ 
/*  612 */           if (chart.getXAxis() != null) {
/*  613 */             chart.getXAxis().addLabels(
/*  614 */               new String[] { datasetLabel[0][0] });
/*      */           }
/*      */ 
/*  617 */           return true;
/*      */         }
/*      */ 
/*  620 */         for (int i = 0; i < rows; i++) {
/*  621 */           xValue[i] = i;
/*      */         }
/*      */ 
/*  624 */         for (int i = 0; i < datasetName.length; i++) {
/*  625 */           if ((chart instanceof BubbleChart)) {
/*  626 */             chart.addDataset(datasetName[i], xValue, data[i]);
/*      */           } else {
/*  628 */             String[] columns = datasetName[i].split(",");
/*      */ 
/*  635 */             chart.addDataset(datasetName[i], xValue, data[i], 
/*  636 */               (chart instanceof PieChart) ? datasetLabel[0] : 
/*  637 */               nullLabels, tips != null ? tips[i] : 
/*  638 */               new Tip[data[i].length]);
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  644 */       if (chart.getXAxis() != null) {
/*  645 */         chart.getXAxis().addLabels(datasetLabel[0]);
/*      */       }
/*      */     }
/*      */ 
/*  649 */     return true;
/*      */   }
/*      */ 
/*      */   public static String[] asStringArray(Object[] x)
/*      */   {
/*  980 */     String[] strs = new String[x.length];
/*      */ 
/*  982 */     for (int i = 0; i < x.length; i++) {
/*  983 */       strs[i] = x[i];
/*      */     }
/*      */ 
/*  986 */     return strs;
/*      */   }
/*      */ 
/*      */   public static int indexOf(Object[] src, Object x)
/*      */   {
/* 1001 */     for (int i = 0; i < src.length; i++) {
/* 1002 */       boolean equal = src[i] == null ? false : x == null ? true : src[i].equals(x);
/*      */ 
/* 1004 */       if (equal) {
/* 1005 */         return i;
/*      */       }
/*      */     }
/*      */ 
/* 1009 */     return -1;
/*      */   }
/*      */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.ChartSourceUtil
 * JD-Core Version:    0.6.2
 */