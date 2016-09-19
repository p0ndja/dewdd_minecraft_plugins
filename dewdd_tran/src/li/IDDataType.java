package li;

import java.util.ArrayList;

public class IDDataType {
	int id;
	byte data;
	
	
	public static boolean  isThisItemOnTheList(ArrayList<IDDataType> item, int id , byte data) {
		for (int i = 0 ; i < item.size() ; i ++ ) {
			IDDataType ex = item.get(i);
			if (ex.id == id || ex.id == -29) {
				
				if (ex.data == data || ex.data == -29) {
					return true;
				}
			}
			
		}
		
		return false;
	}
		
	
	public static ArrayList<IDDataType> longArgumentToListIDDataType(String longString) {
		String listBlock[] = longString.split(",");
		
		ArrayList<IDDataType> item = new ArrayList<IDDataType>();
		
		for (int kb = 0 ; kb < listBlock.length ; kb ++ ) {
			
			// id:data
			String iddata[] = listBlock[kb].split(":");
			IDDataType newer = new IDDataType();
			
			if (iddata.length == 1) {
				newer.id  = Integer.parseInt(iddata[0]);
				newer.data = -29;
				
			}
			else if (iddata.length == 2) {
				newer.id  = Integer.parseInt(iddata[0]);
				newer.data = Byte.parseByte(iddata[1]);
			}
			
			item.add(newer);
			
		}
		
		return item;
		
		
	}
}
