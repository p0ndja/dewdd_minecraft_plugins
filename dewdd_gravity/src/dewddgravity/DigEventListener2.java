/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddgravity;

import java.util.LinkedList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

class Gravity implements Runnable {
	public Boolean canc = false;
	public static long delay = 20;
	public static int r = 1;
	public static int search = 3;
	public static int stick = 4;

	private Block block;
	private Block middle;

	private Player player = null;
	private int curDelay = 40;

	public Gravity(Block block, Player player, Block middle,int curDelay) {
		this.block = block;
		this.middle = middle;
		this.player = player;
		this.curDelay = curDelay;
		Random rnd = new Random();

		//Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, this, rnd.nextInt(100) + 20);
	
		Bukkit.getScheduler().scheduleSyncDelayedTask(DigEventListener2.ac, this, curDelay );
		
	}

	class RootType {
		boolean foundRoot = false;
		int amount = 0;
		
		
	}

	public void isThisBlockHasRoot(Block cur, Block start, RootType root) {
		Block b2 = null;

		root.amount ++;
		/*if (root.amount > 10) {
			root.amount -- ;
			return;
		}*/
		
		for (int y = -r; y <= -1 && cur.getY() + y >= 0; y++) {
			for (int x = -r; x <= r; x++) {
				for (int z = -r; z <= r; z++) {
					if (root.foundRoot == true) {
						return;
					}
					if (x == 0 && y == 0 && z == 0) {
						continue;
					}

					
					b2 = cur.getRelative(x, y, z);
					//b2.setType(Material.LOG);
					
					double xxx = Math.abs(b2.getX() - start.getX());
					double zzz = Math.abs(b2.getZ() - start.getZ());
					
					
					double dis = (xxx*xxx) + (zzz*xxx);
					dis = Math.pow(dis, 0.5);
					
					if (dis > 5) {
						continue;
					}
					
					/*if (b2.getLocation().distance(start.getLocation()) > 5) {
						continue;
					}*/

					if (b2.getType() == Material.AIR) {
						continue;
					}
					
					if (b2.getType().isSolid() == false) {
						continue;
					}

					if (b2.getType() != Material.AIR) {
						if (b2.getY() > 0) {
							// more research !
					
							isThisBlockHasRoot(b2, start, root);
						} else {
							root.foundRoot = true;
							return;
						}
					}

				}
			}
		}
		
		
		root.amount --;
	}

	@Override
	public void run() {

		// check it's has near block or not

		int r = Gravity.r;

		Block b2 = null;

		if (block.getY() == 0) {
			return;
		}
		if (block.getType() == Material.AIR) {
			return;
		}
		if (block.getRelative(0, -1, 0).getType() != Material.AIR) {
			return;
		}

		RootType root = new RootType();
		root.foundRoot = false;

		
			for (int x = -stick; x <= stick; x++) {
				for (int z = -stick; z <= stick; z++) {
					if (root.foundRoot == true) {
						return;
					}
					
					
					b2 = block.getRelative(x, 0, z);
					isThisBlockHasRoot(b2, block, root);
				}
			}
		
		

		if (root.foundRoot == false) {

			block.getWorld().spawnFallingBlock(block.getLocation(), block.getType(), block.getData());
			block.setTypeId(0, false);
			
			int counter = 0 ;

			for (int x = -search; x <= search; x++) {
				for (int y = -search; y <= search; y++) {
					for (int z = -search; z <= search; z++) {
						counter ++;
						b2 = block.getRelative(x, y, z);
						
						if (b2.getType() == Material.AIR) {
							continue;
						}
						
						Gravity noop = new Gravity(b2, player, block, counter * 30 );

					}
				}

			}

		} // sync
	}
}

public class DigEventListener2 implements Listener {

	public static JavaPlugin ac = null;

	private Random rnd = new Random();

	// BlockPlaceEvent

	@EventHandler
	public void eventja(BlockPhysicsEvent e) {

		Block block = e.getBlock();
		Block b2 = null;

		int r = Gravity.r;
		int counter = 0;
		
		for (int x = -r; x <= r; x++) {
			for (int y = -r; y <= r; y++) {
				for (int z = -r; z <= r; z++) {
					counter ++;
					b2 = block.getRelative(x, y, z);
					if (b2.getType() == Material.AIR) {
						continue;
					}
					Gravity noop = new Gravity(b2, null, block, counter * 25);

				}
			}

		}

	}

	@EventHandler
	public void eventja(BlockBreakEvent e) {

		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName()))
			return;

		Block block = e.getBlock();
		Block b2 = null;

		int r = Gravity.search;
		int counter = 0;
		
		for (int x = -r; x <= r; x++) {
			for (int y = -r; y <= r; y++) {
				for (int z = -r; z <= r; z++) {
					counter ++ ;
					
					b2 = block.getRelative(x, y, z);
					
					if (b2.getType() == Material.AIR) {
						continue;
					}
					
					Gravity noop = new Gravity(b2, e.getPlayer(), block,counter * 26);

				}
			}

		}

	}
	
	@EventHandler
	public void eventja(AsyncPlayerChatEvent e) {
		if (e.getMessage().equalsIgnoreCase("gravity")) {
			e.getPlayer().sendMessage("0.1");
		}

		}

	@EventHandler
	public void eventja(BlockPlaceEvent e) {

		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName()))
			return;

		Block block = e.getBlock();
		Block b2 = null;

		int r = Gravity.search;
		int counter = 0;
		for (int x = -r; x <= r; x++) {
			for (int y = -r; y <= r; y++) {
				for (int z = -r; z <= r; z++) {
					counter ++;
					b2 = block.getRelative(x, y, z);
					
					if (b2.getType() == Material.AIR) {
						continue;
					}
					
					Gravity noop = new Gravity(b2, e.getPlayer(), block,counter * 27);

				}
			}

		}

	}

	// PlayerDeathEvent

	// PlayerInteractEvent

} // class