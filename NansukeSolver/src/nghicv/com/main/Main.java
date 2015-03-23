package nghicv.com.main;

import java.awt.Color;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import nghicv.com.util.FileUtil;
import nghicv.com.util.ListQuestions;
import nghicv.com.util.Nansuke;

import org.sat4j.minisat.*;
public class Main {
	public static void main(String [] args){
		
		
		///////
		Nansuke mNansukeQuestions=ListQuestions.arrNansukeQuestions[0];
		HashMap<Integer,ArrayList<int []>> mHashMap=mNansukeQuestions.getListTypeNumber();
		ArrayList<Integer> category=mNansukeQuestions.getCategory();
		for(int i=0;i<category.size();i++){
			int item =category.get(i);
			System.out.println(item);
			ArrayList<int []> listSequenceNum=mHashMap.get(item);
			for(int j=0;j<listSequenceNum.size();j++){
				int []arr=listSequenceNum.get(j);
				System.out.println(arr.length);
				for(int k=0;k<arr.length;k++){
					
					System.out.print(arr[k]+" ");
				}
				System.out.println();
			}
			System.out.println();
		}
		mNansukeQuestions.printTypeCell();
		
		
	}
}
