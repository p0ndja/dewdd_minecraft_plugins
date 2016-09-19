/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddft;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import dewddflower.dewset;
import dewddtran.tr;

public class DigEventListener2 implements Listener {

	class autosell implements Runnable {
		private Block	b;

		public autosell(Block b) {
			this.b = b;

		}

		@Override
		public void run() {

			Block b2 = null;
			// search near block

			Block b3 = null;

			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					b2 = b.getRelative(x, 0, z);

					if (b2.getTypeId() == 54 || b2.getTypeId() == 146) {
						for (int x2 = -1; x2 <= 1; x2++) {
							for (int z2 = -1; z2 <= 1; z2++) {
								b3 = b2.getRelative(x2, 0, z2);
								if (b3.getTypeId() == 63
										|| b3.getTypeId() == 68) {
									Sign sign = (Sign) b3.getState();

									if (sign.getLine(0).equalsIgnoreCase(
											"[autosell]")) {
										double lowest = 100000;
										double temp = 0;

										Player pla = Bukkit.getPlayer(sign
												.getLine(1));

										if (pla == null) {

											// searh near player

											Player lop = null;

											for (Player pla2 : Bukkit
													.getOnlinePlayers())
												if (pla2.getWorld()
														.getName()
														.equalsIgnoreCase(
																b2.getWorld()
																		.getName())) {
													temp = pla2
															.getLocation()
															.distance(
																	b2.getLocation());

													if (temp <= lowest) {
														lop = pla2;
														lowest = temp;
													}
												}

											if (lop == null)
												return;
											else {
												pla = lop;
											}

										}

										if (pla == null) return;

										// xxx

										Chest c = (Chest) b2.getState();
										for (ItemStack ic : c.getInventory()
												.getContents()) {
											if (ic == null) {
												continue;
											}

											double price = DigEventListener2
													.getprice(ic.getTypeId());

											if (price == -1) {
												continue;
											}

											double price2 = 0;
											pla.sendMessage("price : " + price);

											price2 = price * ic.getAmount();

											try {
												Economy.add(pla.getName(),
														price2);
											}
											catch (UserDoesNotExistException
													| NoLoanPermittedException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}

											pla.sendMessage(pla.getName()
													+ " auto sell ...  "
													+ ic.getTypeId() + ":"
													+ ic.getData()
													+ " amount = "
													+ ic.getAmount()
													+ " price = " + price2);
											c.getInventory().remove(ic);
											c.update();

										}

										// sell item on that chest
									} // auto sell
								}
							}
						}
					}

				}
			}
		}
	}

	class autoshoot implements Runnable {

		private int			second;
		private EntityType	ent;
		private Player		p;
		private int			mode;

		public autoshoot(int second, EntityType ent, Player p, int mode) {
			this.second = second;
			this.ent = ent;
			this.p = p;
			this.mode = mode;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this,
					rnd.nextInt(40));
		}

		@Override
		public void run() {
			second--;

			if (second <= 0) return;

			boolean shootcomplete = false;

			for (Entity en : p.getWorld().getEntities()) {
				if (en == null) {
					continue;
				}
				if (en.getType() == ent) if (en instanceof LivingEntity) {
					LivingEntity er = (LivingEntity) en;
					switch (mode) {
					case 1:
						er.shootArrow();

						break;
					case 2:
						er.throwEgg();

						break;
					case 3:
						er.throwSnowball();

						break;

					case 4:
						er.teleport(er.getEyeLocation());

						break;

					case 5:
						er.getWorld().strikeLightning(er.getEyeLocation());

						break;

					}

					shootcomplete = true;
				}
			}

			if (shootcomplete == true) {
				new autoshoot(second, ent, p, mode);
			}
		}
	}

	class delay extends Thread {

		@Override
		public void run() {
			while (ac == null) {
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				dprint.r.printC("ft waiting ac != null");

			}

			reloadpl();
		}
	}

	class delay_dewset extends Thread {

		@Override
		public void run() {

			try {
				// dew = null;

				int i = 0;
				while (ac == null) {

					i++;
					Thread.sleep(1000);
					System.out
							.println("dew main waiting for create dewset sleeping ac +"
									+ i);

				}

				while (dew == null) {

					i++;
					Thread.sleep(1000);
					System.out
							.println("dew main waiting for create dewset sleeping dew +"
									+ i);

					dew = new dewset();

				}

				while (dew.ac == null) {

					i++;
					Thread.sleep(1000);
					System.out
							.println("dew main waiting for create dewset sleeping dew ac +"
									+ i);

					dew.ac = ac;

				}
				dew.loadmainfile();

			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				delay eee = new delay();
				eee.start();

				e.printStackTrace();
			}

		}
	}

	class giveft implements Runnable {
		private String	give	= "";
		private int		amount	= 0;

		public giveft(String give, int amount) {
			this.give = give;
			this.amount = amount;
		}

		@Override
		public void run() {
			if (give.equalsIgnoreCase("exp")) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.setExp(p.getExp() + amount);
					p.sendMessage("ptdew free item : you got exp += " + amount
							+ " = " + p.getExp());
				}
				return;
			}
			else if (give.equalsIgnoreCase("money")) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					try {
						Economy.add(p.getName(), amount);
					}
					catch (UserDoesNotExistException | NoLoanPermittedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						p.sendMessage("ptdew free item : "
								+ tr.gettr("you_got_money") + " += " + amount
								+ " = " + Economy.getMoney(p.getName()));
					}
					catch (UserDoesNotExistException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return;
			}
			else if (give.equalsIgnoreCase("giveitem")) {

				for (Player pr : Bukkit.getOnlinePlayers()) {

					// check that player have item name

					boolean don = false;

					for (ItemStack it : pr.getInventory().getContents()) {
						if (it == null) continue;

						if (it.getItemMeta() == null) continue;

						if (it.getItemMeta().hasDisplayName() == false)
							continue;

						if (it.getItemMeta().getDisplayName()
								.equalsIgnoreCase("noft")) {
							don = true;
							break;
						}
					}

					for (ItemStack it : pr.getInventory().getArmorContents()) {
						if (it == null) continue;

						if (it.getItemMeta() == null) continue;

						if (it.getItemMeta().hasDisplayName() == false)
							continue;

						if (it.getItemMeta().getDisplayName()
								.equalsIgnoreCase("noft")) {
							don = true;
							break;
						}
					}

					if (don == true) continue;

					for (int i = 1; i <= amount; i++) {
						giveitem ii = new giveitem(pr);
					}
				}

			}

		}
	}

	class giveitem implements Runnable {
		private Player	pr;

		public giveitem(Player player) {
			this.pr = player;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this,
					rnd.nextInt(1200));
		}

		public void run() {
			if (pr.isOnline() == false) {
				// switch player
				if (Bukkit.getOnlinePlayers().length == 0) {
					return;
				}

				int ga = 0;
				while (ga <= 0 || ga >= Bukkit.getOnlinePlayers().length) {

					ga = rnd.nextInt();

					pr = Bukkit.getOnlinePlayers().clone()[ga];

				}
			}


			int ranid  = 0;
			//dprint.r.printAll("ft give item randid " + ranid + " , " + allBlockInGameMax);
			ranid = rnd.nextInt(allBlockInGameMax);
			
				

			if (pr.getInventory().firstEmpty() == -1) {
				// recall
				giveitem ab = new giveitem(pr);

			}

			String bai[] = allBlockInGame[ranid].split(":");
			ItemStack it = new ItemStack( Material.getMaterial( bai[0]));
			it.getData().setData( Byte.parseByte(bai[1]));
			ItemStack it2 = it.getData().toItemStack();
			
			it2.setAmount(1);

			pr.getInventory().addItem(it2);

		}
	}

	class lastinv {
		public int		s1	= 0;
		public String	s2	= "";
		public int		s3	= 0;

		// delay item amount
		// 50 exp 30
		// 100 money 70
		// 100 fix 5
	}

	class monkill implements Runnable {
		private int	delay;
		private int	sec;
		private int	rad;

		public monkill(int sec, int delay, int rad) {
			this.delay = delay;
			this.sec = sec;
			this.rad = rad;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, delay);

		}

		@Override
		public void run() {
			sec--;
			for (World wo : Bukkit.getWorlds()) {
				for (Entity ent : wo.getEntities()) {
					if (ent == null) {
						continue;
					}
					if (ent instanceof Creature) {
						Creature en = (Creature) ent;

						// focus to nearest entity
						for (Entity ent2 : wo.getEntities()) {
							if (ent2 == null) {
								continue;
							}

							if (ent2 instanceof Creature) {
								Creature en2 = (Creature) ent2;

								if (en2.getLocation()
										.distance(en.getLocation()) > rad) {
									continue;
								}

								if (en.getEntityId() == en2.getEntityId()) {
									continue;
								}

								en.setTarget(en2);
								dprint.r.printC("mon kill "
										+ en.getLocation().getBlockX() + ","
										+ en.getLocation().getBlockY() + ","
										+ en.getLocation().getBlockZ() + " to "
										+

										en2.getLocation().getBlockX() + ","
										+ en2.getLocation().getBlockY() + ","
										+ en2.getLocation().getBlockZ()

								);

								break;
							}
						}

					}
				}
			}

			if (sec <= 0) return;

			new monkill(sec, delay, rad);

		}
	}

	class monkillhuman implements Runnable {
		private int	delay;
		private int	sec;
		private int	rad;

		public monkillhuman(int sec, int delay, int rad) {
			this.delay = delay;
			this.sec = sec;
			this.rad = rad;

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, delay);

		}

		@Override
		public void run() {
			sec--;
			for (World wo : Bukkit.getWorlds()) {
				for (Entity ent : wo.getEntities()) {
					if (ent == null) {
						continue;
					}
					if (ent instanceof Creature) {
						Creature en = (Creature) ent;

						// focus to nearest entity
						for (LivingEntity en2 : wo.getPlayers()) {
							if (en2 == null) {
								continue;
							}

							if (en2.getLocation().distance(en.getLocation()) > rad) {
								continue;
							}

							if (en.getEntityId() == en2.getEntityId()) {
								continue;
							}

							en.setTarget(en2);

							dprint.r.printC("mon kill human"
									+ en.getLocation().getBlockX() + ","
									+ en.getLocation().getBlockY() + ","
									+ en.getLocation().getBlockZ() + " to " +

									en2.getLocation().getBlockX() + ","
									+ en2.getLocation().getBlockY() + ","
									+ en2.getLocation().getBlockZ()

							);

							break;

						}

					}
				}
			}

			if (sec <= 0) return;

			new monkillhuman(sec, delay, rad);

		}
	}

	class sell_type {

		public int		id;
		public double	price;
	}

	class teleport_fish implements Runnable {
		private Player		p;
		private Location	loc;

		public teleport_fish(Player p, Location loc) {
			this.p = p;
			this.loc = loc;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, 1);
		}

		@Override
		public void run() {
			p.teleport(loc);

		}
	}

	public static double getprice(int itemid) {
		double price = -1;
		for (int gr = 0; gr < DigEventListener2.sellmax; gr++)
			if (DigEventListener2.sell[gr].id == itemid) {
				price = DigEventListener2.sell[gr].price;
				break;
			}

		return price;
	}

	public dewset			dew			= dewddflower.Main.ds;
	public JavaPlugin		ac			= null;
	int						maxl		= 0;

	public String[] allBlockInGame = new String[500];
	public int allBlockInGameMax = 0;
	
	lastinv[]				inv			= new lastinv[30];

	public static sell_type	sell[];

	public static int		sellmax;

	public Random			rnd			= new Random();

	String					pdura		= "dewdd.ft.fix";

	String					pautoshoot	= "dewdd.ft.autoshoot";

	String					pbleed		= "dewdd.ft.bleed";

	String					pmonkill	= "dewdd.ft.monkill";

	public String			folder_name	= "plugins" + File.separator
												+ "dewdd_ft";

	public DigEventListener2() {
		delay abc = new delay();
		abc.start();

		delay_dewset abc2 = new delay_dewset();
		abc2.start();

	}

	@EventHandler
	public void eventja(BlockRedstoneEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getBlock().getWorld().getName())) {
			return;
		}

		if (rnd.nextInt(100) < 95) return;
		autosell abr = new autosell(e.getBlock());
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, abr);

	}

	@EventHandler
	public void eventja(EntityDamageByEntityEvent e) {

		if (e.getDamager() == null) return;
		if (e.getEntity() == null) return;

		if (!tr.isrunworld(ac.getName(), e.getEntity().getWorld().getName())) {
			return;
		}

		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (e.getEntity() instanceof LivingEntity) {

				LivingEntity en = (LivingEntity) e.getEntity();

				if (p.getItemInHand() == null) return;

				if (p.getItemInHand().getType() == Material.STICK) {
					p.sendMessage(dprint.r.color(tr
							.gettr("canpickupitems_set_to")));
					en.setCanPickupItems(!en.getCanPickupItems());
					return;
				}

				if (p.getItemInHand().getType() == Material.SADDLE) {

					if (en.getPassenger() == null) {

						if (p.getPassenger() != null)
							if (p.getPassenger().getEntityId() == en
									.getEntityId()) {
								p.sendMessage(dprint.r.color(tr
										.gettr("error_can't_ride_who_riding_you")));
								return;
							}
						en.setPassenger(p);

					}

					return;
				}

			}

		}

	}

	@EventHandler
	public void eventja(EntityTargetEvent e) {

		if (e.getEntity() instanceof LivingEntity) {
			LivingEntity a = (LivingEntity) e.getEntity();

			if (e.getTarget() instanceof LivingEntity) {
				LivingEntity b = (LivingEntity) e.getTarget();
				if (b.hasPotionEffect(PotionEffectType.INVISIBILITY)) {

					if (e.getReason() == e.getReason().FORGOT_TARGET) return;

					if (e.getReason() == e.getReason().TARGET_ATTACKED_ENTITY)
						return;

					if (e.getReason() == e.getReason().TARGET_ATTACKED_OWNER)
						return;

					e.setTarget(null);
					e.setCancelled(true);
				}
			}

		}
	}

	@EventHandler
	public void eventja(EntityTargetLivingEntityEvent e) {

		if (e.getEntity() instanceof LivingEntity) {
			LivingEntity a = (LivingEntity) e.getEntity();

			if (e.getTarget() instanceof LivingEntity) {
				LivingEntity b = (LivingEntity) e.getTarget();
				if (b.hasPotionEffect(PotionEffectType.INVISIBILITY)) {

					if (e.getReason() == e.getReason().FORGOT_TARGET) return;

					e.setTarget(null);

					e.setCancelled(true);
				}
			}

		}
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		String m[] = e.getMessage().split("\\s+");
		Player p = e.getPlayer();

		if (m[0].equalsIgnoreCase("/dft")) {
			if (m.length == 1) {
				p.sendMessage("/dft reload");
				p.sendMessage("/dft list");
				p.sendMessage("/dft bleed");
				p.sendMessage("/dft autoshoot <EntityType> <amount> <mode>");

				p.sendMessage("/dft setnpcname <name>");
				p.sendMessage("/dft setle <name>");

				p.sendMessage("/dft monkill <second> <delaytick> <radius>");
				p.sendMessage("/dft monkillhuman <second> <delaytick> <radius>");
				return;
			}
			else if (m.length > 1)
				if (m[1].equalsIgnoreCase("monkillhuman")) {
					if (m.length != 5) {
						p.sendMessage("/sky monkillhuman <second> <delaytick> <radius>");
						return;
					}

					if (!p.hasPermission(pmonkill)) {
						p.sendMessage(tr.gettr("you_don't_have_permission")
								+ pmonkill);
						return;
					}

					int sec = Integer.parseInt(m[2]);
					int tick = Integer.parseInt(m[3]);
					int rad = Integer.parseInt(m[4]);

					new monkillhuman(sec, tick, rad);
				}
				else if (m[1].equalsIgnoreCase("monkill")) {
					if (m.length != 5) {
						p.sendMessage("/sky monkill <second> <delaytick> <radius>");
						return;
					}

					if (!p.hasPermission(pmonkill)) {
						p.sendMessage(tr.gettr("you_don't_have_permission")
								+ pmonkill);
						return;
					}

					int sec = Integer.parseInt(m[2]);
					int tick = Integer.parseInt(m[3]);
					int rad = Integer.parseInt(m[4]);

					new monkill(sec, tick, rad);

				}
				else if (m[1].equalsIgnoreCase("setle")) {
					if (m.length != 3) {
						p.sendMessage("/sky setle <name>");
						return;
					}

					boolean pro = dew.checkpermissionarea(p.getLocation()
							.getBlock(), p.getPlayer(), "build");
					if (pro == true) {
						p.sendMessage(tr.gettr("not_your_zone"));
						return;
					}

					// search near npc

					LivingEntity nearest = null;

					for (Entity en2 : p.getWorld().getEntities()) {

						if (en2 == null) {
							continue;
						}

						if (!(en2 instanceof LivingEntity)) {
							continue;
						}

						if (en2 instanceof Player) {
							continue;
						}

						LivingEntity en = (LivingEntity) en2;

						if (nearest == null) {
							nearest = en;
							continue;
						}

						if (en.getLocation().distance(p.getLocation()) > 50) {
							continue;
						}

						if (en.getLocation().distance(p.getLocation()) < nearest
								.getLocation().distance(p.getLocation())) {
							nearest = en;
						}

					}

					if (nearest == null) {
						p.sendMessage(tr.gettr("not_found_livingentity"));
						return;
					}

					p.sendMessage(tr.gettr("seting_livingentity_name"));
					LivingEntity vi = nearest;
					vi.setCustomName(m[2]);
					vi.setCustomNameVisible(true);
					p.sendMessage(tr.gettr("set_complete") + " "
							+ vi.getType().getName() + " at "
							+ vi.getLocation().getBlockX() + ","
							+ vi.getLocation().getBlockY() + ","
							+ vi.getLocation().getBlockZ());

					// setname from trade

				}

				else if (m[1].equalsIgnoreCase("setnpcname")) {
					if (m.length != 3) {
						p.sendMessage("/sky setnpcname <name>");
						return;
					}

					boolean pro = dew.checkpermissionarea(p.getLocation()
							.getBlock(), p.getPlayer(), "build");
					if (pro == true) {
						p.sendMessage(tr.gettr("not_your_zone"));
						return;
					}

					// search near npc

					Entity nearest = null;

					for (Entity en : p.getWorld().getEntities()) {

						if (en == null) {
							continue;
						}
						if (en.getType() == EntityType.VILLAGER) {
							if (nearest == null) {
								nearest = en;
								continue;
							}

							if (en.getLocation().distance(p.getLocation()) > 50) {
								continue;
							}

							if (en.getLocation().distance(p.getLocation()) < nearest
									.getLocation().distance(p.getLocation())) {
								nearest = en;
							}

						}
					}

					if (nearest == null) {
						p.sendMessage(tr.gettr("not_found_npc"));
						return;
					}

					p.sendMessage(tr.gettr("seting_npc_name"));
					Villager vi = (Villager) nearest;
					vi.setCustomName(m[2]);
					vi.setCustomNameVisible(true);
					p.sendMessage(tr.gettr("set_complete"));

					// setname from trade

				}

				else if (m[1].equalsIgnoreCase("reload")) {
					reloadpl();
					p.sendMessage("reloaded");
				}
				else if (m[1].equalsIgnoreCase("list")) {
					// bubble sort
					for (int i = 0; i < DigEventListener2.sellmax; i++) {
						for (int j = 0; j < DigEventListener2.sellmax - 1 - i; j++) {
							if (DigEventListener2.sell[j].price < DigEventListener2.sell[j + 1].price) {
								int tid = sell[j].id;
								double pri = sell[j].price;

								sell[j].id = sell[j + 1].id;
								sell[j].price = sell[j + 1].price;

								sell[j + 1].id = tid;
								sell[j + 1].price = pri;

							}
						}
					}

					for (int i = 0; i < DigEventListener2.sellmax; i++) {
						p.sendMessage(dprint.r
								.color(DigEventListener2.sell[i].id
										+ "("
										+ Material.getMaterial(
												DigEventListener2.sell[i].id)
												.name() + ") price "
										+ DigEventListener2.sell[i].price));
					}

				}
				else if (m[1].equalsIgnoreCase("autoshoot")) {
					if (!p.hasPermission(pautoshoot) || m.length != 5) {
						p.sendMessage("/dft autoshoot <entitytype> <amount> <mode>  (only op!");
						return;
					}

					EntityType ent = EntityType.fromName(m[2]);
					if (ent == null) {
						for (EntityType ent2 : EntityType.values()) {
							p.sendMessage(ent2.getName());
						}
						return;
					}

					new autoshoot(Integer.parseInt(m[3]), ent, p,
							Integer.parseInt(m[4]));

				}
				else if (m[1].equalsIgnoreCase("bleed")) {
					if (!p.hasPermission(pbleed)) {
						p.sendMessage("/dft bleed   (only op!");
						return;
					}

					for (Entity en : p.getWorld().getEntities()) {
						if (en == null) {
							continue;
						}

						if (en.getType() == EntityType.PIG) {
							Pig x = (Pig) en;
							x.setBreed(true);

							continue;
						}

						if (en.getType() == EntityType.COW) {
							Cow x = (Cow) en;
							x.setBreed(true);
							continue;
						}

						if (en.getType() == EntityType.SHEEP) {
							Sheep x = (Sheep) en;
							x.setBreed(true);
							continue;
						}

						if (en.getType() == EntityType.MUSHROOM_COW) {
							MushroomCow x = (MushroomCow) en;
							x.setBreed(true);
							continue;
						}

						if (en.getType() == EntityType.CHICKEN) {
							Chicken x = (Chicken) en;
							x.setBreed(true);
							continue;
						}

						if (en.getType() == EntityType.WOLF) {
							Wolf x = (Wolf) en;

							x.setBreed(true);
							continue;
						}

						if (en.getType() == EntityType.VILLAGER) {
							Villager x = (Villager) en;

							x.setBreed(true);
							continue;
						}

					}
				}

		}
		else if (m[0].equalsIgnoreCase("/dnick")) {
			if (m.length != 3) {
				p.sendMessage("/dnick <prefix,suffix> <name>");
				return;
			}

			if (m[1].equalsIgnoreCase("prefix") == false
					&& m[1].equalsIgnoreCase("suffix") == false) {
				p.sendMessage("prefix or suffix");
				return;
			}

			p.sendMessage("sending..");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					"manuaddv " + p.getName() + " " + m[1] + " " + m[2]);

		}

	}

	@EventHandler
	public void eventja(PlayerFishEvent e) {

		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		e.getState();
		if (e.getState() == State.IN_GROUND) {
			if (e.getPlayer().getItemInHand().getType() == Material.FISHING_ROD)
				if (e.getPlayer().getItemInHand().getItemMeta()
						.getDisplayName().equalsIgnoreCase("jumper")) {
					new teleport_fish(e.getPlayer(), e.getHook().getLocation());
					return;
				}
		}
		else {
			e.getState();
			if (e.getState() == State.CAUGHT_ENTITY) {
				System.out.println(e.getPlayer().getName() + " CAUGHT_ENTITY "
						+ e.getCaught().getType().name());
			}
		}

	}
	
	public void loadMissionBlockFile() {

		String filena = folder_name + File.separator + "missionblock.txt";
		File fff = new File(filena);

		try {

			allBlockInGame = new String[500];
			allBlockInGameMax = 0;
			
			

			fff.createNewFile();

			dprint.r.printAll("loading mission file : " + filena);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filena);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			String m[];

			while ((strLine = br.readLine()) != null) {

				m = strLine.split("\\s+");
				m = strLine.split(":");
				// Print the content on the console

				allBlockInGame[allBlockInGameMax] = m[0] + ":" + m[1];
				// d.pl("...");
				// rs[rsMax - 1].mission = 0;

				allBlockInGameMax ++;
			}

			dprint.r.printAll(" Loaded " + filena);

			in.close();
		} catch (Exception e) {// Catch exception if any
			dprint.r.printAll("Error load " + filena + e.getMessage());
		}
	}

	public void loadinventoryfile() {
		String filena2 = "ptdew_dewdd_ft_list.txt";

		File dir = new File(folder_name);
		dir.mkdir();

		String filena = folder_name + File.separator + filena2;

		try {
			File fff = new File(filena);
			fff.createNewFile();

			FileInputStream fstream = new FileInputStream(filena);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			// sthae
			// aosthoeau
			// * save
			String m[];

			maxl = 0;
			inv = new lastinv[30];
			for (int l = 0; l < 30; l++) {
				inv[l] = new lastinv();
			}

			while ((strLine = br.readLine()) != null) {

				m = strLine.split("\\s+");
				maxl++;
				inv[maxl - 1].s1 = Integer.parseInt(m[0]);
				inv[maxl - 1].s2 = m[1];
				inv[maxl - 1].s3 = Integer.parseInt(m[2]);

			}

			in.close();

		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dprint.r.printC("reloaded free item file" + filena);

	}

	public void loadselllist() {
		String worldf = "ptdew_dewdd_sell.txt";

		File dir = new File(folder_name);
		dir.mkdir();

		String filena = folder_name + File.separator + worldf;
		File fff = new File(filena);

		try {

			DigEventListener2.sellmax = 0;
			DigEventListener2.sell = new sell_type[500];
			for (int i = 0; i < 500; i++) {
				DigEventListener2.sell[i] = new sell_type();
			}

			fff.createNewFile();

			System.out.println("ptdeW&DewDD ft sell loading : " + filena);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filena);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			// sthae
			// aosthoeau
			// * save

			String m[];

			while ((strLine = br.readLine()) != null) {
				m = strLine.split("\\s+");

				DigEventListener2.sellmax++;
				DigEventListener2.sell[DigEventListener2.sellmax - 1].id = Integer
						.parseInt(m[0]);
				DigEventListener2.sell[DigEventListener2.sellmax - 1].price = Double
						.parseDouble(m[1]);

			}

			System.out.println("ptdew&DewDD ft : Sell loaded " + filena);

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			System.err.println("Error load " + filena + e.getMessage());
		}
	}

	public void reloadpl() {
		loadinventoryfile();
		loadselllist();
		loadMissionBlockFile();
		// clear all schude

		Bukkit.getScheduler().cancelTasks(ac);

		for (int l = 0; l < maxl; l++) {
			giveft a = new giveft(inv[l].s2, inv[l].s3);
			Bukkit.getScheduler().scheduleSyncRepeatingTask(ac, a, inv[l].s1,
					inv[l].s1);
		}

	}

}