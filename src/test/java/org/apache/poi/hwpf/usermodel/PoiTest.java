package org.apache.poi.hwpf.usermodel;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class PoiTest {

	@Autowired
	com.swinginwind.iknowu.dao.TablemaintenanceMapper tbMapper;

	@Test
	public void test() {
		try {
			List<Map<String, Object>> tableList = new ArrayList<Map<String, Object>>();
			FileInputStream in = new FileInputStream("C:\\Users\\Administrator\\Desktop\\DD2015-04城市地质调查数据库结构规范.doc");
			POIFSFileSystem pfs = new POIFSFileSystem(in);
			HWPFDocument hwpf = new HWPFDocument(pfs);
			Range range = hwpf.getRange();

			TableIterator it = new TableIterator(range);
			List<com.swinginwind.iknowu.model.Field> fieldList = null;
			Map<String, Object> map = null, fieldNameMap = null;
			String preTableName = "";
			int index = 0, tableIndex = 0, errorCount = 0;
			while (it.hasNext()) {

				String tableName = range.getParagraph(it.get_index() - 2).text().trim();
				if (StringUtils.isEmpty(tableName))
					tableName = range.getParagraph(it.get_index() - 1).text().trim();
				if (StringUtils.isEmpty(tableName))
					tableName = range.getParagraph(it.get_index() - 3).text().trim();
				// System.out.println(tableName);
				Table tb = (Table) it.next();
				String keyName = tb.getRow(0).getCell(1).text().trim();
				// System.out.println(keyName);
				// System.out.println(tb.getRow(1).numCells());
				if (!(tb.numRows() > 1) || tb.getRow(1).numCells() < 3
						|| (!keyName.equals("数据项名称") && !tb.getRow(1).getCell(2).text().trim().equals("PKIAA")))
					continue;

				if (tb.getRow(1).getCell(0).text().trim().equals("1")) {
					preTableName = tableName;
					tableIndex++;
					map = new HashMap<String, Object>();
					fieldList = new ArrayList<com.swinginwind.iknowu.model.Field>();
					map.put("fields", fieldList);
					map.put("primaryKey", "PKIAA");
					map.put("codename", tableName);
					tableList.add(map);
					System.out.println(tableName);
					fieldNameMap = new HashMap<String, Object>();
				}

				for (int i = 1; i < tb.numRows(); i++) {
					// System.out.println("Numrows :"+tb.numRows());
					com.swinginwind.iknowu.model.Field field = new com.swinginwind.iknowu.model.Field();
					TableRow tr = tb.getRow(i);
					if (tr.numCells() > 5) {
						Integer[] intA = new Integer[10];
						for (int k = 0; k < 10; k++) {
							intA[k] = k;
						}

						for (int k = 0; k < tr.numCells(); k++) {
							if (tr.getCell(k).text().trim().equals("")) {
								for (int j = 0; j < intA.length; j++) {
									if (intA[j] >= k)
										intA[j]++;
								}
							}
						}
						field.setName(tr.getCell(intA[2]).text().trim());
						if (fieldNameMap.containsKey(field.getName())) {
							field.setName(field.getName() + "1");
						}
						fieldNameMap.put(field.getName(), 1);
						field.setTitle(tr.getCell(intA[1]).text().trim());
						String type = tr.getCell(intA[4]).text().trim().toUpperCase();
						if (type.startsWith("C")) {
							field.setType("varchar");
							field.setLength(Integer.parseInt(type.substring(1)));
						} else if (type.startsWith("N") || type.startsWith("L")) {
							field.setType("decimal");
							field.setLength(Integer.parseInt(type.substring(1)));
						} else if (type.equals("DATE")) {
							field.setType("datetime");
						} else if (type.equals("BLOB")) {
							field.setType("blob");
						} else if (type.startsWith("F") || type.startsWith("D")) {
							field.setType("decimal");
							String str = type.substring(1);
							String[] strA = str.split("\\.");
							field.setLength(Integer.parseInt(strA[0]));
							field.setDecimalCount(Integer.parseInt(strA[1]));
						}

						field.setIsEmpty(tr.getCell(intA[5]).text().trim().equals("M") ? 0 : 1);
						fieldList.add(field);
					}

				}
				if (!fieldNameMap.containsKey("PKIAA"))
					map.put("primaryKey", fieldList.get(0).getName());

			}
			// System.out.println(s.toString());
			Map<String, Boolean> isInMap = new HashMap<String, Boolean>();
			for (int i = 10; i < 367; i++)
				isInMap.put("表" + i, false);
			String patt = "表\\d+";
			Pattern r1 = Pattern.compile(patt);
			String patt1 = "（\\w+）|\\(\\w+\\)";
			Pattern r = Pattern.compile(patt1);
			for (Map<String, Object> table : tableList) {
				// tbMapper.inserttable(table);

				if (!table.get("codename").toString().startsWith("表")) {
					System.out.println(table.get("codename"));
				}
				Matcher m1 = r1.matcher(table.get("codename").toString());

				if (m1.find())
					isInMap.put(m1.group(0), true);

				Matcher m = r.matcher(table.get("codename").toString());
				if (m.find()) {
					table.put("tableCode", m.group(0).substring(1, m.group(0).length() - 1));
					table.put("tableName", table.get("codename").toString().substring(m.end()));
				}
				// System.out.println(table);
				for (com.swinginwind.iknowu.model.Field field : (List<com.swinginwind.iknowu.model.Field>) table
						.get("fields")) {
					if (StringUtils.isEmpty(field.getName()) || StringUtils.isEmpty(field.getType()))
						System.out.println(table.get("tableCode").toString() + "-" + field.toString());
				}
				try {
					// tbMapper.inserttable(table);
				} catch (Exception e) {
					System.out.println(table);
					System.out.println(e.getMessage());
					errorCount++;
				}

			}
			System.out.println(errorCount);

			for (String key : isInMap.keySet()) {
				if (!isInMap.get(key)) {
					System.out.println(key);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		String patt = "\\[\\w+\\]";
		Pattern r = Pattern.compile(patt);
		String line = "1111[ZYFKFG01]1111111[CCCCC]";
		Matcher m = r.matcher(line);
		int i = 0;
		while (m.find()) {
			// group(0)或group()将会返回整个匹配的字符串（完全匹配）；group(i)则会返回与分组i匹配的字符 //
			// 这个例子只有一个分组
			System.out.println(patt + " matches \"" + m.group(0) + "\" in \"" + line + "\"");
			System.out.println("start:" + m.start() + " end:" + m.end());
		}

	}

	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */ public static class TableIterator
	/*    */ {
		/*    */ Range _range;
		/*    */ int _index;

		public int get_index() {
			return _index;
		}

		public void set_index(int _index) {
			this._index = _index;
		}

		/*    */ int _levelNum;

		/*    */
		/*    */ TableIterator(Range range, int levelNum)
		/*    */ {
			/* 29 */ this._range = range;
			/* 30 */ this._index = 0;
			/* 31 */ this._levelNum = levelNum;
			/*    */ }

		/*    */
		/*    */ public TableIterator(Range range)
		/*    */ {
			/* 36 */ this(range, 1);
			/*    */ }

		/*    */
		/*    */
		/*    */ public boolean hasNext()
		/*    */ {
			/* 42 */ int numParagraphs = this._range.numParagraphs();
			/* 43 */ for (; this._index < numParagraphs; this._index += 1)
			/*    */ {
				/* 45 */ Paragraph paragraph = this._range.getParagraph(this._index);
				/* 46 */ if ((paragraph.isInTable()) && (paragraph.getTableLevel() == this._levelNum))
				/*    */ {
					/* 48 */ return true;
					/*    */ }
				/*    */ }
			/* 51 */ return false;
			/*    */ }

		/*    */
		/*    */ public Table next()
		/*    */ {
			/* 56 */ int numParagraphs = this._range.numParagraphs();
			/* 57 */ int startIndex = this._index;
			/* 58 */ int endIndex = this._index;
			/*    */
			/* 60 */ for (; this._index < numParagraphs; this._index += 1)
			/*    */ {
				/* 62 */ Paragraph paragraph = this._range.getParagraph(this._index);
				/* 63 */ if ((paragraph.isInTable()) && (paragraph.getTableLevel() >= this._levelNum))/*    */ continue;
				/* 65 */ endIndex = this._index;
				/* 66 */ break;
				/*    */ }
			/*    */
			/* 69 */ return new Table(this._range.getParagraph(startIndex).getStartOffset(),
					this._range.getParagraph(endIndex - 1).getEndOffset(), this._range, this._levelNum);
			/*    */ }
		/*    */ }

}
