/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddbuff;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import dewddtran.tr;

public class buff_run implements Listener {

	class ab implements Runnable {
		private Location	loc;
		private Double		amo;
		private int			ra;
		private boolean		isitemmode;
		private ItemStack	itm;

		public ab(Location loc, Double amo2, int ra, boolean isitemmode,
				ItemStack itm) {
			this.loc = loc;
			this.amo = amo2;
			this.ra = ra;
			this.isitemmode = isitemmode;
			this.itm = itm;
		}

		@Override
		public void run() {
			if (isitemmode == false) {
				ExperienceOrb orb = null;

				Location loc2 = loc;
				loc2.setX(loc2.getX()
						+ (rnd.nextInt(ra) * (rnd.nextInt(2) == 0 ? -1 : 1)));
				loc2.setY(loc2.getY()
						+ (rnd.nextInt(ra) * (rnd.nextInt(2) == 0 ? -1 : 1)));
				loc2.setZ(loc2.getZ()
						+ (rnd.nextInt(ra) * (rnd.nextInt(2) == 0 ? -1 : 1)));

				loc2.getWorld().spawn(loc2, ExperienceOrb.class)
						.setExperience(amo.intValue());
			}
			else {
				Location loc2 = loc;
				loc2.setX(loc2.getX()
						+ (rnd.nextInt(ra) * (rnd.nextInt(2) == 0 ? -1 : 1)));
				loc2.setY(loc2.getY()
						+ (rnd.nextInt(ra) * (rnd.nextInt(2) == 0 ? -1 : 1)));
				loc2.setZ(loc2.getZ()
						+ (rnd.nextInt(ra) * (rnd.nextInt(2) == 0 ? -1 : 1)));
				loc2.getWorld().dropItem(loc2, itm);
			}
		}
	}
	class exprandom extends Thread {
		private Location	loc;
		private int			ra;
		private int			dura;
		private Double		amo;
		private boolean		isitemmode;
		private ItemStack	itm;

		public exprandom(Location loc, int ra, int dura, Double amo2,
				boolean isitemmode, ItemStack itm) {
			this.loc = loc;
			this.ra = ra;
			this.dura = dura;
			this.amo = amo2;
			this.isitemmode = isitemmode;
			this.itm = itm;

		}

		@Override
		public void run() {

			int ee = 0;

			while (ee < dura) {
				ee++;

				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				for (int i = 0; i < 20; i++) {
					ab aaa = new ab(loc, amo, ra, isitemmode, itm);
					Bukkit.getScheduler().scheduleSyncDelayedTask(ac, aaa,
							rnd.nextInt(100));

				}
			}

		}

	}

	JavaPlugin	ac	= null;

	Random		rnd	= new Random();

	@EventHandler
	public void eventja(AsyncPlayerChatEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = e.getPlayer();
		String m[] = e.getMessage().split("\\s+");

		if (m[0].equalsIgnoreCase("giveexp")) {

			if (m.length == 1) {
				player.sendMessage("giveexp <ra> <dura> <amo>");
				return;
			}
			else if (m.length == 4) {

				if (player.isOp() == false) {
					player.sendMessage("only op");
					return;
				}

				int ra = Integer.parseInt(m[1]);
				int dura = Integer.parseInt(m[2]);
				Double amo = Double.parseDouble(m[3]);
				exprandom abr = new exprandom(player.getLocation(), ra, dura,
						amo, false, null);
				abr.start();
			}

		}

		if (m[0].equalsIgnoreCase("giveitem")) {

			if (m.length == 1) {
				player.sendMessage("giveitem <ra> <dura> <amo>");
				return;
			}
			else if (m.length == 4) {

				if (player.isOp() == false) {
					player.sendMessage("only op");
					return;
				}

				int ra = Integer.parseInt(m[1]);
				int dura = Integer.parseInt(m[2]);
				Double amo = Double.parseDouble(m[3]);
				exprandom abr = new exprandom(player.getLocation(), ra, dura,
						amo, true, player.getItemInHand());
				abr.start();
			}

		}

		if (e.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd buff");
			e.setCancelled(true);
		}
		if (e.getMessage().equalsIgnoreCase("dewdd buff") == true) {

			e.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(BlockBreakEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}
		// player.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(200,
		// dew.rnd.nextInt(100)),false);

		Block block = e.getBlock();
		Player player = e.getPlayer();
		// check host block

		switch (block.getTypeId()) {
		case 14:
		case 15:
		case 16:
		case 13:
		case 21:
		case 73:
		case 56:
		case 82:
		case 86:
		case 35:
		case 48:
		case 49:
		case 54:
		case 153:
		case 88:
			player.addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(
					rnd.nextInt(500), rnd.nextInt(100)), false);
			break;
		}

		randomplaynote(player.getLocation());

	}

