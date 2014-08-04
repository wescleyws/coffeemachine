package br.ufpb.dce.aps.coffeemachine.impl;

import static org.mockito.Matchers.anyDouble;

import java.util.ArrayList;
import java.util.List;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Dispenser;
import br.ufpb.dce.aps.coffeemachine.Display;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.DrinkDispenser;
import br.ufpb.dce.aps.coffeemachine.Messages;
import net.compor.frameworks.jcf.api.ComporFacade;

public class MyCoffeeMachine extends ComporFacade implements CoffeeMachine {
	private List<Coin> coins, troco;
	private int totalcentavos = 0;
	private ComponentsFactory fac;
	private CashBox box;
	private Drink drink;
	private final int CAFE = 35;

	public MyCoffeeMachine(ComponentsFactory factory) {
		fac = factory;
		fac.getDisplay().info("Insert coins and select a drink!");
		box = fac.getCashBox();
		this.coins = new ArrayList<Coin>();
		this.troco = new ArrayList<Coin>();

	}

	public void insertCoin(Coin coin) {

		if (coin == null) {
			throw new CoffeeMachineException("Moeda não aceita");
		}

		this.coins.add(coin);

		totalcentavos += coin.getValue();
		fac.getDisplay()
				.info("Total: US$ " + totalcentavos / 100 + "." + totalcentavos
						% 100);
	}

	public void retornaTroco(int change) {
		for (Coin moeda : Coin.reverse()) {
			while (moeda.getValue() <= change) {
				fac.getCashBox().release(moeda);
				this.troco.add(moeda);
				change -= moeda.getValue();
			}
		}

	}

	public void planCoins(int troco) {
		for (Coin moeda : Coin.reverse()) {
			if (moeda.getValue() <= troco) {
				this.fac.getCashBox().count(moeda);
				troco -= moeda.getValue();
			}
		}
	}

	public int calculaTroco() {

		int contador = 0;

		for (Coin aux : this.coins) {
			contador += aux.getValue();

		}

		return contador - this.CAFE;
	}

	public void cancel() {
		if (this.totalcentavos == 0) {
			throw new CoffeeMachineException("Não possui moedas inseridas");
		}

		fac.getDisplay().warn(Messages.CANCEL);
		removerCoin(fac);
		fac.getDisplay().info(Messages.INSERT_COINS);

	}

	private void removerCoin(ComponentsFactory factory) {
		List<Integer> remover = new ArrayList<Integer>();

		for (Coin coin : Coin.reverse()) {
			for (int i = 0; i < this.coins.size(); i++) {
				if (this.coins.get(i).equals(coin)) {
					fac.getCashBox().release(coins.get(i));
					remover.add(new Integer(i));
				}
			}
		}
		this.coins.removeAll(remover);
	}

	public void select(Drink drink) {

		switch (drink) {
		case BLACK:

			if (!fac.getCupDispenser().contains(1)) {
				fac.getDisplay().warn(Messages.OUT_OF_CUP);
				removerCoin(fac);
				fac.getDisplay().info(Messages.INSERT_COINS);
				return;
			}

			if (!fac.getWaterDispenser().contains(1.2)) {
				fac.getDisplay().warn(Messages.OUT_OF_WATER);
				removerCoin(fac);
				fac.getDisplay().info(Messages.INSERT_COINS);
				return;
			}

			if (!fac.getCoffeePowderDispenser().contains(1.2)) {
				fac.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
				removerCoin(fac);
				fac.getDisplay().info(Messages.INSERT_COINS);
				return;
			}

			fac.getDisplay().info(Messages.MIXING);
			fac.getCoffeePowderDispenser().release(1.5);
			fac.getWaterDispenser().release(2.5);

			fac.getDisplay().info(Messages.RELEASING);
			fac.getCupDispenser().release(1);
			fac.getDrinkDispenser().release(anyDouble());
			fac.getDisplay().info(Messages.TAKE_DRINK);

			fac.getDisplay().info(Messages.INSERT_COINS);

			break;

		case BLACK_SUGAR:

			if (!fac.getCupDispenser().contains(1)) {
				fac.getDisplay().warn(Messages.OUT_OF_CUP);
				removerCoin(fac);
				fac.getDisplay().info(Messages.INSERT_COINS);
				return;
			}

			if (!fac.getWaterDispenser().contains(1.2)) {
				fac.getDisplay().warn(Messages.OUT_OF_WATER);
				removerCoin(fac);
				fac.getDisplay().info(Messages.INSERT_COINS);
				return;
			}
			fac.getCoffeePowderDispenser().contains(2.3);

			if (!fac.getSugarDispenser().contains(1.2)) {
				fac.getDisplay().warn(Messages.OUT_OF_SUGAR);
				removerCoin(fac);
				fac.getDisplay().info(Messages.INSERT_COINS);

				return;
			}

			fac.getDisplay().info(Messages.MIXING);
			fac.getCoffeePowderDispenser().release(1.2);
			fac.getWaterDispenser().release(2.2);
			fac.getSugarDispenser().release(3.2);

			fac.getDisplay().info(Messages.RELEASING);
			fac.getCupDispenser().release(1);
			fac.getDrinkDispenser().release(2.2);
			fac.getDisplay().info(Messages.TAKE_DRINK);

			fac.getDisplay().info(Messages.INSERT_COINS);

			break;

		case WHITE:

			fac.getCupDispenser().contains(1);
			fac.getWaterDispenser().contains(1);
			fac.getCoffeePowderDispenser().contains(1);
			fac.getCreamerDispenser().contains(1.2);

			fac.getDisplay().info(Messages.MIXING);
			fac.getCoffeePowderDispenser().release(1.9);
			fac.getWaterDispenser().release(1.10);
			fac.getCreamerDispenser().release(1.8);

			fac.getDisplay().info(Messages.RELEASING);
			fac.getCupDispenser().release(1);
			fac.getDrinkDispenser().release(0.9);
			fac.getDisplay().info(Messages.TAKE_DRINK);

			fac.getDisplay().info(Messages.INSERT_COINS);

			break;

		case WHITE_SUGAR:

			fac.getCupDispenser().contains(1);
			fac.getWaterDispenser().contains(1);
			fac.getCoffeePowderDispenser().contains(1);
			fac.getCreamerDispenser().contains(1.5);
			fac.getSugarDispenser().contains(1.5);

			planCoins(calculaTroco());

			fac.getDisplay().info(Messages.MIXING);
			fac.getCoffeePowderDispenser().release(1.9);
			fac.getWaterDispenser().release(1.10);
			fac.getCreamerDispenser().release(1.8);
			fac.getSugarDispenser().release(5.0);

			fac.getDisplay().info(Messages.RELEASING);
			fac.getCupDispenser().release(1);
			fac.getDrinkDispenser().release(0.9);
			fac.getDisplay().info(Messages.TAKE_DRINK);

			retornaTroco(calculaTroco());

			fac.getDisplay().info(Messages.INSERT_COINS);

		}

		coins.clear();
	}

}
