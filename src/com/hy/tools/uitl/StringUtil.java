package com.hy.tools.uitl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * 
 * <pre>
 * 字符串解析辅助类
 * </pre>
 * 
 * @author 黄云
 * 2015年11月10日
 */
public class StringUtil {
	/**
	 * 检测字符串是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	/**
	 * 检测字符串是否不为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(String s) {
		return s != null && s.trim().length() > 0;
	}

	/**
	 * 首字母转大写
	 * @param str
	 * @return
	 */
	public static String upFirstChar(String str) {
		if (str != null && !str.equals("")) {
			char[] ch = str.toCharArray();
			if (ch[0] >= 'a' && ch[0] <= 'z') {
				ch[0] = (char) (ch[0] - 32);
			}
			return new String(ch);
		}
		return str;
	}
	
	/**
	 * 首字母转小写
	 * @param str
	 * @return
	 */
	public static String lowFirstChar(String str) {
		if (str != null && !str.equals("")) {
			char[] ch = str.toCharArray();
			if (ch[0] >= 'A' && ch[0] <= 'Z') {
				ch[0] = (char) (ch[0] + 32);
			}
			return new String(ch);
		}
		return str;
	}

	/**
	 * 截取string+...
	 * @param s
	 * @param size
	 * @return
	 */
	public static String cutString(String s, int size) {
		if (s == null)
			return "";
		int strsize = s.length();
		if (strsize <= size)
			return s;
		return s.substring(0, size) + "...";
	}

	public static String getMarkString(String Str, String LeftMark,
			String RightMark) {
		String tmpreturn = Str;
		if (tmpreturn.indexOf(LeftMark, 0) != -1) {
			int LeftMarkPoint = tmpreturn.indexOf(LeftMark, 0)
					+ LeftMark.length();
			int RightMarkPoint = tmpreturn.indexOf(RightMark, LeftMarkPoint);
			if (RightMarkPoint != -1) {
				int ValueLength = RightMarkPoint;
				tmpreturn = tmpreturn.substring(LeftMarkPoint, ValueLength);
			}
		}
		if (tmpreturn == Str) {
			return null;
		} else {
			return tmpreturn;
		}

	}

	public static ArrayList<String> getMarkStringList(String Str,
			String LeftMark, String RightMark) {
		ArrayList<String> al = new ArrayList<String>();
		String tempstr = new String(Str);
		String tempv = "";
		while (getMarkString(tempstr, LeftMark, RightMark) != null) {
			tempv = getMarkString(tempstr, LeftMark, RightMark);
			if (tempv == null)
				break;
			al.add(new String(tempv));
			// System.out.println(Matcher.quoteReplacement(LeftMark+tempv+RightMark));

			tempstr = Pattern
					.compile(LeftMark + tempv + RightMark, Pattern.LITERAL)
					.matcher(tempstr).replaceFirst("");
		}
		return al;
	}

	public static String filterMark(String strText, String sMark, String eMark) {
		while (getMarkString(strText, sMark, eMark) != null) {
			String Markl = getMarkString(strText, sMark, eMark);
			strText = Pattern.compile(sMark + Markl + eMark, Pattern.LITERAL)
					.matcher(strText).replaceAll("");
		}
		return strText;
	}

	public static String repalceAll(String strText, String source, String target) {
		return Pattern.compile(source, Pattern.LITERAL).matcher(strText)
				.replaceAll(target);
	}

	public static String repalceFirst(String strText, String source,
			String target) {
		return Pattern.compile(source, Pattern.LITERAL).matcher(strText)
				.replaceFirst(target);
	}

	/**
	 * 读取文件
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String filePath) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream is = new FileInputStream(new File(filePath));
			byte[] buf = new byte[1024];
			int len;
			while ((len = is.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			is.close();

			return out.toString("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 读文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		String ret = readFile(is);
		is.close();
		return ret;
	}

	/**
	 * 读文件
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String readFile(InputStream is) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		byte[] buf = new byte[1024];
		int len;
		while ((len = is.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		out.close();
		byte[] lens = out.toByteArray();
		String result = new String(lens);
		return result;
	}

	/**
	 * 从classpath读取文件
	 * 
	 * @param thisObj 调用本方法的当前对象，需要从当前对象中获得ClassLoader，所以必须将对象传进来
	 * @param className
	 * @return
	 */
	public static String readFileFromClassPath(Object thisObj, String className) {
		InputStream is = thisObj.getClass().getClassLoader().getResourceAsStream(className);
		if (is != null) {
			try {
				return readFile(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 写文件
	 */
	public static void write(String path, String content) {
		try {
			File dir = new File(new File(path).getParent());
			if (!dir.exists())
				dir.mkdirs();
			File file = new File(path);
			if (file.exists())
				file.delete();
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			out.write(new StringBuffer(content).toString().getBytes("UTF-8"));
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 文件拷贝
	 * @param sourcePath
	 * @param path
	 */
	public static void copyFile(String sourcePath, String path){
		String template = StringUtil.readFile(sourcePath);
		StringUtil.write(path, template);//写文件到项目路径
	}
	
	// java调用cmd命令行方法
	/**
	 * 运行可执行文件
	 *
	 * @param cmd
	 * @return String
	 */
	public static synchronized boolean executeCmdFlash(String cmd) {
		StringBuffer stdout = new StringBuffer();
		try {
			final Process process = Runtime.getRuntime().exec(cmd);
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					process.destroy();
				}
			});
			InputStreamReader inputstreamreader = new InputStreamReader(
					process.getInputStream());
			char c = (char) inputstreamreader.read();
			if (c != '\uFFFF')
				stdout.append(c);
			while (c != '\uFFFF') {
				if (!inputstreamreader.ready()) {
					System.out.println(stdout);
					try {
						process.exitValue();
						break;
					} catch (IllegalThreadStateException _ex) {
						try {
							Thread.sleep(100L);
						} catch (InterruptedException _ex2) {
						}
					}
				} else {
					c = (char) inputstreamreader.read();
					stdout.append(c);
				}
			}
			try {
				inputstreamreader.close();
			} catch (IOException ioexception2) {
				System.err.println("RunCmd : Error closing InputStream "
						+ ioexception2);
				return false;
			}
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