	@EventHandler
	public void eventja(PlayerExpChangeEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		e.getPlayer().addPotionEffect(
				PotionEffectType.SPEED.createEffect(rnd.nextInt(150),
						rnd.nextInt(5)), false);
		e.getPlayer().addPotionEffect(
				PotionEffectType.FIRE_RESISTANCE.createEffect(rnd.nextInt(150),
						rnd.nextInt(5)), false);

		e.getPlayer().addPotionEffect(
				PotionEffectType.INCREASE_DAMAGE.createEffect(rnd.nextInt(150),
						rnd.nextInt(5)), false);
		e.getPlayer().addPotionEffect(
				PotionEffectType.DAMAGE_RESISTANCE.createEffect(
						rnd.nextInt(150), rnd.nextInt(5)), false);

		e.getPlayer().addPotionEffect(
				PotionEffectType.FAST_DIGGING.createEffect(rnd.nextInt(150),
						rnd.nextInt(5)), false);
		e.getPlayer().addPotionEffect(
				PotionEffectType.JUMP.createEffect(rnd.nextInt(100),
						rnd.nextInt(5)), false);

		double money = tr.gettrint("CONFIG_GET_EXP_GET_MONEY") * e.getAmount();

		try {
			Economy.add(e.getPlayer().getName(), money);
		}
		catch (UserDoesNotExistException | NoLoanPermittedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// e.getPlayer().sendMessage("+=" + money);

	}

	@EventHandler
	public void eventja(PlayerLevelChangeEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		boolean forc = !(e.getPlayer().getInventory().first(38) == -1);

		e.getPlayer().addPotionEffect(
				PotionEffectType.SPEED.createEffect(rnd.nextInt(500),
						rnd.nextInt(5)), forc);
		e.getPlayer().addPotionEffect(
				PotionEffectType.FIRE_RESISTANCE.createEffect(
						rnd.nextInt(1000), rnd.nextInt(5)), forc);
		e.getPlayer().addPotionEffect(
				PotionEffectType.REGENERATION.createEffect(rnd.nextInt(500),
						rnd.nextInt(5)), forc);

		e.getPlayer().addPotionEffect(
				PotionEffectType.INCREASE_DAMAGE.createEffect(rnd.nextInt(500),
						rnd.nextInt(5)), forc);
		e.getPlayer().addPotionEffect(
				PotionEffectType.DAMAGE_RESISTANCE.createEffect(
						rnd.nextInt(500), rnd.nextInt(5)), forc);
		e.getPlayer().addPotionEffect(
				PotionEffectType.HEAL.createEffect(rnd.nextInt(500),
						rnd.nextInt(5)), forc);
		e.getPlayer().addPotionEffect(
				PotionEffectType.FAST_DIGGING.createEffect(rnd.nextInt(500),
						rnd.nextInt(5)), forc);

	}

	public void randomplaynote(Location player) {
		if (rnd.nextInt(100) > 10) {
			return;
		}

		for (Player pla : player.getWorld().getPlayers()) {
			pla.playSound(player, Sound.NOTE_PIANO, rnd.nextInt(50),
					rnd.nextFloat() + 1);
		}
	}

} // dig