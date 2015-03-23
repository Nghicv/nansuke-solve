package nghicv.com.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale.Category;
import java.util.Map;

import nghicv.com.nansukesolver.Variable;

public class Nansuke {
	private Matrix mMatrix;
	private String[] mListNumber;
	private  ArrayList<Integer> category=new ArrayList<Integer>();
	private HashMap<Integer, ArrayList<int []>> listTypeNumber;
	private HashMap<Integer, ArrayList<Variable[]>> listTypeCell;
	public Nansuke(Matrix mMatrix,String[] mListNumner){
		this.mMatrix=mMatrix;
		this.mListNumber=mListNumner;
		listTypeNumber=getTypeNumber();
		listTypeCell=getTypeCell();
	}
	private HashMap<Integer, ArrayList<int []>> getTypeNumber(){
		HashMap<Integer, ArrayList<int []>> mHashMap=new HashMap<Integer,ArrayList<int []>>();
		for(int i=0;i<mListNumber.length;i++){
			String s=mListNumber[i];
			Integer length=s.length();
			int arr[]=new int[length];
			for(int j=0;j<length;j++){
				int num=Integer.valueOf(s.substring(j, j+1));
				arr[j]=num;
				//System.out.println(num);
			}
			if(mHashMap.containsKey(length)){
				ArrayList<int[]> mList=mHashMap.get(length);
				mList.add(arr);
			}else{
				ArrayList<int []> mList=new ArrayList<int[]>();
				mList.add(arr);
				mHashMap.put(length, mList);
				category.add(length);
				
			}
			
		}
		Collections.sort(category);
		
		return mHashMap;
	}
	private HashMap<Integer, ArrayList<Variable[]>>   getTypeCell(){
		
		HashMap<Integer, ArrayList<Variable[]>> mHashMap=new HashMap<Integer, ArrayList<Variable[]>>();
		for(int i=0;i<category.size();i++){
			mHashMap.put(category.get(i), getListOfType(category.get(i)));
		}
		
		return mHashMap;
		
	}
	private ArrayList<Variable[]> getListOfType(int type){
		ArrayList<Variable[]> mList=new ArrayList<Variable[]>();
		for(int i=0;i<mMatrix.getRow();i++){
			int count=0;
			for(int j=0;j<mMatrix.getColumn();j++){
				if(count+1==type&&mMatrix.getColumn()-1==j&&mMatrix.get(i, j)==1){
					Variable [] arrVariable=new Variable[type];
					int digit=0;
					int flag=type;
					int k=j;
					while(flag!=0){
						Variable mVariable=new Variable(i, k);
						arrVariable[digit]=mVariable;
						digit++;
						k--;
						flag--;
						System.out.println("gia tri cua k="+k+" "+i);
					}
					mList.add(arrVariable);
				}else{
					if((mMatrix.get(i, j)==0&&count==type)){
						count=0;
						
						Variable [] arrVariable=new Variable[type];
						int digit=0;
						int flag=type;
						int k=j-1;
						while(flag!=0){
							Variable mVariable=new Variable(i, k);
							arrVariable[digit]=mVariable;
							digit++;
							k--;
							flag--;
							System.out.println("gia tri cua k="+k+" "+i);
						}
						mList.add(arrVariable);
					
					}else{
						if(mMatrix.get(i, j)==0){
							count=0;
						}else{
							count++;
						}
					}
				}
			}
		}
		for(int i=0;i<mMatrix.getColumn();i++){
			int count=0;
			for(int j=0;j<mMatrix.getRow();j++){
				if(count+1==type&&mMatrix.getRow()-1==j&&mMatrix.get(j, i)==1){
					Variable [] arrVariable=new Variable[type];
					int digit=0;
					int flag=type;
					int k=j;
					while(flag!=0){
						Variable mVariable=new Variable(k, i);
						arrVariable[digit]=mVariable;
						digit++;
						k--;
						flag--;
						System.out.println("gia tri cua k="+k+" "+i);
					}
					mList.add(arrVariable);
				}else{
					if((mMatrix.get(j, i)==0&&count==type)){
						count=0;
						
						Variable [] arrVariable=new Variable[type];
						int digit=0;
						int flag=type;
						int k=j-1;
						while(flag!=0){
							Variable mVariable=new Variable(k, i);
							arrVariable[digit]=mVariable;
							digit++;
							k--;
							flag--;
							System.out.println("gia tri theo doc cua k="+k+" "+i);
						}
						mList.add(arrVariable);
			
					}else{
						if(mMatrix.get(j, i)==0){
							count=0;
						}else{
							count++;
						}
					}
				}
				
			}
		}
		
		return mList;
	}
	public Matrix getMatrix(){
		return this.mMatrix;
	}
	public String[] getListNumber(){
		return this.mListNumber;
	}
	public ArrayList<Integer> getCategory(){
		return category;
	}
	public HashMap<Integer, ArrayList<int []>> getListTypeNumber(){
		return this.listTypeNumber;
	}
	public void printTypeCell(){
		for(int i=0;i<category.size();i++){
			ArrayList<Variable[]> listVariable=listTypeCell.get(category.get(i));
			System.out.println("so o loai "+category.get(i));
			for(int j=0;j<listVariable.size();j++){
				Variable[] mVariables=listVariable.get(j);
				String s="";
				for(int k=0;k<mVariables.length;k++){
					s=s+mVariables[k].toString();
				}
				System.out.println(s);
			}
		}
	}
	
}