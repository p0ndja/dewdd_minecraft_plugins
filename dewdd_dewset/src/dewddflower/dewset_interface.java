/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddflower;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import dewddtran.tr;
import li.Constant_Protect;
import li.LXRXLZRZType;

public abstract class dewset_interface extends dewsetdatabase {

	public  int selectmax = 29;

	public  int dewsignlimit = 200;

	public  static int FWMaxPlayer = 20;

	public  String pmaindelete = "dewdd.main.delete";
	public  String pmaindewbuychangehost = "dewdd.main.dewbuy.changehost";
	public  String pmaindewbuydelete = "dewdd.main.dewbuydelete";
	public  String pmaindewbuymodifymember = "dewdd.main.dewbuy.modifymember";

	public  String pmaindewbuynotcount = "dewdd.main.dewbuy.notcount";

	public  String pmaindewbuyreplace = "dewdd.main.dewbuy.replace";

	public  String pmaindewseteverywhere = "dewdd.main.dewset.everywhere";

	public  String pmaininfinite = "dewdd.main.dewset.infinite";

	public  String pmainoveride = "dewdd.main.overide";

	public  String pmainplaceblocknoprotect = "dewdd.main.placeblock.noprotect";

	public  String pmainalsocopyinventoryblockwhenyouusedewset = "dewdd.main.dewset.copyinventoryblocks";

	public  String puseitem55 = "dewdd.main.dewset.item55";

	

}
