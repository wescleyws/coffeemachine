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

	public boolean  planCoins(int troco) {
		for (Coin moeda : Coin.reverse()) {
			if (moeda.getValue() <= troco && this.fac.getCashBox().count(moeda) > 0) {
				
				troco -= moeda.getValue();
			}
		}
		
		return troco == 0;
	}

	public int calculaTroco() {

		int contador = 0;

		for (Coin aux : this.coins) {
			contador += aux.getValue();

		}

		return contador - this.CAFE;
	}

	public void removerCoin(ComponentsFactory fac) {
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

	public void cancel() {
		if (this.totalcentavos == 0) {
			throw new CoffeeMachineException("Não possui moedas inseridas");
		}

		fac.getDisplay().warn(Messages.CANCEL);
		removerCoin(fac);
		fac.getDisplay().info(Messages.INSERT_COINS);

	}

	
	public void select(Drink drink) {
		
		Cafe cafe = null;
		
		switch (drink) {
		
		case BLACK:
			cafe = new CafeBlack();
			break;

		case BLACK_SUGAR:
			cafe = new CafeBlackSugar();
			break;

		case WHITE:
			cafe = new CafeWhite();
			break;

		case WHITE_SUGAR:
			cafe = new CafeWhiteSugar();
			
		}
		
		cafe.prepararCafe(this, fac);

		coins.clear();

	}

}
