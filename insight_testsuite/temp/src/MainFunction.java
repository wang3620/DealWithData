package Daniel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

public class MainFunction {

	private static BufferedReader bufferedReader;
	private static PrintWriter printWriter;
	private static Hashtable<String, ArrayList<Integer>> zipTable;
	private static TreeMap<String, ArrayList<Integer>> dateTable;
	public static void main(String[] args) throws IOException {
		bufferedReader = new BufferedReader(new FileReader("./input/itcont.txt"));
		printWriter = new PrintWriter("./output/medianvals_by_zip.txt");
		zipTable = new Hashtable<>();
		dateTable = new TreeMap<String, ArrayList<Integer>>(new Comparator<String>(){
			@Override
			public int compare(String s1, String s2) {
				s1 = s1.substring(0,s1.length()-8)+s1.substring(s1.length()-4, s1.length())+s1.substring(s1.length()-8, s1.length()-4);
				s2 = s2.substring(0,s2.length()-8)+s2.substring(s2.length()-4, s2.length())+s2.substring(s2.length()-8, s2.length()-4);
				return s1.compareTo(s2);
			}
		});
		String readThisLine;
		while ((readThisLine = bufferedReader.readLine()) != null){
			String writeThisLine = "";
			String readThisLineArray[] = readThisLine.split("\\|");
			if (!readThisLineArray[15].equals(""))
				continue;
			if (readThisLineArray[10].length()<5)
				continue;
			writeThisLine += readThisLineArray[0]+"|"+readThisLineArray[10].substring(0, 5)+"|";
			ArrayList<Integer> currentList; // the value of the key
			String zipKey = readThisLineArray[0]+"|"+readThisLineArray[10].substring(0, 5);
			String dateKey = readThisLineArray[0]+"|"+readThisLineArray[13];
			if ((currentList = zipTable.get(zipKey)) == null){
				ArrayList<Integer> arrayList = new ArrayList<>();
				arrayList.add(Integer.parseInt(readThisLineArray[14]));
				zipTable.put(zipKey, arrayList);
				writeThisLine += readThisLineArray[14]+"|1|"+readThisLineArray[14];
			}
			else {
				// currentList.add(Integer.parseInt(readThisLineArray[14]));
				// Collections.sort(currentList);
				add(currentList, Integer.parseInt(readThisLineArray[14]));
				zipTable.put(zipKey, currentList);
				String Median = Integer.toString(findMedian(currentList));
				writeThisLine += Median + "|" +  Integer.toString(currentList.size()) + "|" + Integer.toString(currentList.stream().mapToInt(value -> value).sum());
			}
			if ((currentList = dateTable.get(dateKey)) == null) {
				ArrayList<Integer> arrayList = new ArrayList<>();
				arrayList.add(Integer.parseInt(readThisLineArray[14]));
				dateTable.put(dateKey, arrayList);
			}
			else {
				currentList.add(Integer.parseInt(readThisLineArray[14]));
				dateTable.put(dateKey, currentList);
			}
			printWriter.println(writeThisLine);
		}
		printWriter.close();
		printWriter = new PrintWriter("./output/medianvals_by_date.txt");
		for (Map.Entry<String, ArrayList<Integer>> entry: dateTable.entrySet()) {
			String writeThisLine = "";
			String dateKey = entry.getKey();
			ArrayList<Integer> arrayList = entry.getValue();
			Collections.sort(arrayList);
			String Median = Integer.toString(findMedian(arrayList));
			writeThisLine += dateKey + "|" + Median + "|" +  Integer.toString(arrayList.size()) + "|" + Integer.toString(arrayList.stream().mapToInt(value -> value).sum());
			printWriter.println(writeThisLine);
		}
		printWriter.close();
		bufferedReader.close();
	}
	private static void add (ArrayList<Integer> currentList, Integer toInsert){
		int index = 0;
		for(;index<currentList.size() && toInsert>currentList.get(index);index++){
			
		}
		currentList.add(index, toInsert);
	}
	private static Integer findMedian(ArrayList<Integer> currentList) {
		// TODO Auto-generated method stub
		int Size = currentList.size();
		if (currentList.size()%2 == 1) {
			return currentList.get((Size - 1)/2);
		}
		else {
			float tempResult = (float) ((currentList.get(Size/2)+currentList.get(Size/2-1))/2.0);
			return (int) Math.ceil(tempResult);
		}
	}
}

