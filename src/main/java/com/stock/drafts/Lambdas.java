package com.stock.drafts;

import java.util.ArrayList;
import java.util.List;

public class Lambdas {

	public static void main(String[] args) {

        List<String> symbolList = getSymbolList();

//        String sls = symbolList.stream().reduce("",(s,c) -> s + "," + c);
        System.out.println(symbolList.stream().reduce("",(s,c) -> s + "," + c));
	}
	
	
	
	static List<String> getSymbolList() {
		List<String> r = new ArrayList<>();
		
		r.add("BMO.TO");
		r.add("BB.TO");
		r.add("TD.TO");
		r.add("RY.TO");
		r.add("ENB.TO");
		r.add("OVV.TO");
		
		return r;
	}

}
